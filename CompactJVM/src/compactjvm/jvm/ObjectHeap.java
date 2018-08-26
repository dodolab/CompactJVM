package compactjvm.jvm;

import compactjvm.classfile.ClassFile;
import compactjvm.exceptions.OutOfHeapMemException;
import compactjvm.jvm.instructions.AAAException;
import compactjvm.structures.SGenericRef;
import compactjvm.structures.*;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Heap of the virtual machine
 *
 * @author Adam Vesecky
 */
public class ObjectHeap {

    // THE HEAP. ActiveHeapOffset and inactiveHeapOffset determines which
    // half is the active one 
    private SStruct[] heap;

    private GarbageCollector garbageCollector;

    // offset of active and inactive part
    private int activeHeapOffset;
    private int inactiveHeapOffset;

    private int heapSize;

    // index of the first free space <0-heapSize>
    private int nextFreeSpace;

    private MethodArea methodArea;
    private JVMThread jvmThread;

    public ObjectHeap(MethodArea methodArea, int size) {
        this.methodArea = methodArea;
        heap = new SStruct[size];
        heapSize = size / 2; // only one half is an active heap
        activeHeapOffset = 0;
        inactiveHeapOffset = heapSize;
        nextFreeSpace = 0;
        this.garbageCollector = new GarbageCollector(this);
        JVMLogger.log(JVMLogger.TAG_HEAP, "Creating heap");
    }

    public JVMThread getJVMThread() {
        return jvmThread;
    }

    public void setJVMThread(JVMThread thread) {
        this.jvmThread = thread;
    }

    /**
     * Writes a new value into the heap
     * Example: If the record contains 2 words in its head (1 word for the 
     * index of the class, 1 word for e.g. GC), it will be written at (reference+2+index)
     * 
     */
    public <T extends SStruct> void writeToHeap(int reference, int index, T value) {
        int headerSize = 1;
        writeToActiveHeap(reference + headerSize + index, value);
    }

    /**
     * Reads a value from a heap
     */
    public <T extends SStruct> T readFromHeap(int reference, int index) {
        int headerSize = 1;
        T output = readFromActiveHeap(reference + headerSize + index);
        return output;
    }

    /**
     * Reads a primitive array from a heap, based on reference
     *
     */
    public SStruct[] readPrimitiveArrayFromHeap(int reference) {
        SArrayRef arrayRef = readFromActiveHeap(reference);
        SStruct[] arr = new SStruct[arrayRef.getSize()];

        for (int i = 0; i < arrayRef.getSize(); i++) {
            arr[i] = readFromHeap(reference, i);
        }
        return arr;
    }

    /**
     * Reads an object array from an active heap
     *
     */
    public SGenericRef[] readObjectArrayFromHeap(int reference) {
        SArrayRef arrayRef = readFromActiveHeap(reference);
        SGenericRef[] arr = new SGenericRef[arrayRef.getSize()];

        JVMLogger.log(JVMLogger.TAG_HEAP, "Read object array #" + reference + "#-->" + arrayRef);

        // disable logging for that moment (we don't need to have each read in the log)
        boolean loggingEnabled = JVMLogger.loggingEnabled(JVMLogger.TAG_HEAP);
        JVMLogger.disableLogging(JVMLogger.TAG_HEAP);

        for (int i = 0; i < arrayRef.getSize(); i++) {
            SGenericRef ref = readFromHeap(reference, i);
            arr[i] = ref;
        }

        if (loggingEnabled) {
            JVMLogger.enableLogging(JVMLogger.TAG_HEAP);
        }

        return arr;
    }

    /**
     * Allocates a space for the data of the object and returns a reference 
     * for that particular object
     * 
     * @param classFile
     * @return
     */
    public SObjectRef allocObject(ClassFile classFile) throws OutOfHeapMemException {

        int wordsRequired = 1 + classFile.recursiveFieldCount;
        checkHeapSpace(wordsRequired);
        int reference = nextFreeSpace;

        JVMLogger.log(JVMLogger.TAG_HEAP, "Allocating object #" + reference + "#[sz=" + wordsRequired + "]-->" + classFile.className);

        // and this, gentlemen, may be faster then malloc in C !!!
        SObjectRef ref = new SObjectRef(reference, classFile);
        writeToActiveHeap(reference, ref);
        nextFreeSpace += wordsRequired;
        initializeSpace(reference + 1/*getObjectHeaderSize()*/, classFile.recursiveFieldCount);
        return ref;
    }

    /**
     * Allocates a space for an array
     *
     * @param arraySize number of items of the array
     */
    public <T extends SStruct> SArrayRef allocPrimitiveArray(T[] arr, int arraySize) throws OutOfHeapMemException {

        int wordsRequired = 1 + arraySize;
        checkHeapSpace(wordsRequired);
        int reference = nextFreeSpace;

        JVMLogger.log(JVMLogger.TAG_HEAP, "Allocating primitive array [" + reference + "][sz=" + arraySize + "]-->");
        JVMLogger.increaseGlobalPadding(4);
        SArrayRef ref = new SArrayRef(reference, null, arraySize);
        writeToActiveHeap(reference, ref);

        // write default values into heap
        for (int i = 0; i < arraySize; i++) {
            writeToHeap(reference, i, arr[i]);
        }
        JVMLogger.decreaseGlobalPadding(4);
        nextFreeSpace += wordsRequired;

        return ref;
    }

    /**
     * Allocates an object array
     *
     */
    public SArrayRef allocObjectArray(ClassFile classFile, int arraySize) throws OutOfHeapMemException {
        
        int wordsRequired = 1 + arraySize;
        checkHeapSpace(wordsRequired);
        int reference = nextFreeSpace;

        JVMLogger.log(JVMLogger.TAG_HEAP, "Allocating object array #" + reference + "#[sz=" + arraySize + "]-->" + classFile.className);

        SArrayRef arrayRef = new SArrayRef(reference, classFile, arraySize);
        writeToActiveHeap(reference, arrayRef);

        JVMLogger.increaseGlobalPadding(4);
        // write references to the heap
        for (int i = 0; i < arraySize; i++) {
            writeToHeap(reference, i, new SObjectRef(-1, classFile));
        }
        JVMLogger.decreaseGlobalPadding(4);

        nextFreeSpace += wordsRequired;

        return arrayRef;

    }

    public GarbageCollector getGarbageCollector() {
        return this.garbageCollector;
    }

    /**
     * Writes into an active part of the heap (index is a real index calculated
     * from the beginning of this part of the heap)
     *
     */
    private <T extends SStruct> void writeToActiveHeap(int index, T value) {
        try {
            JVMLogger.log(JVMLogger.TAG_HEAP, "Write #" + index + "#-->" + value);
            heap[index + activeHeapOffset] = value;
        } catch (ArrayIndexOutOfBoundsException e) {
            try {
                AAAException.throwException(new OutOfHeapMemException(), this.jvmThread.getStack(), this, methodArea);
            } catch (Exception ex) {
                // nothing to handle here
            }
        }
    }

    /**
     * Reads from an active part of the heap (index is a real index calculated
     * from the beginning of this part of the heap)
     *
     */
    private <T extends SStruct> T readFromActiveHeap(int index) {
        T value = (T) heap[index + activeHeapOffset];
        JVMLogger.log(JVMLogger.TAG_HEAP, "Read #" + index + "#-->" + value);
        return value;
    }

    /**
     * Writes to an inactive part of the heap
     *
     */
    private <T extends SStruct> void writeToSpareHeap(int index, T value) {
        heap[index + inactiveHeapOffset] = value;
    }

    /**
     * Reads from an inactive part of the heap
     *
     */
    public <T extends SStruct> T readFromSpareHeap(int index) {
        return (T) heap[index + inactiveHeapOffset];
    }

    public SStruct[] readPrimitiveArrayFromSpareHeap(int reference) {
        SArrayRef arrayRef = readFromSpareHeap(reference);
        SStruct[] arr = new SStruct[arrayRef.getSize()];

        for (int i = 0; i < arrayRef.getSize(); i++) {
            arr[i] = readFromSpareHeap(reference + 1 + i);
        }
        return arr;
    }

    /**
     * Swaps active and inactive heap
     */
    public void swapHeap() {
        JVMLogger.log(JVMLogger.TAG_GC, "Swapping heap, occupied space: " + nextFreeSpace);
        int temp = activeHeapOffset;
        activeHeapOffset = inactiveHeapOffset;
        inactiveHeapOffset = temp;
        nextFreeSpace = 0;
    }

    /**
     * Moves object from old heap based on reference Note that this method must
     * be called IMMEDIATELY after swapHeap and before any writing or reading
     *
     * @param oldReference
     */
    public void moveObjectFromOldHeap(int oldReference, int size) {

        SGenericRef ref = readFromSpareHeap(oldReference);

        int newIndex = nextFreeSpace++;
        ref.setReference(newIndex);
        writeToActiveHeap(newIndex, ref);

        JVMLogger.log(JVMLogger.TAG_GC, "Object " + ref + " (fldcnt=" + size + ") moved from " + oldReference + " to " + newIndex);

        for (int i = 0; i < (size); i++) {
            int oldRefIndex = oldReference + 1 + i;
            SStruct str = readFromSpareHeap(oldRefIndex);
            if (str.isReference()) {
                SGenericRef rf = (SGenericRef) str;
                if (rf.getReference() == (oldRefIndex)) {
                    ((SGenericRef) str).setReference(nextFreeSpace);
                } else {
                    // nothing to do here, because new reference has been
                    // already set (one instance of the same object could
                    // be more than once in the heap
                }
            }
            writeToActiveHeap(nextFreeSpace++, str);
            JVMLogger.log(JVMLogger.TAG_GC, "  Inner object " + str + " moved from " + (oldRefIndex) + " to " + (nextFreeSpace - 1));
        }
    }

    public void logAllHeap() {
        JVMLogger.log(JVMLogger.TAG_HEAP, "---------- HEAP CONTENT --------------");
        for (int i = 0; i < nextFreeSpace; i++) {
            SStruct str = this.heap[i + activeHeapOffset];
            if (str != null) {
                JVMLogger.log(JVMLogger.TAG_HEAP, "#" + (i) + "#" + str.toString());
            }
        }
        JVMLogger.log(JVMLogger.TAG_HEAP, "--------------------------------------");
    }

    private void initializeSpace(int index, int length) {
        for (int i = 0; i < length; ++i) {
            writeToActiveHeap(index + i, new SInt(0));
        }
    }

    /**
     * Checks if the heap contains enough space. If not, a GC will be invoked
     *
     */
    private void checkHeapSpace(int wordsRequired) throws OutOfHeapMemException {
        if (isFull(wordsRequired)) {
            garbageCollector.runGC();
        }
        if (isFull(wordsRequired)) {
            throw new OutOfHeapMemException();
        }
    }

    /**
     * Checks if the heap is full
     */
    private boolean isFull(int wordsRequired) {
        return (heapSize - nextFreeSpace) <= wordsRequired;
    }
}

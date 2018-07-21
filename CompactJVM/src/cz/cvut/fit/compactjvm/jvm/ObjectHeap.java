package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.classfile.ClassFile;
import cz.cvut.fit.compactjvm.exceptions.OutOfHeapMemException;
import cz.cvut.fit.compactjvm.structures.SGenericRef;
import cz.cvut.fit.compactjvm.structures.*;

/**
 * Heap of the virtual machine
 * @author Nick Nemame
 */
public class ObjectHeap {

    // Halda - puleni haldy je definovano pomoci activeHeapOffset a inactiveHeapOffset
    // Pri stridani se budou tyto dve hodnoty stridat
    private SStruct[] heap;

    private GarbageCollector garbageCollector;

    // Offset aktivni a neaktivni casti (0 nebo polovina velikosti)
    private int activeHeapOffset;
    private int inactiveHeapOffset;

    //Velikost aktivni haldy, tzn. velikost jedne poloviny.
    private int heapSize;

    //index prvniho volneho mista v aktivni casti haldy (0 - heapSize)
    private int nextFreeSpace;

    private MethodArea methodArea;
    private JVMThread jvmThread;

    public ObjectHeap(MethodArea methodArea, int size) {
        this.methodArea = methodArea;
        heap = new SStruct[size];
        heapSize = size / 2;
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
     * Zapise do haldy (index je index v datove casti) Pr: Obsahuje-li zaznam 2
     * slova v hlavicce (1. slovo index tridy, 2. slovo napriklad pro GC), pak
     * pokud zadam chci zapsat na index = 0, pak zapise na misto reference + 2 +
     * index = reference + 2.
     *
     * @param reference
     * @param index
     * @param value
     */
    public <T extends SStruct> void writeToHeap(int reference, int index, T value) {
        JVMLogger.log(JVMLogger.TAG_HEAP, "Write #"+reference+"#["+index+"]-->"+value);
        int headerSize = 1; 
        writeToActiveHeap(reference + headerSize + index, value);
    }

    /**
     * Cte z haldy stejne jako u zapisu, index je index v datove casti.
     *
     * @param reference
     * @param index
     * @return
     */
    public <T extends SStruct> T readFromHeap(int reference, int index) {
        int headerSize = 1; //(getClassIndex(reference) == ARRAY_INDEX) ? getArrayHeaderSize() : getObjectHeaderSize();
        T output = readFromActiveHeap(reference + headerSize + index);
        JVMLogger.log(JVMLogger.TAG_HEAP, "Read #"+reference+"#["+index+"]-->"+output);
        return output;
    }
    
    /**
     * Reads a primitive array from heap, based on reference
     * @param reference
     * @return 
     */
    public SStruct[] readPrimitiveArrayFromHeap(int reference){
        SArrayRef arrayRef = readFromActiveHeap(reference);
        SStruct[] arr = new SStruct[arrayRef.getSize()];
        
        for(int i=0; i<arrayRef.getSize(); i++){
            arr[i] = readFromHeap(reference, i);
        }
        return arr;
    }
    
    /**
     * Reads object array from active heap
     * @param reference
     * @return 
     */
    public SGenericRef[] readObjectArrayFromHeap(int reference){
        SArrayRef arrayRef = readFromActiveHeap(reference);
        SGenericRef[] arr = new SGenericRef[arrayRef.getSize()];
        
        JVMLogger.log(JVMLogger.TAG_HEAP, "Read object array #"+reference+"#-->"+arrayRef);
        
        // disable logging for that moment (we don't need to have each read in the log)
        boolean loggingEnabled = JVMLogger.loggingEnabled(JVMLogger.TAG_HEAP);
        JVMLogger.disableLogging(JVMLogger.TAG_HEAP);
        
        for(int i=0; i<arrayRef.getSize(); i++){
            SGenericRef ref = readFromHeap(reference,i);
            arr[i] = ref;
        }
        
        if(loggingEnabled) JVMLogger.enableLogging(JVMLogger.TAG_HEAP);
        
        return arr;
    }

    /**
     * Alokuje misto pro data objektu a vrati referenci na objekt
     *
     * @param classFile
     * @return
     */
    public SObjectRef allocObject(ClassFile classFile) throws OutOfHeapMemException {
        int reference = nextFreeSpace;
        int wordsRequired = /*getObjectHeaderSize() +*/ 1+ classFile.recursiveFieldCount;
        checkHeapSpace(wordsRequired);
        
        JVMLogger.log(JVMLogger.TAG_HEAP, "Allocating object #"+reference+"#[sz="+wordsRequired+"]-->"+classFile.className);
        
        SObjectRef ref = new SObjectRef(reference,classFile);
        writeToActiveHeap(reference, ref);
        nextFreeSpace += wordsRequired;
        initializeSpace(reference + 1/*getObjectHeaderSize()*/, classFile.recursiveFieldCount);
        return ref;
    }

    /**
     * Alokuje misto pro pole
     *
     * @param itemSize velikost jedne polozky
     * @param arraySize pocet prvku pole
     * @return
     * @throws OutOfHeapMemException
     */
    public <T extends SStruct> SArrayRef allocPrimitiveArray(T[] arr, int arraySize) throws OutOfHeapMemException {
        
        int reference = nextFreeSpace;
        int wordsRequired = /*getArrayHeaderSize()*/ 1 + arraySize;
        checkHeapSpace(wordsRequired);
        
        JVMLogger.log(JVMLogger.TAG_HEAP, "Allocating primitive array ["+reference+"][sz="+arraySize+"]-->");
        JVMLogger.increaseGlobalPadding(4);
        SArrayRef ref = new SArrayRef(reference,null, arraySize);
        writeToActiveHeap(reference, ref);
        
        // write default values into heap
        for(int i=0; i<arraySize; i++){
            writeToHeap(reference, i,arr[i]);
        }
        JVMLogger.decreaseGlobalPadding(4);
        nextFreeSpace+=wordsRequired;

        return ref;
    }

    /**
     * Allocates an object array
     * @return
     * @throws OutOfHeapMemException 
     */
    public SArrayRef allocObjectArray(ClassFile classFile, int arraySize) throws OutOfHeapMemException{
        
        int reference = nextFreeSpace;
        int wordsRequired = 1 + arraySize;
        checkHeapSpace(wordsRequired);
        
        JVMLogger.log(JVMLogger.TAG_HEAP, "Allocating object array #"+reference+"#[sz="+arraySize+"]-->"+classFile.className);
        
        SArrayRef arrayRef = new SArrayRef(reference,classFile, arraySize);
        writeToActiveHeap(reference, arrayRef);
        
        JVMLogger.increaseGlobalPadding(4);
        // write references to the heap
        for(int i=0; i<arraySize; i++){
            writeToHeap(reference, i,new SObjectRef(reference+1+i,classFile));
        }
        JVMLogger.decreaseGlobalPadding(4);
        
        nextFreeSpace+=wordsRequired;
        
        return arrayRef;

    }
   
    
    /**
     * Zapise do aktivni casti haldy (index je realny index od zacatku teto
     * casti haldy)
     *
     * @param index
     * @param value
     */
    private <T extends SStruct> void writeToActiveHeap(int index, T value) {
        heap[index + activeHeapOffset] = value;
    }

    /**
     * Cte z aktivni casti haldy (index je realny index od zacatku teto casti
     * haldy)
     *
     * @param index
     * @return
     */
    private <T extends SStruct> T readFromActiveHeap(int index) {
        return (T)heap[index + activeHeapOffset];
    }

    /**
     * Zapise do aktualne nekativni casti haldy (index je realny index od
     * zacatku teto casti haldy)
     *
     * @param index
     * @param value
     */
    private <T extends SStruct> void writeToSpareHeap(int index, T value) {
        heap[index + inactiveHeapOffset] = value;
    }

    /**
     * Cte z aktualne neaktivni casti haldy (index je realny index od zacatku
     * teto casti haldy)
     *
     * @param index
     * @return
     */
    public <T extends SStruct> T readFromSpareHeap(int index) {
        return (T)heap[index + inactiveHeapOffset];
    }
    
    /**
     * Swaps active and inactive heap
     */
    public void swapHeap(){
        JVMLogger.log(JVMLogger.TAG_GC, "Swapping heap");
        int temp = activeHeapOffset;
        activeHeapOffset = inactiveHeapOffset;
        inactiveHeapOffset = temp;
        nextFreeSpace = 0;
    }
    
    /**
     * Moves object from old heap based on reference
     * Note that this method must be called IMMEDIATELY after swapHeap and
     * before any writing or reading
     * @param oldReference 
     */
    public void moveObjectFromOldHeap(int oldReference){    
        SGenericRef ref = readFromSpareHeap(oldReference);
        int newIndex = nextFreeSpace++;
        ref.setReference(newIndex);
        writeToActiveHeap(newIndex, ref);
        
        JVMLogger.log(JVMLogger.TAG_GC, "Object "+ref+" moved from "+oldReference+" to "+newIndex);
    }

    private void initializeSpace(int index, int length) {
        for (int i = 0; i < length; ++i) {
            writeToActiveHeap(index + i, new SInt(0));
        }
    }

    /**
     * Zkontroluje, zda se do haldy vejde urcity pocet slov, pokud ne, spusti
     * GC.
     *
     * @param wordsRequired
     * @throws OutOfHeapMemException
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
     * Zjisti, zda se vejde potrebny pocet slov do haldy
     *
     * @todo neni lepsi spoustet GC napriklad uz pri 70% zaplneni treba? Nebo to
     * bychom museli spoustet ve vedlejsim vlakne?
     * @param wordsRequired
     * @return
     */
    private boolean isFull(int wordsRequired) {
        return (heapSize - nextFreeSpace) < wordsRequired;
    }
}

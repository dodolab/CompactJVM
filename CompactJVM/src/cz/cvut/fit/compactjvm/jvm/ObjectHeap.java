/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.core.ClassFile;
import cz.cvut.fit.compactjvm.exceptions.OutOfHeapMemException;

/**
 * @todp Tady bude prostor pro alokovane objekty, pole atd.
 * @author Nick Nemame
 */
public class ObjectHeap {

    // number of bytes in object header, used for space calculation
    public static final int OBJECT_HEADER_BYTES = 12;
    
    public static final int FORWARDING_POINTER = -1;

    private final int ARRAY_INDEX = -1000;
    
    private final int ARRAY = 1;
    private final int OBJECT = 2;

    private int[] heap;
    
    private GarbageCollector garbageCollector;
    
    private int activeHeapOffset;
    private int inactiveHeapOffset;
    private int heapSize;
    private int nextFreeSpace;
    
    private MethodArea methodArea;
    
    public ObjectHeap(MethodArea methodArea, GarbageCollector garbageCollector, int size) {
        this.methodArea = methodArea;
        heap = new int[size];
        heapSize = size / 2;
        activeHeapOffset = 0;
        inactiveHeapOffset = heapSize;
        nextFreeSpace = 0;
        this.garbageCollector = garbageCollector;
    }

    /**
     * Nastavi forwarding pointer na misto v nove halde. Prvni slovo znaci
     * informaci, ze jde o forwarding pointer, druhe slovo obsahuje tento pointer.
     * @param oldReference
     * @param newReference 
     */
    public void setForwardingPointer(int oldReference, int newReference) {
        writeToActiveHeap(oldReference, FORWARDING_POINTER);
        writeToActiveHeap(oldReference + 1, newReference);
    }
    
    /**
     * Zjisti, zda na miste, kam ukazuje puvodni reference, je jiz forwarding pointer
     * @param oldReference
     * @return 
     */
    public boolean isForwardingPointer(int oldReference) {
        return readFromActiveHeap(oldReference) == FORWARDING_POINTER;
    }
    
    public void writeToHeap(int reference, int index, int value) {
        int headerSize = (getClassIndex(reference) == ARRAY_INDEX) ? getArrayHeaderSize() : getObjectHeaderSize();
        writeToActiveHeap(reference + headerSize + index, value);
    }
    
    public int readFromHeap(int reference, int index) {
        int headerSize = (getClassIndex(reference) == ARRAY_INDEX) ? getArrayHeaderSize() : getObjectHeaderSize();
        return readFromActiveHeap(reference + headerSize + index);
    }
    
    private void writeToActiveHeap(int index, int value) {
        heap[index + activeHeapOffset] = value;
    }
    
    private int readFromActiveHeap(int index) {
        return heap[index + activeHeapOffset];
    }
    
    private void writeToSpareHeap(int index, int value) {
        heap[index + activeHeapOffset] = value;
    }
    /*
    public int readFromSpareHeap(int index) {
        return heap[index + activeHeapOffset];
    }*/
    
    
    /**
     * Alokuje data a vrati referenci na objekt
     * @param classFile
     * @return 
     */
    public int allocObject(ClassFile classFile) throws OutOfHeapMemException {
        int reference = nextFreeSpace;
        int wordsRequired = getObjectHeaderSize() + classFile.fieldDataBytes;
        checkHeapSpace(wordsRequired);
        writeToActiveHeap(reference, classFile.index);
        garbageCollector.initializeDataHeader();
        nextFreeSpace += wordsRequired;
        initializeSpace(reference + getObjectHeaderSize(), classFile.fieldDataBytes);
        return reference;
    }
    
    public int allocArray(int itemSize, int arraySize) throws OutOfHeapMemException {
        int reference = nextFreeSpace;
        int wordsRequired = getArrayHeaderSize() + (itemSize * arraySize);
        checkHeapSpace(wordsRequired);
        writeToActiveHeap(reference, ARRAY_INDEX);
        writeToActiveHeap(reference + 1, arraySize);
        garbageCollector.initializeDataHeader();
        nextFreeSpace += wordsRequired;
        initializeSpace(reference + getArrayHeaderSize(), itemSize * arraySize);
        return reference;
    }
    
    public void initializeSpace(int index, int length) {
        for(int i = 0; i < length; ++i) {
            writeToActiveHeap(index + i, 0);
        }
    }
    
    public void checkHeapSpace(int wordsRequired) throws OutOfHeapMemException {
        if(isFull(wordsRequired)) {
            garbageCollector.collect();
        }
        if(isFull(wordsRequired)) {
            throw new OutOfHeapMemException();
        }
    }
    
    public int getObjectHeaderSize() {
        return 1 + garbageCollector.getRequiredHeaderBytes();
    }
    
    public int getArrayHeaderSize() {
        return 2 + garbageCollector.getRequiredHeaderBytes();
    }
    
    public int getClassIndex(int reference) {
        return readFromActiveHeap(reference);
    }
    
    /**
     * Zjisti, zda se vejde potrebny pocet slov do haldy
     * @todo chtelo by spoustet GC napriklad uz pri 70% zaplneni treba?
     * @param wordsRequired
     * @return 
     */
    private boolean isFull(int wordsRequired) {
        return (heapSize - nextFreeSpace) < wordsRequired;
    }
    /*
    public byte[] getObjectFieldValue(byte[] bytes, int objectStart, int index) {
        byte[] value = new byte[4];
        int start = index * 4 + objectStart + OBJECT_HEADER_BYTES;

        for (int i = 0; i < value.length; i++) {
            value[i] = bytes[start + i];
        }

        return value;
    }

    public static byte[] getValue(byte[] bytes, int from) {
        byte[] value = new byte[4];

        for (int i = 0; i < value.length; i++) {
            value[i] = bytes[from + i];
        }

        return value;
    }
*/
}

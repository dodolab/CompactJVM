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
    
    public static final int FORWARDING_POINTER = -1;

    private final int ARRAY_INDEX = -1000;
    
    // Halda - puleni haldy je definovano pomoci activeHeapOffset a inactiveHeapOffset
    // Pri stridani se budou tyto dve hodnoty stridat
    private int[] heap;
    
    private GarbageCollector garbageCollector;
    
    // Offset aktivni a neaktivni casti (0 nebo polovina velikosti)
    private int activeHeapOffset;
    private int inactiveHeapOffset;
    
    //Velikost aktivni haldy, tzn. velikost jedne poloviny.
    private int heapSize;
    
    //index prvniho volneho mista v aktivni casti haldy (0 - heapSize)
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
    
    /**
     * Zapise do haldy (index je index v datove casti)
     * Pr: Obsahuje-li zaznam 2 slova v hlavicce (1. slovo index tridy, 2. slovo napriklad pro GC),
     * pak pokud zadam chci zapsat na index = 0, pak zapise na misto reference + 2 + index = reference + 2.
     * @param reference
     * @param index
     * @param value 
     */
    public void writeToHeap(int reference, int index, int value) {
        int headerSize = (getClassIndex(reference) == ARRAY_INDEX) ? getArrayHeaderSize() : getObjectHeaderSize();
        writeToActiveHeap(reference + headerSize + index, value);
    }
    
    /**
     * Cte z haldy
     * stejne jako u zapisu, index je index v datove casti.
     * @param reference
     * @param index
     * @return 
     */
    public int readFromHeap(int reference, int index) {
        int headerSize = (getClassIndex(reference) == ARRAY_INDEX) ? getArrayHeaderSize() : getObjectHeaderSize();
        return readFromActiveHeap(reference + headerSize + index);
    }
    
    /**
     * Alokuje misto pro data objektu a vrati referenci na objekt
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
    
    /**
     * Alokuje misto pro pole
     * @param itemSize velikost jedne polozky
     * @param arraySize pocet prvku pole
     * @return
     * @throws OutOfHeapMemException 
     */
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
    
    /**
     * Zapise do aktivni casti haldy (index je realny index od zacatku teto casti haldy)
     * @param index
     * @param value 
     */
    private void writeToActiveHeap(int index, int value) {
        heap[index + activeHeapOffset] = value;
    }
    
    /**
     * Cte z aktivni casti haldy (index je realny index od zacatku teto casti haldy)
     * @param index
     * @return 
     */
    private int readFromActiveHeap(int index) {
        return heap[index + activeHeapOffset];
    }
    
    /**
     * Zapise do aktualne nekativni casti haldy (index je realny index od zacatku teto casti haldy)
     * @param index
     * @param value 
     */
    private void writeToSpareHeap(int index, int value) {
        heap[index + activeHeapOffset] = value;
    }
    
    /**
     * Cte z aktualne neaktivni casti haldy (index je realny index od zacatku teto casti haldy)
     * @param index
     * @return 
     */
    public int readFromSpareHeap(int index) {
        return heap[index + activeHeapOffset];
    }
    
    private void initializeSpace(int index, int length) {
        for(int i = 0; i < length; ++i) {
            writeToActiveHeap(index + i, 0);
        }
    }
    
    /**
     * Zkontroluje, zda se do haldy vejde urcity pocet slov, pokud ne, spusti GC.
     * @param wordsRequired
     * @throws OutOfHeapMemException 
     */
    private void checkHeapSpace(int wordsRequired) throws OutOfHeapMemException {
        if(isFull(wordsRequired)) {
            garbageCollector.collect();
        }
        if(isFull(wordsRequired)) {
            throw new OutOfHeapMemException();
        }
    }
    
    /**
     * Vrati velikost hlavicky objektu (zavisi mj. na implementaci GC)
     * @return 
     */
    private int getObjectHeaderSize() {
        return 1 + garbageCollector.getRequiredHeaderBytes();
    }
    
    /**
     * Vrati velikost hlavicky objektu (zavisi mj. na implementaci GC)
     * @return 
     */
    private int getArrayHeaderSize() {
        return 2 + garbageCollector.getRequiredHeaderBytes();
    }
    
    /**
     * Vrati class index (tzn. prvni slovo, kam ukazuje reference, pokud jde o pole)
     * @param reference
     * @return 
     */
    public int getClassIndex(int reference) {
        return readFromActiveHeap(reference);
    }
    
    /**
     * Zjisti, zda se vejde potrebny pocet slov do haldy
     * @todo neni lepsi spoustet GC napriklad uz pri 70% zaplneni treba? Nebo to bychom museli spoustet ve vedlejsim vlakne?
     * @param wordsRequired
     * @return 
     */
    private boolean isFull(int wordsRequired) {
        return (heapSize - nextFreeSpace) < wordsRequired;
    }
}

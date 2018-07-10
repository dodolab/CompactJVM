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

    private final byte[] heapA;
    private final byte[] heapB;
    private int activeHeap;
    private int heapPointer;
    private MethodArea methodArea;
    private JVMThread jvmThread;
    private final int ARRAY_INDEX = -1000;

    public ObjectHeap(MethodArea methodArea, int size) {
        this.methodArea = methodArea;
        heapA = new byte[size / 2];
        heapB = new byte[size / 2];
        activeHeap = 0;
        heapPointer = 0;
        getActiveHeap()[heapPointer++] = 0; // null pointer
    }

    public void setJVMThread(JVMThread jvmThread) {
        this.jvmThread = jvmThread;
    }

    /**
     * Allocates array on heap
     * @param size array size
     * @return
     * @throws ClassNotFoundException
     * @throws OutOfHeapMemException 
     */
    public int allocArray(int size) throws ClassNotFoundException, OutOfHeapMemException {
        // this is crap.. we should find another way (maybe implement own Array class)
        return alloc(ARRAY_INDEX, size, size * 4);
    }

    public int alloc(ClassFile cls, byte[] bytes) throws OutOfHeapMemException {
        int fieldsL = cls.fieldCount * 4;
        int start = alloc(cls.index, 0, fieldsL + bytes.length);
        for (int i = 0; i < bytes.length; i++) {
            getActiveHeap()[start + OBJECT_HEADER_BYTES + i + fieldsL] = bytes[i];
        }
        return start;
    }

    private int alloc(int classIndex, int size, int bytes) throws OutOfHeapMemException {

        if (isFull(bytes)) {
            // todo: run garbage collector
        }

        if (isFull(bytes)) {
            throw new OutOfHeapMemException();
        }

        int start = heapPointer;
        insertIntToHeap(classIndex);
        insertIntToHeap(size);

        // inicialize to 0
        for (int i = 0; i < bytes; i++) {
            getActiveHeap()[heapPointer++] = 0;
        }

        return start;
    }

    private boolean isFull(int bytes) {
        return (getActiveHeap().length - heapPointer) < (OBJECT_HEADER_BYTES + bytes);
    }

    public ClassFile loadObject(int address) {
        ClassFile cls = methodArea.getClassStorage().getClass(byteArrayToInt(getActiveHeap(), address));
        return cls;
    }

    public int[] loadIntArray(int address) {

        // will be -1000... must be implemented another way
        int classIndex = byteArrayToInt(getActiveHeap(), address);
        int size = byteArrayToInt(getActiveHeap(), address + 4);

        int start = address + 8;

        // todo: optimize and transform byte subarray into int array maybe?
        int[] arrCopy = new int[size];

        for (int i = start; i < size; i += 4) {
            arrCopy[(i - start) / 4] = byteArrayToInt(getActiveHeap(), i);
        }

        return arrCopy;
    }

    private void insertIntToHeap(int value) {
        byte[] intBytes = intToByteArray(value);
        getActiveHeap()[heapPointer++] = intBytes[0];
        getActiveHeap()[heapPointer++] = intBytes[1];
        getActiveHeap()[heapPointer++] = intBytes[2];
        getActiveHeap()[heapPointer++] = intBytes[3];
    }

    public byte[] getActiveHeap() {
        if (activeHeap == 0) {
            return heapA;
        }
        return heapB;
    }

    public int getHeapPointer() {
        return heapPointer;
    }

    public final byte[] intToByteArray(int value) {
        return new byte[]{(byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value};
    }

    public int byteArrayToInt(byte[] bytes, int from) {
        return bytes[from] << 24 | (bytes[from + 1] & 0xFF) << 16 | (bytes[from + 2] & 0xFF) << 8 | (bytes[from + 3] & 0xFF);
    }

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
}

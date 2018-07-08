/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm;

import java.nio.ByteBuffer;

/**
 *
 * @author Nick Nemame
 */
public class LocalVariableArray {
    
    int[] localVariables;
    
    /**
     * @param size Velikost pole lokalnich promennych
     * @todo pridat moznost vlozit "this" referenci pro instancni volani metody
     */
    public LocalVariableArray(int size) {
        localVariables = new int[size];
        
    }
    
    public byte getByte(int index) {
        return (byte) localVariables[index];
    }
    
    public void setByte(int index, byte value) {
        localVariables[index] = (int) value;
    }
    
    public int getInt(int index) {
        return localVariables[index];
    }
    
    public void setInt(int index, int value) {
        localVariables[index] = value;
    }
    
    public float getFloat(int index) {
        return (float) localVariables[index];
    }
    
    public void setFloat(int index, float value) {
        localVariables[index] = (int) value;
    }
    
    public double getDouble(int index) {
        byte[] bytes = new byte[Double.BYTES];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.putInt(0, localVariables[index]);
        byteBuffer.putInt(Integer.BYTES, localVariables[index]);
        return byteBuffer.getDouble();
    }
    
    public void setDouble(int index, double value) {
        byte[] bytes = new byte[Double.BYTES];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.putDouble(value);
        localVariables[index] = byteBuffer.getInt(0);
        localVariables[index] = byteBuffer.getInt(Integer.BYTES);
    }
    
    public long getLong(int index) {
        byte[] bytes = new byte[Long.BYTES];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.putInt(0, localVariables[index]);
        byteBuffer.putInt(Integer.BYTES, localVariables[index]);
        return byteBuffer.getLong();
    }
    
    public void setLong(int index, long value) {
        byte[] bytes = new byte[Long.BYTES];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.putLong(value);
        localVariables[index] = byteBuffer.getInt(0);
        localVariables[index] = byteBuffer.getInt(Integer.BYTES);
    }
    
    
}

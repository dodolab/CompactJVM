/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm;

import java.nio.ByteBuffer;

/**
 * Velikost slova je v teto implementaci definovana jako int.
 * int, short, byte a float odpovidaji velikosti 1 slova, double a long opodvidaji
 * velikosti 2 slov. Horni slovo doublu a longu by nemelo byt dostupne naprimo.
 * @todo Zvazit kontrolu na to, zda jiz na danem indexu byla nastavena hodnota nebo ne, ackoliv Java na me vlastne asi nezarve, kdyz saham nekam, kde jsem neinicalizoval hodnotu.
 * @todo Pro volani instancni metody je treba do localVariables umistit na pozici 0 referenci na "this"
 * @author Nick Nemame
 */
public class LocalVariableArray {
    
    /**
     * Pole slov pro jednotlive lokalni promenne
     */
    int[] localVariables;
    
    /**
     * @param size Velikost pole lokalnich promennych, ziskano z ClassFile
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
    
    public short getShort(int index) {
        return (short) localVariables[index];
    }
    
    public void setShort(int index, short value) {
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

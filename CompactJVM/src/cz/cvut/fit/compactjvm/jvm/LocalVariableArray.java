/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.structures.*;
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
    SStruct[] localVariables;
    
    StackFrameReferences references;
    
    /**
     * @param size Velikost pole lokalnich promennych, ziskano z ClassFile
     * @todo pridat moznost vlozit "this" referenci pro instancni volani metody
     */
    public LocalVariableArray(int size, StackFrameReferences references) {
        localVariables = new SStruct[size];
        this.references = references;
        
    }
    
    public <T extends SStruct> T getVar(int index) throws LoadingException{
        SStruct ent = localVariables[index];
        T entity = (T)ent;
        
        if(entity == null) throw new LoadingException("Wrong type! Found: "+ent.toString());
        return entity;
    }
    
    public <T extends SStruct> void setVar(int index, T value){
        localVariables[index] = value;
    }
    /*
    public boolean getBoolean(int index) {
        return localVariables[index] == 1;
    }
    
    public void setBoolean(int index, boolean value) {
        localVariables[index] = value ? 1 : 0;
    }
    
    public byte getByte(int index) {
        return (byte) localVariables[index];
    }
    
    public void setByte(int index, byte value) {
        localVariables[index] = (int) value;
    }
    
    public char getChar(int index) {
        return (char) localVariables[index];
    }
    
    public void setChar(int index, char value) {
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
    
    public int getReference(int index) {
        return getInt(index);
    }
    
    public void setReference(int index, int objectReference) {
        int oldObjectReference = getInt(index);
        if(oldObjectReference != 0) references.removeReference(objectReference);
        setInt(index, objectReference);
        references.addReference(objectReference);
    }
    */
}

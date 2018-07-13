/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.structures;

/**
 * Byte structure
 * 
 * @author Adam Vesecky
 */
public class SByte extends SStruct {
    private final byte value;


    public SByte(byte value) {
	this.value = value;
    }

    public byte getValue() {
        return value;
    }
    
    @Override
    public SByte makeCopy() {
	return new SByte(this.value);
     }

    @Override
    public String toString() {
        return "SByte["+value+"]";
    }
}

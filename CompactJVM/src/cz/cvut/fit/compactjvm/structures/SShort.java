/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.structures;

/**
 * Short structure
 * 
 * @author Adam Vesecky
 */
public class SShort extends SStruct{
    private final short value;


    public SShort(short value) {
	this.value = value;
    }

    public short getValue() {
        return value;
    }

    @Override
    public SShort makeCopy() {
	return new SShort(this.value);
     }

    @Override
    public String toString() {
        return "SShort["+value+"]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.structures;

/**
 * Long structure
 * 
 * @author Adam Vesecky
 */
public class SLong extends SStruct {
    private final long value;


    public SLong(long value) {
	this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public SLong makeCopy() {
	return new SLong(this.value);
     }

    @Override
    public String toString() {
        return "SLong["+value+"]";
    }
}

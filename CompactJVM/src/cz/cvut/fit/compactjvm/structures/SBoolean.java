/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.structures;

/**
 * Boolean structure
 * 
 * @author Adam Vesecky
 */
public class SBoolean extends SStruct {
    private final boolean value;


    public SBoolean(boolean value) {
	this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public SStruct makeCopy() {
	return new SBoolean(this.value);
     }

    @Override
    public String toString() {
        return "SBoolean["+value+"]";
    }
}

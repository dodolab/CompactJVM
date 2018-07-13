/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.structures;

/**
 * Integer structure
 * 
 * @author Adam Vesecky
 */
public class SInt extends SIntable{
    private final int value;


    public SInt(int value) {
	this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public SInt makeCopy() {
	return new SInt(this.value);
     }

    @Override
    public String toString() {
        return "SInt["+value+"]";
    }

    @Override
    public SInt toInt() {
        return this;
    }
}

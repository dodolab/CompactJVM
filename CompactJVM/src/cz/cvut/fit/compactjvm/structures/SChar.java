/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.structures;

/**
 * Character structure
 * 
 * @author Adam Vesecky
 */
public class SChar extends SStruct{
    private final char value;


    public SChar(char value) {
	this.value = value;
    }

    public char getValue() {
        return value;
    }

    @Override
    public SChar makeCopy() {
	return new SChar(this.value);
     }

    @Override
    public String toString() {
        return "SChar["+value+"]";
    }
}

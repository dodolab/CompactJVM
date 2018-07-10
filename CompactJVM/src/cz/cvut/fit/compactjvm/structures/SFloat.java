/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.structures;

/**
 * Float structure
 * 
 * @author Adam Vesecky
 */
public class SFloat extends SStruct {
    private final float value;


    public SFloat(float value) {
	this.value = value;
    }

    public float getValue() {
        return value;
    }

    @Override
    public SFloat makeCopy() {
	return new SFloat(this.value);
     }

    @Override
    public String toString() {
        return "SFloat["+value+"]";
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.exceptions;

/**
 * Occurs when heap is full and an allocation request is made
 * @author Adam Vesecky
 */
public class ArrayOutOfBoundsException extends Exception{
    
    public ArrayOutOfBoundsException(String exc){
        super(exc);
    }
}

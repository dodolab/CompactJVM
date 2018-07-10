/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm;

/**
 * Dummy storage for arrays
 * @author Adam Vesecky
 */
public class ArrayStorage {
    private int[][] arrayBuffer;
    private int indexCounter = 0;
    
    public ArrayStorage(){
        arrayBuffer = new int[20][];
    }
    
    public int storeArray(int[] array){
        arrayBuffer[indexCounter++] = array;
        return indexCounter-1;
    }
    
    public int[] getArray(int index){
        return arrayBuffer[index];
    }
}

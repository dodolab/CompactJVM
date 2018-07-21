/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compactjvmlab;

/**
 *
 * @author Nick Nemame
 */
public class TestClass extends TestSuperclass {
    
    private int myItem;
    private double myDoubleItem;
    
    protected static int static2;
    
    public void setStatic1() {
        static1 = 13;
    }
    
    public void setStatic2() {
        static2 = 14;
    }
    
    public void setMyItem(int value) {
        protectedSuperVar = value;
    }
    
    public void addToMyItem(int add) {
        myItem = myItem + add;
    }
    
    public int getMyItem() {
        return myItem;
    }
    
    public static int testMethod(int j, int k) {
        int i = 3;
        i = i + j + k;
        return i;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compactjvmlab;

/**
 * Simple class that will be used for testing CompactJVM project
 * Just write anything, compile into class file and load it, using CompactJVM
 * @author Adam Vesecky
 */
public class CompactJVMLab {

    private static int staticPrivateProm1;
    private static int staticPrivateProm2;
    private static int staticPrivateProm3;
    
    
    private void setStaticProm() {
        staticPrivateProm1 = 11;
        staticPrivateProm2 = 12;
        staticPrivateProm3 = 13;
    }

    public static void main() {
        /*int local = 7;
        local = local + 1;*/
        
        /* objects */
        
        TestClass testObject1 = new TestClass();
        //TestClass testObject2 = new TestClass();
        
        testObject1.setMyItem(17);
        testObject1.addToMyItem(10);
        //testObject2.setMyItem(3);
        testObject1.getMyItem();
        
        /* arrays, cycles, static method calls
        int test1 = TestClass.testMethod(4, 5);
        int test2 = TestClass.testMethod(10,8);
        int test3 = TestClass.testMethod(1,1);
        
        test1 = test2+test3*test1;
        
        int[] array = new int[20];
        array[8] = 21;
        
        int[] secondArray = new int[10];
        secondArray[9] = 6;
        
        array[8] = secondArray[9];
        
        int i = 5;
        
        while(i > 0) i--;
        
        int abcd = 12;
        
        for(int j=0; j<10; j++){
            abcd++;
        }
        */
    }
    /*public static int staticPublicProm;
    
    private int instantPrivateProm;
    public int instantPublicProm;
    
    public int localVariableMethod() {
        int a = 13;
        return a;
    }
    
    public static void staticVoidMethod(){
        
    }
    
    public static int staticIntMethod(){
        return 12;
    }
    
    public void instantVoidMethod(){
        
    }
    
    public int instantIntMethod(){
        return 25;
    }
    
    public static void main(String[] args) {
        staticVoidMethod();
        staticIntMethod();
        
        CompactJVMLab mn = new CompactJVMLab();
        mn.instantVoidMethod();
        mn.instantIntMethod();
    }
    */
}

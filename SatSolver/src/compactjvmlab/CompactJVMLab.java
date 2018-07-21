/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compactjvmlab;

import compactjvmlab.satsolver.Clauses;
import compactjvmlab.satsolver.FileLoader;
import compactjvmlab.satsolver.SATSolver;
import compactjvmlab.satsolver.VariableEvaluation;
import cz.cvut.fit.compactjvm.proxies.JVMFunctions;
import java.io.IOException;

/**
 * Simple class that will be used for testing CompactJVM project
 * Just write anything, compile into class file and load it, using CompactJVM
 * @author Adam Vesecky
 */
public class CompactJVMLab {


    public static void main(String[] args) throws IOException {

        //String s = "a a d";
        //boolean b = s.equals("a a d");
        //JVMFunctions.println(b ? "ano" : "ne");
        //Test3 test = new Test3();
        //test.getInt();
        //boolean equals = split[0].equals("a");
        //String s2 = s + "vvd";
        /*Test2 test2 = new Test2();
        Test3 test3 = new Test3();
        test2.test3 = test3;*/
        /*if(args.length != 1) {
        System.out.println("Neplatny pocet argumentu");
        System.exit(1);
        }*/
        //Clauses clauses;
        //int variablesCount;
        //try {
            //FileLoader fileLoader = new FileLoader("c:\\Data\\Skola\\FIT\\3. semestr\\MI-RUN\\MiRunProject\\CompactJVMLab\\data\\cnf.txt"/*args[0]*/);
            FileLoader fileLoader = new FileLoader(args[0]);
            //clauses = fileLoader.getClauses();
            //variablesCount = fileLoader.getVariablesCount();
            //SATSolver solver = new SATSolver();
            //VariableEvaluation evaluation = solver.solve(clauses, variablesCount);
            //if(evaluation == null) System.out.println("Není splnitelná");
            //else System.out.println("Je splnitelná: " + evaluation.toString());
        //} catch (IOException e) {
        //    System.out.println(e.getMessage());
        //    System.exit(1);
        //}
        /** TEST 5 - volani metody v rodicovske tride **
        TestClass t = new TestClass();
        int i = t.getValueFromSuperclass();
         */
        /** TEST 1 - zachovani funkcnosti poli
        int[] myarray = new int[2];
        myarray[0] = 13;
        myarray[1] = 14;
        int i = myarray[0];
         */
        /** TEST 2 - v setMyItem nastavuju field, ktery je definova v predkovi tridy **
        TestClass testObject1 = new TestClass();
        //TestClass testObject2 = new TestClass();
        testObject1.setMyItem(17);
         */
        /*
        testObject1.addToMyItem(10);
        //testObject2.setMyItem(3);
        testObject1.getMyItem();
         */
        /** TEST 3 - pristup ke statickemu fieldu svemu **
        TestClass testObject1 = new TestClass();
        testObject1.setStatic2();
         */
        /** TEST 4 - pristup ke statickemu fieldu sveho predka **
        TestClass testObject1 = new TestClass();
        testObject1.setStatic1();
         */
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
    public static int staticPublicProm;
    
    private int instantPrivateProm;
    public int instantPublicProm;
    
    public int localVariableMethod() {
        int a = 13;
        return a;
    }
    /*
    public static void staticVoidMethod(){

        fileR.close();
    }
*/
}

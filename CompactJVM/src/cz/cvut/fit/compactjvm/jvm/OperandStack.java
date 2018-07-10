/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.structures.*;
import java.nio.ByteBuffer;
import java.util.Stack;

/**
 * Predstavuje zasobnik operandu volanych instrukci. Stejne jako v poli
 * lokalnich promennych, pracuje zasobnik nad "slovy", pricemz tato implementace
 * pocita se slovem delky velikosti typu INT.
 * Pokud tedy budeme chtit nacitat operand typu long a double, pak musime z interniho
 * zasobniku nacist dve slova.
 * @author Nick Nemame
 */
public class OperandStack {
    
    Stack<SStruct> operandStack;
    
    StackFrameReferences references;

    public OperandStack(StackFrameReferences references) {
        this.operandStack = new Stack<>();
        this.references = references;
    }
        
    public boolean isEmpty(){
        return operandStack.isEmpty();
    }
    
    public <T extends SStruct> T pop() throws LoadingException{
        SStruct ent = operandStack.pop();
        T entity = (T)ent;
        
        if(entity == null) throw new LoadingException("Wrong type! Found: "+ent.toString());
        return entity;
    }
    
    public <T extends SStruct> void push(T value){
        operandStack.push(value);
    }
    
    /*
    public SDouble popDouble() {
        byte[] bytes = new byte[Double.BYTES];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.putInt(Integer.BYTES, operandStack.pop());
        byteBuffer.putInt(0, operandStack.pop());
        return byteBuffer.getDouble();
    }
    
    public void pushDouble(double value) {
        byte[] bytes = new byte[Double.BYTES];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.putDouble(value);
        operandStack.push(byteBuffer.getInt(0));
        operandStack.push(byteBuffer.getInt(Integer.BYTES));
    }
    
    public long popLong() {
        byte[] bytes = new byte[Long.BYTES];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.putInt(Integer.BYTES, operandStack.pop());
        byteBuffer.putInt(0, operandStack.pop());
        return byteBuffer.getLong();
    }
    
    public void pushLong(long value) {
        byte[] bytes = new byte[Long.BYTES];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.putLong(value);
        operandStack.push(byteBuffer.getInt(0));
        operandStack.push(byteBuffer.getInt(Integer.BYTES));
    }
    
    public void pushReference(int objectReference) {
        pushInt(objectReference);
        references.addReference(objectReference);
    }
    
    public int popReference() {
        int objectReference = popInt();
        references.removeReference(objectReference);
        return objectReference;
    }
    */
}

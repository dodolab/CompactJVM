/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm;

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
    
    Stack<Integer> operandStack;

    public OperandStack() {
        this.operandStack = new Stack<>();
    }
    
    
    public byte popByte() {
        return operandStack.pop().byteValue();
    }
    
    public void pushByte(byte value) {
        operandStack.push(Integer.valueOf(value));
    }
    
    public int popInt() {
        return operandStack.pop();
    }
    
    public void pushInt(int value) {
        operandStack.push(value);
    }
    
    public short popShort() {
        return operandStack.pop().shortValue();
    }
    
    public void pushShort(short value) {
        operandStack.push(Integer.valueOf(value));
    }
    
    public float popFloat() {
        return operandStack.pop().floatValue();
    }
    
    public void pushFloat(float value) {
        operandStack.push(Float.floatToIntBits(value));
    }
    
    public double popDouble() {
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
    
}

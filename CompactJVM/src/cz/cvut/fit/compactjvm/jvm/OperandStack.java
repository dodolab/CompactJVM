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
 * pocita se slovem delky velikosti typu INT. Pokud tedy budeme chtit nacitat
 * operand typu long a double, pak musime z interniho zasobniku nacist dve
 * slova.
 *
 * @author Nick Nemame
 */
public class OperandStack {

    Stack<SStruct> operandStack;

    public OperandStack() {
        this.operandStack = new Stack<>();
    }

    public boolean isEmpty() {
        return operandStack.isEmpty();
    }

    public <T extends SStruct> T get(int level) throws LoadingException{
        // don't touch it!
        SStruct ent = operandStack.get(operandStack.size() - level - 1);
        T entity = (T) ent;

        if (entity == null) {
            throw new LoadingException("Wrong type! Found: " + ent.toString());
        }
        return entity;
    }

    public <T extends SStruct> T peek() throws LoadingException {
        SStruct ent = operandStack.peek();
        T entity = (T) ent;

        if (entity == null) {
            throw new LoadingException("Wrong type! Found: " + ent.toString());
        }
        return entity;
    }

    public <T extends SStruct> T pop() throws LoadingException {
        SStruct ent = operandStack.pop();
        T entity = (T) ent;

        if (entity == null) {
            throw new LoadingException("Wrong type! Found: " + ent.toString());
        }
        return entity;
    }

    public <T extends SStruct> void push(T value) {
        operandStack.push(value);
    }

}

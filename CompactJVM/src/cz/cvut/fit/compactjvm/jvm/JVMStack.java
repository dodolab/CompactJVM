/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm;

import java.util.Stack;

/**
 *
 * @author Nick Nemame
 */
public class JVMStack {
    private Stack<StackFrame> stack;
    
    public void push(StackFrame frame) {
        stack.add(frame);
    }
    
    public StackFrame removeCurrentFrame() {
        return stack.remove(stack.size() - 1);
    }
    
    public StackFrame getCurrentFrame() {
        return stack.get(stack.size() - 1);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm;

import java.util.Stack;

/**
 * Zakladni stack JVM, ktery obsahuje stack framy
 * @author Nick Nemame
 */
public class JVMStack {
    
    private final Stack<StackFrame> stack;

    public JVMStack() {
        stack = new Stack<>();
    }
    
    public boolean isEmpty() {
        return stack.isEmpty();
    }
    
    public void push(StackFrame frame) {
        stack.add(frame);
    }
    
    
    public void removeCurrentFrame() {
        stack.pop();
    }
    
    public StackFrame getCurrentFrame() {
        return stack.peek();
    }
}

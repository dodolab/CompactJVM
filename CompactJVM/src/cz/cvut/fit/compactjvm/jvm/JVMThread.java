/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm;

/**
 *
 * @author Nick Nemame
 */
public class JVMThread {
    
    private JVMStack jvmStack;
    
    //@todo pc register
    private StackFrame currentFrame;
    
    public JVMThread() {
        jvmStack = new JVMStack();
    }
}

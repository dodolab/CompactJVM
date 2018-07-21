
package cz.cvut.fit.compactjvm.jvm;


import java.util.Stack;

/**
 * Zakladni stack JVM, ktery obsahuje stack framy
 * @author Nick Nemame
 */
public class JVMStack {
    
    private final Stack<StackFrame> stack;
    public JVMThread jvmThread;
    
    public JVMStack(JVMThread jvmThread) {
        this.jvmThread = jvmThread;
        stack = new Stack<>();
    }
    
    public boolean isEmpty() {
        return stack.isEmpty();
    }
    
    public void push(StackFrame frame) {
        JVMLogger.increaseGlobalPadding(4);
        stack.add(frame);
    }
    
    
    public void removeCurrentFrame() {
        JVMLogger.increaseGlobalPadding(-4);
        stack.pop();
    }
    
    public StackFrame getCurrentFrame() {
        return stack.peek();
    }
    
    public int getCurrentInstructionIndex(){
        if(stack.empty()) return -1;
        else return getCurrentFrame().getCurrentInstructionIndex();
    }
    
    public Stack<StackFrame> getAllFrames(){
        return stack;
    }
}

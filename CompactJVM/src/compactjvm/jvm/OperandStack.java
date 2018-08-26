package compactjvm.jvm;

import compactjvm.exceptions.LoadingException;
import compactjvm.structures.*;
import java.util.Stack;

/**
 * Stack of invoked instructions, operates with words of a length of INT
 * Thus, if we need to operate with longs and doubles, we need to read
 * two values from the stack
 * @author Adam Vesecky
 */
public class OperandStack {

    Stack<SStruct> operandStack;

    public OperandStack() {
        this.operandStack = new Stack<>();
    }

    public boolean isEmpty() {
        return operandStack.isEmpty();
    }

    /**
     * Gets an item on the selected level of the stack
     * Note that level = 0 is the stack peek !
     * @param <T>
     * @param level
     * @return
     * @throws LoadingException 
     */
    public <T extends SStruct> T get(int level) throws LoadingException{
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

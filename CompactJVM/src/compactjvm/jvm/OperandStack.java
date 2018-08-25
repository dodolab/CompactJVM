package compactjvm.jvm;

import compactjvm.exceptions.LoadingException;
import compactjvm.structures.*;
import java.util.Stack;

/**
 * Predstavuje zasobnik operandu volanych instrukci. Stejne jako v poli
 * lokalnich promennych, pracuje zasobnik nad "slovy", pricemz tato implementace
 * pocita se slovem delky velikosti typu INT. Pokud tedy budeme chtit nacitat
 * operand typu long a double, pak musime z interniho zasobniku nacist dve
 * slova.
 *
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

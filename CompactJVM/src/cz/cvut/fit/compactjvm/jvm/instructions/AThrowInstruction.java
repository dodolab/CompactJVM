/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.core.MethodDefinition;
import cz.cvut.fit.compactjvm.entities.MTHEntity;
import cz.cvut.fit.compactjvm.entities.MethodExcTableItem;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.JVMStack;
import cz.cvut.fit.compactjvm.jvm.MethodArea;
import cz.cvut.fit.compactjvm.jvm.StackFrame;
import cz.cvut.fit.compactjvm.logging.JVMLogger;
import cz.cvut.fit.compactjvm.structures.SObjectRef;

/**
 * Throws exception of error
 *
 * @author Adam Vesecky
 */
public class AThrowInstruction {

    public static void run(JVMStack stack, MethodArea methodArea) throws LoadingException {

        // first of all, get exception reference
        SObjectRef exception = stack.getCurrentFrame().operandStack.pop();

        if (exception.isNull()) {
            throw new LoadingException("Exception that shouldn't be null is null !!!");
        }

        StackFrame actualFrame;

        while (!stack.isEmpty()) {
            actualFrame = stack.getCurrentFrame();
            MethodDefinition method = actualFrame.methodDefinition;

            if (method == null) {
                // probably main method
                throw new RuntimeException("Method definition not found and exception has been thrown!! Leaving main method and exiting... ");
            }

            MethodExcTableItem[] excTable = method.getExceptionTable();

            if (excTable != null) {
                for (int i = 0; i < excTable.length; i++) {
                    MethodExcTableItem excItem = excTable[i];

                    if (excItem.startPc <= actualFrame.getCurrentInstructionIndex()
                            && (excItem.endPc) >= actualFrame.getCurrentInstructionIndex()) {
                        if (excItem.catchClass != null && methodArea.isSuperClass(excItem.catchClass, exception.getClassFile())) {
                            actualFrame.setCurrentInstructionIndex(excItem.handlerPc);
                            actualFrame.operandStack.push(exception);

                            JVMLogger.log(JVMLogger.TAG_INSTR, "AThrow:: " + exception.getClassFile().getClassName() + " catched as a " + excItem.catchClass.getClassName());
                            return;
                        }
                    }
                }
            }
            stack.removeCurrentFrame();
        }

        throw new RuntimeException("No exception handler found!!!!! EXIT !!!!");
    }
}

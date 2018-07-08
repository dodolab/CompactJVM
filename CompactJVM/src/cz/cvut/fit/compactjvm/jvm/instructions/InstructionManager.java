/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.definitions.Instruction;
import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.jvm.JVMStack;
import cz.cvut.fit.compactjvm.jvm.JVMThread;
import cz.cvut.fit.compactjvm.jvm.MethodArea;
import cz.cvut.fit.compactjvm.jvm.StackFrame;

/**
 * Spusti instrukci prislusnou danemu kodu
 * Obsahuje jvmStack, method area, takze je schopen poskytnout instrukcim pristup
 * ke vsemu, co potrebuji, aby mohly pracovat s operand stackem, lokalnimi promennymi,
 * tridnimi promennymi, aby mohly vyvolavat nove metody - pridavat stack frames,
 * vracet hodnoty - odebirat stack frames
 * @author Nick Nemame
 */
public class InstructionManager {
    
    JVMStack jvmStack;
    MethodArea methodArea;

    public InstructionManager(JVMStack jvmStack, MethodArea methodArea) {
        this.methodArea = methodArea;
        this.jvmStack = jvmStack;
    }
    
    public void runInstruction(byte instructionCode) throws LoadingException {
        int code = instructionCode & 0xFF;
        switch(code) {
            //operand stack and local variables instructions
            case Instruction.IN_ISTORE: IStoreInstruciton.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_ISTORE0: IStoreNInstruction.run(jvmStack.getCurrentFrame(), 0); break;
            case Instruction.IN_ISTORE1: IStoreNInstruction.run(jvmStack.getCurrentFrame(), 1); break;
            case Instruction.IN_ISTORE2: IStoreNInstruction.run(jvmStack.getCurrentFrame(), 2); break;
            case Instruction.IN_ISTORE3: IStoreNInstruction.run(jvmStack.getCurrentFrame(), 3); break;
            case Instruction.IN_ILOAD: ILoadInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_ILOAD0: ILoadNInstruction.run(jvmStack.getCurrentFrame(), 0); break;
            case Instruction.IN_ILOAD1: ILoadNInstruction.run(jvmStack.getCurrentFrame(), 1); break;
            case Instruction.IN_ILOAD2: ILoadNInstruction.run(jvmStack.getCurrentFrame(), 2); break;
            case Instruction.IN_ILOAD3: ILoadNInstruction.run(jvmStack.getCurrentFrame(), 3); break;
            case Instruction.IN_IADD: IAddInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_IMUL: IMulInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_BIPUSH: BiPushInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_ICONSTM1: IConstIInstruction.run(jvmStack.getCurrentFrame(), -1); break;
            case Instruction.IN_ICONST0: IConstIInstruction.run(jvmStack.getCurrentFrame(), 0); break;
            case Instruction.IN_ICONST1: IConstIInstruction.run(jvmStack.getCurrentFrame(), 1); break;
            case Instruction.IN_ICONST2: IConstIInstruction.run(jvmStack.getCurrentFrame(), 2); break;
            case Instruction.IN_ICONST3: IConstIInstruction.run(jvmStack.getCurrentFrame(), 3); break;
            case Instruction.IN_ICONST4: IConstIInstruction.run(jvmStack.getCurrentFrame(), 4); break;
            case Instruction.IN_ICONST5: IConstIInstruction.run(jvmStack.getCurrentFrame(), 5); break;
            
            case Instruction.IN_INVOKESTATIC: InvokeStaticInstruction.run(jvmStack, methodArea); break;
            case Instruction.IN_RETURN: ReturnInstruction.run(jvmStack); break;
            case Instruction.IN_IRETURN: IReturnInstruction.run(jvmStack); break;
                
            default:
                System.out.println("Not implemented instruction: "+code);
        }
    }
    
}

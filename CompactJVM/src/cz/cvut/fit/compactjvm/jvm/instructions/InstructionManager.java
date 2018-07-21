package cz.cvut.fit.compactjvm.jvm.instructions;

import cz.cvut.fit.compactjvm.definitions.Instruction;
import cz.cvut.fit.compactjvm.jvm.JVMStack;
import cz.cvut.fit.compactjvm.jvm.MethodArea;
import cz.cvut.fit.compactjvm.jvm.ObjectHeap;
import cz.cvut.fit.compactjvm.jvm.JVMLogger;

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
    ObjectHeap heap;

    public InstructionManager(JVMStack jvmStack, MethodArea methodArea, ObjectHeap heap) {
        this.methodArea = methodArea;
        this.jvmStack = jvmStack;
        this.heap = heap;
    }
    
    public void runInstruction(byte instructionCode) throws Exception {
        int code = instructionCode & 0xFF;
        switch(code) {
            //operand stack and local variables instructions
            case Instruction.IN_ISTORE: IStoreInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_ISTORE0: IStoreNInstruction.run(jvmStack.getCurrentFrame(), 0); break;
            case Instruction.IN_ISTORE1: IStoreNInstruction.run(jvmStack.getCurrentFrame(), 1); break;
            case Instruction.IN_ISTORE2: IStoreNInstruction.run(jvmStack.getCurrentFrame(), 2); break;
            case Instruction.IN_ISTORE3: IStoreNInstruction.run(jvmStack.getCurrentFrame(), 3); break;
            case Instruction.IN_ILOAD: ILoadInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_ILOAD0: ILoadNInstruction.run(jvmStack.getCurrentFrame(), 0); break;
            case Instruction.IN_ILOAD1: ILoadNInstruction.run(jvmStack.getCurrentFrame(), 1); break;
            case Instruction.IN_ILOAD2: ILoadNInstruction.run(jvmStack.getCurrentFrame(), 2); break;
            case Instruction.IN_ILOAD3: ILoadNInstruction.run(jvmStack.getCurrentFrame(), 3); break;
            case Instruction.IN_LLOAD: LLoadInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_LLOAD0: LLoadNInstruction.run(jvmStack.getCurrentFrame(), 0); break;
            case Instruction.IN_LLOAD1: LLoadNInstruction.run(jvmStack.getCurrentFrame(), 1); break;
            case Instruction.IN_LLOAD2: LLoadNInstruction.run(jvmStack.getCurrentFrame(), 2); break;
            case Instruction.IN_LLOAD3: LLoadNInstruction.run(jvmStack.getCurrentFrame(), 3); break;
                
            case Instruction.IN_AALOAD: AALoadInstruction.run(jvmStack.getCurrentFrame(), heap);break;
            case Instruction.IN_ALOAD: ALoadInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_ALOAD0: ALoadInstruction.run(jvmStack.getCurrentFrame(),0); break;
            case Instruction.IN_ALOAD1: ALoadInstruction.run(jvmStack.getCurrentFrame(),1); break;
            case Instruction.IN_ALOAD2: ALoadInstruction.run(jvmStack.getCurrentFrame(),2); break;
            case Instruction.IN_ALOAD3: ALoadInstruction.run(jvmStack.getCurrentFrame(),3); break;
            case Instruction.IN_IADD: IAddInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_ISUB: ISubInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_IMUL: IMulInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_BIPUSH: BiPushInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_SIPUSH: SiPushInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_ICONSTM1: IConstIInstruction.run(jvmStack.getCurrentFrame(), -1); break;
            case Instruction.IN_ICONST0: IConstIInstruction.run(jvmStack.getCurrentFrame(), 0); break;
            case Instruction.IN_ICONST1: IConstIInstruction.run(jvmStack.getCurrentFrame(), 1); break;
            case Instruction.IN_ICONST2: IConstIInstruction.run(jvmStack.getCurrentFrame(), 2); break;
            case Instruction.IN_ICONST3: IConstIInstruction.run(jvmStack.getCurrentFrame(), 3); break;
            case Instruction.IN_ICONST4: IConstIInstruction.run(jvmStack.getCurrentFrame(), 4); break;
            case Instruction.IN_ICONST5: IConstIInstruction.run(jvmStack.getCurrentFrame(), 5); break;
            case Instruction.IN_ATHROW: AThrowInstruction.run(jvmStack,methodArea);break;
            case Instruction.IN_LDC: LdcInstruction.run(jvmStack.getCurrentFrame(), methodArea, heap); break;
                
            case Instruction.IN_ACONST_NULL: AConstNullInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_INVOKESTATIC: InvokeStaticInstruction.run(jvmStack, methodArea); break;
            case Instruction.IN_RETURN: ReturnInstruction.run(jvmStack); break;
            case Instruction.IN_ARETURN: AReturnInstruction.run(jvmStack);break;
            case Instruction.IN_IRETURN: IReturnInstruction.run(jvmStack); break;
            case Instruction.IN_NEWARRAY: NewArrayInstruction.run(jvmStack.getCurrentFrame(), heap); break;
            case Instruction.IN_ANEWARRAY: ANewArrayInstruction.run(jvmStack.getCurrentFrame(),methodArea, heap);break;
            case Instruction.IN_ARRAYLENGTH: ArrayLengthInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_LCONST0: LConstNInstruction.run(jvmStack.getCurrentFrame(),0); break;
            case Instruction.IN_LCONST1: LConstNInstruction.run(jvmStack.getCurrentFrame(),1); break;
            case Instruction.IN_AASTORE: AAStoreInstruction.run(jvmStack.getCurrentFrame(), heap, methodArea); break;
            case Instruction.IN_ASTORE: AStoreNInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_ASTORE0: AStoreNInstruction.run(jvmStack.getCurrentFrame(),0); break;
            case Instruction.IN_ASTORE1: AStoreNInstruction.run(jvmStack.getCurrentFrame(),1); break;
            case Instruction.IN_ASTORE2: AStoreNInstruction.run(jvmStack.getCurrentFrame(),2); break;
            case Instruction.IN_ASTORE3: AStoreNInstruction.run(jvmStack.getCurrentFrame(),3); break;
            case Instruction.IN_IASTORE: IAStoreInstruction.run(jvmStack.getCurrentFrame(), heap, methodArea);break;
            case Instruction.IN_BALOAD: BaLoadInstruction.run(jvmStack.getCurrentFrame(), heap);break;
            case Instruction.IN_IALOAD : IALoadInstruction.run(jvmStack.getCurrentFrame(), heap); break;
            case Instruction.IN_IFLE : IfleInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_NOP : NopInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_POP : PopInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_GOTO : GotoInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_IINC: IIncInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_FCONST0: FConstInstruction.run(jvmStack.getCurrentFrame(), 0.0f); break;
            case Instruction.IN_FCONST1: FConstInstruction.run(jvmStack.getCurrentFrame(), 1.0f); break;
            case Instruction.IN_FCONST2: FConstInstruction.run(jvmStack.getCurrentFrame(), 2.0f); break;
            case Instruction.IN_IF_ICMPGE: IfIcmpgeInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_IF_ICMPGT: IfIcmpgtInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_IF_ICMPEQ: IfcmpeqInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_IF_ICMPNE: IfcmpneInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_IF_NULL: IfnullInstruction.run(jvmStack.getCurrentFrame());break;
            case Instruction.IN_IF_NON_NULL: IfnonnullInstruction.run(jvmStack.getCurrentFrame());break;
            case Instruction.IN_IF_EQ: IfeqInstruction.run(jvmStack.getCurrentFrame());break;
            case Instruction.IN_NEW: NewInstruction.run(jvmStack, methodArea, heap); break;
            case Instruction.IN_DUP: DupInstruction.run(jvmStack.getCurrentFrame()); break;
            case Instruction.IN_INVOKESPECIAL: InvokeSpecialInstruction.run(jvmStack, methodArea); break;
            case Instruction.IN_PUTFIELD: PutfieldInstruction.run(jvmStack, heap, methodArea); break;
            case Instruction.IN_GETFIELD: GetfieldInstruction.run(jvmStack, heap); break;
            case Instruction.IN_INVOKEVIRTUAL: InvokeVirtualInstruction.run(jvmStack, methodArea); break;
            case Instruction.IN_PUTSTATIC: PutStaticInstruction.run(jvmStack.getCurrentFrame());break;
            case Instruction.IN_GETSTATIC: GetStaticInstruction.run(jvmStack, methodArea); break; 
            case Instruction.IN_CALOAD : CALoadInstruction.run(jvmStack.getCurrentFrame(), heap); break;
            case Instruction.IN_CASTORE : CAStoreInstruction.run(jvmStack.getCurrentFrame(), heap); break;
            default:
                JVMLogger.log(JVMLogger.TAG_OTHER, "Not implemented instruction: "+code+" (0x"+Integer.toHexString(code)+")");
        }
    }
    
}

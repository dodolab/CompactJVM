
------------------------------------------
Experimental Java Virtual Machine
------------------------------------------

Budget: 100 hours

## Projects
- CompactJVM - virtual machine 
- CopmactJVMLab - library of dummy objects and native proxy methods, has to be included by a project that runs inside the CompactJVM 
- SatSolver - an example app for resolving boolean satisfiability

## Logger, heap size

## Parameters
- path to the root dir of your app 
- path to the root dir of libraries (compiled CompactJVMLib)
- namespace of the main class (e.g. satSolver/Main)
- arguments for excecuted application

## SAT Solver parameters
- input file with clauses
- output file for writing

## Build 
- run following commands from within CompactJVM folder

```
ant compile
ant run
```

- run following commands from within SatSolver folder 

```
ant compile 
```

- to execute the SatSolver with a test file you need to go to the `CompactJVM/build/jar` and execute following command:

```
java -jar CompactJVM.jar ../../SatSolver/build/classes ../../CompactJVMLib/build/classes satSolver/Main ../../SatSolver/examples/cnf_3_12.txt result.txt
```

## Input file
- line begining with 'c' contains a comment 
- the first line should contain 'p' letter, followed by 2 values: number of variables (M) and number of clauses (N)
- N lines follow, containing clauses as numbers divided by spaces. Each number (1-M) represents a variable. If the number is negative, the variable is negated. At the end of each line must be '0' as an ending character.

Example:

```
c test 176
p 3 4
1 -2 3
-1 2 -3
1 -3
2 -3
```

## Output file:
- contains a message whether or not is the formula satisfiable
- if the formula is satisfable, the output file will contain an interpretation that satisfies it as follows (e.g. [True True False])
   
   
## Features:
### Garbage collector
- Mark&Copy algorithm, searches for objects in use that are to be copied into other half of the heap, the first one will be discared afterwards

### Exceptions 
- support for exception from within and outside the running application (ArithmeticException, NullPointerException,...)

### Native method 
- IO operations are handled by native methods, implemented in CompactJVM


### Inheritance

## What is not implemented
- Everything else :-)
- multithreading, annotations, generics, anonymous objects, Java7+ features

## Packages
- compactjvm.attributes
-- attributes providing information about exceptions, constants, methods and fields
- compactjvm.classfile
-- entities describing classfile such as method definitions, exception tables and field definitions 
- compactjvm.cpentities
-- constant pool entity descriptors (primitive types, classes, interfaces, strings)
- compactjvm.definitions
-- enums, instruction set, attribute types
- compactjvm.exceptions 
-- exceptions the JVM may throw (e.g. ArrayOutOfBoundsException)
- compactjvm.jvm 
-- core of the virtual machine (stack frame, heap, logger, garbage collector)
- compactjvm.jvm.instructions 
-- instructions and their implementation
- compactjvm.natives 
-- implementations of several native classes (FileReader, TextReader and TextWriter)
- compactjvm.parsing
-- classes for parsing a class file 
- compactjvm.structures
-- bytecode entities (primitive fields, pointers, array references, objects)

## Supported instructions:
- aaload
- aastore
- aconstnull
- aload
- anewarray
- areturn
- astore
- athrow
- arraylength
- baload
- bipush
- caload
- castore
- dup
- fconst
- getstatic
- getfield
- goto
- iaload
- iastore
- iadd
- iconst
- idiv
- iinc
- iload
- iloadN
- imul
- ineg
- ireturn
- istore
- istoreN
- isub
- ifacmpeq
- ifacmpne
- ificmpge
- ificmpgt
- ificmple
- ificmpeq
- ificmpne
- ifeq
- ifge
- ifgt
- iflt
- ifle
- ifne
- ifnonnull
- ifnull
- invokespecial
- invokestatic
- invokevirtual
- lconstn
- lload
- lloadn
- ldc
- newarray
- new
- nop
- pop
- putfield
- sipush


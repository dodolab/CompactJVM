
------------------------------------------
## Compact Java Virtual Machine
------------------------------------------

This is my modest implementation of a java bytecode interpreter I created, mainly for educational purposes. 

Budget: 120 working hours

## Projects 
- `CompactJVM` - virtual machine
- `CompactJVMLib` - a library that contains a dummy implementation of several `java.lang` classes (such as `String` and `Exception`) and native objects (`FileReader`) 
- `SatSolver` - a demo project that can be tested with the JVM (boolean satisfiability problem solver)

# Virtual machine 

## Installation
- just execute `ant compile` from within each folder of each project or use **NetBeans IDE**

## Run
Run the `CompactJVM` as follows: 

```bash
java -jar CompactJVM.jar <app-dir> <lib-dir> <main-class> <args>
```

- `app-dir` is path to the bytecode directory of your application you want to execute
- `lib-dir` is path to the bytecode directory of libraries (`CompactJVMLib`)
- `main-class` is the namespace of your main class (e.g. `satSovler/Main`)
- `args` are arguments of your application

Example: executing sat solver from within dist directory of the `CompactJVM` project:

```bash
java -jar CompactJVM.jar ../../SatSolver/build/classes ../../CompactJVMLib/build/classes satSolver/Main ../../SatSolver/examples/cnf_3_12.txt result.txt
```

## JVM Config
There is no configuration file. If you want to increase heap size or enable or disable particular logging, go to the `compactjvm.jvm.Main.java`. The code should be commented enough to give you a bigger picture.

## Packages
- `compactjvm.attributes` - attributes providing information about exceptions, constants, methods and fields
- `compactjvm.classfile` - entities describing classfile such as method definitions, exception tables and field definitions 
- `compactjvm.cpentities` - constant pool entity descriptors (primitive types, classes, interfaces, strings)
- `compactjvm.definitions` - enums, instruction set, attribute types
- `compactjvm.exceptions` - exceptions the JVM may throw (e.g. ArrayOutOfBoundsException)
- `compactjvm.jvm` - core of the virtual machine (stack frame, heap, logger, garbage collector)
- `compactjvm.jvm.instructions` - instructions and their implementation
- `compactjvm.natives` - implementations of several native classes (FileReader, TextReader and TextWriter)
- `compactjvm.parsing` - classes for parsing a class file 
- `compactjvm.structures` - bytecode entities (primitive fields, pointers, array references, objects)

## Features:
- **Garbage collector** - Mark&Copy algorithm, searches for objects in use that are to be copied into other half of the heap, the first one will be discared afterwards
- **Exceptions** - support for exception from within and outside the running application (ArithmeticException, NullPointerException,...)
- **Native methods** - IO operations are handled by native methods, implemented in CompactJVM
- **Inheritance** - simple inheritance with virtual invocation

## What is not implemented
- multithreading, annotations, generics, anonymous objects, Java7+ features

## Implemented instructions:
```
aaload, aastore, aconstnull, aload, anewarray, areturn, astore, athrow,
arraylength, baload, bipush, caload, castore, dup, fconst, getstatic,
 getfield, goto, iaload, iastore, iadd, iconst, idiv, iinc, iload, iloadN,
 imul, ineg, ireturn, istore, istoreN, isub, ifacmpeq, ifacmpne, ificmpge,
 ificmpgt, ificmple, ificmpeq, ificmpne, ifeq, ifge, ifgt, iflt, ifle, ifne,
 ifnonnull, ifnull, invokespecial, invokestatic, invokevirtual, lconstn,
 lload, lloadn, ldc, newarray, new, nop, pop, putfield, sipush
```

# SAT Solver 
This is a demonstration project that can be executed in `CompactJVM`

## Arguments
- path to the input file with clauses 
- path to the output file with results 

## Input file
- line begining with **c** contains a comment 
- the first line must contain **p** letter, followed by 2 values: number of variables **M** and number of clauses **N**
- N lines follow, containing clauses as numbers divided by spaces. Each number **1-M** represents a variable. If the number is negative, the variable is negated. At the end of each line must be '0' as an ending character.
- **You can find several examples inside SatSolver/examples folder**


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
   

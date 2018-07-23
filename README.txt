MI-RUN semestrálka
Autoøi: Adam Vesecký (svecadam), Jan Havlíèek (havlij17) 


------------------------------------------
Implementace Java Virtual Machine v Javì
------------------------------------------

***********************************
Projekty
***********************************
***********************************

CompactJVM
- virtuální stroj

parametry:
- cesta do koøenového adresáøe s aplikací
- cesta do koøenového adresáøe s knihovnami (zkompilované CompactJVMLib)
- namespace main tøídy (napø. satSolver/Main)
- argumenty pro spouštìný program

-------------------

CompactJVMLab
- knihovna dummy objektù a nativních proxy metod
- musí být includována projektem, který pobìží v CompactJVM

-------------------

SatSolver
- SAT øešiè

parametry:
- vstupní soubor s klauzulemi
- výstupní soubor pro zápis


***********************************
Build:
***********************************
***********************************


V adresáøi projektù CompactJVM spustit tyto pøíkazy:
ant compile
ant jar

V adresáøi projektu SatSolver spustit tento pøíkaz:
ant compile


Pro spuštìní projektu SatSolver nad testovacími daty pak staèí v adresáøi CompactJVM/build/jar spustit následující pøíkaz:
java -jar CompactJVM.jar ../../../SatSolver/build/classes/ ../../../CompactJVMLib/build/classes/ satSolver/Main ../../../SatSolver/cnf.txt result.txt

-- v metodì Main projektu CompactJVM je možno povypínat jednotlivé typy logù


***********************************
Vstupní soubor:
***********************************
***********************************

Vstupní soubor odpovídá bìžné konvenci pro zadání SAT problému.
 - øádek zaèánající znakem "c" obsahuje komentáø
 - následuje øádek zaèínající znakem "p" a ten dále obsahuje 2 hodnoty oddìlené mezerou:
    - 1. poèet promìnných (M)
    - 2. poèet klauzulí (N)
 - dále následuje N øádkù obsahující klauzule v podobì èísel oddìlených mezerou. Každé èíslo (1 až M) pøedstavuje promìnnou a pokud je èíslo záporné, je promìnná negována. Na konci øádku je ukonèovací znak "0".
 
 Pøíklad:
 c Testovací soubor cnf
 p 3 4
 1 -2 3
 -1 2 -3
 1 -3
 2 -3
    
    
***********************************
Výstupní soubor:
***********************************
***********************************

Obsahuje zprávu, zda je zadaný výraz splnitelný nebo ne, pokud splnitelný je, jsou vypsány hodnoty True nebo False v poøadí oznaèení promìnných, napø:
[True True False] znamená promìnná 1 = True, 2 = True, 3 = False.


***********************************
Features:
***********************************
***********************************

Garbage collector:
- použit Mark&Copy, vyhledává používané objekty, které zkopíruje do druhé poloviny heapy, ta první je poté zahozena

Exceptions:
- podpora výjimek uvnitø i vnì bìžící aplikace (ArithmeticException, NullPointerException)

Nativní metody
- zápis do souboru a výstup do konzole je øešen pomocí nativních metod
- tyto metody jsou implementovány v CompactJVM

Dìdiènost


***********************************



Seznam implementovaných instrukcí:
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
- ificmpge
- ificmpgt
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
- putstatic
- putfield
- sipush


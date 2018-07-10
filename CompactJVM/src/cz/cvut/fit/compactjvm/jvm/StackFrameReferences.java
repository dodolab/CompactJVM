/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.core.Word;
import java.util.Map;
import java.util.Set;

/**
 * Tato struktura udrzuje reference pro stack frame. Obsahuje reference
 * z lokalnich promennych a operand stacku. Jelikoz je pole lokalnich promennych
 * i operand stack pouze pole slov a nevime semantiku jednotlivych slov,
 * ukladame reference zaroven do teto struktury, ktera reflektuje, ktere reference
 * jsou v danem framu pouzity (a kolikrat). 
 * 
 * Tato struktura tedy neimplementuje GC pomoci citacu referenci, ale pouze
 * je napomocna k snadnemu nalezeni "root" referenci v danem framu.
 * @author Nick Nemame
 */
public class StackFrameReferences {
    
    Map<Integer, Integer> references; //Klic je reference, integer je interni citac
    
    public void addReference(int reference) {
        if(!references.containsKey(reference)) references.put(reference, 1);
        else references.put(reference, references.get(reference) + 1);
    }
    
    public void removeReference(int reference) {
        //if(!references.containsKey(reference)) throw new Exception("Stack frame reference some shit with reference:"+ reference );
        if(references.get(reference) > 1) references.put(reference, references.get(reference) - 1);
        else references.remove(reference);
    }
    
    public Set<Integer> getReferences() {
        return references.keySet();
    }
    
}

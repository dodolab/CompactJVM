
package cz.cvut.fit.compactjvm.jvm;

import cz.cvut.fit.compactjvm.exceptions.LoadingException;
import cz.cvut.fit.compactjvm.structures.*;


/**
 * Velikost slova je v teto implementaci definovana jako int.
 * int, short, byte a float odpovidaji velikosti 1 slova, double a long opodvidaji
 * velikosti 2 slov. Horni slovo doublu a longu by nemelo byt dostupne naprimo.
 * @todo Zvazit kontrolu na to, zda jiz na danem indexu byla nastavena hodnota nebo ne, ackoliv Java na me vlastne asi nezarve, kdyz saham nekam, kde jsem neinicalizoval hodnotu.
 * @todo Pro volani instancni metody je treba do localVariables umistit na pozici 0 referenci na "this"
 * @author Nick Nemame
 */
public class LocalVariableArray {
    
    /**
     * Pole slov pro jednotlive lokalni promenne
     */
    SStruct[] localVariables;
    
    /**
     * @param size Velikost pole lokalnich promennych, ziskano z ClassFile
     * @todo pridat moznost vlozit "this" referenci pro instancni volani metody
     */
    public LocalVariableArray(int size) {
        localVariables = new SStruct[size];
    }
    
    public <T extends SStruct> T getVar(int index) throws LoadingException{
        SStruct ent = localVariables[index];
        T entity = (T)ent;
        
        if(entity == null) throw new LoadingException("Wrong type! Found: "+ent.toString());
        return entity;
    }
    
    public <T extends SStruct> void setVar(int index, T value) throws LoadingException {
        if(value == null) throw new LoadingException("Variable can't be null!");
        
        localVariables[index] = value;
    }
}

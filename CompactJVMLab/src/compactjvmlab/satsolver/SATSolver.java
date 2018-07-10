/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compactjvmlab.satsolver;

import java.util.Date;

/**
 *
 * @author Nick Nemame
 */
public class SATSolver {
    
    /**
     * http://ktiml.mff.cuni.cz/~bartak/ui_seminar/talks/2014LS/BioInspiredSAT_Intro.pdf
     * @param clauses
     * @param variablesCount
     * @return 
     */
    public int[] solve(int[][] clauses, int variablesCount) {
        
        return null;
    }
    
    //nova generace
    //selekce
    // - turnajovy vyber (vyberu nahodne "r" jedincu do turnaje a vyberu nejlepsiho) a opakovat do naplneni populace
    // - selekcni tlak se ridi velikosti turnaje (r = 1 - zadny selekcni tlak, r = all - urcite vyberu nejlepsiho)
    //krizeni (1bodove, 2bodove, uniformni)
    // - nastaveni pravdepodobnosti krizeni
    //mutace (nahodne vybrany bod z cele populace)
    // - nastaveni pravdepodobnosti mutace
    //ukoncovaci podminky
    
    //VSTUPY:
    //N: velikost populace
    //r: pocet ucastniku souboje selekce
    //pc: pravdepodobnost krizeni
    //pm: pravdepodobnost mutace
    
}

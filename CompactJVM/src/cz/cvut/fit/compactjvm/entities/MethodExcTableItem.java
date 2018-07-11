/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.entities;

import cz.cvut.fit.compactjvm.core.ClassFile;

/**
 * The same as AttrExcTableItem but it is used in MethodDefinition entity
 * 
 * @author Adam Vesecky
 */
public class MethodExcTableItem {
    public int startPc;
    public int endPc;
    public int handlerPc;
    public ClassFile catchClass;
}

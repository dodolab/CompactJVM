/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Field definition entity, used when a field is needed by an instruction
 * @author Adam Vesecky
 */
public final class FieldDefinition {
    private String fieldClass;
    private String fieldName;
    private String fieldDescriptor;
    private List<String> fieldParams;
    private String returnType;

    public FieldDefinition(String fieldClass, String fieldName, String fieldDescriptor) {
        this.fieldClass = fieldClass;
        this.fieldName = fieldName;
        this.fieldDescriptor = fieldDescriptor;
        fieldParams = new ArrayList<>();
        parseFieldDescriptor();
    }
    
    public String getFieldClass() {
        return fieldClass;
    }

    public String getFieldName() {
        return fieldName;
    }

    public List<String> getFieldParams() {
        return fieldParams;
    }

    public String getReturnType() {
        return returnType;
    }
    
    public String getFieldDescriptor() {
        return fieldDescriptor;
    }
    
    private void parseFieldDescriptor() {
        // todo
    }
   
}

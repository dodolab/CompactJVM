package cz.cvut.fit.compactjvm.classfile;

import cz.cvut.fit.compactjvm.attributes.AttrExcTableItem;
import cz.cvut.fit.compactjvm.definitions.MethodAccessFlag;
import java.util.ArrayList;
import java.util.List;

/**
 * Pouzije se pri volani metody, kdy musim z constant poolu nacist tyto hodnoty
 * a pote je v instrukci zpracovat
 * @author Nick Nemame
 */
public final class MethodDefinition {
    private String methodClass;
    private String methodName;
    private String methodDescriptor;
    private List<String> methodParams;
    private String returnType;
    private int accessFlags;
    private MethodExcTableItem[] exceptionTable;
    
    public MethodDefinition(String methodClass, String methodName, String methodDescriptor, int accessFlags) {
        this.methodClass = methodClass;
        this.methodName = methodName;
        this.methodDescriptor = methodDescriptor;
        methodParams = new ArrayList<>();
        this.accessFlags = accessFlags;
        parseMethodDescriptor();
    }
    
    public String getMethodClass() {
        return methodClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<String> getMethodParams() {
        return methodParams;
    }

    public String getReturnType() {
        return returnType;
    }
    
    public void setExceptionTable(MethodExcTableItem[] excTable){
        this.exceptionTable = excTable;
    }
    
    public MethodExcTableItem[] getExceptionTable(){
        return exceptionTable;
    }
    
    public boolean isNativeMethod(){
        return (this.accessFlags & MethodAccessFlag.ACC_NATIVE) == MethodAccessFlag.ACC_NATIVE;
    }
    
    /**
     * Spocte pocet slov, ktere je potreba alokovat na parametry v poli lokalnich promennych.
     * @todo zjistit, jak se delaji pole, jestli je to typ "reference", stejne tak instance objektu.
     * @return 
     */
    public int getMethodParamsWordsCount() {
        int wordsCount = 0;
        for(String param : methodParams) {
            //if(param.charAt(0) == '[') wordsCount += 1;
            wordsCount += ("L".equals(param) || "D".equals(param)) ? 2 : 1;
        }
        return wordsCount;
    }

    public String getMethodDescriptor() {
        return methodDescriptor;
    }
    
    private void parseMethodDescriptor() {
        //String staticTypes = "ZBCSIJFD"; //Z=boolean, B=byte, C=char, S=short,I=int, J=long, F=float, D=double
        //String objectTypes = "L([^;]);";
        int paramsStart = methodDescriptor.indexOf("(") + 1;
        int paramsEnd = methodDescriptor.indexOf(")");
        String _params = methodDescriptor.substring(paramsStart, paramsEnd);
        String _returnType = methodDescriptor.substring(paramsEnd + 1);
        parseMethodParams(_params);
        parseReturnType(_returnType);
    }
    
    private void parseReturnType(String _returnType) {
        returnType = _returnType.replaceAll("[L;]", ""); //Muze zacinat [ jako pole a pak bud jeden znak nebo L...;
    }
    
    /**
     * Zparsuje string s parametry a vytvori list, ktery obsahuje bud znaky
     * definujici jednoduche typy nebo nazvy trid, popripade pokud hodnota zacina
     * znakem [, pak predstavuje pole tohoto typu.
     * @todo Co generiky, kaslem na ne, jak to vypada?
     * @param _params 
     */
    private void parseMethodParams(String _params) {
        boolean isArrayParam = false;
        boolean classNameReading = false;
        StringBuilder buff = new StringBuilder();
        char[] _paramsChars = _params.toCharArray();
        for(int i = 0; i < _paramsChars.length; ++i) {
            if(_paramsChars[i] == '[') {
                isArrayParam = true;
            } else if(_paramsChars[i] == 'L') {
                //Zacinam nacitat nazev tridy
                classNameReading = true;
            } else if(_paramsChars[i] == ';') {
                //Nacetl jsem cely nazev tridy
                String className = buff.toString();
                methodParams.add((isArrayParam ? "[" : "") + className);
                buff.delete(0, buff.length());
                classNameReading = false;
                isArrayParam = false;
            } else if(classNameReading) {
                //nacitam nazev tridy - jsem mezi L a ;
                buff.append(_paramsChars[i]);
            } else {
                //Nacitam znaky identifikujici zakladni typy
                methodParams.add((isArrayParam ? "[" : "") + _paramsChars[i]);
                isArrayParam = false;
            }
                
        }
    }

}

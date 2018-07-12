/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java.lang;

/**
 *
 * @author Nick Nemame
 */
public class String {

    char[] data;
    
    public String(char data[]) {
        this.data = data;
    }
    
    public int length() {
        return data.length;
    }
    
    public boolean equals(String str) {
        if(str.length() != data.length) return false;
        char[] strData = str.toCharArray();
        for(int i = 0; i < strData.length; ++i) {
            if(data[i] != strData[i]) return false;
        }
        return true;
    }
    
    public char[] toCharArray() {
        char[] copy = new char[data.length];
        for(int i = 0; i < data.length; ++i) copy[i] = data[i];
        return copy;
    }
    
}

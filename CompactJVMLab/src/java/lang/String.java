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
    
    public String[] split(String delimiter) {
        //if(delimiter.length() > 1) throw new Exception("Large delimiters not implemented");
        char delimiterChar = delimiter.charAt(0);
        int matchesCount = getMatchesCount(delimiterChar);
        String[] str = new String[matchesCount+1];
        
        int stringIndex = 0;
        int oldIndex = 0;
        for(int i = 0; i <= data.length; ++i) {
            if(i == data.length || data[i] == delimiterChar) {
                char[] piece = new char[i - oldIndex];
                if(oldIndex == i) {
                    str[stringIndex++] = "";
                } else {
                    for(int j = oldIndex; j < i; ++j) {
                        piece[j - oldIndex] = data[j];
                    }
                    str[stringIndex++] = new String(piece);
                }
                oldIndex = i + 1;
            }
        }
        return str;
    }
    
    private int getMatchesCount(char delimiterChar) {
        int count = 0;
        for(int i = 0; i < data.length; ++i) {
            if(data[i] == delimiterChar) ++count;
        }
        return count;
    }
    
    char charAt(int index) {
        return data[index];
    }
}

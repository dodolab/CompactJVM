/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java.lang;

/**
 *
 * @author Adam Vesecky
 */
public class Throwable {
    String message;
    
    public Throwable(String message){
        this.message = message;
    }
    
    public String getMessage(){
        return message;
    }
}

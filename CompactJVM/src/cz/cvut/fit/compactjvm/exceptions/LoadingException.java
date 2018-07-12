/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.compactjvm.exceptions;

/**
 * Exception that can be thrown anytime during the instruction processing
 *
 * @author Adam Vesecky
 */

public class LoadingException  extends Exception{
    
    public LoadingException(String exc){
        super(exc);
    }
}

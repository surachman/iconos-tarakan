/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author R Seno Anggoro
 */
@ApplicationException(rollback=true)
public class TimeSelectionNotValidException extends Exception {
    public TimeSelectionNotValidException(){
        super("Time Selection Not Valid");
    }

    public TimeSelectionNotValidException(String message){
        super(message);
    }
}

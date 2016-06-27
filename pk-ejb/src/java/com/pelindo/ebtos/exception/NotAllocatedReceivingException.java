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
public class NotAllocatedReceivingException extends Exception {
    public NotAllocatedReceivingException(){
        super("Receiving Is Not Allocated yet");
    }

    public NotAllocatedReceivingException(String message){
        super(message);
    }
}

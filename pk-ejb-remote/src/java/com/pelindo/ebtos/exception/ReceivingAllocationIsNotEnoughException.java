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
public class ReceivingAllocationIsNotEnoughException extends Exception {
    public ReceivingAllocationIsNotEnoughException(){
        super("Receiving Allocation Is Not Enough Exception");
    }

    public ReceivingAllocationIsNotEnoughException(String message){
        super(message);
    }
}

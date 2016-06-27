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
public class ReceivingAllocationCont40ftIsNotEnoughException extends Exception {
    public ReceivingAllocationCont40ftIsNotEnoughException(){
        super("Receiving Allocation Container 40ft Is Not Enough Exception");
    }

    public ReceivingAllocationCont40ftIsNotEnoughException(String message){
        super(message);
    }
}

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
public class HasJobSlipAndNotEnteringGateYetException extends Exception {
    public HasJobSlipAndNotEnteringGateYetException(){
        super("Has jobSlip and not entering gate yet");
    }
}

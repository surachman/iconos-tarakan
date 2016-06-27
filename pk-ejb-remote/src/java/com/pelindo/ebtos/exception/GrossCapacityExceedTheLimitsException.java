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
public class GrossCapacityExceedTheLimitsException extends Exception {
    private int maxGross = 0;

    public GrossCapacityExceedTheLimitsException(){
        super("Gross capacity exceed the Limits");
    }

    public GrossCapacityExceedTheLimitsException(int maxGross){
        super();
        this.maxGross = maxGross;
    }

    public int getMaxGross() {
        return maxGross;
    }
}

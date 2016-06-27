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
public class TeusCapacityExceedTheLimitsException extends Exception {
    private int maxTeus = 0;

    public TeusCapacityExceedTheLimitsException(){
        super("Teus capacity exceed the Limits");
    }

    public TeusCapacityExceedTheLimitsException(int maxTeus){
        super();
        this.maxTeus = maxTeus;
    }

    public int getMaxTeus() {
        return maxTeus;
    }
}

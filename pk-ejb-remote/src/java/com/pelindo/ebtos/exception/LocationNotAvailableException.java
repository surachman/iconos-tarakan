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
public class LocationNotAvailableException extends Exception {
    public LocationNotAvailableException(){
        super("Location not available");
    }

    public LocationNotAvailableException(String message){
        super(message);
    }
}

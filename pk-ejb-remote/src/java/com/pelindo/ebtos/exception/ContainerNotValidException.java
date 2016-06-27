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
public class ContainerNotValidException extends Exception {
    public ContainerNotValidException(){
        super("Container attribute is not valid");
    }

    public ContainerNotValidException(String message){
        super(message);
    }
}

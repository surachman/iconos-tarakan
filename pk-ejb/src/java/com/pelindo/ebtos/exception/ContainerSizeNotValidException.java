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
public class ContainerSizeNotValidException extends Exception {
    public ContainerSizeNotValidException(){
        super("Container size is not valid");
    }

    public ContainerSizeNotValidException(String message){
        super(message);
    }
}

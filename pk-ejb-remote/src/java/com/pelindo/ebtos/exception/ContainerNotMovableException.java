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
public class ContainerNotMovableException extends Exception {
    public ContainerNotMovableException(){
        super("Container is not movable with current state");
    }

    public ContainerNotMovableException(String message){
        super(message);
    }
}

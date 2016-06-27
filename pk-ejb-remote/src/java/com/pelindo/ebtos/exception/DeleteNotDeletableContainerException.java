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
public class DeleteNotDeletableContainerException extends Exception {
    public DeleteNotDeletableContainerException(){
        super("Delete Not Deletable Container Exception");
    }

    public DeleteNotDeletableContainerException(String message){
        super(message);
    }
}

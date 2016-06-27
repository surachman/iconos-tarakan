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
public class RegisteredWithSamePpkbContainerException extends Exception {
    public RegisteredWithSamePpkbContainerException(){
        super("Already registered with same PPKB");
    }
}

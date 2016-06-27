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
public class MasterSettingAppValueNotValidException extends Exception {
    public MasterSettingAppValueNotValidException(){
        super("MasterSettingApp value is not valid");
    }

    public MasterSettingAppValueNotValidException(String message){
        super(message);
    }
}

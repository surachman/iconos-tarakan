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
public class MasterSettingAppNotFoundException extends Exception {
    public MasterSettingAppNotFoundException(){
        super("MasterSettingApp not found");
    }

    public MasterSettingAppNotFoundException(String message){
        super(message);
    }
}

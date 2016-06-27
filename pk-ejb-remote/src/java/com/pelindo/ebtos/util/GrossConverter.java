/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.util;

import com.pelindo.ebtos.ejb.facade.remote.MasterGrossParameterFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterGrossParameter;
import javax.naming.Context;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
/**
 *
 * @author Dyware-Dev01
 */
public class GrossConverter {

    private static MasterGrossParameterFacadeRemote masterGrossParameterFacade = lookupMasterGrossParameterFacadeRemote();
    private static MasterGrossParameter masterGrossParameter;
    
    public static String convert(Short contSize, int contGros) {
        masterGrossParameter = masterGrossParameterFacade.findByContTypeAndGross(contSize, contGros);
        if (masterGrossParameter != null)
            return masterGrossParameter.getGrossClass();
        else
            return "A";

    }


    private static MasterGrossParameterFacadeRemote lookupMasterGrossParameterFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterGrossParameterFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterGrossParameterFacade!com.pelindo.ebtos.ejb.facade.remote.MasterGrossParameterFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(GrossConverter.class.getName()).log(Level.SEVERE, "exception caught", ne);
        }
        return null;
    }
}

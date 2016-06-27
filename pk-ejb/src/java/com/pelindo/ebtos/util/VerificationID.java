/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.util;

import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author R Seno Anggoro
 */
public class VerificationID {
    private static final String VALUES_TABLE_PARAM = "ebtos.verificationid.values_table";
    private static final String SUFIX_TABLE_PARAM = "ebtos.verificationid.sufix_table";

    private static final StringBuilder DEFAULT_VALUES_TABLE = new StringBuilder("QRSTUVWXYZ");
    private static final StringBuilder DEFAULT_SUFIX_TABLE = new StringBuilder("ABCDEFGHIJ");
    private static final String DEFAULT_DELIMETER = ".";

    private MasterSettingAppFacadeRemote masterSettingAppFacade = lookupMasterSettingAppFacadeRemote();

    private String shortRegistrationID;
    private String shortNotaID;
    private String sufix;
    private StringBuilder valuesTable;
    private StringBuilder sufixTable;

    public VerificationID(String registrationID, String notaID){
        try {
            int indexOfDelimeter = registrationID.indexOf(DEFAULT_DELIMETER);
            shortRegistrationID = String.valueOf(Integer.parseInt(registrationID.substring(indexOfDelimeter + 1, registrationID.length())));
            shortNotaID = String.valueOf(Integer.parseInt(notaID.substring(notaID.indexOf(DEFAULT_DELIMETER) + 1, notaID.length())));
            sufix = registrationID.substring(0, indexOfDelimeter);

            try {
                MasterSettingApp setting = masterSettingAppFacade.find(VALUES_TABLE_PARAM);
                if (setting != null)
                    valuesTable = new StringBuilder(setting.getValueString());
            } catch (RuntimeException re) {

            }

            try {
                MasterSettingApp setting = masterSettingAppFacade.find(SUFIX_TABLE_PARAM);
                if (setting != null)
                    sufixTable = new StringBuilder(setting.getValueString());
            } catch (RuntimeException re) {

            }
        } catch (RuntimeException re){
            
        }
    }

    public StringBuilder getSufixTable(){
        if (sufixTable == null)
            return DEFAULT_SUFIX_TABLE;
        return sufixTable;
    }

    public StringBuilder getValuesTable(){
        if (valuesTable == null)
            return DEFAULT_VALUES_TABLE;
        return valuesTable;
    }

    public String getGeneratedID(){
        try {
            return sufix + "." + encode(shortRegistrationID + shortNotaID) + getSuffix();
        } catch (RuntimeException re) {
            return "";
        }
    }

    private StringBuilder encode(String text){
        StringBuilder result = new StringBuilder("");
        for (int i = 0;i < text.length();i++){
            result.append(getValuesTable().charAt(Integer.parseInt(String.valueOf(text.charAt(i)))));
        }
        return result;
    }

    private String getSuffix(){
        String safeShortNotaID = "0" + shortNotaID;
        int length = safeShortNotaID.length();
        return String.valueOf(getSufixTable().charAt(Integer.parseInt(safeShortNotaID.substring(length - 2, length - 1))));
    }

    private MasterSettingAppFacadeRemote lookupMasterSettingAppFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterSettingAppFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterSettingAppFacade!com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
        }
        return null;
    }
}

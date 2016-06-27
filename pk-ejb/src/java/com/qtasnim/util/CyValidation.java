/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qtasnim.util;

import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author USER
 */
public class CyValidation {
    MasterYardCoordinatFacadeRemote masterYardCoordinatFacadeRemote = lookupMasterYardCoordinatFacadeRemote();
    private static final int MAX_TIER = 5;
    private static final int MIN_TIER = 1;
    private static final int CONT_20 = 20;
    private static final int CONT_40 = 40;

    public CyValidation() {
    }

    public Boolean isTopExist(String block, Short slot, Short row, Short tier, Short cont_size) {
        Boolean retValue = false;

        if (tier < MAX_TIER) {
            Object[] statusCoordinat = new Object[1];
            statusCoordinat = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(block, slot, row, (short) (tier + 1));

            /**  Cek Size Container */
            switch (cont_size) {
                case CONT_20:

                    /** jika ada data */
                    if (statusCoordinat != null && statusCoordinat[1].equals("exist")) {
                        retValue = true;
                    }
                    break;
                case CONT_40:
                    Object[] statusCoordinat2 = new Object[1];
                    statusCoordinat2 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(block, (short) (slot + 1), row, (short) (tier + 1));
                    /** jika slot pertama ada data ATAU slot kedua ada data */
                    if ((statusCoordinat != null && statusCoordinat[1].equals("exist")) || (statusCoordinat2 != null && statusCoordinat2[1].equals("exist"))) {
                        retValue = true;
                    }
                    break;
                default:
                //do Nothing
            }
        }
        return retValue;
    }

    public Boolean isBottomExist(String block, short slot, short row, short tier, Short cont_size) {
        Boolean retValue = false;
        Object[] statusCoordinat, statusCoordinat2;

        if (tier > MIN_TIER) {
            statusCoordinat = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(block, slot, row, (short) (tier - 1));
            
            /**  Cek Size Container */
            switch (cont_size) {
                case CONT_20:
                    /** jika ada data */
                    if (statusCoordinat != null && statusCoordinat[1].equals("exist")) {
                        retValue = true;
                    }
                    break;
                case CONT_40:
                    statusCoordinat2 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(block, (short) (slot + 1), row, (short) (tier - 1));
                    /** jika slot pertama ada data DAN slot kedua ada data */
                    if ((statusCoordinat != null && statusCoordinat[1].equals("exist")) && (statusCoordinat2 != null && statusCoordinat2[1].equals("exist"))) {
                        retValue = true;
                    }
                    break;
                default:
                //do Nothing
            }
        } else {
            retValue = true;
        }
        return retValue;
    }

    //TODO method isCurrentExist
    public Boolean isCurrentExist(){
        return false;
    }

    private MasterYardCoordinatFacadeRemote lookupMasterYardCoordinatFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterYardCoordinatFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterYardCoordinatFacade!com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}

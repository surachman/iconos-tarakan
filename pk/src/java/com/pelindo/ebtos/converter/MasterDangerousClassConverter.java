/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.converter;

import com.pelindo.ebtos.ejb.facade.remote.MasterDangerousClassFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterDangerousClass;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author R Seno Anggoro
 */
@FacesConverter(forClass=MasterDangerousClass.class)
public class MasterDangerousClassConverter implements Converter {
    private String namePattern = "Class #";
    private MasterDangerousClassFacadeRemote masterCommodityFacadeRemote = lookupMasterDangerousClassFacadeRemote();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof MasterDangerousClass) {
            MasterDangerousClass masterDangerousClass = (MasterDangerousClass) value;
            return String.format("%s (%s)", masterDangerousClass.getName(), masterDangerousClass.getDescription());
        }
        return null;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.length() > namePattern.length()) {
            return masterCommodityFacadeRemote.find(value.substring(6, namePattern.length()));
        }
        return null;
    }

    private MasterDangerousClassFacadeRemote lookupMasterDangerousClassFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterDangerousClassFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterDangerousClassFacade!com.pelindo.ebtos.ejb.facade.remote.MasterDangerousClassFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}

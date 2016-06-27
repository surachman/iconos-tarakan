/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.converter;

import com.pelindo.ebtos.ejb.facade.remote.MasterServiceFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterService;
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
@FacesConverter(forClass=MasterService.class)
public class MasterServiceConverter implements Converter {
    private MasterServiceFacadeRemote masterServiceFacadeRemote = lookupMasterServiceFacadeRemote();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof MasterService) {
            return String.valueOf(((MasterService) value).getServiceCode());
        }
        return null;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            return masterServiceFacadeRemote.find(value);
        }
        return null;
    }

    private MasterServiceFacadeRemote lookupMasterServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterServiceFacade!com.pelindo.ebtos.ejb.facade.remote.MasterServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}

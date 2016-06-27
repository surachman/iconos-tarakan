/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.converter;

import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterPort;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
@FacesConverter(forClass=MasterPort.class)
public class MasterPortConverter implements Converter {
    private MasterPortFacadeRemote masterPortFacadeRemote = lookupMasterPortFacadeRemote();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof MasterPort) {
            return String.valueOf(((MasterPort) value).getPortCode());
        }
        return null;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            return masterPortFacadeRemote.find(value);
        }
        return null;
    }

    private MasterPortFacadeRemote lookupMasterPortFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterPortFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterPortFacade!com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}

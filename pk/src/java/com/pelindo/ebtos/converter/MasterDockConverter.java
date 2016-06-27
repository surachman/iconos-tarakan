/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.converter;

import com.pelindo.ebtos.ejb.facade.remote.MasterDockFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterDock;
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
@FacesConverter(forClass=MasterDock.class)
public class MasterDockConverter implements Converter {
    private MasterDockFacadeRemote masterDockFacadeRemote = lookupMasterDockFacadeRemote();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof MasterDock) {
            return String.valueOf(((MasterDock) value).getDockCode());
        }
        return null;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            return masterDockFacadeRemote.find(value);
        }
        return null;
    }

    private MasterDockFacadeRemote lookupMasterDockFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterDockFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterDockFacade!com.pelindo.ebtos.ejb.facade.remote.MasterDockFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.converter;

import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
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
@FacesConverter(forClass=MasterContainerType.class)
public class MasterContainerTypeConverter implements Converter {
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote = lookupMasterCommodityFacadeRemote();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof MasterContainerType) {
            return String.valueOf(((MasterContainerType) value).getContType());
        }
        return null;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            return masterContainerTypeFacadeRemote.find(Integer.parseInt(value));
        }
        return null;
    }

    private MasterContainerTypeFacadeRemote lookupMasterCommodityFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterContainerTypeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterContainerTypeFacade!com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}

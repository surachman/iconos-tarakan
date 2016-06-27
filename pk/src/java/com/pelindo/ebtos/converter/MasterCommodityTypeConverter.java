/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.converter;

import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityTypeFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterCommodityType;
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
@FacesConverter(forClass=MasterCommodityType.class)
public class MasterCommodityTypeConverter implements Converter {
    private MasterCommodityTypeFacadeRemote masterCommodityTypeFacadeRemote = lookupMasterCommodityFacadeRemote();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof MasterCommodityType) {
            return String.valueOf(((MasterCommodityType) value).getCommodityTypeCode());
        }
        return null;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            return masterCommodityTypeFacadeRemote.find(value);
        }
        return null;
    }

    private MasterCommodityTypeFacadeRemote lookupMasterCommodityFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterCommodityTypeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterCommodityTypeFacade!com.pelindo.ebtos.ejb.facade.remote.MasterCommodityTypeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}

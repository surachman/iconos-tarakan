/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.converter;

import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
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
@FacesConverter(forClass=MasterCommodity.class)
public class MasterCommodityConverter implements Converter {
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote = lookupMasterCommodityFacadeRemote();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof MasterCommodity && ((MasterCommodity) value).getCommodityCode() != null) {
            MasterCommodity masterCommodity = (MasterCommodity) value;
            return String.format("%s (%s)", masterCommodity.getName(), masterCommodity.getCommodityCode());
        }
        return null;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.length() > 5) {
            return masterCommodityFacadeRemote.find(value.substring(value.indexOf("(") + 1, value.indexOf(")")));
        }
        return null;
    }

    private MasterCommodityFacadeRemote lookupMasterCommodityFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterCommodityFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterCommodityFacade!com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}

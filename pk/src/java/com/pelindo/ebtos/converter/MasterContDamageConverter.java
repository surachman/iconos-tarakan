/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.converter;

import com.pelindo.ebtos.ejb.facade.remote.MasterContDamageFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterContDamage;
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
@FacesConverter(forClass=MasterContDamage.class)
public class MasterContDamageConverter implements Converter {
    private MasterContDamageFacadeRemote masterCommodityFacadeRemote = lookupMasterContDamageFacadeRemote();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof MasterContDamage) {
            return String.valueOf(((MasterContDamage) value).getId());
        }
        return null;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            return masterCommodityFacadeRemote.find(value);
        }
        return null;
    }

    private MasterContDamageFacadeRemote lookupMasterContDamageFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterContDamageFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterContDamageFacade!com.pelindo.ebtos.ejb.facade.remote.MasterContDamageFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}

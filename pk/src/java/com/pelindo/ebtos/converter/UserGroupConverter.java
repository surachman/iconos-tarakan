/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.converter;

import com.pelindo.ebtos.ejb.facade.remote.MasterUserGroupFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterUserGroup;
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
 * @author senoanggoro
 */
@FacesConverter(value="userGroupConverter")
public class UserGroupConverter implements Converter {
    private MasterUserGroupFacadeRemote masterUserGroupFacade = lookupMasterUserGroupFacadeRemote();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return masterUserGroupFacade.find(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof MasterUserGroup) {
            return ((MasterUserGroup) value).getId().toString();
        }
        else {
            throw new IllegalArgumentException( "Cannot convert non-MasterUserGroup object in userGroupConverter" );
        }
    }

    private MasterUserGroupFacadeRemote lookupMasterUserGroupFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterUserGroupFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterUserGroupFacade!com.pelindo.ebtos.ejb.facade.remote.MasterUserGroupFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}

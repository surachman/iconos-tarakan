/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.qtasnim.persistence;

import com.pelindo.ebtos.security.UserUtil;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.servlet.http.HttpSession;

/**
 *
 * @author senoanggoro
 */
public class AuditTrailEntityListener {
    static final Logger logger = Logger.getLogger(AuditTrailEntityListener.class.getName());

    @PrePersist
    public void prePersist(Object object) {
        Logger.getLogger(getClass().getName()).log(Level.FINE, "prePersist: called");

        if (object instanceof EntityAuditable) {
            EntityAuditable entity = (EntityAuditable) object;
            
            if (entity.getCreatedBy() == null || entity.getCreatedDate() == null) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                String username = null;

                if (facesContext != null && facesContext.getExternalContext() != null) {
                    HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);

                    if (httpSession != null) {
                        username = (String) httpSession.getAttribute("username");
                    } else {
                        username = UserUtil.getCurrentUser();
                    }
                } else {
                    username = UserUtil.getCurrentUser();
                }

                username = username == null ? "unknown" : username;
                
                entity.setCreatedBy(username);
                entity.setCreatedDate(new Date());
            }
        }
        
        Logger.getLogger(getClass().getName()).log(Level.FINE, "prePersist: done");
    }

    @PreUpdate
    public void preUpdate(Object object){
        Logger.getLogger(getClass().getName()).log(Level.FINE, "preUpdate: called");

        if (object instanceof EntityAuditable) {
            EntityAuditable entity = (EntityAuditable) object;

            if (entity.getCreatedBy() == null || entity.getCreatedDate() == null){
                prePersist(object);
            } else {
                String username = null;
                FacesContext facesContext = FacesContext.getCurrentInstance();

                if (facesContext != null && facesContext.getExternalContext() != null) {
                    HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);

                    if (httpSession != null) {
                        username = (String) httpSession.getAttribute("username");
                    } else {
                        username = UserUtil.getCurrentUser();
                    }
                } else {
                    username = UserUtil.getCurrentUser();
                }

                username = username == null ? "unknown" : username;

                entity.setModifiedBy(username);
                entity.setModifiedDate(new Date());
            }
        }

        Logger.getLogger(getClass().getName()).log(Level.FINE, "preUpdate: done");
    }
}
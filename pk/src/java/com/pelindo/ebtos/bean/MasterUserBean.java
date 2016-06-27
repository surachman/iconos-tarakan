/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.ActiveDirectoryFacadeRemote;
import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.PreserviceVesselFacadeRemote;
import com.pelindo.ebtos.model.NamedObject;
import com.pelindo.ebtos.model.db.master.MasterUser;
import com.pelindo.ebtos.model.db.master.MasterUserGroup;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name="masterUserBean")
@ViewScoped
public class MasterUserBean implements Serializable{
    
    ActiveDirectoryFacadeRemote masterUserFacadeRemote = lookupMasterUserFacadeRemote();
    
    private MasterUser entity;
    private String newPassword;
    private Boolean cekId;
    
    private List<MasterUserGroup> groups;

    /** Creates a new instance of MasterVesselBean */
    public MasterUserBean() {
        entity = new MasterUser();
        setGroups(new ArrayList<MasterUserGroup>());
    }

    public void handleEditDeleteButton(ActionEvent event) {
        Number id = (Number) event.getComponent().getAttributes().get("eid");
        entity = masterUserFacadeRemote.find(id.intValue());
        setGroups(new ArrayList<MasterUserGroup>(entity.getGroups()));
        newPassword = entity.getPassword();
    }

    public void handleAdd(ActionEvent event){
        entity = new MasterUser();
        setGroups(new ArrayList<MasterUserGroup>());
        newPassword = entity.getPassword();
    }

    public void DeleteButton(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        masterUserFacadeRemote.remove(entity);
        entity = new MasterUser();
        entity.setGroups(new ArrayList<MasterUserGroup>());
        newPassword = entity.getPassword();
        FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
    }

    public void saveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        
        try {
            if(entity.getPassword() != null)
            {
                masterUserFacadeRemote.edit(entity);
                if(!newPassword.equals(entity.getPassword()))
                {
                    NamedObject no = masterUserFacadeRemote.findUserByUid(entity.getUsername());
                    masterUserFacadeRemote.changePassword(no, newPassword);
                }
            }
            else
            {
                entity.setPassword(newPassword);
                masterUserFacadeRemote.create(entity);
            }
            entity = new MasterUser();
            setGroups(new ArrayList<MasterUserGroup>());
            newPassword = entity.getPassword();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            context.addCallbackParam("loggedIn", true);
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
            context.addCallbackParam("loggedIn", false);
        }
    }

    private PreserviceVesselFacadeRemote lookupPreserviceVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PreserviceVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PreserviceVesselFacade!com.pelindo.ebtos.ejb.facade.remote.PreserviceVesselFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ActiveDirectoryFacadeRemote lookupMasterUserFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ActiveDirectoryFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ActiveDirectoryFacade!com.pelindo.ebtos.ejb.facade.remote.ActiveDirectoryFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public List<Object[]> getEntities() {
        List<Object[]> results = masterUserFacadeRemote.findMasterUsers();
        return results;
    }

    /**
     * @return the vessel
     */
    public MasterUser getEntity() {
        return entity;
    }

    /**
     * @param vessel the vessel to set
     */
    public void setEntity(MasterUser vessel) {
        this.entity = vessel;
    }

    /**
     * @return the cekId
     */
    public Boolean getCekId() {
        if(entity.getUsername() == null)
            cekId = false;
        else
            cekId = true;
        return cekId;
    }

    /**
     * @param cekId the cekId to set
     */
    public void setCekId(Boolean cekId) {
        this.cekId = cekId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    public void setSelectedGroup(MasterUserGroup group) {
        getGroups().add(group);
    }
    
    public void setRemovedGroup(MasterUserGroup group) {
        getGroups().remove(group);
    }
    
    public void handleSelectGroup() {
        entity.setGroups(getGroups());
    }

    public List<MasterUserGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<MasterUserGroup> groups) {
        this.groups = groups;
    }
    
    
}

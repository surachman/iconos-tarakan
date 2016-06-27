/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.MasterRoleFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterUserGroupFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterViewFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterRole;
import com.pelindo.ebtos.model.db.master.MasterUserGroup;
import com.pelindo.ebtos.model.db.master.MasterView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.model.DualListModel;

/**
 *
 * @author senoanggoro
 */
@ManagedBean(name="masterViewBean")
@ViewScoped
public class MasterViewBean implements Serializable {
    MasterRoleFacadeRemote masterRoleFacade = lookupMasterRoleFacadeRemote();
    MasterUserGroupFacadeRemote masterUserGroupFacade = lookupMasterUserGroupFacadeRemote();
    MasterViewFacadeRemote masterViewFacade = lookupMasterViewFacadeRemote();

    private Integer idView;
    private List<MasterView> views;
    private DualListModel<MasterUserGroup> groups;

    /** Creates a new instance of MasterViewBean */
    public MasterViewBean() {
        views = masterViewFacade.findAll();
    }

    public List<MasterView> getViews() {
        return views;
    }

    public DualListModel<MasterUserGroup> getGroups() {
        return groups;
    }

    public void setGroups(DualListModel<MasterUserGroup> groups) {
        this.groups = groups;
    }

    public void handleEditMasterView(ActionEvent event){
        idView = (Integer) event.getComponent().getAttributes().get("masterView");
        groups = new DualListModel<MasterUserGroup>(masterUserGroupFacade.findAllForView(idView), masterUserGroupFacade.findGroupsByView(idView));
    }

    public void handleSubmitRole(ActionEvent event){
        List<MasterRole> roles = new ArrayList();
        if (groups != null){
            MasterView masterView = masterViewFacade.find(idView);
            
            List<MasterRole> objects = masterRoleFacade.findRolesByView(idView);
            for (MasterRole object: objects)
                masterRoleFacade.remove(object);

            for (MasterUserGroup group: groups.getTarget()){
                MasterRole role = new MasterRole();
                role.setMasterUserGroup(group);
                role.setMasterView(masterView);
                roles.add(role);
            }
            
            masterView.setMasterRoleList(roles);
            masterViewFacade.edit(masterView);
            Logger.getLogger(getClass().getName()).log(Level.INFO, "Save roles done");
            return;
        }
        Logger.getLogger(getClass().getName()).log(Level.WARNING, "Save roles failed, groups null");
    }

    private MasterViewFacadeRemote lookupMasterViewFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterViewFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterViewFacade!com.pelindo.ebtos.ejb.facade.remote.MasterViewFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught ", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterUserGroupFacadeRemote lookupMasterUserGroupFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterUserGroupFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterUserGroupFacade!com.pelindo.ebtos.ejb.facade.remote.MasterUserGroupFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught ", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterRoleFacadeRemote lookupMasterRoleFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterRoleFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterRoleFacade!com.pelindo.ebtos.ejb.facade.remote.MasterRoleFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught ", ne);
            throw new RuntimeException(ne);
        }
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "masterActivityBean")
@ViewScoped
public class MasterActivityBean implements Serializable {

    MasterActivityFacadeRemote masterActivityFacade = lookupMasterActivityFacadeRemote();
    private List<Object[]> activityConts = lookupMasterActivityFacadeRemote().findAllActivityExc02();
    private MasterActivity activityCont;
    private Boolean vis = false;

    /** Creates a new instance of MasterActivityBean */
    public MasterActivityBean() {
        activityCont = new MasterActivity();
        vis = false;
    }

    private MasterActivityFacadeRemote lookupMasterActivityFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterActivityFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterActivityFacade!com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * @return the activityConts
     */
    public List<Object[]> getActivityConts() {
        return activityConts;
    }

    /**
     * @param activityConts the activityConts to set
     */
    public void setActivityConts(List<Object[]> activityConts) {
        this.activityConts = activityConts;
    }

    /**
     * @return the activityCont
     */
    public MasterActivity getActivityCont() {
        return activityCont;
    }

    /**
     * @param activityCont the activityCont to set
     */
    public void setActivityCont(MasterActivity activityCont) {
        this.activityCont = activityCont;
    }

    public void handleAdd(ActionEvent event) {
        activityCont = new MasterActivity();
        this.vis = false;
    }

    public void handleSaveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean validate = false;
        if (activityCont.getActivityCode() != null && activityCont.getDescription() != null) {
            validate = true;
            masterActivityFacade.edit(activityCont);
            activityConts = masterActivityFacade.findAllActivityExc02();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } else {
            validate = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        }
        context.addCallbackParam("validated", validate);
    }

    public void handleDelete(ActionEvent event) {        
        try {
            masterActivityFacade.remove(activityCont);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info" ,"application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info" ,"application.delete.failed");
        }
        activityConts = masterActivityFacade.findAllActivityExc02();
    }

    public void handleSelectTable(ActionEvent event) {
        String act_code = (String) event.getComponent().getAttributes().get("act_code");
        activityCont = masterActivityFacade.find(act_code);
        this.vis = true;
    }

    public Boolean getVis() {
        return vis;
    }

    public void setVis(Boolean vis) {
        this.vis = vis;
    }
}

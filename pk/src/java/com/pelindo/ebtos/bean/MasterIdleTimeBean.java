/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterIdletimeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterIdletimetypeFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterIdletime;
import com.pelindo.ebtos.model.db.master.MasterIdletimetype;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author USER
 */
@Named(value = "masterIdleTimeBean")
@RequestScoped
public class MasterIdleTimeBean {

    //@Inject
    //Conversation con;
    @EJB
    private MasterIdletimeFacadeRemote mifr;
    @EJB
    private MasterIdletimetypeFacadeRemote masterIdletimetypeFacadeRemote;
    private MasterIdletime mi;
    private List<MasterIdletime> mis;
    private List<MasterIdletimetype> mittbs;

    /** Creates a new instance of MasterIdleTimeBean */
    public MasterIdleTimeBean() {
        mi = new MasterIdletime();
        mi.setMasterIdletimetype(new MasterIdletimetype());
    }

    public List<MasterIdletimetype> getMittbs() {
        return masterIdletimetypeFacadeRemote.findAll();
    }

    /**
     * @return the mi
     */
    public MasterIdletime getMi() {
        return mi;
    }

    /**
     * @param mi the mi to set
     */
    public void setMi(MasterIdletime mi) {
        this.mi = mi;
    }

    /**
     * @return the mis
     */
    public List<Object[]> getMis() {
        return mifr.findAllNative();
    }

    public void handleOnAdd(ActionEvent event) {
        mi = new MasterIdletime();
    }

    public void handleClick(ActionEvent event) {
        Integer s = (Integer) event.getComponent().getAttributes().get("code");
        mi = mifr.find(s);
        System.out.println(s);
    }

    public void handleSaveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        if (mi.getIdleTimeCode() == null || mi.getName() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else {
            loggedIn = true;
            mifr.edit(mi);
            mis = mifr.findAll();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        }
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleOnDelete(ActionEvent event) {
        try {
            mifr.remove(mi);
            mis = mifr.findAll();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info" ,"application.delete.succeeded");
        } catch (EJBAccessException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info" ,"application.delete.failed");
        }
    }
}

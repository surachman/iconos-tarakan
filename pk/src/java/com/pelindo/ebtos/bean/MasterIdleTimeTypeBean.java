/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterIdletimeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterIdletimetypeFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterIdletimetype;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author USER
 */
@Named(value = "masterIdleTimeTypeBean")
@RequestScoped
public class MasterIdleTimeTypeBean {

    @EJB
    private MasterIdletimetypeFacadeRemote masterIdletimetypeFacadeRemote;
    @EJB
    private MasterIdletimeFacadeRemote masterIdletimeFacadeRemote;

    private MasterIdletimetype mi;
    private List<MasterIdletimetype> mis;
    private List<String> idleTimeTypes;

    /** Creates a new instance of MasterIdleTimeTypeBean */
    public MasterIdleTimeTypeBean() {}

    @PostConstruct
    private void construct() {
        mi = new MasterIdletimetype();
        idleTimeTypes = new ArrayList<String>();
        idleTimeTypes.add(MasterIdletimetype.IT);
        idleTimeTypes.add(MasterIdletimetype.NOT);
        idleTimeTypes.add(MasterIdletimetype.ET);
    }

    public void handleSaveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        if (mi.getIdType() == null || mi.getName() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else {
            loggedIn = true;
            masterIdletimetypeFacadeRemote.edit(mi);
            mis = masterIdletimetypeFacadeRemote.findAll();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        }
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleOnDelete(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (masterIdletimeFacadeRemote.findAllMasterIdletimeByDelete(mi.getIdType()).isEmpty()) {
            masterIdletimetypeFacadeRemote.remove(mi);
            mi = new MasterIdletimetype();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
        }
    }

    public MasterIdletimetype getMi() {
        return mi;
    }

    public void setMi(MasterIdletimetype mi) {
        this.mi = mi;
    }

    public List<MasterIdletimetype> getMis() {
        return masterIdletimetypeFacadeRemote.findAll();
    }

    public void setMis(List<MasterIdletimetype> mis) {
        this.mis = mis;
    }

    public List<String> getIdleTimeTypes() {
        return idleTimeTypes;
    }

    public void setIdleTimeTypes(List<String> idleTimeTypes) {
        this.idleTimeTypes = idleTimeTypes;
    }

    public void handleOnAdd(ActionEvent event) {
        mi = new MasterIdletimetype();
    }

    public void handleClick(ActionEvent event) {
        Integer s = (Integer) event.getComponent().getAttributes().get("code");
        mi = masterIdletimetypeFacadeRemote.find(s);
    }
}

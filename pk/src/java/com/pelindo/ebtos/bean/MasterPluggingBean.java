/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterPluggingReeferFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterPluggingReefer;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author wulan
 */
@ManagedBean(name = "masterPluggingBean")
@ViewScoped
public class MasterPluggingBean implements Serializable {

    @EJB
    MasterPluggingReeferFacadeRemote masterPluggingReeferFacadeRemote;
    private MasterPluggingReefer masterPluggingReefer;
    private List<Object[]> plugingReeres;
    private boolean disable = Boolean.FALSE;

    /** Creates a new instance of MasterPluggingBean */
    public MasterPluggingBean() {
    }

    @PostConstruct
    private void construct() {
        masterPluggingReefer = new MasterPluggingReefer();
        plugingReeres = masterPluggingReeferFacadeRemote.findPluggingCodeList();
    }

    public void handleAddButton(ActionEvent event) {
        masterPluggingReefer = new MasterPluggingReefer();
        this.disable = Boolean.FALSE;
    }

    public void handleDeleteButton(ActionEvent event) {
        String plugCode = (String) event.getComponent().getAttributes().get("plugCode");
        masterPluggingReefer = masterPluggingReeferFacadeRemote.find(plugCode);
        this.disable = Boolean.TRUE;
    }

    public void saveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean validate = false;
        try {
            validate = true;
            masterPluggingReeferFacadeRemote.edit(masterPluggingReefer);
            plugingReeres = masterPluggingReeferFacadeRemote.findPluggingCodeList();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (EJBException e) {
            e.printStackTrace();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        }
        context.addCallbackParam("validated", validate);
    }

    public void handleDelete(ActionEvent event) {
        try {
            masterPluggingReeferFacadeRemote.remove(masterPluggingReefer);
            plugingReeres = masterPluggingReeferFacadeRemote.findPluggingCodeList();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
        }
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public MasterPluggingReefer getMasterPluggingReefer() {
        return masterPluggingReefer;
    }

    public void setMasterPluggingReefer(MasterPluggingReefer masterPluggingReefer) {
        this.masterPluggingReefer = masterPluggingReefer;
    }

    public List<Object[]> getPlugingReeres() {
        return plugingReeres;
    }

    public void setPlugingReeres(List<Object[]> plugingReeres) {
        this.plugingReeres = plugingReeres;
    }
}

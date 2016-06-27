/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterGrossParameterFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterGrossParameter;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author arie
 */
@ManagedBean(name = "masterGrossParameterBean")
@ViewScoped
public class MasterGrossParameterBean {

    @EJB
    MasterGrossParameterFacadeRemote masterGrossParameterFacadeRemote;
    private List<MasterGrossParameter> masterGrossParameters;
    private MasterGrossParameter masterGrossParameter;
    private String idGross;
    private boolean delete;
    private boolean disable;

    /** Creates a new instance of MasterGrossParameterBean */
    public MasterGrossParameterBean() {
        masterGrossParameter = new MasterGrossParameter();
        disable = false;
    }

    @PostConstruct
    private void construct(){
        masterGrossParameters = masterGrossParameterFacadeRemote.findAll();
    }

    public void handleAddButton(ActionEvent event) {
        masterGrossParameter = new MasterGrossParameter();
        disable = false;
    }

    public void handleEditButton(ActionEvent event) {
        idGross = (String) event.getComponent().getAttributes().get("idGross");
        masterGrossParameter = masterGrossParameterFacadeRemote.find(idGross);
        disable = true;
    }

    public void saveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean validate = false;

        if (masterGrossParameter.getGrossCode() != null) {
            validate = true;
            masterGrossParameterFacadeRemote.edit(masterGrossParameter);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            masterGrossParameters = masterGrossParameterFacadeRemote.findAll();
        } else {
            validate = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        }
        context.addCallbackParam("validated", validate);
    }

    public void handleDelete(ActionEvent event) {
        try {
            masterGrossParameterFacadeRemote.remove(masterGrossParameter);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
        }
        masterGrossParameters = masterGrossParameterFacadeRemote.findAll();
    }

    /**
     * @return the masterGrossParameters
     */
    public List<MasterGrossParameter> getMasterGrossParameters() {
        return masterGrossParameters;
    }

    /**
     * @param masterGrossParameters the masterGrossParameters to set
     */
    public void setMasterGrossParameters(List<MasterGrossParameter> masterGrossParameters) {
        this.masterGrossParameters = masterGrossParameters;
    }

    /**
     * @return the masterGrossParameter
     */
    public MasterGrossParameter getMasterGrossParameter() {
        return masterGrossParameter;
    }

    /**
     * @param masterGrossParameter the masterGrossParameter to set
     */
    public void setMasterGrossParameter(MasterGrossParameter masterGrossParameter) {
        this.masterGrossParameter = masterGrossParameter;
    }

    /**
     * @return the idGross
     */
    public String getIdGross() {
        return idGross;
    }

    /**
     * @param idGross the idGross to set
     */
    public void setIdGross(String idGross) {
        this.idGross = idGross;
    }

    /**
     * @return the delete
     */
    public boolean isDelete() {
        return delete;
    }

    /**
     * @param delete the delete to set
     */
    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    /**
     * @return the disable
     */
    public boolean isDisable() {
        return disable;
    }

    /**
     * @param disable the disable to set
     */
    public void setDisable(boolean disable) {
        this.disable = disable;
    }

}

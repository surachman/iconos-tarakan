/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.BaplieDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityTypeFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterCommodityType;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dycoder
 */
@Named(value = "masterCommodityBean")
@RequestScoped
public class MasterCommodityBean {

    @EJB
    MasterCommodityTypeFacadeRemote MasterCommodityTypeFacadeRemote;
    @EJB
    MasterCommodityFacadeRemote masterCommodityFacadeRemote;
    @EJB
    BaplieDischargeFacadeRemote planningBayplanDischargeFacadeRemote;
    private MasterCommodityType masterCommodityType;
    private MasterCommodity masterCommodity;
    private List<MasterCommodityType> masterCommodityTypes = lookupMasterCommodityTypeFacadeRemote().findAll();
    private Boolean cekId;

    /** Creates a new instance of MasterCommodityBean */
    public MasterCommodityBean() {
        masterCommodity = new MasterCommodity();
        masterCommodityType = new MasterCommodityType();
        masterCommodity.setMasterCommodityType(new MasterCommodityType());
    }

    public void handleAdd(ActionEvent event) {
        masterCommodity = new MasterCommodity();
        masterCommodityType = new MasterCommodityType();
        masterCommodity.setMasterCommodityType(new MasterCommodityType());
    }

    public String getCommodityCode() {
        String temp = masterCommodityFacadeRemote.findMasterCommodityByGenerate();
        Integer tempCode = 1 + Integer.parseInt(temp);
        String comCod = "";
        temp = String.valueOf(tempCode);
        switch (temp.length()) {
            case 1:
                temp = "00" + temp;
                break;
            case 2:
                temp = "0" + temp;
                break;
        }
        comCod = temp;
        return comCod;
    }

    //GENERATE MASTER Commodity Code
    public void saveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        if (masterCommodity.getCommodityCode() == null || masterCommodity.getName() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        } else {
            loggedIn = true;
            if (masterCommodity.getCommodityCode().equals("")) {
                masterCommodity.setCommodityCode(getCommodityCode());
            }
            masterCommodityFacadeRemote.edit(masterCommodity);
            masterCommodity = new MasterCommodity();
            masterCommodityType = new MasterCommodityType();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        }
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleEditDeleteButton(ActionEvent event) {
        String commodity_code = (String) event.getComponent().getAttributes().get("commodity_code");
        masterCommodity = masterCommodityFacadeRemote.find(commodity_code);
    }

    public void delete(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (planningBayplanDischargeFacadeRemote.findBaplieDischargesByCode(masterCommodity.getCommodityCode()).isEmpty()) {
            masterCommodityFacadeRemote.remove(masterCommodity);
            masterCommodity = new MasterCommodity();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.failed");
        }

    }

    private MasterCommodityTypeFacadeRemote lookupMasterCommodityTypeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterCommodityTypeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterCommodityTypeFacade!com.pelindo.ebtos.ejb.facade.remote.MasterCommodityTypeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public List<Object[]> getMasterCommoditys() {
        return masterCommodityFacadeRemote.findMasterCommoditys();
    }

    /**
     * @return the masterCommodityType
     */
    public MasterCommodityType getMasterCommodityType() {
        return masterCommodityType;
    }

    /**
     * @param masterCommodityType the masterCommodityType to set
     */
    public void setMasterCommodityType(MasterCommodityType masterCommodityType) {
        this.masterCommodityType = masterCommodityType;
    }

    /**
     * @return the masterCommodity
     */
    public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    /**
     * @param masterCommodity the masterCommodity to set
     */
    public void setMasterCommodity(MasterCommodity masterCommodity) {
        this.masterCommodity = masterCommodity;
    }

    /**
     * @return the masterCommodityTypes
     */
    public List<MasterCommodityType> getMasterCommodityTypes() {
        return masterCommodityTypes;
    }

    /**
     * @param masterCommodityTypes the masterCommodityTypes to set
     */
    public void setMasterCommodityTypes(List<MasterCommodityType> masterCommodityTypes) {
        this.masterCommodityTypes = masterCommodityTypes;
    }

    /**
     * @return the cekId
     */
    public Boolean getCekId() {
        if (masterCommodity.getCommodityCode() == null) {
            cekId = false;
        } else {
            cekId = true;
        }
        return cekId;
    }

    /**
     * @param cekId the cekId to set
     */
    public void setCekId(Boolean cekId) {
        this.cekId = cekId;
    }
}

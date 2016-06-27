/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "planningContReceivingBean")
@ViewScoped
public class PlanningContReceivingBean implements Serializable {

    @EJB
    PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    PlanningContReceivingFacadeRemote planningContReceivingFacadeRemote;
    @EJB
    MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    MasterCommodityFacadeRemote masterCommodityFacadeRemote;

    private List<Object[]> planningVessels;
    private Object[] planningVessel;
    private PlanningVessel vessel;
    private String no_ppkb;
    private String noPPKB;
    private List<PlanningContReceiving> planningContReceivings;
    private PlanningContReceiving planningContReceiving;
    private String opsi;
    private boolean disableFinish;

    /** Creates a new instance of BaplieLoadBean */
    public PlanningContReceivingBean() {
    }

    @PostConstruct
    private void construct() {
        planningContReceiving = new PlanningContReceiving();
        vessel = new PlanningVessel();
        disableFinish = true;
    }

    public void handleNewPpkb(ActionEvent event){
        planningVessels = planningVesselFacadeRemote.findPlanningVesselsCy();
    }

    public void clearSave() {
        planningContReceiving = new PlanningContReceiving();
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        setNo_ppkb((String) event.getComponent().getAttributes().get("noPpkb"));
        noPPKB = (String) event.getComponent().getAttributes().get("noPpkb");
        vessel = new PlanningVessel();
        vessel = planningVesselFacadeRemote.find(noPPKB);

        if (vessel != null) {
            disableFinish = vessel.getCheckBaplie() == "TRUE" ? true : false;
        }
        setPlanningVessel(planningVesselFacadeRemote.findPlanningVesselByPpkb(getNo_ppkb()));
        planningContReceivings = planningContReceivingFacadeRemote.findByNoPpkb(getNo_ppkb());
    }

    public void handleSelectCommodity() {
        planningContReceiving.setDg("FALSE");

        if (planningContReceiving.getMasterCommodity() != null && planningContReceiving.getMasterCommodity().getMasterDangerousClass() != null) {
            planningContReceiving.setDg("TRUE");
        }
    }

    public void onChangeConstraintType(ValueChangeEvent event) {
        int newItem = (Integer) event.getNewValue();
        MasterContainerType mct = masterContainerTypeFacadeRemote.find(newItem);
        planningContReceiving.setMasterContainerType(mct);
        planningContReceiving.setContSize(mct.getFeetMark());
    }

    public void handleEditDeleteButton(ActionEvent event) {
        Integer id = (Integer) event.getComponent().getAttributes().get("idCont");
        planningContReceiving = planningContReceivingFacadeRemote.find(id);
        //System.out.println("sds" + planningContReceiving.getMasterCommodity().getName());
        opsi = "delete";
    }

    public void saveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        try {
            loggedIn = true;
            planningContReceiving.setIsCompleted("TRUE");
            planningContReceivingFacadeRemote.edit(planningContReceiving);
            planningContReceivings = planningContReceivingFacadeRemote.findByNoPpkb(getNo_ppkb());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            context.addCallbackParam("loggedIn", loggedIn);
            this.clearSave();
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
            ex.printStackTrace();
        }
    }

    public void delete(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (opsi.equalsIgnoreCase("delete")) {
                planningContReceivingFacadeRemote.remove(planningContReceiving);
            } else {
                planningContReceivings = planningContReceivingFacadeRemote.findByNoPpkb(getNo_ppkb());
                for (PlanningContReceiving row: planningContReceivings) {
                    planningContReceivingFacadeRemote.remove(row);
                }
            }
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            planningContReceivings = planningContReceivingFacadeRemote.findByNoPpkb(getNo_ppkb());
            this.clearSave();
        } catch (RuntimeException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
        }
    }

    public void handleDeleteAll(ActionEvent event) {
        setOpsi("deleteall");
    }

    public void handleButtonFinish() {
        FacesContext context = FacesContext.getCurrentInstance();
        vessel = new PlanningVessel();
        vessel = planningVesselFacadeRemote.find(noPPKB);
        vessel.setCheckBaplie("TRUE");
        planningVesselFacadeRemote.edit(vessel);
        disableFinish = vessel.getCheckBaplie() == "TRUE" ? true : false;
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.transaction.succeeded");
    }

    /**
     * @return the planningVessels
     */
    public List<Object[]> getPlanningVessels() {
        return planningVessels;
    }

    /**
     * @param planningVessels the planningVessels to set
     */
    public void setPlanningVessels(List<Object[]> planningVessels) {
        this.planningVessels = planningVessels;
    }

    /**
     * @return the planningVessel
     */
    public Object[] getPlanningVessel() {
        return planningVessel;
    }

    /**
     * @param planningVessel the planningVessel to set
     */
    public void setPlanningVessel(Object[] planningVessel) {
        this.planningVessel = planningVessel;
    }

    /**
     * @return the no_ppkb
     */
    public String getNo_ppkb() {
        return no_ppkb;
    }

    /**
     * @param no_ppkb the no_ppkb to set
     */
    public void setNo_ppkb(String no_ppkb) {
        this.no_ppkb = no_ppkb;
    }

    /**
     * @return the planningContReceivings
     */
    public List<PlanningContReceiving> getPlanningContReceivings() {
        return planningContReceivings;
    }

    /**
     * @param planningContReceivings the planningContReceivings to set
     */
    public void setPlanningContReceivings(List<PlanningContReceiving> planningContReceivings) {
        this.planningContReceivings = planningContReceivings;
    }

    /**
     * @return the planningContReceiving
     */
    public PlanningContReceiving getPlanningContReceiving() {
        return planningContReceiving;
    }

    /**
     * @param planningContReceiving the planningContReceiving to set
     */
    public void setPlanningContReceiving(PlanningContReceiving planningContReceiving) {
        this.planningContReceiving = planningContReceiving;
    }

    /**
     * @param listMasterCommodity the listMasterCommodity to set
     */
    public void setListMasterCommodity(List<MasterCommodity> listMasterCommodity) {
        this.setListMasterCommodity(listMasterCommodity);
    }

    /**
     * @return the opsi
     */
    public String getOpsi() {
        return opsi;
    }

    /**
     * @param opsi the opsi to set
     */
    public void setOpsi(String opsi) {
        this.opsi = opsi;
    }

    /**
     * @return the disableFinish
     */
    public boolean isDisableFinish() {
        return disableFinish;
    }
}

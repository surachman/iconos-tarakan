/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.PlanningContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningShiftDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningTransDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name="dischargeBean")
@ViewScoped
public class DischargeBean implements Serializable{

    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacadeRemote;
    @EJB
    private PlanningTransDischargeFacadeRemote planningTransDischargeFacade;
    @EJB
    private PlanningShiftDischargeFacadeRemote planningShiftDischargeFacade;
    @EJB
    private PlanningContDischargeFacadeRemote planningContDischargeFacade;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacade;

    private List<Object[]> planningVessels ;
    private List<Object[]> planningContDischarges;
    private List<Object[]> planningTransDischarges;
    private List<Object[]> planningShiftDischarges;
    private List<Object[]> planningUncontainerizeds;
    private Object[] planningVessel;
    private String noPpkb;
    private String mode;

    /** Creates a new instance of DischargeBean */
    public DischargeBean() {}

    @PostConstruct
    private void construct() {
        planningVessel = new Object[4];
    }

    public void handleShowPlanningVessels(ActionEvent event){
        planningVessels = planningVesselFacade.findPlanningVesselApprovalReady();
    }
    
    //penambahan untuk menampilkan semua no ppkb untuk discharge list by ade chelsea tgl 23 april 2014
    public void handleShowPlanningVessels2(ActionEvent event){
        planningVessels = planningVesselFacade.findPlanningVesselApprovalReady2();
    }

    public void handleSelectNoPpkb(ActionEvent event){
        noPpkb = (String) event.getComponent().getAttributes().get("noPpkb");
        planningVessel = planningVesselFacade.findPlanningVesselByPpkb(noPpkb);
        planningContDischarges = planningContDischargeFacade.findPlanningContDischargeListNative(noPpkb);
        planningTransDischarges = planningTransDischargeFacade.findPlanningTransDischargeListNative(noPpkb);
        planningShiftDischarges = planningShiftDischargeFacade.findPlanningShiftDischargeListNative(noPpkb);
        planningUncontainerizeds = uncontainerizedServiceFacadeRemote.findByPpkbOperation(noPpkb, "DISCHARGE");
    }

    public void handleDownloadTransactionRecap(ActionEvent event){
        RequestContext context = RequestContext.getCurrentInstance();
        mode = (String) event.getComponent().getAttributes().get("mode");
        context.addCallbackParam("doPrint", true);
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../discharge.pdf?mode=" + mode + "&no_ppkb=" + noPpkb + ""));
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
     * @return the planningContDischarges
     */
    public List<Object[]> getPlanningContDischarges() {
        return planningContDischarges;
    }

    /**
     * @param planningContDischarges the planningContDischarges to set
     */
    public void setPlanningContDischarges(List<Object[]> planningContDischarges) {
        this.planningContDischarges = planningContDischarges;
    }

    /**
     * @return the planningTransDischarges
     */
    public List<Object[]> getPlanningTransDischarges() {
        return planningTransDischarges;
    }

    /**
     * @param planningTransDischarges the planningTransDischarges to set
     */
    public void setPlanningTransDischarges(List<Object[]> planningTransDischarges) {
        this.planningTransDischarges = planningTransDischarges;
    }

    /**
     * @return the planningShiftDischarges
     */
    public List<Object[]> getPlanningShiftDischarges() {
        return planningShiftDischarges;
    }

    /**
     * @param planningShiftDischarges the planningShiftDischarges to set
     */
    public void setPlanningShiftDischarges(List<Object[]> planningShiftDischarges) {
        this.planningShiftDischarges = planningShiftDischarges;
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
     * @return the planningUncontainerizeds
     */
    public List<Object[]> getPlanningUncontainerizeds() {
        return planningUncontainerizeds;
    }

    /**
     * @param planningUncontainerizeds the planningUncontainerizeds to set
     */
    public void setPlanningUncontainerizeds(List<Object[]> planningUncontainerizeds) {
        this.planningUncontainerizeds = planningUncontainerizeds;
    }

}

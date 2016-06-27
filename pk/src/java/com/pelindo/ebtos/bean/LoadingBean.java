/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
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
@ManagedBean(name="loadingBean")
@ViewScoped
public class LoadingBean implements Serializable{
    @EJB
    private PlanningContLoadFacadeRemote planningContLoadFacade;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacade;
    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacade;

    private Object[] planningVessel;
    private String no_ppkb;
    private String mode;
    private List<Object[]> planningVessels;
    private List<Object[]> planningContLoads;
    private List<Object[]> planningUncontainerizeds;

    /** Creates a new instance of LoadingBean */
    public LoadingBean() {}

    @PostConstruct
    private void construct() {
        planningVessel = new Object[4];
    }

    public void handleShowPlanningVessels(ActionEvent event){
        planningVessels = planningVesselFacade.findPlanningVesselByStatusAppReadyServicing();
    }

    public void handleSelectNoPpkb(ActionEvent event){
        no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        planningVessel = planningVesselFacade.findPlanningVesselByPpkb(no_ppkb);
        planningContLoads = planningContLoadFacade.findLoadingListByPpkb(no_ppkb);
        planningUncontainerizeds = uncontainerizedServiceFacade.findByPpkbOperation(no_ppkb, "LOAD");
    }

    public void handleDownloadTransactionRecap(ActionEvent event){
        RequestContext context = RequestContext.getCurrentInstance();
        mode = (String) event.getComponent().getAttributes().get("mode");
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../discharge.pdf?mode=" + mode + "&no_ppkb=" + no_ppkb + ""));
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
     * @return the planningContLoads
     */
    public List<Object[]> getPlanningContLoads() {
        return planningContLoads;
    }

    /**
     * @param planningContLoads the planningContLoads to set
     */
    public void setPlanningContLoads(List<Object[]> planningContLoads) {
        this.planningContLoads = planningContLoads;
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

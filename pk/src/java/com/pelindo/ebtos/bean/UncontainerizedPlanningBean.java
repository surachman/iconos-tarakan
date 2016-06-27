/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterYard;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "uncontainerizedPlanningBean")
@ViewScoped
public class UncontainerizedPlanningBean implements Serializable {
    private String implementedPortCode;
    @EJB
    private MasterPortFacadeRemote masterPortFacadeRemote;
    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacadeRemote;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacadeRemote;

    private List<Object[]> masterYards;
    private List<Object[]> masterPorts;
    private List<Object[]> planningVessels;
    private List<Object[]> uncontainerizedServices;
    private List<Object[]> uncontainerizedServicesLoad;

    private UncontainerizedService uncontainerizedService;
    private MasterCommodity masterCommodity;
    private MasterYard masterYard;
    private Object[] planningVessel;

    private String comodity;
    String selectedPort1, selectedPort2;

    public UncontainerizedPlanningBean() {}

    @PostConstruct
    private void construct() {
        planningVessel = new Object[1];
        masterCommodity = new MasterCommodity();
        masterYard = new MasterYard();
        uncontainerizedService = new UncontainerizedService();
        
        implementedPortCode = masterSettingAppFacadeRemote.findImplementedPortCode();
    }

    public void handleNewPpkb(ActionEvent event){
         planningVessels = planningVesselFacadeRemote.findPlanningVesselApproval();
    }

    public void handleAdd(ActionEvent event){
        comodity = "";
        masterYards = masterYardFacadeRemote.findMasterYards();
//        masterPorts = masterPortFacadeRemote.findAllNative();

        uncontainerizedService = new UncontainerizedService();
        uncontainerizedService.setOperation("DISCHARGE");
        uncontainerizedService.setDischPort(implementedPortCode);
        
        selectedPort1 = "";
        selectedPort2 = masterPortFacadeRemote.find(implementedPortCode).getName();

        if (!masterYards.isEmpty()) {
            uncontainerizedService.setBlock((String) masterYards.get(0)[0]);
        }
    }

    public void handleAddLoad(ActionEvent event){
        comodity = "";
        masterYards = masterYardFacadeRemote.findMasterYards();
//        masterPorts = masterPortFacadeRemote.findAllNative();
        
        uncontainerizedService = new UncontainerizedService();
        uncontainerizedService.setOperation("LOAD");
        uncontainerizedService.setLoadPort(implementedPortCode);
        
        selectedPort1 = masterPortFacadeRemote.find(implementedPortCode).getName();
        selectedPort2 = "";

        if (!masterYards.isEmpty()) {
            uncontainerizedService.setBlock((String) masterYards.get(0)[0]);
        }
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        String no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        planningVessel= planningVesselFacadeRemote.findPlanningVesselByPpkb(no_ppkb);
        uncontainerizedServices = uncontainerizedServiceFacadeRemote.findByPpkbPlanning(no_ppkb, "DISCHARGE");
        uncontainerizedServicesLoad = uncontainerizedServiceFacadeRemote.findByPpkbPlanning(no_ppkb, "LOAD");
    }

    public void handleEditDelete(ActionEvent event) {
        Integer idd = (Integer) event.getComponent().getAttributes().get("idd");
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(idd);
        String portCode1 = uncontainerizedService.getLoadPort();

        selectedPort1 = (String)masterPortFacadeRemote.findNameByCode(portCode1);
        String portCode2 = uncontainerizedService.getDischPort();
        selectedPort2 = (String)masterPortFacadeRemote.findNameByCode(portCode2);
        masterYards = masterYardFacadeRemote.findMasterYards();
//        masterPorts = masterPortFacadeRemote.findAllNative();
    }

    public void handleSelectCommodity(ActionEvent event) {
        String commodity_code = (String) event.getComponent().getAttributes().get("cust_code");
        MasterCommodity commodity = new MasterCommodity();
        commodity = masterCommodityFacadeRemote.find(commodity_code);
        comodity = commodity.getName();
        uncontainerizedService.setCommodity(commodity_code);
    }

    public void handleSave(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        Boolean isValid = false;

        try {
            uncontainerizedService.setNoPpkb((String) planningVessel[0]);
            uncontainerizedService.setCrane("L");
            uncontainerizedService.setStatus("PLANNING");
            uncontainerizedService.setIsDelivery("FALSE");
            uncontainerizedService.setIsShifting("FALSE");
            uncontainerizedService.setPlacementDate(null);
            
            if (uncontainerizedService.getIdUc() == null) {
                UncontainerizedService otherUc = uncontainerizedServiceFacadeRemote.findByNoPpkbAndBlNo(uncontainerizedService.getNoPpkb(), uncontainerizedService.getBlNo());

                if (otherUc != null) {
                    requestContext.addCallbackParam("isValid", isValid);
                    FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
                    return;
                }
            }
                MasterPort port1 = masterPortFacadeRemote.findMasterPortByName(selectedPort1);
                uncontainerizedService.setLoadPort(port1.getPortCode());
                MasterPort port2 = masterPortFacadeRemote.findMasterPortByName(selectedPort2);
                uncontainerizedService.setDischPort(port2.getPortCode());

            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            isValid = true;
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
        }

        uncontainerizedServices = uncontainerizedServiceFacadeRemote.findByPpkbPlanning((String) planningVessel[0], "DISCHARGE");
        uncontainerizedServicesLoad = uncontainerizedServiceFacadeRemote.findByPpkbPlanning((String) planningVessel[0], "LOAD");
        requestContext.addCallbackParam("isValid", isValid);
    }

    public void delete(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        uncontainerizedServiceFacadeRemote.remove(uncontainerizedService);
        uncontainerizedServices = uncontainerizedServiceFacadeRemote.findByPpkbPlanning((String) planningVessel[0], "DISCHARGE");
        uncontainerizedServicesLoad = uncontainerizedServiceFacadeRemote.findByPpkbPlanning((String) planningVessel[0], "LOAD");
        FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
    }

    public List<Object[]> getMasterCommoditys() {
        return masterCommodityFacadeRemote.findMasterCommoditys();
    }

    public String getSelectedPort1() {
        return selectedPort1;
    }

    public void setSelectedPort1(String selectedPort1) {
        this.selectedPort1 = selectedPort1;
    }

    public String getSelectedPort2() {
        return selectedPort2;
    }

    public void setSelectedPort2(String selectedPort2) {
        this.selectedPort2 = selectedPort2;
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
     * @return the uncontainerizedServices
     */
    public List<Object[]> getUncontainerizedServices() {
        return uncontainerizedServices;
    }

    /**
     * @param uncontainerizedServices the uncontainerizedServices to set
     */
    public void setUncontainerizedServices(List<Object[]> uncontainerizedServices) {
        this.uncontainerizedServices = uncontainerizedServices;
    }

    /**
     * @return the uncontainerizedService
     */
    public UncontainerizedService getUncontainerizedService() {
        return uncontainerizedService;
    }

    /**
     * @param uncontainerizedService the uncontainerizedService to set
     */
    public void setUncontainerizedService(UncontainerizedService uncontainerizedService) {
        this.uncontainerizedService = uncontainerizedService;
    }

    /**
     * @return the masterYards
     */
    public List<Object[]> getMasterYards() {
        return masterYards;
    }

    /**
     * @param masterYards the masterYards to set
     */
    public void setMasterYards(List<Object[]> masterYards) {
        this.masterYards = masterYards;
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
     * @return the MasterYard
     */
    public MasterYard getMasterYard() {
        return masterYard;
    }

    /**
     * @param MasterYard the MasterYard to set
     */
    public void setMasterYard(MasterYard masterYard) {
        this.masterYard = masterYard;
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
     * @return the masterPorts
     */
    public List<Object[]> getMasterPorts() {
        return masterPorts;
    }

    /**
     * @param masterPorts the masterPorts to set
     */
    public void setMasterPorts(List<Object[]> masterPorts) {
        this.masterPorts = masterPorts;
    }

    /**
     * @return the uncontainerizedServicesLoad
     */
    public List<Object[]> getUncontainerizedServicesLoad() {
        return uncontainerizedServicesLoad;
    }

    /**
     * @param uncontainerizedServicesLoad the uncontainerizedServicesLoad to set
     */
    public void setUncontainerizedServicesLoad(List<Object[]> uncontainerizedServicesLoad) {
        this.uncontainerizedServicesLoad = uncontainerizedServicesLoad;
    }

    /**
     * @return the comodity
     */
    public String getComodity() {
        if(uncontainerizedService.getCommodity() != null){
            MasterCommodity commodity = new MasterCommodity();
            commodity = masterCommodityFacadeRemote.find(uncontainerizedService.getCommodity());
            comodity = commodity.getName();
        }
        return comodity;
    }

    /**
     * @param comodity the comodity to set
     */
    public void setComodity(String comodity) {
        this.comodity = comodity;
    }
}

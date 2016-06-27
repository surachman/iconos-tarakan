/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.local.PlanningContLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceContLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.exception.DeleteNotDeletableContainerException;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "updateLiftOnBean")
@ViewScoped
public class UpdateLiftOnBean implements Serializable {
    @EJB
    private ServiceContLoadFacadeRemote serviceContLoadFacadeRemote;
    @EJB
    private PlanningContLoadFacadeRemote planningContLoadFacadeRemote;
    @EJB
    private ServiceReceivingFacadeRemote serviceReceivingFacadeRemote;

    private List<ServiceReceiving> serviceReceivings;
    ServiceReceiving serviceReceiving;

    private String noPPKB;
    private String contNo;

    @PostConstruct
    private void construct() {
        noPPKB = null;
        contNo = null;
        serviceReceivings = new ArrayList<ServiceReceiving>();
    }

    public List<String> handleFilterByNoPpkb(String noPpkb) {
        List<String> results = serviceReceivingFacadeRemote.findLikeNoPPKB(noPpkb);
        return results;
    }

    public List<String> handleFilterByNoPpkbContNo(String contNo) {
        List<String> results = serviceReceivingFacadeRemote.findLikeContNo(noPPKB, contNo);
        return results;
    }

    public void onSelectPPKB(SelectEvent event){
        noPPKB = event.getObject().toString();
        contNo = null;
        serviceReceivings.clear();
    }

    public void handleEdit(ActionEvent event) {
        int idPK = (Integer) event.getComponent().getAttributes().get("idPK");
        serviceReceiving = serviceReceivingFacadeRemote.find(idPK);
    }

    public void onSearch() {
        serviceReceivings = serviceReceivingFacadeRemote.findByNoPpkbAndContNoUpdateLiftOn(noPPKB, contNo);
        for(ServiceReceiving obj : serviceReceivings){
            System.out.println("********** ServiceReceiving **********");
            System.out.println("Block : " + obj.getMasterYard().getBlock());
            System.out.println("YSlot : " + obj.getYSlot());
            System.out.println("YRow : " + obj.getYRow());
            System.out.println("YTier : " + obj.getYTier());
//            System.out.println("********** PlanningContLoad **********");
//            PlanningContLoad cl = serviceReceiving.getPlanningContLoad();
//            System.out.println("YSlot : " + cl.getYSlot());
//            System.out.println("YRow : " + cl.getYRow());
//            System.out.println("YTier : " + cl.getYTier());
            System.out.println("**********  **********");

        }
    }

    public void handleSave(ActionEvent event) {
        boolean loggedIn = false;
        try{
        System.out.println("handleSave");
        System.out.println("noPPKB : " + noPPKB);
        System.out.println("contNo : " + contNo);
        System.out.println("Block : " + serviceReceiving.getMasterYard().getBlock());

        ServiceContLoad serviceContLoad = serviceContLoadFacadeRemote.findByNoPpkbAndContNoUpdateLiftOn(noPPKB, contNo);
        System.out.println("serviceContLoad : " + serviceContLoad.getMasterYard().getBlock());
        if (serviceContLoad != null) {
            serviceContLoad.getMasterYard().setBlock(serviceReceiving.getMasterYard().getBlock());
            serviceContLoad.setYSlot(serviceReceiving.getYSlot());
            serviceContLoad.setYRow(serviceReceiving.getYRow());
            serviceContLoad.setYTier(serviceReceiving.getYTier());
            serviceContLoadFacadeRemote.edit(serviceContLoad);
        }
        System.out.println("new serviceContLoad : " + serviceContLoad.getMasterYard().getBlock());
        PlanningContLoad planningContLoad = planningContLoadFacadeRemote.findByNoPpkbAndContNoUpdateLiftOn(noPPKB, contNo);
        System.out.println("planningContLoad : " + planningContLoad.getMasterYard().getBlock());
        if (planningContLoad != null) {
            planningContLoad.getMasterYard().setBlock(serviceReceiving.getMasterYard().getBlock());
            planningContLoad.setYSlot(serviceReceiving.getYSlot());
            planningContLoad.setYRow(serviceReceiving.getYRow());
            planningContLoad.setYTier(serviceReceiving.getYTier());
            planningContLoadFacadeRemote.edit(planningContLoad);
        }
        System.out.println("new planningContLoad : " + planningContLoad.getMasterYard().getBlock());

        serviceReceivingFacadeRemote.edit(serviceReceiving);

        serviceReceivings = serviceReceivingFacadeRemote.findByNoPpkbAndContNoUpdateLiftOn(noPPKB, contNo);
        loggedIn = true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.save.succeeded");
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public List<ServiceReceiving> getServiceReceivings() {
        return serviceReceivings;
    }

    public void setServiceReceivings(List<ServiceReceiving> serviceReceivings) {
        this.serviceReceivings = serviceReceivings;
    }
    
    public String getNoPPKB() {
        return noPPKB;
    }

    public void setNoPPKB(String noPPKB) {
        this.noPPKB = noPPKB;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public ServiceReceiving getServiceReceiving() {
        return serviceReceiving;
    }

    public void setServiceReceiving(ServiceReceiving serviceReceiving) {
        this.serviceReceiving = serviceReceiving;
    }
}

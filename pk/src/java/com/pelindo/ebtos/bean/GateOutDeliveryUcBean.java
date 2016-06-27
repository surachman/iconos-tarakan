/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.*;
import com.pelindo.ebtos.model.db.UcDeliveryService;
import com.pelindo.ebtos.model.db.UcGatedeliveryService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContDamage;
import java.io.Serializable;
import java.util.Calendar;
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

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "gateOutDeliveryUcBean")
@ViewScoped
public class GateOutDeliveryUcBean implements Serializable {
    @EJB
    private MasterContDamageFacadeRemote masterContDamageFacadeRemote;
    @EJB
    private UcGatedeliveryServiceFacadeRemote ucGatedeliveryServiceFacadeRemote;
    @EJB
    private UcDeliveryServiceFacadeRemote ucDeliveryServiceFacadeRemote;
    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacadeRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;

    private List<MasterContDamage> masterContDamages;
    private UcDeliveryService ucDeliveryService;
    private UncontainerizedService uncontainerizedService;
    private MasterCommodity masterCommodity;
    private UcGatedeliveryService ucGatedeliveryService;
    private Object[] gateOutUc;
    private String jobSlip;

    /** Creates a new instance of GateOutDeliveryUcBean */
    public GateOutDeliveryUcBean() {}

    @PostConstruct
    private void construct(){
        ucGatedeliveryService = new UcGatedeliveryService();
        uncontainerizedService = new UncontainerizedService();
        masterCommodity = new MasterCommodity();
        ucDeliveryService = new UcDeliveryService();

        masterContDamages = masterContDamageFacadeRemote.findAll();
    }

    public List<String> setListAutoComplete(String query) {
        List<String> results = ucGatedeliveryServiceFacadeRemote.findUcGatedeliveryServiceByAutoComplete(query);
        return results;
    }

    public void ambilContNo(ActionEvent event) {
        setJobSlip((String) event.getComponent().getAttributes().get("jobSlip"));
        gateOutUc = ucGatedeliveryServiceFacadeRemote.findUcGatedeliveryServiceByDateOutNull(jobSlip);
        
        if (gateOutUc != null) {
            ucGatedeliveryService = ucGatedeliveryServiceFacadeRemote.find(gateOutUc[1]);
            ucDeliveryService = ucDeliveryServiceFacadeRemote.find(ucGatedeliveryService.getJobslip());
            uncontainerizedService = ucDeliveryService.getUncontainerizedService();
            
            masterCommodity = masterCommodityFacadeRemote.find(uncontainerizedService.getCommodity());

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        } else {
            ucGatedeliveryService = new UcGatedeliveryService();
            uncontainerizedService = new UncontainerizedService();
            ucDeliveryService = new UcDeliveryService();
            
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
        }
    }

    public void saveEdit(ActionEvent event) {
        try {
            ucGatedeliveryService.setDateOut(Calendar.getInstance().getTime());
            uncontainerizedService.setStatus("OUTSIDE");

            if (uncontainerizedService.getTruckLoosing().equals("TRUE")) {
                ucDeliveryService.setIsDelivery("TRUE");
                ucDeliveryService.setDeliveryDate(uncontainerizedService.getPlacementDate());
                ucDeliveryServiceFacadeRemote.edit(ucDeliveryService);
            }
            
            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
            ucGatedeliveryServiceFacadeRemote.edit(ucGatedeliveryService);

            ucGatedeliveryService = new UcGatedeliveryService();
            uncontainerizedService = new UncontainerizedService();
            ucDeliveryService = new UcDeliveryService();
            
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (RuntimeException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }
    }

    public void clear(ActionEvent actionEvent) {
        ucGatedeliveryService = new UcGatedeliveryService();
        uncontainerizedService = new UncontainerizedService();
        ucDeliveryService = new UcDeliveryService();
    }

    public UcDeliveryService getUcDeliveryService() {
        return ucDeliveryService;
    }

    public void setUcDeliveryService(UcDeliveryService ucDeliveryService) {
        this.ucDeliveryService = ucDeliveryService;
    }

    public UcGatedeliveryService getUcGatedeliveryService() {
        return ucGatedeliveryService;
    }

    public void setUcGatedeliveryService(UcGatedeliveryService ucGatedeliveryService) {
        this.ucGatedeliveryService = ucGatedeliveryService;
    }

    public UncontainerizedService getUncontainerizedService() {
        return uncontainerizedService;
    }

    public void setUncontainerizedService(UncontainerizedService uncontainerizedService) {
        this.uncontainerizedService = uncontainerizedService;
    }

    public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    public void setMasterCommodity(MasterCommodity masterCommodity) {
        this.masterCommodity = masterCommodity;
    }

    public Object[] getGateOutUc() {
        return gateOutUc;
    }

    public void setGateOutUc(Object[] gateOutUc) {
        this.gateOutUc = gateOutUc;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public List<MasterContDamage> getMasterContDamages() {
        return masterContDamages;
    }

    public void setMasterContDamages(List<MasterContDamage> masterContDamages) {
        this.masterContDamages = masterContDamages;
    }

}

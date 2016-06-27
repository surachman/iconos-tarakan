/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.master.MasterYard;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Lenovo
 */
@ManagedBean(name = "changeDischargeYardAllocationBean")
@ViewScoped

public class ChangeDischargeYardAllocationBean implements Serializable {
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote;

    private List<Object[]> vessels;
    private List<Object[]> services;
    private Object[] vessel;
    private List<String> fromYards, toYards;
    private String selectedFromYard, selectedToYard;
    
    String noPPKB;
    boolean disable = true;
    
    @PostConstruct
    private void construct() {
        fromYards = masterYardFacadeRemote.findAllBlocks();
        toYards = fromYards;
        if (!fromYards.isEmpty()) {
            selectedFromYard = fromYards.get(0);
        }else{
            selectedFromYard = "A";
        }
    }

    public void handleNewPpkb(ActionEvent event) {
        vessels = planningVesselFacadeRemote.findPlanningVesselsServicingOnly();
        noPPKB = "";
        if (services != null) {
            if (!services.isEmpty()) {
                services.clear();
            }
        }
    }
    
    public void handleSelectNoPpkb(ActionEvent event) {
        noPPKB = (String) event.getComponent().getAttributes().get("noPpkb");
        vessel = planningVesselFacadeRemote.findPlanningVesselByPpkb(noPPKB);

        if (!fromYards.isEmpty()) {
            selectedFromYard = fromYards.get(0);
        }else{
            selectedFromYard = "A";
        }
        setData(noPPKB, selectedFromYard);
        disable = false;
    }
    
    public void handleChangeBlock(ValueChangeEvent event){
        selectedFromYard = event.getNewValue().toString();
        setData(noPPKB, selectedFromYard);
    }

    private void setData(String noPPKB, String selectedFromYard) {
        if (noPPKB != null && selectedFromYard != null) {
            services = serviceContDischargeFacadeRemote.findServiceContDischargeByPPKBAndBlock(noPPKB, selectedFromYard);
        }else{
            System.out.println("noPPKB & selectedFromYard = Kosong...");
        }
    }
    
    public void handleReplace(){
        MasterYard yard = masterYardFacadeRemote.find(selectedToYard);
        for (int i = 0; i < services.size(); i++) {
            ServiceContDischarge obj = serviceContDischargeFacadeRemote.find(services.get(i)[0]);
            obj.setMasterYard(yard);
            serviceContDischargeFacadeRemote.edit(obj);
        }
        String temp = selectedFromYard;
        selectedFromYard = selectedToYard;
        selectedToYard = temp;
        setData(noPPKB, selectedFromYard);
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public String getNoPPKB() {
        return noPPKB;
    }

    public void setNoPPKB(String noPPKB) {
        this.noPPKB = noPPKB;
    }

    public Object[] getVessel() {
        return vessel;
    }

    public void setVessel(Object[] vessel) {
        this.vessel = vessel;
    }

    public List<Object[]> getVessels() {
        return vessels;
    }

    public void setVessels(List<Object[]> vessels) {
        this.vessels = vessels;
    }

    public List<String> getFromYards() {
        return fromYards;
    }

    public void setFromYards(List<String> fromYards) {
        this.fromYards = fromYards;
    }

    public String getSelectedFromYard() {
        return selectedFromYard;
    }

    public void setSelectedFromYard(String selectedFromYard) {
        this.selectedFromYard = selectedFromYard;
    }

    public String getSelectedToYard() {
        return selectedToYard;
    }

    public void setSelectedToYard(String selectedToYard) {
        this.selectedToYard = selectedToYard;
    }

    public List<String> getToYards() {
        return toYards;
    }

    public void setToYards(List<String> toYards) {
        this.toYards = toYards;
    }

    public List<Object[]> getServices() {
        return services;
    }

    public void setServices(List<Object[]> services) {
        this.services = services;
    }
    
}

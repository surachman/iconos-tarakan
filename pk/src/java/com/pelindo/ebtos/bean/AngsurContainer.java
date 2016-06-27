/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author dycoder
 */
@Named(value="angsurContainer")
@RequestScoped
public class AngsurContainer {
    private PlanningVessel planningVessel;
    private List<ServiceContDischarge> serviceContDischarges;
    private ServiceContDischarge serviceContDischarge;
    private Equipment equipment;
    private List<MasterEquipment> masterEquipments;
    /** Creates a new instance of AngsurContainer */
    public AngsurContainer() {
         planningVessel=new PlanningVessel();
         equipment=new Equipment();
         serviceContDischarge=new ServiceContDischarge();
    }

    /**
     * @return the planningVessel
     */
    public PlanningVessel getPlanningVessel() {       
        return planningVessel;
    }

    /**
     * @param planningVessel the planningVessel to set
     */
    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    /**
     * @return the serviceContDischarges
     */
    public List<ServiceContDischarge> getServiceContDischarges() {
        return serviceContDischarges;
    }

    /**
     * @param serviceContDischarges the serviceContDischarges to set
     */
    public void setServiceContDischarges(List<ServiceContDischarge> serviceContDischarges) {
        this.serviceContDischarges = serviceContDischarges;
    }

    /**
     * @return the serviceContDischarge
     */
    public ServiceContDischarge getServiceContDischarge() {
        return serviceContDischarge;
    }

    /**
     * @param serviceContDischarge the serviceContDischarge to set
     */
    public void setServiceContDischarge(ServiceContDischarge serviceContDischarge) {
        this.serviceContDischarge = serviceContDischarge;
    }

    /**
     * @return the equipment
     */
    public Equipment getEquipment() {
        return equipment;
    }

    /**
     * @param equipment the equipment to set
     */
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    /**
     * @return the masterEquipments
     */
    public List<MasterEquipment> getMasterEquipments() {
        return masterEquipments;
    }

    /**
     * @param masterEquipments the masterEquipments to set
     */
    public void setMasterEquipments(List<MasterEquipment> masterEquipments) {
        this.masterEquipments = masterEquipments;
    }

}

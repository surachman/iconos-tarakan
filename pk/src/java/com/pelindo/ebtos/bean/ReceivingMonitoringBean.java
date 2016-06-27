/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.ServiceVessel;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author USER
 */
@Named(value="receivingMonitoringBean")
@RequestScoped
public class ReceivingMonitoringBean {

    private ServiceVessel serviceVessel;
    private List<PlanningContReceiving> planningContReceivings;

    /** Creates a new instance of ReceivingMonitoringBean */
    public ReceivingMonitoringBean() {
        serviceVessel=new ServiceVessel();
    }

    public List<PlanningContReceiving> getPlanningContReceivings() {
        return planningContReceivings;
    }

    public void setPlanningContReceivings(List<PlanningContReceiving> planningContReceivings) {
        this.planningContReceivings = planningContReceivings;
    }

    public ServiceVessel getServiceVessel() {
        return serviceVessel;
    }

    public void setServiceVessel(ServiceVessel serviceVessel) {
        this.serviceVessel = serviceVessel;
    }

    

}

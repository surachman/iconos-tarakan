/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "containerCancelVesselBean")
@ViewScoped
public class ContainerCancelVesselBean implements Serializable {
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote;

    private List<Object[]> serviceVesselsDisch;
    private List<Object[]> serviceCancelLoadings;
    private PlanningVessel planningVessel;
    private String noPpkb;

    /** Creates a new instance of ChangeOfDestinationBean */
    public ContainerCancelVesselBean() {}

    public void handleShowPlanningVessels(ActionEvent event) {
        serviceVesselsDisch = planningVesselFacadeRemote.findCanceledPpkb();
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        noPpkb = (String) event.getComponent().getAttributes().get("noPpkb");
        planningVessel = planningVesselFacadeRemote.find(noPpkb);
        serviceCancelLoadings = cancelLoadingServiceFacadeRemote.findByChangeDestination(noPpkb);
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public List<Object[]> getServiceVesselsDisch() {
        return serviceVesselsDisch;
    }

    public List<Object[]> getServiceCancelLoadings() {
        return serviceCancelLoadings;
    }
}

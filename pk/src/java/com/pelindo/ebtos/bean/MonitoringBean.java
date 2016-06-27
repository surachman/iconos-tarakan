/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author USER
 */
@Named(value="monitoringBean")
@RequestScoped
public class MonitoringBean implements Serializable {
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacade;
    private List<Object[]> activities;

    /** Creates a new instance of MonitoringBean */
    public MonitoringBean() {}

    @PostConstruct
    private void construct() {
        activities = planningVesselFacade.vesselMonitoringToday();
    }

    public List<Object[]> getActivities() {
        return activities;
    }
}

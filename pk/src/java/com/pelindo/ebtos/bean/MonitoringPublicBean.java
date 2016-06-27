/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author dycoder
 */
@Named(value="monitoringPublicBean")
@RequestScoped
public class MonitoringPublicBean {

  PlanningVesselFacadeRemote planningVesselFacade = lookupPlanningVesselFacadeRemote();

    private List<Object[]> activities = lookupPlanningVesselFacadeRemote().vesselMonitoringToday();
    /** Creates a new instance of MonitoringBean */
    public MonitoringPublicBean() {

    }

    public List<Object[]> getActivities() {
        return activities;
    }

    private PlanningVesselFacadeRemote lookupPlanningVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PlanningVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PlanningVesselFacade!com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}

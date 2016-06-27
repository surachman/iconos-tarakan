/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.PlanningContReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import java.io.Serializable;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "monitoringContainerLoadBean")
@ViewScoped
public class MonitoringContainerLoadBean implements Serializable {

    private List<Object[]> serviceVessels = lookupServiceVesselFacadeRemote().findServiceVesselsByMonitoringContLoad();
    private List<Object[]> planningContReceiving;
    private Object[] serviceVessel;
    private String noPPKB;
    private String filter = "01";
    private Object[] summaryContLoad;

    /** Creates a new instance of MonitoringContainerLoadBean */
    public MonitoringContainerLoadBean() {
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        setNoPPKB((String) event.getComponent().getAttributes().get("noppkb"));
        serviceVessel = lookupServiceVesselFacadeRemote().findServiceVesselByPpkb(getNoPPKB());
        planningContReceiving=lookupPlanningContReceivingFacadeRemote().findPlanningContMonitoringLoad(getNoPPKB(),this.filter);
        summaryContLoad = lookupServiceVesselFacadeRemote().findServiceVesselsByMonitoringCountLoad2(getNoPPKB());
    }


   public void OnChangeFilter(ValueChangeEvent event){
      if(getNoPPKB() == null ? "" != null : !getNoPPKB().equals("")){
            String fl = (String)event.getNewValue();
            planningContReceiving=lookupPlanningContReceivingFacadeRemote().findPlanningContMonitoringLoad(getNoPPKB(),fl);
            System.out.println("No PPKB :" + getNoPPKB());
            System.out.println("filter :" + fl);
        }
        
    }

    private ServiceVesselFacadeRemote lookupServiceVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceVesselFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PlanningContReceivingFacadeRemote lookupPlanningContReceivingFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PlanningContReceivingFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PlanningContReceivingFacade!com.pelindo.ebtos.ejb.facade.remote.PlanningContReceivingFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public List<Object[]> getPlanningContReceiving() {
        return planningContReceiving;
    }

    public void setPlanningContReceiving(List<Object[]> planningContReceiving) {
        this.planningContReceiving = planningContReceiving;
    }

    public Object[] getServiceVessel() {
        return serviceVessel;
    }

    public void setServiceVessel(Object[] serviceVessel) {
        this.serviceVessel = serviceVessel;
    }

    public List<Object[]> getServiceVessels() {
        return serviceVessels;
    }

    public void setServiceVessels(List<Object[]> serviceVessels) {
        this.serviceVessels = serviceVessels;
    }
    
    public Object[] getSummaryContLoad() {
        return summaryContLoad;
    }

    public void setSummaryContLoad(Object[] summaryContLoad) {
        this.summaryContLoad = summaryContLoad;
    }

    public String getNoPPKB() {
        return noPPKB;
    }

    /**
     * @param noPPKB the noPPKB to set
     */
    public void setNoPPKB(String noPPKB) {
        this.noPPKB = noPPKB;
    }

        /**
     * @return the filter
     */
    public String getFilter() {
        return filter;
    }

    /**
     * @param filter the filter to set
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

}

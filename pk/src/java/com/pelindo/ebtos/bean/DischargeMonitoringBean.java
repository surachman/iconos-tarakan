/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author USER
 */
@ManagedBean(name="dischargeMonitoringBean")
@ViewScoped
public class DischargeMonitoringBean implements Serializable{

    protected ServiceVesselFacadeRemote serviceVesselFacadeRemote = lookupServiceVesselFacadeRemote();
    protected ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote = lookupServiceContDischargeFacadeRemote();

    private Object[] serviceVessel;
    private List<Object[]> serviceVessels;
    private List<Object[]> dischargeMonitorings;
    private List<Object[]> ucDischargeMonitorings;
    private String filter = "03";
    private boolean del = false;
    protected String noPPKB;
    protected String mode;
    

    /** Creates a new instance of DischargeMonitoringBean */
    public DischargeMonitoringBean() {
        serviceVessels = serviceVesselFacadeRemote.findServiceVesselStatusService();
    }

    /**
     * @return the serviceVessel
     */
    public Object[] getServiceVessel() {
        return serviceVessel;
    }

    /**
     * @return the serviceVessels
     */
    public List<Object[]> getServiceVessels() {
        return serviceVessels;
    }

    /**
     * @return the dischargeMonitoring
     */
    public List<Object[]> getDischargeMonitorings() {
        return dischargeMonitorings;
    }   

    public void handleSelectNoPPKB(ActionEvent event){
        String fl = this.filter;
        setNoPPKB((String) event.getComponent().getAttributes().get("noPPKB"));
        serviceVessel = serviceVesselFacadeRemote.findServiceVesselByPpkb(getNoPPKB());
            if (fl.equals("01") || fl.equals("02")) {
                   dischargeMonitorings = serviceContDischargeFacadeRemote.findDischargeMonitoringByPPKB(getNoPPKB(),fl);
            } else if (fl.equals("03")) {
                   del = false;
                   dischargeMonitorings = serviceContDischargeFacadeRemote.findDischargeMonitoringByPPKB1(getNoPPKB(),fl,del);
            } else {
                 del = true;
                 dischargeMonitorings = serviceContDischargeFacadeRemote.findDischargeMonitoringByPPKB2(getNoPPKB(),del);
            }
       ucDischargeMonitorings = serviceContDischargeFacadeRemote.findUcDischargeMonitoringByPPKB(getNoPPKB());
    }

      public void OnChangeFilter(ValueChangeEvent event){
      if(getNoPPKB() == null ? "" != null : !getNoPPKB().equals("")){
            String fl = (String)event.getNewValue();

            if (fl.equals("01") || fl.equals("02")) {
                   dischargeMonitorings = serviceContDischargeFacadeRemote.findDischargeMonitoringByPPKB(getNoPPKB(),fl);
            } else if (fl.equals("03")) {
                   del = false;
                   dischargeMonitorings = serviceContDischargeFacadeRemote.findDischargeMonitoringByPPKB1(getNoPPKB(),fl,del);
            } else if (fl.equals("05")){
                dischargeMonitorings = serviceContDischargeFacadeRemote.findDischargeMonitoringByPPKB(getNoPPKB(),null);
            } else{
                 del = true;
                 dischargeMonitorings = serviceContDischargeFacadeRemote.findDischargeMonitoringByPPKB2(getNoPPKB(),del);
            }

            ucDischargeMonitorings = serviceContDischargeFacadeRemote.findUcDischargeMonitoringByPPKB(getNoPPKB());
            System.out.println("No PPKB :" + getNoPPKB());
            System.out.println("filter :" + fl);
        }

    }

    public String getUrlDischarge(){
        if (dischargeMonitorings == null)
            return "#";

        mode = "discharge";
	return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/realisasi.pdf?"
                + "mode=" + mode + "&" + "no_ppkb=" + noPPKB + "";
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

    private ServiceContDischargeFacadeRemote lookupServiceContDischargeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceContDischargeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceContDischargeFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * @return the ucDischargeMonitorings
     */
    public List<Object[]> getUcDischargeMonitorings() {
        return ucDischargeMonitorings;
    }

    /**
     * @param ucDischargeMonitorings the ucDischargeMonitorings to set
     */
    public void setUcDischargeMonitorings(List<Object[]> ucDischargeMonitorings) {
        this.ucDischargeMonitorings = ucDischargeMonitorings;
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

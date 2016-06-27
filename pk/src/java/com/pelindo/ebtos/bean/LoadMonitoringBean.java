/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
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
@ManagedBean(name="loadMonitoringBean")
@ViewScoped
public class LoadMonitoringBean implements Serializable{

    ServiceVesselFacadeRemote serviceVesselFacadeRemote = lookupServiceVesselFacadeRemote();
    ServiceContLoadFacadeRemote serviceContLoadFacadeRemote = lookupServiceContLoadFacadeRemote();

    private Object[] serviceVessel;
    private List<Object[]> serviceVessels;
    private List<Object[]> loadMonitorings;
    private List<Object[]> ucLoadMonitorings;
    private String filter = "01";
    protected String noPPKB;
    protected String mode;

        /** Creates a new instance of LoadMonitoringBean */
    public LoadMonitoringBean() {       
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
     * @return the loadMonitorings
     */
    public List<Object[]> getLoadMonitorings() {
        return loadMonitorings;
    }

    public void handleSelectNoPPKB(ActionEvent event){
        //noPPKB = (String) event.getComponent().getAttributes().get("noPPKB");
        setNoPPKB((String) event.getComponent().getAttributes().get("noPPKB"));
        serviceVessel = serviceVesselFacadeRemote.findServiceVesselByPpkb(getNoPPKB());
        loadMonitorings = serviceContLoadFacadeRemote.findLoadMonitoringsByPPKB(getNoPPKB(),this.filter);
        ucLoadMonitorings = serviceContLoadFacadeRemote.findUcLoadMonitoringsByPPKB(getNoPPKB());
    }


   public void OnChangeFilter(ValueChangeEvent event){
      if(getNoPPKB() == null ? "" != null : !getNoPPKB().equals("")){
            String fl = (String)event.getNewValue();
            loadMonitorings = serviceContLoadFacadeRemote.findLoadMonitoringsByPPKB(getNoPPKB(),fl);
            System.out.println("No PPKB :" + getNoPPKB());
            System.out.println("filter :" + fl);
        }

    }

    public String getUrlLoad(){
        if (loadMonitorings == null)
            return "#";

        mode = "load";
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

    private ServiceContLoadFacadeRemote lookupServiceContLoadFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceContLoadFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceContLoadFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * @return the ucLoadMonitorings
     */
    public List<Object[]> getUcLoadMonitorings() {
        return ucLoadMonitorings;
    }

    /**
     * @param ucLoadMonitorings the ucLoadMonitorings to set
     */
    public void setUcLoadMonitorings(List<Object[]> ucLoadMonitorings) {
        this.ucLoadMonitorings = ucLoadMonitorings;
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

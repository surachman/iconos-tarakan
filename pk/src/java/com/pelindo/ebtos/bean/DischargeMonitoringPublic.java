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
/**
 *
 * @author dycoder
 */
@ManagedBean(name="dischargeMonitoringPublic")
@ViewScoped
public class DischargeMonitoringPublic implements Serializable{

   protected ServiceVesselFacadeRemote serviceVesselFacadeRemote = lookupServiceVesselFacadeRemote();
    protected ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote = lookupServiceContDischargeFacadeRemote();

    private Object[] serviceVessel;
    private List<Object[]> serviceVessels;
    private List<Object[]> dischargeMonitorings;
    protected String noPPKB;
    protected String mode;


    /** Creates a new instance of DischargeMonitoringBean */
    public DischargeMonitoringPublic() {
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
        noPPKB = (String) event.getComponent().getAttributes().get("noPPKB");
        serviceVessel = serviceVesselFacadeRemote.findServiceVesselByPpkb(noPPKB);
        dischargeMonitorings = serviceContDischargeFacadeRemote.findDischargeMonitoringByPPKBFront(noPPKB);
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

}

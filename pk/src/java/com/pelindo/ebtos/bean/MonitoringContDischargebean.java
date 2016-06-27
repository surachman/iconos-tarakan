/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
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

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "monitoringContDischargebean")
@ViewScoped
public class MonitoringContDischargebean implements Serializable{
     private List<Object[]> serviceVessels = lookupServiceVesselFacadeRemote().findServiceVesselsByMonitoringContDischarge();
     private List<Object[]> serviceContDischarge;
     private Object[] serviceVessel;
    /** Creates a new instance of MonitoringContDischargebean */
    public MonitoringContDischargebean() {
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        String no_ppkb=(String) event.getComponent().getAttributes().get("noppkb");
         serviceVessel = lookupServiceVesselFacadeRemote().findServiceVesselByPpkb(no_ppkb);
        serviceContDischarge=lookupServiceContDischargeFacadeRemote().findServiceCondischargeMonitoringContDisch(no_ppkb);
    }

    //menyiapkan koneksi untuk service vessels
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

    public List<Object[]> getServiceContDischarge() {
        return serviceContDischarge;
    }

    public void setServiceContDischarge(List<Object[]> serviceContDischarge) {
        this.serviceContDischarge = serviceContDischarge;
    }

    public List<Object[]> getServiceVessels() {
        return serviceVessels;
    }

    public void setServiceVessels(List<Object[]> serviceVessels) {
        this.serviceVessels = serviceVessels;
    }

    public Object[] getServiceVessel() {
        return serviceVessel;
    }

    public void setServiceVessel(Object[] serviceVessel) {
        this.serviceVessel = serviceVessel;
    }
}

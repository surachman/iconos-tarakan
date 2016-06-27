/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name="monitoringVesselBean")
@ViewScoped
public class MonitoringVesselBean implements Serializable{
    ServiceVesselFacadeRemote serviceVesselFacade = lookupServiceVesselFacadeRemote();

    private List<Object[]> serviceVessels;
    private Integer bulan;
    private Integer tahun;

    /** Creates a new instance of MonitoringVesselBean */
    public MonitoringVesselBean() {
        serviceVessels = new ArrayList<Object[]>();
        serviceVessels = serviceVesselFacade.findServiceVessels();
        bulan = 1;
        tahun = Calendar.getInstance().getTime().getYear() + 1900;
    }

    public void handleSearch(ActionEvent event){
        serviceVessels = serviceVesselFacade.findServiceVesselsByMonthAndYear(bulan, tahun);
        if(serviceVessels.size() > 0)
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        else
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.notfound");
    }

    public void handleClear(ActionEvent event){
        bulan = 1;
        tahun = Calendar.getInstance().getTime().getYear() + 1900;
        serviceVessels.clear();
        serviceVessels = serviceVesselFacade.findServiceVessels();
    }

    public void handleDownload(ActionEvent event){
        boolean print = false;
        if (serviceVessels != null){
            print = true;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", print); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../goodsCfs.pdf?"
                + "mode=" + "vesselMonitoring"));
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

    /**
     * @return the serviceVessels
     */
    public List<Object[]> getServiceVessels() {
        return serviceVessels;
    }

    /**
     * @param serviceVessels the serviceVessels to set
     */
    public void setServiceVessels(List<Object[]> serviceVessels) {
        this.serviceVessels = serviceVessels;
    }

    /**
     * @return the bulan
     */
    public Integer getBulan() {
        return bulan;
    }

    /**
     * @param bulan the bulan to set
     */
    public void setBulan(Integer bulan) {
        this.bulan = bulan;
    }

    /**
     * @return the tahun
     */
    public Integer getTahun() {
        return tahun;
    }

    /**
     * @param tahun the tahun to set
     */
    public void setTahun(Integer tahun) {
        this.tahun = tahun;
    }

}

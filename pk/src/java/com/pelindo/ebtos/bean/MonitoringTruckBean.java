/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterVehicleFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateDeliveryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
@ManagedBean(name="monitoringTruckBean")
@ViewScoped
public class MonitoringTruckBean implements Serializable{
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacade;
    @EJB
    private MasterVehicleFacadeRemote masterVehicleFacadeRemote;

    private List<Object[]> planningVessels;
    private List<Object[]> truckList;

    private Object[] planningVessel;
    private String no_ppkb;
    private Date tanggal1,tanggal2;
    private String pnJawab,supervisor;
    private String tipe;

    /** Creates a new instance of MonitoringTruckBean */
    public MonitoringTruckBean() {}

    @PostConstruct
    private void construct() {
        tanggal1=Calendar.getInstance().getTime();
        tanggal2=Calendar.getInstance().getTime();
        planningVessel = new Object[4];
        truckList = new ArrayList<Object[]>();
        planningVessels = planningVesselFacade.findPlanningVesselExcConfirm();
        this.tipe="k";
    }


    public void handleSelectNoPpkb(ActionEvent event){
        no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        planningVessel = planningVesselFacade.findPlanningVesselByPpkb(no_ppkb);
        truckList = masterVehicleFacadeRemote.findTruckMonitoringList(no_ppkb);
    }

    public void handlePrintGate(ActionEvent event){
        DateFormat formatter;
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        String s1 = formatter.format(tanggal1);
        String s2 = formatter.format(tanggal2);
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/daftarPetikemasMasuk.pdf?tipe=" + tipe + "&tanggal1=" + s1 + "&tanggal2=" + s2 + "&pnJawab=" + pnJawab +"&supervisor="+supervisor + "&owner=saya")));
    }

    public void handleDownload(ActionEvent event){
        boolean print = false;
        if (truckList != null){
            print = true;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", print); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../discharge.pdf?"
                + "mode=" + "truckMonitoring" + "&"
                + "no_ppkb=" + no_ppkb));
    }

    /**
     * @return the planningVessels
     */
    public List<Object[]> getPlanningVessels() {
        return planningVessels;
    }

    /**
     * @param planningVessels the planningVessels to set
     */
    public void setPlanningVessels(List<Object[]> planningVessels) {
        this.planningVessels = planningVessels;
    }

    /**
     * @return the planningVessel
     */
    public Object[] getPlanningVessel() {
        return planningVessel;
    }

    /**
     * @param planningVessel the planningVessel to set
     */
    public void setPlanningVessel(Object[] planningVessel) {
        this.planningVessel = planningVessel;
    }

    /**
     * @return the truckList
     */
    public List<Object[]> getTruckList() {
        return truckList;
    }

    /**
     * @param truckList the truckList to set
     */
    public void setTruckList(List<Object[]> truckList) {
        this.truckList = truckList;
    }

    public String getPnJawab() {
        return pnJawab;
    }

    public void setPnJawab(String pnJawab) {
        this.pnJawab = pnJawab;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public Date getTanggal1() {
        return tanggal1;
    }

    public void setTanggal1(Date tanggal1) {
        this.tanggal1 = tanggal1;
    }

    public Date getTanggal2() {
        return tanggal2;
    }

    public void setTanggal2(Date tanggal2) {
        this.tanggal2 = tanggal2;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }
}

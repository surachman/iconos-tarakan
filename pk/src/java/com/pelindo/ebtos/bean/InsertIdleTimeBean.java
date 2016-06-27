/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterIdletimeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterIdletimetypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceIdleTimeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceIdleTime;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.master.MasterIdletime;
import com.pelindo.ebtos.model.db.master.MasterIdletimetype;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "insertIdleTimeBean")
@ViewScoped
public class InsertIdleTimeBean implements Serializable {

    @EJB
    ServiceIdleTimeFacadeRemote serviceIdleTimeFacadeRemote;
    ServiceVesselFacadeRemote serviceVesselFacade = lookupServiceVesselFacadeRemote();
    private List<Object[]> serviceVessels;
    private String no_ppkb;
    private Object[] serviceVessel;
    private List<Object[]> insertIdleTimes;
    private ServiceIdleTime serviceIdleTime;
    private List<MasterIdletimetype> masterIdleTimeTypes;
    private List<Object[]> masterIdleTimes;
    private Integer newItem;
    private ServiceShifting serviceShifting;
    private List<Object[]> eqForCrane;
    private String tipe = "b";
    private Date tgl;
    private String foreman;
    private String agen;
    private String equip;

    /** Creates a new instance of InsertIdleTime */
    public InsertIdleTimeBean() {
        tgl = Calendar.getInstance().getTime();
        serviceIdleTime = new ServiceIdleTime();
        serviceIdleTime.setMasterIdletime(new MasterIdletime());
        serviceIdleTime.setMasterIdletimetype(new MasterIdletimetype());
        serviceVessel = new Object[4];
        newItem = 1;
        masterIdleTimes = lookupMasterIdletimeFacadeRemote().findAllMasterIdletimeById(newItem);
        eqForCrane = lookupMasterEquipmentFacadeRemote().findCraneExcKapal();
    }

    public void handleViewPpkb(ActionEvent event) {
        serviceVessels = lookupServiceVesselFacadeRemote().findServiceVesselsServicing();
        masterIdleTimeTypes = lookupMasterIdletimetypeFacadeRemote().findAll();
        //System.out.println("tesssss");
    }

    public void clearSave() {
        serviceIdleTime = new ServiceIdleTime();
        serviceIdleTime.setMasterIdletime(new MasterIdletime());
        serviceIdleTime.setMasterIdletimetype(new MasterIdletimetype());
        newItem = 1;
    }

    public void handleOnSelect(ActionEvent event) {
        setNo_ppkb((String) event.getComponent().getAttributes().get("noPpkb"));
        setServiceVessel(serviceVesselFacade.findServiceVesselByPpkb(getNo_ppkb()));
        insertIdleTimes = serviceIdleTimeFacadeRemote.findServiceIdleTimesByPpkb(getNo_ppkb());
    }

    public void handleRefresh(ActionEvent event) {
        insertIdleTimes = serviceIdleTimeFacadeRemote.findServiceIdleTimesByPpkb(getNo_ppkb());
    }

    public void handleDelete(ActionEvent event) {
        try {
            serviceIdleTimeFacadeRemote.remove(serviceIdleTime);
            insertIdleTimes = serviceIdleTimeFacadeRemote.findServiceIdleTimesByPpkb(getNo_ppkb());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            clearSave();
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
        }

    }

    public void handleAdd(ActionEvent event) {
        this.clearSave();
    }

    public void handleEditDeleteButton(ActionEvent event) {
        Integer idIdl = (Integer) event.getComponent().getAttributes().get("idIdl");
        serviceIdleTime = serviceIdleTimeFacadeRemote.find(idIdl);
    }

    public void handleSubmit(ActionEvent actionEvent) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        if (serviceIdleTime.getStartTime().after(serviceIdleTime.getEndTime()) || serviceIdleTime.getStartTime().equals(serviceIdleTime.getEndTime())) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.time");
        } else {
            loggedIn = true;
            serviceIdleTime.setNoPpkb(getNo_ppkb());
            serviceIdleTimeFacadeRemote.edit(serviceIdleTime);
            insertIdleTimes = serviceIdleTimeFacadeRemote.findServiceIdleTimesByPpkb(getNo_ppkb());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            this.clearSave();
        }

        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void onChangeEvent(ValueChangeEvent event) {
        //Integer newItem=(Integer) event.getNewValue();
        this.setNewItem((Integer) event.getNewValue());
        masterIdleTimes = lookupMasterIdletimeFacadeRemote().findAllMasterIdletimeById(getNewItem());
        //masterIdleTimes=masterIdletimeFacadeRemote.findAllMasterIdletimeByDelete(newItem);
        //setMasterIdleTimes(masterIdleTimes=(List<MasterIdletime>) masterIdletimeFacadeRemote.find(newItem));
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

    private MasterIdletimetypeFacadeRemote lookupMasterIdletimetypeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterIdletimetypeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterIdletimetypeFacade!com.pelindo.ebtos.ejb.facade.remote.MasterIdletimetypeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterIdletimeFacadeRemote lookupMasterIdletimeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterIdletimeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterIdletimeFacade!com.pelindo.ebtos.ejb.facade.remote.MasterIdletimeFacadeRemote");
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
     * @return the no_ppkb
     */
    public String getNo_ppkb() {
        return no_ppkb;
    }

    /**
     * @param no_ppkb the no_ppkb to set
     */
    public void setNo_ppkb(String no_ppkb) {
        this.no_ppkb = no_ppkb;
    }

    /**
     * @return the serviceVessel
     */
    public Object[] getServiceVessel() {
        return serviceVessel;
    }

    /**
     * @param serviceVessel the serviceVessel to set
     */
    public void setServiceVessel(Object[] serviceVessel) {
        this.serviceVessel = serviceVessel;
    }

    /**
     * @return the insertIdleTimes
     */
    public List<Object[]> getInsertIdleTimes() {
        return insertIdleTimes;
    }

    /**
     * @param insertIdleTimes the insertIdleTimes to set
     */
    public void setInsertIdleTimes(List<Object[]> insertIdleTimes) {
        this.insertIdleTimes = insertIdleTimes;
    }

    /**
     * @return the serviceIdleTime
     */
    public ServiceIdleTime getServiceIdleTime() {
        return serviceIdleTime;
    }

    /**
     * @param serviceIdleTime the serviceIdleTime to set
     */
    public void setServiceIdleTime(ServiceIdleTime serviceIdleTime) {
        this.serviceIdleTime = serviceIdleTime;
    }

    /**
     * @return the masterIdleTimeTypes
     */
    public List<MasterIdletimetype> getMasterIdleTimeTypes() {
        return masterIdleTimeTypes;
    }

    /**
     * @param masterIdleTimeTypes the masterIdleTimeTypes to set
     */
    public void setMasterIdleTimeTypes(List<MasterIdletimetype> masterIdleTimeTypes) {
        this.masterIdleTimeTypes = masterIdleTimeTypes;
    }

    /**
     * @return the masterIdleTimes
     */
    public List<Object[]> getMasterIdleTimes() {
        return masterIdleTimes;
    }

    /**
     * @param masterIdleTimes the masterIdleTimes to set
     */
    public void setMasterIdleTimes(List<Object[]> masterIdleTimes) {
        this.masterIdleTimes = masterIdleTimes;
    }

    /**
     * @return the newItem
     */
    public Integer getNewItem() {
        return newItem;
    }

    /**
     * @param newItem the newItem to set
     */
    public void setNewItem(Integer newItem) {
        this.newItem = newItem;
    }

    /**
     * @return the serviceShifting
     */
    public ServiceShifting getServiceShifting() {
        return serviceShifting;
    }

    /**
     * @param serviceShifting the serviceShifting to set
     */
    public void setServiceShifting(ServiceShifting serviceShifting) {
        this.serviceShifting = serviceShifting;
    }

    public List<Object[]> getEqForCrane() {
        return eqForCrane;
    }

    public void setEqForCrane(List<Object[]> eqForCrane) {
        this.eqForCrane = eqForCrane;
    }

    public String getAgen() {
        return agen;
    }

    public void setAgen(String agen) {
        this.agen = agen;
    }

    public String getEquip() {
        return equip;
    }

    public void setEquip(String equip) {
        this.equip = equip;
    }

    public String getForeman() {
        return foreman;
    }

    public void setForeman(String foreman) {
        this.foreman = foreman;
    }

    public Date getTgl() {
        return tgl;
    }

    public void setTgl(Date tgl) {
        this.tgl = tgl;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public void handlePrintBeritaAcara(ActionEvent event) {
        DateFormat formatter;
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        String s = formatter.format(tgl);
        //System.out.println("cek print tgl : " + s);
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/beritaAcaraWeb.pdf?tipe=" + tipe + "&no_ppkb=" + no_ppkb + "&tgl=" + s + "&foreman=" + foreman + "&agen=" + agen + "&equip=" + equip)));
    }

    public void handlePrintBeritaAcaraP(ActionEvent event) {
        DateFormat formatter;
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        String s = formatter.format(tgl);
        //System.out.println("cek print tgl : " + s);
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/beritaAcaraWeb.pdf?tipe=x" + "&no_ppkb=" + no_ppkb + "&tgl=" + s + "&foreman=" + foreman + "&agen=" + agen + "&equip=" + equip)));
    }

    private MasterEquipmentFacadeRemote lookupMasterEquipmentFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterEquipmentFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterEquipmentFacade!com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}

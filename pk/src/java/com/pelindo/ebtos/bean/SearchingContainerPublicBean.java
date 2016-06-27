/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author wulan
 */
@ManagedBean(name = "searchingContainerPublicBean")
@ViewScoped
public class SearchingContainerPublicBean {

    ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote = lookupServiceContDischargeFacadeRemote();
    ServiceReceivingFacadeRemote serviceReceivingFacadeRemote = lookupServiceReceivingFacadeRemote();
    private List<Object[]> listDischarge;
    private List<Object[]> listTracking;
    private String contNo = "";
    private String tipe = "DISCHARGE";
    private Boolean detailCont = Boolean.TRUE;
    private Boolean tracking = Boolean.FALSE;

    /** Creates a new instance of SearchingContainerPublic */
    public SearchingContainerPublicBean() {
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public Boolean getDetailCont() {
        return detailCont;
    }

    public void setDetailCont(Boolean detailCont) {
        this.detailCont = detailCont;
    }

    public Boolean getTracking() {
        return tracking;
    }

    public void setTracking(Boolean tracking) {
        this.tracking = tracking;
    }

    public void handleClear(ActionEvent event) {
        contNo = "";
        listDischarge = new ArrayList<Object[]>();
        this.tracking = Boolean.FALSE;
        this.detailCont = Boolean.TRUE;
    }

    public void tutup(ActionEvent event) {
        this.tracking = Boolean.FALSE;
        this.detailCont = Boolean.TRUE;
    }

    public void handleView(ActionEvent event) {
        String noCont = (String) event.getComponent().getAttributes().get("cont");
        String noPpkb = (String) event.getComponent().getAttributes().get("ppkb");

        this.tracking = Boolean.TRUE;
        this.detailCont = Boolean.FALSE;

        if (tipe.equalsIgnoreCase("LOAD")) {
            listTracking = serviceReceivingFacadeRemote.findServiceReceivingFrontByTrackingCont(noPpkb, noCont);
        } else {
            listTracking = serviceContDischargeFacadeRemote.findSearchingContainerTrackingDischargeFront(noPpkb, noCont);
        }
    }

    public void onChangeOperation(ValueChangeEvent event) {
        listDischarge = new ArrayList<Object[]>();
        if (tipe.equalsIgnoreCase("LOAD") && !contNo.equals("") && contNo != null) {
            listDischarge = serviceReceivingFacadeRemote.findServiceReceivingFrontByContainerNo(contNo);
        } else if (tipe.equalsIgnoreCase("DISCHARGE") && !contNo.equals("") && contNo != null) {
            listDischarge = serviceContDischargeFacadeRemote.findSearchingContainerDischargeFront(contNo);
        }
    }

    public void handleSearch(ActionEvent event) {
        listDischarge = new ArrayList<Object[]>();
        if (tipe.equalsIgnoreCase("LOAD") && !contNo.equals("") && contNo != null) {
            listDischarge = serviceReceivingFacadeRemote.findServiceReceivingFrontByContainerNo(contNo);
        } else if (tipe.equalsIgnoreCase("DISCHARGE") && !contNo.equals("") && contNo != null) {
            listDischarge = serviceContDischargeFacadeRemote.findSearchingContainerDischargeFront(contNo);
        }
        if (listDischarge.size() > 0) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.notfound");
        }
        this.tracking = Boolean.FALSE;
        this.detailCont = Boolean.TRUE;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public List<Object[]> getListDischarge() {
        return listDischarge;
    }

    public void setListDischarge(List<Object[]> listDischarge) {
        this.listDischarge = listDischarge;
    }

    public List<Object[]> getListTracking() {
        return listTracking;
    }

    public void setListTracking(List<Object[]> listTracking) {
        this.listTracking = listTracking;
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

    private ServiceReceivingFacadeRemote lookupServiceReceivingFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceReceivingFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceReceivingFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}

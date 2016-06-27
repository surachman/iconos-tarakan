/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;


import com.pelindo.ebtos.ejb.facade.remote.ServiceCfsFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceCfsStrippingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceCfsStuffingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
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
@ManagedBean(name = "cfsRecapBean")
@ViewScoped
public class CfsRecapBean implements Serializable{
    ServiceVesselFacadeRemote serviceVesselFacade = lookupServiceVesselFacadeRemote();
    ServiceCfsFacadeRemote serviceCfsFacade = lookupServiceCfsFacadeRemote();
    ServiceCfsStrippingFacadeRemote serviceCfsStrippingFacade = lookupServiceCfsStrippingFacadeRemote();
    ServiceCfsStuffingFacadeRemote serviceCfsStuffingFacade = lookupServiceCfsStuffingFacadeRemote();

    private List<Object[]> serviceVessels = lookupServiceVesselFacadeRemote().findPpkbCfs();
    private List<Object[]> serviceCfs;
    private List<Object[]> serviceCfsStuffing;
    private List<Object[]> serviceCfsStripping;

    private String no_ppkb;
    private String cont_no;
    private String mode;

    /** Creates a new instance of CfsRecapBean */
    public CfsRecapBean() {
    }

    public void handleSelectNoPpkb(ActionEvent event){
        no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        if(serviceCfsFacade.findByPpkb(no_ppkb) != null)
            serviceCfs = serviceCfsFacade.findByPpkb(no_ppkb);
    }

    public void handleSelectNoCont(ActionEvent event){
        cont_no = (String) event.getComponent().getAttributes().get("noCont");
        if(serviceCfsStrippingFacade.findByPpkbNCont(no_ppkb, cont_no) != null)
            serviceCfsStripping = serviceCfsStrippingFacade.findByPpkbNCont(no_ppkb, cont_no);
        if(serviceCfsStuffingFacade.findByPpkbNCont(no_ppkb, cont_no) != null)
            serviceCfsStuffing = serviceCfsStuffingFacade.findByPpkbNCont(no_ppkb, cont_no);
    }

    public void handleDownloadStripping(ActionEvent event){
        boolean print = false;
        if (serviceCfsStripping != null){
            print = true;
        }
        mode = "stripping";
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", print); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../goodsCfs.pdf?"
                + "mode=" + mode + "&"
                + "cont_no=" + cont_no + "&"
                + "no_ppkb=" + no_ppkb));
    }

    public String getUrlStripping(){
        if (serviceCfsStripping == null)
            return "#";

        mode = "stripping";
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	return  context.encodeResourceURL(context.getRequestScheme() + "://" + context.getRequestServerName() + ":" + context.getRequestServerPort() + context.getRequestContextPath() + "/goodsCfs.pdf?"
                + "mode=" + mode + "&"
                + "cont_no=" + cont_no + "&"
                + "no_ppkb=" + no_ppkb + "");
    }

    public void handleDownloadStuffing(ActionEvent event){
        boolean print = false;
        if (serviceCfsStuffing != null){
            print = true;
        }
        mode = "stuffing";
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", print); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../goodsCfs.pdf?"
                + "mode=" + mode + "&"
                + "cont_no=" + cont_no + "&"
                + "no_ppkb=" + no_ppkb));
    }

    public String getUrlStuffing(){
        if (serviceCfsStuffing == null)
            return "#";

        mode = "stuffing";
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	return  context.encodeResourceURL(context.getRequestScheme() + "://" + context.getRequestServerName() + ":" + context.getRequestServerPort() + context.getRequestContextPath() + "/goodsCfs.pdf?"
                + "mode=" + mode + "&"
                + "cont_no=" + cont_no + "&"
                + "no_ppkb=" + no_ppkb + "");
    }

    private ServiceCfsStrippingFacadeRemote lookupServiceCfsStrippingFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceCfsStrippingFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceCfsStrippingFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceCfsStrippingFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ServiceCfsStuffingFacadeRemote lookupServiceCfsStuffingFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceCfsStuffingFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceCfsStuffingFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceCfsStuffingFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ServiceCfsFacadeRemote lookupServiceCfsFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceCfsFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceCfsFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceCfsFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
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
     * @return the serviceCfs
     */
    public List<Object[]> getServiceCfs() {
        return serviceCfs;
    }

    /**
     * @param serviceCfs the serviceCfs to set
     */
    public void setServiceCfs(List<Object[]> serviceCfs) {
        this.serviceCfs = serviceCfs;
    }

    /**
     * @return the serviceCfsStuffing
     */
    public List<Object[]> getServiceCfsStuffing() {
        return serviceCfsStuffing;
    }

    /**
     * @param serviceCfsStuffing the serviceCfsStuffing to set
     */
    public void setServiceCfsStuffing(List<Object[]> serviceCfsStuffing) {
        this.serviceCfsStuffing = serviceCfsStuffing;
    }

    /**
     * @return the serviceCfsStripping
     */
    public List<Object[]> getServiceCfsStripping() {
        return serviceCfsStripping;
    }

    /**
     * @param serviceCfsStripping the serviceCfsStripping to set
     */
    public void setServiceCfsStripping(List<Object[]> serviceCfsStripping) {
        this.serviceCfsStripping = serviceCfsStripping;
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
     * @return the cont_no
     */
    public String getCont_no() {
        return cont_no;
    }

    /**
     * @param cont_no the cont_no to set
     */
    public void setCont_no(String cont_no) {
        this.cont_no = cont_no;
    }

}

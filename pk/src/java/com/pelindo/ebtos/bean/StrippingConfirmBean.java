/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceCfsFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceCfsStrippingFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceCfs;
import com.pelindo.ebtos.model.db.ServiceCfsStripping;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "strippingConfirmBean")
@ViewScoped
public class StrippingConfirmBean implements Serializable {

    MasterCommodityFacadeRemote masterCommodityFacade = lookupMasterCommodityFacadeRemote();
    ServiceCfsFacadeRemote serviceCfsFacade = lookupServiceCfsFacadeRemote();
    ServiceCfsStrippingFacadeRemote serviceCfsStrippingFacade = lookupServiceCfsStrippingFacadeRemote();
    private List<Object[]> serviceCfsStrippings;
    private List<Object[]> serviceCfss;
    private List<Object[]> commoditys;
    private ServiceCfsStripping serviceCfsStripping;
    private Date tanggal;

    /** Creates a new instance of StrippingConfirmBean */
    public StrippingConfirmBean() {
        serviceCfsStripping = new ServiceCfsStripping();
        serviceCfsStrippings = serviceCfsStrippingFacade.findAllNative();
        commoditys = masterCommodityFacade.findAllNative();
    }

    public void handleAdd(ActionEvent event) {
        serviceCfsStripping = new ServiceCfsStripping();
        serviceCfss = serviceCfsFacade.findStripping();
        this.tanggal=Calendar.getInstance().getTime();
    }

    public void handleEditDelete(ActionEvent event) {
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        serviceCfsStripping = serviceCfsStrippingFacade.find(id_cont);
    }

    public void handleConfirm(ActionEvent event) {
        boolean loggedIn = false;
        if (serviceCfsStripping.getContNo() == null || serviceCfsStripping.getCommodityCode() == null || serviceCfsStripping.getUnit() == null
                || serviceCfsStripping.getVolume() == null || serviceCfsStripping.getWeight() == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else {
            loggedIn = true;
            Date now = Calendar.getInstance().getTime();
            serviceCfsStripping.setPlacementDate(tanggal);
            serviceCfsStrippingFacade.edit(serviceCfsStripping);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
            serviceCfsStrippings = serviceCfsStrippingFacade.findAllNative();
            serviceCfss = serviceCfsFacade.findStripping();
            //setCommoditys(masterCommodityFacade.findAll());
        }
    }

    public void handleDelete(ActionEvent event) {
        serviceCfsStrippingFacade.remove(serviceCfsStripping);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        serviceCfsStrippings = serviceCfsStrippingFacade.findAllNative();
        serviceCfss = serviceCfsFacade.findStripping();
        //setCommoditys(masterCommodityFacade.findAll());
    }

    public void handleSelectContPick(ActionEvent event) {
        Integer id_cfs = (Integer) event.getComponent().getAttributes().get("idCfs");
        ServiceCfs cfs = serviceCfsFacade.find(id_cfs);
        serviceCfsStripping.setContNo(cfs.getContNo());
        serviceCfsStripping.setMlo(cfs.getMlo());
        serviceCfsStripping.setNoPpkb(cfs.getNoPpkb());
        tanggal=serviceCfsStripping.getPlacementDate();
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

    private ServiceCfsFacadeRemote lookupServiceCfsFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceCfsFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceCfsFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceCfsFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterCommodityFacadeRemote lookupMasterCommodityFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterCommodityFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterCommodityFacade!com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * @return the serviceCfsStrippings
     */
    public List<Object[]> getServiceCfsStrippings() {
        return serviceCfsStrippings;
    }

    /**
     * @param serviceCfsStrippings the serviceCfsStrippings to set
     */
    public void setServiceCfsStrippings(List<Object[]> serviceCfsStrippings) {
        this.serviceCfsStrippings = serviceCfsStrippings;
    }

    /**
     * @return the serviceCfss
     */
    public List<Object[]> getServiceCfss() {
        return serviceCfss;
    }

    /**
     * @param serviceCfss the serviceCfss to set
     */
    public void setServiceCfss(List<Object[]> serviceCfss) {
        this.serviceCfss = serviceCfss;
    }

    /**
     * @return the serviceCfsStripping
     */
    public ServiceCfsStripping getServiceCfsStripping() {
        return serviceCfsStripping;
    }

    /**
     * @param serviceCfsStripping the serviceCfsStripping to set
     */
    public void setServiceCfsStripping(ServiceCfsStripping serviceCfsStripping) {
        this.serviceCfsStripping = serviceCfsStripping;
    }

    /**
     * @return the commoditys
     */
    public List<Object[]> getCommoditys() {
        return commoditys;
    }

    /**
     * @param commoditys the commoditys to set
     */
    public void setCommoditys(List<Object[]> commoditys) {
        this.commoditys = commoditys;
    }
    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

}

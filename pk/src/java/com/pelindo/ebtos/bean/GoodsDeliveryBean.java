/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.DeliveryBarangServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceCfsStrippingFacadeRemote;
import com.pelindo.ebtos.model.db.DeliveryBarangService;
import com.pelindo.ebtos.model.db.ServiceCfsStripping;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "goodsDeliveryBean")
@ViewScoped
public class GoodsDeliveryBean implements Serializable {

    ServiceCfsStrippingFacadeRemote serviceCfsStrippingFacade = lookupServiceCfsStrippingFacadeRemote();
    DeliveryBarangServiceFacadeRemote deliveryBarangServiceFacade = lookupDeliveryBarangServiceFacadeRemote();
    private List<Object[]> serviceCfsStrippings = serviceCfsStrippingFacade.findDeliveryTrue();
    private Object[] deliveryBarangService;
    private ServiceCfsStripping serviceCfsStripping;
    private DeliveryBarangService deliveryBarangServiceObject;
    private String jobslip;
    private Boolean jb = Boolean.FALSE;
    private String act = "bl";
    private String bl_no;
    private List<Object[]> goodsDeliveryList;
    private Object[][] deliveryBarang;

    /** Creates a new instance of GoodsDeliveryBean */
    public GoodsDeliveryBean() {
        serviceCfsStripping = new ServiceCfsStripping();
        deliveryBarangServiceObject = new DeliveryBarangService();
        deliveryBarangService= new Object[0];
        this.act = "bl";
    }

    public void handleAdd(ActionEvent event) {
        serviceCfsStripping = new ServiceCfsStripping();
        jobslip = "";
        deliveryBarangServiceObject = new DeliveryBarangService();
    }

    public List<String> setListAutoComplete(String query) {
        List<String> results = new ArrayList<String>();
        results = deliveryBarangServiceFacade.findJobslipAutoComplete(query);
        return results;
    }

    public void ambilContNo(ActionEvent event) {
        jobslip = (String) event.getComponent().getAttributes().get("jobSlip");
        Object[] deliveryBarangService = deliveryBarangServiceFacade.findJobslip(jobslip);
        if (deliveryBarangService != null) {
            Integer temp = serviceCfsStrippingFacade.findId((String) deliveryBarangService[1], (String) deliveryBarangService[2], (String) deliveryBarangService[3]);
            serviceCfsStripping = serviceCfsStrippingFacade.find(temp);
            if (serviceCfsStripping.getJobslip() != null) {
                jb = Boolean.TRUE;
            } else {
                jb = Boolean.FALSE;
            }
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.data.notfound");
            serviceCfsStripping = new ServiceCfsStripping();
        }
    }

    public void handleConfirm(ActionEvent event) {
        Date now = Calendar.getInstance().getTime();
        serviceCfsStripping.setDeliveryDate(now);
        serviceCfsStripping.setJobslip(jobslip);
        serviceCfsStripping.setIsDelivery("TRUE");
        serviceCfsStrippingFacade.edit(serviceCfsStripping);
        serviceCfsStrippings = serviceCfsStrippingFacade.findDeliveryTrue();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
    }

    public void handleEditDelete(ActionEvent event) {
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        serviceCfsStripping = serviceCfsStrippingFacade.find(id_cont);
    }

    public void handleDelete(ActionEvent event) {
        serviceCfsStripping.setIsDelivery("FALSE");
        serviceCfsStripping.setDeliveryDate(null);
        serviceCfsStripping.setJobslip(null);
        serviceCfsStrippingFacade.edit(serviceCfsStripping);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        serviceCfsStrippings = serviceCfsStrippingFacade.findDeliveryTrue();
    }

    public void cariBlNo(ActionEvent event) {
        setBl_no((String) event.getComponent().getAttributes().get("blNo"));
        goodsDeliveryList = lookupDeliveryBarangServiceFacadeRemote().findDeliveryBarangServiceByList(bl_no);
        if (goodsDeliveryList.size() < 1) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        }
    }

    public void cariBlNo2(ActionEvent event) {
        setBl_no((String) event.getComponent().getAttributes().get("blNo"));
        goodsDeliveryList = lookupDeliveryBarangServiceFacadeRemote().findDeliveryBarangServiceByListAll();
        if (goodsDeliveryList.size() < 1) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        }
        this.act = "all";
    }

    public void handleSelectClik(ActionEvent event) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy-HHmmss");
        Date tgl = Calendar.getInstance().getTime();
        for (int i = 0; i < deliveryBarang.length; i++) {
            deliveryBarangServiceObject = lookupDeliveryBarangServiceFacadeRemote().find(deliveryBarang[i][0].toString());
            //deliveryBarangService = deliveryBarangServiceFacade.findJobslip(deliveryBarangServiceObject.getJobslip());
            if (deliveryBarangServiceObject != null) {
                Integer temp = serviceCfsStrippingFacade.findId(deliveryBarangServiceObject.getNoPpkb(), deliveryBarangServiceObject.getContNo(), deliveryBarangServiceObject.getBlNo());
                serviceCfsStripping = serviceCfsStrippingFacade.find(temp);
                if (serviceCfsStripping.getJobslip() != null) {
                    jb = Boolean.TRUE;
                } else {
                    jb = Boolean.FALSE;
                }
                Date now = Calendar.getInstance().getTime();
                serviceCfsStripping.setDeliveryDate(now);
                serviceCfsStripping.setJobslip(deliveryBarangServiceObject.getJobslip());
                serviceCfsStripping.setIsDelivery("TRUE");
                serviceCfsStrippingFacade.edit(serviceCfsStripping);
                serviceCfsStrippings = serviceCfsStrippingFacade.findDeliveryTrue();
            }
            if (getAct().equals("all")) {
                goodsDeliveryList = lookupDeliveryBarangServiceFacadeRemote().findDeliveryBarangServiceByListAll();
            } else {
                goodsDeliveryList = lookupDeliveryBarangServiceFacadeRemote().findDeliveryBarangServiceByList(bl_no);
            }
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        }
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

    private DeliveryBarangServiceFacadeRemote lookupDeliveryBarangServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (DeliveryBarangServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/DeliveryBarangServiceFacade!com.pelindo.ebtos.ejb.facade.remote.DeliveryBarangServiceFacadeRemote");
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
     * @return the deliveryBarangService
     */
    public Object[] getDeliveryBarangService() {
        return deliveryBarangService;
    }

    /**
     * @param deliveryBarangService the deliveryBarangService to set
     */
    public void setDeliveryBarangService(Object[] deliveryBarangService) {
        this.deliveryBarangService = deliveryBarangService;
    }

    /**
     * @return the serviceCfsStripping
     */
    public ServiceCfsStripping getServiceCfsStripping() {
        return serviceCfsStripping;
    }

    /**
     * @param serviceCfsStrippingthe serviceCfsStrippingto set
     */
    public void setServiceCfsStripping(ServiceCfsStripping serviceCfsStripping) {
        this.serviceCfsStripping = serviceCfsStripping;
    }

    /**
     * @return the jobslip
     */
    public String getJobslip() {
        return jobslip;
    }

    /**
     * @param jobslip the jobslip to set
     */
    public void setJobslip(String jobslip) {
        this.jobslip = jobslip;
    }

    /**
     * @return the jb
     */
    public Boolean getJb() {
        return jb;
    }

    /**
     * @param jb the jb to set
     */
    public void setJb(Boolean jb) {
        this.jb = jb;
    }

    public DeliveryBarangService getDeliveryBarangServiceObject() {
        return deliveryBarangServiceObject;
    }

    public void setDeliveryBarangServiceObject(DeliveryBarangService deliveryBarangServiceObject) {
        this.deliveryBarangServiceObject = deliveryBarangServiceObject;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getBl_no() {
        return bl_no;
    }

    public void setBl_no(String bl_no) {
        this.bl_no = bl_no;
    }

    public Object[][] getDeliveryBarang() {
        return deliveryBarang;
    }

    public void setDeliveryBarang(Object[][] deliveryBarang) {
        this.deliveryBarang = deliveryBarang;
    }

    public List<Object[]> getGoodsDeliveryList() {
        return goodsDeliveryList;
    }

    public void setGoodsDeliveryList(List<Object[]> goodsDeliveryList) {
        this.goodsDeliveryList = goodsDeliveryList;
    }
}

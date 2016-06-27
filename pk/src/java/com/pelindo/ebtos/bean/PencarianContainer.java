/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.DeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.model.db.DeliveryService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
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
@ManagedBean(name = "pencarianContainer")
@ViewScoped
public class PencarianContainer implements Serializable {

    private ReceivingServiceFacadeRemote receivingServiceFacadeRemote = lookupReceivingServiceFacadeRemote();
    private DeliveryServiceFacadeRemote deliveryServiceFacadeRemote = lookupDeliveryServiceFacadeRemote();
    private String noPpkb;
    private String tipe = "L";
    private List<Object[]> listObject;
    private DeliveryService deliveryService;
    private Boolean edit = Boolean.TRUE;

    /** Creates a new instance of PencarianContainer */
    public PencarianContainer() {
        deliveryService = new DeliveryService();
    }

    public void onChangeOperation(ValueChangeEvent event) {
        if (tipe.equalsIgnoreCase("L") && noPpkb != null) {
            listObject = receivingServiceFacadeRemote.findContainerDetail(noPpkb);
            this.edit = Boolean.TRUE;
        } else if (tipe.equalsIgnoreCase("D") && noPpkb != null) {
            listObject = deliveryServiceFacadeRemote.findContainerDetail(noPpkb);
            this.edit = Boolean.FALSE;
        }
    }

    public void handleClear() {
        this.noPpkb = null;
        listObject = new ArrayList<Object[]>();
        this.tipe = "L";
    }

    public void handleCariContainer() {
        System.out.print("no" + noPpkb);
        if (tipe.equalsIgnoreCase("L")) {
            listObject = receivingServiceFacadeRemote.findContainerDetail(noPpkb);
            this.edit = Boolean.TRUE;
        } else if(tipe.equalsIgnoreCase("D")){
            listObject = deliveryServiceFacadeRemote.findContainerDetail(noPpkb);
            this.edit = Boolean.FALSE;
        }
        System.out.print("no" + listObject.size());
    }

    public void handleEditDelete(ActionEvent event) {
        String jobSlip = (String) event.getComponent().getAttributes().get("job_slip");
        deliveryService = deliveryServiceFacadeRemote.find(jobSlip);
    }

    public void saveDeliveryService(ActionEvent event) {
        try {
            deliveryServiceFacadeRemote.edit(deliveryService);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (EJBException e) {
            e.printStackTrace();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }
    }

    public List<Object[]> getListObject() {
        return listObject;
    }

    public void setListObject(List<Object[]> listObject) {
        this.listObject = listObject;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    private ReceivingServiceFacadeRemote lookupReceivingServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ReceivingServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ReceivingServiceFacade!com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private DeliveryServiceFacadeRemote lookupDeliveryServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (DeliveryServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/DeliveryServiceFacade!com.pelindo.ebtos.ejb.facade.remote.DeliveryServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public DeliveryService getDeliveryService() {
        return deliveryService;
    }

    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

}

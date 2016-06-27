/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.local.InvoiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.DeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.model.db.DeliveryService;
import com.pelindo.ebtos.model.db.Invoice;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
@ManagedBean(name = "updatePrintInvoice")
@ViewScoped
public class UpdatePrintInvoice implements Serializable {

    @EJB InvoiceFacadeLocal invoiceFacadeLocal;

    String reg;
    Invoice invoice;

    public UpdatePrintInvoice() {
    }

    public void handleClear() {
        this.reg = null;
        invoice = new Invoice();
    }

    public void handleCariReg() {
        invoice = invoiceFacadeLocal.findByRegForUpdatePrintNumber(reg);
    }

    public void handleUpdatePrint() {
        invoice.setPrinted(0);
        invoiceFacadeLocal.edit(invoice);
    }

    public void saveDeliveryService(ActionEvent event) {
//        try {
//            deliveryServiceFacadeRemote.edit(deliveryService);
//            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
//        } catch (EJBException e) {
//            e.printStackTrace();
//            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
//        }
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
    
    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

}

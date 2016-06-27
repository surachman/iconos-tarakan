/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.BatalNotaFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.DeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "batalNotaBean")
@ViewScoped
public class BatalNotaBean implements Serializable {

    private List<Object[]> listView;

    /** Creates a new instance of BatalNotaBean */
    public BatalNotaBean() {
        listView=lookupBatalNotaFacadeRemote().findByBatalNotaList();
    }
   
    
    public List<Object[]> getListView() {
        return listView;
    }

    public void setListView(List<Object[]> listView) {
        this.listView = listView;
    }

    private BatalNotaFacadeRemote lookupBatalNotaFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (BatalNotaFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/BatalNotaFacade!com.pelindo.ebtos.ejb.facade.remote.BatalNotaFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}

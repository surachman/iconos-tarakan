/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterCountryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterCountry;
import com.pelindo.ebtos.model.db.master.MasterPort;
import java.io.Serializable;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "masterPortBean")
@ViewScoped
public class MasterPortBean implements Serializable {

    MasterCountryFacadeRemote masterCountryFacadeRemote = lookupMasterCountryFacadeRemote();
    MasterPortFacadeRemote masterPortFacadeRemote = lookupMasterPortFacadeRemote();
    private List<MasterPort> masterPorts;
    private MasterPort masterPort;
    private String idPort;
    private boolean delete;
    private boolean disable;
    public boolean loggedIn = false;

    /** Creates a new instance of MasterPortBean */
    public MasterPortBean() {
        masterPorts = lookupMasterPortFacadeRemote().findAll();
        masterPort = new MasterPort();
        masterPort.setMasterCountry(new MasterCountry());
        disable = false;
    }

    /**
     * @return the masterPort
     */
    public MasterPort getMasterPort() {
        return masterPort;
    }

    /**
     * @param masterPort the masterPort to set
     */
    public void setMasterPort(MasterPort masterPort) {
        this.masterPort = masterPort;
    }

    public List<MasterPort> getMasterPorts() {
        return masterPorts;

    }

    public List<MasterCountry> getMasterCountries() {
        return masterCountryFacadeRemote.findAll();
    }

    public void handleAddButton(ActionEvent event) {
        masterPort = new MasterPort();
        masterPort.setMasterCountry(new MasterCountry());
        disable = false;
    }

    public void handleEditButton(ActionEvent event) {
        idPort = (String) event.getComponent().getAttributes().get("idPort");
        masterPort = masterPortFacadeRemote.find(idPort);
        disable = true;
    }

    public void saveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean validate = false;

        if (masterPort.getPortCode() != null && masterPort.getName() != null) {
            validate = true;
            masterPortFacadeRemote.edit(masterPort);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            masterPorts = lookupMasterPortFacadeRemote().findAll();
        } else {
            validate = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        }
        context.addCallbackParam("validated", validate);
    }

    public void handleDeleteButton(ActionEvent event) {
        idPort = (String) event.getComponent().getAttributes().get("idPort");
        masterPort = masterPortFacadeRemote.find(idPort);
        //this.delete = true;
    }

    public void handleDelete(ActionEvent event) {        
        try {
            masterPortFacadeRemote.remove(masterPort);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
        }
        masterPorts = lookupMasterPortFacadeRemote().findAll();
    }

    public void handleDeleteAll(ActionEvent event) {
        this.delete = false;
    }

//    public void delete(ActionEvent event) {
//        if (this.delete) {
//            masterPort = masterPortFacadeRemote.find(idPort);
//            if (preserviceVesselFacadeRemote.findPreserviceVesselByPort(idPort).isEmpty()) {
//                masterPortFacadeRemote.remove(masterPort);
//                context.addMessage(null, new FacesMessage("Info", "Data berhasil dihapus"));
//            } else {
//                context.addMessage(null, new FacesMessage("Info", "Data <strong>" + masterPort.getPortCode() + "</strong> gagal dihapus! Karena mempunyai relasi dengan tabel lainnya."));
//            }
//        } else {
//            for (MasterPort contain : masterPortFacadeRemote.findAll()) {
//                if (preserviceVesselFacadeRemote.findPreserviceVesselByPort(contain.getPortCode()).isEmpty()) {
//                    masterPortFacadeRemote.remove(contain);
//                    context.addMessage(null, new FacesMessage("Info", "Data berhasil dihapus"));
//                } else {
//                    context.addMessage(null, new FacesMessage("Info", "Data <strong>" + contain.getPortCode() + "</strong> gagal dihapus! Karena mempunyai relasi dengan tabel lainnya."));
//                }
//            }
//        }
//        masterPorts = lookupMasterPortFacadeRemote().findAll();
//    }
    /**
     * @return the idPort
     */
    public String getIdPort() {
        return idPort;
    }

    /**
     * @param idPort the idPort to set
     */
    public void setIdPort(String idPort) {
        this.idPort = idPort;
    }

    /**
     * @return the delete
     */
    public boolean isDelete() {
        return delete;
    }

    /**
     * @param delete the delete to set
     */
    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    /**
     * @return the disable
     */
    public boolean isDisable() {
        return disable;
    }

    /**
     * @param disable the disable to set
     */
    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    private MasterPortFacadeRemote lookupMasterPortFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterPortFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterPortFacade!com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterCountryFacadeRemote lookupMasterCountryFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterCountryFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterCountryFacade!com.pelindo.ebtos.ejb.facade.remote.MasterCountryFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}

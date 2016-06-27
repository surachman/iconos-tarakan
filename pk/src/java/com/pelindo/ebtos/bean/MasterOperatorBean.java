/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import java.io.Serializable;
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
import org.primefaces.context.RequestContext;

/**
 *
 * @author Dyware-Dev01
 */
@ManagedBean(name = "masterOperatorBean")
@ViewScoped
public class MasterOperatorBean implements Serializable {

    MasterOperatorFacadeRemote masterOperatorFacadeRemote = lookupMasterOperatorFacadeRemote();
    List<MasterOperator> masterOperators;
    MasterOperator masterOperator;

    /** Creates a new instance of MasterOperatorBean */
    public MasterOperatorBean() {
        masterOperators = lookupMasterOperatorFacadeRemote().findAll();
        masterOperator = new MasterOperator();
    }

    public MasterOperator getMasterOperator() {
        return masterOperator;
    }

    public void setMasterOperator(MasterOperator masterOperator) {
        this.masterOperator = masterOperator;
    }

    public List<MasterOperator> getMasterOperators() {
        return masterOperators;
    }

    public void setMasterOperators(List<MasterOperator> masterOperators) {
        this.masterOperators = masterOperators;
    }

    /** Function Event **/
    public void handleAdd(ActionEvent actionEvent) {
        masterOperator = new MasterOperator();
    }

    public void handleSaveEdit(ActionEvent actionEvent) {
        RequestContext context = RequestContext.getCurrentInstance();      
        boolean validate = false;

        if (masterOperator.getOptCode() != null && masterOperator.getOptCode() != null) {
            validate = true;
            masterOperatorFacadeRemote.edit(masterOperator);
            //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Data berhasil disimpan!");
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            masterOperators = lookupMasterOperatorFacadeRemote().findAll();
        } else {
            validate = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        }
        context.addCallbackParam("validated", validate);
    }

    public void handleSelectTable(ActionEvent event) {
        String op_code = (String) event.getComponent().getAttributes().get("opCode");
        masterOperator = masterOperatorFacadeRemote.find(op_code);
    }

    public void handleDelete(ActionEvent event) {
        try {
            masterOperatorFacadeRemote.remove(masterOperator);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
        }
        masterOperators = lookupMasterOperatorFacadeRemote().findAll();
    }

    /** Lookup Facade **/
    private MasterOperatorFacadeRemote lookupMasterOperatorFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterOperatorFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterOperatorFacade!com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}

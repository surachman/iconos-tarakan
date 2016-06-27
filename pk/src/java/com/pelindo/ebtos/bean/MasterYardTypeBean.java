/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardTypeFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterYardType;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author wulan
 */
@ManagedBean(name = "masterYardTypeBean")
@ViewScoped
public class MasterYardTypeBean implements Serializable {

    @EJB
    MasterYardTypeFacadeRemote masterYardTypeFacadeRemote;
    private MasterYardType masterYardType;
    private List<MasterYardType> masterYards;

    /** Creates a new instance of MasterYardTypeBean */
    public MasterYardTypeBean() {
    }

    @PostConstruct
    private void construct() {
        masterYardType = new MasterYardType();
        masterYards = masterYardTypeFacadeRemote.findAll();
    }

    public void handleEditButton(ActionEvent event){
        Integer id=(Integer) event.getComponent().getAttributes().get("idType");
        masterYardType=masterYardTypeFacadeRemote.find(id);
    }

    public void saveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean validate = false;
        try {
            validate = true;
            masterYardTypeFacadeRemote.edit(masterYardType);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            masterYards = masterYardTypeFacadeRemote.findAll();
        } catch (EJBException e) {
            e.printStackTrace();
            validate = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        }
        context.addCallbackParam("validated", validate);
    }

    public void handleDelete(ActionEvent event){
        try {
            masterYardTypeFacadeRemote.remove(masterYardType);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            masterYards = masterYardTypeFacadeRemote.findAll();
        } catch (EJBException e) {
            e.printStackTrace();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.failed");
        }
    }

    public void handleAddButton(ActionEvent event){
        masterYardType=new MasterYardType();
    }

    public MasterYardType getMasterYardType() {
        return masterYardType;
    }

    public void setMasterYardType(MasterYardType masterYardType) {
        this.masterYardType = masterYardType;
    }

    public List<MasterYardType> getMasterYards() {
        return masterYards;
    }

    public void setMasterYards(List<MasterYardType> masterYards) {
        this.masterYards = masterYards;
    }
}

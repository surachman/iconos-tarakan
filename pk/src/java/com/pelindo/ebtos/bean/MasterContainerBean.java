/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.exception.DeleteNotDeletableContainerException;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "masterContainerBean")
@ViewScoped
public class MasterContainerBean implements Serializable {
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private PlanningContReceivingFacadeRemote planningContReceivingFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private MasterContainerFacadeRemote masterContainerFacadeRemote;

    private List<Object[]> serviceContainers;
    private ServiceContDischarge serviceContDischarge;
    private PlanningContReceiving planningContReceiving;
    private MasterCommodity masterCommodity;
    private Boolean isDischarge;
    private String noPPKB;
    private String contNo;

    /** Creates a new instance of MasterContainerBean */
    public MasterContainerBean() {}

    @PostConstruct
    private void construct() {
        noPPKB = "";
        contNo = "";
        isDischarge = true;
    }

    public void onChangeOperation(ValueChangeEvent event) {
        if (isDischarge) {
            if (noPPKB.equalsIgnoreCase(null) || noPPKB.equalsIgnoreCase("")) {
                serviceContainers = serviceContDischargeFacadeRemote.findContainerDetailsPPKBnull(contNo);
            } else if (contNo.equalsIgnoreCase(null) || contNo.equalsIgnoreCase("")) {
                serviceContainers = serviceContDischargeFacadeRemote.findContainerDetailsCONTnull(noPPKB);
            } else {
                serviceContainers = serviceContDischargeFacadeRemote.findContainerDetails(noPPKB, contNo);
            }
        } else {
            if (noPPKB.equalsIgnoreCase(null) || noPPKB.equalsIgnoreCase("")) {
                serviceContainers = planningContReceivingFacadeRemote.findContainerDetailsPPKBnull(contNo);
            } else if (contNo.equalsIgnoreCase(null) || contNo.equalsIgnoreCase("")) {
                serviceContainers = planningContReceivingFacadeRemote.findContainerDetailsCONTnull(noPPKB);
            } else {
                serviceContainers = planningContReceivingFacadeRemote.findContainerDetails(noPPKB, contNo);
            }
        }
    }

    public void handleEditDeleteButton(ActionEvent event) {
        int idContainer = (Integer) event.getComponent().getAttributes().get("idCont");

        if (isDischarge) {
            serviceContDischarge = serviceContDischargeFacadeRemote.find(idContainer);
            masterCommodity = serviceContDischarge.getMasterCommodity();
            contNo = serviceContDischarge.getContNo();
        } else {
            planningContReceiving = planningContReceivingFacadeRemote.find(idContainer);
            masterCommodity = planningContReceiving.getMasterCommodity();
            contNo = planningContReceiving.getContNo();
        }

        RequestContext.getCurrentInstance().addCallbackParam("isDischarge", isDischarge);
    }
    
    String contNoForDelete = "";
    String noPpkbForDelete = "";

    public void prepareHandleDeleteDischarge(ActionEvent event) {
        contNoForDelete = (String) event.getComponent().getAttributes().get("contNo");
        noPpkbForDelete = (String) event.getComponent().getAttributes().get("noPpkb");

    }

    public void handleDeleteDischarge(ActionEvent event) {
        try {
            masterContainerFacadeRemote.handleDeleteDischargeContainer(contNoForDelete, noPpkbForDelete);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.delete.succeeded");
        } catch (DeleteNotDeletableContainerException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "application.validation.delete_not_deletable_container");
        } catch (RuntimeException re) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "application.delete.failed");
            Logger.getLogger(MasterContainerBean.class.getName()).log(Level.SEVERE, "exception caugh when call handleDeleteDischarge", re);
        }

        contNo = "";
        contNoForDelete = "";
        noPpkbForDelete = "";
        search();
    }

    public List<String> containerNumbers(String query) {
        contNo = query;
        
        if (isDischarge) {
            return serviceContDischargeFacadeRemote.findContainerNumbers(noPPKB, contNo);
        } else {
            return planningContReceivingFacadeRemote.findContainerNumbers(noPPKB, contNo);
        }
    }

    public List<String> ppkbNumbers(String query) {
        noPPKB = query;
        
        if (isDischarge) {
            return serviceVesselFacadeRemote.findPpkbNumbers(noPPKB);
        } else {
            return planningVesselFacadeRemote.findPpkbNumbers(noPPKB);
        }
    }

    public void search() {
        if (isDischarge) {
            if (noPPKB.equalsIgnoreCase(null) || noPPKB.equalsIgnoreCase("")) {
                serviceContainers = serviceContDischargeFacadeRemote.findContainerDetailsPPKBnull(contNo);
            } else if (contNo.equalsIgnoreCase(null) || contNo.equalsIgnoreCase("")) {
                serviceContainers = serviceContDischargeFacadeRemote.findContainerDetailsCONTnull(noPPKB);
            } else {
                serviceContainers = serviceContDischargeFacadeRemote.findContainerDetails(noPPKB, contNo);
            }
        } else {
            if (noPPKB.equalsIgnoreCase(null) || noPPKB.equalsIgnoreCase("")) {
                serviceContainers = planningContReceivingFacadeRemote.findContainerDetailsPPKBnull(contNo);
            } else if (contNo.equalsIgnoreCase(null) || contNo.equalsIgnoreCase("")) {
                serviceContainers = planningContReceivingFacadeRemote.findContainerDetailsCONTnull(noPPKB);
            } else {
                serviceContainers = planningContReceivingFacadeRemote.findContainerDetails(noPPKB, contNo);
            }
        }
    }

    public void saveEdit() {
        boolean loggedIn = true;
        
        if (isDischarge) {
            serviceContDischargeFacadeRemote.saveMasterContainer(serviceContDischarge,contNo);
            if (noPPKB.equalsIgnoreCase(null) || noPPKB.equalsIgnoreCase("")) {
                serviceContainers = serviceContDischargeFacadeRemote.findContainerDetailsPPKBnull(serviceContDischarge.getContNo());
            } else if (contNo.equalsIgnoreCase(null) || contNo.equalsIgnoreCase("")) {
                serviceContainers = serviceContDischargeFacadeRemote.findContainerDetailsCONTnull(noPPKB);
            } else {
                serviceContainers = serviceContDischargeFacadeRemote.findContainerDetails(noPPKB, serviceContDischarge.getContNo());
            }
        } else {
            planningContReceivingFacadeRemote.saveMasterContainer(planningContReceiving,contNo);
            if (noPPKB.equalsIgnoreCase(null) || noPPKB.equalsIgnoreCase("")) {
                serviceContainers = planningContReceivingFacadeRemote.findContainerDetailsPPKBnull(planningContReceiving.getContNo());
            } else if (contNo.equalsIgnoreCase(null) || contNo.equalsIgnoreCase("")) {
                serviceContainers = planningContReceivingFacadeRemote.findContainerDetailsCONTnull(noPPKB);
            } else {
                serviceContainers = planningContReceivingFacadeRemote.findContainerDetails(noPPKB, planningContReceiving.getContNo());
            }
        }
        
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.save.succeeded");
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void onChangeConstraintType() {
        if (isDischarge) {
            MasterContainerType masterContainerType = masterContainerTypeFacadeRemote.find(serviceContDischarge.getMasterContainerType().getContType());
            serviceContDischarge.setMasterContainerType(masterContainerType);
            serviceContDischarge.setContSize(masterContainerType.getFeetMark());
        } else {
            MasterContainerType masterContainerType = masterContainerTypeFacadeRemote.find(planningContReceiving.getMasterContainerType().getContType());
            planningContReceiving.setMasterContainerType(masterContainerType);
            planningContReceiving.setContSize(masterContainerType.getFeetMark());
        }
    }

    public void handleSelectCommodity() {
        if (isDischarge) {
            serviceContDischarge.setMasterCommodity(masterCommodity);
            serviceContDischarge.setDangerous("FALSE");

            if (serviceContDischarge.getMasterCommodity() != null && serviceContDischarge.getMasterCommodity().getMasterDangerousClass() != null) {
                serviceContDischarge.setDangerous("TRUE");
            }
        } else {
            planningContReceiving.setMasterCommodity(masterCommodity);
            planningContReceiving.setDg("FALSE");

            if (planningContReceiving.getMasterCommodity() != null && planningContReceiving.getMasterCommodity().getMasterDangerousClass() != null) {
                planningContReceiving.setDg("TRUE");
            }
        }
    }
    
    public String getNoPPKB() {
        return noPPKB;
    }

    public void setNoPPKB(String noPPKB) {
        this.noPPKB = noPPKB;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public Boolean getIsDischarge() {
        return isDischarge;
    }

    public void setIsDischarge(Boolean isDischarge) {
        this.isDischarge = isDischarge;
    }

    public List<Object[]> getServiceContainers() {
        return serviceContainers;
    }

    public ServiceContDischarge getServiceContDischarge() {
        return serviceContDischarge;
    }

    public void setServiceContDischarge(ServiceContDischarge serviceContDischarge) {
        this.serviceContDischarge = serviceContDischarge;
    }

    public PlanningContReceiving getPlanningContReceiving() {
        return planningContReceiving;
    }

    public void setPlanningContReceiving(PlanningContReceiving planningContReceiving) {
        this.planningContReceiving = planningContReceiving;
    }

    public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    public void setMasterCommodity(MasterCommodity masterCommodity) {
        this.masterCommodity = masterCommodity;
    }
}

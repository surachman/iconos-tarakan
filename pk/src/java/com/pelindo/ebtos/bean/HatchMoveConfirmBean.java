/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselProfileFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceHatchMovesFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.ServiceHatchMoves;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "hatchMoveConfirmBean")
@ViewScoped
public class HatchMoveConfirmBean implements Serializable {
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacade;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacade;
    @EJB
    private EquipmentFacadeRemote equipmentFacadeRemote;
    @EJB
    private ServiceHatchMovesFacadeRemote serviceHatchMovesFacadeRemote;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacadeRemote;
    @EJB
    private MasterVesselFacadeRemote masterVesselFacadeRemote;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacade;

    private List<MasterOperator> masterOperators;
    private List<Object[]> serviceVessels;
    private List<Object[]> serviceHatchMoves;
    private List<Object[]> masterCranes;
    private List<Integer> masterVesselProfile;
    private ServiceVessel serviceVessell;
    private Object[] serviceVessel = null;
    private String no_ppkb;
    private Equipment equipment;
    private boolean disableOptCrane;
    private ServiceHatchMoves serviceHatchMove;

    /** Creates a new instance of HatchMoveConfirmBean */
    public HatchMoveConfirmBean() {}

    @PostConstruct
    private void construct() {
        equipment = new Equipment();
        equipment.setMasterEquipment(new MasterEquipment());
        equipment.setMasterOperator(new MasterOperator());
        serviceHatchMove = new ServiceHatchMoves();
        serviceVessell = new ServiceVessel();
        disableOptCrane = true;
        serviceVessels = serviceVesselFacadeRemote.findServiceVesselsServicing();
        masterOperators = masterOperatorFacade.findAll();
        masterCranes = masterEquipmentFacade.findCraneForView();
    }

    public void clearSave() {
        equipment = new Equipment();
        equipment.setMasterEquipment(new MasterEquipment());
        equipment.setMasterOperator(new MasterOperator());
        serviceHatchMove = new ServiceHatchMoves();
        serviceVessell = new ServiceVessel();
        disableOptCrane = true;
    }

    public void handleOnSelect(ActionEvent event) {
        setNo_ppkb((String) event.getComponent().getAttributes().get("noPpkb"));
        setServiceVessel(serviceVesselFacadeRemote.findServiceVesselByPpkb(getNo_ppkb()));
        serviceHatchMoves = serviceHatchMovesFacadeRemote.findServiceHatchMovesByPpkb(getNo_ppkb());
        String vessel = (String) serviceVessel[4];

        MasterVessel masterVessel = masterVesselFacadeRemote.find(vessel);
        masterVesselProfile = new ArrayList<Integer>();
        if (masterVessel.getJumlahPalka() != null) {
            for (int i = 0; i < masterVessel.getJumlahPalka(); i++) {
                masterVesselProfile.add(i + 1);
            }
        }
    }

    public void handleAdd(ActionEvent actionEvent) {
        clearSave();
    }

    public void handleSelectCrane(ActionEvent event) {
        String idCrane = (String) event.getComponent().getAttributes().get("idCrane");
        equipment.setMasterEquipment(masterEquipmentFacade.find(idCrane));
        disableOptCrane = false;
        //crane.setMasterEquipment(masterEquipmentFacadeRemote.find(idCrane));        
    }

    public void handleSelectOperatorCrane(ActionEvent event) {
        String idOperatorCrane = (String) event.getComponent().getAttributes().get("idOperatorCrane");
        equipment.setMasterOperator(masterOperatorFacade.find(idOperatorCrane));
    }

    public void handleEditDeleteButton(ActionEvent event) {
        Integer idBay = ((BigDecimal) event.getComponent().getAttributes().get("idBay")).intValue();
        Integer idEquip = ((BigDecimal) event.getComponent().getAttributes().get("idEquip")).intValue();
        equipment = equipmentFacadeRemote.find(idEquip);
        serviceHatchMove = serviceHatchMovesFacadeRemote.find(idBay);
        //setEquipment(equipment=equipmentFacadeRemote.find(idBay));
    }

    public void handleRefreshUc(ActionEvent event){
        serviceHatchMoves = serviceHatchMovesFacadeRemote.findServiceHatchMovesByPpkb(getNo_ppkb());
    }

    public void handleDeleteButton(ActionEvent event) {
        try {
            equipmentFacadeRemote.remove(equipment);
            serviceHatchMovesFacadeRemote.remove(serviceHatchMove);
            serviceHatchMoves = serviceHatchMovesFacadeRemote.findServiceHatchMovesByPpkb(getNo_ppkb());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            this.clearSave();
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.failed");
            ex.printStackTrace();
        }

    }

    public void handleSubmit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;

//        Object[] temp = null;

//        temp = equipmentFacadeRemote.findEquipmentByValidasi(equipment.getStartTime(),equipment.getEndTime(),serviceHatchMove.getHatchMoves(),serviceHatchMove.getOperation());

        if (equipment.getMasterEquipment().getEquipCode() == null || equipment.getMasterOperator().getOptCode() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (equipment.getEndTime().equals(equipment.getStartTime()) || equipment.getStartTime().after(equipment.getEndTime())) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.time");
        } else {
            loggedIn = true;
            setServiceVessell(serviceVesselFacadeRemote.find(getNo_ppkb()));
            equipment.setEquipmentActCode(serviceHatchMove.getHatchMoves());
            equipment.setOperation(serviceHatchMove.getOperation());
            equipment.setPlanningVessel(serviceVessell.getPlanningVessel());
            equipmentFacadeRemote.edit(equipment);

            int id = equipmentFacadeRemote.findEquipmentId(getNo_ppkb(), equipment.getStartTime(), equipment.getEndTime(), equipment.getEquipmentActCode(), equipment.getOperation());

            serviceHatchMove.setNoPpkb(getNo_ppkb());
            serviceHatchMove.setIdEquipment(id);
//            if (serviceHatchMove.getCrane().equals("L")) {
//                serviceHatchMove.setIsPaid(Boolean.TRUE);
//
//            } else {
            serviceHatchMove.setIsPaid("TRUE");
//
//            }

            String hatchMoveActivityCode = masterActivityFacade.translateHatchMoveActivityCode(serviceHatchMove.getCrane());
            MasterActivity hatchMoveMasterActivity = masterActivityFacade.find(hatchMoveActivityCode);
            serviceHatchMove.setMasterActivity(hatchMoveMasterActivity);
            
            serviceHatchMove.setHatchMoveDate(equipment.getEndTime());
            serviceHatchMovesFacadeRemote.edit(serviceHatchMove);
            serviceHatchMoves = serviceHatchMovesFacadeRemote.findServiceHatchMovesByPpkb(getNo_ppkb());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            this.clearSave();
        }
        context.addCallbackParam("loggedIn", loggedIn);
    }

    //handling crane
    public void onChangeCrane(ValueChangeEvent event) {
        String newCrane = (String) event.getNewValue();
        serviceHatchMove.setCrane(newCrane);
        if (newCrane.equalsIgnoreCase("S")) {
            equipment.setMasterEquipment(masterEquipmentFacade.find("EQ000"));
            equipment.setMasterOperator(masterOperatorFacade.find("0"));
        } else if (newCrane.equalsIgnoreCase("L")) {
            equipment.setMasterEquipment(new MasterEquipment());
            equipment.setMasterOperator(new MasterOperator());
        }
    }

    /**
     * @return the serviceHatchMove
     */
    public ServiceHatchMoves getServiceHatchMove() {
        return serviceHatchMove;
    }

    /**
     * @param serviceHatchMove the serviceHatchMove to set
     */
    public void setServiceHatchMove(ServiceHatchMoves serviceHatchMove) {
        this.serviceHatchMove = serviceHatchMove;
    }

    /**
     * @return the serviceHatchMoves
     */
    public List<Object[]> getServiceHatchMoves() {
        return serviceHatchMoves;
    }

    /**
     * @param serviceHatchMoves the serviceHatchMoves to set
     */
    public void setServiceHatchMoves(List<Object[]> serviceHatchMoves) {
        this.serviceHatchMoves = serviceHatchMoves;
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
     * @return the serviceVessel
     */
    public Object[] getServiceVessel() {
        return serviceVessel;
    }

    /**
     * @param serviceVessel the serviceVessel to set
     */
    public void setServiceVessel(Object[] serviceVessel) {
        this.serviceVessel = serviceVessel;
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
     * @return the equipment
     */
    public Equipment getEquipment() {
        return equipment;
    }

    /**
     * @param equipment the equipment to set
     */
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    /**
     * @return the masterOperators
     */
    public List<MasterOperator> getMasterOperators() {
        return masterOperators;
    }

    /**
     * @param masterOperators the masterOperators to set
     */
    public void setMasterOperators(List<MasterOperator> masterOperators) {
        this.masterOperators = masterOperators;
    }

    /**
     * @return the masterCranes
     */
    public List<Object[]> getMasterCranes() {
        return masterCranes;
    }

    /**
     * @param masterCranes the masterCranes to set
     */
    public void setMasterCranes(List<Object[]> masterCranes) {
        this.masterCranes = masterCranes;
    }

    /**
     * @return the disableOptCrane
     */
    public boolean isDisableOptCrane() {
        return disableOptCrane;
    }

    /**
     * @param disableOptCrane the disableOptCrane to set
     */
    public void setDisableOptCrane(boolean disableOptCrane) {
        this.disableOptCrane = disableOptCrane;
    }

    /**
     * @return the serviceVessell
     */
    public ServiceVessel getServiceVessell() {
        return serviceVessell;
    }

    /**
     * @param serviceVessell the serviceVessell to set
     */
    public void setServiceVessell(ServiceVessel serviceVessell) {
        this.serviceVessell = serviceVessell;
    }

    /**
     * @return the masterVesselProfile
     */
    public List<Integer> getMasterVesselProfile() {
        return masterVesselProfile;
    }

    /**
     * @param masterVesselProfile the masterVesselProfile to set
     */
    public void setMasterVesselProfile(List<Integer> masterVesselProfile) {
        this.masterVesselProfile = masterVesselProfile;
    }
}

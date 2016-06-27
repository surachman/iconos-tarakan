/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterProfileDetailFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselProfileFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcShiftingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.UcShiftingService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "shiftingConfirmWithoutPlan")
@ViewScoped
public class ShiftingConfirmWithoutPlan implements Serializable {
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacade;
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacade;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacadeRemote;
    @EJB
    private ServiceShiftingFacadeRemote serviceShiftingFacadeRemote;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote;
    @EJB
    private ServiceContLoadFacadeRemote serviceContLoadFacadeRemote;
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacadeRemote;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacadeRemote;
    @EJB
    private EquipmentFacadeRemote equipmentFacadeRemote;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacade;
    @EJB
    private UcShiftingServiceFacadeRemote ucShiftingServiceFacadeRemote;
    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacadeRemote;
    @EJB
    private MasterVesselProfileFacadeRemote masterVesselProfileFacadeRemote;
    @EJB
    private MasterProfileDetailFacadeRemote masterProfileDetailFacadeRemote;

    private List<MasterOperator> masterOperators;
    private List<ServiceShifting> serviceShiftings;
    private List<Object[]> serviceShiftingList;
    private List<Object[]> serviceListGabunganLoadDischs;
    private List<Object[]> serviceVessels;
    private List<Object[]> masterCranes;
    private List<Object[]> serviceShiftingUcList;
    private List<Object[]> serviceUcList;
    private List<Integer> bayChoices;
    private List<Integer> rowChoices;
    private List<Integer> tierChoices;
    private List<Integer> rowUps;
    private List<Integer> rowBottoms;
    private Equipment equipment;
    private Equipment equipmentRe;
    private ServiceShifting serviceShifting;
    private ServiceVessel serviceVessell;
    private ServiceContLoad serviceContLoad;
    private ServiceContDischarge serviceContDischarge;
    private UncontainerizedService uncontainerizedService;
    private UcShiftingService ucShiftingService;
    private Object[] serviceVessel;
    private String no_ppkb;
    private String newItem;
    private String cont_no;
    private String cont_status;
    private String Charge;
    private String vesselCode;
    private Integer cont_type;
    private Integer container_no;
    private Integer idEquipp;
    private boolean visiFBay = Boolean.TRUE;
    private boolean visiFPos = Boolean.TRUE;
    private boolean visiFRow = Boolean.TRUE;
    private boolean visiFTier = Boolean.TRUE;
    private boolean visiToBay = Boolean.TRUE;
    private boolean visiToPos = Boolean.TRUE;
    private boolean visiToRow = Boolean.TRUE;
    private boolean visiToTier = Boolean.TRUE;
    private boolean disableCrane;
    private boolean disableOptCrane;
    private boolean disable = Boolean.FALSE;
    private short cont_size;

    /** Creates a new instance of ShiftingConfirmBean */
    public ShiftingConfirmWithoutPlan() {}

    @PostConstruct
    private void construct() {
        bayChoices = new ArrayList<Integer>();
        rowChoices = new ArrayList<Integer>();
        tierChoices = new ArrayList<Integer>();
        rowUps = new ArrayList<Integer>();
        rowBottoms = new ArrayList<Integer>();

        serviceContDischarge = new ServiceContDischarge();
        serviceContLoad = new ServiceContLoad();
        equipment = new Equipment();
        equipment.setMasterEquipment(new MasterEquipment());
        equipment.setMasterOperator(new MasterOperator());
        serviceShifting = new ServiceShifting();
        uncontainerizedService = new UncontainerizedService();
        ucShiftingService = new UcShiftingService();
        serviceListGabunganLoadDischs = new LinkedList<Object[]>();
        serviceContDischarge = new ServiceContDischarge();
        serviceContLoad = new ServiceContLoad();
        
        cont_size = 0;
        cont_type = 0;
        disable = Boolean.FALSE;
        Charge = "LANDED";
        newItem = "LOAD";
        disableCrane = true;
        disableOptCrane = true;

        masterOperators = masterOperatorFacade.findAll();
        serviceVessels = serviceVesselFacade.findServiceVesselServicing();
        masterCranes = masterEquipmentFacade.findCraneForView();
    }

    public void clear() {
        serviceContDischarge = new ServiceContDischarge();
        serviceContLoad = new ServiceContLoad();
        equipment = new Equipment();
        equipment.setMasterEquipment(new MasterEquipment());
        equipment.setMasterOperator(new MasterOperator());
        serviceShifting = new ServiceShifting();
        uncontainerizedService = new UncontainerizedService();
        ucShiftingService = new UcShiftingService();
        newItem = "LOAD";
        serviceContDischarge = new ServiceContDischarge();
        serviceContLoad = new ServiceContLoad();
        Charge = "LANDED";

        cont_no = null;
        cont_size = 0;
        cont_type = 0;
        cont_status = null;
        this.disable = Boolean.FALSE;
        this.visiFBay = Boolean.TRUE;
        this.visiFPos = Boolean.TRUE;
        this.visiFRow = Boolean.TRUE;
        this.visiFTier = Boolean.TRUE;
        this.visiToBay = Boolean.TRUE;
        this.visiToPos = Boolean.TRUE;
        this.visiToRow = Boolean.TRUE;
        this.visiToTier = Boolean.TRUE;
    }

    public void handleAdd(ActionEvent actionEvent) {
        clear();
        serviceShifting.setVBay(Short.parseShort(bayChoices.get(0).toString()));
        changeBay(serviceShifting.getVBay());
        changeRow(Short.parseShort(rowChoices.get(0).toString()));
    }

    public void handleRefresh(ActionEvent event){
        serviceShiftingList = serviceShiftingFacadeRemote.findServiceShiftingReshippingWithout(getNo_ppkb());
    }

    public void handleRefreshUc(ActionEvent event){
        serviceShiftingUcList = ucShiftingServiceFacadeRemote.findUcShiftingServiceWithoutPlan(no_ppkb);
    }

    public void handleOnSelect(ActionEvent event) {
        setNo_ppkb((String) event.getComponent().getAttributes().get("noPpkb"));
        setServiceVessel(serviceVesselFacadeRemote.findServiceVesselByPpkb(getNo_ppkb()));
        serviceShiftingList = serviceShiftingFacadeRemote.findServiceShiftingReshippingWithout(getNo_ppkb());
        serviceShiftingUcList = ucShiftingServiceFacadeRemote.findUcShiftingServiceWithoutPlan(no_ppkb);
        serviceUcList = uncontainerizedServiceFacadeRemote.findShiftingWithout(no_ppkb);
        //setServiceContLoadsListObjek(serviceContLoadFacadeRemote.findServiceContLoadsByPpkb(getNo_ppkb()));

        vesselCode = getServiceVessel()[4].toString();
        getBayChoices().clear();
        bayChoices = masterVesselProfileFacadeRemote.findBaysByVessel(vesselCode);
    }

    public void handleSelectCrane(ActionEvent event) {
        String idCrane = (String) event.getComponent().getAttributes().get("idCrane");
        equipment.setMasterEquipment(masterEquipmentFacadeRemote.find(idCrane));
        disableOptCrane = false;
    }

    public void handleDeleteButton(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (getIdEquipp() != null) {
                equipmentFacadeRemote.remove(equipment);
                equipmentFacadeRemote.remove(equipmentRe);
                serviceShiftingFacadeRemote.remove(serviceShifting);
                serviceShiftingList = serviceShiftingFacadeRemote.findServiceShiftingReshippingWithout(getNo_ppkb());
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            } else {
                equipmentFacadeRemote.remove(equipment);
                serviceShiftingFacadeRemote.remove(serviceShifting);
                serviceShiftingList = serviceShiftingFacadeRemote.findServiceShiftingReshippingWithout(getNo_ppkb());
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            }
            clear();
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
        }

    }

    public void handleDeleteButtonUc(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (getIdEquipp() != null) {
                equipmentFacadeRemote.remove(equipment);
                equipmentFacadeRemote.remove(equipmentRe);
                ucShiftingServiceFacadeRemote.remove(ucShiftingService);
                serviceShiftingUcList = ucShiftingServiceFacadeRemote.findUcShiftingServiceWithoutPlan(no_ppkb);
                serviceUcList = uncontainerizedServiceFacadeRemote.findShiftingWithout(no_ppkb);
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            } else {
                equipmentFacadeRemote.remove(equipment);
                ucShiftingServiceFacadeRemote.remove(ucShiftingService);
                serviceShiftingUcList = ucShiftingServiceFacadeRemote.findUcShiftingServiceWithoutPlan(no_ppkb);
                serviceUcList = uncontainerizedServiceFacadeRemote.findShiftingWithout(no_ppkb);
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            }
            clear();
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
        }

    }

    public void handleEditDeleteButtonUr(ActionEvent event) {
        Integer idBay = (Integer) event.getComponent().getAttributes().get("idCont");
        Integer idU = (Integer) event.getComponent().getAttributes().get("idU");
        ucShiftingService = ucShiftingServiceFacadeRemote.find(idBay);
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(idU);
        this.disable = Boolean.TRUE;
    }

    public void handleEditDeleteButton2(ActionEvent event) {
        Integer idBay = (Integer) event.getComponent().getAttributes().get("idCont");
        serviceShifting = serviceShiftingFacadeRemote.find(idBay);

        changeBay(serviceShifting.getVBay());
        changeRow(serviceShifting.getVRow());
    }

    public void handleSelectContRe(ActionEvent event) {
        Integer idBay = (Integer) event.getComponent().getAttributes().get("idCont");
        Integer idEquip = (Integer) event.getComponent().getAttributes().get("idQ");
        serviceShifting = serviceShiftingFacadeRemote.find(idBay);
        if (idEquip != null) {
            equipment = equipmentFacadeRemote.find(idEquip);
        } else {
            equipment = new Equipment();
        }

        changeBay(serviceShifting.getVBay());
        changeRow(serviceShifting.getVRow());
    }

    public void handleSelectContReUc(ActionEvent event) {
        Integer idBay = (Integer) event.getComponent().getAttributes().get("idCont");
        Integer idEquip = (Integer) event.getComponent().getAttributes().get("idQ");
        Integer idU = (Integer) event.getComponent().getAttributes().get("idU");
        ucShiftingService = ucShiftingServiceFacadeRemote.find(idBay);
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(idU);
        if (idEquip != null) {
            equipment = equipmentFacadeRemote.find(idEquip);
        } else {
            equipment = new Equipment();
        }
        this.disable = Boolean.TRUE;
    }

    public void handleEditDeleteButtonRe(ActionEvent event) {
        Integer idBay = (Integer) event.getComponent().getAttributes().get("idCont");
        Integer idEquip = (Integer) event.getComponent().getAttributes().get("idQ");
        setIdEquipp((Integer) event.getComponent().getAttributes().get("idQQ"));
        if (getIdEquipp() != null) {
            equipment = equipmentFacadeRemote.find(idEquip);
            equipmentRe = equipmentFacadeRemote.find(getIdEquipp());
            serviceShifting = serviceShiftingFacadeRemote.find(idBay);
        } else {
            equipment = equipmentFacadeRemote.find(idEquip);
            serviceShifting = serviceShiftingFacadeRemote.find(idBay);
        }
    }

    public void handleEditDeleteButtonReUc(ActionEvent event) {
        Integer idBay = (Integer) event.getComponent().getAttributes().get("idCont");
        Integer idEquip = (Integer) event.getComponent().getAttributes().get("idQ");
        setIdEquipp((Integer) event.getComponent().getAttributes().get("idQQ"));
        if (getIdEquipp() != null) {
            equipment = equipmentFacadeRemote.find(idEquip);
            equipmentRe = equipmentFacadeRemote.find(getIdEquipp());
            ucShiftingService = ucShiftingServiceFacadeRemote.find(idBay);
        } else {
            equipment = equipmentFacadeRemote.find(idEquip);
            ucShiftingService = ucShiftingServiceFacadeRemote.find(idBay);
        }
    }

    public void handleEditDeleteButton(ActionEvent event) {
        Integer idBay = (Integer) event.getComponent().getAttributes().get("idCont");
        Integer idEquip = (Integer) event.getComponent().getAttributes().get("idQ");
        equipment = equipmentFacadeRemote.find(idEquip);
        serviceShifting = serviceShiftingFacadeRemote.find(idBay);

        changeBay(serviceShifting.getVBay());
        changeRow(serviceShifting.getVRow());


        if (serviceShifting.getShiftTo().equals("LANDED")) {
            this.setVisiFBay(true);
            this.setVisiFRow(true);
            this.setVisiFTier(true);
            this.setVisiFPos(true);

            this.setVisiToBay(true);
            this.setVisiToPos(true);
            this.setVisiToRow(true);
            this.setVisiToTier(true);
        } else if (serviceShifting.getShiftTo().equals("NOLANDED")) {
            this.setVisiFBay(true);
            this.setVisiFRow(true);
            this.setVisiFTier(true);
            this.setVisiFPos(true);

            this.setVisiToBay(false);
            this.setVisiToPos(false);
            this.setVisiToRow(false);
            this.setVisiToTier(false);

        }
    }

    public void handleEditDeleteButtonUc(ActionEvent event) {
        Integer idBay = (Integer) event.getComponent().getAttributes().get("idCont");
        Integer idEquip = (Integer) event.getComponent().getAttributes().get("idQ");
        Integer idU = (Integer) event.getComponent().getAttributes().get("idU");
        equipment = equipmentFacadeRemote.find(idEquip);
        ucShiftingService = ucShiftingServiceFacadeRemote.find(idBay);
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(idU);
        this.disable = Boolean.TRUE;
    }

    public void handleSelectOperatorCrane(ActionEvent event) {
        String idOperatorCrane = (String) event.getComponent().getAttributes().get("idOperatorCrane");
        equipment.setMasterOperator(masterOperatorFacadeRemote.find(idOperatorCrane));
    }

    public void editGlobalShifting(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;

        boolean temp;
        boolean temp2;
        if (getCharge().equals("LANDED") || serviceShifting.getShiftTo().equals("LANDED")) {
            temp = true;
        } else {
            temp = false;
        }
        if (getCharge().equals("NOLANDED") || serviceShifting.getShiftTo().equals("NOLANDED")) {
            temp2 = true;
        } else {
            temp2 = false;
        }

        if (equipment.getMasterEquipment().getEquipCode() == null || equipment.getMasterOperator().getOptCode() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (equipment.getStartTime() == null || equipment.getEndTime() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (equipment.getStartTime().after(equipment.getEndTime()) || equipment.getStartTime().equals(equipment.getEndTime())) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.time");
        } else {
            loggedIn = true;
            if (temp) {
                serviceShifting.setIsLanded("TRUE");
            } else {
                serviceShifting.setIsLanded("FALSE");
            }
                
            if (serviceShifting.getIsLanded().equals(Boolean.FALSE)) {
                serviceShifting.setShiftingDate(equipment.getEndTime());
            } else {
                serviceShifting.setShiftingDate(null);
            }
            if (temp2) {
                serviceShifting.setIsReshipping("TRUE");
            } else {
                serviceShifting.setIsReshipping("FALSE");
            }
            serviceShiftingFacadeRemote.edit(serviceShifting);
            equipmentFacadeRemote.edit(equipment);
            serviceShiftingList = serviceShiftingFacadeRemote.findServiceShiftingReshippingWithout(getNo_ppkb());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            clear();
        }
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void editReshipping(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();

        boolean loggedIn = false;
//        Object[] tempQ = null;
//        tempQ = equipmentFacadeRemote.findEquipmentByValidasi(equipment.getStartTime(), equipment.getEndTime(), "RESHIPPING", serviceShifting.getOperation());
        if (equipment.getMasterEquipment().getEquipCode() == null || equipment.getMasterOperator().getOptCode() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (equipment.getStartTime() == null || equipment.getEndTime() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (equipment.getStartTime().after(equipment.getEndTime()) || equipment.getStartTime().equals(equipment.getEndTime())) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.time");
        } //        else if (tempQ != null) {
        //            loggedIn = false;
        //            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "start time and end time already exist in database", "Invalid Data");
        //        }
        else {
            loggedIn = true;
            equipmentFacadeRemote.edit(equipment);
            serviceShifting.setShiftingDate(equipment.getEndTime());
            serviceShiftingFacadeRemote.edit(serviceShifting);
            serviceShiftingList = serviceShiftingFacadeRemote.findServiceShiftingReshippingWithout(getNo_ppkb());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            clear();
        }
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void saveResippping(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        serviceVessell = serviceVesselFacadeRemote.find(getNo_ppkb());
//        Object[] tempQ = null;
//        tempQ = equipmentFacadeRemote.findEquipmentByValidasi(equipment.getStartTime(), equipment.getEndTime(), "RESHIPPING", serviceShifting.getOperation());
        //Object[] tempQ=equipmentFacadeRemote.findEquipmentByValidasi(equipment.getStartTime(),equipment.getEndTime() ,equipment.getEquipmentActCode(),equipment.getOperation());
        if (equipment.getMasterEquipment().getEquipCode() == null || equipment.getMasterOperator().getOptCode() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (equipment.getStartTime() == null || equipment.getEndTime() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (equipment.getStartTime().after(equipment.getEndTime()) || equipment.getStartTime().equals(equipment.getEndTime())) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.time");
        } //        else if (tempQ != null) {
        //            loggedIn = false;
        //            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "start time and end time already exist in database", "Invalid Data");
        //        }
        else {
            loggedIn = true;
            equipment.setContNo(serviceShifting.getContNo());
            equipment.setMlo(serviceShifting.getMlo());
            equipment.setPlanningVessel(serviceVessell.getPlanningVessel());
            equipment.setOperation("RESHIPPING");
            equipment.setEquipmentActCode("STEVEDORING");
            equipmentFacadeRemote.edit(equipment);

            serviceShifting.setIsReshipping("TRUE");
            String e = (String) equipment.getMasterEquipment().getEquipCode();
            String o = (String) equipment.getMasterOperator().getOptCode();
            int id = equipmentFacadeRemote.findIdEquipmentByAll(equipment.getMasterEquipment().getEquipCode(), equipment.getMasterOperator().getOptCode(), equipment.getContNo(), getNo_ppkb(), equipment.getStartTime(), equipment.getEndTime(), equipment.getEquipmentActCode(), equipment.getOperation());

            serviceShifting.setIdEquipmentReshipping(id);
            serviceShifting.setShiftingDate(equipment.getEndTime());
            serviceShiftingFacadeRemote.edit(serviceShifting);
            serviceShiftingList = serviceShiftingFacadeRemote.findServiceShiftingReshippingWithout(getNo_ppkb());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            clear();
        }
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void saveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;

        boolean temp = false;
        boolean temp2 = false;
        if (getCharge().equals("LANDED")) {
            temp = true;
        } else if (getCharge().equals("NOLANDED")) {
            temp2 = true;
        }
        serviceVessell = serviceVesselFacadeRemote.find(getNo_ppkb());

//        Object[] tempQ = null;
//        tempQ = equipmentFacadeRemote.findEquipmentByValidasi(equipment.getStartTime(), equipment.getEndTime(), "SHIFTING", serviceShifting.getOperation());
        //Object[] tempQ=equipmentFacadeRemote.findEquipmentByValidasi(equipment.getStartTime(),equipment.getEndTime() ,equipment.getEquipmentActCode(),equipment.getOperation());
        if (cont_no == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (equipment.getMasterEquipment().getEquipCode() == null || equipment.getMasterOperator().getOptCode() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (equipment.getStartTime() == null || equipment.getEndTime() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (equipment.getStartTime().after(equipment.getEndTime()) || equipment.getStartTime().equals(equipment.getEndTime())) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.time");
        } //        else if (tempQ != null) {
        //            loggedIn = false;
        //            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "start time and end time already exist in database", "Invalid Data");
        //        } 
        else {
            loggedIn = true;
            if (getNewItem().equals("LOAD")) {
                serviceContLoad = serviceContLoadFacadeRemote.find(getContainer_no());
                serviceShifting.setContNo(serviceContLoad.getContNo());
                serviceShifting.setMlo(serviceContLoad.getMlo());
                serviceShifting.setContGross(serviceContLoad.getContGross());
                serviceShifting.setContSize(serviceContLoad.getContSize());
                serviceShifting.setContStatus(serviceContLoad.getContStatus());
                serviceShifting.setDg(serviceContLoad.getDangerous());
                serviceShifting.setDgLabel(serviceContLoad.getDgLabel());
                serviceShifting.setMasterContainerType(serviceContLoad.getMasterContainerType());
                serviceShifting.setMasterCommodity(serviceContLoad.getMasterCommodity());
                serviceShifting.setBlNo(serviceContLoad.getBlNo());
                serviceShifting.setServiceVessel(serviceVessell);
                if (temp) {
                    serviceShifting.setIsLanded("TRUE");
                } else {
                    serviceShifting.setIsLanded("FALSE");
                }

                if (serviceShifting.getIsLanded().equals("FALSE")) {
                    serviceShifting.setShiftingDate(equipment.getEndTime());
                    equipment.setOperation("SHIFTING-NOLAND");
                } else {
                    equipment.setOperation("SHIFTING-LANDED");
                }
                
                if (temp2) {
                    serviceShifting.setIsReshipping("TRUE");
                } else {
                    serviceShifting.setIsReshipping("FALSE");
                }
                serviceShifting.setIsPlanning("FALSE");
                serviceShifting.setSealNo(serviceContLoad.getSealNo());

                equipment.setContNo(serviceShifting.getContNo());
                equipment.setMlo(serviceShifting.getMlo());
                equipment.setEquipmentActCode("STEVEDORING");
                equipment.setPlanningVessel(serviceVessell.getPlanningVessel());
                equipmentFacadeRemote.edit(equipment);

                int id = equipmentFacadeRemote.findIdEquipmentByAll(equipment.getMasterEquipment().getEquipCode(), equipment.getMasterOperator().getOptCode(), equipment.getContNo(), getNo_ppkb(), equipment.getStartTime(), equipment.getEndTime(), equipment.getEquipmentActCode(), equipment.getOperation());
                serviceShifting.setIdEquipment(id);
                serviceShiftingFacadeRemote.edit(serviceShifting);
                serviceShiftingList = serviceShiftingFacadeRemote.findServiceShiftingReshippingWithout(getNo_ppkb());
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
                clear();

            } else if (getNewItem().equals("DISCHARGE")) {
                setServiceContDischarge(serviceContDischargeFacadeRemote.find(getContainer_no()));
                serviceShifting.setContNo(serviceContDischarge.getContNo());
                serviceShifting.setMlo(serviceContDischarge.getMlo());
                serviceShifting.setContSize(serviceContDischarge.getContSize());
                serviceShifting.setContStatus(serviceContDischarge.getContStatus());
                serviceShifting.setContGross(serviceContDischarge.getContGross());
                serviceShifting.setDg(serviceContDischarge.getDangerous());
                serviceShifting.setDgLabel(serviceContDischarge.getDgLabel());
                serviceShifting.setMasterContainerType(serviceContDischarge.getMasterContainerType());
                serviceShifting.setMasterCommodity(serviceContDischarge.getMasterCommodity());
                serviceShifting.setBlNo(serviceContDischarge.getBlNo());
                serviceShifting.setServiceVessel(serviceVessell);
                if (temp) {
                    serviceShifting.setIsLanded("TRUE");
                } else {
                    serviceShifting.setIsLanded("FALSE");
                }

                if (serviceShifting.getIsLanded().equals("FALSE")) {
                    serviceShifting.setShiftingDate(equipment.getEndTime());
                    equipment.setOperation("SHIFTING-NOLAND");
                } else {
                    equipment.setOperation("SHIFTING-LANDED");
                }
                
                if (temp2) {
                    serviceShifting.setIsReshipping("TRUE");
                } else {
                    serviceShifting.setIsReshipping("FALSE");
                }
                serviceShifting.setIsPlanning("FALSE");
                serviceShifting.setSealNo(serviceContDischarge.getSealNo());

                equipment.setContNo(serviceShifting.getContNo());
                equipment.setMlo(serviceShifting.getMlo());
                equipment.setEquipmentActCode("STEVEDORING");
                equipment.setPlanningVessel(serviceVessell.getPlanningVessel());
                equipmentFacadeRemote.edit(equipment);

                int id = equipmentFacadeRemote.findIdEquipmentByAll(equipment.getMasterEquipment().getEquipCode(), equipment.getMasterOperator().getOptCode(), equipment.getContNo(), getNo_ppkb(), equipment.getStartTime(), equipment.getEndTime(), equipment.getEquipmentActCode(), equipment.getOperation());
                serviceShifting.setIdEquipment(id);
                serviceShiftingFacadeRemote.edit(serviceShifting);
                serviceShiftingList = serviceShiftingFacadeRemote.findServiceShiftingReshippingWithout(getNo_ppkb());
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
                clear();
            }
        }
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleSelectNoContUc(ActionEvent event) {
        Integer id = (Integer) event.getComponent().getAttributes().get("id_no");
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(id);
        ucShiftingService.setShiftTo(Charge);
    }

    public void handleReshippingConfirmUc(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;

        serviceVessell = serviceVesselFacadeRemote.find(getNo_ppkb());

        if (equipment.getStartTime().after(equipment.getEndTime()) || equipment.getStartTime().equals(equipment.getEndTime())) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.time");
        } else {
            uncontainerizedService.setStatus(UncontainerizedService.STATUS_VESSEL);
            
            equipment.setBlNo(ucShiftingService.getBlNo());
            equipment.setPlanningVessel(serviceVessell.getPlanningVessel());
            equipment.setOperation("RESHIPPING");
            equipment.setEquipmentActCode("STEVEDORING");
            equipmentFacadeRemote.edit(equipment);

            ucShiftingService.setIsReshipping("TRUE");
            int id = equipmentFacadeRemote.findIdEquipmentByAllBlNo(equipment.getMasterEquipment().getEquipCode(), equipment.getMasterOperator().getOptCode(), equipment.getBlNo(), getNo_ppkb(), equipment.getStartTime(), equipment.getEndTime(), equipment.getEquipmentActCode(), equipment.getOperation());

            ucShiftingService.setIdEquipmentReshipping(id);
            serviceShifting.setShiftingDate(equipment.getEndTime());
            ucShiftingServiceFacadeRemote.edit(ucShiftingService);
            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
            
            serviceShiftingUcList = ucShiftingServiceFacadeRemote.findUcShiftingServiceWithoutPlan(no_ppkb);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            clear();
            loggedIn = true;
        }
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleShiftingConfirmUc(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;

        serviceVessell = serviceVesselFacadeRemote.find(getNo_ppkb());

        if (equipment.getStartTime().after(equipment.getEndTime()) || equipment.getStartTime().equals(equipment.getEndTime())) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.time");
        } else {
            
            if (ucShiftingService.getShiftTo().equals("LANDED")) {
                Date now = new Date();

                equipment.setOperation("SHIFTING-LANDED");
                
                ucShiftingService.setIsLanded("TRUE");
                ucShiftingService.setIsReshipping("FALSE");

                uncontainerizedService.setPlacementDate(now);
                uncontainerizedService.setStatus(UncontainerizedService.STATUS_STV);
            } else if (ucShiftingService.getShiftTo().equals("NOLANDED")) {
                equipment.setOperation("SHIFTING-NOLAND");
                ucShiftingService.setIsLanded("FALSE");
                ucShiftingService.setIsReshipping("TRUE");
                uncontainerizedService.setStatus(UncontainerizedService.STATUS_VESSEL);
            }

            ucShiftingService.setIsPlanning("FALSE");
            ucShiftingService.setBlNo(uncontainerizedService.getBlNo());
            ucShiftingService.setNoPpkb(no_ppkb);
            ucShiftingService.setOperation(uncontainerizedService.getOperation());

            equipment.setBlNo(ucShiftingService.getBlNo());
            equipment.setEquipmentActCode("STEVEDORING");
            equipment.setPlanningVessel(serviceVessell.getPlanningVessel());
            equipmentFacadeRemote.edit(equipment);

            int id = equipmentFacadeRemote.findIdEquipmentByAllBlNo(equipment.getMasterEquipment().getEquipCode(), equipment.getMasterOperator().getOptCode(), equipment.getBlNo(), getNo_ppkb(), equipment.getStartTime(), equipment.getEndTime(), equipment.getEquipmentActCode(), equipment.getOperation());
            ucShiftingService.setIdEquipment(id);
            ucShiftingServiceFacadeRemote.edit(ucShiftingService);
            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
            
            serviceShiftingUcList = ucShiftingServiceFacadeRemote.findUcShiftingServiceWithoutPlan(no_ppkb);
            serviceUcList = uncontainerizedServiceFacadeRemote.findShiftingWithout(no_ppkb);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            loggedIn = true;
            clear();
        }
        
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleSelectNoCont(ActionEvent event) {
        setContainer_no((Integer) event.getComponent().getAttributes().get("id_no"));

        if (getNewItem().equals("LOAD")) {
            serviceContLoad = serviceContLoadFacadeRemote.find(getContainer_no());
            setCont_no(serviceContLoad.getContNo());
            setCont_size(serviceContLoad.getContSize());
            setCont_type(serviceContLoad.getMasterContainerType().getContType());
            setCont_status(serviceContLoad.getContStatus());
            serviceShifting.setVBay(serviceContLoad.getVBay());
            serviceShifting.setVRow(serviceContLoad.getVRow());
            serviceShifting.setVTier(serviceContLoad.getVTier());
            //serviceShifting.setBlNo(serviceContLoad.getBlNo());

        } else if (getNewItem().equals("DISCHARGE")) {
            setServiceContDischarge(serviceContDischargeFacadeRemote.find(getContainer_no()));
            setCont_no(serviceContDischarge.getContNo());
            setCont_size(serviceContDischarge.getContSize());
            setCont_type(serviceContDischarge.getMasterContainerType().getContType());
            setCont_status(serviceContDischarge.getContStatus());
            serviceShifting.setVBay(serviceContDischarge.getVBay());
            serviceShifting.setVRow(serviceContDischarge.getVRow());
            serviceShifting.setVTier(serviceContDischarge.getVTier());
            //serviceShifting.setBlNo(serviceContDischarge.getBlNo());
        }
        disableCrane = false;
    }

    public void onChangeEvent(ValueChangeEvent event) {
        //Integer newItem=(Integer) event.getNewValue();
        this.setNewItem((String) event.getNewValue());
        //System.out.println(getNewItem());
    }

    public void onChangeEventCharge(ValueChangeEvent event) {
        this.setCharge((String) event.getNewValue());

        if (getCharge().equals("LANDED")) {
            this.setVisiFBay(true);
            this.setVisiFRow(true);
            this.setVisiFTier(true);
            this.setVisiFPos(true);

            this.setVisiToBay(true);
            this.setVisiToPos(true);
            this.setVisiToRow(true);
            this.setVisiToTier(true);
        } else if (getCharge().equals("NOLANDED")) {
            this.setVisiFBay(true);
            this.setVisiFRow(true);
            this.setVisiFTier(true);
            this.setVisiFPos(true);

            this.setVisiToBay(false);
            this.setVisiToPos(false);
            this.setVisiToRow(false);
            this.setVisiToTier(false);

        }
    }

     //handling crane
    public void onChangeCrane(ValueChangeEvent event) {
        String newCrane = (String) event.getNewValue();
        serviceShifting.setCrane(newCrane);
        if (newCrane.equalsIgnoreCase("S")) {
            equipment.setMasterEquipment(masterEquipmentFacade.find("EQ000"));
            equipment.setMasterOperator(masterOperatorFacade.find("0"));
        }else if (newCrane.equalsIgnoreCase("L")) {
            equipment.setMasterEquipment(new MasterEquipment());
            equipment.setMasterOperator(new MasterOperator());
        }
    }

    public void coba(ActionEvent event) {
        if (getNewItem().equals("LOAD")) {
            serviceListGabunganLoadDischs = serviceContLoadFacadeRemote.findServiceContLoadsStatusSatu(getNo_ppkb());
        } else if (getNewItem().equals("DISCHARGE")) {
            serviceListGabunganLoadDischs = serviceContDischargeFacadeRemote.findServiceContDischargesStatusSatu(getNo_ppkb());
            //System.out.println(serviceListGabunganLoadDischs.size());
        }
    }

    public void changeBay(Short newBay) {
        Integer startRow = 0;
        Integer totalRow = 0;
        //nyiapin data row
        rowChoices.clear();
        rowBottoms.clear();
        rowUps.clear();
        List<Object[]> loopRowBottom = masterVesselProfileFacadeRemote.findRowByBay(vesselCode, "bottom", newBay.intValue());
        if (!loopRowBottom.isEmpty()) {
            startRow = (Integer) loopRowBottom.get(0)[0];
            totalRow = (Integer) loopRowBottom.get(0)[1];
        }
        Integer entryRow = startRow;
        if(totalRow != null)
        for (Integer i = 0; i < totalRow; i++) {
            rowChoices.add(entryRow);
            rowBottoms.add(entryRow);
            entryRow += 1;
        }
        List<Object[]> loopRowUp = masterVesselProfileFacadeRemote.findRowByBay(vesselCode, "up", newBay.intValue());
        if (!loopRowUp.isEmpty()) {
            startRow = (Integer) loopRowUp.get(0)[0];
            totalRow = (Integer) loopRowUp.get(0)[1];
        }
        entryRow = startRow;
        if(totalRow != null)
        for (Integer i = 0; i < totalRow; i++) {
            if (!rowChoices.contains(entryRow)) {
                rowChoices.add(entryRow);
            }
            rowUps.add(entryRow);
            entryRow += 1;
        }
    }

    public void changeRow(Short newRow) {
        Integer startTier = 0;
        Integer totalTier = 0;
        Integer row = newRow.intValue();
        tierChoices.clear();
        if (rowUps.contains(row) && rowBottoms.contains(row)) {
            List<Object[]> loopTierBottom = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "bottom", serviceShifting.getVBay(), row);
            if (!loopTierBottom.isEmpty()) {
                startTier = (Integer) loopTierBottom.get(0)[0];
                totalTier = (Integer) loopTierBottom.get(0)[1];
            }
            Integer entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                tierChoices.add(entryTier);
                entryTier += 2;
            }
            List<Object[]> loopTierUp = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "up", serviceShifting.getVBay(), row);
            if (!loopTierUp.isEmpty()) {
                startTier = (Integer) loopTierUp.get(0)[0];
                totalTier = (Integer) loopTierUp.get(0)[1];
            }
            entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                if (!tierChoices.contains(entryTier)) {
                    tierChoices.add(entryTier);
                }
                entryTier += 2;
            }
        } else if (rowBottoms.contains(row)) {
            List<Object[]> loopTier = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "bottom", serviceShifting.getVBay(), row);
            if (!loopTier.isEmpty()) {
                startTier = (Integer) loopTier.get(0)[0];
                totalTier = (Integer) loopTier.get(0)[1];
            }
            Integer entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                tierChoices.add(entryTier);
                entryTier += 2;
            }
        } else if (rowUps.contains(row)) {
            List<Object[]> loopTier = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "up", serviceShifting.getVBay(), row);
            if (!loopTier.isEmpty()) {
                startTier = (Integer) loopTier.get(0)[0];
                totalTier = (Integer) loopTier.get(0)[1];
            }
            Integer entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                tierChoices.add(entryTier);
                entryTier += 2;
            }
        }
    }

    public void onchangeBay(ValueChangeEvent event) {
        Short newBay = (Short) event.getNewValue();
        serviceShifting.setVBay(newBay);
        changeBay(newBay);
    }

    public void onChangeRow(ValueChangeEvent event) {
        Short newRow = (Short) event.getNewValue();
        serviceShifting.setVRow(newRow);
        changeRow(newRow);
    }

    public List<Object[]> getServiceListGabunganLoadDischs() {
        return serviceListGabunganLoadDischs;
    }

    /**
     * @return the serviceShifting
     */
    public ServiceShifting getServiceShifting() {
        return serviceShifting;
    }

    /**
     * @param serviceShifting the serviceShifting to set
     */
    public void setServiceShifting(ServiceShifting serviceShifting) {
        this.serviceShifting = serviceShifting;
    }

    /**
     * @return the serviceShiftings
     */
    public List<ServiceShifting> getServiceShiftings() {
        return serviceShiftings;
    }

    /**
     * @param serviceShiftings the serviceShiftings to set
     */
    public void setServiceShiftings(List<ServiceShifting> serviceShiftings) {
        this.serviceShiftings = serviceShiftings;
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
     * @return the serviceShiftingList
     */
    public List<Object[]> getServiceShiftingList() {
        return serviceShiftingList;
    }

    /**
     * @param serviceShiftingList the serviceShiftingList to set
     */
    public void setServiceShiftingList(List<Object[]> serviceShiftingList) {
        this.serviceShiftingList = serviceShiftingList;
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
     * @return the serviceListGabunganLoadDischs
     */
    /**
     * @param serviceListGabunganLoadDischs the serviceListGabunganLoadDischs to set
     */
    public void setServiceListGabunganLoadDischs(List<Object[]> serviceListGabunganLoadDischs) {
        this.serviceListGabunganLoadDischs = serviceListGabunganLoadDischs;
    }

    /**
     * @return the newItem
     */
    public String getNewItem() {
        return newItem;
    }

    /**
     * @param newItem the newItem to set
     */
    public void setNewItem(String newItem) {
        this.newItem = newItem;
    }

    /**
     * @return the serviceContLoad
     */
    public ServiceContLoad getServiceContLoad() {
        return serviceContLoad;
    }

    /**
     * @param serviceContLoad the serviceContLoad to set
     */
    public void setServiceContLoad(ServiceContLoad serviceContLoad) {
        this.serviceContLoad = serviceContLoad;
    }

    /**
     * @return the serviceContDischarge
     */
    public ServiceContDischarge getServiceContDischarge() {
        return serviceContDischarge;
    }

    /**
     * @param serviceContDischarge the serviceContDischarge to set
     */
    public void setServiceContDischarge(ServiceContDischarge serviceContDischarge) {
        this.serviceContDischarge = serviceContDischarge;
    }

    /**
     * @return the cont_no
     */
    public String getCont_no() {
        return cont_no;
    }

    /**
     * @param cont_no the cont_no to set
     */
    public void setCont_no(String cont_no) {
        this.cont_no = cont_no;
    }

    /**
     * @return the cont_size
     */
    public short getCont_size() {
        return cont_size;
    }

    /**
     * @param cont_size the cont_size to set
     */
    public void setCont_size(short cont_size) {
        this.cont_size = cont_size;
    }

    /**
     * @return the cont_type
     */
    public Integer getCont_type() {
        return cont_type;
    }

    /**
     * @param cont_type the cont_type to set
     */
    public void setCont_type(Integer cont_type) {
        this.cont_type = cont_type;
    }

    /**
     * @return the cont_status
     */
    public String getCont_status() {
        return cont_status;
    }

    /**
     * @param cont_status the cont_status to set
     */
    public void setCont_status(String cont_status) {
        this.cont_status = cont_status;
    }

    /**
     * @return the visiFBay
     */
    public boolean isVisiFBay() {
        return visiFBay;
    }

    /**
     * @param visiFBay the visiFBay to set
     */
    public void setVisiFBay(boolean visiFBay) {
        this.visiFBay = visiFBay;
    }

    /**
     * @return the visiFRow
     */
    public boolean isVisiFRow() {
        return visiFRow;
    }

    /**
     * @param visiFRow the visiFRow to set
     */
    public void setVisiFRow(boolean visiFRow) {
        this.visiFRow = visiFRow;
    }

    /**
     * @return the visiFTier
     */
    public boolean isVisiFTier() {
        return visiFTier;
    }

    /**
     * @param visiFTier the visiFTier to set
     */
    public void setVisiFTier(boolean visiFTier) {
        this.visiFTier = visiFTier;
    }

    /**
     * @return the visiToBay
     */
    public boolean isVisiToBay() {
        return visiToBay;
    }

    /**
     * @param visiToBay the visiToBay to set
     */
    public void setVisiToBay(boolean visiToBay) {
        this.visiToBay = visiToBay;
    }

    /**
     * @return the visiToRow
     */
    public boolean isVisiToRow() {
        return visiToRow;
    }

    /**
     * @param visiToRow the visiToRow to set
     */
    public void setVisiToRow(boolean visiToRow) {
        this.visiToRow = visiToRow;
    }

    /**
     * @return the visiToTier
     */
    public boolean isVisiToTier() {
        return visiToTier;
    }

    /**
     * @param visiToTier the visiToTier to set
     */
    public void setVisiToTier(boolean visiToTier) {
        this.visiToTier = visiToTier;
    }

    /**
     * @return the Charge
     */
    public String getCharge() {
        return Charge;
    }

    /**
     * @param Charge the Charge to set
     */
    public void setCharge(String Charge) {
        this.Charge = Charge;
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
     * @return the container_no
     */
    public Integer getContainer_no() {
        return container_no;
    }

    /**
     * @param container_no the container_no to set
     */
    public void setContainer_no(Integer container_no) {
        this.container_no = container_no;
    }

    /**
     * @return the disableCrane
     */
    public boolean isDisableCrane() {
        return disableCrane;
    }

    /**
     * @param disableCrane the disableCrane to set
     */
    public void setDisableCrane(boolean disableCrane) {
        this.disableCrane = disableCrane;
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
     * @return the equipmentRe
     */
    public Equipment getEquipmentRe() {
        return equipmentRe;
    }

    /**
     * @param equipmentRe the equipmentRe to set
     */
    public void setEquipmentRe(Equipment equipmentRe) {
        this.equipmentRe = equipmentRe;
    }

    /**
     * @return the idEquipp
     */
    public Integer getIdEquipp() {
        return idEquipp;
    }

    /**
     * @param idEquipp the idEquipp to set
     */
    public void setIdEquipp(Integer idEquipp) {
        this.idEquipp = idEquipp;
    }

    /**
     * @return the visiFPos
     */
    public boolean isVisiFPos() {
        return visiFPos;
    }

    /**
     * @param visiFPos the visiFPos to set
     */
    public void setVisiFPos(boolean visiFPos) {
        this.visiFPos = visiFPos;
    }

    /**
     * @return the visiToPos
     */
    public boolean isVisiToPos() {
        return visiToPos;
    }

    /**
     * @param visiToPos the visiToPos to set
     */
    public void setVisiToPos(boolean visiToPos) {
        this.visiToPos = visiToPos;
    }

    /**
     * @return the bayChoices
     */
    public List<Integer> getBayChoices() {
        return bayChoices;
    }

    /**
     * @param bayChoices the bayChoices to set
     */
    public void setBayChoices(List<Integer> bayChoices) {
        this.bayChoices = bayChoices;
    }

    /**
     * @return the rowChoices
     */
    public List<Integer> getRowChoices() {
        return rowChoices;
    }

    /**
     * @param rowChoices the rowChoices to set
     */
    public void setRowChoices(List<Integer> rowChoices) {
        this.rowChoices = rowChoices;
    }

    /**
     * @return the tierChoices
     */
    public List<Integer> getTierChoices() {
        return tierChoices;
    }

    /**
     * @param tierChoices the tierChoices to set
     */
    public void setTierChoices(List<Integer> tierChoices) {
        this.tierChoices = tierChoices;
    }

    public List<Object[]> getServiceShiftingUcList() {
        return serviceShiftingUcList;
    }

    public void setServiceShiftingUcList(List<Object[]> serviceShiftingUcList) {
        this.serviceShiftingUcList = serviceShiftingUcList;
    }

    public List<Object[]> getServiceUcList() {
        return serviceUcList;
    }

    public void setServiceUcList(List<Object[]> serviceUcList) {
        this.serviceUcList = serviceUcList;
    }

    public UcShiftingService getUcShiftingService() {
        return ucShiftingService;
    }

    public void setUcShiftingService(UcShiftingService ucShiftingService) {
        this.ucShiftingService = ucShiftingService;
    }

    public UncontainerizedService getUncontainerizedService() {
        return uncontainerizedService;
    }

    public void setUncontainerizedService(UncontainerizedService uncontainerizedService) {
        this.uncontainerizedService = uncontainerizedService;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public List<Integer> getRowBottoms() {
        return rowBottoms;
    }

    public void setRowBottoms(List<Integer> rowBottoms) {
        this.rowBottoms = rowBottoms;
    }

    public List<Integer> getRowUps() {
        return rowUps;
    }

    public void setRowUps(List<Integer> rowUps) {
        this.rowUps = rowUps;
    }

    public String getVesselCode() {
        return vesselCode;
    }

    public void setVesselCode(String vesselCode) {
        this.vesselCode = vesselCode;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcDeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DeliveryOperationRemote;
import com.pelindo.ebtos.exception.ContainerNotMovableException;
import com.pelindo.ebtos.exception.LocationNotAvailableException;
import com.pelindo.ebtos.exception.TimeSelectionNotValidException;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceGateDelivery;
import com.pelindo.ebtos.model.db.UcDeliveryService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContDamage;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import com.pelindo.ebtos.model.db.master.MasterVehicle;
import com.pelindo.ebtos.model.db.master.MasterYard;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name="deliveryConfirm")
@ViewScoped
public class DeliveryConfirm implements Serializable{
    private static final Logger logger = Logger.getLogger(DeliveryConfirm.class.getName());

    @EJB
    private UcDeliveryServiceFacadeRemote ucDeliveryServiceFacade;
    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacade;
    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacadeRemote;
    @EJB
    private EquipmentFacadeRemote equipmentFacade;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacade;
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacade;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacade;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private DeliveryOperationRemote deliveryOperationRemote;

    private List<Object[]> serviceVessels;
    private List<Object[]> serviceContDischarges;
    private List<Object[]> deliverableCancelLoadings;
    private List<Object[]> deliveredCancelLoadings;
    private List<Object[]> serviceContDischargeList;
    private List<Object[]> equipments;
    private List<Object[]> uncontainerizedServiceSelect;
    private List<Object[]> uncontainerizedServices;
    private List<MasterOperator> operators;
    private Object[] uncontainerizedDetail;
    private ServiceContDischarge serviceContDischarge;
    private ServiceGateDelivery serviceGateDelivery;
    private PlanningVessel planningVessel;
    private CancelLoadingService cancelLoadingService;
    private UncontainerizedService uncontainerizedService;
    private UcDeliveryService ucDeliveryService;
    private Equipment equipment;
    private String opsi;
    private boolean disableEquipment;
    private boolean disableOptTango;

    /** Creates a new instance of DeliveryConfirm */
    public DeliveryConfirm() {
        planningVessel = new PlanningVessel();
    }

    @PostConstruct
    private void construct(){
        equipment = new Equipment();
        equipment.setMasterEquipment(new MasterEquipment());
        equipment.setMasterOperator(new MasterOperator());
        serviceContDischarge = new ServiceContDischarge();
        serviceContDischarge.setMasterYard(new MasterYard());
        serviceContDischarge.setMasterCommodity(new MasterCommodity());
        serviceContDischarge.setMasterContainerType(new MasterContainerType());
        serviceGateDelivery = new ServiceGateDelivery();
        serviceGateDelivery.setMasterVehicle(new MasterVehicle());
        serviceGateDelivery.setMasterContDamage(new MasterContDamage());
        cancelLoadingService = new CancelLoadingService();
    }

    
    public void handleShowServiceContDischarges(ActionEvent event) {
        serviceContDischarges = serviceContDischargeFacade.findDeliverableContainersByPpkb(planningVessel.getNoPpkb());
    }
    
    //penambahan perubahan bentuk form by ade chelsea tanggal 17 maret 2014
    public void handleShowServiceContDischarges2(ActionEvent event) {
        serviceContDischarges = serviceContDischargeFacade.findDeliverableContainersByPpkb2();
        equipment.setStartTime(equipmentFacade.findByStartTimeData());
        equipment.setEndTime(equipmentFacade.findByEndTimeData());
    }

    public void handleShowDeliverableCancelLoadings(ActionEvent event) {
        deliverableCancelLoadings = cancelLoadingServiceFacade.findDeliverableContainersByPpkb(planningVessel.getNoPpkb());
    }
    
    //penambahan nama kapal , vo in voy out by ade chelsea tanggal 17 maret 2014
    public void handleShowDeliverableCancelLoadings2(ActionEvent event) {
        deliverableCancelLoadings = cancelLoadingServiceFacade.findDeliverableContainersByPpkb2();
    }

    public void handleShowUncontainerizedContainers(ActionEvent event) {
        uncontainerizedServiceSelect = uncontainerizedServiceFacadeRemote.findDeliverableUcs(planningVessel.getNoPpkb());
    }
    //penambahan perubahan bentuk form ny ade chelsea tanggal 17 maret 2014
    public void handleShowUncontainerizedContainers2(ActionEvent event) {
        equipment.setStartTime(equipmentFacade.findByStartTimeData());
        equipment.setEndTime(equipmentFacade.findByEndTimeData());
        uncontainerizedServiceSelect = uncontainerizedServiceFacadeRemote.findDeliverableUcs2();
    }

    public void handleShowServiceVessels(ActionEvent event) {
        serviceVessels = planningVesselFacadeRemote.findPlanningVesselsSgOther();
    }

    public void handleShowEquipments(ActionEvent event) {
        if (uncontainerizedService != null 
                && uncontainerizedService.getIdUc() != null 
                && uncontainerizedService.getTruckLoosing().equals("TRUE")) {
            equipments = masterEquipmentFacade.findCraneForView();
            return;
        }
        
        equipments = masterEquipmentFacade.findTangoForView();
    }

    public void handleShowOperators(ActionEvent event) {
        operators = masterOperatorFacade.findAll();
    }
    
    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(cellStyle);
        }
    }

    public void handleSelectNoPpkb(ActionEvent event){
        String noPpkb = (String) event.getComponent().getAttributes().get("noPpkb");
        planningVessel = planningVesselFacadeRemote.find(noPpkb);
        serviceContDischargeList = serviceContDischargeFacade.findServiceContDischargesByPpkbDeliveryTrue(planningVessel.getNoPpkb());
        uncontainerizedServices = uncontainerizedServiceFacadeRemote.findDeliveredUcsByNoPpkb(planningVessel.getNoPpkb());
        deliveredCancelLoadings = cancelLoadingServiceFacade.findDeliveredContainersByPpkb(planningVessel.getNoPpkb());
    }

    public void handleAdd(ActionEvent event){
        construct();
        
    }

    public void handleAddCancelLoadingDelivery(ActionEvent event){
        construct();
    }

    public void handleRefresh(ActionEvent event){
        serviceContDischargeList = serviceContDischargeFacade.findServiceContDischargesByPpkbDeliveryTrue(planningVessel.getNoPpkb());
    }

    public void handleDeliveredCancelLoadings(ActionEvent event){
        deliveredCancelLoadings = cancelLoadingServiceFacade.findDeliveredContainersByPpkb(planningVessel.getNoPpkb());
    }

    public void handleRefreshUc(ActionEvent event){
        uncontainerizedServices = uncontainerizedServiceFacadeRemote.findDeliveredUcsByNoPpkb(planningVessel.getNoPpkb());
    }

    public void handleSelectCont(ActionEvent event){
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        serviceContDischarge = serviceContDischargeFacade.find(id_cont);
        equipment = equipmentFacade.findByEquipmentActCodeAndOperation(serviceContDischarge.getServiceVessel().getNoPpkb(), serviceContDischarge.getContNo(), "DELIVERY", "DISCHARGE");
    }

    public void handleSelectDeliveredCancelLoading(ActionEvent event){
        String jobSlip = (String) event.getComponent().getAttributes().get("jobSlip");
        cancelLoadingService = cancelLoadingServiceFacade.find(jobSlip);
        equipment = equipmentFacade.findByEquipmentActCodeAndOperation(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), "DELIVERY", "DISCHARGE");
    }
    
    public void handleSelectDeliverableCancelLoading(ActionEvent event){
        String jobSlip = (String) event.getComponent().getAttributes().get("jobSlip");
        cancelLoadingService = cancelLoadingServiceFacade.find(jobSlip);
    }

    public void handleSelectContPick(ActionEvent event){
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        serviceContDischarge = serviceContDischargeFacade.find(id_cont);
    }
    
    //penamabhan by ade chelsea
     public void handleSelectContPick2(ActionEvent event){
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("bl");
        serviceContDischarge = serviceContDischargeFacade.find(id_cont);
    }
    
    public void handleSelectOperator(ActionEvent event){
        String id_operator = (String) event.getComponent().getAttributes().get("idOperator");
        equipment.setMasterOperator(masterOperatorFacade.find(id_operator));
    }

    public void handleSelectEquipment(ActionEvent event){
        String id_equipment = (String) event.getComponent().getAttributes().get("idEquipment");
        equipment.setMasterEquipment(masterEquipmentFacade.find(id_equipment));
        disableOptTango = false;
    }
    
    public void resetForm() {
        serviceContDischarge = new ServiceContDischarge();
        equipment = new Equipment();
        cancelLoadingService = new CancelLoadingService();
        //equipment.setMasterOperator(null);
        
        uncontainerizedService= new UncontainerizedService();
        uncontainerizedDetail = null;
        
    }
    
    public void resetForm2() {
        serviceContDischarge = new ServiceContDischarge();
        //equipment = new Equipment();
        cancelLoadingService = new CancelLoadingService();
        //equipment.setMasterOperator(null);
        
        uncontainerizedService= new UncontainerizedService();
        uncontainerizedDetail = null;
        
    }
    
    public void handleConfirm(ActionEvent event){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        Boolean isValid = false;

        try {
            deliveryOperationRemote.handleDeliveryConfirm(serviceContDischarge, equipment);

            if (equipment.getId() != null){
                isValid = true;
            }
                resetForm2();
            //handleAdd(event);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (TimeSelectionNotValidException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.time");
        } catch (ContainerNotMovableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.cant_move_container");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.loading.error");
            logger.log(Level.SEVERE, null, ex);
        }

        serviceContDischargeList = serviceContDischargeFacade.findServiceContDischargesByPpkbDeliveryTrue(planningVessel.getNoPpkb());
        requestContext.addCallbackParam("loggedIn", isValid);
    }

    public void handleDeliveryCancelLoadingConfirm(ActionEvent event){
        Boolean isValid = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        try {
            deliveryOperationRemote.handleDeliveryConfirmCancelLoading(cancelLoadingService, equipment);

            if (equipment.getId() != null){
                isValid = true;
            }

            handleAddCancelLoadingDelivery(event);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (TimeSelectionNotValidException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.time");
        } catch (ContainerNotMovableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.cant_move_container");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.loading.error");
            logger.log(Level.SEVERE, null, ex);
        }

        deliveredCancelLoadings = cancelLoadingServiceFacade.findDeliveredContainersByPpkb(planningVessel.getNoPpkb());
        requestContext.addCallbackParam("loggedIn", isValid);
    }

    public void handleDelete(ActionEvent event){
        FacesContext facesContext = FacesContext.getCurrentInstance();

        try {
            deliveryOperationRemote.handleCancelDeliveryConfirm(serviceContDischarge, equipment);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (LocationNotAvailableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.location_not_available");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.delete.failed");
            logger.log(Level.SEVERE, null, ex);
        }

        serviceContDischargeList = serviceContDischargeFacade.findServiceContDischargesByPpkbDeliveryTrue(planningVessel.getNoPpkb());
    }

    public void handleDeleteDeliveredCancelLoading(ActionEvent event){
        FacesContext facesContext = FacesContext.getCurrentInstance();

        try {
            deliveryOperationRemote.handleCancelDeliveryConfirmCancelLoading(cancelLoadingService, equipment);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (LocationNotAvailableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.location_not_available");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.delete.failed");
            logger.log(Level.SEVERE, null, ex);
        }

        deliveredCancelLoadings = cancelLoadingServiceFacade.findDeliveredContainersByPpkb(planningVessel.getNoPpkb());
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
     * @return the serviceContDischarge
     */
    public List<Object[]> getServiceContDischarges() {
        return serviceContDischarges;
    }

    /**
     * @param serviceContDischarge the serviceContDischarge to set
     */
    public void setServiceContDischarges(List<Object[]> serviceContDischarges) {
        this.serviceContDischarges = serviceContDischarges;
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
     * @return the equipments
     */
    public List<Object[]> getEquipments() {
        return equipments;
    }

    /**
     * @param equipments the equipments to set
     */
    public void setEquipments(List<Object[]> equipments) {
        this.equipments = equipments;
    }

    /**
     * @return the operators
     */
    public List<MasterOperator> getOperators() {
        return operators;
    }

    /**
     * @param operators the operators to set
     */
    public void setOperators(List<MasterOperator> operators) {
        this.operators = operators;
    }

    /**
     * @return the serviceContDischargeList
     */
    public List<Object[]> getServiceContDischargeList() {
        return serviceContDischargeList;
    }

    /**
     * @param serviceContDischargeList the serviceContDischargeList to set
     */
    public void setServiceContDischargeList(List<Object[]> serviceContDischargeList) {
        this.serviceContDischargeList = serviceContDischargeList;
    }

    /**
     * @return the uncontainerizedServiceSelect
     */
    public List<Object[]> getUncontainerizedServiceSelect() {
        return uncontainerizedServiceSelect;
    }

    /**
     * @return the uncontainerizedServices
     */
    public List<Object[]> getUncontainerizedServices() {
        return uncontainerizedServices;
    }

    /**
     * @return the uncontainerizedDetail
     */
    public Object[] getUncontainerizedDetail() {
        return uncontainerizedDetail;
    }

    //uncontainerized logic
    public void handleAddUncontainerized(){
        ucDeliveryService = new UcDeliveryService();
        setUncontainerizedService(new UncontainerizedService());
        //inisialisasi tango
        equipment = new Equipment();
        equipment.setMasterEquipment(new MasterEquipment());
        equipment.setMasterOperator(new MasterOperator());
        equipment.setPlanningVessel(new PlanningVessel());
        disableEquipment = true;
        disableOptTango = true;
        uncontainerizedDetail = null;
    }

    public void handleEditDeleteUncontainerized(ActionEvent event){
        int idUC = (Integer) event.getComponent().getAttributes().get("idUC");

        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(idUC);
        uncontainerizedDetail = uncontainerizedServiceFacadeRemote.findDetailByIdUc(idUC);
        equipment = equipmentFacade.findByPpkbBLNoEquipmentActCodeAndOperation(planningVessel.getNoPpkb(), uncontainerizedService.getBlNo(), "DELIVERY", "DISCHARGEUC");
        disableEquipment = false;
        disableOptTango = false;
        opsi = "deleteUC";
        ucDeliveryService = uncontainerizedService.getUcDeliveryService();
    }

    public void handleSelectUncontainerized(ActionEvent event){
        int idUC = (Integer) event.getComponent().getAttributes().get("idSelect");
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(idUC);
        uncontainerizedDetail = uncontainerizedServiceFacadeRemote.findDetailByIdUc(idUC);
        ucDeliveryService = uncontainerizedService.getUcDeliveryService();
        
        disableEquipment = false;
    }

    public void saveUncontainerized() {
        boolean loggedIn = false;
        FacesContext contex = FacesContext.getCurrentInstance();

        if (uncontainerizedService.getBlNo() == null || equipment.getMasterEquipment() == null || equipment.getMasterOperator() == null || equipment.getStartTime() == null || equipment.getEndTime() == null) {
            FacesHelper.addFacesMessage(contex, FacesMessage.SEVERITY_WARN, "Warning", "application.transaction.notcompleted");
        } else if (equipment.getStartTime().after(equipment.getEndTime()) || equipment.getStartTime().equals(equipment.getEndTime())) {
            FacesHelper.addFacesMessage(contex, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.time");
        } else {
            uncontainerizedService.setIsDelivery("TRUE");
            
            ucDeliveryService.setIsDelivery("TRUE");
            ucDeliveryService.setDeliveryDate(equipment.getStartTime());

            equipment.setPlanningVessel(planningVessel);
            equipment.setBlNo(uncontainerizedService.getBlNo());
            equipment.setEquipmentActCode("DELIVERY");
            equipment.setOperation("DISCHARGEUC");

            equipmentFacade.edit(equipment);
            ucDeliveryServiceFacade.edit(ucDeliveryService);
            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);

            ucDeliveryService = new UcDeliveryService();
            uncontainerizedService = new UncontainerizedService();
            FacesHelper.addFacesMessage(contex, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            resetForm2();
            loggedIn = true;
        }

        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
        uncontainerizedServices = uncontainerizedServiceFacadeRemote.findDeliveredUcsByNoPpkb(planningVessel.getNoPpkb());
    }

    /**
     * @return the uncontainerizedService
     */
    public UncontainerizedService getUncontainerizedService() {
        return uncontainerizedService;
    }

    /**
     * @param uncontainerizedService the uncontainerizedService to set
     */
    public void setUncontainerizedService(UncontainerizedService uncontainerizedService) {
        this.uncontainerizedService = uncontainerizedService;
    }

    public void deleteUC(){
        FacesContext context = FacesContext.getCurrentInstance();
        
        if (opsi.equalsIgnoreCase("deleteUC")) {
            uncontainerizedService.setIsDelivery("FALSE");
            uncontainerizedService.setStatus("CY");
            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
            ucDeliveryService.setIsDelivery("FALSE");
            ucDeliveryService.setDeliveryDate(null);
            ucDeliveryServiceFacade.edit(ucDeliveryService);
            equipmentFacade.remove(equipment);

            uncontainerizedService = new UncontainerizedService();
            FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        }

        uncontainerizedServices = uncontainerizedServiceFacadeRemote.findDeliveredUcsByNoPpkb(planningVessel.getNoPpkb());
    }

    /**
     * @return the diasbleEquipment
     */
    public boolean isDiasbleEquipment() {
        return disableEquipment;
    }

    /**
     * @return the disableOptTango
     */
    public boolean isDisableOptTango() {
        return disableOptTango;
    }

    /**
     * @return the cancelLoadings
     */
    public List<Object[]> getDeliveredCancelLoadings() {
        return deliveredCancelLoadings;
    }

    /**
     * @return the cancelLoadingService
     */
    public CancelLoadingService getCancelLoadingService() {
        return cancelLoadingService;
    }

    /**
     * @param cancelLoadingService the cancelLoadingService to set
     */
    public void setCancelLoadingService(CancelLoadingService cancelLoadingService) {
        this.cancelLoadingService = cancelLoadingService;
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public List<Object[]> getDeliverableCancelLoadings() {
        return deliverableCancelLoadings;
    }
}

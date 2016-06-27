/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterContDamageFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVehicleFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PluggingReeferServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PlanningReceivingAllocation;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PluggingReeferService;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import com.pelindo.ebtos.model.db.ServiceGateReceiving;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.master.MasterContDamage;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterDangerousClass;
import com.pelindo.ebtos.model.db.master.MasterVehicle;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.util.GrossConverter;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "gateInReceivingBean")
@ViewScoped
public class GateInReceivingBean implements Serializable {
    @EJB
    private MasterContDamageFacadeRemote masterContDamageFacade;
    @EJB
    private ReceivingServiceFacadeRemote receivingServiceFacade;
    @EJB
    private ServiceGateReceivingFacadeRemote serviceGateReceivingFacadeRemote;
    @EJB
    private MasterVehicleFacadeRemote masterVehicleFacadeRemote;
    @EJB
    private PlanningContReceivingFacadeRemote planningContReceivingFacadeRemote;
    @EJB
    private PlanningReceivingFacadeRemote planningReceivingFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private PluggingReeferServiceFacadeRemote pluggingReeferServiceFacadeRemote;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private ServiceReceivingFacadeRemote serviceReceivingFacadeRemote;
    @EJB
    private ServiceContLoadFacadeRemote serviceContLoadFacadeRemote;
    @EJB
    private PlanningContLoadFacadeRemote planningContLoadFacadeRemote;
    
    private List<Object[]> serviceGateIntList;
    private List<Object[]> masterContDamages;
    private List<MasterVehicle> vehicles;
    private Object[] serviceGateReceivingObject;
    private Object[] receivingObject;
    private Object[] pluggingReefer;
    private Object[] vesselCapacityStatus;
    private ServiceGateReceiving serviceGateReceiving;
    private MasterVehicle masterVehicle;
    private PlanningReceivingAllocation planningReceivingAllocation;
    private MasterContainerType masterContainerType;
    private ReceivingService receivingService;
    private PlanningVessel planningVessel;
    private PreserviceVessel preserviceVessel;
    private MasterContDamage masterContDamage;
    private PluggingReeferService pluggingReeferService;
    private PlanningContReceiving planningContReceiving;
    private Integer idGate;
    private String containerNo;
    private String blok;
    private String vesselName;
    private String voyage;
    private String mode;
    private String jobSlip;
    private String contNo; 
    private String contSize; 
    private String contNamaType; 
    private String containerType;
    private String noPpkb;
    private String contStatus;
    private String operation = "RECEIVING";
    private String sealNo;
    private String isoCode;
    private boolean visible;
    private String slot;
    private short fRow;
    private short tRow;
    private String contType;
    private Integer estContWeight;
    private Short tonage;
    private String inputState;

    /** Creates a new instance of GateInReceivingBean */
    public GateInReceivingBean() {}

    @PostConstruct
    private void construct() {
        serviceGateReceiving = new ServiceGateReceiving();
        serviceGateReceiving.setMasterContDamage(new MasterContDamage());
        serviceGateReceiving.setMasterVehicle(new MasterVehicle());
        receivingService = new ReceivingService();
        masterVehicle = new MasterVehicle();
        masterContainerType = new MasterContainerType();
        masterContDamage = new MasterContDamage();
        preserviceVessel = new PreserviceVessel();
        visible = Boolean.FALSE;
        pluggingReeferService = new PluggingReeferService();
        blok = "-";
        noPpkb = null;
        operation = "RECEIVING";
        contType = "1";
        inputState = "1";
        tonage = 0;
        
        serviceGateIntList = receivingServiceFacade.findReceivingServiceByClosingTime(Calendar.getInstance().getTime());
        vehicles = masterVehicleFacadeRemote.findAll();
        masterContDamages = masterContDamageFacade.findMasterContDamages();
    }

    public void clear2() {
        Integer i = serviceGateReceiving.getWeight();
        serviceGateReceiving = new ServiceGateReceiving();
        serviceGateReceiving.setMasterContDamage(new MasterContDamage());

        if (getContType().equals("2") && inputState.equals("1")) {
            inputState = "2";
            serviceGateReceiving.setContWeight(i - tonage - estContWeight);
            estContWeight = 0;
            serviceGateReceiving.setMasterVehicle(masterVehicle);
            serviceGateReceiving.setWeight(i);
        } else {
            contType = "1";
            inputState = "1";
            serviceGateReceiving.setMasterVehicle(new MasterVehicle());
        }
        
        contStatus = null;
        sealNo = null;
        isoCode = null;
        contNamaType=null;
        //this.operation = "RECEIVING";
    }

    public void clear() {
        serviceGateReceiving = new ServiceGateReceiving();
        serviceGateReceiving.setMasterContDamage(new MasterContDamage());
        serviceGateReceiving.setMasterVehicle(new MasterVehicle());
        receivingService = new ReceivingService();
        masterVehicle = new MasterVehicle();
        masterContainerType = new MasterContainerType();
        masterContDamage = new MasterContDamage();
        preserviceVessel = new PreserviceVessel();
        pluggingReeferService = new PluggingReeferService();
        visible = Boolean.FALSE;
        blok = "-";
        fRow = 0;
        slot = "00";
        tRow = 0;
        vesselName = null;
        voyage = null;
        containerNo = null;
        containerType = null;
        contStatus = null;
        sealNo = null;
        isoCode = null;
        operation = "RECEIVING";
        planningContReceiving = null;
    }

    public void handleClear(ActionEvent event) {
        this.clear();
    }

    public List<String> setListAutoComplete(String query) {
        List<String> results = receivingServiceFacade.findGateInPassableJobSlips(query);
        return results;
    }

    public void ambilContNo(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        boolean bPlanContRecIsOverSize = false;

        setContNo((String) event.getComponent().getAttributes().get("contNo"));
        receivingObject = receivingServiceFacade.findReceivingServiceByClosingTimeByJobSlip(contNo);
        pluggingReefer = pluggingReeferServiceFacadeRemote.findPluggingReeferServiceByJobSlip(contNo);
        serviceGateReceivingObject = serviceGateReceivingFacadeRemote.findServiceGateReceivingDateOutByJobSlip(contNo);

        if (receivingObject != null) {
            operation = "RECEIVING";
            //contNo="AAA11223344";
            receivingService = receivingServiceFacade.findDataReceiving(contNo);
            serviceGateReceiving.setJobSlip(receivingService.getJobSlip());
            serviceGateReceiving.setContNo(receivingService.getContNo());
            serviceGateReceiving.setMlo(receivingService.getMlo());
            serviceGateReceiving.setContType(receivingService.getMasterContainerType().getContType());
            masterContainerType = masterContainerTypeFacadeRemote.find(serviceGateReceiving.getContType());
            contStatus = receivingService.getContStatus();
            contSize=receivingService.getContSize().toString();
            contNamaType=masterContainerType.getName();
            serviceGateReceiving.setNoPpkb(receivingService.getPlanningVessel().getNoPpkb());
            serviceGateReceiving.setContGross(receivingService.getContGross());
            serviceGateReceiving.setSealNo(receivingService.getSealNo());
            
            serviceGateReceiving.setBlNo(receivingService.getBlNo());
            this.visible = Boolean.FALSE;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
            loggedIn = true;
        } else if (pluggingReefer != null) {
            operation = "PLUGG";
            pluggingReeferService = pluggingReeferServiceFacadeRemote.find(jobSlip);
            serviceGateReceiving.setContNo(pluggingReeferService.getContNo());
            serviceGateReceiving.setMlo(pluggingReeferService.getMlo());
            serviceGateReceiving.setContType(pluggingReeferService.getMasterContainerType().getContType());
            masterContainerType = masterContainerTypeFacadeRemote.find(serviceGateReceiving.getContType());
            contStatus = pluggingReeferService.getContStatus();
            serviceGateReceiving.setNoPpkb(pluggingReeferService.getNoPpkb());
            serviceGateReceiving.setContGross(pluggingReeferService.getContGross());
            //serviceGateReceiving.setSealNo(pluggingReeferService.getSealNo());
            serviceGateReceiving.setJobSlip(pluggingReeferService.getJobSlip());
            serviceGateReceiving.setBlNo(pluggingReeferService.getBlNo());
            this.visible = Boolean.FALSE;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
            loggedIn = true;
        } else if (serviceGateReceivingObject != null) {

            serviceGateReceiving = serviceGateReceivingFacadeRemote.find(serviceGateReceivingObject[0]);
            planningContReceiving = planningContReceivingFacadeRemote.findByNoPpkbAndContNo(serviceGateReceiving.getNoPpkb(), serviceGateReceiving.getContNo());
            
            if (planningContReceiving != null) {
                String grossClass = GrossConverter.convert(planningContReceiving.getContSize(), serviceGateReceiving.getContWeight());
                planningContReceiving.setGrossClass(grossClass);
                
                
                
                Object[] recommendedLocation = planningReceivingFacadeRemote.findRecommendedLocation(
                        planningContReceiving.getPlanningVessel().getNoPpkb(),
                        planningContReceiving.getDischPort(),
                        planningContReceiving.getMasterContainerType().getContType(),
                        planningContReceiving.getGrossClass(),
                        planningContReceiving.getContSize(),
                        planningContReceiving.getContStatus(),
                        planningContReceiving.getOverSize(),
                        planningContReceiving.getDg(),
                        planningContReceiving.getIsExport());

                if (recommendedLocation != null) {
                    blok = (String) recommendedLocation[1];
                    slot = String.format("%02d", ((Integer) recommendedLocation[2]));
                    fRow = ((Integer) recommendedLocation[5]).shortValue();
                    tRow = ((Integer) recommendedLocation[6]).shortValue();
                } else {
                    blok = null;
                    slot = "00";
                    fRow = 0;
                    tRow = 0;
                    loggedIn = false;
                    context.addCallbackParam("receivingAllocationIsNotEnough", true);
                    FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.validation.receiving_allocation_not_enough");
                }

                containerNo = planningContReceiving.getContNo();
                masterContainerType = planningContReceiving.getMasterContainerType();
                containerType = masterContainerType.getName();
                planningVessel = planningContReceiving.getPlanningVessel();
                vesselName = planningVessel.getPreserviceVessel().getMasterVessel().getName();
                voyage = planningVessel.getPreserviceVessel().getVoyIn();
                noPpkb = planningVessel.getNoPpkb();
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
                loggedIn = true;
            } else {
                setContainerNo(serviceGateReceiving.getContNo());
                masterContainerType = masterContainerTypeFacadeRemote.find(serviceGateReceiving.getContType());
                setContainerType(masterContainerType.getName());
                setVesselName("-");
                setVoyage("-");
                this.noPpkb = serviceGateReceiving.getNoPpkb();
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
                loggedIn = true;
            }
            this.visible = Boolean.TRUE;
            this.clear2();
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.search.notfound_jobslip");
            //this.clear();
        }
        context.addCallbackParam("loggedIn", loggedIn);
        context.addCallbackParam("tipe", "receiv");
    }

    public void handleLookupMasterVehicle(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (serviceGateReceiving.getMasterVehicle().getVehicleCode() != null) {
            masterVehicle = masterVehicleFacadeRemote.find(serviceGateReceiving.getMasterVehicle().getVehicleCode());
            
            if (masterVehicle != null) {

                setTonage(masterVehicle.getTonage());
                serviceGateReceiving.setMasterVehicle(masterVehicle);
                requestContext.addCallbackParam("loggedIn", true);
                return;
            }

            requestContext.addCallbackParam("loggedIn", false);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.license_plate.not_registered");
            return;
        }

        masterVehicle = new MasterVehicle();
        serviceGateReceiving.setMasterVehicle(masterVehicle);
    }
    
    public void handleResetMasterVehicle(ActionEvent event) {
        masterVehicle = new MasterVehicle();
        serviceGateReceiving.setMasterVehicle(masterVehicle);
    }

    public void saveEdit(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Date tgl = Calendar.getInstance().getTime();
        MasterContainerType receivedContainerType = masterContainerTypeFacadeRemote.findByIsoCode(isoCode);
        boolean isValid = true;

        if (getContType().equals("2") && inputState.equals("1")){
            serviceGateReceiving.setContWeight(estContWeight);
        }

        if (serviceGateReceiving.getWeight() == null || serviceGateReceiving.getWeight() < 2000) {
            isValid = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.gate_gross");
        } else if (receivedContainerType == null || !(receivedContainerType.getName().equals(contNamaType)) ) {
            isValid = false; 
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.receiving_iso_code_not_same");
         
        } else if (serviceGateReceiving.getContWeight() == null || serviceGateReceiving.getContWeight() < 1000) {
            isValid = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.cont_weight");
        }else if (receivedContainerType == null || !(masterContainerType.getFeetMark().compareTo(receivedContainerType.getFeetMark()) == 0 || masterContainerType.getFeetMark() == 40)) {
            isValid = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.feet_mark.not_match");
        } else if (serviceGateReceiving.getContWeight() > masterVehicle.getWeightMax()) {
            isValid = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.cont_weight.exceeds_the_limit");
        } else {
            if (operation.equalsIgnoreCase("PLUGG")) {
                blok = "-";
                fRow = Short.parseShort("0");
                slot = "00";
                tRow = Short.parseShort("0");

                masterContainerType = masterContainerTypeFacadeRemote.find(serviceGateReceiving.getContType());
                containerType = masterContainerType.getName();
                vesselName = "-";
                voyage = "-";
                containerNo = serviceGateReceiving.getContNo();
                noPpkb = serviceGateReceiving.getNoPpkb();
                serviceGateReceiving.setDateIn(tgl);
                serviceGateReceivingFacadeRemote.create(serviceGateReceiving);
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
                clear2();
            } else {
                planningContReceiving = planningContReceivingFacadeRemote.findByNoPpkbAndContNo(serviceGateReceiving.getNoPpkb(), serviceGateReceiving.getContNo());
                vesselCapacityStatus = serviceGateReceivingFacadeRemote.findReceivingCapacityStatusByNoPpkb(serviceGateReceiving.getNoPpkb());
                planningVessel = planningContReceiving.getPlanningVessel();

                Long currentGrt = ((Number) vesselCapacityStatus[0]).longValue();
                Long currentContCapacity = ((Number) vesselCapacityStatus[1]).longValue();
                Integer contTeus = 0;

                if (receivedContainerType.getFeetMark() == 20) {
                    contTeus = 1;
                } else if (receivedContainerType.getFeetMark() == 40) {
                    contTeus = 2;
                }
                
                if (currentGrt + (serviceGateReceiving.getContWeight() / 1000) > planningVessel.getPreserviceVessel().getMasterVessel().getGrt()) {
                    isValid = false;
                    FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.exceed_limit", facesContext), "Container Gross", "(max " + planningVessel.getPreserviceVessel().getMasterVessel().getGrt() + " ton)"), null);
                }

                if (currentContCapacity + contTeus > planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity()) {
                    isValid = false;
                    FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.exceed_limit", facesContext), "Container Capacity", "(max " + planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity() + " teus)"), null);
                }
                
                if (isValid && planningContReceiving != null) {
                    String grossClass = GrossConverter.convert(planningContReceiving.getContSize(), serviceGateReceiving.getContWeight());
                    planningContReceiving.setContGross(serviceGateReceiving.getContWeight());
                    planningContReceiving.setSealNo(sealNo);
                    planningContReceiving.setGrossClass(grossClass);
                    planningContReceiving.setMasterContainerType(receivedContainerType);

                    Object[] recommendedLocation = planningReceivingFacadeRemote.findRecommendedLocation(
                            planningContReceiving.getPlanningVessel().getNoPpkb(),
                            planningContReceiving.getDischPort(),
                            planningContReceiving.getMasterContainerType().getContType(),
                            planningContReceiving.getGrossClass(),
                            planningContReceiving.getContSize(),
                            planningContReceiving.getContStatus(),
                            planningContReceiving.getOverSize(),
                            planningContReceiving.getDg(),
                            planningContReceiving.getIsExport());

                    boolean dangerous = receivingService.getMasterCommodity() != null && receivingService.getMasterCommodity().getMasterDangerousClass() != null && MasterDangerousClass.affectedToHandling.contains(receivingService.getMasterCommodity().getMasterDangerousClass().getDangerousClass());

                    if (recommendedLocation == null && !dangerous) {
                        blok = null;
                        slot = "00";
                        fRow = 0;
                        tRow = 0;
                        requestContext.addCallbackParam("loggedIn", false);
                        requestContext.addCallbackParam("receivingAllocationIsNotEnough", true);
                        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.validation.receiving_allocation_not_enough");
                        return;
                    } else if (recommendedLocation == null && dangerous){
                        blok = "-";
                        fRow = Short.parseShort("0");
                        slot = "00";
                        tRow = Short.parseShort("0");
                    } else {
                        blok = (String) recommendedLocation[1];
                        slot = String.format("%02d", ((Integer) recommendedLocation[2]));
                        fRow = ((Integer) recommendedLocation[5]).shortValue();
                        tRow = ((Integer) recommendedLocation[6]).shortValue();
                    }

                    serviceGateReceiving.setDateIn(tgl);
                    serviceGateReceiving.setSealNo(planningContReceiving.getSealNo());
                    serviceGateReceiving.setContType(receivedContainerType.getContType());
                    containerType = receivedContainerType.getName();
                    containerNo = planningContReceiving.getContNo();
                    noPpkb = planningVessel.getNoPpkb();
                    vesselName = planningVessel.getPreserviceVessel().getMasterVessel().getName();
                    voyage = planningVessel.getPreserviceVessel().getVoyIn();

                    //validasi service vessel
                    if (receivingService.getMasterCommodity() != null && receivingService.getMasterCommodity().getMasterDangerousClass() != null && MasterDangerousClass.affectedToHandling.contains(receivingService.getMasterCommodity().getMasterDangerousClass().getDangerousClass())) {
                        if (planningVessel.getServiceVessel() != null) {
                            MasterYard masterYard = masterYardFacadeRemote.find("A");

                            ServiceReceiving serviceReceiving = new ServiceReceiving();
                            serviceReceiving.setContNo(planningContReceiving.getContNo());
                            serviceReceiving.setMlo(planningContReceiving.getMlo());
                            serviceReceiving.setContGross(planningContReceiving.getContGross());
                            serviceReceiving.setContSize(planningContReceiving.getContSize());
                            serviceReceiving.setContStatus(planningContReceiving.getContStatus());
                            serviceReceiving.setDangerous(planningContReceiving.getDg());
                            serviceReceiving.setDgLabel(planningContReceiving.getDgLabel());
                            serviceReceiving.setMasterCommodity(planningContReceiving.getMasterCommodity());
                            serviceReceiving.setMasterContainerType(planningContReceiving.getMasterContainerType());
                            serviceReceiving.setTwentyOneFeet(planningContReceiving.getTwentyOneFeet());
                            serviceReceiving.setPlanningVessel(planningContReceiving.getPlanningVessel());
                            serviceReceiving.setOverSize(planningContReceiving.getOverSize());
                            serviceReceiving.setSealNo(planningContReceiving.getSealNo());
                            serviceReceiving.setGrossClass(planningContReceiving.getGrossClass());
                            serviceReceiving.setTransactionDate(tgl);
                            serviceReceiving.setIsBehandle("FALSE");
                            serviceReceiving.setIsCfs("FALSE");
                            serviceReceiving.setIsInspection("FALSE");
                            serviceReceiving.setBlNo(planningContReceiving.getBlNo());
                            serviceReceiving.setPortOfDelivery(planningContReceiving.getPortOfDelivery());
                            serviceReceiving.setChangeStatus("FALSE");
                            serviceReceiving.setIsChangeDestination("FALSE");
                            serviceReceiving.setStatusCancelLoading("FALSE");
                            serviceReceiving.setIsLifton("FALSE");
                            serviceReceiving.setMasterYard(masterYard);
                            serviceReceiving.setYSlot((short) 1);
                            serviceReceiving.setYRow((short) 1);
                            serviceReceiving.setYTier((short) 1);

                            planningContReceiving.setPosition("02");

                            PlanningContLoad planningContLoad = new PlanningContLoad();
                            planningContLoad.setMasterCommodity(serviceReceiving.getMasterCommodity());
                            planningContLoad.setMasterYard(serviceReceiving.getMasterYard());
                            planningContLoad.setMasterContainerType(serviceReceiving.getMasterContainerType());
                            planningContLoad.setPlanningVessel(serviceReceiving.getPlanningVessel());
                            planningContLoad.setContNo(serviceReceiving.getContNo());
                            planningContLoad.setMlo(serviceReceiving.getMlo());
                            planningContLoad.setDischPort(planningContReceiving.getDischPort());
                            planningContLoad.setLoadPort(planningContReceiving.getLoadPort());
                            planningContLoad.setContSize(serviceReceiving.getContSize());
                            planningContLoad.setIsSling(serviceReceiving.getIsSling());
                            planningContLoad.setContStatus(serviceReceiving.getContStatus());
                            planningContLoad.setIsTranshipment("FALSE");
                            planningContLoad.setPosition("02");
                            planningContLoad.setContGross(serviceReceiving.getContGross());
                            planningContLoad.setSealNo(serviceReceiving.getSealNo());
                            planningContLoad.setOverSize(serviceReceiving.getOverSize());
                            planningContLoad.setDgLabel(serviceReceiving.getDgLabel());
                            planningContLoad.setDg(serviceReceiving.getDangerous());
                            planningContLoad.setIsExportImport(planningContReceiving.getIsExport());
                            planningContLoad.setTwentyOneFeet(planningContReceiving.getTwentyOneFeet());
                            planningContLoad.setYSlot(serviceReceiving.getYSlot());
                            planningContLoad.setYRow(serviceReceiving.getYRow());
                            planningContLoad.setYTier(serviceReceiving.getYTier());
                            planningContLoad.setCompleted("FALSE");
                            planningContLoad.setUnknown("FALSE");
                            planningContLoad.setBlNo(serviceReceiving.getBlNo());
                            planningContLoad.setPortOfDelivery(serviceReceiving.getPortOfDelivery());

                            ServiceContLoad serviceContLoad = new ServiceContLoad();
                            serviceContLoad.setContNo(planningContLoad.getContNo());
                            serviceContLoad.setMlo(planningContLoad.getMlo());
                            serviceContLoad.setContGross(planningContLoad.getContGross());
                            serviceContLoad.setContSize(planningContLoad.getContSize());
                            serviceContLoad.setContStatus(planningContLoad.getContStatus());
                            serviceContLoad.setDangerous(planningContLoad.getDg());
                            serviceContLoad.setIsExportImport(planningContLoad.getIsExportImport());
                            serviceContLoad.setIsSeling(planningContLoad.getIsSling());
                            serviceContLoad.setDgLabel(planningContLoad.getDgLabel());
                            serviceContLoad.setMasterCommodity(planningContLoad.getMasterCommodity());
                            serviceContLoad.setMasterContainerType(planningContLoad.getMasterContainerType());
                            serviceContLoad.setMasterYard(planningContLoad.getMasterYard());
                            serviceContLoad.setServiceVessel(planningVessel.getServiceVessel());
                            serviceContLoad.setOverSize(planningContLoad.getOverSize());
                            serviceContLoad.setSealNo(planningContLoad.getSealNo());
                            serviceContLoad.setVBay(planningContLoad.getVBay());
                            serviceContLoad.setVRow(planningContLoad.getVRow());
                            serviceContLoad.setVTier(planningContLoad.getVTier());
                            serviceContLoad.setYSlot(planningContLoad.getYSlot());
                            serviceContLoad.setYRow(planningContLoad.getYRow());
                            serviceContLoad.setYTier(planningContLoad.getYTier());
                            serviceContLoad.setTransactionDate(tgl);
                            serviceContLoad.setTwentyOneFeet(planningContLoad.getTwentyOneFeet());
                            serviceContLoad.setPosition("02");
                            serviceContLoad.setIsTranshipment("FALSE");
                            serviceContLoad.setIsChangeVessel(planningContLoad.getIsChangeVessel());
                            serviceContLoad.setBlNo(planningContLoad.getBlNo());
                            serviceContLoad.setStatusCancelLoading("FALSE");
                            serviceContLoad.setPortOfDelivery(planningContLoad.getPortOfDelivery());

                            serviceReceivingFacadeRemote.create(serviceReceiving);
                            serviceContLoadFacadeRemote.create(serviceContLoad);
                            planningContLoadFacadeRemote.create(planningContLoad);
                            serviceGateReceivingFacadeRemote.create(serviceGateReceiving);
                            planningContReceivingFacadeRemote.edit(planningContReceiving);
                            masterVehicleFacadeRemote.edit(masterVehicle);

                            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
                        } else {
                            clear();
                            isValid = false;
                            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.validation.start_service");
                        }
                    } else {
                        serviceGateReceivingFacadeRemote.create(serviceGateReceiving);
                        planningContReceivingFacadeRemote.edit(planningContReceiving);
                        masterVehicleFacadeRemote.edit(masterVehicle);
                        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
                    }

                    clear2();
                } else {
                    isValid = false;
                    FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.save.failed");
                }
            }
        }
        requestContext.addCallbackParam("loggedIn", isValid);
        requestContext.addCallbackParam("receivingAllocationIsNotEnough", false);
    }

    public void handleDownload(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/gateInReceiving.pdf?no_ppkb=" + noPpkb + "&block=" + blok + "&slot=" + slot + "&fr_row=" + fRow + "&to_row=" + tRow + "&cont_no=" + containerNo + "&operation=" + operation)));

    }

    public void onChangeContType(ValueChangeEvent event) {
        String ct = (String) event.getNewValue();
        inputState = "1";
    }

    public void onChangeWeight(ValueChangeEvent event) {
        Integer wg = (Integer) event.getNewValue();
        
    }

    public void onChangeTrailer(ValueChangeEvent event) {
        String ttype = (String) event.getNewValue();
        contType = "1";
        inputState = "1";

        if(!masterVehicle.getMasterVehicleType().getVehicleTypeCode().equals(ttype)){
            setTonage(masterVehicle.getMasterVehicleType().getTonage());
        }else{
            setTonage(masterVehicle.getTonage());
        }

    }

    /**
     * @return the serviceGateReceiving
     */
    public ServiceGateReceiving getServiceGateReceiving() {
        return serviceGateReceiving;
    }

    /**
     * @param serviceGateReceiving the serviceGateReceiving to set
     */
    public void setServiceGateReceiving(ServiceGateReceiving serviceGateReceiving) {
        this.serviceGateReceiving = serviceGateReceiving;
    }

    /**
     * @return the vehicles
     */
    public List<MasterVehicle> getVehicles() {
        return vehicles;
    }

    /**
     * @param vehicles the vehicles to set
     */
    public void setVehicles(List<MasterVehicle> vehicles) {
        this.setVehicles(vehicles);
    }

    /**
     * @return the planningReceivingAllocation
     */
    public PlanningReceivingAllocation getPlanningReceivingAllocation() {
        return planningReceivingAllocation;
    }

    /**
     * @param planningReceivingAllocation the planningReceivingAllocation to set
     */
    public void setPlanningReceivingAllocation(PlanningReceivingAllocation planningReceivingAllocation) {
        this.planningReceivingAllocation = planningReceivingAllocation;
    }

    public Object[] getPluggingReefer() {
        return pluggingReefer;
    }

    public void setPluggingReefer(Object[] pluggingReefer) {
        this.pluggingReefer = pluggingReefer;
    }

    /**
     * @return the serviceGateIntList
     */
    public List<Object[]> getServiceGateIntList() {
        return serviceGateIntList;
    }

    /**
     * @param serviceGateIntList the serviceGateIntList to set
     */
    public void setServiceGateIntList(List<Object[]> serviceGateIntList) {
        this.serviceGateIntList = serviceGateIntList;
    }

    /**
     * @return the receivingService
     */
    public ReceivingService getReceivingService() {
        return receivingService;
    }

    /**
     * @param receivingService the receivingService to set
     */
    public void setReceivingService(ReceivingService receivingService) {
        this.receivingService = receivingService;
    }

    /**
     * @return the masterContDamages
     */
    public List<Object[]> getMasterContDamages() {
        return masterContDamages;
    }

    /**
     * @param masterContDamages the masterContDamages to set
     */
    public void setMasterContDamages(List<Object[]> masterContDamages) {
        this.masterContDamages = masterContDamages;
    }

    /**
     * @return the masterVehicle
     */
    public MasterVehicle getMasterVehicle() {
        return masterVehicle;
    }

    /**
     * @param masterVehicle the masterVehicle to set
     */
    public void setMasterVehicle(MasterVehicle masterVehicle) {
        this.masterVehicle = masterVehicle;
    }

    /**
     * @return the blok
     */
    public String getBlok() {
        return blok;
    }

    /**
     * @param blok the blok to set
     */
    public void setBlok(String blok) {
        this.blok = blok;
    }

    /**
     * @return the fRow
     */
    public short getfRow() {
        return fRow;
    }

    /**
     * @param fRow the fRow to set
     */
    public void setfRow(short fRow) {
        this.fRow = fRow;
    }

    /**
     * @return the tRow
     */
    public short gettRow() {
        return tRow;
    }

    /**
     * @param tRow the tRow to set
     */
    public void settRow(short tRow) {
        this.tRow = tRow;
    }

    /**
     * @return the masterContainerType
     */
    public MasterContainerType getMasterContainerType() {
        return masterContainerType;
    }

    /**
     * @param masterContainerType the masterContainerType to set
     */
    public void setMasterContainerType(MasterContainerType masterContainerType) {
        this.masterContainerType = masterContainerType;
    }
    /**
     * @return the vesselName
     */
    public String getVesselName() {
        return vesselName;
    }

    /**
     * @param vesselName the vesselName to set
     */
    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    /**
     * @return the preserviceVessel
     */
    public PreserviceVessel getPreserviceVessel() {
        return preserviceVessel;
    }

    /**
     * @param preserviceVessel the preserviceVessel to set
     */
    public void setPreserviceVessel(PreserviceVessel preserviceVessel) {
        this.preserviceVessel = preserviceVessel;
    }

    /**
     * @return the voyage
     */
    public String getVoyage() {
        return voyage;
    }

    /**
     * @param voyage the voyage to set
     */
    public void setVoyage(String voyage) {
        this.voyage = voyage;
    }

    /**
     * @return the masterContDamage
     */
    public MasterContDamage getMasterContDamage() {
        return masterContDamage;
    }

    /**
     * @param masterContDamage the masterContDamage to set
     */
    public void setMasterContDamage(MasterContDamage masterContDamage) {
        this.masterContDamage = masterContDamage;
    }

    /**
     * @return the idGate
     */
    public Integer getIdGate() {
        return idGate;
    }

    /**
     * @param idGate the idGate to set
     */
    public void setIdGate(Integer idGate) {
        this.idGate = idGate;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * @return the jobSlip
     */
    public String getJobSlip() {
        return jobSlip;
    }

    /**
     * @param jobSlip the jobSlip to set
     */
    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
    }
    
    
    
    /**
     * @return the receivingObject
     */
    public Object[] getReceivingObject() {
        return receivingObject;
    }

    /**
     * @param receivingObject the receivingObject to set
     */
    public void setReceivingObject(Object[] receivingObject) {
        this.receivingObject = receivingObject;
    }

    /**
     * @return the serviceGateReceivingObject
     */
    public Object[] getServiceGateReceivingObject() {
        return serviceGateReceivingObject;
    }

    /**
     * @param serviceGateReceivingObject the serviceGateReceivingObject to set
     */
    public void setServiceGateReceivingObject(Object[] serviceGateReceivingObject) {
        this.serviceGateReceivingObject = serviceGateReceivingObject;
    }

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * @return the container_no
     */
    public String getContainerNo() {
        return containerNo;
    }

    /**
     * @param container_no the container_no to set
     */
    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    /**
     * @return the container_type
     */
    public String getContainerType() {
        return containerType;
    }

    /**
     * @param container_type the container_type to set
     */
    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public PluggingReeferService getPluggingReeferService() {
        return pluggingReeferService;
    }

    public void setPluggingReeferService(PluggingReeferService pluggingReeferService) {
        this.pluggingReeferService = pluggingReeferService;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public String getSlot() {
        return slot;
    }

    public PlanningContReceiving getPlanningContReceiving() {
        return planningContReceiving;
    }

    /**
     * @return the contType
     */
    public String getContType() {
        return contType;
    }

    /**
     * @param contType the contType to set
     */
    public void setContType(String contType) {
        this.contType = contType;
    }

    /**
     * @return the estContWeight
     */
    public Integer getEstContWeight() {
        return estContWeight;
    }

    /**
     * @param estContWeight the estContWeight to set
     */
    public void setEstContWeight(Integer estContWeight) {
        this.estContWeight = estContWeight;
    }

    /**
     * @return the tonage
     */
    public Short getTonage() {
        return tonage;
    }

    /**
     * @param tonage the tonage to set
     */
    public void setTonage(Short tonage) {
        this.tonage = tonage;
    }

    public String getInputState() {
        return inputState;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public String getContSize() {
        return contSize;
    }

    public void setContSize(String contSize) {
        this.contSize = contSize;
    }

    public String getContNamaType() {
        return contNamaType;
    }

    public void setContNamaType(String contNamaType) {
        this.contNamaType = contNamaType;
    }
    
    
}

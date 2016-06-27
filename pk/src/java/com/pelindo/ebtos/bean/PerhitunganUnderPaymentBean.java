/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.BankingSyncFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.CancelDocumentServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganHandlingDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganLiftServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPassGateFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganUpahBuruhFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningReceivingAllocationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UnderpaymentHistoryFacadeRemote;
import com.pelindo.ebtos.ejb.remote.CancelDocumentCalculationOperationRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.CancelDocumentService;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PerhitunganHandlingDischarge;
import com.pelindo.ebtos.model.db.PerhitunganLiftService;
import com.pelindo.ebtos.model.db.PerhitunganPassGate;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukan;
import com.pelindo.ebtos.model.db.PerhitunganUpahBuruh;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PlanningReceivingAllocation;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.UnderpaymentHistory;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterDangerousClass;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.util.GrossConverter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "perhitunganUnderPaymentBean")
@ViewScoped
public class PerhitunganUnderPaymentBean implements Serializable {
    private static final Logger logger = Logger.getLogger(PerhitunganUnderPaymentBean.class.getName());

    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacade;
    @EJB
    private InvoiceFacadeRemote invoiceFacadeRemote;
    @EJB
    private RegistrationFacadeRemote registrationFacadeRemote;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private MasterPortFacadeRemote masterPortFacadeRemote;
    @EJB
    private ReceivingServiceFacadeRemote receivingServiceFacadeRemote;
    @EJB
    private PlanningReceivingAllocationFacadeRemote planningReceivingAllocationFacadeRemote;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacadeRemote;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacadeRemote;
    @EJB
    private PerhitunganPassGateFacadeRemote perhitunganPassGateFacadeRemote;
    @EJB
    private PerhitunganHandlingDischargeFacadeRemote perhitunganHandlingDischargeFacadeRemote;
    @EJB
    private PerhitunganLiftServiceFacadeRemote perhitunganLiftServiceFacadeRemote;
    @EJB
    private PerhitunganUpahBuruhFacadeRemote perhitunganUpahBuruhFacadeRemote;
    @EJB
    private PerhitunganPenumpukanFacadeRemote perhitunganPenumpukanFacadeRemote;
    @EJB
    private UnderpaymentHistoryFacadeRemote underpaymentHistoryFacadeRemote;
    @EJB
    private CancelDocumentServiceFacadeRemote cancelDocumentServiceFacadeRemote;
    @EJB
    private PlanningContReceivingFacadeRemote planningContReceivingFacadeRemote;
    @EJB
    private CancelDocumentCalculationOperationRemote cancelDocumentCalculationOperationRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;

    private List<CancelDocumentService> cancelDocumentServices;
    private List<Object[]> registrations;
    private List<Object[]> vessels;
    private CancelDocumentService cancelDocumentService;
    private Registration registration;
    private ReceivingService receivingService;
    private MasterCurrency masterCurrency;
    private PlanningReceivingAllocation planningReceivingAllocation;
    private UnderpaymentHistory underpaymentHistory;
    private BigDecimal ppn = BigDecimal.ZERO;
    private BigDecimal ppnPrint = BigDecimal.ZERO;
    private BigDecimal materaiPrint = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal additionalCharge = BigDecimal.ZERO;
    private BatalNota batalNota;
    private String userId;
    private String mode;
    private String selectedPort1;
    private String selectedPort2;
    private Boolean disPrint = true;
    private Boolean disKredit = false;
    private Boolean disDetail = false;
    private Boolean disCancelInv = true;
    private Boolean isSimpatReady = false;
    private Boolean changeVessel = false;
    private Object[] receivingStatus;

    /** Creates a new instance of perhitunganCancelDocumentBean */
    public PerhitunganUnderPaymentBean() {}

    @PostConstruct
    private void construct() {
        registration = new Registration();
        registration.setInvoice(new Invoice());
        registration.getInvoice().setPaymentType("CASH");
        receivingService = new ReceivingService();
        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        userId = loginSessionBean.getName();
        masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
    }

    public void handleShowRegistrations(ActionEvent event) {
        registrations = registrationFacadeRemote.findRegistrationCancelDocument();
    }

    public void handleSelectCommodity() {
        cancelDocumentService.setDg("FALSE");

        if (cancelDocumentService.getMasterCommodity() != null && cancelDocumentService.getMasterCommodity().getMasterDangerousClass() != null) {
            cancelDocumentService.setDg("TRUE");
        }
    }

    public void handleViewUnderPaymentDetail(ActionEvent event) {
        String jobSlip = (String) event.getComponent().getAttributes().get("jobSlip");
        cancelDocumentService = cancelDocumentServiceFacadeRemote.find(jobSlip);
        underpaymentHistory = underpaymentHistoryFacadeRemote.find(jobSlip);
        receivingService = null;
    }

    public void handleSelectRegistration(ActionEvent event) {
        String noReg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacadeRemote.find(noReg);
        cancelDocumentServices = cancelDocumentServiceFacadeRemote.findByNoReg(noReg);
        viewData();
    }

    private boolean isDestinationChanged() {
        return !cancelDocumentService.getDischargePort().getPortCode().equals(receivingService.getMasterPort().getPortCode());
    }

    private void viewData() {
        disDetail = false;
        total = BigDecimal.ZERO;
        additionalCharge = BigDecimal.ZERO;

        for (CancelDocumentService o : cancelDocumentServices) {
            BigDecimal upahBuruhCharge = o.getPerhitunganUpahBuruh() == null ? BigDecimal.ZERO : o.getPerhitunganUpahBuruh().getCharge();
            total = total.add(o.getTotalCharge().subtract(upahBuruhCharge));
            additionalCharge = additionalCharge.add(upahBuruhCharge);
        }

        //ambil nilai ppn
        MasterSettingApp ppnSetting = masterSettingAppFacade.find("ppn");
        ppnPrint = ppnSetting.getValueFloat();
        ppn = total.multiply(ppnPrint);
        masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();

        List<Object[]> materai = masterMateraiFacade.findByCurr(masterCurrency.getCurrCode());

        if (total.compareTo((BigDecimal) materai.get(1)[2]) == -1) {
            materaiPrint = (BigDecimal) materai.get(0)[1];
        } else if (total.compareTo((BigDecimal) materai.get(2)[2]) == -1) {
            materaiPrint = (BigDecimal) materai.get(1)[1];
        } else {
            materaiPrint = (BigDecimal) materai.get(2)[1];
        }

        if (registration.getInvoice() != null) {
            disPrint = registration.getInvoice().getValidasiPrint().equals("TRUE") ? true : false;
            disKredit = registration.getInvoice().getPaymentType().equalsIgnoreCase("CASH");
        } else {
            registration.setInvoice(new Invoice());
            registration.getInvoice().setPaymentType("CASH");
            disPrint = true;
            disKredit = true;
        }
    }

    public void handleAdd(ActionEvent event) {
        cancelDocumentService = new CancelDocumentService();
        receivingService = null;
    }

    public void handleSubmit(ActionEvent event) {
        String status;
        Boolean loggedIn = false;

        try {
            registration.getInvoice().setNoReg(registration.getNoReg());
            registration.getInvoice().setRegistration(registration);
            registration.getInvoice().setPaymentStatus("UNPAID");
            registration.getInvoice().setJumlahTagihan(total);
            registration.getInvoice().setPpn(ppn);
            registration.getInvoice().setMaterai(materaiPrint);
            registration.getInvoice().setAdditionalCharge(additionalCharge);
            registration.getInvoice().setTotalTagihan(registration.getInvoice().getJumlahTagihan().add(registration.getInvoice().getMaterai()).add(registration.getInvoice().getPpn()).add(registration.getInvoice().getAdditionalCharge()));
            registration.getInvoice().setMasterCurrency(masterCurrency);

            if (registration.getInvoice().getPaymentType().equals("KREDIT")) {
                status = "approve";
                disKredit = true;
            } else {
                status = "confirm";
                disKredit = false;
            }

            registration.setStatusService(status);
            registration = registrationFacadeRemote.editAndFetch(registration);

            viewData();
            loggedIn = true;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "exception caught", re);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }

        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleSelectJobSlip(ActionEvent event) {
        String jobSlip = (String) event.getComponent().getAttributes().get("jobSlip");
        cancelDocumentService = cancelDocumentServiceFacadeRemote.find(jobSlip);
    }

    public void handleLookupReceivingJobSlip(ActionEvent event) {
        try {
            Object[] cancelableContainer = receivingServiceFacadeRemote.cancelableDocumentContainer(cancelDocumentService.getJobSlip());
            changeVessel = false;
            if (cancelableContainer != null) {
                System.out.println("cancelableContainer");
                receivingService = receivingServiceFacadeRemote.find(cancelableContainer[0]);

                selectedPort1 = receivingService.getMasterPort().getName();
                selectedPort2 = receivingService.getPortOfDelivery().getName();
                String grossClass = GrossConverter.convert(receivingService.getContSize(), receivingService.getContGross());

                cancelDocumentService = new CancelDocumentService();
                cancelDocumentService.setJobSlip(receivingService.getJobSlip());
                cancelDocumentService.setBlNo(receivingService.getBlNo());
                cancelDocumentService.setContGross(receivingService.getContGross());
                cancelDocumentService.setContNo(receivingService.getContNo());
                cancelDocumentService.setContSize(receivingService.getContSize());
                cancelDocumentService.setContStatus(receivingService.getContStatus());
                cancelDocumentService.setDg(receivingService.getDg());
                cancelDocumentService.setDgLabel(receivingService.getDgLabel());
                cancelDocumentService.setDischargePort(receivingService.getMasterPort());
                cancelDocumentService.setIsExport(receivingService.getIsExport());
                cancelDocumentService.setTwentyOneFeet(receivingService.getTwentyOneFeet());
                cancelDocumentService.setLoadPort(receivingService.getMasterPort1());
                cancelDocumentService.setMasterCommodity(receivingService.getMasterCommodity());
                cancelDocumentService.setMasterContainerType(receivingService.getMasterContainerType());
                cancelDocumentService.setMlo(receivingService.getMlo());
                cancelDocumentService.setOverSize(receivingService.getOverSize());
                cancelDocumentService.setPortOfDelivery(receivingService.getPortOfDelivery());
                cancelDocumentService.setReferenceReceiving(receivingService.getRegistration());
                cancelDocumentService.setRegistration(registration);
                cancelDocumentService.setSealNo(receivingService.getSealNo());
                cancelDocumentService.setGrossClass(grossClass);

                if (changeVessel) {
                    cancelDocumentService.setNewPlanningVessel(receivingService.getPlanningVessel());
                } else {
                    cancelDocumentService.setNewPlanningVessel(null);
                }

                underpaymentHistory = new UnderpaymentHistory();
                underpaymentHistory.setChargeBiayaHandling((BigDecimal) cancelableContainer[6]);
                underpaymentHistory.setChargeHandling((BigDecimal) cancelableContainer[3]);
                underpaymentHistory.setChargeLift((BigDecimal) cancelableContainer[2]);
                underpaymentHistory.setChargePassGate((BigDecimal) cancelableContainer[4]);
                underpaymentHistory.setChargePenumpukan((BigDecimal) cancelableContainer[5]);
                underpaymentHistory.setTotalCharge(underpaymentHistory.getChargeBiayaHandling().add(underpaymentHistory.getChargeHandling()).add(underpaymentHistory.getChargeLift()).add(underpaymentHistory.getChargePassGate()).add(underpaymentHistory.getChargePenumpukan()));
                underpaymentHistory.setJobSlip(receivingService.getJobSlip());
                underpaymentHistory.setRegistration(receivingService.getRegistration());
                underpaymentHistory.setUnderpaymentRegistration(registration);
                underpaymentHistory.setBlNo(receivingService.getBlNo());
                underpaymentHistory.setContGross(receivingService.getContGross());
                underpaymentHistory.setContNo(receivingService.getContNo());
                underpaymentHistory.setContSize(receivingService.getContSize());
                underpaymentHistory.setContStatus(receivingService.getContStatus());
                underpaymentHistory.setDg(receivingService.getDg());
                underpaymentHistory.setDgLabel(receivingService.getDgLabel());
                underpaymentHistory.setDischargePort(receivingService.getMasterPort());
                underpaymentHistory.setIsExport(receivingService.getIsExport());
                underpaymentHistory.setLoadPort(receivingService.getMasterPort1());
                underpaymentHistory.setTwentyOneFeet(receivingService.getTwentyOneFeet());
                underpaymentHistory.setMasterCommodity(receivingService.getMasterCommodity());
                underpaymentHistory.setMasterContainerType(receivingService.getMasterContainerType());
                underpaymentHistory.setMlo(receivingService.getMlo());
                underpaymentHistory.setOverSize(receivingService.getOverSize());
                underpaymentHistory.setPortOfDelivery(receivingService.getPortOfDelivery());
                underpaymentHistory.setSealNo(receivingService.getSealNo());
                underpaymentHistory.setGrossClass(grossClass);
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
            } else {
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
            }
        } catch (RuntimeException re) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.loading.error");
            logger.log(Level.SEVERE, "exception caught", re);
        }
    }

    public void handleConfirm(ActionEvent actionEvent) {
        Boolean isValid = false;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        cancelDocumentService.setTotalCharge(BigDecimal.ZERO);
        cancelDocumentService.setCharge(BigDecimal.ZERO);

        try {
            if (isDestinationChanged()) {
                receivingStatus = receivingServiceFacadeRemote.findReceivingCapacityStatusByNoPpkb(registration.getPlanningVessel().getNoPpkb());

                Long currentGrt = (Long) receivingStatus[0] - (receivingService.getContGross() / 1000);
                Long currentContCapacity = (Long) receivingStatus[1] - receivingService.getContTeus();

                if (currentGrt.doubleValue() + (cancelDocumentService.getContGross() / 1000) > registration.getPlanningVessel().getPreserviceVessel().getMasterVessel().getGrt()) {
                    FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.exceed_limit", facesContext), "Container Gross", "(max " + registration.getPlanningVessel().getPreserviceVessel().getMasterVessel().getGrt() + " ton)"), null);
                    requestContext.addCallbackParam("isValid", isValid);
                    return;
                }

                if (currentContCapacity.doubleValue() + cancelDocumentService.getContTeus() > registration.getPlanningVessel().getPreserviceVessel().getMasterVessel().getContCapacity()) {
                    FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.exceed_limit", facesContext), "Container Capacity", "(max " + registration.getPlanningVessel().getPreserviceVessel().getMasterVessel().getContCapacity() + " teus)"), null);
                    requestContext.addCallbackParam("isValid", isValid);
                    return;
                }

                String grossClass = GrossConverter.convert(cancelDocumentService.getContSize(), cancelDocumentService.getContGross());
                Object[] receivingAllocation = planningReceivingAllocationFacadeRemote.findPlanningReceivingByQueryCopy(registration.getPlanningVessel().getNoPpkb(), cancelDocumentService.getContSize(), cancelDocumentService.getMasterContainerType().getContType(), cancelDocumentService.getContStatus(), cancelDocumentService.isOverSize(), cancelDocumentService.isDg(), cancelDocumentService.isIsExport(), grossClass, cancelDocumentService.getDischargePort().getPortCode());

                if (receivingAllocation == null) {
                    FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Info", "application.validation.record_beda");
                    requestContext.addCallbackParam("isValid", isValid);
                    return;
                } else {
                    planningReceivingAllocation = planningReceivingAllocationFacadeRemote.find(receivingAllocation[0]);

                    if (planningReceivingAllocation.getTotalBooking2() >= planningReceivingAllocation.getAllocated() 
                            && registration.getPlanningVessel().getPreserviceVessel().getMasterVessel().isKapalBayangan().equals("FALSE")) {
                        FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.alokasi_booking");
                        requestContext.addCallbackParam("isValid", isValid);
                        return;
                    } else {
                        receivingStatus[0] = currentGrt + (cancelDocumentService.getContGross() / 1000);
                        receivingStatus[1] = currentContCapacity + cancelDocumentService.getContTeus();
                    }
                }
            }

            PerhitunganUpahBuruh perhitunganUpahBuruh = perhitunganUpahBuruhFacadeRemote.findByJobslip(underpaymentHistory.getJobSlip());

            if (perhitunganUpahBuruh != null) {
                String upahBuruhActivityCode = masterActivityFacadeRemote.translateUbahBuruhHandlingActivityCode(
                        cancelDocumentService.getContStatus(), 
                        cancelDocumentService.getContSize(), 
                        cancelDocumentService.isOverSize().equals("TRUE") ? true : false);
                MasterActivity upahBuruhMasterActivity = masterActivityFacadeRemote.find(upahBuruhActivityCode);
                BigDecimal upahBuruhCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(perhitunganUpahBuruh.getMasterCurrency().getCurrCode(), upahBuruhActivityCode);

                if (upahBuruhCharge.compareTo(underpaymentHistory.getChargeBiayaHandling()) > 0) {
                    cancelDocumentService.setPerhitunganUpahBuruh(new PerhitunganUpahBuruh());
                    cancelDocumentService.getPerhitunganUpahBuruh().setRegistration(registration);
                    cancelDocumentService.getPerhitunganUpahBuruh().setJobslip(cancelDocumentService.getJobSlip());
                    cancelDocumentService.getPerhitunganUpahBuruh().setMasterActivity(upahBuruhMasterActivity);
                    cancelDocumentService.getPerhitunganUpahBuruh().setMasterCurrency(masterCurrency);
                    cancelDocumentService.getPerhitunganUpahBuruh().setCharge(upahBuruhCharge.subtract(underpaymentHistory.getChargeBiayaHandling()));

                    cancelDocumentService.setTotalCharge(cancelDocumentService.getTotalCharge().add(cancelDocumentService.getPerhitunganUpahBuruh().getCharge()));
                }
            }

            boolean isDangerous = cancelDocumentService.getMasterCommodity() != null && cancelDocumentService.getMasterCommodity().getMasterDangerousClass() != null && MasterDangerousClass.affectedToHandling.contains(cancelDocumentService.getMasterCommodity().getMasterDangerousClass().getDangerousClass());
            PerhitunganHandlingDischarge perhitunganHandlingDischarge = perhitunganHandlingDischargeFacadeRemote.findByContNoAndNoReg(underpaymentHistory.getContNo(), underpaymentHistory.getRegistration().getNoReg());

            if (perhitunganHandlingDischarge != null) {
                String dangerousClass = null;
                if(cancelDocumentService.getMasterCommodity() != null && cancelDocumentService.getMasterCommodity().getMasterDangerousClass() != null)
                    dangerousClass = cancelDocumentService.getMasterCommodity().getMasterDangerousClass().getDangerousClass();

                String handlingActivityCode = masterActivityFacadeRemote.translateStevedoringActivityCode(
                        cancelDocumentService.getContStatus(), 
                        cancelDocumentService.getContSize(), "L", 
                        receivingService.getOverSize().equals("TRUE") ? true : false,
                        "CC", 
                        receivingService.getDg().equals("TRUE"),
                        receivingService.getDgLabel().equals("TRUE"),
                        dangerousClass,
                        receivingService.getOverSize().equals("TRUE"),
                        cancelDocumentService.getTwentyOneFeet().equals("TRUE"));
                
                BigDecimal handlingCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(perhitunganHandlingDischarge.getCurrCode(), handlingActivityCode);

                if (handlingCharge.compareTo(underpaymentHistory.getChargeHandling()) > 0) {
                    cancelDocumentService.setPerhitunganHandlingDischarge(new PerhitunganHandlingDischarge());
                    cancelDocumentService.getPerhitunganHandlingDischarge().setNoReg(registration.getNoReg());
                    cancelDocumentService.getPerhitunganHandlingDischarge().setNoPpkb(registration.getPlanningVessel().getNoPpkb());
                    cancelDocumentService.getPerhitunganHandlingDischarge().setContNo(cancelDocumentService.getContNo());
                    cancelDocumentService.getPerhitunganHandlingDischarge().setMlo(cancelDocumentService.getMlo());
                    cancelDocumentService.getPerhitunganHandlingDischarge().setBlNo(cancelDocumentService.getBlNo());
                    cancelDocumentService.getPerhitunganHandlingDischarge().setActivityCode(handlingActivityCode);
                    cancelDocumentService.getPerhitunganHandlingDischarge().setCurrCode(masterCurrency.getCurrCode());
                    cancelDocumentService.getPerhitunganHandlingDischarge().setCharge(handlingCharge.subtract(underpaymentHistory.getChargeHandling()));
                    
                    cancelDocumentService.setTotalCharge(cancelDocumentService.getTotalCharge().add(cancelDocumentService.getPerhitunganHandlingDischarge().getCharge()));
                }
            }

            PerhitunganLiftService perhitunganLiftService = perhitunganLiftServiceFacadeRemote.findByContNoAndNoReg(underpaymentHistory.getContNo(), underpaymentHistory.getRegistration().getNoReg());

            
            String dangerousClass = null;
            if(receivingService.getMasterCommodity() != null && receivingService.getMasterCommodity().getMasterDangerousClass() != null)
                dangerousClass = receivingService.getMasterCommodity().getMasterDangerousClass().getDangerousClass();
        
            if (perhitunganLiftService != null) {
                String loloActivityCode = masterActivityFacadeRemote.translateLoLoActivityCode(
                        cancelDocumentService.getContStatus(), 
                        cancelDocumentService.getContSize(), 
                        cancelDocumentService.isOverSize().equals("TRUE") ? true : false, 
                        cancelDocumentService.isDg().equals("TRUE"),
                        cancelDocumentService.isDgLabel().equals("TRUE"),
                        dangerousClass,"CC");
                MasterActivity loloMasterActivity = masterActivityFacadeRemote.find(loloActivityCode);
                BigDecimal loloCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(perhitunganLiftService.getCurrCode(), loloActivityCode);

                if (loloCharge.compareTo(underpaymentHistory.getChargeLift()) > 0) {
                    cancelDocumentService.setPerhitunganLiftService(new PerhitunganLiftService());
                    cancelDocumentService.getPerhitunganLiftService().setContNo(cancelDocumentService.getContNo());
                    cancelDocumentService.getPerhitunganLiftService().setMlo(cancelDocumentService.getMlo());
                    cancelDocumentService.getPerhitunganLiftService().setNoPpkb(registration.getPlanningVessel().getNoPpkb());
                    cancelDocumentService.getPerhitunganLiftService().setRegistration(registration);
                    cancelDocumentService.getPerhitunganLiftService().setBlNo(cancelDocumentService.getBlNo());
                    cancelDocumentService.getPerhitunganLiftService().setMasterActivity(loloMasterActivity);
                    cancelDocumentService.getPerhitunganLiftService().setCurrCode(masterCurrency.getCurrCode());
                    cancelDocumentService.getPerhitunganLiftService().setCharge(loloCharge.subtract(underpaymentHistory.getChargeLift()));

                    cancelDocumentService.setTotalCharge(cancelDocumentService.getTotalCharge().add(cancelDocumentService.getPerhitunganLiftService().getCharge()));
                }
            }

            PerhitunganPassGate perhitunganPassGate = perhitunganPassGateFacadeRemote.findByJobSlip(underpaymentHistory.getJobSlip());

            if (perhitunganPassGate != null) {
                String passGateActivityCode = masterActivityFacadeRemote.translatePassGateActivityCode(cancelDocumentService.getContStatus(), cancelDocumentService.getContSize());
                MasterActivity passGateMasterActivity = masterActivityFacadeRemote.find(passGateActivityCode);
                Integer jumlah = 1;
                BigDecimal passGateCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity("IDR", passGateActivityCode);
                BigDecimal passGateTotalCharge = passGateCharge.multiply(new BigDecimal(jumlah));

                if (passGateTotalCharge.compareTo(underpaymentHistory.getChargePassGate()) > 0) {
                    cancelDocumentService.setPerhitunganPassGate(new PerhitunganPassGate());
                    cancelDocumentService.getPerhitunganPassGate().setJobSlip(cancelDocumentService.getJobSlip());
                    cancelDocumentService.getPerhitunganPassGate().setMasterActivity(passGateMasterActivity);
                    cancelDocumentService.getPerhitunganPassGate().setPlanningVessel(registration.getPlanningVessel());
                    cancelDocumentService.getPerhitunganPassGate().setRegistration(registration);
                    cancelDocumentService.getPerhitunganPassGate().setCharge(passGateCharge);
                    cancelDocumentService.getPerhitunganPassGate().setJumlah(jumlah);
                    cancelDocumentService.getPerhitunganPassGate().setTotalCharge(passGateTotalCharge.subtract(underpaymentHistory.getChargePassGate()));

                    cancelDocumentService.setTotalCharge(cancelDocumentService.getTotalCharge().add(cancelDocumentService.getPerhitunganPassGate().getTotalCharge()));
                }
            }

            PerhitunganPenumpukan perhitunganPenumpukan = perhitunganPenumpukanFacadeRemote.findByContNoAndNoReg(underpaymentHistory.getContNo(), underpaymentHistory.getRegistration().getNoReg());

            if (perhitunganPenumpukan != null) {
                boolean isOverSized = false;
                if ((!cancelDocumentService.getContStatus().equals("MTY") && cancelDocumentService.isOverSize().equals("TRUE")) 
                        || cancelDocumentService.getTwentyOneFeet().equals("TRUE")) {
                    isOverSized = true;
                }
                String penumpukanActivityCode = masterActivityFacadeRemote.translatePenumpukanActivityCode(
                        cancelDocumentService.getContStatus(), 
                        cancelDocumentService.getMasterContainerType().getIsoCode(), 
                        isOverSized, 
                        cancelDocumentService.getContSize(), false);
                MasterActivity penumpukanMasterActivity = masterActivityFacadeRemote.find(penumpukanActivityCode);
                BigDecimal penumpukanCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(perhitunganPenumpukan.getCurrCode(), penumpukanActivityCode);
                BigDecimal jasaDermaga = BigDecimal.ZERO;
                BigDecimal masa1Charge = penumpukanCharge.multiply(BigDecimal.valueOf(perhitunganPenumpukan.getMasa1()));
                BigDecimal masa2Charge = penumpukanCharge.multiply(BigDecimal.valueOf(perhitunganPenumpukan.getMasa2())).multiply(BigDecimal.valueOf(2));
                BigDecimal totalPenumpukanCharge = masa1Charge.add(masa2Charge).add(jasaDermaga);

                if (totalPenumpukanCharge.compareTo(underpaymentHistory.getChargePenumpukan()) > 0) {
                    cancelDocumentService.setPerhitunganPenumpukan(new PerhitunganPenumpukan());
                    cancelDocumentService.getPerhitunganPenumpukan().setRegistration(registration);
                    cancelDocumentService.getPerhitunganPenumpukan().setNoPpkb(registration.getPlanningVessel().getNoPpkb());
                    cancelDocumentService.getPerhitunganPenumpukan().setContNo(cancelDocumentService.getContNo());
                    cancelDocumentService.getPerhitunganPenumpukan().setMlo(cancelDocumentService.getMlo());
                    cancelDocumentService.getPerhitunganPenumpukan().setBlNo(cancelDocumentService.getBlNo());
                    cancelDocumentService.getPerhitunganPenumpukan().setMasa1(perhitunganPenumpukan.getMasa1());
                    cancelDocumentService.getPerhitunganPenumpukan().setMasa2(perhitunganPenumpukan.getMasa2());
                    cancelDocumentService.getPerhitunganPenumpukan().setMasterActivity(penumpukanMasterActivity);
                    cancelDocumentService.getPerhitunganPenumpukan().setCurrCode(masterCurrency.getCurrCode());
                    cancelDocumentService.getPerhitunganPenumpukan().setJasaDermaga(jasaDermaga);
                    cancelDocumentService.getPerhitunganPenumpukan().setCharge(penumpukanCharge);
                    cancelDocumentService.getPerhitunganPenumpukan().setChargeM1(masa1Charge);
                    cancelDocumentService.getPerhitunganPenumpukan().setChargeM2(masa2Charge);
                    cancelDocumentService.getPerhitunganPenumpukan().setTotalCharge(totalPenumpukanCharge.subtract(underpaymentHistory.getChargePenumpukan()));

                    cancelDocumentService.setTotalCharge(cancelDocumentService.getTotalCharge().add(cancelDocumentService.getPerhitunganPenumpukan().getTotalCharge()));
                }
            }

            String cancelDocumentActivityCode = masterActivityFacadeRemote.translateCancelDocumentActivityCode();
            MasterActivity cancelDocumentActivity = masterActivityFacadeRemote.find(cancelDocumentActivityCode);
            BigDecimal cancelDocumentCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), cancelDocumentActivityCode);

            cancelDocumentService.setMasterActivity(cancelDocumentActivity);
            cancelDocumentService.setCharge(cancelDocumentCharge);
            cancelDocumentService.setTotalCharge(cancelDocumentService.getTotalCharge().add(cancelDocumentService.getCharge()));

            if (cancelDocumentService.getIsUnderpaymentRequired()) {
                isValid = true;
                requestContext.addCallbackParam("isValid", isValid);
                requestContext.addCallbackParam("underpaymentConfirmationRequired", true);
                return;
            }

            submitCancelDocument();

            viewData();
            isValid = true;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (Exception e) {
            isValid = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
            logger.log(Level.SEVERE, "exception caught", e);
        }

        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", isValid);
    }

    public void handleDelete(ActionEvent event) {
        try {
            cancelDocumentCalculationOperationRemote.removeCancelDocument(cancelDocumentService);
            cancelDocumentServices.remove(cancelDocumentService);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            viewData();
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "exception caught", re);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.delete.failed");
        }
    }

    public void handleContainerTypeChanged(ValueChangeEvent event) {
        int newItem = (Integer) event.getNewValue();
        MasterContainerType mct = masterContainerTypeFacadeRemote.find(newItem);
        cancelDocumentService.setMasterContainerType(mct);
        cancelDocumentService.setContSize(mct.getFeetMark());
    }

    public void handleSubmitUnderPaymentConfirmation(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        Boolean isValid = false;

        try {
            submitCancelDocument();
            viewData();
            isValid = true;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (RuntimeException re) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
            logger.log(Level.SEVERE, "exception caught", re);
        }

        requestContext.addCallbackParam("isValid", isValid);
    }

    private void submitCancelDocument() {
        MasterPort dischargePort = masterPortFacadeRemote.findMasterPortByName(selectedPort1);
        MasterPort pod = masterPortFacadeRemote.findMasterPortByName(selectedPort2);

        underpaymentHistory.setDischargePort(receivingService.getMasterPort());
        underpaymentHistory.setPortOfDelivery(receivingService.getPortOfDelivery());
        
        cancelDocumentService.setDischargePort(dischargePort);
        cancelDocumentService.setPortOfDelivery(pod);

        PerhitunganLiftService perhitunganLiftService  = perhitunganLiftServiceFacadeRemote.findByContNoAndNoReg(receivingService.getContNo(), receivingService.getNoReg());
        if (perhitunganLiftService != null) {
            perhitunganLiftService.setContNo(cancelDocumentService.getContNo());
            if (changeVessel) {
                perhitunganLiftService.setNoPpkb(cancelDocumentService.getNewPlanningVessel().getNoPpkb());
            }
            perhitunganLiftServiceFacadeRemote.edit(perhitunganLiftService);
        }

        PerhitunganPenumpukan perhitunganPenumpukan = perhitunganPenumpukanFacadeRemote.findByContNoAndNoReg(receivingService.getContNo(), receivingService.getNoReg());
        if (perhitunganPenumpukan != null) {
            perhitunganPenumpukan.setContNo(cancelDocumentService.getContNo());
            if (changeVessel) {
                perhitunganPenumpukan.setNoPpkb(cancelDocumentService.getNewPlanningVessel().getNoPpkb());
            }
            perhitunganPenumpukanFacadeRemote.edit(perhitunganPenumpukan);
        }

        receivingService.setJobSlip(cancelDocumentService.getJobSlip());
        receivingService.setBlNo(cancelDocumentService.getBlNo());
        receivingService.setContGross(cancelDocumentService.getContGross());
        receivingService.setContNo(cancelDocumentService.getContNo());
        receivingService.setContSize(cancelDocumentService.getContSize());
        receivingService.setContStatus(cancelDocumentService.getContStatus());
        receivingService.setDg(cancelDocumentService.isDg());
        receivingService.setDgLabel(cancelDocumentService.isDgLabel());
        receivingService.setTwentyOneFeet(cancelDocumentService.getTwentyOneFeet());
        receivingService.setMasterPort(cancelDocumentService.getDischargePort());
        receivingService.setIsExport(cancelDocumentService.isIsExport());
        receivingService.setMasterPort1(cancelDocumentService.getLoadPort());
        receivingService.setMasterCommodity(cancelDocumentService.getMasterCommodity());
        receivingService.setMasterContainerType(cancelDocumentService.getMasterContainerType());
        receivingService.setMlo(cancelDocumentService.getMlo());
        receivingService.setOverSize(cancelDocumentService.isOverSize());
        receivingService.setPortOfDelivery(cancelDocumentService.getPortOfDelivery());
        receivingService.setRegistration(cancelDocumentService.getReferenceReceiving());
        receivingService.setSealNo(cancelDocumentService.getSealNo());
        if(changeVessel){
            receivingService.setPlanningVessel(cancelDocumentService.getNewPlanningVessel());
        }
        PlanningContReceiving planningContReceiving = planningContReceivingFacadeRemote.findByNoPpkbAndContNo(underpaymentHistory.getRegistration().getPlanningVessel().getNoPpkb(), underpaymentHistory.getContNo());
        planningContReceiving.setContGross(receivingService.getContGross());
        planningContReceiving.setMasterContainerType(receivingService.getMasterContainerType());
        planningContReceiving.setContSize(receivingService.getContSize());
        planningContReceiving.setContStatus(receivingService.getContStatus());
        planningContReceiving.setOverSize(receivingService.getOverSize());
        planningContReceiving.setDg(receivingService.getDg());
        planningContReceiving.setDgLabel(receivingService.getDgLabel());
        planningContReceiving.setContNo(receivingService.getContNo());
        planningContReceiving.setMlo(receivingService.getMlo());
        planningContReceiving.setTwentyOneFeet(receivingService.getTwentyOneFeet());
        planningContReceiving.setDischPort(receivingService.getMasterPort().getPortCode());
        planningContReceiving.setLoadPort(receivingService.getMasterPort1().getPortCode());
        planningContReceiving.setMasterCommodity(receivingService.getMasterCommodity());
        planningContReceiving.setSealNo(receivingService.getSealNo());
        planningContReceiving.setPlanningVessel(receivingService.getPlanningVessel());
        planningContReceiving.setIsExport(receivingService.getIsExport());
        planningContReceiving.setGrossClass(cancelDocumentService.getGrossClass());
        planningContReceiving.setPortOfDelivery(receivingService.getPortOfDelivery());
        planningContReceiving.setBlNo(receivingService.getBlNo());

        if (isDestinationChanged()) {
            if (registration.getPlanningVessel().getPreserviceVessel().getMasterVessel().isKapalBayangan().equals("FALSE")) {
                planningReceivingAllocation.setTotalBooking2(planningReceivingAllocation.getTotalBooking2() + 1);
                planningReceivingAllocationFacadeRemote.edit(planningReceivingAllocation);
            }

            planningContReceiving.setIdConstrainPlanning(planningReceivingAllocation.getId());

            if (underpaymentHistory.getRegistration().getPlanningVessel().getPreserviceVessel().getMasterVessel().isKapalBayangan().equals("FALSE")) {
                planningReceivingAllocation = planningReceivingAllocationFacadeRemote.findByAllocationConstraint(underpaymentHistory.getRegistration().getPlanningVessel().getNoPpkb(), underpaymentHistory.getContSize(), underpaymentHistory.getMasterContainerType().getContType(), underpaymentHistory.getContStatus(), underpaymentHistory.isOverSize(), underpaymentHistory.isDg(), underpaymentHistory.getGrossClass(), underpaymentHistory.getDischargePort().getPortCode(), underpaymentHistory.isIsExport());
                planningReceivingAllocation.setTotalBooking2(planningReceivingAllocation.getTotalBooking2() - 1);
                planningReceivingAllocationFacadeRemote.edit(planningReceivingAllocation);
            }
        }

        planningContReceivingFacadeRemote.edit(planningContReceiving);
        receivingServiceFacadeRemote.edit(receivingService);
        underpaymentHistoryFacadeRemote.create(underpaymentHistory);
        cancelDocumentService = cancelDocumentServiceFacadeRemote.createAndFetch(cancelDocumentService);

        cancelDocumentServices.add(cancelDocumentService);
    }

    public void onChangeEvent(ValueChangeEvent event) {
        String temp = (String) event.getNewValue();
        registration.getInvoice().setPaymentType(temp);
        
        if (temp.equalsIgnoreCase("CASH")) {
            disKredit = false;
        } else {
            disKredit = true;
        }
    }

    public void handleSubmitBatalNota(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean isValid = Boolean.FALSE;

        try {
            final String noInvoice = registration.getInvoice().getNoInvoice();
            cancelDocumentCalculationOperationRemote.cancelInvoice(registration, batalNota);
            disCancelInv = Boolean.TRUE;
            cancelDocumentServices.clear();

            if (masterSettingAppFacade.isPaymentBankingEnabled())
                try {
                    bankingSyncFacadeRemote.cancelInvoice(noInvoice);
                } catch (RuntimeException e) {
                    System.err.println(FacesHelper.getLocaleMessage("application.integration.banking.failed", FacesContext.getCurrentInstance()));
                    FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.integration.banking.failed");
                }

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            isValid = Boolean.TRUE;
        } catch (RuntimeException e) {
            logger.log(Level.SEVERE, "exception caught when call handleSubmitBatalNota", e);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }

        context.addCallbackParam("isValid", isValid);
    }

    public void handleDownloadTransactionRecap(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean doPrint = false;

        try {
            String key = cancelDocumentCalculationOperationRemote.preparePerhitunganReport(registration, masterCurrency, total.multiply(ppnPrint), materaiPrint, cancelDocumentServices);

            if (key != null) {
                doPrint = true;
                requestContext.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
                requestContext.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(facesContext, String.format("/report?key=%s", key))));
            } else {
                throw new RuntimeException("failed to generate report");
            }
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "exception caught when handleDownloadTransactionRecap", re);
        }

        requestContext.addCallbackParam("doPrint", doPrint);
    }

    public void handleDownloadNota(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean doPrint = false;

        if (registration.getInvoice().getValidasiPrint().equals("FALSE")) {
            registration.setInvoice(invoiceFacadeRemote.publishInvoice(registration.getInvoice()));
        }

        viewData();
        String key = cancelDocumentCalculationOperationRemote.prepareInvoiceReport(registration, cancelDocumentServices);

        if (key != null) {
            doPrint = true;
            requestContext.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
            requestContext.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(facesContext, String.format("/report?key=%s", key))));
        } else {
            throw new RuntimeException("failed to generate report");
        }

        requestContext.addCallbackParam("doPrint", doPrint);
    }

    public void handleDownloadDetail(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean doPrint = false;

        try {
            String key = cancelDocumentCalculationOperationRemote.preparePerhitunganInvoiceReport(registration, cancelDocumentServices);

            if (key != null) {
                doPrint = true;
                requestContext.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
                requestContext.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(facesContext, String.format("/report?key=%s", key))));
            } else {
                throw new RuntimeException("failed to generate report");
            }
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "exception caught when handleDownloadDetail", re);
        }

        requestContext.addCallbackParam("doPrint", doPrint);
    }

    public void handleShowVessels(ActionEvent event) {
        vessels = planningVesselFacadeRemote.findPlanningVesselsSgOther();
    }

    public void handleSelectVessel(ActionEvent event) {
        String noPpkb = (String) event.getComponent().getAttributes().get("noPpkb");
        if(changeVessel){
            PlanningVessel planningVessel = planningVesselFacadeRemote.find(noPpkb);
            cancelDocumentService.setNewPlanningVessel(planningVessel);
            selectedPort1 = planningVessel.getPreserviceVessel().getMasterPort1().getName();
            selectedPort2 = planningVessel.getPreserviceVessel().getMasterPort1().getName();
        }else {
            selectedPort1 = receivingService.getMasterPort().getName();
            selectedPort2 = receivingService.getPortOfDelivery().getName();
        }
        receivingStatus = receivingServiceFacadeRemote.findReceivingCapacityStatusByNoPpkb(noPpkb);
    }
    
    public void handleChangeVessel(ValueChangeEvent e) {
        System.out.println("handleChangeVessel");
        System.out.println("e.getNewValue() : " + e.getNewValue());
        boolean yes = (Boolean)e.getNewValue();
        System.out.println("yes : " + yes);
        if (yes) {
            PlanningVessel planningVessel = registration.getPlanningVessel();
            String portName = planningVessel.getPreserviceVessel().getMasterPort1().getName();
            cancelDocumentService.setNewPlanningVessel(planningVessel);
            selectedPort1 = portName;
            selectedPort2 = portName;
            System.out.println("NewPlanningVessel noPpkb : " + cancelDocumentService.getNewPlanningVessel().getNoPpkb());
        }else {
            selectedPort1 = receivingService.getMasterPort().getName();
            selectedPort2 = receivingService.getPortOfDelivery().getName();
        }
        System.out.println(selectedPort1 + " : " + selectedPort2);
    }

    public List<Object[]> getVessels() {
        return vessels;
    }

    public void setVessels(List<Object[]> vessels) {
        this.vessels = vessels;
    }

    public String getSelectedPort1() {
        return selectedPort1;
    }

    public void setSelectedPort1(String selectedPort1) {
        this.selectedPort1 = selectedPort1;
    }

    public String getSelectedPort2() {
        return selectedPort2;
    }

    public void setSelectedPort2(String selectedPort2) {
        this.selectedPort2 = selectedPort2;
    }

    /**
     * @return the registrations
     */
    public List<Object[]> getRegistrations() {
        return registrations;
    }

    /**
     * @param registrations the registrations to set
     */
    public void setRegistrations(List<Object[]> registrations) {
        this.registrations = registrations;
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return the registration
     */
    public Registration getRegistration() {
        return registration;
    }

    /**
     * @param registration the registration to set
     */
    public void setRegistration(Registration registration) {
        this.registration = registration;
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

    public ReceivingService getReceivingService() {
        return receivingService;
    }

    public void setReceivingService(ReceivingService receivingService) {
        this.receivingService = receivingService;
    }

    public BigDecimal getMateraiPrint() {
        return materaiPrint;
    }

    public void setMateraiPrint(BigDecimal materaiPrint) {
        this.materaiPrint = materaiPrint;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getDisDetail() {
        return disDetail;
    }

    public void setDisDetail(Boolean disDetail) {
        this.disDetail = disDetail;
    }

    public Boolean getDisKredit() {
        return disKredit;
    }

    public void setDisKredit(Boolean disKredit) {
        this.disKredit = disKredit;
    }

    public Boolean getDisPrint() {
        return disPrint;
    }

    public void setDisPrint(Boolean disPrint) {
        this.disPrint = disPrint;
    }

    public Boolean getDisCancelInv() {
        return disCancelInv;
    }

    public void setDisCancelInv(Boolean disCancelInv) {
        this.disCancelInv = disCancelInv;
    }

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    public BigDecimal getPpn() {
        return ppn;
    }

    public Object[] getReceivingStatus() {
        return receivingStatus;
    }

    public CancelDocumentService getCancelDocumentService() {
        return cancelDocumentService;
    }

    public void setCancelDocumentService(CancelDocumentService cancelDocumentService) {
        this.cancelDocumentService = cancelDocumentService;
    }

    public List<CancelDocumentService> getCancelDocumentServices() {
        return cancelDocumentServices;
    }

    public BatalNota getBatalNota() {
        return batalNota;
    }

    public void setBatalNota(BatalNota batalNota) {
        this.batalNota = batalNota;
    }

    public UnderpaymentHistory getUnderpaymentHistory() {
        return underpaymentHistory;
    }

    public BigDecimal getAdditionalCharge() {
        return additionalCharge;
    }

    public Boolean getIsSimpatReady() {
        return isSimpatReady;
    }

    public boolean isChangeVessel() {
        return changeVessel;
    }

    public void setChangeVessel(boolean changeVessel) {
        this.changeVessel = changeVessel;
    }
}

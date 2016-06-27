/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.port.util.ContainerUtilization;
import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.BankingSyncFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.BatalNotaFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDiscountFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
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
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PerhitunganHandlingDischarge;
import com.pelindo.ebtos.model.db.PerhitunganLiftService;
import com.pelindo.ebtos.model.db.PerhitunganPassGate;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukan;
import com.pelindo.ebtos.model.db.PerhitunganUpahBuruh;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PlanningReceivingAllocation;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterContainerTypeInGeneral;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterDangerousClass;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.util.GrossConverter;
import com.qtasnim.text.SqlHelper;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.ejb.EJB;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.component.commandbutton.CommandButton;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "receivingBean")
@ViewScoped
public class ReceivingBean implements Serializable {
    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacade;
    @EJB
    private ReceivingServiceFacadeRemote receivingServiceFacadeRemote;
    @EJB
    private InvoiceFacadeRemote invoiceFacadeRemote;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;
    @EJB
    private PerhitunganLiftServiceFacadeRemote perhitunganLiftServiceFacadeRemote;
    @EJB
    private RegistrationFacadeRemote registrationFacade;
    @EJB
    private PlanningReceivingAllocationFacadeRemote planningReceivingAllocationFacadeRemote;
    @EJB
    private PlanningContReceivingFacadeRemote planningContReceivingFacadeRemote;
    @EJB
    private BatalNotaFacadeRemote batalNotaFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private MasterDiscountFacadeRemote masterDiscountFacadeRemote;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacadeRemote;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private MasterPortFacadeRemote masterPortFacadeRemote;
    @EJB
    private MasterYardCoordinatFacadeRemote masterYardCoordinatFacadeRemote;
    @EJB
    private PerhitunganHandlingDischargeFacadeRemote perhitunganHandlingDischargeFacadeRemote;
    @EJB
    private PerhitunganPenumpukanFacadeRemote perhitunganPenumpukanFacadeRemote;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private PerhitunganPassGateFacadeRemote perhitunganPassGateFacadeRemote;
    @EJB
    private PerhitunganUpahBuruhFacadeRemote perhitunganUpahBuruhFacadeRemote;

    private List<MasterPort> masterPorts;
    private List<Object[]> registrations;
    private List<Object[]> receivingServices;
    private List<Object[]> planningContReceivingListGenerate;
    private List<Object[]> receivingAllocationCapacityInfo;
    private Object[][] receivingObjectArray;
    private Object[] receivingStatus;
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal upahBuruh = BigDecimal.ZERO;
    private BigDecimal ppnPrint = BigDecimal.ZERO;
    private BigDecimal materaiPrint = BigDecimal.ZERO;
    private Registration registration;
    private MasterCustomer masterCustomer;
    private PreserviceVessel preserviceVessel;
    private ReceivingService receivingService;
    private MasterContainerType masterContainerType;
    private MasterCurrency masterCurrency;
    private MasterPort masterPort;
    private MasterPort masterPort2;
    private Invoice invoice;
    private PlanningReceivingAllocation planningReceivingAllocation;
    private PlanningContReceiving planningContReceiving;
    private PlanningVessel planningVessel;
    private Integer planningContReceivingIdFromBaplie = null;
    private Integer planningContReceivingId = null;
    private Integer idBaru = null;
    private String userId = null;
    private String opsi = "add";
    private String comodityName = null;
    private String noRegistrasi;
    private String mode;
    private String blNo;
    private String act = "bl";
    private Boolean disPrint = true;
    private Boolean disKredit = false;
    private Boolean disDetail = false;
    private Boolean disCancelInv = true;
    private BatalNota batalNota;
    private MasterPort loadPort;
    private String selectedPort1;
    private String selectedPort2;
    private Boolean isSimpatReady;

    /** Creates a new instance of ReceivingBean */
    public ReceivingBean() {
    }

    @PostConstruct
    private void construct() {
        registration = new Registration();
        masterCustomer = new MasterCustomer();
        preserviceVessel = new PreserviceVessel();
        invoice = new Invoice();
        invoice.setPaymentType("CASH");
        masterContainerType = new MasterContainerType();
        masterContainerType = new MasterContainerType();
        planningReceivingAllocation = new PlanningReceivingAllocation();
        planningContReceiving = new PlanningContReceiving();
        planningVessel = new PlanningVessel();
        planningContReceivingIdFromBaplie = null;
        planningContReceivingId = null;
        blNo = null;
        receivingObjectArray = new Object[0][0];
        batalNota = new BatalNota();

        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        userId = loginSessionBean.getName();
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
    }

    public void handleViewReg(ActionEvent event) {
        registrations = registrationFacade.findRegistrationReceiving();
    }

    public void resetForm() {
        registration = new Registration();
        receivingServices = null;
        invoice = new Invoice();
        masterCurrency = new MasterCurrency();
        //serviceContDischarges = null;
        //deliveryService = new DeliveryService();
        total = BigDecimal.ZERO;
        upahBuruh = BigDecimal.ZERO;
        ppnPrint = BigDecimal.ZERO;
        materaiPrint = BigDecimal.ZERO;
    }
    
    public void handleAdd() {
        String port_code = masterSettingAppFacade.findImplementedPortCode();
        receivingService = new ReceivingService();
        receivingService.setMasterContainerType(new MasterContainerType());
        receivingService.setMasterCommodity(new MasterCommodity());
        receivingService.setContGross(0);
        planningContReceivingListGenerate = new ArrayList<Object[]>();
        
        loadPort = masterPortFacadeRemote.find(port_code);
        receivingService.setMasterPort1(loadPort);
        
        opsi = "add";
        comodityName = null;

        masterContainerType = masterContainerTypeFacadeRemote.find(1);
        receivingService.setMasterContainerType(masterContainerType);
        receivingService.setContSize(masterContainerType.getFeetMark());
    }

    public void handleSelectRegistration(ActionEvent event) {
        String noReg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(noReg);
        viewData();
    }

    private void viewData() {
        invoice = registration.getInvoice();
        receivingStatus = receivingServiceFacadeRemote.findReceivingCapacityStatusByNoPpkb(registration.getPlanningVessel().getNoPpkb());
        receivingServices = receivingServiceFacadeRemote.findPerhitungan(registration.getNoReg());

        disDetail = false;
        total = BigDecimal.ZERO;
        upahBuruh = BigDecimal.ZERO;

        for (Object[] o : receivingServices) {
            total = total.add((BigDecimal) (o[8]))
                    .add((BigDecimal) (o[17]))
                    .add((BigDecimal) (o[18]))
                    .add((BigDecimal) (o[19]))
                    .add((BigDecimal) (o[20]))
                    .add((BigDecimal) (o[21]))
                    .add((BigDecimal) (o[22]));
            
            upahBuruh = upahBuruh.add((BigDecimal) o[25]);
        }

        MasterSettingApp settingApp = masterSettingAppFacade.find("ppn");
        ppnPrint = settingApp.getValueFloat();

        if (registration.getPlanningVessel().getTipePelayaran().equals("d")) {
            masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        } else {
            masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        }

        List<Object[]> materai = masterMateraiFacade.findByCurr(masterCurrency.getCurrCode());

        if (total.compareTo((BigDecimal) materai.get(1)[2]) == -1) { /* jumlah tagihan < nilai minimum */
            materaiPrint = (BigDecimal) materai.get(0)[1];
        } else if (total.compareTo((BigDecimal) materai.get(2)[2]) == -1) {
            materaiPrint = (BigDecimal) materai.get(1)[1];
        } else {
            materaiPrint = (BigDecimal) materai.get(2)[1];
        }

        if (invoice != null) {
            disPrint = invoice.getValidasiPrint().equals("TRUE") ? true : false;

            if (invoice.getPaymentType().equalsIgnoreCase("CASH")) {
                disKredit = false;
            } else {
                disKredit = true;
            }
        } else {
            invoice = new Invoice();
            invoice.setPaymentType("CASH");
            disPrint = true;
            disKredit = false;
        }

        if (invoice.getNoInvoice() != null && invoice.isCancelInvoice().equals("FALSE")) {
            /*cek apakah container sudah masuk gate, apabila sudah maka invoice tidak dapat di cancel*/
            try {
                disCancelInv = receivingServiceFacadeRemote.isPassedThroughTheGate(registration.getNoReg());
            } catch (RuntimeException re) {
                disCancelInv = false;
            }
            /*akhir pengecekan container di gate*/
        }
    }

    public void handleSelect(ActionEvent event) {
        String no_cont = (String) event.getComponent().getAttributes().get("noCont");
        receivingService = receivingServiceFacadeRemote.find(no_cont);
        planningContReceivingIdFromBaplie = planningContReceivingFacadeRemote.findPlanningCont(receivingService.getContNo(), receivingService.getPlanningVessel().getNoPpkb(), "TRUE");
        planningContReceivingId = planningContReceivingFacadeRemote.findPlanningCont(receivingService.getContNo(), receivingService.getPlanningVessel().getNoPpkb(), "FALSE");
        opsi = "edit";
    }

    public void handleDelete(ActionEvent event) {
        try {
            receivingStatus = receivingServiceFacadeRemote.findReceivingCapacityStatusByNoPpkb(registration.getPlanningVessel().getNoPpkb());
            String grossClass = "";
            
            if (planningContReceivingIdFromBaplie != null) {
                planningContReceiving = planningContReceivingFacadeRemote.find(planningContReceivingIdFromBaplie);
                planningContReceiving.setIsGenerate("TRUE");
                planningContReceiving.setPosition(null);
                grossClass = planningContReceiving.getGrossClass();
                planningContReceivingFacadeRemote.edit(planningContReceiving);
            } else if (planningContReceivingId != null) {
                planningContReceiving = planningContReceivingFacadeRemote.find(planningContReceivingId);
                grossClass = planningContReceiving.getGrossClass();
                planningContReceivingFacadeRemote.remove(planningContReceiving);
            }

            boolean isDangerous = receivingService.getMasterCommodity() != null && receivingService.getMasterCommodity().getMasterDangerousClass() != null && MasterDangerousClass.affectedToHandling.contains(receivingService.getMasterCommodity().getMasterDangerousClass().getDangerousClass());
            Object[] temp = planningReceivingAllocationFacadeRemote.findPlanningReceivingByQueryCopy(receivingService.getPlanningVessel().getNoPpkb(), receivingService.getContSize(), receivingService.getMasterContainerType().getContType(), receivingService.getContStatus(), receivingService.getOverSize(), receivingService.getDg(), receivingService.getIsExport(), grossClass, receivingService.getMasterPort().getPortCode());

            if (!isDangerous && temp != null 
                    && receivingService.getPlanningVessel().getPreserviceVessel().getMasterVessel().isKapalBayangan().equals("FALSE")) {
                planningReceivingAllocation = planningReceivingAllocationFacadeRemote.find(temp[0]);
                planningReceivingAllocation.setTotalBooking2(planningReceivingAllocation.getTotalBooking2() - 1);
                planningReceivingAllocationFacadeRemote.edit(planningReceivingAllocation);
            }

            receivingStatus[0] = ((Number) receivingStatus[0]).longValue() - receivingService.getContGross();
            receivingStatus[1] = ((Number) receivingStatus[1]).longValue() - receivingService.getContTeus();

            perhitunganPassGateFacadeRemote.deleteByJobSlip(receivingService.getJobSlip());
            perhitunganUpahBuruhFacadeRemote.deleteByJobslip(receivingService.getJobSlip());
            perhitunganHandlingDischargeFacadeRemote.deleteByContNoAndNoReg(receivingService.getContNo(), receivingService.getNoReg());
            perhitunganPenumpukanFacadeRemote.deleteByContNoAndNoReg(receivingService.getContNo(), receivingService.getNoReg());
            perhitunganLiftServiceFacadeRemote.deleteByContNoAndNoReg(receivingService.getContNo(), receivingService.getNoReg());
            receivingServiceFacadeRemote.remove(receivingService);

            viewData();

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            this.clearSave();
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
        }
    }

    public void handleSubmit(ActionEvent event) {
        Boolean loggedIn = false;

        try {
            invoice.setNoReg(registration.getNoReg());
            invoice.setRegistration(registration);
            invoice.setPaymentStatus("UNPAID");
            invoice.setValidasiPrint("FALSE");
            invoice.setMaterai(materaiPrint);
            invoice.setJumlahTagihan(total);
            invoice.setPpn(invoice.getJumlahTagihan().multiply(ppnPrint));
            invoice.setAdditionalCharge(upahBuruh);
            invoice.setTotalTagihan(invoice.getJumlahTagihan().add(invoice.getMaterai()).add(invoice.getPpn()).add(invoice.getAdditionalCharge()));
            invoice.setMasterCurrency(masterCurrency);
            registration.setInvoice(invoice);

            if (invoice.getPaymentType().equals("KREDIT")) {
                registration.setStatusService("approve");
                disKredit = true;
            } else {
                registration.setStatusService("confirm");
                disKredit = false;
            }

            registrationFacade.edit(registration);
            viewData();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            loggedIn = true;
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
            loggedIn = true;
        }

        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void bersihObject() {
        planningContReceiving = new PlanningContReceiving();
        planningReceivingAllocation = new PlanningReceivingAllocation();
        receivingService.setJobSlip(null);
        receivingService.setContNo(null);
        receivingService.setMlo(null);
    }

    //funtion clear
    public void clearSave() {
        receivingService = new ReceivingService();
        receivingService.setMasterContainerType(new MasterContainerType());
        receivingService.setMasterPort(new MasterPort());
        receivingService.setMasterPort1(new MasterPort());
        planningContReceiving = new PlanningContReceiving();
        planningReceivingAllocation = new PlanningReceivingAllocation();
    }

    public void saveEdit(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        boolean isValid = true;

        try {
            receivingStatus = receivingServiceFacadeRemote.findReceivingCapacityStatusByNoPpkb(registration.getPlanningVessel().getNoPpkb());

            //OPSI EDIT DATA RECEIVING
            if (receivingService.getContGross() == null || receivingService.getContNo() == null || receivingService.getContSize() == null) {
                isValid = false;
                FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
                requestContext.addCallbackParam("loggedIn", isValid);
                return;
            }

            Long currentGrt = ((Number) receivingStatus[0]).longValue();
            Long currentContCapacity = ((Number) receivingStatus[1]).longValue();

            if (receivingService.getMasterContainerType().getMasterContainerTypeInGeneral().getId().equals(MasterContainerTypeInGeneral.TYPE_REEFER) && registration.getPlanningVessel().getIsClosingDocReeferExpired()) {
                isValid = false;
                FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.time_expired", facesContext), "Closing Document Reefer"), null);
            } else if (!receivingService.getMasterContainerType().getMasterContainerTypeInGeneral().getId().equals(MasterContainerTypeInGeneral.TYPE_REEFER) && registration.getPlanningVessel().getIsClosingDocExpired()) {
                isValid = false;
                FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.time_expired", facesContext), "Closing Document"), null);
            }

            if (currentGrt.doubleValue() + (receivingService.getContGross() / 1000) > registration.getPlanningVessel().getPreserviceVessel().getMasterVessel().getGrt()) {
                isValid = false;
                FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.exceed_limit", facesContext), "Container Gross", "(max " + registration.getPlanningVessel().getPreserviceVessel().getMasterVessel().getGrt() + " ton)"), null);
            }

            if (currentContCapacity.doubleValue() + receivingService.getContTeus() > registration.getPlanningVessel().getPreserviceVessel().getMasterVessel().getContCapacity()) {
                isValid = false;
                FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.exceed_limit", facesContext), "Container Capacity", "(max " + registration.getPlanningVessel().getPreserviceVessel().getMasterVessel().getContCapacity() + " teus)"), null);
            }

            if (isValid) {
                if (receivingService.getRegistration() == null) {
                    Registration rg = registrationFacade.find(registration.getNoReg());
                    receivingService.setRegistration(rg);
                    receivingService.setPlanningVessel(planningVesselFacadeRemote.find(registration.getPlanningVessel().getNoPpkb()));
                }
                List<Object[]> alreadyEnteredCy = masterYardCoordinatFacadeRemote.findByContNoStatusExist(receivingService.getContNo());

                if (!alreadyEnteredCy.isEmpty() && masterSettingAppFacade.isCYValidationEnabledWhenReceiving()) {
                    isValid = false;

                    for (Object[] container: alreadyEnteredCy) {
                        String location = container[7] + " " + container[4] + " " + container[5] + " " + container[6];
                        String contNo = (String) container[0];
                        FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.receiving.exist_on_cy", facesContext), contNo, location), null);
                    }
                } else {
                    List<Object[]> hasJobSlipAndNotEnteringGateYet = receivingServiceFacadeRemote.findNotEnteringGateYetByContNo(receivingService.getContNo());

                    if (!hasJobSlipAndNotEnteringGateYet.isEmpty()) {
                        isValid = false;

                        for (Object[] container: hasJobSlipAndNotEnteringGateYet) {
                            Object contNo = container[8];
                            FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.receiving.has_job_but_not_entering_gate_yet", facesContext), contNo), null);
                        }
                    } else {
                        
                        receivingService.setMasterPort1(loadPort);
                        MasterPort port1 = masterPortFacadeRemote.findMasterPortByName(selectedPort1);
                        receivingService.setMasterPort(port1);
                        MasterPort pod = masterPortFacadeRemote.findMasterPortByName(selectedPort2);
                        receivingService.setPortOfDelivery(pod);

                        boolean isDangerous = receivingService.getMasterCommodity() != null && receivingService.getMasterCommodity().getMasterDangerousClass() != null && MasterDangerousClass.affectedToHandling.contains(receivingService.getMasterCommodity().getMasterDangerousClass().getDangerousClass());
                        
                        String grossClass = GrossConverter.convert(receivingService.getContSize(), receivingService.getContGross());
                        Object[] receivingAllocation = planningReceivingAllocationFacadeRemote.findPlanningReceivingByQueryCopy(
                                registration.getPlanningVessel().getNoPpkb(), receivingService.getContSize(), 
                                receivingService.getMasterContainerType().getContType(), receivingService.getContStatus(), 
                                receivingService.getOverSize(), receivingService.getDg(), receivingService.getIsExport(), 
                                grossClass, receivingService.getMasterPort().getPortCode());
                        
                        System.out.println(registration.getPlanningVessel().getNoPpkb()+"-"+ receivingService.getContSize()+"-"+ 
                                receivingService.getMasterContainerType().getContType()+"-"+ receivingService.getContStatus()+"-"+ 
                                receivingService.getOverSize()+"-"+ receivingService.getDg()+"-"+ receivingService.getIsExport()+"-"+ 
                                grossClass+"-"+ receivingService.getMasterPort().getPortCode());
                        if (!isDangerous && receivingAllocation == null) {
                            isValid = false;
                            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Info", "application.validation.record_beda");
                            //FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Info", "no ppkb=" + registration.getPlanningVessel().getNoPpkb() + "-getContSize=" + receivingService.getContSize() + "-getContType" + receivingService.getMasterContainerType().getContType() + "-getContStatus=" + receivingService.getContStatus() + "-getOverSize" + receivingService.getOverSize() + "-getDg=" + receivingService.getDg() + "-getIsExport=" + receivingService.getIsExport() + "-grossClass=" + grossClass + "-getPortCode=" + receivingService.getMasterPort().getPortCode());
                        } else {
                            if (receivingAllocation == null) {
                                receivingAllocation = new Object[] {
                                    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null
                                };
                            }

                            planningReceivingAllocation = planningReceivingAllocationFacadeRemote.find(receivingAllocation[0]);

                            if (!isDangerous && (planningReceivingAllocation == null || (planningReceivingAllocation.getTotalBooking2() >= planningReceivingAllocation.getAllocated() && 
                                    registration.getPlanningVessel().getPreserviceVessel().getMasterVessel().isKapalBayangan().equals("FALSE")))) {
                                isValid = false;
                                FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.alokasi_booking");
                            } else {
                                Object[] registeredWithSamePpkbContainer = planningContReceivingFacadeRemote.findPlanningContReceivingByCekExist(receivingService.getContNo(), registration.getPlanningVessel().getNoPpkb());

                                if (registeredWithSamePpkbContainer != null) {
                                    isValid = false;
                                    FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.data_exist");
                                } else {
                                    if (!ContainerUtilization.checkContainerNumberValidation(receivingService.getContNo()) && !((CommandButton) event.getComponent()).getId().equals("invalidContainerConfirmationButton")) {
                                        requestContext.addCallbackParam("showInvalidContainerConfirmation", true);
                                        return;
                                    }

                                    planningVessel = planningVesselFacadeRemote.find(registration.getPlanningVessel().getNoPpkb());
                                    receivingStatus[0] = currentGrt + (receivingService.getContGross() / 1000);
                                    receivingStatus[1] = currentContCapacity + receivingService.getContTeus();

                                    planningContReceiving = planningContReceivingFacadeRemote.findByNoPpkbAndContNo(registration.getPlanningVessel().getNoPpkb(), receivingService.getContNo());

                                    if (planningContReceiving == null) {
                                        planningContReceiving = new PlanningContReceiving();
                                    }

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
                                    planningContReceiving.setPlanningVessel(registration.getPlanningVessel());
                                    planningContReceiving.setIsExport(receivingService.getIsExport());
                                    planningContReceiving.setIdConstrainPlanning(planningReceivingAllocation == null ? null : planningReceivingAllocation.getId());
                                    planningContReceiving.setIsGenerate("FALSE");
                                    planningContReceiving.setGrossClass(grossClass);
                                    planningContReceiving.setPosition("01");
                                    planningContReceiving.setIsCompleted("TRUE");
                                    planningContReceiving.setPortOfDelivery(receivingService.getPortOfDelivery());
                                    planningContReceiving.setBlNo(receivingService.getBlNo());

                                    if (receivingService.getJobSlip() == null) {
                                        receivingService.setJobSlip(iDGeneratorFacade.generateJobSlipID("01"));
                                    }

                                    receivingService.setMasa1((short) 0);
                                    receivingService.setMasa2((short) 0);
                                    receivingService.setValidDate(planningVessel.getCloseRec());
                                    receivingService.setIsGenerate("FALSE");
                                    receivingService.setStatusCancelLoading("FALSE");
                                    
                                    String dangerousClass = null;
                                    if(receivingService.getMasterCommodity() != null && receivingService.getMasterCommodity().getMasterDangerousClass() != null)
                                        dangerousClass = receivingService.getMasterCommodity().getMasterDangerousClass().getDangerousClass();

                                    
                                    boolean bIsOverSize = false;
                                    if ((!receivingService.getContStatus().equals("MTY") && receivingService.getOverSize().equals("TRUE")) 
                                            || receivingService.getTwentyOneFeet().equals("TRUE")) {
                                        bIsOverSize = true;
                                    }
                                    
                                    String passGateActivityCode = masterActivityFacadeRemote.translatePassGateActivityCode(receivingService.getContStatus(), receivingService.getContSize());
                                    
                                    String stevedoringActivityCode = masterActivityFacadeRemote.translateStevedoringActivityCode(
                                            receivingService.getContStatus(), 
                                            receivingService.getContSize(), "L", 
                                            receivingService.getOverSize().equals("TRUE")  ? true : false,
                                            "CC", 
                                            receivingService.getDg().equals("TRUE"),
                                            receivingService.getDgLabel().equals("TRUE"),
                                            dangerousClass,
                                            bIsOverSize,
                                            receivingService.getTwentyOneFeet().equals("TRUE"));
                                    
                                    String htActivityCode = masterActivityFacadeRemote.translateHaulageTruckingActivityCode(
                                            receivingService.getContStatus(), 
                                            receivingService.getContSize(), "L", 
                                            receivingService.getOverSize().equals("TRUE")  ? true : false,
                                            "CC", 
                                            receivingService.getDg().equals("TRUE"),
                                            receivingService.getDgLabel().equals("TRUE"),
                                            dangerousClass,
                                            bIsOverSize,
                                            receivingService.getTwentyOneFeet().equals("TRUE"));
                                    
                                    String otherHandlingActivityCode = masterActivityFacadeRemote.translateOtherHandlingChargesActivityCode(
                                            receivingService.getContStatus(), 
                                            receivingService.getContSize(), "L", 
                                            receivingService.getOverSize().equals("TRUE")  ? true : false,
                                            "CC", 
                                            receivingService.getDg().equals("TRUE"),
                                            receivingService.getDgLabel().equals("TRUE"),
                                            dangerousClass,
                                            bIsOverSize,
                                            receivingService.getTwentyOneFeet().equals("TRUE"));
                                    
                                    String loloActivityCode = masterActivityFacadeRemote.translateLoLoActivityCode(
                                            receivingService.getContStatus(), 
                                            receivingService.getContSize(), 
                                            receivingService.getOverSize().equals("TRUE") ? true : false, 
                                            receivingService.getDg().equals("TRUE"),
                                            receivingService.getDgLabel().equals("TRUE"),
                                            dangerousClass,"CC");
                                    
                                    String penumpukanActivityCode = masterActivityFacadeRemote.translatePenumpukanActivityCode(
                                            receivingService.getContStatus(), 
                                            receivingService.getMasterContainerType().getIsoCode(), 
                                            bIsOverSize, 
                                            receivingService.getContSize(), false);
                                    String upahBuruhActivityCode = masterActivityFacadeRemote.translateUbahBuruhHandlingActivityCode(
                                            receivingService.getContStatus(), 
                                            receivingService.getContSize(), 
                                            receivingService.getOverSize().equals("TRUE") ? true : false);

                                    MasterActivity passGateMasterActivity = masterActivityFacadeRemote.find(passGateActivityCode);
                                    MasterActivity loloMasterActivity = masterActivityFacadeRemote.find(loloActivityCode);
                                    MasterActivity penumpukanMasterActivity = masterActivityFacadeRemote.find(penumpukanActivityCode);
                                    MasterActivity upahBuruhMasterActivity = masterActivityFacadeRemote.find(upahBuruhActivityCode);
                                    MasterCurrency idrMasterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();

                                    BigDecimal penumpukanCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(idrMasterCurrency.getCurrCode(), penumpukanActivityCode);
                                    BigDecimal stevedoringCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), stevedoringActivityCode);
                                    BigDecimal htCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), htActivityCode);
                                    BigDecimal otherHandlingCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), otherHandlingActivityCode);
                                    BigDecimal loloCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(idrMasterCurrency.getCurrCode(), loloActivityCode);
                                    BigDecimal passGateCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(idrMasterCurrency.getCurrCode(), passGateActivityCode);
                                    BigDecimal upahBuruhCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(idrMasterCurrency.getCurrCode(), upahBuruhActivityCode);

                                    PerhitunganPassGate perhitunganPassGate = new PerhitunganPassGate();
                                    perhitunganPassGate.setJobSlip(receivingService.getJobSlip());
                                    perhitunganPassGate.setMasterActivity(passGateMasterActivity);
                                    perhitunganPassGate.setPlanningVessel(planningVessel);
                                    perhitunganPassGate.setRegistration(registration);

                                    PerhitunganHandlingDischarge perhitunganStevedoring = new PerhitunganHandlingDischarge();
                                    perhitunganStevedoring.setNoReg(registration.getNoReg());
                                    perhitunganStevedoring.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
                                    perhitunganStevedoring.setContNo(receivingService.getContNo());
                                    perhitunganStevedoring.setMlo(receivingService.getMlo());
                                    perhitunganStevedoring.setBlNo(receivingService.getBlNo());
                                    perhitunganStevedoring.setActivityCode(stevedoringActivityCode);
                                    perhitunganStevedoring.setCurrCode(masterCurrency.getCurrCode());
                                    
                                    PerhitunganHandlingDischarge perhitunganHt = new PerhitunganHandlingDischarge();
                                    perhitunganHt.setNoReg(registration.getNoReg());
                                    perhitunganHt.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
                                    perhitunganHt.setContNo(receivingService.getContNo());
                                    perhitunganHt.setMlo(receivingService.getMlo());
                                    perhitunganHt.setBlNo(receivingService.getBlNo());
                                    perhitunganHt.setActivityCode(htActivityCode);
                                    perhitunganHt.setCurrCode(masterCurrency.getCurrCode());
                                    
                                    PerhitunganHandlingDischarge perhitunganOtherHandling = new PerhitunganHandlingDischarge();
                                    perhitunganOtherHandling.setNoReg(registration.getNoReg());
                                    perhitunganOtherHandling.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
                                    perhitunganOtherHandling.setContNo(receivingService.getContNo());
                                    perhitunganOtherHandling.setMlo(receivingService.getMlo());
                                    perhitunganOtherHandling.setBlNo(receivingService.getBlNo());
                                    perhitunganOtherHandling.setActivityCode(otherHandlingActivityCode);
                                    perhitunganOtherHandling.setCurrCode(masterCurrency.getCurrCode());

                                    PerhitunganUpahBuruh perhitunganUbahBuruh = new PerhitunganUpahBuruh();
                                    perhitunganUbahBuruh.setRegistration(registration);
                                    perhitunganUbahBuruh.setJobslip(receivingService.getJobSlip());
                                    perhitunganUbahBuruh.setMasterActivity(upahBuruhMasterActivity);
                                    perhitunganUbahBuruh.setMasterCurrency(idrMasterCurrency);
                                    perhitunganUbahBuruh.setCharge(upahBuruhCharge);

                                    PerhitunganLiftService perhitunganLiftService = new PerhitunganLiftService();
                                    perhitunganLiftService.setContNo(receivingService.getContNo());
                                    perhitunganLiftService.setMlo(receivingService.getMlo());
                                    perhitunganLiftService.setNoPpkb(receivingService.getPlanningVessel().getNoPpkb());
                                    perhitunganLiftService.setRegistration(receivingService.getRegistration());
                                    perhitunganLiftService.setBlNo(receivingService.getBlNo());
                                    perhitunganLiftService.setMasterActivity(loloMasterActivity);
                                    perhitunganLiftService.setCurrCode(idrMasterCurrency.getCurrCode());

                                    PerhitunganPenumpukan perhitunganPenumpukan = new PerhitunganPenumpukan();
                                    perhitunganPenumpukan.setRegistration(registration);
                                    perhitunganPenumpukan.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
                                    perhitunganPenumpukan.setContNo(receivingService.getContNo());
                                    perhitunganPenumpukan.setMlo(receivingService.getMlo());
                                    perhitunganPenumpukan.setBlNo(receivingService.getBlNo());
                                    perhitunganPenumpukan.setMasa1((short) 1);
                                    perhitunganPenumpukan.setMasa2((short) 0);
                                    perhitunganPenumpukan.setMasterActivity(penumpukanMasterActivity);
                                    perhitunganPenumpukan.setCurrCode(idrMasterCurrency.getCurrCode());

                                    BigDecimal[] discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), loloActivityCode);
                                    BigDecimal discountAsPercentage = discount[0];
                                    BigDecimal discountAsMoney = discount[1];

                                    if (discountAsMoney == null) {
                                        discountAsMoney = loloCharge.multiply(discountAsPercentage);
                                    }

                                    loloCharge = loloCharge.subtract(discountAsMoney);

                                    perhitunganLiftService.setCharge(loloCharge);

                                    discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), passGateActivityCode);
                                    discountAsPercentage = discount[0];
                                    discountAsMoney = discount[1];

                                    if (discountAsMoney == null) {
                                        discountAsMoney = passGateCharge.multiply(discountAsPercentage);
                                    }

                                    passGateCharge = passGateCharge.subtract(discountAsMoney);
                                    perhitunganPassGate.setCharge(passGateCharge);
                                    perhitunganPassGate.setJumlah(1);
                                    perhitunganPassGate.setTotalCharge(perhitunganPassGate.getCharge().multiply(new BigDecimal(perhitunganPassGate.getJumlah())));
                                    
                                    //BigDecimal totalHandlingCharge = BigDecimal.ZERO;

                                    discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), stevedoringActivityCode);
                                    discountAsPercentage = discount[0];
                                    discountAsMoney = discount[1];

                                    if (discountAsMoney == null) {
                                        discountAsMoney = stevedoringCharge.multiply(discountAsPercentage);
                                    }

                                    stevedoringCharge = stevedoringCharge.subtract(discountAsMoney);

                                    perhitunganStevedoring.setCharge(stevedoringCharge);
                                    
                                    discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), otherHandlingActivityCode);
                                    discountAsPercentage = discount[0];
                                    discountAsMoney = discount[1];

                                    if (discountAsMoney == null) {
                                        discountAsMoney = otherHandlingCharge.multiply(discountAsPercentage);
                                    }

                                    otherHandlingCharge = otherHandlingCharge.subtract(discountAsMoney);
                                    
                                    perhitunganOtherHandling.setCharge(otherHandlingCharge);
                                    
                                    
                                    discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), htActivityCode);
                                    discountAsPercentage = discount[0];
                                    discountAsMoney = discount[1];

                                    if (discountAsMoney == null) {
                                        discountAsMoney = htCharge.multiply(discountAsPercentage);
                                    }

                                    htCharge = htCharge.subtract(discountAsMoney);

                                    perhitunganHt.setCharge(htCharge);

                                    discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), penumpukanActivityCode);
                                    discountAsPercentage = discount[0];
                                    discountAsMoney = discount[1];

                                    if (discountAsMoney == null) {
                                        discountAsMoney = penumpukanCharge.multiply(discountAsPercentage);
                                    }

                                    penumpukanCharge = penumpukanCharge.subtract(discountAsMoney);

                                    if (receivingService.getDg().equals("TRUE") && receivingService.getDgLabel().equals("TRUE")) {
                                        penumpukanCharge = penumpukanCharge.multiply(BigDecimal.valueOf(2));
                                    } else if (receivingService.getDg().equals("TRUE") && receivingService.getDgLabel().equals("FALSE")) {
                                        penumpukanCharge = penumpukanCharge.multiply(BigDecimal.valueOf(3));
                                    }

                                    perhitunganPenumpukan.setJasaDermaga(BigDecimal.ZERO);
                                    perhitunganPenumpukan.setCharge(penumpukanCharge);
                                    perhitunganPenumpukan.setChargeM1(perhitunganPenumpukan.getCharge().multiply(BigDecimal.valueOf(perhitunganPenumpukan.getMasa1())));
                                    perhitunganPenumpukan.setChargeM2(perhitunganPenumpukan.getCharge().multiply(BigDecimal.valueOf(perhitunganPenumpukan.getMasa2())).multiply(BigDecimal.valueOf(2)));
                                    BigDecimal totalPenumpukanCharge = perhitunganPenumpukan.getChargeM1().add(perhitunganPenumpukan.getChargeM2()).add(perhitunganPenumpukan.getJasaDermaga());
                                    perhitunganPenumpukan.setTotalCharge(totalPenumpukanCharge);

                                    if (registration.getPlanningVessel().getIsLoadPortToPort()) {
                                        perhitunganHandlingDischargeFacadeRemote.create(perhitunganStevedoring);
                                        perhitunganHandlingDischargeFacadeRemote.create(perhitunganHt);
                                        perhitunganHandlingDischargeFacadeRemote.create(perhitunganOtherHandling);
                                        perhitunganPenumpukanFacadeRemote.create(perhitunganPenumpukan);
                                        perhitunganUpahBuruhFacadeRemote.create(perhitunganUbahBuruh);
                                    }
                                    
                                    receivingService.getMasterPort();

                                    perhitunganLiftServiceFacadeRemote.create(perhitunganLiftService);
                                    perhitunganPassGateFacadeRemote.create(perhitunganPassGate);
                                    planningContReceivingFacadeRemote.edit(planningContReceiving);
                                    receivingServiceFacadeRemote.create(receivingService);
                                    
                                    if (planningReceivingAllocation != null 
                                            && registration.getPlanningVessel().getPreserviceVessel().getMasterVessel().isKapalBayangan().equals("FALSE")) {
                                        planningReceivingAllocation.setTotalBooking2(planningReceivingAllocation.getTotalBooking2() + 1);
                                        planningReceivingAllocationFacadeRemote.edit(planningReceivingAllocation);
                                    }
                                    
                                    viewData();
                                    bersihObject();
                                    FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
                                    isValid = true;
                                }
                            }
                        }
                    }
                }
            }
        } catch (RuntimeException re) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, re);
        }

        requestContext.addCallbackParam("loggedIn", isValid);
    }

    public void cariBlNo(ActionEvent event) {
        blNo = (String) event.getComponent().getAttributes().get("blNo");
        planningContReceivingListGenerate = planningContReceivingFacadeRemote.findPlanningContReceivingIsGenerateTrueAndBlNo(registration.getPlanningVessel().getNoPpkb(), getBlNo());

        if (planningContReceivingListGenerate.size() < 1) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
            this.blNo = null;
            this.clearSave();
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
            this.clearSave();
        }
    }

    public void cariBlNo2(ActionEvent event) {
        blNo = (String) event.getComponent().getAttributes().get("blNo");
        planningContReceivingListGenerate = planningContReceivingFacadeRemote.findPlanningContReceivingIsGenerateTrue(registration.getPlanningVessel().getNoPpkb());

        if (planningContReceivingListGenerate.size() < 1) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
            clearSave();
            blNo = null;
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
            clearSave();
        }

        act = "all";
    }

    public void handleInputReceivingFromBaplie(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        try {
            receivingStatus = receivingServiceFacadeRemote.findReceivingCapacityStatusByNoPpkb(registration.getPlanningVessel().getNoPpkb());
            Long currentGrt = ((Number) receivingStatus[0]).longValue();
            Long currentContCapacity = ((Number) receivingStatus[1]).longValue();
            Integer successfullySaved = 0;
            List<Object[]> notAllocatedContNo = new ArrayList();
            String portCode = masterSettingAppFacade.findImplementedPortCode();
            MasterPort implementedPort = masterPortFacadeRemote.find(portCode);
            planningVessel = planningVesselFacadeRemote.find(registration.getPlanningVessel().getNoPpkb());
            receivingAllocationCapacityInfo = null;

            for (int i = 0; i < receivingObjectArray.length; i++) {
                planningContReceiving = planningContReceivingFacadeRemote.find(receivingObjectArray[i][0]);

                if (planningContReceiving.getMasterContainerType().getMasterContainerTypeInGeneral().getId().equals(MasterContainerTypeInGeneral.TYPE_REEFER) && registration.getPlanningVessel().getIsClosingDocReeferExpired()) {
                    FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.time_expired", facesContext), "Closing Document Reefer"), null);
                    continue;
                }

                if (!planningContReceiving.getMasterContainerType().getMasterContainerTypeInGeneral().getId().equals(MasterContainerTypeInGeneral.TYPE_REEFER) && registration.getPlanningVessel().getIsClosingDocExpired()) {
                    FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.time_expired", facesContext), "Closing Document"), null);
                    continue;
                }

                if (currentGrt + (planningContReceiving.getContGross() / 1000) > registration.getPlanningVessel().getPreserviceVessel().getMasterVessel().getGrt()) {
                    FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.exceed_limit", facesContext), "Container Gross", "(max " + registration.getPlanningVessel().getPreserviceVessel().getMasterVessel().getContCapacity() + " ton)"), null);
                    continue;
                }

                if (currentContCapacity + planningContReceiving.getContTeus() > registration.getPlanningVessel().getPreserviceVessel().getMasterVessel().getContCapacity()) {
                    FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.exceed_limit", facesContext), "Container Capacity", "(max " + registration.getPlanningVessel().getPreserviceVessel().getMasterVessel().getContCapacity() + " teus)"), null);
                    continue;
                }

                List<Object[]> alreadyEnteredCy = masterYardCoordinatFacadeRemote.findByContNoStatusExist(planningContReceiving.getContNo());
                if (!alreadyEnteredCy.isEmpty()) {
                    for (Object[] container: alreadyEnteredCy) {
                        String location = container[7] + " " + container[4] + " " + container[5] + " " + container[6];
                        String contNo = (String) container[0];
                        FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.receiving.exist_on_cy", facesContext), contNo, location), null);
                    }
                    continue;
                }

                List<Object[]> hasJobSlipAndNotEnteringGateYet = receivingServiceFacadeRemote.findNotEnteringGateYetByContNo(planningContReceiving.getContNo());
                if (!hasJobSlipAndNotEnteringGateYet.isEmpty()) {
                    for (Object[] container: hasJobSlipAndNotEnteringGateYet) {
                        Object contNo = container[8];
                        FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.receiving.has_job_but_not_entering_gate_yet", facesContext), contNo), null);
                    }
                    continue;
                }

                boolean isDangerous = planningContReceiving.getMasterCommodity() != null && planningContReceiving.getMasterCommodity().getMasterDangerousClass() != null && MasterDangerousClass.affectedToHandling.contains(planningContReceiving.getMasterCommodity().getMasterDangerousClass().getDangerousClass());

                Object[] temp = planningReceivingAllocationFacadeRemote.findPlanningReceivingByQueryCopy(registration.getPlanningVessel().getNoPpkb(),
                        planningContReceiving.getContSize(),
                        planningContReceiving.getMasterContainerType().getContType(),
                        planningContReceiving.getContStatus(),
                        planningContReceiving.getOverSize(),
                        planningContReceiving.getDg(),
                        planningContReceiving.getIsExport(),
                        planningContReceiving.getGrossClass(),
                        planningContReceiving.getDischPort());

                if (isDangerous || temp != null) {
                    if (temp == null) {
                        temp = new Object[] {
                            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null
                        };
                    }

                    planningReceivingAllocation = planningReceivingAllocationFacadeRemote.find(temp[0]);

                    if (!isDangerous && (planningReceivingAllocation == null || (planningReceivingAllocation.getTotalBooking2() >= planningReceivingAllocation.getAllocated() 
                            && registration.getPlanningVessel().getPreserviceVessel().getMasterVessel().isKapalBayangan().equals("FALSE")))) {
                        notAllocatedContNo.add(receivingObjectArray[i]);
                        continue;
                    } else if (!isDangerous) {
                        if (registration.getPlanningVessel().getPreserviceVessel().getMasterVessel().isKapalBayangan().equals("FALSE")) {
                            planningReceivingAllocation.setTotalBooking2(planningReceivingAllocation.getTotalBooking2() + 1);
                            planningReceivingAllocation.setCountBaplie2(planningReceivingAllocation.getCountBaplie2() - 1);
                            planningReceivingAllocationFacadeRemote.edit(planningReceivingAllocation);
                        }
                    }
                } else {
                    notAllocatedContNo.add(receivingObjectArray[i]);
                    continue;
                }

                MasterPort dischargePort = masterPortFacadeRemote.find(planningContReceiving.getDischPort());
                currentGrt = currentGrt + (planningContReceiving.getContGross() / 1000);
                currentContCapacity = currentContCapacity + planningContReceiving.getContTeus();
                receivingStatus[0] = currentGrt;
                receivingStatus[1] = currentContCapacity;

                receivingService = new ReceivingService();
                receivingService.setMasa1((short) 0);
                receivingService.setMasa2((short) 0);
                receivingService.setJobSlip(iDGeneratorFacade.generateJobSlipID("01"));
                receivingService.setBlNo(planningContReceiving.getBlNo());
                receivingService.setMasterContainerType(planningContReceiving.getMasterContainerType());
                receivingService.setMasterCommodity(planningContReceiving.getMasterCommodity());
                receivingService.setMasterPort(dischargePort);
                receivingService.setTwentyOneFeet(planningContReceiving.getTwentyOneFeet());
                receivingService.setMasterPort1(implementedPort);
                receivingService.setContNo(planningContReceiving.getContNo());
                receivingService.setMlo(planningContReceiving.getMlo());
                receivingService.setContSize(planningContReceiving.getContSize());
                receivingService.setContStatus(planningContReceiving.getContStatus());
                receivingService.setContGross(planningContReceiving.getContGross());
                receivingService.setSealNo(planningContReceiving.getSealNo());
                receivingService.setDg(planningContReceiving.getDg());
                receivingService.setDgLabel(planningContReceiving.getDgLabel());
                receivingService.setOverSize(planningContReceiving.getOverSize());
                receivingService.setBlNo(planningContReceiving.getBlNo());
                receivingService.setIsExport(planningContReceiving.getIsExport());
                receivingService.setPlanningVessel(registration.getPlanningVessel());
                receivingService.setRegistration(registration);
                receivingService.setStatusCancelLoading("FALSE");
                receivingService.setValidDate(planningVessel.getCloseRec());
                receivingService.setIsGenerate("TRUE");
                receivingService.setPortOfDelivery(planningContReceiving.getPortOfDelivery());

                planningContReceiving.setPosition("01");
                planningContReceiving.setIsGenerate("FALSE");
                
                 
                String dangerousClass = null;
                if(receivingService.getMasterCommodity() != null && receivingService.getMasterCommodity().getMasterDangerousClass() != null)
                    dangerousClass = receivingService.getMasterCommodity().getMasterDangerousClass().getDangerousClass();


                boolean bIsOverSize = false;
                if ((!receivingService.getContStatus().equals("MTY") && receivingService.getOverSize().equals("TRUE")) 
                        || receivingService.getTwentyOneFeet().equals("TRUE")) {
                    bIsOverSize = true;
                }

                String passGateActivityCode = masterActivityFacadeRemote.translatePassGateActivityCode(receivingService.getContStatus(), receivingService.getContSize());
                String stevedoringActivityCode = masterActivityFacadeRemote.translateStevedoringActivityCode(
                        receivingService.getContStatus(), 
                        receivingService.getContSize(), "L", 
                        receivingService.getOverSize().equals("TRUE") ? true : false, 
                        "CC",
                        receivingService.getDg().equals("TRUE"),
                        receivingService.getDgLabel().equals("TRUE"),
                        dangerousClass,
                        bIsOverSize,
                        receivingService.getTwentyOneFeet().equals("TRUE"));
                
                String htActivityCode = masterActivityFacadeRemote.translateHaulageTruckingActivityCode(
                        receivingService.getContStatus(), 
                        receivingService.getContSize(), "L", 
                        receivingService.getOverSize().equals("TRUE") ? true : false, 
                        "CC",
                        receivingService.getDg().equals("TRUE"),
                        receivingService.getDgLabel().equals("TRUE"),
                        dangerousClass,
                        bIsOverSize,
                        receivingService.getTwentyOneFeet().equals("TRUE"));
                
                String otherHandlingActivityCode = masterActivityFacadeRemote.translateOtherHandlingChargesActivityCode(
                        receivingService.getContStatus(), 
                        receivingService.getContSize(), "L", 
                        receivingService.getOverSize().equals("TRUE") ? true : false, 
                        "CC",
                        receivingService.getDg().equals("TRUE"),
                        receivingService.getDgLabel().equals("TRUE"),
                        dangerousClass,
                        bIsOverSize,
                        receivingService.getTwentyOneFeet().equals("TRUE"));
                
                String loloActivityCode = masterActivityFacadeRemote.translateLoLoActivityCode(
                        receivingService.getContStatus(), 
                        receivingService.getContSize(), 
                        receivingService.getOverSize().equals("TRUE") ? true : false, 
                        receivingService.getDg().equals("TRUE"),
                        receivingService.getDgLabel().equals("TRUE"),
                        dangerousClass,"CC");
                
                boolean bIsOverSized = false;
                if ((!receivingService.getContStatus().equals("MTY") && receivingService.getOverSize().equals("TRUE")) 
                        || receivingService.getTwentyOneFeet().equals("FALSE")) {
                    bIsOverSized = true;
                }
                String penumpukanActivityCode = masterActivityFacadeRemote.translatePenumpukanActivityCode(
                        receivingService.getContStatus(), 
                        receivingService.getMasterContainerType().getIsoCode(), 
                        bIsOverSized, 
                        receivingService.getContSize(), false);
                
                String upahBuruhActivityCode = masterActivityFacadeRemote.translateUbahBuruhHandlingActivityCode(
                        receivingService.getContStatus(), 
                        receivingService.getContSize(), 
                        receivingService.getOverSize().equals("TRUE") ? true : false);

                MasterActivity passGateMasterActivity = masterActivityFacadeRemote.find(passGateActivityCode);
                MasterActivity penumpukanMasterActivity = masterActivityFacadeRemote.find(penumpukanActivityCode);
                MasterActivity loloMasterActivity = masterActivityFacadeRemote.find(loloActivityCode);
                MasterActivity upahBuruhMasterActivity = masterActivityFacadeRemote.find(upahBuruhActivityCode);
                MasterCurrency idrMasterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();

                BigDecimal penumpukanCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(idrMasterCurrency.getCurrCode(), penumpukanActivityCode);
                BigDecimal stevedoringCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), stevedoringActivityCode);
                BigDecimal htCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), htActivityCode);
                BigDecimal otherHandlingCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), otherHandlingActivityCode);
                BigDecimal loloCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(idrMasterCurrency.getCurrCode(), loloActivityCode);
                BigDecimal passGateCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(idrMasterCurrency.getCurrCode(), passGateActivityCode);
                BigDecimal upahBuruhCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(idrMasterCurrency.getCurrCode(), upahBuruhActivityCode);
                
                PerhitunganPassGate perhitunganPassGate = new PerhitunganPassGate();
                perhitunganPassGate.setJobSlip(receivingService.getJobSlip());
                perhitunganPassGate.setMasterActivity(passGateMasterActivity);
                perhitunganPassGate.setPlanningVessel(planningVessel);
                perhitunganPassGate.setRegistration(registration);

                PerhitunganLiftService perhitunganLiftService = new PerhitunganLiftService();
                perhitunganLiftService.setContNo(receivingService.getContNo());
                perhitunganLiftService.setMlo(receivingService.getMlo());
                perhitunganLiftService.setNoPpkb(receivingService.getPlanningVessel().getNoPpkb());
                perhitunganLiftService.setRegistration(receivingService.getRegistration());
                perhitunganLiftService.setMasterActivity(loloMasterActivity);
                perhitunganLiftService.setCurrCode(idrMasterCurrency.getCurrCode());

                PerhitunganHandlingDischarge perhitunganStevedoring = new PerhitunganHandlingDischarge();
                perhitunganStevedoring.setNoReg(registration.getNoReg());
                perhitunganStevedoring.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
                perhitunganStevedoring.setContNo(receivingService.getContNo());
                perhitunganStevedoring.setMlo(receivingService.getMlo());
                perhitunganStevedoring.setBlNo(receivingService.getBlNo());
                perhitunganStevedoring.setActivityCode(stevedoringActivityCode);
                perhitunganStevedoring.setCurrCode(masterCurrency.getCurrCode());
                
                PerhitunganHandlingDischarge perhitunganHt = new PerhitunganHandlingDischarge();
                perhitunganHt.setNoReg(registration.getNoReg());
                perhitunganHt.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
                perhitunganHt.setContNo(receivingService.getContNo());
                perhitunganHt.setMlo(receivingService.getMlo());
                perhitunganHt.setBlNo(receivingService.getBlNo());
                perhitunganHt.setActivityCode(htActivityCode);
                perhitunganHt.setCurrCode(masterCurrency.getCurrCode());
                
                PerhitunganHandlingDischarge perhitunganOtherHandling = new PerhitunganHandlingDischarge();
                perhitunganHt.setNoReg(registration.getNoReg());
                perhitunganHt.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
                perhitunganHt.setContNo(receivingService.getContNo());
                perhitunganHt.setMlo(receivingService.getMlo());
                perhitunganHt.setBlNo(receivingService.getBlNo());
                perhitunganHt.setActivityCode(otherHandlingActivityCode);
                perhitunganHt.setCurrCode(masterCurrency.getCurrCode());

                PerhitunganUpahBuruh perhitunganUbahBuruh = new PerhitunganUpahBuruh();
                perhitunganUbahBuruh.setRegistration(registration);
                perhitunganUbahBuruh.setJobslip(receivingService.getJobSlip());
                perhitunganUbahBuruh.setMasterActivity(upahBuruhMasterActivity);
                perhitunganUbahBuruh.setMasterCurrency(idrMasterCurrency);
                perhitunganUbahBuruh.setCharge(upahBuruhCharge);

                PerhitunganPenumpukan perhitunganPenumpukan = new PerhitunganPenumpukan();
                perhitunganPenumpukan.setRegistration(registration);
                perhitunganPenumpukan.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
                perhitunganPenumpukan.setContNo(receivingService.getContNo());
                perhitunganPenumpukan.setMlo(receivingService.getMlo());
                perhitunganPenumpukan.setBlNo(receivingService.getBlNo());
                perhitunganPenumpukan.setMasa1((short) 1);
                perhitunganPenumpukan.setMasa2((short) 0);
                perhitunganPenumpukan.setMasterActivity(penumpukanMasterActivity);
                perhitunganPenumpukan.setCurrCode(idrMasterCurrency.getCurrCode());

                BigDecimal[] discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), loloActivityCode);
                BigDecimal discountAsPercentage = discount[0];
                BigDecimal discountAsMoney = discount[1];

                if (discountAsMoney == null) {
                    discountAsMoney = loloCharge.multiply(discountAsPercentage);
                }

                loloCharge = loloCharge.subtract(discountAsMoney);

                perhitunganLiftService.setCharge(loloCharge);

                discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), passGateActivityCode);
                discountAsPercentage = discount[0];
                discountAsMoney = discount[1];

                if (discountAsMoney == null) {
                    discountAsMoney = passGateCharge.multiply(discountAsPercentage);
                }

                passGateCharge = passGateCharge.subtract(discountAsMoney);

                perhitunganPassGate.setCharge(passGateCharge);
                perhitunganPassGate.setJumlah(1);
                perhitunganPassGate.setTotalCharge(perhitunganPassGate.getCharge().multiply(new BigDecimal(perhitunganPassGate.getJumlah())));

                //BigDecimal totalHandlingCharge = BigDecimal.ZERO;

                discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), stevedoringActivityCode);
                discountAsPercentage = discount[0];
                discountAsMoney = discount[1];
                

                if (discountAsMoney == null) {
                    discountAsMoney = stevedoringCharge.multiply(discountAsPercentage);
                }
                stevedoringCharge = stevedoringCharge.subtract(discountAsMoney);
                perhitunganStevedoring.setCharge(stevedoringCharge);
                
                discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), htActivityCode);
                discountAsPercentage = discount[0];
                discountAsMoney = discount[1];
                
                if (discountAsMoney == null) {
                    discountAsMoney = htCharge.multiply(discountAsPercentage);
                }
                htCharge = htCharge.subtract(discountAsMoney);
                perhitunganHt.setCharge(htCharge);
                
                discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), otherHandlingActivityCode);
                discountAsPercentage = discount[0];
                discountAsMoney = discount[1];
                
                if (discountAsMoney == null) {
                    discountAsMoney = otherHandlingCharge.multiply(discountAsPercentage);
                }
                otherHandlingCharge = otherHandlingCharge.subtract(discountAsMoney);
                perhitunganOtherHandling.setCharge(otherHandlingCharge);

                discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), penumpukanActivityCode);
                discountAsPercentage = discount[0];
                discountAsMoney = discount[1];
                BigDecimal totalPenumpukanCharge = BigDecimal.ZERO;

                if (discountAsMoney == null) {
                    discountAsMoney = penumpukanCharge.multiply(discountAsPercentage);
                }

                penumpukanCharge = penumpukanCharge.subtract(discountAsMoney);

                if (receivingService.getDg().equals("TRUE") && receivingService.getDgLabel().equals("TRUE")) {
                    totalPenumpukanCharge = penumpukanCharge.multiply(BigDecimal.valueOf(2));
                } else if (receivingService.getDg().equals("TRUE") && receivingService.getDgLabel().equals("FALSE")) {
                    totalPenumpukanCharge = penumpukanCharge.multiply(BigDecimal.valueOf(3));
                } else {
                    totalPenumpukanCharge = penumpukanCharge;
                }

                perhitunganPenumpukan.setCharge(totalPenumpukanCharge);
                perhitunganPenumpukan.setChargeM1(perhitunganPenumpukan.getCharge().multiply(BigDecimal.valueOf(perhitunganPenumpukan.getMasa1())));
                perhitunganPenumpukan.setChargeM2(perhitunganPenumpukan.getCharge().multiply(BigDecimal.valueOf(perhitunganPenumpukan.getMasa2())).multiply(BigDecimal.valueOf(2)));
                perhitunganPenumpukan.setJasaDermaga(BigDecimal.ZERO);
                totalPenumpukanCharge = perhitunganPenumpukan.getChargeM1().add(perhitunganPenumpukan.getChargeM2()).add(perhitunganPenumpukan.getJasaDermaga());
                perhitunganPenumpukan.setTotalCharge(totalPenumpukanCharge);

                if (registration.getPlanningVessel().getIsLoadPortToPort()) {
                    perhitunganHandlingDischargeFacadeRemote.create(perhitunganStevedoring);
                    perhitunganHandlingDischargeFacadeRemote.create(perhitunganHt);
                    perhitunganHandlingDischargeFacadeRemote.create(perhitunganOtherHandling);
                    perhitunganPenumpukanFacadeRemote.create(perhitunganPenumpukan);
                    perhitunganUpahBuruhFacadeRemote.create(perhitunganUbahBuruh);
                }

                perhitunganPassGateFacadeRemote.create(perhitunganPassGate);
                perhitunganLiftServiceFacadeRemote.create(perhitunganLiftService);
                receivingServiceFacadeRemote.create(receivingService);
                planningContReceivingFacadeRemote.edit(planningContReceiving);

                successfullySaved++;
            }

            Boolean isReceivingAllocationNotEnough = false;

            if (!notAllocatedContNo.isEmpty()) {
                String contListAsString = SqlHelper.translateListAsSqlString(notAllocatedContNo, 4);
                receivingAllocationCapacityInfo = masterYardCoordinatFacadeRemote.findReceivingPlanningAllocations(planningVessel.getNoPpkb(), contListAsString);
                isReceivingAllocationNotEnough = true;
                FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.alokasi_booking");
            }

            if (successfullySaved == 0){
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.save.failed");
            } else if (successfullySaved < receivingObjectArray.length) {
                FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", String.format(FacesHelper.getLocaleMessage("application.validation.some_data_fail_to_saved", facesContext), successfullySaved, receivingObjectArray.length), null);
            } else if (successfullySaved == receivingObjectArray.length) {
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            }

            viewData();

            if (getAct().equals("all")) {
                planningContReceivingListGenerate = planningContReceivingFacadeRemote.findPlanningContReceivingIsGenerateTrue(registration.getPlanningVessel().getNoPpkb());
            } else {
                planningContReceivingListGenerate = planningContReceivingFacadeRemote.findPlanningContReceivingIsGenerateTrueAndBlNo(registration.getPlanningVessel().getNoPpkb(), getBlNo());
            }

            clearSave();
            receivingObjectArray = (Object[][]) new ArrayList().toArray(new Object[][]{});
            requestContext.addCallbackParam("isReceivingAllocationNotEnough", isReceivingAllocationNotEnough);
            
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (RuntimeException re) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, re);
        }
    }

    public void handleSelectCommodity() {
        receivingService.setDg("FALSE");

        if (receivingService.getMasterCommodity() != null && receivingService.getMasterCommodity().getMasterDangerousClass() != null) {
            receivingService.setDg("TRUE");
        }
    }

    public void onChangeEvent(ValueChangeEvent event) {
        String temp = (String) event.getNewValue();
        invoice.setPaymentType(temp);

        if (temp.equalsIgnoreCase("CASH")) {
            disKredit = false;
        } else {
            disKredit = true;
        }
    }

    public void onChangeConstraintType(ValueChangeEvent event) {
        int newItem = (Integer) event.getNewValue();

        masterContainerType = masterContainerTypeFacadeRemote.find(newItem);
        receivingService.setMasterContainerType(masterContainerType);
        receivingService.setContSize(masterContainerType.getFeetMark());
    }

    public void handleAddCancelInvoice(ActionEvent event) {
        batalNota = new BatalNota();
    }

    //FUNCTION BATAL NOTA
    public void handleSubmitBatalNota(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        receivingStatus = receivingServiceFacadeRemote.findReceivingCapacityStatusByNoPpkb(registration.getPlanningVessel().getNoPpkb());

        if (!disCancelInv) {
            try {
                batalNota.setCustCode(registration.getMasterCustomer().getCustCode());
                batalNota.setNoFakturPajak(invoice.getNoFakturPajak());
                final String noInvoice = invoice.getNoInvoice();
                batalNota.setNoInvoice(noInvoice);
                batalNota.setNoReg(invoice.getNoReg());
                batalNota.setPaymentDate(invoice.getPaymentDate());
                batalNota.setTotalTagihan(invoice.getTotalTagihan());

                batalNotaFacadeRemote.create(batalNota);

                invoice.setCancelInvoice("TRUE");
                invoice.setNoFakturPajak(null);
                invoice.setNoInvoice(null);

                invoiceFacadeRemote.edit(invoice);
                
                List<Object[]> receivingList = receivingServiceFacadeRemote.findByListBatalNotaService(registration.getPlanningVessel().getNoPpkb(), registration.getNoReg());

                for (int i = 0; i < receivingList.size(); i++) {
                    receivingService = receivingServiceFacadeRemote.find(receivingList.get(i)[0]);
                    planningContReceivingIdFromBaplie = planningContReceivingFacadeRemote.findPlanningCont(receivingService.getContNo(), receivingService.getPlanningVessel().getNoPpkb(), "TRUE");
                    planningContReceivingId = planningContReceivingFacadeRemote.findPlanningCont(receivingService.getContNo(), receivingService.getPlanningVessel().getNoPpkb(), "FALSE");
                    String grossClass = "";
                    if (planningContReceivingIdFromBaplie != null) {
                        planningContReceiving = planningContReceivingFacadeRemote.find(planningContReceivingIdFromBaplie);
                        planningContReceiving.setIsGenerate("TRUE");
                        planningContReceiving.setPosition(null);
                        grossClass = planningContReceiving.getGrossClass();
                        planningContReceivingFacadeRemote.edit(planningContReceiving);
                    } else if (planningContReceivingId != null) {
                        planningContReceiving = planningContReceivingFacadeRemote.find(planningContReceivingId);
                        grossClass = planningContReceiving.getGrossClass();
                        planningContReceivingFacadeRemote.remove(planningContReceiving);
                    }

                    Object[] temp = planningReceivingAllocationFacadeRemote.findPlanningReceivingByQueryCopy(receivingService.getPlanningVessel().getNoPpkb(), receivingService.getContSize(), receivingService.getMasterContainerType().getContType(), receivingService.getContStatus(), receivingService.getOverSize(), receivingService.getDg(), receivingService.getIsExport(), grossClass, receivingService.getMasterPort1().getPortCode());

                    if (temp != null) {
                        if (receivingService.getPlanningVessel().getPreserviceVessel().getMasterVessel().isKapalBayangan().equals("FALSE")) {
                            planningReceivingAllocation = planningReceivingAllocationFacadeRemote.find(temp[0]);
                            planningReceivingAllocation.setTotalBooking2(planningReceivingAllocation.getTotalBooking2() - 1);
                            planningReceivingAllocationFacadeRemote.edit(planningReceivingAllocation);
                        }
                    }
                    
                    receivingStatus[0] = ((Number) receivingStatus[0]).longValue() - receivingService.getContGross();
                    receivingStatus[1] = ((Number) receivingStatus[1]).longValue() - receivingService.getContTeus();
                    receivingServiceFacadeRemote.remove(receivingService);
                }

                perhitunganPassGateFacadeRemote.deleteByNoReg(registration.getNoReg());
                perhitunganLiftServiceFacadeRemote.deleteByNoReg(registration.getNoReg());
                perhitunganHandlingDischargeFacadeRemote.deleteByNoReg(registration.getNoReg());
                perhitunganPenumpukanFacadeRemote.deleteByNoReg(registration.getNoReg());
                perhitunganUpahBuruhFacadeRemote.deleteByNoReg(registration.getNoReg());

                receivingServices.clear();
                receivingStatus = receivingServiceFacadeRemote.findReceivingCapacityStatusByNoPpkb(registration.getPlanningVessel().getNoPpkb());
                disCancelInv = Boolean.TRUE;

                if (masterSettingAppFacade.isPaymentBankingEnabled())
                    try {
                        bankingSyncFacadeRemote.cancelInvoice(noInvoice);
                    } catch (RuntimeException e) {
                        System.err.println(FacesHelper.getLocaleMessage("application.integration.banking.failed", FacesContext.getCurrentInstance()));
                        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.integration.banking.failed");
                    }

                loggedIn = true;
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            } catch (RuntimeException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
            }

            context.addCallbackParam("loggedIn", loggedIn);
        } else {
            context.addCallbackParam("loggedIn", true);
        }
    }

    public void handleDownloadTemp(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/perhitunganReceiving.pdf?mode=" + "receiving"
                + "&noRegistrasi=" + registration.getNoReg()
                + "&status=" + invoice.getPaymentType()
                + "&ppn=" + String.valueOf(getPpnPrint())
                + "&materai=" + String.valueOf(getMateraiPrint())
                + "&userId=" + getUserId()
                + "&total=" + String.valueOf(total))));
    }

    public void handleDownloadNota(ActionEvent event) {
        invoice = invoiceFacadeRemote.find(registration.getNoReg());

        if (invoice.getValidasiPrint().equals("FALSE")) {
            invoice = invoiceFacadeRemote.publishInvoice(invoice);
            registration.setInvoice(invoice);
        }

        if (invoice.getNoInvoice() != null && invoice.isCancelInvoice().equals("FALSE")) {
            /*cek apakah container sudah masuk gate, apabila sudah maka invoice tidak dapat di cancel*/
            try {
                disCancelInv = receivingServiceFacadeRemote.isPassedThroughTheGate(registration.getNoReg());
            } catch (RuntimeException re) {
                disCancelInv = false;
            }
            /*akhir pengecekan container di gate*/
        }

        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();

        if (!disPrint || loginSessionBean.isSupervisor()) {
            context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
            context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/invoice.pdf?tipe=" + registration.getMasterService().getServiceCode()
                    + "&no_reg=" + registration.getNoReg()
                    + "&detail=" + String.valueOf(disDetail)
                    + "&to=" + invoice.getTotalTagihan()
                    + "&curr=" + masterCurrency.getCountry()
                    + "&tipe=" + registration.getMasterService().getServiceCode()
                    + "&username=" + getUserId())));
            context.addCallbackParam("doPrint", true);
        } else {
            context.addCallbackParam("doPrint", false);
        }

        disPrint = invoice.getValidasiPrint().equals("TRUE") ? true : false;
    }

    public void handleDownloadDetail(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();

        if (registration.getInvoice() == null || registration.getInvoice().getNoInvoice() == null) {
            context.addCallbackParam("doPrint", false);
            FacesHelper.addFacesMessage(fc, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.empty_invoice_number");
            return;
        }

        context.addCallbackParam("doPrint", true); 
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/detail.pdf?tipe=" + registration.getMasterService().getServiceCode()
                + "&no_reg=" + registration.getNoReg()
                + "&username=" + getUserId() + "&ppn=" + String.valueOf(ppnPrint) + "&materai=" + String.valueOf(materaiPrint) + "&total=" + String.valueOf(total))));
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
     * @return the planningContReceivingListGenerate
     */
    public List<Object[]> getPlanningContReceivingListGenerate() {
        return planningContReceivingListGenerate;
    }

    /**
     * @param planningContReceivingListGenerate the planningContReceivingListGenerate to set
     */
    public void setPlanningContReceivingListGenerate(List<Object[]> planningContReceivingListGenerate) {
        this.setPlanningContReceivingListGenerate(planningContReceivingListGenerate);
    }

    /**
     * @return the masterPorts
     */
    public List<MasterPort> getMasterPorts() {
        return masterPorts;
    }

    /**
     * @param masterPorts the masterPorts to set
     */
    public void setMasterPorts(List<MasterPort> masterPorts) {
        this.masterPorts = masterPorts;
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
     * @return the masterCustomer
     */
    public MasterCustomer getMasterCustomer() {
        return masterCustomer;
    }

    /**
     * @param masterCustomer the masterCustomer to set
     */
    public void setMasterCustomer(MasterCustomer masterCustomer) {
        this.masterCustomer = masterCustomer;
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
     * @return the receivingServices
     */
    public List<Object[]> getReceivingServices() {
        return receivingServices;
    }

    /**
     * @param receivingServices the receivingServices to set
     */
    public void setReceivingServices(List<Object[]> receivingServices) {
        this.receivingServices = receivingServices;
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
     * @return the invoice
     */
    public Invoice getInvoice() {
        return invoice;
    }

    /**
     * @param invoice the invoice to set
     */
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
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
     * @return the noReg
     */
    public String getNoRegistrasi() {
        return noRegistrasi;
    }

    /**
     * @param noReg the noReg to set
     */
    public void setNoRegistrasi(String noRegistrasi) {
        this.noRegistrasi = noRegistrasi;
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

    public MasterPort getMasterPort() {
        return masterPort;
    }

    public void setMasterPort(MasterPort masterPort) {
        this.masterPort = masterPort;
    }

    public MasterPort getMasterPort2() {
        return masterPort2;
    }

    public void setMasterPort2(MasterPort masterPort2) {
        this.masterPort2 = masterPort2;
    }

    public PlanningReceivingAllocation getPlanningReceivingAllocation() {
        return planningReceivingAllocation;
    }

    public void setPlanningReceivingAllocation(PlanningReceivingAllocation planningReceivingAllocation) {
        this.planningReceivingAllocation = planningReceivingAllocation;
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    public Integer getIdPlan() {
        return planningContReceivingIdFromBaplie;
    }

    public void setIdPlan(Integer idPlan) {
        this.planningContReceivingIdFromBaplie = idPlan;
    }

    public Integer getIdPlanDel() {
        return planningContReceivingId;
    }

    public void setIdPlanDel(Integer idPlanDel) {
        this.planningContReceivingId = idPlanDel;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

    public Object[][] getReceivingObjectArray() {
        return receivingObjectArray;
    }

    public void setReceivingObjectArray(Object[][] receivingObjectArray) {
        this.receivingObjectArray = receivingObjectArray;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public BigDecimal getMateraiPrint() {
        return materaiPrint;
    }

    public void setMateraiPrint(BigDecimal materaiPrint) {
        this.materaiPrint = materaiPrint;
    }

    public BigDecimal getPpnPrint() {
        return ppnPrint;
    }

    public void setPpnPrint(BigDecimal ppnPrint) {
        this.ppnPrint = ppnPrint;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Boolean getDisDetail() {
        return disDetail;
    }

    public void setDisDetail(Boolean disDetail) {
        this.disDetail = disDetail;
    }

    public Boolean getDisCancelInv() {
        return disCancelInv;
    }

    public void setDisCancelInv(Boolean disCancelInv) {
        this.disCancelInv = disCancelInv;
    }

    public String getOpsi() {
        return opsi;
    }

    public void setOpsi(String opsi) {
        this.opsi = opsi;
    }

    public Integer getIdBaru() {
        return idBaru;
    }

    public void setIdBaru(Integer idBaru) {
        this.idBaru = idBaru;
    }

    public String getComodityName() {
        return comodityName;
    }

    public void setComodityName(String comodityName) {
        this.comodityName = comodityName;
    }

    public Object[] getReceivingStatus() {
        return receivingStatus;
    }

    public List<Object[]> getReceivingAllocationCapacityInfo() {
        return receivingAllocationCapacityInfo;
    }

    public BatalNota getBatalNota() {
        return batalNota;
    }

    public void setBatalNota(BatalNota batalNota) {
        this.batalNota = batalNota;
    }

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    public BigDecimal getUpahBuruh() {
        return upahBuruh;
    }

    public Boolean getIsSimpatReady() {
        return isSimpatReady;
    }
}

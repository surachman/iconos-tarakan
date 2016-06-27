/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.BankingSyncFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.StrippingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.ejb.remote.StrippingCalculationOperationRemote;
import com.pelindo.ebtos.exception.GrossCapacityExceedTheLimitsException;
import com.pelindo.ebtos.exception.HasJobSlipAndNotEnteringGateYetException;
import com.pelindo.ebtos.exception.NotAllocatedReceivingException;
import com.pelindo.ebtos.exception.ReceivingAllocationIsNotEnoughException;
import com.pelindo.ebtos.exception.RegisteredWithSamePpkbContainerException;
import com.pelindo.ebtos.exception.TeusCapacityExceedTheLimitsException;
import com.pelindo.ebtos.model.PaketStrippingStuffing;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.StrippingService;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PerhitunganLiftService;
import com.pelindo.ebtos.model.db.PerhitunganPassGate;
import com.pelindo.ebtos.model.db.PerhitunganSewaAlat;
import com.pelindo.ebtos.model.db.PerhitunganStripping;
import com.pelindo.ebtos.model.db.PerhitunganUpahBuruh;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "strippingBean")
@ViewScoped
public class StrippingBean implements Serializable {
    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacade;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacade;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacade;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private StrippingServiceFacadeRemote strippingServiceFacade;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;
    @EJB
    private RegistrationFacadeRemote registrationFacade;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private StrippingCalculationOperationRemote strippingCalculationOperationRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private MasterPortFacadeRemote masterPortFacadeRemote;
    @EJB
    private MasterCustomerFacadeRemote masterCustomerFacadeRemote;
    @EJB
    private InvoiceFacadeRemote invoiceFacadeRemote;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacadeRemote;

    private LazyDataModel<PlanningVessel> availableVessels;
    private List<StrippingService> strippingServices;
    private List<Object[]> registrations;
    private List<Object[]> serviceContDischarges;
    private List<Object[]> masterCustomers;
    private BatalNota batalNota;
    private PlanningVessel planningVessel;
    private Registration registration;
    private StrippingService strippingService;
    private MasterCurrency masterCurrency;
    private PaketStrippingStuffing paketStripping;
    private BigDecimal total;
    private BigDecimal upahBuruh;
    private BigDecimal ppnPrint;
    private BigDecimal materaiPrint;
    private String userId;
    private Boolean disPrint;
    private Boolean disKredit;
    private Boolean disDetail;
    private Boolean disCancelInv;
    private Boolean isSimpatReady;
    private String dischargePortName;
    private String portOfDeliveryName;

    /** Creates a new instance of StrippingBean */
    public StrippingBean() {
    }

    @PostConstruct
    private void construct() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        registration.setInvoice(new Invoice());
        strippingService = new StrippingService();
        batalNota = new BatalNota();
        paketStripping = new PaketStrippingStuffing();

        total = BigDecimal.ZERO;
        upahBuruh = BigDecimal.ZERO;
        ppnPrint = BigDecimal.ZERO;
        materaiPrint = BigDecimal.ZERO;
        disPrint = Boolean.TRUE;
        disKredit = Boolean.FALSE;
        disDetail = Boolean.TRUE;
        disCancelInv = Boolean.TRUE;
        
        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        userId = loginSessionBean.getName();

        populateAvailablePlanningVessels();
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
    }

    private void populateAvailablePlanningVessels() {
        availableVessels = new LazyDataModel<PlanningVessel>(){
            @Override
            public List<PlanningVessel> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String,String> filters) {
                int count = planningVesselFacadeRemote.findByStatussesAndLoadOnly_Count(filters, "Approved", "ReadyService", "Servicing");
                availableVessels.setRowCount(count);

                return planningVesselFacadeRemote.findByStatussesAndLoadOnly(first, pageSize, sortField, sortOrder, filters, "Approved", "ReadyService", "Servicing");
            }

            @Override
            public void setRowIndex(final int rowIndex){
                if (rowIndex == -1 || getPageSize() == 0) {
                    super.setRowIndex(-1);
                } else {
                    super.setRowIndex(rowIndex % getPageSize());
                }
            }
        };
    }

    public void handleShowRegistrations(ActionEvent event) {
        registrations = registrationFacade.findRegistrationStriping();
    }

    public void handleSelectRegistration(ActionEvent event) {
        String noReg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(noReg);
        strippingServices = strippingServiceFacade.findByNoReg(registration.getNoReg());
        paketStripping = new PaketStrippingStuffing();
        viewData();
    }

    public void viewData() {
        disCancelInv = Boolean.TRUE;
        disDetail = Boolean.FALSE;
        disPrint = Boolean.TRUE;
        total = BigDecimal.ZERO;
        upahBuruh = BigDecimal.ZERO;
        
      /*  for (StrippingService o : strippingServices) {
            total = total.add(o.getPerhitunganStripping().getTotalCharge());
            upahBuruh = upahBuruh.add(o.getPerhitunganStripping().getPerhitunganUpahBuruh().getCharge());
        }
*/
        MasterSettingApp settingApp = masterSettingAppFacade.find("ppn");
        ppnPrint = settingApp.getValueFloat();
        masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();

        List<Object[]> materai = masterMateraiFacade.findByCurr(masterCurrency.getCurrCode());
        if (total.compareTo((BigDecimal) materai.get(1)[2]) == -1) { /* jumlah tagihan < nilai minimum */
            setMateraiPrint((BigDecimal) materai.get(0)[1]);
        } else if (total.compareTo((BigDecimal) materai.get(2)[2]) == -1) {
            setMateraiPrint((BigDecimal) materai.get(1)[1]);
        } else {
            setMateraiPrint((BigDecimal) materai.get(2)[1]);
        }
        
        if (registration.getInvoice() != null && registration.getInvoice().getNoReg() != null) {
            disPrint = registration.getInvoice().getValidasiPrint().equals("TRUE") ? true : false;
            disKredit = registration.getInvoice().getPaymentType().equals("KREDIT");
            disCancelInv = registration.getInvoice().isCancelInvoice().equals("TRUE") ? true : false;
        } else {
            registration.setInvoice(new Invoice());
            registration.getInvoice().setPaymentType("CASH");
            disPrint = Boolean.TRUE;
            disKredit = Boolean.FALSE;
        }
    }

    public void onChangeEvent(ValueChangeEvent event) {
        String temp = (String) event.getNewValue();
        registration.getInvoice().setPaymentType(temp);
        if (temp.equalsIgnoreCase("CASH")) {
            disKredit = Boolean.FALSE;
        } else {
            disKredit = Boolean.TRUE;
        }
    }

    public void calculatePaketStripping() {
        BigDecimal upahBuruhCharge = BigDecimal.ZERO;
        strippingService.getPerhitunganStripping().getPerhitunganSewaAlat().setCharge(BigDecimal.ZERO);
        
        //dummy
        strippingService.getPerhitunganStripping().getPerhitunganUpahBuruh().setCharge(BigDecimal.ZERO);

        /*
        if (paketStripping.isIsUseEquipment()) {
            String mekanikActivityCode = masterActivityFacade.translateStrippingStuffingMekanikActivityCode(paketStripping.getMekanik().toString());
            BigDecimal mekanikCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), mekanikActivityCode);
            mekanikCharge = mekanikCharge.multiply(BigDecimal.valueOf(paketStripping.getMekanikTonage()));
            upahBuruhCharge = upahBuruhCharge.add(mekanikCharge);
            
            String sewaForkLiftActivityCode = masterActivityFacade.translateSewaForkLiftActivityCode(paketStripping.getEquipment().toString(), strippingService.getContSize());
            BigDecimal sewaForkLiftCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), sewaForkLiftActivityCode);
            strippingService.getPerhitunganStripping().getPerhitunganSewaAlat().setCharge(sewaForkLiftCharge);
            paketStripping.setSewaAlatActivityCode(sewaForkLiftActivityCode);
        }

        String fullHandlingActivityCode = masterActivityFacade.translateStrippingStuffingFullHandlingActivityCode(paketStripping.getFullHandling().toString());
        BigDecimal fullHandlingCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), fullHandlingActivityCode);
        fullHandlingCharge = fullHandlingCharge.multiply(BigDecimal.valueOf(paketStripping.getFullHandlingTonage()));

        upahBuruhCharge = upahBuruhCharge.add(fullHandlingCharge);
        strippingService.getPerhitunganStripping().getPerhitunganUpahBuruh().setCharge(upahBuruhCharge);

        String upahBuruhStrippingActivityCode = masterActivityFacade.translateUpahBuruhStrippingStuffingActivityCode();
        paketStripping.setUpahBuruhActivityCode(upahBuruhStrippingActivityCode);
        */
    }

    public void handleAdd(ActionEvent event) {
        strippingService = new StrippingService();
        strippingService.setPerhitunganStripping(new PerhitunganStripping());
        strippingService.getPerhitunganStripping().setPerhitunganUpahBuruh(new PerhitunganUpahBuruh());
        strippingService.getPerhitunganStripping().setPerhitunganSewaAlat(new PerhitunganSewaAlat());
        strippingService.getPerhitunganStripping().setPerhitunganPassGate(new PerhitunganPassGate());
        strippingService.getPerhitunganStripping().setPerhitunganLiftService(new PerhitunganLiftService());

        planningVessel = new PlanningVessel();
    }

    public void handleShowStripAbleContainers(ActionEvent event) {
        serviceContDischarges = serviceContDischargeFacade.findServiceContDischargesStrippingService(registration.getPlanningVessel().getNoPpkb());
    }

    public void handleShowMasterCustomers(ActionEvent event) {
        masterCustomers = masterCustomerFacadeRemote.findMasterCustomers();
    }

    public void handleSelectStripAbleContainer(ActionEvent event) {
        try {
            Integer id = (Integer) event.getComponent().getAttributes().get("idCont");
            ServiceContDischarge scd = serviceContDischargeFacade.find(id);

            strippingService = new StrippingService();
            strippingService.setBlNo(scd.getBlNo());
            strippingService.setContNo(scd.getContNo());
            strippingService.setMlo(scd.getMlo());
            strippingService.setContSize(scd.getContSize());
            strippingService.setContGross(scd.getContGross());
            strippingService.setContStatus(scd.getContStatus());
            strippingService.setOverSize(scd.getOverSize());
            strippingService.setDg(scd.getDangerous());
            strippingService.setDgLabel(scd.getDgLabel());
            strippingService.setCommodity(scd.getMasterCommodity().getName());
            strippingService.setMasterContainerType(scd.getMasterContainerType());
            strippingService.setRegistration(registration);
            strippingService.setIsLoad("TRUE");
            strippingService.setPerhitunganStripping(new PerhitunganStripping());
            strippingService.getPerhitunganStripping().setPerhitunganUpahBuruh(new PerhitunganUpahBuruh());
            strippingService.getPerhitunganStripping().setPerhitunganSewaAlat(new PerhitunganSewaAlat());
            strippingService.getPerhitunganStripping().setPerhitunganPassGate(new PerhitunganPassGate());
            strippingService.getPerhitunganStripping().setPerhitunganLiftService(new PerhitunganLiftService());
            
            String strippingActivityCode = masterActivityFacade.translateStrippingStuffingActivityCode2(
                    strippingService.getContSize(),strippingService.getContStatus(),
                    strippingService.getOverSize().equals("TRUE") ? true : false);
            String liftActivityCode = masterActivityFacade.translateLoLoActivityCode("MTY", 
                    strippingService.getContSize(), 
                    false, 
                    strippingService.getDg().equals("TRUE"),
                    strippingService.getDgLabel().equals("TRUE"),
                    null,"CC");
            String passGateActivityCode = masterActivityFacade.translatePassGateActivityCode("MTY", strippingService.getContSize());

            BigDecimal strippingCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), strippingActivityCode);
            BigDecimal liftCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), liftActivityCode);
            BigDecimal passGateCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), passGateActivityCode);

            strippingService.getPerhitunganStripping().setCharge(strippingCharge);
            strippingService.getPerhitunganStripping().getPerhitunganPassGate().setCharge(passGateCharge);
            strippingService.getPerhitunganStripping().getPerhitunganPassGate().setJumlah(1);
            strippingService.getPerhitunganStripping().getPerhitunganPassGate().setTotalCharge(strippingService.getPerhitunganStripping().getPerhitunganPassGate().getCharge().multiply(BigDecimal.valueOf(strippingService.getPerhitunganStripping().getPerhitunganPassGate().getJumlah())));

            strippingService.getPerhitunganStripping().getPerhitunganLiftService().setCharge(BigDecimal.ZERO);

            paketStripping.setStrippingActivityCode(strippingActivityCode);
            paketStripping.setLiftActivityCode(liftActivityCode);
            paketStripping.setPassGateActivityCode(passGateActivityCode);

            calculatePaketStripping();
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when call handleSelectStripAbleContainer", re);
        }
    }

    public void handleConfirm(ActionEvent event) {
        boolean isValid = Boolean.FALSE;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();

        try {
            /**
            if ((paketStripping.getFullHandlingTonage() + paketStripping.getMekanikTonage()) == 0) {
                if (paketStripping.isIsUseEquipment()) {
                    FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "Tonage is required", "containerSelectionForm:mekanikTonase");
                }

                FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "Tonage is required", "containerSelectionForm:fullHandlingTonase");
                requestContext.addCallbackParam("isValid", isValid);
                return;
            }
            **/   
            if (strippingService.getJobSlip() == null) {
                strippingService.setJobSlip(iDGeneratorFacade.generateJobSlipID("06"));
            }
            
            MasterPort dischargePort = masterPortFacadeRemote.findMasterPortByName(dischargePortName);
            MasterPort portOfDelivery = masterPortFacadeRemote.findMasterPortByName(portOfDeliveryName);

            MasterActivity strippingMasterActivity = masterActivityFacade.find(paketStripping.getStrippingActivityCode());
            MasterActivity liftMasterActivity = masterActivityFacade.find(paketStripping.getLiftActivityCode());
            MasterActivity upahBuruhStrippingMasterActivity = masterActivityFacade.find(paketStripping.getUpahBuruhActivityCode());
            MasterActivity sewaAlatMasterActivity = masterActivityFacade.find(paketStripping.getSewaAlatActivityCode());
            MasterActivity passGateMasterActivity = masterActivityFacade.find(paketStripping.getPassGateActivityCode());
            
            strippingService.getPerhitunganStripping().getPerhitunganUpahBuruh().setMasterActivity(upahBuruhStrippingMasterActivity);
            strippingService.getPerhitunganStripping().getPerhitunganUpahBuruh().setRegistration(registration);
            strippingService.getPerhitunganStripping().getPerhitunganUpahBuruh().setJobslip(strippingService.getJobSlip());
            strippingService.getPerhitunganStripping().getPerhitunganUpahBuruh().setMasterCurrency(masterCurrency);

            strippingService.getPerhitunganStripping().getPerhitunganSewaAlat().setMasterActivity(sewaAlatMasterActivity);
            strippingService.getPerhitunganStripping().getPerhitunganSewaAlat().setJobslip(strippingService.getJobSlip());
            strippingService.getPerhitunganStripping().getPerhitunganSewaAlat().setRegistration(registration);

            strippingService.getPerhitunganStripping().getPerhitunganPassGate().setMasterActivity(passGateMasterActivity);
            strippingService.getPerhitunganStripping().getPerhitunganPassGate().setRegistration(registration);
            strippingService.getPerhitunganStripping().getPerhitunganPassGate().setJobSlip(strippingService.getJobSlip());
            strippingService.getPerhitunganStripping().getPerhitunganPassGate().setPlanningVessel(registration.getPlanningVessel());

            strippingService.getPerhitunganStripping().getPerhitunganLiftService().setMasterActivity(liftMasterActivity);
            strippingService.getPerhitunganStripping().getPerhitunganLiftService().setRegistration(registration);
            strippingService.getPerhitunganStripping().getPerhitunganLiftService().setBlNo(strippingService.getBlNo());
            strippingService.getPerhitunganStripping().getPerhitunganLiftService().setContNo(strippingService.getContNo());
            strippingService.getPerhitunganStripping().getPerhitunganLiftService().setCurrCode(masterCurrency.getCurrCode());
            strippingService.getPerhitunganStripping().getPerhitunganLiftService().setMlo(strippingService.getMlo());
            strippingService.getPerhitunganStripping().getPerhitunganLiftService().setNoPpkb(registration.getPlanningVessel().getNoPpkb());

            strippingService.getPerhitunganStripping().setMasterActivity(strippingMasterActivity);
            strippingService.getPerhitunganStripping().setTotalCharge(strippingService.getPerhitunganStripping().getCharge().add(strippingService.getPerhitunganStripping().getPerhitunganSewaAlat().getCharge()).add(strippingService.getPerhitunganStripping().getPerhitunganPassGate().getTotalCharge()).add(strippingService.getPerhitunganStripping().getPerhitunganLiftService().getCharge()));

            strippingService = strippingCalculationOperationRemote.saveStrippingContainers(strippingService, registration.getPlanningVessel(), dischargePort, portOfDelivery);
            strippingServices.add(strippingService);

            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            isValid = Boolean.TRUE;
            viewData();
            handleAdd(event);
        } catch (GrossCapacityExceedTheLimitsException ex) {
            FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.exceed_limit", facesContext), "Container Gross", "(max " + ex.getMaxGross() + " ton)"), null);
        } catch (TeusCapacityExceedTheLimitsException ex) {
            FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.exceed_limit", facesContext), "Container Capacity", "(max " + ex.getMaxTeus() + " teus)"), null);
        } catch (HasJobSlipAndNotEnteringGateYetException ex) {
            FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.receiving.has_job_but_not_entering_gate_yet", facesContext), strippingService.getContNo()), null);
        } catch (NotAllocatedReceivingException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.record_beda");
        } catch (ReceivingAllocationIsNotEnoughException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.alokasi_booking");
        } catch (RegisteredWithSamePpkbContainerException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.data_exist");
        } catch (RuntimeException re) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when call handleConfirm", re);
        }

        requestContext.addCallbackParam("isValid", isValid);
    }

    public void handleSubmit(ActionEvent event) {
        Boolean isValid = Boolean.TRUE;
        registration.getInvoice().setNoReg(registration.getNoReg());
        registration.getInvoice().setRegistration(registration);
        registration.getInvoice().setPaymentStatus("UNPAID");
        registration.getInvoice().setJumlahTagihan(total);
        registration.getInvoice().setAdditionalCharge(upahBuruh);
        registration.getInvoice().setValidasiPrint("FALSE");
        registration.getInvoice().setPpn(registration.getInvoice().getJumlahTagihan().multiply(ppnPrint));

        List<Object[]> materai = masterMateraiFacade.findByCurr(masterCurrency.getCurrCode());
        
        if (registration.getInvoice().getJumlahTagihan().compareTo((BigDecimal) materai.get(1)[2]) == -1) { /* jumlah tagihan < nilai minimum */
            registration.getInvoice().setMaterai((BigDecimal) materai.get(0)[1]);
            materaiPrint = registration.getInvoice().getMaterai();
        } else if (registration.getInvoice().getJumlahTagihan().compareTo((BigDecimal) materai.get(2)[2]) == -1) {
            registration.getInvoice().setMaterai((BigDecimal) materai.get(1)[1]);
            materaiPrint = registration.getInvoice().getMaterai();
        } else {
            registration.getInvoice().setMaterai((BigDecimal) materai.get(2)[1]);
            materaiPrint = registration.getInvoice().getMaterai();
        }

        registration.getInvoice().setTotalTagihan(registration.getInvoice().getJumlahTagihan().add(registration.getInvoice().getMaterai()).add(registration.getInvoice().getPpn()).add(registration.getInvoice().getAdditionalCharge()));
        registration.getInvoice().setMasterCurrency(masterCurrency);

        if (registration.getInvoice().getPaymentType().equalsIgnoreCase("CASH")) {
            registration.setStatusService("confirm");
        } else if (registration.getInvoice().getPaymentType().equalsIgnoreCase("KREDIT")) {
            registration.setStatusService("approve");
        }
        
        registration = registrationFacade.editAndFetch(registration);
        viewData();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        RequestContext.getCurrentInstance().addCallbackParam("isValid", isValid);
    }

    public void handleDelete(ActionEvent event) {
        strippingServices.remove(strippingService);
        strippingCalculationOperationRemote.removeStrippingContainer(strippingService);
        
        viewData();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
    }

    //funtion batal nota
    public void handleAddCancelInvoice(ActionEvent event) {
        batalNota = new BatalNota();
    }

    //FUNCTION BATAL NOTA
    public void handleSubmitBatalNota(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean isValid = Boolean.FALSE;
        
        try {
            final String noInvoice = registration.getInvoice().getNoInvoice();
            strippingCalculationOperationRemote.cancelInvoice(registration, batalNota);
            disCancelInv = Boolean.TRUE;
            strippingServices.clear();

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
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when call handleSubmitBatalNota", e);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }
        
        context.addCallbackParam("isValid", isValid);
    }

    public void handleDownloadTransactionRecap(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean doPrint = false;

        try {
            String key = strippingCalculationOperationRemote.preparePerhitunganReport(registration, masterCurrency, total.multiply(ppnPrint), materaiPrint, strippingServices);

            if (key != null) {
                doPrint = true;
                requestContext.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
                requestContext.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(facesContext, String.format("/report?key=%s", key))));
            } else {
                throw new RuntimeException("failed to generate report");
            }
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when handleDownloadTransactionRecap", re);
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
        String key = strippingCalculationOperationRemote.prepareInvoiceReport(registration, strippingServices);

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
            String key = strippingCalculationOperationRemote.preparePerhitunganInvoiceReport(registration, strippingServices);

            if (key != null) {
                doPrint = true;
                requestContext.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
                requestContext.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(facesContext, String.format("/report?key=%s", key))));
            } else {
                throw new RuntimeException("failed to generate report");
            }
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when handleDownloadDetail", re);
        }

        requestContext.addCallbackParam("doPrint", doPrint);
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
     * @return the strippingServices
     */
    public List<StrippingService> getStrippingServices() {
        return strippingServices;
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
     * @return the serviceContDischarges
     */
    public List<Object[]> getServiceContDischarges() {
        return serviceContDischarges;
    }

    /**
     * @param serviceContDischarges the serviceContDischarges to set
     */
    public void setServiceContDischarges(List<Object[]> serviceContDischarges) {
        this.serviceContDischarges = serviceContDischarges;
    }

    /**
     * @return the strippingService
     */
    public StrippingService getStrippingService() {
        return strippingService;
    }

    /**
     * @param strippingService the strippingService to set
     */
    public void setStrippingService(StrippingService strippingService) {
        this.strippingService = strippingService;
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

    /**
     * @return the disPrint
     */
    public Boolean getDisPrint() {
        return disPrint;
    }

    /**
     * @param disPrint the disPrint to set
     */
    public void setDisPrint(Boolean disPrint) {
        this.disPrint = disPrint;
    }

    /**
     * @return the disKredit
     */
    public Boolean getDisKredit() {
        return disKredit;
    }

    /**
     * @param disKredit the disKredit to set
     */
    public void setDisKredit(Boolean disKredit) {
        this.disKredit = disKredit;
    }

    /**
     * @return the disDetail
     */
    public Boolean getDisDetail() {
        return disDetail;
    }

    /**
     * @param disDetail the disDetail to set
     */
    public void setDisDetail(Boolean disDetail) {
        this.disDetail = disDetail;
    }

    public BatalNota getBatalNota() {
        return batalNota;
    }

    public void setBatalNota(BatalNota batalNota) {
        this.batalNota = batalNota;
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

    public BigDecimal getUpahBuruh() {
        return upahBuruh;
    }

    public LazyDataModel<PlanningVessel> getAvailableVessels() {
        return availableVessels;
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    public List<Object[]> getMasterCustomers() {
        return masterCustomers;
    }

    public Boolean getIsSimpatReady() {
        return isSimpatReady;
    }

    public PaketStrippingStuffing getPaketStripping() {
        return paketStripping;
    }

    public void setPaketStripping(PaketStrippingStuffing paketStripping) {
        this.paketStripping = paketStripping;
    }

    public String getPortOfDeliveryName() {
        return portOfDeliveryName;
    }

    public void setPortOfDeliveryName(String portOfDeliveryName) {
        this.portOfDeliveryName = portOfDeliveryName;
    }

    public String getDischargePortName() {
        return dischargePortName;
    }

    public void setDischargePortName(String dischargePortName) {
        this.dischargePortName = dischargePortName;
    }
}

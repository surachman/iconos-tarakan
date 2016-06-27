/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.BankingSyncFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.StuffingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.ejb.remote.StuffingCalculationOperationRemote;
import com.pelindo.ebtos.exception.GrossCapacityExceedTheLimitsException;
import com.pelindo.ebtos.exception.HasJobSlipAndNotEnteringGateYetException;
import com.pelindo.ebtos.exception.NotAllocatedReceivingException;
import com.pelindo.ebtos.exception.ReceivingAllocationIsNotEnoughException;
import com.pelindo.ebtos.exception.RegisteredWithSamePpkbContainerException;
import com.pelindo.ebtos.exception.TeusCapacityExceedTheLimitsException;
import com.pelindo.ebtos.model.PaketStrippingStuffing;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.StuffingService;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PerhitunganLiftService;
import com.pelindo.ebtos.model.db.PerhitunganPassGate;
import com.pelindo.ebtos.model.db.PerhitunganSewaAlat;
import com.pelindo.ebtos.model.db.PerhitunganStuffing;
import com.pelindo.ebtos.model.db.PerhitunganUpahBuruh;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
@ManagedBean(name = "stuffingBean")
@ViewScoped
public class StuffingBean implements Serializable {

    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacade;
    @EJB
    private StuffingServiceFacadeRemote stuffingServiceFacade;
    @EJB
    private InvoiceFacadeRemote invoiceFacade;
    @EJB
    private RegistrationFacadeRemote registrationFacade;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacade;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacadeRemote;
    @EJB
    private StuffingCalculationOperationRemote stuffingCalculationOperationRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private ServiceContLoadFacadeRemote serviceContLoadFacade;

    private LazyDataModel<PlanningVessel> availableVessels;
    private List<Object[]> cancelLoadingServices;
    private List<StuffingService> stuffingServices;
    private List<Object[]> registrations;
    private List<Object[]> masterCommodities;
    private PlanningVessel planningVessel;
    private Registration registration;
    private CancelLoadingService cancelLoadingService;
    private StuffingService stuffingService;
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal ppnPrint = BigDecimal.ZERO;
    private BigDecimal materaiPrint = BigDecimal.ZERO;
    private String userId = null;
    private Boolean disPrint = true;
    private Boolean disKredit = false;
    private Boolean disDetail = true;
    private BatalNota batalNota;
    private Boolean disCancelInv = true;
    private Boolean isSimpatReady;
    private BigDecimal upahBuruh;
    private MasterCurrency masterCurrency;
    private PaketStrippingStuffing paketStuffing;
    private List<Object[]> serviceContLoads;
    private String portOfDeliveryName;

    /** Creates a new instance of StuffingBean */
    public StuffingBean() {
    }

    @PostConstruct
    private void construct(){
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        registration.setInvoice(new Invoice());
        stuffingService = new StuffingService();
        cancelLoadingServices = new ArrayList<Object[]>();
        cancelLoadingService = null;

        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        userId = loginSessionBean.getName();

        populateAvailablePlanningVessels();
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
        masterCommodities = masterCommodityFacadeRemote.findMasterCommoditys();
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
        registrations = registrationFacade.findRegistrationStuffing();
    }

    public void handleSelectRegistration(ActionEvent event) {
        String noReg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(noReg);
        stuffingServices = stuffingServiceFacade.findByNoReg(registration.getNoReg());
        paketStuffing = new PaketStrippingStuffing();
        viewData();
    }

    public void handleSelectCommodity(ActionEvent event) {
        String commodityCode = (String) event.getComponent().getAttributes().get("commodityCode");

        MasterCommodity masterCommodity = masterCommodityFacadeRemote.find(commodityCode);
        stuffingService.setMasterCommodity(masterCommodity);
    }

    public void viewData() {
        disCancelInv = Boolean.TRUE;
        disDetail = Boolean.FALSE;
        disPrint = Boolean.TRUE;
        total = BigDecimal.ZERO;
        upahBuruh = BigDecimal.ZERO;

        for (StuffingService o : stuffingServices) {
            total = total.add(o.getPerhitunganStuffing().getTotalCharge());
            //upahBuruh = upahBuruh.add(o.getPerhitunganStuffing().getPerhitunganUpahBuruh().getCharge());
        }

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

    public void handleSelectStuffAbleContainer(ActionEvent event) {
        try {
            //String jobSlip = (String) event.getComponent().getAttributes().get("jobSlip");
            //cancelLoadingService = cancelLoadingServiceFacadeRemote.find(jobSlip);
            Integer id = (Integer) event.getComponent().getAttributes().get("idCont");
            ServiceContLoad scd = serviceContLoadFacade.find(id);
            planningVessel = registration.getPlanningVessel();

            stuffingService = new StuffingService();
            stuffingService.setBlNo(scd.getBlNo());
            stuffingService.setContNo(scd.getContNo());
            stuffingService.setMlo(scd.getMlo());
            stuffingService.setContSize(scd.getContSize());
            stuffingService.setContGross(scd.getContGross());
            stuffingService.setContStatus(scd.getContStatus());
            stuffingService.setOverSize("FALSE");
            stuffingService.setDg(scd.getDangerous());
            stuffingService.setDgLabel(scd.getDgLabel());
            stuffingService.setMasterContainerType(scd.getMasterContainerType());
            stuffingService.setRegistration(registration);
            stuffingService.setPerhitunganStuffing(new PerhitunganStuffing());
            stuffingService.setMasterCommodity(new MasterCommodity());
            //stuffingService.getPerhitunganStuffing().setPerhitunganUpahBuruh(new PerhitunganUpahBuruh());
            //stuffingService.getPerhitunganStuffing().setPerhitunganSewaAlat(new PerhitunganSewaAlat());
            //stuffingService.getPerhitunganStuffing().setPerhitunganPassGate(new PerhitunganPassGate());
            //stuffingService.getPerhitunganStuffing().setPerhitunganLiftService(new PerhitunganLiftService());

            String stuffingActivityCode = masterActivityFacade.translateStrippingStuffingActivityCode2(
                    stuffingService.getContSize(),stuffingService.getContStatus(),
                    stuffingService.getOverSize().equals("TRUE") ? true : false);
            String liftActivityCode = masterActivityFacade.translateLoLoActivityCode("MTY", 
                    stuffingService.getContSize(), 
                    false, 
                    stuffingService.getDg().equals("TRUE"),
                    stuffingService.getDgLabel().equals("TRUE"),
                    null,"CC");
            String passGateActivityCode = masterActivityFacade.translatePassGateActivityCode("MTY", stuffingService.getContSize());

            BigDecimal strippingCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), stuffingActivityCode);
            BigDecimal liftCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), liftActivityCode);
            BigDecimal passGateCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), passGateActivityCode);

            stuffingService.getPerhitunganStuffing().setCharge(strippingCharge);
            
           // stuffingService.getPerhitunganStuffing().getPerhitunganPassGate().setCharge(BigDecimal.ZERO);
            //stuffingService.getPerhitunganStuffing().getPerhitunganPassGate().setJumlah(1);
           // stuffingService.getPerhitunganStuffing().getPerhitunganPassGate().setTotalCharge(stuffingService.getPerhitunganStuffing()
           //         .getPerhitunganPassGate().getCharge().multiply(BigDecimal.valueOf(stuffingService.getPerhitunganStuffing().getPerhitunganPassGate().getJumlah())));

           //stuffingService.getPerhitunganStuffing().getPerhitunganLiftService().setCharge(BigDecimal.ZERO);
            

            paketStuffing.setStrippingActivityCode(stuffingActivityCode);
            paketStuffing.setLiftActivityCode(liftActivityCode);
            paketStuffing.setPassGateActivityCode(passGateActivityCode);
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when call handleSelectStuffAbleContainer", re);
        }
    }

    public void handleOpenDoorChanged(){
        String stuffingActivityCode = masterActivityFacade.translateStrippingStuffingActivityCode(
                stuffingService.getContSize(), 
                stuffingService.getOverSize().equals("TRUE") ? true : false);
        String liftActivityCode = masterActivityFacade.translateLoLoActivityCode(
                "FCL", 
                stuffingService.getContSize(), 
                stuffingService.getOverSize().equals("TRUE") ? true : false, 
                stuffingService.getDg().equals("TRUE"),
                stuffingService.getDgLabel().equals("TRUE"),
                null,"CC");

        BigDecimal strippingCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), stuffingActivityCode);
        BigDecimal liftCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), liftActivityCode);

        stuffingService.getPerhitunganStuffing().setCharge(strippingCharge);
        paketStuffing.setStrippingActivityCode(stuffingActivityCode);

        stuffingService.getPerhitunganStuffing().getPerhitunganLiftService().setCharge(liftCharge);
        paketStuffing.setLiftActivityCode(liftActivityCode);
    }

    public void calculatePaketStuffing() {
        BigDecimal upahBuruhCharge = BigDecimal.ZERO;
        stuffingService.getPerhitunganStuffing().getPerhitunganSewaAlat().setCharge(BigDecimal.ZERO);

       /** if (paketStuffing.isIsUseEquipment()) {
            String mekanikActivityCode = masterActivityFacade.translateStrippingStuffingMekanikActivityCode(paketStuffing.getMekanik().toString());
            BigDecimal mekanikCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), mekanikActivityCode);
            mekanikCharge = mekanikCharge.multiply(BigDecimal.valueOf(paketStuffing.getMekanikTonage()));
            upahBuruhCharge = upahBuruhCharge.add(mekanikCharge);

            String sewaForkLiftActivityCode = masterActivityFacade.translateSewaForkLiftActivityCode(paketStuffing.getEquipment().toString(), stuffingService.getContSize());
            BigDecimal sewaForkLiftCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), sewaForkLiftActivityCode);
            stuffingService.getPerhitunganStuffing().getPerhitunganSewaAlat().setCharge(sewaForkLiftCharge);
            paketStuffing.setSewaAlatActivityCode(sewaForkLiftActivityCode);
        }

        String fullHandlingActivityCode = masterActivityFacade.translateStrippingStuffingFullHandlingActivityCode(paketStuffing.getFullHandling().toString());
        BigDecimal fullHandlingCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), fullHandlingActivityCode);
        fullHandlingCharge = fullHandlingCharge.multiply(BigDecimal.valueOf(paketStuffing.getFullHandlingTonage()));

        upahBuruhCharge = upahBuruhCharge.add(fullHandlingCharge);
        stuffingService.getPerhitunganStuffing().getPerhitunganUpahBuruh().setCharge(upahBuruhCharge);

        String upahBuruhStrippingActivityCode = masterActivityFacade.translateUpahBuruhStrippingStuffingActivityCode();
        paketStuffing.setUpahBuruhActivityCode(upahBuruhStrippingActivityCode);
        * **/
    }

    public void handleAdd(ActionEvent event) {
        stuffingService = new StuffingService();
        stuffingService.setPerhitunganStuffing(new PerhitunganStuffing());
        /*
        stuffingService.getPerhitunganStuffing().setPerhitunganUpahBuruh(new PerhitunganUpahBuruh());
        stuffingService.getPerhitunganStuffing().setPerhitunganSewaAlat(new PerhitunganSewaAlat());
        stuffingService.getPerhitunganStuffing().setPerhitunganPassGate(new PerhitunganPassGate());
        stuffingService.getPerhitunganStuffing().setPerhitunganLiftService(new PerhitunganLiftService());
        */

        cancelLoadingService = null;

        planningVessel = new PlanningVessel();
    }

    public void handleShowStuffAbleContainers(ActionEvent event) {
        //cancelLoadingServices = cancelLoadingServiceFacadeRemote.findStuffAbleContainers(registration.getPlanningVessel().getNoPpkb());
       // serviceContLoads = serviceContLoadFacade.findServiceContDischargesStrippingService(registration.getPlanningVessel().getNoPpkb());
          serviceContLoads = serviceContLoadFacade.findServiceContLoadsStuffingService(registration.getPlanningVessel().getNoPpkb());
    }

    public void handleConfirm(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean isValid = false;

        try {
            if (stuffingService.getJobSlip() == null) {
                stuffingService.setJobSlip(iDGeneratorFacade.generateJobSlipID("07"));
            }

            stuffingService.setContStatus("FCL");
            stuffingService.setContGross(((int) (paketStuffing.getFullHandlingTonage() + paketStuffing.getMekanikTonage()) * 1000) + (stuffingService.getContSize() == 20 ? 2000 : 3500));

            MasterActivity strippingMasterActivity = masterActivityFacade.find(paketStuffing.getStrippingActivityCode());
            MasterActivity upahBuruhStrippingMasterActivity = masterActivityFacade.find(paketStuffing.getUpahBuruhActivityCode());
            MasterActivity sewaAlatMasterActivity = masterActivityFacade.find(paketStuffing.getSewaAlatActivityCode());
            MasterActivity passGateMasterActivity = masterActivityFacade.find(paketStuffing.getPassGateActivityCode());
            MasterActivity liftMasterActivity = masterActivityFacade.find(paketStuffing.getLiftActivityCode());

            /*
            stuffingService.getPerhitunganStuffing().getPerhitunganUpahBuruh().setMasterActivity(upahBuruhStrippingMasterActivity);
            stuffingService.getPerhitunganStuffing().getPerhitunganUpahBuruh().setRegistration(registration);
            stuffingService.getPerhitunganStuffing().getPerhitunganUpahBuruh().setJobslip(stuffingService.getJobSlip());
            stuffingService.getPerhitunganStuffing().getPerhitunganUpahBuruh().setMasterCurrency(masterCurrency);

            stuffingService.getPerhitunganStuffing().getPerhitunganSewaAlat().setMasterActivity(sewaAlatMasterActivity);
            stuffingService.getPerhitunganStuffing().getPerhitunganSewaAlat().setJobslip(stuffingService.getJobSlip());
            stuffingService.getPerhitunganStuffing().getPerhitunganSewaAlat().setRegistration(registration);

            stuffingService.getPerhitunganStuffing().getPerhitunganPassGate().setMasterActivity(passGateMasterActivity);
            stuffingService.getPerhitunganStuffing().getPerhitunganPassGate().setRegistration(registration);
            stuffingService.getPerhitunganStuffing().getPerhitunganPassGate().setJobSlip(stuffingService.getJobSlip());
            stuffingService.getPerhitunganStuffing().getPerhitunganPassGate().setPlanningVessel(registration.getPlanningVessel());

            stuffingService.getPerhitunganStuffing().getPerhitunganLiftService().setMasterActivity(liftMasterActivity);
            stuffingService.getPerhitunganStuffing().getPerhitunganLiftService().setRegistration(registration);
            stuffingService.getPerhitunganStuffing().getPerhitunganLiftService().setBlNo(stuffingService.getBlNo());
            stuffingService.getPerhitunganStuffing().getPerhitunganLiftService().setContNo(stuffingService.getContNo());
            stuffingService.getPerhitunganStuffing().getPerhitunganLiftService().setCurrCode(masterCurrency.getCurrCode());
            stuffingService.getPerhitunganStuffing().getPerhitunganLiftService().setMlo(stuffingService.getMlo());
            stuffingService.getPerhitunganStuffing().getPerhitunganLiftService().setNoPpkb(registration.getPlanningVessel().getNoPpkb());
            */

            stuffingService.getPerhitunganStuffing().setMasterActivity(strippingMasterActivity);
            stuffingService.getPerhitunganStuffing().setTotalCharge(stuffingService.getPerhitunganStuffing().getCharge());
            stuffingService = stuffingCalculationOperationRemote.saveStuffingContainers(stuffingService, planningVessel.getNoPpkb());
            stuffingServices.add(stuffingService);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            isValid = Boolean.TRUE;
            viewData();
            handleAdd(event);
        } catch (GrossCapacityExceedTheLimitsException ex) {
            FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.exceed_limit", facesContext), "Container Gross", "(max " + ex.getMaxGross() + " ton)"), null);
        } catch (TeusCapacityExceedTheLimitsException ex) {
            FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.exceed_limit", facesContext), "Container Capacity", "(max " + ex.getMaxTeus() + " teus)"), null);
        } catch (HasJobSlipAndNotEnteringGateYetException ex) {
            FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.receiving.has_job_but_not_entering_gate_yet", facesContext), stuffingService.getContNo()), null);
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
        
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", isValid);
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
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", isValid);
    }

    public void handleDelete(ActionEvent event) {
        stuffingServices.remove(stuffingService);
        stuffingCalculationOperationRemote.removeStuffingContainer(stuffingService);
        viewData();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
    }

    public void handleAddCancelInvoice(ActionEvent event) {
        batalNota = new BatalNota();
    }

    public void handleSubmitBatalNota(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean isValid = Boolean.FALSE;
        try {
            final String noInvoice = registration.getInvoice().getNoInvoice();
            stuffingCalculationOperationRemote.cancelInvoice(registration, batalNota);
            disCancelInv = Boolean.TRUE;
            stuffingServices.clear();

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
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when ", e);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }
        context.addCallbackParam("isValid", isValid);
    }

    public void handleDownloadTransactionRecap(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean doPrint = false;

        try {
            String key = stuffingCalculationOperationRemote.preparePerhitunganReport(registration, masterCurrency, total.multiply(ppnPrint), materaiPrint, stuffingServices);

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
            registration.setInvoice(invoiceFacade.publishInvoice(registration.getInvoice()));
        }

        viewData();
        String key = stuffingCalculationOperationRemote.prepareInvoiceReport(registration, stuffingServices);

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
            String key = stuffingCalculationOperationRemote.preparePerhitunganInvoiceReport(registration, stuffingServices);

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
     * @return the stuffingServices
     */
    public List<StuffingService> getStuffingServices() {
        return stuffingServices;
    }

    /**
     * @param stuffingServices the stuffingServices to set
     */
    public void setStuffingServices(List<StuffingService> stuffingServices) {
        this.stuffingServices = stuffingServices;
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

    public List<Object[]> getCancelLoadingServices() {
        return cancelLoadingServices;
    }

    /**
     * @return the stuffingService
     */
    public StuffingService getStuffingService() {
        return stuffingService;
    }

    /**
     * @param stuffingService the stuffingService to set
     */
    public void setStuffingService(StuffingService stuffingService) {
        this.stuffingService = stuffingService;
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

    public Boolean getIsSimpatReady() {
        return isSimpatReady;
    }

    public BigDecimal getUpahBuruh() {
        return upahBuruh;
    }

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    public PaketStrippingStuffing getPaketStuffing() {
        return paketStuffing;
    }

    public void setPaketStuffing(PaketStrippingStuffing paketStuffing) {
        this.paketStuffing = paketStuffing;
    }

    public List<Object[]> getMasterCommodities() {
        return masterCommodities;
    }

    public CancelLoadingService getCancelLoadingService() {
        return cancelLoadingService;
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
        /**
     * @return the serviceContLoads
     */
    public List<Object[]> getServiceContLoads() {
        return serviceContLoads;
    }

    /**
     * @param serviceContLoads the serviceContLoads to set
     */
    public void setServiceContLoads(List<Object[]> serviceContLoads) {
        this.serviceContLoads = serviceContLoads;
    }
    
    public String getPortOfDeliveryName() {
        return portOfDeliveryName;
    }

    public void setPortOfDeliveryName(String portOfDeliveryName) {
        this.portOfDeliveryName = portOfDeliveryName;
    }
}

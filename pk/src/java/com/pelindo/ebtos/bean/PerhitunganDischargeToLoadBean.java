/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.url.UrlHelper;
import com.qtasnim.util.DateCalculator;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.BankingSyncFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.DischargeToLoadOperationRemote;
import com.pelindo.ebtos.ejb.facade.remote.DischargeToLoadServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReeferFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.exception.GrossCapacityExceedTheLimitsException;
import com.pelindo.ebtos.exception.HasJobSlipAndNotEnteringGateYetException;
import com.pelindo.ebtos.exception.NotAllocatedReceivingException;
import com.pelindo.ebtos.exception.ReceivingAllocationIsNotEnoughException;
import com.pelindo.ebtos.exception.RegisteredWithSamePpkbContainerException;
import com.pelindo.ebtos.exception.TeusCapacityExceedTheLimitsException;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.DischargeToLoadService;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PerhitunganDischargeToLoad;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukan;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "perhitunganDischargeToLoadBean")
@ViewScoped
public class PerhitunganDischargeToLoadBean implements Serializable {
    private static final Logger logger = Logger.getLogger(PerhitunganDischargeToLoadBean.class.getName());

    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacade;
    @EJB
    private RegistrationFacadeRemote registrationFacade;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private DischargeToLoadOperationRemote dischargeToLoadCalculationOperationRemote;
    @EJB
    private InvoiceFacadeRemote invoiceFacadeRemote;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacadeRemote;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacadeRemote;
    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacadeRemote;
    @EJB
    private DischargeToLoadServiceFacadeRemote dischargeToLoadServiceFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private MasterPortFacadeRemote masterPortFacadeRemote;
    @EJB
    private ServiceReeferFacadeRemote serviceReeferFacadeRemote;
    
    private List<Object[]> registrations;
    private List<Object[]> serviceContDischarges;
    private List<DischargeToLoadService> dischargeToLoadServices;
    private LazyDataModel<PlanningVessel> availableVessels;
    private Registration registration;
    private DischargeToLoadService dischargeToLoadService;
    private MasterCurrency masterCurrency;
    private PlanningVessel planningVessel;
    private BatalNota batalNota;
    private BigDecimal total;
    private BigDecimal ppnPrint;
    private BigDecimal materaiPrint;
    private BigDecimal upahBuruh;
    private String userId;
    private Boolean disPrint;
    private Boolean disKredit;
    private Boolean disDetail;
    private Boolean disCancelInv;
    private Boolean isSimpatReady;
    private String dischargePortName;
    private String portOfDeliveryName;
    
    /** Creates a new instance of DischargeToLoadBean */
    public PerhitunganDischargeToLoadBean() {}

    @PostConstruct
    private void construct() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());

        total = BigDecimal.ZERO;
        ppnPrint = BigDecimal.ZERO;
        materaiPrint = BigDecimal.ZERO;
        disPrint = true;
        disKredit = false;
        disDetail = true;
        disCancelInv = true;

        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        userId = loginSessionBean.getName();
        batalNota = new BatalNota();
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
        populateAvailablePlanningVessels();
    }

    private void populateAvailablePlanningVessels() {
        availableVessels = new LazyDataModel<PlanningVessel>(){
            @Override
            public List<PlanningVessel> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String,String> filters) {
                int count = planningVesselFacadeRemote.findByStatussesAndLoadOnly_Count(filters, "Approved", "ReadyService", "Servicing");
                availableVessels.setRowCount(count);
                List<PlanningVessel> vessels = planningVesselFacadeRemote.findByStatussesAndLoadOnly(first, pageSize, sortField, sortOrder, filters, "Approved", "ReadyService", "Servicing");
                return vessels;
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

    public void handleSelectRegistration(ActionEvent event) {
        String noReg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(noReg);
        dischargeToLoadServices = dischargeToLoadServiceFacadeRemote.findByNoReg(registration.getNoReg());
        viewData();
    }

    public void handleShowRegistrations(ActionEvent event) {
        registrations = registrationFacade.findRegistrationDischargeToLoad();
    }

    public void findConvertableToLoadContainers(ActionEvent event) {
        serviceContDischarges = serviceContDischargeFacade.findConvertableToLoadContainers(registration.getPlanningVessel().getNoPpkb());
    }

    public void viewData() {
        disCancelInv = Boolean.FALSE;
        total = BigDecimal.ZERO;
        upahBuruh = BigDecimal.ZERO;
        disPrint = true;

        for (DischargeToLoadService o : dischargeToLoadServices) {
            total = total.add(o.getTotalCharge());
            disCancelInv = disCancelInv || o.getServiceReceiving().getIsLifton().equals("TRUE");
        }

        //ambil nilai ppn
        MasterSettingApp settingApp = masterSettingAppFacade.find("ppn");
        ppnPrint = settingApp.getValueFloat();

        if (registration.getPlanningVessel().getTipePelayaran().equals("d")) {
            masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        } else {
            masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        }

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
            disKredit = registration.getInvoice().getPaymentType().equalsIgnoreCase("KREDIT");
        } else {
            registration.setInvoice(new Invoice());
            registration.getInvoice().setPaymentType("CASH");
            disPrint = true;
            disKredit = false;
        }
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

    public void handleSubmit(ActionEvent event) {
        Boolean isValid = false;

        try {
            MasterSettingApp settingApp = masterSettingAppFacade.find("ppn");
            ppnPrint = settingApp.getValueFloat();

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
            isValid = true;
            viewData();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "exception caught when call handleSubmit", re);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Warning", "application.save.failed");
        }
        
        RequestContext.getCurrentInstance().addCallbackParam("isValid", isValid);
    }

    public void handleDelete(ActionEvent event) {
        try {
            dischargeToLoadCalculationOperationRemote.removeDischargeToLoadContainer(dischargeToLoadService);
            dischargeToLoadServices.remove(dischargeToLoadService);
            viewData();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "exception caught when call handleDelete", re);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Warning", "application.delete.failed");
        }
    }

    public void handleAdd(ActionEvent event) {
        dischargeToLoadService = new DischargeToLoadService();
        planningVessel = null;
        dischargePortName = null;
        portOfDeliveryName = null;
    }

    public void handleSelectContPick(ActionEvent event) {
        Integer id = (Integer) event.getComponent().getAttributes().get("idCont");
        ServiceContDischarge serviceContDischarge = serviceContDischargeFacade.find(id);

        dischargeToLoadService.setRegistration(registration);
        dischargeToLoadService.setServiceContDischarge(serviceContDischarge);
    }

    public void handleConfirm(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean isValid = false;
        
        try {
            int masaFree = masterSettingAppFacade.getMasa1FreeRange();
            int masa1Range = masterSettingAppFacade.getMasa1ContainerRange();

            Date tanggalValidDate = new Date();

            int min = DateCalculator.countRangeInDays(tanggalValidDate, dischargeToLoadService.getServiceContDischarge().getStartPlacementDate()) + 1;
            int m1 = min - (masaFree - 1);
            short masa1 = 0;
            short masa2 = 0;

            if (min < (masa1Range + 1)) {
                if (min <= masaFree) {
                    masa1 = (short) 1;
                } else {
                    masa1 = (short) m1;
                }

                masa2 = (short) 0;
            } else {
                masa2 = (short) (min - masa1Range);
                masa1 = (short) (m1 - masa2);
            }

            if (dischargeToLoadService.getJobSlip() == null) {
                dischargeToLoadService.setJobSlip(iDGeneratorFacadeRemote.generateJobSlipID("25"));
            }
            
            MasterPort dischargePort = masterPortFacadeRemote.findMasterPortByName(dischargePortName);
            MasterPort portOfDelivery = masterPortFacadeRemote.findMasterPortByName(portOfDeliveryName);

            Integer idReefer = serviceReeferFacadeRemote.findValidasiPenumpukan(dischargeToLoadService.getServiceContDischarge().getServiceVessel().getNoPpkb(), dischargeToLoadService.getServiceContDischarge().getContNo(), "DISCHARGE");
            boolean bIsOverSize = false;
            if ((!dischargeToLoadService.getServiceContDischarge().getContStatus().equals("MTY") 
                    && dischargeToLoadService.getServiceContDischarge().getOverSize().equals("TRUE")) 
                    || dischargeToLoadService.getServiceContDischarge().getTwentyOneFeet().equals("TRUE")) {
                bIsOverSize = true;
            }
            String penumpukanActivityCode = masterActivityFacadeRemote.translatePenumpukanActivityCode(
                    dischargeToLoadService.getServiceContDischarge().getContStatus(), 
                    dischargeToLoadService.getServiceContDischarge().getMasterContainerType().getIsoCode(), 
                    bIsOverSize, 
                    dischargeToLoadService.getServiceContDischarge().getContSize(), idReefer > 0);

            String dischargeToLoadActivityCode = "";
            dischargeToLoadActivityCode = masterActivityFacadeRemote.translateDischargeToLoadActivityCode(dischargeToLoadService.getServiceContDischarge().getContSize(), dischargeToLoadService.getServiceContDischarge().getContStatus());

            MasterActivity dischargeToLoadMasterActivity = masterActivityFacadeRemote.find(dischargeToLoadActivityCode);
            MasterActivity penumpukanMasterActivity = masterActivityFacadeRemote.find(penumpukanActivityCode);

            BigDecimal dischargeToLoadCharge = masterTarifContFacadeRemote.findChargeAfterDiscount(masterCurrency.getCurrCode(), dischargeToLoadActivityCode, registration.getMasterCustomer().getCustCode());
            BigDecimal penumpukanCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), penumpukanActivityCode);

            if (dischargeToLoadService.getServiceContDischarge().getDangerous().equals("TRUE")
                    && dischargeToLoadService.getServiceContDischarge().getDgLabel().equals("TRUE")) {
                penumpukanCharge = penumpukanCharge.multiply(BigDecimal.valueOf(2));
            } else if (dischargeToLoadService.getServiceContDischarge().getDangerous().equals("TRUE")
                    && dischargeToLoadService.getServiceContDischarge().getDgLabel().equals("FALSE")) {
                penumpukanCharge = penumpukanCharge.multiply(BigDecimal.valueOf(3));
            }

            dischargeToLoadService.setPerhitunganPenumpukan(new PerhitunganPenumpukan());
            dischargeToLoadService.getPerhitunganPenumpukan().setRegistration(registration);
            dischargeToLoadService.getPerhitunganPenumpukan().setNoPpkb(registration.getPlanningVessel().getNoPpkb());
            dischargeToLoadService.getPerhitunganPenumpukan().setContNo(dischargeToLoadService.getServiceContDischarge().getContNo());
            dischargeToLoadService.getPerhitunganPenumpukan().setMlo(dischargeToLoadService.getServiceContDischarge().getMlo());
            dischargeToLoadService.getPerhitunganPenumpukan().setBlNo(dischargeToLoadService.getServiceContDischarge().getBlNo());
            dischargeToLoadService.getPerhitunganPenumpukan().setMasterActivity(penumpukanMasterActivity);
            dischargeToLoadService.getPerhitunganPenumpukan().setCurrCode(masterCurrency.getCurrCode());
            dischargeToLoadService.getPerhitunganPenumpukan().setMasa1((short) (masa1 - 1));
            dischargeToLoadService.getPerhitunganPenumpukan().setMasa2(masa2);
            dischargeToLoadService.getPerhitunganPenumpukan().setCharge(penumpukanCharge);
            dischargeToLoadService.getPerhitunganPenumpukan().setChargeM1(dischargeToLoadService.getPerhitunganPenumpukan().getCharge().multiply(BigDecimal.valueOf(dischargeToLoadService.getPerhitunganPenumpukan().getMasa1())));
            dischargeToLoadService.getPerhitunganPenumpukan().setChargeM2(dischargeToLoadService.getPerhitunganPenumpukan().getCharge().multiply(BigDecimal.valueOf(dischargeToLoadService.getPerhitunganPenumpukan().getMasa2())).multiply(BigDecimal.valueOf(2)));
            dischargeToLoadService.getPerhitunganPenumpukan().setJasaDermaga(BigDecimal.ZERO);
            dischargeToLoadService.getPerhitunganPenumpukan().setTotalCharge(dischargeToLoadService.getPerhitunganPenumpukan().getChargeM1().add(dischargeToLoadService.getPerhitunganPenumpukan().getChargeM2()));

            dischargeToLoadService.setPerhitunganDischargeToLoad(new PerhitunganDischargeToLoad());
            dischargeToLoadService.getPerhitunganDischargeToLoad().setMasterActivity(dischargeToLoadMasterActivity);
            dischargeToLoadService.getPerhitunganDischargeToLoad().setCharge(dischargeToLoadCharge);
            dischargeToLoadService.getPerhitunganDischargeToLoad().setJumlah(1);
            dischargeToLoadService.getPerhitunganDischargeToLoad().setTotalCharge(dischargeToLoadService.getPerhitunganDischargeToLoad().getCharge().multiply(BigDecimal.valueOf(dischargeToLoadService.getPerhitunganDischargeToLoad().getJumlah())));

            dischargeToLoadService.setTotalCharge(dischargeToLoadService.getPerhitunganDischargeToLoad().getTotalCharge().add(dischargeToLoadService.getPerhitunganPenumpukan().getTotalCharge()));

            dischargeToLoadService = dischargeToLoadCalculationOperationRemote.saveDischargeToLoadContainers(dischargeToLoadService, planningVessel, dischargePort, portOfDelivery);
            dischargeToLoadServices.add(dischargeToLoadService);

            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            isValid = true;
            viewData();
        } catch (GrossCapacityExceedTheLimitsException ex) {
            FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.exceed_limit", facesContext), "Container Gross", "(max " + ex.getMaxGross() + " ton)"), null);
        } catch (TeusCapacityExceedTheLimitsException ex) {
            FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.exceed_limit", facesContext), "Container Capacity", "(max " + ex.getMaxTeus() + " teus)"), null);
        } catch (HasJobSlipAndNotEnteringGateYetException ex) {
            FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.receiving.has_job_but_not_entering_gate_yet", facesContext), dischargeToLoadService.getServiceContDischarge().getContNo()), null);
        } catch (NotAllocatedReceivingException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.record_beda");
        } catch (ReceivingAllocationIsNotEnoughException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.alokasi_booking");
        } catch (RegisteredWithSamePpkbContainerException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.data_exist");
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "exception caught when call handleConfirm", re);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
        }

        requestContext.addCallbackParam("isValid", isValid);
    }

    public void handleAddCancelInvoice(ActionEvent event){
    }

    public void handleCancelInvoice(ActionEvent event) {
        boolean isValid = false;
        
        try {
            final String noInvoice = registration.getInvoice().getNoInvoice();
            System.out.println("noInvoice : " + noInvoice);
            System.out.println("getNoBeritaAcara : " + batalNota.getNoBeritaAcara());
            System.out.println("getAlasanPembatalan : " + batalNota.getAlasanPembatalan());
            System.out.println("getTglPembatalan : " + batalNota.getTglPembatalan());

            dischargeToLoadCalculationOperationRemote.cancelInvoice(registration, batalNota);
            dischargeToLoadServices.clear();
            
            if (masterSettingAppFacade.isPaymentBankingEnabled())
                try {
                    bankingSyncFacadeRemote.cancelInvoice(registration.getInvoice().getNoInvoice());
                } catch (RuntimeException e) {
                    System.err.println(FacesHelper.getLocaleMessage("application.integration.banking.failed", FacesContext.getCurrentInstance()));
                    FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.integration.banking.failed");
                }
            
            isValid = true;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.cancel.succeeded");
        } catch (RuntimeException e) {
            logger.log(Level.SEVERE, "exception caught when call handleCancelInvoice", e);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }
        
        RequestContext.getCurrentInstance().addCallbackParam("isValid", isValid);
        viewData();
    }

    public void handleDownloadTransactionRecap(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean doPrint = false;

        try {
            String key = dischargeToLoadCalculationOperationRemote.preparePerhitunganReport(registration, masterCurrency, total.multiply(ppnPrint), materaiPrint, dischargeToLoadServices);

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
        Boolean doPrint = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (registration.getInvoice().getValidasiPrint().equals("FALSE")) {
            registration.setInvoice(invoiceFacadeRemote.publishInvoice(registration.getInvoice()));
        }

        viewData();

        String key = dischargeToLoadCalculationOperationRemote.prepareInvoiceReport(registration, dischargeToLoadServices);

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
            String key = dischargeToLoadCalculationOperationRemote.preparePerhitunganInvoiceReport(registration, dischargeToLoadServices);

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

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    /**
     * @return the batalNota
     */
    public BatalNota getBatalNota() {
        return batalNota;
    }

    /**
     * @param batalNota the batalNota to set
     */
    public void setBatalNota(BatalNota batalNota) {
        this.batalNota = batalNota;
    }

    /**
     * @return the disCancelInv
     */
    public Boolean getDisCancelInv() {
        return disCancelInv;
    }

    /**
     * @param disCancelInv the disCancelInv to set
     */
    public void setDisCancelInv(Boolean disCancelInv) {
        this.disCancelInv = disCancelInv;
    }

    public DischargeToLoadService getDischargeToLoadService() {
        return dischargeToLoadService;
    }

    public void setDischargeToLoadService(DischargeToLoadService dischargeToLoadService) {
        this.dischargeToLoadService = dischargeToLoadService;
    }

    public List<DischargeToLoadService> getDischargeToLoadServices() {
        return dischargeToLoadServices;
    }

    public Boolean getIsSimpatReady() {
        return isSimpatReady;
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

    public String getDischargePortName() {
        return dischargePortName;
    }

    public void setDischargePortName(String dischargePortName) {
        this.dischargePortName = dischargePortName;
    }

    public String getPortOfDeliveryName() {
        return portOfDeliveryName;
    }

    public void setPortOfDeliveryName(String portOfDeliveryName) {
        this.portOfDeliveryName = portOfDeliveryName;
    }
}

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
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganBehandleFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateDeliveryFacadeRemote;
import com.pelindo.ebtos.ejb.remote.BehandleCalculationOperationRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.model.PaketBehandle;
import com.pelindo.ebtos.model.db.*;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.math.BigDecimal;
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
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "behandleBean")
@ViewScoped
public class BehandleBean implements Serializable {
    private static final Logger logger = Logger.getLogger(BehandleBean.class.getName());

    @EJB
    private ServiceGateDeliveryFacadeRemote serviceGateDeliveryFacade;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacade;
    @EJB
    private PerhitunganBehandleFacadeRemote perhitunganBehandleFacade;
    @EJB
    private RegistrationFacadeRemote registrationFacade;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private BehandleCalculationOperationRemote behandleCalculationOperationRemote;
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
    
    private List<Object[]> registrations;
    private List<PerhitunganBehandle> perhitunganBehandles;
    private List<Object[]> serviceContDischarges;
    private Registration registration;
    private PerhitunganBehandle perhitunganBehandle;
    private PaketBehandle paketBehandle;
    private MasterCurrency masterCurrency;
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
    
    /** Creates a new instance of BehandleBean */
    public BehandleBean() {}

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
        paketBehandle = new PaketBehandle();
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
    }

    public void handleSelectRegistration(ActionEvent event) {
        String noReg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(noReg);
        perhitunganBehandles = perhitunganBehandleFacade.findByNoReg(registration.getNoReg());
        viewData();
    }

    public void handleShowRegistrations(ActionEvent event) {
        registrations = registrationFacade.findRegistrationBehandle();
    }

    public void handleShowContainersThatAbleToBehandled(ActionEvent event) {
        serviceContDischarges = serviceContDischargeFacade.findServiceContDischargesBehandleService(registration.getPlanningVessel().getNoPpkb());
    }

    public void viewData() {
        disCancelInv = Boolean.FALSE;
        boolean bdiCancelInv  = false;
        total = BigDecimal.ZERO;
        upahBuruh = BigDecimal.ZERO;
        disPrint = true;

        for (PerhitunganBehandle o : perhitunganBehandles) {
            total = total.add(o.getTotalCharge());
            
            if (o.getPerhitunganUpahBuruh() != null) {
                upahBuruh = upahBuruh.add(o.getPerhitunganUpahBuruh().getCharge());
            }
            
            if(o.getBehandleService().getServiceContDischarge().getIsDelivery().equals("TRUE")) {
                bdiCancelInv = true;
            }
            disCancelInv = disCancelInv || bdiCancelInv;
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
            if(registration.getInvoice().getValidasiPrint().equals("TRUE")) {
                disPrint = true;
            }
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
        Boolean loggedIn = false;

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
            loggedIn = true;
            viewData();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "exception caught when call handleSubmit", re);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Warning", "application.save.failed");
        }
        
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleDelete(ActionEvent event) {
        try {
            perhitunganBehandles.remove(perhitunganBehandle);
            perhitunganBehandleFacade.remove(perhitunganBehandle);
            viewData();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "exception caught when call handleDelete", re);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Warning", "application.delete.failed");
        }
    }

    public void calculatePaketBehandle() {
        try {
            BigDecimal upahBuruhCharge = BigDecimal.ZERO;
            perhitunganBehandle.getPerhitunganSewaAlat().setCharge(BigDecimal.ZERO);

            if (paketBehandle.isIncludeStrippingStuffing()) {
                if (paketBehandle.isIsUseEquipment()) {
                    String mekanikActivityCode = masterActivityFacadeRemote.translateStrippingStuffingMekanikActivityCode(paketBehandle.getMekanik().toString());
                    BigDecimal mekanikCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), mekanikActivityCode);
                    mekanikCharge = mekanikCharge.multiply(BigDecimal.valueOf(paketBehandle.getMekanikTonage())).multiply(BigDecimal.valueOf(2));
                    upahBuruhCharge = upahBuruhCharge.add(mekanikCharge);

                    String sewaForkLiftActivityCode = masterActivityFacadeRemote.translateSewaForkLiftActivityCode(paketBehandle.getEquipment().toString(), perhitunganBehandle.getBehandleService().getContSize());
                    BigDecimal sewaForkLiftCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), sewaForkLiftActivityCode);
                    perhitunganBehandle.getPerhitunganSewaAlat().setCharge(sewaForkLiftCharge.multiply(BigDecimal.valueOf(2)));
                    paketBehandle.setSewaAlatActivityCode(sewaForkLiftActivityCode);
                }

                String fullHandlingActivityCode = masterActivityFacadeRemote.translateStrippingStuffingFullHandlingActivityCode(paketBehandle.getFullHandling().toString());
                BigDecimal fullHandlingCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), fullHandlingActivityCode);
                fullHandlingCharge = fullHandlingCharge.multiply(BigDecimal.valueOf(paketBehandle.getFullHandlingTonage())).multiply(BigDecimal.valueOf(2));

                String upahBuruhStrippingActivityCode = masterActivityFacadeRemote.translateUpahBuruhStrippingStuffingActivityCode();
                paketBehandle.setUpahBuruhActivityCode(upahBuruhStrippingActivityCode);
                upahBuruhCharge = upahBuruhCharge.add(fullHandlingCharge);
            }

            perhitunganBehandle.getPerhitunganUpahBuruh().setCharge(upahBuruhCharge);
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "exception caught when call calculatePaketBehandle", re);
        }
    }

    public void handleAdd(ActionEvent event) {
        perhitunganBehandle = new PerhitunganBehandle();
        perhitunganBehandle.setBehandleService(new BehandleService());
        perhitunganBehandle.setPerhitunganUpahBuruh(new PerhitunganUpahBuruh());
        perhitunganBehandle.setPerhitunganSewaAlat(new PerhitunganSewaAlat());
        perhitunganBehandle.setPerhitunganSupervisi(new PerhitunganSupervisi());
    }

    public void handleSelectContPick(ActionEvent event) {
        Integer id = (Integer) event.getComponent().getAttributes().get("idCont");
        ServiceContDischarge serviceContDischarge = serviceContDischargeFacade.find(id);

        perhitunganBehandle.getBehandleService().setServiceContDischarge(serviceContDischarge);
        perhitunganBehandle.getBehandleService().setContNo(serviceContDischarge.getContNo());
        perhitunganBehandle.getBehandleService().setPlanningVessel(registration.getPlanningVessel());
        perhitunganBehandle.getBehandleService().setRegistration(registration);
        perhitunganBehandle.getBehandleService().setMlo(serviceContDischarge.getMlo());
        perhitunganBehandle.getBehandleService().setContSize(serviceContDischarge.getContSize());
        perhitunganBehandle.getBehandleService().setContGross(serviceContDischarge.getContGross());
        perhitunganBehandle.getBehandleService().setContStatus(serviceContDischarge.getContStatus());
        perhitunganBehandle.getBehandleService().setOverSize(serviceContDischarge.getOverSize());
        perhitunganBehandle.getBehandleService().setDg(serviceContDischarge.getDangerous());
        perhitunganBehandle.getBehandleService().setDgLabel(serviceContDischarge.getDgLabel());
        perhitunganBehandle.getBehandleService().setCommodity(serviceContDischarge.getMasterCommodity().getName());
        perhitunganBehandle.getBehandleService().setMasterContainerType(serviceContDischarge.getMasterContainerType());
        perhitunganBehandle.setPerhitunganUpahBuruh(new PerhitunganUpahBuruh());
        perhitunganBehandle.setPerhitunganSewaAlat(new PerhitunganSewaAlat());
        perhitunganBehandle.setPerhitunganSupervisi(new PerhitunganSupervisi());

        String behandleActivityCode = masterActivityFacadeRemote.translateBehandleActivityCode(perhitunganBehandle.getBehandleService().getContSize());
        String supervisiActivityCode = masterActivityFacadeRemote.translateSupervisiActivityCode(perhitunganBehandle.getBehandleService().getContSize());

        BigDecimal behandleCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), behandleActivityCode);
        BigDecimal supervisiCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), supervisiActivityCode);

        perhitunganBehandle.setCharge(behandleCharge);
        perhitunganBehandle.getPerhitunganSupervisi().setCharge(supervisiCharge);
        perhitunganBehandle.getPerhitunganSupervisi().setJumlah(1);
        perhitunganBehandle.getPerhitunganSupervisi().setTotalCharge(perhitunganBehandle.getPerhitunganSupervisi().getCharge().multiply(BigDecimal.valueOf(perhitunganBehandle.getPerhitunganSupervisi().getJumlah())));

        paketBehandle.setBehandleActivityCode(behandleActivityCode);
        paketBehandle.setSupervisiActivityCode(supervisiActivityCode);

        calculatePaketBehandle();
    }

    public void handleConfirm(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean loggedIn = false;
        
        try {
            perhitunganBehandle.getBehandleService().setJobSlip(iDGeneratorFacadeRemote.generateJobSlipID("04"));

            MasterActivity behandleMasterActivity = masterActivityFacadeRemote.find(paketBehandle.getBehandleActivityCode());
            MasterActivity supervisiMasterActivity = masterActivityFacadeRemote.find(paketBehandle.getSupervisiActivityCode());
            MasterActivity upahBuruhStrippingMasterActivity = masterActivityFacadeRemote.find(paketBehandle.getUpahBuruhActivityCode());
            MasterActivity sewaAlatMasterActivity = masterActivityFacadeRemote.find(paketBehandle.getSewaAlatActivityCode());

            perhitunganBehandle.getPerhitunganUpahBuruh().setMasterActivity(upahBuruhStrippingMasterActivity);
            perhitunganBehandle.getPerhitunganUpahBuruh().setRegistration(registration);
            perhitunganBehandle.getPerhitunganUpahBuruh().setJobslip(perhitunganBehandle.getBehandleService().getJobSlip());
            perhitunganBehandle.getPerhitunganUpahBuruh().setMasterCurrency(masterCurrency);

            perhitunganBehandle.getPerhitunganSewaAlat().setMasterActivity(sewaAlatMasterActivity);
            perhitunganBehandle.getPerhitunganSewaAlat().setJobslip(perhitunganBehandle.getBehandleService().getJobSlip());
            perhitunganBehandle.getPerhitunganSewaAlat().setRegistration(registration);

            perhitunganBehandle.getPerhitunganSupervisi().setMasterActivity(supervisiMasterActivity);
            perhitunganBehandle.getPerhitunganSupervisi().setRegistration(registration);
            perhitunganBehandle.getPerhitunganSupervisi().setJobslip(perhitunganBehandle.getBehandleService().getJobSlip());

            perhitunganBehandle.setMasterActivity(behandleMasterActivity);
            perhitunganBehandle.setTotalCharge(perhitunganBehandle.getCharge().add(perhitunganBehandle.getPerhitunganSewaAlat().getCharge()).add(perhitunganBehandle.getPerhitunganSupervisi().getTotalCharge()));

            perhitunganBehandle = behandleCalculationOperationRemote.saveBehandleContainers(perhitunganBehandle);
            perhitunganBehandles.add(perhitunganBehandle);

            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            loggedIn = true;
            viewData();
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "exception caught when call handleConfirm", re);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
        }

        requestContext.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleCancelInvoice(ActionEvent event) {
        boolean loggedIn = false;
        
        try {
            final String noInvoice = registration.getInvoice().getNoInvoice();
            behandleCalculationOperationRemote.cancelInvoice(registration, batalNota);
            perhitunganBehandles.clear();
            
            if (masterSettingAppFacade.isPaymentBankingEnabled())
                try {
                    bankingSyncFacadeRemote.cancelInvoice(noInvoice);
                } catch (RuntimeException e) {
                    System.err.println(FacesHelper.getLocaleMessage("application.integration.banking.failed", FacesContext.getCurrentInstance()));
                    FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.integration.banking.failed");
                }
            
            loggedIn = true;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.cancel.succeeded");
        } catch (RuntimeException e) {
            logger.log(Level.SEVERE, "exception caught when call handleCancelInvoice", e);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }
        
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
        viewData();
    }

    public void handleDownloadTransactionRecap(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean doPrint = false;

        try {
            String key = behandleCalculationOperationRemote.preparePerhitunganReport(registration, masterCurrency, total.multiply(ppnPrint), materaiPrint, perhitunganBehandles);

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

        String key = behandleCalculationOperationRemote.prepareInvoiceReport(registration, perhitunganBehandles);

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
            String key = behandleCalculationOperationRemote.preparePerhitunganInvoiceReport(registration, perhitunganBehandles);

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
     * @return the behandleServices
     */
    public List<PerhitunganBehandle> getPerhitunganBehandles() {
        return perhitunganBehandles;
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

    public PerhitunganBehandle getPerhitunganBehandle() {
        return perhitunganBehandle;
    }

    public void setPerhitunganBehandle(PerhitunganBehandle perhitunganBehandle) {
        this.perhitunganBehandle = perhitunganBehandle;
    }

    public Boolean getIsSimpatReady() {
        return isSimpatReady;
    }

    public PaketBehandle getPaketBehandle() {
        return paketBehandle;
    }

    public void setPaketBehandle(PaketBehandle paketBehandle) {
        this.paketBehandle = paketBehandle;
    }

    public BigDecimal getUpahBuruh() {
        return upahBuruh;
    }
}

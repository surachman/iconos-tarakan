/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.BankingSyncFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReeferDischargeServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReeferMonitoringFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.ejb.remote.ReeferDischargeCalculationOperationRemote;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PerhitunganMonitoring;
import com.pelindo.ebtos.model.db.PerhitunganPlugging;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.ReeferDischargeService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.faces.validator.LongRangeValidator;
import javax.faces.validator.Validator;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "dischargeReeferBean")
@ViewScoped
public class DischargeReeferBean implements Serializable {
    private static final Logger logger = Logger.getLogger(DischargeReeferBean.class.getName());
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacade;
    @EJB
    private InvoiceFacadeRemote invoiceFacade;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private ReeferDischargeServiceFacadeRemote reeferDischargeServiceFacade;
    @EJB
    private RegistrationFacadeRemote registrationFacade;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private ReeferMonitoringFacadeRemote reeferMonitoringFacadeRemote;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;
    @EJB
    private ReeferDischargeCalculationOperationRemote reeferDischargeCalculationOperationRemote;

    private List<Object[]> registrations;
    private List<Object[]> reeferDischargeServices;
    private List<Object[]> serviceContDischarges;
    private Object[] serviceContDischargeAsObjectArray;
    private Registration registration;
    private ReeferDischargeService reeferDischargeService;
    private PerhitunganMonitoring perhitunganMonitoring;
    private PerhitunganPlugging perhitunganPlugging;
    private MasterCurrency masterCurrency;
    private BatalNota batalNota;
    private Object[][] deliveryContainer;
    private BigDecimal ppnPrint;
    private BigDecimal materaiPrint;
    private BigDecimal total;
    private String userId;
    private String blno;
    private String selectedContNo;
    private Boolean disPrint = true;
    private Boolean disKredit = false;
    private Boolean disDetail = true;
    private Boolean disCancelInv = true;
    private Integer minimumShift;
    private Boolean isSimpatReady;

    /** Creates a new instance of DischargeReeferBean */
    public DischargeReeferBean() {}

    @PostConstruct
    private void construct(){
        ppnPrint = BigDecimal.ZERO;
        materaiPrint = BigDecimal.ZERO;
        total = BigDecimal.ZERO;
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        perhitunganPlugging = new PerhitunganPlugging();
        perhitunganPlugging.setRegistration(new Registration());
        perhitunganPlugging.setMasterActivity(new MasterActivity());
        perhitunganMonitoring = new PerhitunganMonitoring();
        perhitunganMonitoring.setRegistration(new Registration());
        perhitunganMonitoring.setMasterActivity(new MasterActivity());
        deliveryContainer = new Object[0][0];

        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        userId = loginSessionBean.getName();
        batalNota = new BatalNota();
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
    }

    public void handleSelectRegistration(ActionEvent event) {
        String noReg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(noReg);
        viewData();
    }

    public void handleShowRegistrations() {
        registrations = registrationFacade.findRegistrationReeferDischarge();
    }

    public void viewData() {
        disCancelInv = Boolean.TRUE;
        reeferDischargeServices = reeferDischargeServiceFacade.findPerhitungan(registration.getNoReg());
        disDetail = false;
        disCancelInv = registration.getInvoice() == null || registration.getInvoice().getNoInvoice() == null;

        total = BigDecimal.ZERO;

        for (Object[] o : reeferDischargeServices) {
            total = total.add((BigDecimal) o[8]).add((BigDecimal) o[10]);
            disCancelInv = disCancelInv || (o[11] != null && (Boolean) o[11]);
        }

        MasterSettingApp settingApp = masterSettingAppFacade.find("ppn");
        ppnPrint = settingApp.getValueFloat();

        masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();

        List<Object[]> materai = masterMateraiFacade.findByCurr(masterCurrency.getCurrCode());

        if (total.compareTo((BigDecimal) materai.get(1)[2]) == -1) { /* jumlah tagihan < nilai minimum */
            materaiPrint = (BigDecimal) materai.get(0)[1];
        } else if (total.compareTo((BigDecimal) materai.get(2)[2]) == -1) {
            materaiPrint = (BigDecimal) materai.get(1)[1];
        } else {
            materaiPrint = (BigDecimal) materai.get(2)[1];
        }
        
        if (registration.getInvoice() != null) {
            if (registration.getInvoice().getValidasiPrint().equals("TRUE")) {
                disPrint = true;
            } else {
                disPrint = false;
            }
                
            disKredit = registration.getInvoice().getPaymentType().equalsIgnoreCase("KREDIT");
        } else {
            registration.setInvoice(new Invoice());
            registration.getInvoice().setPaymentType("CASH");
            disPrint = true;
            disKredit = false;
        }

        blno = "";
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

    public void handleAdd(ActionEvent event) {
        reeferDischargeService = new ReeferDischargeService();
        reeferDischargeService.setMasterContainerType(new MasterContainerType());
        reeferDischargeService.setMasterCommodity(new MasterCommodity());
        minimumShift = 1;
    }

    public void handleShowPluggableContainers(ActionEvent event) {
        serviceContDischarges = serviceContDischargeFacade.findReeferDischargeService(registration.getPlanningVessel().getNoPpkb());
    }

    public void handleSelectPluggableContainer() {
        ServiceContDischarge serviceContDischarge = serviceContDischargeFacade.find(serviceContDischargeAsObjectArray[6]);
        Date plugOn = (Date) serviceContDischargeAsObjectArray[8];
        Date plugOff = (Date) serviceContDischargeAsObjectArray[9];

        if (plugOff == null) {
            plugOff = new Date();
        }

        long duration = plugOff.getTime() - plugOn.getTime();
        minimumShift = 1;

        if (duration > 1) {
            minimumShift = (int) Math.ceil(((duration) * 24) / (8 * 86400000));
        }

        BigDecimal temperature = reeferMonitoringFacadeRemote.findLastTemperature(serviceContDischarge.getContNo());

        reeferDischargeService = new ReeferDischargeService();
        reeferDischargeService.setContNo(serviceContDischarge.getContNo());
        reeferDischargeService.setMlo(serviceContDischarge.getMlo());
        reeferDischargeService.setContSize(serviceContDischarge.getContSize());
        reeferDischargeService.setContGross(serviceContDischarge.getContGross());
        reeferDischargeService.setContStatus(serviceContDischarge.getContStatus());
        reeferDischargeService.setOverSize(serviceContDischarge.getOverSize());
        reeferDischargeService.setDg(serviceContDischarge.getDangerous());
        reeferDischargeService.setDgLabel(serviceContDischarge.getDgLabel());
        reeferDischargeService.setMasterCommodity(serviceContDischarge.getMasterCommodity());
        reeferDischargeService.setMasterContainerType(serviceContDischarge.getMasterContainerType());
        reeferDischargeService.setTemperature(temperature);
        reeferDischargeService.setPlugOnDate(plugOn);
        reeferDischargeService.setShiftPlanning(minimumShift.shortValue());
        reeferDischargeService.setRegistration(registration);
        reeferDischargeService.setPlanningVessel(registration.getPlanningVessel());
    }

    public void handleReeferDischargeConfirm(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        boolean isValid = false;

        try {
            reeferDischargeCalculationOperationRemote.handleAddReeferDischarge(reeferDischargeService, masterCurrency);
            viewData();
            isValid = true;
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (Exception e) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
            logger.log(Level.SEVERE, "exception when call handlereeferDischargeConfirm", e);
        }

        requestContext.addCallbackParam("loggedIn", isValid);
    }

    public void handleSelect(ActionEvent event) {
        selectedContNo = (String) event.getComponent().getAttributes().get("noCont");
    }

    public void handleSubmit(ActionEvent event) {
        Boolean loggedIn = true;
        registration.getInvoice().setNoReg(registration.getNoReg());
        registration.getInvoice().setRegistration(registration);
        registration.getInvoice().setPaymentStatus("UNPAID");
        registration.getInvoice().setJumlahTagihan(total);
        registration.getInvoice().setValidasiPrint("FALSE");
        registration.getInvoice().setMaterai(materaiPrint);
        registration.getInvoice().setPpn(registration.getInvoice().getJumlahTagihan().multiply(ppnPrint));
        registration.getInvoice().setTotalTagihan(registration.getInvoice().getJumlahTagihan().add(registration.getInvoice().getMaterai()).add(registration.getInvoice().getPpn()));
        registration.getInvoice().setMasterCurrency(masterCurrency);
        
        if (registration.getInvoice().getPaymentType().equalsIgnoreCase("CASH")) {
            registration.setStatusService("confirm");
        } else if (registration.getInvoice().getPaymentType().equalsIgnoreCase("KREDIT")) {
            registration.setStatusService("approve");
        }
        
        registration = registrationFacade.editAndFetch(registration);
        viewData();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleCancelInvoice(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean isValid = false;

        try {
            final String noInvoice = registration.getInvoice().getNoInvoice();
            reeferDischargeCalculationOperationRemote.cancelInvoice(registration, batalNota);
            disCancelInv = Boolean.TRUE;
            reeferDischargeServices.clear();

            if (masterSettingAppFacade.isPaymentBankingEnabled())
                try {
                    bankingSyncFacadeRemote.cancelInvoice(noInvoice);
                } catch (RuntimeException e) {
                    System.err.println(FacesHelper.getLocaleMessage("application.integration.banking.failed", facesContext));
                    FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.integration.banking.failed");
                }

            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.cancel.succeeded");
            isValid = true;
            viewData();
        } catch (RuntimeException e) {
            logger.log(Level.SEVERE, "exception caught when call handleSubmitBatalNota", e);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }

        requestContext.addCallbackParam("loggedIn", isValid);
    }

    public void handleDelete(ActionEvent event) {
        try {
            reeferDischargeCalculationOperationRemote.handleDeleteDischargeReefer(selectedContNo, registration.getNoReg());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            viewData();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception when call handleDelete", e);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.delete.failed");
        }
    }

    public void handleFindBl(ActionEvent event) {
        serviceContDischarges = serviceContDischargeFacade.findReeferDischargeServiceByBL(blno, registration.getPlanningVessel().getNoPpkb());
        if (serviceContDischarges.isEmpty()) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.notfound");
        }
    }

    public void handleFindAllBl(ActionEvent event) {
        blno = "";
        serviceContDischarges = serviceContDischargeFacade.findReeferDischargeService(registration.getPlanningVessel().getNoPpkb());
        if (serviceContDischarges.isEmpty()) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.notfound");
        }
    }

    public void handleDownloadTransactionRecap(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/delivery.pdf?no_reg=" + registration.getNoReg() + "&total=" + String.valueOf(total) + "&ppn=" + String.valueOf(getPpnPrint()) + "&materai=" + String.valueOf(getMateraiPrint()) + "&userId=" + getUserId() + "&tipe=reeferD")));
    }

    public void handleDownloadNota(ActionEvent event) {
        if (registration.getInvoice().getValidasiPrint().equals("FALSE")) {
            registration.setInvoice(invoiceFacade.publishInvoice(registration.getInvoice()));
        }

        viewData();

        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/invoice.pdf?no_reg=" + registration.getNoReg() + "&to=" + registration.getInvoice().getTotalTagihan() + "&curr=" + masterCurrency.getCountry() + "&tipe=" + registration.getMasterService().getServiceCode() + "&username=" + getUserId() + "&detail=" + String.valueOf(disDetail))));
    }

    public void handleDownloadDetail(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/detail.pdf?no_reg=" + registration.getNoReg() + "&tipe=" + registration.getMasterService().getServiceCode() + "&username=" + getUserId() + "&ppn=" + registration.getInvoice().getPpn() + "&materai=" + String.valueOf(materaiPrint) + "&total=" + String.valueOf(total))));
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
     * @return the reeferDischargeServices
     */
    public List<Object[]> getReeferDischargeServices() {
        return reeferDischargeServices;
    }

    /**
     * @param reeferDischargeServices the reeferDischargeServices to set
     */
    public void setReeferDischargeServices(List<Object[]> reeferDischargeServices) {
        this.reeferDischargeServices = reeferDischargeServices;
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
     * @return the reeferDischargeService
     */
    public ReeferDischargeService getReeferDischargeService() {
        return reeferDischargeService;
    }

    /**
     * @param reeferDischargeService the reeferDischargeService to set
     */
    public void setReeferDischargeService(ReeferDischargeService reeferDischargeService) {
        this.reeferDischargeService = reeferDischargeService;
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
     * @return the blno
     */
    public String getBlno() {
        return blno;
    }

    /**
     * @param blno the blno to set
     */
    public void setBlno(String blno) {
        this.blno = blno;
    }

    /**
     * @return the deliveryContainer
     */
    public Object[][] getDeliveryContainer() {
        return deliveryContainer;
    }

    /**
     * @param deliveryContainer the deliveryContainer to set
     */
    public void setDeliveryContainer(Object[][] deliveryContainer) {
        this.deliveryContainer = deliveryContainer;
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

    public Boolean getIsSimpatReady() {
        return isSimpatReady;
    }

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    public Object[] getServiceContDischargeAsObjectArray() {
        return serviceContDischargeAsObjectArray;
    }

    public void setServiceContDischargeAsObjectArray(Object[] serviceContDischargeAsObjectArray) {
        this.serviceContDischargeAsObjectArray = serviceContDischargeAsObjectArray;
    }

    public Integer getMinimumShift() {
        return minimumShift;
    }

    public Validator getReeferShiftValidator() {
        return new LongRangeValidator(Long.MAX_VALUE, minimumShift);
    }
}

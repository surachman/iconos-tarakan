/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.ChangeStatusServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.BankingSyncFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.BatalNotaFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.ChangeStatusService;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterService;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Eka
 */
@ManagedBean(name = "changeStatusBean")
@ViewScoped

public class ChangeStatusBean implements Serializable{

    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacade;
    @EJB
    private BatalNotaFacadeRemote batalNotaFacade;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacade;
    @EJB
    private InvoiceFacadeRemote invoiceFacade;
    @EJB
    private RegistrationFacadeRemote registrationFacade;
    @EJB
    private MasterServiceFacadeRemote masterServiceFacadeRemote;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacadeRemote;
    @EJB
    private ChangeStatusServiceFacadeRemote changeStatusServiceFacadeRemote;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacadeRemote;
    
    private List<Object[]> registrations;
    private List<Object[]> serviceContDischarges;
    private List<Object[]> changeStatuses;
    private Registration registration;
    private ServiceContDischarge serviceContDischarge;
    private ChangeStatusService changeStatusService;
    private BatalNota batalNota;
    private Invoice invoice;
    private BigDecimal total;
    private BigDecimal ppnPrint;
    private BigDecimal materaiPrint;
    private Boolean disPrint;
    private Boolean disKredit;
    private Boolean disDetail;
    private Boolean disCancelInv;
    private String userId;
    private MasterCurrency masterCurrency;
    
    @PostConstruct
    private void construct() {
        total = BigDecimal.ZERO;
        ppnPrint = BigDecimal.ZERO;
        materaiPrint = BigDecimal.ZERO;
        disPrint = Boolean.TRUE;
        disKredit = Boolean.FALSE;
        disDetail = Boolean.TRUE;
        disCancelInv = Boolean.TRUE;
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        invoice = new Invoice();
        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        userId = loginSessionBean.getName();
        batalNota = new BatalNota();
    }

    public void handleSelectRegistration(ActionEvent event) {
        String noReg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(noReg);
        viewData();
    }

    public void handleShowRegistration(ActionEvent event) {
        registrations = registrationFacade.findRegistrationChangeStatus();
    }

    public void viewData() {
        changeStatuses = changeStatusServiceFacadeRemote.findChangeStatusService(registration.getPlanningVessel().getNoPpkb());
        disCancelInv = Boolean.TRUE;
        disDetail = Boolean.TRUE;
        disPrint = Boolean.TRUE;
        disKredit = Boolean.FALSE;
        total = BigDecimal.ZERO;

        for (int i = 0; i < changeStatuses.size(); i++) {
            Object[] object = changeStatuses.get(i);
            total = total.add((BigDecimal)object[10]);
        }

        MasterSettingApp settingApp = masterSettingAppFacade.find("ppn");
        ppnPrint = settingApp.getValueFloat();

        masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        List<Object[]> materai = masterMateraiFacade.findByCurr(masterCurrency.getCurrCode());

        if (total.compareTo((BigDecimal) materai.get(1)[2]) == -1) {
            setMateraiPrint((BigDecimal) materai.get(0)[1]);
        } else if (total.compareTo((BigDecimal) materai.get(2)[2]) == -1) {
            setMateraiPrint((BigDecimal) materai.get(1)[1]);
        } else {
            setMateraiPrint((BigDecimal) materai.get(2)[1]);
        }

        invoice = registration.getInvoice();

        if (invoice != null) {
            if(invoice.getValidasiPrint().equals("TRUE")) {
                disPrint = true;
            }
                disKredit = invoice.getPaymentType().equalsIgnoreCase("KREDIT");
            
            if (invoice.isCancelInvoice().equals("TRUE")) {
                disCancelInv = true;
            }
        } else {
            invoice = new Invoice();
            invoice.setPaymentType("CASH");
            disPrint = Boolean.TRUE;
            disKredit = Boolean.FALSE;
            disCancelInv = Boolean.TRUE;
        }
    }
    
    public void onChangeEvent(ValueChangeEvent event) {
        String temp = (String) event.getNewValue();
        invoice.setPaymentType(temp);
        if (temp.equalsIgnoreCase("CASH")) {
            disKredit = Boolean.FALSE;
        } else {
            disKredit = Boolean.TRUE;
        }
    }

    public void handleSelect(ActionEvent event) {
        String jobSlip = (String) event.getComponent().getAttributes().get("jobSlip");
        changeStatusService = changeStatusServiceFacadeRemote.find(jobSlip);
        registration = registrationFacade.find(changeStatusService.getRegistration().getNoReg());
    }

    public void handleSubmit(ActionEvent event) {
        Boolean loggedIn = Boolean.TRUE;
        invoice.setNoReg(registration.getNoReg());
        invoice.setRegistration(registration);
        invoice.setPaymentStatus("UNPAID");
        invoice.setJumlahTagihan(total);
        invoice.setAdditionalCharge(BigDecimal.ZERO);
        invoice.setValidasiPrint("FALSE");
        invoice.setMasterCurrency(masterCurrency);
        invoice.setPpn(invoice.getJumlahTagihan().multiply(ppnPrint));
        
        List<Object[]> materai = masterMateraiFacade.findByCurr(masterCurrency.getCurrCode());

        if (invoice.getJumlahTagihan().compareTo((BigDecimal) materai.get(1)[2]) == -1) { /* jumlah tagihan < nilai minimum */
            invoice.setMaterai((BigDecimal) materai.get(0)[1]);
            setMateraiPrint(invoice.getMaterai());
        } else if (invoice.getJumlahTagihan().compareTo((BigDecimal) materai.get(2)[2]) == -1) {
            invoice.setMaterai((BigDecimal) materai.get(1)[1]);
            setMateraiPrint(invoice.getMaterai());
        } else {
            invoice.setMaterai((BigDecimal) materai.get(2)[1]);
            setMateraiPrint(invoice.getMaterai());
        }

        invoice.setTotalTagihan(invoice.getJumlahTagihan().add(invoice.getMaterai()).add(invoice.getPpn()));
        registration.setInvoice(invoice);

        if (invoice.getPaymentType().equalsIgnoreCase("CASH")) {
            registration.setStatusService("confirm");
        } else if (invoice.getPaymentType().equalsIgnoreCase("KREDIT")) {
            getRegistration().setStatusService("approve");
        }

        registrationFacade.edit(registration);
        viewData();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleDelete(ActionEvent event) {
        changeStatusServiceFacadeRemote.remove(changeStatusService);
        viewData();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
    }

    public void handleAdd(ActionEvent event) {
        changeStatusService = new ChangeStatusService();
        serviceContDischarges = serviceContDischargeFacade.findServiceContDischargeChangeStatus(registration.getPlanningVessel().getNoPpkb());
    }

    public void handleSelectContPick(ActionEvent event) {
        System.out.println("handleSelectContPick");
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        serviceContDischarge = serviceContDischargeFacade.find(id_cont);
        changeStatusService = new ChangeStatusService();
        changeStatusService.setJobSlip(iDGeneratorFacade.generateJobSlipID("24"));
        changeStatusService.setPlanningVessel(registration.getPlanningVessel());
        changeStatusService.setRegistration(registration);
        changeStatusService.setContNo(serviceContDischarge.getContNo());
        changeStatusService.setMlo(serviceContDischarge.getMlo());
        changeStatusService.setContSize(serviceContDischarge.getContSize());
        changeStatusService.setContGross(serviceContDischarge.getContGross());
        changeStatusService.setContStatus(serviceContDischarge.getContStatus());
        changeStatusService.setOverSize(serviceContDischarge.getOverSize());
        changeStatusService.setDg(serviceContDischarge.getDangerous());
        changeStatusService.setDgLabel(serviceContDischarge.getDgLabel());
        changeStatusService.setMasterCommodity(serviceContDischarge.getMasterCommodity());
        changeStatusService.setMasterContainerType(serviceContDischarge.getMasterContainerType());
        changeStatusService.setBlNo(serviceContDischarge.getBlNo());
    }

    public void handleConfirm(ActionEvent event) {
        boolean loggedIn = Boolean.FALSE;
        boolean isOverSize = false;
        
        if (changeStatusService.getContNo() == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.transaction.notcompleted");
        } else {
            if (serviceContDischarge.getOverSize().equals("TRUE")) {
                isOverSize = true;
            }
            
            String dangerousClass = null;
                if(serviceContDischarge.getMasterCommodity() != null && serviceContDischarge.getMasterCommodity().getMasterDangerousClass() != null)
                    dangerousClass = serviceContDischarge.getMasterCommodity().getMasterDangerousClass().getDangerousClass();

            String fclHandlingActivityCode = masterActivityFacadeRemote.translateStevedoringActivityCode(
                    "FCL", 
                    serviceContDischarge.getContSize(), serviceContDischarge.getCrane(), 
                    isOverSize, 
                    null,
                    serviceContDischarge.getDangerous().equals("TRUE"),
                    serviceContDischarge.getDgLabel().equals("TRUE"),
                    dangerousClass,
                    isOverSize,
                    serviceContDischarge.getTwentyOneFeet().equals("TRUE"));
            
            String lclHandlingActivityCode = masterActivityFacadeRemote.translateStevedoringActivityCode(
                    "LCL", serviceContDischarge.getContSize(), 
                    serviceContDischarge.getCrane(), 
                    isOverSize, 
                    null,
                    serviceContDischarge.getDangerous().equals("TRUE"),
                    serviceContDischarge.getDgLabel().equals("TRUE"),
                    dangerousClass,
                    isOverSize,
                    serviceContDischarge.getTwentyOneFeet().equals("TRUE"));
            
            String administrationActivityCode = masterActivityFacadeRemote.translateCancelDocumentActivityCode();

            BigDecimal administrationCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), administrationActivityCode);
            BigDecimal changeStatusCharge = masterTarifContFacadeRemote.findChangeStatusChargeByActivityAndCurr(masterCurrency.getCurrCode(),fclHandlingActivityCode, lclHandlingActivityCode);

            changeStatusService.setAdmCharge(administrationCharge);
            changeStatusService.setCharge(changeStatusCharge);
            changeStatusService.setTotalCharge(administrationCharge.add(changeStatusCharge));
            
            changeStatusServiceFacadeRemote.create(changeStatusService);
            
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
            viewData();
        }
    }

    public void handleCancelInvoice(ActionEvent event) {
        boolean loggedIn = Boolean.FALSE;

        try {
            loggedIn = Boolean.TRUE;
            registration = registrationFacade.find(invoice.getNoReg());
            batalNota.setCustCode(registration.getMasterCustomer().getCustCode());
            final String noInvoice = invoice.getNoInvoice();
            batalNota.setNoInvoice(noInvoice);
            batalNota.setNoReg(invoice.getNoReg());
            batalNota.setPaymentDate(invoice.getPaymentDate());
            batalNota.setTotalTagihan(invoice.getTotalTagihan());

            invoiceFacade.edit(invoice);

            invoice.setCancelInvoice("TRUE");
            invoice.setNoInvoice(null);
            invoice.setNoFakturPajak(null);

            batalNotaFacade.create(batalNota);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.cancel.succeeded");
           
            if (masterSettingAppFacade.isPaymentBankingEnabled())
                try {
                    bankingSyncFacadeRemote.cancelInvoice(noInvoice);
                } catch (RuntimeException e) {
                    System.err.println(FacesHelper.getLocaleMessage("application.integration.banking.failed", FacesContext.getCurrentInstance()));
                    FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.integration.banking.failed");
                }
        } catch (EJBException e) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
        viewData();
    }

    public void handleDownloadTransactionRecap(ActionEvent event) {
        System.out.println("handleDownloadTransactionRecap");
        
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", Boolean.TRUE); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc,
                "/delivery.pdf?no_reg=" + registration.getNoReg()
                + "&total=" + String.valueOf(total)
                + "&ppn=" + String.valueOf(getPpnPrint())
                + "&materai=" + String.valueOf(getMateraiPrint())
                + "&userId=" + getUserId() + 
                "&tipe=changeStatus")));
    }

    public void handleDownloadNota(ActionEvent event) {
        invoice = invoiceFacade.find(registration.getNoReg());

        if (invoice.getValidasiPrint().equals("FALSE")) {
            invoice.setNoInvoice(iDGeneratorFacade.generateInvoiceID());
            invoice.setNoFakturPajak(iDGeneratorFacade.generateFakturPajakID());
            invoice.setValidasiPrint("TRUE");
            invoiceFacade.edit(invoice);
            registration.setInvoice(invoice);

            if (masterSettingAppFacade.isPaymentBankingEnabled())
                try {
                    GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
                    gc.setTime(invoice.getCreatedDate());
                    MasterService masterService = masterServiceFacadeRemote.find(invoice.getRegistration().getMasterService().getServiceCode());
                    invoice.getRegistration().setMasterService(masterService);
                    String noInvoice = bankingSyncFacadeRemote.createInvoice(
                            invoice.getNoInvoice(),
                            invoice.getRegistration().getMasterCustomer().getCustCode(),
                            invoice.getRegistration().getMasterCustomer().getName(),
                            invoice.getRegistration().getPlanningVessel().getVesselCode(),
                            invoice.getRegistration().getPlanningVessel().getPreserviceVessel().getMasterVessel().getName(),
                            invoice.getRegistration().getMasterService().getServiceCode(),
                            invoice.getRegistration().getMasterService().getServiceName(),
                            invoice.getMasterCurrency().getCurrCode(),
                            invoice.getTotalTagihan(),
                            DatatypeFactory.newInstance().newXMLGregorianCalendar(gc));
                } catch (DatatypeConfigurationException ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                } catch (RuntimeException ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "failed post data to e-banking", ex);
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "failed post data to e-banking", ex);
                }
        }

        viewData();

        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", Boolean.TRUE); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, 
                "/invoice.pdf?no_reg=" + registration.getNoReg() + "&to=" + invoice.getTotalTagihan() + "&curr=" + masterCurrency.getCountry() + "&tipe=" +
                registration.getMasterService().getServiceCode() + "&username=" + getUserId() + 
                "&detail=" + String.valueOf(disDetail))));
    }

    public void handleDownloadDetail(ActionEvent event) {
        System.out.println("handleDownloadDetail");
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", Boolean.TRUE);
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, 
                "/detail.pdf?no_reg=" + registration.getNoReg() +
                "&tipe=" + registration.getMasterService().getServiceCode() + 
                "&username=" + getUserId() + 
                "&ppn=" + String.valueOf(ppnPrint) + 
                "&materai=" + String.valueOf(materaiPrint) + 
                "&total=" + String.valueOf(total))));
    }
    
    public List<Object[]> getRegistrations() {
        return registrations;
    }
    
    public void setRegistrations(List<Object[]> registrations) {
        this.registrations = registrations;
    }
    
    public Registration getRegistration() {
        return registration;
    }
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }
    public Invoice getInvoice() {
        return invoice;
    }
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public List<Object[]> getServiceContDischarges() {
        return serviceContDischarges;
    }
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
    public Boolean getDisPrint() {
        return disPrint;
    }
    public void setDisPrint(Boolean disPrint) {
        this.disPrint = disPrint;
    }
    public Boolean getDisKredit() {
        return disKredit;
    }
    public void setDisKredit(Boolean disKredit) {
        this.disKredit = disKredit;
    }
    public Boolean getDisDetail() {
        return disDetail;
    }
    
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

    public List<Object[]> getChangeStatuses() {
        return changeStatuses;
    }

    public void setChangeStatuses(List<Object[]> changeStatuses) {
        this.changeStatuses = changeStatuses;
    }

    public ChangeStatusService getChangeStatusService() {
        return changeStatusService;
    }

    public void setChangeStatusService(ChangeStatusService changeStatusService) {
        this.changeStatusService = changeStatusService;
    }

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }
}

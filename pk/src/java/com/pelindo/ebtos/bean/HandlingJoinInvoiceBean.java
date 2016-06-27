/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.BankingSyncFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.HandlingInvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.model.NamedObject;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterService;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author dycoder
 */
@ManagedBean(name="handlingJoinInvoiceBean")
@ViewScoped
public class HandlingJoinInvoiceBean implements Serializable {
    private static final String SERVICE_CODE = "SCHDL";
    private static final String PPKB_SUFFIX = "L";
    private static final String DISCHARGE = "discharge";
    private static final String LOAD = "load";

    @EJB
    private IDGeneratorFacadeRemote generatorFacadeRemote;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacadeRemote;
    @EJB
    private HandlingInvoiceFacadeRemote handlingInvoiceFacadeRemote;
    @EJB
    private InvoiceFacadeRemote invoiceFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private RegistrationFacadeRemote registrationFacadeRemote;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private MasterServiceFacadeRemote masterServiceFacadeRemote;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacadeRemote;

    private List<Object[]> serviceVessels;
    private LoginSessionBean loginSessionBean;
    private PlanningVessel planningVessel;
    private Registration registration;
    private Registration registrationUsd;
    private Invoice invoice;
    private Invoice invoiceUsd;
    private BigDecimal ppn;
    private BigDecimal ppnUsd;
    private BigDecimal materai;
    private BigDecimal materaiUsd;
    private BigDecimal totalCharge;
    private BigDecimal totalChargeUsd;
    private BigDecimal upahBuruhCharge;
    private MasterCurrency masterCurrency;
    private MasterCurrency usdMasterCurrency;
    private Object[] containerServicesChargeDetail;
    private Object[] containerServicesChargeDetailUsd;
    private Object[] serviceVessel;    
    private String noPpkb;
    private String mode;
    private String username;
    private String ppkbHandling;
    private String selectedReport;
    private String selectedReportCurrency;
    private String tabState;

    /** Creates a new instance of HandlingLoadInvoiceBean */
    public HandlingJoinInvoiceBean() {}

    @PostConstruct
    private void construct() {
        invoice = new Invoice();
        registration = new Registration();
        loginSessionBean = LoginSessionBean.getCurrentInstance();
        username = loginSessionBean.getName();
        serviceVessels = serviceVesselFacadeRemote.findServiceVesselsServiced();
        selectedReport = "perhitunganHandlingPetikemas";
        selectedReportCurrency = "IDR";
        masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        usdMasterCurrency = masterCurrencyFacadeRemote.getUSDCurrency();
        tabState = DISCHARGE;
    }

    public void handleSelectNoPPKB(ActionEvent event){
        noPpkb = (String) event.getComponent().getAttributes().get("noPPKB");
        selectNoPPKB();
    }
    
    public void selectNoPPKB() {        
        ppkbHandling = noPpkb + PPKB_SUFFIX;
        planningVessel = planningVesselFacadeRemote.find(noPpkb);        
        serviceVessel = serviceVesselFacadeRemote.findServiceVesselByPpkb(noPpkb);

        registration = invoiceFacadeRemote.findRegistrationByPpkbHandlingAndCurrency(ppkbHandling, masterCurrency.getCurrCode());
        if (registration != null)
            invoice = registration.getInvoice();

        registrationUsd = invoiceFacadeRemote.findRegistrationByPpkbHandlingAndCurrency(ppkbHandling, usdMasterCurrency.getCurrCode());
        if (registrationUsd != null)
            invoiceUsd = registrationUsd.getInvoice();

        prepareInvoiceCalculation(tabState);
    }

    public void handleBentukTiga(ActionEvent event) {
        String reportUrl = "../../../../perhitunganJasaTerminal.pdf?no_ppkb=%s&report=%s&curr_code=%s";
        boolean print = true;
        mode = (String) event.getComponent().getAttributes().get("mode");
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", print);
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(String.format(reportUrl, planningVessel.getNoPpkb(), selectedReport, selectedReportCurrency)));
    }
    
    public void handleTabChange(TabChangeEvent event){
        tabState = event.getTab().getId();
        selectNoPPKB();
    }
    
    private void prepareInvoiceCalculation(String mode) {
        List<Object[]> results;
        switch(mode){
            case LOAD:
                results = handlingInvoiceFacadeRemote.findContainerServicesChargeDetailLoadByNoPpkb(planningVessel.getNoPpkb());
                break;                
            default:
                results = handlingInvoiceFacadeRemote.findContainerServicesChargeDetailDischargeByNoPpkb(planningVessel.getNoPpkb());
                break;            
        }
        calculateInvoice(results);
    }

    private void calculateInvoice(List<Object[]> results) {
        containerServicesChargeDetail = results.get(0);
        BigDecimal handlingCharge = (BigDecimal) containerServicesChargeDetail[0];
        BigDecimal penumpukanCharge = (BigDecimal) containerServicesChargeDetail[1];
        BigDecimal shiftingCharge = (BigDecimal) containerServicesChargeDetail[4];
        BigDecimal transhipmentCharge = (BigDecimal) containerServicesChargeDetail[3];
        BigDecimal hatchMovesCharge = (BigDecimal) containerServicesChargeDetail[2];
        BigDecimal airKapalCharge = (BigDecimal) containerServicesChargeDetail[6];
        upahBuruhCharge = (BigDecimal) containerServicesChargeDetail[5];
        totalCharge = handlingCharge.add(penumpukanCharge).add(shiftingCharge).add(transhipmentCharge).add(hatchMovesCharge).add(airKapalCharge);

        MasterSettingApp settingApp = masterSettingAppFacade.find("ppn");
        ppn = totalCharge.multiply(settingApp.getValueFloat());

        List<Object[]> materaiList = masterMateraiFacadeRemote.findByCurr(masterCurrency.getCurrCode());
        if (totalCharge.compareTo((BigDecimal) materaiList.get(1)[2]) == -1) {
            materai = (BigDecimal) materaiList.get(0)[1];
        } else if (totalCharge.compareTo((BigDecimal) materaiList.get(2)[2]) == -1) {
            materai = (BigDecimal) materaiList.get(1)[1];
        } else {
            materai = (BigDecimal) materaiList.get(2)[1];
        }

        containerServicesChargeDetailUsd = results.get(1);
        handlingCharge = (BigDecimal) containerServicesChargeDetailUsd[0];
        shiftingCharge = (BigDecimal) containerServicesChargeDetailUsd[4];
        transhipmentCharge = (BigDecimal) containerServicesChargeDetailUsd[3];
        hatchMovesCharge = (BigDecimal) containerServicesChargeDetailUsd[2];
        airKapalCharge = (BigDecimal) containerServicesChargeDetailUsd[6];
        totalChargeUsd = handlingCharge.add(shiftingCharge).add(transhipmentCharge).add(hatchMovesCharge).add(airKapalCharge);

        ppnUsd = totalChargeUsd.multiply(settingApp.getValueFloat());

        materaiList = masterMateraiFacadeRemote.findByCurr(usdMasterCurrency.getCurrCode());
        if (totalChargeUsd.compareTo((BigDecimal) materaiList.get(1)[2]) == -1) {
            materaiUsd = (BigDecimal) materaiList.get(0)[1];
        } else if (totalChargeUsd.compareTo((BigDecimal) materaiList.get(2)[2]) == -1) {
            materaiUsd = (BigDecimal) materaiList.get(1)[1];
        } else {
            materaiUsd = (BigDecimal) materaiList.get(2)[1];
        }
    }

    public void handleDownloadDischargeInvoice(ActionEvent event){
        handleDownloadInvoice(event, DISCHARGE);
    }
    
    public void handleDownloadLoadInvoice(ActionEvent event){
        handleDownloadInvoice(event, LOAD);
    }
    
    public void handleDownloadInvoice(ActionEvent event, String mode) {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            prepareInvoiceCalculation(mode);

            if (registration == null) {
                registration = new Registration();
                invoice = new Invoice();

                String generatedRegistrationID = generatorFacadeRemote.generateRegistrationID();
                String generatedInvoiceID = generatorFacadeRemote.generateInvoiceID();
                String generatedFakturPajakID = generatorFacadeRemote.generateFakturPajakID();
                MasterService masterService = masterServiceFacadeRemote.find(SERVICE_CODE);
                MasterCustomer masterCustomer = planningVessel.getPreserviceVessel().getMasterCustomer();

                registration.setNoReg(generatedRegistrationID);
                registration.setMasterService(masterService);
                registration.setStatusService(Registration.STATUS_APPROVE);
                registration.setMasterCustomer(masterCustomer);
                registration.setPlanningVessel(planningVessel);

                invoice.setNoReg(generatedRegistrationID);
                invoice.setPpkbHandling(ppkbHandling);
                invoice.setNoInvoice(generatedInvoiceID);
                invoice.setNoFakturPajak(generatedFakturPajakID);
                invoice.setPaymentType("KREDIT");
                invoice.setPaymentStatus("UNPAID");
                invoice.setValidasiPrint("FALSE");
                invoice.setMasterCurrency(masterCurrency);
                invoice.setJumlahTagihan(totalCharge);
                invoice.setAdditionalCharge(upahBuruhCharge);
                invoice.setPpn(ppn);
                invoice.setMaterai(materai);
                invoice.setTotalTagihan(invoice.getJumlahTagihan().add(invoice.getPpn()).add(invoice.getMaterai()).add(invoice.getAdditionalCharge()));

                registration.setInvoice(invoice);
                registrationFacadeRemote.create(registration);
                invoice = invoiceFacadeRemote.find(registration.getNoReg());

                GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
                gc.setTime(invoice.getCreatedDate());

                if (masterSettingAppFacade.isPaymentBankingEnabled())
                    try {
                        String noInvoice = bankingSyncFacadeRemote.createInvoice(
                                invoice.getNoInvoice(),
                                masterCustomer.getCustCode(),
                                masterCustomer.getName(),
                                planningVessel.getVesselCode(),
                                planningVessel.getPreserviceVessel().getMasterVessel().getName(),
                                masterService.getServiceCode(),
                                masterService.getServiceName(),
                                invoice.getMasterCurrency().getCurrCode(),
                                invoice.getTotalTagihan(),
                                DatatypeFactory.newInstance().newXMLGregorianCalendar(gc));
                    } catch (DatatypeConfigurationException ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    } catch (RuntimeException ex) {
                        System.err.println("failed post data to e-banking");
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        System.err.println("failed post data to e-banking");
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
            } else {
                invoice.setMasterCurrency(masterCurrency);
                invoice.setJumlahTagihan(totalCharge);
                invoice.setPpn(ppn);
                invoice.setMaterai(materai);
                invoice.setAdditionalCharge(upahBuruhCharge);
                invoice.setTotalTagihan(invoice.getJumlahTagihan().add(invoice.getPpn()).add(invoice.getMaterai()).add(invoice.getAdditionalCharge()));
                invoiceFacadeRemote.edit(invoice);
            }

            if (invoice.getValidasiPrint().equals("FALSE") || loginSessionBean.isSupervisor()) {
                mode = "HandlingInvoice";

                context.addCallbackParam("doPrint", true);
                context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
                context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/ageninvoice.pdf?"
                        + "no_ppkb=" + noPpkb + "&"
                        + "mode=" + mode + "&"
                        + "username=" + username + "&"
                        + "no_reg=" + invoice.getNoReg() + "&"
                        + "curr=" + masterCurrency.getCurrCode() + "&"
                        + "total=" + invoice.getTotalTagihan().toString())));

                if (invoice.getValidasiPrint().equals("FALSE")) {
                    invoice.setValidasiPrint("TRUE");
                    invoiceFacadeRemote.edit(invoice);
                }

                return;
            }
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        context.addCallbackParam("doPrint", false);
    }
    
    public void handleDownloadDischargeInvoiceUsd(ActionEvent event){
        handleDownloadInvoiceUsd(event, DISCHARGE);
    }
    
    public void handleDownloadLoadInvoiceUsd(ActionEvent event){
        handleDownloadInvoiceUsd(event, LOAD);
    }

    public void handleDownloadInvoiceUsd(ActionEvent event, String mode) {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            prepareInvoiceCalculation(mode);

            if (registrationUsd == null) {
                registrationUsd = new Registration();
                invoiceUsd = new Invoice();

                String generatedRegistrationID = generatorFacadeRemote.generateRegistrationID();
                String generatedInvoiceID = generatorFacadeRemote.generateInvoiceID();
                String generatedFakturPajakID = generatorFacadeRemote.generateFakturPajakID();
                MasterService masterService = masterServiceFacadeRemote.find(SERVICE_CODE);
                MasterCustomer masterCustomer = planningVessel.getPreserviceVessel().getMasterCustomer();

                registrationUsd.setNoReg(generatedRegistrationID);
                registrationUsd.setMasterService(masterService);
                registrationUsd.setStatusService(Registration.STATUS_APPROVE);
                registrationUsd.setMasterCustomer(masterCustomer);
                registrationUsd.setPlanningVessel(planningVessel);

                invoiceUsd.setNoReg(generatedRegistrationID);
                invoiceUsd.setPpkbHandling(ppkbHandling);
                invoiceUsd.setNoInvoice(generatedInvoiceID);
                invoiceUsd.setNoFakturPajak(generatedFakturPajakID);
                invoiceUsd.setPaymentType("KREDIT");
                invoiceUsd.setPaymentStatus("UNPAID");
                invoiceUsd.setValidasiPrint("FALSE");
                invoiceUsd.setMasterCurrency(usdMasterCurrency);
                invoiceUsd.setJumlahTagihan(totalChargeUsd);
                invoiceUsd.setAdditionalCharge(BigDecimal.ZERO);
                invoiceUsd.setPpn(ppnUsd);
                invoiceUsd.setMaterai(materaiUsd);
                invoiceUsd.setTotalTagihan(invoiceUsd.getJumlahTagihan().add(invoiceUsd.getPpn()).add(invoiceUsd.getMaterai()).add(invoiceUsd.getAdditionalCharge()));

                registrationUsd.setInvoice(invoiceUsd);
                registrationFacadeRemote.create(registrationUsd);
                invoiceUsd = invoiceFacadeRemote.find(registrationUsd.getNoReg());

                GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
                gc.setTime(invoiceUsd.getCreatedDate());

                if (masterSettingAppFacade.isPaymentBankingEnabled())
                    try {
                        String noInvoice = bankingSyncFacadeRemote.createInvoice(
                                invoiceUsd.getNoInvoice(),
                                masterCustomer.getCustCode(),
                                masterCustomer.getName(),
                                planningVessel.getVesselCode(),
                                planningVessel.getPreserviceVessel().getMasterVessel().getName(),
                                masterService.getServiceCode(),
                                masterService.getServiceName(),
                                invoiceUsd.getMasterCurrency().getCurrCode(),
                                invoiceUsd.getTotalTagihan(),
                                DatatypeFactory.newInstance().newXMLGregorianCalendar(gc));
                    } catch (DatatypeConfigurationException ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    } catch (RuntimeException ex) {
                        System.err.println("failed post data to e-banking");
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        System.err.println("failed post data to e-banking");
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
            } else {
                invoiceUsd.setMasterCurrency(usdMasterCurrency);
                invoiceUsd.setJumlahTagihan(totalChargeUsd);
                invoiceUsd.setPpn(ppnUsd);
                invoiceUsd.setMaterai(materaiUsd);
                invoiceUsd.setAdditionalCharge(BigDecimal.ZERO);
                invoiceUsd.setTotalTagihan(invoiceUsd.getJumlahTagihan().add(invoiceUsd.getPpn()).add(invoiceUsd.getMaterai()).add(invoiceUsd.getAdditionalCharge()));
                invoiceFacadeRemote.edit(invoiceUsd);
            }

            if (invoiceUsd.getValidasiPrint().equals("FALSE") || loginSessionBean.isSupervisor()) {
                mode = "HandlingLoadInvoice";

                context.addCallbackParam("doPrint", true);
                context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
                context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/ageninvoice.pdf?"
                        + "no_ppkb=" + noPpkb + "&"
                        + "mode=" + mode + "&"
                        + "username=" + username + "&"
                        + "no_reg=" + invoiceUsd.getNoReg() + "&"
                        + "curr=" + usdMasterCurrency.getCurrCode() + "&"
                        + "total=" + invoiceUsd.getTotalTagihan().toString())));

                if (invoiceUsd.getValidasiPrint().equals("FALSE")) {
                    invoiceUsd.setValidasiPrint("TRUE");
                    invoiceFacadeRemote.edit(invoiceUsd);
                }

                return;
            }
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        context.addCallbackParam("doPrint", false);
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public boolean isKeuangan(){
        if (loginSessionBean != null && loginSessionBean.isActive() && loginSessionBean.getGroups().contains(NamedObject.KEUANGAN_CN)){
            return true;
        }
        return false;
    }

    public boolean isOperasional(){
        if (loginSessionBean != null && loginSessionBean.isActive() && (loginSessionBean.getGroups().contains(NamedObject.PERENCANA_DAN_OPERASI_CN) || loginSessionBean.getGroups().contains(NamedObject.SPV_PERENCANAAN_DAN_OPERASI_CN))){
            return true;
        }
        return false;
    }

    public LoginSessionBean getLoginSessionBean(){
        return loginSessionBean;
    }

    public Object[] getServiceVessel() {
        return serviceVessel;
    }

    public List<Object[]> getServiceVessels() {
        return serviceVessels;
    }

    public BigDecimal getMaterai() {
        return materai;
    }

    public BigDecimal getPpn() {
        return ppn;
    }

    public Object[] getContainerServicesChargeDetail() {
        return containerServicesChargeDetail;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    public BigDecimal getUpahBuruhCharge() {
        return upahBuruhCharge;
    }

    public String getSelectedReport() {
        return selectedReport;
    }

    public void setSelectedReport(String selectedReport) {
        this.selectedReport = selectedReport;
    }

    public String getSelectedReportCurrency() {
        return selectedReportCurrency;
    }

    public void setSelectedReportCurrency(String selectedReportCurrency) {
        this.selectedReportCurrency = selectedReportCurrency;
    }

    public Object[] getContainerServicesChargeDetailUsd() {
        return containerServicesChargeDetailUsd;
    }

    public BigDecimal getMateraiUsd() {
        return materaiUsd;
    }

    public BigDecimal getTotalChargeUsd() {
        return totalChargeUsd;
    }

    public BigDecimal getPpnUsd() {
        return ppnUsd;
    }

    public MasterCurrency getUsdMasterCurrency() {
        return usdMasterCurrency;
    }

    public Invoice getInvoiceUsd() {
        return invoiceUsd;
    }
}

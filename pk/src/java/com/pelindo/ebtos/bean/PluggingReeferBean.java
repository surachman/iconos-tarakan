/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.BankingSyncFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.BatalNotaFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDiscountFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganMonitoringFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPluggingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PluggingReeferServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PerhitunganMonitoring;
import com.pelindo.ebtos.model.db.PerhitunganPlugging;
import com.pelindo.ebtos.model.db.PluggingReeferService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterService;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
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
@ManagedBean(name = "pluggingReeferBean")
@ViewScoped
public class PluggingReeferBean implements Serializable {

    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacade;
    @EJB
    private BatalNotaFacadeRemote batalNotaFacade;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacade;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacade;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacade;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacade;
    @EJB
    private InvoiceFacadeRemote invoiceFacade;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private PerhitunganMonitoringFacadeRemote perhitunganMonitoringFacade;
    @EJB
    private PerhitunganPluggingFacadeRemote perhitunganPluggingFacade;
    @EJB
    private PluggingReeferServiceFacadeRemote pluggingReeferServiceFacade;
    @EJB
    private RegistrationFacadeRemote registrationFacade;
    @EJB
    private MasterDiscountFacadeRemote masterDiscountFacade;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;

    private List<Object[]> masterContainerTypes, masterCommoditys;
    private List<Object[]> registrations;
    private List<Object[]> pluggingReeferServices;
    private Registration registration;
    private Invoice invoice;
    private PluggingReeferService pluggingReeferService;
    private PerhitunganMonitoring perhitunganMonitoring;
    private PerhitunganPlugging perhitunganPlugging;
    private BatalNota batalNota;
    private String no_reg;
    private BigDecimal total = BigDecimal.ZERO;
    private String language = null;
    private String country = null;
    private BigDecimal ppnPrint = BigDecimal.ZERO;
    private BigDecimal materaiPrint = BigDecimal.ZERO;
    private String symbol;
    private String userId = null;
    private String to;
    private Boolean disPrint = true;
    private Boolean disKredit = false;
    private Boolean disDetail = true;
    private Boolean disCancelInv = true;
    private Boolean isSimpatReady;

    /** Creates a new instance of PluggingReefer */
    public PluggingReeferBean() {}

    @PostConstruct
    private void construct() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setMasterService(new MasterService());
        pluggingReeferService = new PluggingReeferService();
        pluggingReeferService.setMasterCommodity(new MasterCommodity());
        pluggingReeferService.setMasterContainerType(new MasterContainerType());
        perhitunganPlugging = new PerhitunganPlugging();
        perhitunganPlugging.setRegistration(new Registration());
        perhitunganPlugging.setMasterActivity(new MasterActivity());
        perhitunganMonitoring = new PerhitunganMonitoring();
        perhitunganMonitoring.setRegistration(new Registration());
        perhitunganMonitoring.setMasterActivity(new MasterActivity());
        invoice = new Invoice();
        this.language = "id";
        this.country = "ID";

        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        userId = loginSessionBean.getName();
        batalNota = new BatalNota();

        registrations = registrationFacade.findRegistrationPluggingOnly();
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(no_reg);
        viewData();
    }

    public void viewData() {
        disCancelInv = Boolean.TRUE;
        setPluggingReeferServices(pluggingReeferServiceFacade.findPerhitungan(no_reg));
        if (pluggingReeferServices.size() > 10) {
            disDetail = false;
        } else {
            disDetail = true;
        }

        BigDecimal t = BigDecimal.ZERO;
        for (Object[] o : getPluggingReeferServices()) {
            this.symbol = (String) (o[10]);
            this.language = (String) (o[11]);
            this.country = (String) (o[12]);
            t = t.add((BigDecimal) o[7]).add((BigDecimal) o[9]);
        }
        setTotal(t);

        //ambil nilai ppn
        MasterSettingApp settingApp = new MasterSettingApp();
        settingApp = masterSettingAppFacade.find("ppn");
        setPpnPrint(settingApp.getValueFloat());

        //ambil nilai materai        
        List<Object[]> materai = new ArrayList<Object[]>();

        materai = masterMateraiFacade.findByCurr("IDR");
        if (total.compareTo((BigDecimal) materai.get(1)[2]) == -1) { /* jumlah tagihan < nilai minimum */
            setMateraiPrint((BigDecimal) materai.get(0)[1]);
        } else if (total.compareTo((BigDecimal) materai.get(2)[2]) == -1) {
            setMateraiPrint((BigDecimal) materai.get(1)[1]);
        } else {
            setMateraiPrint((BigDecimal) materai.get(2)[1]);
        }

        invoice = invoiceFacade.find(no_reg);
        if (invoice != null) {
            BigDecimal tt = invoice.getTotalTagihan();
            BigInteger tot = tt.toBigInteger();
            if (invoice.getMasterCurrency().getCurrCode().equalsIgnoreCase("USD")) {
                to = tt.toString();
            } else {
                to = tot.toString();
            }
            disPrint = invoice.getValidasiPrint().equals("TRUE") ? true : false;
            if (invoice.getPaymentType().equalsIgnoreCase("KREDIT")) {
                disKredit = true;
            } else {
                disKredit = false;
            }

            /*cek apakah invoice sudah memiliki no invoice dan belum dicancel invoice, apabila sudah maka invoice tidak dapat di cancel*/
            if (invoice.getNoInvoice() != null && invoice.isCancelInvoice().equals("FALSE")) {
                disCancelInv = Boolean.FALSE;
            }
        } else {
            invoice = new Invoice();
            invoice.setPaymentType("CASH");
            disPrint = true;
            disKredit = false;
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

    public void handleAdd(ActionEvent event) {
        pluggingReeferService = new PluggingReeferService();
        pluggingReeferService.setMasterCommodity(new MasterCommodity());
        pluggingReeferService.setMasterContainerType(new MasterContainerType());
        pluggingReeferService.setJobSlip(iDGeneratorFacade.generateJobSlipID("03"));
        masterContainerTypes = masterContainerTypeFacade.findReefer();
        masterCommoditys = masterCommodityFacade.findAllNative();
    }

    public void handleSave(ActionEvent event) {
        Boolean loggedIn = true;
        MasterContainerType mct = new MasterContainerType();
        mct = masterContainerTypeFacade.find(pluggingReeferService.getMasterContainerType().getContType());
        pluggingReeferService.setContSize(mct.getFeetMark());
        pluggingReeferService.setRegistration(registration);
        pluggingReeferServiceFacade.edit(pluggingReeferService);

        // insert ke perhitungan_plugging
        perhitunganPlugging.setRegistration(registration);
        perhitunganPlugging.setContNo(pluggingReeferService.getContNo());
        perhitunganPlugging.setMlo(pluggingReeferService.getMlo());
        perhitunganPlugging.setBlNo(pluggingReeferService.getBlNo());

        // menentukan id activity
        String pluggingActivityCode = null;
        if (pluggingReeferService.getContSize() == 20) {
            pluggingActivityCode = "D001";
        } else {
            pluggingActivityCode = "D002";
        }
        perhitunganPlugging.setMasterActivity(masterActivityFacade.find(pluggingActivityCode));

        // set amount
        BigDecimal pluggingCharge = BigDecimal.ZERO;
        for (Object[] o1 : masterTarifContFacade.findAmountByActivity(pluggingActivityCode)) {
            if (o1[1].equals("IDR")) {
                pluggingCharge = (BigDecimal) o1[0];
            }
        }

        BigDecimal[] discount = masterDiscountFacade.getValidDiscount(registration.getMasterCustomer().getCustCode(), pluggingActivityCode);
        BigDecimal discountAsPercentage = discount[0];
        BigDecimal discountAsMoney = discount[1];
        
        if (discountAsMoney == null) {
            discountAsMoney = pluggingCharge.multiply(discountAsPercentage);
        }

        perhitunganPlugging.setCurrCode("IDR");
        perhitunganPlugging.setCharge(pluggingCharge.subtract(discountAsMoney));
        BigDecimal totalPluggingCharge = perhitunganPlugging.getCharge().multiply(BigDecimal.valueOf(pluggingReeferService.getShiftPlanning()));
        perhitunganPlugging.setTotalCharge(totalPluggingCharge);
        perhitunganPluggingFacade.edit(perhitunganPlugging);

        // insert ke perhitungan_monitoring
        perhitunganMonitoring.setRegistration(registration);
        perhitunganMonitoring.setContNo(pluggingReeferService.getContNo());
        perhitunganMonitoring.setMlo(pluggingReeferService.getMlo());
        perhitunganMonitoring.setBlNo(pluggingReeferService.getBlNo());

        // menentukan id activity
        String monitoringActivityCode = null;
        if (pluggingReeferService.getContSize() == 20) {
            monitoringActivityCode = "D003";
        } else {
            monitoringActivityCode = "D004";
        }
        perhitunganMonitoring.setMasterActivity(masterActivityFacade.find(monitoringActivityCode));

        // set amount
        BigDecimal monitoringCharge = BigDecimal.ZERO;
        for (Object[] o1 : masterTarifContFacade.findAmountByActivity(monitoringActivityCode)) {
            if (o1[1].equals("IDR")) {
                monitoringCharge = (BigDecimal) o1[0];
            }
        }

        discount = masterDiscountFacade.getValidDiscount(registration.getMasterCustomer().getCustCode(), monitoringActivityCode);
        discountAsPercentage = discount[0];
        discountAsMoney = discount[1];

        if (discountAsMoney == null) {
            discountAsMoney = monitoringCharge.multiply(discountAsPercentage);
        }

        perhitunganMonitoring.setCurrCode("IDR");
        perhitunganMonitoring.setCharge(monitoringCharge.subtract(discountAsMoney));
        BigDecimal totalMonitoringCharge = perhitunganMonitoring.getCharge().multiply(BigDecimal.valueOf(pluggingReeferService.getShiftPlanning()));
        perhitunganMonitoring.setTotalCharge(totalMonitoringCharge);
        perhitunganMonitoringFacade.edit(perhitunganMonitoring);

        viewData();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleSelect(ActionEvent event) {
        String no_cont = (String) event.getComponent().getAttributes().get("noCont");
        String idDS = pluggingReeferServiceFacade.findInvoice(no_cont, no_reg);
        pluggingReeferService = pluggingReeferServiceFacade.find(idDS);
        Integer idMon = perhitunganMonitoringFacade.findInvoicePlugging(no_cont, no_reg);
        perhitunganMonitoring = perhitunganMonitoringFacade.find(idMon);
        Integer idPlug = perhitunganPluggingFacade.findInvoicePlugging(no_cont, no_reg);
        perhitunganPlugging = perhitunganPluggingFacade.find(idPlug);
    }

    public void handleSubmit(ActionEvent event) {
        Boolean loggedIn = true;
        invoice.setNoReg(no_reg);
        getInvoice().setRegistration(registration);
        invoice.setPaymentStatus("UNPAID");
        invoice.setJumlahTagihan(total);
        invoice.setValidasiPrint("FALSE");

        //ambil nilai ppn dari master setting app
        MasterSettingApp settingApp = new MasterSettingApp();
        settingApp = masterSettingAppFacade.find("ppn");
        setPpnPrint(settingApp.getValueFloat());
        BigDecimal ppn = invoice.getJumlahTagihan().multiply(settingApp.getValueFloat());
        invoice.setPpn(ppn);

        MasterCurrency masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
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
        invoice.setMasterCurrency(masterCurrency);
        registration.setInvoice(invoice);

        if (invoice.getPaymentType().equalsIgnoreCase("CASH")) {
            getRegistration().setStatusService("confirm");
        } else if (invoice.getPaymentType().equalsIgnoreCase("KREDIT")) {
            getRegistration().setStatusService("approve");
        }
        registrationFacade.edit(registration);
        
        viewData();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleDelete(ActionEvent event) {
        pluggingReeferServiceFacade.remove(pluggingReeferService);
        perhitunganMonitoringFacade.remove(perhitunganMonitoring);
        perhitunganPluggingFacade.remove(perhitunganPlugging);
        viewData();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
    }

    public void handleDownloadTransactionRecap(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/delivery.pdf?no_reg=" + no_reg + "&total=" + String.valueOf(total) + "&ppn=" + String.valueOf(getPpnPrint()) + "&materai=" + String.valueOf(getMateraiPrint()) + "&userId=" + getUserId() + "&tipe=plugging")));
    }

    public void handleDownloadNota(ActionEvent event) {
        invoice = invoiceFacade.find(no_reg);
        //cek validasi print nota berulang-ulang
        if (invoice.getValidasiPrint().equals("FALSE")) {
            invoice = invoiceFacade.publishInvoice(invoice);
        }

        viewData();

        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/invoice.pdf?no_reg=" + no_reg + "&to=" + to + "&curr=" + country + "&tipe=" + registration.getMasterService().getServiceCode() + "&username=" + getUserId() + "&detail=" + String.valueOf(disDetail))));
    }

    public void handleDownloadDetail(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/detail.pdf?no_reg=" + no_reg + "&tipe=" + registration.getMasterService().getServiceCode() + "&username=" + getUserId() + "&ppn=" + String.valueOf(ppnPrint) + "&materai=" + String.valueOf(materaiPrint) + "&total=" + String.valueOf(total))));
    }

    public void handleCancelInvoice(ActionEvent event) {
        //input k batal nota
        registration = registrationFacade.find(invoice.getNoReg());
        batalNota.setCustCode(registration.getMasterCustomer().getCustCode());
        batalNota.setNoFakturPajak(invoice.getNoFakturPajak());
        final String noInvoice = invoice.getNoInvoice();
        batalNota.setNoInvoice(noInvoice);
        batalNota.setNoReg(invoice.getNoReg());
        batalNota.setPaymentDate(invoice.getPaymentDate());
        batalNota.setTotalTagihan(invoice.getTotalTagihan());

        for (Object[] o : pluggingReeferServiceFacade.findPluggingReeferServiceByReg(no_reg)) {
            pluggingReeferService = pluggingReeferServiceFacade.find(o[0]);
            pluggingReeferServiceFacade.remove(pluggingReeferService);
        }

        for (Object o : perhitunganPluggingFacade.findByReg(no_reg)) {
            perhitunganPlugging = perhitunganPluggingFacade.find(o);
            perhitunganPluggingFacade.remove(perhitunganPlugging);
        }

        for (Object o : perhitunganMonitoringFacade.findByReg(no_reg)) {
            perhitunganMonitoring = perhitunganMonitoringFacade.find(o);
            perhitunganMonitoringFacade.remove(perhitunganMonitoring);
        }

        //save ke database
        batalNotaFacade.create(batalNota);
        
        invoice.setCancelInvoice("TRUE");
        invoice.setNoFakturPajak(null);
        invoice.setNoInvoice(null);

        invoiceFacade.edit(invoice);

        if (masterSettingAppFacade.isPaymentBankingEnabled())
            try {
                bankingSyncFacadeRemote.cancelInvoice(noInvoice);
            } catch (RuntimeException e) {
                System.err.println(FacesHelper.getLocaleMessage("application.integration.banking.failed", FacesContext.getCurrentInstance()));
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.integration.banking.failed");
            }

        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.cancel.succeeded");
        viewData();
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
     * @return the pluggingReeferServices
     */
    public List<Object[]> getPluggingReeferServices() {
        return pluggingReeferServices;
    }

    /**
     * @param pluggingReeferServices the pluggingReeferServices to set
     */
    public void setPluggingReeferServices(List<Object[]> pluggingReeferServices) {
        this.pluggingReeferServices = pluggingReeferServices;
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

    public String getFormattedTotal() {
        NumberFormat numberFormat = new DecimalFormat("#,##0.00");
        return numberFormat.format(total).toString();
    }

    public String getFormattedPpn() {
        NumberFormat numberFormat = new DecimalFormat("#,##0.00");
        return numberFormat.format((total.multiply(ppnPrint))).toString();
    }

    public String getFormattedMaterai() {
        NumberFormat numberFormat = new DecimalFormat("#,##0.00");
        return numberFormat.format(materaiPrint).toString();
    }

    public String getFormattedJmlTotal() {
//        String local = null;
//        Locale trLocale = new Locale(language, country);
        NumberFormat numberFormat = new DecimalFormat("#,##0.00");
        return numberFormat.format((total.add((total.multiply(ppnPrint))))).toString();
    }

    /**
     * @return the masterContainerTypes
     */
    public List<Object[]> getMasterContainerTypes() {
        return masterContainerTypes;
    }

    /**
     * @param masterContainerTypes the masterContainerTypes to set
     */
    public void setMasterContainerTypes(List<Object[]> masterContainerTypes) {
        this.masterContainerTypes = masterContainerTypes;
    }

    /**
     * @return the pluggingReeferService
     */
    public PluggingReeferService getPluggingReeferService() {
        return pluggingReeferService;
    }

    /**
     * @param pluggingReeferService the pluggingReeferService to set
     */
    public void setPluggingReeferService(PluggingReeferService pluggingReeferService) {
        this.pluggingReeferService = pluggingReeferService;
    }

    public List<Object[]> getMasterCommoditys() {
        return masterCommoditys;
    }

    public void setMasterCommoditys(List<Object[]> masterCommoditys) {
        this.masterCommoditys = masterCommoditys;
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
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
}

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
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganLiftServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PluggingReeferServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PerhitunganLiftService;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PluggingReeferService;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterService;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.util.GrossConverter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author wulan
 */
@ManagedBean(name = "perhitunganPluggingReefer")
@ViewScoped
public class PerhitunganPluggingReefer implements Serializable {

    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacade;
    @EJB
    private RegistrationFacadeRemote registrationFacadeRemote;
    @EJB
    private InvoiceFacadeRemote invoiceFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;
    @EJB
    private PluggingReeferServiceFacadeRemote pluggingReeferServiceFacadeRemote;
    @EJB
    private PlanningContReceivingFacadeRemote planningContReceivingFacadeRemote;
    @EJB
    private ReceivingServiceFacadeRemote receivingServiceFacadeRemote;
    @EJB
    private PerhitunganLiftServiceFacadeRemote perhitunganLiftServiceFacadeRemote;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacadeRemote;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacadeRemote;
    @EJB
    private MasterDiscountFacadeRemote masterDiscountFacadeRemote;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacadeRemote;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacadeRemote;
    @EJB
    private ServiceGateReceivingFacadeRemote serviceGateReceivingFacadeRemote;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private BatalNotaFacadeRemote batalNotaFacadeRemote;
    @EJB
    private MasterSettingAppFacadeRemote  masterSettingAppFacade;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;


    private Registration registration;
    private List<Object[]> registrations;
    private String no_reg;
    private Invoice invoice;
    private PluggingReeferService pluggingReeferService;
    private String userId = null;
    private Boolean disKredit = false;
    private List<Object[]> masterContainerTypes, masterCommoditys;
    private MasterContainerType masterContainerType;
    private PerhitunganLiftService perhitunganLiftService;
    private ReceivingService receivingService;
    private PlanningContReceiving planningContReceiving;
    private String currency = null;
    private List<Object[]> pluggingServices;
    private String symbol;
    private Boolean disPrint = true;
    private Boolean disDetail = true;
    private Boolean disCancelInv = true;
    private String language = null;
    private String country = null;
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal ppnPrint = BigDecimal.ZERO;
    private BigDecimal materaiPrint = BigDecimal.ZERO;
    private String to;
    private BatalNota batalNota;
    private Boolean isSimpatReady;

    /** Creates a new instance of PerhitunganPluggingReefer */
    public PerhitunganPluggingReefer() {}

    @PostConstruct
    private void construct(){
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setMasterService(new MasterService());
        pluggingReeferService = new PluggingReeferService();
        pluggingReeferService.setMasterCommodity(new MasterCommodity());
        pluggingReeferService.setMasterContainerType(new MasterContainerType());
        invoice = new Invoice();
        planningContReceiving = new PlanningContReceiving();
        perhitunganLiftService = new PerhitunganLiftService();
        receivingService = new ReceivingService();
        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        userId = loginSessionBean.getName();
        masterContainerType = new MasterContainerType();
        registrations = registrationFacadeRemote.findRegistrationPluggingOnly();
        receivingService.setRegistration(new Registration());
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
    }

    public void onChangeConstraintType(ValueChangeEvent event) {
        int newItem = (Integer) event.getNewValue();
        masterContainerType = masterContainerTypeFacadeRemote.find(newItem);
        pluggingReeferService.setContSize(masterContainerType.getFeetMark());
        System.out.println(newItem);
    }

    public void handleAdd(ActionEvent event) {
        pluggingReeferService = new PluggingReeferService();
        pluggingReeferService.setMasterCommodity(new MasterCommodity());
        pluggingReeferService.setMasterContainerType(new MasterContainerType());
        planningContReceiving = new PlanningContReceiving();
        perhitunganLiftService = new PerhitunganLiftService();
        receivingService = new ReceivingService();
        masterContainerType = new MasterContainerType();
        receivingService.setRegistration(new Registration());

        registrations = registrationFacadeRemote.findRegistrationPluggingOnly();
        pluggingReeferService.setJobSlip(iDGeneratorFacade.generateJobSlipID("03"));
        masterContainerTypes = masterContainerTypeFacadeRemote.findReefer();
        masterCommoditys = masterCommodityFacadeRemote.findAllNative();
        pluggingReeferService.setContSize(Short.valueOf("20"));
    }

    public void handleSelect(ActionEvent event) {
        String no_cont = (String) event.getComponent().getAttributes().get("noCont");
        //String idDS = pluggingReeferServiceFacadeRemote.findInvoice(no_cont, no_reg);
        pluggingReeferService = pluggingReeferServiceFacadeRemote.find(no_cont);
    }

    public void handleDelete(ActionEvent event) {
        pluggingReeferServiceFacadeRemote.remove(pluggingReeferService);
        viewData();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
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

    public void viewData() {
        disCancelInv = Boolean.TRUE;
        pluggingServices = pluggingReeferServiceFacadeRemote.findByNoregPlugingReefer(no_reg);
        if (pluggingServices.size() > 10) {
            disDetail = false;
        } else {
            disDetail = true;
        }

        pluggingServices = pluggingReeferServiceFacadeRemote.findByNoregPlugingReefer(no_reg);
        BigDecimal t = BigDecimal.ZERO;
        for (Object[] o : pluggingServices) {
            this.symbol = (String) (o[11]);
            this.language = (String) (o[12]);
            this.country = (String) (o[13]);
            if (o[8] != null || o[10] != null) {
                t = t.add((BigDecimal) o[8]).add((BigDecimal) o[10]);
            }
        }
        setTotal(t);

        //ambil nilai ppn
        MasterSettingApp settingApp = new MasterSettingApp();
        settingApp = masterSettingAppFacadeRemote.find("ppn");
        setPpnPrint(settingApp.getValueFloat());

        //ambil nilai materai
        MasterCurrency masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        List<Object[]> materai = masterMateraiFacadeRemote.findByCurr(masterCurrency.getCurrCode());

        if (total.compareTo((BigDecimal) materai.get(1)[2]) == -1) { /* jumlah tagihan < nilai minimum */
            setMateraiPrint((BigDecimal) materai.get(0)[1]);
        } else if (total.compareTo((BigDecimal) materai.get(2)[2]) == -1) {
            setMateraiPrint((BigDecimal) materai.get(1)[1]);
        } else {
            setMateraiPrint((BigDecimal) materai.get(2)[1]);
        }


        invoice = invoiceFacadeRemote.find(no_reg);
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

            if (invoice.getNoInvoice() != null && invoice.isCancelInvoice().equals("FALSE")) {
                /*cek apakah container sudah masuk gate, apabila sudah maka invoice tidak dapat di cancel*/
                disCancelInv = Boolean.FALSE;
                for (Object[] ob : pluggingServices) {
                    pluggingReeferService = pluggingReeferServiceFacadeRemote.find(ob[0]);
                    if (serviceGateReceivingFacadeRemote.findByContPpkb(pluggingReeferService.getContNo(), pluggingReeferService.getNoPpkb()) == null) {
                        if (disCancelInv == false) {
                            disCancelInv = Boolean.FALSE;
                        }
                    } else {
                        disCancelInv = Boolean.TRUE;
                    }
                }
                /*akhir pengecekan container di gate*/
            }
        } else {
            invoice = new Invoice();
            invoice.setPaymentType("KREDIT");
            disPrint = true;
            disKredit = false;
        }
    }

    public void handleSave(ActionEvent event) {
        Boolean loggedIn = true;

        MasterContainerType mct = new MasterContainerType();
        mct = masterContainerTypeFacadeRemote.find(pluggingReeferService.getMasterContainerType().getContType());
        pluggingReeferService.setContSize(mct.getFeetMark());
        pluggingReeferService.setRegistration(registration);
        pluggingReeferService.setNoPpkb(registration.getNo_ppkb_plug());
        pluggingReeferService.setStatus("01");
        pluggingReeferServiceFacadeRemote.edit(pluggingReeferService);

        //INPUT DATA K TABEL PLANNING CONT RECEIVING
        String grossClass = GrossConverter.convert(pluggingReeferService.getContSize(), pluggingReeferService.getContGross());
        planningContReceiving.setContGross(pluggingReeferService.getContGross());
        planningContReceiving.setMasterContainerType(pluggingReeferService.getMasterContainerType());
        planningContReceiving.setContSize(pluggingReeferService.getContSize());
        planningContReceiving.setContStatus(pluggingReeferService.getContStatus());
        planningContReceiving.setOverSize(pluggingReeferService.getOverSize());
        planningContReceiving.setDg(pluggingReeferService.getDg());
        planningContReceiving.setDgLabel(pluggingReeferService.getDgLabel());
        planningContReceiving.setContNo(pluggingReeferService.getContNo());
        planningContReceiving.setMlo(pluggingReeferService.getMlo());
//        planningContReceiving.setDischPort(receivingService.getMasterPort().getPortCode());
//        planningContReceiving.setLoadPort(receivingService.getMasterPort1().getPortCode());
        planningContReceiving.setMasterCommodity(pluggingReeferService.getMasterCommodity());
        //planningContReceiving.setPlanningVessel(planningVesselFacadeRemote.find(getRegistrationObjek()[1]));
//        planningContReceiving.setPlanningVessel(registration.getPlanningVessel());
//        planningContReceiving.setIdConstrainPlanning((Integer) temp[0]);
        planningContReceiving.setIsGenerate("FALSE");
        planningContReceiving.setGrossClass(grossClass);
        planningContReceiving.setPosition("01");
        planningContReceiving.setIsCompleted("TRUE");
        planningContReceiving.setBlNo(pluggingReeferService.getBlNo());
        planningContReceivingFacadeRemote.edit(planningContReceiving);

        // input data k tabel reaceiving service
        receivingService.getRegistration().setNoReg(no_reg);
        receivingService.setJobSlip(pluggingReeferService.getJobSlip());
        receivingService.setContGross(pluggingReeferService.getContGross());
        receivingService.setMasterContainerType(pluggingReeferService.getMasterContainerType());
        receivingService.setContSize(pluggingReeferService.getContSize());
        receivingService.setContStatus(pluggingReeferService.getContStatus());
        receivingService.setOverSize(pluggingReeferService.getOverSize());
        receivingService.setDg(pluggingReeferService.getDg());
        receivingService.setDgLabel(pluggingReeferService.getDgLabel());
        receivingService.setContNo(pluggingReeferService.getContNo());
        receivingService.setMlo(pluggingReeferService.getMlo());
        receivingService.setMasterCommodity(pluggingReeferService.getMasterCommodity());
        receivingService.setBlNo(pluggingReeferService.getBlNo());
        //receivingService.setValidDate(planningVessel.getCloseRec());
        receivingService.setIsGenerate("FALSE");
        receivingService.setStatusCancelLoading("FALSE");
        receivingServiceFacadeRemote.edit(receivingService);
        
        String dangerousClass = null;
        if(receivingService.getMasterCommodity() != null && receivingService.getMasterCommodity().getMasterDangerousClass() != null)
            dangerousClass = receivingService.getMasterCommodity().getMasterDangerousClass().getDangerousClass();


        //INPUT DATA K TABEL PERHITUNGAN LIFT
        String loloActivityCode = masterActivityFacadeRemote.translateLoLoActivityCode(
                pluggingReeferService.getContStatus(), 
                pluggingReeferService.getContSize(), 
                pluggingReeferService.getOverSize().equals("TRUE") ? true : false, 
                pluggingReeferService.getDg().equals("TRUE"),
                pluggingReeferService.getDgLabel().equals("TRUE"),
                dangerousClass,"CC");
        perhitunganLiftService.setContNo(pluggingReeferService.getContNo());
        perhitunganLiftService.setMlo(pluggingReeferService.getMlo());
        perhitunganLiftService.setNoPpkb(pluggingReeferService.getNoPpkb());
        perhitunganLiftService.setBlNo(pluggingReeferService.getBlNo());
        perhitunganLiftService.setRegistration(pluggingReeferService.getRegistration());
        perhitunganLiftService.setMasterActivity(masterActivityFacadeRemote.find(loloActivityCode));
        // set amount berdasarkan tipe pelayaran
        BigDecimal cLiftD = BigDecimal.ZERO;
        BigDecimal cLiftI = BigDecimal.ZERO;

        for (Object[] o1 : masterTarifContFacadeRemote.findAmountByActivity(loloActivityCode)) {
            if (o1[1].equals("IDR")) {
                cLiftD = ((BigDecimal) (o1[0]));
            } else if (o1[1].equals("USD")) {
                cLiftI = ((BigDecimal) (o1[0]));
            }
        }

        BigDecimal loloCharge = BigDecimal.ZERO;
        loloCharge = cLiftD;
        setCurrency("IDR");

        BigDecimal[] discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), loloActivityCode);
        BigDecimal discountAsPercentage = discount[0];
        BigDecimal discountAsMoney = discount[1];
        BigDecimal totalLoloCharge = BigDecimal.ZERO;

        if (discountAsMoney == null) {
            discountAsMoney = loloCharge.multiply(discountAsPercentage);
        }

        loloCharge = loloCharge.subtract(discountAsMoney);

        totalLoloCharge = loloCharge;

        perhitunganLiftService.setCharge(totalLoloCharge);
        perhitunganLiftService.setCurrCode(currency);
        perhitunganLiftServiceFacadeRemote.edit(perhitunganLiftService);

        pluggingServices = pluggingReeferServiceFacadeRemote.findByNoregPlugingReefer(no_reg);
        viewData();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacadeRemote.find(no_reg);
        pluggingServices = pluggingReeferServiceFacadeRemote.findByNoregPlugingReefer(no_reg);
        viewData();
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
        settingApp = masterSettingAppFacadeRemote.find("ppn");
        setPpnPrint(settingApp.getValueFloat());
        BigDecimal ppn = invoice.getJumlahTagihan().multiply(settingApp.getValueFloat());
        invoice.setPpn(ppn);

        MasterCurrency masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        List<Object[]> materai = masterMateraiFacadeRemote.findByCurr(masterCurrency.getCurrCode());
        
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
        //invoiceFacade.edit(invoice);
        registration.setInvoice(invoice);

        if (invoice.getPaymentType().equalsIgnoreCase("CASH")) {
            getRegistration().setStatusService("confirm");
        } else if (invoice.getPaymentType().equalsIgnoreCase("KREDIT")) {
            getRegistration().setStatusService("approve");
        }
        registrationFacadeRemote.edit(registration);
        viewData();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleDownloadTransactionRecap(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/delivery.pdf?no_reg=" + no_reg + "&total=" + String.valueOf(total) + "&ppn=" + String.valueOf(getPpnPrint()) + "&materai=" + String.valueOf(getMateraiPrint()) + "&userId=" + getUserId() + "&tipe=plugging")));
    }

    public void handleDownloadNota(ActionEvent event) {
        invoice = invoiceFacadeRemote.find(no_reg);
        //cek validasi print nota berulang-ulang
        if (invoice.getValidasiPrint().equals("FALSE")) {
            invoice = invoiceFacadeRemote.publishInvoice(invoice);
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
        registration = registrationFacadeRemote.find(invoice.getNoReg());
        batalNota.setCustCode(registration.getMasterCustomer().getCustCode());
        batalNota.setNoFakturPajak(invoice.getNoFakturPajak());
        final String noInvoice = invoice.getNoInvoice();
        batalNota.setNoInvoice(noInvoice);
        batalNota.setNoReg(invoice.getNoReg());
        batalNota.setPaymentDate(invoice.getPaymentDate());
        batalNota.setTotalTagihan(invoice.getTotalTagihan());

        for (Object[] o : pluggingReeferServiceFacadeRemote.findPluggingReeferServiceByReg(no_reg)) {
            pluggingReeferService = pluggingReeferServiceFacadeRemote.find(o[0]);
            pluggingReeferServiceFacadeRemote.remove(pluggingReeferService);
        }

        batalNotaFacadeRemote.create(batalNota);
        
        invoice.setCancelInvoice("TRUE");
        invoice.setNoFakturPajak(null);
        invoice.setNoInvoice(null);

        invoiceFacadeRemote.edit(invoice);

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

    public String getUrl() {
        if (pluggingServices == null) {
            return "#";
        }
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        return context.getRequestScheme() + "://" + context.getRequestServerName() + ":" + context.getRequestServerPort() + context.getRequestContextPath() + "/delivery.pdf?"
                + "no_reg=" + no_reg + "&"
                + "total=" + String.valueOf(total) + "&"
                + "ppn=" + String.valueOf(getPpnPrint()) + "&"
                + "materai=" + String.valueOf(getMateraiPrint()) + "&"
                + "userId=" + getUserId() + "&"
                + "tipe=" + "plugging" + "";
    }

    public String getUrlNota() {
        if (pluggingServices == null) {
            return "#";
        }
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        return context.getRequestScheme() + "://" + context.getRequestServerName() + ":" + context.getRequestServerPort() + context.getRequestContextPath() + "/invoice.pdf?"
                + "no_reg=" + no_reg + "&"
                + "to=" + to + "&"
                + "curr=" + country + "&"
                + "tipe=" + registration.getMasterService().getServiceCode() + "&"
                + "username=" + getUserId() + "&"
                + "detail=" + String.valueOf(disDetail) + "";
    }

    public String getUrlDetail() {
        if (pluggingServices == null) {
            return "#";
        }
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        return context.getRequestScheme() + "://" + context.getRequestServerName() + ":" + context.getRequestServerPort() + context.getRequestContextPath() + "/detail.pdf?"
                + "no_reg=" + no_reg + "&"
                + "tipe=" + registration.getMasterService().getServiceCode() + "&"
                + "username=" + getUserId() + "";
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
        NumberFormat numberFormat = new DecimalFormat("#,##0.00");
        return numberFormat.format((total.add((total.multiply(ppnPrint))))).toString();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public String getNo_reg() {
        return no_reg;
    }

    public void setNo_reg(String no_reg) {
        this.no_reg = no_reg;
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

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public List<Object[]> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Object[]> registrations) {
        this.registrations = registrations;
    }

    public Boolean getDisKredit() {
        return disKredit;
    }

    public void setDisKredit(Boolean disKredit) {
        this.disKredit = disKredit;
    }

    public List<Object[]> getMasterCommoditys() {
        return masterCommoditys;
    }

    public void setMasterCommoditys(List<Object[]> masterCommoditys) {
        this.masterCommoditys = masterCommoditys;
    }

    public List<Object[]> getMasterContainerTypes() {
        return masterContainerTypes;
    }

    public void setMasterContainerTypes(List<Object[]> masterContainerTypes) {
        this.masterContainerTypes = masterContainerTypes;
    }

    public PluggingReeferService getPluggingReeferService() {
        return pluggingReeferService;
    }

    public void setPluggingReeferService(PluggingReeferService pluggingReeferService) {
        this.pluggingReeferService = pluggingReeferService;
    }

    public MasterContainerType getMasterContainerType() {
        return masterContainerType;
    }

    public void setMasterContainerType(MasterContainerType masterContainerType) {
        this.masterContainerType = masterContainerType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Object[]> getPluggingServices() {
        return pluggingServices;
    }

    public void setPluggingServices(List<Object[]> pluggingServices) {
        this.pluggingServices = pluggingServices;
    }

    public ReceivingService getReceivingService() {
        return receivingService;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setReceivingService(ReceivingService receivingService) {
        this.receivingService = receivingService;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Boolean getDisCancelInv() {
        return disCancelInv;
    }

    public void setDisCancelInv(Boolean disCancelInv) {
        this.disCancelInv = disCancelInv;
    }

    public Boolean getDisDetail() {
        return disDetail;
    }

    public void setDisDetail(Boolean disDetail) {
        this.disDetail = disDetail;
    }

    public Boolean getDisPrint() {
        return disPrint;
    }

    public void setDisPrint(Boolean disPrint) {
        this.disPrint = disPrint;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BatalNota getBatalNota() {
        return batalNota;
    }

    public void setBatalNota(BatalNota batalNota) {
        this.batalNota = batalNota;
    }

    public Boolean getIsSimpatReady() {
        return isSimpatReady;
    }
}

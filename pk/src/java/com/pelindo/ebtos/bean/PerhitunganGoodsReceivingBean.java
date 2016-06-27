/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.url.UrlHelper;
import com.qtasnim.util.IndonesianNumberConverter;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.BankingSyncFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.BatalNotaFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDiscountFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganLiftBarangFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingBarangServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PerhitunganLiftBarang;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ReceivingBarangService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
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
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.event.ValueChangeEvent;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "perhitunganGoodsReceivingBean")
@ViewScoped
public class PerhitunganGoodsReceivingBean implements Serializable {

    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacade;
    @EJB
    private InvoiceFacadeRemote invoiceFacadeRemote;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;
    @EJB
    private RegistrationFacadeRemote registrationFacadeRemote;
    @EJB
    private PerhitunganLiftBarangFacadeRemote perhitunganLiftBarangFacadeRemote;
    @EJB
    private ReceivingBarangServiceFacadeRemote receivingBarangServiceFacadeRemote;
    @EJB
    private BatalNotaFacadeRemote batalNotaFacadeRemote;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private MasterDiscountFacadeRemote masterDiscountFacadeRemote;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;

    private List<Object[]> registrations;
    private List<Object[]> masterCommodityList;
    private Registration registration;
    private PerhitunganLiftBarang perhitunganLiftBarang;
    private ReceivingBarangService receivingBarangService;
    private Invoice invoice;
    private List<Object[]> liftServices;
    private String no_reg;
    private BigDecimal total = BigDecimal.ZERO;
    private String mode;
    private String terbilang = null;
    private IndonesianNumberConverter converter;
    private String language = null;
    private String country = null;
    private MasterCommodity masterCommodity;
    private PlanningVessel planningVessel;
    private BigDecimal ppnPrint = BigDecimal.ZERO;
    private BigDecimal materaiPrint = BigDecimal.ZERO;
    private String userId = null;
    private String toinvoice;
    private Boolean disPrint = true;
    private Boolean disKredit = false;
    private Boolean disDetail = false;
    private String symbol;
    private BatalNota batalNota;
    private Boolean disCancelInv = true;
    private Boolean isSimpatReady;

    /** Creates a new instance of PerhitunganGoodsReceivingBean */
    public PerhitunganGoodsReceivingBean() {}

    @PostConstruct
    private void construct() {
        invoice = new Invoice();
        converter = new IndonesianNumberConverter();
        invoice.setPaymentType("KREDIT");
        this.language = "id";
        this.country = "ID";
        receivingBarangService = new ReceivingBarangService();
        registration = new Registration();
        receivingBarangService = new ReceivingBarangService();
        masterCommodity = new MasterCommodity();
        planningVessel = new PlanningVessel();
        perhitunganLiftBarang = new PerhitunganLiftBarang();
        batalNota = new BatalNota();

        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        userId = loginSessionBean.getName();

        registrations = registrationFacadeRemote.findRegistrationReceivingBarang();
        masterCommodityList = masterCommodityFacadeRemote.findMasterCommoditys();
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
    }

    public void handleCancel(ActionEvent event) {
        this.clear();
    }

    public void handleSelectRegistration(ActionEvent event) {
        setNo_reg((String) event.getComponent().getAttributes().get("noReg"));
        registration = registrationFacadeRemote.find(getNo_reg());
        liftServices = perhitunganLiftBarangFacadeRemote.findPerhitunganLiftBarangByList(registration.getNoReg());

        if (liftServices.size() > 10) {
            this.disDetail = false;
        } else {
            this.disDetail = true;
        }

        BigDecimal t = BigDecimal.ZERO;
        for (Object[] o : liftServices) {
            t = t.add((BigDecimal) (o[6]));
            this.language = (String) (o[9]);
            this.country = (String) (o[10]);
            this.symbol = (String) (o[8]);
            //t = t + (Integer) o[8];
        }
        setTotal(t);
        BigInteger tot = BigInteger.ZERO;
        tot = total.toBigInteger();
        String to = tot.toString();
        terbilang = converter.convertToWord(to);

        //ambil nilai ppn
        MasterSettingApp settingApp = new MasterSettingApp();
        settingApp = masterSettingAppFacade.find("ppn");
        setPpnPrint(settingApp.getValueFloat());

        //ambil nilai materai
        MasterCurrency masterCurrency = null;
        List<Object[]> materai = new ArrayList<Object[]>();
        if (registration.getPlanningVessel().getTipePelayaran().equals("d")) {
            masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        } else {
            masterCurrency = masterCurrencyFacadeRemote.getUSDCurrency();
        }
        materai = masterMateraiFacade.findByCurr(masterCurrency.getCurrCode());
        if (getTotal().compareTo((BigDecimal) materai.get(1)[2]) == -1) {
            //invoice.setMaterai((BigDecimal) materai.get(0)[1]);
            setMateraiPrint((BigDecimal) materai.get(0)[1]);
        } else {
            //invoice.setMaterai((BigDecimal) materai.get(1)[1]);
            setMateraiPrint((BigDecimal) materai.get(1)[1]);
        }

        invoice = invoiceFacadeRemote.find(no_reg);
        if (invoice != null) {
            BigDecimal ttinvoice = invoice.getTotalTagihan();
            BigInteger tottinvoice = ttinvoice.toBigInteger();
            if (invoice.getMasterCurrency().getCurrCode().equalsIgnoreCase("USD")) {
                toinvoice = ttinvoice.toString();
            } else {
                toinvoice = tottinvoice.toString();
            }

            disPrint = invoice.getValidasiPrint().equals("TRUE") ? true : false;
            if (invoice.getPaymentType().equalsIgnoreCase("CASH")) {
                disKredit = false;
            } else {
                disKredit = true;
            }
        } else {
            invoice = new Invoice();
            invoice.setPaymentType("KREDIT");
            disPrint = true;
            disKredit = true;
        }

        if (invoice.getNoInvoice() != null && invoice.isCancelInvoice().equals("FALSE")) {
            /*cek apakah container sudah masuk gate, apabila sudah maka invoice tidak dapat di cancel*/
            disCancelInv = Boolean.FALSE;
            /*akhir pengecekan container di gate*/
        } else {
            disCancelInv = Boolean.TRUE;
        }

    }

    public void clear() {
        receivingBarangService = new ReceivingBarangService();
        masterCommodity = new MasterCommodity();
        perhitunganLiftBarang = new PerhitunganLiftBarang();
    }

    public void handleAdd(ActionEvent event) {
        this.clear();
        receivingBarangService.setJobslip(iDGeneratorFacade.generateJobSlipID("18"));
    }

    public void handleDelete(ActionEvent event) {
        try {
            perhitunganLiftBarangFacadeRemote.remove(perhitunganLiftBarang);
            receivingBarangServiceFacadeRemote.remove(receivingBarangService);
            liftServices = perhitunganLiftBarangFacadeRemote.findPerhitunganLiftBarangByList(registration.getNoReg());
            BigDecimal t = BigDecimal.ZERO;
            for (Object[] o : liftServices) {
                t = t.add((BigDecimal) (o[6]));
                //t = t + (Integer) o[8];
                this.language = (String) (o[9]);
                this.country = (String) (o[10]);
                this.symbol = (String) (o[8]);
            }
            setTotal(t);
            BigInteger tot = BigInteger.ZERO;
            tot = total.toBigInteger();
            String to = tot.toString();
            terbilang = converter.convertToWord(to);

            //ambil nilai ppn
            MasterSettingApp settingApp = new MasterSettingApp();
            settingApp = masterSettingAppFacade.find("ppn");
            setPpnPrint(settingApp.getValueFloat());

            //ambil nilai materai
            String currCode = null;
            List<Object[]> materai = new ArrayList<Object[]>();
            if (registration.getPlanningVessel().getTipePelayaran().equals("d")) {
                currCode = "IDR";
            } else {
                currCode = "USD";
            }
            materai = masterMateraiFacade.findByCurr(currCode);
            if (getTotal().compareTo((BigDecimal) materai.get(1)[2]) == -1) {
                //invoice.setMaterai((BigDecimal) materai.get(0)[1]);
                setMateraiPrint((BigDecimal) materai.get(0)[1]);
            } else {
                //invoice.setMaterai((BigDecimal) materai.get(1)[1]);
                setMateraiPrint((BigDecimal) materai.get(1)[1]);
            }
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
        }
    }

    public void handleSelectEdit(ActionEvent event) {
        int id = (Integer) event.getComponent().getAttributes().get("noCont");
        perhitunganLiftBarang = perhitunganLiftBarangFacadeRemote.find(id);
        receivingBarangService = receivingBarangServiceFacadeRemote.find(perhitunganLiftBarang.getJobslip());
    }

    public void handleSelectCommodity(ActionEvent event) {
        String idHT = (String) event.getComponent().getAttributes().get("idOperatorCrane");
        masterCommodity = masterCommodityFacadeRemote.find(idHT);
        receivingBarangService.setCommodityCode(masterCommodity.getCommodityCode());
    }

    //funtion batal nota
    public void handleAddCancelInvoice(ActionEvent event) {
        batalNota = new BatalNota();
    }

    //FUNCTION BATAL NOTA
    public void handleSubmitBatalNota(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        try {
            List<Object[]> perhitunganGoodsList = null;
            loggedIn = true;
            //input k batal nota
            registration = registrationFacadeRemote.find(invoice.getNoReg());
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

            //find and update tabel-table relasi
            perhitunganGoodsList = receivingBarangServiceFacadeRemote.findByListBatalNotaService(registration.getPlanningVessel().getNoPpkb(), no_reg);
            if (!perhitunganGoodsList.isEmpty()) {
                for (int i = 0; i < perhitunganGoodsList.size(); i++) {
                    receivingBarangService = receivingBarangServiceFacadeRemote.find(perhitunganGoodsList.get(i)[0]);
                    System.out.println("test ID : " + perhitunganGoodsList.get(i)[0]);

                    Integer id = perhitunganLiftBarangFacadeRemote.findCancelInvoice(receivingBarangService.getJobslip(), registration.getPlanningVessel().getNoPpkb(), no_reg);
                    System.out.println("sdsd" + id);
                    perhitunganLiftBarang = perhitunganLiftBarangFacadeRemote.find(id);


                    perhitunganLiftBarangFacadeRemote.remove(perhitunganLiftBarang);
                    receivingBarangServiceFacadeRemote.remove(receivingBarangService);

                }
            }
            disCancelInv = Boolean.TRUE;
            liftServices = perhitunganLiftBarangFacadeRemote.findPerhitunganLiftBarangByList(registration.getNoReg());

            if (masterSettingAppFacade.isPaymentBankingEnabled())
                try {
                    bankingSyncFacadeRemote.cancelInvoice(noInvoice);
                } catch (RuntimeException e) {
                    System.err.println(FacesHelper.getLocaleMessage("application.integration.banking.failed", FacesContext.getCurrentInstance()));
                    FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.integration.banking.failed");
                }

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (EJBException e) {
            e.printStackTrace();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleDownloadTemp(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/perhitunganReceiving.pdf?mode=" + "goodsReceiving"
                + "&noRegistrasi=" + no_reg
                + "&status=" + invoice.getPaymentType()
                + "&ppn=" + String.valueOf(getPpnPrint())
                + "&materai=" + String.valueOf(getMateraiPrint())
                + "&userId=" + getUserId()
                + "&total=" + String.valueOf(total))));
    }

    public void handleDownloadNota(ActionEvent event) {
        invoice = invoiceFacadeRemote.find(no_reg);
        //validasi print
        if (invoice.getValidasiPrint().equals("FALSE")) {
            invoice = invoiceFacadeRemote.publishInvoice(invoice);
        }


        if (invoice.getNoInvoice() != null && invoice.isCancelInvoice().equals("FALSE")) {
            /*cek apakah container sudah masuk gate, apabila sudah maka invoice tidak dapat di cancel*/
            disCancelInv = Boolean.FALSE;
            /*akhir pengecekan container di gate*/
        } else {
            disCancelInv = Boolean.TRUE;
        }


        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/invoice.pdf?tipe=" + registration.getMasterService().getServiceCode()
                + "&no_reg=" + no_reg
                + "&detail=" + String.valueOf(disDetail)
                + "&to=" + toinvoice
                + "&curr=" + country
                + "&username=" + getUserId())));

        disPrint = invoice.getValidasiPrint().equals("TRUE") ? true : false;
    }

    public void handleDownloadDetail(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/detail.pdf?tipe=" + registration.getMasterService().getServiceCode()
                + "&no_reg=" + no_reg
                + "&username=" + getUserId() + "&ppn=" + String.valueOf(ppnPrint) + "&materai=" + String.valueOf(materaiPrint) + "&total=" + String.valueOf(total))));
    }

    public String getUrlDetail() {
        if (liftServices == null) {
            return "#";
        }
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        return context.getRequestScheme() + "://" + context.getRequestServerName() + ":" + context.getRequestServerPort() + context.getRequestContextPath() + "/detail.pdf?"
                + "no_reg=" + no_reg + "&"
                + "tipe=" + registration.getMasterService().getServiceCode() + "&"
                + "username=" + getUserId() + "";
    }

    public String getUrlNota() {
        if (liftServices == null) {
            return "#";
        }
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        return context.getRequestScheme() + "://" + context.getRequestServerName() + ":" + context.getRequestServerPort() + context.getRequestContextPath() + "/invoice.pdf?"
                + "no_reg=" + no_reg + "&"
                + "detail=" + String.valueOf(disDetail) + "&"
                + "to=" + toinvoice + "&"
                + "curr=" + country + "&"
                + "tipe=" + registration.getMasterService().getServiceCode() + "&"
                + "username=" + getUserId() + "";
    }

    public String getUrlReceiving() {
        if (liftServices == null) {
            return "#";
        }

        setMode("goodsReceiving");
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/perhitunganReceiving.pdf?"
                + "mode=" + getMode() + "&"
                + "noRegistrasi=" + no_reg + "&"
                + "status=" + invoice.getPaymentType() + "&"
                + "ppn=" + String.valueOf(getPpnPrint()) + "&"
                + "materai=" + String.valueOf(getMateraiPrint()) + "&"
                + "userId=" + getUserId() + "&"
                + "terbilang=" + terbilang + "&"
                + "total=" + String.valueOf(total) + "";
    }

    public void handleSubmit(ActionEvent event) {
        String status;
        Boolean loggedIn = true;
        invoice.setNoReg(no_reg);
        invoice.setRegistration(registration);
        invoice.setPaymentStatus("UNPAID");
        invoice.setJumlahTagihan(total);

        //ambil nilai ppn dari master setting app
        MasterSettingApp settingApp = new MasterSettingApp();
        settingApp = masterSettingAppFacade.find("ppn");
        setPpnPrint(settingApp.getValueFloat());
        BigDecimal ppn = invoice.getJumlahTagihan().multiply(settingApp.getValueFloat());
        invoice.setPpn(ppn);

        MasterCurrency masterCurrency = null;
        List<Object[]> materai = new ArrayList<Object[]>();
        if (registration.getPlanningVessel().getTipePelayaran().equals("d")) {
            masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        } else {
            masterCurrency = masterCurrencyFacadeRemote.getUSDCurrency();
        }
        materai = masterMateraiFacade.findByCurr(masterCurrency.getCurrCode());
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

        //invoiceFacadeRemote.create(invoice);
        registration.setInvoice(invoice);

        if (invoice.getPaymentType().equals("KREDIT")) {
            status = "approve";
            disKredit = true;
        } else {
            status = "confirm";
            disKredit = false;
        }

        registration.setStatusService(status);
        registrationFacadeRemote.edit(registration);

        liftServices = perhitunganLiftBarangFacadeRemote.findPerhitunganLiftBarangByList(registration.getNoReg());
        BigDecimal t = BigDecimal.ZERO;
        for (Object[] o : liftServices) {
            t = t.add((BigDecimal) (o[6]));
            //t = t + (Integer) o[8];
            this.language = (String) (o[9]);
            this.country = (String) (o[10]);
            this.symbol = (String) (o[8]);
        }
        setTotal(t);

        if (liftServices.size() > 10) {
            this.disDetail = false;
        } else {
            this.disDetail = true;
        }

        if (invoice != null) {
            BigDecimal ttinvoice = invoice.getTotalTagihan();
            BigInteger tottinvoice = ttinvoice.toBigInteger();
            if (invoice.getMasterCurrency().getCurrCode().equalsIgnoreCase("USD")) {
                toinvoice = ttinvoice.toString();
            } else {
                toinvoice = tottinvoice.toString();
            }
            disPrint = invoice.getValidasiPrint().equals("TRUE") ? true : false;
        }

        if (invoice.getNoInvoice() != null && invoice.isCancelInvoice().equals("FALSE")) {
            /*cek apakah container sudah masuk gate, apabila sudah maka invoice tidak dapat di cancel*/
            disCancelInv = Boolean.FALSE;
            /*akhir pengecekan container di gate*/
        } else {
            disCancelInv = Boolean.TRUE;
        }

        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleConfirm(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        BigDecimal chargeGoodsLolo = null;
        String jasa = null;
        String goodsLoloActivityCode = null;
        if (receivingBarangService.getContNo() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (receivingBarangService.getVolume() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (receivingBarangService.getWeight() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (receivingBarangService.getUnit() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (receivingBarangService.getCommodityCode() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else {
            loggedIn = true;
            //no_ppkb =registration.getPlanningVessel().getNoPpkb();
            setPlanningVessel(planningVesselFacadeRemote.find(registration.getPlanningVessel().getNoPpkb()));

            receivingBarangService.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
            receivingBarangService.setNoReg(registration.getNoReg());
            receivingBarangService.setValidDate(planningVessel.getCloseRec());
            //receivingBarangService.setCommodityCode(masterCommodity.getCommodityCode());
            receivingBarangServiceFacadeRemote.edit(receivingBarangService);
            if (receivingBarangService.getCountBy().equalsIgnoreCase("WEIGHT")) {
                goodsLoloActivityCode = "N001";
            } else {
                goodsLoloActivityCode = "N002";
            }
            if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("d")) {
                chargeGoodsLolo = masterTarifContFacadeRemote.findByCurrCodeAndActivity("IDR", goodsLoloActivityCode);
                jasa = "IDR";
            } else {
                chargeGoodsLolo = masterTarifContFacadeRemote.findByCurrCodeAndActivity("USD", goodsLoloActivityCode);
                jasa = "USD";
            }
            perhitunganLiftBarang.setNoReg(receivingBarangService.getNoReg());
            perhitunganLiftBarang.setBlNo(receivingBarangService.getBlNo());
            perhitunganLiftBarang.setContNo(receivingBarangService.getContNo());
            perhitunganLiftBarang.setMlo(receivingBarangService.getMlo());
            perhitunganLiftBarang.setNoPpkb(receivingBarangService.getNoPpkb());
            perhitunganLiftBarang.setCommodityCode(receivingBarangService.getCommodityCode());
            perhitunganLiftBarang.setJobslip(receivingBarangService.getJobslip());
            perhitunganLiftBarang.setOperation("RECEIVING");
            perhitunganLiftBarang.setCurrCode(jasa);
            perhitunganLiftBarang.setActivityCode(goodsLoloActivityCode);

            BigDecimal[] discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), goodsLoloActivityCode);
            BigDecimal discountAsPercentage = discount[0];
            BigDecimal discountAsMoney = discount[1];
            
            if (discountAsMoney == null) {
                discountAsMoney = chargeGoodsLolo.multiply(discountAsPercentage);
            }

            perhitunganLiftBarang.setChargeEquipmet(chargeGoodsLolo.subtract(discountAsMoney));
            perhitunganLiftBarangFacadeRemote.edit(perhitunganLiftBarang);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            liftServices = perhitunganLiftBarangFacadeRemote.findPerhitunganLiftBarangByList(registration.getNoReg());
            BigDecimal t = BigDecimal.ZERO;
            for (Object[] o : liftServices) {
                t = t.add((BigDecimal) (o[6]));
                //t = t + (Integer) o[8];
                this.language = (String) (o[9]);
                this.country = (String) (o[10]);
                this.symbol = (String) (o[8]);
            }
            setTotal(t);
            this.clear();
        }
        context.addCallbackParam("loggedIn", loggedIn);
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

//    public String getFormattedTotal() {
//        String local = null;
//        Locale trLocale = new Locale(language, country);
//        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(trLocale);
//        return local = decimalFormat.format(total).toString();
//    }
    public String getFormattedTotal() {
        NumberFormat numberFormat = new DecimalFormat("#,##0.00");
        if (invoice.getPaymentType().equals("KREDIT")) {
            return numberFormat.format(0).toString();
        } else {
            return numberFormat.format(total).toString();
        }
    }

    public String getFormattedPpn() {
        NumberFormat numberFormat = new DecimalFormat("#,##0.00");
        if (invoice.getPaymentType().equals("KREDIT")) {
            return numberFormat.format(0).toString();
        } else {
            return numberFormat.format((total.multiply(ppnPrint))).toString();
        }
    }

    public String getFormattedMaterai() {
        NumberFormat numberFormat = new DecimalFormat("#,##0.00");
        if (invoice.getPaymentType().equals("KREDIT")) {
            return numberFormat.format(0).toString();
        } else {
            return numberFormat.format(materaiPrint).toString();
        }
    }

    public String getFormattedJmlTotal() {
//        String local = null;
//        Locale trLocale = new Locale(language, country);
        NumberFormat numberFormat = new DecimalFormat("#,##0.00");
        if (invoice.getPaymentType().equals("KREDIT")) {
            return numberFormat.format(0).toString();
        } else {
            return numberFormat.format((total.add((total.multiply(ppnPrint))))).toString();
        }
    }

    /**
     * @return the masterCommodityList
     */
    public List<Object[]> getMasterCommodityList() {
        return masterCommodityList;
    }

    /**
     * @param masterCommodityList the masterCommodityList to set
     */
    public void setMasterCommodityList(List<Object[]> masterCommodityList) {
        this.masterCommodityList = masterCommodityList;
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
     * @return the liftServices
     */
    public List<Object[]> getLiftServices() {
        return liftServices;
    }

    /**
     * @param liftServices the liftServices to set
     */
    public void setLiftServices(List<Object[]> liftServices) {
        this.liftServices = liftServices;
    }

    /**
     * @return the no_reg
     */
    public String getNo_reg() {
        return no_reg;
    }

    /**
     * @param no_reg the no_reg to set
     */
    public void setNo_reg(String no_reg) {
        this.no_reg = no_reg;
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

    /**
     * @return the terbilang
     */
    public String getTerbilang() {
        return terbilang;
    }

    /**
     * @param terbilang the terbilang to set
     */
    public void setTerbilang(String terbilang) {
        this.terbilang = terbilang;
    }

    /**
     * @return the converter
     */
    public IndonesianNumberConverter getConverter() {
        return converter;
    }

    /**
     * @param converter the converter to set
     */
    public void setConverter(IndonesianNumberConverter converter) {
        this.converter = converter;
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
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    public void setMasterCommodity(MasterCommodity masterCommodity) {
        this.masterCommodity = masterCommodity;
    }

    public PerhitunganLiftBarang getPerhitunganLiftBarang() {
        return perhitunganLiftBarang;
    }

    public void setPerhitunganLiftBarang(PerhitunganLiftBarang perhitunganLiftBarang) {
        this.perhitunganLiftBarang = perhitunganLiftBarang;
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    public ReceivingBarangService getReceivingBarangService() {
        return receivingBarangService;
    }

    public void setReceivingBarangService(ReceivingBarangService receivingBarangService) {
        this.receivingBarangService = receivingBarangService;
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

    public Boolean getDisDetail() {
        return disDetail;
    }

    public void setDisDetail(Boolean disDetail) {
        this.disDetail = disDetail;
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

    public String getToinvoice() {
        return toinvoice;
    }

    public void setToinvoice(String toinvoice) {
        this.toinvoice = toinvoice;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
}

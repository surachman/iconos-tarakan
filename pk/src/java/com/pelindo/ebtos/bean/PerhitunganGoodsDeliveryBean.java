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
import com.pelindo.ebtos.ejb.facade.remote.BatalNotaFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.DeliveryBarangServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDiscountFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganLiftBarangFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanBarangFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceCfsStrippingFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.DeliveryBarangService;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PerhitunganLiftBarang;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukanBarang;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceCfsStripping;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "perhitunganGoodsDeliveryBean")
@ViewScoped
public class PerhitunganGoodsDeliveryBean implements Serializable {

    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacade;
    @EJB
    private BatalNotaFacadeRemote batalNotaFacade;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacade;
    @EJB
    private ServiceCfsStrippingFacadeRemote serviceCfsStrippingFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacade;
    @EJB
    private InvoiceFacadeRemote invoiceFacade;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private PerhitunganPenumpukanBarangFacadeRemote perhitunganPenumpukanBarangFacade;
    @EJB
    private PerhitunganLiftBarangFacadeRemote perhitunganLiftBarangFacade;
    @EJB
    private DeliveryBarangServiceFacadeRemote deliveryBarangServiceFacade;
    @EJB
    private RegistrationFacadeRemote registrationFacade;
    @EJB
    private MasterDiscountFacadeRemote masterDiscountFacade;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;

    private List<Object[]> registrations;
    private List<Object[]> deliveryBarangServices;
    private List<Object[]> serviceCfsStrippings;
    private List<Object[]> serviceCfsStrippingsBL;
    private Registration registration;
    private DeliveryBarangService deliveryBarangService;
    private PerhitunganLiftBarang lift;
    private PerhitunganPenumpukanBarang tumpuk;
    private BatalNota batalNota;
    private Invoice invoice;
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

    /** Creates a new instance of PerhitunganGoodsDeliveryBean */
    public PerhitunganGoodsDeliveryBean() {}

    @PostConstruct
    private void construct() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        invoice = new Invoice();
        deliveryBarangService = new DeliveryBarangService();
        lift = new PerhitunganLiftBarang();
        tumpuk = new PerhitunganPenumpukanBarang();
        this.language = "id";
        this.country = "ID";

        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        userId = loginSessionBean.getName();
        batalNota = new BatalNota();

        registrations = registrationFacade.findRegistrationDeliveryBarang();
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
    }

    public void viewData() {
        if (no_reg != null) {
            disCancelInv = Boolean.TRUE;
            registration = registrationFacade.find(no_reg);
            deliveryBarangServices = deliveryBarangServiceFacade.findPerhitungan(no_reg);
            if (deliveryBarangServices.size() > 10) {
                disDetail = false;
            } else {
                disDetail = true;
            }

            BigDecimal t = BigDecimal.ZERO;
            for (Object[] o : deliveryBarangServices) {
                this.symbol = (String) (o[14]);
                this.language = (String) (o[15]);
                this.country = (String) (o[16]);
                t = t.add((BigDecimal) o[10]).add((BigDecimal) o[11]).add((BigDecimal) o[12]).add((BigDecimal) o[13]);
            }
            total = t;

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

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        viewData();
        serviceCfsStrippings = serviceCfsStrippingFacade.findForDeliveryGoods();
    }

    public void handleAdd(ActionEvent event) {
        deliveryBarangService = new DeliveryBarangService();
        deliveryBarangService.setJobslip(iDGeneratorFacade.generateJobSlipID("19"));
        deliveryBarangService.setCountBy("weight");
    }

    public void handleSelectContPick(ActionEvent event) {
        String id_cont = (String) event.getComponent().getAttributes().get("idCont");
        deliveryBarangService.setContNo(id_cont);
        serviceCfsStrippingsBL = serviceCfsStrippingFacade.findBL(id_cont);
    }

    public void handleSelectBL(ActionEvent event) {
        Integer id_bl = (Integer) event.getComponent().getAttributes().get("idBl");
        ServiceCfsStripping scd = serviceCfsStrippingFacade.find(id_bl);
        deliveryBarangService.setNoPpkb(scd.getNoPpkb());
        deliveryBarangService.setBlNo(scd.getBlNo());
        deliveryBarangService.setCommodityCode(scd.getCommodityCode());
        deliveryBarangService.setUnit(scd.getUnit());
        deliveryBarangService.setWeight(scd.getWeight());
        deliveryBarangService.setVolume(scd.getVolume());
        deliveryBarangService.setPlacementDate(scd.getPlacementDate());
    }

    public void handleConfirm(ActionEvent event) {
        boolean loggedIn = false;
        if (deliveryBarangService.getContNo() == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.transaction.notcompleted");
        } else {
            Calendar now = Calendar.getInstance();
            if (now.getTime().getHours() > deliveryBarangService.getPlacementDate().getHours()) {
                now.add(Calendar.DATE, 1);
                now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), deliveryBarangService.getPlacementDate().getHours(), deliveryBarangService.getPlacementDate().getMinutes(), deliveryBarangService.getPlacementDate().getSeconds());
                deliveryBarangService.setValidDate(now.getTime());
            } else if (now.getTime().getHours() == deliveryBarangService.getPlacementDate().getHours()) {
                if (now.getTime().getMinutes() > deliveryBarangService.getPlacementDate().getMinutes()) {
                    now.add(Calendar.DATE, 1);
                    now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), deliveryBarangService.getPlacementDate().getHours(), deliveryBarangService.getPlacementDate().getMinutes(), deliveryBarangService.getPlacementDate().getSeconds());
                    deliveryBarangService.setValidDate(now.getTime());
                } else if (now.getTime().getMinutes() == deliveryBarangService.getPlacementDate().getMinutes()) {
                    if (now.getTime().getSeconds() > deliveryBarangService.getPlacementDate().getSeconds()) {
                        now.add(Calendar.DATE, 1);
                        now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), deliveryBarangService.getPlacementDate().getHours(), deliveryBarangService.getPlacementDate().getMinutes(), deliveryBarangService.getPlacementDate().getSeconds());
                        deliveryBarangService.setValidDate(now.getTime());
                    } else if (now.getTime().getSeconds() == deliveryBarangService.getPlacementDate().getSeconds()) {
                        now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), deliveryBarangService.getPlacementDate().getHours(), deliveryBarangService.getPlacementDate().getMinutes(), deliveryBarangService.getPlacementDate().getSeconds());
                        deliveryBarangService.setValidDate(now.getTime());
                    }
                }
            } else {
                now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), deliveryBarangService.getPlacementDate().getHours(), deliveryBarangService.getPlacementDate().getMinutes(), deliveryBarangService.getPlacementDate().getSeconds());
                deliveryBarangService.setValidDate(now.getTime());
            }
            
            int min = DateCalculator.countRangeInDays(now.getTime(), deliveryBarangService.getPlacementDate()) + 1;
            int masaFree = masterSettingAppFacade.getMasa1FreeRange();
            int masa1Range = masterSettingAppFacade.getMasa1GoodsRange();
            int m1 = min - (masaFree - 1);

            if (min < (masa1Range + 1)) {
                if (min <= masaFree) {
                    deliveryBarangService.setMasa1(1);
                } else {
                    deliveryBarangService.setMasa1(m1);
                }
                deliveryBarangService.setMasa2(0);
            } else {
                Integer masa2 = min - masa1Range;
                Integer masa1 = m1 - masa2;
                deliveryBarangService.setMasa1(masa1);
                deliveryBarangService.setMasa2(masa2);
            }

            deliveryBarangService.setNoReg(no_reg);
            deliveryBarangServiceFacade.edit(deliveryBarangService);
            loggedIn = true;

            String loloActivityCode = "";
            String penumpukanActivityCode = "";
            if (deliveryBarangService.getCountBy().equalsIgnoreCase("weight")) {
                loloActivityCode = "N001";
                penumpukanActivityCode = "M001";
            } else {
                loloActivityCode = "N002";
                penumpukanActivityCode = "M002";
            }

            lift.setNoReg(no_reg);
            lift.setNoPpkb(getRegistration().getPlanningVessel().getNoPpkb());
            lift.setContNo(deliveryBarangService.getContNo());
            lift.setMlo(deliveryBarangService.getMlo());
            lift.setJobslip(deliveryBarangService.getJobslip());
            lift.setCommodityCode(deliveryBarangService.getCommodityCode());
            lift.setBlNo(deliveryBarangService.getBlNo());
            lift.setOperation("DELIVERY");
            lift.setActivityCode(loloActivityCode);

            // set amount berdasarkan tipe pelayaran
            BigDecimal cLiftD = BigDecimal.ZERO;
            BigDecimal cLiftI = BigDecimal.ZERO;
            for (Object[] o1 : masterTarifContFacade.findAmountByActivity(loloActivityCode)) {
                if (o1[1].equals("IDR")) {
                    cLiftD = (BigDecimal) o1[0];
                } else {
                    cLiftI = (BigDecimal) o1[0];
                }
                lift.setCurrCode((String) o1[1]);
            }

            tumpuk.setNoReg(getRegistration().getNoReg());
            tumpuk.setNoPpkb(getRegistration().getPlanningVessel().getNoPpkb());
            tumpuk.setContNo(deliveryBarangService.getContNo());
            tumpuk.setMlo(deliveryBarangService.getMlo());
            tumpuk.setJobslip(deliveryBarangService.getJobslip());
            tumpuk.setCommodityCode(deliveryBarangService.getCommodityCode());
            tumpuk.setBlNo(deliveryBarangService.getBlNo());
            tumpuk.setOperation("DELIVERY");
            tumpuk.setActivityCode(penumpukanActivityCode);

            // set charge dan jasa dermaga berdasarkan tipe pelayaran
            BigDecimal cTumpukD = BigDecimal.ZERO;
            BigDecimal cTumpukI = BigDecimal.ZERO;
            BigDecimal jasaDerD = BigDecimal.ZERO;
            BigDecimal jasaDerI = BigDecimal.ZERO;

            for (Object[] o1 : masterTarifContFacade.findAmountByActivity(penumpukanActivityCode)) {
                if (o1[1].equals("IDR")) {
                    cTumpukD = (BigDecimal) o1[0];
                } else {
                    cTumpukI = (BigDecimal) o1[0];
                }
                tumpuk.setCurrCode((String) o1[1]);
            }

            for (Object[] o1 : masterTarifContFacade.findAmountByActivity("J001")) {
                if (o1[1].equals("IDR")) {
                    jasaDerD = (BigDecimal) o1[0];
                } else {
                    jasaDerI = (BigDecimal) o1[0];
                }
            }

            BigDecimal penumpukanCharge = BigDecimal.ZERO;
            BigDecimal chargeJasaDermaga = BigDecimal.ZERO;
            BigDecimal loloCharge = BigDecimal.ZERO;

            if (getRegistration().getPlanningVessel().getTipePelayaran().equals("d")) {
                penumpukanCharge = cTumpukD;
                chargeJasaDermaga = jasaDerD;
                loloCharge = cLiftD;
                lift.setCurrCode("IDR");
                tumpuk.setCurrCode("IDR");
            } else {
                penumpukanCharge = cTumpukI;
                chargeJasaDermaga = jasaDerI;
                loloCharge = cLiftI;
                lift.setCurrCode("USD");
                tumpuk.setCurrCode("USD");
            }

            BigDecimal[] discount = masterDiscountFacade.getValidDiscount(registration.getMasterCustomer().getCustCode(), loloActivityCode);
            BigDecimal discountAsPercentage = discount[0];
            BigDecimal discountAsMoney = discount[1];

            if (discountAsMoney == null) {
                discountAsMoney = loloCharge.multiply(discountAsPercentage);
            }

            lift.setChargeEquipmet(loloCharge.subtract(discountAsMoney));

            discount = masterDiscountFacade.getValidDiscount(registration.getMasterCustomer().getCustCode(), penumpukanActivityCode);
            discountAsPercentage = discount[0];
            discountAsMoney = discount[1];

            if (discountAsMoney == null) {
                discountAsMoney = penumpukanCharge.multiply(discountAsPercentage);
            }

            tumpuk.setChargePenumpukan(penumpukanCharge.subtract(discountAsMoney));
            tumpuk.setChargeMasa1(tumpuk.getChargePenumpukan().multiply(BigDecimal.valueOf(deliveryBarangService.getMasa1())));
            tumpuk.setChargeMasa2(tumpuk.getChargePenumpukan().multiply(BigDecimal.valueOf(deliveryBarangService.getMasa2())).multiply(BigDecimal.valueOf(2)));
            tumpuk.setJasaDermaga(chargeJasaDermaga);
            BigDecimal totalChargePenumpukan = tumpuk.getChargeMasa1().add(tumpuk.getChargeMasa2()).add(tumpuk.getJasaDermaga());
            tumpuk.setTotalCharge(totalChargePenumpukan);

            perhitunganPenumpukanBarangFacade.edit(tumpuk);
            perhitunganLiftBarangFacade.edit(lift);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
            viewData();
            serviceCfsStrippings = serviceCfsStrippingFacade.findForDeliveryGoods();
        }
    }

    public void handleSubmit(ActionEvent event) {
        Boolean loggedIn = true;
        invoice.setNoReg(no_reg);
        invoice.setRegistration(registration);
        invoice.setPaymentStatus("UNPAID");
        invoice.setJumlahTagihan(total);
        invoice.setValidasiPrint("FALSE");

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
        //invoiceFacade.edit(invoice);
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

    public void handleSelect(ActionEvent event) {
        String job_slip = (String) event.getComponent().getAttributes().get("jobSlip");
        String no_cont = (String) event.getComponent().getAttributes().get("noCont");
        deliveryBarangService = deliveryBarangServiceFacade.find(job_slip);
        Object[] idLift = perhitunganLiftBarangFacade.findPerhitunganLiftBarangByEdit(job_slip, no_cont);
        lift = perhitunganLiftBarangFacade.find(idLift[0]);
        Object[] idTump = perhitunganPenumpukanBarangFacade.findPerhitunganPenumpukanBarangByEdit(job_slip, no_cont);
        tumpuk = perhitunganPenumpukanBarangFacade.find(idTump[0]);
    }

    public void handleDelete(ActionEvent event) {
        deliveryBarangServiceFacade.remove(deliveryBarangService);
        perhitunganLiftBarangFacade.remove(lift);
        perhitunganPenumpukanBarangFacade.remove(tumpuk);
        viewData();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
    }

    public void handleDownloadTransactionRecap(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/delivery.pdf?no_reg=" + no_reg + "&total=" + String.valueOf(total) + "&ppn=" + String.valueOf(getPpnPrint()) + "&materai=" + String.valueOf(getMateraiPrint()) + "&userId=" + getUserId() + "&tipe=deliveryBarang")));
    }

    public void handleDownloadNota(ActionEvent event) {
        invoice = invoiceFacade.find(no_reg);

        //validasi print nota invoice
        if (invoice.getValidasiPrint().equals("FALSE")) {
            invoice = invoiceFacade.publishInvoice(invoice);
        }

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

        for (Object[] o : deliveryBarangServiceFacade.findByReg(no_reg)) {
            deliveryBarangService = deliveryBarangServiceFacade.find(o[0]);
            deliveryBarangServiceFacade.remove(deliveryBarangService);
        }

        for (Object o : perhitunganLiftBarangFacade.findByReg(no_reg)) {
            lift = perhitunganLiftBarangFacade.find(o);
            perhitunganLiftBarangFacade.remove(lift);
        }

        for (Object o : perhitunganPenumpukanBarangFacade.findByReg(no_reg)) {
            tumpuk = perhitunganPenumpukanBarangFacade.find(o);
            perhitunganPenumpukanBarangFacade.remove(tumpuk);
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

    public String getUrl() {
        if (deliveryBarangServices == null) {
            return "#";
        }
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        return context.getRequestScheme() + "://" + context.getRequestServerName() + ":" + context.getRequestServerPort() + context.getRequestContextPath() + "/delivery.pdf?"
                + "no_reg=" + no_reg + "&"
                + "total=" + String.valueOf(total) + "&"
                + "ppn=" + String.valueOf(getPpnPrint()) + "&"
                + "materai=" + String.valueOf(getMateraiPrint()) + "&"
                + "userId=" + getUserId() + "&"
                + "tipe=" + "deliveryBarang" + "";
    }

    public String getUrlNota() {
        if (deliveryBarangServices == null) {
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
        if (deliveryBarangServices == null) {
            return "#";
        }
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        return context.getRequestScheme() + "://" + context.getRequestServerName() + ":" + context.getRequestServerPort() + context.getRequestContextPath() + "/detail.pdf?"
                + "no_reg=" + no_reg + "&"
                + "tipe=" + registration.getMasterService().getServiceCode() + "&"
                + "username=" + getUserId() + "";
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
     * @return the deliveryBarangServices
     */
    public List<Object[]> getDeliveryBarangServices() {
        return deliveryBarangServices;
    }

    /**
     * @param deliveryBarangServices the deliveryBarangServices to set
     */
    public void setDeliveryBarangServices(List<Object[]> deliveryBarangServices) {
        this.deliveryBarangServices = deliveryBarangServices;
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
     * @return the deliveryBarangService
     */
    public DeliveryBarangService getDeliveryBarangService() {
        return deliveryBarangService;
    }

    /**
     * @param deliveryBarangService the deliveryBarangService to set
     */
    public void setDeliveryBarangService(DeliveryBarangService deliveryBarangService) {
        this.deliveryBarangService = deliveryBarangService;
    }

    /**
     * @return the lift
     */
    public PerhitunganLiftBarang getLift() {
        return lift;
    }

    /**
     * @param lift the lift to set
     */
    public void setLift(PerhitunganLiftBarang lift) {
        this.lift = lift;
    }

    /**
     * @return the tumpuk
     */
    public PerhitunganPenumpukanBarang getTumpuk() {
        return tumpuk;
    }

    /**
     * @param tumpuk the tumpuk to set
     */
    public void setTumpuk(PerhitunganPenumpukanBarang tumpuk) {
        this.tumpuk = tumpuk;
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
     * @return the serviceCfsStrippings
     */
    public List<Object[]> getServiceCfsStrippings() {
        return serviceCfsStrippings;
    }

    /**
     * @param serviceCfsStrippings the serviceCfsStrippings to set
     */
    public void setServiceCfsStrippings(List<Object[]> serviceCfsStrippings) {
        this.serviceCfsStrippings = serviceCfsStrippings;
    }

    /**
     * @return the serviceCfsStrippingsBL
     */
    public List<Object[]> getServiceCfsStrippingsBL() {
        return serviceCfsStrippingsBL;
    }

    /**
     * @param serviceCfsStrippingsBL the serviceCfsStrippingsBL to set
     */
    public void setServiceCfsStrippingsBL(List<Object[]> serviceCfsStrippingsBL) {
        this.serviceCfsStrippingsBL = serviceCfsStrippingsBL;
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

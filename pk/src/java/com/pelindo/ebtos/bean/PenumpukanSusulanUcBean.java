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
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanSusulanFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcDeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcGatedeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcPenumpukanSusulanServiceFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukanSusulan;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.UcDeliveryService;
import com.pelindo.ebtos.model.db.UcPenumpukanSusulanService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.math.BigDecimal;
import java.util.List;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
 * @author dycoder
 */
@ManagedBean(name = "penumpukanSusulanUcBean")
@ViewScoped
public class PenumpukanSusulanUcBean implements Serializable {

    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacade;
    @EJB
    private UcGatedeliveryServiceFacadeRemote ucGatedeliveryServiceFacade;
    @EJB
    private BatalNotaFacadeRemote batalNotaFacade;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacade;
    @EJB
    private UcDeliveryServiceFacadeRemote deliveryServiceFacade;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacade;
    @EJB
    private InvoiceFacadeRemote invoiceFacade;
    @EJB
    private PerhitunganPenumpukanSusulanFacadeRemote perhitunganPenumpukanSusulanFacade;
    @EJB
    private UcPenumpukanSusulanServiceFacadeRemote penumpukanSusulanServiceFacade;
    @EJB
    private RegistrationFacadeRemote registrationFacade;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;

    private List<Object[]> registrations;
    private List<Object[]> ucPenumpukanSusulanServices;
    private List<Object[]> deliveryServices;
    private Registration registration;
    private UcPenumpukanSusulanService ucPenumpukanSusulanService;
    private PerhitunganPenumpukanSusulan perhitunganPenumpukanSusulan;
    private UncontainerizedService uncontainerizedService;
    private UcDeliveryService ucDeliveryService;
    private BatalNota batalNota;
    private Invoice invoice;
    private String no_reg;
    private String blno;
    private BigDecimal total = BigDecimal.ZERO;
    private String language = null;
    private String country = null;
    private String actTumpuk;
    private Object[][] deliveryContainer;
    private BigDecimal ppnPrint = BigDecimal.ZERO;
    private BigDecimal materaiPrint = BigDecimal.ZERO;
    private String symbol;
    private String userId = null;
    private String to;
    private Boolean disPrint = true;
    private Boolean disKredit = false;
    private Boolean disDetail = true;
    private Boolean disCancelInv = true;
    private Date tanggalValidDate;
    private Boolean isSimpatReady;

    /** Creates a new instance of PenumpukanSusulanUcBean */
    public PenumpukanSusulanUcBean() {}

    @PostConstruct
    private void construct() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        perhitunganPenumpukanSusulan = new PerhitunganPenumpukanSusulan();
        uncontainerizedService = new UncontainerizedService();
        invoice = new Invoice();
        this.language = "id";
        this.country = "ID";
        deliveryContainer = new Object[0][0];
        ucDeliveryService = new UcDeliveryService();
        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        userId = loginSessionBean.getName();
        batalNota = new BatalNota();

        registrations = registrationFacade.findRegistrationPenumpukanSusulanUC();
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        setRegistration(registrationFacade.find(no_reg));
        viewData();
    }

    public void viewData() {
        disCancelInv = Boolean.TRUE;
        setUcPenumpukanSusulanServices(penumpukanSusulanServiceFacade.findPerhitungan(no_reg));
        if (ucPenumpukanSusulanServices.size() > 10) {
            disDetail = false;
        } else {
            disDetail = true;
        }

        BigDecimal t = BigDecimal.ZERO;
        for (Object[] o : getUcPenumpukanSusulanServices()) {
            t = t.add((BigDecimal) o[10]);
            this.symbol = (String) (o[11]);
            this.setLanguage((String) (o[12]));
            this.setCountry((String) (o[13]));
        }
        setTotal(t);

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
        if (total.compareTo((BigDecimal) materai.get(1)[2]) == -1) { /* jumlah tagihan < nilai minimum */
            setMateraiPrint((BigDecimal) materai.get(0)[1]);
        } else if (total.compareTo((BigDecimal) materai.get(2)[2]) == -1) {
            setMateraiPrint((BigDecimal) materai.get(1)[1]);
        } else {
            setMateraiPrint((BigDecimal) materai.get(2)[1]);
        }

        if (blno == null) {
            deliveryServices = new ArrayList<Object[]>();
        } else if (blno.equals("")) {
            deliveryServices = deliveryServiceFacade.findDeliveryServiceByPPKB(registration.getPlanningVessel().getNoPpkb());
        } else {
            deliveryServices = deliveryServiceFacade.findByPpkbSusulanByBL(blno, registration.getPlanningVessel().getNoPpkb());
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

            if (invoice.getNoInvoice() != null && invoice.isCancelInvoice().equals("FALSE")) {
                /*cek apakah container sudah masuk gate, apabila sudah maka invoice tidak dapat di cancel*/
                disCancelInv = Boolean.FALSE;
                for (Object[] ob : ucPenumpukanSusulanServices) {
                    ucPenumpukanSusulanService = penumpukanSusulanServiceFacade.find(ob[0]);
                    Object jobslipUcDelivery = deliveryServiceFacade.findByPpkbNIdUC(ucPenumpukanSusulanService.getIdUc(), ucPenumpukanSusulanService.getNoPpkb());
                    if (ucGatedeliveryServiceFacade.findUcGatedeliveryServiceByGate(jobslipUcDelivery.toString()) == null) {
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
            invoice.setPaymentType("CASH");
            disPrint = true;
            disKredit = false;
        }

        blno = "";
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
        ucPenumpukanSusulanService = new UcPenumpukanSusulanService();
        uncontainerizedService = new UncontainerizedService();
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.MILLISECOND, 0);
        tanggalValidDate = now.getTime();
    }

    public void handleSave(ActionEvent event) {
        for (int j = 0; j < deliveryContainer.length; j++) {
            ucDeliveryService = deliveryServiceFacade.find(deliveryContainer[j][0]);
            ucPenumpukanSusulanService.setJobSlip(iDGeneratorFacade.generateJobSlipID("21"));
            uncontainerizedService = ucDeliveryService.getUncontainerizedService();
            ucPenumpukanSusulanService.setIdUc(ucDeliveryService.getUncontainerizedService().getIdUc());
            handleConfirm();
            ucPenumpukanSusulanService = new UcPenumpukanSusulanService();
        }
    }

    public void handleConfirm() {
        boolean loggedIn = false;
        if (ucPenumpukanSusulanService.getIdUc() == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.transaction.notcompleted");
        } else {
            setUncontainerizedService(new UncontainerizedService());
            setUncontainerizedService(getUcDeliveryService().getUncontainerizedService());

            Date placementDate1 = getUncontainerizedService().getPlacementDate();
            Date validDate1 = getUcDeliveryService().getValidDate();
            Date validDate2 = tanggalValidDate;
            int rangeSinceFirstPlacement = DateCalculator.countRangeInDays(validDate2, placementDate1) + 1;
            int rangeDeliveryService = DateCalculator.countRangeInDays(validDate1, placementDate1) + 1;
            int rangePenumpukanSusulan = rangeSinceFirstPlacement - rangeDeliveryService;
            int masa1Range = masterSettingAppFacade.getMasa1UcRange();
            int masa1 = 0;
            int masa2 = 0;

            if (rangeSinceFirstPlacement < (masa1Range + 1)) {
                masa1 = rangePenumpukanSusulan;
                masa2 = 0;
            } else {
                masa1 = (rangeDeliveryService > masa1Range ? 0 : masa1Range - rangeDeliveryService);
                masa2 = rangeSinceFirstPlacement - (rangeDeliveryService > masa1Range ? rangeDeliveryService : masa1Range);
            }

            ucPenumpukanSusulanService.setMasa1((short) masa1);
            ucPenumpukanSusulanService.setMasa2((short) masa2);
            ucPenumpukanSusulanService.setNoReg(registration.getNoReg());
            ucPenumpukanSusulanService.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
            ucPenumpukanSusulanService.setPlacementDate(ucDeliveryService.getValidDate());
            loggedIn = true;

            if (getUncontainerizedService().getWeight() <= 20000) {
                actTumpuk = "P010";
            } else if (getUncontainerizedService().getWeight() <= 35000) {
                actTumpuk = "P011";
            } else {
                actTumpuk = "P012";
            }
            
            perhitunganPenumpukanSusulan.setBlNo(uncontainerizedService.getBlNo());
            perhitunganPenumpukanSusulan.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
            perhitunganPenumpukanSusulan.setRegistration(registration);
            perhitunganPenumpukanSusulan.setMasterActivity(masterActivityFacade.find(actTumpuk));

            // set amount berdasarkan tipe pelayaran
            BigDecimal cHandD = BigDecimal.ZERO;
            BigDecimal cHandI = BigDecimal.ZERO;
            for (Object[] o1 : masterTarifContFacade.findAmountByActivity(actTumpuk)) {
                if (o1[1].equals("IDR")) {
                    cHandD = (BigDecimal) o1[0];
                } else {
                    cHandI = (BigDecimal) o1[0];
                }
            }

            BigDecimal cHand = BigDecimal.ZERO;
            if (registration.getPlanningVessel().getTipePelayaran().equals("d")) {
                cHand = cHandD;
                perhitunganPenumpukanSusulan.setCurrCode("IDR");
            } else {
                cHand = cHandI;
                perhitunganPenumpukanSusulan.setCurrCode("USD");
            }

            ucDeliveryService.setValidDate(getTanggalValidDate());
            perhitunganPenumpukanSusulan.setCharge(cHand);
            perhitunganPenumpukanSusulan.setChargeM1(cHand.multiply(BigDecimal.valueOf(ucPenumpukanSusulanService.getMasa1())));
            perhitunganPenumpukanSusulan.setChargeM2(cHand.multiply(BigDecimal.valueOf(ucPenumpukanSusulanService.getMasa2())).multiply(new BigDecimal(2)));
            perhitunganPenumpukanSusulan.setTotalCharge(perhitunganPenumpukanSusulan.getChargeM1().add(perhitunganPenumpukanSusulan.getChargeM2()));
            
            penumpukanSusulanServiceFacade.edit(ucPenumpukanSusulanService);
            perhitunganPenumpukanSusulanFacade.create(perhitunganPenumpukanSusulan);
            deliveryServiceFacade.edit(ucDeliveryService);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
            viewData();
        }
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

    public void handleSelect(ActionEvent event) {
        String no_bl = (String) event.getComponent().getAttributes().get("noBL");
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        String idDS = penumpukanSusulanServiceFacade.findInvoice(id_cont, getRegistration().getPlanningVessel().getNoPpkb(), no_reg);
        ucPenumpukanSusulanService = penumpukanSusulanServiceFacade.find(idDS);
        Integer idTumpuk = perhitunganPenumpukanSusulanFacade.findInvoiceUC(no_bl, getRegistration().getPlanningVessel().getNoPpkb(), no_reg);
        perhitunganPenumpukanSusulan = perhitunganPenumpukanSusulanFacade.find(idTumpuk);
    }

    public void handleSubmit(ActionEvent event) {
        Boolean loggedIn = true;
        invoice.setNoReg(no_reg);
        getInvoice().setRegistration(getRegistration());
        getInvoice().setPaymentStatus("UNPAID");
        getInvoice().setJumlahTagihan(getTotal());
        invoice.setValidasiPrint("FALSE");

        //ambil nilai ppn dari master setting app
        MasterSettingApp settingApp = new MasterSettingApp();
        settingApp = masterSettingAppFacade.find("ppn");

        setPpnPrint(settingApp.getValueFloat());

        BigDecimal ppn = getInvoice().getJumlahTagihan().multiply(settingApp.getValueFloat());
        getInvoice().setPpn(ppn);

        MasterCurrency masterCurrency = null;
        List<Object[]> materai = new ArrayList<Object[]>();
        if (getRegistration().getPlanningVessel().getTipePelayaran().equals("d")) {
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

        getInvoice().setTotalTagihan(getInvoice().getJumlahTagihan().add(getInvoice().getMaterai()).add(getInvoice().getPpn()));
        getInvoice().setMasterCurrency(masterCurrency);
        invoiceFacade.edit(invoice);

        if (getInvoice().getPaymentType().equalsIgnoreCase("CASH")) {
            getRegistration().setStatusService("confirm");
        } else if (getInvoice().getPaymentType().equalsIgnoreCase("KREDIT")) {
            getRegistration().setStatusService("approve");
        }
        registrationFacade.edit(getRegistration());
        viewData();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleDelete(ActionEvent event) {
        ucDeliveryService.setValidDate(ucPenumpukanSusulanService.getPlacementDate());
        deliveryServiceFacade.edit(ucDeliveryService);
        penumpukanSusulanServiceFacade.remove(ucPenumpukanSusulanService);
        perhitunganPenumpukanSusulanFacade.remove(perhitunganPenumpukanSusulan);
        viewData();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
    }

    public void handleFindBl(ActionEvent event) {
        deliveryServices.clear();
        deliveryServices = deliveryServiceFacade.findByPpkbSusulanByBL(blno, registration.getPlanningVessel().getNoPpkb());
        if (deliveryServices.isEmpty()) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.notfound");
        }
    }

    public void handleFindAllBl(ActionEvent event) {
        blno = "";
        deliveryServices.clear();
        deliveryServices = deliveryServiceFacade.findDeliveryServiceByPPKB(registration.getPlanningVessel().getNoPpkb());
        if (deliveryServices.isEmpty()) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.notfound");
        }
    }

    public void handleDownloadTransactionRecap(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/delivery.pdf?no_reg=" + no_reg + "&total=" + String.valueOf(total) + "&ppn=" + String.valueOf(getPpnPrint()) + "&materai=" + String.valueOf(getMateraiPrint()) + "&userId=" + getUserId() + "&tipe=penumpukanSusulanUC")));
    }

    public void handleDownloadNota(ActionEvent event) {
        invoice = invoiceFacade.find(no_reg);

        //validasi print invoice
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

        for (Object o : penumpukanSusulanServiceFacade.findByReg(no_reg)) {
            ucPenumpukanSusulanService = penumpukanSusulanServiceFacade.find(o);
            penumpukanSusulanServiceFacade.remove(ucPenumpukanSusulanService);
        }

        for (Object o : perhitunganPenumpukanSusulanFacade.findByReg(no_reg)) {
            perhitunganPenumpukanSusulan = perhitunganPenumpukanSusulanFacade.find(o);
            perhitunganPenumpukanSusulanFacade.remove(perhitunganPenumpukanSusulan);
        }

        //save ke database
        batalNotaFacade.create(batalNota);
        
        invoice.setNoFakturPajak(null);
        invoice.setNoInvoice(null);
        invoice.setCancelInvoice("TRUE");

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
        if (getUcPenumpukanSusulanServices() == null) {
            return "#";
        }
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        return context.getRequestScheme() + "://" + context.getRequestServerName() + ":" + context.getRequestServerPort() + context.getRequestContextPath() + "/delivery.pdf?"
                + "no_reg=" + no_reg + "&"
                + "total=" + String.valueOf(getTotal()) + "&"
                + "ppn=" + String.valueOf(getPpnPrint()) + "&"
                + "materai=" + String.valueOf(getMateraiPrint()) + "&"
                + "userId=" + getUserId() + "&"
                + "tipe=" + "penumpukanSusulanUC" + "";
    }

    public String getUrlNota() {
        if (getUcPenumpukanSusulanServices() == null) {
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
        if (getUcPenumpukanSusulanServices() == null) {
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
     * @return the penumpukanSusulanServices
     */
    public List<Object[]> getUcPenumpukanSusulanServices() {
        return ucPenumpukanSusulanServices;
    }

    /**
     * @param penumpukanSusulanServices the penumpukanSusulanServices to set
     */
    public void setUcPenumpukanSusulanServices(List<Object[]> penumpukanSusulanServices) {
        this.ucPenumpukanSusulanServices = penumpukanSusulanServices;
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
     * @return the deliveryServices
     */
    public List<Object[]> getDeliveryServices() {
        return deliveryServices;
    }

    /**
     * @param deliveryServices the deliveryServices to set
     */
    public void setDeliveryServices(List<Object[]> deliveryServices) {
        this.deliveryServices = deliveryServices;
    }

    /**
     * @return the penumpukanSusulanService
     */
    public UcPenumpukanSusulanService getUcPenumpukanSusulanService() {
        return ucPenumpukanSusulanService;
    }

    /**
     * @param penumpukanSusulanService the penumpukanSusulanService to set
     */
    public void setUcPenumpukanSusulanService(UcPenumpukanSusulanService penumpukanSusulanService) {
        this.ucPenumpukanSusulanService = penumpukanSusulanService;
    }

    /**
     * @return the uncontainerizedService
     */
    public UncontainerizedService getUncontainerizedService() {
        return uncontainerizedService;
    }

    /**
     * @param uncontainerizedService the uncontainerizedService to set
     */
    public void setUncontainerizedService(UncontainerizedService uncontainerizedService) {
        this.uncontainerizedService = uncontainerizedService;
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

    public UcDeliveryService getUcDeliveryService() {
        return ucDeliveryService;
    }

    public void setDs(UcDeliveryService ds) {
        this.ucDeliveryService = ds;
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
    
    public Date getTanggalValidDate() {
        return tanggalValidDate;
    }

    public void setTanggalValidDate(Date tanggalValidDate) {
        this.tanggalValidDate = tanggalValidDate;
    }

    public Boolean getIsSimpatReady() {
        return isSimpatReady;
    }
}

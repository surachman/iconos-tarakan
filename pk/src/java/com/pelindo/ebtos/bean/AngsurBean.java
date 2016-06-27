/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.url.UrlHelper;
import com.qtasnim.util.IndonesianNumberConverter;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.AngsurServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.BankingSyncFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.BatalNotaFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganAngsurFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateDeliveryFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.model.db.AngsurService;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PerhitunganAngsur;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
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
import javax.faces.event.ValueChangeEvent;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "angsurBean")
@ViewScoped
public class AngsurBean implements Serializable {
    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacade;
    @EJB
    private AngsurServiceFacadeRemote angsurServiceFacadeRemote;
    @EJB
    private RegistrationFacadeRemote registrationFacadeRemote;
    @EJB
    private InvoiceFacadeRemote invoiceFacadeRemote;
    @EJB
    private PerhitunganAngsurFacadeRemote perhitunganAngsurFacadeRemote;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote;
    @EJB
    private ServiceGateDeliveryFacadeRemote serviceGateDeliveryFacadeRemote;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacadeRemote;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacadeRemote;
    @EJB
    private BatalNotaFacadeRemote batalNotaFacadeRemote;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;
    
    private List<Object[]> registrations;
    private Registration registration;
    private MasterCustomer masterCustomer;
    private PlanningVessel planningVessel;
    private List<Object[]> angsurServices;
    private String no_reg;
    private BigDecimal total = BigDecimal.ZERO;
    private Invoice invoice;
    private AngsurService angsurService;
    private PerhitunganAngsur perhitunganAngsur;
    private String mode;
    private String language = null;
    private String country = null;
    private IndonesianNumberConverter converter;
    private String terbilang;
    private List<Object[]> serviceContDischarges;
    private String currency;
    private BigDecimal ppnPrint = BigDecimal.ZERO;
    private BigDecimal materaiPrint = BigDecimal.ZERO;
    private String userId = null;
    private String toinvoice;
    private Boolean disPrint = true;
    private Boolean disKredit = Boolean.FALSE;
    private Boolean disDetail = Boolean.FALSE;
    private String symbol;
    private BatalNota batalNota;
    private Boolean disCancelInv = true;
    private Boolean isSimpatReady;

    /** Creates a new instance of AngsurBean */
    public AngsurBean() {}

    @PostConstruct
    private void construct() {
        registration = new Registration();
        masterCustomer = new MasterCustomer();
        planningVessel = new PlanningVessel();
        invoice = new Invoice();
        invoice.setPaymentType("KREDIT");
        angsurService = new AngsurService();
        perhitunganAngsur = new PerhitunganAngsur();
        this.language = "id";
        this.country = "ID";
        converter = new IndonesianNumberConverter();
        batalNota = new BatalNota();
        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        userId = loginSessionBean.getName();

        registrations = registrationFacadeRemote.findRegistrationAngsur();
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
    }

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

    public void handleSelectRegistration(ActionEvent event) {
        setNo_reg((String) event.getComponent().getAttributes().get("noReg"));
        registration = registrationFacadeRemote.find(getNo_reg());
        angsurServices = angsurServiceFacadeRemote.findPerhitungan(getNo_reg());

        if (angsurServices.size() > 10) {
            this.disDetail = Boolean.FALSE;
        } else {
            this.disDetail = true;
        }

        BigDecimal t = BigDecimal.ZERO;
        for (Object[] o : angsurServices) {
            t = t.add((BigDecimal) (o[8]));
            this.language = (String) (o[10]);
            this.country = (String) (o[11]);
            this.symbol = (String) (o[9]);
        }
        total = t;
        BigInteger tot = BigInteger.ZERO;
        tot = total.toBigInteger();
        String to = tot.toString();
        setTerbilang(getConverter().convertToWord(to));
        serviceContDischarges = serviceContDischargeFacadeRemote.findServiceContDischargesAngsurService(registration.getPlanningVessel().getNoPpkb());

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

        invoice = invoiceFacadeRemote.find(no_reg);
        if (invoice != null) {
            BigDecimal ttinvoice = invoice.getTotalTagihan();
            BigInteger tottinvoice = ttinvoice.toBigInteger();
            if (invoice.getMasterCurrency().getCurrCode().equalsIgnoreCase("USD")) {
                toinvoice = ttinvoice.toString();
            } else {
                toinvoice = tottinvoice.toString();
            }
            disPrint = false;
            if (invoice.getValidasiPrint().equals("TRUE")) {
                disPrint = true;
            }
            if (invoice.getPaymentType().equalsIgnoreCase("CASH")) {
                disKredit = Boolean.FALSE;
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
            for (Object[] ob : angsurServices) {
                angsurService = angsurServiceFacadeRemote.find(ob[0]);
                if (serviceGateDeliveryFacadeRemote.findByContAndPpkb(angsurService.getContNo(), angsurService.getPlanningVessel().getNoPpkb()) == null) {
                    if (disCancelInv == Boolean.FALSE) {
                        disCancelInv = Boolean.FALSE;
                    }
                } else {
                    disCancelInv = Boolean.TRUE;
                }
            }
            /*akhir pengecekan container di gate*/
        }

    }

    public void onChangeEvent(ValueChangeEvent event) {
        String temp = (String) event.getNewValue();
        invoice.setPaymentType(temp);
        if (temp.equalsIgnoreCase("CASH")) {
            disKredit = Boolean.FALSE;
        } else {
            disKredit = true;
        }
        //System.out.println(temp);
    }

    public void handleDownloadTransactionRecap(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/perhitunganReceiving.pdf?noRegistrasi=" + no_reg + "&total=" + String.valueOf(total) + "&ppn=" + String.valueOf(getPpnPrint()) + "&materai=" + String.valueOf(getMateraiPrint()) + "&userId=" + getUserId() + "&mode=angsur")));
    }

    public void handleDownloadNota(ActionEvent event) {
        invoice = invoiceFacadeRemote.find(no_reg);
        //validasi print invoice
        if (invoice.getValidasiPrint().equals("FALSE")) {
            invoice = invoiceFacadeRemote.publishInvoice(invoice);
        }
        
        disPrint = false;
        if (invoice.getValidasiPrint().equals("TRUE")) {
            disPrint = true;
        }
         
        angsurServices = angsurServiceFacadeRemote.findPerhitungan(getNo_reg());

        if (invoice.getNoInvoice() != null && invoice.isCancelInvoice().equals("FALSE")) {
            /*cek apakah container sudah masuk gate, apabila sudah maka invoice tidak dapat di cancel*/
            disCancelInv = Boolean.FALSE;
            for (Object[] ob : angsurServices) {
                angsurService = angsurServiceFacadeRemote.find(ob[0]);
                if (serviceGateDeliveryFacadeRemote.findByContAndPpkb(angsurService.getContNo(), angsurService.getPlanningVessel().getNoPpkb()) == null) {
                    if (disCancelInv == Boolean.FALSE) {
                        disCancelInv = Boolean.FALSE;
                    }
                } else {
                    disCancelInv = Boolean.TRUE;
                }
            }
            /*akhir pengecekan container di gate*/
        }

        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/invoice.pdf?no_reg=" + no_reg + "&to=" + toinvoice + "&curr=" + country + "&tipe=" + registration.getMasterService().getServiceCode() + "&username=" + getUserId() + "&detail=" + String.valueOf(disDetail))));
    }

    public void handleDownloadDetail(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/detail.pdf?no_reg=" + no_reg + "&tipe=" + registration.getMasterService().getServiceCode() + "&username=" + getUserId() + "&ppn=" + String.valueOf(ppnPrint) + "&materai=" + String.valueOf(materaiPrint) + "&total=" + String.valueOf(total))));
    }

    public void handleSelect(ActionEvent event) {
        String job_slip = (String) event.getComponent().getAttributes().get("jobSlip");
        setAngsurService(angsurServiceFacadeRemote.find(job_slip));
        int id = perhitunganAngsurFacadeRemote.findPerhitunganAngsurId(getAngsurService().getPlanningVessel().getNoPpkb(), getAngsurService().getRegistration().getNoReg(), getAngsurService().getContNo());
        perhitunganAngsur = perhitunganAngsurFacadeRemote.find(id);
    }

    public void handleDelete(ActionEvent event) {
        try {
            angsurServiceFacadeRemote.remove(angsurService);
            perhitunganAngsurFacadeRemote.remove(perhitunganAngsur);
            angsurServices = angsurServiceFacadeRemote.findPerhitungan(no_reg);
            BigDecimal t = BigDecimal.ZERO;
            for (Object[] o : angsurServices) {
                t = t.add((BigDecimal) (o[8]));
                this.language = (String) (o[10]);
                this.country = (String) (o[11]);
                this.symbol = (String) (o[9]);
            }
            total = t;
            BigInteger tot = BigInteger.ZERO;
            tot = total.toBigInteger();
            String to = tot.toString();
            setTerbilang(getConverter().convertToWord(to));

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
            serviceContDischarges = serviceContDischargeFacadeRemote.findServiceContDischargesAngsurService(registration.getPlanningVessel().getNoPpkb());
            clear();
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
        }
    }

    public void clear() {
        angsurService = new AngsurService();
    }

    public void handleAdd(ActionEvent actionEvent) {
        clear();
    }

    public void handleConfirm(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Boolean loggedIn = Boolean.FALSE;
        
        Boolean bSpecialEq =  false;
        
        if (angsurService.getContNo() == null) {
            loggedIn = Boolean.FALSE;
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        } else {
            if (angsurService.getSpecialEquipment().equals("TRUE")) {
                bSpecialEq = true;
            }
            String extraMovementActivityCode = masterActivityFacadeRemote.translateExtraMovementActivityCode(
                    angsurService.getContSize(), 
                    bSpecialEq);
            
            MasterActivity extraMovementMasterActivity = masterActivityFacadeRemote.find(extraMovementActivityCode);

            if (angsurService.getPlanningVessel().getTipePelayaran().equals("d")) {
                currency = "IDR";
            } else if (angsurService.getPlanningVessel().getTipePelayaran().equals("i")) {
                currency = "USD";
            }

            BigDecimal loloCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(currency, extraMovementActivityCode);

            if (angsurService.getDg().equals("TRUE") && angsurService.getDgLabel().equals("TRUE")) {
                loloCharge = loloCharge.multiply(BigDecimal.valueOf(2));
            } else if (angsurService.getDg().equals("TRUE")&& angsurService.getDgLabel().equals("FALSE")) {
                loloCharge = loloCharge.multiply(BigDecimal.valueOf(3));
            }
            
            perhitunganAngsur.setCharge(loloCharge);
            perhitunganAngsur.setContNo(angsurService.getContNo());
            perhitunganAngsur.setMlo(angsurService.getMlo());
            perhitunganAngsur.setNoPpkb(angsurService.getPlanningVessel().getNoPpkb());
            perhitunganAngsur.setRegistration(angsurService.getRegistration());
            perhitunganAngsur.setMasterActivity(extraMovementMasterActivity);
            perhitunganAngsur.setCurrCode(currency);

            angsurService.setJobSlip(iDGeneratorFacade.generateJobSlipID("17"));
            angsurService.setRegistration(registration);
            angsurService.setPlanningVessel(registration.getPlanningVessel());

            angsurServiceFacadeRemote.create(angsurService);
            perhitunganAngsurFacadeRemote.create(perhitunganAngsur);

            angsurServices = angsurServiceFacadeRemote.findPerhitungan(getNo_reg());
            
            total = BigDecimal.ZERO;

            for (Object[] o : angsurServices) {
                total = total.add((BigDecimal) (o[8]));
                this.language = (String) (o[10]);
                this.country = (String) (o[11]);
                this.symbol = (String) (o[9]);
            }

            loggedIn = true;
            serviceContDischarges = serviceContDischargeFacadeRemote.findServiceContDischargesAngsurService(registration.getPlanningVessel().getNoPpkb());
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        }

        requestContext.addCallbackParam("loggedIn", loggedIn);
        clear();
    }

    //invoice
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
            disKredit = Boolean.FALSE;
        }

        angsurServices = angsurServiceFacadeRemote.findPerhitungan(getNo_reg());
        BigDecimal t = BigDecimal.ZERO;
        for (Object[] o : angsurServices) {
            t = t.add((BigDecimal) (o[8]));
            this.language = (String) (o[10]);
            this.country = (String) (o[11]);
            this.symbol = (String) (o[9]);
        }
        total = t;

        if (angsurServices.size() > 10) {
            this.disDetail = Boolean.FALSE;
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
            
            disPrint = false;
            if(invoice.getValidasiPrint().equals("TRUE")) {
                disPrint = false;
            }
            
        }

        registration.setStatusService(status);
        registrationFacadeRemote.edit(registration);

        if (invoice.getNoInvoice() != null && invoice.isCancelInvoice().equals("FALSE")) {
            /*cek apakah container sudah masuk gate, apabila sudah maka invoice tidak dapat di cancel*/
            disCancelInv = Boolean.FALSE;
            for (Object[] ob : angsurServices) {
                angsurService = angsurServiceFacadeRemote.find(ob[0]);
                if (serviceGateDeliveryFacadeRemote.findByContAndPpkb(angsurService.getContNo(), angsurService.getPlanningVessel().getNoPpkb()) == null) {
                    if (disCancelInv == Boolean.FALSE) {
                        disCancelInv = Boolean.FALSE;
                    }
                } else {
                    disCancelInv = Boolean.TRUE;
                }
            }
            /*akhir pengecekan container di gate*/
        }

        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleSelectContPick(ActionEvent event) {
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        ServiceContDischarge scd = serviceContDischargeFacadeRemote.find(id_cont);
        angsurService.setContNo(scd.getContNo());
        angsurService.setMlo(scd.getMlo());
        angsurService.setContSize(scd.getContSize());
        angsurService.setContGross(scd.getContGross());
        angsurService.setContStatus(scd.getContStatus());
        angsurService.setOverSize(scd.getOverSize());
        angsurService.setDg(scd.getDangerous());
        angsurService.setDgLabel(scd.getDgLabel());
        angsurService.setMasterCommodity(scd.getMasterCommodity());
        angsurService.setMasterContainerType(scd.getMasterContainerType());
        angsurService.setRegistration(registration);
        angsurService.setPlanningVessel(registration.getPlanningVessel());
        angsurService.setBlNo(scd.getBlNo());
    }

    //funtion batal nota
    public void handleAddCancelInvoice(ActionEvent event) {
        batalNota = new BatalNota();
    }

    //FUNCTION BATAL NOTA
    public void handleSubmitBatalNota(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = Boolean.FALSE;
        try {
            List<Object[]> angsurList = null;
            loggedIn = true;
            //input k batal nota
            registration = registrationFacadeRemote.find(invoice.getNoReg());
            batalNota.setNoFakturPajak(invoice.getNoFakturPajak());
            batalNota.setCustCode(registration.getMasterCustomer().getCustCode());
            final String noInvoice = invoice.getNoInvoice();
            batalNota.setNoInvoice(noInvoice);
            batalNota.setNoReg(invoice.getNoReg());
            batalNota.setPaymentDate(invoice.getPaymentDate());
            batalNota.setTotalTagihan(invoice.getTotalTagihan());

            batalNotaFacadeRemote.create(batalNota);

            invoice.setNoFakturPajak(null);
            invoice.setNoInvoice(null);
            invoice.setCancelInvoice("TRUE");
            
            invoiceFacadeRemote.edit(invoice);

            if (masterSettingAppFacade.isPaymentBankingEnabled())
                try {
                    bankingSyncFacadeRemote.cancelInvoice(noInvoice);
                } catch (RuntimeException e) {
                    System.err.println(FacesHelper.getLocaleMessage("application.integration.banking.failed", FacesContext.getCurrentInstance()));
                    FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.integration.banking.failed");
                }

            //find and update tabel-table relasi            
            angsurList = angsurServiceFacadeRemote.findByListBatalNotaService(registration.getPlanningVessel().getNoPpkb(), no_reg);
            if (!angsurList.isEmpty()) {
                for (int i = 0; i < angsurList.size(); i++) {
                    angsurService = angsurServiceFacadeRemote.find(angsurList.get(i)[0]);
                    System.out.println("test ID : " + angsurList.get(i)[0]);
                    int id = perhitunganAngsurFacadeRemote.findPerhitunganAngsurId(angsurService.getPlanningVessel().getNoPpkb(), angsurService.getRegistration().getNoReg(), angsurService.getContNo());
                    perhitunganAngsur = perhitunganAngsurFacadeRemote.find(id);

                    angsurServiceFacadeRemote.remove(angsurService);
                    perhitunganAngsurFacadeRemote.remove(perhitunganAngsur);
                }
            }

            disCancelInv = Boolean.TRUE;
            serviceContDischarges = serviceContDischargeFacadeRemote.findServiceContDischargesAngsurService(registration.getPlanningVessel().getNoPpkb());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (EJBException e) {
            e.printStackTrace();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }
        context.addCallbackParam("loggedIn", loggedIn);
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
     * @return the masterCustomer
     */
    public MasterCustomer getMasterCustomer() {
        return masterCustomer;
    }

    /**
     * @param masterCustomer the masterCustomer to set
     */
    public void setMasterCustomer(MasterCustomer masterCustomer) {
        this.masterCustomer = masterCustomer;
    }

    /**
     * @return the planningVessel
     */
    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    /**
     * @param planningVessel the planningVessel to set
     */
    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    /**
     * @return the angsurServices
     */
    public List<Object[]> getAngsurServices() {
        return angsurServices;
    }

    /**
     * @param angsurServices the angsurServices to set
     */
    public void setAngsurServices(List<Object[]> angsurServices) {
        this.angsurServices = angsurServices;
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
     * @return the angsurService
     */
    public AngsurService getAngsurService() {
        return angsurService;
    }

    /**
     * @param angsurService the angsurService to set
     */
    public void setAngsurService(AngsurService angsurService) {
        this.angsurService = angsurService;
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
     * @return the serviceContDischarges
     */
    public List<Object[]> getServiceContDischarges() {
        return serviceContDischarges;
    }

    /**
     * @param serviceContDischarges the serviceContDischarges to set
     */
    public void setServiceContDischarges(List<Object[]> serviceContDischarges) {
        this.setServiceContDischarges(serviceContDischarges);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

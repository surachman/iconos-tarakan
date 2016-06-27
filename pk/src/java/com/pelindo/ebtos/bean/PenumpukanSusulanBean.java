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
import com.pelindo.ebtos.ejb.facade.remote.DeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDiscountFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PenumpukanSusulanServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanSusulanFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateDeliveryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReeferFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.DeliveryService;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PenumpukanSusulanService;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukanSusulan;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import com.qtasnim.util.DateCalculator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "penumpukanSusulanBean")
@ViewScoped
public class PenumpukanSusulanBean implements Serializable {

    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacade;
    @EJB
    private InvoiceFacadeRemote invoiceFacade;
    @EJB
    private PerhitunganPenumpukanSusulanFacadeRemote perhitunganPenumpukanSusulanFacade;
    @EJB
    private PenumpukanSusulanServiceFacadeRemote penumpukanSusulanServiceFacade;
    @EJB
    private RegistrationFacadeRemote registrationFacade;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private ServiceReeferFacadeRemote serviceReeferFacadeRemote;
    @EJB
    private DeliveryServiceFacadeRemote deliveryServiceFacadeRemote;
    @EJB
    private ServiceGateDeliveryFacadeRemote serviceGateDeliveryFacadeRemote;
    @EJB
    private MasterActivityFacadeRemote activityFacadeRemote;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacadeRemote;
    @EJB
    private MasterDiscountFacadeRemote masterDiscountFacadeRemote;
    @EJB
    private BatalNotaFacadeRemote batalNotaFacadeRemote;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacadeRemote;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote;

    private List<Object[]> registrations;
    private List<Object[]> penumpukanSusulanServices;
    private Registration registration;
    private PenumpukanSusulanService penumpukanSusulanService;
    private PerhitunganPenumpukanSusulan perhitunganPenumpukanSusulan;
    private Invoice invoice;
    private String no_reg;
    private BigDecimal total = BigDecimal.ZERO;
    private String language = null;
    private String country = null;
    private DeliveryService ds;
    private List<Object[]> deliveryServices;
    private Object[][] penumpukanSusulan;
    private String act = "bl";
    private String bl_no;
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
    private DeliveryService deliveryService;
    private Date tanggalValidDate;
    private Boolean isSimpatReady;

    /** Creates a new instance of PenumpukanSusulanBean */
    public PenumpukanSusulanBean() {
    }

    @PostConstruct
    public void construct() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        penumpukanSusulanService = new PenumpukanSusulanService();
        perhitunganPenumpukanSusulan = new PerhitunganPenumpukanSusulan();
        invoice = new Invoice();
        invoice.setPaymentType("CASH");
        this.language = "id";
        this.country = "ID";
        penumpukanSusulan = new Object[0][0];
        batalNota = new BatalNota();
        deliveryService = new DeliveryService();
        registrations = registrationFacade.findRegistrationPenumpukanSusulan();

        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        userId = loginSessionBean.getName();
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();

    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(no_reg);
        setPenumpukanSusulanServices(penumpukanSusulanServiceFacade.findPerhitungan(no_reg));

        if (penumpukanSusulanServices.size() > 5) {
            this.disDetail = false;
        } else {
            this.disDetail = true;
        }

        //setDeliveryServices(deliveryServiceFacadeRemote.findByPpkbPenumpukanSusulan(registration.getPlanningVessel().getNoPpkb()));
        BigDecimal t = BigDecimal.ZERO;
        for (Object[] o : getPenumpukanSusulanServices()) {
            t = t.add((BigDecimal) o[13]);
            this.language = (String) (o[15]);
            this.country = (String) (o[16]);
            this.symbol = (String) (o[14]);
        }
        setTotal(t);
        System.out.println(total);
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
            invoice.setPaymentType("CASH");
            disPrint = true;
            disKredit = true;
        }

        if (invoice.getNoInvoice() != null && invoice.isCancelInvoice().equals("FALSE")) {
            /*cek apakah container sudah masuk gate, apabila sudah maka invoice tidak dapat di cancel*/
            disCancelInv = Boolean.FALSE;
            for (Object[] ob : penumpukanSusulanServices) {
                penumpukanSusulanService = penumpukanSusulanServiceFacade.find(ob[19]);
                String del = null;
                del = deliveryServiceFacadeRemote.findByPpkbNCont(penumpukanSusulanService.getContNo(), penumpukanSusulanService.getPlanningVessel().getNoPpkb());
                deliveryService = deliveryServiceFacadeRemote.find(del);
                if (del != null) {
                    if (serviceGateDeliveryFacadeRemote.findByContAndPpkb(deliveryService.getContNo(), deliveryService.getPlanningVessel().getNoPpkb()) == null) {
                        if (disCancelInv == false) {
                            disCancelInv = Boolean.FALSE;
                        }
                    } else {
                        disCancelInv = Boolean.TRUE;
                    }
                }

            }
            /*akhir pengecekan container di gate*/
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
        //System.out.println(temp);
    }

    public void cariBlNo(ActionEvent event) {
        this.bl_no = (String) event.getComponent().getAttributes().get("blNo");
        setDeliveryServices(deliveryServiceFacadeRemote.findByPpkbAndBlNoPenumpukanSusulan(registration.getPlanningVessel().getNoPpkb(), bl_no));
        if (deliveryServices.size() < 1) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
            penumpukanSusulanService = new PenumpukanSusulanService();
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        }
        this.act = "bl";
    }

    public void cariBlNo2(ActionEvent event) {
        //setBlNo((String) event.getComponent().getAttributes().get("blNo"));
        this.bl_no = (String) event.getComponent().getAttributes().get("blNo");
        setDeliveryServices(deliveryServiceFacadeRemote.findByPpkbPenumpukanSusulan(registration.getPlanningVessel().getNoPpkb()));
        if (deliveryServices.size() < 1) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
            penumpukanSusulanService = new PenumpukanSusulanService();
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        }
    }

//    public String getFormattedTotal() {
//        String local = null;
//        Locale trLocale = new Locale(getLanguage(), getCountry());
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
            return numberFormat.format((total.add((total.multiply(ppnPrint))).add(materaiPrint))).toString();
        }
    }

    public void handleSelect(ActionEvent event) {
        Integer no_cont = ((Number) (event.getComponent().getAttributes().get("noCont"))).intValue();
        penumpukanSusulanService = penumpukanSusulanServiceFacade.find(no_cont);
        Integer idTumpuk = perhitunganPenumpukanSusulanFacade.findInvoice(penumpukanSusulanService.getContNo(), registration.getPlanningVessel().getNoPpkb(), no_reg);
        perhitunganPenumpukanSusulan = perhitunganPenumpukanSusulanFacade.find(idTumpuk);
    }

    public void handleAdd(ActionEvent event) {
        penumpukanSusulanService = new PenumpukanSusulanService();
        perhitunganPenumpukanSusulan = new PerhitunganPenumpukanSusulan();
        deliveryServices = new ArrayList<Object[]>();

        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        now.set(Calendar.MILLISECOND, 0);
        tanggalValidDate = now.getTime();
    }

    public void handleSubmit(ActionEvent event) {
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

        //invoiceFacade.create(invoice);
        registration.setInvoice(invoice);

        if (invoice.getPaymentType().equalsIgnoreCase("CASH")) {
            getRegistration().setStatusService("confirm");
            disKredit = false;
        } else if (invoice.getPaymentType().equalsIgnoreCase("KREDIT")) {
            getRegistration().setStatusService("approve");
            disKredit = true;
        }
        registrationFacade.edit(registration);

        setPenumpukanSusulanServices(penumpukanSusulanServiceFacade.findPerhitungan(no_reg));

        if (penumpukanSusulanServices.size() > 10) {
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
            disPrint = invoice.getValidasiPrint().equals("TRUE") ? true : false ;
        }

        if (invoice.getNoInvoice() != null && invoice.isCancelInvoice().equals("FALSE")) {
            /*cek apakah container sudah masuk gate, apabila sudah maka invoice tidak dapat di cancel*/
            disCancelInv = Boolean.FALSE;
            for (Object[] ob : penumpukanSusulanServices) {
                penumpukanSusulanService = penumpukanSusulanServiceFacade.find(ob[19]);
                deliveryService = deliveryServiceFacadeRemote.find(penumpukanSusulanService.getJobSlip());
                if (serviceGateDeliveryFacadeRemote.findByContAndPpkb(deliveryService.getContNo(), deliveryService.getPlanningVessel().getNoPpkb()) == null) {
                    if (disCancelInv == false) {
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

    public void handleDelete(ActionEvent event) {
        try {
            penumpukanSusulanServiceFacade.remove(penumpukanSusulanService);
            perhitunganPenumpukanSusulanFacade.remove(perhitunganPenumpukanSusulan);
            setPenumpukanSusulanServices(penumpukanSusulanServiceFacade.findPerhitungan(no_reg));
            //setDeliveryServices(deliveryServiceFacadeRemote.findByPpkbPenumpukanSusulan(registration.getPlanningVessel().getNoPpkb()));
            BigDecimal t = BigDecimal.ZERO;
            for (Object[] o : getPenumpukanSusulanServices()) {
                t = t.add((BigDecimal) o[13]);
                this.language = (String) (o[15]);
                this.country = (String) (o[16]);
                this.symbol = (String) (o[14]);
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

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
        }
    }

    public void handleSelectContPick(ActionEvent event) {
        String id_cont = (String) event.getComponent().getAttributes().get("idCont");
        ds = deliveryServiceFacadeRemote.find(id_cont);
        penumpukanSusulanService.setContNo(ds.getContNo());
        penumpukanSusulanService.setMlo(ds.getMlo());
        penumpukanSusulanService.setContSize(ds.getContSize());
        penumpukanSusulanService.setContGross(ds.getContGross());
        penumpukanSusulanService.setContStatus(ds.getContStatus());
        penumpukanSusulanService.setOverSize(ds.getOverSize());
        penumpukanSusulanService.setTwentyOneFeet(ds.getTwentyOneFeet());
        penumpukanSusulanService.setDg(ds.getDg());
        penumpukanSusulanService.setDgLabel(ds.getDgLabel());
        penumpukanSusulanService.setMasterCommodity(ds.getMasterCommodity());
        penumpukanSusulanService.setMasterContainerType(ds.getMasterContainerType());
        penumpukanSusulanService.setPlacementDate(ds.getValidDate());
        penumpukanSusulanService.setBlNo(ds.getBlNo());
    }

    public void saveEdit(ActionEvent event) {
        for (int j = 0; j < penumpukanSusulan.length; j++) {
            ds = deliveryServiceFacadeRemote.find(penumpukanSusulan[j][0].toString());

            String job = penumpukanSusulanServiceFacade.findCekValidasi(ds.getContNo(), ds.getPlanningVessel().getNoPpkb(), no_reg);

            if (job != null) {
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
            } else {
                DeliveryService delivery = deliveryServiceFacadeRemote.findByNoPpkbAndContNo(registration.getPlanningVessel().getNoPpkb(), ds.getContNo());
                ServiceContDischarge serviceContDischarge = serviceContDischargeFacadeRemote.findByNoPpkbAndContNo(registration.getPlanningVessel().getNoPpkb(), ds.getContNo());
                Date placementDate1 = serviceContDischarge.getStartPlacementDate();
                Date validDate1 = delivery.getValidDate();
                Date validDate2 = tanggalValidDate;

                int rangeSinceFirstPlacement = DateCalculator.countRangeInDays(validDate2, placementDate1) + 1;
                int rangeDeliveryService = DateCalculator.countRangeInDays(validDate1, placementDate1) + 1;
                int rangePenumpukanSusulan = rangeSinceFirstPlacement - rangeDeliveryService;
                int masa1Range = masterSettingAppFacade.getMasa1ContainerRange();
                int masa1 = 0;
                int masa2 = 0;

                if (rangeSinceFirstPlacement < (masa1Range + 1)) {
                    masa1 = rangePenumpukanSusulan;
                    masa2 = 0;
                } else {
                    masa1 = (rangeDeliveryService > masa1Range ? 0 : masa1Range - rangeDeliveryService);
                    masa2 = rangeSinceFirstPlacement - (rangeDeliveryService > masa1Range ? rangeDeliveryService : masa1Range);
                }

                penumpukanSusulanService.setMasa1((short) masa1);
                penumpukanSusulanService.setMasa2((short) masa2);
                penumpukanSusulanService.setJobSlip(ds.getJobSlip());
                penumpukanSusulanService.setContNo(ds.getContNo());
                penumpukanSusulanService.setMlo(ds.getMlo());
                penumpukanSusulanService.setContSize(ds.getContSize());
                penumpukanSusulanService.setContGross(ds.getContGross());
                penumpukanSusulanService.setContStatus(ds.getContStatus());
                penumpukanSusulanService.setOverSize(ds.getOverSize());
                penumpukanSusulanService.setDg(ds.getDg());
                penumpukanSusulanService.setTwentyOneFeet(ds.getTwentyOneFeet());
                penumpukanSusulanService.setDgLabel(ds.getDgLabel());
                penumpukanSusulanService.setMasterCommodity(ds.getMasterCommodity());
                penumpukanSusulanService.setMasterContainerType(ds.getMasterContainerType());
                penumpukanSusulanService.setPlacementDate(ds.getValidDate());
                penumpukanSusulanService.setBlNo(ds.getBlNo());
                penumpukanSusulanService.setValidDate(tanggalValidDate);
                penumpukanSusulanService.setRegistration(registration);
                penumpukanSusulanService.setPlanningVessel(registration.getPlanningVessel());
                penumpukanSusulanServiceFacade.create(penumpukanSusulanService);
                
                Integer idReefer = serviceReeferFacadeRemote.findValidasiPenumpukan(penumpukanSusulanService.getPlanningVessel().getNoPpkb(), penumpukanSusulanService.getContNo(), "DISCHARGE");
                boolean isOverSize = false;
                if ((!penumpukanSusulanService.getContStatus().equals("MTY") 
                        && penumpukanSusulanService.getOverSize().equals("TRUE")) 
                        || penumpukanSusulanService.isTwentyOneFeet().equals("TRUE")) {
                    isOverSize = true;
                }
                String penumpukanActivityCode = masterActivityFacadeRemote.translatePenumpukanActivityCode(
                        penumpukanSusulanService.getContStatus(), 
                        penumpukanSusulanService.getMasterContainerType().getIsoCode(), 
                        isOverSize, 
                        penumpukanSusulanService.getContSize(), idReefer > 0);

                perhitunganPenumpukanSusulan.setContNo(penumpukanSusulanService.getContNo());
                perhitunganPenumpukanSusulan.setMlo(penumpukanSusulanService.getMlo());
                perhitunganPenumpukanSusulan.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
                perhitunganPenumpukanSusulan.setRegistration(registration);
                perhitunganPenumpukanSusulan.setMasterActivity(activityFacadeRemote.find(penumpukanActivityCode));

                // set amount berdasarkan tipe pelayaran
                BigDecimal handlingDomesticCharge = BigDecimal.ZERO;
                BigDecimal handlingInternationalCharge = BigDecimal.ZERO;
                for (Object[] o1 : masterTarifContFacadeRemote.findAmountByActivity(penumpukanActivityCode)) {
                    if (o1[1].equals("IDR")) {
                        handlingDomesticCharge = (BigDecimal) o1[0];
                    } else {
                        handlingInternationalCharge = (BigDecimal) o1[0];
                    }
                }
                
                BigDecimal penumpukanCharge = BigDecimal.ZERO;
                if (registration.getPlanningVessel().getTipePelayaran().equals("d")) {
                    penumpukanCharge = handlingDomesticCharge;
                    perhitunganPenumpukanSusulan.setCurrCode("IDR");
                } else {
                    penumpukanCharge = handlingInternationalCharge;
                    perhitunganPenumpukanSusulan.setCurrCode("USD");
                }

                BigDecimal[] discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), penumpukanActivityCode);
                BigDecimal discountAsPercentage = discount[0];
                BigDecimal discountAsMoney = discount[1];

                if (discountAsMoney == null) {
                    discountAsMoney = penumpukanCharge.multiply(discountAsPercentage);
                }
                
                penumpukanCharge = penumpukanCharge.subtract(discountAsMoney);

                if (ds.getDg().equals("TRUE") && ds.getDgLabel().equals("TRUE")) {
                    penumpukanCharge = penumpukanCharge.multiply(BigDecimal.valueOf(2));
                } else if (ds.getDg().equals("TRUE") && ds.getDgLabel().equals("FALSE")) {
                    penumpukanCharge = penumpukanCharge.multiply(BigDecimal.valueOf(3));
                }

                perhitunganPenumpukanSusulan.setCharge(penumpukanCharge);
                perhitunganPenumpukanSusulan.setChargeM1(perhitunganPenumpukanSusulan.getCharge().multiply(BigDecimal.valueOf(penumpukanSusulanService.getMasa1())));
                perhitunganPenumpukanSusulan.setChargeM2(perhitunganPenumpukanSusulan.getCharge().multiply(BigDecimal.valueOf(penumpukanSusulanService.getMasa2())).multiply(new BigDecimal(2)));
                BigDecimal totalCharge = perhitunganPenumpukanSusulan.getChargeM1().add(perhitunganPenumpukanSusulan.getChargeM2());
                perhitunganPenumpukanSusulan.setTotalCharge(totalCharge);
                perhitunganPenumpukanSusulanFacade.create(perhitunganPenumpukanSusulan);

                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
                penumpukanSusulanService = new PenumpukanSusulanService();
                perhitunganPenumpukanSusulan = new PerhitunganPenumpukanSusulan();
            }
        }

        penumpukanSusulanServices = penumpukanSusulanServiceFacade.findPerhitungan(no_reg);
        BigDecimal t = BigDecimal.ZERO;
        for (Object[] o : getPenumpukanSusulanServices()) {
            t = t.add((BigDecimal) o[13]);
            this.language = (String) (o[15]);
            this.country = (String) (o[16]);
            this.symbol = (String) (o[14]);
        }
        setTotal(t);
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

        if (getAct().equals("bl")) {
            deliveryServices = deliveryServiceFacadeRemote.findByPpkbAndBlNoPenumpukanSusulan(registration.getPlanningVessel().getNoPpkb(), bl_no);
        } else {
            deliveryServices = deliveryServiceFacadeRemote.findByPpkbPenumpukanSusulan(registration.getPlanningVessel().getNoPpkb());
        }
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
            List<Object[]> penumpukanList = null;
            loggedIn = true;
            //input k batal nota
            registration = registrationFacade.find(invoice.getNoReg());
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

            invoiceFacade.edit(invoice);

            //find and update tabel-table relasi
            penumpukanList = penumpukanSusulanServiceFacade.findByListBatalNotaService(registration.getPlanningVessel().getNoPpkb(), no_reg);
            if (!penumpukanList.isEmpty()) {
                for (int i = 0; i < penumpukanList.size(); i++) {
                    penumpukanSusulanService = penumpukanSusulanServiceFacade.find(penumpukanList.get(i)[4]);
                    Integer idTumpuk = perhitunganPenumpukanSusulanFacade.findInvoice(penumpukanSusulanService.getContNo(), registration.getPlanningVessel().getNoPpkb(), no_reg);
                    perhitunganPenumpukanSusulan = perhitunganPenumpukanSusulanFacade.find(idTumpuk);

                    penumpukanSusulanServiceFacade.remove(penumpukanSusulanService);
                    perhitunganPenumpukanSusulanFacade.remove(perhitunganPenumpukanSusulan);
                }
            }
            disCancelInv = Boolean.TRUE;
            setPenumpukanSusulanServices(penumpukanSusulanServiceFacade.findPerhitungan(no_reg));
            setDeliveryServices(deliveryServiceFacadeRemote.findByPpkbPenumpukanSusulan(registration.getPlanningVessel().getNoPpkb()));

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

    public void handleDownloadTransactionRecap(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/perhitunganReceiving.pdf?noRegistrasi=" + no_reg + "&total=" + String.valueOf(total) + "&ppn=" + String.valueOf(getPpnPrint()) + "&materai=" + String.valueOf(getMateraiPrint()) + "&userId=" + getUserId() + "&mode=penumpukanSusulan")));
    }

    public void handleDownloadNota(ActionEvent event) {
        try {
            invoice = invoiceFacade.find(no_reg);

            //validasi print nota berulang
            if (invoice.getValidasiPrint().equals("FALSE")) {
                invoice = invoiceFacade.publishInvoice(invoice);
            }

            disPrint = invoice.getValidasiPrint().equals("TRUE") ? true : false;

            setPenumpukanSusulanServices(penumpukanSusulanServiceFacade.findPerhitungan(no_reg));

            if (invoice.getNoInvoice() != null && invoice.isCancelInvoice().equals("FALSE")) {
                /*cek apakah container sudah masuk gate, apabila sudah maka invoice tidak dapat di cancel*/
                disCancelInv = Boolean.FALSE;
                for (Object[] ob : penumpukanSusulanServices) {
                    deliveryService = deliveryServiceFacadeRemote.findByNoPpkbAndContNo((String) ob[18], (String) ob[1]);
                    if (serviceGateDeliveryFacadeRemote.findByContAndPpkb(deliveryService.getContNo(), deliveryService.getPlanningVessel().getNoPpkb()) == null) {
                        if (disCancelInv == false) {
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
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public void handleDownloadDetail(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/detail.pdf?no_reg=" + no_reg + "&tipe=" + registration.getMasterService().getServiceCode() + "&username=" + getUserId() + "&ppn=" + invoice.getPpn() + "&materai=" + String.valueOf(materaiPrint) + "&total=" + String.valueOf(total))));
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

    public List<Object[]> getPenumpukanSusulanServices() {
        return penumpukanSusulanServices;
    }

    public void setPenumpukanSusulanServices(List<Object[]> penumpukanSusulanServices) {
        this.penumpukanSusulanServices = penumpukanSusulanServices;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public PenumpukanSusulanService getPenumpukanSusulanService() {
        return penumpukanSusulanService;
    }

    public void setPenumpukanSusulanService(PenumpukanSusulanService penumpukanSusulanService) {
        this.penumpukanSusulanService = penumpukanSusulanService;
    }

    public DeliveryService getDs() {
        return ds;
    }

    public void setDs(DeliveryService ds) {
        this.ds = ds;
    }

    public List<Object[]> getDeliveryServices() {
        return deliveryServices;
    }

    public void setDeliveryServices(List<Object[]> deliveryServices) {
        this.deliveryServices = deliveryServices;
    }

    public PerhitunganPenumpukanSusulan getPerhitunganPenumpukanSusulan() {
        return perhitunganPenumpukanSusulan;
    }

    public void setPerhitunganPenumpukanSusulan(PerhitunganPenumpukanSusulan perhitunganPenumpukanSusulan) {
        this.perhitunganPenumpukanSusulan = perhitunganPenumpukanSusulan;
    }

    public String getNo_reg() {
        return no_reg;
    }

    public void setNo_reg(String no_reg) {
        this.no_reg = no_reg;
    }

    public Object[][] getPenumpukanSusulan() {
        return penumpukanSusulan;
    }

    public void setPenumpukanSusulan(Object[][] penumpukanSusulan) {
        this.penumpukanSusulan = penumpukanSusulan;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getBl_no() {
        return bl_no;
    }

    public void setBl_no(String bl_no) {
        this.bl_no = bl_no;
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

    public DeliveryService getDeliveryService() {
        return deliveryService;
    }

    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
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

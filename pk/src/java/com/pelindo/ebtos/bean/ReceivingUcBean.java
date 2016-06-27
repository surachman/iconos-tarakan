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
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDiscountFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPassGateFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganUpahBuruhFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcPerhitunganLiftFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcPerhitunganPenumpukanFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcPerhitunganStevedoringFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PerhitunganPassGate;
import com.pelindo.ebtos.model.db.PerhitunganUpahBuruh;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.UcPerhitunganLift;
import com.pelindo.ebtos.model.db.UcPerhitunganPenumpukan;
import com.pelindo.ebtos.model.db.UcPerhitunganStevedoring;
import com.pelindo.ebtos.model.db.UcReceivingService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterMaterai;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.model.db.master.MasterYard;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "receivingUcBean")
@ViewScoped
public class ReceivingUcBean implements Serializable {

    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterDiscountFacadeRemote masterDiscountFacadeRemote;
    @EJB
    private InvoiceFacadeRemote invoiceFacadeRemote;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacadeRemote;
    @EJB
    private RegistrationFacadeRemote registrationFacadeRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;
    @EJB
    private MasterPortFacadeRemote masterPortFacadeRemote;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacadeRemote;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private UcReceivingServiceFacadeRemote ucReceivingServiceFacadeRemote;
    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacadeRemote;
    @EJB
    private UcPerhitunganLiftFacadeRemote ucPerhitunganLiftFacadeRemote;
    @EJB
    private BatalNotaFacadeRemote batalNotaFacadeRemote;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacadeRemote;
    @EJB
    private PerhitunganPassGateFacadeRemote perhitunganPassGateFacadeRemote;
    @EJB
    private UcPerhitunganStevedoringFacadeRemote ucPerhitunganStevedoringFacadeRemote;
    @EJB
    private UcPerhitunganPenumpukanFacadeRemote ucPerhitunganPenumpukanFacadeRemote;
    @EJB
    private PerhitunganUpahBuruhFacadeRemote perhitunganUpahBuruhFacadeRemote;

    private List<Object[]> registrations;
    private List<Object[]> ucServices;
    private List<Object[]> uncontainerizedList;
    private List<MasterYard> masterYards;
    private List<MasterPort> masterPorts;
    private List<Object[]> masterCommoditys;
    private BatalNota batalNota;
    private Invoice invoice;
    private Registration registration;
    private PlanningVessel planningVessel;
    private MasterMaterai masterMaterai;
    private MasterCustomer masterCustomer;
    private MasterCurrency masterCurrency;
    private UcReceivingService receivingUc;
    private UncontainerizedService uncontainerizedService;
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal ppnPrint = BigDecimal.ZERO;
    private BigDecimal ppn = BigDecimal.ZERO;
    private BigDecimal materaiPrint = BigDecimal.ZERO;
    private BigDecimal additionalCharge = BigDecimal.ZERO;
    private String userId = null;
    private String mode = null;
    private String implementedPortCode;
    private Boolean disCancelInv = true;
    private Boolean disabPlanning = true;
    private Boolean isEdit = false;
    private Boolean disPrint = true;
    private Boolean disKredit = false;
    private Boolean disDetail = false;
    private String selectedPort1;
    private String selectedPort2;
    private Boolean isSimpatReady;

    /** Creates a new instance of ReceivingUc */
    public ReceivingUcBean() {}

    @PostConstruct
    private void construct() {
        registration = new Registration();
        masterCustomer = new MasterCustomer();
        invoice = new Invoice();
        invoice.setPaymentType("CASH");
        masterMaterai = new MasterMaterai();
        receivingUc = new UcReceivingService();
        planningVessel = new PlanningVessel();
        uncontainerizedService = new UncontainerizedService();
        isEdit = false;
        batalNota = new BatalNota();

        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        userId = loginSessionBean.getName();

        masterCommoditys = masterCommodityFacadeRemote.findMasterCommoditys();
//        masterPorts = masterPortFacadeRemote.findAll();
        masterYards = masterYardFacadeRemote.findAll();
        registrations = registrationFacadeRemote.findRegistrationRecUC();
        implementedPortCode = masterSettingAppFacade.findImplementedPortCode();
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
    }

    public void handleSelectRegistration(ActionEvent event) {
        String noReg = (String) event.getComponent().getAttributes().get("noReg");
        viewData(noReg);
    }

    public void viewData(String noReg) {
        registration = registrationFacadeRemote.find(noReg);
        ucServices = ucReceivingServiceFacadeRemote.findByUcReceivingPerhitungan(noReg);
        planningVessel = planningVesselFacadeRemote.find(registration.getPlanningVessel().getNoPpkb());
        uncontainerizedList = uncontainerizedServiceFacadeRemote.findByEntryReceivingUcLoad(registration.getPlanningVessel().getNoPpkb());

        disCancelInv = false;
        disDetail = false;
        total = BigDecimal.ZERO;
        additionalCharge = BigDecimal.ZERO;

        for (Object[] o : ucServices) {
            total = total.add((BigDecimal) (o[8])).add((BigDecimal) (o[9])).add((BigDecimal) (o[10])).add((BigDecimal) (o[11])).add((BigDecimal) (o[12]));
            additionalCharge = additionalCharge.add((BigDecimal) (o[15]));
            disCancelInv = disCancelInv || (Boolean) (o[14]);
        }

        //ambil nilai ppn
        MasterSettingApp settingApp = masterSettingAppFacade.find("ppn");
        ppnPrint = settingApp.getValueFloat();
        ppn = total.multiply(ppnPrint);

        if (registration.getPlanningVessel().getTipePelayaran().equals("d")) {
            masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        } else {
            masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        }

        List<Object[]> materai = masterMateraiFacadeRemote.findByCurr(masterCurrency.getCurrCode());

        if (total.compareTo((BigDecimal) materai.get(1)[2]) == -1) {
            materaiPrint = (BigDecimal) materai.get(0)[1];
        } else if (total.compareTo((BigDecimal) materai.get(2)[2]) == -1) {
            materaiPrint = (BigDecimal) materai.get(1)[1];
        } else {
            materaiPrint = (BigDecimal) materai.get(2)[1];
        }

        invoice = registration.getInvoice();

        if (invoice != null) {
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
            ucPerhitunganStevedoringFacadeRemote.deleteByNoReg(registration.getNoReg());
            ucPerhitunganLiftFacadeRemote.deleteByNoReg(registration.getNoReg());
            ucPerhitunganPenumpukanFacadeRemote.deleteByNoReg(registration.getNoReg());
            perhitunganPassGateFacadeRemote.deleteByNoReg(registration.getNoReg());
            uncontainerizedServiceFacadeRemote.resetPlannedUcServiceByNoReg(registration.getNoReg());
            uncontainerizedServiceFacadeRemote.deleteUnplannedUcServiceByNoReg(registration.getNoReg());
            ucReceivingServiceFacadeRemote.deleteByNoReg(registration.getNoReg());
            perhitunganUpahBuruhFacadeRemote.deleteByNoReg(registration.getNoReg());

            disCancelInv = Boolean.TRUE;
            ucServices = ucReceivingServiceFacadeRemote.findByUcReceivingPerhitungan(registration.getNoReg());

            if (masterSettingAppFacade.isPaymentBankingEnabled())
                try {
                    bankingSyncFacadeRemote.cancelInvoice(noInvoice);
                } catch (RuntimeException e) {
                    System.err.println(FacesHelper.getLocaleMessage("application.integration.banking.failed", FacesContext.getCurrentInstance()));
                    FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.integration.banking.failed");
                }

            loggedIn = true;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (EJBException e) {
            e.printStackTrace();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handlePlanning(ActionEvent event) {
        this.disabPlanning = Boolean.TRUE;
    }

    public void handleDownloadTemp(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/perhitunganReceiving.pdf?mode=" + "ReceivingUc"
                + "&noRegistrasi=" + registration.getNoReg()
                + "&status=" + invoice.getPaymentType()
                + "&ppn=" + String.valueOf(getPpnPrint())
                + "&materai=" + String.valueOf(getMateraiPrint())
                + "&userId=" + getUserId()
                + "&total=" + String.valueOf(total))));
    }

    public void handleDownloadNota(ActionEvent event) {
        invoice = invoiceFacadeRemote.find(registration.getNoReg());

        //validasi print
        if (invoice.getValidasiPrint().equals("FALSE")) {
            invoice = invoiceFacadeRemote.publishInvoice(invoice);
        }

        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true);
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/invoice.pdf?tipe=" + registration.getMasterService().getServiceCode()
                + "&no_reg=" + registration.getNoReg()
                + "&detail=" + disDetail.toString()
                + "&to=" + invoice.getTotalTagihan().toString()
                + "&curr=" + masterCurrency.getCountry()
                + "&tipe=" + registration.getMasterService().getServiceCode()
                + "&username=" + getUserId())));

        disPrint = invoice.getValidasiPrint().equals("TRUE") ? true : false;
    }

    public void handleDownloadDetail(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/detail.pdf?tipe=" + registration.getMasterService().getServiceCode()
                + "&no_reg=" + registration.getNoReg()
                + "&username=" + getUserId() + "&ppn=" + String.valueOf(ppnPrint) + "&materai=" + String.valueOf(materaiPrint) + "&total=" + String.valueOf(total))));
    }

    public void handleSubmit(ActionEvent event) {
        String status;
        Boolean loggedIn = true;
        invoice.setNoReg(registration.getNoReg());
        invoice.setRegistration(registration);
        invoice.setPaymentStatus("UNPAID");
        invoice.setJumlahTagihan(total);
        invoice.setPpn(ppn);
        invoice.setMaterai(materaiPrint);
        invoice.setAdditionalCharge(additionalCharge);
        invoice.setTotalTagihan(invoice.getJumlahTagihan().add(invoice.getMaterai()).add(invoice.getPpn()).add(invoice.getAdditionalCharge()));
        invoice.setMasterCurrency(masterCurrency);
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

        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
        disPrint = false;
    }

    public void handleSelect(ActionEvent event) {
        String job_slip = (String) event.getComponent().getAttributes().get("jobSlip");
        receivingUc = ucReceivingServiceFacadeRemote.find(job_slip);
        uncontainerizedService = receivingUc.getUncontainerizedService();
        
        this.isEdit = Boolean.TRUE;
    }

    public void handleDelete(ActionEvent event) {
        try {
            ucPerhitunganLiftFacadeRemote.deleteByJobslip(receivingUc.getJobslip());
            ucPerhitunganPenumpukanFacadeRemote.deleteByJobslip(receivingUc.getJobslip());
            ucPerhitunganStevedoringFacadeRemote.deleteByJobslip(receivingUc.getJobslip());
            perhitunganPassGateFacadeRemote.deleteByJobSlip(receivingUc.getJobslip());
            perhitunganUpahBuruhFacadeRemote.deleteByJobslip(receivingUc.getJobslip());

            if (uncontainerizedService.getStatus().equals("UNPLANNED")) {
                uncontainerizedServiceFacadeRemote.remove(uncontainerizedService);
            } else {
                uncontainerizedService.setStatus("PLANNING");
                uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
            }

            ucReceivingServiceFacadeRemote.remove(receivingUc);

            viewData(registration.getNoReg());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
        }

    }

    public void handleAdd(ActionEvent event) {
        this.isEdit = false;
        receivingUc = new UcReceivingService();
        uncontainerizedService = new UncontainerizedService();
        uncontainerizedService.setOperation("LOAD");
        uncontainerizedService.setLoadPort(implementedPortCode);
        
        String port_code = masterSettingAppFacade.findImplementedPortCode();
        selectedPort1 = masterPortFacadeRemote.find(port_code).getName();
        selectedPort2 = "";
    }

    public void handleSelectPlanningUC(ActionEvent event) {
        Integer id = (Integer) event.getComponent().getAttributes().get("idd");
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(id);
    }

    public void handleConfirm(ActionEvent event) {
        boolean loggedIn = false;

        try {
            
                MasterPort port1 = masterPortFacadeRemote.findMasterPortByName(selectedPort1);
                uncontainerizedService.setLoadPort(port1.getPortCode());
                MasterPort port2 = masterPortFacadeRemote.findMasterPortByName(selectedPort2);
                uncontainerizedService.setDischPort(port2.getPortCode());
            
            String jobSlip = iDGeneratorFacade.generateJobSlipID("13");
            
            receivingUc.setJobslip(jobSlip);
            receivingUc.setMasa1(0);
            receivingUc.setMasa2(0);
            receivingUc.setValidDate(planningVessel.getCloseRec());
            receivingUc.setReceivingDate(Calendar.getInstance().getTime());
            receivingUc.setRegistration(registration);

            String handlingActivityCode = masterActivityFacadeRemote.translateHandlingUcActivityCode(uncontainerizedService.getWeight(), uncontainerizedService.getCrane());
            String loloActivityCode = masterActivityFacadeRemote.translateLoLoUcActivityCode(uncontainerizedService.getWeight());
            String penumpukanActivityCode = masterActivityFacadeRemote.translatePenumpukanUcActivityCode();
            String passGateActivityCode = masterActivityFacadeRemote.translatePassGateUcActivityCode();
            String upahBuruhActivityCode = masterActivityFacadeRemote.translateUbahBuruhUcActivityCode();
            
            MasterActivity passGateMasterActivity = masterActivityFacadeRemote.find(passGateActivityCode);
            MasterActivity upahBuruhMasterActivity = masterActivityFacadeRemote.find(upahBuruhActivityCode);
            MasterCurrency idrMasterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();

            BigDecimal penumpukanCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(idrMasterCurrency.getCurrCode(), penumpukanActivityCode);
            BigDecimal handlingCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(idrMasterCurrency.getCurrCode(), handlingActivityCode);
            BigDecimal loloCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(idrMasterCurrency.getCurrCode(), loloActivityCode);
            BigDecimal passGateCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(idrMasterCurrency.getCurrCode(), passGateActivityCode);
            BigDecimal upahBuruhCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(idrMasterCurrency.getCurrCode(), upahBuruhActivityCode);
            BigDecimal jasaDermagaCharge = BigDecimal.ZERO;

            BigDecimal[] discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), handlingActivityCode);
            BigDecimal discountAsPercentage = discount[0];
            BigDecimal discountAsMoney = discount[1];

            if (discountAsMoney == null) {
                discountAsMoney = handlingCharge.multiply(discountAsPercentage);
            }

            handlingCharge = handlingCharge.subtract(discountAsMoney);

            discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), passGateActivityCode);
            discountAsPercentage = discount[0];
            discountAsMoney = discount[1];

            if (discountAsMoney == null) {
                discountAsMoney = passGateCharge.multiply(discountAsPercentage);
            }

            passGateCharge = passGateCharge.subtract(discountAsMoney);

            discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), penumpukanActivityCode);
            discountAsPercentage = discount[0];
            discountAsMoney = discount[1];

            if (discountAsMoney == null) {
                discountAsMoney = penumpukanCharge.multiply(discountAsPercentage);
            }

            penumpukanCharge = penumpukanCharge.subtract(discountAsMoney);

            discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), loloActivityCode);
            discountAsPercentage = discount[0];
            discountAsMoney = discount[1];

            if (discountAsMoney == null) {
                discountAsMoney = loloCharge.multiply(discountAsPercentage);
            }

            loloCharge = loloCharge.subtract(discountAsMoney);

            PerhitunganPassGate perhitunganPassGate = new PerhitunganPassGate();
            perhitunganPassGate.setJobSlip(receivingUc.getJobslip());
            perhitunganPassGate.setMasterActivity(passGateMasterActivity);
            perhitunganPassGate.setPlanningVessel(registration.getPlanningVessel());
            perhitunganPassGate.setRegistration(registration);
            perhitunganPassGate.setCharge(passGateCharge);
            perhitunganPassGate.setJumlah(1);
            perhitunganPassGate.setTotalCharge(perhitunganPassGate.getCharge().multiply(new BigDecimal(perhitunganPassGate.getJumlah())));

            UcPerhitunganStevedoring ucPerhitunganStevedoring = new UcPerhitunganStevedoring();
            ucPerhitunganStevedoring.setNoReg(registration.getNoReg());
            ucPerhitunganStevedoring.setJobslip(receivingUc.getJobslip());
            ucPerhitunganStevedoring.setActivityCode(handlingActivityCode);
            ucPerhitunganStevedoring.setCurrCode(masterCurrency.getCurrCode());
            ucPerhitunganStevedoring.setOperation("LOAD");
            ucPerhitunganStevedoring.setJasaDermaga(jasaDermagaCharge);
            ucPerhitunganStevedoring.setCharge(handlingCharge.add(ucPerhitunganStevedoring.getJasaDermaga()));

            UcPerhitunganLift ucPerhitunganLift = new UcPerhitunganLift();
            ucPerhitunganLift.setNoReg(registration.getNoReg());
            ucPerhitunganLift.setJobslip(receivingUc.getJobslip());
            ucPerhitunganLift.setActivityCode(loloActivityCode);
            ucPerhitunganLift.setCurrCode(masterCurrency.getCurrCode());
            ucPerhitunganLift.setOperation("LOAD");
            ucPerhitunganLift.setCharge(loloCharge);

            PerhitunganUpahBuruh perhitunganUbahBuruh = new PerhitunganUpahBuruh();
            perhitunganUbahBuruh.setRegistration(registration);
            perhitunganUbahBuruh.setJobslip(receivingUc.getJobslip());
            perhitunganUbahBuruh.setMasterActivity(upahBuruhMasterActivity);
            perhitunganUbahBuruh.setMasterCurrency(idrMasterCurrency);

            BigDecimal tonage = new BigDecimal(uncontainerizedService.getWeight() / 1000);

            if (tonage.compareTo(uncontainerizedService.getCubication()) > 0) {
                upahBuruhCharge = upahBuruhCharge.multiply(tonage);
            } else {
                upahBuruhCharge = upahBuruhCharge.multiply(uncontainerizedService.getCubication());
            }
             
            perhitunganUbahBuruh.setCharge(upahBuruhCharge);

            UcPerhitunganPenumpukan ucPerhitunganPenumpukan = new UcPerhitunganPenumpukan();
            ucPerhitunganPenumpukan.setRegistration(registration);
            ucPerhitunganPenumpukan.setJobslip(receivingUc.getJobslip());
            ucPerhitunganPenumpukan.setOperation("LOAD");
            ucPerhitunganPenumpukan.setActivityCode(penumpukanActivityCode);
            ucPerhitunganPenumpukan.setCurrCode(masterCurrency.getCurrCode());
            ucPerhitunganPenumpukan.setMasa1((short) 1);
            ucPerhitunganPenumpukan.setMasa2((short) 0);

          

            if (tonage.compareTo(uncontainerizedService.getCubication()) > 0) {
                penumpukanCharge = penumpukanCharge.multiply(tonage);
            } else {
                penumpukanCharge = penumpukanCharge.multiply(uncontainerizedService.getCubication());
            }

            ucPerhitunganPenumpukan.setCharge(penumpukanCharge);
            ucPerhitunganPenumpukan.setChargeMasa1(ucPerhitunganPenumpukan.getCharge().multiply(BigDecimal.valueOf(ucPerhitunganPenumpukan.getMasa1())));
            ucPerhitunganPenumpukan.setChargeMasa2(ucPerhitunganPenumpukan.getCharge().multiply(BigDecimal.valueOf(ucPerhitunganPenumpukan.getMasa2())).multiply(BigDecimal.valueOf(2)));
            ucPerhitunganPenumpukan.setCharge(ucPerhitunganPenumpukan.getChargeMasa1().add(ucPerhitunganPenumpukan.getChargeMasa2()));

            if (registration.getPlanningVessel().getIsLoadPortToPort()) {
                ucPerhitunganStevedoringFacadeRemote.create(ucPerhitunganStevedoring);
                ucPerhitunganPenumpukanFacadeRemote.create(ucPerhitunganPenumpukan);
                perhitunganUpahBuruhFacadeRemote.create(perhitunganUbahBuruh);
            }

            ucPerhitunganLiftFacadeRemote.create(ucPerhitunganLift);
            receivingUc.setUncontainerizedService(uncontainerizedService);
            ucReceivingServiceFacadeRemote.create(receivingUc);
            perhitunganPassGateFacadeRemote.create(perhitunganPassGate);

            viewData(registration.getNoReg());

            loggedIn = true;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exeption caught", re);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        }

        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
        uncontainerizedService = new UncontainerizedService();
        receivingUc = new UcReceivingService();
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

    public String getSelectedPort1() {
        return selectedPort1;
    }

    public void setSelectedPort1(String selectedPort1) {
        this.selectedPort1 = selectedPort1;
    }

    public String getSelectedPort2() {
        return selectedPort2;
    }

    public void setSelectedPort2(String selectedPort2) {
        this.selectedPort2 = selectedPort2;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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

    public List<Object[]> getUcServices() {
        return ucServices;
    }

    public void setUcServices(List<Object[]> UcServices) {
        this.ucServices = UcServices;
    }

    public List<Object[]> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Object[]> registrations) {
        this.registrations = registrations;
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

    public MasterMaterai getMasterMaterai() {
        return masterMaterai;
    }

    public void setMasterMaterai(MasterMaterai masterMaterai) {
        this.masterMaterai = masterMaterai;
    }

    public UcReceivingService getReceivingUc() {
        return receivingUc;
    }

    public void setReceivingUc(UcReceivingService receivingUc) {
        this.receivingUc = receivingUc;
    }

    public Boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Boolean isEdit) {
        this.isEdit = isEdit;
    }

    public UncontainerizedService getUncontainerizedService() {
        return uncontainerizedService;
    }

    public void setUncontainerizedService(UncontainerizedService uncontainerizedService) {
        this.uncontainerizedService = uncontainerizedService;
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    public List<Object[]> getUncontainerizedList() {
        return uncontainerizedList;
    }

    public void setUncontainerizedList(List<Object[]> UncontainerizedList) {
        this.uncontainerizedList = UncontainerizedList;
    }

    public List<Object[]> getMasterCommoditys() {
        return masterCommoditys;
    }

    public void setMasterCommoditys(List<Object[]> masterCommoditys) {
        this.masterCommoditys = masterCommoditys;
    }

    public List<MasterPort> getMasterPorts() {
        return masterPorts;
    }

    public void setMasterPorts(List<MasterPort> masterPorts) {
        this.masterPorts = masterPorts;
    }

    public List<MasterYard> getMasterYards() {
        return masterYards;
    }

    public void setMasterYards(List<MasterYard> masterYards) {
        this.masterYards = masterYards;
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

    public Boolean getDisabPlanning() {
        return disabPlanning;
    }

    public void setDisabPlanning(Boolean disabPlanning) {
        this.disabPlanning = disabPlanning;
    }

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    public BigDecimal getPpn() {
        return ppn;
    }

    public BigDecimal getAdditionalCharge() {
        return additionalCharge;
    }

    public Boolean getIsSimpatReady() {
        return isSimpatReady;
    }
}

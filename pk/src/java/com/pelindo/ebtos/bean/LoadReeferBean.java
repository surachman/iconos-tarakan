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
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganMonitoringFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPluggingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReeferLoadServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReeferFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PerhitunganMonitoring;
import com.pelindo.ebtos.model.db.PerhitunganPlugging;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.ReeferLoadService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceReefer;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
@ManagedBean(name = "loadReeferBean")
@ViewScoped
public class LoadReeferBean implements Serializable {
    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacade;
    @EJB
    private ServiceGateReceivingFacadeRemote serviceGateReceivingFacade;
    @EJB
    private BatalNotaFacadeRemote batalNotaFacade;
    @EJB
    private ReceivingServiceFacadeRemote receivingServiceFacade;
    @EJB
    private ServiceReeferFacadeRemote serviceReeferFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacade;
    @EJB
    private InvoiceFacadeRemote invoiceFacade;
    @EJB
    private PerhitunganMonitoringFacadeRemote perhitunganMonitoringFacade;
    @EJB
    private PerhitunganPluggingFacadeRemote perhitunganPluggingFacade;
    @EJB
    private ReeferLoadServiceFacadeRemote reeferLoadServiceFacade;
    @EJB
    private RegistrationFacadeRemote registrationFacade;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacadeRemote;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacadeRemote;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private PlanningContLoadFacadeRemote planningContLoadFacadeRemote;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;
    
    private List<Object[]> registrations;
    private List<Object[]> reeferLoadServices;
    private List<Object[]> countableReefers;
    private BatalNota batalNota;
    private Invoice invoice;
    private LoginSessionBean loginSessionBean;
    private MasterCurrency masterCurrency;
    private PerhitunganMonitoring perhitunganMonitoring;
    private PerhitunganPlugging perhitunganPlugging;
    private Registration registration;
    private ReeferLoadService reeferLoadService;
    private Object[][] selectedCountableReefers;
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal ppnPrint = BigDecimal.ZERO;
    private BigDecimal materaiPrint = BigDecimal.ZERO;
    private BigDecimal temperature;
    private Boolean disPrint = true;
    private Boolean disKredit = false;
    private Boolean disDetail = true;
    private Boolean disCancelInv = true;
    private Boolean isPerencanaDanOperasi = false;
    private Integer plugShiftPlanning;
    private String no_reg;
    private String blno;
    private Boolean isSimpatReady;

    /** Creates a new instance of LoadReeferBean */
    public LoadReeferBean() {}

    @PostConstruct
    private void construct(){
        invoice = new Invoice();
        batalNota = new BatalNota();
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        selectedCountableReefers = new Object[0][0];
        countableReefers = new ArrayList<Object[]>();

        loginSessionBean = LoginSessionBean.getCurrentInstance();

        if (loginSessionBean != null &&
                loginSessionBean.getGroups() != null &&
                !loginSessionBean.getGroups().isEmpty() &&
                (loginSessionBean.getGroups().contains("Perencana dan Operasi") || loginSessionBean.getGroups().contains("Spv Perencanaan dan Operasi"))) {
            isPerencanaDanOperasi = true;
        }
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
    }

    public void handleShowRegistrations(ActionEvent event) {
        if (isPerencanaDanOperasi) {
            registrations = registrationFacade.findRegistrationReeferLoadServiceOnly();
        } else {
            registrations = registrationFacade.findRegistrationReeferLoad();
        }
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(no_reg);
        viewData();
    }

    public void viewData() {
        disCancelInv = Boolean.TRUE;
        reeferLoadServices = reeferLoadServiceFacade.findPerhitungan(no_reg);
        disDetail = false;
        
        total = BigDecimal.ZERO;
        for (Object[] o : getReeferLoadServices()) {
            if (o[8] != null || o[10] != null) {
                total = total.add((BigDecimal) o[8]).add((BigDecimal) o[10]);
            }
        }

        //ambil nilai ppn
        MasterSettingApp settingApp = masterSettingAppFacade.find("ppn");
        ppnPrint = settingApp.getValueFloat();

        if (registration.getPlanningVessel().getTipePelayaran().equals("d")) {
            masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        } else {
            masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        }

        List<Object[]> materai = masterMateraiFacade.findByCurr(masterCurrency.getCurrCode());
        
        if (total.compareTo((BigDecimal) materai.get(1)[2]) == -1) { /* jumlah tagihan < nilai minimum */
            setMateraiPrint((BigDecimal) materai.get(0)[1]);
        } else if (total.compareTo((BigDecimal) materai.get(2)[2]) == -1) {
            setMateraiPrint((BigDecimal) materai.get(1)[1]);
        } else {
            setMateraiPrint((BigDecimal) materai.get(2)[1]);
        }

        if (blno != null && !isPerencanaDanOperasi) {
            if (blno.equals("")) {
                handleFindAllBl(null);
            } else {
                handleFindBl(null);
            }
        } 
        
        invoice = invoiceFacade.find(no_reg);

        if (invoice != null) {
            if (invoice.getValidasiPrint().equals("TRUE")) {
                disPrint = true;
            } else {
                disPrint = false;
            }

            if (invoice.getPaymentType().equalsIgnoreCase("KREDIT")) {
                disKredit = true;
            } else {
                disKredit = false;
            }

            if (invoice.getNoInvoice() != null && invoice.isCancelInvoice().equals("FALSE")) {
                /*cek apakah container sudah masuk gate, apabila sudah maka invoice tidak dapat di cancel*/
                disCancelInv = Boolean.FALSE;
                for (Object[] ob : reeferLoadServices) {
                    reeferLoadService = reeferLoadServiceFacade.find(ob[0]);
                    if (serviceGateReceivingFacade.findByContPpkb(reeferLoadService.getContNo(), reeferLoadService.getPlanningVessel().getNoPpkb()) == null) {
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

            if (isPerencanaDanOperasi) {
                invoice.setPaymentType("KREDIT");
            } else {
                invoice.setPaymentType("CASH");
            }
            
            disPrint = true;
            disKredit = true;
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
        reeferLoadService = new ReeferLoadService();
        reeferLoadService.setMasterContainerType(new MasterContainerType());
        reeferLoadService.setMasterCommodity(new MasterCommodity());
        selectedCountableReefers = new Object[0][0];
        countableReefers = new ArrayList<Object[]>();
        plugShiftPlanning = null;

        if (isPerencanaDanOperasi) {
            countableReefers = planningContLoadFacadeRemote.findCountableLoadReefers(registration.getPlanningVessel().getNoPpkb());
            selectedCountableReefers = countableReefers.toArray(selectedCountableReefers);
        }
    }

    public void handleSave(ActionEvent event) {
        int successfullySaved = 0;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        for (Object[] selectedCountableReefer: selectedCountableReefers) {
            reeferLoadService = new ReeferLoadService();
            reeferLoadService.setTemperature(temperature);

            if (isPerencanaDanOperasi) {
                PlanningContLoad planningContLoad = planningContLoadFacadeRemote.findByNoPpkbAndContNoExcludeCancelLoading(registration.getPlanningVessel().getNoPpkb(), (String) selectedCountableReefer[1]);
                reeferLoadService.setContNo(planningContLoad.getContNo());
                reeferLoadService.setMlo(planningContLoad.getMlo());
                reeferLoadService.setContSize(planningContLoad.getContSize());
                reeferLoadService.setContGross(planningContLoad.getContGross());
                reeferLoadService.setContStatus(planningContLoad.getContStatus());
                reeferLoadService.setOverSize(planningContLoad.getOverSize());
                reeferLoadService.setDg(planningContLoad.getDg());
                reeferLoadService.setDgLabel(planningContLoad.getDgLabel());
                reeferLoadService.setMasterCommodity(planningContLoad.getMasterCommodity());
                reeferLoadService.setMasterContainerType(planningContLoad.getMasterContainerType());
            } else {
                ReceivingService receivingService = receivingServiceFacade.find(selectedCountableReefer[0]);
                reeferLoadService.setContNo(receivingService.getContNo());
                reeferLoadService.setMlo(receivingService.getMlo());
                reeferLoadService.setContSize(receivingService.getContSize());
                reeferLoadService.setContGross(receivingService.getContGross());
                reeferLoadService.setContStatus(receivingService.getContStatus());
                reeferLoadService.setOverSize(receivingService.getOverSize());
                reeferLoadService.setDg(receivingService.getDg());
                reeferLoadService.setDgLabel(receivingService.getDgLabel());
                reeferLoadService.setMasterCommodity(receivingService.getMasterCommodity());
                reeferLoadService.setMasterContainerType(receivingService.getMasterContainerType());
            }

            ServiceReefer serviceReefer = serviceReeferFacade.findByCountableLoadReefer(reeferLoadService.getContNo(), registration.getPlanningVessel().getNoPpkb());
            String service = (String) selectedCountableReefer[8];

            if (service != null && service.equalsIgnoreCase("Receiving")) {
                ReeferLoadService lastReeferLoad = reeferLoadServiceFacade.findValidReeferByNoPpkbAndContNo(registration.getPlanningVessel().getNoPpkb(), reeferLoadService.getContNo());
                Date validDate = (Date) selectedCountableReefer[9];

                if (lastReeferLoad == null || validDate == null) {
                    continue;
                }

                Long k = serviceReefer.getPlugOff().getTime() - validDate.getTime();
                int shiftPlan = (int) Math.ceil(Double.parseDouble(String.valueOf(k * 24)) / (8 * 86400000));

                if (shiftPlan < 1) {
                    shiftPlan = 1;
                }

                reeferLoadService.setPlugOnDate(serviceReefer.getPlugOn());
                reeferLoadService.setShiftPlanning(Short.parseShort(String.valueOf(shiftPlan)));

            } else if (serviceReefer == null || serviceReefer.getPlugOn() == null) {
                if (plugShiftPlanning == null) {
                    FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", FacesHelper.getLocaleMessage("application.save.failed", facesContext), "dsFormDialogD:shift");
                    continue;
                }
                
                reeferLoadService.setShiftPlanning(plugShiftPlanning.shortValue());
            } else if (serviceReefer.getPlugOff() == null) {
                Date now = Calendar.getInstance().getTime();
                Long k = now.getTime() - serviceReefer.getPlugOn().getTime();
                int shiftPlan = (int) Math.ceil(Double.parseDouble(String.valueOf(k * 24)) / (8 * 86400000));
                
                if (shiftPlan < 1) {
                    shiftPlan = 1;
                }

                reeferLoadService.setPlugOnDate(serviceReefer.getPlugOn());
                reeferLoadService.setShiftPlanning(Short.parseShort(String.valueOf(shiftPlan)));
            } else {
                Long k = serviceReefer.getPlugOff().getTime() - serviceReefer.getPlugOn().getTime();
                int shiftPlan = (int) Math.ceil(Double.parseDouble(String.valueOf(k * 24)) / (8 * 86400000));
                
                if (shiftPlan < 1) {
                    shiftPlan = 1;
                }

                reeferLoadService.setPlugOnDate(serviceReefer.getPlugOn());
                reeferLoadService.setShiftPlanning(Short.parseShort(String.valueOf(shiftPlan)));
            }
            
            reeferLoadService.setRegistration(registration);
            reeferLoadService.setPlanningVessel(registration.getPlanningVessel());
            handleConfirm();
            successfullySaved++;
        }

        if (successfullySaved < selectedCountableReefers.length) {
            FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.some_data_fail_to_saved", facesContext), successfullySaved, selectedCountableReefers.length), null);
        } else if (successfullySaved == selectedCountableReefers.length) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } else if (successfullySaved == 0){
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.save.failed");
        }
        
        handleAdd(event);
        viewData();
    }

    public void handleConfirm() {
        boolean loggedIn = false;
        if (reeferLoadService.getContNo() == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.transaction.notcompleted");
        } else {
            if (reeferLoadService.getJobSlip() == null) {
                reeferLoadService.setJobSlip(iDGeneratorFacade.generateJobSlipID("16"));
            }

            String pluggingActivityCode = masterActivityFacadeRemote.translatePluggingActivityCode(reeferLoadService.getContSize());
            String monitoringActivityCode = masterActivityFacadeRemote.translateMonitoringActivityCode(reeferLoadService.getContSize());
            MasterActivity pluggingMasterActivity = masterActivityFacadeRemote.find(pluggingActivityCode);
            MasterActivity monitoringMasterActivity = masterActivityFacadeRemote.find(monitoringActivityCode);
            BigDecimal pluggingCharge = masterTarifContFacadeRemote.findChargeAfterDiscount(masterCurrency.getCurrCode(), pluggingActivityCode, registration.getMasterCustomer().getCustCode());
            BigDecimal monitoringCharge = masterTarifContFacadeRemote.findChargeAfterDiscount(masterCurrency.getCurrCode(), monitoringActivityCode, registration.getMasterCustomer().getCustCode());
            
            perhitunganPlugging = new PerhitunganPlugging();
            perhitunganPlugging.setRegistration(registration);
            perhitunganPlugging.setContNo(reeferLoadService.getContNo());
            perhitunganPlugging.setMlo(reeferLoadService.getMlo());
            perhitunganPlugging.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
            perhitunganPlugging.setMasterActivity(pluggingMasterActivity);
            perhitunganPlugging.setCurrCode(masterCurrency.getCurrCode());
            perhitunganPlugging.setCharge(pluggingCharge);
            perhitunganPlugging.setTotalCharge(perhitunganPlugging.getCharge().multiply(BigDecimal.valueOf(reeferLoadService.getShiftPlanning())));

            perhitunganMonitoring = new PerhitunganMonitoring();
            perhitunganMonitoring.setRegistration(registration);
            perhitunganMonitoring.setContNo(reeferLoadService.getContNo());
            perhitunganMonitoring.setMlo(reeferLoadService.getMlo());
            perhitunganMonitoring.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
            perhitunganMonitoring.setMasterActivity(monitoringMasterActivity);
            perhitunganMonitoring.setCurrCode(masterCurrency.getCurrCode());
            perhitunganMonitoring.setCharge(monitoringCharge);;
            perhitunganMonitoring.setTotalCharge(perhitunganMonitoring.getCharge().multiply(BigDecimal.valueOf(reeferLoadService.getShiftPlanning())));

            perhitunganPluggingFacade.edit(perhitunganPlugging);
            reeferLoadServiceFacade.edit(reeferLoadService);
            perhitunganMonitoringFacade.edit(perhitunganMonitoring);
            loggedIn = true;
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
        }
    }

    public void handleSelect(ActionEvent event) {
        String no_cont = (String) event.getComponent().getAttributes().get("noCont");
        String idDS = reeferLoadServiceFacade.findInvoice(no_cont, getRegistration().getPlanningVessel().getNoPpkb(), no_reg);
        reeferLoadService = reeferLoadServiceFacade.find(idDS);
        perhitunganMonitoring = perhitunganMonitoringFacade.findByContNoAndNoReg(no_cont, no_reg);
        perhitunganPlugging = perhitunganPluggingFacade.findByContNoAndNoReg(no_cont, no_reg);
    }

    public void handleSubmit(ActionEvent event) {
        Boolean loggedIn = true;
        invoice.setNoReg(no_reg);
        invoice.setRegistration(registration);
        invoice.setPaymentStatus("UNPAID");
        invoice.setJumlahTagihan(total);
        invoice.setValidasiPrint("FALSE");

        //ambil nilai ppn dari master setting app
        MasterSettingApp settingApp = masterSettingAppFacade.find("ppn");
        setPpnPrint(settingApp.getValueFloat());

        BigDecimal ppn = invoice.getJumlahTagihan().multiply(settingApp.getValueFloat());
        invoice.setPpn(ppn);

        List<Object[]> materai = new ArrayList<Object[]>();
        if (registration.getPlanningVessel().getTipePelayaran().equals("d")) {
            masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        } else {
            masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
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

    public void handleFindBl(ActionEvent event) {
        countableReefers.clear();
        countableReefers = receivingServiceFacade.findCountableLoadReefersByNoBl(blno, registration.getPlanningVessel().getNoPpkb());
        if (countableReefers.isEmpty()) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.notfound");
        }
    }

    public void handleFindAllBl(ActionEvent event) {
        countableReefers.clear();
        blno = "";
        countableReefers = receivingServiceFacade.findCountableLoadReefers(registration.getPlanningVessel().getNoPpkb());
        if (countableReefers.isEmpty()) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.notfound");
        }
    }

    public void handleDelete(ActionEvent event) {
        perhitunganMonitoringFacade.remove(perhitunganMonitoring);
        perhitunganPluggingFacade.remove(perhitunganPlugging);
        reeferLoadServiceFacade.remove(reeferLoadService);
        viewData();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
    }

    public void handleDownloadTransactionRecap(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/delivery.pdf?no_reg=" + no_reg + "&total=" + String.valueOf(total) + "&ppn=" + String.valueOf(getPpnPrint()) + "&materai=" + String.valueOf(getMateraiPrint()) + "&userId=" + loginSessionBean.getUsername() + "&tipe=reeferL")));
    }

    public void handleDownloadNota(ActionEvent event) {
        invoice = invoiceFacade.find(no_reg);
        //validasi print invoice berulang-ulang
        if (invoice.getValidasiPrint().equals("FALSE")) {
            invoice = invoiceFacade.publishInvoice(invoice);
        }

        viewData();

        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/invoice.pdf?no_reg=" + no_reg + "&to=" + invoice.getTotalTagihan().toString() + "&curr=" + masterCurrency.getCountry() + "&tipe=" + registration.getMasterService().getServiceCode() + "&username=" + loginSessionBean.getUsername() + "&detail=" + String.valueOf(disDetail))));
    }

    public void handleDownloadDetail(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/detail.pdf?no_reg=" + no_reg + "&tipe=" + registration.getMasterService().getServiceCode() + "&username=" + loginSessionBean.getUsername() + "&ppn=" + invoice.getPpn() + "&materai=" + String.valueOf(materaiPrint) + "&total=" + String.valueOf(total))));
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

        //delete semua jobslip dan perhitungan
        for (Object[] o : reeferLoadServiceFacade.findLoadReeferServiceByReg(no_reg)) {
            reeferLoadService = reeferLoadServiceFacade.find(o[0]);
            reeferLoadServiceFacade.remove(reeferLoadService);
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

        invoice.setNoFakturPajak(null);
        invoice.setNoInvoice(null);
        invoice.setCancelInvoice("TRUE");
        
        invoiceFacade.edit(invoice);

        if (masterSettingAppFacade.isPaymentBankingEnabled() && invoice.getPaymentType() != null && invoice.getPaymentType().equals("CASH"))
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
     * @return the reeferLoadServices
     */
    public List<Object[]> getReeferLoadServices() {
        return reeferLoadServices;
    }

    /**
     * @param reeferLoadServices the reeferLoadServices to set
     */
    public void setReeferLoadServices(List<Object[]> reeferLoadServices) {
        this.reeferLoadServices = reeferLoadServices;
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
     * @return the receivingServices
     */
    public List<Object[]> getCountableReefers() {
        return countableReefers;
    }

    /**
     * @return the reeferLoadService
     */
    public ReeferLoadService getReeferLoadService() {
        return reeferLoadService;
    }

    /**
     * @param reeferLoadService the reeferLoadService to set
     */
    public void setReeferLoadService(ReeferLoadService reeferLoadService) {
        this.reeferLoadService = reeferLoadService;
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
    public Object[][] getSelectedCountableReefers() {
        return selectedCountableReefers;
    }

    /**
     * @param deliveryContainer the deliveryContainer to set
     */
    public void setSelectedCountableReefers(Object[][] deliveryContainer) {
        this.selectedCountableReefers = deliveryContainer;
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

    public Integer getPlugShiftPlanning() {
        return plugShiftPlanning;
    }

    public void setPlugShiftPlanning(Integer plugShiftPlanning) {
        this.plugShiftPlanning = plugShiftPlanning;
    }
    
    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    public Boolean getIsPerencanaDanOperasi() {
        return isPerencanaDanOperasi;
    }

    public Boolean getIsSimpatReady() {
        return isSimpatReady;
    }
}

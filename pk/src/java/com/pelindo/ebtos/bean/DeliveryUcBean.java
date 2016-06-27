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
import com.pelindo.ebtos.ejb.facade.remote.MasterDiscountFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPassGateFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganUpahBuruhFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcDeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcGatedeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcPerhitunganLiftFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcPerhitunganPenumpukanFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcPerhitunganStevedoringFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PerhitunganPassGate;
import com.pelindo.ebtos.model.db.PerhitunganUpahBuruh;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.UcDeliveryService;
import com.pelindo.ebtos.model.db.UcPerhitunganLift;
import com.pelindo.ebtos.model.db.UcPerhitunganPenumpukan;
import com.pelindo.ebtos.model.db.UcPerhitunganStevedoring;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
@ManagedBean(name = "deliveryUcBean")
@ViewScoped
public class DeliveryUcBean implements Serializable {
    
    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacade;
    @EJB
    private UcGatedeliveryServiceFacadeRemote ucGatedeliveryServiceFacade;
    @EJB
    private BatalNotaFacadeRemote batalNotaFacade;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacadeRemote;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacade;
    @EJB
    private InvoiceFacadeRemote invoiceFacade;
    @EJB
    private RegistrationFacadeRemote registrationFacade;
    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacade;
    @EJB
    private UcDeliveryServiceFacadeRemote ucDeliveryServiceFacade;
    @EJB
    private UcPerhitunganPenumpukanFacadeRemote ucPerhitunganPenumpukanFacade;
    @EJB
    private UcPerhitunganLiftFacadeRemote ucPerhitunganLiftFacade;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private UcPerhitunganStevedoringFacadeRemote ucPerhitunganStevedoringFacade;
    @EJB
    private MasterDiscountFacadeRemote masterDiscountFacadeRemote;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacadeRemote;
    @EJB
    private PerhitunganPassGateFacadeRemote perhitunganPassGateFacadeRemote;
    @EJB
    private PerhitunganUpahBuruhFacadeRemote perhitunganUpahBuruhFacadeRemote;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;

    private List<Object[]> registrations;
    private List<Object[]> ucDeliveryServices;
    private List<Object[]> uncontainerizedServices;
    private MasterCurrency masterCurrency;
    private Registration registration;
    private UcDeliveryService ucDeliveryService;
    private UncontainerizedService uncontainerizedService;
    private BatalNota batalNota;
    private Invoice invoice;
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal ppn = BigDecimal.ZERO;
    private BigDecimal ppnPrint = BigDecimal.ZERO;
    private BigDecimal materaiPrint = BigDecimal.ZERO;
    private BigDecimal additionalCharge = BigDecimal.ZERO;
    private Date tanggalValidDate;
    private Boolean inclHandling;
    private Boolean disPrint = true;
    private Boolean disKredit = false;
    private Boolean disDetail = true;
    private Boolean disCancelInv = true;
    private String no_reg;
    private String userId;
    private Boolean isSimpatReady;

    public DeliveryUcBean() {}

    @PostConstruct
    private void construct() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        uncontainerizedService = new UncontainerizedService();
        invoice = new Invoice();
        invoice.setPaymentType("CASH");
        no_reg = null;
        inclHandling = true;

        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        userId = loginSessionBean.getName();
        batalNota = new BatalNota();
        registrations = registrationFacade.findRegistrationDeliveryUC();
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
    }

    public void viewData() {
        if (no_reg != null) {
            disCancelInv = Boolean.TRUE;
            registration = registrationFacade.find(no_reg);
            ucDeliveryServices = ucDeliveryServiceFacade.findPerhitungan(no_reg);

            if (ucDeliveryServices.size() > 10) {
                disDetail = false;
            } else {
                disDetail = true;
            }

            if (registration.getPlanningVessel().getIsLoadPortToPort()) {
                inclHandling = true;
            } else if (registration.getPlanningVessel().getIsLoadCyToCy()) {
                inclHandling = false;
            }

            if (registration.getIsIncludeHandling() == null) {
                if (inclHandling) {
                    registration.setIsIncludeHandling("TRUE");
                } else {
                    registration.setIsIncludeHandling("FALSE");
                }
            }

            total = BigDecimal.ZERO;
            additionalCharge = BigDecimal.ZERO;
            
            for (Object[] o : ucDeliveryServices) {
                total = total.add((BigDecimal) o[8]).add((BigDecimal) o[9]).add((BigDecimal) o[10]).add((BigDecimal) o[11]).add((BigDecimal) o[12]);
                additionalCharge = additionalCharge.add((BigDecimal) o[15]);
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

            List<Object[]> materai = masterMateraiFacade.findByCurr(masterCurrency.getCurrCode());
            
            if (total.compareTo((BigDecimal) materai.get(1)[2]) == -1) { /* jumlah tagihan < nilai minimum */
                setMateraiPrint((BigDecimal) materai.get(0)[1]);
            } else if (total.compareTo((BigDecimal) materai.get(2)[2]) == -1) {
                setMateraiPrint((BigDecimal) materai.get(1)[1]);
            } else {
                setMateraiPrint((BigDecimal) materai.get(2)[1]);
            }
            
            uncontainerizedServices = uncontainerizedServiceFacade.findByEntryDelivery(registration.getPlanningVessel().getNoPpkb());
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
                    disCancelInv = Boolean.FALSE;
                    for (Object[] ob : ucDeliveryServices) {
                        ucDeliveryService = ucDeliveryServiceFacade.find(ob[0]);
                        if (ucGatedeliveryServiceFacade.findUcGatedeliveryServiceByGate(ucDeliveryService.getJobslip()) == null) {
                            if (disCancelInv == false) {
                                disCancelInv = Boolean.FALSE;
                            }
                        } else {
                            disCancelInv = Boolean.TRUE;
                        }
                    }
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
    }

    public void onChangeAction(ValueChangeEvent event) {
        inclHandling = !inclHandling;
        viewData();
    }

    public void handleAdd(ActionEvent event) {
        ucDeliveryService = new UcDeliveryService();
        uncontainerizedService = new UncontainerizedService();
        ucDeliveryService.setJobslip(iDGeneratorFacade.generateJobSlipID("14"));

        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        now.set(Calendar.MILLISECOND, 0);
        tanggalValidDate = now.getTime();
    }

    public void handleSelectContPick(ActionEvent event) {
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        uncontainerizedService = uncontainerizedServiceFacade.find(id_cont);
        ucDeliveryService.setUncontainerizedService(uncontainerizedService);

        if (uncontainerizedService.getTruckLoosing().equals("TRUE")) {
            uncontainerizedService.setCrane("L");
        }
    }

    public void handleConfirm(ActionEvent event) {
        boolean loggedIn = false;
        if (ucDeliveryService.getUncontainerizedService() == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.transaction.notcompleted");
        } else {
            if (uncontainerizedService.getPlacementDate() == null) {
                uncontainerizedService.setPlacementDate(new Date());
            }

            int min = DateCalculator.countRangeInDays(tanggalValidDate, uncontainerizedService.getPlacementDate()) + 1;
            int masaFree = masterSettingAppFacade.getMasa1FreeRange();
            int masa1Range = masterSettingAppFacade.getMasa1UcRange();
            int m1 = min - (masaFree - 1);
            
            if (min < (masa1Range + 1)) {
                if (min <= masaFree) {
                    ucDeliveryService.setMasa1(1);
                } else {
                    ucDeliveryService.setMasa1(m1);
                }
                ucDeliveryService.setMasa2(0);
            } else {
                Integer masa2 = min - masa1Range;
                Integer masa1 = m1 - masa2;
                ucDeliveryService.setMasa1(masa1);
                ucDeliveryService.setMasa2(masa2);
            }

            ucDeliveryService.setValidDate(tanggalValidDate);
            ucDeliveryService.setNoReg(registration.getNoReg());
            ucDeliveryService.setDeliveryDate(null);
            ucDeliveryService.setIsDelivery("FALSE");

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
            perhitunganPassGate.setJobSlip(ucDeliveryService.getJobslip());
            perhitunganPassGate.setMasterActivity(passGateMasterActivity);
            perhitunganPassGate.setPlanningVessel(registration.getPlanningVessel());
            perhitunganPassGate.setRegistration(registration);
            perhitunganPassGate.setCharge(passGateCharge);
            perhitunganPassGate.setJumlah(1);
            perhitunganPassGate.setTotalCharge(perhitunganPassGate.getCharge().multiply(new BigDecimal(perhitunganPassGate.getJumlah())));

            UcPerhitunganStevedoring ucPerhitunganStevedoring = new UcPerhitunganStevedoring();
            ucPerhitunganStevedoring.setNoReg(registration.getNoReg());
            ucPerhitunganStevedoring.setJobslip(ucDeliveryService.getJobslip());
            ucPerhitunganStevedoring.setActivityCode(handlingActivityCode);
            ucPerhitunganStevedoring.setCurrCode(idrMasterCurrency.getCurrCode());
            ucPerhitunganStevedoring.setOperation("DISCHARGE");
            ucPerhitunganStevedoring.setJasaDermaga(jasaDermagaCharge);
            ucPerhitunganStevedoring.setCharge(handlingCharge.add(ucPerhitunganStevedoring.getJasaDermaga()));

            UcPerhitunganLift ucPerhitunganLift = new UcPerhitunganLift();
            ucPerhitunganLift.setNoReg(registration.getNoReg());
            ucPerhitunganLift.setJobslip(ucDeliveryService.getJobslip());
            ucPerhitunganLift.setActivityCode(loloActivityCode);
            ucPerhitunganLift.setCurrCode(idrMasterCurrency.getCurrCode());
            ucPerhitunganLift.setOperation("DISCHARGE");
            ucPerhitunganLift.setCharge(loloCharge);

            UcPerhitunganPenumpukan ucPerhitunganPenumpukan = new UcPerhitunganPenumpukan();
            ucPerhitunganPenumpukan.setRegistration(registration);
            ucPerhitunganPenumpukan.setJobslip(ucDeliveryService.getJobslip());
            ucPerhitunganPenumpukan.setOperation("DISCHARGE");
            ucPerhitunganPenumpukan.setActivityCode(penumpukanActivityCode);
            ucPerhitunganPenumpukan.setCurrCode(idrMasterCurrency.getCurrCode());

            PerhitunganUpahBuruh perhitunganUbahBuruh = new PerhitunganUpahBuruh();
            perhitunganUbahBuruh.setRegistration(registration);
            perhitunganUbahBuruh.setJobslip(ucDeliveryService.getJobslip());
            perhitunganUbahBuruh.setMasterActivity(upahBuruhMasterActivity);
            perhitunganUbahBuruh.setMasterCurrency(idrMasterCurrency);


            BigDecimal tonage = new BigDecimal(uncontainerizedService.getWeight() / 1000);

            if (tonage.compareTo(uncontainerizedService.getCubication()) > 0) {
                upahBuruhCharge = upahBuruhCharge.multiply(tonage);
            } else {
                upahBuruhCharge = upahBuruhCharge.multiply(uncontainerizedService.getCubication());
            }



            perhitunganUbahBuruh.setCharge(upahBuruhCharge);



            if (registration.getPlanningVessel().getIsDischargePortToPort()) {
                ucPerhitunganPenumpukan.setMasa1(ucDeliveryService.getMasa1().shortValue());
                ucPerhitunganPenumpukan.setMasa2(ucDeliveryService.getMasa2().shortValue());
            } else if (registration.getPlanningVessel().getIsDischargeCyToCy()) {
                ucPerhitunganPenumpukan.setMasa1((short) (ucDeliveryService.getMasa1() - 1));
                ucPerhitunganPenumpukan.setMasa2(ucDeliveryService.getMasa2().shortValue());
            }

           

            if (tonage.compareTo(uncontainerizedService.getCubication()) > 0) {
                penumpukanCharge = penumpukanCharge.multiply(tonage);
            } else {
                penumpukanCharge = penumpukanCharge.multiply(uncontainerizedService.getCubication());
            }

            ucPerhitunganPenumpukan.setCharge(penumpukanCharge);
            ucPerhitunganPenumpukan.setChargeMasa1(ucPerhitunganPenumpukan.getCharge().multiply(BigDecimal.valueOf(ucPerhitunganPenumpukan.getMasa1())));
            ucPerhitunganPenumpukan.setChargeMasa2(ucPerhitunganPenumpukan.getCharge().multiply(BigDecimal.valueOf(ucPerhitunganPenumpukan.getMasa2())).multiply(BigDecimal.valueOf(2)));
            ucPerhitunganPenumpukan.setCharge(ucPerhitunganPenumpukan.getChargeMasa1().add(ucPerhitunganPenumpukan.getChargeMasa2()));

            if (registration.getPlanningVessel().getIsDischargePortToPort() && ucPerhitunganPenumpukan.getMasa1() > 0) {
                ucPerhitunganStevedoringFacade.create(ucPerhitunganStevedoring);
                perhitunganUpahBuruhFacadeRemote.create(perhitunganUbahBuruh);
            }

            perhitunganPassGateFacadeRemote.create(perhitunganPassGate);
            ucPerhitunganPenumpukanFacade.create(ucPerhitunganPenumpukan);
            ucPerhitunganLiftFacade.create(ucPerhitunganLift);
            ucDeliveryServiceFacade.create(ucDeliveryService);

            loggedIn = true;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
            viewData();
        }
    }

    public void handleSelect(ActionEvent event) {
        String job_slip = (String) event.getComponent().getAttributes().get("jobSlip");
        ucDeliveryService = ucDeliveryServiceFacade.find(job_slip);
    }

    public void handleSubmit(ActionEvent event) {
        Boolean loggedIn = true;
        invoice.setNoReg(no_reg);
        invoice.setRegistration(registration);
        invoice.setPaymentStatus("UNPAID");
        invoice.setJumlahTagihan(total);
        invoice.setValidasiPrint("FALSE");

        MasterSettingApp settingApp = masterSettingAppFacade.find("ppn");
        ppnPrint = settingApp.getValueFloat();

        ppn = invoice.getJumlahTagihan().multiply(settingApp.getValueFloat());
        invoice.setPpn(ppn);

        if (registration.getPlanningVessel().getTipePelayaran().equals("d")) {
            masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        } else {
            masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        }

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

        invoice.setAdditionalCharge(additionalCharge);
        invoice.setTotalTagihan(invoice.getJumlahTagihan().add(invoice.getMaterai()).add(invoice.getPpn()).add(invoice.getAdditionalCharge()));
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
        ucPerhitunganLiftFacade.deleteByJobslip(ucDeliveryService.getJobslip());
        ucPerhitunganPenumpukanFacade.deleteByJobslip(ucDeliveryService.getJobslip());
        ucPerhitunganStevedoringFacade.deleteByJobslip(ucDeliveryService.getJobslip());
        perhitunganPassGateFacadeRemote.deleteByJobSlip(ucDeliveryService.getJobslip());
        perhitunganUpahBuruhFacadeRemote.deleteByJobslip(ucDeliveryService.getJobslip());
        ucDeliveryServiceFacade.remove(ucDeliveryService);
        viewData();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
    }

    public void handleDownloadTransactionRecap(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/delivery.pdf?no_reg=" + no_reg + "&total=" + String.valueOf(total) + "&ppn=" + String.valueOf(getPpnPrint()) + "&materai=" + String.valueOf(getMateraiPrint()) + "&userId=" + getUserId() + "&tipe=deliveryUC&inclHandling=" + String.valueOf(inclHandling))));
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
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/invoice.pdf?no_reg=" + no_reg + "&curr=" + masterCurrency.getCountry() + "&tipe=" + registration.getMasterService().getServiceCode() + "&to=" + invoice.getTotalTagihan() + "&username=" + getUserId() + "&detail=" + String.valueOf(disDetail))));
    }

    public void handleDownloadDetail(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/detail.pdf?no_reg=" + no_reg + "&tipe=" + registration.getMasterService().getServiceCode() + "&username=" + getUserId() + "&ppn=" + String.valueOf(ppnPrint) + "&materai=" + String.valueOf(materaiPrint) + "&total=" + String.valueOf(total))));
    }

    public void handleCancelInvoice(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        Boolean isValid = false;

        try {
            registration = registrationFacade.find(invoice.getNoReg());
            batalNota.setCustCode(registration.getMasterCustomer().getCustCode());
            batalNota.setNoFakturPajak(invoice.getNoFakturPajak());
            final String noInvoice = invoice.getNoInvoice();
            batalNota.setNoInvoice(noInvoice);
            batalNota.setNoReg(invoice.getNoReg());
            batalNota.setPaymentDate(invoice.getPaymentDate());
            batalNota.setTotalTagihan(invoice.getTotalTagihan());

            ucPerhitunganStevedoringFacade.deleteByNoReg(registration.getNoReg());
            ucPerhitunganLiftFacade.deleteByNoReg(registration.getNoReg());
            ucPerhitunganPenumpukanFacade.deleteByNoReg(registration.getNoReg());
            perhitunganPassGateFacadeRemote.deleteByNoReg(registration.getNoReg());
            ucDeliveryServiceFacade.deleteByNoReg(registration.getNoReg());
            perhitunganUpahBuruhFacadeRemote.deleteByNoReg(registration.getNoReg());
            
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
            isValid = true;
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, re);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.cancel.failed");
        }

        context.addCallbackParam("loggedIn", isValid);
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
     * @return the ucDeliveryServices
     */
    public List<Object[]> getUcDeliveryServices() {
        return ucDeliveryServices;
    }

    /**
     * @param ucDeliveryServices the ucDeliveryServices to set
     */
    public void setUcDeliveryServices(List<Object[]> ucDeliveryServices) {
        this.ucDeliveryServices = ucDeliveryServices;
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
     * @return the inclHandling
     */
    public Boolean getInclHandling() {
        return inclHandling;
    }

    /**
     * @param inclHandling the inclHandling to set
     */
    public void setInclHandling(Boolean inclHandling) {
        this.inclHandling = inclHandling;
    }

    /**
     * @return the uncontainerizedServices
     */
    public List<Object[]> getUncontainerizedServices() {
        return uncontainerizedServices;
    }

    /**
     * @param uncontainerizedServices the uncontainerizedServices to set
     */
    public void setUncontainerizedServices(List<Object[]> uncontainerizedServices) {
        this.uncontainerizedServices = uncontainerizedServices;
    }

    /**
     * @return the ucDeliveryService
     */
    public UcDeliveryService getUcDeliveryService() {
        return ucDeliveryService;
    }

    /**
     * @param ucDeliveryService the ucDeliveryService to set
     */
    public void setUcDeliveryService(UcDeliveryService ucDeliveryService) {
        this.ucDeliveryService = ucDeliveryService;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    public BigDecimal getPpn() {
        return ppn;
    }

    public Date getTanggalValidDate() {
        return tanggalValidDate;
    }

    public void setTanggalValidDate(Date tanggalValidDate) {
        this.tanggalValidDate = tanggalValidDate;
    }

    public BigDecimal getAdditionalCharge() {
        return additionalCharge;
    }

    public Boolean getIsSimpatReady() {
        return isSimpatReady;
    }
}

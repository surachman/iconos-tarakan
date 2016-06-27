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
import com.pelindo.ebtos.ejb.facade.remote.ContainerMovementHistoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.DeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDiscountFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganHandlingDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganLiftServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPassGateFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganUpahBuruhFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReeferFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.ContainerMovementHistory;
import com.pelindo.ebtos.model.db.DeliveryService;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PerhitunganHandlingDischarge;
import com.pelindo.ebtos.model.db.PerhitunganLiftService;
import com.pelindo.ebtos.model.db.PerhitunganPassGate;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukan;
import com.pelindo.ebtos.model.db.PerhitunganUpahBuruh;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterDangerousClass;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
@ManagedBean(name = "deliveryBean")
@ViewScoped
public class DeliveryBean implements Serializable {

    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacade;
    @EJB
    private BatalNotaFacadeRemote batalNotaFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacade;
    @EJB
    private InvoiceFacadeRemote invoiceFacade;
    @EJB
    private PerhitunganPenumpukanFacadeRemote perhitunganPenumpukanFacade;
    @EJB
    private PerhitunganLiftServiceFacadeRemote perhitunganLiftServiceFacade;
    @EJB
    private PerhitunganHandlingDischargeFacadeRemote perhitunganHandlingDischargeFacade;
    @EJB
    private DeliveryServiceFacadeRemote deliveryServiceFacade;
    @EJB
    private RegistrationFacadeRemote registrationFacade;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacade;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacade;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacade;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private MasterDiscountFacadeRemote masterDiscountFacade;
    @EJB
    private ServiceReeferFacadeRemote serviceReeferFacadeRemote;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private PerhitunganPassGateFacadeRemote perhitunganPassGateFacadeRemote;
    @EJB
    private PerhitunganUpahBuruhFacadeRemote perhitunganUpahBuruhFacadeRemote;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;
    @EJB
    private ContainerMovementHistoryFacadeRemote containerMovementHistoryFacadeRemote;

    private List<Object[]> containerMovementHistories;
    private List<Object[]> registrations;
    private List<Object[]> deliveryServices;
    private List<Object[]> serviceContDischarges;
    private Object[][] deliveryContainer;
    private Registration registration;
    private DeliveryService deliveryService;
    private BatalNota batalNota;
    private LoginSessionBean loginSessionBean;
    private MasterCurrency masterCurrency;
    private Date tanggalValidDate, tanggalValidDate2;
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal upahBuruh = BigDecimal.ZERO;
    private BigDecimal ppnPrint = BigDecimal.ZERO;
    private BigDecimal materaiPrint = BigDecimal.ZERO;
    private Invoice invoice;
    private String no_reg;
    private String blno;
    private String userId = null;
    private Boolean disPrint = true;
    private Boolean disKredit = false;
    private Boolean disDetail = true;
    private Boolean disCancelInv = true;
    private Boolean inclHandling;
    private Boolean isSimpatReady;
    private ContainerMovementHistory containerMovementHistory;
    /** Creates a new instance of DeliveryBean */
    public DeliveryBean() {}

    @PostConstruct
    private void construct() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        deliveryService = new DeliveryService();
        invoice = new Invoice();
        no_reg = null;
        inclHandling = true;
        deliveryContainer = new Object[0][0];
        loginSessionBean = LoginSessionBean.getCurrentInstance();

        userId = loginSessionBean.getName();
        batalNota = new BatalNota();
        registrations = registrationFacade.findRegistrationDelivery();
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
    }

    public void viewData() {
        if (no_reg != null) {
            disCancelInv = false;
            registration = registrationFacade.find(no_reg);
            deliveryServices = deliveryServiceFacade.findPerhitungan(no_reg);
            invoice = registration.getInvoice();

            if (invoice == null || invoice.getNoInvoice() == null || invoice.isCancelInvoice().equals("FALSE")) {
                disCancelInv = true;
            }

            if (deliveryServices.size() > 10) {
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
            upahBuruh = BigDecimal.ZERO;

            for (Object[] o : deliveryServices) {
                total = total
                        .add((BigDecimal) o[12])
                        .add((BigDecimal) o[13])
                        .add((BigDecimal) o[14])
                        .add((BigDecimal) o[15])
                        .add((BigDecimal) o[16])
                        .add((BigDecimal) o[17])
                        .add((BigDecimal) o[18])
                        .add((BigDecimal) o[19]);
                upahBuruh = upahBuruh.add((BigDecimal) o[22]);
                disCancelInv = disCancelInv || (Boolean) (o[21]);
            }

            MasterSettingApp settingApp = masterSettingAppFacade.find("ppn");
            ppnPrint = settingApp.getValueFloat();
            
            if (registration.getPlanningVessel().getTipePelayaran().equals("d")) {
                masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
            } else {
                masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
            }

            List<Object[]> materai = masterMateraiFacade.findByCurr(masterCurrency.getCurrCode());
            
            if (total.compareTo((BigDecimal) materai.get(1)[2]) == -1) { /* jumlah tagihan < nilai minimum */
                materaiPrint = (BigDecimal) materai.get(0)[1];
            } else if (total.compareTo((BigDecimal) materai.get(2)[2]) == -1) {
                materaiPrint = (BigDecimal) materai.get(1)[1];
            } else {
                materaiPrint = (BigDecimal) materai.get(2)[1];
            }

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

            } else {
                invoice = new Invoice();
                invoice.setPaymentType("CASH");
                disPrint = true;
                disKredit = false;
            }

            if (blno == null) {
                serviceContDischarges = new ArrayList<Object[]>();
            } else if (blno.equals("")) {
                serviceContDischarges = serviceContDischargeFacade.findServiceContDischargesDeliveryService(registration.getPlanningVessel().getNoPpkb());
            } else {
                serviceContDischarges = serviceContDischargeFacade.findServiceContDischargesDeliveryServiceByBL(blno, registration.getPlanningVessel().getNoPpkb());
            }

            blno = "";
        }
    }
    
    
    //penambahan list find data delivery realtime
    public void handleViewReg(ActionEvent event) {
        registrations = registrationFacade.findRegistrationDelivery();
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

    public void handleAddCancelInvoice(ActionEvent event) {
        batalNota = new BatalNota(invoice.getNoFakturPajak());
    }
    
    
    
    public void resetForm() {
        registration = new Registration();
        deliveryServices = null;
        invoice = new Invoice();
        masterCurrency = new MasterCurrency();
        //serviceContDischarges = null;
        //deliveryService = new DeliveryService();
        total = BigDecimal.ZERO;
        upahBuruh = BigDecimal.ZERO;
        ppnPrint = BigDecimal.ZERO;
        materaiPrint = BigDecimal.ZERO;
    }
    public void handleAdd(ActionEvent event) {
        deliveryService = new DeliveryService();
        deliveryService.setMasterContainerType(new MasterContainerType());
        deliveryService.setMasterCommodity(new MasterCommodity());
        serviceContDischarges = new ArrayList<Object[]>();

        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        now.set(Calendar.MILLISECOND, 0);

        tanggalValidDate = now.getTime();
        tanggalValidDate2 = now.getTime();
        
    }
    
    public void handleSave(ActionEvent event) {
        try {
            for (int j = 0; j < deliveryContainer.length; j++) {
                ServiceContDischarge scd = serviceContDischargeFacade.find(deliveryContainer[j][9]);
                deliveryService = deliveryServiceFacade.findByNoPpkbAndContNo(scd.getServiceVessel().getNoPpkb(), scd.getContNo());

                if (deliveryService != null) {
                    continue;
                }

                deliveryService = new DeliveryService();
                deliveryService.setJobSlip(iDGeneratorFacade.generateJobSlipID("02"));
                deliveryService.setBlNo(scd.getBlNo());
                deliveryService.setContNo(scd.getContNo());
                deliveryService.setMlo(scd.getMlo());
                deliveryService.setContSize(scd.getContSize());
                deliveryService.setContGross(scd.getContGross());
                deliveryService.setTwentyOneFeet(scd.getTwentyOneFeet());
                deliveryService.setContStatus(scd.getContStatus());
                deliveryService.setOverSize(scd.getOverSize());
                deliveryService.setDg(scd.getDangerous());
                deliveryService.setDgLabel(scd.getDgLabel());
                deliveryService.setCrane(scd.getCrane());
                deliveryService.setPlacementDate(scd.getStartPlacementDate());
                deliveryService.setMasterCommodity(scd.getMasterCommodity());
                deliveryService.setMasterContainerType(scd.getMasterContainerType());
                deliveryService.setSealNo(scd.getSealNo());
                handleConfirm(scd);
            }

            viewData();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (RuntimeException re) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, re);
        }

        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", true);
    }

    public void handleConfirm(ServiceContDischarge serviceContDischarge) {
        int masaFree = masterSettingAppFacade.getMasa1FreeRange();
        int masa1Range = masterSettingAppFacade.getMasa1ContainerRange();
        int masa2Range = masterSettingAppFacade.getMasa2ContainerRange();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(deliveryService.getPlacementDate());
        calendar.add(Calendar.DAY_OF_YEAR, masaFree - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        Date minimalValidDate = calendar.getTime();
        deliveryService.setValidDate(tanggalValidDate2);
        tanggalValidDate = minimalValidDate.compareTo(tanggalValidDate) > 0 ? minimalValidDate : tanggalValidDate;

        int min = DateCalculator.countRangeInDays(tanggalValidDate, deliveryService.getPlacementDate()) + 1;
        System.out.println("min : "+min);
        int m1 = min - (masaFree - 1)-1;
       
        if (min < (masa1Range + 1)) {
            if (min <= masaFree) {
                deliveryService.setMasa1((short) 0);
            } else {
                deliveryService.setMasa1((short) m1);
            }
            deliveryService.setMasa2((short) 0);
            deliveryService.setMasa3((short) 0);
        } else {
            Integer masa2 = min - masa1Range;
            Integer masa1 = (m1 - masa2);
            Integer masa3 = 0;
            //if(masa2 > masa2Range){
            //    masa3 = masa2 - masa2Range;
            //    masa2= masa2Range;
            //}
            deliveryService.setMasa1(masa1.shortValue());
            deliveryService.setMasa2(masa2.shortValue());
            deliveryService.setMasa3(masa3.shortValue());
        }
                
        //deliveryService.setValidDate(tanggalValidDate);
        deliveryService.setRealPenumpukan(min);
        deliveryService.setRegistration(registration);
        deliveryService.setPlanningVessel(registration.getPlanningVessel());

        MasterCurrency idrMasterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();

        Integer idReefer = serviceReeferFacadeRemote.findValidasiPenumpukan(deliveryService.getPlanningVessel().getNoPpkb(), deliveryService.getContNo(), "DISCHARGE");
        boolean isOverSize = false, isSeling = false;
        
        if ((!deliveryService.getContStatus().equals("MTY") 
                && deliveryService.getOverSize().equals("TRUE")) 
                || deliveryService.getTwentyOneFeet().equals("TRUE")) {
                 isOverSize = true;
        } else {
                 isOverSize = false;
        }         
        String penumpukanActivityCode = "";
        containerMovementHistory = containerMovementHistoryFacadeRemote.findLastRecord(registration.getPlanningVessel().getNoPpkb(), deliveryService.getContNo());
        if (containerMovementHistory != null){
                deliveryService.getContStatus();
                if (deliveryService.getContStatus().equalsIgnoreCase("MTY")){
                    deliveryService.setContStatus("FCL");
                } else {
                    deliveryService.setContStatus("MTY");
                }
                penumpukanActivityCode = masterActivityFacade.translatePenumpukanActivityCode(
                deliveryService.getContStatus(), 
                deliveryService.getMasterContainerType().getIsoCode(), 
                isOverSize, 
                deliveryService.getContSize(), idReefer > 0);
        } else{
                penumpukanActivityCode = masterActivityFacade.translatePenumpukanActivityCode(
                deliveryService.getContStatus(), 
                deliveryService.getMasterContainerType().getIsoCode(), 
                isOverSize, 
                deliveryService.getContSize(), idReefer > 0);
    }
        String passGateActivityCode = masterActivityFacade.translatePassGateActivityCode(deliveryService.getContStatus(), deliveryService.getContSize());
        
        if(serviceContDischarge.getIsSeling().equals("TRUE")) {
            isSeling = true;
        }
        String dangerousClass = null;
        if(serviceContDischarge.getMasterCommodity() != null && serviceContDischarge.getMasterCommodity().getMasterDangerousClass() != null)
            dangerousClass = serviceContDischarge.getMasterCommodity().getMasterDangerousClass().getDangerousClass();
        
        String stevedoringActivityCode = masterActivityFacade.translateStevedoringActivityCode(
                deliveryService.getContStatus(), 
                deliveryService.getContSize(), 
                deliveryService.getCrane(), 
                isSeling, 
                serviceContDischarge.getEquipmentGroup(), 
                serviceContDischarge.getDangerous().equals("TRUE"),
                serviceContDischarge.getDgLabel().equals("TRUE"),
                dangerousClass,
                isSeling,
                deliveryService.getTwentyOneFeet().equals("TRUE"));
        
        String htActivityCode = masterActivityFacade.translateHaulageTruckingActivityCode(
                deliveryService.getContStatus(), 
                deliveryService.getContSize(), 
                deliveryService.getCrane(), 
                isSeling, 
                serviceContDischarge.getEquipmentGroup(), 
                serviceContDischarge.getDangerous().equals("TRUE"),
                serviceContDischarge.getDgLabel().equals("TRUE"),
                dangerousClass,
                isSeling,
                deliveryService.getTwentyOneFeet().equals("TRUE"));
        
        String otherHandlingActivityCode = masterActivityFacade.translateOtherHandlingChargesActivityCode(
                deliveryService.getContStatus(), 
                deliveryService.getContSize(), 
                deliveryService.getCrane(), 
                isSeling, 
                serviceContDischarge.getEquipmentGroup(), 
                serviceContDischarge.getDangerous().equals("TRUE"),
                serviceContDischarge.getDgLabel().equals("TRUE"),
                dangerousClass,
                isSeling,
                deliveryService.getTwentyOneFeet().equals("TRUE"));
        
        String loloActivityCode = masterActivityFacade.translateLoLoActivityCode(
                deliveryService.getContStatus(), 
                deliveryService.getContSize(), 
                isOverSize, 
                serviceContDischarge.getDangerous().equals("TRUE"),
                serviceContDischarge.getDgLabel().equals("TRUE"),
                dangerousClass,serviceContDischarge.getEquipmentGroup());
        
        String upahBuruhActivityCode = masterActivityFacade.translateUbahBuruhHandlingActivityCode(
                deliveryService.getContStatus(), 
                deliveryService.getContSize(), 
                isOverSize || isSeling);

        MasterActivity passGateMasterActivity = masterActivityFacade.find(passGateActivityCode);
        MasterActivity loloMasterActivity = masterActivityFacade.find(loloActivityCode);
        MasterActivity penumpukanMasterActivity = masterActivityFacade.find(penumpukanActivityCode);
        MasterActivity upahBuruhMasterActivity = masterActivityFacade.find(upahBuruhActivityCode);

        BigDecimal penumpukanCharge = masterTarifContFacade.findByCurrCodeAndActivity(idrMasterCurrency.getCurrCode(), penumpukanActivityCode);
        BigDecimal upahBuruhCharge = masterTarifContFacade.findByCurrCodeAndActivity(idrMasterCurrency.getCurrCode(), upahBuruhActivityCode);
        BigDecimal stevedoringCharge = masterTarifContFacade.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), stevedoringActivityCode);
        BigDecimal htCharge = masterTarifContFacade.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), htActivityCode);
        BigDecimal otherHandlingCharge = masterTarifContFacade.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), otherHandlingActivityCode);
        BigDecimal loloCharge = masterTarifContFacade.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), loloActivityCode);
        BigDecimal passGateCharge = masterTarifContFacade.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), passGateActivityCode);
        BigDecimal jasaDermagaCharge = BigDecimal.ZERO;

        PerhitunganPassGate perhitunganPassGate = new PerhitunganPassGate();
        perhitunganPassGate.setJobSlip(deliveryService.getJobSlip());
        perhitunganPassGate.setMasterActivity(passGateMasterActivity);
        perhitunganPassGate.setPlanningVessel(registration.getPlanningVessel());
        perhitunganPassGate.setRegistration(registration);

        PerhitunganHandlingDischarge perhitunganStevedoring = new PerhitunganHandlingDischarge();
        perhitunganStevedoring.setNoReg(no_reg);
        perhitunganStevedoring.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
        perhitunganStevedoring.setContNo(deliveryService.getContNo());
        perhitunganStevedoring.setMlo(deliveryService.getMlo());
        perhitunganStevedoring.setBlNo(deliveryService.getBlNo());
        perhitunganStevedoring.setActivityCode(stevedoringActivityCode);
        perhitunganStevedoring.setCurrCode(masterCurrency.getCurrCode());
        
        PerhitunganHandlingDischarge perhitunganHt = new PerhitunganHandlingDischarge();
        perhitunganHt.setNoReg(no_reg);
        perhitunganHt.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
        perhitunganHt.setContNo(deliveryService.getContNo());
        perhitunganHt.setMlo(deliveryService.getMlo());
        perhitunganHt.setBlNo(deliveryService.getBlNo());
        perhitunganHt.setActivityCode(htActivityCode);
        perhitunganHt.setCurrCode(masterCurrency.getCurrCode());
        
        PerhitunganHandlingDischarge perhitunganOtherHandling = new PerhitunganHandlingDischarge();
        perhitunganOtherHandling.setNoReg(no_reg);
        perhitunganOtherHandling.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
        perhitunganOtherHandling.setContNo(deliveryService.getContNo());
        perhitunganOtherHandling.setMlo(deliveryService.getMlo());
        perhitunganOtherHandling.setBlNo(deliveryService.getBlNo());
        perhitunganOtherHandling.setActivityCode(otherHandlingActivityCode);
        perhitunganOtherHandling.setCurrCode(masterCurrency.getCurrCode());

        PerhitunganUpahBuruh perhitunganUpahBuruh = new PerhitunganUpahBuruh();
        perhitunganUpahBuruh.setRegistration(registration);
        perhitunganUpahBuruh.setJobslip(deliveryService.getJobSlip());
        perhitunganUpahBuruh.setMasterActivity(upahBuruhMasterActivity);
        perhitunganUpahBuruh.setMasterCurrency(idrMasterCurrency);
        perhitunganUpahBuruh.setCharge(upahBuruhCharge);

        PerhitunganLiftService perhitunganLiftService = new PerhitunganLiftService();
        perhitunganLiftService.setRegistration(registration);
        perhitunganLiftService.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
        perhitunganLiftService.setContNo(deliveryService.getContNo());
        perhitunganLiftService.setMlo(deliveryService.getMlo());
        perhitunganLiftService.setBlNo(deliveryService.getBlNo());
        perhitunganLiftService.setMasterActivity(loloMasterActivity);
        perhitunganLiftService.setCurrCode(masterCurrency.getCurrCode());

        PerhitunganPenumpukan perhitunganPenumpukan = new PerhitunganPenumpukan();
        perhitunganPenumpukan.setRegistration(registration);
        perhitunganPenumpukan.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
        perhitunganPenumpukan.setContNo(deliveryService.getContNo());
        perhitunganPenumpukan.setMlo(deliveryService.getMlo());
        perhitunganPenumpukan.setBlNo(deliveryService.getBlNo());
        perhitunganPenumpukan.setMasterActivity(penumpukanMasterActivity);
        perhitunganPenumpukan.setCurrCode(idrMasterCurrency.getCurrCode());

        if (registration.getPlanningVessel().getIsDischargePortToPort()) {
            perhitunganPenumpukan.setMasa1(deliveryService.getMasa1());
            perhitunganPenumpukan.setMasa2(deliveryService.getMasa2());
            perhitunganPenumpukan.setMasa3(deliveryService.getMasa3());
        } else if (registration.getPlanningVessel().getIsDischargeCyToCy()){
            perhitunganPenumpukan.setMasa1((short) (deliveryService.getMasa1()));
            perhitunganPenumpukan.setMasa2(deliveryService.getMasa2());
            perhitunganPenumpukan.setMasa3(deliveryService.getMasa3());
        }

        BigDecimal[] discount = masterDiscountFacade.getValidDiscount(registration.getMasterCustomer().getCustCode(), loloActivityCode);
        BigDecimal discountAsPercentage = discount[0];
        BigDecimal discountAsMoney = discount[1];
                
        if (discountAsMoney == null) {
            discountAsMoney = loloCharge.multiply(discountAsPercentage);
        }

        loloCharge = loloCharge.subtract(discountAsMoney);

        perhitunganLiftService.setCharge(loloCharge);

        discount = masterDiscountFacade.getValidDiscount(registration.getMasterCustomer().getCustCode(), passGateActivityCode);
        discountAsPercentage = discount[0];
        discountAsMoney = discount[1];

        if (discountAsMoney == null) {
            discountAsMoney = passGateCharge.multiply(discountAsPercentage);
        }

        passGateCharge = passGateCharge.subtract(discountAsMoney);
        perhitunganPassGate.setCharge(passGateCharge);
        perhitunganPassGate.setJumlah(1);
        perhitunganPassGate.setTotalCharge(perhitunganPassGate.getCharge().multiply(new BigDecimal(perhitunganPassGate.getJumlah())));
        
        //BigDecimal totalHandlingCharge = BigDecimal.ZERO;

        discount = masterDiscountFacade.getValidDiscount(registration.getMasterCustomer().getCustCode(), stevedoringActivityCode);
        discountAsPercentage = discount[0];
        discountAsMoney = discount[1];
        if (discountAsMoney == null) {
            discountAsMoney = stevedoringCharge.multiply(discountAsPercentage);
        }
        stevedoringCharge = stevedoringCharge.subtract(discountAsMoney);
        perhitunganStevedoring.setCharge(stevedoringCharge);

        discount = masterDiscountFacade.getValidDiscount(registration.getMasterCustomer().getCustCode(), htActivityCode);
        discountAsPercentage = discount[0];
        discountAsMoney = discount[1];
        if (discountAsMoney == null) {
            discountAsMoney = htCharge.multiply(discountAsPercentage);
        }
        htCharge = htCharge.subtract(discountAsMoney);
        perhitunganHt.setCharge(htCharge);

        discount = masterDiscountFacade.getValidDiscount(registration.getMasterCustomer().getCustCode(), otherHandlingActivityCode);
        discountAsPercentage = discount[0];
        discountAsMoney = discount[1];
        if (discountAsMoney == null) {
            discountAsMoney = otherHandlingCharge.multiply(discountAsPercentage);
        }
        otherHandlingCharge = otherHandlingCharge.subtract(discountAsMoney);
        perhitunganOtherHandling.setCharge(otherHandlingCharge);
        
        discount = masterDiscountFacade.getValidDiscount(registration.getMasterCustomer().getCustCode(), penumpukanActivityCode);
        discountAsPercentage = discount[0];
        discountAsMoney = discount[1];
        //BigDecimal totalPenumpukanCharge = BigDecimal.ZERO;

        if (discountAsMoney == null) {
            discountAsMoney = penumpukanCharge.multiply(discountAsPercentage);
        }

        penumpukanCharge = penumpukanCharge.subtract(discountAsMoney);
        //containerMovementHistory = containerMovementHistoryFacadeRemote.findLastRecord(registration.getPlanningVessel().getNoPpkb(), deliveryService.getContNo());
        if(containerMovementHistory != null){
        int beforeStripping = DateCalculator.countRangeInDays(containerMovementHistory.getModifiedDate(),deliveryService.getPlacementDate());
        int afterStripping = DateCalculator.countRangeInDays(tanggalValidDate2,containerMovementHistory.getModifiedDate()) + 1;
        perhitunganPenumpukan.setCharge(penumpukanCharge);
        String penumpukanActivityCodeStripping = "";
        if(deliveryService.getContStatus().equalsIgnoreCase("MTY")){
        penumpukanActivityCodeStripping = masterActivityFacade.translatePenumpukanActivityCode(
                "FCL", 
                deliveryService.getMasterContainerType().getIsoCode(), 
                isOverSize, 
                deliveryService.getContSize(), idReefer > 0);
        }
        if(deliveryService.getContStatus().equalsIgnoreCase("FCL")){
        penumpukanActivityCodeStripping = masterActivityFacade.translatePenumpukanActivityCode(
                "MTY", 
                deliveryService.getMasterContainerType().getIsoCode(), 
                isOverSize, 
                deliveryService.getContSize(), idReefer > 0);
        }
        //MasterActivity penumpukanMasterActivity = masterActivityFacade.find(penumpukanActivityCode);
        BigDecimal penumpukanChargeStripping = masterTarifContFacade.findByCurrCodeAndActivity(idrMasterCurrency.getCurrCode(), penumpukanActivityCodeStripping);
        penumpukanChargeStripping = penumpukanChargeStripping.subtract(discountAsMoney);
        }
//       if (min < (masa1Range + 1)) {
//           masa1Stripping = 
//       }
        int m1Amount = perhitunganPenumpukan.getMasa1().intValue();
        int m2Amount = perhitunganPenumpukan.getMasa2().intValue();
        int m3Amount = perhitunganPenumpukan.getMasa3().intValue();
        perhitunganPenumpukan.setChargeM1(perhitunganPenumpukan.getCharge()
                .multiply(BigDecimal.valueOf(m1Amount)));
        perhitunganPenumpukan.setChargeM2(perhitunganPenumpukan.getCharge()
                .multiply(BigDecimal.valueOf(m2Amount))
                .multiply(BigDecimal.valueOf(2)));
        perhitunganPenumpukan.setChargeM3(perhitunganPenumpukan.getCharge()
                .multiply(BigDecimal.valueOf(m3Amount))
                .multiply(BigDecimal.valueOf(2)));
        perhitunganPenumpukan.setJasaDermaga(jasaDermagaCharge);

        BigDecimal totalPenumpukanCharge = perhitunganPenumpukan.getChargeM1()
                .add(perhitunganPenumpukan.getChargeM2())
                .add(perhitunganPenumpukan.getChargeM3())
                .add(perhitunganPenumpukan.getJasaDermaga());
        perhitunganPenumpukan.setTotalCharge(totalPenumpukanCharge);

        if (registration.getPlanningVessel().getIsDischargePortToPort()) {
            perhitunganHandlingDischargeFacade.create(perhitunganStevedoring);
            perhitunganHandlingDischargeFacade.create(perhitunganHt);
            perhitunganHandlingDischargeFacade.create(perhitunganOtherHandling);
            perhitunganUpahBuruhFacadeRemote.create(perhitunganUpahBuruh);
        }
        
        if (perhitunganPenumpukan.getTotalCharge().compareTo(BigDecimal.ZERO) > 0) {
            perhitunganPenumpukanFacade.create(perhitunganPenumpukan);
        }

        deliveryServiceFacade.create(deliveryService);
        perhitunganLiftServiceFacade.create(perhitunganLiftService);
        perhitunganPassGateFacadeRemote.create(perhitunganPassGate);
    }

    public void handleCancelInvoice(ActionEvent event) {
        boolean isValid = false;

        registration = registrationFacade.find(invoice.getNoReg());
        batalNota.setCustCode(registration.getMasterCustomer().getCustCode());
        batalNota.setNoFakturPajak(invoice.getNoFakturPajak());
        final String noInvoice = invoice.getNoInvoice();
        batalNota.setNoInvoice(noInvoice);
        batalNota.setNoReg(invoice.getNoReg());
        batalNota.setPaymentDate(invoice.getPaymentDate());
        batalNota.setTotalTagihan(invoice.getTotalTagihan());

        try {
            deliveryServiceFacade.deleteByPpkbAndReg(registration.getPlanningVessel().getNoPpkb(), registration.getNoReg());
            perhitunganLiftServiceFacade.deleteByNoReg(registration.getNoReg());
            perhitunganHandlingDischargeFacade.deleteByNoReg(registration.getNoReg());
            perhitunganPenumpukanFacade.deleteByNoReg(registration.getNoReg());
            perhitunganPassGateFacadeRemote.deleteByNoReg(registration.getNoReg());
            perhitunganUpahBuruhFacadeRemote.deleteByNoReg(registration.getNoReg());

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
            isValid = true;
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception", re);
        }

        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.addCallbackParam("loggedIn", isValid);
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        viewData();
    }

    public void handleSelect(ActionEvent event) {
        String contNo = (String) event.getComponent().getAttributes().get("noCont");
        deliveryService = deliveryServiceFacade.findByContNoPpkbAndReg(contNo, registration.getPlanningVessel().getNoPpkb(), no_reg);
    }

    public void handleSubmit(ActionEvent event) {
        invoice.setPpn(total.multiply(ppnPrint));
        invoice.setMaterai(materaiPrint);
        invoice.setJumlahTagihan(total);
        invoice.setAdditionalCharge(upahBuruh);
        invoice.setTotalTagihan(invoice.getJumlahTagihan().add(invoice.getMaterai()).add(invoice.getPpn()).add(invoice.getAdditionalCharge()));
        invoice.setNoReg(no_reg);
        invoice.setRegistration(registration);
        invoice.setPaymentStatus("UNPAID");
        invoice.setValidasiPrint("FALSE");
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
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", true);
    }

    public void handleDelete(ActionEvent event) {
        perhitunganLiftServiceFacade.deleteByContNoAndNoReg(deliveryService.getContNo(), deliveryService.getRegistration().getNoReg());
        perhitunganHandlingDischargeFacade.deleteByContNoAndNoReg(deliveryService.getContNo(), deliveryService.getRegistration().getNoReg());
        perhitunganPenumpukanFacade.deleteByContNoAndNoReg(deliveryService.getContNo(), deliveryService.getRegistration().getNoReg());
        perhitunganPassGateFacadeRemote.deleteByJobSlip(deliveryService.getJobSlip());
        perhitunganUpahBuruhFacadeRemote.deleteByJobslip(deliveryService.getJobSlip());
        deliveryServiceFacade.remove(deliveryService);
    
        viewData();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
    }

    public void handleFindBl(ActionEvent event) {
        serviceContDischarges = serviceContDischargeFacade.findServiceContDischargesDeliveryServiceByBL(blno, registration.getPlanningVessel().getNoPpkb());
        if (serviceContDischarges.isEmpty()) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.notfound");
        }
    }

    public void handleFindAllBl(ActionEvent event) {
        blno = "";
        serviceContDischarges = serviceContDischargeFacade.findServiceContDischargesDeliveryService(registration.getPlanningVessel().getNoPpkb());
        if (serviceContDischarges.isEmpty()) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.notfound");
        }
    }

    public void handleDownloadTransactionRecap(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/delivery.pdf?no_reg=" + no_reg + "&total=" + String.valueOf(total) + "&ppn=" + String.valueOf(getPpnPrint()) + "&materai=" + String.valueOf(getMateraiPrint()) + "&userId=" + getUserId() + "&tipe=delivery&inclHandling=" + String.valueOf(inclHandling))));
    }

    public void handleDownloadNota(ActionEvent event) {
        invoice = invoiceFacade.find(no_reg);

        //validasi print ulang invoice

        if (invoice.getValidasiPrint().equals("FALSE")) {
            invoice = invoiceFacade.publishInvoice(invoice);
        }

        viewData();

        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/invoice.pdf?no_reg=" + no_reg + "&to=" + invoice.getTotalTagihan().toString() + "&curr=" + masterCurrency.getCountry() + "&tipe=" + registration.getMasterService().getServiceCode() + "&username=" + getUserId() + "&detail=false")));
    }

    public void handleDownloadDetail(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();

        if (registration.getInvoice() == null || registration.getInvoice().getNoInvoice() == null) {
            context.addCallbackParam("doPrint", false);
            FacesHelper.addFacesMessage(fc, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.empty_invoice_number");
            return;
        }

        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/detail.pdf?no_reg=" + no_reg + "&tipe=" + registration.getMasterService().getServiceCode() + "&username=" + getUserId() + "&ppn=" + String.valueOf(ppnPrint) + "&materai=" + String.valueOf(materaiPrint) + "&total=" + String.valueOf(total))));
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
     * @return the deliveryService
     */
    public DeliveryService getDeliveryService() {
        return deliveryService;
    }

    /**
     * @param deliveryService the deliveryService to set
     */
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
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
     * @return the serviceContDischarges
     */
    public List<Object[]> getServiceContDischarges() {
        return serviceContDischarges;
    }

    /**
     * @param serviceContDischarges the serviceContDischarges to set
     */
    public void setServiceContDischarges(List<Object[]> serviceContDischarges) {
        this.serviceContDischarges = serviceContDischarges;
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

    public BatalNota getBatalNota() {
        return batalNota;
    }

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

    public LoginSessionBean getLoginSessionBean() {
        return loginSessionBean;
    }

    public void setLoginSessionBean(LoginSessionBean loginSessionBean) {
        this.loginSessionBean = loginSessionBean;
    }

    public Date getTanggalValidDate() {
        return tanggalValidDate;
    }

    public void setTanggalValidDate(Date tanggalValidDate) {
        this.tanggalValidDate = tanggalValidDate;
    }
    
    public Date getTanggalValidDate2() {
        return tanggalValidDate2;
    }

    public void setTanggalValidDate2(Date tanggalValidDate2) {
        this.tanggalValidDate2 = tanggalValidDate2;
    }
    
    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    public BigDecimal getUpahBuruh() {
        return upahBuruh;
    }

    public Boolean getIsSimpatReady() {
        return isSimpatReady;
    }
}

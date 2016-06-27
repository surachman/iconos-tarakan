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
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDiscountFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganMonitoringFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPluggingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PluggingOnlyRekapFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PluggingReeferServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateDeliveryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReeferFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PerhitunganMonitoring;
import com.pelindo.ebtos.model.db.PerhitunganPlugging;
import com.pelindo.ebtos.model.db.PluggingOnlyRekap;
import com.pelindo.ebtos.model.db.PluggingReeferService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceReefer;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
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
 * @author wulan
 */
@ManagedBean(name = "perhitunganPluggingDeliveryBean")
@ViewScoped
public class PerhitunganPluggingDeliveryBean implements Serializable {

    @EJB
    private RegistrationFacadeRemote registrationFacadeRemote;
    @EJB
    private PluggingReeferServiceFacadeRemote pluggingReeferServiceFacadeRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private PluggingOnlyRekapFacadeRemote pluggingOnlyRekapFacadeRemote;
    @EJB
    private ServiceReeferFacadeRemote serviceReeferFacadeRemote;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacadeRemote;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacadeRemote;
    @EJB
    private PerhitunganPluggingFacadeRemote perhitunganPluggingFacadeRemote;
    @EJB
    private PerhitunganMonitoringFacadeRemote perhitunganMonitoringFacadeRemote;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacadeRemote;
    @EJB
    private MasterDiscountFacadeRemote masterDiscountFacadeRemote;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacadeRemote;
    @EJB
    private InvoiceFacadeRemote invoiceFacadeRemote;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private BatalNotaFacadeRemote batalNotaFacadeRemote;
    @EJB
    private ServiceGateDeliveryFacadeRemote serviceGateDeliveryFacadeRemote;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;

    /** Creates a new instance of PluggingReceiving */
    private List<Object[]> pluggingServices;
    private Registration registration;
    private List<Object[]> registrations;
    private String no_reg;
    private List<Object[]> listDeliverySelect;
    private List<Object[]> masterContainerTypes, masterCommoditys;
    private PluggingReeferService pluggingReeferService;
    private PluggingOnlyRekap pluggingOnlyRekap;
    private String status = "DELIVERY";
    private PerhitunganPlugging perhitunganPlugging;
    private PerhitunganMonitoring perhitunganMonitoring;
    private BigDecimal ppnPrint = BigDecimal.ZERO;
    private BigDecimal materaiPrint = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;
    private Invoice invoice;
    private String userId;
    private BatalNota batalNota;
    private Boolean disPrint = true;
    private Boolean disDetail = true;
    private Boolean disCancelInv = true;
    private Boolean disKredit = false;
    private String to;
    private String symbol;
    private String language = null;
    private String country = null;
    private Boolean isSimpatReady;

    /** Creates a new instance of PerhitunganPluggingReceivingBean */
    public PerhitunganPluggingDeliveryBean() {}

    @PostConstruct
    private void construct() {
        registrations = registrationFacadeRemote.findRegistrationPluggingOnly();

        masterContainerTypes = masterContainerTypeFacadeRemote.findReefer();
        masterCommoditys = masterCommodityFacadeRemote.findAllNative();
        perhitunganPlugging = new PerhitunganPlugging();
        perhitunganMonitoring = new PerhitunganMonitoring();
        pluggingOnlyRekap = new PluggingOnlyRekap();
        pluggingReeferService = new PluggingReeferService();
        perhitunganPlugging.setRegistration(new Registration());
        perhitunganMonitoring.setRegistration(new Registration());
        registration = new Registration();
        invoice = new Invoice();
        batalNota = new BatalNota();
        invoice.setPaymentType("CASH");
        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        userId = loginSessionBean.getName();
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
    }

    public void clear() {
        masterContainerTypes = masterContainerTypeFacadeRemote.findReefer();
        masterCommoditys = masterCommodityFacadeRemote.findAllNative();
        perhitunganPlugging = new PerhitunganPlugging();
        perhitunganMonitoring = new PerhitunganMonitoring();
        pluggingOnlyRekap = new PluggingOnlyRekap();
        pluggingReeferService = new PluggingReeferService();
        perhitunganPlugging.setRegistration(new Registration());
        perhitunganMonitoring.setRegistration(new Registration());
        invoice = new Invoice();
        invoice.setPaymentType("CASH");
        batalNota = new BatalNota();
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacadeRemote.find(no_reg);
        pluggingServices = pluggingReeferServiceFacadeRemote.findPerhitunganDeliveryPluggingService(no_reg);
        listDeliverySelect = pluggingReeferServiceFacadeRemote.findSelectDeliveryPluggingReeferService(registration.getMasterCustomer().getCustCode());
        viewData();
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
        pluggingReeferService = new PluggingReeferService();
        registrations = registrationFacadeRemote.findRegistrationPluggingOnly();
        pluggingOnlyRekap = new PluggingOnlyRekap();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void handleSelect(ActionEvent event) {
        String job = (String) event.getComponent().getAttributes().get("noCont");
        Integer idPlug = (Integer) event.getComponent().getAttributes().get("idPlug");
        Integer inMon = (Integer) event.getComponent().getAttributes().get("idMon");

        pluggingOnlyRekap = pluggingOnlyRekapFacadeRemote.find(job);
        if (idPlug != null) {
            perhitunganPlugging = perhitunganPluggingFacadeRemote.find(idPlug);
        }
        if (inMon != null) {
            perhitunganMonitoring = perhitunganMonitoringFacadeRemote.find(inMon);
        }
    }

    public void viewData() {
        if (no_reg != null) {
            pluggingServices = pluggingReeferServiceFacadeRemote.findPerhitunganDeliveryPluggingService(no_reg);
            if (pluggingServices.size() > 10) {
                disDetail = false;
            } else {
                disDetail = true;
            }
            invoice = invoiceFacadeRemote.find(no_reg);

            BigDecimal t = BigDecimal.ZERO;
            for (Object[] o : pluggingServices) {

                this.symbol = "Rp";
                this.language = "id";
                this.country = "ID";
                t = t.add((BigDecimal) o[17]);

            }
            total = t;

            //ambil nilai ppn
            MasterSettingApp settingApp = new MasterSettingApp();
            settingApp = masterSettingAppFacadeRemote.find("ppn");
            setPpnPrint(settingApp.getValueFloat());

            //ambil nilai materai
            MasterCurrency masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
            List<Object[]> materai = new ArrayList<Object[]>();

            materai = masterMateraiFacadeRemote.findByCurr(masterCurrency.getCurrCode());
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
                        //pluggingReeferService = pluggingReeferServiceFacadeRemote.find(ob[0]);
                        //Object[] gate=null;
                        String job = serviceGateDeliveryFacadeRemote.findByContAndNoreg((String) ob[0]);
                        if (job == null) {
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
    }

    public void handleDelete(ActionEvent event) {
        try {
            pluggingOnlyRekapFacadeRemote.remove(pluggingOnlyRekap);
            perhitunganMonitoringFacadeRemote.remove(perhitunganMonitoring);
            perhitunganPluggingFacadeRemote.remove(perhitunganPlugging);
            pluggingServices = pluggingReeferServiceFacadeRemote.findPerhitunganDeliveryPluggingService(no_reg);
            listDeliverySelect = pluggingReeferServiceFacadeRemote.findSelectDeliveryPluggingReeferService(registration.getMasterCustomer().getCustCode());
            this.clear();
            viewData();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException e) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.failed");
        }
    }

    public void handleSave(ActionEvent event) {
        Boolean loggedIn = true;
        ServiceReefer sc=new ServiceReefer();
        pluggingOnlyRekap.setJobSlip(pluggingReeferService.getJobSlip());
        //set plugOnDate ambil dari service_reefer yang cont_no dan no_ppkb nya sama
        Date plugOn = serviceReeferFacadeRemote.findPlugOnForReeferPluggingService(pluggingReeferService.getJobSlip());
        Date plugOff = serviceReeferFacadeRemote.findPlugOffForReeferPluggingService(pluggingReeferService.getJobSlip());
        Integer idServiceRef = serviceReeferFacadeRemote.findValidasiUpdatePlugging(pluggingReeferService.getJobSlip(), pluggingReeferService.getContNo(), "PLUGGING");
        sc=serviceReeferFacadeRemote.find(idServiceRef);
        sc.setPlugOff(Calendar.getInstance().getTime());
        //reeferDischargeService.setPlugOnDate(plugOn);

        // set shift planning : (now() - plugOnDate) / 8, dibulatkan keatas
        Date now = Calendar.getInstance().getTime();
        int shiftPlan = 0;
        if (plugOff == null) {
            Long k = now.getTime() - plugOn.getTime();
            shiftPlan = (int) Math.ceil(Double.parseDouble(String.valueOf(k * 24)) / (8 * 86400000));
            if (shiftPlan < 1) {
                shiftPlan = 1;
            }
        }

        pluggingReeferService.setNoRegDel(no_reg);

        pluggingReeferService.setShiftPlanning(Short.parseShort(String.valueOf(shiftPlan)));

        // insert ke perhitungan_plugging
        perhitunganPlugging.getRegistration().setNoReg(registration.getNoReg());
        perhitunganPlugging.setContNo(pluggingReeferService.getContNo());
        perhitunganPlugging.setMlo(pluggingReeferService.getMlo());
        perhitunganPlugging.setNoPpkb(registration.getNo_ppkb_plug());

        // menentukan id activity
        String pluggingActivityCode = null;
        if (pluggingReeferService.getContSize() == 20) {
            pluggingActivityCode = "D001";
        } else {
            pluggingActivityCode = "D002";
        }
        perhitunganPlugging.setMasterActivity(masterActivityFacadeRemote.find(pluggingActivityCode));

        // set amount berdasarkan tipe pelayaran
        BigDecimal cPlugD = BigDecimal.ZERO;
        BigDecimal cPlugI = BigDecimal.ZERO;
        for (Object[] o1 : masterTarifContFacadeRemote.findAmountByActivity(pluggingActivityCode)) {
            if (o1[1].equals("IDR")) {
                cPlugD = (BigDecimal) o1[0];
            } else {
                cPlugI = (BigDecimal) o1[0];
            }
        }

        // insert ke perhitungan_monitoring
        perhitunganMonitoring.setRegistration(registration);
        perhitunganMonitoring.setContNo(pluggingReeferService.getContNo());
        perhitunganMonitoring.setMlo(pluggingReeferService.getMlo());
        perhitunganMonitoring.setNoPpkb(registration.getNo_ppkb_plug());

        // menentukan id activity
        String monitoringActivityCode = null;
        if (pluggingReeferService.getContSize() == 20) {
            monitoringActivityCode = "D003";
        } else {
            monitoringActivityCode = "D004";
        }
        perhitunganMonitoring.setMasterActivity(masterActivityFacadeRemote.find(monitoringActivityCode));

        // set amount berdasarkan tipe pelayaran
        BigDecimal cMonD = BigDecimal.ZERO;
        BigDecimal cMonI = BigDecimal.ZERO;
        for (Object[] o1 : masterTarifContFacadeRemote.findAmountByActivity(monitoringActivityCode)) {
            if (o1[1].equals("IDR")) {
                cMonD = (BigDecimal) o1[0];
            } else {
                cMonI = (BigDecimal) o1[0];
            }
        }

        BigDecimal chargePlugging = BigDecimal.ZERO;
        BigDecimal chargeMonitoring = BigDecimal.ZERO;
//            if (registration.getPlanningVessel().getTipePelayaran().equals("d")) {
        perhitunganPlugging.setCurrCode("IDR");
        perhitunganMonitoring.setCurrCode("IDR");
        chargePlugging = cPlugD;
        chargeMonitoring = cMonD;
//            } else {
//                perhitunganPlugging.setCurrCode("USD");
//                perhitunganMonitoring.setCurrCode("USD");
//                cPlug = cPlugI;
//                cMon = cMonI;
//            }

        BigDecimal[] discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), pluggingActivityCode);
        BigDecimal discountAsPercentage = discount[0];
        BigDecimal discountAsMoney = discount[1];

        if (discountAsMoney == null) {
            discountAsMoney = chargePlugging.multiply(discountAsPercentage);
        }

        perhitunganPlugging.setCharge(chargePlugging.subtract(discountAsMoney));
        BigDecimal totalPluggingCharge = perhitunganPlugging.getCharge().multiply(BigDecimal.valueOf(pluggingReeferService.getShiftPlanning()));
        perhitunganPlugging.setTotalCharge(totalPluggingCharge);
        perhitunganPluggingFacadeRemote.create(perhitunganPlugging);

        discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), monitoringActivityCode);
        discountAsPercentage = discount[0];
        discountAsMoney = discount[1];
        BigDecimal totalMonitoringCharge = BigDecimal.ZERO;

        if (discountAsMoney == null) {
            discountAsMoney = chargeMonitoring.multiply(discountAsPercentage);
        }

        perhitunganMonitoring.setCharge(chargeMonitoring.subtract(discountAsMoney));
        totalMonitoringCharge = perhitunganMonitoring.getCharge().multiply(BigDecimal.valueOf(pluggingReeferService.getShiftPlanning()));
        perhitunganMonitoring.setTotalCharge(totalMonitoringCharge);
        perhitunganMonitoringFacadeRemote.create(perhitunganMonitoring);

        handleConfirm();
        serviceReeferFacadeRemote.edit(sc);
        pluggingReeferServiceFacadeRemote.edit(pluggingReeferService);
        this.clear();
        pluggingServices = pluggingReeferServiceFacadeRemote.findPerhitunganDeliveryPluggingService(no_reg);
        listDeliverySelect = pluggingReeferServiceFacadeRemote.findSelectDeliveryPluggingReeferService(registration.getMasterCustomer().getCustCode());
        viewData();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleConfirm() {
        boolean loggedIn = false;
        if (pluggingReeferService.getContNo() == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.transaction.notcompleted");
        } else {
            loggedIn = true;
            Calendar now = Calendar.getInstance();
            int min = DateCalculator.countRangeInDays(now.getTime(), pluggingReeferService.getPlacementDate()) + 1;

            Calendar placementDate = Calendar.getInstance();
            placementDate.setTime(pluggingReeferService.getPlacementDate());
            placementDate.set(Calendar.HOUR_OF_DAY, 23);
            placementDate.set(Calendar.MINUTE, 59);
            placementDate.set(Calendar.SECOND, 59);
            placementDate.set(Calendar.MILLISECOND, 0);
            placementDate.add(Calendar.DAY_OF_MONTH, 2);

            now.set(Calendar.HOUR_OF_DAY, 23);
            now.set(Calendar.MINUTE, 59);
            now.set(Calendar.SECOND, 59);
            now.set(Calendar.MILLISECOND, 0);

            int masaFree = masterSettingAppFacade.getMasa1FreeRange();
            int masa1Range = masterSettingAppFacade.getMasa1ContainerRange();
            int m1 = min - (masaFree - 1);

            if (min < (masa1Range + 1)) {
                if (min <= masaFree) {
                    pluggingOnlyRekap.setMassa1((short) 1);
                } else {
                    pluggingOnlyRekap.setMassa1((short) m1);
                }
                pluggingOnlyRekap.setMassa2((short) 0);
            } else {
                int masa2 = min - masa1Range;
                int masa1 = m1 - masa2;
                pluggingOnlyRekap.setMassa1((short) masa1);
                pluggingOnlyRekap.setMassa2((short) masa2);
            }

            pluggingOnlyRekap.setNoReg(registration.getNoReg());
            
            String dangerousClass = null;
            if(pluggingReeferService.getMasterCommodity() != null && pluggingReeferService.getMasterCommodity().getMasterDangerousClass() != null)
                dangerousClass = pluggingReeferService.getMasterCommodity().getMasterDangerousClass().getDangerousClass();

            String loloActivityCode = masterActivityFacadeRemote.translateLoLoActivityCode(
                    pluggingReeferService.getContStatus(), 
                    pluggingReeferService.getContSize(), 
                    pluggingReeferService.getOverSize().equals("TRUE") ? true : false, 
                    pluggingReeferService.getDg().equals("TRUE"),
                    pluggingReeferService.getDgLabel().equals("TRUE"),
                    dangerousClass,"CC");
            pluggingOnlyRekap.setActivityLift(loloActivityCode);

            // set amount berdasarkan tipe pelayaran
            BigDecimal loloDomesticCharge = BigDecimal.ZERO;
            BigDecimal loloInternationalCharge = BigDecimal.ZERO;
            BigDecimal penumpukanDomesticCharge = BigDecimal.ZERO;
            BigDecimal penumpukanInternationalCharge = BigDecimal.ZERO;
            List<Object[]> listTarif = new ArrayList<Object[]>();
            listTarif = masterTarifContFacadeRemote.findAmountByActivity(loloActivityCode);
            for (Object[] o1 : listTarif) {
                if (o1[1].equals("IDR")) {
                    loloDomesticCharge = (BigDecimal) o1[0];
                } else {
                    loloInternationalCharge = (BigDecimal) o1[0];
                }
                pluggingOnlyRekap.setCurrCode((String) o1[1]);
            }

            Integer idReefer = serviceReeferFacadeRemote.findValidasiPenumpukan(pluggingReeferService.getNoPpkb(), pluggingReeferService.getContNo(), "DISCHARGE");
            String penumpukanActivityCode = masterActivityFacadeRemote.translatePenumpukanActivityCode(
                    pluggingReeferService.getContStatus(), 
                    pluggingReeferService.getMasterContainerType().getIsoCode(), 
                    pluggingReeferService.getOverSize().equals("TRUE") ? true : false, 
                    pluggingReeferService.getContSize(), idReefer > 0);
            listTarif = masterTarifContFacadeRemote.findAmountByActivity(penumpukanActivityCode);
            for (Object[] o1 : listTarif) {
                if (o1[1].equals("IDR")) {
                    penumpukanDomesticCharge = (BigDecimal) o1[0];
                } else {
                    penumpukanInternationalCharge = (BigDecimal) o1[0];
                }
            }
            pluggingOnlyRekap.setActivityLift(loloActivityCode);

            // set charge dan jasa dermaga berdasarkan tipe pelayaran
            BigDecimal penumpukanCharge = penumpukanDomesticCharge;
            BigDecimal loloCharge = loloDomesticCharge;

            //HITUNG LIFT SERVICE RECEIVING DELIVERY

            BigDecimal[] discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), loloActivityCode);
            BigDecimal discountAsPercentage = discount[0];
            BigDecimal discountAsMoney = discount[1];
            BigDecimal totalDeliveryCharge = BigDecimal.ZERO;
            BigDecimal totalReceivingCharge = BigDecimal.ZERO;

            if (discountAsMoney == null) {
                discountAsMoney = loloCharge.multiply(discountAsPercentage);
            }

            loloCharge = loloCharge.subtract(discountAsMoney);

            totalDeliveryCharge = loloCharge;
            totalReceivingCharge = loloCharge;

            pluggingOnlyRekap.setChargeDeliveryLift(totalDeliveryCharge);
            pluggingOnlyRekap.setChargeReceivingLift(totalReceivingCharge);

            discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), penumpukanActivityCode);
            discountAsPercentage = discount[0];
            discountAsMoney = discount[1];
            BigDecimal totalPenumpukanCharge = BigDecimal.ZERO;

            if (discountAsMoney == null) {
                discountAsMoney = penumpukanCharge.multiply(discountAsPercentage);
            }

            penumpukanCharge = penumpukanCharge.subtract(discountAsMoney);

            if (pluggingReeferService.getDg().equals("TRUE") && pluggingReeferService.getDgLabel().equals("TRUE")) {
                totalPenumpukanCharge = penumpukanCharge.multiply(BigDecimal.valueOf(2));
            } else if (pluggingReeferService.getDg().equals("TRUE") && pluggingReeferService.getDgLabel().equals("FALSE")) {
                totalPenumpukanCharge = penumpukanCharge.multiply(BigDecimal.valueOf(3));
            } else {
                totalPenumpukanCharge = penumpukanCharge;
            }

            pluggingOnlyRekap.setChargePenumpukan(totalPenumpukanCharge);
            pluggingOnlyRekap.setActivityTumpuk(penumpukanActivityCode);
            pluggingOnlyRekap.setChargeMassa1(pluggingOnlyRekap.getChargePenumpukan().multiply(BigDecimal.valueOf(pluggingOnlyRekap.getMassa1())));
            pluggingOnlyRekap.setChargeMassa2(pluggingOnlyRekap.getChargePenumpukan().multiply(BigDecimal.valueOf(pluggingOnlyRekap.getMassa2())));
            pluggingOnlyRekap.setTotalCharge(pluggingOnlyRekap.getChargeMassa1().add(pluggingOnlyRekap.getChargeMassa2()).add(pluggingOnlyRekap.getChargeDeliveryLift()).add(pluggingOnlyRekap.getChargeReceivingLift()).add(perhitunganMonitoring.getTotalCharge()).add(perhitunganPlugging.getTotalCharge()));

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
            pluggingOnlyRekapFacadeRemote.create(pluggingOnlyRekap);
            viewData();
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
        settingApp = masterSettingAppFacadeRemote.find("ppn");
        setPpnPrint(settingApp.getValueFloat());
        BigDecimal ppn = invoice.getJumlahTagihan().multiply(settingApp.getValueFloat());
        invoice.setPpn(ppn);

        MasterCurrency masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        List<Object[]> materai = new ArrayList<Object[]>();
        //if (registration.getPlanningVessel().getTipePelayaran().equals("d")) {

//        } else {
//            currCode = "USD";
//        }
        materai = masterMateraiFacadeRemote.findByCurr(masterCurrency.getCurrCode());

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
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/invoice.pdf?no_reg=" + no_reg + "&to=" + to + "&curr=ID&tipe=" + registration.getMasterService().getServiceCode() + "&username=" + getUserId() + "&detail=" + String.valueOf(disDetail))));
    }

    public void handleDownloadDetail(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/detail.pdf?no_reg=" + no_reg + "&tipe=" + registration.getMasterService().getServiceCode() + "&username=" + getUserId() + "&ppn=" + String.valueOf(ppnPrint) + "&materai=" + String.valueOf(materaiPrint) + "&total=" + String.valueOf(total))));
    }

    public void handleKlikCancel(ActionEvent event) {
        batalNota = new BatalNota();
    }

    public void handleCancelInvoice(ActionEvent event) {
        //input k batal nota
        Boolean loggedIn = false;
        try {
            loggedIn = true;
            registration = registrationFacadeRemote.find(invoice.getNoReg());
            batalNota.setCustCode(registration.getMasterCustomer().getCustCode());
            batalNota.setNoFakturPajak(invoice.getNoFakturPajak());
            final String noInvoice = invoice.getNoInvoice();
            batalNota.setNoInvoice(noInvoice);
            batalNota.setNoReg(invoice.getNoReg());
            batalNota.setPaymentDate(invoice.getPaymentDate());
            batalNota.setTotalTagihan(invoice.getTotalTagihan());

            for (Object[] o : pluggingReeferServiceFacadeRemote.findByRegCancelInvoice(no_reg)) {
                pluggingReeferService = pluggingReeferServiceFacadeRemote.find(o[0]);
                pluggingOnlyRekap = pluggingOnlyRekapFacadeRemote.find(pluggingReeferService.getJobSlip());
                pluggingOnlyRekapFacadeRemote.remove(pluggingOnlyRekap);
                pluggingReeferService.setShiftPlanning(Short.valueOf("0"));
            }


            for (Object ob : perhitunganMonitoringFacadeRemote.findByReg(no_reg)) {
                perhitunganMonitoring = perhitunganMonitoringFacadeRemote.find(ob);
                perhitunganMonitoringFacadeRemote.remove(perhitunganMonitoring);
            }

            for (Object oc : perhitunganPluggingFacadeRemote.findByReg(no_reg)) {
                perhitunganPlugging = perhitunganPluggingFacadeRemote.find(oc);
                perhitunganPluggingFacadeRemote.remove(perhitunganPlugging);
            }

            //save ke database
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
        } catch (EJBException e) {
            e.printStackTrace();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.cancel.failed");
        }
        viewData();
    }

    public void handleSelectNoCont(ActionEvent event) {
        String job = (String) event.getComponent().getAttributes().get("cont_no");
        pluggingReeferService = pluggingReeferServiceFacadeRemote.find(job);
    }

    public String getFormattedTotal() {
        NumberFormat numberFormat = new DecimalFormat("#,##0");
        return numberFormat.format(total).toString();
    }

    public String getFormattedPpn() {
        NumberFormat numberFormat = new DecimalFormat("#,##0");
        return numberFormat.format((total.multiply(ppnPrint))).toString();
    }

    public String getFormattedMaterai() {
        NumberFormat numberFormat = new DecimalFormat("#,##0");
        return numberFormat.format(materaiPrint).toString();
    }

    public String getFormattedJmlTotal() {
//        String local = null;
//        Locale trLocale = new Locale(language, country);
        NumberFormat numberFormat = new DecimalFormat("#,##0");
        return numberFormat.format((total.add((total.multiply(ppnPrint)))).add(materaiPrint)).toString();
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

    public PluggingOnlyRekap getPluggingOnlyRekap() {
        return pluggingOnlyRekap;
    }

    public void setPluggingOnlyRekap(PluggingOnlyRekap pluggingOnlyRekap) {
        this.pluggingOnlyRekap = pluggingOnlyRekap;
    }

    public PluggingReeferService getPluggingReeferService() {
        return pluggingReeferService;
    }

    public void setPluggingReeferService(PluggingReeferService pluggingReeferService) {
        this.pluggingReeferService = pluggingReeferService;
    }

    public String getNo_reg() {
        return no_reg;
    }

    public void setNo_reg(String no_reg) {
        this.no_reg = no_reg;
    }

    public List<Object[]> getPluggingServices() {
        return pluggingServices;
    }

    public void setPluggingServices(List<Object[]> pluggingServices) {
        this.pluggingServices = pluggingServices;
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

    public List<Object[]> getListDeliverySelect() {
        return listDeliverySelect;
    }

    public void setListDeliverySelect(List<Object[]> listDeliverySelect) {
        this.listDeliverySelect = listDeliverySelect;
    }

    public PerhitunganMonitoring getPerhitunganMonitoring() {
        return perhitunganMonitoring;
    }

    public void setPerhitunganMonitoring(PerhitunganMonitoring perhitunganMonitoring) {
        this.perhitunganMonitoring = perhitunganMonitoring;
    }

    public PerhitunganPlugging getPerhitunganPlugging() {
        return perhitunganPlugging;
    }

    public void setPerhitunganPlugging(PerhitunganPlugging perhitunganPlugging) {
        this.perhitunganPlugging = perhitunganPlugging;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Boolean getDisKredit() {
        return disKredit;
    }

    public void setDisKredit(Boolean disKredit) {
        this.disKredit = disKredit;
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Boolean getIsSimpatReady() {
        return isSimpatReady;
    }
}

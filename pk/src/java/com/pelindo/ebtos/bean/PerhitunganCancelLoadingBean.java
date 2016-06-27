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
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDiscountFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPassGateFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganUpahBuruhFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningReceivingAllocationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContTranshipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateDeliveryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReeferFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PerhitunganPassGate;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukan;
import com.pelindo.ebtos.model.db.PerhitunganUpahBuruh;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningReceivingAllocation;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceGateReceiving;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterDangerousClass;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import com.pelindo.ebtos.util.GrossConverter;
import com.qtasnim.util.DateCalculator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@ManagedBean(name = "perhitunganCancelLoadingBean")
@ViewScoped
public class PerhitunganCancelLoadingBean implements Serializable {

    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacade;
    @EJB
    private InvoiceFacadeRemote invoiceFacadeRemote;
    @EJB
    private RegistrationFacadeRemote registrationFacadeRemote;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote;
    @EJB
    private ServiceReeferFacadeRemote serviceReeferFacadeRemote;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private ServiceGateDeliveryFacadeRemote serviceGateDeliveryFacadeRemote;
    @EJB
    private ReceivingServiceFacadeRemote receivingServiceFacadeRemote;
    @EJB
    private ServiceContLoadFacadeRemote serviceContLoadFacadeRemote;
    @EJB
    private ServiceReceivingFacadeRemote serviceReceivingFacadeRemote;
    @EJB
    private PlanningContLoadFacadeRemote planningContLoadFacadeRemote;
    @EJB
    private MasterDiscountFacadeRemote masterDiscountFacadeRemote;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacadeRemote;
    @EJB
    private BatalNotaFacadeRemote batalNotaFacadeRemote;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacadeRemote;
    @EJB
    private PerhitunganPenumpukanFacadeRemote perhitunganPenumpukanFacadeRemote;
    @EJB
    private PerhitunganPassGateFacadeRemote perhitunganPassGateFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private PlanningReceivingAllocationFacadeRemote planningReceivingAllocationFacadeRemote;
    @EJB
    private PerhitunganUpahBuruhFacadeRemote perhitunganUpahBuruhFacadeRemote;
    @EJB
    private MasterPortFacadeRemote masterPortFacadeRemote;
    @EJB
    private MasterYardCoordinatFacadeRemote masterYardCoordinatFacadeRemote;
    @EJB
    private ServiceContTranshipmentFacadeRemote serviceContTranshipmentFacadeRemote;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private ServiceGateReceivingFacadeRemote serviceGateReceivingFacadeRemote;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;

    private List<Object[]> registrations;
    private List<Object[]> perhitunganCancelLoadings;
    private List<Object[]> newVessels;
    private List<Object[]> masterYards;
    private Map<Integer, Integer> slots;
    private Map<Integer, Integer> rows;
    private Map<Integer, Integer> tiers;
    private CancelLoadingService cancelLoadingService;
    private PlanningContLoad planningContLoad;
    private ServiceContLoad serviceContLoad;
    private ReceivingService receivingService;
    private Registration registration;
    private MasterCurrency masterCurrency;
    private MasterPort implementedPort;
    private MasterYard masterYard;
    private PlanningVessel newPlanningVessel;
    private Invoice invoice;
    private BigDecimal ppn = BigDecimal.ZERO;
    private BigDecimal ppnPrint = BigDecimal.ZERO;
    private BigDecimal materaiPrint = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal upahBuruh = BigDecimal.ZERO;
    private BatalNota batalNota;
    private String userId = null;
    private String mode;
    private Short category;
    private Boolean isNotPullOutAble = false;
    private Boolean disPrint = true;
    private Boolean disKredit = false;
    private Boolean disDetail = false;
    private Boolean disCancelInv = true;
    private Object[] receivingStatus;
    private String selectedPort1;
    private Boolean isSimpatReady;
    private Boolean noCharge;
    private Boolean noRelocation;

    /** Creates a new instance of PerhitunganCancelLoadingBean */
    public PerhitunganCancelLoadingBean() {
    }

    @PostConstruct
    private void construct() {
        noCharge = false;
        registration = new Registration();
        invoice = new Invoice();
        invoice.setPaymentType("CASH");
        cancelLoadingService = new CancelLoadingService();
        isNotPullOutAble = false;
        cancelLoadingService = new CancelLoadingService();
        receivingService = new ReceivingService();
        planningContLoad = new PlanningContLoad();
        batalNota = new BatalNota();
        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        userId = loginSessionBean.getName();
        slots = new LinkedHashMap<Integer, Integer>();
        rows = new LinkedHashMap<Integer, Integer>();
        tiers = new LinkedHashMap<Integer, Integer>();
        masterYards = masterYardFacadeRemote.findMasterYards();
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
    }

    public void prepareHandleSelectRegistration() {
        registrations = registrationFacadeRemote.findRegistrationBatalMuat();
    }

    public void handleSelectRegistration(ActionEvent event) {
        String noReg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacadeRemote.find(noReg);
        viewData();

        noCharge = false;

        if (perhitunganCancelLoadings != null && !perhitunganCancelLoadings.isEmpty() && total.compareTo(BigDecimal.ZERO) == 0) {
            noCharge = true;
        }
    }

    private void viewData() {
        perhitunganCancelLoadings = cancelLoadingServiceFacadeRemote.findPerhitungan(registration.getNoReg());

        disDetail = false;
        total = BigDecimal.ZERO;
        upahBuruh = BigDecimal.ZERO;

        for (Object[] o : perhitunganCancelLoadings) {
            total = total.add((BigDecimal) (o[21])).add((BigDecimal) (o[22])).add((BigDecimal) (o[24])).add((BigDecimal) (o[25]));
            upahBuruh = upahBuruh.add((BigDecimal) o[27]);
        }

        //ambil nilai ppn
        MasterSettingApp ppnSetting = masterSettingAppFacade.find("ppn");
        ppnPrint = ppnSetting.getValueFloat();
        ppn = total.multiply(ppnPrint);
        masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();

        List<Object[]> materai = masterMateraiFacade.findByCurr(masterCurrency.getCurrCode());

        if (total.compareTo((BigDecimal) materai.get(1)[2]) == -1) {
            materaiPrint = (BigDecimal) materai.get(0)[1];
        } else if (total.compareTo((BigDecimal) materai.get(2)[2]) == -1) {
            materaiPrint = (BigDecimal) materai.get(1)[1];
        } else {
            materaiPrint = (BigDecimal) materai.get(2)[1];
        }

        invoice = invoiceFacadeRemote.find(registration.getNoReg());

        if (invoice != null) {
            if (invoice.getValidasiPrint().equals("TRUE")) {
                disPrint = true;
            } else {
                disPrint = false;
            }

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
            disCancelInv = false;
            for (Object[] ob : perhitunganCancelLoadings) {
                cancelLoadingService = cancelLoadingServiceFacadeRemote.find(ob[0]);
                if (serviceGateDeliveryFacadeRemote.findByContAndPpkb(cancelLoadingService.getContNo(), cancelLoadingService.getPlanningVessel().getNoPpkb()) == null) {
                    if (disCancelInv == false) {
                        disCancelInv = false;
                    }
                } else {
                    disCancelInv = true;
                }
            }
            /*akhir pengecekan container di gate*/
        }

    }

    public void handleDelete(ActionEvent event) {
        try {

            if (cancelLoadingService.getCategory() == 1) {
                receivingService = receivingServiceFacadeRemote.findByNoPpkbAndContNoCancelLoading(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                receivingService.setStatusCancelLoading("FALSE");

                receivingServiceFacadeRemote.edit(receivingService);
            } else if (cancelLoadingService.getCategory() == 2) {
                planningContLoad = planningContLoadFacadeRemote.findByNoPpkbContNoAndStatusCancelLoading(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), true);

                if (cancelLoadingService.getIsTranshipment().equals("TRUE")) {
                    ServiceContTranshipment serviceContTranshipment = serviceContTranshipmentFacadeRemote.findByNewPpkbAndContNo(cancelLoadingService.getNewPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                    serviceContTranshipment.setNewPpkb(registration.getPlanningVessel().getNoPpkb());

                    serviceContTranshipmentFacadeRemote.edit(serviceContTranshipment);
                } else {
                    receivingService = receivingServiceFacadeRemote.findByNoPpkbAndContNoCancelLoading(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                    receivingService.setStatusCancelLoading("FALSE");

                    ServiceReceiving serviceReceiving = serviceReceivingFacadeRemote.findByNoPpkbAndContNoCancelLoading(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                    serviceReceiving.setStatusCancelLoading("FALSE");

                    receivingServiceFacadeRemote.edit(receivingService);
                    serviceReceivingFacadeRemote.edit(serviceReceiving);
                }

                planningContLoad.setStatusCancelLoading("FALSE");

                String grossClass = GrossConverter.convert(cancelLoadingService.getContSize(), cancelLoadingService.getContGross());

                if (cancelLoadingService.getNewPlanningVessel().getPreserviceVessel().getMasterVessel().isKapalBayangan().equals("FALSE")) {
                    PlanningReceivingAllocation planningReceivingAllocation = planningReceivingAllocationFacadeRemote.findByAllocationConstraint(cancelLoadingService.getNewPlanningVessel().getNoPpkb(), cancelLoadingService.getContSize(), cancelLoadingService.getMasterContainerType().getContType(), cancelLoadingService.getContStatus(), cancelLoadingService.getOverSize(), cancelLoadingService.getDg(), grossClass, cancelLoadingService.getDischargePort().getPortCode(), cancelLoadingService.getIsExport());
                    planningReceivingAllocation.setTotalBooking2(planningReceivingAllocation.getTotalBooking2() - 1);
                    planningReceivingAllocationFacadeRemote.edit(planningReceivingAllocation);
                }

                planningContLoadFacadeRemote.edit(planningContLoad);
                planningContLoadFacadeRemote.deleteByNoPpkbAndContNo(cancelLoadingService.getNewPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
            } else if (cancelLoadingService.getCategory() == 3) {
                planningContLoad = planningContLoadFacadeRemote.findByNoPpkbContNoAndStatusCancelLoading(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), true);

                if (cancelLoadingService.getIsTranshipment().equals("FALSE")) {
                    receivingService = receivingServiceFacadeRemote.findByNoPpkbAndContNoCancelLoading(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                    receivingService.setStatusCancelLoading("FALSE");
                    receivingServiceFacadeRemote.edit(receivingService);
                    ServiceReceiving serviceReceiving = serviceReceivingFacadeRemote.findByNoPpkbAndContNoCancelLoading(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                    serviceReceiving.setStatusCancelLoading("FALSE");
                    serviceReceivingFacadeRemote.edit(serviceReceiving);
                }

                planningContLoad.setStatusCancelLoading("FALSE");

                perhitunganPassGateFacadeRemote.deleteByJobSlip(cancelLoadingService.getJobSlip());
                perhitunganPenumpukanFacadeRemote.deleteByContNoAndNoReg(cancelLoadingService.getContNo(), cancelLoadingService.getRegistration().getNoReg());
                planningContLoadFacadeRemote.edit(planningContLoad);
            } else if (cancelLoadingService.getCategory() == 4) {
                planningContLoad = planningContLoadFacadeRemote.findByNoPpkbContNoAndStatusCancelLoading(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), true);
                serviceContLoad = serviceContLoadFacadeRemote.findByNoPpkbAndContNo(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());

                if (cancelLoadingService.getIsTranshipment().equals("TRUE")) {
                    ServiceContTranshipment serviceContTranshipment = serviceContTranshipmentFacadeRemote.findByNewPpkbAndContNo(cancelLoadingService.getNewPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                    serviceContTranshipment.setNewPpkb(registration.getPlanningVessel().getNoPpkb());

                    serviceContTranshipmentFacadeRemote.edit(serviceContTranshipment);
                } else {
                    ServiceReceiving serviceReceiving = serviceReceivingFacadeRemote.findByNoPpkbAndContNoCancelLoading(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                    serviceReceiving.setStatusCancelLoading("FALSE");
                    receivingService = receivingServiceFacadeRemote.findByNoPpkbAndContNoCancelLoading(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                    receivingService.setStatusCancelLoading("FALSE");

                    serviceReceivingFacadeRemote.edit(serviceReceiving);
                    receivingServiceFacadeRemote.edit(receivingService);
                }

                planningContLoad.setStatusCancelLoading("FALSE");
                serviceContLoad.setStatusCancelLoading("FALSE");

                String grossClass = GrossConverter.convert(cancelLoadingService.getContSize(), cancelLoadingService.getContGross());

                if (cancelLoadingService.getNewPlanningVessel().getPreserviceVessel().getMasterVessel().isKapalBayangan().equals("FALSE")) {
                    PlanningReceivingAllocation planningReceivingAllocation = planningReceivingAllocationFacadeRemote.findByAllocationConstraint(cancelLoadingService.getNewPlanningVessel().getNoPpkb(), cancelLoadingService.getContSize(), cancelLoadingService.getMasterContainerType().getContType(), cancelLoadingService.getContStatus(), cancelLoadingService.getOverSize(), cancelLoadingService.getDg(), grossClass, cancelLoadingService.getDischargePort().getPortCode(), cancelLoadingService.getIsExport());
                    planningReceivingAllocation.setTotalBooking2(planningReceivingAllocation.getTotalBooking2() - 1);
                    planningReceivingAllocationFacadeRemote.edit(planningReceivingAllocation);
                }

                perhitunganUpahBuruhFacadeRemote.deleteByJobslip(cancelLoadingService.getJobSlip());
                serviceContLoadFacadeRemote.edit(serviceContLoad);
                planningContLoadFacadeRemote.edit(planningContLoad);
            } else if (cancelLoadingService.getCategory() == 5) {
                planningContLoad = planningContLoadFacadeRemote.findByNoPpkbContNoAndStatusCancelLoading(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), true);
                serviceContLoad = serviceContLoadFacadeRemote.findByNoPpkbAndContNo(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());

                planningContLoad.setStatusCancelLoading("FALSE");
                serviceContLoad.setStatusCancelLoading("FALSE");

                if (cancelLoadingService.getIsTranshipment().equals("FALSE")) {
                    ServiceReceiving serviceReceiving = serviceReceivingFacadeRemote.findByNoPpkbAndContNoCancelLoading(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                    serviceReceiving.setStatusCancelLoading("FALSE");
                    receivingService = receivingServiceFacadeRemote.findByNoPpkbAndContNoCancelLoading(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                    receivingService.setStatusCancelLoading("FALSE");

                    serviceReceivingFacadeRemote.edit(serviceReceiving);
                    receivingServiceFacadeRemote.edit(receivingService);
                }

                MasterYardCoordinat targetLocation = masterYardCoordinatFacadeRemote.findAvailableCoordinat(cancelLoadingService.getMasterYard().getBlock(), cancelLoadingService.getySlot(), cancelLoadingService.getyRow(), cancelLoadingService.getyTier());
                MasterYardCoordinat targetLocation40ft = masterYardCoordinatFacadeRemote.findAvailableCoordinat(cancelLoadingService.getMasterYard().getBlock(), (cancelLoadingService.getySlot() + 1), cancelLoadingService.getyRow(), cancelLoadingService.getyTier());
                MasterYardCoordinat targetLocation45ft = masterYardCoordinatFacadeRemote.findAvailableCoordinat(cancelLoadingService.getMasterYard().getBlock(), (cancelLoadingService.getySlot() + 2), cancelLoadingService.getyRow(), cancelLoadingService.getyTier());

                if (targetLocation != null && targetLocation.getContNo() != null && targetLocation.getContNo().equals(cancelLoadingService.getContNo())) {
                    targetLocation.setContNo(null);
                    targetLocation.setMlo(null);
                    targetLocation.setContWeight(null);
                    targetLocation.setPod(null);
                    targetLocation.setNoPpkb(null);
                    targetLocation.setStatus("empty");
                    targetLocation.setContSize(null);
                    targetLocation.setContType(null);
                    targetLocation.setGrossClass(null);
                    masterYardCoordinatFacadeRemote.edit(targetLocation);
                }

                if (cancelLoadingService.getContSize() == 40 && targetLocation40ft != null && targetLocation40ft.getContNo() != null && targetLocation40ft.getContNo().equals(cancelLoadingService.getContNo())) {
                    targetLocation40ft.setContNo(null);
                    targetLocation40ft.setMlo(null);
                    targetLocation40ft.setContWeight(null);
                    targetLocation40ft.setPod(null);
                    targetLocation40ft.setNoPpkb(null);
                    targetLocation40ft.setStatus("empty");
                    targetLocation40ft.setContSize(null);
                    targetLocation40ft.setContType(null);
                    targetLocation40ft.setGrossClass(null);
                    masterYardCoordinatFacadeRemote.edit(targetLocation40ft);
                }

                if (cancelLoadingService.getContSize() == 45 && targetLocation45ft != null && targetLocation45ft.getContNo() != null && targetLocation45ft.getContNo().equals(cancelLoadingService.getContNo())) {
                    targetLocation45ft.setContNo(null);
                    targetLocation45ft.setMlo(null);
                    targetLocation45ft.setContWeight(null);
                    targetLocation45ft.setPod(null);
                    targetLocation45ft.setNoPpkb(null);
                    targetLocation45ft.setStatus("empty");
                    targetLocation45ft.setContSize(null);
                    targetLocation45ft.setContType(null);
                    targetLocation45ft.setGrossClass(null);
                    masterYardCoordinatFacadeRemote.edit(targetLocation45ft);
                }

                perhitunganPassGateFacadeRemote.deleteByJobSlip(cancelLoadingService.getJobSlip());
                perhitunganUpahBuruhFacadeRemote.deleteByJobslip(cancelLoadingService.getJobSlip());
                perhitunganPenumpukanFacadeRemote.deleteByContNoAndNoReg(cancelLoadingService.getContNo(), cancelLoadingService.getRegistration().getNoReg());
                serviceContLoadFacadeRemote.edit(serviceContLoad);
                planningContLoadFacadeRemote.edit(planningContLoad);
            } else {
                throw new RuntimeException("Cancel loading category is not valid");
            }

            cancelLoadingServiceFacadeRemote.remove(cancelLoadingService);

            viewData();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
        }

    }

    public void handleAdd(ActionEvent event) {
        this.clear();
        newPlanningVessel = null;
        cancelLoadingService.setPullOut("FALSE");

        if (!masterYards.isEmpty()) {
            masterYard = masterYardFacadeRemote.find(masterYards.get(0)[0]);
        }
    }

    public void handleSubmit(ActionEvent event) {
        Boolean loggedIn = false;

        try {
            if (invoice.getPaymentType().equals("KREDIT")) {
                registration.setStatusService("approve");
                disKredit = true;
            } else {
                registration.setStatusService("confirm");
                disKredit = false;
            }

            invoice.setNoReg(registration.getNoReg());
            invoice.setRegistration(registration);
            invoice.setJumlahTagihan(total);
            invoice.setPpn(ppn);
            invoice.setMaterai(materaiPrint);
            invoice.setAdditionalCharge(upahBuruh);
            invoice.setTotalTagihan(invoice.getJumlahTagihan().add(invoice.getMaterai()).add(invoice.getPpn()).add(invoice.getAdditionalCharge()));
            invoice.setMasterCurrency(masterCurrency);

            if (noCharge) {
                invoice.setPaymentStatus("PAYMENT");
                invoice.setPaymentDate(new Date());
                invoice.setKasir(userId);
                registration.setStatusService("approve");
            } else {
                invoice.setPaymentStatus("UNPAID");
            }

            registration.setInvoice(invoice);
            registrationFacadeRemote.edit(registration);

            viewData();
            loggedIn = true;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }

        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleSelect(ActionEvent event) {
        String job_slip = (String) event.getComponent().getAttributes().get("jobSlip");
        cancelLoadingService = cancelLoadingServiceFacadeRemote.find(job_slip);
    }

    public void ambilContNo(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String contNo = (String) event.getComponent().getAttributes().get("jobSlip");
        cancelLoadingService = new CancelLoadingService();
        planningContLoad = planningContLoadFacadeRemote.findByCancelableContainer(registration.getPlanningVessel().getNoPpkb(), contNo);
        if (planningContLoad != null) {
            serviceContLoad = serviceContLoadFacadeRemote.findCancelableContainerByContNoAndNoPpkb(registration.getPlanningVessel().getNoPpkb(), contNo);

            if (serviceContLoad != null) {
                if (serviceContLoad.getServiceVessel().getDepartureTime() == null) {
                    MasterPort dischargePort = masterPortFacadeRemote.find(planningContLoad.getDischPort());

                    cancelLoadingService.setMasterCommodity(serviceContLoad.getMasterCommodity());
                    cancelLoadingService.setContGross(serviceContLoad.getContGross());
                    cancelLoadingService.setContNo(serviceContLoad.getContNo());
                    cancelLoadingService.setMlo(serviceContLoad.getMlo());
                    cancelLoadingService.setContSize(serviceContLoad.getContSize());
                    cancelLoadingService.setContStatus(serviceContLoad.getContStatus());
                    cancelLoadingService.setMasterContainerType(serviceContLoad.getMasterContainerType());
                    cancelLoadingService.setDgLabel(serviceContLoad.getDgLabel());
                    cancelLoadingService.setTwentyOneFeet(serviceContLoad.getTwentyOneFeet());
                    cancelLoadingService.setDg(serviceContLoad.getDangerous());
                    cancelLoadingService.setPlanningVessel(serviceContLoad.getServiceVessel().getPlanningVessel());
                    cancelLoadingService.setRegistration(registration);
                    cancelLoadingService.setOverSize(serviceContLoad.getOverSize());
                    cancelLoadingService.setSealNo(serviceContLoad.getSealNo());
                    cancelLoadingService.setCrane(serviceContLoad.getCrane());
                    cancelLoadingService.setBlNo(serviceContLoad.getBlNo());
                    cancelLoadingService.setPosition("VESSEL");
                    cancelLoadingService.setIsDischarge("FALSE");
                    cancelLoadingService.setIsTranshipment(serviceContLoad.getIsTranshipment());
                    cancelLoadingService.setIsExport(serviceContLoad.getIsExportImport());
                    cancelLoadingService.setDischargePort(dischargePort);
                    selectedPort1 = dischargePort.getName();
                } else {
                    FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
                    cancelLoadingService = new CancelLoadingService();
                    return;
                }
            } else {
                MasterPort dischargePort = masterPortFacadeRemote.find(planningContLoad.getDischPort());

                cancelLoadingService.setMasterCommodity(planningContLoad.getMasterCommodity());
                cancelLoadingService.setContGross(planningContLoad.getContGross());
                cancelLoadingService.setContNo(planningContLoad.getContNo());
                cancelLoadingService.setMlo(planningContLoad.getMlo());
                cancelLoadingService.setContSize(planningContLoad.getContSize());
                cancelLoadingService.setContStatus(planningContLoad.getContStatus());
                cancelLoadingService.setMasterContainerType(planningContLoad.getMasterContainerType());
                cancelLoadingService.setDgLabel(planningContLoad.getDgLabel());
                cancelLoadingService.setDg(planningContLoad.getDg());
                cancelLoadingService.setPlanningVessel(planningContLoad.getPlanningVessel());
                cancelLoadingService.setRegistration(registration);
                cancelLoadingService.setOverSize(planningContLoad.getOverSize());
                cancelLoadingService.setSealNo(planningContLoad.getSealNo());
                cancelLoadingService.setPosition("CY");
                cancelLoadingService.setBlNo(planningContLoad.getBlNo());
                cancelLoadingService.setTwentyOneFeet(planningContLoad.getTwentyOneFeet());
                cancelLoadingService.setySlot(planningContLoad.getYSlot());
                cancelLoadingService.setyRow(planningContLoad.getYRow());
                cancelLoadingService.setyTier(planningContLoad.getYTier());
                cancelLoadingService.setMasterYard(planningContLoad.getMasterYard());
                cancelLoadingService.setIsDischarge("TRUE");
                cancelLoadingService.setIsExport(planningContLoad.getIsExportImport());
                cancelLoadingService.setDischargePort(dischargePort);
                cancelLoadingService.setIsTranshipment(planningContLoad.getIsTranshipment());
                selectedPort1 = dischargePort.getName();
            }

            if (planningContLoad.getIsTranshipment().equals("TRUE")) {
                ServiceContTranshipment serviceContTranshipment = serviceContTranshipmentFacadeRemote.findByNewPpkbAndContNo(registration.getPlanningVessel().getNoPpkb(), contNo);
                cancelLoadingService.setMasterContDamage(serviceContTranshipment.getMasterContDamage());
                selectedPort1 = cancelLoadingService.getDischargePort().getName();
            } else {
                receivingService = receivingServiceFacadeRemote.findCancelableContainerByContNoAndNoPpkb(registration.getPlanningVessel().getNoPpkb(), contNo);
                ServiceGateReceiving serviceGateReceiving = serviceGateReceivingFacadeRemote.findByNoPpkbAndContNo(registration.getPlanningVessel().getNoPpkb(), contNo);
                cancelLoadingService.setMasterContDamage(serviceGateReceiving.getMasterContDamage());
                selectedPort1 = cancelLoadingService.getDischargePort().getName();
            }

            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        } else {
            receivingService = receivingServiceFacadeRemote.findCancelableContainerByContNoAndNoPpkb(registration.getPlanningVessel().getNoPpkb(), contNo);

            if (receivingService != null) {
                if (!receivingService.getRegistration().getMasterCustomer().getCustCode().equals(registration.getMasterCustomer().getCustCode())) {
                    FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.validation.customer_not_match");
                    return;
                }

                cancelLoadingService.setMasterCommodity(receivingService.getMasterCommodity());
                cancelLoadingService.setContGross(receivingService.getContGross());
                cancelLoadingService.setContNo(receivingService.getContNo());
                cancelLoadingService.setMlo(receivingService.getMlo());
                cancelLoadingService.setContSize(receivingService.getContSize());
                cancelLoadingService.setContStatus(receivingService.getContStatus());
                cancelLoadingService.setMasterContainerType(receivingService.getMasterContainerType());
                cancelLoadingService.setDgLabel(receivingService.getDgLabel());
                cancelLoadingService.setDg(receivingService.getDg());
                cancelLoadingService.setPlanningVessel(receivingService.getPlanningVessel());
                cancelLoadingService.setTwentyOneFeet(receivingService.getTwentyOneFeet());
                cancelLoadingService.setRegistration(registration);
                cancelLoadingService.setOverSize(receivingService.getOverSize());
                cancelLoadingService.setSealNo(receivingService.getSealNo());
                cancelLoadingService.setBlNo(receivingService.getBlNo());
                cancelLoadingService.setPosition("OUTSIDE");
                cancelLoadingService.setIsDischarge("FALSE");
                cancelLoadingService.setPullOut("FALSE");
                cancelLoadingService.setIsExport(receivingService.getIsExport());
                cancelLoadingService.setDischargePort(receivingService.getMasterPort());

                selectedPort1 = receivingService.getMasterPort().getName();

                isNotPullOutAble = true;
                FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
            } else {
                FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
                cancelLoadingService = new CancelLoadingService();
            }
        }
    }

    public void clear() {
        cancelLoadingService = new CancelLoadingService();
        receivingService = new ReceivingService();
        planningContLoad = new PlanningContLoad();
        isNotPullOutAble = false;
    }

    public void handleConfirm(ActionEvent actionEvent) {
        Boolean isValid = false;

        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            BigDecimal totalCancelLoadingCharge = BigDecimal.ZERO;
            String jobSlip = iDGeneratorFacade.generateJobSlipID("05");
            int masa1Range = masterSettingAppFacade.getMasa1ContainerRange();
            int masaFreeRange = 1;
            int masaFreeTranshipmentRange = masterSettingAppFacade.getMasa1FreeRange();
            String grossClass = GrossConverter.convert(cancelLoadingService.getContSize(), cancelLoadingService.getContGross());
            Date now = new Date();

            if (noCharge) {
                cancelLoadingService.setPullOut("FALSE");
            }

            if (cancelLoadingService.getPosition().equals("OUTSIDE")) {
                String cancelDocumentActivityCode = masterActivityFacadeRemote.translateCancelDocumentActivityCode();
                MasterActivity cancelDocumentActivity = masterActivityFacadeRemote.find(cancelDocumentActivityCode);
                category = 1;

                cancelLoadingService.setMasterActivity(cancelDocumentActivity);
                cancelLoadingService.setStatus("Container belum ada di CY");
                cancelLoadingService.setPosisi(CancelLoadingService.AT_OUTSIDE);
                receivingService.setStatusCancelLoading("TRUE");

                BigDecimal[] discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), cancelDocumentActivityCode);
                BigDecimal discountAsPercentage = discount[0];
                BigDecimal discountAsMoney = discount[1];
                BigDecimal chargeCancelDocument = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), cancelDocumentActivityCode);

                if (discountAsMoney == null) {
                    discountAsMoney = chargeCancelDocument.multiply(discountAsPercentage);
                }

                totalCancelLoadingCharge = chargeCancelDocument.subtract(discountAsMoney);

                receivingServiceFacadeRemote.edit(receivingService);
            } else if (cancelLoadingService.getPosition().equals("CY") && cancelLoadingService.getPullOut().equals(false)) {
                category = 2;
                cancelLoadingService.setStatus("Container ada di CY");
                cancelLoadingService.setPosisi(CancelLoadingService.AT_CY);

                receivingStatus = receivingServiceFacadeRemote.findReceivingCapacityStatusByNoPpkb(newPlanningVessel.getNoPpkb());
                Long currentGrt = (Long) receivingStatus[0];
                Long currentContCapacity = (Long) receivingStatus[1];

                if (currentGrt.doubleValue() + (cancelLoadingService.getContGross() / 1000) > newPlanningVessel.getPreserviceVessel().getMasterVessel().getGrt()) {
                    FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.exceed_limit", facesContext), "Container Gross", "(max " + newPlanningVessel.getPreserviceVessel().getMasterVessel().getGrt() + " ton)"), null);
                    return;
                }

                if (currentContCapacity.doubleValue() + cancelLoadingService.getContTeus() > newPlanningVessel.getPreserviceVessel().getMasterVessel().getContCapacity()) {
                    FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.exceed_limit", facesContext), "Container Capacity", "(max " + newPlanningVessel.getPreserviceVessel().getMasterVessel().getContCapacity() + " teus)"), null);
                    return;
                }

                MasterPort dischargePort = masterPortFacadeRemote.findMasterPortByName(selectedPort1);
                cancelLoadingService.setDischargePort(dischargePort);
                PlanningReceivingAllocation planningReceivingAllocation = planningReceivingAllocationFacadeRemote.findByAllocationConstraint(newPlanningVessel.getNoPpkb(), cancelLoadingService.getContSize(), cancelLoadingService.getMasterContainerType().getContType(), cancelLoadingService.getContStatus(), cancelLoadingService.getOverSize(), cancelLoadingService.getDg(), grossClass, cancelLoadingService.getDischargePort().getPortCode(), cancelLoadingService.getIsExport());

                if (planningReceivingAllocation == null) {
                    FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Info", "application.validation.record_beda");
                    return;
                }

                if (planningReceivingAllocation.getTotalBooking2() >= planningReceivingAllocation.getAllocated() 
                        && newPlanningVessel.getPreserviceVessel().getMasterVessel().isKapalBayangan().equals("FALSE")) {
                    FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.alokasi_booking");
                    return;
                }

                receivingStatus[0] = currentGrt + (cancelLoadingService.getContGross() / 1000);
                receivingStatus[1] = currentContCapacity + cancelLoadingService.getContTeus();

                if (cancelLoadingService.getIsTranshipment().equals("TRUE")) {
                    ServiceContTranshipment serviceContTranshipment = serviceContTranshipmentFacadeRemote.findByNewPpkbAndContNo(registration.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                    serviceContTranshipment.setNewPpkb(newPlanningVessel.getNoPpkb());
                    serviceContTranshipmentFacadeRemote.edit(serviceContTranshipment);
                } else {
                    ServiceReceiving serviceReceiving = serviceReceivingFacadeRemote.findCancelableContainerByContNoAndNoPpkb(registration.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                    serviceReceiving.setStatusCancelLoading("TRUE");
                    receivingService.setStatusCancelLoading("TRUE");

                    serviceReceivingFacadeRemote.edit(serviceReceiving);
                    receivingServiceFacadeRemote.edit(receivingService);
                }

                planningContLoad.setStatusCancelLoading("TRUE");

                String cancelLoadingActivityCode = masterActivityFacadeRemote.translateCancelLoadingActivityCode(cancelLoadingService.getContStatus(), cancelLoadingService.getContSize());
                MasterActivity cancelLoadingActivity = masterActivityFacadeRemote.find(cancelLoadingActivityCode);

                BigDecimal[] discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), cancelLoadingActivityCode);
                BigDecimal discountCancelLoadingAsPercentage = discount[0];
                BigDecimal discountCancelLoadingAsMoney = discount[1];
                BigDecimal chargeCancelLoading = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), cancelLoadingActivityCode);

                if (discountCancelLoadingAsMoney == null) {
                    discountCancelLoadingAsMoney = chargeCancelLoading.multiply(discountCancelLoadingAsPercentage);
                }

                if (noCharge) {
                    totalCancelLoadingCharge = BigDecimal.ZERO;
                } else {
                    totalCancelLoadingCharge = chargeCancelLoading.subtract(discountCancelLoadingAsMoney);
                }

                if (newPlanningVessel.getPreserviceVessel().getMasterVessel().isKapalBayangan().equals("FALSE")) {
                    planningReceivingAllocation.setTotalBooking2(planningReceivingAllocation.getTotalBooking2() + 1);
                }
                
                cancelLoadingService.setNewPlanningVessel(newPlanningVessel);
                cancelLoadingService.setMasterActivity(cancelLoadingActivity);

                planningReceivingAllocationFacadeRemote.edit(planningReceivingAllocation);
                planningContLoadFacadeRemote.edit(planningContLoad);
                masterYardCoordinatFacadeRemote.changeNoPpkbByContNo(cancelLoadingService.getNewPlanningVessel().getNoPpkb(), cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
            } else if (cancelLoadingService.getPosition().equals("CY") && cancelLoadingService.getPullOut().equals(true)) {
                BigDecimal handlingCharge = BigDecimal.ZERO;
                int masa1 = 0;
                int masa2 = 0;

                category = 3;
                planningContLoad.setStatusCancelLoading("TRUE");
                MasterPort mp1 = masterPortFacadeRemote.findMasterPortByName(selectedPort1);
                cancelLoadingService.setDischargePort(mp1);
                cancelLoadingService.setStatus("Container ada di CY dan di tarik keluar");
                cancelLoadingService.setPosisi(CancelLoadingService.AT_CY);

                if (cancelLoadingService.getIsTranshipment().equals("TRUE")) {
                    ServiceContTranshipment serviceContTranshipment = serviceContTranshipmentFacadeRemote.findByNewPpkbAndContNo(registration.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                    cancelLoadingService.setPlacementDate(serviceContTranshipment.getStartPlacementDate());

                    int min = DateCalculator.countRangeInDays(now, serviceContTranshipment.getStartPlacementDate()) + 1;
                    int masaFree = 0;

                    if (min < (masa1Range + 1)) {
                        if (min <= masaFreeTranshipmentRange) {
                            masa1 = masaFree;
                        } else {
                            masa1 = min - masaFreeTranshipmentRange + masaFree;
                        }
                    } else {
                        masa1 = masa1Range - masaFreeTranshipmentRange + masaFree;
                        masa2 = min - masa1Range;
                    }
                    
                    boolean bIsSling = false;
                    if (serviceContTranshipment.getIsSling().equals("TRUE")) {
                        bIsSling = true;
                    }
                    String transhipmentActivityCode = masterActivityFacadeRemote.translateTranshipmentActivityCode(
                            bIsSling, 
                            serviceContTranshipment.getContStatus(), 
                            serviceContTranshipment.getCrane(), 
                            serviceContTranshipment.getContSize());
                    BigDecimal transhipmentCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), transhipmentActivityCode);

                    String dangerousClass = null;
                    if(cancelLoadingService.getMasterCommodity() != null && cancelLoadingService.getMasterCommodity().getMasterDangerousClass() != null)
                        dangerousClass = cancelLoadingService.getMasterCommodity().getMasterDangerousClass().getDangerousClass();

                                    
                    String handlingActivityCode = masterActivityFacadeRemote.translateStevedoringActivityCode(
                            cancelLoadingService.getContStatus(), 
                            cancelLoadingService.getContSize(), 
                            serviceContTranshipment.getCrane(), 
                            bIsSling,
                            null,
                            cancelLoadingService.getDg().equals("TRUE"),
                            cancelLoadingService.getDgLabel().equals("TRUE"),
                            dangerousClass,
                            bIsSling,
                            false);
                    
                    handlingCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), handlingActivityCode);

                    if (serviceContTranshipment.getIsSling().equals("FALSE") && cancelLoadingService.getOverSize().equals("TRUE")) {
                        transhipmentCharge = transhipmentCharge.multiply(BigDecimal.valueOf(1.5));
                        handlingCharge = handlingCharge.multiply(BigDecimal.valueOf(1.5));
                    } else if (serviceContTranshipment.getIsSling().equals("FALSE")
                                && cancelLoadingService.getOverSize().equals("FALSE")
                                && cancelLoadingService.getTwentyOneFeet().equals("TRUE")) {
                        transhipmentCharge = transhipmentCharge.multiply(BigDecimal.valueOf(1.2));
                        handlingCharge = handlingCharge.multiply(BigDecimal.valueOf(1.2));
                    }

                    BigDecimal[] discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), handlingActivityCode);
                    BigDecimal discountHandlingAsPercentage = discount[0];
                    BigDecimal discountHandlingAsMoney = discount[1];

                    if (discountHandlingAsMoney == null) {
                        discountHandlingAsMoney = handlingCharge.multiply(discountHandlingAsPercentage);
                    }

                    handlingCharge = handlingCharge.subtract(discountHandlingAsMoney);

                    if (cancelLoadingService.getDg().equals("TRUE") && cancelLoadingService.getDgLabel().equals("TRUE")) {
                        handlingCharge = handlingCharge.multiply(BigDecimal.valueOf(2));
                    } else if (cancelLoadingService.getDg().equals("TRUE") && cancelLoadingService.getDgLabel().equals("FALSE")) {
                        handlingCharge = handlingCharge.multiply(BigDecimal.valueOf(3));
                    }

                    handlingCharge = handlingCharge.subtract(transhipmentCharge);
                } else {
                    ServiceReceiving serviceReceiving = serviceReceivingFacadeRemote.findCancelableContainerByContNoAndNoPpkb(registration.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                    serviceReceiving.setStatusCancelLoading("TRUE");
                    receivingService.setStatusCancelLoading("TRUE");
                    cancelLoadingService.setPlacementDate(serviceReceiving.getTransactionDate());

                    int min = DateCalculator.countRangeInDays(now, serviceReceiving.getTransactionDate()) + 1;
                    int masaFree = 1;

                    if (min < (masa1Range + 1)) {
                        if (min <= masaFreeRange) {
                            masa1 = masaFree;
                        } else {
                            masa1 = min - masaFreeRange + masaFree;
                        }
                    } else {
                        masa1 = masa1Range - masaFreeRange + masaFree;
                        masa2 = min - masa1Range;
                    }

                    serviceReceivingFacadeRemote.edit(serviceReceiving);
                    receivingServiceFacadeRemote.edit(receivingService);
                }

                Integer idReefer = serviceReeferFacadeRemote.findValidasiPenumpukan(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), "DISCHARGE");
                boolean bIsOverSize = false;
                if((!cancelLoadingService.getContStatus().equals("MTY") 
                        && cancelLoadingService.getOverSize().equals("TRUE")) 
                        || cancelLoadingService.getTwentyOneFeet().equals("TRUE")) {
                    bIsOverSize = true;
                }
                String dangerousClass = null;
                    if(cancelLoadingService.getMasterCommodity() != null && cancelLoadingService.getMasterCommodity().getMasterDangerousClass() != null)
                        dangerousClass = cancelLoadingService.getMasterCommodity().getMasterDangerousClass().getDangerousClass();

                    
                String penumpukanActivityCode = masterActivityFacadeRemote.translatePenumpukanActivityCode(
                        cancelLoadingService.getContStatus(), 
                        cancelLoadingService.getMasterContainerType().getIsoCode(), 
                        bIsOverSize,
                        cancelLoadingService.getContSize(), idReefer > 0);
                String passGateActivityCode = masterActivityFacadeRemote.translatePassGateActivityCode(cancelLoadingService.getContStatus(), cancelLoadingService.getContSize());
                String loloActivityCode = masterActivityFacadeRemote.translateLoLoActivityCode(
                        cancelLoadingService.getContStatus(), 
                        cancelLoadingService.getContSize(), 
                        cancelLoadingService.getOverSize().equals("TRUE") ? true : false, 
                        cancelLoadingService.getDg().equals("TRUE"),
                        cancelLoadingService.getDgLabel().equals("TRUE"),
                        dangerousClass,serviceContLoad.getEquipmentGroup());
                String cancelLoadingActivityCode = masterActivityFacadeRemote.translateCancelLoadingActivityCode(cancelLoadingService.getContStatus(), cancelLoadingService.getContSize());

                MasterActivity cancelLoadingActivity = masterActivityFacadeRemote.find(cancelLoadingActivityCode);
                MasterActivity passGateMasterActivity = masterActivityFacadeRemote.find(passGateActivityCode);

                cancelLoadingService.setMasterActivity(cancelLoadingActivity);

                BigDecimal passGateCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), passGateActivityCode);
                BigDecimal cancelLoadingCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), cancelLoadingActivityCode);
                BigDecimal loloCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), loloActivityCode);
                BigDecimal penumpukanCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), penumpukanActivityCode);

                PerhitunganPenumpukan perhitunganPenumpukan = new PerhitunganPenumpukan();
                perhitunganPenumpukan.setRegistration(registration);
                perhitunganPenumpukan.setMasa1((short) masa1);
                perhitunganPenumpukan.setMasa2((short) masa2);
                perhitunganPenumpukan.setNoPpkb(cancelLoadingService.getPlanningVessel().getNoPpkb());
                perhitunganPenumpukan.setContNo(cancelLoadingService.getContNo());
                perhitunganPenumpukan.setMlo(cancelLoadingService.getMlo());
                perhitunganPenumpukan.setBlNo(cancelLoadingService.getBlNo());
                perhitunganPenumpukan.setMasterActivity(masterActivityFacadeRemote.find(penumpukanActivityCode));

                PerhitunganPassGate perhitunganPassGate = new PerhitunganPassGate();
                perhitunganPassGate.setJobSlip(jobSlip);
                perhitunganPassGate.setMasterActivity(passGateMasterActivity);
                perhitunganPassGate.setPlanningVessel(registration.getPlanningVessel());
                perhitunganPassGate.setRegistration(registration);

                BigDecimal[] discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), cancelLoadingActivityCode);
                BigDecimal discountCancelLoadingAsPercentage = discount[0];
                BigDecimal discountCancelLoadingAsMoney = discount[1];

                discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), penumpukanActivityCode);
                BigDecimal discountPenumpukanAsPercentage = discount[0];
                BigDecimal discountPenumpukanAsMoney = discount[1];
                BigDecimal totalPenumpukanCharge = BigDecimal.ZERO;

                discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), passGateActivityCode);
                BigDecimal discountPassGateAsPercentage = discount[0];
                BigDecimal discountPassGateAsMoney = discount[1];

                discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), loloActivityCode);
                BigDecimal discountLoloAsPercentage = discount[0];
                BigDecimal discountLoloAsMoney = discount[1];

                if (discountCancelLoadingAsMoney == null) {
                    discountCancelLoadingAsMoney = cancelLoadingCharge.multiply(discountCancelLoadingAsPercentage);
                }

                if (discountPenumpukanAsMoney == null) {
                    discountPenumpukanAsMoney = penumpukanCharge.multiply(discountPenumpukanAsPercentage);
                }

                if (discountPassGateAsMoney == null) {
                    discountPassGateAsMoney = passGateCharge.multiply(discountPassGateAsPercentage);
                }

                if (discountLoloAsMoney == null) {
                    discountLoloAsMoney = loloCharge.multiply(discountLoloAsPercentage);
                }

                loloCharge = loloCharge.subtract(discountLoloAsMoney);
                penumpukanCharge = penumpukanCharge.subtract(discountPenumpukanAsMoney);

                if (cancelLoadingService.getDg().equals("TRUE") && cancelLoadingService.getDgLabel().equals("TRUE")) {
                    penumpukanCharge = penumpukanCharge.multiply(BigDecimal.valueOf(2));
                    loloCharge = loloCharge.multiply(BigDecimal.valueOf(2));
                } else if (cancelLoadingService.getDg().equals("TRUE") && cancelLoadingService.getDgLabel().equals("FALSE")) {
                    penumpukanCharge = penumpukanCharge.multiply(BigDecimal.valueOf(3));
                    loloCharge = loloCharge.multiply(BigDecimal.valueOf(3));
                }

                perhitunganPenumpukan.setCurrCode(masterCurrency.getCurrCode());
                perhitunganPenumpukan.setCharge(penumpukanCharge);
                perhitunganPenumpukan.setChargeM1(perhitunganPenumpukan.getCharge().multiply(BigDecimal.valueOf(perhitunganPenumpukan.getMasa1())));
                perhitunganPenumpukan.setChargeM2(perhitunganPenumpukan.getCharge().multiply(BigDecimal.valueOf(perhitunganPenumpukan.getMasa2())).multiply(BigDecimal.valueOf(2)));
                perhitunganPenumpukan.setJasaDermaga(BigDecimal.ZERO);
                totalPenumpukanCharge = perhitunganPenumpukan.getChargeM1().add(perhitunganPenumpukan.getChargeM2()).add(perhitunganPenumpukan.getJasaDermaga());
                perhitunganPenumpukan.setTotalCharge(totalPenumpukanCharge);

                passGateCharge = passGateCharge.subtract(discountPassGateAsMoney);
                perhitunganPassGate.setCharge(passGateCharge);
                perhitunganPassGate.setJumlah(1);
                perhitunganPassGate.setTotalCharge(perhitunganPassGate.getCharge().multiply(new BigDecimal(perhitunganPassGate.getJumlah())));

                if (noRelocation) {
                    totalCancelLoadingCharge = loloCharge.add(handlingCharge);
                } else {
                    totalCancelLoadingCharge = cancelLoadingCharge.subtract(discountCancelLoadingAsMoney).add(loloCharge).add(handlingCharge);
                }
                noRelocation = false;

                planningContLoadFacadeRemote.edit(planningContLoad);

                perhitunganPassGateFacadeRemote.create(perhitunganPassGate);

                if (perhitunganPenumpukan.getTotalCharge().compareTo(BigDecimal.ZERO) > 0 
                        && cancelLoadingService.getNoStacking().equals("FALSE")) {
                    perhitunganPenumpukanFacadeRemote.create(perhitunganPenumpukan);
                }
            } else if (cancelLoadingService.getPosition().equals("VESSEL") && cancelLoadingService.getPullOut().equals(false)) {
                receivingStatus = receivingServiceFacadeRemote.findReceivingCapacityStatusByNoPpkb(newPlanningVessel.getNoPpkb());
                category = 4;
                cancelLoadingService.setStatus("Container sudah di kapal dan kembali ke CY");
                cancelLoadingService.setPosisi(CancelLoadingService.AT_VESSEL);

                Long currentGrt = (Long) receivingStatus[0];
                Long currentContCapacity = (Long) receivingStatus[1];

                if (currentGrt.doubleValue() + (cancelLoadingService.getContGross() / 1000) > newPlanningVessel.getPreserviceVessel().getMasterVessel().getGrt()) {
                    FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.exceed_limit", facesContext), "Container Gross", "(max " + newPlanningVessel.getPreserviceVessel().getMasterVessel().getGrt() + " ton)"), null);
                    return;
                }

                if (currentContCapacity.doubleValue() + cancelLoadingService.getContTeus() > newPlanningVessel.getPreserviceVessel().getMasterVessel().getContCapacity()) {
                    FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.exceed_limit", facesContext), "Container Capacity", "(max " + newPlanningVessel.getPreserviceVessel().getMasterVessel().getContCapacity() + " teus)"), null);
                    return;
                }

                MasterPort mp1 = masterPortFacadeRemote.findMasterPortByName(selectedPort1);
                cancelLoadingService.setDischargePort(mp1);
                PlanningReceivingAllocation planningReceivingAllocation = planningReceivingAllocationFacadeRemote.findByAllocationConstraint(newPlanningVessel.getNoPpkb(), cancelLoadingService.getContSize(), cancelLoadingService.getMasterContainerType().getContType(), cancelLoadingService.getContStatus(), cancelLoadingService.getOverSize(), cancelLoadingService.getDg(), grossClass, cancelLoadingService.getDischargePort().getPortCode(), cancelLoadingService.getIsExport());

                if (planningReceivingAllocation == null) {
                    FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Info", "application.validation.record_beda");
                    return;
                }

                if (planningReceivingAllocation.getTotalBooking2() >= planningReceivingAllocation.getAllocated() 
                        && newPlanningVessel.getPreserviceVessel().getMasterVessel().isKapalBayangan().equals("FALSE")) {
                    FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.alokasi_booking");
                    return;
                }

                receivingStatus[0] = currentGrt + (cancelLoadingService.getContGross() / 1000);
                receivingStatus[1] = currentContCapacity + cancelLoadingService.getContTeus();

                if (cancelLoadingService.getIsTranshipment().equals("TRUE")) {
                    ServiceContTranshipment serviceContTranshipment = serviceContTranshipmentFacadeRemote.findByNewPpkbAndContNo(registration.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                    serviceContTranshipment.setNewPpkb(newPlanningVessel.getNoPpkb());
                    serviceContTranshipmentFacadeRemote.edit(serviceContTranshipment);
                } else {
                    ServiceReceiving serviceReceiving = serviceReceivingFacadeRemote.findCancelableContainerByContNoAndNoPpkb(registration.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                    serviceReceiving.setStatusCancelLoading("TRUE");
                    receivingService.setStatusCancelLoading("TRUE");
                    serviceReceivingFacadeRemote.edit(serviceReceiving);
                    receivingServiceFacadeRemote.edit(receivingService);
                }

                serviceContLoad.setStatusCancelLoading("TRUE");
                planningContLoad.setStatusCancelLoading("TRUE");
                
                String dangerousClass = null;
                if(receivingService.getMasterCommodity() != null && receivingService.getMasterCommodity().getMasterDangerousClass() != null)
                    dangerousClass = receivingService.getMasterCommodity().getMasterDangerousClass().getDangerousClass();

                                    
                String cancelLoadingActivityCode = masterActivityFacadeRemote.translateCancelLoadingActivityCode(cancelLoadingService.getContStatus(), cancelLoadingService.getContSize());
                String handlingActivityCode = masterActivityFacadeRemote.translateStevedoringActivityCode(
                        cancelLoadingService.getContStatus(), 
                        cancelLoadingService.getContSize(), 
                        cancelLoadingService.getCrane(), 
                        cancelLoadingService.getOverSize().equals("TRUE") ? true : false,
                        null, 
                        cancelLoadingService.getDg().equals("TRUE"),
                        cancelLoadingService.getDgLabel().equals("TRUE"),
                        dangerousClass,
                        cancelLoadingService.getOverSize().equals("TRUE"),
                        receivingService.getTwentyOneFeet().equals("TRUE"));
                
                String upahBuruhActivityCode = masterActivityFacadeRemote.translateUbahBuruhHandlingActivityCode(
                        cancelLoadingService.getContStatus(), 
                        cancelLoadingService.getContSize(), 
                        cancelLoadingService.getOverSize().equals("TRUE") ? true : false);

                MasterActivity upahBuruhMasterActivity = masterActivityFacadeRemote.find(upahBuruhActivityCode);
                MasterActivity cancelLoadingActivity = masterActivityFacadeRemote.find(cancelLoadingActivityCode);

                cancelLoadingService.setMasterActivity(cancelLoadingActivity);

                BigDecimal handlingCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), handlingActivityCode);
                BigDecimal chargeCancelLoading = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), cancelLoadingActivityCode);
                BigDecimal upahBuruhCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), upahBuruhActivityCode);

                PerhitunganUpahBuruh perhitunganUbahBuruh = new PerhitunganUpahBuruh();
                perhitunganUbahBuruh.setRegistration(registration);
                perhitunganUbahBuruh.setJobslip(jobSlip);
                perhitunganUbahBuruh.setMasterActivity(upahBuruhMasterActivity);
                perhitunganUbahBuruh.setMasterCurrency(masterCurrency);
                perhitunganUbahBuruh.setCharge(upahBuruhCharge.multiply(new BigDecimal(2)));

                BigDecimal[] discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), cancelLoadingActivityCode);
                BigDecimal discountCancelLoadingAsPercentage = discount[0];
                BigDecimal discountCancelLoadingAsMoney = discount[1];

                discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), handlingActivityCode);
                BigDecimal discountHandlingAsPercentage = discount[0];
                BigDecimal discountHandlingAsMoney = discount[1];

                if (discountCancelLoadingAsMoney == null) {
                    discountCancelLoadingAsMoney = chargeCancelLoading.multiply(discountCancelLoadingAsPercentage);
                }

                if (discountHandlingAsMoney == null) {
                    discountHandlingAsMoney = handlingCharge.multiply(discountHandlingAsPercentage);
                }

                cancelLoadingService.setNewPlanningVessel(newPlanningVessel);

                if (newPlanningVessel.getPreserviceVessel().getMasterVessel().isKapalBayangan().equals("FALSE")) {
                    planningReceivingAllocation.setTotalBooking2(planningReceivingAllocation.getTotalBooking2() + 1);
                    planningReceivingAllocationFacadeRemote.edit(planningReceivingAllocation);
                }

                handlingCharge = handlingCharge.subtract(discountHandlingAsMoney);

                totalCancelLoadingCharge = chargeCancelLoading.subtract(discountCancelLoadingAsMoney).add(handlingCharge.multiply(new BigDecimal(2)));

                serviceContLoadFacadeRemote.edit(serviceContLoad);
                planningContLoadFacadeRemote.edit(planningContLoad);

                perhitunganUpahBuruhFacadeRemote.create(perhitunganUbahBuruh);
                masterYardCoordinatFacadeRemote.changeNoPpkbByContNo(cancelLoadingService.getNewPlanningVessel().getNoPpkb(), cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
            } else if (cancelLoadingService.getPosition().equals("VESSEL") && cancelLoadingService.getPullOut().equals(true)) {
                BigDecimal additionalHandlingCharge = BigDecimal.ZERO;
                int masa1 = 0;
                int masa2 = 0;
                category = 5;

                cancelLoadingService.setStatus("Container sudah ada di kapal,kembali ke CY dan ditarik keluar");
                cancelLoadingService.setPosisi(CancelLoadingService.AT_VESSEL);
                MasterPort dischargePort = masterPortFacadeRemote.findMasterPortByName(selectedPort1);
                cancelLoadingService.setDischargePort(dischargePort);

                MasterYardCoordinat targetLocation = masterYardCoordinatFacadeRemote.findAvailableCoordinat(cancelLoadingService.getMasterYard().getBlock(), cancelLoadingService.getySlot(), cancelLoadingService.getyRow(), cancelLoadingService.getyTier());
                MasterYardCoordinat targetLocation40ft = masterYardCoordinatFacadeRemote.findAvailableCoordinat(cancelLoadingService.getMasterYard().getBlock(), (cancelLoadingService.getySlot() + 1), cancelLoadingService.getyRow(), cancelLoadingService.getyTier());
                MasterYardCoordinat targetLocation45ft = masterYardCoordinatFacadeRemote.findAvailableCoordinat(cancelLoadingService.getMasterYard().getBlock(), (cancelLoadingService.getySlot() + 2), cancelLoadingService.getyRow(), cancelLoadingService.getyTier());

                if (!((cancelLoadingService.getContSize() == 20 && targetLocation != null) ||
                        (cancelLoadingService.getContSize() == 40 && targetLocation != null && targetLocation40ft != null) ||
                        (cancelLoadingService.getContSize() == 45 && targetLocation != null && targetLocation40ft != null && targetLocation45ft != null))) {
                    FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.yard_allocation_not_available");
                    return;
                }

                if (cancelLoadingService.getIsTranshipment().equals("TRUE")) {
                    ServiceContTranshipment serviceContTranshipment = serviceContTranshipmentFacadeRemote.findByNewPpkbAndContNo(registration.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                    cancelLoadingService.setPlacementDate(serviceContTranshipment.getStartPlacementDate());

                    int min = DateCalculator.countRangeInDays(now, serviceContTranshipment.getStartPlacementDate()) + 1;
                    int masaFree = 0;

                    if (min < (masa1Range + 1)) {
                        if (min <= masaFreeTranshipmentRange) {
                            masa1 = masaFree;
                        } else {
                            masa1 = min - masaFreeTranshipmentRange + masaFree;
                        }
                    } else {
                        masa1 = masa1Range - masaFreeTranshipmentRange + masaFree;
                        masa2 = min - masa1Range;
                    }

                    String transhipmentActivityCode = masterActivityFacadeRemote.translateTranshipmentActivityCode(
                            serviceContTranshipment.getIsSling().equals("TRUE") ? true : false, 
                            serviceContTranshipment.getContStatus(), 
                            serviceContTranshipment.getCrane(), 
                            serviceContTranshipment.getContSize());
                    BigDecimal transhipmentCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), transhipmentActivityCode);

                    String dangerousClass = null;
                    if(receivingService.getMasterCommodity() != null && receivingService.getMasterCommodity().getMasterDangerousClass() != null)
                        dangerousClass = receivingService.getMasterCommodity().getMasterDangerousClass().getDangerousClass();

                    
                    String handlingActivityCode = masterActivityFacadeRemote.translateStevedoringActivityCode(
                            cancelLoadingService.getContStatus(), 
                            cancelLoadingService.getContSize(), 
                            serviceContTranshipment.getCrane(), 
                            serviceContTranshipment.getIsSling().equals("TRUE") ? true : false,
                            null, 
                            cancelLoadingService.getDg().equals("TRUE"),
                            cancelLoadingService.getDgLabel().equals("TRUE"),
                            dangerousClass,
                            serviceContTranshipment.getIsSling().equals("TRUE"),
                            receivingService.getTwentyOneFeet().equals("TRUE"));
                    
                    additionalHandlingCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), handlingActivityCode);

                    BigDecimal[] discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), handlingActivityCode);
                    BigDecimal discountHandlingAsPercentage = discount[0];
                    BigDecimal discountHandlingAsMoney = discount[1];

                    if (discountHandlingAsMoney == null) {
                        discountHandlingAsMoney = additionalHandlingCharge.multiply(discountHandlingAsPercentage);
                    }

                    additionalHandlingCharge = additionalHandlingCharge.subtract(discountHandlingAsMoney);

                    additionalHandlingCharge = additionalHandlingCharge.subtract(transhipmentCharge);
                } else {
                    ServiceReceiving serviceReceiving = serviceReceivingFacadeRemote.findCancelableContainerByContNoAndNoPpkb(registration.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                    serviceReceiving.setStatusCancelLoading("TRUE");
                    receivingService.setStatusCancelLoading("TRUE");
                    cancelLoadingService.setPlacementDate(serviceReceiving.getTransactionDate());

                    int min = DateCalculator.countRangeInDays(now, serviceReceiving.getTransactionDate()) + 1;
                    int masaFree = 1;

                    if (min < (masa1Range + 1)) {
                        if (min <= masaFreeRange) {
                            masa1 = masaFree;
                        } else {
                            masa1 = min - masaFreeRange + masaFree;
                        }
                    } else {
                        masa1 = masa1Range - masaFreeRange + masaFree;
                        masa2 = min - masa1Range;
                    }

                    serviceReceivingFacadeRemote.edit(serviceReceiving);
                    receivingServiceFacadeRemote.edit(receivingService);
                }

                serviceContLoad.setStatusCancelLoading("TRUE");
                planningContLoad.setStatusCancelLoading("TRUE");

                Integer idReefer = serviceReeferFacadeRemote.findValidasiPenumpukan(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), "DISCHARGE");
                boolean bIsOverSize = false;
                if ((!cancelLoadingService.getContStatus().equals("MTY") && cancelLoadingService.getOverSize().equals("TRUE")) 
                        || cancelLoadingService.getTwentyOneFeet().equals("TRUE")) {
                    bIsOverSize = true;
                }
                String dangerousClass = null;
                if(receivingService.getMasterCommodity() != null && receivingService.getMasterCommodity().getMasterDangerousClass() != null)
                    dangerousClass = receivingService.getMasterCommodity().getMasterDangerousClass().getDangerousClass();

                String penumpukanActivityCode = masterActivityFacadeRemote.translatePenumpukanActivityCode(
                        cancelLoadingService.getContStatus(), 
                        cancelLoadingService.getMasterContainerType().getIsoCode(), 
                        bIsOverSize, 
                        cancelLoadingService.getContSize(), idReefer > 0);
                String passGateActivityCode = masterActivityFacadeRemote.translatePassGateActivityCode(
                        cancelLoadingService.getContStatus(), 
                        cancelLoadingService.getContSize());
                
                String loloActivityCode = masterActivityFacadeRemote.translateLoLoActivityCode(
                        cancelLoadingService.getContStatus(), 
                        cancelLoadingService.getContSize(), 
                        cancelLoadingService.getOverSize().equals("TRUE")? true : false, 
                        cancelLoadingService.getDg().equals("TRUE"),
                        cancelLoadingService.getDgLabel().equals("TRUE"),
                        dangerousClass,serviceContLoad.getEquipmentGroup());
                
                String handlingActivityCode = masterActivityFacadeRemote.translateStevedoringActivityCode(
                        cancelLoadingService.getContStatus(), 
                        cancelLoadingService.getContSize(), 
                        cancelLoadingService.getCrane(), 
                        serviceContLoad.getIsSeling().equals("TRUE") ? true : false,
                        null, 
                        cancelLoadingService.getDg().equals("TRUE"),
                        cancelLoadingService.getDgLabel().equals("TRUE"),
                        dangerousClass,
                        bIsOverSize,
                        cancelLoadingService.getTwentyOneFeet().equals("TRUE"));
                
                String upahBuruhActivityCode = masterActivityFacadeRemote.translateUbahBuruhHandlingActivityCode(
                        cancelLoadingService.getContStatus(), 
                        cancelLoadingService.getContSize(), 
                        cancelLoadingService.getOverSize().equals("TRUE") ? true : false);
                String cancelLoadingActivityCode = masterActivityFacadeRemote.translateCancelLoadingActivityCode(cancelLoadingService.getContStatus(), cancelLoadingService.getContSize());

                MasterActivity cancelLoadingActivity = masterActivityFacadeRemote.find(cancelLoadingActivityCode);
                MasterActivity passGateMasterActivity = masterActivityFacadeRemote.find(passGateActivityCode);
                MasterActivity upahBuruhMasterActivity = masterActivityFacadeRemote.find(upahBuruhActivityCode);

                cancelLoadingService.setMasterActivity(cancelLoadingActivity);

                BigDecimal passGateCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), passGateActivityCode);
                BigDecimal chargeCancelLoading = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), cancelLoadingActivityCode);
                BigDecimal penumpukanCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), penumpukanActivityCode);
                BigDecimal loloCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), loloActivityCode);
                BigDecimal handlingCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), handlingActivityCode);
                BigDecimal upahBuruhCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), upahBuruhActivityCode);

                PerhitunganPenumpukan perhitunganPenumpukan = new PerhitunganPenumpukan();
                perhitunganPenumpukan.setRegistration(registration);
                perhitunganPenumpukan.setMasa1((short) masa1);
                perhitunganPenumpukan.setMasa2((short) masa2);
                perhitunganPenumpukan.setNoPpkb(cancelLoadingService.getPlanningVessel().getNoPpkb());
                perhitunganPenumpukan.setContNo(cancelLoadingService.getContNo());
                perhitunganPenumpukan.setMlo(cancelLoadingService.getMlo());
                perhitunganPenumpukan.setBlNo(cancelLoadingService.getBlNo());
                perhitunganPenumpukan.setMasterActivity(masterActivityFacadeRemote.find(penumpukanActivityCode));

                PerhitunganPassGate perhitunganPassGate = new PerhitunganPassGate();
                perhitunganPassGate.setJobSlip(jobSlip);
                perhitunganPassGate.setMasterActivity(passGateMasterActivity);
                perhitunganPassGate.setPlanningVessel(registration.getPlanningVessel());
                perhitunganPassGate.setRegistration(registration);

                PerhitunganUpahBuruh perhitunganUbahBuruh = new PerhitunganUpahBuruh();
                perhitunganUbahBuruh.setRegistration(registration);
                perhitunganUbahBuruh.setJobslip(jobSlip);
                perhitunganUbahBuruh.setMasterActivity(upahBuruhMasterActivity);
                perhitunganUbahBuruh.setMasterCurrency(masterCurrency);
                perhitunganUbahBuruh.setCharge(upahBuruhCharge.multiply(new BigDecimal(2)));

                BigDecimal[] discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), cancelLoadingActivityCode);
                BigDecimal discountCancelLoadingAsPercentage = discount[0];
                BigDecimal discountCancelLoadingAsMoney = discount[1];

                discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), penumpukanActivityCode);
                BigDecimal discountPenumpukanAsPercentage = discount[0];
                BigDecimal discountPenumpukanAsMoney = discount[1];

                discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), passGateActivityCode);
                BigDecimal discountPassGateAsPercentage = discount[0];
                BigDecimal discountPassGateAsMoney = discount[1];

                discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), loloActivityCode);
                BigDecimal discountLoloAsPercentage = discount[0];
                BigDecimal discountLoloAsMoney = discount[1];

                discount = masterDiscountFacadeRemote.getValidDiscount(registration.getMasterCustomer().getCustCode(), handlingActivityCode);
                BigDecimal discountHandlingAsPercentage = discount[0];
                BigDecimal discountHandlingAsMoney = discount[1];

                if (discountCancelLoadingAsMoney == null) {
                    discountCancelLoadingAsMoney = chargeCancelLoading.multiply(discountCancelLoadingAsPercentage);
                }

                if (discountPenumpukanAsMoney == null) {
                    discountPenumpukanAsMoney = penumpukanCharge.multiply(discountPenumpukanAsPercentage);
                }

                if (discountPassGateAsMoney == null) {
                    discountPassGateAsMoney = passGateCharge.multiply(discountPassGateAsPercentage);
                }

                if (discountLoloAsMoney == null) {
                    discountLoloAsMoney = loloCharge.multiply(discountLoloAsPercentage);
                }

                if (discountHandlingAsMoney == null) {
                    discountHandlingAsMoney = handlingCharge.multiply(discountHandlingAsPercentage);
                }

                handlingCharge = handlingCharge.subtract(discountHandlingAsMoney);
                loloCharge = loloCharge.subtract(discountLoloAsMoney);
                penumpukanCharge = penumpukanCharge.subtract(discountPenumpukanAsMoney);

                perhitunganPenumpukan.setCurrCode(masterCurrency.getCurrCode());
                perhitunganPenumpukan.setCharge(penumpukanCharge);
                perhitunganPenumpukan.setChargeM1(perhitunganPenumpukan.getCharge().multiply(BigDecimal.valueOf(perhitunganPenumpukan.getMasa1())));
                perhitunganPenumpukan.setChargeM2(perhitunganPenumpukan.getCharge().multiply(BigDecimal.valueOf(perhitunganPenumpukan.getMasa2())).multiply(BigDecimal.valueOf(2)));
                perhitunganPenumpukan.setJasaDermaga(BigDecimal.ZERO);
                BigDecimal totalPenumpukanCharge = perhitunganPenumpukan.getChargeM1().add(perhitunganPenumpukan.getChargeM2()).add(perhitunganPenumpukan.getJasaDermaga());
                perhitunganPenumpukan.setTotalCharge(totalPenumpukanCharge);

                passGateCharge = passGateCharge.subtract(discountPassGateAsMoney);
                perhitunganPassGate.setCharge(passGateCharge);
                perhitunganPassGate.setJumlah(1);
                perhitunganPassGate.setTotalCharge(perhitunganPassGate.getCharge().multiply(new BigDecimal(perhitunganPassGate.getJumlah())));

                totalCancelLoadingCharge = chargeCancelLoading.subtract(discountCancelLoadingAsMoney).add(loloCharge).add(handlingCharge.multiply(new BigDecimal(2))).add(additionalHandlingCharge);

                targetLocation.setContNo(cancelLoadingService.getContNo());
                targetLocation.setMlo(cancelLoadingService.getMlo());
                targetLocation.setContWeight(cancelLoadingService.getContGross());
                targetLocation.setPod(null);
                targetLocation.setNoPpkb(cancelLoadingService.getPlanningVessel().getNoPpkb());
                targetLocation.setStatus("planning");
                targetLocation.setContSize(cancelLoadingService.getContSize());
                targetLocation.setContType(cancelLoadingService.getMasterContainerType().getContType().toString());
                targetLocation.setGrossClass(grossClass);

                if (cancelLoadingService.getContSize() == 40) {
                    targetLocation40ft.setContNo(cancelLoadingService.getContNo());
                    targetLocation40ft.setMlo(cancelLoadingService.getMlo());
                    targetLocation40ft.setContWeight(cancelLoadingService.getContGross());
                    targetLocation40ft.setPod(null);
                    targetLocation40ft.setNoPpkb(cancelLoadingService.getPlanningVessel().getNoPpkb());
                    targetLocation40ft.setStatus("planning");
                    targetLocation40ft.setContSize(cancelLoadingService.getContSize());
                    targetLocation40ft.setContType(cancelLoadingService.getMasterContainerType().getContType().toString());
                    targetLocation40ft.setGrossClass(grossClass);
                    masterYardCoordinatFacadeRemote.edit(targetLocation40ft);
                }

                if (cancelLoadingService.getContSize() == 45) {
                    targetLocation45ft.setContNo(cancelLoadingService.getContNo());
                    targetLocation45ft.setMlo(cancelLoadingService.getMlo());
                    targetLocation45ft.setContWeight(cancelLoadingService.getContGross());
                    targetLocation45ft.setPod(null);
                    targetLocation45ft.setNoPpkb(cancelLoadingService.getPlanningVessel().getNoPpkb());
                    targetLocation45ft.setStatus("planning");
                    targetLocation45ft.setContSize(cancelLoadingService.getContSize());
                    targetLocation45ft.setContType(cancelLoadingService.getMasterContainerType().getContType().toString());
                    targetLocation45ft.setGrossClass(grossClass);
                    masterYardCoordinatFacadeRemote.edit(targetLocation45ft);
                }

                masterYardCoordinatFacadeRemote.edit(targetLocation);
                serviceContLoadFacadeRemote.edit(serviceContLoad);
                planningContLoadFacadeRemote.edit(planningContLoad);
                perhitunganUpahBuruhFacadeRemote.create(perhitunganUbahBuruh);
                perhitunganPassGateFacadeRemote.create(perhitunganPassGate);

                if (perhitunganPenumpukan.getTotalCharge().compareTo(BigDecimal.ZERO) > 0 
                        && cancelLoadingService.getNoStacking().equals("FALSE")) {
                    perhitunganPenumpukanFacadeRemote.create(perhitunganPenumpukan);
                }
            }

            cancelLoadingService.setTruckLosing("FALSE");
            cancelLoadingService.setChangeDestination("FALSE");
            cancelLoadingService.setJobSlip(jobSlip);
            cancelLoadingService.setCurrCode(masterCurrency.getCurrCode());
            cancelLoadingService.setCharge(totalCancelLoadingCharge);
            cancelLoadingService.setCategory(category);
            cancelLoadingServiceFacadeRemote.create(cancelLoadingService);

            viewData();
            isValid = true;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (EJBException ex) {
            isValid = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
            ex.printStackTrace();
        }

        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", isValid);
    }

    public List<String> setListAutoComplete(String query) {
        List<String> results = cancelLoadingServiceFacadeRemote.findCancelableContainerByNoPpkb(getRegistration().getPlanningVessel().getNoPpkb(), query);
        return results;
    }

    public void onBlockChanged(ValueChangeEvent event) {
        slots.clear();
        rows.clear();
        tiers.clear();
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
        batalNota = new BatalNota();
    }

    public void handleShowNewVessels(ActionEvent event) {
        newVessels = planningVesselFacadeRemote.findPlanningVesselsSgOther();
    }

    public void handleSelectNewVessel(ActionEvent event) {
        String noPpkb = (String) event.getComponent().getAttributes().get("noPpkb");
        newPlanningVessel = planningVesselFacadeRemote.find(noPpkb);
        receivingStatus = receivingServiceFacadeRemote.findReceivingCapacityStatusByNoPpkb(noPpkb);
    }

    public void handleSubmitBatalNota(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        try {
            List<CancelLoadingService> cancelLoadingServices = cancelLoadingServiceFacadeRemote.findByNoReg(registration.getNoReg());

            for (CancelLoadingService cls : cancelLoadingServices) {
                if (cls.getCategory() == 1) {
                    receivingService = receivingServiceFacadeRemote.findByNoPpkbAndContNoCancelLoading(cls.getPlanningVessel().getNoPpkb(), cls.getContNo());
                    receivingService.setStatusCancelLoading("FALSE");

                    receivingServiceFacadeRemote.edit(receivingService);
                } else if (cls.getCategory() == 2) {
                    planningContLoad = planningContLoadFacadeRemote.findByNoPpkbContNoAndStatusCancelLoading(cls.getPlanningVessel().getNoPpkb(), cls.getContNo(), true);

                    if (cancelLoadingService.getIsTranshipment().equals("TRUE")) {
                        ServiceContTranshipment serviceContTranshipment = serviceContTranshipmentFacadeRemote.findByNewPpkbAndContNo(cancelLoadingService.getNewPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                        serviceContTranshipment.setNewPpkb(registration.getPlanningVessel().getNoPpkb());

                        serviceContTranshipmentFacadeRemote.edit(serviceContTranshipment);
                    } else {
                        receivingService = receivingServiceFacadeRemote.findByNoPpkbAndContNoCancelLoading(cls.getPlanningVessel().getNoPpkb(), cls.getContNo());
                        receivingService.setStatusCancelLoading("FALSE");

                        ServiceReceiving serviceReceiving = serviceReceivingFacadeRemote.findByNoPpkbAndContNoCancelLoading(cls.getPlanningVessel().getNoPpkb(), cls.getContNo());
                        serviceReceiving.setStatusCancelLoading("FALSE");

                        receivingServiceFacadeRemote.edit(receivingService);
                        serviceReceivingFacadeRemote.edit(serviceReceiving);
                    }

                    planningContLoad.setStatusCancelLoading("FALSE");

                    String grossClass = GrossConverter.convert(cls.getContSize(), cls.getContGross());
                    MasterPort mp1 = masterPortFacadeRemote.findMasterPortByName(selectedPort1);
                    cls.setDischargePort(mp1);
                    
                    if (cls.getNewPlanningVessel().getPreserviceVessel().getMasterVessel().isKapalBayangan().equals("FALSE")) {
                        PlanningReceivingAllocation planningReceivingAllocation = planningReceivingAllocationFacadeRemote.findByAllocationConstraint(cls.getNewPlanningVessel().getNoPpkb(), cls.getContSize(), cls.getMasterContainerType().getContType(), cls.getContStatus(), cls.getOverSize(), cls.getDg(), grossClass, cls.getDischargePort().getPortCode(), cls.getIsExport());
                        planningReceivingAllocation.setTotalBooking2(planningReceivingAllocation.getTotalBooking2() - 1);
                        planningReceivingAllocationFacadeRemote.edit(planningReceivingAllocation);
                    }

                    planningContLoadFacadeRemote.edit(planningContLoad);
                    planningContLoadFacadeRemote.deleteByNoPpkbAndContNo(cls.getNewPlanningVessel().getNoPpkb(), cls.getContNo());
                } else if (cls.getCategory() == 3) {
                    planningContLoad = planningContLoadFacadeRemote.findByNoPpkbContNoAndStatusCancelLoading(cls.getPlanningVessel().getNoPpkb(), cls.getContNo(), true);

                    if (cancelLoadingService.getIsTranshipment().equals("FALSE")) {
                        receivingService = receivingServiceFacadeRemote.findByNoPpkbAndContNoCancelLoading(cls.getPlanningVessel().getNoPpkb(), cls.getContNo());
                        receivingService.setStatusCancelLoading("FALSE");
                        receivingServiceFacadeRemote.edit(receivingService);
                        ServiceReceiving serviceReceiving = serviceReceivingFacadeRemote.findByNoPpkbAndContNoCancelLoading(cls.getPlanningVessel().getNoPpkb(), cls.getContNo());
                        serviceReceiving.setStatusCancelLoading("FALSE");
                        serviceReceivingFacadeRemote.edit(serviceReceiving);
                    }

                    planningContLoad.setStatusCancelLoading("FALSE");

                    perhitunganPassGateFacadeRemote.deleteByJobSlip(cls.getJobSlip());
                    perhitunganPenumpukanFacadeRemote.deleteByContNoAndNoReg(cls.getContNo(), cls.getRegistration().getNoReg());
                    planningContLoadFacadeRemote.edit(planningContLoad);
                } else if (cls.getCategory() == 4) {
                    planningContLoad = planningContLoadFacadeRemote.findByNoPpkbContNoAndStatusCancelLoading(cls.getPlanningVessel().getNoPpkb(), cls.getContNo(), true);
                    serviceContLoad = serviceContLoadFacadeRemote.findByNoPpkbAndContNo(cls.getPlanningVessel().getNoPpkb(), cls.getContNo());

                    if (cancelLoadingService.getIsTranshipment().equals("TRUE")) {
                        ServiceContTranshipment serviceContTranshipment = serviceContTranshipmentFacadeRemote.findByNewPpkbAndContNo(cancelLoadingService.getNewPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
                        serviceContTranshipment.setNewPpkb(registration.getPlanningVessel().getNoPpkb());

                        serviceContTranshipmentFacadeRemote.edit(serviceContTranshipment);
                    } else {
                        ServiceReceiving serviceReceiving = serviceReceivingFacadeRemote.findByNoPpkbAndContNoCancelLoading(cls.getPlanningVessel().getNoPpkb(), cls.getContNo());
                        serviceReceiving.setStatusCancelLoading("FALSE");
                        receivingService = receivingServiceFacadeRemote.findByNoPpkbAndContNoCancelLoading(cls.getPlanningVessel().getNoPpkb(), cls.getContNo());
                        receivingService.setStatusCancelLoading("FALSE");

                        serviceReceivingFacadeRemote.edit(serviceReceiving);
                        receivingServiceFacadeRemote.edit(receivingService);
                    }

                    planningContLoad.setStatusCancelLoading("FALSE");
                    serviceContLoad.setStatusCancelLoading("FALSE");

                    String grossClass = GrossConverter.convert(cls.getContSize(), cls.getContGross());
                    MasterPort mp1 = masterPortFacadeRemote.findMasterPortByName(selectedPort1);
                    cls.setDischargePort(mp1);

                    if (cls.getNewPlanningVessel().getPreserviceVessel().getMasterVessel().isKapalBayangan().equals("FALSE")) {
                        PlanningReceivingAllocation planningReceivingAllocation = planningReceivingAllocationFacadeRemote.findByAllocationConstraint(cls.getNewPlanningVessel().getNoPpkb(), cls.getContSize(), cls.getMasterContainerType().getContType(), cls.getContStatus(), cls.getOverSize(), cls.getDg(), grossClass, cls.getDischargePort().getPortCode(), cls.getIsExport());
                        planningReceivingAllocation.setTotalBooking2(planningReceivingAllocation.getTotalBooking2() - 1);
                        planningReceivingAllocationFacadeRemote.edit(planningReceivingAllocation);
                    }

                    perhitunganUpahBuruhFacadeRemote.deleteByJobslip(cls.getJobSlip());
                    serviceContLoadFacadeRemote.edit(serviceContLoad);
                    planningContLoadFacadeRemote.edit(planningContLoad);
                } else if (cls.getCategory() == 5) {
                    planningContLoad = planningContLoadFacadeRemote.findByNoPpkbContNoAndStatusCancelLoading(cls.getPlanningVessel().getNoPpkb(), cls.getContNo(), true);
                    serviceContLoad = serviceContLoadFacadeRemote.findByNoPpkbAndContNo(cls.getPlanningVessel().getNoPpkb(), cls.getContNo());

                    if (cancelLoadingService.getIsTranshipment().equals("FALSE")) {
                        ServiceReceiving serviceReceiving = serviceReceivingFacadeRemote.findByNoPpkbAndContNoCancelLoading(cls.getPlanningVessel().getNoPpkb(), cls.getContNo());
                        serviceReceiving.setStatusCancelLoading("FALSE");
                        receivingService = receivingServiceFacadeRemote.findByNoPpkbAndContNoCancelLoading(cls.getPlanningVessel().getNoPpkb(), cls.getContNo());
                        receivingService.setStatusCancelLoading("FALSE");

                        serviceReceivingFacadeRemote.edit(serviceReceiving);
                        receivingServiceFacadeRemote.edit(receivingService);
                    }

                    planningContLoad.setStatusCancelLoading("FALSE");
                    serviceContLoad.setStatusCancelLoading("FALSE");

                    MasterYardCoordinat targetLocation = masterYardCoordinatFacadeRemote.findAvailableCoordinat(cls.getMasterYard().getBlock(), cls.getySlot(), cls.getyRow(), cls.getyTier());
                    MasterYardCoordinat targetLocation40ft = masterYardCoordinatFacadeRemote.findAvailableCoordinat(cls.getMasterYard().getBlock(), (cls.getySlot() + 1), cls.getyRow(), cls.getyTier());
                    MasterYardCoordinat targetLocation45ft = masterYardCoordinatFacadeRemote.findAvailableCoordinat(cls.getMasterYard().getBlock(), (cls.getySlot() + 2), cls.getyRow(), cls.getyTier());

                    if (targetLocation != null && targetLocation.getContNo().equals(cls.getContNo())) {
                        targetLocation.setContNo(null);
                        targetLocation.setMlo(null);
                        targetLocation.setContWeight(null);
                        targetLocation.setPod(null);
                        targetLocation.setNoPpkb(null);
                        targetLocation.setStatus("empty");
                        targetLocation.setContSize(null);
                        targetLocation.setContType(null);
                        targetLocation.setGrossClass(null);
                        masterYardCoordinatFacadeRemote.edit(targetLocation);
                    }

                    if (cls.getContSize() == 40 && targetLocation40ft != null && targetLocation40ft.getContNo().equals(cls.getContNo())) {
                        targetLocation40ft.setContNo(null);
                        targetLocation40ft.setMlo(null);
                        targetLocation40ft.setContWeight(null);
                        targetLocation40ft.setPod(null);
                        targetLocation40ft.setNoPpkb(null);
                        targetLocation40ft.setStatus("empty");
                        targetLocation40ft.setContSize(null);
                        targetLocation40ft.setContType(null);
                        targetLocation40ft.setGrossClass(null);
                        masterYardCoordinatFacadeRemote.edit(targetLocation40ft);
                    }

                    if (cls.getContSize() == 45 && targetLocation45ft != null && targetLocation45ft.getContNo() != null && targetLocation45ft.getContNo().equals(cls.getContNo())) {
                        targetLocation45ft.setContNo(null);
                        targetLocation45ft.setMlo(null);
                        targetLocation45ft.setContWeight(null);
                        targetLocation45ft.setPod(null);
                        targetLocation45ft.setNoPpkb(null);
                        targetLocation45ft.setStatus("empty");
                        targetLocation45ft.setContSize(null);
                        targetLocation45ft.setContType(null);
                        targetLocation45ft.setGrossClass(null);
                        masterYardCoordinatFacadeRemote.edit(targetLocation45ft);
                    }

                    perhitunganPassGateFacadeRemote.deleteByJobSlip(cls.getJobSlip());
                    perhitunganUpahBuruhFacadeRemote.deleteByJobslip(cls.getJobSlip());
                    perhitunganPenumpukanFacadeRemote.deleteByContNoAndNoReg(cls.getContNo(), cls.getRegistration().getNoReg());
                    serviceContLoadFacadeRemote.edit(serviceContLoad);
                    planningContLoadFacadeRemote.edit(planningContLoad);
                } else {
                    throw new RuntimeException(String.format("Cancel loading category is not valid [contNo=%s]", cls.getContNo()));
                }

                cancelLoadingServiceFacadeRemote.remove(cls);
            }

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

            disCancelInv = true;
            perhitunganCancelLoadings.clear();

            if (masterSettingAppFacade.isPaymentBankingEnabled()) {
                try {
                    bankingSyncFacadeRemote.cancelInvoice(noInvoice);
                } catch (RuntimeException e) {
                    System.err.println(FacesHelper.getLocaleMessage("application.integration.banking.failed", FacesContext.getCurrentInstance()));
                    FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.integration.banking.failed");
                }
            }

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            loggedIn = true;
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
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/perhitunganReceiving.pdf?noRegistrasi=" + registration.getNoReg() + "&total=" + String.valueOf(total) + "&ppn=" + String.valueOf(ppnPrint) + "&materai=" + String.valueOf(getMateraiPrint()) + "&userId=" + getUserId() + "&mode=cancelLoading")));
    }

    public void handleDownloadNota(ActionEvent event) {
        invoice = invoiceFacadeRemote.find(registration.getNoReg());

        //validasi print nota berulang
        if (invoice.getValidasiPrint().equals("FALSE")) {
            invoice = invoiceFacadeRemote.publishInvoice(invoice);
        }

        disPrint = invoice.getValidasiPrint().equals("TRUE") ? true : false;
        viewData();

        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/invoice.pdf?no_reg=" + registration.getNoReg() + "&to=" + invoice.getTotalTagihan().toString() + "&curr=" + masterCurrency.getCountry() + "&tipe=" + registration.getMasterService().getServiceCode() + "&username=" + getUserId() + "&detail=" + String.valueOf(disDetail))));
    }

    public void handleDownloadDetail(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/detail.pdf?no_reg=" + registration.getNoReg() + "&tipe=" + registration.getMasterService().getServiceCode() + "&username=" + getUserId() + "&ppn=" + String.valueOf(ppnPrint) + "&materai=" + String.valueOf(materaiPrint) + "&total=" + String.valueOf(total))));
    }

    public Boolean getNoRelocation() {
        return noRelocation;
    }

    public void setNoRelocation(Boolean relocation) {
        this.noRelocation = relocation;
    }

    public String getSelectedPort1() {
        return selectedPort1;
    }

    public void setSelectedPort1(String selectedPort1) {
        this.selectedPort1 = selectedPort1;
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
     * @return the cancelLoadings
     */
    public List<Object[]> getPerhitunganCancelLoadings() {
        return perhitunganCancelLoadings;
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
     * @return the cancelLoadingService
     */
    public CancelLoadingService getCancelLoadingService() {
        return cancelLoadingService;
    }

    /**
     * @param cancelLoadingService the cancelLoadingService to set
     */
    public void setCancelLoadingService(CancelLoadingService cancelLoadingService) {
        this.cancelLoadingService = cancelLoadingService;
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

    public boolean getIsNotPullOutAble() {
        return isNotPullOutAble;
    }

    public ServiceContLoad getServiceContLoadObject() {
        return serviceContLoad;
    }

    public void setServiceContLoadObject(ServiceContLoad serviceContLoadObject) {
        this.serviceContLoad = serviceContLoadObject;
    }

    public ReceivingService getReceivingService() {
        return receivingService;
    }

    public void setReceivingService(ReceivingService receivingService) {
        this.receivingService = receivingService;
    }

    public Short getCategory() {
        return category;
    }

    public void setCategory(Short category) {
        this.category = category;
    }

    public BigDecimal getMateraiPrint() {
        return materaiPrint;
    }

    public void setMateraiPrint(BigDecimal materaiPrint) {
        this.materaiPrint = materaiPrint;
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

    public PlanningContLoad getPlanningContLoad() {
        return planningContLoad;
    }

    public void setPlanningContLoad(PlanningContLoad planningContLoad) {
        this.planningContLoad = planningContLoad;
    }

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    public BigDecimal getPpn() {
        return ppn;
    }

    public PlanningVessel getNewPlanningVessel() {
        return newPlanningVessel;
    }

    public List<Object[]> getNewVessels() {
        return newVessels;
    }

    public Object[] getReceivingStatus() {
        return receivingStatus;
    }

    public BigDecimal getUpahBuruh() {
        return upahBuruh;
    }
    
    public MasterPort getImplementedPort() {
        return implementedPort;
    }

    public List<Object[]> getMasterYards() {
        return masterYards;
    }

    public Map<Integer, Integer> getRows() {
        if (masterYard != null) {
            for (int i = 1; i <= masterYard.getRow(); i++) {
                rows.put(i, i);
            }
        }

        return rows;
    }

    public Map<Integer, Integer> getSlots() {
        if (masterYard != null) {
            for (int i = 1; i <= masterYard.getSlot(); i++) {
                slots.put(i, i);
            }
        }

        return slots;
    }

    public Map<Integer, Integer> getTiers() {
        if (masterYard != null) {
            for (int i = 1; i <= masterYard.getTier(); i++) {
                tiers.put(i, i);
            }
        }

        return tiers;
    }

    public Boolean getIsSimpatReady() {
        return isSimpatReady;
    }

    public Boolean getNoCharge() {
        return noCharge;
    }

    public void setNoCharge(Boolean noCharge) {
        this.noCharge = noCharge;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.qtasnim.util.EnglishNumberConverter;
import com.qtasnim.util.IndonesianNumberConverter;
import com.pelindo.ebtos.ejb.facade.local.BatalNotaFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.InvoiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterPortFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterSettingAppFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningReceivingAllocationFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningVesselFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ReceivingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.StuffingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.local.DataStorageManagerLocal;
import com.pelindo.ebtos.ejb.remote.StuffingCalculationOperationRemote;
import com.pelindo.ebtos.exception.GrossCapacityExceedTheLimitsException;
import com.pelindo.ebtos.exception.HasJobSlipAndNotEnteringGateYetException;
import com.pelindo.ebtos.exception.NotAllocatedReceivingException;
import com.pelindo.ebtos.exception.ReceivingAllocationIsNotEnoughException;
import com.pelindo.ebtos.exception.RegisteredWithSamePpkbContainerException;
import com.pelindo.ebtos.exception.TeusCapacityExceedTheLimitsException;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PlanningReceivingAllocation;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.StuffingService;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.report.model.Invoice;
import com.pelindo.ebtos.report.model.Perhitungan;
import com.pelindo.ebtos.report.model.charge.StrippingStuffingChargeDetail;
import com.pelindo.ebtos.util.GrossConverter;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author R. Seno Anggoro A
 */
@Stateless
public class StuffingCalculationOperation implements StuffingCalculationOperationRemote {
    @EJB
    private BatalNotaFacadeLocal batalNotaFacadeLocal;
    @EJB
    private InvoiceFacadeLocal invoiceFacadeLocal;
    @EJB
    private StuffingServiceFacadeLocal stuffingServiceFacadeLocal;
    @EJB
    private DataStorageManagerLocal dataStorageManagerlocal;
    @EJB
    private MasterSettingAppFacadeLocal masterSettingAppFacadeLocal;
    @EJB
    private PlanningContReceivingFacadeLocal planningContReceivingFacadeLocal;
    @EJB
    private MasterPortFacadeLocal masterPortFacadeLocal;
    @EJB
    private PlanningReceivingAllocationFacadeLocal planningReceivingAllocationFacadeLocal;
    @EJB
    private ReceivingServiceFacadeLocal receivingServiceFacadeLocal;
    @EJB
    private PlanningVesselFacadeLocal planningVesselFacadeLocal;

    public StuffingService saveStuffingContainers(StuffingService stuffingService, String noPpkb) throws GrossCapacityExceedTheLimitsException, TeusCapacityExceedTheLimitsException, NotAllocatedReceivingException, ReceivingAllocationIsNotEnoughException, RegisteredWithSamePpkbContainerException, HasJobSlipAndNotEnteringGateYetException {
        PlanningVessel planningVessel = planningVesselFacadeLocal.find(noPpkb);
        stuffingServiceFacadeLocal.create(stuffingService);

        return stuffingServiceFacadeLocal.find(stuffingService.getJobSlip());
/**
        PlanningContReceiving planningContReceiving = planningContReceivingFacadeLocal.findByNoPpkbAndContNo(stuffingService.getRegistration().getPlanningVessel().getNoPpkb(), stuffingService.getContNo());
        String grossClass = GrossConverter.convert(stuffingService.getContSize(), stuffingService.getContGross());
        MasterPort dischargePort = masterPortFacadeLocal.find(planningContReceiving.getDischPort());
        MasterPort loadPort = masterPortFacadeLocal.find(planningContReceiving.getLoadPort());

        planningContReceiving.setContGross(stuffingService.getContGross());
        planningContReceiving.setContStatus(stuffingService.getContStatus());
        planningContReceiving.setPosition("01");
        planningContReceiving.setGrossClass(grossClass);
        planningContReceiving.setOverSize(stuffingService.getOverSize());

        Object[] receivingStatus = receivingServiceFacadeLocal.findReceivingCapacityStatusByNoPpkb(planningVessel.getNoPpkb());

        Long currentGrt = ((Number) receivingStatus[0]).longValue();
        Long currentContCapacity = ((Number) receivingStatus[1]).longValue();

        if (currentGrt.doubleValue() + (planningContReceiving.getContGross() / 1000) > planningVessel.getPreserviceVessel().getMasterVessel().getGrt()) {
            throw new GrossCapacityExceedTheLimitsException(planningVessel.getPreserviceVessel().getMasterVessel().getGrt());
        }

        if (currentContCapacity.doubleValue() + planningContReceiving.getContTeus() > planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity()) {
            throw new TeusCapacityExceedTheLimitsException(planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity());
        }

        List<Object[]> hasJobSlipAndNotEnteringGateYet = receivingServiceFacadeLocal.findNotEnteringGateYetByContNo(planningContReceiving.getContNo());

        if (!hasJobSlipAndNotEnteringGateYet.isEmpty()) {
            throw new HasJobSlipAndNotEnteringGateYetException();
        }

        //[] registeredWithSamePpkbContainer = planningContReceivingFacadeLocal.findPlanningContReceivingByCekExist(planningContReceiving.getContNo(), planningVessel.getNoPpkb());

        //if (registeredWithSamePpkbContainer != null) {
            //throw new RegisteredWithSamePpkbContainerException();
        //}

        PlanningReceivingAllocation newAllocation = planningReceivingAllocationFacadeLocal.findByAllocationConstraint(
                planningVessel.getNoPpkb(),
                planningContReceiving.getContSize(),
                planningContReceiving.getMasterContainerType().getContType(),
                planningContReceiving.getContStatus(),
                planningContReceiving.getOverSize(),
                planningContReceiving.getDg(),
                planningContReceiving.getGrossClass(),
                planningContReceiving.getDischPort(),
                planningContReceiving.getIsExport());

        if (newAllocation == null) {
            throw new NotAllocatedReceivingException();
        }

        if (newAllocation.getTotalBooking2() >= newAllocation.getAllocated()) {
            throw new ReceivingAllocationIsNotEnoughException();
        }

        newAllocation.setTotalBooking2(newAllocation.getTotalBooking2() + 1);
        planningContReceiving.setIdConstrainPlanning(newAllocation.getId());
        planningContReceiving.setPlanningVessel(planningVessel);

        ReceivingService receivingService = new ReceivingService();
        receivingService.setMasa1((short) 0);
        receivingService.setMasa2((short) 0);
        receivingService.setJobSlip(stuffingService.getJobSlip());
        receivingService.setMasterContainerType(stuffingService.getMasterContainerType());
        receivingService.setMasterCommodity(stuffingService.getMasterCommodity());
        receivingService.setMasterPort(dischargePort);
        receivingService.setMasterPort1(loadPort);
        receivingService.setContNo(stuffingService.getContNo());
        receivingService.setContSize(stuffingService.getContSize());
        receivingService.setContStatus(stuffingService.getContStatus());
        receivingService.setContGross(stuffingService.getContGross());
        receivingService.setDg(stuffingService.getDg());
        receivingService.setDgLabel(stuffingService.getDgLabel());
        receivingService.setOverSize(stuffingService.getOverSize());
        receivingService.setIsExport(planningContReceiving.getIsExport());
        receivingService.setPlanningVessel(planningContReceiving.getPlanningVessel());
        receivingService.setRegistration(stuffingService.getRegistration());
        receivingService.setStatusCancelLoading("FALSE");
        receivingService.setValidDate(stuffingService.getRegistration().getPlanningVessel().getCloseRec());
        receivingService.setIsGenerate("FALSE");
        receivingService.setPortOfDelivery(planningContReceiving.getPortOfDelivery());
        receivingService.setCounterPrint(0);

        stuffingService.setExStuffingLoadContainer(receivingService);

        planningContReceivingFacadeLocal.edit(planningContReceiving);
        planningReceivingAllocationFacadeLocal.edit(newAllocation);
        receivingServiceFacadeLocal.create(receivingService);
        return stuffingServiceFacadeLocal.find(stuffingService.getJobSlip());
**/        
    }

    public void removeStuffingContainer(StuffingService stuffingService){
        PlanningContReceiving planningContReceiving = planningContReceivingFacadeLocal.findByNoPpkbAndContNo(stuffingService.getExStuffingLoadContainer().getPlanningVessel().getNoPpkb(), stuffingService.getContNo());

        PlanningReceivingAllocation newAllocation = planningReceivingAllocationFacadeLocal.findByAllocationConstraint(stuffingService.getExStuffingLoadContainer().getPlanningVessel().getNoPpkb(),
                planningContReceiving.getContSize(),
                planningContReceiving.getMasterContainerType().getContType(),
                planningContReceiving.getContStatus(),
                planningContReceiving.getOverSize(),
                planningContReceiving.getDg(),
                planningContReceiving.getGrossClass(),
                planningContReceiving.getDischPort(),
                planningContReceiving.getIsExport());

        planningContReceiving.setContGross(stuffingService.getContSize() == 20 ? 2000 : 3500);
        planningContReceiving.setContStatus("MTY");
        planningContReceiving.setPosition("02");
        planningContReceiving.setGrossClass("E");
        planningContReceiving.setPlanningVessel(stuffingService.getRegistration().getPlanningVessel());
        planningContReceivingFacadeLocal.edit(planningContReceiving);

        PlanningReceivingAllocation previousAllocation = planningReceivingAllocationFacadeLocal.findByAllocationConstraint(planningContReceiving.getPlanningVessel().getNoPpkb(),
                planningContReceiving.getContSize(),
                planningContReceiving.getMasterContainerType().getContType(),
                planningContReceiving.getContStatus(),
                planningContReceiving.getOverSize(),
                planningContReceiving.getDg(),
                planningContReceiving.getGrossClass(),
                planningContReceiving.getDischPort(),
                planningContReceiving.getIsExport());

        planningContReceiving.setIdConstrainPlanning(previousAllocation.getId());
        newAllocation.setTotalBooking2(newAllocation.getTotalBooking2() - 1);

        planningReceivingAllocationFacadeLocal.edit(newAllocation);
        stuffingServiceFacadeLocal.remove(stuffingService);
    }

    public void cancelInvoice(Registration registration, BatalNota batalNota) {
        if (registration != null && registration.getInvoice() != null && batalNota != null && batalNota.getAlasanPembatalan() != null && batalNota.getNoBeritaAcara() != null && batalNota.getTglPembatalan() != null) {
            com.pelindo.ebtos.model.db.Invoice invoice = registration.getInvoice();

            batalNota.setCustCode(registration.getMasterCustomer().getCustCode());
            batalNota.setNoFakturPajak(invoice.getNoFakturPajak());
            batalNota.setNoInvoice(invoice.getNoInvoice());
            batalNota.setNoReg(invoice.getNoReg());
            batalNota.setPaymentDate(invoice.getPaymentDate());
            batalNota.setTotalTagihan(invoice.getTotalTagihan());

            batalNotaFacadeLocal.create(batalNota);

            invoice.setCancelInvoice("TRUE");
            invoice.setNoFakturPajak(null);
            invoice.setNoInvoice(null);
            
            invoiceFacadeLocal.edit(invoice);
            List<StuffingService> stuffingServices = stuffingServiceFacadeLocal.findByNoReg(registration.getNoReg());
            for (StuffingService stuffingService: stuffingServices) {
                removeStuffingContainer(stuffingService);
            }
        } else {
            throw new RuntimeException("Some parameter to Cancel Invoice is empty");
        }
    }
    
    public String preparePerhitunganReport(Registration registration, MasterCurrency masterCurrency, BigDecimal ppn, BigDecimal materai, List<StuffingService> stuffingServices) {
        if (registration != null && masterCurrency != null && ppn != null && materai != null && stuffingServices != null && !stuffingServices.isEmpty()) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            String loggedInUser = (String) session.getAttribute("username");
            loggedInUser = loggedInUser == null ? "unknown" : loggedInUser;

            Perhitungan perhitungan = new Perhitungan(registration.getMasterService().getServiceName());
            perhitungan.setTitle("Rincian Perhitungan Stuffing");
            perhitungan.setReport("/com/pelindo/ebtos/report/Perhitungan.jasper");
            perhitungan.setMasterCurrency(masterCurrency);
            perhitungan.setMaterai(materai);
            perhitungan.setPpn(ppn);
            perhitungan.setRegistration(registration);
            perhitungan.setUsername(loggedInUser);
            perhitungan.setReportId("Perhitungan_" + registration.getNoReg());
            perhitungan.addSubReport("SUBREPORT_DIR", "/com/pelindo/ebtos/report/PerhitunganStuffing_subreport.jasper");
            perhitungan.setListDataSource(stuffingServices);

            String key = UUID.randomUUID().toString();
            dataStorageManagerlocal.putData(session.getId(), key, perhitungan);

            return key;
        } else {
            throw new RuntimeException("Some parameter to generate Perhitungan Report is empty");
        }
    }

    public String preparePerhitunganInvoiceReport(Registration registration, List<StuffingService> stuffingServices) {
        if (registration != null && registration.getInvoice() != null && stuffingServices != null && !stuffingServices.isEmpty()) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            String username = (String) session.getAttribute("username");
            username = username == null ? "unknown" : username;

            Perhitungan perhitungan = new Perhitungan(registration.getMasterService().getServiceName());
            perhitungan.setTitle("Rincian Perhitungan Stuffing");
            perhitungan.setReport("/com/pelindo/ebtos/report/Perhitungan.jasper");
            perhitungan.setMasterCurrency(registration.getInvoice().getMasterCurrency());
            perhitungan.setMaterai(registration.getInvoice().getMaterai());
            perhitungan.setPpn(registration.getInvoice().getPpn());
            perhitungan.setRegistration(registration);
            perhitungan.setUsername(username);
            perhitungan.setReportId("Perhitungan_" + registration.getNoReg());
            perhitungan.setNotebene("Catatan : Lampiran ini hanya rincian dari Nota Tagihan, dan tidak dapat digunakan sebagai bukti pembayaran sah atau sebagai faktur pajak.");
            perhitungan.addSubReport("SUBREPORT_DIR", "/com/pelindo/ebtos/report/PerhitunganStuffing_subreport.jasper");
            perhitungan.setListDataSource(stuffingServices);

            String key = UUID.randomUUID().toString();
            dataStorageManagerlocal.putData(session.getId(), key, perhitungan);

            return key;
        } else {
            throw new RuntimeException("Some parameter that required to generate Perhitungan Report is empty");
        }
    }
    public String prepareInvoiceReport(Registration registration, List<StuffingService> stuffingServices) {
        try {
            if (registration != null && registration.getInvoice() != null && stuffingServices != null && !stuffingServices.isEmpty()) {
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                String username = (String) session.getAttribute("username");
                username = username == null ? "unknown" : username;

                MasterSettingApp npwp = masterSettingAppFacadeLocal.find("ebtos.company.npwp");
                MasterSettingApp ppkp = masterSettingAppFacadeLocal.find("ebtos.company.ppkp");
                MasterSettingApp ppkpTmt = masterSettingAppFacadeLocal.find("ebtos.company.ppkp_tmt");
                MasterSettingApp dirOfFinance = masterSettingAppFacadeLocal.find("ebtos.ttd.invoice");
                String terbilang = new String();
                String copyStatus = invoiceFacadeLocal.invoiceCopyStatus(registration.getInvoice().getNoInvoice());

                if(registration.getInvoice().getMasterCurrency().getCurrCode().equalsIgnoreCase("IDR")){
                    IndonesianNumberConverter converter = new IndonesianNumberConverter();
                    terbilang = converter.convertToWord(registration.getInvoice().getTotalTagihan().toBigInteger().toString()) + "Rupiah";
                } else {
                    EnglishNumberConverter converterEng = new EnglishNumberConverter();
                    terbilang = converterEng.numberAsSentenceFactory(registration.getInvoice().getTotalTagihan().toString());
                }

                BigDecimal totalStuffing = BigDecimal.ZERO;
                BigDecimal totalSewaAlat = BigDecimal.ZERO;
                BigDecimal upahBuruh = BigDecimal.ZERO;

                for (StuffingService stuffingService: stuffingServices) {
                    totalStuffing = totalStuffing.add(stuffingService.getPerhitunganStuffing().getCharge());
                    //upahBuruh = upahBuruh.add(stuffingService.getPerhitunganStuffing().getPerhitunganUpahBuruh().getCharge());

                    //if (stuffingService.getPerhitunganStuffing().getPerhitunganSewaAlat() != null && stuffingService.getPerhitunganStuffing().getPerhitunganSewaAlat().getCharge() != null) {
                    //    totalSewaAlat = totalSewaAlat.add(stuffingService.getPerhitunganStuffing().getPerhitunganSewaAlat().getCharge());
                    //}
                }

                StrippingStuffingChargeDetail chargeDetail = new StrippingStuffingChargeDetail();
                chargeDetail.setPpn(registration.getInvoice().getPpn());
                chargeDetail.setUpahBuruh(upahBuruh);
                chargeDetail.setMaterai(registration.getInvoice().getMaterai());
                chargeDetail.setJumlahTagihan(registration.getInvoice().getJumlahTagihan());
                chargeDetail.setTotalTagihan(registration.getInvoice().getTotalTagihan());
                chargeDetail.setStrippingStuffing(totalStuffing);
                chargeDetail.setSewaAlat(totalSewaAlat);
                chargeDetail.setTerbilang(terbilang);

                Invoice invoice = new Invoice();
                invoice.setChargeDetail(chargeDetail);
                invoice.setCompanyNpwp(npwp.getValueString());
                invoice.setCompanyPpkp(ppkp.getValueString());
                invoice.setCompanyValidPpkpDate(ppkpTmt.getValueDate());
                invoice.setContainersCount(stuffingServices.size());
                invoice.setDirectorOfFinanceId(dirOfFinance.getValueString());
                invoice.setDirectorOfFinanceName(dirOfFinance.getDescription());
                invoice.setRegistration(registration);
                invoice.setUsername(username);
                invoice.setCopyStatus(copyStatus);
                invoice.setReportId("Invoice_" + registration.getInvoice().getNoInvoice());
                invoice.setReport("/com/pelindo/ebtos/report/Invoice.jasper");
                invoice.addSubReport("detailChargeSubreport", "/com/pelindo/ebtos/report/InvoiceStuffing_detailChargeSubreport.jasper");
                invoice.addSubReport("chargeSummarySubreport", "/com/pelindo/ebtos/report/Invoice_chargeSummaryWithUpahBuruhSubreport.jasper");

                String key = UUID.randomUUID().toString();
                dataStorageManagerlocal.putData(session.getId(), key, invoice);

                return key;
            }
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when prepareInvoiceReport", re);
        }

        return null;
    }
}

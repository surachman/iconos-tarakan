/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.qtasnim.util.EnglishNumberConverter;
import com.qtasnim.util.IndonesianNumberConverter;
import com.pelindo.ebtos.ejb.facade.local.BatalNotaFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.CancelDocumentServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.InvoiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterSettingAppFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningReceivingAllocationFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ReceivingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.UnderpaymentHistoryFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganLiftServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanFacadeRemote;
import com.pelindo.ebtos.ejb.local.DataStorageManagerLocal;
import com.pelindo.ebtos.ejb.remote.CancelDocumentCalculationOperationRemote;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.CancelDocumentService;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PlanningReceivingAllocation;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.CancelDocumentService;
import com.pelindo.ebtos.model.db.PerhitunganLiftService;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukan;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.UnderpaymentHistory;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.report.model.Invoice;
import com.pelindo.ebtos.report.model.Perhitungan;
import com.pelindo.ebtos.report.model.charge.CancelDocumentChargeDetail;
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
public class CancelDocumentCalculationOperation implements CancelDocumentCalculationOperationRemote {
    @EJB
    private UnderpaymentHistoryFacadeLocal underpaymentHistoryFacadeLocal;
    @EJB
    private ReceivingServiceFacadeLocal receivingServiceFacadeLocal;
    @EJB
    private PlanningContReceivingFacadeLocal planningContReceivingFacadeLocal;
    @EJB
    private PlanningReceivingAllocationFacadeLocal planningReceivingAllocationFacadeLocal;
    @EJB
    private CancelDocumentServiceFacadeLocal cancelDocumentServiceFacadeLocal;
    @EJB
    private BatalNotaFacadeLocal batalNotaFacadeLocal;
    @EJB
    private InvoiceFacadeLocal invoiceFacadeLocal;
    @EJB
    private DataStorageManagerLocal dataStorageManagerlocal;
    @EJB
    private MasterSettingAppFacadeLocal masterSettingAppFacadeLocal;

    @EJB PerhitunganLiftServiceFacadeRemote perhitunganLiftServiceFacadeRemote;
    @EJB PerhitunganPenumpukanFacadeRemote perhitunganPenumpukanFacadeRemote;
    
    public void removeCancelDocument(CancelDocumentService cancelDocumentService) {
        UnderpaymentHistory underpaymentHistory = underpaymentHistoryFacadeLocal.find(cancelDocumentService.getJobSlip());
        PlanningVessel ppkb = null;
        if(cancelDocumentService.getNewPlanningVessel() != null){
            ppkb = cancelDocumentService.getNewPlanningVessel();
        }else{
            ppkb = cancelDocumentService.getRegistration().getPlanningVessel();
        }
        ReceivingService receivingService = receivingServiceFacadeLocal.findByNoPpkbAndContNoNotCancelLoading(ppkb.getNoPpkb(), cancelDocumentService.getContNo());
        PerhitunganLiftService perhitunganLiftService = perhitunganLiftServiceFacadeRemote.findByContNoAndNoReg(underpaymentHistory.getContNo(), underpaymentHistory.getRegistration().getNoReg());
        if (perhitunganLiftService != null) {
            perhitunganLiftService.setContNo(underpaymentHistory.getContNo());
            perhitunganLiftService.setNoPpkb(cancelDocumentService.getRegistration().getPlanningVessel().getNoPpkb());
            perhitunganLiftServiceFacadeRemote.edit(perhitunganLiftService);
        }

        PerhitunganPenumpukan perhitunganPenumpukan = perhitunganPenumpukanFacadeRemote.findByContNoAndNoReg(underpaymentHistory.getContNo(), underpaymentHistory.getRegistration().getNoReg());
        if (perhitunganPenumpukan != null) {
            perhitunganPenumpukan.setContNo(underpaymentHistory.getContNo());
            perhitunganPenumpukan.setNoPpkb(cancelDocumentService.getRegistration().getPlanningVessel().getNoPpkb());
            perhitunganPenumpukanFacadeRemote.edit(perhitunganPenumpukan);
        }

        receivingService.setJobSlip(underpaymentHistory.getJobSlip());
        receivingService.setBlNo(underpaymentHistory.getBlNo());
        receivingService.setContGross(underpaymentHistory.getContGross());
        receivingService.setContNo(underpaymentHistory.getContNo());
        receivingService.setContSize(underpaymentHistory.getContSize());
        receivingService.setContStatus(underpaymentHistory.getContStatus());
        receivingService.setDg(underpaymentHistory.isDg());
        receivingService.setDgLabel(underpaymentHistory.isDgLabel());
        receivingService.setMasterPort(underpaymentHistory.getDischargePort());
        receivingService.setTwentyOneFeet(underpaymentHistory.getTwentyOneFeet());
        receivingService.setIsExport(underpaymentHistory.isIsExport());
        receivingService.setMasterPort1(underpaymentHistory.getLoadPort());
        receivingService.setMasterCommodity(underpaymentHistory.getMasterCommodity());
        receivingService.setMasterContainerType(underpaymentHistory.getMasterContainerType());
        receivingService.setMlo(underpaymentHistory.getMlo());
        receivingService.setOverSize(underpaymentHistory.isOverSize());
        receivingService.setPortOfDelivery(underpaymentHistory.getPortOfDelivery());
        receivingService.setRegistration(underpaymentHistory.getRegistration());
        receivingService.setSealNo(underpaymentHistory.getSealNo());
        receivingService.setPlanningVessel(underpaymentHistory.getRegistration().getPlanningVessel());

        PlanningContReceiving planningContReceiving = planningContReceivingFacadeLocal.findByNoPpkbAndContNo(ppkb.getNoPpkb(), cancelDocumentService.getContNo());
        planningContReceiving.setContGross(receivingService.getContGross());
        planningContReceiving.setMasterContainerType(receivingService.getMasterContainerType());
        planningContReceiving.setContSize(receivingService.getContSize());
        planningContReceiving.setContStatus(receivingService.getContStatus());
        planningContReceiving.setOverSize(receivingService.getOverSize());
        planningContReceiving.setDg(receivingService.getDg());
        planningContReceiving.setDgLabel(receivingService.getDgLabel());
        planningContReceiving.setContNo(receivingService.getContNo());
        planningContReceiving.setTwentyOneFeet(receivingService.getTwentyOneFeet());
        planningContReceiving.setMlo(receivingService.getMlo());
        planningContReceiving.setDischPort(receivingService.getMasterPort().getPortCode());
        planningContReceiving.setLoadPort(receivingService.getMasterPort1().getPortCode());
        planningContReceiving.setMasterCommodity(receivingService.getMasterCommodity());
        planningContReceiving.setSealNo(receivingService.getSealNo());
        planningContReceiving.setPlanningVessel(receivingService.getPlanningVessel());
        planningContReceiving.setIsExport(receivingService.getIsExport());
        planningContReceiving.setGrossClass(underpaymentHistory.getGrossClass());
        planningContReceiving.setPortOfDelivery(receivingService.getPortOfDelivery());
        planningContReceiving.setBlNo(receivingService.getBlNo());

        if (!cancelDocumentService.getDischargePort().getPortCode().equals(underpaymentHistory.getDischargePort().getPortCode())) {
            PlanningReceivingAllocation planningReceivingAllocation = 
                        planningReceivingAllocationFacadeLocal.findByAllocationConstraint(
                            underpaymentHistory.getRegistration().getPlanningVessel().getNoPpkb(), underpaymentHistory.getContSize(), underpaymentHistory.getMasterContainerType().getContType(), underpaymentHistory.getContStatus(), underpaymentHistory.isOverSize(), underpaymentHistory.isDg(), underpaymentHistory.getGrossClass(), underpaymentHistory.getDischargePort().getPortCode(), underpaymentHistory.isIsExport());

            if ((underpaymentHistory.getRegistration().getPlanningVessel().getPreserviceVessel().getMasterVessel().isKapalBayangan()).contentEquals("FALSE")) {
                planningReceivingAllocation.setTotalBooking2(planningReceivingAllocation.getTotalBooking2() + 1);
                planningReceivingAllocationFacadeLocal.edit(planningReceivingAllocation);
            }

            planningContReceiving.setIdConstrainPlanning(planningReceivingAllocation.getId());

            if ((cancelDocumentService.getRegistration().getPlanningVessel().getPreserviceVessel().getMasterVessel().isKapalBayangan()).contentEquals("FALSE") ) {
                planningReceivingAllocation = planningReceivingAllocationFacadeLocal.findByAllocationConstraint(cancelDocumentService.getRegistration().getPlanningVessel().getNoPpkb(), cancelDocumentService.getContSize(), cancelDocumentService.getMasterContainerType().getContType(), cancelDocumentService.getContStatus(), cancelDocumentService.isOverSize(), cancelDocumentService.isDg(), cancelDocumentService.getGrossClass(), cancelDocumentService.getDischargePort().getPortCode(), cancelDocumentService.isIsExport());
                planningReceivingAllocation.setTotalBooking2(planningReceivingAllocation.getTotalBooking2() - 1);
                planningReceivingAllocationFacadeLocal.edit(planningReceivingAllocation);
            }
        }

        planningContReceivingFacadeLocal.edit(planningContReceiving);
        receivingServiceFacadeLocal.edit(receivingService);
        cancelDocumentServiceFacadeLocal.remove(cancelDocumentService);
        underpaymentHistoryFacadeLocal.remove(underpaymentHistory);
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

            List<CancelDocumentService> cancelDocumentServices = cancelDocumentServiceFacadeLocal.findByNoReg(registration.getNoReg());
            for (CancelDocumentService cancelDocumentService: cancelDocumentServices) {
                removeCancelDocument(cancelDocumentService);
            }
        } else {
            throw new RuntimeException("Some parameter to Cancel Invoice is empty");
        }
    }


    public String preparePerhitunganReport(Registration registration, MasterCurrency masterCurrency, BigDecimal ppn, BigDecimal materai, List<CancelDocumentService> cancelDocumentServices) {
        if (registration != null && masterCurrency != null && ppn != null && materai != null && cancelDocumentServices != null && !cancelDocumentServices.isEmpty()) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            String loggedInUser = (String) session.getAttribute("username");
            loggedInUser = loggedInUser == null ? "unknown" : loggedInUser;

            Perhitungan perhitungan = new Perhitungan(registration.getMasterService().getServiceName());
            perhitungan.setTitle("Rincian Perhitungan Cancel Document");
            perhitungan.setReport("/com/pelindo/ebtos/report/Perhitungan.jasper");
            perhitungan.setMasterCurrency(masterCurrency);
            perhitungan.setMaterai(materai);
            perhitungan.setPpn(ppn);
            perhitungan.setRegistration(registration);
            perhitungan.setUsername(loggedInUser);
            perhitungan.setReportId("Perhitungan_" + registration.getNoReg());
            perhitungan.addSubReport("SUBREPORT_DIR", "/com/pelindo/ebtos/report/PerhitunganCancelDocument_subreport.jasper");
            perhitungan.setListDataSource(cancelDocumentServices);

            String key = UUID.randomUUID().toString();
            dataStorageManagerlocal.putData(session.getId(), key, perhitungan);

            return key;
        } else {
            throw new RuntimeException("Some parameter to generate Perhitungan Report is empty");
        }
    }

    public String preparePerhitunganInvoiceReport(Registration registration, List<CancelDocumentService> cancelDocumentServices) {
        if (registration != null && registration.getInvoice() != null && cancelDocumentServices != null && !cancelDocumentServices.isEmpty()) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            String username = (String) session.getAttribute("username");
            username = username == null ? "unknown" : username;

            Perhitungan perhitungan = new Perhitungan(registration.getMasterService().getServiceName());
            perhitungan.setTitle("Rincian Perhitungan Cancel Document");
            perhitungan.setReport("/com/pelindo/ebtos/report/Perhitungan.jasper");
            perhitungan.setMasterCurrency(registration.getInvoice().getMasterCurrency());
            perhitungan.setMaterai(registration.getInvoice().getMaterai());
            perhitungan.setPpn(registration.getInvoice().getPpn());
            perhitungan.setRegistration(registration);
            perhitungan.setUsername(username);
            perhitungan.setReportId("Perhitungan_" + registration.getNoReg());
            perhitungan.setNotebene("Catatan : Lampiran ini hanya rincian dari Nota Tagihan, dan tidak dapat digunakan sebagai bukti pembayaran sah atau sebagai faktur pajak.");
            perhitungan.addSubReport("SUBREPORT_DIR", "/com/pelindo/ebtos/report/PerhitunganCancelDocument_subreport.jasper");
            perhitungan.setListDataSource(cancelDocumentServices);

            String key = UUID.randomUUID().toString();
            dataStorageManagerlocal.putData(session.getId(), key, perhitungan);

            return key;
        } else {
            throw new RuntimeException("Some parameter that required to generate Perhitungan Report is empty");
        }
    }

    public String prepareInvoiceReport(Registration registration, List<CancelDocumentService> cancelDocumentServices) {
        try {
            if (registration != null && registration.getInvoice() != null && cancelDocumentServices != null && !cancelDocumentServices.isEmpty()) {
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

                BigDecimal totalAdministration = BigDecimal.ZERO;
                BigDecimal totalUpahBuruhCharge = BigDecimal.ZERO;
                BigDecimal totalPenumpukanCharge = BigDecimal.ZERO;
                BigDecimal totalHandlingCharge = BigDecimal.ZERO;
                BigDecimal totalLiftCharge = BigDecimal.ZERO;
                BigDecimal totalPassGateCharge = BigDecimal.ZERO;

                for (CancelDocumentService cancelDocumentService: cancelDocumentServices) {
                    BigDecimal upahBuruhCharge = cancelDocumentService.getPerhitunganUpahBuruh() == null ? BigDecimal.ZERO : cancelDocumentService.getPerhitunganUpahBuruh().getCharge();
                    BigDecimal penumpukanCharge = cancelDocumentService.getPerhitunganPenumpukan() == null ? BigDecimal.ZERO : cancelDocumentService.getPerhitunganPenumpukan().getTotalCharge();
                    BigDecimal handlingCharge = cancelDocumentService.getPerhitunganHandlingDischarge() == null ? BigDecimal.ZERO : cancelDocumentService.getPerhitunganHandlingDischarge().getCharge();
                    BigDecimal passGateCharge = cancelDocumentService.getPerhitunganPassGate() == null ? BigDecimal.ZERO : cancelDocumentService.getPerhitunganPassGate().getTotalCharge();
                    BigDecimal liftCharge = cancelDocumentService.getPerhitunganLiftService() == null ? BigDecimal.ZERO : cancelDocumentService.getPerhitunganLiftService().getCharge();
                    
                    totalAdministration = totalAdministration.add(cancelDocumentService.getCharge());
                    totalUpahBuruhCharge = totalUpahBuruhCharge.add(upahBuruhCharge);
                    totalPenumpukanCharge = totalPenumpukanCharge.add(penumpukanCharge);
                    totalHandlingCharge = totalHandlingCharge.add(handlingCharge);
                    totalLiftCharge = totalLiftCharge.add(liftCharge);
                    totalPassGateCharge = totalPassGateCharge.add(passGateCharge);
                }

                CancelDocumentChargeDetail chargeDetail = new CancelDocumentChargeDetail();
                chargeDetail.setPpn(registration.getInvoice().getPpn());
                chargeDetail.setUpahBuruh(totalUpahBuruhCharge);
                chargeDetail.setHandlingCharge(totalHandlingCharge);
                chargeDetail.setPenumpukanCharge(totalPenumpukanCharge);
                chargeDetail.setPassGateCharge(totalPassGateCharge);
                chargeDetail.setLiftOffReceivingCharge(totalLiftCharge);
                chargeDetail.setAdministrationCharge(totalAdministration);
                chargeDetail.setMaterai(registration.getInvoice().getMaterai());
                chargeDetail.setJumlahTagihan(registration.getInvoice().getJumlahTagihan());
                chargeDetail.setTotalTagihan(registration.getInvoice().getTotalTagihan());
                chargeDetail.setTerbilang(terbilang);

                Invoice invoice = new Invoice();
                invoice.setChargeDetail(chargeDetail);
                invoice.setCompanyNpwp(npwp.getValueString());
                invoice.setCompanyPpkp(ppkp.getValueString());
                invoice.setCompanyValidPpkpDate(ppkpTmt.getValueDate());
                invoice.setContainersCount(cancelDocumentServices.size());
                invoice.setDirectorOfFinanceId(dirOfFinance.getValueString());
                invoice.setDirectorOfFinanceName(dirOfFinance.getDescription());
                invoice.setRegistration(registration);
                invoice.setUsername(username);
                invoice.setCopyStatus(copyStatus);
                invoice.setReportId("Invoice_" + registration.getInvoice().getNoInvoice());
                invoice.setReport("/com/pelindo/ebtos/report/Invoice.jasper");
                invoice.addSubReport("detailChargeSubreport", "/com/pelindo/ebtos/report/InvoiceCancelDocument_detailChargeSubreport.jasper");
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

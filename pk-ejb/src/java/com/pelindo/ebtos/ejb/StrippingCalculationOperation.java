/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.qtasnim.util.EnglishNumberConverter;
import com.qtasnim.util.IndonesianNumberConverter;
import com.pelindo.ebtos.ejb.facade.local.BatalNotaFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.InvoiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterCommodityFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterPortFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterSettingAppFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningReceivingAllocationFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ReceivingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.StrippingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.local.DataStorageManagerLocal;
import com.pelindo.ebtos.ejb.remote.StrippingCalculationOperationRemote;
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
import com.pelindo.ebtos.model.db.StrippingService;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.report.model.Invoice;
import com.pelindo.ebtos.report.model.Perhitungan;
import com.pelindo.ebtos.report.model.charge.StrippingStuffingChargeDetail;
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
public class StrippingCalculationOperation implements StrippingCalculationOperationRemote {
    @EJB
    private BatalNotaFacadeLocal batalNotaFacadeLocal;
    @EJB
    private InvoiceFacadeLocal invoiceFacadeLocal;
    @EJB
    private DataStorageManagerLocal dataStorageManagerlocal;
    @EJB
    private MasterSettingAppFacadeLocal masterSettingAppFacadeLocal;
    @EJB
    private StrippingServiceFacadeLocal strippingServiceFacadeLocal;
    @EJB
    private ReceivingServiceFacadeLocal receivingServiceFacadeLocal;
    @EJB
    private MasterPortFacadeLocal masterPortFacadeLocal;
    @EJB
    private MasterCommodityFacadeLocal masterCommodityFacadeLocal;
    @EJB
    private PlanningReceivingAllocationFacadeLocal planningReceivingAllocationFacadeLocal;
    @EJB
    private PlanningContReceivingFacadeLocal planningContReceivingFacadeLocal;

    public StrippingService saveStrippingContainers(StrippingService strippingService, PlanningVessel planningVessel, MasterPort dischargePort, MasterPort portOfDelivery) throws GrossCapacityExceedTheLimitsException, TeusCapacityExceedTheLimitsException, NotAllocatedReceivingException, ReceivingAllocationIsNotEnoughException, RegisteredWithSamePpkbContainerException, HasJobSlipAndNotEnteringGateYetException {
        strippingServiceFacadeLocal.create(strippingService);

        if (strippingService.getPerhitunganStripping().getPerhitunganUpahBuruh().getCharge().compareTo(BigDecimal.ZERO) == 0) {
            strippingService.getPerhitunganStripping().setPerhitunganUpahBuruh(null);
        }

        if (strippingService.getPerhitunganStripping().getPerhitunganSewaAlat().getCharge().compareTo(BigDecimal.ZERO) == 0) {
            strippingService.getPerhitunganStripping().setPerhitunganSewaAlat(null);
        }

        String portCode = masterSettingAppFacadeLocal.findImplementedPortCode();
        MasterPort implementedPort = masterPortFacadeLocal.find(portCode);
        MasterCommodity emptyCommodity = masterCommodityFacadeLocal.getEmptyCommodity();

        ReceivingService receivingService = new ReceivingService();
        receivingService.setMasa1((short) 0);
        receivingService.setMasa2((short) 0);
        receivingService.setJobSlip(strippingService.getJobSlip());
        receivingService.setBlNo(strippingService.getBlNo());
        receivingService.setMasterContainerType(strippingService.getMasterContainerType());
        receivingService.setMasterPort1(implementedPort);
        receivingService.setMasterCommodity(emptyCommodity);
        receivingService.setContNo(strippingService.getContNo());
        receivingService.setContSize(strippingService.getContSize());
        receivingService.setContStatus("MTY");
        receivingService.setContGross(strippingService.getContSize() == 20 ? 2000 : 3500);
        receivingService.setCounterPrint(0);
        receivingService.setDg("FALSE");
        receivingService.setDgLabel("FALSE");
        receivingService.setOverSize("FALSE");
        receivingService.setRegistration(strippingService.getRegistration());
        receivingService.setStatusCancelLoading("FALSE");
        receivingService.setIsGenerate("FALSE");
        receivingService.setIsExport("FALSE");
        receivingService.setPlanningVessel(planningVessel);
        receivingService.setValidDate(planningVessel.getCloseRec());
        receivingService.setMasterPort(dischargePort);
        receivingService.setPortOfDelivery(portOfDelivery);

        Object[] receivingStatus = receivingServiceFacadeLocal.findReceivingCapacityStatusByNoPpkb(planningVessel.getNoPpkb());

        Long currentGrt = ((Number) receivingStatus[0]).longValue();
        Long currentContCapacity = ((Number) receivingStatus[1]).longValue();
        /**
        if (currentGrt.doubleValue() + (receivingService.getContGross() / 1000) > planningVessel.getPreserviceVessel().getMasterVessel().getGrt()) {
            throw new GrossCapacityExceedTheLimitsException(planningVessel.getPreserviceVessel().getMasterVessel().getGrt());
        }

        if (currentContCapacity.doubleValue() + receivingService.getContTeus() > planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity()) {
            throw new TeusCapacityExceedTheLimitsException(planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity());
        }
        **/

        List<Object[]> hasJobSlipAndNotEnteringGateYet = receivingServiceFacadeLocal.findNotEnteringGateYetByContNo(receivingService.getContNo());

        if (!hasJobSlipAndNotEnteringGateYet.isEmpty()) {
            throw new HasJobSlipAndNotEnteringGateYetException();
        }

        String grossClass = "E";
        /**
        PlanningReceivingAllocation planningReceivingAllocation = planningReceivingAllocationFacadeLocal.findByAllocationConstraint(
                receivingService.getPlanningVessel().getNoPpkb(),
                receivingService.getContSize(),
                receivingService.getMasterContainerType().getContType(),
                receivingService.getContStatus(),
                receivingService.getOverSize(),
                receivingService.getDg(),
                grossClass,
                receivingService.getMasterPort().getPortCode(),
                receivingService.getIsExport());
        
        if (planningReceivingAllocation == null) {
            throw new NotAllocatedReceivingException();
        }

        if (planningReceivingAllocation.getTotalBooking2() >= planningReceivingAllocation.getAllocated()) {
            throw new ReceivingAllocationIsNotEnoughException();
        }
        **/
        Object[] registeredWithSamePpkbContainer = planningContReceivingFacadeLocal.findPlanningContReceivingByCekExist(receivingService.getContNo(), planningVessel.getNoPpkb());

        if (registeredWithSamePpkbContainer != null) {
            throw new RegisteredWithSamePpkbContainerException();
        }

       // planningReceivingAllocation.setTotalBooking2(planningReceivingAllocation.getTotalBooking2() + 1);

        PlanningContReceiving planningContReceiving = planningContReceivingFacadeLocal.findByNoPpkbAndContNo(planningVessel.getNoPpkb(), receivingService.getContNo());
        
        if (planningContReceiving == null) {
            planningContReceiving = new PlanningContReceiving();
        }
        
        planningContReceiving.setContGross(receivingService.getContGross());
        planningContReceiving.setMasterContainerType(receivingService.getMasterContainerType());
        planningContReceiving.setContSize(receivingService.getContSize());
        planningContReceiving.setContStatus(receivingService.getContStatus());
        planningContReceiving.setOverSize(receivingService.getOverSize());
        planningContReceiving.setDg(receivingService.getDg());
        planningContReceiving.setDgLabel(receivingService.getDgLabel());
        planningContReceiving.setContNo(receivingService.getContNo());
        planningContReceiving.setMlo(receivingService.getMlo());
        //planningContReceiving.setDischPort(receivingService.getMasterPort().getPortCode());
        //planningContReceiving.setLoadPort(receivingService.getMasterPort1().getPortCode());
        planningContReceiving.setMasterCommodity(receivingService.getMasterCommodity());
        planningContReceiving.setSealNo(receivingService.getSealNo());
        planningContReceiving.setPlanningVessel(planningVessel);
        planningContReceiving.setIsExport(receivingService.getIsExport());
        //planningContReceiving.setIdConstrainPlanning(planningReceivingAllocation.getId());
        planningContReceiving.setIsGenerate("FALSE");
        planningContReceiving.setGrossClass(grossClass);
        planningContReceiving.setPosition("01");
        planningContReceiving.setIsCompleted("TRUE");
        planningContReceiving.setPortOfDelivery(receivingService.getPortOfDelivery());
        planningContReceiving.setBlNo(receivingService.getBlNo());
        
        strippingService.setExStrippingLoadContainer(receivingService);

        //planningReceivingAllocationFacadeLocal.edit(planningReceivingAllocation);
        planningContReceivingFacadeLocal.edit(planningContReceiving);
        receivingServiceFacadeLocal.create(receivingService);
        return strippingServiceFacadeLocal.find(strippingService.getJobSlip());
    }

    public void removeStrippingContainer(StrippingService strippingService){
        PlanningContReceiving planningContReceiving = planningContReceivingFacadeLocal.findByNoPpkbAndContNo(strippingService.getExStrippingLoadContainer().getPlanningVessel().getNoPpkb(), strippingService.getContNo());

        PlanningReceivingAllocation newAllocation = planningReceivingAllocationFacadeLocal.findByAllocationConstraint(strippingService.getExStrippingLoadContainer().getPlanningVessel().getNoPpkb(),
                planningContReceiving.getContSize(),
                planningContReceiving.getMasterContainerType().getContType(),
                planningContReceiving.getContStatus(),
                planningContReceiving.getOverSize(),
                planningContReceiving.getDg(),
                planningContReceiving.getGrossClass(),
                planningContReceiving.getDischPort(),
                planningContReceiving.getIsExport());

        newAllocation.setTotalBooking2(newAllocation.getTotalBooking2() - 1);

        planningReceivingAllocationFacadeLocal.edit(newAllocation);
        planningContReceivingFacadeLocal.deleteByNoPpkbAndContNo(strippingService.getExStrippingLoadContainer().getPlanningVessel().getNoPpkb(), strippingService.getContNo());
        strippingServiceFacadeLocal.remove(strippingService);
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

            List<StrippingService> strippingServices = strippingServiceFacadeLocal.findByNoReg(registration.getNoReg());
            for (StrippingService strippingService: strippingServices) {
                removeStrippingContainer(strippingService);
            }
        } else {
            throw new RuntimeException("Some parameter to Cancel Invoice is empty");
        }
    }


    public String preparePerhitunganReport(Registration registration, MasterCurrency masterCurrency, BigDecimal ppn, BigDecimal materai, List<StrippingService> strippingServices) {
        if (registration != null && masterCurrency != null && ppn != null && materai != null && strippingServices != null && !strippingServices.isEmpty()) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            String loggedInUser = (String) session.getAttribute("username");
            loggedInUser = loggedInUser == null ? "unknown" : loggedInUser;

            Perhitungan perhitungan = new Perhitungan(registration.getMasterService().getServiceName());
            perhitungan.setTitle("Rincian Perhitungan Stripping");
            perhitungan.setReport("/com/pelindo/ebtos/report/Perhitungan.jasper");
            perhitungan.setMasterCurrency(masterCurrency);
            perhitungan.setMaterai(materai);
            perhitungan.setPpn(ppn);
            perhitungan.setRegistration(registration);
            perhitungan.setUsername(loggedInUser);
            perhitungan.setReportId("Perhitungan_" + registration.getNoReg());
            perhitungan.addSubReport("SUBREPORT_DIR", "/com/pelindo/ebtos/report/PerhitunganStripping_subreport.jasper");
            perhitungan.setListDataSource(strippingServices);

            String key = UUID.randomUUID().toString();
            dataStorageManagerlocal.putData(session.getId(), key, perhitungan);

            return key;
        } else {
            throw new RuntimeException("Some parameter to generate Perhitungan Report is empty");
        }
               }

    public String preparePerhitunganInvoiceReport(Registration registration, List<StrippingService> strippingServices) {
        if (registration != null && registration.getInvoice() != null && strippingServices != null && !strippingServices.isEmpty()) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            String username = (String) session.getAttribute("username");
            username = username == null ? "unknown" : username;

            Perhitungan perhitungan = new Perhitungan(registration.getMasterService().getServiceName());
            perhitungan.setTitle("Rincian Perhitungan Stripping");
            perhitungan.setReport("/com/pelindo/ebtos/report/Perhitungan.jasper");
            perhitungan.setMasterCurrency(registration.getInvoice().getMasterCurrency());
            perhitungan.setMaterai(registration.getInvoice().getMaterai());
            perhitungan.setPpn(registration.getInvoice().getPpn());
            perhitungan.setRegistration(registration);
            perhitungan.setUsername(username);
            perhitungan.setReportId("Perhitungan_" + registration.getNoReg());
            perhitungan.setNotebene("Catatan : Lampiran ini hanya rincian dari Nota Tagihan, dan tidak dapat digunakan sebagai bukti pembayaran sah atau sebagai faktur pajak.");
            perhitungan.addSubReport("SUBREPORT_DIR", "/com/pelindo/ebtos/report/PerhitunganStripping_subreport.jasper");
            perhitungan.setListDataSource(strippingServices);

            String key = UUID.randomUUID().toString();
            dataStorageManagerlocal.putData(session.getId(), key, perhitungan);

            return key;
        } else {
            throw new RuntimeException("Some parameter that required to generate Perhitungan Report is empty");
        }
    }

    public String prepareInvoiceReport(Registration registration, List<StrippingService> strippingServices) {
        try {
            if (registration != null && registration.getInvoice() != null && strippingServices != null && !strippingServices.isEmpty()) {
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

                BigDecimal totalStripping = BigDecimal.ZERO;
                BigDecimal totalSewaAlat = BigDecimal.ZERO;
                BigDecimal upahBuruh = BigDecimal.ZERO;

                for (StrippingService strippingService: strippingServices) {
                    totalStripping = totalStripping.add(strippingService.getPerhitunganStripping().getCharge());
                }

                StrippingStuffingChargeDetail chargeDetail = new StrippingStuffingChargeDetail();
                chargeDetail.setPpn(registration.getInvoice().getPpn());
                chargeDetail.setUpahBuruh(upahBuruh);
                chargeDetail.setMaterai(registration.getInvoice().getMaterai());
                chargeDetail.setJumlahTagihan(registration.getInvoice().getJumlahTagihan());
                chargeDetail.setTotalTagihan(registration.getInvoice().getTotalTagihan());
                chargeDetail.setStrippingStuffing(totalStripping);
                chargeDetail.setSewaAlat(totalSewaAlat);
                chargeDetail.setTerbilang(terbilang);

                Invoice invoice = new Invoice();
                invoice.setChargeDetail(chargeDetail);
                invoice.setCompanyNpwp(npwp.getValueString());
                invoice.setCompanyPpkp(ppkp.getValueString());
                invoice.setCompanyValidPpkpDate(ppkpTmt.getValueDate());
                invoice.setContainersCount(strippingServices.size());
                invoice.setDirectorOfFinanceId(dirOfFinance.getValueString());
                invoice.setDirectorOfFinanceName(dirOfFinance.getDescription());
                invoice.setRegistration(registration);
                invoice.setUsername(username);
                invoice.setCopyStatus(copyStatus);
                invoice.setReportId("Invoice_" + registration.getInvoice().getNoInvoice());
                invoice.setReport("/com/pelindo/ebtos/report/Invoice.jasper");
                invoice.addSubReport("detailChargeSubreport", "/com/pelindo/ebtos/report/InvoiceStripping_detailChargeSubreport.jasper");
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.qtasnim.util.EnglishNumberConverter;
import com.qtasnim.util.IndonesianNumberConverter;
import com.pelindo.ebtos.ejb.facade.local.BatalNotaFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.DischargeToLoadOperationRemote;
import com.pelindo.ebtos.ejb.facade.local.DischargeToLoadServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.InvoiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterPortFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterSettingAppFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterYardCoordinatFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningReceivingAllocationFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ReceivingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceContDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.local.DataStorageManagerLocal;
import com.pelindo.ebtos.exception.GrossCapacityExceedTheLimitsException;
import com.pelindo.ebtos.exception.HasJobSlipAndNotEnteringGateYetException;
import com.pelindo.ebtos.exception.NotAllocatedReceivingException;
import com.pelindo.ebtos.exception.ReceivingAllocationIsNotEnoughException;
import com.pelindo.ebtos.exception.RegisteredWithSamePpkbContainerException;
import com.pelindo.ebtos.exception.TeusCapacityExceedTheLimitsException;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.DischargeToLoadService;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PlanningReceivingAllocation;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.RinciContBatalNota;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import com.pelindo.ebtos.report.model.Invoice;
import com.pelindo.ebtos.report.model.Perhitungan;
import com.pelindo.ebtos.report.model.charge.DischargeToLoadCalculationChargeDetail;
import com.pelindo.ebtos.util.GrossConverter;
import java.math.BigDecimal;
import java.util.Date;
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
public class DischargeToLoadOperation implements DischargeToLoadOperationRemote {
    @EJB
    private DischargeToLoadServiceFacadeLocal dischargeToLoadServiceFacadeLocal;
    @EJB
    private MasterSettingAppFacadeLocal masterSettingAppFacadeLocal;
    @EJB
    private DataStorageManagerLocal dataStorageManagerlocal;
    @EJB
    private BatalNotaFacadeLocal batalNotaFacadeLocal;
    @EJB
    private InvoiceFacadeLocal invoiceFacadeLocal;
    @EJB
    private MasterPortFacadeLocal masterPortFacadeLocal;
    @EJB
    private ReceivingServiceFacadeLocal receivingServiceFacadeLocal;
    @EJB
    private PlanningReceivingAllocationFacadeLocal planningReceivingAllocationFacadeLocal;
    @EJB
    private PlanningContReceivingFacadeLocal planningContReceivingFacadeLocal;
    @EJB
    private PlanningContLoadFacadeLocal planningContLoadFacadeLocal;
    @EJB
    private ServiceContDischargeFacadeLocal serviceContDischargeFacadeLocal;

    @EJB MasterYardCoordinatFacadeLocal masterYardContainerFacadeLocal;

    public DischargeToLoadService saveDischargeToLoadContainers(DischargeToLoadService dischargeToLoadService, PlanningVessel planningVessel, MasterPort dischargePort, MasterPort portOfDelivery) throws GrossCapacityExceedTheLimitsException, TeusCapacityExceedTheLimitsException, NotAllocatedReceivingException, ReceivingAllocationIsNotEnoughException, RegisteredWithSamePpkbContainerException, HasJobSlipAndNotEnteringGateYetException {
        Date now = new Date();
        ServiceContDischarge serviceContDischarge = dischargeToLoadService.getServiceContDischarge();

        if (dischargeToLoadService.getPerhitunganDischargeToLoad().getTotalCharge().compareTo(BigDecimal.ZERO) == 0) {
            dischargeToLoadService.setPerhitunganDischargeToLoad(null);
        }
        
        if (dischargeToLoadService.getPerhitunganPenumpukan().getTotalCharge().compareTo(BigDecimal.ZERO) == 0) {
            dischargeToLoadService.setPerhitunganPenumpukan(null);
        }

        String portCode = masterSettingAppFacadeLocal.findImplementedPortCode();
        MasterPort implementedPort = masterPortFacadeLocal.find(portCode);

        ReceivingService receivingService = new ReceivingService();
        receivingService.setMasa1((short) 0);
        receivingService.setMasa2((short) 0);
        receivingService.setJobSlip(dischargeToLoadService.getJobSlip());
        receivingService.setBlNo(serviceContDischarge.getBlNo());
        receivingService.setMasterContainerType(serviceContDischarge.getMasterContainerType());
        receivingService.setMasterPort1(implementedPort);
        receivingService.setMasterCommodity(serviceContDischarge.getMasterCommodity());
        receivingService.setContNo(serviceContDischarge.getContNo());
        receivingService.setContSize(serviceContDischarge.getContSize());
        receivingService.setContStatus(serviceContDischarge.getContStatus());
        receivingService.setContGross(serviceContDischarge.getContGross());
        receivingService.setCounterPrint(1);
        receivingService.setDg(serviceContDischarge.getDangerous());
        receivingService.setDgLabel(serviceContDischarge.getDgLabel());
        receivingService.setOverSize(serviceContDischarge.getOverSize());
        receivingService.setRegistration(dischargeToLoadService.getRegistration());
        receivingService.setStatusCancelLoading("FALSE");
        receivingService.setIsGenerate("FALSE");
        receivingService.setIsExport(serviceContDischarge.getIsImport());
        receivingService.setPlanningVessel(planningVessel);
        receivingService.setValidDate(planningVessel.getCloseRec());
        receivingService.setMasterPort(dischargePort);
        receivingService.setPortOfDelivery(portOfDelivery);

        Object[] receivingStatus = receivingServiceFacadeLocal.findReceivingCapacityStatusByNoPpkb(planningVessel.getNoPpkb());

        Long currentGrt = (Long) receivingStatus[0];
        Long currentContCapacity = (Long) receivingStatus[1];

        if (currentGrt.doubleValue() + (receivingService.getContGross() / 1000) > planningVessel.getPreserviceVessel().getMasterVessel().getGrt()) {
            throw new GrossCapacityExceedTheLimitsException(planningVessel.getPreserviceVessel().getMasterVessel().getGrt());
        }

        if (currentContCapacity.doubleValue() + receivingService.getContTeus() > planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity()) {
            throw new TeusCapacityExceedTheLimitsException(planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity());
        }

        List<Object[]> hasJobSlipAndNotEnteringGateYet = receivingServiceFacadeLocal.findNotEnteringGateYetByContNo(receivingService.getContNo());

        if (!hasJobSlipAndNotEnteringGateYet.isEmpty()) {
            throw new HasJobSlipAndNotEnteringGateYetException();
        }

        String grossClass = GrossConverter.convert(receivingService.getContSize(), receivingService.getContGross());

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

        Object[] registeredWithSamePpkbContainer = planningContReceivingFacadeLocal.findPlanningContReceivingByCekExist(receivingService.getContNo(), planningVessel.getNoPpkb());

        if (registeredWithSamePpkbContainer != null) {
            throw new RegisteredWithSamePpkbContainerException();
        }

        planningReceivingAllocation.setTotalBooking2(planningReceivingAllocation.getTotalBooking2() + 1);

        PlanningContReceiving planningContReceiving = new PlanningContReceiving();
        planningContReceiving.setContGross(receivingService.getContGross());
        planningContReceiving.setMasterContainerType(receivingService.getMasterContainerType());
        planningContReceiving.setContSize(receivingService.getContSize());
        planningContReceiving.setContStatus(receivingService.getContStatus());
        planningContReceiving.setOverSize(receivingService.getOverSize());
        planningContReceiving.setDg(receivingService.getDg());
        planningContReceiving.setDgLabel(receivingService.getDgLabel());
        planningContReceiving.setContNo(receivingService.getContNo());
        planningContReceiving.setMlo(receivingService.getMlo());
        planningContReceiving.setDischPort(receivingService.getMasterPort().getPortCode());
        planningContReceiving.setLoadPort(receivingService.getMasterPort1().getPortCode());
        planningContReceiving.setMasterCommodity(receivingService.getMasterCommodity());
        planningContReceiving.setSealNo(receivingService.getSealNo());
        planningContReceiving.setPlanningVessel(planningVessel);
        planningContReceiving.setIsExport(receivingService.getIsExport());
        planningContReceiving.setIdConstrainPlanning(planningReceivingAllocation.getId());
        planningContReceiving.setIsGenerate("FALSE");
        planningContReceiving.setGrossClass(grossClass);
        planningContReceiving.setPosition("02");
        planningContReceiving.setIsCompleted("TRUE");
        planningContReceiving.setPortOfDelivery(receivingService.getPortOfDelivery());
        planningContReceiving.setBlNo(receivingService.getBlNo());

        ServiceReceiving serviceReceiving = new ServiceReceiving();
        serviceReceiving.setContNo(planningContReceiving.getContNo());
        serviceReceiving.setMlo(planningContReceiving.getMlo());
        serviceReceiving.setContGross(planningContReceiving.getContGross());
        serviceReceiving.setContSize(planningContReceiving.getContSize());
        serviceReceiving.setContStatus(planningContReceiving.getContStatus());
        serviceReceiving.setDangerous(planningContReceiving.getDg());
        serviceReceiving.setDgLabel(planningContReceiving.getDgLabel());
        serviceReceiving.setMasterCommodity(planningContReceiving.getMasterCommodity());
        serviceReceiving.setMasterContainerType(planningContReceiving.getMasterContainerType());
        serviceReceiving.setTwentyOneFeet(planningContReceiving.getTwentyOneFeet());
        serviceReceiving.setPlanningVessel(planningContReceiving.getPlanningVessel());
        serviceReceiving.setOverSize(planningContReceiving.getOverSize());
        serviceReceiving.setSealNo(planningContReceiving.getSealNo());
        serviceReceiving.setGrossClass(planningContReceiving.getGrossClass());
        serviceReceiving.setTransactionDate(now);
        serviceReceiving.setIsBehandle("FALSE");
        serviceReceiving.setIsCfs("FALSE");
        serviceReceiving.setIsInspection("FALSE");
        serviceReceiving.setBlNo(planningContReceiving.getBlNo());
        serviceReceiving.setPortOfDelivery(planningContReceiving.getPortOfDelivery());
        serviceReceiving.setChangeStatus("FALSE");
        serviceReceiving.setIsChangeDestination("FALSE");
        serviceReceiving.setStatusCancelLoading("FALSE");
        serviceReceiving.setIsLifton("FALSE");
        serviceReceiving.setExDischargeToLoad("TRUE");
        serviceReceiving.setMasterYard(serviceContDischarge.getMasterYard());
        serviceReceiving.setYSlot(serviceContDischarge.getYSlot());
        serviceReceiving.setYRow(serviceContDischarge.getYRow());
        serviceReceiving.setYTier(serviceContDischarge.getYTier());

        PlanningContLoad planningContLoad = new PlanningContLoad();
        planningContLoad.setMasterCommodity(serviceReceiving.getMasterCommodity());
        planningContLoad.setMasterYard(serviceReceiving.getMasterYard());
        planningContLoad.setMasterContainerType(serviceReceiving.getMasterContainerType());
        planningContLoad.setPlanningVessel(serviceReceiving.getPlanningVessel());
        planningContLoad.setContNo(serviceReceiving.getContNo());
        planningContLoad.setMlo(serviceReceiving.getMlo());
        planningContLoad.setDischPort(planningContReceiving.getDischPort());
        planningContLoad.setLoadPort(planningContReceiving.getLoadPort());
        planningContLoad.setContSize(serviceReceiving.getContSize());
        planningContLoad.setIsSling(serviceReceiving.getIsSling());
        planningContLoad.setContStatus(serviceReceiving.getContStatus());
        planningContLoad.setIsTranshipment("FALSE");
        planningContLoad.setPosition(PlanningContLoad.LOCATION_CY);
        planningContLoad.setContGross(serviceReceiving.getContGross());
        planningContLoad.setSealNo(serviceReceiving.getSealNo());
        planningContLoad.setOverSize(serviceReceiving.getOverSize());
        planningContLoad.setDgLabel(serviceReceiving.getDgLabel());
        planningContLoad.setDg(serviceReceiving.getDangerous());
        planningContLoad.setIsExportImport(planningContReceiving.getIsExport());
        planningContLoad.setTwentyOneFeet(planningContReceiving.getTwentyOneFeet());
        planningContLoad.setYSlot(serviceReceiving.getYSlot());
        planningContLoad.setYRow(serviceReceiving.getYRow());
        planningContLoad.setYTier(serviceReceiving.getYTier());
        planningContLoad.setCompleted("FALSE");
        planningContLoad.setUnknown("FALSE");
        planningContLoad.setBlNo(serviceReceiving.getBlNo());
        planningContLoad.setPortOfDelivery(serviceReceiving.getPortOfDelivery());

        dischargeToLoadService.setServiceReceiving(serviceReceiving);
        dischargeToLoadService.setPlanningVessel(planningVessel);

        serviceContDischarge.setDischargeToLoad("TRUE");
        serviceContDischarge.setIsDelivery("TRUE");
        serviceContDischarge.setPosition(ServiceContDischarge.LOCATION_OUTSIDE);

        serviceContDischargeFacadeLocal.edit(serviceContDischarge);

        dischargeToLoadService.setServiceContDischarge(serviceContDischarge);

        dischargeToLoadService.setLastNoPpkb(serviceContDischarge.getNoPpkb());
        dischargeToLoadService.setLastPod(serviceContDischarge.getDischPort());

        dischargeToLoadServiceFacadeLocal.create(dischargeToLoadService);
        receivingServiceFacadeLocal.create(receivingService);
        planningContLoadFacadeLocal.create(planningContLoad);
        planningContReceivingFacadeLocal.create(planningContReceiving);
        planningReceivingAllocationFacadeLocal.edit(planningReceivingAllocation);

        List<MasterYardCoordinat> m_yardco = masterYardContainerFacadeLocal.findByContNoAndStatusExist(receivingService.getContNo());
        for (MasterYardCoordinat yard : m_yardco) {
            yard.setNoPpkb(receivingService.getNoPpkb());
            yard.setPod(receivingService.getPortOfDelivery().getPortCode());
            masterYardContainerFacadeLocal.edit(yard);
        }

        return dischargeToLoadServiceFacadeLocal.find(dischargeToLoadService.getJobSlip());
    }

    public void removeDischargeToLoadContainer(DischargeToLoadService dischargeToLoadService) {
        PlanningContReceiving planningContReceiving = planningContReceivingFacadeLocal.findByNoPpkbAndContNo(dischargeToLoadService.getPlanningVessel().getNoPpkb(), dischargeToLoadService.getServiceContDischarge().getContNo());

        PlanningReceivingAllocation newAllocation = planningReceivingAllocationFacadeLocal.findByAllocationConstraint(dischargeToLoadService.getPlanningVessel().getNoPpkb(),
                planningContReceiving.getContSize(),
                planningContReceiving.getMasterContainerType().getContType(),
                planningContReceiving.getContStatus(),
                planningContReceiving.getOverSize(),
                planningContReceiving.getDg(),
                planningContReceiving.getGrossClass(),
                planningContReceiving.getDischPort(),
                planningContReceiving.getIsExport());

        newAllocation.setTotalBooking2(newAllocation.getTotalBooking2() - 1);

        ServiceContDischarge serviceContDischarge = dischargeToLoadService.getServiceContDischarge();

        serviceContDischarge.setDischargeToLoad("FALSE");
        serviceContDischarge.setIsDelivery("FALSE");
        serviceContDischarge.setPosition(ServiceContDischarge.LOCATION_CY);

        List<MasterYardCoordinat> m_yardco = masterYardContainerFacadeLocal.findByContNoAndStatusExist(serviceContDischarge.getContNo());
        for (MasterYardCoordinat yard : m_yardco) {
            yard.setNoPpkb(dischargeToLoadService.getLastNoPpkb());
            yard.setPod(dischargeToLoadService.getLastPod());
            masterYardContainerFacadeLocal.edit(yard);
        }
        
        receivingServiceFacadeLocal.deleteByNoPpkbAndContNoNotCancelLoading(dischargeToLoadService.getPlanningVessel().getNoPpkb(), serviceContDischarge.getContNo());
        planningContLoadFacadeLocal.deleteByNoPpkbAndContNo(dischargeToLoadService.getPlanningVessel().getNoPpkb(), serviceContDischarge.getContNo());
        planningContReceivingFacadeLocal.deleteByNoPpkbAndContNo(dischargeToLoadService.getPlanningVessel().getNoPpkb(), serviceContDischarge.getContNo());
        planningReceivingAllocationFacadeLocal.edit(newAllocation);
        serviceContDischargeFacadeLocal.edit(serviceContDischarge);
        dischargeToLoadServiceFacadeLocal.remove(dischargeToLoadService);
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

            invoice.setNoFakturPajak(null);
            invoice.setNoInvoice(null);
            invoice.setCancelInvoice("FALSE");

            batalNotaFacadeLocal.create(batalNota);
            invoiceFacadeLocal.edit(invoice);

            List<DischargeToLoadService> dischargeToLoadServices = dischargeToLoadServiceFacadeLocal.findByNoReg(registration.getNoReg());

            for (DischargeToLoadService dischargeToLoadService : dischargeToLoadServices) {
                removeDischargeToLoadContainer(dischargeToLoadService);

                List<MasterYardCoordinat> m_yardco = masterYardContainerFacadeLocal.findByContNoAndStatusExist(dischargeToLoadService.getServiceContDischarge().getContNo());
                for (MasterYardCoordinat yard : m_yardco) {
                    yard.setNoPpkb(dischargeToLoadService.getLastNoPpkb());
                    yard.setPod(dischargeToLoadService.getLastPod());
                    masterYardContainerFacadeLocal.edit(yard);
                }
            }
        } else {
            throw new RuntimeException("Some parameter to Cancel Invoice is empty");
        }
    }

    public String preparePerhitunganReport(Registration registration, MasterCurrency masterCurrency, BigDecimal ppn, BigDecimal materai, List<DischargeToLoadService> dischargeToLoadServices) {
        if (registration != null && masterCurrency != null && ppn != null && materai != null && dischargeToLoadServices != null && !dischargeToLoadServices.isEmpty()) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            String loggedInUser = (String) session.getAttribute("username");
            loggedInUser = loggedInUser == null ? "unknown" : loggedInUser;

            Perhitungan perhitungan = new Perhitungan(registration.getMasterService().getServiceName());
            perhitungan.setTitle("Rincian Perhitungan Discharge To Load");
            perhitungan.setReport("/com/pelindo/ebtos/report/Perhitungan.jasper");
            perhitungan.setMasterCurrency(masterCurrency);
            perhitungan.setMaterai(materai);
            perhitungan.setPpn(ppn);
            perhitungan.setRegistration(registration);
            perhitungan.setUsername(loggedInUser);
            perhitungan.setReportId("Perhitungan_" + registration.getNoReg());
            perhitungan.addSubReport("SUBREPORT_DIR", "/com/pelindo/ebtos/report/PerhitunganDischargeToLoad_subreport.jasper");
            perhitungan.setListDataSource(dischargeToLoadServices);

            String key = UUID.randomUUID().toString();
            dataStorageManagerlocal.putData(session.getId(), key, perhitungan);

            return key;
        } else {
            throw new RuntimeException("Some parameter to generate Perhitungan Report is empty");
        }
    }

    public String prepareInvoiceReport(Registration registration, List<DischargeToLoadService> dischargeToLoadServices) {
        try {
            if (registration != null && registration.getInvoice() != null && dischargeToLoadServices != null && !dischargeToLoadServices.isEmpty()) {
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

                BigDecimal totalDischargeToLoadCharge = BigDecimal.ZERO;
                BigDecimal totalPenumpukanCharge = BigDecimal.ZERO;

                for (DischargeToLoadService perhitunganDischargeToLoad: dischargeToLoadServices) {
                    if (perhitunganDischargeToLoad.getPerhitunganDischargeToLoad() != null) {
                        totalDischargeToLoadCharge = totalDischargeToLoadCharge.add(perhitunganDischargeToLoad.getPerhitunganDischargeToLoad().getTotalCharge());
                    }

                    if (perhitunganDischargeToLoad.getPerhitunganPenumpukan() != null) {
                        totalPenumpukanCharge = totalPenumpukanCharge.add(perhitunganDischargeToLoad.getPerhitunganPenumpukan().getTotalCharge());
                    }
                }

                DischargeToLoadCalculationChargeDetail chargeDetail = new DischargeToLoadCalculationChargeDetail();
                chargeDetail.setPpn(registration.getInvoice().getPpn());
                chargeDetail.setMaterai(registration.getInvoice().getMaterai());
                chargeDetail.setJumlahTagihan(registration.getInvoice().getJumlahTagihan());
                chargeDetail.setTotalTagihan(registration.getInvoice().getTotalTagihan());
                chargeDetail.setDischargeToLoadCharge(totalDischargeToLoadCharge);
                chargeDetail.setPenumpukanCharge(totalPenumpukanCharge);
                chargeDetail.setTerbilang(terbilang);

                Invoice invoice = new Invoice();
                invoice.setChargeDetail(chargeDetail);
                invoice.setCompanyNpwp(npwp.getValueString());
                invoice.setCompanyPpkp(ppkp.getValueString());
                invoice.setCompanyValidPpkpDate(ppkpTmt.getValueDate());
                invoice.setContainersCount(dischargeToLoadServices.size());
                invoice.setDirectorOfFinanceId(dirOfFinance.getValueString());
                invoice.setDirectorOfFinanceName(dirOfFinance.getDescription());
                invoice.setRegistration(registration);
                invoice.setUsername(username);
                invoice.setCopyStatus(copyStatus);
                invoice.setReportId("Invoice_" + registration.getInvoice().getNoInvoice());
                invoice.setReport("/com/pelindo/ebtos/report/Invoice.jasper");
                invoice.addSubReport("detailChargeSubreport", "/com/pelindo/ebtos/report/InvoiceDischargeToLoad_detailChargeSubreport.jasper");
                invoice.addSubReport("chargeSummarySubreport", "/com/pelindo/ebtos/report/Invoice_chargeSummarySubreport.jasper");

                String key = UUID.randomUUID().toString();
                dataStorageManagerlocal.putData(session.getId(), key, invoice);

                return key;
            }
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when prepareInvoiceReport", re);
        }

        return null;
    }

    public String preparePerhitunganInvoiceReport(Registration registration, List<DischargeToLoadService> dischargeToLoadServices) {
        if (registration != null && registration.getInvoice() != null && dischargeToLoadServices != null && !dischargeToLoadServices.isEmpty()) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            String username = (String) session.getAttribute("username");
            username = username == null ? "unknown" : username;

            Perhitungan perhitungan = new Perhitungan(registration.getMasterService().getServiceName());
            perhitungan.setTitle("Rincian Perhitungan DischargeToLoad");
            perhitungan.setReport("/com/pelindo/ebtos/report/Perhitungan.jasper");
            perhitungan.setMasterCurrency(registration.getInvoice().getMasterCurrency());
            perhitungan.setMaterai(registration.getInvoice().getMaterai());
            perhitungan.setPpn(registration.getInvoice().getPpn());
            perhitungan.setRegistration(registration);
            perhitungan.setUsername(username);
            perhitungan.setReportId("Perhitungan_" + registration.getNoReg());
            perhitungan.setNotebene("Catatan : Lampiran ini hanya rincian dari Nota Tagihan, dan tidak dapat digunakan sebagai bukti pembayaran sah atau sebagai faktur pajak.");
            perhitungan.addSubReport("SUBREPORT_DIR", "/com/pelindo/ebtos/report/PerhitunganDischargeToLoad_subreport.jasper");
            perhitungan.setListDataSource(dischargeToLoadServices);

            String key = UUID.randomUUID().toString();
            dataStorageManagerlocal.putData(session.getId(), key, perhitungan);

            return key;
        } else {
            throw new RuntimeException("Some parameter that required to generate Perhitungan Report is empty");
        }
    }
}

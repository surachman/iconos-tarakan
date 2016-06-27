/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.pelindo.ebtos.ejb.facade.local.BatalNotaFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.IDGeneratorFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.InvoiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterActivityFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterTarifContFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PerhitunganMonitoringFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PerhitunganPluggingFacadeLocal;
import com.pelindo.ebtos.ejb.remote.ReeferDischargeCalculationOperationRemote;
import com.pelindo.ebtos.ejb.facade.local.ReeferDischargeServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceReeferFacadeLocal;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.PerhitunganMonitoring;
import com.pelindo.ebtos.model.db.PerhitunganPlugging;
import com.pelindo.ebtos.model.db.ReeferDischargeService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceReefer;
import com.pelindo.ebtos.model.db.StrippingService;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author R. Seno Anggoro A
 */
@Stateless
public class ReeferDischargeCalculationOperation implements ReeferDischargeCalculationOperationRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @EJB
    private ReeferDischargeServiceFacadeLocal reeferDischargeServiceFacadeLocal;
    @EJB
    private PerhitunganMonitoringFacadeLocal perhitunganMonitoringFacadeLocal;
    @EJB
    private PerhitunganPluggingFacadeLocal perhitunganPluggingFacadeLocal;
    @EJB
    private ServiceReeferFacadeLocal serviceReeferFacadeLocal;
    @EJB
    private IDGeneratorFacadeLocal iDGeneratorFacadeLocal;
    @EJB
    private MasterActivityFacadeLocal masterActivityFacadeLocal;
    @EJB
    private MasterTarifContFacadeLocal masterTarifContFacadeLocal;
    @EJB
    private BatalNotaFacadeLocal batalNotaFacadeLocal;
    @EJB
    private InvoiceFacadeLocal invoiceFacadeLocal;

    public void handleAddReeferDischarge(ReeferDischargeService reeferDischargeService, MasterCurrency masterCurrency) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reeferDischargeService.getPlugOnDate());
        calendar.add(Calendar.HOUR, reeferDischargeService.getShiftPlanning() * 8);
        Date validDate = calendar.getTime();
        reeferDischargeService.setValidDate(validDate);

        if (reeferDischargeService.getJobSlip() == null) {
            reeferDischargeService.setJobSlip(iDGeneratorFacadeLocal.generateJobSlipID("15"));
        }

        String pluggingActivityCode = masterActivityFacadeLocal.translatePluggingActivityCode(reeferDischargeService.getContSize());
        String monitoringActivityCode = masterActivityFacadeLocal.translateMonitoringActivityCode(reeferDischargeService.getContSize());
        MasterActivity pluggingMasterActivity = masterActivityFacadeLocal.find(pluggingActivityCode);
        MasterActivity monitoringMasterActivity = masterActivityFacadeLocal.find(monitoringActivityCode);
        BigDecimal pluggingCharge = masterTarifContFacadeLocal.findChargeAfterDiscount(masterCurrency.getCurrCode(), pluggingActivityCode, reeferDischargeService.getRegistration().getMasterCustomer().getCustCode());
        BigDecimal monitoringCharge = masterTarifContFacadeLocal.findChargeAfterDiscount(masterCurrency.getCurrCode(), monitoringActivityCode, reeferDischargeService.getRegistration().getMasterCustomer().getCustCode());

        PerhitunganPlugging perhitunganPlugging = new PerhitunganPlugging();
        perhitunganPlugging.setMasterActivity(pluggingMasterActivity);
        perhitunganPlugging.setRegistration(reeferDischargeService.getRegistration());
        perhitunganPlugging.setContNo(reeferDischargeService.getContNo());
        perhitunganPlugging.setMlo(reeferDischargeService.getMlo());
        perhitunganPlugging.setNoPpkb(reeferDischargeService.getRegistration().getPlanningVessel().getNoPpkb());
        perhitunganPlugging.setCurrCode(masterCurrency.getCurrCode());
        perhitunganPlugging.setCharge(pluggingCharge);
        perhitunganPlugging.setTotalCharge(perhitunganPlugging.getCharge().multiply(BigDecimal.valueOf(reeferDischargeService.getShiftPlanning())));

        PerhitunganMonitoring perhitunganMonitoring = new PerhitunganMonitoring();
        perhitunganMonitoring.setRegistration(reeferDischargeService.getRegistration());
        perhitunganMonitoring.setContNo(reeferDischargeService.getContNo());
        perhitunganMonitoring.setMlo(reeferDischargeService.getMlo());
        perhitunganMonitoring.setNoPpkb(reeferDischargeService.getRegistration().getPlanningVessel().getNoPpkb());
        perhitunganMonitoring.setCurrCode(masterCurrency.getCurrCode());
        perhitunganMonitoring.setMasterActivity(monitoringMasterActivity);
        perhitunganMonitoring.setCharge(monitoringCharge);
        perhitunganMonitoring.setTotalCharge(perhitunganMonitoring.getCharge().multiply(BigDecimal.valueOf(reeferDischargeService.getShiftPlanning())));

        reeferDischargeServiceFacadeLocal.edit(reeferDischargeService);
        perhitunganPluggingFacadeLocal.edit(perhitunganPlugging);
        perhitunganMonitoringFacadeLocal.edit(perhitunganMonitoring);
    }
    
    public void handleDeleteDischargeReefer(String contNo, String noReg) {
        ReeferDischargeService reeferDischargeService = reeferDischargeServiceFacadeLocal.findByContNoAndNoReg(contNo, noReg);
        handleDeleteDischargeReefer(reeferDischargeService);
    }

    private void handleDeleteDischargeReefer(ReeferDischargeService reeferDischargeService) {
        PerhitunganMonitoring perhitunganMonitoring = perhitunganMonitoringFacadeLocal.findByContNoAndNoReg(reeferDischargeService.getContNo(), reeferDischargeService.getRegistration().getNoReg());
        PerhitunganPlugging perhitunganPlugging = perhitunganPluggingFacadeLocal.findByContNoAndNoReg(reeferDischargeService.getContNo(), reeferDischargeService.getRegistration().getNoReg());

        reeferDischargeServiceFacadeLocal.remove(reeferDischargeService);
        perhitunganMonitoringFacadeLocal.remove(perhitunganMonitoring);
        perhitunganPluggingFacadeLocal.remove(perhitunganPlugging);
        em.flush();

        ReeferDischargeService lastReeferDischarge = reeferDischargeServiceFacadeLocal.findLastValidReeferByNoPpkbAndContNo(reeferDischargeService.getPlanningVessel().getNoPpkb(), reeferDischargeService.getContNo());

        if (lastReeferDischarge != null) {
            ServiceReefer serviceReefer = serviceReeferFacadeLocal.findByContNoPpkbAndOperation(reeferDischargeService.getContNo(), reeferDischargeService.getPlanningVessel().getNoPpkb(), "DISCHARGE");
            serviceReefer.setPlugOn(lastReeferDischarge.getPlugOnDate());
            serviceReeferFacadeLocal.edit(serviceReefer);
        }
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

            List<ReeferDischargeService> reeferDischargeServices = reeferDischargeServiceFacadeLocal.findByNoReg(registration.getNoReg());

            for (ReeferDischargeService reeferDischargeService: reeferDischargeServices) {
                handleDeleteDischargeReefer(reeferDischargeService);
            }
        } else {
            throw new RuntimeException("Some parameter to Cancel Invoice is empty");
        }
    }
}

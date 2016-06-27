/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.qtasnim.jreport.data.QTBeanCollectionDataSource;
import com.qtasnim.util.EnglishNumberConverter;
import com.qtasnim.util.IndonesianNumberConverter;
import com.pelindo.ebtos.ejb.facade.local.BatalNotaFacadeLocal;
import com.pelindo.ebtos.ejb.remote.ChangeVesselOperationRemote;
import com.pelindo.ebtos.ejb.facade.local.ChangeVesselServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.EquipmentFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.InvoiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterSettingAppFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PerhitunganHandlingDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PerhitunganLiftServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PerhitunganPassGateFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PerhitunganPenumpukanFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningReceivingAllocationFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningVesselFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PreserviceVesselFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ReceivingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.RegistrationFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceGateReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.local.ChangeVesselOperationLocal;
import com.pelindo.ebtos.ejb.local.DataStorageManagerLocal;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.ChangeVesselService;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.report.model.Invoice;
import com.pelindo.ebtos.report.model.Perhitungan;
import com.pelindo.ebtos.report.model.charge.BatalKapalChargeDetail;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JREmptyDataSource;

/**
 *
 * @author R. Seno Anggoro A
 */
@Stateless
public class ChangeVesselOperation implements ChangeVesselOperationRemote, ChangeVesselOperationLocal {
    @EJB
    private PlanningVesselFacadeLocal planningVesselFacadeLocal;
    @EJB
    private PreserviceVesselFacadeLocal preserviceVesselFacadeLocal;
    @EJB
    private ChangeVesselServiceFacadeLocal changeVesselServiceFacadeLocal;
    @EJB
    private DataStorageManagerLocal dataStorageManagerlocal;
    @EJB
    private MasterSettingAppFacadeLocal masterSettingAppFacadeLocal;
    @EJB
    private PlanningReceivingAllocationFacadeLocal planningReceivingAllocationFacadeLocal;
    @EJB
    private RegistrationFacadeLocal registrationFacadeLocal;
    @EJB
    private ReceivingServiceFacadeLocal receivingServiceFacadeLocal;
    @EJB
    private PlanningContReceivingFacadeLocal planningContReceivingFacadeLocal;
    @EJB
    private PerhitunganHandlingDischargeFacadeLocal perhitunganHandlingDischargeFacadeLocal;
    @EJB
    private PerhitunganPenumpukanFacadeLocal perhitunganPenumpukanFacadeLocal;
    @EJB
    private PerhitunganLiftServiceFacadeLocal perhitunganLiftServiceFacadeLocal;
    @EJB
    private PerhitunganPassGateFacadeLocal perhitunganPassGateFacadeLocal;
    @EJB
    private ServiceGateReceivingFacadeLocal serviceGateReceivingFacadeLocal;
    @EJB
    private ServiceReceivingFacadeLocal serviceReceivingFacadeLocal;
    @EJB
    private PlanningContLoadFacadeLocal planningContLoadFacadeLocal;
    @EJB
    private EquipmentFacadeLocal equipmentFacadeLocal;
    @EJB
    private BatalNotaFacadeLocal batalNotaFacadeLocal;
    @EJB
    private InvoiceFacadeLocal invoiceFacadeLocal;

    public void submitChangeVessel(Registration registration, List<ChangeVesselService> containers) {
        for (ChangeVesselService container: containers) {
            changeVesselServiceFacadeLocal.create(container);
        }

        PlanningVessel planningVessel = registration.getPlanningVessel();
        PreserviceVessel preserviceVessel = planningVessel.getPreserviceVessel();
        
        preserviceVessel.setStatus("Canceled");
        preserviceVesselFacadeLocal.edit(preserviceVessel);

        planningVessel.setStatus("Canceled");
        planningVesselFacadeLocal.edit(planningVessel);
    }

    public void cancelInvoice(Registration registration, BatalNota batalNota, PlanningVessel oldPlanningVessel) {
        if (registration != null && registration.getInvoice() != null && batalNota != null && batalNota.getAlasanPembatalan() != null && batalNota.getNoBeritaAcara() != null && batalNota.getTglPembatalan() != null) {
            PlanningVessel planningVessel = registration.getPlanningVessel();
            PreserviceVessel preserviceVessel = planningVessel.getPreserviceVessel();
            com.pelindo.ebtos.model.db.Invoice invoice = registration.getInvoice();

            preserviceVessel.setStatus("Confirm");
            planningVessel.setStatus("Approved");

            batalNota.setCustCode(registration.getMasterCustomer().getCustCode());
            batalNota.setNoFakturPajak(invoice.getNoFakturPajak());
            batalNota.setNoInvoice(invoice.getNoInvoice());
            batalNota.setNoReg(invoice.getNoReg());
            batalNota.setPaymentDate(invoice.getPaymentDate());
            batalNota.setTotalTagihan(invoice.getTotalTagihan());

            invoice.setNoFakturPajak(null);
            invoice.setCancelInvoice("TRUE");

            handleUpdateVessel(planningVessel, oldPlanningVessel);
            batalNotaFacadeLocal.create(batalNota);
            invoiceFacadeLocal.edit(invoice);
            changeVesselServiceFacadeLocal.deleteByNoPpkb(planningVessel.getNoPpkb());
            preserviceVesselFacadeLocal.edit(preserviceVessel);
            planningVesselFacadeLocal.edit(planningVessel);
        } else {
            throw new RuntimeException("Some parameter to Cancel Invoice is empty");
        }
    }

    public void handleUpdateVessel(PlanningVessel newValue, PlanningVessel oldValue) {
        if (newValue != null && oldValue != null) {
            int result = planningReceivingAllocationFacadeLocal.updatePlanningVessel(newValue, oldValue);
            result = registrationFacadeLocal.updatePlanningVesselExceptCancelVessel(newValue, oldValue);
            result = receivingServiceFacadeLocal.updatePlanningVessel(newValue, oldValue);
            result = planningContReceivingFacadeLocal.updatePlanningVessel(newValue, oldValue);
            result = perhitunganHandlingDischargeFacadeLocal.updateNoPpkb(newValue.getNoPpkb(), oldValue.getNoPpkb());
            result = serviceGateReceivingFacadeLocal.updateNoPpkb(newValue.getNoPpkb(), oldValue.getNoPpkb());
            result = perhitunganPenumpukanFacadeLocal.updateNoPpkb(newValue.getNoPpkb(), oldValue.getNoPpkb());
            result = perhitunganLiftServiceFacadeLocal.updateNoPpkb(newValue.getNoPpkb(), oldValue.getNoPpkb());
            result = perhitunganPassGateFacadeLocal.updatePlanningVessel(newValue, oldValue);
            result = serviceReceivingFacadeLocal.updatePlanningVessel(newValue, oldValue);
            result = planningContLoadFacadeLocal.updatePlanningVessel(newValue, oldValue);
            result = equipmentFacadeLocal.updatePlanningVessel(newValue, oldValue);
        } else {
            throw new RuntimeException("Some parameter to Update Vessel for Change Vessel Transaction");
        }
    }

    public String preparePerhitunganReport(Registration registration, MasterCurrency masterCurrency, BigDecimal ppn, BigDecimal materai, List<ChangeVesselService> changeVesselServices) {
        try {
            if (registration != null && masterCurrency != null && ppn != null && materai != null && changeVesselServices != null && !changeVesselServices.isEmpty()) {
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                String loggedInUser = (String) session.getAttribute("username");
                loggedInUser = loggedInUser == null ? "unknown" : loggedInUser;

                Perhitungan perhitungan = new Perhitungan(registration.getMasterService().getServiceName());
                perhitungan.setTitle("Rincian Perhitungan Batal Kapal");
                perhitungan.setReport("/com/pelindo/ebtos/report/Perhitungan.jasper");
                perhitungan.setMasterCurrency(masterCurrency);
                perhitungan.setMaterai(materai);
                perhitungan.setPpn(ppn);
                perhitungan.setRegistration(registration);
                perhitungan.setUsername(loggedInUser);
                perhitungan.setReportId("Perhitungan_" + registration.getNoReg());
                perhitungan.addSubReport("SUBREPORT_DIR", "/com/pelindo/ebtos/report/PerhitunganBatalKapal_subreport.jasper");
                perhitungan.setListDataSource(changeVesselServices);

                String key = UUID.randomUUID().toString();
                dataStorageManagerlocal.putData(session.getId(), key, perhitungan);

                return key;
            } else {
                throw new RuntimeException("Some parameter to generate Perhitungan Report is empty");
            }
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when preparePerhitunganReport", re);
        }

        return null;
    }

    public String preparePerhitunganInvoiceReport(Registration registration, List<ChangeVesselService> changeVesselServices) {
        try {
            if (registration != null && registration.getInvoice() != null && changeVesselServices != null && !changeVesselServices.isEmpty()) {
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                String username = (String) session.getAttribute("username");
                username = username == null ? "unknown" : username;

                Perhitungan perhitungan = new Perhitungan(registration.getMasterService().getServiceName());
                perhitungan.setTitle("Rincian Perhitungan Batal Kapal");
                perhitungan.setReport("/com/pelindo/ebtos/report/Perhitungan.jasper");
                perhitungan.setMasterCurrency(registration.getInvoice().getMasterCurrency());
                perhitungan.setMaterai(registration.getInvoice().getMaterai());
                perhitungan.setPpn(registration.getInvoice().getPpn());
                perhitungan.setRegistration(registration);
                perhitungan.setUsername(username);
                perhitungan.setReportId("Perhitungan_" + registration.getNoReg());
                perhitungan.setNotebene("Catatan : Lampiran ini hanya rincian dari Nota Tagihan, dan tidak dapat digunakan sebagai bukti pembayaran sah atau sebagai faktur pajak.");
                perhitungan.addSubReport("SUBREPORT_DIR", "/com/pelindo/ebtos/report/PerhitunganBatalKapal_subreport.jasper");
                perhitungan.setListDataSource(changeVesselServices);

                String key = UUID.randomUUID().toString();
                dataStorageManagerlocal.putData(session.getId(), key, perhitungan);

                return key;
            } else {
                throw new RuntimeException("Some parameter that required to generate Perhitungan Report is empty");
            }
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when preparePerhitunganReport", re);
        }

        return null;
    }

    public String prepareInvoiceReport(Registration registration, List<ChangeVesselService> changeVesselServices) {
        try {
            if (registration != null && registration.getInvoice() != null && changeVesselServices != null && !changeVesselServices.isEmpty()) {
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

                BigDecimal total = BigDecimal.ZERO;

                for (ChangeVesselService changeVesselService: changeVesselServices) {
                    total = total.add(changeVesselService.getCharge());
                }

                BatalKapalChargeDetail chargeDetail = new BatalKapalChargeDetail();
                chargeDetail.setPpn(registration.getInvoice().getPpn());
                chargeDetail.setMaterai(registration.getInvoice().getMaterai());
                chargeDetail.setJumlahTagihan(registration.getInvoice().getJumlahTagihan());
                chargeDetail.setTotalTagihan(registration.getInvoice().getTotalTagihan());
                chargeDetail.setBatalKapalCharge(total);
                chargeDetail.setTerbilang(terbilang);

                Invoice invoice = new Invoice();
                invoice.setChargeDetail(chargeDetail);
                invoice.setCompanyNpwp(npwp.getValueString());
                invoice.setCompanyPpkp(ppkp.getValueString());
                invoice.setCompanyValidPpkpDate(ppkpTmt.getValueDate());
                invoice.setContainersCount(changeVesselServices.size());
                invoice.setDirectorOfFinanceId(dirOfFinance.getValueString());
                invoice.setDirectorOfFinanceName(dirOfFinance.getDescription());
                invoice.setRegistration(registration);
                invoice.setUsername(username);
                invoice.setCopyStatus(copyStatus);
                invoice.setReportId("Invoice_" + registration.getInvoice().getNoInvoice());
                invoice.setReport("/com/pelindo/ebtos/report/Invoice.jasper");
                invoice.addSubReport("detailChargeSubreport", "/com/pelindo/ebtos/report/InvoiceBatalKapal_detailChargeSubreport.jasper");
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
}

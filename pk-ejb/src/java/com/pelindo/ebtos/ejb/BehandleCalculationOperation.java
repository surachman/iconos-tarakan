/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.qtasnim.util.EnglishNumberConverter;
import com.qtasnim.util.IndonesianNumberConverter;
import com.pelindo.ebtos.ejb.facade.local.BatalNotaFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.BehandleServiceFacadeLocal;
import com.pelindo.ebtos.ejb.remote.BehandleCalculationOperationRemote;
import com.pelindo.ebtos.ejb.facade.local.IDGeneratorFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.InvoiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterActivityFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterSettingAppFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterTarifContFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PerhitunganBehandleFacadeLocal;
import com.pelindo.ebtos.ejb.local.DataStorageManagerLocal;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.BehandleService;
import com.qtasnim.jreport.data.QTBeanCollectionDataSource;
import com.pelindo.ebtos.model.db.PerhitunganBehandle;
import com.pelindo.ebtos.model.db.PerhitunganStripping;
import com.pelindo.ebtos.model.db.PerhitunganStuffing;
import com.pelindo.ebtos.model.db.PerhitunganSupervisi;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.report.model.Invoice;
import com.pelindo.ebtos.report.model.Perhitungan;
import com.pelindo.ebtos.report.model.charge.BehandleCalculationChargeDetail;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JREmptyDataSource;

/**
 *
 * @author R. Seno Anggoro A
 */
@Stateless
@LocalBean
public class BehandleCalculationOperation implements BehandleCalculationOperationRemote{
    @EJB
    private DataStorageManagerLocal dataStorageManagerlocal;
    @EJB
    private IDGeneratorFacadeLocal iDGeneratorFacadeLocal;
    @EJB
    private MasterActivityFacadeLocal masterActivityFacadeLocal;
    @EJB
    private MasterTarifContFacadeLocal masterTarifContFacadeLocal;
    @EJB
    private PerhitunganBehandleFacadeLocal perhitunganBehandleFacadeLocal;
    @EJB
    private BatalNotaFacadeLocal batalNotaFacadeLocal;
    @EJB
    private InvoiceFacadeLocal invoiceFacadeLocal;
    @EJB
    private MasterSettingAppFacadeLocal masterSettingAppFacadeLocal;
    @EJB
    private BehandleServiceFacadeLocal behandleServiceFacadeLocal;

    public PerhitunganBehandle saveBehandleContainers(PerhitunganBehandle perhitunganBehandle) {
        perhitunganBehandleFacadeLocal.create(perhitunganBehandle);

        if (perhitunganBehandle.getPerhitunganUpahBuruh().getCharge().compareTo(BigDecimal.ZERO) == 0) {
            perhitunganBehandle.setPerhitunganUpahBuruh(null);
        }

        if (perhitunganBehandle.getPerhitunganSewaAlat().getCharge().compareTo(BigDecimal.ZERO) == 0) {
            perhitunganBehandle.setPerhitunganSewaAlat(null);
        }

        return perhitunganBehandle;
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
            invoice.setCancelInvoice("TRUE");

            batalNotaFacadeLocal.create(batalNota);
            invoiceFacadeLocal.edit(invoice);
            perhitunganBehandleFacadeLocal.deleteByNoReg(registration.getNoReg());
        } else {
            throw new RuntimeException("Some parameter to Cancel Invoice is empty");
        }
    }
    
    public String preparePerhitunganReport(Registration registration, MasterCurrency masterCurrency, BigDecimal ppn, BigDecimal materai, List<PerhitunganBehandle> perhitunganBehandles) {
        if (registration != null && masterCurrency != null && ppn != null && materai != null && perhitunganBehandles != null && !perhitunganBehandles.isEmpty()) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            String loggedInUser = (String) session.getAttribute("username");
            loggedInUser = loggedInUser == null ? "unknown" : loggedInUser;

            Perhitungan perhitungan = new Perhitungan(registration.getMasterService().getServiceName());
            perhitungan.setTitle("Rincian Perhitungan Behandle");
            perhitungan.setReport("/com/pelindo/ebtos/report/Perhitungan.jasper");
            perhitungan.setMasterCurrency(masterCurrency);
            perhitungan.setMaterai(materai);
            perhitungan.setPpn(ppn);
            perhitungan.setRegistration(registration);
            perhitungan.setUsername(loggedInUser);
            perhitungan.setReportId("Perhitungan_" + registration.getNoReg());
            perhitungan.addSubReport("SUBREPORT_DIR", "/com/pelindo/ebtos/report/PerhitunganBehandle_subreport.jasper");
            perhitungan.setListDataSource(perhitunganBehandles);

            String key = UUID.randomUUID().toString();
            dataStorageManagerlocal.putData(session.getId(), key, perhitungan);

            return key;
        } else {
            throw new RuntimeException("Some parameter to generate Perhitungan Report is empty");
        }
    }

    public String prepareInvoiceReport(Registration registration, List<PerhitunganBehandle> perhitunganBehandles) {
        try {
            if (registration != null && registration.getInvoice() != null && perhitunganBehandles != null && !perhitunganBehandles.isEmpty()) {
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

                BigDecimal totalbehandleCharge = BigDecimal.ZERO;
                BigDecimal totalSewaAlatCharge = BigDecimal.ZERO;
                BigDecimal totalUpahBuruhCharge = BigDecimal.ZERO;

                for (PerhitunganBehandle perhitunganBehandle: perhitunganBehandles) {
                    totalbehandleCharge = totalbehandleCharge.add(perhitunganBehandle.getCharge());

                    if (perhitunganBehandle.getPerhitunganSupervisi() != null) {
                        totalbehandleCharge = totalbehandleCharge.add(perhitunganBehandle.getPerhitunganSupervisi().getTotalCharge());
                    }

                    if (perhitunganBehandle.getPerhitunganSewaAlat() != null) {
                        totalSewaAlatCharge = totalSewaAlatCharge.add(perhitunganBehandle.getPerhitunganSewaAlat().getCharge());
                    }

                    if (perhitunganBehandle.getPerhitunganUpahBuruh() != null) {
                        totalUpahBuruhCharge = totalUpahBuruhCharge.add(perhitunganBehandle.getPerhitunganUpahBuruh().getCharge());
                    }
                }

                BehandleCalculationChargeDetail chargeDetail = new BehandleCalculationChargeDetail();
                chargeDetail.setPpn(registration.getInvoice().getPpn());
                chargeDetail.setMaterai(registration.getInvoice().getMaterai());
                chargeDetail.setJumlahTagihan(registration.getInvoice().getJumlahTagihan());
                chargeDetail.setTotalTagihan(registration.getInvoice().getTotalTagihan());
                chargeDetail.setBehandleCharge(totalbehandleCharge);
                chargeDetail.setSewaAlat(totalSewaAlatCharge);
                chargeDetail.setUpahBuruh(totalUpahBuruhCharge);
                chargeDetail.setTerbilang(terbilang);

                Invoice invoice = new Invoice();
                invoice.setChargeDetail(chargeDetail);
                invoice.setCompanyNpwp(npwp.getValueString());
                invoice.setCompanyPpkp(ppkp.getValueString());
                invoice.setCompanyValidPpkpDate(ppkpTmt.getValueDate());
                invoice.setContainersCount(perhitunganBehandles.size());
                invoice.setDirectorOfFinanceId(dirOfFinance.getValueString());
                invoice.setDirectorOfFinanceName(dirOfFinance.getDescription());
                invoice.setRegistration(registration);
                invoice.setUsername(username);
                invoice.setCopyStatus(copyStatus);
                invoice.setReportId("Invoice_" + registration.getInvoice().getNoInvoice());
                invoice.setReport("/com/pelindo/ebtos/report/Invoice.jasper");
                invoice.addSubReport("detailChargeSubreport", "/com/pelindo/ebtos/report/InvoiceBehandle_detailChargeSubreport.jasper");
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

    public String preparePerhitunganInvoiceReport(Registration registration, List<PerhitunganBehandle> perhitunganBehandles) {
        if (registration != null && registration.getInvoice() != null && perhitunganBehandles != null && !perhitunganBehandles.isEmpty()) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            String username = (String) session.getAttribute("username");
            username = username == null ? "unknown" : username;

            Perhitungan perhitungan = new Perhitungan(registration.getMasterService().getServiceName());
            perhitungan.setTitle("Rincian Perhitungan Behandle");
            perhitungan.setReport("/com/pelindo/ebtos/report/Perhitungan.jasper");
            perhitungan.setMasterCurrency(registration.getInvoice().getMasterCurrency());
            perhitungan.setMaterai(registration.getInvoice().getMaterai());
            perhitungan.setPpn(registration.getInvoice().getPpn());
            perhitungan.setRegistration(registration);
            perhitungan.setUsername(username);
            perhitungan.setReportId("Perhitungan_" + registration.getNoReg());
            perhitungan.setNotebene("Catatan : Lampiran ini hanya rincian dari Nota Tagihan, dan tidak dapat digunakan sebagai bukti pembayaran sah atau sebagai faktur pajak.");
            perhitungan.addSubReport("SUBREPORT_DIR", "/com/pelindo/ebtos/report/PerhitunganBehandle_subreport.jasper");
            perhitungan.setListDataSource(perhitunganBehandles);

            String key = UUID.randomUUID().toString();
            dataStorageManagerlocal.putData(session.getId(), key, perhitungan);

            return key;
        } else {
            throw new RuntimeException("Some parameter that required to generate Perhitungan Report is empty");
        }
    }
}

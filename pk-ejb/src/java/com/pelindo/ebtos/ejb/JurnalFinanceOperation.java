/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.qtasnim.util.EnglishNumberConverter;
import com.qtasnim.util.IndonesianNumberConverter;
import com.pelindo.ebtos.ejb.facade.local.MasterCurrencyFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterSettingAppFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.RecapJurnalFacadeLocal;
import com.pelindo.ebtos.ejb.local.DataStorageManagerLocal;
import com.pelindo.ebtos.ejb.remote.JurnalFinanceOperationRemote;
import com.pelindo.ebtos.model.db.RecapJurnal;
import com.pelindo.ebtos.model.db.RecapJurnalDetail;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.report.model.BuktiJkm;
import com.qtasnim.jreport.data.QTBeanCollectionDataSource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author R. Seno Anggoro A
 */
@Stateless
public class JurnalFinanceOperation implements JurnalFinanceOperationRemote {
    @EJB
    private MasterSettingAppFacadeLocal masterSettingAppFacadeLocal;
    @EJB
    private MasterCurrencyFacadeLocal masterCurrencyFacadeLocal;
    @EJB
    private DataStorageManagerLocal dataStorageManagerlocal;
    @EJB
    private RecapJurnalFacadeLocal recapJurnalFacadeLocal;

    public String preparePrintBuktiJKM(String sumber) {
        RecapJurnal recapJurnal = recapJurnalFacadeLocal.findBySumber(sumber);

        if (recapJurnal != null) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            String username = (String) session.getAttribute("username");
            username = username == null ? "unknown" : username;
            BigDecimal totalDebit = BigDecimal.ZERO;
            String terbilang;
            MasterSettingApp dirOfFinance = masterSettingAppFacadeLocal.find("ebtos.ttd.invoice");
            MasterCurrency masterCurrency = masterCurrencyFacadeLocal.findByMataUangId(recapJurnal.getMataUangId());

            for (RecapJurnalDetail detail : recapJurnal.getRecapJurnalDetailList()) {
                totalDebit = totalDebit.add(detail.getJumlahDebit());
            }

            if (masterCurrency.getCurrCode().equalsIgnoreCase("IDR")) {
                IndonesianNumberConverter converter = new IndonesianNumberConverter();
                terbilang = converter.convertToWord(totalDebit.toBigInteger().toString()) + "Rupiah";
            } else {
                EnglishNumberConverter converterEng = new EnglishNumberConverter();
                terbilang = converterEng.numberAsSentenceFactory(totalDebit.toString());
            }

            BuktiJkm buktiJkm = new BuktiJkm();
            buktiJkm.setDirectorOfFinanceId(dirOfFinance.getValueString());
            buktiJkm.setDirectorOfFinanceName(dirOfFinance.getDescription());
            buktiJkm.setMasterCurrency(masterCurrency);
            buktiJkm.setRecapJurnal(recapJurnal);
            buktiJkm.setReportId("buktiJKM_" + recapJurnal.getSumber());
            buktiJkm.setNamaCabang("KARIANGAU");
            buktiJkm.setNamaPerusahaan("PT. KALTIM KARIANGAU TERMINAL");
            buktiJkm.setNamaTerminal("TERMINAL PETIKEMAS KARIANGAU");
            buktiJkm.setTanggalCetak(recapJurnal.getTanggal());
            buktiJkm.setTerbilang(terbilang);
            buktiJkm.setUsername(username);
            buktiJkm.setTotalDebit(totalDebit);
            buktiJkm.setListDataSource(recapJurnal.getRecapJurnalDetailList());
            buktiJkm.addSubReport("journalDetailSubreport", "/com/pelindo/ebtos/report/buktiJKM_subreport.jasper");
            buktiJkm.setReport("/com/pelindo/ebtos/report/buktiJKM.jasper");

            String key = UUID.randomUUID().toString();
            dataStorageManagerlocal.putData(session.getId(), key, buktiJkm);
            return key;
        }

        return null;
    }
}

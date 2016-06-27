/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean.report;

import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author x42jr
 */
@ManagedBean(name="laporanRencanaKedatanganKapalBean")
@ViewScoped
public class LaporanRencanaKedatanganKapal {
    private Integer bulan;

    /** Creates a new instance of LaporanRencanaKedatanganKapal */
    public LaporanRencanaKedatanganKapal() {}
    
    @PostConstruct
    private void construct() {
        Calendar cal = Calendar.getInstance();
        bulan = cal.get(Calendar.MONTH) + 1;
    }

    public void handleDownloadReport(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../laporanRencanaKedatanganKapal.pdf?bulan=" + bulan));
    }

    public Integer getBulan() {
        return bulan;
    }

    public void setBulan(Integer bulan) {
        this.bulan = bulan;
    }
}

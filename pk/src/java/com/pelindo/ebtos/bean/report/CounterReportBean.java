/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean.report;

import com.pelindo.ebtos.bean.security.LoginSessionBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;


/**
 *
 * @author R. Seno Anggoro A
 */
@ManagedBean(name="counterReportBean")
@RequestScoped
public class CounterReportBean {
    private Date startDate;
    private Date endDate;
    private String username;
    private String penanggungJawab;

    /** Creates a new instance of CounterReport */
    public CounterReportBean() {
    }

    @PostConstruct
    private void construct() {
        LoginSessionBean session = LoginSessionBean.getCurrentInstance();
        username = session.getUsername();
        startDate = new Date();
        endDate = startDate;
    }

    public void handleDownloadReport(ActionEvent event) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            RequestContext context = RequestContext.getCurrentInstance();

            context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
            String url = "../../../CounterReport.pdf?" +
                    "startDate=" + formatter.format(startDate) + "&" +
                    "endDate=" + formatter.format(endDate) + "&" +
                    "username=" + username + "&" +
                    "penanggungJawab=" + penanggungJawab;
            context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(url));
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when call handleDownloadReport", re);
        }
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPenanggungJawab() {
        return penanggungJawab;
    }

    public void setPenanggungJawab(String penanggungJawab) {
        this.penanggungJawab = penanggungJawab;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}

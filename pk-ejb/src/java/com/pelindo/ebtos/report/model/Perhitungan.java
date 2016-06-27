/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.report.model;

import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author x42jr
 */
public class Perhitungan implements IPrintable{
    private String report;
    private String service;
    private String title;
    private String username;
    private String notebene;
    private String reportId;
    private List listDataSource;
    private Registration registration;
    private MasterCurrency masterCurrency;
    private BigDecimal ppn;
    private BigDecimal materai;
    private Map<String, Object> parameters;
    private Map<String, String> subReports;

    public Perhitungan(String service) {
        this.service = service;
        this.parameters = new HashMap<String, Object>();
        this.subReports = new HashMap<String, String>();
    }

    public List getListDataSource() {
        return listDataSource;
    }

    public void setListDataSource(List listDataSource) {
        this.listDataSource = listDataSource;
    }

    public void addParameter(String parameter, Object value) {
        parameters.put(parameter, value);
    }

    public void addSubReport(String parameter, String subreport) {
        subReports.put(parameter, subreport);
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    public void setMasterCurrency(MasterCurrency masterCurrency) {
        this.masterCurrency = masterCurrency;
    }

    public BigDecimal getMaterai() {
        return materai;
    }

    public void setMaterai(BigDecimal materai) {
        this.materai = materai;
    }

    public BigDecimal getPpn() {
        return ppn;
    }

    public void setPpn(BigDecimal ppn) {
        this.ppn = ppn;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public Map<String, String> getSubReports() {
        return subReports;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getNotebene() {
        return notebene;
    }

    public void setNotebene(String notebene) {
        this.notebene = notebene;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.report.model;

import com.pelindo.ebtos.model.db.RecapJurnal;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author R. Seno Anggoro A
 */
public class BuktiJkm implements IPrintable{
    private Map<String, Object> parameters;
    private Map<String, String> subReports;
    private String reportId;
    private String report;
    private String terbilang;
    private String namaCabang;
    private String namaPerusahaan;
    private String namaTerminal;
    private String username;
    private String directorOfFinanceName;
    private String directorOfFinanceId;
    private Date tanggalCetak;
    private RecapJurnal recapJurnal;
    private BigDecimal totalDebit;
    private MasterCurrency masterCurrency;
    private List listDataSource;

    public BuktiJkm() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDirectorOfFinanceId() {
        return directorOfFinanceId;
    }

    public void setDirectorOfFinanceId(String directorOfFinanceId) {
        this.directorOfFinanceId = directorOfFinanceId;
    }

    public String getDirectorOfFinanceName() {
        return directorOfFinanceName;
    }

    public void setDirectorOfFinanceName(String directorOfFinanceName) {
        this.directorOfFinanceName = directorOfFinanceName;
    }

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    public void setMasterCurrency(MasterCurrency masterCurrency) {
        this.masterCurrency = masterCurrency;
    }

    public String getNamaCabang() {
        return namaCabang;
    }

    public void setNamaCabang(String namaCabang) {
        this.namaCabang = namaCabang;
    }

    public RecapJurnal getRecapJurnal() {
        return recapJurnal;
    }

    public void setRecapJurnal(RecapJurnal recapJurnal) {
        this.recapJurnal = recapJurnal;
    }

    public Date getTanggalCetak() {
        return tanggalCetak;
    }

    public void setTanggalCetak(Date tanggalCetak) {
        this.tanggalCetak = tanggalCetak;
    }

    public String getTerbilang() {
        return terbilang;
    }

    public void setTerbilang(String terbilang) {
        this.terbilang = terbilang;
    }

    public BigDecimal getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(BigDecimal totalDebit) {
        this.totalDebit = totalDebit;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public Map<String, String> getSubReports() {
        return subReports;
    }

    public void setSubReports(Map<String, String> subReports) {
        this.subReports = subReports;
    }

    public String getNamaPerusahaan() {
        return namaPerusahaan;
    }

    public void setNamaPerusahaan(String namaPerusahaan) {
        this.namaPerusahaan = namaPerusahaan;
    }

    public String getNamaTerminal() {
        return namaTerminal;
    }

    public void setNamaTerminal(String namaTerminal) {
        this.namaTerminal = namaTerminal;
    }
}

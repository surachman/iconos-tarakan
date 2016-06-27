/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.report.model;

import com.pelindo.ebtos.report.model.charge.IChargeDetail;
import com.pelindo.ebtos.model.db.Registration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author R. Seno Anggoro A
 */
public class Invoice implements IPrintable{
    private Registration registration;
    private String reportId;
    private String report;
    private String directorOfFinanceName;
    private String directorOfFinanceId;
    private String companyNpwp;
    private String companyPpkp;
    private String copyStatus;
    private String username;
    private Date companyValidPpkpDate;
    private Integer containersCount;
    private IChargeDetail chargeDetail;
    private Map<String, Object> parameters;
    private Map<String, String> subReports;
    private List listDataSource;

    public Invoice() {
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

    public String getCompanyNpwp() {
        return companyNpwp;
    }

    public void setCompanyNpwp(String companyNpwp) {
        this.companyNpwp = companyNpwp;
    }

    public String getCompanyPpkp() {
        return companyPpkp;
    }

    public void setCompanyPpkp(String companyPpkp) {
        this.companyPpkp = companyPpkp;
    }

    public Integer getContainersCount() {
        return containersCount;
    }

    public void setContainersCount(Integer containersCount) {
        this.containersCount = containersCount;
    }

    public String getCopyStatus() {
        return copyStatus;
    }

    public void setCopyStatus(String copyStatus) {
        this.copyStatus = copyStatus;
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

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public Date getCompanyValidPpkpDate() {
        return companyValidPpkpDate;
    }

    public void setCompanyValidPpkpDate(Date companyValidPpkpDate) {
        this.companyValidPpkpDate = companyValidPpkpDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public IChargeDetail getChargeDetail() {
        return chargeDetail;
    }

    public void setChargeDetail(IChargeDetail chargeDetail) {
        this.chargeDetail = chargeDetail;
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

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
}

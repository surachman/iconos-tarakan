/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.report.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author R. Seno Anggoro A
 */
public interface IPrintable extends Serializable{
    public Map<String, Object> getParameters();
    public Map<String, String> getSubReports();
    public void addParameter(String parameter, Object value);
    public void addSubReport(String parameter, String subreport);
    public String getReport();
    public String getReportId();
    public List getListDataSource();
}

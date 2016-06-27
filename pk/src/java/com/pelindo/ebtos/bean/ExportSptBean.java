/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.util.CsvHelper;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author R Seno Anggoro
 */
@ManagedBean(name="exportSptBean")
@ViewScoped
public class ExportSptBean {
    @EJB
    private InvoiceFacadeRemote invoiceFacadeRemote;

    private StreamedContent fileDownload;
    private List<Object[]> sptsIdr;
    private List<Object[]> sptsUsd;
    private String month;
    private String year;
    
    public ExportSptBean() {}

    @PostConstruct
    private void construct() {
        Date now = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM");
        month = dateFormatter.format(now);
        dateFormatter.applyPattern("yyyy");
        year = dateFormatter.format(now);

        try {
            sptsIdr = invoiceFacadeRemote.findSPTWithInterval("IDR", month, year);
            sptsUsd = invoiceFacadeRemote.findSPTWithInterval("USD", month, year);
        } catch (IOException ex) {
            Logger.getLogger(ExportSptBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public StreamedContent getFileDownload() {
        return fileDownload;
    }

    public List<Object[]> getSptsIdr() {
        return sptsIdr;
    }

    public List<Object[]> getSptsUsd() {
        return sptsUsd;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void handleDownloadSpt(ActionEvent event){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        InputStream stream;

        try {
            String currCode = facesContext.getExternalContext().getRequestParameterMap().get("currCode");

            if (currCode.equals(Invoice.IDR)) {
                stream = CsvHelper.getSptStream(sptsIdr);
            } else if (currCode.equals(Invoice.USD)) {
                stream = CsvHelper.getSptStream(sptsUsd);
            } else {
                throw new RuntimeException("Currency code not valid!");
            }

            fileDownload = new DefaultStreamedContent(stream, "application/csv", "spt-" + month + "." + year + ".csv");
        } catch (IOException ex) {
            Logger.getLogger(ExportSptBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleChangeFilter(ActionEvent event){
        try {
            sptsIdr = invoiceFacadeRemote.findSPTWithInterval("IDR", month, year);
            sptsUsd = invoiceFacadeRemote.findSPTWithInterval("USD", month, year);
        } catch (IOException ex) {
            Logger.getLogger(ExportSptBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

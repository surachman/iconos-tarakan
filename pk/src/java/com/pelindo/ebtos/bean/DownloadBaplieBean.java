/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.util.FileBuilder;
import com.pelindo.ebtos.ejb.facade.remote.LogExcelFacadeRemote;
import com.pelindo.ebtos.model.db.LogExcel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name="downloadBaplieBean")
@ViewScoped
public class DownloadBaplieBean implements Serializable {

    @EJB
    protected LogExcelFacadeRemote logExcelFacadeRemote;

    private List<Object[]> logExcelList;
    private List<Object[]> baplies;
    private Object[] baplie;
    private boolean visible;
    private StreamedContent fileDownload;
    protected LogExcel logExcel;

    /** Creates a new instance of DownloadBaplieBean */
    public DownloadBaplieBean() {
        logExcelList = lookupLogExcelFacadeRemote().findAllExcel();
    }

    private LogExcelFacadeRemote lookupLogExcelFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (LogExcelFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/LogExcelFacade!com.pelindo.ebtos.ejb.facade.remote.LogExcelFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * @return the logExcelList
     */
    public List<Object[]> getLogExcelList() {
        return logExcelList;
    }

    /**
     * @return the baplies
     */
    public List<Object[]> getBaplies() {
        return baplies;
    }    

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    public void clear() {
        baplies = new ArrayList<Object[]>();
        visible = false;
        fileDownload = null;
    }

    public void handlePreview(ActionEvent event) throws FileNotFoundException, IOException, BiffException{
        String idExcel = (String) event.getComponent().getAttributes().get("idExcel");
        logExcel = logExcelFacadeRemote.find(idExcel);
        String fileName = "resources/temp/" + logExcel.getChangeName();
        File file = FileBuilder.createFile(fileName);
        baplies = input(file);
        InputStream stream = FileBuilder.createInputStream(file);
        fileDownload = new DefaultStreamedContent(stream, "application/excel", logExcel.getOriginalName());
        visible = true;
        System.out.println(logExcel.getOriginalName());
    }

    public List<Object[]> input(File file) throws FileNotFoundException, IOException, BiffException {
        baplies = new ArrayList<Object[]>();
        File data = file;
        Workbook w;
        try {
            w = Workbook.getWorkbook(data);
            Sheet sheet = w.getSheet(0);
            //variable handle loop
            boolean stoploop = false;
            int atas = 0;
            int bawah = 0;
            int pinggir = 0;
            int x;
            int y;
            int mulai;
            int sel;

            for (x = 0; x < sheet.getRows(); x++) {
                for (y = 0; y < sheet.getColumns(); y++) {
                    if (sheet.getCell(y, x).getContents().equalsIgnoreCase("<BOF>")) {
                        pinggir = y;
                        atas = x + 1;
                        continue;
                    }
                    if (sheet.getCell(y, x).getContents().equalsIgnoreCase("<EOF>")) {
                        pinggir = y;
                        bawah = x - 2;
                        stoploop = true;
                        break;
                    }
                }
                if (stoploop == true) {
                    break;
                }
            }
            //loop for save data
            int ab = 0;
            for (mulai = atas; mulai <= bawah; mulai++) {
                baplie = new Object[sheet.getColumns()-1];
                for (sel = pinggir; sel < sheet.getColumns(); sel++) {
                    baplie[ab] = sheet.getCell(sel, mulai).getContents();
                    if (ab == sheet.getColumns()-2) {
                        ab = 0;
                        break;
                    }
                    ab++;
                }
                baplies.add(baplie);
            }
        } catch (BiffException e) {
        }
        return baplies;
    }

    /**
     * @return the fileDownload
     */
    public StreamedContent getFileDownload() {
        return fileDownload;
    }

}

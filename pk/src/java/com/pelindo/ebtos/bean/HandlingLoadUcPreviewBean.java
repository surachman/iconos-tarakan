/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.context.RequestContext;

/**
 *
 * @author USER
 */
@ManagedBean(name="handlingLoadUcPreviewBean")
@ViewScoped
public class HandlingLoadUcPreviewBean implements Serializable{

    protected ServiceVesselFacadeRemote serviceVesselFacadeRemote = lookupServiceVesselFacadeRemote();
    //protected ReceivingServiceFacadeRemote receivingServiceFacadeRemote = lookupReceivingServiceFacadeRemote();
    protected UncontainerizedServiceFacadeRemote uncontainerizedServiceFacade = lookupUncontainerizedServiceFacadeRemote();

    private Object[] serviceVessel;
    private List<Object[]> serviceVessels;
    private List<Object[]> handlingLoadPreviews;
    protected String noPPKB;
    protected String mode;
    private List<Object[]> serviceContLoadsList;


    /** Creates a new instance of DischargeMonitoringBean */
    public HandlingLoadUcPreviewBean() {
        serviceVessels = serviceVesselFacadeRemote.findServiceVesselsServiced();
    }

    /**
     * @return the serviceVessel
     */
    public Object[] getServiceVessel() {
        return serviceVessel;
    }

    /**
     * @return the serviceVessels
     */
    public List<Object[]> getServiceVessels() {
        return serviceVessels;
    }

    /**
     * @return the dischargeMonitoring
     */
    public List<Object[]> getHandlingLoadPreviews() {
        return handlingLoadPreviews;
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(cellStyle);
        }
    }

    public void handleSelectNoPPKB(ActionEvent event){
        noPPKB = (String) event.getComponent().getAttributes().get("noPPKB");
        serviceVessel = serviceVesselFacadeRemote.findServiceVesselByPpkb(noPPKB);
        //handlingLoadPreviews = receivingServiceFacadeRemote.findHandlingLoadMonitoring(noPPKB);
        handlingLoadPreviews = uncontainerizedServiceFacade.findHandlingLoad(noPPKB);
        serviceContLoadsList=lookupServiceContLoadFacadeRemote().findServiceContLoadByDownloadExcel(noPPKB);
//        for (int i = 0; i < handlingLoadPreviews.size(); i++) {
//            handlingLoadPreviews.get(i)[0] =(Integer)(i+1);
//        }
    }

    public void handleDownload(ActionEvent event){
        boolean print = false;
        if (handlingLoadPreviews != null){
            print = true;
        }
        mode = "handlingloaduc";
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", print); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../realisasi.pdf?" + "mode=" + mode + "&" + "no_ppkb=" + noPPKB));
    }

    public String getUrlHandlingLoad(){
        if (handlingLoadPreviews == null)
            return "#";

        mode = "handlingloaduc";
	return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/realisasi.pdf?"
                + "mode=" + mode + "&" + "no_ppkb=" + noPPKB + "";
    }

    private ServiceVesselFacadeRemote lookupServiceVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceVesselFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ServiceContLoadFacadeRemote lookupServiceContLoadFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceContLoadFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceContLoadFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

//    private ReceivingServiceFacadeRemote lookupReceivingServiceFacadeRemote() {
//        try {
//            Context c = new InitialContext();
//            return (ReceivingServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ReceivingServiceFacade!com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote");
//        } catch (NamingException ne) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
//            throw new RuntimeException(ne);
//        }
//    }

    private UncontainerizedServiceFacadeRemote lookupUncontainerizedServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (UncontainerizedServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/UncontainerizedServiceFacade!com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public List<Object[]> getServiceContLoadsList() {
        return serviceContLoadsList;
    }

    public void setServiceContLoadsList(List<Object[]> serviceContLoadsList) {
        this.serviceContLoadsList = serviceContLoadsList;
    }

    

}

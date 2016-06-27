/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateDeliveryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateReceivingFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceGateDelivery;
import com.pelindo.ebtos.model.db.ServiceGateReceiving;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.context.RequestContext;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Lenovo
 */
@ManagedBean(name = "gateActivityMonitoringBean")
@ViewScoped

public class GateActivityMonitoringBean implements Serializable {

    @EJB ServiceGateReceivingFacadeRemote serviceGateReceivingFacadeRemote;
    @EJB ServiceGateDeliveryFacadeRemote serviceGateDeliveryFacadeRemote;

    int serviceType = 1;
    List<Object[]> services;
    Date startDate, endDate;
    Date startDateEdited, endDateEdited;
    String now;
    ServiceGateReceiving receiving;
    ServiceGateDelivery delivery;
    boolean gatedOut = false;

    @PostConstruct
    public void initData(){
        services = new ArrayList<Object[]>();
        serviceType = 1;
        setData();
    }

    private void setData() {
        services.clear();
        if(startDate != null && endDate != null){
            if (serviceType == 1) {
                services = serviceGateReceivingFacadeRemote.findGateActivityMonitoring(startDate, endDate);
            } else if (serviceType == 2) {
                services = serviceGateDeliveryFacadeRemote.findGateActivityMonitoring(startDate, endDate);
            }
        }
    }

    public void handleRefresh(ActionEvent event) {
        setData();
    }

    public void handleEdit(ActionEvent event) {
        int id = (Integer) event.getComponent().getAttributes().get("sgid");
        if (serviceType == 1) {
            receiving = serviceGateReceivingFacadeRemote.find(id);
            startDateEdited = receiving.getDateIn();
            endDateEdited = receiving.getDateOut();
            if(receiving.getDateOut() == null){
                gatedOut = true;
            }else{
                gatedOut = false;
            }
        } else if (serviceType == 2) {
            delivery = serviceGateDeliveryFacadeRemote.find(id);
            startDateEdited = delivery.getDateIn();
            endDateEdited = delivery.getDateOut();
            if(delivery.getDateOut() == null){
                gatedOut = true;
            }else{
                gatedOut = false;
            }
        }
    }

    public void handleSave(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean isValid = false;
        try {
            if (startDateEdited != null) {
                if (serviceType == 1) {
                    receiving.setDateIn(startDateEdited);
                    if (receiving.getDateOut() != null) {
                        if (endDateEdited != null) {
                            receiving.setDateOut(endDateEdited);
                            serviceGateReceivingFacadeRemote.edit(receiving);
                        } else {
                            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
                            return;
                        }
                    } else {
                        serviceGateReceivingFacadeRemote.edit(receiving);
                    }
                } else if (serviceType == 2) {
                    delivery.setDateIn(startDateEdited);
                    if (delivery.getDateOut() != null) {
                        if (endDateEdited != null) {
                            delivery.setDateOut(endDateEdited);
                            serviceGateDeliveryFacadeRemote.edit(delivery);
                        } else {
                            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
                            return;
                        }
                    } else {
                        serviceGateDeliveryFacadeRemote.edit(delivery);
                    }
                }
                isValid = true;
                setData();
                FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            }
        } catch (Exception re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when call handleSave", re);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
        }
        requestContext.addCallbackParam("isValid", isValid);
    }

    public void exportToExcel(Object document) {
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
        Date date = new Date();
        now = new SimpleDateFormat("dd MM yyyy").format(date);
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public List<Object[]> getServices() {
        return services;
    }

    public void setServices(List<Object[]> services) {
        this.services = services;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public Date getEndDateEdited() {
        return endDateEdited;
    }

    public void setEndDateEdited(Date endDateEdited) {
        this.endDateEdited = endDateEdited;
    }

    public Date getStartDateEdited() {
        return startDateEdited;
    }

    public void setStartDateEdited(Date startDateEdited) {
        this.startDateEdited = startDateEdited;
    }

    public boolean isGatedOut() {
        return gatedOut;
    }

    public void setGatedOut(boolean gatedOut) {
        this.gatedOut = gatedOut;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.ServiceGateDeliveryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateReceivingFacadeRemote;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 *
 * @author Lenovo
 */
@ManagedBean(name = "unrealizedActivityMonitoringBean")
@ViewScoped

public class UnrealizedActivityMonitoringBean implements Serializable {

    @EJB ServiceGateReceivingFacadeRemote serviceGateReceivingFacadeRemote;
    @EJB ServiceGateDeliveryFacadeRemote serviceGateDeliveryFacadeRemote;

    int serviceType = 1;
    List<Object[]> services;
    String now;

    @PostConstruct
    public void initData(){
        services = new ArrayList<Object[]>();
        serviceType = 1;
        setData();
    }

    private void setData() {
        services.clear();
        if (serviceType == 1) {
            services = serviceGateReceivingFacadeRemote.findUnrealizedActivityMonitoring();
        } else if (serviceType == 2) {
            services = serviceGateDeliveryFacadeRemote.findUnrealizedActivityMonitoring();
        }
    }

    public void onChange(ValueChangeEvent event) {
        serviceType = (Integer)event.getNewValue();
        setData();
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

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }
    
}

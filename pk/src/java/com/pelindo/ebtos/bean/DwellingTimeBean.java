/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.ServiceContDischargeFacade;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import java.io.Serializable;
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
 * @author senoanggoro
 */
@ManagedBean(name="dwellingTimeBean")
@ViewScoped
public class DwellingTimeBean implements Serializable{
    @EJB ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote;
    List<Object[]> objects;
    String selectedPeriode = "A";

    @PostConstruct
    private void construct() {
        objects = serviceContDischargeFacadeRemote.findDwellingTime(ServiceContDischargeFacade.PERIODE_0_15_DAY);
    }

    public void onChangeEvent(ValueChangeEvent event) {
        String periode = (String) event.getNewValue();
        System.out.println("periode : " + periode);
        if (periode.equals("A")) {
            objects = serviceContDischargeFacadeRemote.findDwellingTime(ServiceContDischargeFacade.PERIODE_0_15_DAY);
        } else if (periode.equals("B")) {
            objects = serviceContDischargeFacadeRemote.findDwellingTime(ServiceContDischargeFacade.PERIODE_16_30_DAY);
        } else if (periode.equals("C")) {
            objects = serviceContDischargeFacadeRemote.findDwellingTime(ServiceContDischargeFacade.PERIODE_30_MORE_DAY);
        }
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

    public String getSelectedPeriode() {
        return selectedPeriode;
    }

    public void setSelectedPeriode(String selectedPeriode) {
        this.selectedPeriode = selectedPeriode;
    }

    public List<Object[]> getObjects() {
        return objects;
    }

    public void setObjects(List<Object[]> objects) {
        this.objects = objects;
    }
    
}

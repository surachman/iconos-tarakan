/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.PlanningTransDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningTransDischarge;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.ejb.EJB;
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
 * @author arie
 */
@ManagedBean(name="transhipmentPlanBean")
@ViewScoped
public class TranshipmentPlanBean implements Serializable {

    @EJB
    private PlanningTransDischargeFacadeRemote planningTransDischargeFacade;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacade;
    private List<Object[]> planningTransDischarges;
    private Object[] serviceVessel;
    private List<Object[]> serviceVessels;
    private String noPPKB;

    public TranshipmentPlanBean(){
        
    }

    @PostConstruct
    private void construct(){
        setServiceVessels(serviceVesselFacade.findAllReceivingService());
    }

    public void handleSelectNoPPKB(ActionEvent event){
        setNoPPKB((String) event.getComponent().getAttributes().get("noPPKB"));
        setServiceVessel(serviceVesselFacade.findReceivingServiceByPpkb(getNoPPKB()));
        planningTransDischarges = planningTransDischargeFacade.findPlanningTranshipmentMonitoring(noPPKB);
    }

    public void handleDownloadTransactionRecap(ActionEvent event){
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../receiving.pdf?mode=transhipment&no_ppkb=" + getNoPPKB() + ""));
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

    /**
     * @return the serviceVessel
     */
    public Object[] getServiceVessel() {
        return serviceVessel;
    }

    /**
     * @param serviceVessel the serviceVessel to set
     */
    public void setServiceVessel(Object[] serviceVessel) {
        this.serviceVessel = serviceVessel;
    }

    /**
     * @return the serviceVessels
     */
    public List<Object[]> getServiceVessels() {
        return serviceVessels;
    }

    /**
     * @param serviceVessels the serviceVessels to set
     */
    public void setServiceVessels(List<Object[]> serviceVessels) {
        this.serviceVessels = serviceVessels;
    }

    /**
     * @return the noPPKB
     */
    public String getNoPPKB() {
        return noPPKB;
    }

    /**
     * @param noPPKB the noPPKB to set
     */
    public void setNoPPKB(String noPPKB) {
        this.noPPKB = noPPKB;
    }

    /**
     * @return the planningTransDischarges
     */
    public List<Object[]> getPlanningTransDischarges() {
        return planningTransDischarges;
    }

    /**
     * @param planningTransDischarges the planningTransDischarges to set
     */
    public void setPlanningTransDischarges(List<Object[]> planningTransDischarges) {
        this.planningTransDischarges = planningTransDischarges;
    }

}

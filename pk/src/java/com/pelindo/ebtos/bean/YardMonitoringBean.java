/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.PlanningReceivingAllocationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.YardAllocationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.YardAllocationFilterFacadeRemote;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 *
 * @author wulan
 */
@ManagedBean(name = "yardMonitoringBean")
@ViewScoped
public class YardMonitoringBean implements Serializable {

    protected YardAllocationFilterFacadeRemote yardAllocationFilterFacadeRemote = lookupYardAllocationFilterFacadeRemote();
    protected PlanningReceivingAllocationFacadeRemote planningReceivingAllocationFacadeRemote = lookupPlanningReceivingAllocationFacadeRemote();
    protected YardAllocationFacadeRemote yardAllocationFacadeRemote = lookupYardAllocationFacadeRemote();
    protected PlanningReceivingFacadeRemote planningReceivingFacadeRemote = lookupPlanningReceivingFacadeRemote();
    protected ServiceVesselFacadeRemote serviceVesselFacadeRemote = lookupServiceVesselFacadeRemote();
    protected PlanningVesselFacadeRemote planningVesselFacadeRemote = lookupPlanningVesselFacadeRemote();
    private List<Object[]> vessels;
    private Object[] vessel;
    private List<Object[]> dischargeList;
    private List<Object[]> loadingList;
    private String no_ppkb;

    /** Creates a new instance of YardMonitoring */
    public YardMonitoringBean() {
        vessel = new Object[0];
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

    public void handleViewPpkb(ActionEvent event) {
        vessels = serviceVesselFacadeRemote.findServiceVesselsByYardPlanningMonitoring();
        System.out.println("samdaskd " + vessels.size());
    }

    public void handleSelectPpkb(ActionEvent event) {
        this.no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        vessel = planningVesselFacadeRemote.findPlanningVesselByPpkb(no_ppkb);
        setBlock();
        setBlockY();
    }

    private void setBlock() {
        dischargeList = yardAllocationFilterFacadeRemote.yardAllocationFilterfindByNoPpkb(no_ppkb, "FALSE", "FALSE");
        for (Object[] o : dischargeList) {
            List<Object[]> yardAllocat = yardAllocationFacadeRemote.findAllByYardAllocationFilter((Integer) o[8]);
            String block = "";
            String bl = "";
            for (Object[] ob : yardAllocat) {
                if (!bl.equalsIgnoreCase((String) ob[0])) {
                    if (block.equals("")) {
                        block = (String) ob[0];
                    } else {
                        block = block + ", " + (String) ob[0];
                    }
                }
                bl = (String) ob[0];
            }
            o[9] = block;
        }
    }

    private void setBlockY() {
        loadingList = planningReceivingAllocationFacadeRemote.findAllByPPKB(no_ppkb);
        for (Object[] o : loadingList) {
            List<Object[]> planRec = planningReceivingFacadeRemote.findAllByIdReceivingAllocation((Integer) o[11]);
            String block = "";
            String bl = "";
            for (Object[] ob : planRec) {
                if (!bl.equalsIgnoreCase((String) ob[0])) {
                    if (block.equals("")) {
                        block = (String) ob[0];
                    } else {
                        block = block + ", " + (String) ob[0];
                    }
                }
                bl = (String) ob[0];
            }
            o[12] = block;
        }
    }

    public List<Object[]> getDischargeList() {
        return dischargeList;
    }

    public void setDischargeList(List<Object[]> dischargeList) {
        this.dischargeList = dischargeList;
    }

    public List<Object[]> getLoadingList() {
        return loadingList;
    }

    public void setLoadingList(List<Object[]> loadingList) {
        this.loadingList = loadingList;
    }

    public Object[] getVessel() {
        return vessel;
    }

    public void setVessel(Object[] vessel) {
        this.vessel = vessel;
    }

    public List<Object[]> getVessels() {
        return vessels;
    }

    public void setVessels(List<Object[]> vessels) {
        this.vessels = vessels;
    }

    private YardAllocationFilterFacadeRemote lookupYardAllocationFilterFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (YardAllocationFilterFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/YardAllocationFilterFacade!com.pelindo.ebtos.ejb.facade.remote.YardAllocationFilterFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PlanningVesselFacadeRemote lookupPlanningVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PlanningVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PlanningVesselFacade!com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PlanningReceivingAllocationFacadeRemote lookupPlanningReceivingAllocationFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PlanningReceivingAllocationFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PlanningReceivingAllocationFacade!com.pelindo.ebtos.ejb.facade.remote.PlanningReceivingAllocationFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private YardAllocationFacadeRemote lookupYardAllocationFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (YardAllocationFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/YardAllocationFacade!com.pelindo.ebtos.ejb.facade.remote.YardAllocationFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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

    private PlanningReceivingFacadeRemote lookupPlanningReceivingFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PlanningReceivingFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PlanningReceivingFacade!com.pelindo.ebtos.ejb.facade.remote.PlanningReceivingFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public String getNo_ppkb() {
        return no_ppkb;
    }

    public void setNo_ppkb(String no_ppkb) {
        this.no_ppkb = no_ppkb;
    }
}

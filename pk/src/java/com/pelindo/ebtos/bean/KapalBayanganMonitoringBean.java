/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.remote.KapalBayanganOperationRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;


/**
 *
 * @author R. Seno Anggoro A
 */
@ManagedBean(name="kapalBayanganMonitoringBean")
@ViewScoped
public class KapalBayanganMonitoringBean {
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private KapalBayanganOperationRemote kapalBayanganOperationRemote;
    @EJB
    private ServiceReceivingFacadeRemote serviceReceivingFacadeRemote;
    
    private LazyDataModel<PlanningVessel> dummyVessels;
    private LazyDataModel<PlanningVessel> availableVessels;
    private List<Object[]> notAllocatedContainers;
    private Object[] selectedNotAllocatedContainers;
    private PlanningVessel dummyVessel;
    private PlanningVessel vessel;

    /** Creates a new instance of ChangeVesselMonitoring */
    public KapalBayanganMonitoringBean() {
    }

    @PostConstruct
    private void construct() {
        populateAvailablePlanningVessels();
        populateAvailableDummyVessels();
    }

    private void populateAvailablePlanningVessels() {
        availableVessels = new LazyDataModel<PlanningVessel>(){
            @Override
            public List<PlanningVessel> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String,String> filters) {
                int count = planningVesselFacadeRemote.findByStatussesAndLoadOnly_Count(filters, "Approved", "ReadyService", "Servicing");
                availableVessels.setRowCount(count);

                return planningVesselFacadeRemote.findByStatussesAndLoadOnly(first, pageSize, sortField, sortOrder, filters, "Approved", "ReadyService", "Servicing");
            }

            @Override
            public void setRowIndex(final int rowIndex){
                if (rowIndex == -1 || getPageSize() == 0) {
                    super.setRowIndex(-1);
                } else {
                    super.setRowIndex(rowIndex % getPageSize());
                }
            }
        };
    }

    private void populateAvailableDummyVessels() {
        dummyVessels = new LazyDataModel<PlanningVessel>(){
            @Override
            public List<PlanningVessel> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String,String> filters) {
                int count = planningVesselFacadeRemote.findByStatussesAndLoadOnly_Count(filters, "Approved", "ReadyService", "Servicing");
                dummyVessels.setRowCount(count);

                return planningVesselFacadeRemote.findByStatussesAndLoadOnly(first, pageSize, sortField, sortOrder, filters, "Approved", "ReadyService", "Servicing");
            }

            @Override
            public void setRowIndex(final int rowIndex){
                if (rowIndex == -1 || getPageSize() == 0) {
                    super.setRowIndex(-1);
                } else {
                    super.setRowIndex(rowIndex % getPageSize());
                }
            }
        };
    }

    public void handleSelectDummyVessel() {
        notAllocatedContainers = serviceReceivingFacadeRemote.findVesselChangeAbleContainers(dummyVessel.getNoPpkb());
    }

    public void handleValidateChangeVessel() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        boolean isRowsSelected = true;

        if (selectedNotAllocatedContainers == null || selectedNotAllocatedContainers.length == 0) {
            isRowsSelected = false;
        }

        requestContext.addCallbackParam("isRowsSelected", isRowsSelected);
    }

    public void handleChangeVessel() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            if (selectedNotAllocatedContainers != null && selectedNotAllocatedContainers.length > 0) {
                List<String> containers = new ArrayList<String>();
                List<String> jobSlips = new ArrayList<String>();

                for (Object row: selectedNotAllocatedContainers) {
                    Object[] serviceReceivings = (Object[]) row;
                    containers.add((String) serviceReceivings[0]);
                    jobSlips.add((String) serviceReceivings[8]);
                }

                kapalBayanganOperationRemote.handleChangePpkb(containers, jobSlips, vessel, dummyVessel);

                handleSelectDummyVessel();
            }

            FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when call handleChangeVessel", re);
            FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
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

    public LazyDataModel<PlanningVessel> getAvailableVessels() {
        return availableVessels;
    }

    public LazyDataModel<PlanningVessel> getDummyVessels() {
        return dummyVessels;
    }

    public PlanningVessel getDummyVessel() {
        return dummyVessel;
    }

    public void setDummyVessel(PlanningVessel dummyVessel) {
        this.dummyVessel = dummyVessel;
    }

    public Object[] getSelectedNotAllocatedContainers() {
        return selectedNotAllocatedContainers;
    }

    public void setSelectedNotAllocatedContainers(Object[] selectedNotAllocatedContainers) {
        this.selectedNotAllocatedContainers = selectedNotAllocatedContainers;
    }

    public PlanningVessel getVessel() {
        return vessel;
    }

    public void setVessel(PlanningVessel vessel) {
        this.vessel = vessel;
    }

    public List<Object[]> getNotAllocatedContainers() {
        return notAllocatedContainers;
    }
}

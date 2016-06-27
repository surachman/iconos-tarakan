/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PlanningVessel;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author senoanggoro
 */
@Remote
public interface PlanningVesselFacadeRemote {

    void create(PlanningVessel planningVessel);

    void edit(PlanningVessel planningVessel);

    void remove(PlanningVessel planningVessel);

    PlanningVessel find(Object id);

    List<PlanningVessel> findAll();

    List<PlanningVessel> findRange(int[] range);

    int count();

    Object[] findPlanningVesselByPpkb(String no_ppkb);

    List<Object[]> findPlanningVesselsReceivingConfirm();

    List<Object[]> findPlanningVessels();

    List<Object[]> findPlanningVesselsCy();

    List<Object[]> findPlanningVesselsSg(Date close_rec);

    List<Object[]> findPlanningVesselList();

    Object[] findPlanningVesselDetail(String no_ppkb);

    List<Object[]> findPlanningVesselsByActivity();

    List<Object[]> findPlanningVesselsByActivityYardAlocation();

    List<Object[]> findPlanningVesselByVessel(String vessel_code);

    List<Object[]> findPlanningVesselByDock(String dock_code);

    List<Object[]> findPlanningVesselByVesselCode(String vessel_code);

    List<String> findConflictBirthPlan(String dock, Date eta, Date etd, int fr_meter, int to_meter, String noPpkb);

    List<Object[]> findBerthingsWithRange(Date eta, Date etd, String dockCode, String noPpkb);

    List<Object[]> findBerthingsBoundWithRange(Date eta, Date etd);

    List<Object[]> findPlanningVesselByStatus();

    List<Object[]> findPlanningVesselApproval();

    List<Object[]> findPlanningVesselApprovalReady();
    
    //penambahan untuk menampilkan semua no ppkb untuk discharge list by ade chelsea tgl 23 april 2014
    List<Object[]> findPlanningVesselApprovalReady2();

    List<Object[]> findPlanningVesselReadyService();

    List<Object[]> findPlanningVesselByStatusAppReadyServicing();

    List<Object[]> findPlanningVesselByActivityLoad();

    List<Object[]> vesselMonitoringToday();

    Integer findBooking(String no_ppkb);

    String findCurrCode(String no_ppkb);

    List<Object[]> vesselMonitoringOnline(String no_ppkb);

    String findTipePelayaranByPPKB(String no_ppkb);

    List<Object[]> findPlanningVesselsSgOther();

    String generateId(String bulan);

    Object[] findReadyServicesMobile(String no_ppkb);

    List<Object[]> findPlanningVesselsByFinished();

    List<Object[]> findPlanningVesselsByFinishedNotServed();

    List<Object[]> findPlanningVesselsHasApprovedtoLoad();

    List<String> findPpkbNumbers(String no_Ppkb);

    List<Object[]> findPlanningVesselExcConfirm();

    public void handleStartService(PlanningVessel planningVessel);

    List<Object[]> findPlanningVesselByBeritaAcara(String no_ppkb);

    public void saveBaplieFromExcel(com.pelindo.ebtos.model.db.PlanningVessel vessel, java.util.List<java.lang.Object[]> baplies, java.lang.String action, com.pelindo.ebtos.model.db.LogExcel logExcel);

    List<Object[]> findPlanningVesselByPluggingToLoad();

    public void handleCancelVesselSchedule(com.pelindo.ebtos.model.db.PlanningVessel planningVessel);

    public java.util.List<java.lang.Object[]> findCanceledPpkb();

    public java.util.List<java.lang.Object[]> findPlanningVesselsServicing();

    public java.lang.Integer findReceivingAllocationCountByNoPpkb(java.lang.String noPpkb);

    public java.util.List<java.lang.Object[]> findByStatus4HHT(String status);

    public com.pelindo.ebtos.model.db.PlanningVessel findByPpkbAndStatus(java.lang.String noPpkb, java.lang.String status);

    public java.util.List<java.lang.Object[]> findVesselsCanBeCanceled();

    public int findByStatussesAndLoadOnly_Count(Map<String,String> likes, String... status);
    
    public List<PlanningVessel> findByStatussesAndLoadOnly(int first, int pageSize, String sortField, boolean sortOrder, Map<String,String> filters, String... status);
    
    public List<Object[]> findPlanningVesselsServicingOnly();

    public java.util.List<java.lang.Object[]> findCancelStatusAbleVessels();

    public java.util.List<com.pelindo.ebtos.model.db.PlanningVessel> findByKapalBayanganStatus(java.lang.Boolean kapalBayangan);
}

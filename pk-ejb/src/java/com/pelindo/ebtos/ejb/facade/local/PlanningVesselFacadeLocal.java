/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PlanningVessel;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author senoanggoro
 */
@Local
public interface PlanningVesselFacadeLocal {

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

    List<Object[]> findPlanningVesselByVessel(String vessel_code);

    List<Object[]> findPlanningVesselByDock(String dock_code);

    List<Object[]> findPlanningVesselByVesselCode(String vessel_code);

    List<String> findConflictBirthPlan(String dock, Date eta, Date etd, int fr_meter, int to_meter, String noPpkb);

    List<Object[]> findBerthingsWithRange(Date eta, Date etd, String dockCode, String noPpkb);

    List<Object[]> findBerthingsBoundWithRange(Date eta, Date etd);

    List<Object[]> findPlanningVesselByStatus();

    List<Object[]> findPlanningVesselApproval();

    List<Object[]> findPlanningVesselReadyService();

    List<Object[]> findPlanningVesselByActivityLoad();

    List<Object[]> vesselMonitoringToday();

    Object[] findGatePassedContainer(String noPpkb, String contNo);

    java.util.List<java.lang.Object[]> findPlanningVesselApprovalByDock(java.lang.String dockCode);

    String generateId(String bulan);

    public java.util.List<java.lang.Object[]> findVesselScheduleByDateRangeAndDock(java.util.Date eta, java.util.Date etd, java.lang.String dockCode);
}

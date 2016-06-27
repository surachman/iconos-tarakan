/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.master.MasterPort;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author senoanggoro
 */
@Local
public interface PlanningContLoadFacadeLocal {

    void create(PlanningContLoad planningContLoad);

    void edit(PlanningContLoad planningContLoad);

    void remove(PlanningContLoad planningContLoad);
    
    Object[] findPlanningBayplanLoadCekBayExist(short bay, short row, short tier, String no_ppkb);

    PlanningContLoad find(Object id);

    public java.lang.Integer updateStatusCancelLoadingByNoPpkb(java.lang.String noPpkb, java.lang.String statusCancelLoading);

    public java.util.List<java.lang.Object[]> findGrossCapacityByNoPpkbAndBay(java.lang.String noPpkb, java.lang.Short bay, java.lang.Integer minRow, java.lang.Integer maxRow, Integer minTier, Integer maxTier);

    public java.util.List<com.pelindo.ebtos.model.db.PlanningContLoad> findNotPlannedContainerByNoPpkbAndYardLocation(java.lang.String noPpkb, java.lang.String block, java.lang.Short slot);
    
    public java.util.List<com.pelindo.ebtos.model.db.PlanningContLoad> findPlannedContainerByBayLocation(java.lang.String noPpkb, java.lang.Short bay);

    public java.util.List<java.lang.Object[]> findPlannedContainerByBayLocationAsObjectArray(java.lang.String noPpkb, java.lang.Short bay);

    public com.pelindo.ebtos.model.db.PlanningContLoad findByNoPpkbAndContNoExcludeCancelLoading(java.lang.String noPpkb, java.lang.String contNo);

    public com.pelindo.ebtos.model.db.PlanningContLoad findMovableContainer(java.lang.String contNo);

    public int updatePlanningVessel(com.pelindo.ebtos.model.db.PlanningVessel newValue, com.pelindo.ebtos.model.db.PlanningVessel oldValue);

    public int updatePlanningVesselByContNo(com.pelindo.ebtos.model.db.PlanningVessel newValue, MasterPort nextPort, com.pelindo.ebtos.model.db.PlanningVessel oldValue, java.util.List<java.lang.String> containers);

    int deleteByNoPpkbAndContNo(String noPpkb, String contNo);

    public PlanningContLoad findByNoPpkbContNoAndStatusCancelLoading(String noPpkb, String contNo, Boolean b);

    public List<PlanningContLoad> findByNoPpkb(String noPpkb);
    
    public List findContainerStatus(String noPPKB);
    
    public List<PlanningContLoad> findTheSameContainer(String noPpkb, String contNo);
}

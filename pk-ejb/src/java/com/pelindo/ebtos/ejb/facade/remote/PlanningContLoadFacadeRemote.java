/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.master.MasterPort;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author senoanggoro
 */
@Remote
public interface PlanningContLoadFacadeRemote {

    void create(PlanningContLoad planningContLoad);

    void edit(PlanningContLoad planningContLoad);

    void remove(PlanningContLoad planningContLoad);

    PlanningContLoad find(Object id);

    List<PlanningContLoad> findAll();

    List<PlanningContLoad> findRange(int[] range);

    List<Object[]> findPlanningBayplanLoadsByPpkb(String no_ppkb);

    List<Object[]> findLoadingListByPpkb(String no_ppkb);

    int count();

    List<Object[]> findPlanningContLoads();

    List<Object[]> findPlanningContLoads3(String no_ppkb);

    List<Object[]> findPlanningContLoadsTranshipment(String no_ppkb);
    
    List<Object[]> findPluggableOnContainersByPpkb(String noPpkb);

    Object[] findByContNo(String cont_no, String pos, Boolean trans);

    Object[] findByContNoLiftOn(String no_ppkb, String cont_no);

    Object[] findPlanningBayplanLoadCekBayExist(short bay, short row, short tier,String no_ppkb);

    List<Object[]> findPlanningBayplanLoadCekNotCompleted(String no_ppkb);

    Integer findByContNoAndPpkbByEdit(String no_ppkb, String cont_no);

    Object[] PlanningContLoadfindCancelLoading(String no_ppkb, String cont_no);

    public java.lang.Integer setCompletedByNoPpkb(java.lang.String noPpkb);

    public java.util.List<com.pelindo.ebtos.model.db.PlanningContLoad> findNotPlannedContainerByNoPpkbAndYardLocation(java.lang.String noPpkb, java.lang.String block, java.lang.Short slot);

    public java.util.List<com.pelindo.ebtos.model.db.PlanningContLoad> findPlannedContainerByBayLocation(java.lang.String noPpkb, java.lang.Short bay);

    public com.pelindo.ebtos.model.db.PlanningContLoad findByNoPpkbAndContNoExcludeCancelLoading(java.lang.String noPpkb, java.lang.String contNo);

    public int deleteByNoPpkbAndContNo(java.lang.String noPpkb, java.lang.String contNo);

    public java.lang.Object[] findLiftableOnContainer(java.lang.String noPpkb);

    public com.pelindo.ebtos.model.db.PlanningContLoad findMovableContainer(java.lang.String contNo);

    public com.pelindo.ebtos.model.db.PlanningContLoad findMovableOffContainer(java.lang.String contNo);

    public java.util.List<java.lang.String> findPodByNoPpkbAndYardLocation(java.lang.String noPpkb, java.lang.String block, java.lang.Short slot);

    public java.util.List<com.pelindo.ebtos.model.db.PlanningContLoad> findNotPlannedContainerByPodNoPpkbAndYardLocation(java.lang.String noPpkb, java.lang.String block, java.lang.Short slot, java.lang.String pod);

    public com.pelindo.ebtos.model.db.PlanningContLoad findByCancelableContainer(java.lang.String noPpkb, java.lang.String contNo);

    public com.pelindo.ebtos.model.db.PlanningContLoad findByNoPpkbContNoAndStatusCancelLoading(java.lang.String noPpkb, java.lang.String contNo, Boolean statusCancelLoading);

    public java.util.List<java.lang.Object[]> findCountableLoadReefers(java.lang.String noPpkb);

    public java.lang.Object[] findPluggableContainer(java.lang.String contNo);

    public int updatePlanningVesselByContNo(com.pelindo.ebtos.model.db.PlanningVessel newValue, MasterPort nextPort, com.pelindo.ebtos.model.db.PlanningVessel oldValue, java.util.List<java.lang.String> containers);

    public java.util.List<java.lang.Object[]> findLoadCandidatesPerCustomer(java.lang.String custCode);

    public java.lang.Object[] findLoadCandidatesPerCustomer_summary(java.lang.String custCode);

    public java.util.List<com.pelindo.ebtos.model.db.PlanningContLoad> findContainersThatHaveNotBeenLoaded(java.lang.String noPpkb);

    public com.pelindo.ebtos.model.db.PlanningContLoad findByNoPpkbAndContNoUpdateLiftOn(java.lang.String noPpkb, java.lang.String contNo);

    public java.util.List<java.lang.Object[]> findMonitoringCustoms();
    
    public List<PlanningContLoad> findByNoPpkb(String noPpkb);

    public List findContainerStatus(String noPPKB);

    public List<PlanningContLoad> findTheSameContainer(String noPpkb, String contNo);

    public List<PlanningContLoad> findByPpkbNotCompleted(String noPPKB);

}

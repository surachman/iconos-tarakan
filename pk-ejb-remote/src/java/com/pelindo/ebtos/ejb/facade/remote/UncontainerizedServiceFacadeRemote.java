/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.UncontainerizedService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author postgres
 */
@Remote
public interface UncontainerizedServiceFacadeRemote {

    void create(UncontainerizedService uncontainerizedService);

    void edit(UncontainerizedService uncontainerizedService);

    void remove(UncontainerizedService uncontainerizedService);

    UncontainerizedService find(Object id);

    UncontainerizedService findByNoPpkbAndBlNo(String noPpkb, String blNo);

    List<UncontainerizedService> findAll();

    List<UncontainerizedService> findRange(int[] range);

    int count();

    List<Object[]> findIdByPpkb(String no_ppkb);

    public List<Object[]> findByPpkbPlanning(String no_ppkb, String operation);

    List<Object[]> findByPpkbOperation(String no_ppkb, String operation);

    List<Object[]> findForLiftOFF(String no_ppkb, String operation);

    public List<Object[]> findByPpkbAndBl(String no_ppkb);

    public List<Object[]> findByPpkbAndBlList(String no_ppkb);

    List<Object[]> findByPpkbStatusOperation(String no_ppkb, String status, String operation);

    public List<Object[]> findByPpkb(String no_ppkb);

    List<Object[]> findByEntryDelivery(String no_ppkb);

    public List<Object[]> findByEntryReceivingUcLoad(String no_ppkb);

    Object[] findDetailByIdUc(int id_uc);

    Object[] findByPpkbAndBlNoMobile(String no_ppkb, String bl_no, String status, String operation, Boolean is_delivery);

    Object[] findByBlNoMobile(String bl_no, String status, String operation, Boolean is_delivey);

    List<Object[]> findDeliveredUcsByNoPpkb(String no_ppkb);

    List<Object[]> findDeliverableUcs(String no_ppkb);

    List<Object[]> findShifting(String no_ppkb);

    public List<Object[]> findShiftingWithout(String no_ppkb);

    Integer findWeightByPpkbNBl(String no_ppkb, String bl_no);

    public int findUncontainerizedServiceId(String no_ppkb, String bl_no);

    Object[] findByBlNoMobileUc(String bl_no);

    List<Object[]> findByUcMonitoringFrontDischarge(String no_ppkb);

    List<Object[]> findByUcMonitoringFrontLoad(String no_ppkb);

    Object[] findShiftingWithoutMobile(String no_ppkb, String bl_no);

    Object[] findShiftingMobile(String no_ppkb, String bl_no);

    List<Object[]> findForLoadConfirmList(String no_ppkb, String status, String operation);

    List<Object[]> findForLoadConfirmByTL(String no_ppkb);

    public java.util.List<com.pelindo.ebtos.model.db.UncontainerizedService> findUCThatHaveNotBeenLoaded(java.lang.String noPpkb);

    public java.util.List<com.pelindo.ebtos.model.db.UncontainerizedService> findUCThatHaveNotReachedCY(java.lang.String noPpkb);

    List<Object[]> findUncontainerizedTranshipment(String no_ppkb);

    List<Object[]> findUncontainerizedAfterTranshipment(String no_ppkb);

    public java.util.List<com.pelindo.ebtos.model.db.UncontainerizedService> findTranshipmentUCThatHaveReachedCY(java.lang.String noPpkb);

    List<Object[]> findUncontainerizedBayPlanLoadList(String no_ppkb);

    List<Object[]> findUncontainerizedBayPlanLoadListSelect(String no_ppkb);

    Integer findUncontainerizedBayPlanLoadId(String no_ppkb, String bl_no);

    public com.pelindo.ebtos.model.db.UncontainerizedService findUcByStatusOperationAndDeliveryStatus(java.lang.String noPpkb, java.lang.String blNo, java.lang.String status, java.lang.String operation, java.lang.Boolean isDelivery);

    public int resetPlannedUcServiceByNoReg(java.lang.String noReg);

    public int deleteUnplannedUcServiceByNoReg(java.lang.String noReg);

    public java.util.List<java.lang.Object[]> findReceivableUcsByNoPpkb(java.lang.String noPpkb);

    public java.util.List<java.lang.Object[]> findDischargableUcsByNoPpkb(java.lang.String no_ppkb);

    public java.util.List<java.lang.Object[]> findDischargedUcsByNoPpkb(java.lang.String no_ppkb);

    public java.util.List<java.lang.Object[]> findLiftableOffByNoPpkb(java.lang.String noPpkb);

    public java.util.List<java.lang.Object[]> findHasLiftedOffByNoPpkb(java.lang.String noPpkb);

    public java.util.List<java.lang.Object[]> findLiftableOnByNoPpkb(java.lang.String noPpkb);

    public java.util.List<java.lang.Object[]> findHasLiftedOnByNoPpkb(java.lang.String noPpkb);

    public java.util.List<java.lang.Object[]> findHasLoadedByNoPpkb(java.lang.String noPpkb);

    public java.util.List<java.lang.Object[]> findLoadableByNoPpkb(java.lang.String noPpkb);

    public java.util.List<java.lang.Object[]> findHandlingDischarge(java.lang.String noPpkb);

    public java.util.List<java.lang.Object[]> findHandlingLoad(java.lang.String noPpkb);

    public UncontainerizedService findDischargableUcByNoPpkbAndBlNo(java.lang.String no_ppkb, java.lang.String bl_no);

    public Object[] findDeliverableUcByBlNo(java.lang.String blNo);

    public java.lang.Object[] findLoadableByNoPpkbAndNoBl(java.lang.String noPpkb, java.lang.String noBl);
}

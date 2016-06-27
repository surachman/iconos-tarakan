/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.master.MasterPort;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface PlanningContReceivingFacadeRemote {

    void create(PlanningContReceiving planningContReceiving);

    void edit(PlanningContReceiving planningContReceiving);

    void remove(PlanningContReceiving planningContReceiving);

    PlanningContReceiving find(Object id);

    List<PlanningContReceiving> findAll();

    List<PlanningContReceiving> findRange(int[] range);

    int count();

    List<Object[]> findPlanningContReceivingByNoPpkb(String no_ppkb);

    List<Object[]> findPlanningContReceivings();

    List<Object[]> findPlanningContReceivingsByNoPpkb(String no_ppkb);

    List<Object[]> findReceivingConfirm(String no_ppkb);
    
    //penambahan pencarian list no container by ade chelsea tanggal 24 maret 2014
    List<Object[]> findReceivingConfirm2();

    Object[] findByContDelete(String no_ppkb, String cont_no);

    Object[] findByContNo(String no_ppkb, String cont_no, String pos);

    List<Object[]> findPlanningContReceivingIsGenerateTrue(String no_ppkb);

    List<Object[]> findPlanningContReceivingBaplieLoad(String no_ppkb);

    Object[] findPlanningContReceivingGateReceiving(String noPpkb, String cont_no);

    List<Integer> findBaplieByConstraint(int cont_type, String cont_status, String gross_class, String commodity_code, String dg, String over_size, String isExport, String disch_port, String no_ppkb);

    List<Integer> findIdByIdConstraint(int id);

    List<Object[]> createGenerateByPPKB(String no_ppkb);

    int deleteByIdConstraint(int id);

    Integer findPlanningCont(String cont_no,String no_ppkb,String is_generate);

    Object[] findPlanningContReceivingByCekExist(String cont_no,String no_ppkb);

    List<Object[]> findPlanningContReceivingIsGenerateTrueAndBlNo(String no_ppkb,String bl_no);

    Object[] findByContNoMobile(String cont_no, String pos);

    Object[] findPlanningContReceivingCreateGenerate(int id);

    List<Object[]> findContainerDetails(String no_ppkb, String cont_no);

    List<Object[]> findContainerDetailsPPKBnull(String cont_no);

    List<Object[]> findContainerDetailsCONTnull(String no_ppkb);

    List<String> findContainerNumbers(String no_Ppkb, String contNo);

    void saveMasterContainer(PlanningContReceiving planningContReceiving,String cont);

    List<Object[]> findPlanningContMonitoringLoad(String no_ppkb, String pos);

    Object[] findReceivableContainerWithSuggestedLocation(String cont_no);

    List<PlanningContReceiving> findByNoPpkb(String noPpkb);

    Integer findByContValidasiConstraint(String no_ppkb, int contSize, int contType, String contStatus, String overSize, String dg, String isEdit, String gross, String port);

    public com.pelindo.ebtos.model.db.PlanningContReceiving findByNoPpkbAndContNo(java.lang.String noPpkb, java.lang.String contNo);

    public java.lang.Object[] findReceivableContainer(java.lang.String contNo);

    public int deleteByNoPpkbAndContNo(java.lang.String noPpkb, java.lang.String contNo);

    public int updatePlanningVesselByContNo(com.pelindo.ebtos.model.db.PlanningVessel newValue, MasterPort nextPort, com.pelindo.ebtos.model.db.PlanningVessel oldValue, java.util.List<java.lang.String> containers);
}

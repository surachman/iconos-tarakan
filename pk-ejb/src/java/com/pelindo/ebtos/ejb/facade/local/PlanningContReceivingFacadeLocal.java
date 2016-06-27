/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.master.MasterPort;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface PlanningContReceivingFacadeLocal {

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

    Object[] findReceivableContainerWithSuggestedLocation(String cont_no);

    public com.pelindo.ebtos.model.db.PlanningContReceiving findByNoPpkbAndContNo(java.lang.String noPpkb, java.lang.String contNo);

    public int updatePlanningVessel(com.pelindo.ebtos.model.db.PlanningVessel newValue, com.pelindo.ebtos.model.db.PlanningVessel oldValue);

    public int deleteByNoPpkbAndContNo(java.lang.String noPpkb, java.lang.String contNo);

    int updatePlanningVesselByContNo(PlanningVessel newValue, MasterPort nextPort, PlanningVessel oldValue, List<String> containers);
}

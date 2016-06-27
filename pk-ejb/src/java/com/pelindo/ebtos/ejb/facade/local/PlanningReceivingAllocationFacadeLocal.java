/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PlanningReceivingAllocation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author senoanggoro
 */
@Local
public interface PlanningReceivingAllocationFacadeLocal {

    void create(PlanningReceivingAllocation planningReceivingAllocation);

    void edit(PlanningReceivingAllocation planningReceivingAllocation);

    void remove(PlanningReceivingAllocation planningReceivingAllocation);

    PlanningReceivingAllocation find(Object id);

    List<PlanningReceivingAllocation> findAll();

    List<PlanningReceivingAllocation> findRange(int[] range);

    int count();

    List<Object[]> findPlanningReceivingAllocationListNative(String booking_code);

    List<Object[]> findPlanningReceivingAllocationsByBlock (String block);

     //Object[] findPlanningReceiving(String no_ppkb);

    Object[] findPlanningReceiving(String no_ppkb,int cont_size,int cont_type,String cont_status, String over_size, String dg);

    Object[] findPlanningReceivingByValidasi(String no_ppkb,int cont_size,int cont_type,String cont_status, String over_size, String dg);

    Object[] findPlanningReceivingByQueryCopy(String no_ppkb, int cont_size, int cont_type, String cont_status, String over_size, String dg, String isExport,String gross_class,String portCode);

    int findPlanningReceivingById(String no_ppkb, int cont_size, int cont_type, String cont_status, String over_size, String dg,String gross_class);

    List<Object[]> findPlanningReceivingAllocationsByMapping(String booking_code);

     List<Object[]> findPlanningReceivingList(Integer receivingId);

     List<Object[]> findPlanningReceivingSearchId(String noPpkb);

     List<Object[]> findAllByPPKB(String no_ppkb);

     Integer findLastOfId();

     Object findDuplicateConstraint(String no_ppkb, int cont_type, String cont_status, String gross_class, String dg, String over_size, String isExport, String port_code);

     Object[] findAllByID(int id);

     public com.pelindo.ebtos.model.db.PlanningReceivingAllocation findByAllocationConstraint(java.lang.String noPpkb, java.lang.Short contSize, java.lang.Integer contType, java.lang.String contStatus, java.lang.String overSize, java.lang.String dg, java.lang.String grossClass, java.lang.String portCode, java.lang.String isExport);

    public int updatePlanningVessel(com.pelindo.ebtos.model.db.PlanningVessel newValue, com.pelindo.ebtos.model.db.PlanningVessel oldValue);
    
    public PlanningReceivingAllocation findByNoPpkbAndContainer(String noPpkb, Short contSize, Integer contType, String contStatus);
}

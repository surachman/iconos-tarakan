/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.Equipment;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Dyware-Dev01
 */
@Remote
public interface EquipmentFacadeRemote {

    void create(Equipment equipment);

    void edit(Equipment equipment);

    void remove(Equipment equipment);

    Equipment find(Object id);

    List<Equipment> findAll();

    List<Equipment> findRange(int[] range);

    int findByIdContainerReceiving(String no_ppkb, String cont_no, String eac, String operation);

    int findByIdContainer (String no_ppkb, String cont_no, String eac, String operation);

    int count();

    Object[] findByContNoActCode(String cont_no, String act_code, String operation);

    int findByIdContainerOperation (String no_ppkb, String cont_no, String eac, String op);

    int findEquipmentId (String no_ppkb,Date start_time, Date end_time, String eac, String op);

    int findIdEquipmentByAll(String eq_code, String op_code, String cont_no, String no_ppkb, Date start_time, Date end_time, String eac, String op);

    int deleteEquipmentById(int id);

    int updateEndTimeByEquipmentConstraint(Date end_time, String no_ppkb, String cont_no, String eac, String operation);

    Object[] findEquipmentByValidasi(Date start_time,Date end_time,String eac,String operation);

    int findEquipmentHTUc(String no_ppkb, String bl_no, String eac, String op);

    Date findStartTimeServiceReceiving(String no_ppkb, String cont_no);

    List<Object[]> findByPpkb(String no_ppkb);

    Integer findIdByBLno(String no_ppkb, String bl_no, String eac, String operation);

    int updateEquipmentHTUncontainerized(Date end_time, String no_ppkb, String bl_no, String eac, String operation);

    int findIdEquipmentByAllBlNo(String eq_code, String op_code, String bl_no, String no_ppkb, Date start_time, Date end_time, String eac, String op);

    List<Integer> findIdByPPKBnContNo(String no_ppkb, String cont_no);

    Integer findByIdContainerReceivingPlug(String no_ppkb, String cont_no, String eac, String operation);

    public com.pelindo.ebtos.model.db.Equipment findByEquipmentActCodeAndOperation(java.lang.String noPpkb, java.lang.String contNo, java.lang.String equipmentActCode, java.lang.String operation);
    
    public com.pelindo.ebtos.model.db.Equipment findByPpkbBLNoEquipmentActCodeAndOperation(java.lang.String noPpkb, java.lang.String blNo, java.lang.String equipmentActCode, java.lang.String operation);

    public com.pelindo.ebtos.model.db.Equipment findIdByPPKBnContNo2(java.lang.String noPpkb, java.lang.String contNo);

    public int updatePlanningVesselByContNo(com.pelindo.ebtos.model.db.PlanningVessel newValue, com.pelindo.ebtos.model.db.PlanningVessel oldValue, List<java.lang.String> contNo);

    public com.pelindo.ebtos.model.db.Equipment findByEquipmentActCodeAndOperationReceiving(java.lang.String noPpkb, java.lang.String contNo, java.lang.String equipmentActCode, java.lang.String operation);
}

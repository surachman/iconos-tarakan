/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningVessel;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dyware-Dev01
 */
@Local
public interface EquipmentFacadeLocal {

    void create(Equipment equipment);

    void edit(Equipment equipment);

    void remove(Equipment equipment);

    Equipment find(Object id);

    List<Equipment> findAll();

    List<Equipment> findRange(int[] range);

    int count();

    public Integer findIdByBLno(String no_ppkb, String bl_no, String eac, String operation);

    public com.pelindo.ebtos.model.db.Equipment findByPpkbBLNoEquipmentActCodeAndOperation(java.lang.String noPpkb, java.lang.String blNo, java.lang.String equipmentActCode, java.lang.String operation);
    
    public com.pelindo.ebtos.model.db.Equipment findIdByPPKBnContNo2(java.lang.String noPpkb, java.lang.String contNo);

    public int updatePlanningVessel(com.pelindo.ebtos.model.db.PlanningVessel newValue, com.pelindo.ebtos.model.db.PlanningVessel oldValue);

    public int updatePlanningVesselByContNo(PlanningVessel newValue, PlanningVessel oldValue, List<String> containers);

    public int updatePlanningVesselReceivingByContNo(java.lang.String newValue, java.lang.String oldValue, java.util.List<java.lang.String> containers);

    public int updateEndTimeByEquipmentConstraint(Date startTime, String noPpkb, String contNo, String string, String string0);

    public Equipment findByEquipmentActCodeAndOperation(String noPpkb, String contNo, String equipmentActCode, String operation);
}

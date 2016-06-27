/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.EquipmentFacadeLocal;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningVessel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author Dyware-Dev01
 */
@Stateless
public class EquipmentFacade extends AbstractFacade<Equipment> implements EquipmentFacadeLocal, EquipmentFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public EquipmentFacade() {
        super(Equipment.class);
    }


    public int findByIdContainerReceiving(String no_ppkb, String cont_no, String eac, String operation) {
        return decimalToInt(
            (BigDecimal) getEntityManager().createNamedQuery("Equipment.Native.findByIdContainerReceiving").setParameter(1, no_ppkb).setParameter(2, cont_no).setParameter(3, eac).setParameter(4, operation).getSingleResult()
        );
    }

    //Equipment.Native.findByIdContainer
    public int findByIdContainer(String no_ppkb, String cont_no, String eac, String operation) {
        return decimalToInt(
                (BigDecimal) getEntityManager().createNamedQuery("Equipment.Native.findByIdContainer").setParameter(1, no_ppkb).setParameter(2, cont_no).setParameter(3, eac).setParameter(4, operation).getSingleResult()
        );
    }

    public int findByIdContainerOperation(String no_ppkb, String cont_no, String eac, String op) {
        return decimalToInt(
            (BigDecimal) getEntityManager().createNamedQuery("Equipment.Native.findByIdContainerOperation").setParameter(1, no_ppkb).setParameter(2, cont_no).setParameter(3, eac).setParameter(4, op).getSingleResult()
        );
    }

    public int findEquipmentId(String no_ppkb, Date start_time, Date end_time, String eac, String op) {
        return decimalToInt( 
                (BigDecimal) getEntityManager().createNamedQuery("Equipment.Native.findEquipmentId").setParameter(1, no_ppkb).setParameter(2, start_time).setParameter(3, end_time).setParameter(4, eac).setParameter(5, op).getSingleResult()
        );
    }

    public int findIdEquipmentByAll(String eq_code, String op_code, String cont_no, String no_ppkb, Date start_time, Date end_time, String eac, String op) {
        return decimalToInt(
                (BigDecimal) getEntityManager().createNamedQuery("Equipment.Native.findIdEquipmentByAll").setParameter(1, eq_code).setParameter(2, op_code).setParameter(3, cont_no).setParameter(4, no_ppkb).setParameter(5, start_time).setParameter(6, end_time).setParameter(7, eac).setParameter(8, op).getSingleResult()
        );
    }
     public int findIdEquipmentByAllBlNo(String eq_code, String op_code, String bl_no, String no_ppkb, Date start_time, Date end_time, String eac, String op) {
        return decimalToInt(
                (BigDecimal) getEntityManager().createNamedQuery("Equipment.Native.findIdEquipmentByAllBlNo").setParameter(1, eq_code).setParameter(2, op_code).setParameter(3, bl_no).setParameter(4, no_ppkb).setParameter(5, start_time).setParameter(6, end_time).setParameter(7, eac).setParameter(8, op).getSingleResult()
        );
    }

    public Object[] findByContNoActCode(String cont_no, String act_code, String operation) {
        return objectDecimalsToObjectInts(
                (Object[]) getEntityManager().createNamedQuery("Equipment.Native.findByContNoActCode").setParameter(1, cont_no).setParameter(2, act_code).setParameter(3, operation).getSingleResult()
        );
    }

    public int deleteEquipmentById(int id) {
        return getEntityManager().createNamedQuery("Equipment.Native.deleteEquipmentById").setParameter(1, id).executeUpdate();
    }

    public int updateEndTimeByEquipmentConstraint(Date endTime, String noPpkb, String contNo, String equipmentActCode, String operation) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("Equipment.updateEndTimeByEquipmentConstraint")
                .setParameter("endTime", endTime)
                .setParameter("noPpkb", noPpkb)
                .setParameter("contNo", contNo)
                .setParameter("equipmentActCode", equipmentActCode)
                .setParameter("operation", operation)
                .executeUpdate();
    }   

    public Object[] findEquipmentByValidasi(Date start_time, Date end_time, String eac, String op) {
        Object[] temp = new Object[11];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("Equipment.Native.findEquipmentByValidasi").setParameter(1, start_time).setParameter(2, end_time).setParameter(3, eac).setParameter(4, op).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }
    
    public Date findByStartTimeData() {
        Date date = null;
        try {
            date = (Date) getEntityManager().createNamedQuery("Equipment.Native.findByStartTimeData").getSingleResult();
        } catch (NoResultException ex) {
            date = null;
        }
        return date;
    }
    
    
    public Date findByEndTimeData() {
         Date date = null;
        try {
            date = (Date) getEntityManager().createNamedQuery("Equipment.Native.findByEndTimeData").getSingleResult();
        } catch (NoResultException ex) {
            date = null;
        }
        return date;
    }
    
    

    public int findEquipmentHTUc(String no_ppkb, String bl_no, String eac, String op) {
        return decimalToInt(
                (BigDecimal) getEntityManager().createNamedQuery("Equipment.Native.findEquipmentHTUc").setParameter(1, no_ppkb).setParameter(2, bl_no).setParameter(3, eac).setParameter(4, op).getSingleResult()
        );
    }

    public Date findStartTimeServiceReceiving(String no_ppkb, String cont_no) {
        Date date = null;
        try {
            date = (Date) getEntityManager().createNamedQuery("Equipment.Native.findStartTimeServiceReceiving").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult();
        } catch (NoResultException ex) {
            date = null;
        }
        return date;
    }

    public List<Object[]> findByPpkb(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("Equipment.Native.findByPpkb").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Integer findIdByBLno(String no_ppkb, String bl_no, String eac, String operation) {
        Integer temp;
        try {
            temp = decimalToInt(
                    (BigDecimal) getEntityManager().createNamedQuery("Equipment.Native.findIdByBLno").setParameter(1, no_ppkb).setParameter(2, bl_no).setParameter(3, eac).setParameter(4, operation).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Integer findByIdContainerReceivingPlug(String no_ppkb, String cont_no, String eac, String operation) {
        Integer temp;
        try {
            temp = decimalToInt(
                    (BigDecimal) getEntityManager().createNamedQuery("Equipment.Native.findByIdContainerReceivingPlug").setParameter(1, no_ppkb).setParameter(2, cont_no).setParameter(3, eac).setParameter(4, operation).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Equipment findByPpkbBLNoEquipmentActCodeAndOperation(String noPpkb, String blNo, String equipmentActCode, String operation) {
        try {
            return (Equipment) getEntityManager().createNamedQuery("Equipment.findByPpkbBLNoEquipmentActCodeAndOperation")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("blNo", blNo)
                    .setParameter("equipmentActCode", equipmentActCode)
                    .setParameter("operation", operation)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    public Equipment findIdByPPKBnContNo2(String noPpkb, String contNo) {
        try {
            return (Equipment) getEntityManager().createNamedQuery("Equipment.findIdByPPKBnContNo2")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Equipment findByEquipmentActCodeAndOperation(String noPpkb, String contNo, String equipmentActCode, String operation) {
        try {
            return (Equipment) getEntityManager().createNamedQuery("Equipment.findByEquipmentActCodeAndOperation")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .setParameter("equipmentActCode", equipmentActCode)
                    .setParameter("operation", operation)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Equipment findByEquipmentActCodeAndOperationReceiving(String noPpkb, String contNo, String equipmentActCode, String operation) {
        try {
            return (Equipment) getEntityManager().createNamedQuery("Equipment.findByEquipmentActCodeAndOperationReceiving")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .setParameter("equipmentActCode", equipmentActCode)
                    .setParameter("operation", operation)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    

    public int updateEquipmentHTUncontainerized(Date end_time, String no_ppkb, String bl_no, String eac, String operation) {
        return getEntityManager().createNamedQuery("Equipment.Native.updateEquipmentHTUncontainerized").setParameter(1, end_time).setParameter(2, no_ppkb).setParameter(3, bl_no).setParameter(4, eac).setParameter(5, operation).executeUpdate();
    }

    public List<Integer> findIdByPPKBnContNo(String no_ppkb, String cont_no){
        return decimalsToInts(
                getEntityManager().createNamedQuery("Equipment.Native.findIdByPPKBnContNo").setParameter(1, no_ppkb).setParameter(2, cont_no).getResultList()
        );
    }

    @Deprecated
    public int removeMasaAndRealPenumpukanThatHaveLiftedOn(String noPpkb) {
        return getEntityManager().createNamedQuery("Equipment.updateMasaAndRealPenumpukanThatHaveLiftedOn")
                .setParameter("masa1", null)
                .setParameter("masa2", null)
                .setParameter("realPenumpukan", null)
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }

    public int updatePlanningVessel(PlanningVessel newValue, PlanningVessel oldValue) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("Equipment.updatePlanningVessel")
                .setParameter("newValue", newValue)
                .setParameter("oldValue", oldValue)
                .executeUpdate();
    }

    public int updatePlanningVesselByContNo(PlanningVessel newValue, PlanningVessel oldValue, List<String> containers) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("Equipment.updatePlanningVesselByContNo")
                .setParameter("newValue", newValue)
                .setParameter("oldValue", oldValue)
                .setParameter("containers", containers)
                .executeUpdate();
    }

    public int updatePlanningVesselReceivingByContNo(String newValue, String oldValue, List<String> containers) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("Equipment.updatePlanningVesselReceivingByContNo")
                .setParameter("newValue", newValue)
                .setParameter("oldValue", oldValue)
                .setParameter("containers", containers)
                .executeUpdate();
    }
}

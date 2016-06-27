/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PlanningReceivingAllocationFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeInGeneralFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningReceivingAllocationFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningReceivingAllocation;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class PlanningReceivingAllocationFacade extends AbstractFacade<PlanningReceivingAllocation> implements PlanningReceivingAllocationFacadeRemote, PlanningReceivingAllocationFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private MasterContainerTypeInGeneralFacadeRemote masterContainerTypeInGeneralFacadeRemote;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PlanningReceivingAllocationFacade() {
        super(PlanningReceivingAllocation.class);
    }

    @Override
    public List<Object[]> findPlanningReceivingAllocationListNative(String booking_code) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningReceivingAllocation.Native.findPlanningReceivingAllocationsByBooking").setParameter(1, booking_code).getResultList()
        );

    }

    public List<Object[]> findPlanningReceivingAllocationsByMapping(String booking_code) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningReceivingAllocation.Native.findPlanningReceivingAllocationsByMapping").setParameter(1, booking_code).getResultList()
        );

    }

    public List<Object[]> findPlanningReceivingAllocationsByBlock(String block) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningReceivingAllocation.Native.findPlanningReceivingAllocationsByBlock").setParameter(1, block).getResultList()
        );
    }
//     public Object[] findPlanningReceiving(String no_ppkb){
//        return (Object[]) getEntityManager().createNamedQuery("PlanningReceivingAllocation.Native.findPlanningReceiving").setParameter(1, no_ppkb).getSingleResult();
//    }

    public Object[] findPlanningReceiving(String no_ppkb, int cont_size, int cont_type, String cont_status, String over_size, String dg) {
        return objectDecimalsToObjectInts(
                (Object[]) getEntityManager().createNamedQuery("PlanningReceivingAllocation.Native.findPlanningReceiving").setParameter(1, no_ppkb).setParameter(2, cont_size).setParameter(3, cont_type).setParameter(4, cont_status).setParameter(5, over_size).setParameter(6, dg).getSingleResult()
        );
    }

    public Object[] findPlanningReceivingByValidasi(String no_ppkb, int cont_size, int cont_type, String cont_status, String over_size, String dg) {
        Object[] temp = new Object[15];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PlanningReceivingAllocation.Native.findPlanningReceivingByValidasi").setParameter(1, no_ppkb).setParameter(2, cont_size).setParameter(3, cont_type).setParameter(4, cont_status).setParameter(5, over_size).setParameter(6, dg).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }

    public Object[] findPlanningReceivingByQueryCopy(String no_ppkb, int cont_size, int cont_type, String cont_status, String over_size, String dg, String isExport, String gross_class, String portCode) {
        Object[] tempp = new Object[15];
        MasterContainerType masterContainerType = masterContainerTypeFacadeRemote.find(cont_type);
        cont_type = masterContainerTypeInGeneralFacadeRemote.findGenericContType(masterContainerType.getFeetMark(), masterContainerType.getMasterContainerTypeInGeneral().getId());

        try {
            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PlanningReceivingAllocation.Native.findPlanningReceivingByQueryCopy")
                    .setParameter(1, no_ppkb)
                    .setParameter(2, cont_size)
                    .setParameter(3, cont_type)
                    .setParameter(4, cont_status)
                    .setParameter(5, over_size)
                    .setParameter(6, dg)
                    .setParameter(7, gross_class)
                    .setParameter(8, portCode)
                    .setParameter(9, isExport)
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            return null;
        }
    }

    public int findPlanningReceivingById(String no_ppkb, int cont_size, int cont_type, String cont_status, String over_size, String dg, String gross_class) {
        return decimalToInt(
                (BigDecimal) getEntityManager().createNamedQuery("PlanningReceivingAllocation.Native.findPlanningReceivingById").setParameter(1, no_ppkb).setParameter(2, cont_size).setParameter(3, cont_type).setParameter(4, cont_status).setParameter(5, over_size).setParameter(6, dg).setParameter(7, gross_class).getSingleResult()
        );
    }

    public List<Object[]> findPlanningReceivingList(Integer receivingId) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningReceivingAllocation.Native.findPlanningReceivingList").setParameter(1, receivingId).getResultList()
        );

    }

    public List<Object[]> findPlanningReceivingSearchId(String noPpkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningReceivingAllocation.Native.findPlanningReceivingSearchId").setParameter(1, noPpkb).getResultList()
        );
    }

    public List<Object[]> findAllByPPKB(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningReceivingAllocation.Native.findAllByPPKB").setParameter(1, no_ppkb).getResultList()
        );
    }

    public Integer findLastOfId() {
        return decimalToInt(
                (BigDecimal) getEntityManager().createNamedQuery("PlanningReceivingAllocation.Native.findLastOfId").getSingleResult()
        );
    }

    public Object findDuplicateConstraint(String no_ppkb, int cont_type, String cont_status, String gross_class, String dg, String over_size, String isExport, String port_code) {
        Object temp = null;
        MasterContainerType masterContainerType = masterContainerTypeFacadeRemote.find(cont_type);
        cont_type = masterContainerTypeInGeneralFacadeRemote.findGenericContType(masterContainerType.getFeetMark(), masterContainerType.getMasterContainerTypeInGeneral().getId());

        try {
            temp = (Object) getEntityManager().createNamedQuery("PlanningReceivingAllocation.Native.findDuplicateConstraint")
                    .setParameter(1, no_ppkb)
                    .setParameter(2, cont_type)
                    .setParameter(3, cont_status)
                    .setParameter(4, gross_class)
                    .setParameter(5, dg)
                    .setParameter(6, over_size)
                    .setParameter(7, port_code)
                    .setParameter(8, isExport)
                    .getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Integer findValidasiConstraint(String no_ppkb, int cont_size, int cont_type, String cont_status, String over_size, String dg, String isExport, String gross_class, String port_code) {
        Integer idPlan = null;
        try {
            MasterContainerType masterContainerType = masterContainerTypeFacadeRemote.find(cont_type);
            cont_type = masterContainerTypeInGeneralFacadeRemote.findGenericContType(masterContainerType.getFeetMark(), masterContainerType.getMasterContainerTypeInGeneral().getId());

            idPlan = decimalToInt(
                    (BigDecimal) getEntityManager().createNamedQuery("PlanningReceivingAllocation.Native.findValidasiConstraint")
                    .setParameter(1, no_ppkb)
                    .setParameter(2, cont_size)
                    .setParameter(3, cont_type)
                    .setParameter(4, cont_status)
                    .setParameter(5, over_size)
                    .setParameter(6, dg)
                    .setParameter(7, gross_class)
                    .setParameter(8, port_code)
                    .setParameter(9, isExport)
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            idPlan = null;
        }
        return idPlan;
    }

    public Object[] findAllByID(int id) {
        return objectDecimalsToObjectInts(
                (Object[]) getEntityManager().createNamedQuery("PlanningReceivingAllocation.Native.findAllByID").setParameter(1, id).getSingleResult()
        );
    }

    public Integer findAllocationBooking(String no_ppkb) {
        Integer temp;
        try {
            temp = decimalToInt(
                    (BigDecimal) getEntityManager().createNamedQuery("PlanningReceivingAllocation.Native.findAllocationBooking").setParameter(1, no_ppkb).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = 0;
        }
        return temp;
    }

    public PlanningReceivingAllocation findByAllocationConstraint(String noPpkb, Short contSize, Integer contType, String contStatus, String overSize, String dg, String grossClass, String portCode, String isExport) {
        try {
            MasterContainerType masterContainerType = masterContainerTypeFacadeRemote.find(contType);
            contType = masterContainerTypeInGeneralFacadeRemote.findGenericContType(masterContainerType.getFeetMark(), masterContainerType.getMasterContainerTypeInGeneral().getId());

            return (PlanningReceivingAllocation) getEntityManager().createNamedQuery("PlanningReceivingAllocation.findByAllocationConstraint")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contSize", contSize)
                    .setParameter("contType", contType)
                    .setParameter("contStatus", contStatus)
                    .setParameter("overSize", overSize)
                    .setParameter("dg", dg)
                    .setParameter("grossClass", grossClass)
                    .setParameter("portCode", portCode)
                    .setParameter("isExport", isExport)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    public PlanningReceivingAllocation findByNoPpkbAndContainer(String noPpkb, Short contSize, Integer contType, String contStatus) {
        try {
            MasterContainerType masterContainerType = masterContainerTypeFacadeRemote.find(contType);
            contType = masterContainerTypeInGeneralFacadeRemote.findGenericContType(masterContainerType.getFeetMark(), masterContainerType.getMasterContainerTypeInGeneral().getId());

            return (PlanningReceivingAllocation) getEntityManager().createNamedQuery("PlanningReceivingAllocation.findByNoPpkbAndContainer")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contSize", contSize)
                    .setParameter("contType", contType)
                    .setParameter("contStatus", contStatus)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public int updatePlanningVessel(PlanningVessel newValue, PlanningVessel oldValue) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PlanningReceivingAllocation.updatePlanningVessel")
                .setParameter("newValue", newValue)
                .setParameter("oldValue", oldValue)
                .executeUpdate();
    }
}

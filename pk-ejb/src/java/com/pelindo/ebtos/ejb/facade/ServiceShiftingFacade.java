/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ServiceShiftingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceShifting;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author dycoder
 */
@Stateless
public class ServiceShiftingFacade extends AbstractFacade<ServiceShifting> implements ServiceShiftingFacadeRemote, ServiceShiftingFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceShiftingFacade() {
        super(ServiceShifting.class);
    }

    public List<Object[]> findServiceShiftingReshippingFalse(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceShifting.Native.findServiceShiftingReshippingFalse").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findServiceShiftingReshipping(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceShifting.Native.findServiceShiftingReshipping").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findServiceShiftingReshippingNotToCY(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceShifting.Native.findServiceShiftingReshippingNotToCY").setParameter(1, no_ppkb).getResultList()
        );
    }
    public List<Object[]> findServiceShiftingReshippingWithout(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceShifting.Native.findServiceShiftingReshippingWithout").setParameter(1, no_ppkb).getResultList()
        );
    }

    public Object[] findByContNo(String no_ppkb, String cont_no) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceShifting.Native.findByContNo").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult()
            );

        } catch (NoResultException ex) {

            temp = null;
        }
        return temp;
    }

    public Object[] findByPpkbLiftOffeConfirmSelectHht(String cont_no, String pos) {
        Object[] temp = new Object[14];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceShifting.Native.findByPpkbLiftOffeConfirmSelectHht").setParameter(1, cont_no).setParameter(2, pos).getSingleResult()
            );

        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findByPpkbLiftOnConfirmSelectHht(String cont_no, String pos) {
        Object[] temp = new Object[8];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceShifting.Native.findByPpkbLiftOnConfirmSelectHht").setParameter(1, cont_no).setParameter(2, pos).getSingleResult()
            );

        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findByPpkbDischargeConfirmSelectHht(String no_ppkb, String cont_no) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceShifting.Native.findByPpkbDischargeConfirmSelectHht").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public ServiceShifting findContainerShiftingByPositionAndShiftDestination(String noPpkb, String contNo, String position, String shiftTo) {
        try {
            return (ServiceShifting) getEntityManager().createNamedQuery("ServiceShifting.findContainerShiftingByPositionAndShiftDestination")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .setParameter("position", position)
                    .setParameter("shiftTo", shiftTo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public ServiceShifting findByNoPpkbContNoAndReshippingStatus(String noPpkb, String contNo, String isReshipping) {
        try {
            return (ServiceShifting) getEntityManager().createNamedQuery("ServiceShifting.findByNoPpkbContNoAndReshippingStatus")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .setParameter("isReshipping", isReshipping)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public ServiceShifting findContainerShiftingByContNoPositionAndShiftDestination(String contNo, String position, String shiftTo) {
        try {
            return (ServiceShifting) getEntityManager().createNamedQuery("ServiceShifting.findContainerShiftingByContNoPositionAndShiftDestination")
                    .setParameter("contNo", contNo)
                    .setParameter("position", position)
                    .setParameter("shiftTo", shiftTo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public ServiceShifting findByPositionAndContNo(String noPpkb, String contNo, String position) {
        try {
            return (ServiceShifting) getEntityManager().createNamedQuery("ServiceShifting.findByPositionAndContNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .setParameter("position", position)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Object[] findByContNoMobile(String no_ppkb, String cont_no) {
        Object[] temp = new Object[10];
        try {
            System.out.println(no_ppkb + " " + cont_no);
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNativeQuery("ServiceShifting.Native.findByContNoMobile").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            System.out.println("Masuk Null");
            temp = null;
        }
        return temp;
    }

    public Object[] findByContNoReship(String no_ppkb, String cont_no, String is_reshipping) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceShifting.Native.findByContNoReship").setParameter(1, no_ppkb).setParameter(2, cont_no).setParameter(3, is_reshipping).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findByPpkbLoadConfirmSelectHht(String no_ppkb,String cont_no, String pos) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceShifting.Native.findByPpkbLoadConfirmSelectHht").setParameter(1, no_ppkb).setParameter(2, cont_no).setParameter(3, pos).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    /**
     *
     * @param noPpkb
     * @return
     *      0 = cont_no,
     *      1 = no_ppkb,
     *      2 = vessel_name,
     *      3 = block,
     *      4 = y_slot,
     *      5 = y_row,
     *      6 = y_tier,
     *      7 = cont_size,
     *      8 = cont_status,
     *      9 = oversize,
     *      10 = dg,
     *      11 = cont_damage
     */
    public Object[] findLiftableOnContainer(String contNo) {
        try {
            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceShifting.Native.findLiftableOnContainer")
                    .setParameter(1, contNo)
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            return null;
        }
    }

    public ServiceShifting findByNoPpkbAndContNo(String noPpkb, String contNo) {
        try {
            return (ServiceShifting) getEntityManager().createNamedQuery("ServiceShifting.findByNoPpkbAndContNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

     public Object[] findByPpkbLoadConfirmSelectHht2(String cont_no, String pos) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceShifting.Native.findByPpkbLoadConfirmSelectHht2").setParameter(1, cont_no).setParameter(2, pos).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findRekap(String no_ppkb, String operation) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceShifting.Native.findRekap").setParameter(1, no_ppkb).setParameter(2, operation).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByPPKB(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceShifting.Native.findByPpkb").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Integer> findByPPKBPaid(String no_ppkb) {
        List<Integer> temp = new ArrayList<Integer>();
        try {
            temp = decimalsToInts(
                    getEntityManager().createNamedQuery("ServiceShifting.Native.findByPpkbPaid").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<ServiceShifting> findByNoPpkbAndHasPaid(String noPpkb) {
        return getEntityManager().createNamedQuery("ServiceShifting.findByNoPpkbAndStatusPaid").setParameter("noPpkb", noPpkb).setParameter("isPaid", "TRUE").getResultList();
    }

    public int removeMasterActivityByNoPpkbAndHasPaid(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("ServiceShifting.updateMasterActivityByNoPpkbAndStatusPaid").setParameter("masterActivity", null).setParameter("noPpkb", noPpkb).setParameter("isPaid", "TRUE").executeUpdate();
    }

    public List<ServiceShifting> findByNoPpkbAndReshippingStatus(String noPpkb, String isReshipping) {
        return getEntityManager().createNamedQuery("ServiceShifting.findByNoPpkbAndReshippingStatus").setParameter("noPpkb", noPpkb).setParameter("isReshipping", isReshipping).getResultList();
    }

    public List<ServiceShifting> findByNotReshippedShiftingByNoPpkb(String noPpkb) {
        return getEntityManager().createNamedQuery("ServiceShifting.findByNotReshippedShiftingByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .getResultList();
    }

    public List<Object[]> findByPpkbDischargeConfirmList(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceShifting.Native.findByPpkbDischargeConfirmList").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findByPpkbDischargeConfirmSelect(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceShifting.Native.findByPpkbDischargeConfirmSelect").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findByPpkbLiftOffList(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceShifting.Native.findByPpkbLiftOffList").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findByPpkbLiftOffSelect(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceShifting.Native.findByPpkbLiftOffSelect").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findByPpkbBayPlanLoadList(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceShifting.Native.findByPpkbBayPlanLoadList").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findByPpkbLiftOnList(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceShifting.Native.findByPpkbLiftOnList").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findByPpkbLiftOnListSelect(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceShifting.Native.findByPpkbLiftOnListSelect").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findByPpkbLoadConfirmList(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceShifting.Native.findByPpkbLoadConfirmList").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findByPpkbLoadConfirmSelect(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceShifting.Native.findByPpkbLoadConfirmSelect").setParameter(1, no_ppkb).getResultList()
        );
    }

    /**
     *
     * @param noPpkb
     * @param contNo
     * @return
     *      0 = ss.id,
     *      1 = ss.cont_no,
     *      2 = ss.bl_no,
     *      3 = ss.cont_gross,
     *      4 = ss.cont_size,
     *      5 = ss.cont_status,
     *      6 = ss.dg,
     *      7 = ss.dg_label,
     *      8 = ss.cont_type,
     *      9 = ss.commodity_code,
     *      10 = ss.mlo,
     *      11 = ss.seal_no,
     *      12 = ss.is_export_import,
     *      13 = ss.shift_to,
     *      14 = ss.v_bay,
     *      15 = ss.v_row,
     *      16 = ss.v_tier
     *      17 = ss.oversize
     *      18 = isoCode
     */
    @Override
    public Object[] findShiftableContainer(String noPpkb, String contNo) {
        try {
            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceShifting.Native.findShiftableContainer")
                    .setParameter(1, noPpkb)
                    .setParameter(2, contNo)
                    .getSingleResult()
            );
        } catch (NoResultException nre) {
            return null;
        }
    }
    
    @Override
    public List<ServiceShifting> findServiceShiftingLoadConfirm(String noPpkb, short bay) {
        String sql = "select ssf.id, ssf.v_row, ssf.v_tier from service_shifting ssf "
                + "where ssf.position='05' "
                + "and ssf.no_ppkb='" + noPpkb + "' and ssf.v_bay=" + bay;
        List<Object[]> dbs = getEntityManager().createNativeQuery(sql).getResultList();
        List<ServiceShifting> objs = new ArrayList<ServiceShifting>();
        for (int i = 0; i < dbs.size(); i++) {
            Object[] db = dbs.get(i);
            ServiceShifting obj = new ServiceShifting();
            obj.setId(Integer.parseInt(db[0].toString()));
            obj.setVRow(Short.parseShort(db[1].toString()));
            obj.setVTier(Short.parseShort(db[2].toString()));
            objs.add(obj);
        }
        
        return objs;
    }
    
    public List<Object[]> findServiceShiftingByBayLocationAsObjectArray(String ppkb, int bay){
        String sql = "SELECT ss.cont_no, mct.new_iso_code, ss.cont_gross, ss.v_bay, ss.v_row, ss.v_tier, "
                + " CASE WHEN obj.bay <> ss.v_bay AND ss.cont_size <> 20 THEN 'TRUE' ELSE 'FALSE' "
                + " END AS is_crossed, ss.v_bay || ' ' || ss.v_row || ' ' || ss.v_tier, "
                + " ss.disch_port FROM service_shifting ss "
                + " JOIN m_container_type mct ON (ss.cont_type=mct.cont_type), "
                + " (SELECT " + bay + " AS bay from dual) obj WHERE ss.no_ppkb = '" + ppkb + "' "
                + " and ss.position='05' "
                + " AND (ss.v_bay = obj.bay OR (ss.v_bay = obj.bay - 2 AND ss.cont_size IN (40, 45)) OR (ss.v_bay = obj.bay - 4 AND ss.cont_size = 45)) "
                + " AND ss.v_row IS NOT NULL AND ss.v_tier IS NOT NULL";
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNativeQuery(sql).getResultList()
        );
    }
}

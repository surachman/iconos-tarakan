/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.UcShiftingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.UcShiftingServiceFacadeRemote;
import com.pelindo.ebtos.model.db.UcShiftingService;
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
public class UcShiftingServiceFacade extends AbstractFacade<UcShiftingService> implements UcShiftingServiceFacadeLocal, UcShiftingServiceFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public UcShiftingServiceFacade() {
        super(UcShiftingService.class);
    }

    public List<Object[]> findUcShiftingServiceWithoutPlan(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("UcShiftingService.Native.findUcShiftingServiceWithoutPlan").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findUcShiftingServiceWithPlan(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("UcShiftingService.Native.findUcShiftingServiceWithPlan").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByPpkb(String no_ppkb){
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("UcShiftingService.Native.findByPpkb").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Integer> findByPPKBPaid(String no_ppkb) {
        List<Integer> temp = new ArrayList<Integer>();
        try {
            temp = decimalsToInts(
                    getEntityManager().createNamedQuery("UcShiftingService.Native.findByPpkbPaid").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<UcShiftingService> findByNoPpkbAndHasPaid(String noPpkb) {
        return getEntityManager().createNamedQuery("UcShiftingService.findByNoPpkbStatusPaid")
                .setParameter("noPpkb", noPpkb)
                .setParameter("isPaid", "TRUE")
                .getResultList();
    }

    public int removeActivityCodeByNoPpkbAndHasPaid(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("UcShiftingService.updateActivityCodeByNoPpkbStatusPaid")
                .setParameter("activityCode", null)
                .setParameter("noPpkb", noPpkb)
                .setParameter("isPaid", "TRUE")
                .executeUpdate();
    }

    public int deleteByNoPpkbAndBlNo(String noPpkb, String blNo) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("UcShiftingService.deleteByNoPpkbAndBlNo")
                .setParameter("noPpkb", noPpkb)
                .setParameter("blNo", blNo)
                .executeUpdate();
    }

    public Object[] findByPpkbBlNo(String no_ppkb, String bl_no, Integer is_reshipping) {
        Object[] temp = new Object[8];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("UcShiftingService.Native.findByPpkbBlNo")
                    .setParameter(1, no_ppkb)
                    .setParameter(2, bl_no)                   
                    .setParameter(3, is_reshipping == 1 ? "TRUE" : "FALSE")
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public UcShiftingService findByNoPpkbAndBlNo(String noPpkb, String blNo) {
        try {
            return (UcShiftingService) getEntityManager().createNamedQuery("UcShiftingService.findByNoPpkbAndBlNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("blNo", blNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<UcShiftingService> findByNoPpkbAndReshippingStatus(String noPpkb, boolean isReshipping) {
        return getEntityManager().createNamedQuery("UcShiftingService.findByNoPpkbAndReshippingStatus")
                .setParameter("noPpkb", noPpkb)
                .setParameter("isReshipping", isReshipping ? "TRUE" : "FALSE")
                .getResultList();
    }
}

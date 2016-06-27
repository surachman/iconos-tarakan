/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.UncontainerizedServiceFacadeLocal;
import com.pelindo.ebtos.model.db.UncontainerizedService;
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
 * @author postgres
 */
@Stateless
public class UncontainerizedServiceFacade extends AbstractFacade<UncontainerizedService> implements UncontainerizedServiceFacadeLocal, UncontainerizedServiceFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public UncontainerizedServiceFacade() {
        super(UncontainerizedService.class);
    }

    public List<Object[]> findIdByPpkb(String no_ppkb) {
        return getEntityManager().createNamedQuery("UncontainerizedService.Native.findIdByPpkb").setParameter(1, no_ppkb).getResultList();
    }

    public List<Object[]> findByPpkb(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("UncontainerizedService.Native.findByPpkb").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByPpkbPlanning(String no_ppkb, String operation) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("UncontainerizedService.Native.findByPpkbPlanning").setParameter(1, no_ppkb).setParameter(2, operation).getResultList()
        );
    }

    public List<Object[]> findByPpkbOperation(String no_ppkb, String operation) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("UncontainerizedService.Native.findByPpkbOperation").setParameter(1, no_ppkb).setParameter(2, operation).getResultList()
        );
    }

    public List<Object[]> findForLiftOFF(String no_ppkb, String operation) {
        return objectsDecimalsToObjectsInts( 
                getEntityManager().createNamedQuery("UncontainerizedService.Native.findForLiftOFF").setParameter(1, no_ppkb).setParameter(2, operation).getResultList()
        );
    }

    public List<Object[]> findLiftableOffByNoPpkb(String noPpkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("UncontainerizedService.Native.findLiftableOffByNoPpkb")
                .setParameter(1, noPpkb)
                .getResultList()
        );
    }

    public List<Object[]> findHasLiftedOffByNoPpkb(String noPpkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("UncontainerizedService.Native.findHasLiftedOffByNoPpkb")
                .setParameter(1, noPpkb)
                .getResultList()
        );
    }

    public List<Object[]> findReceivableUcsByNoPpkb(String noPpkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("UncontainerizedService.Native.findReceivableUcsByNoPpkb")
                .setParameter(1, noPpkb)
                .getResultList()
        );
    }

    public List<Object[]> findByPpkbAndBl(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("UncontainerizedService.Native.findByPpkbAndBl").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByPpkbAndBlList(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("UncontainerizedService.Native.findByPpkbAndBlList").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByPpkbStatusOperation(String no_ppkb, String status, String operation) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("UncontainerizedService.Native.findByPpkbStatusOperation").setParameter(1, no_ppkb).setParameter(2, status).setParameter(3, operation).getResultList()
        );
    }

    public List<Object[]> findDischargableUcsByNoPpkb(String no_ppkb) {
        return objectsDecimalsToObjectsInts(getEntityManager().createNamedQuery("UncontainerizedService.Native.findDischargableUcsByNoPpkb")
                .setParameter(1, no_ppkb)
                .getResultList());
    }

    public List<Object[]> findDischargedUcsByNoPpkb(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("UncontainerizedService.Native.findDischargedUcsByNoPpkb")
                .setParameter(1, no_ppkb)
                .getResultList());
    }
    
    public List<Object[]> findLiftableOnByNoPpkb(String noPpkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("UncontainerizedService.Native.findLiftableOnByNoPpkb")
                .setParameter(1, noPpkb)
                .getResultList()
        );
    }
    
    public List<Object[]> findHasLiftedOnByNoPpkb(String noPpkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("UncontainerizedService.Native.findHasLiftedOnByNoPpkb")
                .setParameter(1, noPpkb)
                .getResultList()
        );
    }

    public List<Object[]> findLoadableByNoPpkb(String noPpkb) {
        return objectsDecimalsToObjectsInts( 
                getEntityManager().createNamedQuery("UncontainerizedService.Native.findLoadableByNoPpkb")
                .setParameter(1, noPpkb)
                .getResultList()
        );
    }

    public Object[] findLoadableByNoPpkbAndNoBl(String noPpkb, String noBl) {
        try {
            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("UncontainerizedService.Native.findLoadableByNoPpkbAndNoBl")
                    .setParameter(1, noPpkb)
                    .setParameter(2, noBl)
                    .getSingleResult()
            );
        } catch (RuntimeException re) {
            return null;
        }
    }

    public List<Object[]> findHasLoadedByNoPpkb(String noPpkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("UncontainerizedService.Native.findHasLoadedByNoPpkb")
                .setParameter(1, noPpkb)
                .getResultList()
        );
    }

    public List<Object[]> findByEntryDelivery(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("UncontainerizedService.Native.findByEntryDelivery").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByEntryReceivingUcLoad(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts( 
                    getEntityManager().createNamedQuery("UncontainerizedService.Native.findByEntryReceivingUcLoad").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findDetailByIdUc(int id_uc) {
        Object[] temp = new Object[8];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("UncontainerizedService.Native.findDetailByIdUc").setParameter(1, id_uc).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findByPpkbAndBlNoMobile(String no_ppkb, String bl_no, String status, String operation, Integer is_delivery) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts( 
                    (Object[]) getEntityManager().createNamedQuery("UncontainerizedService.Native.findByPpkbAndBlMobile").setParameter(1, no_ppkb).setParameter(2, bl_no).setParameter(3, status).setParameter(4, operation).setParameter(5, is_delivery).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public UncontainerizedService findDischargableUcByNoPpkbAndBlNo(String noPpkb, String blNo) {
        try {
            return (UncontainerizedService) getEntityManager().createNamedQuery("UncontainerizedService.findDischargableUcByNoPpkbAndBlNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("blNo", blNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    /**
     *
     * @param blNo
     * @return
     *      0. id_uc
     *      1. no_ppkb
     *      2. bl_no
     *      3. commodity
     *      4. unit
     *      5. weight
     *      6. truck_loosing
     *      7. block
     */
    public Object[] findDeliverableUcByBlNo(String blNo) {
        try {
            return (Object[]) getEntityManager().createNamedQuery("UncontainerizedService.Native.findDeliverableUcByBlNo")
                    .setParameter(1, blNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Object[] findByBlNoMobile(String bl_no, String status, String operation, Integer is_delivery) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("UncontainerizedService.Native.findByBlNoMobile").setParameter(1, bl_no).setParameter(2, status).setParameter(3, operation).setParameter(4, is_delivery).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public UncontainerizedService findUcByStatusOperationAndDeliveryStatus(String noPpkb, String blNo, String status, String operation, Integer isDelivery) {
        try {
            return (UncontainerizedService) getEntityManager().createNamedQuery("UncontainerizedService.findUcByStatusOperationAndDeliveryStatus")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("blNo", blNo)
                    .setParameter("status", status)
                    .setParameter("operation", operation)
                    .setParameter("isDelivery", isDelivery)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public UncontainerizedService findByNoPpkbAndBlNo(String noPpkb, String blNo) {
        try {
            return (UncontainerizedService) getEntityManager().createNamedQuery("UncontainerizedService.findByNoPpkbAndBlNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("blNo", blNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public int resetPlannedUcServiceByNoReg(String noReg) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("UncontainerizedService.resetPlannedUcServiceByNoReg")
                .setParameter("noReg", noReg)
                .executeUpdate();
    }

    public int deleteUnplannedUcServiceByNoReg(String noReg) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("UncontainerizedService.deleteUnplannedUcServiceByNoReg")
                .setParameter("noReg", noReg)
                .executeUpdate();
    }

    public List<Object[]> findDeliveredUcsByNoPpkb(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("UncontainerizedService.Native.findDeliveredUcsByNoPpkb")
                .setParameter(1, no_ppkb)
                .getResultList()
        );
    }

    public List<Object[]> findDeliverableUcs(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("UncontainerizedService.Native.findDeliverableUcs").setParameter(1, no_ppkb).getResultList()
        );
    }
    
    //penambahan perubahan bentuk form ny ade chelsea tanggal 17 maret 2014
    public List<Object[]> findDeliverableUcs2() {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("UncontainerizedService.Native.findDeliverableUcs2").getResultList()
        );
    }

    public List<Object[]> findShifting(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("UncontainerizedService.Native.findShifting").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findShiftingWithout(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts( 
                    getEntityManager().createNamedQuery("UncontainerizedService.Native.findShiftingWithout").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public Integer findWeightByPpkbNBl(String no_ppkb, String bl_no) {
        Integer temp;
        try {
            temp = (Integer) getEntityManager().createNamedQuery("UncontainerizedService.Native.findWeightByPpkbNBl").setParameter(1, no_ppkb).setParameter(2, bl_no).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public int findUncontainerizedServiceId(String no_ppkb, String bl_no) {
        return (Integer) getEntityManager().createNamedQuery("UncontainerizedService.Native.findUncontainerizedServiceId").setParameter(1, no_ppkb).setParameter(2, bl_no).getSingleResult();
    }

    public Object[] findByBlNoMobileUc(String bl_no) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts( 
                    (Object[]) getEntityManager().createNamedQuery("UncontainerizedService.Native.findByBlNoMobileUc").setParameter(1, bl_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByUcMonitoringFrontDischarge(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts( 
                    getEntityManager().createNamedQuery("UncontainerizedService.Native.findByUcMonitoringFrontDischarge").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByUcMonitoringFrontLoad(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts( 
                    getEntityManager().createNamedQuery("UncontainerizedService.Native.findByUcMonitoringFrontLoad").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public Object[] findShiftingWithoutMobile(String no_ppkb, String bl_no) {
        Object[] temp = new Object[10];
        try {
            temp =  objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("UncontainerizedService.Native.findShiftingWithoutMobile").setParameter(1, no_ppkb).setParameter(2, bl_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findShiftingMobile(String no_ppkb, String bl_no) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts( 
                    (Object[]) getEntityManager().createNamedQuery("UncontainerizedService.Native.findShiftingMobile").setParameter(1, no_ppkb).setParameter(2, bl_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findForLoadConfirmList(String no_ppkb, String status, String operation) {
        return objectsDecimalsToObjectsInts( 
                getEntityManager().createNamedQuery("UncontainerizedService.Native.findForLoadConfirmList").setParameter(1, no_ppkb).setParameter(2, status).setParameter(3, operation).getResultList()
        );
    }

    public List<Object[]> findForLoadConfirmByTL(String no_ppkb) {
        return objectsDecimalsToObjectsInts( 
                getEntityManager().createNamedQuery("UncontainerizedService.Native.findForLoadConfirmByTL").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<UncontainerizedService> findUCThatHaveNotReachedCY(String noPpkb) {
        return getEntityManager().createNamedQuery("UncontainerizedService.findUCThatHaveNotReachedCY").setParameter("noPpkb", noPpkb).getResultList();
    }

    public List<UncontainerizedService> findUCThatHaveNotBeenLoaded(String noPpkb) {
        return getEntityManager().createNamedQuery("UncontainerizedService.findUCThatHaveNotBeenLoaded").setParameter("noPpkb", noPpkb).getResultList();
    }

    public List<Object[]> findUncontainerizedTranshipment(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("UncontainerizedService.Native.findUncontainerizedTranshipment").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findUncontainerizedAfterTranshipment(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("UncontainerizedService.Native.findUncontainerizedAfterTranshipment").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<UncontainerizedService> findTranshipmentUCThatHaveReachedCY(String noPpkb) {
        return getEntityManager().createNamedQuery("UncontainerizedService.findTranshipmentUCThatHaveReachedCY").setParameter("noPpkb", noPpkb).getResultList();
    }

    public List<UncontainerizedService> findTranshipmentUCThatHadBeenLoaded(String noPpkb) {
        return getEntityManager().createNamedQuery("UncontainerizedService.findTranshipmentUCThatHadBeenLoaded").setParameter("noPpkb", noPpkb).getResultList();
    }

    public List<Object[]> findUncontainerizedBayPlanLoadList(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("UncontainerizedService.Native.findUncontainerizedBayPlanLoadList").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findUncontainerizedBayPlanLoadListSelect(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts( 
                    getEntityManager().createNamedQuery("UncontainerizedService.Native.findUncontainerizedBayPlanLoadListSelect").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
    
    public Integer findUncontainerizedBayPlanLoadId(String no_ppkb, String bl_no) {
        Integer temp=0;
        try {
            temp = (Integer) getEntityManager().createNamedQuery("UncontainerizedService.Native.findUncontainerizedBayPlanLoadId").setParameter(1, no_ppkb).setParameter(2, bl_no).getSingleResult();
        } catch (NoResultException ex) {
            temp = 0;
        }
        return temp=0;
    }

    public List<UncontainerizedService> findDeliveryUCThatHaveReachedCY(String noPpkb) {
        return getEntityManager().createNamedQuery("UncontainerizedService.findDeliveryUCThatHaveReachedCY")
                .setParameter("noPpkb", noPpkb)
                .getResultList();
    }

    public List<UncontainerizedService> findReceivingUcThatHadBeenLoaded(String noPpkb) {
        return getEntityManager().createNamedQuery("UncontainerizedService.findReceivingUcThatHadBeenLoaded")
                .setParameter("noPpkb", noPpkb)
                .getResultList();
    }

    public List<Object[]> findHandlingDischarge(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts( 
                    getEntityManager().createNamedQuery("UncontainerizedService.Native.findHandlingDischarge").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findHandlingLoad(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("UncontainerizedService.Native.findHandlingLoad").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    @Override
    public List<UncontainerizedService> findByNoPpkbForRecapJasaDermagaUC(String noPpkb) {
        try {
            return (List<UncontainerizedService>) getEntityManager().createNamedQuery("UncontainerizedService.findByNoPpkbForRecapJasaDermagaUC")
                    .setParameter("noPpkb", noPpkb)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
}

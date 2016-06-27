/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.CancelLoadingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class CancelLoadingServiceFacade extends AbstractFacade<CancelLoadingService> implements CancelLoadingServiceFacadeRemote, CancelLoadingServiceFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public CancelLoadingServiceFacade() {
        super(CancelLoadingService.class);
    }

    public List<Object[]> findCancelLoadingServiceByNoreg(String no_reg) {
        return getEntityManager().createNamedQuery("CancelLoadingService.Native.findCancelLoadingServiceByNoreg").setParameter(1, no_reg).getResultList();
    }

    /**
     *
     * @param noReg
     * @return
     *      0. job_slip
     *      1. bl_no
     *      2. cont_no
     *      3. cont_size
     *      4. cont_type
     *      5. cont_status
     *      6. seal_no
     *      7. dg
     *      8. dg_label
     *      9. over_size
     *      10. cont_gross
     *      11. pull_out
     *      12. category
     *      13. status
     *      14. posisi
     *      15. default_fraction_digits
     *      16. language
     *      17. country
     *      18. placement_date
     *      19. masa_1
     *      20. masa_2
     *      21. charge_m1
     *      22. charge_m2
     *      23. penumpukan_charge
     *      24. cancel_charge
     *      25. pass_gate_charge
     *      26. new_ppkb
     *      27. upah_buruh_charge
     *      28. is_transhipment
     * 
     */
    public List<Object[]> findPerhitungan(String noReg) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("CancelLoadingService.Native.findPerhitungan")
                .setParameter(1, noReg)
                .getResultList()
        );
    }

    /**
     *
     * @param noPpkb
     * @return
     *      0. job_slip
     *      1. cont_no
     *      2. cont_size
     *      3. type_in_general
     *      4. cont_status
     *      5. cont_gross
     *      6. block
     *      7. y_slot
     *      8. y_row
     *      9. y_tier
     *      10. seal_no
     *      11. vessel_bay
     *      12. vessel_row
     *      13. vessel_tier
     */
    public List<Object[]> findDeliverableContainersByPpkb(String noPpkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("CancelLoadingService.Native.findDeliverableContainersByPpkb")
                .setParameter(1, noPpkb)
                .getResultList()
        );
    }
    
    //penambahan nama kapal , vo in voy out by ade chelsea tanggal 17 maret 2014
    public List<Object[]> findDeliverableContainersByPpkb2() {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("CancelLoadingService.Native.findDeliverableContainersByPpkb2")
                .getResultList()
        );
    }

    /**
     *
     * @param noPpkb
     * @return
     *      0. job_slip
     *      1. cont_no
     *      2. cont_size
     *      3. type_in_general
     *      4. cont_status
     *      5. cont_gross
     *      6. block
     *      7. y_slot
     *      8. y_row
     *      9. y_tier
     *      10. seal_no
     *      11. vessel_bay
     *      12. vessel_row
     *      13. vessel_tier
     */
    public List<Object[]> findDeliveredContainersByPpkb(String noPpkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("CancelLoadingService.Native.findDeliveredContainersByPpkb")
                .setParameter(1, noPpkb)
                .getResultList()
        );
    }
    
    public String generateId(String bulan) {
        return (String) getEntityManager().createNamedQuery("CancelLoadingService.Native.generateId").setParameter(1, bulan).getSingleResult();
    }

    public List<String> findGateInPassableJobslips(String jobslip) {
        return getEntityManager().createNamedQuery("CancelLoadingService.Native.findGateInPassableJobslips").setParameter(1, jobslip).getResultList();
    }

    public List<Object[]> findByChangeDestination(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("CancelLoadingService.Native.findByChangeDestination").setParameter(1, no_ppkb).getResultList()
        );
    }

    public Object[] findCancelLoadingServiceByDestination(String no_ppkb, String cont_no) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("CancelLoadingService.Native.findCancelLoadingServiceByDestination").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }

    /**
     *
     * @param contNo
     * @return
     *      0. job_slip
     *      1. cont_no
     *      2. cont_size
     *      3. type_in_general
     *      4. cont_status
     *      5. cont_gross
     *      6. seal_no
     *      7. over_size
     *      8. dg
     *      9. cont_damage
     *      10. vessel_name
     *      11. block
     *      12. y_slot
     *      13. y_row
     *      14. y_tier
     */
    public Object[] findDeliverableContainerByContNo(String contNo) {
        try {
            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("CancelLoadingService.Native.findDeliverableContainerByContNo")
                    .setParameter(1, contNo)
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<Object[]> findHasDischargedContainerByNoPpkb(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("CancelLoadingService.Native.findHasDischargedContainerByNoPpkb").setParameter(1, no_ppkb).getResultList()
        );
    }

    @Override
    public List<Object[]> findHasLiftedOffByNoPpkb(String noPpkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("CancelLoadingService.Native.findHasLiftedOffByNoPpkb").setParameter(1, noPpkb).getResultList()
        );
    }

    @Override
    public List<Object[]> findLiftableOffByNoPpkb(String noPpkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("CancelLoadingService.Native.findLiftableOffByNoPpkb").setParameter(1, noPpkb).getResultList()
        );
    }

    public List<String> findCancelableContainerByNoPpkb(String noPpkb, String contNo) {
        return getEntityManager().createNamedQuery("CancelLoadingService.Native.findCancelableContainerByNoPpkb")
                .setParameter(1, noPpkb)
                .setParameter(2, contNo)
                .getResultList();
    }

    public List<Object[]> findDischargableContainerByNoPpkb(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("CancelLoadingService.Native.findDischargableContainerByNoPpkb").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<CancelLoadingService> findByNoReg(String noReg) {
        return getEntityManager().createNamedQuery("CancelLoadingService.findByNoReg")
                .setParameter("noReg", noReg)
                .getResultList();
    }

    public Object[] findGateInPassableByJobSlip(String job_slip) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("CancelLoadingService.Native.findGateInPassableByJobSlip").setParameter(1, job_slip).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }

    public List<Object[]> findByPPKB(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("CancelLoadingService.Native.findByPPKB").setParameter(1, no_ppkb).getResultList()
        );
    }

    public Object findByPPKBandCont(String no_ppkb, String no_cont, Boolean destination) {
        Object temp = new Object();
        try {
            temp = (Object) getEntityManager().createNamedQuery("CancelLoadingService.Native.findByPPKBandCont").setParameter(1, no_ppkb).setParameter(2, no_cont).setParameter(3, destination).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findByPpkbAndContMobile(String no_ppkb, String cont_no) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("CancelLoadingService.Native.findByPpkbAndContMobile").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }

    public Object[] findByPpkbAndContMobileDelivery(String no_ppkb, String cont_no) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("CancelLoadingService.Native.findByPpkbAndContMobileDelivery").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }

    public List<Object[]> findByListBatalNotaService(String no_ppkb, String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("CancelLoadingService.Native.findByListBatalNotaService").setParameter(1, no_ppkb).setParameter(2, no_reg).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
    public List<Object[]> findByNoregValidasiPrint(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("CancelLoadingService.Native.findByNoregValidasiPrint").setParameter(1, no_reg).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    @Override
    public CancelLoadingService findByNewPpkbAndRelocationStatus(String noPpkb, String contNo, Boolean hasRelocated) {
        try {
            return (CancelLoadingService) getEntityManager().createNamedQuery("CancelLoadingService.findByNewPpkbAndRelocationStatus")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .setParameter("hasRelocated", hasRelocated)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    public CancelLoadingService findDischargableContainerByContNoAndPpkb(String noPpkb, String contNo) {
        try {
            return (CancelLoadingService) getEntityManager().createNamedQuery("CancelLoadingService.findDischargableContainerByContNoAndPpkb")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    public CancelLoadingService findLiftableOffByContNo(String contNo) {
        try {
            return (CancelLoadingService) getEntityManager().createNamedQuery("CancelLoadingService.findLiftableOffByContNo")
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    public CancelLoadingService findByNoPpkbContNoAndStatusDelivery(String noPpkb, String contNo, Boolean isDelivery) {
        try {
            return (CancelLoadingService) getEntityManager().createNamedQuery("CancelLoadingService.findByNoPpkbContNoAndStatusDelivery")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .setParameter("isDelivery", isDelivery)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    public CancelLoadingService findByNewPpkbAndContNo(String noPpkb, String contNo) {
        try {
            return (CancelLoadingService) getEntityManager().createNamedQuery("CancelLoadingService.findByNewPpkbAndContNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    public List<Object[]> findStuffAbleContainers(String noPpkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("CancelLoadingService.Native.findStuffAbleContainers")
                .setParameter(1, noPpkb)
                .getResultList()
        );
    }

    @Override
    public CancelLoadingService findByNoPpkbAndContNo(String noPpkb, String contNo) {
        try {
            return (CancelLoadingService) getEntityManager().createNamedQuery("CancelLoadingService.findByNoPpkbAndContNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    @Override
    public CancelLoadingService findMovableContainer(String contNo) {
        try {
            return (CancelLoadingService) getEntityManager().createNamedQuery("CancelLoadingService.findMovableContainer")
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    @Override
    public CancelLoadingService findMovableOffContainer(String contNo) {
        try {
            return (CancelLoadingService) getEntityManager().createNamedQuery("CancelLoadingService.findMovableOffContainer")
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    public List<Object[]> findCashierCancelLoading(String noPpkb, String contNo) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("CancelLoadingService.Native.findCashierCancelLoading").setParameter(1, noPpkb).setParameter(2, contNo).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    @Override
    public List<String> findLikeNoPPKB(String noPpkb) {
        List<String> result = new ArrayList<String>();
        String sql = "select distinct(cls.no_ppkb) from cancel_loading_service cls, planning_vessel pv "
                + " where cls.no_ppkb=pv.no_ppkb "
                + " and cls.no_ppkb like '%" + noPpkb + "%' and "
                + " (pv.status='Servicing' or pv.status='Served' or pv.status='Approved') and rownum <= 10";
        result = getEntityManager().createNativeQuery(sql).getResultList();
        return result;
    }

    @Override
    public List<String> findLikeContNo(String noPpkb, String contNo) {
        List<String> result = new ArrayList<String>();
        String sql = "select cont_no from cancel_loading_service where no_ppkb = '" + noPpkb
                + "' and cont_no like '%" + contNo + "%' and rownum <= 10";
        result = getEntityManager().createNativeQuery(sql).getResultList();
        return result;
    }

    @Override
    public List<Object[]> findByNoPpkbAndContNoUpdateDelivery(String noPpkb, String contNo) {
        List<Object[]> result = new ArrayList<Object[]>();
        String sql = "select no_ppkb, bl_no, cont_no, cont_size, block, y_slot, y_row, y_tier from cancel_loading_service where no_ppkb = '" + noPpkb + "' "
                + " and cont_no like '%" + contNo + "%';";
        result = objectsDecimalsToObjectsInts(getEntityManager().createNativeQuery(sql).getResultList());
        return result;
    }
}

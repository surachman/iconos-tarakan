/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PlanningContLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.master.MasterPort;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author dycoder
 */
@Stateless
public class PlanningContLoadFacade extends AbstractFacade<PlanningContLoad> implements PlanningContLoadFacadeRemote, PlanningContLoadFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PlanningContLoadFacade() {
        super(PlanningContLoad.class);
    }

    @Override
    public List<Object[]> findPlanningBayplanLoadsByPpkb(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContLoad.Native.findPlanningBayplanLoadsByPpkb").setParameter(1, no_ppkb).getResultList()
        );
    }
    
    public List<Object[]> findLoadingListByPpkb(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContLoad.Native.findLoadingListByPpkb")
                .setParameter(1, no_ppkb)
                .getResultList()
        );
    }

    public List<Object[]> findPlanningContLoads() {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContLoad.Native.findPlanningContLoads").getResultList()
        );
    }

    public List<Object[]> findPlanningContLoads3(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContLoad.Native.findPlanningContLoads3").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findLoadCandidatesPerCustomer(String custCode) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContLoad.Native.findLoadCandidatesPerCustomer")
                .setParameter(1, custCode)
                .getResultList()
        );
    }

    public Object[] findLoadCandidatesPerCustomer_summary(String custCode) {
        try {
            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PlanningContLoad.Native.findLoadCandidatesPerCustomer_summary")
                    .setParameter(1, custCode)
                    .getSingleResult()
            );
        } catch (NoResultException nre) {
            return new Object[] {custCode, 0, 0, 0, 0};
        }
    }

    public List<Object[]> findPlanningContLoadsTranshipment(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContLoad.Native.findPlanningContLoadsTranshipment").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findPluggableOnContainersByPpkb(String noPpkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContLoad.Native.findPluggableOnContainersByPpkb").setParameter(1, noPpkb).getResultList()
        );
    }

    public List<Object[]> findGrossCapacityByNoPpkbAndBay(String noPpkb, Short bay, Integer minRow, Integer maxRow, Integer minTier, Integer maxTier) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContLoad.Native.findGrossCapacityByNoPpkbAndBay")
                .setParameter(1, minRow)
                .setParameter(2, maxRow)
                .setParameter(3, bay)
                .setParameter(4, noPpkb)
                .setParameter(5, minTier)
                .setParameter(6, maxTier)
                .getResultList()
        );
    }
    /*
    public List<Object[]> findServiceContDischargesStuffingService(String no_ppkb) {
    List<Object[]> temp = new ArrayList<Object[]>();
    try {
    temp = getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesStuffingService").setParameter(1, no_ppkb).getResultList();
    } catch (NoResultException e) {
    temp = null;
    }
    return temp;
    }
     */

    public List<Object[]> findPlanningBayplanLoadCekNotCompleted(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("PlanningContLoad.Native.findPlanningBayplanLoadCekNotCompleted").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException e) {
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
     *      11 = is_transhipment,
     *      12 = is_shifting,
     *      13 = cont_damage
     */
    public Object[] findLiftableOnContainer(String noPpkb) {
        try {
            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PlanningContLoad.Native.findLiftableOnContainer")
                    .setParameter(1, noPpkb)
                    .getSingleResult()
            );
        } catch (NoResultException e) {
            return null;
        }
    }

    public Object[] findPluggableContainer(String contNo) {
        try {
            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PlanningContLoad.Native.findPluggableContainer")
                    .setParameter(1, contNo)
                    .getSingleResult()
            );
        } catch (NoResultException e) {
            return null;
        }
    }

    public java.util.List<java.lang.Object[]> findCountableLoadReefers(String noPpkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContLoad.Native.findCountableLoadReefers")
                .setParameter(1, noPpkb)
                .getResultList()
        );
    }

    public PlanningContLoad findByNoPpkbAndContNoExcludeCancelLoading(String noPpkb, String contNo) {
        try {
            return (PlanningContLoad) getEntityManager().createNamedQuery("PlanningContLoad.findByNoPpkbAndContNoExcludeCancelLoading")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public PlanningContLoad findByCancelableContainer(String noPpkb, String contNo) {
        try {
            return (PlanningContLoad) getEntityManager().createNamedQuery("PlanningContLoad.findByCancelableContainer")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public PlanningContLoad findByNoPpkbContNoAndStatusCancelLoading(String noPpkb, String contNo, Boolean statusCancelLoading) {
        try {
            return (PlanningContLoad) getEntityManager().createNamedQuery("PlanningContLoad.findByNoPpkbContNoAndStatusCancelLoading")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .setParameter("statusCancelLoading", statusCancelLoading)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<PlanningContLoad> findNotPlannedContainerByNoPpkbAndYardLocation(String noPpkb, String block, Short slot) {
        return getEntityManager().createNamedQuery("PlanningContLoad.findNotPlannedContainerByNoPpkbAndYardLocation")
                .setParameter("noPpkb", noPpkb)
                .setParameter("block", block)
                .setParameter("slot", slot)
                .getResultList();
    }

    public List<PlanningContLoad> findNotPlannedContainerByPodNoPpkbAndYardLocation(String noPpkb, String block, Short slot, String pod) {
        return getEntityManager().createNamedQuery("PlanningContLoad.findNotPlannedContainerByPodNoPpkbAndYardLocation")
                .setParameter("noPpkb", noPpkb)
                .setParameter("block", block)
                .setParameter("slot", slot)
                .setParameter("dischPort", pod)
                .getResultList();
    }

    public List<String> findPodByNoPpkbAndYardLocation(String noPpkb, String block, Short slot) {
        return getEntityManager().createNamedQuery("PlanningContLoad.Native.findPodByNoPpkbAndYardLocation")
                .setParameter(1, noPpkb)
                .setParameter(2, block)
                .setParameter(3, slot)
                .getResultList();
    }

    public List<PlanningContLoad> findPlannedContainerByBayLocation(String noPpkb, Short bay) {
        return getEntityManager().createNamedQuery("PlanningContLoad.findPlannedContainerByBayLocation")
                .setParameter("noPpkb", noPpkb)
                .setParameter("bay", bay)
                .getResultList();
    }

    public List<Object[]> findPlannedContainerByBayLocationAsObjectArray(String noPpkb, Short bay) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContLoad.Native.findPlannedContainerByBayLocation")
                .setParameter(1, bay)
                .setParameter(2, noPpkb)
                .getResultList()
        );
    }

    public int deleteByNoPpkbAndContNo(String noPpkb, String contNo) {
        return getEntityManager().createNamedQuery("PlanningContLoad.deleteByNoPpkbAndContNo")
                .setParameter("contNo", contNo)
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }

    public Object[] findByContNo(String cont_no, String pos, Boolean trans) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PlanningContLoad.Native.findByContNo").setParameter(1, cont_no).setParameter(2, pos).setParameter(3, trans).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

     public Object[] PlanningContLoadfindCancelLoading(String no_ppkb, String cont_no) {
        Object[] temp = new Object[2];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PlanningContLoad.Native.PlanningContLoadfindCancelLoading").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }


    public Integer findByContNoAndPpkbByEdit(String no_ppkb, String cont_no) {
        Integer temp ;
        try {
            temp = ((BigDecimal) getEntityManager().createNamedQuery("PlanningContLoad.Native.findByContNoAndPpkbByEdit").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult()).intValue();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }



    public Object[] findPlanningBayplanLoadCekBayExist(short bay, short row, short tier,String no_ppkb) {
        try {
            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PlanningContLoad.Native.findPlanningBayplanLoadCekBayExist").setParameter(1, bay).setParameter(2, row).setParameter(3, tier).setParameter(4, no_ppkb).getSingleResult()
            );
        } catch (NoResultException ex) {
            return null;
        } catch (NonUniqueResultException nuse) {
            return new Object[] {
                "", "", "", "error", " ", "error"
            };
        }
    }

    public Object[] findByContNoLiftOn(String no_ppkb, String cont_no) {
        Object[] temp = new Object[3];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PlanningContLoad.Native.findByContNoLiftOn").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public PlanningContLoad findMovableContainer(String contNo) {
        try {
            return (PlanningContLoad) getEntityManager().createNamedQuery("PlanningContLoad.findMovableContainer")
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public PlanningContLoad findMovableOffContainer(String contNo) {
        try {
            return (PlanningContLoad) getEntityManager().createNamedQuery("PlanningContLoad.findMovableOffContainer")
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Integer setCompletedByNoPpkb(String noPpkb) {
        return getEntityManager().createNamedQuery("PlanningContLoad.updateCompleteStatusByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .setParameter("completed", true)
                .executeUpdate();
    }


    public Integer updateStatusCancelLoadingByNoPpkb(String noPpkb, String statusCancelLoading) {
        return getEntityManager().createNamedQuery("PlanningContLoad.updateStatusCancelLoadingByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .setParameter("statusCancelLoading", statusCancelLoading)
                .executeUpdate();
    }

    public int updatePlanningVessel(PlanningVessel newValue, PlanningVessel oldValue) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PlanningContLoad.updatePlanningVessel")
                .setParameter("newValue", newValue)
                .setParameter("oldValue", oldValue)
                .executeUpdate();
    }

    @Override
    public int updatePlanningVesselByContNo(PlanningVessel newValue, MasterPort nextPort, PlanningVessel oldValue, List<String> containers) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PlanningContLoad.updatePlanningVesselByContNo")
                .setParameter("newValue", newValue)
                .setParameter("dischPort", nextPort.getPortCode())
                .setParameter("portOfDelivery", nextPort)
                .setParameter("oldValue", oldValue)
                .setParameter("containers", containers)
                .executeUpdate();
    }

    public List<PlanningContLoad> findContainersThatHaveNotBeenLoaded(String noPpkb) {
        List<String> positions = Arrays.asList("02", "03", "MV");

        return getEntityManager().createNamedQuery("PlanningContLoad.findByPpkbPositionsAndNotCancelLoading")
                .setParameter("noPpkb", noPpkb)
                .setParameter("positions", positions)
                .getResultList();
    }

    @Override
    public PlanningContLoad findByNoPpkbAndContNoUpdateLiftOn(String noPpkb, String contNo) {
        return (PlanningContLoad)getEntityManager().createNamedQuery("PlanningContLoad.findByNoPpkbAndContNo")
                .setParameter("noPpkb", noPpkb).setParameter("contNo", contNo).getSingleResult();
    }

    @Override
    public List<Object[]> findMonitoringCustoms() {
    /*
        String sql = "SELECT p.cont_no,p.cont_size,m.type_in_general,p.cont_status,mc.name as commodity,p.cont_gross,change(p.dg) as dangerous, "
                + " CASE WHEN mc.dangerous_class IS NULL THEN 'NON DG' ELSE mc.dangerous_class END, "
                + " p.no_ppkb,mv.name as vessel_name, ps.voy_in as voyage,"
                + " CASE WHEN pv.tipe_pelayaran = 'd' THEN 'Domestik'::text ELSE 'International'::text END as shipping_type, "
                + " mct.name as agent,'LOAD'::text as operation "
                + " FROM planning_cont_load p, m_container_type m, m_commodity mc,planning_vessel pv,preservice_vessel ps,m_vessel mv, m_customer mct "
                + " WHERE p.is_export_import = TRUE AND p.position='03' AND p.is_transhipment  = FAlSE AND m.cont_type = p.cont_type AND mc.commodity_code = p.commodity_code AND p.no_ppkb = pv.no_ppkb AND pv.booking_code = ps.booking_code AND ps.cust_code = mct.cust_code AND ps.vessel_code = mv.vessel_code "
                + " UNION ALL "
                + " SELECT p.cont_no,p.cont_size,m.type_in_general,p.cont_status,mc.name as commodity,p.cont_gross,change(p.dangerous) as dangerous, "
                + " CASE WHEN mc.dangerous_class IS NULL THEN 'NON DG' ELSE mc.dangerous_class END, "
                + " p.no_ppkb,mv.name as vessel_name, ps.voy_in as voyage, CASE WHEN pv.tipe_pelayaran = 'd' THEN 'Domestik'::text ELSE 'International'::text END as shipping_type, "
                + " mct.name as agent,'DISCHARGE'::text as operation "
                + " FROM service_cont_discharge p, m_container_type m, m_commodity mc,planning_vessel pv,preservice_vessel ps,m_vessel mv, m_customer mct "
                + " WHERE p.is_import = TRUE AND p.position='03' AND p.is_delivery=FALSE AND m.cont_type = p.cont_type AND mc.commodity_code = p.commodity_code AND p.no_ppkb = pv.no_ppkb AND pv.booking_code = ps.booking_code AND ps.cust_code = mct.cust_code AND ps.vessel_code = mv.vessel_code "
                + " UNION ALL "
                + " SELECT p.cont_no,p.cont_size,m.type_in_general,p.cont_status,mc.name as commodity,p.cont_gross,change(p.dg) as dangerous, "
                + " CASE WHEN mc.dangerous_class IS NULL THEN 'NON DG' ELSE mc.dangerous_class END, "
                + " p.no_ppkb,mv.name as vessel_name,CASE WHEN pv.tipe_pelayaran = 'd' THEN 'Domestik'::text ELSE 'International'::text END as shipping_type, "
                + " ps.voy_in as voyage,mct.name as agent,'TRANSHIPMENT'::text as operation "
                + " FROM planning_cont_load p, m_container_type m, m_commodity mc,planning_vessel pv,preservice_vessel ps,m_vessel mv, m_customer mct "
                + " WHERE p.is_export_import = TRUE AND p.position='03' AND p.is_transhipment  = TRUE AND m.cont_type = p.cont_type AND mc.commodity_code = p.commodity_code AND p.no_ppkb = pv.no_ppkb AND pv.booking_code = ps.booking_code AND ps.cust_code = mct.cust_code AND ps.vessel_code = mv.vessel_code";
        */
        /*
            Author Change Srach
        */
        String sql = "SELECT p.cont_no, " 
        +"  p.cont_size, " 
        +"  m.type_in_general, " 
        +"  p.cont_status, " 
        +"  mc.name AS commodity, " 
        +"  p.cont_gross, " 
        +"  change(p.dg) AS dangerous, " 
        +"  CASE " 
        +"    WHEN mc.dangerous_class IS NULL " 
        +"    THEN 'NON DG' " 
        +"    ELSE mc.dangerous_class " 
        +"  END, " 
        +"  p.no_ppkb, " 
        +"  mv.name   AS vessel_name, " 
        +"  ps.voy_in AS voyage, " 
        +"  CASE " 
        +"    WHEN pv.tipe_pelayaran = 'd' " 
        +"    THEN 'Domestik' " 
        +"    ELSE 'International' " 
        +"  END      AS shipping_type, " 
        +"  mct.name AS agent, " 
        +"  'LOAD'   AS operation " 
        +"FROM planning_cont_load p, " 
        +"  m_container_type m, " 
        +"  m_commodity mc, " 
        +"  planning_vessel pv, " 
        +"  preservice_vessel ps, " 
        +"  m_vessel mv, " 
        +"  m_customer mct " 
        +"WHERE p.is_export_import = 'TRUE' " 
        +"AND p.position           ='03' " 
        +"AND p.is_transhipment    = 'FAlSE' " 
        +"AND m.cont_type          = p.cont_type " 
        +"AND mc.commodity_code    = p.commodity_code " 
        +"AND p.no_ppkb            = pv.no_ppkb " 
        +"AND pv.booking_code      = ps.booking_code " 
        +"AND ps.cust_code         = mct.cust_code " 
        +"AND ps.vessel_code       = mv.vessel_code " 
        +"UNION ALL " 
        +"SELECT p.cont_no, " 
        +"  p.cont_size, " 
        +"  m.type_in_general, " 
        +"  p.cont_status, " 
        +"  mc.name AS commodity, " 
        +"  p.cont_gross, " 
        +"  change(p.dangerous) AS dangerous, " 
        +"  CASE " 
        +"    WHEN mc.dangerous_class IS NULL " 
        +"    THEN 'NON DG' " 
        +"    ELSE mc.dangerous_class " 
        +"  END, " 
        +"  p.no_ppkb, " 
        +"  mv.name   AS vessel_name, " 
        +"  ps.voy_in AS voyage, " 
        +"  CASE " 
        +"    WHEN pv.tipe_pelayaran = 'd' " 
        +"    THEN 'Domestik' " 
        +"    ELSE 'International' " 
        +"  END         AS shipping_type, " 
        +"  mct.name    AS agent, " 
        +"  'DISCHARGE' AS operation " 
        +"FROM service_cont_discharge p, " 
        +"  m_container_type m, " 
        +"  m_commodity mc, " 
        +"  planning_vessel pv, " 
        +"  preservice_vessel ps, " 
        +"  m_vessel mv, " 
        +"  m_customer mct " 
        +"WHERE p.is_import     = 'TRUE' " 
        +"AND p.position        ='03' " 
        +"AND p.is_delivery     ='FALSE' " 
        +"AND m.cont_type       = p.cont_type " 
        +"AND mc.commodity_code = p.commodity_code " 
        +"AND p.no_ppkb         = pv.no_ppkb " 
        +"AND pv.booking_code   = ps.booking_code " 
        +"AND ps.cust_code      = mct.cust_code " 
        +"AND ps.vessel_code    = mv.vessel_code " 
        +"UNION ALL " 
        +"SELECT p.cont_no, " 
        +"  p.cont_size, " 
        +"  m.type_in_general, " 
        +"  p.cont_status, " 
        +"  mc.name AS commodity, " 
        +"  p.cont_gross, " 
        +"  change(p.dg) AS dangerous, " 
        +"  CASE " 
        +"    WHEN mc.dangerous_class IS NULL " 
        +"    THEN 'NON DG' " 
        +"    ELSE mc.dangerous_class " 
        +"  END, " 
        +"  p.no_ppkb, " 
        +"  mv.name AS vessel_name, " 
        +"  CASE " 
        +"    WHEN pv.tipe_pelayaran = 'd' " 
        +"    THEN 'Domestik' " 
        +"    ELSE 'International' " 
        +"  END            AS shipping_type, " 
        +"  ps.voy_in      AS voyage, " 
        +"  mct.name       AS agent, " 
        +"  'TRANSHIPMENT' AS operation " 
        +"FROM planning_cont_load p, " 
        +"  m_container_type m, " 
        +"  m_commodity mc, " 
        +"  planning_vessel pv, " 
        +"  preservice_vessel ps, " 
        +"  m_vessel mv, " 
        +"  m_customer mct " 
        +"WHERE p.is_export_import = 'TRUE' " 
        +"AND p.position           ='03' " 
        +"AND p.is_transhipment    = 'TRUE' " 
        +"AND m.cont_type          = p.cont_type " 
        +"AND mc.commodity_code    = p.commodity_code " 
        +"AND p.no_ppkb            = pv.no_ppkb " 
        +"AND pv.booking_code      = ps.booking_code " 
        +"AND ps.cust_code         = mct.cust_code " 
        +"AND ps.vessel_code       = mv.vessel_code";
        
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNativeQuery(sql).getResultList()
        );
    }

    @Override
    public List<PlanningContLoad> findByNoPpkb(String noPpkb) {
        return getEntityManager().createNamedQuery("PlanningContLoad.findByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .getResultList();
    }

    @Override
    public List findContainerStatus(String noPPKB) {
         return getEntityManager().createNamedQuery("PlanningContLoad.Native.findContainerStatus").setParameter(1, noPPKB).getResultList();
    }

    @Override
    public List<PlanningContLoad> findTheSameContainer(String noPpkb, String contNo) {
        return 
                getEntityManager().createNamedQuery("PlanningContLoad.findByNoPpkbAndContNo").setParameter("noPpkb", noPpkb).setParameter("contNo", contNo).getResultList();
    }

    @Override
    public List<PlanningContLoad> findByPpkbNotCompleted(String noPPKB) {
        return getEntityManager().createNamedQuery("PlanningContLoad.findByPpkbNotCompleted").setParameter("noPpkb", noPPKB).getResultList();
    }
}

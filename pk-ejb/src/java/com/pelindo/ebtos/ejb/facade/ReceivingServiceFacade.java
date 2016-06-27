/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PlanningContReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ReceivingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterPort;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import java.util.Date;
import javax.ejb.EJB;
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
public class ReceivingServiceFacade extends AbstractFacade<ReceivingService> implements ReceivingServiceFacadeRemote, ReceivingServiceFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;
    
    @EJB
    private PlanningContReceivingFacadeRemote planningContReceivingFacadeRemote;

    @EJB
    private PlanningContReceivingFacadeLocal planningContReceivingFacadeLocal;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ReceivingServiceFacade() {
        super(ReceivingService.class);
    }

    public List<ReceivingService> findByNoReg(String noReg) {
        return getEntityManager().createNamedQuery("ReceivingService.findByNoReg")
                .setParameter("noReg", noReg)
                .getResultList();
    }

    public void removeThatRelatedToReceiving(String noReg) {
        List<ReceivingService> receivingServices = findByNoReg(noReg);
        
        for (ReceivingService receivingService: receivingServices) {
            remove(receivingService);
            planningContReceivingFacadeRemote.deleteByNoPpkbAndContNo(receivingService.getNoPpkb(), receivingService.getContNo());
        }
    }

    public void removeThatRelatedToReceiving(ReceivingService receivingService) {
        remove(receivingService);
        planningContReceivingFacadeRemote.deleteByNoPpkbAndContNo(receivingService.getNoPpkb(), receivingService.getContNo());
    }

    @Override
    public List<Object[]> findReceivingServiceByNoReg(String no_reg) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ReceivingService.Native.findReceivingServiceByNoReg").setParameter(1, no_reg).getResultList()
        );
    }

    /**
     *
     * @param noPpkb
     * @return
     *      0. cont_no
     *      1. cont_size
     *      2. cont_type
     *      3. commodity_code
     *      4. cont_status
     *      5. cont_gross
     *      6. seal_no
     *      7. over_size
     *      8. dg
     *      9. dg_label
     *      10. bl_no
     *      11. mlo
     *      12. is_export
     *      13. disch_port_code
     *      14. position
     */
    public List<Object[]> findContainersWithPosition(String noPpkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ReceivingService.Native.findContainersWithPosition").setParameter(1, noPpkb).getResultList()
        );
    }

    public List<Object[]> findNotEnteringGateYetByContNo(String contNo) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ReceivingService.Native.findNotEnteringYetByContNo")
                .setParameter(1, contNo)
                .getResultList()
        );
    }

    public String findReceivingServiceByGenerate(String bulan) {
        return (String) getEntityManager().createNamedQuery("ReceivingService.Native.findReceivingServiceByGenerate").setParameter(1, bulan).getSingleResult();
    }

    public List<Object[]> findReceivingServiceByClosingTime(Date close_rec) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ReceivingService.Native.findReceivingServiceByClosingTime").setParameter(1, close_rec).getResultList()
        );
    }

    public List<Object[]> findPerhitungan(String no_reg) {
        return getEntityManager().createNamedQuery("ReceivingService.Native.perhitungan").setParameter(1, no_reg).getResultList();
    }

    public Object[] findReceivingCapacityStatusByNoPpkb(String noPpkb) {
        try {
            return (Object[]) getEntityManager().createNamedQuery("ReceivingService.Native.findReceivingCapacityStatusByNoPpkb")
                    .setParameter(1, noPpkb)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return new Object[] {
                new Long("0"), new Long("0")
            };
        }
    }

    /**
     *
     * @param jobSlip
     * @return object of
     *      0. job_slip
     *      1. cont_no
     *      2. lift_charge
     *      3. handling_charge
     *      4. pass_gate_charge
     *      5. penumpukan_charge
     *      6. upah_buruh_charge
     */
    public Object[] cancelableDocumentContainer(String jobSlip) {
        try {
            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ReceivingService.Native.cancelableDocumentContainer")
                    .setParameter(1, jobSlip)
                    .getSingleResult()
            );
        } catch (NoResultException nre) {
            return null;
        }
    }

    public ReceivingService findByNoPpkbAndContNoCancelLoading(String noPpkb, String contNo) {
        try {
            return (ReceivingService) getEntityManager().createNamedQuery("ReceivingService.findByNoPpkbAndContNoCancelLoading")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public ReceivingService findByNoPpkbAndContNoNotCancelLoading(String noPpkb, String contNo) {
        try {
            return (ReceivingService) getEntityManager().createNamedQuery("ReceivingService.findByNoPpkbAndContNoNotCancelLoading")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public ReceivingService findCancelableContainerByContNoAndNoPpkb(String noPpkb, String contNo) {
        try {
            return (ReceivingService) getEntityManager().createNamedQuery("ReceivingService.findCancelableContainerByContNoAndNoPpkb")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public String findInvoice(String cont_no, String no_ppkb, String no_reg) {
        return (String) getEntityManager().createNamedQuery("ReceivingService.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_ppkb).setParameter(3, no_reg).getSingleResult();
    }

    public List<Object[]> findReceivingRecaps(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ReceivingService.Native.findReceivingRecaps").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findCountableLoadReefers(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ReceivingService.Native.findCountableLoadReefers").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findHandlingLoadMonitoring(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ReceivingService.Native.findHandlingLoadMonitoring").setParameter(1, no_ppkb).getResultList()
        );
    }

    public Object[] findReceivingServiceContNo(String no_ppkb, String cont_no) {
        Object[] temp = new Object[2];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ReceivingService.Native.findReceivingServiceContNo").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findReceivingServiceByClosingTimeByJobSlip(String cont_no) {
        Object[] temp = new Object[6];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ReceivingService.Native.findReceivingServiceByClosingTimeByJobSlip").setParameter(1, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
    
    //penambahan pencarian data receiving by ade chelsea
    
    public ReceivingService findDataReceiving(String contNo) {
        try {
            return (ReceivingService) getEntityManager().createNamedQuery("ReceivingService.findDataReceiving")
                    .setParameter("contNo", contNo).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
    

    public Long findJobSlipAmount(String no_ppkb) {
        Long temp;
        try {
            temp = ((BigDecimal) getEntityManager().createNamedQuery("ReceivingService.Native.findJobSlipAmount").setParameter(1, no_ppkb).getSingleResult()).longValue();
        } catch (NoResultException ex) {
            temp = new Long("0");
        }
        return temp;
    }

    public List<String> findGateInPassableJobSlips(String jobslip) {
        return getEntityManager().createNamedQuery("ReceivingService.Native.findGateInPassableJobSlips").setParameter(1, jobslip).getResultList();
    }

    public List<String> findReceivingServiceByAutoCompleteCancelLoading(String no_ppkb, String cont_no) {
        return getEntityManager().createNamedQuery("ReceivingService.Native.findReceivingServiceByAutoCompleteCancelLoading").setParameter(1, no_ppkb).setParameter(2, cont_no).getResultList();
    }

    public String findReceivingServiceByUpdateDate(String no_ppkb, String cont_no) {
        String jobslip = null;
        try {
            jobslip = (String) getEntityManager().createNamedQuery("ReceivingService.Native.findReceivingServiceByUpdateDate").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult();
        } catch (NoResultException ex) {
            jobslip = null;
        }
        return jobslip;
    }

    public List<Object[]> findCountableLoadReefersByNoBl(String bl_no, String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ReceivingService.Native.findCountableLoadReefersByNoBl").setParameter(1, bl_no).setParameter(2, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findByListBatalNota(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ReceivingService.Native.findByListBatalNota").setParameter(1, no_reg).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findContainerDetail(String noPpkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ReceivingService.Native.findContainerDetail").setParameter(1, noPpkb).getResultList()
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
                    getEntityManager().createNamedQuery("ReceivingService.Native.findByListBatalNotaService").setParameter(1, no_ppkb).setParameter(2, no_reg).getResultList()
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
                    getEntityManager().createNamedQuery("ReceivingService.Native.findByNoregValidasiPrint").setParameter(1, no_reg).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Boolean isPassedThroughTheGate(String no_reg) {
        return ((String) getEntityManager().createNamedQuery("ReceivingService.Native.isPassedThroughTheGate").setParameter(1, no_reg).getSingleResult())
                .equalsIgnoreCase("TRUE");
    }

    public List<Object[]> findCashierLoad(String noPpkb, String contNo) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ReceivingService.Native.findCashierLoad").setParameter(1, noPpkb).setParameter(2, contNo).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findCashierLoadCancel(String noPpkb, String contNo) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ReceivingService.Native.findCashierLoadCancel").setParameter(1, noPpkb).setParameter(2, contNo).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public int updatePlanningVessel(PlanningVessel newValue, PlanningVessel oldValue) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("ReceivingService.updatePlanningVessel")
                .setParameter("newValue", newValue)
                .setParameter("oldValue", oldValue)
                .executeUpdate();
    }

    public int updatePlanningVesselByJobSlips(PlanningVessel newValue, MasterPort nextPort, PlanningVessel oldValue, List<String> jobSlips) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("ReceivingService.updatePlanningVesselByJobSlips")
                .setParameter("newValue", newValue)
                .setParameter("masterPort", nextPort)
                .setParameter("portOfDelivery", nextPort)
                .setParameter("oldValue", oldValue)
                .setParameter("jobSlips", jobSlips)
                .executeUpdate();
    }

    public int deleteByNoPpkbAndContNoNotCancelLoading(String noPpkb, String contNo) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("ReceivingService.deleteByNoPpkbAndContNoNotCancelLoading")
                .setParameter("noPpkb", noPpkb)
                .setParameter("contNo", contNo)
                .executeUpdate();
    }

    @Override
    public ReceivingService editAndFetch(ReceivingService receivingService, PlanningContReceiving planningContReceiving) {
        planningContReceivingFacadeLocal.edit(planningContReceiving);
        receivingService = getEntityManager().merge(receivingService);
        receivingService = find(receivingService.getJobSlip());
        return receivingService;
    }
}

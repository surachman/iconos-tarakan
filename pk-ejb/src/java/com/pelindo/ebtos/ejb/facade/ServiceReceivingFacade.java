/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ServiceReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.master.MasterPort;
import java.math.BigInteger;
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
public class ServiceReceivingFacade extends AbstractFacade<ServiceReceiving> implements ServiceReceivingFacadeRemote, ServiceReceivingFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceReceivingFacade() {
        super(ServiceReceiving.class);
    }

    public List<Object[]> findServiceReceivings() {
        return getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivings").getResultList();
    }

    public List<Object[]> findVesselChangeAbleContainers(String noPpkb) {
        return getEntityManager().createNamedQuery("ServiceReceiving.Native.findVesselChangeAbleContainers")
                .setParameter(1, noPpkb)
                .getResultList();
    }

    public Object[] findServiceReceivingByPpkbb(String no_ppkbb) {
        return (Object[]) getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivingByPpkbb").setParameter(1, no_ppkbb).getSingleResult();
    }

    @Override
    public List<Object[]> findServiceReceivingByPpkbList(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivingByPpkbList").setParameter(1, no_ppkb).getResultList();
    }

    public List<Object[]> findServiceReceivingByPpkbList2(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivingByPpkbList2").setParameter(1, no_ppkb).getResultList();
    }

    public List<Object[]> findServiceReceivingByPpkbReefer(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivingByPpkbReefer").setParameter(1, no_ppkb).getResultList();
    }

    public Object[] findContainerReefer(String cont_no) {
        Object[] temp = new Object[10];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("ServiceReceiving.Native.findContainerReefer").setParameter(1, cont_no).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findByPpkbAndContNo(String no_ppkb, String cont_no) {
        Object[] temp = new Object[10];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("ServiceReceiving.Native.findByPpkbAndContNo").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public ServiceReceiving findByNoPpkbAndContNo(String noPpkb, String contNo) {
        try {
            return (ServiceReceiving) getEntityManager().createNamedQuery("ServiceReceiving.findByNoPpkbAndContNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public ServiceReceiving findByNoPpkbAndContNoNotCancelLoading(String noPpkb, String contNo) {
        try {
            return (ServiceReceiving) getEntityManager().createNamedQuery("ServiceReceiving.findByNoPpkbAndContNoNotCancelLoading")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public ServiceReceiving findByNoPpkbAndContNoCancelLoading(String noPpkb, String contNo) {
        try {
            return (ServiceReceiving) getEntityManager().createNamedQuery("ServiceReceiving.findByNoPpkbAndContNoCancelLoading")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public ServiceReceiving findCancelableContainerByContNoAndNoPpkb(String noPpkb, String contNo) {
        try {
            return (ServiceReceiving) getEntityManager().createNamedQuery("ServiceReceiving.findCancelableContainerByContNoAndNoPpkb")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public ServiceReceiving findMovableContainer(String contNo) {
        try {
            return (ServiceReceiving) getEntityManager().createNamedQuery("ServiceReceiving.findMovableContainer")
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<Object[]> findLoadRecapsByPPKB(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceReceiving.Native.findLoadRecapsByPPKB").setParameter(1, no_ppkb).getResultList();
    }

    public List<Object[]> findServiceReceivingByPPKB(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivingByPPKB").setParameter(1, no_ppkb).getResultList();
    }
    
    public List<Object[]> findServiceReceivingPluggingOnly() {
        return getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivingPluggingOnly").getResultList();
    }


    public Object[] findServiceReceivingContNo(String no_ppkb, String cont_no) {
        Object[] temp = new Object[2];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivingContNo").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findServiceReceivingsStuffingService(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivingsStuffingService").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByPpkbAndContNoFront(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ServiceReceiving.Native.findByPpkbAndContNoFront").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findServiceReceivingFrontByPPKB(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivingFrontByPPKB").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public Long findRealisasiJobSlip(String no_ppkb) {
        Long temp;
        try {
            temp = (Long) getEntityManager().createNamedQuery("ServiceReceiving.Native.findRealisasiJobSlip").setParameter(1, no_ppkb).getSingleResult();
        } catch (NoResultException e) {
            temp = new Long("0");
        }
        return temp;
    }

    public List<String> findServiceReceivingByAutoComplete(String no_ppkb, String cont_no) {
        System.out.println("dari faceade :" + cont_no);
        return getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivingByAutoComplete").setParameter(1, no_ppkb).setParameter(2, cont_no).getResultList();
    }

    public Object[] findServiceReceivingByIsLiftOn(String cont_no, Boolean is_lifton) {
        Object[] temp = new Object[10];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivingByIsLiftOn").setParameter(1, cont_no).setParameter(2, is_lifton).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }

    public List<Object[]> findServiceReceivingsMovement() {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivingsMovement").getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }
//

    public List<Object[]> findServiceReceivingsStuffingServiceByBL(String bl, String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivingsStuffingServiceByBL").setParameter(1, bl).setParameter(2, no_ppkb).getResultList();
    }

    public List<Object[]> findServiceReceivingsChangeStatus(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivingsChangeStatus").setParameter(1, no_ppkb).getResultList();
    }

    public List<Object[]> findServiceReceivingsChangeDestination(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivingsChangeDestination").setParameter(1, no_ppkb).getResultList();
    }

    public Object[] findServiceReceivingByDestination(String no_ppkb, String cont_no) {
        Object[] temp = new Object[10];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivingByDestination").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }

    public List<Object[]> findServiceReceivingFrontByPPKBVersi2(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivingFrontByPPKBVersi2").setParameter(1, no_ppkb).setParameter(2, no_ppkb).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findServiceReceivingFrontByContainerNo(String contNo) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivingFrontByContainerNo").setParameter(1, contNo).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findServiceReceivingFrontByTrackingCont(String noPpkb,String contNo) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ServiceReceiving.Native.findServiceReceivingFrontByTrackingCont")
                    .setParameter(1, noPpkb)
                    .setParameter(2, contNo).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public Integer updateStatusCancelLoadingByNoPpkb(String noPpkb, String statusCancelLoading) {
        return getEntityManager().createNamedQuery("ServiceReceiving.updateStatusCancelLoadingByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .setParameter("statusCancelLoading", statusCancelLoading)
                .executeUpdate();
    }

    public List<ServiceReceiving> findByNoPpkb(String noPpkb) {
        return getEntityManager().createNamedQuery("ServiceReceiving.findByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .getResultList();
    }

    public Long countByNoPpkb(String noPpkb) {
        return (Long) getEntityManager().createNamedQuery("ServiceReceiving.findByNoPpkb.count")
                .setParameter("noPpkb", noPpkb)
                .getSingleResult();
    }

    public List<Object[]> findYardLoad(String noPpkb,String contNo) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ServiceReceiving.Native.findYardLoad")
                    .setParameter(1, noPpkb)
                    .setParameter(2, contNo).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public int updatePlanningVessel(PlanningVessel newValue, PlanningVessel oldValue) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("ServiceReceiving.updatePlanningVessel")
                .setParameter("newValue", newValue)
                .setParameter("oldValue", oldValue)
                .executeUpdate();
    }

    public int updatePlanningVesselByContNo(PlanningVessel newValue, MasterPort nextPort, PlanningVessel oldValue, List<String> containers) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("ServiceReceiving.updatePlanningVesselByContNo")
                .setParameter("newValue", newValue)
                .setParameter("oldValue", oldValue)
                .setParameter("portOfDelivery", nextPort)
                .setParameter("containers", containers)
                .executeUpdate();
    }
    
    @Override
    public List<String> findLikeNoPPKB(String noPpkb) {
        List<String> result = new ArrayList<String>();
        String sql = "select distinct(sr.no_ppkb) from service_receiving sr, planning_vessel pv "
                + " where sr.no_ppkb=pv.no_ppkb "
                + " and sr.no_ppkb like '%" + noPpkb + "%' and pv.status='Servicing' and rownum <=10";
        result = getEntityManager().createNativeQuery(sql).getResultList();
        return result;
    }

    @Override
    public List<String> findLikeContNo(String noPpkb, String contNo) {
        List<String> result = new ArrayList<String>();
        String sql = "select cont_no from service_receiving where no_ppkb = '" + noPpkb
                + "' and cont_no like '%" + contNo + "%' and rownum <=10";
        result = getEntityManager().createNativeQuery(sql).getResultList();
        return result;
    }

    @Override
    public List<ServiceReceiving> findByNoPpkbAndContNoUpdateLiftOn(String noPpkb, String contNo) {
        return getEntityManager().createNamedQuery("ServiceReceiving.findByNoPpkbAndContNo")
                .setParameter("noPpkb", noPpkb).setParameter("contNo", contNo).getResultList();
    }
}

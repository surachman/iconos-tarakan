/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.DeliveryServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.DeliveryServiceFacadeRemote;
import com.pelindo.ebtos.model.db.DeliveryService;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class DeliveryServiceFacade extends AbstractFacade<DeliveryService> implements DeliveryServiceFacadeRemote, DeliveryServiceFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public DeliveryServiceFacade() {
        super(DeliveryService.class);
    }

    public List<Object[]> findDeliveryServiceByPPKBnReg(String no_ppkb, String no_reg) {
        return getEntityManager().createNamedQuery("DeliveryService.Native.findByPpkbNReg").setParameter(1, no_ppkb).setParameter(2, no_reg).getResultList();
    }

    public String generateId(String bulan) {
        return (String) getEntityManager().createNamedQuery("DeliveryService.Native.generateId").setParameter(1, bulan).getSingleResult();
    }

    public List<Object[]> findDeliveryServiceByValidateDate(Date validate) {
        return getEntityManager().createNamedQuery("DeliveryService.Native.findDeliveryServiceByValidateDate").setParameter(1, validate).getResultList();
    }

    public String findInvoice(String cont_no, String no_ppkb, String no_reg) {
        return (String) getEntityManager().createNamedQuery("DeliveryService.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_ppkb).setParameter(3, no_reg).getSingleResult();
    }

    public DeliveryService findByContNoPpkbAndReg(String contNo, String noPpkb, String noReg) {
        try {
            return (DeliveryService) getEntityManager().createNamedQuery("DeliveryService.findByContNoPpkbAndReg")
                    .setParameter("contNo", contNo)
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("noReg", noReg)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    //Penambahan pencarian data receiving by ade chelsea
    public DeliveryService findDataDelivery(String contNo) {
        try {
            List<DeliveryService> list =  (List<DeliveryService>) getEntityManager().createNamedQuery("DeliveryService.findDataDelivery")
                    .setParameter("contNo", contNo)
                    .setMaxResults(1)
                    .getResultList();
            return list.size() > 0 ? list.get(0) : null;
        } catch (NoResultException nre) {
            return null;
        }
    }
    
    
    public DeliveryService findByNoPpkbAndContNo(String noPpkb, String contNo) {
        try {
            return (DeliveryService) getEntityManager().createNamedQuery("DeliveryService.findByNoPpkbAndContNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public String findByPpkbNCont(String cont_no, String no_ppkb) {
        String temp;
        try {
            temp = (String) getEntityManager().createNamedQuery("DeliveryService.Native.findByPpkbNCont").setParameter(1, cont_no).setParameter(2, no_ppkb).getSingleResult();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findPerhitungan(String no_reg) {
        return getEntityManager().createNamedQuery("DeliveryService.Native.perhitungan").setParameter(1, no_reg).getResultList();
    }

    public List<Object[]> findDeliveryServiceByPPKB(String no_ppkb) {
        return getEntityManager().createNamedQuery("DeliveryService.Native.findByPpkb").setParameter(1, no_ppkb).getResultList();
    }

    public List<Object[]> findByPpkbAndBlNoPenumpukanSusulan(String no_ppkb, String bl_no) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("DeliveryService.Native.findByPpkbAndBlNoPenumpukanSusulan").setParameter(1, no_ppkb).setParameter(2, bl_no).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findContainerDetail(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("DeliveryService.Native.findContainerDetail").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByPpkbPenumpukanSusulan(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                (List<Object[]>) getEntityManager().createNamedQuery("DeliveryService.Native.findByPpkbPenumpukanSusulan").setParameter(1, no_ppkb).getResultList()
    );
    }

    public List<Object[]> findDeliveryServiceByReg(String no_reg) {
        return getEntityManager().createNamedQuery("DeliveryService.Native.findByReg").setParameter(1, no_reg).getResultList();
    }

    public Integer deleteByPpkbAndReg(String noPpkb, String noReg) {
            return getEntityManager().createNamedQuery("DeliveryService.deleteByPpkbAndReg")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("noReg", noReg)
                    .executeUpdate();
    }

    public Object[] findDeliveryServiceByClosingTime(String job_slip) {
        Object[] temp = new Object[6];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("DeliveryService.Native.findDeliveryServiceByClosingTime").setParameter(1, job_slip).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<String> findDeliveryServiceByAutoComplete(String jobslip) {
        List<String> temp = new ArrayList<String>();
        try {
            temp = getEntityManager().createNamedQuery("DeliveryService.Native.findDeliveryServiceByAutoComplete").setParameter(1, jobslip).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByPpkbAndBlNo(String no_ppkb, String bl_no) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("DeliveryService.Native.findByPpkbAndBlNo").setParameter(1, no_ppkb).setParameter(2, bl_no).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByListBatalNota(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("DeliveryService.Native.findByListBatalNota").setParameter(1, no_reg).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByNoregValidasiPrint(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("DeliveryService.Native.findByNoregValidasiPrint").setParameter(1, no_reg).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findCashierDischarge(String no_ppkb, String contNo) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("DeliveryService.Native.findCashierDischarge").setParameter(1, no_ppkb).setParameter(2, contNo).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
}

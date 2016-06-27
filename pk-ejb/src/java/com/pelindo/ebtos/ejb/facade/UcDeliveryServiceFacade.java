/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.UcDeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.UcDeliveryServiceFacadeLocal;
import com.pelindo.ebtos.model.db.UcDeliveryService;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author postgres
 */
@Stateless
public class UcDeliveryServiceFacade extends AbstractFacade<UcDeliveryService> implements UcDeliveryServiceFacadeLocal, UcDeliveryServiceFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public UcDeliveryServiceFacade() {
        super(UcDeliveryService.class);
    }

    public List<Object[]> findByUcDeliveryServiceJobSlip(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("UcDeliveryService.Native.findByUcDeliveryServiceJobSlip").setParameter(1, no_reg).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByReg(String no_reg) {
        return getEntityManager().createNamedQuery("UcDeliveryService.Native.findByPpkbNReg").setParameter(1, no_reg).getResultList();
    }

    public String generateId(String bulan) {
        return (String) getEntityManager().createNamedQuery("UcDeliveryService.Native.generateId").setParameter(1, bulan).getSingleResult();
    }

    public List<String> findByUcDeliveryServiceAutoComplete(String jobslip) {
        List<String> temp = new ArrayList<String>();
        try {
            temp = getEntityManager().createNamedQuery("UcDeliveryService.Native.findByUcDeliveryServiceAutoComplete").setParameter(1, jobslip).getResultList();
            System.out.println("lisy" + temp.size());
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public Object[] findByUcDeliveryServiceGateInDeliveryClosingTime(String job_slip) {
        Object[] temp = new Object[6];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("UcDeliveryService.Native.findByUcDeliveryServiceGateInDeliveryClosingTime").setParameter(1, job_slip).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findByUcDeliveryServiceGateInDeliveryClosingTimeTruckYes(String job_slip) {
        Object[] temp = new Object[6];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("UcDeliveryService.Native.findByUcDeliveryServiceGateInDeliveryClosingTimeTruckYes").setParameter(1, job_slip).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findPerhitungan(String no_reg) {
        return getEntityManager().createNamedQuery("UcDeliveryService.Native.perhitungan").setParameter(1, no_reg).getResultList();
    }

    public List<Object[]> findDischargeRecapUc(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("UcDeliveryService.Native.findDischargeRecapUc").setParameter(1, no_ppkb).getResultList();
            System.out.println("lisy" + temp.size());
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findDeliveryServiceByPPKB(String no_ppkb){
        return getEntityManager().createNamedQuery("UcDeliveryService.Native.findByPpkbSusulan").setParameter(1, no_ppkb).getResultList();
    }

    public String findByPpkbNIdUC(Integer id_uc, String no_ppkb) {
        return (String) getEntityManager().createNamedQuery("UcDeliveryService.Native.findByPpkbNIdUC").setParameter(1, id_uc).setParameter(2, no_ppkb).getSingleResult();
    }

    public List<Object[]> findByPpkbSusulanByBL(String bl, String no_ppkb) {
        return getEntityManager().createNamedQuery("UcDeliveryService.Native.findByPpkbSusulanByBL").setParameter(1, bl).setParameter(2, no_ppkb).getResultList();
    }

    public List<Object[]> findByNoregValidasiPrint(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("UcDeliveryService.Native.findByNoregValidasiPrint").setParameter(1, no_reg).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public int deleteByNoReg(String noReg) {
        EntityManager entityManager = emf.createEntityManager();

        return entityManager.createNamedQuery("UcDeliveryService.deleteByNoReg")
                .setParameter("noReg", noReg)
                .executeUpdate();
    }

}

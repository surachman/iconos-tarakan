/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.UcReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.UcReceivingServiceFacadeLocal;
import com.pelindo.ebtos.model.db.UcReceivingService;
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
public class UcReceivingServiceFacade extends AbstractFacade<UcReceivingService> implements UcReceivingServiceFacadeLocal, UcReceivingServiceFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public UcReceivingServiceFacade() {
        super(UcReceivingService.class);
    }

    public List<Object[]> findByUcReceivingServices(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("UcReceivingService.Native.findByUcReceivingServices").setParameter(1, no_reg).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    /**
     *
     * @param no_reg
     * @return
     *      0 = urs.jobslip
     *      1 = bl_no
     *      2 = weight
     *      3 = name
     *      4 = us.truck_loosing
     *      5 = cubication
     *      6 = masa_1
     *      7 = masa_2
     *      8 = upl.charge
     *      9 = pp.charge_masa1
     *      10 = pp.charge_masa2
     *      11 = ups.charge
     *      12 = ppg.total_charge
     *      13 = pp.charge
     */
    public List<Object[]> findByUcReceivingPerhitungan(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("UcReceivingService.Native.findByUcReceivingPerhitungan").setParameter(1, no_reg).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public String generateId(String bulan) {
        return (String) getEntityManager().createNamedQuery("UcReceivingService.Native.generateId").setParameter(1, bulan).getSingleResult();
    }

    public List<Object[]> findByUcReceivingJobSlip(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("UcReceivingService.Native.findByUcReceivingJobSlip").setParameter(1, no_reg).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<String> findGateInPassableJobSlips(String jobslip) {
        List<String> temp = new ArrayList<String>();
        try {
            temp = getEntityManager().createNamedQuery("UcReceivingService.Native.findGateInPassableJobSlips").setParameter(1, jobslip).getResultList();

        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public Object[] findGateInPassableByJobSlip(String job_slip) {
        Object[] temp = new Object[6];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("UcReceivingService.Native.findGateInPassableByJobSlip").setParameter(1, job_slip).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findLoadRecapUc(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("UcReceivingService.Native.findLoadRecapUc").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Integer> findJobslipByIdUC(int id_uc) {
        return getEntityManager().createNamedQuery("UcReceivingService.Native.findJobslipByIdUC").setParameter(1, id_uc).getResultList();
    }

    public Object[] findNoRegJobslipByIdUC(int id_uc) {
        return (Object[]) getEntityManager().createNamedQuery("UcReceivingService.Native.findNoRegJobslipByIdUC").setParameter(1, id_uc).getSingleResult();
    }

    public List<Object[]> findByPpkb(String no_ppkb) {
        return getEntityManager().createNamedQuery("UcReceivingService.Native.findByPpkb").setParameter(1, no_ppkb).getResultList();
    }

    public List<Object[]> findByPpkbTLfalse(String no_ppkb) {
        return getEntityManager().createNamedQuery("UcReceivingService.Native.findByPpkbTLfalse").setParameter(1, no_ppkb).getResultList();
    }

    public List<UcReceivingService> findByNoPpkbAndNotTruckLoosing(String noPpkb) {
        return getEntityManager().createNamedQuery("UcReceivingService.findByNoPpkbAndTruckLoosingStatus")
                .setParameter("truckLoosing", false)
                .setParameter("noPpkb", noPpkb)
                .getResultList();
    }

    public int removeMasaByNoPpkbAndNotTruckLoosing(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("UcReceivingService.updateMasaByNoPpkbAndTruckLoosingStatus")
                .setParameter("masa1", null)
                .setParameter("masa2", null)
                .setParameter("truckLoosing", "FALSE")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }

    public String findJobslipByIdUCMobile(int id_uc) {
        return (String) getEntityManager().createNamedQuery("UcReceivingService.Native.findJobslipByIdUCMobile").setParameter(1, id_uc).getSingleResult();
    }

    public List<Object[]> findCancelInvoice(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("UcReceivingService.Native.findCancelInvoice").setParameter(1, no_reg).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByNoregValidasiPrint(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("UcReceivingService.Native.findByNoregValidasiPrint").setParameter(1, no_reg).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public int deleteByNoReg(String noReg) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("UcReceivingService.deleteByNoReg")
                .setParameter("noReg", noReg)
                .executeUpdate();
    }
}

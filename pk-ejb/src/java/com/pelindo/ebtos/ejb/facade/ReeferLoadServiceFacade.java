/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ReeferLoadServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ReeferLoadServiceFacadeRemote;
import com.pelindo.ebtos.model.db.ReeferLoadService;
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
public class ReeferLoadServiceFacade extends AbstractFacade<ReeferLoadService> implements ReeferLoadServiceFacadeRemote, ReeferLoadServiceFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ReeferLoadServiceFacade() {
        super(ReeferLoadService.class);
    }

    public List<Object[]> findReeferLoadServiceByPPKBnReg(String no_ppkb, String no_reg) {
        return getEntityManager().createNamedQuery("ReeferLoadService.Native.findByPpkbNReg").setParameter(1, no_ppkb).setParameter(2, no_reg).getResultList();
    }

    public List<ReeferLoadService> findByNoPpkb(String noPpkb) {
        return getEntityManager().createNamedQuery("ReeferLoadService.findByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .getResultList();
    }

    public String generateId(String bulan) {
        return (String) getEntityManager().createNamedQuery("ReeferLoadService.Native.generateId").setParameter(1, bulan).getSingleResult();
    }

    public String findInvoice(String cont_no, String no_ppkb, String no_reg) {
        return (String) getEntityManager().createNamedQuery("ReeferLoadService.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_ppkb).setParameter(3, no_reg).getSingleResult();
    }

    public List<Object[]> findPerhitungan(String no_reg) {
        return getEntityManager().createNamedQuery("ReeferLoadService.Native.perhitungan").setParameter(1, no_reg).getResultList();
    }

    public List<String> findReeferLoadServiceByPPKB(String no_ppkb) {
        List<String> temp = new ArrayList<String>();
        try {
            temp = getEntityManager().createNamedQuery("ReeferLoadService.Native.findByPpkb").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findLoadReeferServiceByReg(String no_reg) {
        return getEntityManager().createNamedQuery("ReeferLoadService.Native.findByReg").setParameter(1, no_reg).getResultList();
    }

    public Object[] findGateReeferByJobSlip(String job_slip) {
        Object[] temp = new Object[6];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("ReeferLoadService.Native.findGateReeferByJobSlip").setParameter(1, job_slip).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<String> findReeferLoadServiceByAutoComplete(String jobslip) {
        List<String> temp = new ArrayList<String>();
        try {
            temp = getEntityManager().createNamedQuery("ReeferLoadService.Native.findReeferLoadServiceByAutoComplete").setParameter(1, jobslip).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByNoregValidasiPrint(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ReeferLoadService.Native.findByNoregValidasiPrint").setParameter(1, no_reg).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public int removePlugOnOffAndShiftPlanningByNoPpkb(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("ReeferLoadService.updatePlugOnOffAndShiftPlanningByNoPpkb")
                .setParameter("plugOnDate", null)
                .setParameter("plugOffDate", null)
                .setParameter("shiftPlanning", null)
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }

    public ReeferLoadService findValidReeferByNoPpkbAndContNo(String noPpkb, String contNo) {
        try {
            return (ReeferLoadService) getEntityManager().createNamedQuery("ReeferLoadService.findValidReeferByNoPpkbAndContNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException re) {
            return null;
        }
    }
}

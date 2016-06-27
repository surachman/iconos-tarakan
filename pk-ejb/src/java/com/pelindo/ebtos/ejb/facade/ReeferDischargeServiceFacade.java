/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ReeferDischargeServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ReeferDischargeServiceFacadeRemote;
import com.pelindo.ebtos.model.db.ReeferDischargeService;
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
public class ReeferDischargeServiceFacade extends AbstractFacade<ReeferDischargeService> implements ReeferDischargeServiceFacadeRemote, ReeferDischargeServiceFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ReeferDischargeServiceFacade() {
        super(ReeferDischargeService.class);
    }

    public ReeferDischargeService findByContNoAndNoReg(String contNo, String noReg) {
        try {
            return (ReeferDischargeService) getEntityManager().createNamedQuery("ReeferDischargeService.findByContNoAndNoReg")
                    .setParameter("contNo", contNo)
                    .setParameter("noReg", noReg)
                    .getSingleResult();
        } catch (NoResultException re) {
            return null;
        }
    }

    public ReeferDischargeService findLastValidReeferByNoPpkbAndContNo(String noPpkb, String contNo) {
        try {
            return (ReeferDischargeService) getEntityManager().createNamedQuery("ReeferDischargeService.findValidReeferByNoPpkbAndContNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException re) {
            return null;
        }
    }

    public List<Object[]> findReeferDischargeServiceByPPKBnReg(String no_ppkb, String no_reg) {
        return getEntityManager().createNamedQuery("ReeferDischargeService.Native.findByPpkbNReg").setParameter(1, no_ppkb).setParameter(2, no_reg).getResultList();
    }

    public String generateId(String bulan) {
        return (String) getEntityManager().createNamedQuery("ReeferDischargeService.Native.generateId").setParameter(1, bulan).getSingleResult();
    }

    public String findInvoice(String cont_no, String no_ppkb, String no_reg) {
        return (String) getEntityManager().createNamedQuery("ReeferDischargeService.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_ppkb).setParameter(3, no_reg).getSingleResult();
    }

    public List<Object[]> findPerhitungan(String no_reg) {
        return getEntityManager().createNamedQuery("ReeferDischargeService.Native.perhitungan").setParameter(1, no_reg).getResultList();
    }

    public List<Object[]> findDischargeReeferServiceByReg(String no_reg) {
        return getEntityManager().createNamedQuery("ReeferDischargeService.Native.findByReg").setParameter(1, no_reg).getResultList();
    }

    public Object[] findReeferByReg(String job_slip) {
        Object[] temp = new Object[6];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("ReeferDischargeService.Native.findReeferByReg").setParameter(1, job_slip).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<String> findReeferDischargeServiceByAutoComplete(String jobslip) {
        List<String> temp = new ArrayList<String>();
        try {
            temp = getEntityManager().createNamedQuery("ReeferDischargeService.Native.findReeferDischargeServiceByAutoComplete").setParameter(1, jobslip).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByNoregValidasiPrint(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ReeferDischargeService.Native.findByNoregValidasiPrint").setParameter(1, no_reg).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<ReeferDischargeService> findByNoReg(String noReg) {
        return getEntityManager().createNamedQuery("ReeferDischargeService.findByNoReg")
                .setParameter("noReg", noReg)
                .getResultList();
    }
}

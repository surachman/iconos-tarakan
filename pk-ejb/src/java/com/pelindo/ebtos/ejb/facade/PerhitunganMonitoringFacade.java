/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PerhitunganMonitoringFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganMonitoringFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganMonitoring;
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
public class PerhitunganMonitoringFacade extends AbstractFacade<PerhitunganMonitoring> implements PerhitunganMonitoringFacadeRemote, PerhitunganMonitoringFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PerhitunganMonitoringFacade() {
        super(PerhitunganMonitoring.class);
    }

    public Integer findInvoice(String cont_no, String no_ppkb, String no_reg) {
        Integer temp;
        try {
            temp = (Integer) getEntityManager().createNamedQuery("PerhitunganMonitoring.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_ppkb).setParameter(3, no_reg).getSingleResult();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public Integer findInvoicePlugging(String cont_no, String no_reg) {
        return (Integer) getEntityManager().createNamedQuery("PerhitunganMonitoring.Native.findInvoicePlugging").setParameter(1, cont_no).setParameter(2, no_reg).getSingleResult();
    }

    public List<Object> findByReg(String no_reg) {
        return getEntityManager().createNamedQuery("PerhitunganMonitoring.Native.findByReg").setParameter(1, no_reg).getResultList();
    }

    public int deleteByNoPpkbLoadOnly(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PerhitunganMonitoring.deleteByNoPpkbLoadOnly")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }

    @Override
    public PerhitunganMonitoring findByContNoAndNoReg(String contNo, String noReg) {
        try {
            return (PerhitunganMonitoring) getEntityManager().createNamedQuery("PerhitunganMonitoring.findByContNoAndNoReg")
                    .setParameter("contNo", contNo)
                    .setParameter("noReg", noReg)
                    .getSingleResult();
        } catch (NoResultException re) {
            return null;
        }
    }
}

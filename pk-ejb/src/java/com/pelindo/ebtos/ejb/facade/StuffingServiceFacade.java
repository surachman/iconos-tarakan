/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.StuffingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.StuffingServiceFacadeRemote;
import com.pelindo.ebtos.model.db.StuffingService;
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
public class StuffingServiceFacade extends AbstractFacade<StuffingService> implements StuffingServiceFacadeRemote, StuffingServiceFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public StuffingServiceFacade() {
        super(StuffingService.class);
    }

    public List<Object[]> findStuffingServiceByPPKBnReg(String no_reg) {
        return getEntityManager().createNamedQuery("StuffingService.Native.findByPpkbNReg").setParameter(1, no_reg).getResultList();
    }

    public String generateId(String bulan) {
        return (String) getEntityManager().createNamedQuery("StuffingService.Native.generateId").setParameter(1, bulan).getSingleResult();
    }

    public String findInvoice(String cont_no, String no_reg) {
        return (String) getEntityManager().createNamedQuery("StuffingService.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_reg).getSingleResult();
    }

    public List<Object[]> findPerhitungan(String no_reg) {
        return getEntityManager().createNamedQuery("StuffingService.Native.perhitungan").setParameter(1, no_reg).getResultList();
    }

    public List<Object[]> findStuffingServiceByReg(String no_reg) {
        return getEntityManager().createNamedQuery("StuffingService.Native.findStuffingServiceByReg").setParameter(1, no_reg).getResultList();
    }

    public List<Object[]> findByNoregValidasiPrint(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("StuffingService.Native.findByNoregValidasiPrint").setParameter(1, no_reg).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    @Override
    public List<StuffingService> findByNoReg(String noReg) {
        return getEntityManager().createNamedQuery("StuffingService.findByNoReg")
                .setParameter("noReg", noReg)
                .getResultList();
    }

    public int deleteByNoReg(String noReg) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("StuffingService.deleteByNoReg")
                .setParameter("noReg", noReg)
                .executeUpdate();
    }
}

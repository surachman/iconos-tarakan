/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.StrippingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.StrippingServiceFacadeRemote;
import com.pelindo.ebtos.model.db.StrippingService;
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
public class StrippingServiceFacade extends AbstractFacade<StrippingService> implements StrippingServiceFacadeRemote, StrippingServiceFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public StrippingServiceFacade() {
        super(StrippingService.class);
    }

    public List<Object[]> findStrippingServiceByPPKBnReg(String no_ppkb, String no_reg) {
        return getEntityManager().createNamedQuery("StrippingService.Native.findByPpkbNReg").setParameter(1, no_ppkb).setParameter(2, no_reg).getResultList();
    }

    public String generateId(String bulan) {
        return (String) getEntityManager().createNamedQuery("StrippingService.Native.generateId").setParameter(1, bulan).getSingleResult();
    }

    public String findInvoice(String cont_no, String no_ppkb, String no_reg) {
        return (String) getEntityManager().createNamedQuery("StrippingService.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_ppkb).setParameter(3, no_reg).getSingleResult();
    }

    public List<Object[]> findPerhitungan(String no_reg) {
        return getEntityManager().createNamedQuery("StrippingService.Native.perhitungan").setParameter(1, no_reg).getResultList();
    }

    public List<Object[]> findStrippingServiceByReg(String no_reg) {
        return getEntityManager().createNamedQuery("StrippingService.Native.findStrippingServiceByReg").setParameter(1, no_reg).getResultList();
    }

    public List<StrippingService> findByNoReg(String noReg) {
        return getEntityManager().createNamedQuery("StrippingService.findByNoReg")
                .setParameter("noReg", noReg)
                .getResultList();
    }

    public List<Object[]> findByNoregValidasiPrint(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("StrippingService.Native.findByNoregValidasiPrint").setParameter(1, no_reg).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    @Override
    public StrippingService editAndFetch(StrippingService entity) {
        entity = getEntityManager().merge(entity);
        return find(entity.getJobSlip());
    }

    public int deleteByNoReg (String noReg) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("StrippingService.deleteByNoReg")
                .setParameter("noReg", noReg)
                .executeUpdate();
    }
}

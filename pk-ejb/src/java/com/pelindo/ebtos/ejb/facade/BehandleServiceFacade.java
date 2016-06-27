/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.BehandleServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.BehandleServiceFacadeRemote;
import com.pelindo.ebtos.model.db.BehandleService;
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
public class BehandleServiceFacade extends AbstractFacade<BehandleService> implements BehandleServiceFacadeRemote, BehandleServiceFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public BehandleServiceFacade() {
        super(BehandleService.class);
    }

    public List<Object[]> findBehandleServiceByPPKBnReg(String no_ppkb, String no_reg){
        return getEntityManager().createNamedQuery("BehandleService.Native.findByPpkbNReg").setParameter(1, no_ppkb).setParameter(2, no_reg).getResultList();
    }

    public String generateId(String bulan) {
        return (String) getEntityManager().createNamedQuery("BehandleService.Native.generateId").setParameter(1, bulan).getSingleResult();
    }

    public String findInvoice(String cont_no, String no_ppkb, String no_reg) {
        return (String) getEntityManager().createNamedQuery("BehandleService.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_ppkb).setParameter(3, no_reg).getSingleResult();
    }

    public List<Object[]> findPerhitungan(String no_reg){
        return getEntityManager().createNamedQuery("BehandleService.Native.perhitungan").setParameter(1, no_reg).getResultList();
    }

    public List<Object[]> findByJobslip(String no_reg){
        return getEntityManager().createNamedQuery("BehandleService.Native.findByJobslip").setParameter(1, no_reg).getResultList();
    }

    public List<Object[]> findByNoregValidasiPrint(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("BehandleService.Native.findByNoregValidasiPrint").setParameter(1, no_reg).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    @Override
    public BehandleService findByContNoAndNoReg(String contNo, String noReg) {
        try {
            return (BehandleService) getEntityManager().createNamedQuery("BehandleService.findByContNoAndNoReg")
                    .setParameter("contNo", contNo)
                    .setParameter("noReg", noReg)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    @Override
    public int deleteByNoReg(String noReg) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("BehandleService.deleteByNoReg")
                .setParameter("noReg", noReg)
                .executeUpdate();
    }

}

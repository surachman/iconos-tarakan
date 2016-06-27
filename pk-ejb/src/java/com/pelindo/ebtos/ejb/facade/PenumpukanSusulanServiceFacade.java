/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PenumpukanSusulanServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PenumpukanSusulanServiceFacadeRemote;
import com.pelindo.ebtos.model.db.PenumpukanSusulanService;
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
public class PenumpukanSusulanServiceFacade extends AbstractFacade<PenumpukanSusulanService> implements PenumpukanSusulanServiceFacadeRemote, PenumpukanSusulanServiceFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PenumpukanSusulanServiceFacade() {
        super(PenumpukanSusulanService.class);
    }

    public List<Object[]> findPenumpukanSusulanServiceByPPKBnReg(String no_ppkb, String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("PenumpukanSusulanService.Native.findByPpkbNReg").setParameter(1, no_ppkb).setParameter(2, no_reg).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public String generateId(String bulan) {
        return (String) getEntityManager().createNamedQuery("PenumpukanSusulanService.Native.generateId").setParameter(1, bulan).getSingleResult();
    }

    public String findInvoice(String cont_no, String no_ppkb, String no_reg) {
        return (String) getEntityManager().createNamedQuery("PenumpukanSusulanService.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_ppkb).setParameter(3, no_reg).getSingleResult();
    }

    public List<Object[]> findPerhitungan(String no_reg) {
        return getEntityManager().createNamedQuery("PenumpukanSusulanService.Native.perhitungan").setParameter(1, no_reg).getResultList();
    }

    public String findCekValidasi(String contNo, String noPpkb, String noReg) {
        String temp = null;
        try {
            temp = (String) getEntityManager().createNamedQuery("PenumpukanSusulanService.Native.findCekValidasi").setParameter(1, contNo).setParameter(2, noPpkb).setParameter(3, noReg).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByListBatalNotaService(String no_ppkb, String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("PenumpukanSusulanService.Native.findByListBatalNotaService").setParameter(1, no_ppkb).setParameter(2, no_reg).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
}

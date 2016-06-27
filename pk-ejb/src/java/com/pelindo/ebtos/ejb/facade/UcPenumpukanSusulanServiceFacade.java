/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.UcPenumpukanSusulanServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.UcPenumpukanSusulanServiceFacadeLocal;
import com.pelindo.ebtos.model.db.UcPenumpukanSusulanService;
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
public class UcPenumpukanSusulanServiceFacade extends AbstractFacade<UcPenumpukanSusulanService> implements UcPenumpukanSusulanServiceFacadeLocal, UcPenumpukanSusulanServiceFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public UcPenumpukanSusulanServiceFacade() {
        super(UcPenumpukanSusulanService.class);
    }

    public List<Object[]> findPenumpukanSusulanServiceByPPKBnReg(String no_ppkb, String no_reg){
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("UcPenumpukanSusulanService.Native.findByPpkbNReg").setParameter(1, no_ppkb).setParameter(2, no_reg).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public String generateId(String bulan) {
        return (String) getEntityManager().createNamedQuery("UcPenumpukanSusulanService.Native.generateId").setParameter(1, bulan).getSingleResult();
    }

    public String findInvoice(Integer cont_no, String no_ppkb, String no_reg) {
        return (String) getEntityManager().createNamedQuery("UcPenumpukanSusulanService.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_ppkb).setParameter(3, no_reg).getSingleResult();
    }

    public List<Object[]> findPerhitungan(String no_reg){
        return getEntityManager().createNamedQuery("UcPenumpukanSusulanService.Native.perhitungan").setParameter(1, no_reg).getResultList();
    }

    public List<Object> findByReg(String no_reg){
        return getEntityManager().createNamedQuery("UcPenumpukanSusulanService.Native.findByReg").setParameter(1, no_reg).getResultList();
    }

}

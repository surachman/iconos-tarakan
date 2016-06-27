/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanBarangFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukanBarang;
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
public class PerhitunganPenumpukanBarangFacade extends AbstractFacade<PerhitunganPenumpukanBarang> implements PerhitunganPenumpukanBarangFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PerhitunganPenumpukanBarangFacade() {
        super(PerhitunganPenumpukanBarang.class);
    }
//findPerhitunganPenumpukanBarangByEdit

    public Object[] findPerhitunganPenumpukanBarangByEdit(String job_slip,String cont_no) {
        Object[] temp = new Object[3];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("PerhitunganPenumpukanBarang.Native.findPerhitunganPenumpukanBarangByEdit").setParameter(1, job_slip).setParameter(2, cont_no).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }
     public List<Object[]> findPerhitunganPenumpukanBarangByList() {
        return getEntityManager().createNamedQuery("PerhitunganPenumpukanBarang.Native.findPerhitunganPenumpukanBarangByList").getResultList();
    }

    public List<Object> findByReg(String no_reg) {
        return getEntityManager().createNamedQuery("PerhitunganPenumpukanBarang.Native.findByReg").setParameter(1, no_reg).getResultList();
    }

}

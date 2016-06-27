/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.PerhitunganLiftBarangFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganLiftBarang;
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
public class PerhitunganLiftBarangFacade extends AbstractFacade<PerhitunganLiftBarang> implements PerhitunganLiftBarangFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PerhitunganLiftBarangFacade() {
        super(PerhitunganLiftBarang.class);
    }

    public Object[] findPerhitunganLiftBarangByEdit(String jobSlip, String cont_no) {
        Object[] temp = new Object[2];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("PerhitunganLiftBarang.Native.findPerhitunganLiftBarangByEdit").setParameter(1, jobSlip).setParameter(2, cont_no).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
    public List<Object[]> findPerhitunganLiftBarangByList(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("PerhitunganLiftBarang.Native.findPerhitunganLiftBarangByList").setParameter(1, no_reg).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public Integer findCancelInvoice(String job_slip, String no_ppkb, String no_reg) {
        Integer idPlan = null;
        try {
            idPlan = (Integer) getEntityManager().createNamedQuery("PerhitunganLiftBarang.Native.findCancelInvoice").setParameter(1, job_slip).setParameter(2, no_ppkb).setParameter(3, no_reg).getSingleResult();
        } catch (NoResultException ex) {
            idPlan = null;
        }
        return idPlan;
    }

    public List<Object> findByReg(String no_reg) {
        return getEntityManager().createNamedQuery("PerhitunganLiftBarang.Native.findByReg").setParameter(1, no_reg).getResultList();
    }
}

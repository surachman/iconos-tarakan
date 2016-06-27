/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PerhitunganPenumpukanTranshipmentFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanTranshipmentFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukanTranshipment;
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
public class PerhitunganPenumpukanTranshipmentFacade extends AbstractFacade<PerhitunganPenumpukanTranshipment> implements PerhitunganPenumpukanTranshipmentFacadeRemote, PerhitunganPenumpukanTranshipmentFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PerhitunganPenumpukanTranshipmentFacade() {
        super(PerhitunganPenumpukanTranshipment.class);
    }

    public List<Integer> findByPpkbNCont(String cont_no, String no_ppkb) {
        List<Integer> temp = new ArrayList<Integer>();
        try {
            temp = getEntityManager().createNamedQuery("PerhitunganPenumpukanTranshipment.Native.findByPpkbNCont").setParameter(1, cont_no).setParameter(2, no_ppkb).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    @Override
    public int deleteByNoPpkb(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PerhitunganPenumpukanTranshipment.deleteByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }
}

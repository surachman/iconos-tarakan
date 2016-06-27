/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.PerhitunganNotaManualDetailFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.PerhitunganNotaManualDetailFacadeLocal;
import com.pelindo.ebtos.model.db.PerhitunganNotaManualDetail;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dyware-Dev01
 */
@Stateless
public class PerhitunganNotaManualDetailFacade extends AbstractFacade<PerhitunganNotaManualDetail> implements PerhitunganNotaManualDetailFacadeLocal, PerhitunganNotaManualDetailFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PerhitunganNotaManualDetailFacade() {
        super(PerhitunganNotaManualDetail.class);
    }

    public List<Object[]> findByNoReg(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("PerhitunganNotaManualDetail.Native.findByNoReg").setParameter(1, no_reg).getResultList();
            ;
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }
}

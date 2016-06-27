/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.MasterViewFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterViewFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterView;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class MasterViewFacade extends AbstractFacade<MasterView> implements MasterViewFacadeLocal, MasterViewFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<MasterView> findAll(){
        return getEntityManager().createNamedQuery("MasterView.findAll").getResultList();
    }

    public MasterViewFacade() {
        super(MasterView.class);
    }

    @Override
    public List<Object[]> findChildrenByParent(Integer parent){
        return getEntityManager().createNamedQuery("MasterView.findChildsByParent").
                setParameter(1, parent).
                getResultList();
    }
    @Override
    public String findMessageByView(String view){
        try {
            return (String) getEntityManager().createNamedQuery("MasterView.findMessageByView").setParameter("view", view).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterBankFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterBankFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterBank;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author R Seno Anggoro
 */
@Stateless
public class MasterBankFacade extends AbstractFacade<MasterBank> implements MasterBankFacadeLocal, MasterBankFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterBankFacade() {
        super(MasterBank.class);
    }

    public MasterBank findByGuid(String guid){
        try {
            return (MasterBank) getEntityManager().createNamedQuery("MasterBank.findByGuid")
                    .setParameter("guid", guid)
                    .getSingleResult();
        } catch (NoResultException nre){
            return null;
        }
    }
}

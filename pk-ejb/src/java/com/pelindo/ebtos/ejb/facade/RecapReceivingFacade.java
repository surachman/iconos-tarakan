/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.RecapReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.RecapReceivingFacadeLocal;
import com.pelindo.ebtos.model.db.RecapReceiving;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycode-java
 */
@Stateless
public class RecapReceivingFacade extends AbstractFacade<RecapReceiving> implements RecapReceivingFacadeLocal, RecapReceivingFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapReceivingFacade() {
        super(RecapReceiving.class);
    }

}

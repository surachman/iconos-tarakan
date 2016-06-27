/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.RecapPenumpukanLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.RecapPenumpukanLoadFacadeLocal;
import com.pelindo.ebtos.model.db.RecapPenumpukanLoad;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycode-java
 */
@Stateless
public class RecapPenumpukanLoadFacade extends AbstractFacade<RecapPenumpukanLoad> implements RecapPenumpukanLoadFacadeLocal, RecapPenumpukanLoadFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapPenumpukanLoadFacade() {
        super(RecapPenumpukanLoad.class);
    }

}

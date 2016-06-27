/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.RecapPenumpukanTransloadFacadeRemote;
import com.pelindo.ebtos.model.db.RecapPenumpukanTransload;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class RecapPenumpukanTransloadFacade extends AbstractFacade<RecapPenumpukanTransload> implements RecapPenumpukanTransloadFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapPenumpukanTransloadFacade() {
        super(RecapPenumpukanTransload.class);
    }

    public Integer findRecapPenumpukanTransloadByPpkb(String operation, String no_ppkb) {
        Integer temp;
        try {
            temp = (Integer) getEntityManager().createNamedQuery("RecapPenumpukanTransload.Native.findRecapPenumpukanTransloadByPpkb").setParameter(1, operation).setParameter(2, no_ppkb).getSingleResult();
        } catch (NoResultException ex) {
            temp = 0;
        }
        return temp;
    }
}

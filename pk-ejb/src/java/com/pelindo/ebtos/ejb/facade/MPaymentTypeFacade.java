/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MPaymentTypeFacadeRemote;
import com.pelindo.ebtos.model.db.master.MPaymentType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.List;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class MPaymentTypeFacade extends AbstractFacade<MPaymentType> implements MPaymentTypeFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MPaymentTypeFacade() {
        super(MPaymentType.class);
    }
    public List<Object[]> findByAll() {
        return getEntityManager().createNamedQuery("MPaymentType.Native.findByAll").getResultList();
    }
}

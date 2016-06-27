/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterMaterai;
import java.math.BigDecimal;
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
public class MasterMateraiFacade extends AbstractFacade<MasterMaterai> implements MasterMateraiFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterMateraiFacade() {
        super(MasterMaterai.class);
    }

    public List<Object[]> findByCurr(String currency) {
        return getEntityManager().createNamedQuery("MasterMaterai.Native.findByCurr").setParameter(1, currency).getResultList();
    }

}

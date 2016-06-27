/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.MasterActivityTarifRuleFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityTarifRuleFacadeRemote;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import com.pelindo.ebtos.model.db.master.MasterActivityTarifRule;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterActivityTarifRuleFacade extends AbstractFacade<MasterActivityTarifRule> implements MasterActivityTarifRuleFacadeRemote, MasterActivityTarifRuleFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterActivityTarifRuleFacade() {
        super(MasterActivityTarifRule.class);
    }

    public List<Object[]> findAllActivityTarifRule(){
        return getEntityManager().createNamedQuery("MasterActivityTarifRule.Native.findAllActivityTarifRule").getResultList();
    }

}

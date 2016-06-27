/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PluggingOnlyRekapFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PluggingOnlyRekapFacadeRemote;
import com.pelindo.ebtos.model.db.PluggingOnlyRekap;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author wulan
 */
@Stateless
public class PluggingOnlyRekapFacade extends AbstractFacade<PluggingOnlyRekap> implements PluggingOnlyRekapFacadeLocal, PluggingOnlyRekapFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PluggingOnlyRekapFacade() {
        super(PluggingOnlyRekap.class);
    }

}

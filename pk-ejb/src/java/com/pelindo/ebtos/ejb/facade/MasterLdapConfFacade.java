/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterLdapConfFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterLdapConfFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterLdapConf;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class MasterLdapConfFacade extends AbstractFacade<MasterLdapConf> implements MasterLdapConfFacadeLocal, MasterLdapConfFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterLdapConfFacade() {
        super(MasterLdapConf.class);
    }

}

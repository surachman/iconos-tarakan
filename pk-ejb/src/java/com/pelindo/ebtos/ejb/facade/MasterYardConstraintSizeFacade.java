/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterYardConstraintSizeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterYardConstraintSizeFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterYardConstraintSize;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author USER
 */
@Stateless
public class MasterYardConstraintSizeFacade extends AbstractFacade<MasterYardConstraintSize> implements MasterYardConstraintSizeFacadeLocal, MasterYardConstraintSizeFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterYardConstraintSizeFacade() {
        super(MasterYardConstraintSize.class);
    }

}

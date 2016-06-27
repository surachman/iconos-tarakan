/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterYardConstraintTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterYardConstraintTypeFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterYardConstraintType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author USER
 */
@Stateless
public class MasterYardConstraintTypeFacade extends AbstractFacade<MasterYardConstraintType> implements MasterYardConstraintTypeFacadeLocal, MasterYardConstraintTypeFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterYardConstraintTypeFacade() {
        super(MasterYardConstraintType.class);
    }

}

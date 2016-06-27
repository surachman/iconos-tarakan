/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterCustTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterCustTypeFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterCustType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterCustTypeFacade extends AbstractFacade<MasterCustType> implements MasterCustTypeFacadeLocal, MasterCustTypeFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterCustTypeFacade() {
        super(MasterCustType.class);
    }

}

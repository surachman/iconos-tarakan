/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.MasterYardTypeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardTypeFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterYardType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author wulan
 */
@Stateless
public class MasterYardTypeFacade extends AbstractFacade<MasterYardType> implements MasterYardTypeFacadeLocal, MasterYardTypeFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterYardTypeFacade() {
        super(MasterYardType.class);
    }

}

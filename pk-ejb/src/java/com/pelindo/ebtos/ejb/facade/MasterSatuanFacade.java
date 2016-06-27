/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterSatuanFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterSatuanFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterSatuan;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterSatuanFacade extends AbstractFacade<MasterSatuan> implements MasterSatuanFacadeLocal, MasterSatuanFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterSatuanFacade() {
        super(MasterSatuan.class);
    }

}

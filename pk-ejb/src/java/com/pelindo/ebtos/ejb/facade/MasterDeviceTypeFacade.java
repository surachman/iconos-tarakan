/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterDeviceTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterDeviceTypeFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterDeviceType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class MasterDeviceTypeFacade extends AbstractFacade<MasterDeviceType> implements MasterDeviceTypeFacadeLocal, MasterDeviceTypeFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterDeviceTypeFacade() {
        super(MasterDeviceType.class);
    }

}

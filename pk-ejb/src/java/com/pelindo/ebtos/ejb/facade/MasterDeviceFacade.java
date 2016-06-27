/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterDeviceFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterDevice;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class MasterDeviceFacade extends AbstractFacade<MasterDevice> implements MasterDeviceFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterDeviceFacade() {
        super(MasterDevice.class);
    }

    public Object[] idKeyValid(String key){
        try {
            Object[] md = (Object[]) getEntityManager().createNamedQuery("MasterDevice.Native.CheckAppDevice").setParameter(1, key).getSingleResult();
            return md;
        } catch(NoResultException e) {
            return null;
        }
    }

}

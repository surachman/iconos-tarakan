/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterActivitySharingFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterActivitySharing;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class MasterActivitySharingFacade extends AbstractFacade<MasterActivitySharing> implements MasterActivitySharingFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterActivitySharingFacade() {
        super(MasterActivitySharing.class);
    }

}

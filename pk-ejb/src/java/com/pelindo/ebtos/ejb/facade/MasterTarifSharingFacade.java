/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterTarifSharingFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterTarifSharing;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class MasterTarifSharingFacade extends AbstractFacade<MasterTarifSharing> implements MasterTarifSharingFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterTarifSharingFacade() {
        super(MasterTarifSharing.class);
    }

}

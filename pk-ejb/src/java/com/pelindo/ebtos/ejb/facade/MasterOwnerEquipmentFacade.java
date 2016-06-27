/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterOwnerEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterOwnerEquipmentFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterOwnerEquipment;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class MasterOwnerEquipmentFacade extends AbstractFacade<MasterOwnerEquipment> implements MasterOwnerEquipmentFacadeLocal, MasterOwnerEquipmentFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterOwnerEquipmentFacade() {
        super(MasterOwnerEquipment.class);
    }

}

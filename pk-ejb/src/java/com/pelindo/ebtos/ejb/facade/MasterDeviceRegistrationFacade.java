/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterDeviceRegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterDeviceRegistrationFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterDeviceRegistration;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author x42jr
 */
@Stateless
public class MasterDeviceRegistrationFacade extends AbstractFacade<MasterDeviceRegistration> implements MasterDeviceRegistrationFacadeLocal, MasterDeviceRegistrationFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterDeviceRegistrationFacade() {
        super(MasterDeviceRegistration.class);
    }

}

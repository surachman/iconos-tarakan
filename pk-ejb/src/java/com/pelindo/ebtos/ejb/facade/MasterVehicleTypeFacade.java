/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterVehicleTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterVehicleTypeFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterVehicleType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author arie
 */
@Stateless
public class MasterVehicleTypeFacade extends AbstractFacade<MasterVehicleType> implements MasterVehicleTypeFacadeLocal, MasterVehicleTypeFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterVehicleTypeFacade() {
        super(MasterVehicleType.class);
    }
}

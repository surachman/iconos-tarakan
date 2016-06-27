/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterVesselTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterVesselTypeFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterVesselType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterVesselTypeFacade extends AbstractFacade<MasterVesselType> implements MasterVesselTypeFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterVesselTypeFacade() {
        super(MasterVesselType.class);
    }
   public List<Object[]>findMasterVesselType(){
        return getEntityManager().createNamedQuery("MasterVessel.Native.findMasterVesselType").getResultList();
   }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterContDamageFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterContDamage;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterContDamageFacade extends AbstractFacade<MasterContDamage> implements MasterContDamageFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterContDamageFacade() {
        super(MasterContDamage.class);
    }

    @Override
    public List<MasterContDamage> findAll(){
        return getEntityManager().createNamedQuery("MasterContDamage.findAll")
                .getResultList();
    }

    public List<Object[]> findMasterContDamages(){
        return getEntityManager().createNamedQuery("MasterContDamage.Native.findMasterContDamages").getResultList();
    }
}

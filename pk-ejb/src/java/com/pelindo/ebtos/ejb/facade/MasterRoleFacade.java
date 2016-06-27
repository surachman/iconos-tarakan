/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterRoleFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterRole;
import com.pelindo.ebtos.model.db.master.MasterView;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class MasterRoleFacade extends AbstractFacade<MasterRole> implements MasterRoleFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterRoleFacade() {
        super(MasterRole.class);
    }

    public List<String> findGroupsByView(Integer view){
        return getEntityManager().createNamedQuery("MasterRole.Native.findGroupsByView").setParameter(1, view).getResultList();
    }

    public List<MasterRole> findRolesByView(Integer id){
        return getEntityManager().createNamedQuery("MasterRole.findRolesByView").setParameter("id", id).getResultList();
    }
}

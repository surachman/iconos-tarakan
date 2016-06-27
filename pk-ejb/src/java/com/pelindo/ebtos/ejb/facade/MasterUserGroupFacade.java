/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterUserGroupFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterUserGroup;
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
public class MasterUserGroupFacade extends AbstractFacade<MasterUserGroup> implements MasterUserGroupFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterUserGroupFacade() {
        super(MasterUserGroup.class);
    }

    public List<MasterUserGroup> findAllForView(Integer id){
        return getEntityManager().createNamedQuery("MasterUserGroup.findAllForView").setParameter("id", id).getResultList();
    }

    public List<MasterUserGroup> findGroupsByView(Integer id){
        return getEntityManager().createNamedQuery("MasterUserGroup.findGroupsByView").setParameter("id", id).getResultList();
    }

    public MasterUserGroup findByGroupName(String name){
        List<MasterUserGroup> listUserGroup = getEntityManager().createNamedQuery("MasterUserGroup.findByGroup")
                .setParameter("group", name).setMaxResults(1).getResultList();
        if(listUserGroup.isEmpty())
            return null;
        return listUserGroup.get(0);
    }
    
    public List<Object[]> findLikeName (String customerName){
        return getEntityManager().createNamedQuery("MasterUserGroup.Native.findLikeName")
            .setParameter(1, customerName)
            .getResultList();
    }
    

}

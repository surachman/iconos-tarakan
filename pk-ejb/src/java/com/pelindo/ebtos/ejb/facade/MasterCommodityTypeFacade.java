/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterCommodityTypeFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterCommodityType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterCommodityTypeFacade extends AbstractFacade<MasterCommodityType> implements MasterCommodityTypeFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterCommodityTypeFacade() {
        super(MasterCommodityType.class);
    }

    public List<Object[]> findMasterCommodityTypes(){
        return getEntityManager().createNamedQuery("MasterCommodityType.Native.findMasterCommodityTypes").getResultList();
    }

    public Object[] findMasterCommodityTypeCode(String commodity_code){
        return (Object[]) getEntityManager().createNamedQuery("MasterCommodityType.Native.findMasterCommodityTypeCode").setParameter(1,commodity_code).getSingleResult();
    }

    public String findMasterCommodityByGenerate(){
        return (String) getEntityManager().createNamedQuery("MasterCommodityType.Native.findMasterCommodityByGenerate").getSingleResult();
    }
}

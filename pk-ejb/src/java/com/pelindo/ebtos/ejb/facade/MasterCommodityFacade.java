/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterCommodityFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.qtasnim.persistence.criteria.CriteriaHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterCommodityFacade extends AbstractFacade<MasterCommodity> implements MasterCommodityFacadeRemote, MasterCommodityFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterCommodityFacade() {
        super(MasterCommodity.class);
    }

    public List<Object[]> findAllNative() {
        return getEntityManager().createNamedQuery("MasterCommodity.Native.findAll").getResultList();
    }

    public List<Object[]> findMasterCommoditys() {
        return getEntityManager().createNamedQuery("MasterCommodity.Native.findMasterCommoditys").getResultList();
    }
     public List<Object[]> findMasterCommodityByCode(String commodity_type_code) {
        return getEntityManager().createNamedQuery("MasterCommodity.Native.findMasterCommodityByCode").setParameter(1, commodity_type_code).getResultList();
    }

    public String findMasterCommodityByGenerate(){
        return (String) getEntityManager().createNamedQuery("MasterCommodity.Native.findMasterCommodityByGenerate").getSingleResult();
    }

    public List<Object[]> findMasterCommodityForIdentify(String comm) {
        return getEntityManager().createNamedQuery("MasterCommodity.Native.findMasterCommodityForIdentify").setParameter(1, comm).getResultList();
    }

    public MasterCommodity getEmptyCommodity(){
        return find("000");
    }

    public int findAll_Count(Map<String,String> likes) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery();
        Root root = query.from(MasterCommodity.class);

        return count(builder, query, root, likes, null);
    }

    public List<MasterCommodity> findAll(int first, int pageSize, String sortField, boolean sortOrder, Map<String,String> likes) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery();
        Root root = query.from(MasterCommodity.class);

        return findAll(builder, query, root, first, pageSize, sortField, sortOrder, likes, null);
    }
}

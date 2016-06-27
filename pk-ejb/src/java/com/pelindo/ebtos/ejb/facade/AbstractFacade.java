/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.model.db.Invoice;
import com.qtasnim.persistence.criteria.CriteriaHelper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Iterator;
import java.util.Map;
import javax.persistence.criteria.Predicate;

/**
 *
 * @author dyware-java
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        if (id == null)
            return null;
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public void createAndRefresh(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
        getEntityManager().refresh(entity);
    }

    protected List<T> findAll(CriteriaBuilder builder, CriteriaQuery query, Root root, int first, int pageSize, String sortField, boolean sortOrder, Map<String,String> filters, List<Predicate> additionalConditions) {
        query.select(root);

        if (sortField != null) {
            if (sortOrder) {
                query.orderBy(
                        builder.asc(
                                CriteriaHelper.translateRefference(root, sortField)
                            )
                        );
            } else {
                query.orderBy(
                        builder.desc(
                                CriteriaHelper.translateRefference(root, sortField)
                            )
                        );
            }
        }

        Iterator<Map.Entry<String,String>> it = filters.entrySet().iterator();
        List<Predicate> conditions = new ArrayList<Predicate>();

        while (it.hasNext()) {
            Map.Entry<String,String> pairs = (Map.Entry) it.next();

            conditions.add(builder.and(
                                builder.like(
                                    builder.lower(
                                        CriteriaHelper.translateRefference(root, pairs.getKey())
                                    ), "%" + pairs.getValue() + "%"
                                )
                            ));

            it.remove();
        }

        if (additionalConditions != null) {
             conditions.add(
                        builder.and(
                            additionalConditions.toArray(new Predicate[0])
                        )
                    );
        }

        if (!conditions.isEmpty()) {
            query.where(conditions.toArray(new Predicate[0]));
        }

        return getEntityManager().createQuery(query)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }

    protected int count(CriteriaBuilder builder, CriteriaQuery query, Root root, Map<String,String> filters, List<Predicate> additionalConditions) {
        query.select(builder.count(root));

        Iterator<Map.Entry<String,String>> it = filters.entrySet().iterator();
        List<Predicate> conditions = new ArrayList<Predicate>();

        while (it.hasNext()) {
            Map.Entry<String,String> pairs = (Map.Entry) it.next();

            conditions.add(builder.and(
                                builder.like(
                                    builder.lower(
                                        CriteriaHelper.translateRefference(root, pairs.getKey())
                                    ), "%" + pairs.getValue() + "%"
                                )
                            ));

            it.remove();
        }

        if (additionalConditions != null) {
             conditions.add(
                        builder.and(
                            additionalConditions.toArray(new Predicate[0])
                        )
                    );
        }

        if (!conditions.isEmpty()) {
            query.where(conditions.toArray(new Predicate[0]));
        }
        
        return ((Long) getEntityManager().createQuery(query).getSingleResult()).intValue();
    }
    
     protected List<Integer> decimalsToInts(List<BigDecimal> ints)
    {
        List<Integer> results = new ArrayList<Integer>();
        for(BigDecimal i: ints)
        {
            results.add(decimalToInt(i));
        }
        return results;
    }
     
    protected List<Object[]> objectsDecimalsToObjectsInts(List<Object[]> ints)
    {
        for(Object[] row: ints)
        {
            objectDecimalsToObjectInts(row);
        }
        return ints;
    }
    
    protected Object[] objectDecimalsToObjectInts(Object[] row)
    {
        if(row == null)
            return row;
        
        for(int i = 0; i < row.length; i++)
        {
            if(row[i] instanceof BigDecimal)
            {
                row[i] = decimalToInt((BigDecimal) row[i]);
            }
        }
        return row;
    }
    
    protected Integer decimalToInt(BigDecimal i)
    {
        return i == null ? null : i.intValue();
    }



}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ReeferMonitoringFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ReeferMonitoringFacadeRemote;
import com.pelindo.ebtos.model.db.ReeferMonitoring;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class ReeferMonitoringFacade extends AbstractFacade<ReeferMonitoring> implements ReeferMonitoringFacadeRemote, ReeferMonitoringFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ReeferMonitoringFacade() {
        super(ReeferMonitoring.class);
    }

    public List<Object[]> findByIdReefer (Integer id_reefer){
        return getEntityManager().createNamedQuery("ReeferMonitoring.Native.findByIdReefer").setParameter(1, id_reefer).getResultList();
    }

    public List<Integer> findAllByIdReefer (Integer id_reefer){
        return getEntityManager().createNamedQuery("ReeferMonitoring.Native.findAllByIdReefer").setParameter(1, id_reefer).getResultList();
    }

    public List<ReeferMonitoring> findByContNo (String contNo){
            return getEntityManager().createNamedQuery("ReeferMonitoring.findByContNo")
                    .setParameter("contNo", contNo)
                    .getResultList();
    }

    public BigDecimal findLastTemperature(String contNo) {
        try {
            return (BigDecimal) getEntityManager().createNamedQuery("ReeferMonitoring.Native.findLastTemperature")
                    .setParameter(1, contNo)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}

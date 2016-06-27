/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.VwReceivingMonitoringFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.VwReceivingMonitoringFacadeRemote;
import com.pelindo.ebtos.model.db.VwReceivingMonitoring;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author arie
 */
@Stateless
public class VwReceivingMonitoringFacade extends AbstractFacade<VwReceivingMonitoring> implements VwReceivingMonitoringFacadeRemote, VwReceivingMonitoringFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public VwReceivingMonitoringFacade() {
        super(VwReceivingMonitoring.class);
    }

    public List<VwReceivingMonitoring> findByNoPpkb(String noPpkb) {
        return getEntityManager().createNamedQuery("VwReceivingMonitoring.Native.findByNoPpkb").setParameter(1, noPpkb).getResultList();
    }

    public List<VwReceivingMonitoring> findByInternalStatus(String internalStatus) {
        return getEntityManager().createNamedQuery("VwReceivingMonitoring.Native.findByInternalstatus").setParameter(1, internalStatus).getResultList();
    }

    public List<VwReceivingMonitoring> findByNoPpkbInternalStatus(String noPpkb, String internalStatus) {
        return getEntityManager().createNamedQuery("VwReceivingMonitoring.Native.findByNoPpkbInternalStatus").setParameter(1, noPpkb).setParameter(2, internalStatus).getResultList();
    }
}

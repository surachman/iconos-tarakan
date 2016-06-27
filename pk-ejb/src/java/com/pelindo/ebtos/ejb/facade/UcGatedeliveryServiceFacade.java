/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.UcGatedeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.UcGatedeliveryServiceFacadeLocal;
import com.pelindo.ebtos.model.db.UcGatedeliveryService;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author postgres
 */
@Stateless
public class UcGatedeliveryServiceFacade extends AbstractFacade<UcGatedeliveryService> implements UcGatedeliveryServiceFacadeLocal, UcGatedeliveryServiceFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public UcGatedeliveryServiceFacade() {
        super(UcGatedeliveryService.class);
    }
    public Object[] findUcGatedeliveryServiceByGate(String job_slip) {
        Object[] temp = new Object[6];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("UcGatedeliveryService.Native.findUcGatedeliveryServiceByGate").setParameter(1, job_slip).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public UcGatedeliveryService findNotDeliveredYetByJobSlip(String jobslip) {
        try {
            return (UcGatedeliveryService) getEntityManager().createNamedQuery("UcGatedeliveryService.findNotDeliveredYetByJobSlip")
                    .setParameter("jobslip", jobslip)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Object[] findUcGatedeliveryServiceByDateOutNull(String job_slip) {
        Object[] temp = new Object[2];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("UcGatedeliveryService.Native.findUcGatedeliveryServiceByDateOutNull").setParameter(1, job_slip).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
    public List<String> findUcGatedeliveryServiceByAutoComplete(String jobslip) {
        List<String> temp = new ArrayList<String>();
        try {
            temp = getEntityManager().createNamedQuery("UcGatedeliveryService.Native.findUcGatedeliveryServiceByAutoComplete").setParameter(1, jobslip).getResultList();
            
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.UcGatereceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.UcGatereceivingServiceFacadeLocal;
import com.pelindo.ebtos.model.db.UcGatereceivingService;
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
public class UcGatereceivingServiceFacade extends AbstractFacade<UcGatereceivingService> implements UcGatereceivingServiceFacadeLocal, UcGatereceivingServiceFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public UcGatereceivingServiceFacade() {
        super(UcGatereceivingService.class);
    }

    public Object[] findUcGatereceivingServiceByGatee(String job_slip) {
        Object[] temp = new Object[6];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("UcGatereceivingService.Native.findUcGatereceivingServiceByGatee").setParameter(1, job_slip).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findUcGatereceivingServiceByDateOutNull(String job_slip) {
        Object[] temp = new Object[2];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("UcGatereceivingService.Native.findUcGatereceivingServiceByDateOutNull").setParameter(1, job_slip).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<String> findUcGatereceivingServiceByAutoComplete(String jobslip) {
        List<String> temp = new ArrayList<String>();
        try {
            temp = getEntityManager().createNamedQuery("UcGatereceivingService.Native.findUcGatereceivingServiceByAutoComplete").setParameter(1, jobslip).getResultList();

        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public Object[] findUcGatereceivingServiceByCancelInvoice(String job_slip) {
        Object[] temp = new Object[2];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("UcGatereceivingService.Native.findUcGatereceivingServiceByCancelInvoice").setParameter(1, job_slip).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
}

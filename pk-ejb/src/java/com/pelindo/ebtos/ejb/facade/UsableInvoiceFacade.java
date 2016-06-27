/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.UsableInvoiceFacadeLocal;
import com.pelindo.ebtos.model.db.UsableInvoice;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author R. Seno Anggoro A
 */
@Stateless
public class UsableInvoiceFacade extends AbstractFacade<UsableInvoice> implements UsableInvoiceFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public UsableInvoiceFacade() {
        super(UsableInvoice.class);
    }

    public String getAvailableNoInvoice(){
        UsableInvoice usableInvoice;

        try {
            usableInvoice = (UsableInvoice) getEntityManager().createNamedQuery("UsableInvoice.findAll").setMaxResults(1).getSingleResult();

            if (usableInvoice != null) {
                String noInvoice = usableInvoice.getNoInvoice();
                remove(usableInvoice);
                return noInvoice;
            }
        } catch (NoResultException nre) {
            return null;
        }

        return null;
    }
}

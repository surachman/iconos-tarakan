/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.UsableFakturPajakFacadeLocal;
import com.pelindo.ebtos.model.db.UsableFakturPajak;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author R. Seno Anggoro A
 */
@Stateless
public class UsableFakturPajakFacade extends AbstractFacade<UsableFakturPajak> implements UsableFakturPajakFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public UsableFakturPajakFacade() {
        super(UsableFakturPajak.class);
    }

    public String getAvailableNoFakturPajak(){
        UsableFakturPajak usableFakturPajak;

        try {
            usableFakturPajak = (UsableFakturPajak) getEntityManager().createNamedQuery("UsableFakturPajak.findAll").setMaxResults(1).getSingleResult();

            if (usableFakturPajak != null) {
                String noFakturPajak = usableFakturPajak.getNoFakturPajak();
                remove(usableFakturPajak);
                return noFakturPajak;
            }
        } catch (NoResultException nre) {
            return null;
        }

        return null;
    }
}

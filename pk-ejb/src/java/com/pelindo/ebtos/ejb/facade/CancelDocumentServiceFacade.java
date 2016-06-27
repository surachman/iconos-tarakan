/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.CancelDocumentServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.CancelDocumentServiceFacadeRemote;
import com.pelindo.ebtos.model.db.CancelDocumentService;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author x42jr
 */
@Stateless
public class CancelDocumentServiceFacade extends AbstractFacade<CancelDocumentService> implements CancelDocumentServiceFacadeRemote, CancelDocumentServiceFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public CancelDocumentService createAndFetch(CancelDocumentService cancelDocumentService) {
        em.persist(cancelDocumentService);
        return find(cancelDocumentService.getJobSlip());
    }

    public CancelDocumentServiceFacade() {
        super(CancelDocumentService.class);
    }

    public List<CancelDocumentService> findByNoReg(String noReg) {
        return getEntityManager().createNamedQuery("CancelDocumentService.findByNoReg")
                .setParameter("noReg", noReg)
                .getResultList();
    }
}

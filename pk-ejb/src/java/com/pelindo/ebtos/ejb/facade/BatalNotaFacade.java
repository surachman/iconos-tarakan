/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.BatalNotaFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.InvoiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.UsableFakturPajakFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.UsableInvoiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.BatalNotaFacadeRemote;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.UsableFakturPajak;
import com.pelindo.ebtos.model.db.UsableInvoice;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class BatalNotaFacade extends AbstractFacade<BatalNota> implements BatalNotaFacadeLocal, BatalNotaFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;
    
    @EJB
    private InvoiceFacadeLocal invoiceFacadeLocal;
    @EJB
    private UsableFakturPajakFacadeLocal usableFakturPajakFacadeLocal;
    @EJB
    private UsableInvoiceFacadeLocal usableInvoiceFacadeLocal;

    protected EntityManager getEntityManager() {
        return em;
    }

    public BatalNotaFacade() {
        super(BatalNota.class);
    }

    public void create(BatalNota batalNota) {
        Invoice invoice = invoiceFacadeLocal.findByNoInvoice(batalNota.getNoInvoice());

        if (invoice != null) {
            String noFakturPajak = invoice.getNoFakturPajak();
            String noInvoice = invoice.getNoInvoice();
            batalNota.setNoFakturPajak(noFakturPajak);

            if (invoice.getJournaledDate() == null) {
                UsableFakturPajak usableFakturPajak = new UsableFakturPajak(noFakturPajak);
                UsableInvoice usableInvoice = new UsableInvoice(noInvoice);

                usableFakturPajakFacadeLocal.create(usableFakturPajak);
                usableInvoiceFacadeLocal.create(usableInvoice);
            }
        }

        super.create(batalNota);
    }

    public List<Object[]> findByBatalNotaList() {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("BatalNota.Native.findByBatalNotaList").getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
}

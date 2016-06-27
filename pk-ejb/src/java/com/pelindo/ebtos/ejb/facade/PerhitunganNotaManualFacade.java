/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PerhitunganNotaManualFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganNotaManualFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganNotaManual;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dyware-Dev01
 */
@Stateless
public class PerhitunganNotaManualFacade extends AbstractFacade<PerhitunganNotaManual> implements PerhitunganNotaManualFacadeLocal, PerhitunganNotaManualFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PerhitunganNotaManualFacade() {
        super(PerhitunganNotaManual.class);
    }

    public List<Object[]> findAllNative() {
        return getEntityManager().createNamedQuery("PerhitunganNotaManual.Native.findAllNative").getResultList();
    }
}

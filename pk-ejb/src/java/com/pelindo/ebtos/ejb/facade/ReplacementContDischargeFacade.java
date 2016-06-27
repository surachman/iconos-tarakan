/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.ReplacementContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.ReplacementContDischargeFacadeLocal;
import com.pelindo.ebtos.model.db.ReplacementContDischarge;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dyware-Dev01
 */
@Stateless
public class ReplacementContDischargeFacade extends AbstractFacade<ReplacementContDischarge> implements ReplacementContDischargeFacadeLocal, ReplacementContDischargeFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ReplacementContDischargeFacade() {
        super(ReplacementContDischarge.class);
    }

    public List<Object[]> findReplacementContDischargeByPPKB(String no_ppkb) {
        return getEntityManager().createNamedQuery("ReplacementContDischarge.Native.findReplacementContDischargeByPPKB").setParameter(1, no_ppkb).getResultList();
    }

}

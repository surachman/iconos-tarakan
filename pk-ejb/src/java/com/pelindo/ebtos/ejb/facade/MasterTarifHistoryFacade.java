/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterTarifHistoryFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterTarifHistory;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterTarifHistoryFacade extends AbstractFacade<MasterTarifHistory> implements MasterTarifHistoryFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterTarifHistoryFacade() {
        super(MasterTarifHistory.class);
    }

}

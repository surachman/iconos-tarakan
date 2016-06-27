/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.UnderpaymentHistoryFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.UnderpaymentHistoryFacadeRemote;
import com.pelindo.ebtos.model.db.UnderpaymentHistory;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author x42jr
 */
@Stateless
public class UnderpaymentHistoryFacade extends AbstractFacade<UnderpaymentHistory> implements UnderpaymentHistoryFacadeRemote, UnderpaymentHistoryFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public UnderpaymentHistoryFacade() {
        super(UnderpaymentHistory.class);
    }

}

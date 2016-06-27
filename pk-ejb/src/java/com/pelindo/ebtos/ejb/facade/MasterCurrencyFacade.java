/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterCurrencyFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterCurrencyFacade extends AbstractFacade<MasterCurrency> implements MasterCurrencyFacadeLocal, MasterCurrencyFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterCurrencyFacade() {
        super(MasterCurrency.class);
    }

    public String findSymbolByTradetype(String trade_type){
        String temp = null;
        try {
            temp = (String) getEntityManager().createNamedQuery("MasterCurrency.Native.findSymbolByTradetype").setParameter(1, trade_type).getSingleResult();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public MasterCurrency findByTradetype(String tradeType){
        try {
            return (MasterCurrency) getEntityManager().createNamedQuery("MasterCurrency.findByTradetype")
                    .setParameter("tradeType", tradeType)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public MasterCurrency findByMataUangId(String mataUangId){
        try {
            return (MasterCurrency) getEntityManager().createNamedQuery("MasterCurrency.findByMataUangId")
                    .setParameter("mataUangId", mataUangId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public MasterCurrency getIDRCurrency() {
        return find(MasterCurrency.IDR_CURR_CODE);
    }

    public MasterCurrency getUSDCurrency() {
        return find(MasterCurrency.USD_CURR_CODE);
    }
}

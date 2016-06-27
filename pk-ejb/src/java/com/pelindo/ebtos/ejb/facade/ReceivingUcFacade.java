/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.ReceivingUcFacadeRemote;
import com.pelindo.ebtos.model.db.ReceivingUc;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class ReceivingUcFacade extends AbstractFacade<ReceivingUc> implements ReceivingUcFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ReceivingUcFacade() {
        super(ReceivingUc.class);
    }

    public List<Object[]> findReceivingUcByPPKBnReg(String no_ppkb, String no_reg){
        return getEntityManager().createNamedQuery("ReceivingUc.Native.findByPpkbNReg").setParameter(1, no_ppkb).setParameter(2, no_reg).getResultList();
    }

    public String generateId(String bulan) {
        return (String) getEntityManager().createNamedQuery("ReceivingUc.Native.generateId").setParameter(1, bulan).getSingleResult();
    }

}

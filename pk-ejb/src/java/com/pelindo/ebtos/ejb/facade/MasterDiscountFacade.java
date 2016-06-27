/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.MasterDiscountFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterDiscountFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterDiscount;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class MasterDiscountFacade extends AbstractFacade<MasterDiscount> implements MasterDiscountFacadeLocal, MasterDiscountFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterDiscountFacade() {
        super(MasterDiscount.class);
    }

    /**
     *
     * @param   custCode = Customer Code
     *          actCode  = Activity Code
     * @return
     *          Discount Value
     */
    public BigDecimal[] getValidDiscount(String custCode, String actCode){
        try {
            Object[] result = (Object[]) getEntityManager().createNamedQuery("MasterDiscount.Native.getValidDiscount")
                    .setParameter(1, custCode)
                    .setParameter(2, actCode)
                    .getSingleResult();

            if (result != null) {
                return new BigDecimal[] {
                    (BigDecimal) result[0], (BigDecimal) result[1]
                };
            } else {
                return new BigDecimal[] {
                    BigDecimal.ZERO, null
                };
            }
        } catch(NoResultException e) {
            return new BigDecimal[] {
                BigDecimal.ZERO, null
            };
        }
    }

    public List<Object[]> findAllDiscount() {
        return getEntityManager().createNamedQuery("MasterDiscount.Native.findAllDiscount").getResultList();
    }
}

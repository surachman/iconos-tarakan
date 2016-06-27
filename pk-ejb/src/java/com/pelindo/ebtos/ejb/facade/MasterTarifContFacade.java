/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.MasterDiscountFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterTarifContFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterTarifCont;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class MasterTarifContFacade extends AbstractFacade<MasterTarifCont> implements MasterTarifContFacadeRemote, MasterTarifContFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @EJB
    private MasterDiscountFacadeLocal masterDiscountFacadeLocal;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterTarifContFacade() {
        super(MasterTarifCont.class);
    }

    public List<Object[]> findAllForMaster() {
        return getEntityManager().createNamedQuery("MasterTarifCont.Native.findAllForMaster").getResultList();
    }

    public List<Object[]> findAmountByActivity(String act) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try{
            temp = getEntityManager().createNamedQuery("MasterTarifCont.Native.findByIdActivity").setParameter(1, act).getResultList();
        } catch (NoResultException ex){
            temp = null;
        }
        return temp;
    }

    public BigDecimal findByCurrCodeAndActivity(String curr_code, String activity_code) {
        try {
            return (BigDecimal) getEntityManager().createNamedQuery("MasterTarifCont.Native.findByActivityAndCurr").setParameter(1, curr_code).setParameter(2, activity_code).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    /**
     * mengembalikan tarif yang langsung dipotong discount jika ada
     *
     * @param currCode
     * @param activityCode
     * @param customerCode
     * @return
     */
    public BigDecimal findChargeAfterDiscount(String currCode, String activityCode, String customerCode) {
        try {
            BigDecimal charge = (BigDecimal) getEntityManager().createNamedQuery("MasterTarifCont.Native.findByActivityAndCurr").setParameter(1, currCode).setParameter(2, activityCode).getSingleResult();
            BigDecimal[] discount = masterDiscountFacadeLocal.getValidDiscount(customerCode, activityCode);
            BigDecimal discountAsPercentage = discount[0];
            BigDecimal discountAsMoney = discount[1];

            if (discountAsMoney == null) {
                discountAsMoney = charge.multiply(discountAsPercentage);
            }

            return charge.subtract(discountAsMoney);
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception cought when call findChargeAfterDiscount", re);
            return BigDecimal.ZERO;
        }
    }

    public Integer findForUsd(String curr_code, String activity_code) {
        Integer amount = 0;
        try {
            amount = ((Number) getEntityManager().createNamedQuery("MasterTarifCont.Native.findForUsd")
                    .setParameter(1, curr_code).setParameter(2, activity_code).getSingleResult()).intValue();
        } catch (NoResultException ex) {
            amount = null;
        }
        return amount;
    }

    public BigDecimal findChangeStatusChargeByActivityAndCurr(String currCode, String fclCode, String lclCode) {
        try {
            BigDecimal fclCharge = findByCurrCodeAndActivity(currCode, fclCode);
            BigDecimal lclCharge = findByCurrCodeAndActivity(currCode, lclCode);
            return lclCharge.subtract(fclCharge);
        } catch (NoResultException ex) {
            return BigDecimal.ZERO;
        }
    }
}

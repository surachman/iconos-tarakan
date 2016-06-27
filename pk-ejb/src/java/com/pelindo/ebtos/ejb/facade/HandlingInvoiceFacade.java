/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.HandlingInvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.HandlingInvoiceFacadeLocal;
import com.pelindo.ebtos.model.db.HandlingInvoice;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycode-java
 */
@Stateless
public class HandlingInvoiceFacade extends AbstractFacade<HandlingInvoice> implements HandlingInvoiceFacadeLocal, HandlingInvoiceFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public HandlingInvoiceFacade() {
        super(HandlingInvoice.class);
    }

    public String generateId(String bulan) {
        String temp;
        try {
            temp = (String) getEntityManager().createNamedQuery("HandlingInvoice.Native.generateId").setParameter(1, bulan).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    //HandlingInvoice.Native.findInvoice
    public String findInvoice(String no_ppkb, String operation) {
        String temp;
        try {
            temp = (String) getEntityManager().createNamedQuery("HandlingInvoice.Native.findInvoice").setParameter(1, no_ppkb).setParameter(2, operation).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] handlingInvoiceDischarge(String no_ppkb, boolean include) {
        Object[] temp = new Object[24];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("HandlingInvoice.Native.handlingInvoiceDischarge").setParameter(1, no_ppkb).setParameter(2, include).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
    /*
    paket_handling 0
    receiving 1
    penumpukan 2
    reefer 3
    batal_muat 4
    jumlah 5
    ppn 6
    kalippn 7
    addppn 8
    materai 9
    total 10
    ppnmaterai 11
    costumer_name 12
    custumer_address 13
    costumer_npwp 14
    vessel_name 15
    trade_type 16
    invoice 17
     */

    public Object[] handlingInvoiceLoad(String no_ppkb) {
        Object[] temp = new Object[27];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("HandlingInvoice.Native.handlingInvoiceLoad").setParameter(1, no_ppkb).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    /**
     *
     * @param kasir
     * @return
     *      0 = subtotal,
     *      1 = ppn,
     *      2 = materai,
     *      3 = total
     */
    public Object[] handlingInvoiceLoadCBTG(String noPpkb) {
        try {
            return (Object[]) getEntityManager().createNamedQuery("HandlingInvoice.Native.handlingInvoiceLoadCBTG").setParameter(1, noPpkb).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    /**
     *
     * @param kasir
     * @return
     *      0 = subtotal,
     *      1 = ppn,
     *      2 = materai,
     *      3 = total
     */
    public Object[] handlingInvoiceLoadPLND(String noPpkb) {
        try {
            return (Object[]) getEntityManager().createNamedQuery("HandlingInvoice.Native.handlingInvoiceLoadPLND").setParameter(1, noPpkb).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

     /**
     *
     * @param kasir
     * @return
      *     list of: (index 1: IDR, index 2: USD)
     *      0 = handling_charge,
     *      1 = penumpukan_charge,
     *      2 = hatchmoves_charge,
     *      3 = transhipment_charge
     *      4 = shifting_charge
     */
    @Override
    public List<Object[]> findContainerServicesChargeDetailByNoPpkb(String noPpkb) {
        return getEntityManager().createNamedQuery("HandlingInvoice.Native.findContainerServicesChargeDetailByNoPpkb")
                .setParameter(1, noPpkb)
                .setParameter(2, noPpkb)
                .getResultList();
    }

     /**
     *
     * @param kasir
     * @return
      *     list of: (index 1: IDR, index 2: USD)
     *      0 = handling_charge,
     *      1 = penumpukan_charge,
     *      2 = hatchmoves_charge,
     *      3 = transhipment_charge
     *      4 = shifting_charge
     */
    public List<Object[]> findContainerServicesChargeDetailDischargeByNoPpkb(String noPpkb) {
        return getEntityManager().createNamedQuery("HandlingInvoice.Native.findContainerServicesChargeDetailDischargeByNoPpkb")
                .setParameter(1, noPpkb).setParameter(2, noPpkb).getResultList();
    }

     /**
     *
     * @param kasir
     * @return
      *     list of: (index 1: IDR, index 2: USD)
     *      0 = handling_charge,
     *      1 = penumpukan_charge,
     *      2 = hatchmoves_charge,
     *      3 = transhipment_charge
     *      4 = shifting_charge
     */
    public List<Object[]> findContainerServicesChargeDetailLoadByNoPpkb(String noPpkb) {
        return getEntityManager().createNamedQuery("HandlingInvoice.Native.findContainerServicesChargeDetailLoadByNoPpkb")
                .setParameter(1, noPpkb).setParameter(2, noPpkb).getResultList();
    }

    @Override
    public BigDecimal getUpahLembur(String noPpkb) {
        List results = getEntityManager().createNamedQuery("HandlingInvoice.Native.getUpahLembur")
            .setParameter(1, noPpkb).getResultList();
        if (results.isEmpty()) {
            return BigDecimal.ZERO;
        } else {
            return (BigDecimal)results.get(0);
        }
                
    }
    
    
}

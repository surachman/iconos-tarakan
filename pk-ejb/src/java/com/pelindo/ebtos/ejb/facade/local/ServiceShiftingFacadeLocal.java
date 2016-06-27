/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ServiceShifting;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface ServiceShiftingFacadeLocal {

    void create(ServiceShifting serviceShifting);

    void edit(ServiceShifting serviceShifting);

    void remove(ServiceShifting serviceShifting);

    ServiceShifting find(Object id);

    List<ServiceShifting> findAll();

    List<ServiceShifting> findRange(int[] range);

    List<Object[]> findServiceShiftingReshippingFalse(String no_ppkb);

    List<Object[]> findServiceShiftingReshipping(String no_ppkb);

    List<Object[]> findServiceShiftingReshippingNotToCY(String no_ppkb);

    List<Object[]> findServiceShiftingReshippingWithout(String no_ppkb);

    int count();

    Object[] findByContNo(String no_ppkb, String cont_no);

    Object[] findByContNoMobile(String no_ppkb, String cont_no);

    Object[] findByContNoReship(String no_ppkb, String cont_no, String is_reshipping);

    List<Object[]> findRekap(String no_ppkb, String operation);

    List<Object[]> findByPPKB(String no_ppkb);

    List<Integer> findByPPKBPaid(String no_ppkb);

    public java.util.List<com.pelindo.ebtos.model.db.ServiceShifting> findByNoPpkbAndHasPaid(java.lang.String noPpkb);

    public int removeMasterActivityByNoPpkbAndHasPaid(java.lang.String noPpkb);

    public java.util.List<java.lang.Object[]> findServiceShiftingByBayLocationAsObjectArray(java.lang.String ppkb, int bay);

    public ServiceShifting findByNoPpkbAndContNo(String noPpkb, String contNo);
}

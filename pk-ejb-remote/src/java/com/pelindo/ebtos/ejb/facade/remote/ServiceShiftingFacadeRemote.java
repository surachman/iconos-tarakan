/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ServiceShifting;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ServiceShiftingFacadeRemote {

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

    Object[] findShiftableContainer(String no_ppkb, String cont_no);

    Object[] findByContNoMobile(String no_ppkb, String cont_no);

    Object[] findByContNoReship(String no_ppkb, String cont_no, boolean is_reshipping);

    List<Object[]> findRekap(String no_ppkb, String operation);

    List<Object[]> findByPPKB(String no_ppkb);

    List<Integer> findByPPKBPaid(String no_ppkb);

    public java.util.List<com.pelindo.ebtos.model.db.ServiceShifting> findByNoPpkbAndReshippingStatus(java.lang.String noPpkb, java.lang.Boolean isReshipping);

    List<Object[]> findByPpkbDischargeConfirmList(String no_ppkb);

    List<Object[]> findByPpkbDischargeConfirmSelect(String no_ppkb);

    List<Object[]> findByPpkbLiftOffList(String no_ppkb);

    List<Object[]> findByPpkbLiftOffSelect(String no_ppkb);

    List<Object[]> findByPpkbBayPlanLoadList(String no_ppkb);

    List<Object[]> findByPpkbLiftOnList(String no_ppkb);

    List<Object[]> findByPpkbLiftOnListSelect(String no_ppkb);

    List<Object[]> findByPpkbLoadConfirmList(String no_ppkb);

    List<Object[]> findByPpkbLoadConfirmSelect(String no_ppkb);

    Object[] findByPpkbDischargeConfirmSelectHht(String no_ppkb, String cont_no);

    Object[] findByPpkbLiftOffeConfirmSelectHht(String cont_no, String pos);

    Object[] findByPpkbLiftOnConfirmSelectHht(String cont_no, String pos);

    Object[] findByPpkbLoadConfirmSelectHht(String no_ppkb,String cont_no, String pos);

    Object[] findByPpkbLoadConfirmSelectHht2(String cont_no, String pos);

    public com.pelindo.ebtos.model.db.ServiceShifting findContainerShiftingByPositionAndShiftDestination(java.lang.String noPpkb, java.lang.String contNo, java.lang.String position, java.lang.String shiftTo);

    public com.pelindo.ebtos.model.db.ServiceShifting findByPositionAndContNo(java.lang.String noPpkb, java.lang.String contNo, java.lang.String position);

    public java.lang.Object[] findLiftableOnContainer(java.lang.String contNo);

    public com.pelindo.ebtos.model.db.ServiceShifting findByNoPpkbAndContNo(java.lang.String noPpkb, java.lang.String contNo);

    public com.pelindo.ebtos.model.db.ServiceShifting findContainerShiftingByContNoPositionAndShiftDestination(java.lang.String contNo, java.lang.String position, java.lang.String shiftTo);

    public java.util.List<com.pelindo.ebtos.model.db.ServiceShifting> findByNotReshippedShiftingByNoPpkb(java.lang.String noPpkb);

    public com.pelindo.ebtos.model.db.ServiceShifting findByNoPpkbContNoAndReshippingStatus(java.lang.String noPpkb, java.lang.String contNo, java.lang.Boolean isReshipping);

    public List<ServiceShifting> findServiceShiftingLoadConfirm(String noPpkb, short bay);
}

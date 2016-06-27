/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.UcShiftingService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface UcShiftingServiceFacadeRemote {

    void create(UcShiftingService ucShiftingService);

    void edit(UcShiftingService ucShiftingService);

    void remove(UcShiftingService ucShiftingService);

    UcShiftingService find(Object id);

    List<UcShiftingService> findAll();

    List<UcShiftingService> findRange(int[] range);

    int count();

    public List<Object[]> findUcShiftingServiceWithoutPlan(String no_ppkb);

    List<Object[]> findUcShiftingServiceWithPlan(String no_ppkb);

    List<Object[]> findByPpkb(String no_ppkb);

    List<Integer> findByPPKBPaid(String no_ppkb);

    Object[] findByPpkbBlNo(String no_ppkb, String bl_no, Integer is_reshipping);

    public java.util.List<com.pelindo.ebtos.model.db.UcShiftingService> findByNoPpkbAndReshippingStatus(java.lang.String noPpkb, boolean isReshipping);

    public com.pelindo.ebtos.model.db.UcShiftingService findByNoPpkbAndBlNo(java.lang.String noPpkb, java.lang.String blNo);

    public int deleteByNoPpkbAndBlNo(java.lang.String noPpkb, java.lang.String blNo);
}

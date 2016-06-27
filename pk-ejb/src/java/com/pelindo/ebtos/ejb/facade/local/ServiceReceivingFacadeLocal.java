/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.master.MasterPort;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface ServiceReceivingFacadeLocal {

    void create(ServiceReceiving serviceReceiving);

    void edit(ServiceReceiving serviceReceiving);

    void remove(ServiceReceiving serviceReceiving);

    ServiceReceiving find(Object id);

    List<ServiceReceiving> findAll();

    List<ServiceReceiving> findRange(int[] range);

    int count();

    List<Object[]> findServiceReceivings();

    Object[] findServiceReceivingByPpkbb(String no_ppkb);

    List<Object[]> findServiceReceivingByPpkbList(String no_ppkb);

    List<Object[]> findServiceReceivingByPpkbList2(String no_ppkb);

    List<Object[]> findServiceReceivingByPpkbReefer(String no_ppkb);

    Object[] findContainerReefer(String cont_no);

    List<Object[]> findLoadRecapsByPPKB(String no_ppkb);

    List<Object[]> findServiceReceivingByPPKB(String no_ppkb);

    Object[] findServiceReceivingContNo(String no_ppkb, String cont_no);

    List<Object[]> findServiceReceivingsStuffingService(String no_ppkb);

    Long findRealisasiJobSlip(String no_ppkb);

    Object[] findByPpkbAndContNo(String no_ppkb, String cont_no);

    List<Object[]> findByPpkbAndContNoFront(String no_ppkb);

    List<Object[]> findServiceReceivingFrontByPPKB(String no_ppkb);

    List<String> findServiceReceivingByAutoComplete(String no_ppkb,String cont_no);

    Object[] findServiceReceivingByIsLiftOn(String cont_no, Boolean is_lifton);

    List<Object[]> findServiceReceivingsMovement();

    List<Object[]> findServiceReceivingsStuffingServiceByBL(String bl, String no_ppkb);

    public java.lang.Integer updateStatusCancelLoadingByNoPpkb(java.lang.String noPpkb, java.lang.String statusCancelLoading);

    public java.util.List<com.pelindo.ebtos.model.db.ServiceReceiving> findByNoPpkb(java.lang.String noPpkb);

    public int updatePlanningVessel(com.pelindo.ebtos.model.db.PlanningVessel newValue, com.pelindo.ebtos.model.db.PlanningVessel oldValue);

    int updatePlanningVesselByContNo(PlanningVessel newValue, MasterPort nextPort, PlanningVessel oldValue, List<String> containers);

    public ServiceReceiving findByNoPpkbAndContNoNotCancelLoading(String noPpkb, String contNo);
}

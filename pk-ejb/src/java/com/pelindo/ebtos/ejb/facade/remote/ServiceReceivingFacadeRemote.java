/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.master.MasterPort;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ServiceReceivingFacadeRemote {

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

    List<Object[]> findServiceReceivingsChangeStatus(String no_ppkb);

    List<Object[]> findServiceReceivingsChangeDestination(String no_ppkb);

    Object[] findServiceReceivingByDestination(String no_ppkb, String cont_no) ;

    List<Object[]> findServiceReceivingFrontByPPKBVersi2(String no_ppkb);

    List<Object[]> findServiceReceivingFrontByContainerNo(String contNo);

    List<Object[]> findServiceReceivingFrontByTrackingCont(String noPpkb,String contNo);

    List<Object[]> findServiceReceivingPluggingOnly();

    public java.lang.Integer updateStatusCancelLoadingByNoPpkb(java.lang.String noPpkb, java.lang.String statusCancelLoading);

    public Long countByNoPpkb(java.lang.String noPpkb);

    //public com.pelindo.ebtos.model.db.ServiceReceiving findByNoPpkbAndContNo(java.lang.String noPpkb, java.lang.String contNo);

    public com.pelindo.ebtos.model.db.ServiceReceiving findCancelableContainerByContNoAndNoPpkb(java.lang.String noPpkb, java.lang.String contNo);

    public com.pelindo.ebtos.model.db.ServiceReceiving findMovableContainer(java.lang.String contNo);

    List<Object[]> findYardLoad(String noPpkb,String contNo);

    List<ServiceReceiving> findByNoPpkb(String noPpkb);

    public com.pelindo.ebtos.model.db.ServiceReceiving findByNoPpkbAndContNoNotCancelLoading(java.lang.String noPpkb, java.lang.String contNo);

    public com.pelindo.ebtos.model.db.ServiceReceiving findByNoPpkbAndContNoCancelLoading(java.lang.String noPpkb, java.lang.String contNo);

    List<Object[]> findVesselChangeAbleContainers(String noPpkb);

    public int updatePlanningVesselByContNo(com.pelindo.ebtos.model.db.PlanningVessel newValue, MasterPort nextPort, com.pelindo.ebtos.model.db.PlanningVessel oldValue, java.util.List<java.lang.String> containers);

    public java.util.List<java.lang.String> findLikeNoPPKB(java.lang.String noPpkb);

    public java.util.List<java.lang.String> findLikeContNo(java.lang.String noPpkb, java.lang.String contNo);

    public java.util.List<com.pelindo.ebtos.model.db.ServiceReceiving> findByNoPpkbAndContNoUpdateLiftOn(java.lang.String noPpkb, java.lang.String contNo);
}

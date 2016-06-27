/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.master.MasterYard;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Dyware-Dev01
 */
@Remote
public interface ServiceContDischargeFacadeRemote {

    void create(ServiceContDischarge serviceContDischarge);

    void edit(ServiceContDischarge serviceContDischarge);

    void remove(ServiceContDischarge serviceContDischarge);

    ServiceContDischarge find(Object id);

    List<ServiceContDischarge> findAll();

    List<ServiceContDischarge> findRange(int[] range);

    int count();

    Object[] findByNoPpkb(String no_ppkb, String cont_no, String pos);

    Object[] findByContNo(String cont_no, String pos);

    List<Object[]> findServiceContDischargesByPpkb(String no_ppkb);

    List<Object[]> findServiceContDischarges();

    List<Object[]> findServiceContDischargesStatusSatu(String no_ppkb);

    List<Object[]> findServiceContDischargesConfirm(String no_ppkb);

    List<Object[]> findServiceContDischargesSelect(String no_ppkb);

    List<Object[]> findDeliverableContainersByPpkb(String no_ppkb);

    List<Object[]> findForRecapDischarge(String no_ppkb);

    List<Object[]> findServiceContDischargesByPpkbDeliveryTrue(String no_ppkb);

    int updateServiceContDischargesForDeleteAll(int id);

    List<Object[]> findServiceContDischargesReefer(String no_ppkb);

    Object[] findDeliverableCont(String contNo);

    public List<Object[]> findServiceContDischargesConfirmServiced(String no_ppkb);

    public List<Object[]> findServiceContDischargesSelectServiced(String no_ppkb);

    public int updateServiceContDischargesForDeleteAllPlacement(int id);

    Object[] findContainerReefer(String cont_no);

    List<Object[]> findServiceContDischargesDeliveryService(String no_ppkb);

    List<Object[]> findServiceContDischargesAngsurService(String no_ppkb);

    List<Object[]> findServiceContDischargesStrippingService(String no_ppkb);

    List<Object[]> findServiceContDischargesStuffingService(String no_ppkb);

    List<Object[]> findServiceContDischargesBehandleService(String no_ppkb);

    List<Object[]> findServiceContDischargesMovement();

    List<Object[]> findServiceContDischargesMovementBehandle();

    List<Object[]> findServiceContDischargesMovementInspection();

    Object[] findServiceContDischargesByContAndPpkb(String ppkb, String cont_no);

    List<Object[]> findDischargeMonitoringByPPKB(String no_ppkb, String pos);

    List<Object[]> findDischargeMonitoringByPPKB1(String no_ppkb, String pos, boolean del);

    List<Object[]> findDischargeMonitoringByPPKB2(String no_ppkb, boolean del);

    List<Object[]> findDischargeMonitoringByPPKBFront(String no_ppkb);

    List<Object[]> findUcDischargeMonitoringByPPKB(String no_ppkb);

    Object[] findByContNoMov(String cont_no, String pos, boolean is_delivery, boolean is_behandle, boolean is_cfs, boolean is_inspection);

    List<Object[]> findReeferDischargeService(String no_ppkb);

    List<Object[]> findHandlingDischargeMonitoring(String no_ppkb);

    List<Object[]> findRecapPenumpukan(String no_ppkb);

    Object[] findByPpkbAndContNo(String no_ppkb,String cont_no);

    List<Object[]> findServiceContDischargeByPpkbAndContNoFront(String no_ppkb);

    List<Object[]> findServiceContDischargesDeliveryServiceByBL(String bl, String no_ppkb);

    List<Object[]> findServiceContDischargesStrippingServiceByBL(String bl, String no_ppkb);

    List<Object[]> findServiceContDischargesStuffingServiceByBL(String bl, String no_ppkb);

    List<Object[]> findReeferDischargeServiceByBL(String bl, String no_ppkb);

    List<Object[]> findServiceContDischargeUnplacement(String no_ppkb);

    Object[] findContMovementMobile(String cont_no, String pos, Boolean is_delivery);

    List<Object[]> findContainerDetails(String no_ppkb, String cont_no);

    List<Object[]> findContainerDetailsPPKBnull(String cont_no);

    List<Object[]> findContainerDetailsCONTnull(String no_ppkb);

    List<String> findContainerNumbers(String no_Ppkb, String contNo);

    void saveMasterContainer(ServiceContDischarge serviceContDischarge,String container);

    List<Object[]> findServiceCondischargeToLoads(String no_ppkb);

    List<Object[]> findServiceCondischargeMonitoringContDisch(String no_ppkb);

    public java.util.List<com.pelindo.ebtos.model.db.ServiceContDischarge> findContainersThatHaveNotReachedCY(java.lang.String noPpkb);

    List<Object[]> findSearchingContainerDischargeFront(String contNo);

    List<Object[]> findSearchingContainerTrackingDischargeFront(String noPpkb, String contNo);

    public java.lang.Object[] findConfirmedContainer(java.lang.String noPpkb, java.lang.String contNo);

    public com.pelindo.ebtos.model.db.ServiceContDischarge findByNoPpkbContNoAndPosition(java.lang.String noPpkb, java.lang.String contNo, java.lang.String position);

    public com.pelindo.ebtos.model.db.ServiceContDischarge findByContNoAndPosition(java.lang.String contNo, java.lang.String position);

    public com.pelindo.ebtos.model.db.ServiceContDischarge findByNoPpkbAndContNo(java.lang.String noPpkb, java.lang.String contNo);

    public com.pelindo.ebtos.model.db.ServiceContDischarge findMovableContainer(java.lang.String contNo);

    public com.pelindo.ebtos.model.db.ServiceContDischarge findMovableOffContainer(java.lang.String contNo);

    List<Object[]> findServiceContDischargeByPPKBAndBlock(String ppkb, String block);

    public java.util.List<java.lang.Object[]> findServiceContDischargeChangeStatus(java.lang.String ppkb);

    List<Object[]> exDischargeStocksPerCustomer(String custCode);

    public java.lang.Object[] exDischargeStocksPerCustomer_summary(java.lang.String custCode);

    public List<Object[]> findConvertableToLoadContainers(String noPpkb);

    public com.pelindo.ebtos.model.db.ServiceContDischarge findDischargableContainer(java.lang.String noPpkb, java.lang.String contNo);

    public com.pelindo.ebtos.model.db.ServiceContDischarge findByNoPpkbAndContNoUpdateDelivery(java.lang.String noPpkb, java.lang.String contNo);

    public java.util.List<java.lang.Object[]> findDwellingTime(java.lang.String periode);
}
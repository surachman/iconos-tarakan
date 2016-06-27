/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ServiceContLoad;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ServiceContLoadFacadeRemote {

    void create(ServiceContLoad serviceContLoad);

    void edit(ServiceContLoad serviceContLoad);

    void remove(ServiceContLoad serviceContLoad);

    ServiceContLoad find(Object id);

    List<ServiceContLoad> findAll();

    List<ServiceContLoad> findRange(int[] range);

    int count();

    List<Object[]> findServiceContLoadsByPpkb(String no_ppkb);

    List<Object[]> findServiceContLoadsByPpkbb(String no_ppkb);

    List<Object[]> findServiceContLoads();

    List<Object[]> findServiceContLoadsStatusSatu(String no_ppkb);

    List<Object[]> findServiceContLoadss(String no_ppkb);

    List<Object[]> findServiceContLoadByPpkb2(String no_ppkb);

    List<Object[]> findServiceContLoadByPpkb2t(String no_ppkb);

    List<Object[]> findServiceContLoadConfirm(String no_ppkb);

    List<Object[]> findServiceContLoadConfirm2(String no_ppkb);

    List<Object[]> findServiceContLoadConfirmTranshipment(String no_ppkb);

    List<Object[]> findServiceContLoadSelect(String no_ppkb);

    Object[] findByContNo(String cont_no, String pos);

    Object[] findByContNoPpkb(String no_ppkb, String cont_no, String pos);

    List<Object[]> findLoadMonitoringsByPPKB(String no_ppkb, String pos);

    List<Object[]> findUcLoadMonitoringsByPPKB(String no_ppkb);

    Object[] findServiceContLoadContNo(String no_ppkb, String cont_no);

    List<Object[]> findServiceContLoadByRekap(String no_ppkb);

    List<Object[]> findServiceContLoadContByEndService(String no_ppkb);

    List<Object[]> findServiceContLoadContTranshipmentRecap(String no_ppkb);

    List<String> findServiceContLoadByAutoComplete(String no_ppkb, String cont_no);

    List<Object[]> findServiceContLoadByDownloadExcel(String no_ppkb);

    Object[] findByPpkbAndContNo(String no_ppkb, String cont_no);

    Object[] findShiftableContainer(String noPpkb, String contNo);
    
    List<Object[]> findServiceContLoadsStuffingService(String no_ppkb);

    public java.util.List<com.pelindo.ebtos.model.db.ServiceContLoad> findContainersThatHaveNotBeenLoaded(java.lang.String noPpkb);

    public com.pelindo.ebtos.model.db.ServiceContLoad findByContNoAndPosition(java.lang.String noPpkb, java.lang.String contNo, java.lang.String position);

    public com.pelindo.ebtos.model.db.ServiceContLoad findCancelableContainerByContNoAndNoPpkb(java.lang.String noPpkb, java.lang.String contNo);

    public com.pelindo.ebtos.model.db.ServiceContLoad findByNoPpkbAndContNo(java.lang.String noPpkb, java.lang.String contNo);
    
    public List<ServiceContLoad> findServiceContLoadLoadConfirm(String noPpkb, short bay);

    public com.pelindo.ebtos.model.db.ServiceContLoad findByNoPpkbAndContNoUpdateLiftOn(java.lang.String noPpkb, java.lang.String contNo);
}

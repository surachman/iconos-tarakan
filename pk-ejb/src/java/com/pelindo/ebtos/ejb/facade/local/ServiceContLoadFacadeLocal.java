/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ServiceContLoad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface ServiceContLoadFacadeLocal {

    void create(ServiceContLoad serviceContLoad);

    void edit(ServiceContLoad serviceContLoad);

    void remove(ServiceContLoad serviceContLoad);

    ServiceContLoad find(Object id);

    List<ServiceContLoad> findAll();

    List<ServiceContLoad> findRange(int[] range);

    int count();

    List<Object[]> findServiceContLoadsByPpkb (String no_ppkb);

    List<Object[]> findServiceContLoadsByPpkbb (String no_ppkb);

    List<Object[]> findServiceContLoads();

    List<Object[]>findServiceContLoadsStatusSatu(String no_ppkb);

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

    Object[] findServiceContLoadContNo(String no_ppkb, String cont_no);

    List<Object[]> findServiceContLoadByRekap(String no_ppkb);

    List<Object[]> findServiceContLoadContByEndService(String no_ppkb);

    List<Object[]> findServiceContLoadContTranshipmentRecap(String no_ppkb);

   List<String> findServiceContLoadByAutoComplete(String no_ppkb,String cont_no);
   
   List<Object[]> findServiceContLoadsStuffingService(String no_ppkb);

    public java.util.List<com.pelindo.ebtos.model.db.ServiceContLoad> findContainersThatHaveBeenLoaded(java.lang.String noPpkb);

    public int removeMasterActivityThatHaveBeenLoaded(java.lang.String noPpkb);

    public java.util.List<java.lang.Object[]> findContainersThatHaveBeenLoadedAsObjectArray(java.lang.String noPpkb);
    
    public List<Object[]> findServiceContLoadByBayLocationAsObjectArray(String ppkb, int bay);

    public java.util.List<java.lang.Object[]> findGrossCapacityByNoPpkbAndBay(java.lang.String noPpkb, java.lang.Short bay, java.lang.Integer minRow, java.lang.Integer maxRow, java.lang.Integer minTier, java.lang.Integer maxTier);

    public com.pelindo.ebtos.model.db.ServiceContLoad findByNoPpkbContNoAndStatusCancelLoading(java.lang.String noPpkb, java.lang.String contNo, String statusCancelLoading);
}
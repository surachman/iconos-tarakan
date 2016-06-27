/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ServiceGateReceiving;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ServiceGateReceivingFacadeRemote {

    void create(ServiceGateReceiving serviceGateReceiving);

    void edit(ServiceGateReceiving serviceGateReceiving);

    void remove(ServiceGateReceiving serviceGateReceiving);

    ServiceGateReceiving find(Object id);

    List<ServiceGateReceiving> findAll();

    List<ServiceGateReceiving> findRange(int[] range);

    int count();
    
    List<Object[]> findServiceGateReceivingDateOutNull();

    Object[] findGateOutPassableByJobSlip(String job_slip);

    Object[] findServiceGateReceivingDateOutByJobSlip(String job_slip);

    List<String> findGateOutPassableJobslips(String jobslip);

    List<Object[]> findForMonitoringTruck(String no_ppkb);

    Object findByJobslipPpkb(String jobslip, String no_ppkb);

    Object findByContPpkb(String cont_no, String no_ppkb);

    public java.lang.Object[] findReceivingCapacityStatusByNoPpkb(java.lang.String noPpkb);

    public ServiceGateReceiving findByNoPpkbAndContNo(java.lang.String noPpkb, java.lang.String contNo);

    public int updateNoPpkbByJobSlips(java.lang.String newValue, java.lang.String oldValue, java.util.List<java.lang.String> jobSlips);

    List<Object[]> gateMonitoringPerCustomer(Date from, Date to, String custCode);

    java.util.List<java.lang.Object[]> findGateActivityMonitoring(java.util.Date startDate, java.util.Date endDate);

    java.util.List<java.lang.Object[]> findUnrealizedActivityMonitoring();
}

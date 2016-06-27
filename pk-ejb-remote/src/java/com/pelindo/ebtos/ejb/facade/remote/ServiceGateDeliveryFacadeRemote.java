/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ServiceGateDelivery;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ServiceGateDeliveryFacadeRemote {

    void create(ServiceGateDelivery serviceGateDelivery);

    void edit(ServiceGateDelivery serviceGateDelivery);

    void remove(ServiceGateDelivery serviceGateDelivery);

    ServiceGateDelivery find(Object id);

    ServiceGateDelivery findByContPpkb(String cont_no, String no_ppkb);

    ServiceGateDelivery findNotDeliveredYetByJobSlip(String jobSlip);

    List<ServiceGateDelivery> findAll();

    List<ServiceGateDelivery> findRange(int[] range);

    int count();

    List<Object[]> findServiceGateDeliveryDateOutNull();

    Object[] findGateOutPassableByJobSlip(String job_slip);

    Object[] findServiceGateDeliveryDateOutByJobSlip(String job_slip);

    List<String> findGateOutPassableJobslips(String jobslip);

    List<Object[]> findForMonitoringTruck(String no_ppkb);

    Object findByContAndPpkb(String cont_no, String no_ppkb);

    String findByContAndNoreg(String jobSlip);

    List<Object[]> gateMonitoringPerCustomer(Date from, Date to, String custCode);

    java.util.List<java.lang.Object[]> findGateActivityMonitoring(java.util.Date startDate, java.util.Date endDate);

    java.util.List<java.lang.Object[]> findUnrealizedActivityMonitoring();

    public List<String> findGateInPassableJobSlips(String jobSlip);
}

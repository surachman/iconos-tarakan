/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ServiceGateDelivery;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface ServiceGateDeliveryFacadeLocal {

    void create(ServiceGateDelivery serviceGateDelivery);

    void edit(ServiceGateDelivery serviceGateDelivery);

    void remove(ServiceGateDelivery serviceGateDelivery);

    ServiceGateDelivery find(Object id);

    ServiceGateDelivery findByContPpkb(String cont_no, String no_ppkb);

    List<ServiceGateDelivery> findAll();

    List<ServiceGateDelivery> findRange(int[] range);

    int count();

    List<Object[]> findServiceGateDeliveryDateOutNull();

    Object[] findGateOutPassableByJobSlip(String job_slip);

    Object[] findServiceGateDeliveryDateOutByJobSlip(String job_slip);

    List<String> findGateOutPassableJobslips(String jobslip);
}

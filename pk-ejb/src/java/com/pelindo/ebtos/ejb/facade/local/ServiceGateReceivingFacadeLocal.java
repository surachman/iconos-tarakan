/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ServiceGateReceiving;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface ServiceGateReceivingFacadeLocal {

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

    public int updateNoPpkb(java.lang.String newValue, java.lang.String oldValue);

    ServiceGateReceiving findByNoPpkbAndContNo(java.lang.String noPpkb, java.lang.String contNo);

    int updateNoPpkbByJobSlips(String newValue, String oldValue, List<String> jobSlips);

}

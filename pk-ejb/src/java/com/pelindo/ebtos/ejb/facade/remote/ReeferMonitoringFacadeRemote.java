/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ReeferMonitoring;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ReeferMonitoringFacadeRemote {

    void create(ReeferMonitoring reeferMonitoring);

    void edit(ReeferMonitoring reeferMonitoring);

    void remove(ReeferMonitoring reeferMonitoring);

    ReeferMonitoring find(Object id);

    List<ReeferMonitoring> findAll();

    List<ReeferMonitoring> findRange(int[] range);

    List<Object[]> findByIdReefer (Integer id_reefer);

    int count();

    List<Integer> findAllByIdReefer (Integer id_reefer);

    List<ReeferMonitoring> findByContNo (String cont_no);

    public java.math.BigDecimal findLastTemperature(java.lang.String contNo);

}

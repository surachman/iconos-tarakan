/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.VwReceivingMonitoring;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author arie
 */
@Remote
public interface VwReceivingMonitoringFacadeRemote {

    VwReceivingMonitoring find(Object id);

    List<VwReceivingMonitoring> findAll();

    List<VwReceivingMonitoring> findRange(int[] range);

    int count();

    List<VwReceivingMonitoring> findByNoPpkb(String noPpkb);

    List<VwReceivingMonitoring> findByInternalStatus(String internalStatus);

    List<VwReceivingMonitoring> findByNoPpkbInternalStatus(String noPpkb, String internalStatus);

}

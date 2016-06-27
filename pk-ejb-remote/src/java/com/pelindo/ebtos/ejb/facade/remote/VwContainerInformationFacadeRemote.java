/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.VwContainerInformation;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author arie
 */
@Remote
public interface VwContainerInformationFacadeRemote {

    VwContainerInformation find(Object id);

    List<VwContainerInformation> findAll();

    List<VwContainerInformation> findRange(int[] range);

    int count();

    List<String> findContList (String query);

    Object[] findContInfo (String contNo);

    List<Object[]> findHistoryContList (String contNo);

    List<Object[]> findVesselDetail (String contNo, String ppkbNo);

    List<Object[]> findContainerDetail (String contNo, String ppkbNo);

    List<Object[]> findHandlingDischarge (String contNo, String ppkbNo);

    List<Object[]> findHandlingLoad (String contNo, String ppkbNo);

    List<Object[]> findGateDischarge (String contNo, String ppkbNo);

    List<Object[]> findGateLoad (String contNo, String ppkbNo);


}

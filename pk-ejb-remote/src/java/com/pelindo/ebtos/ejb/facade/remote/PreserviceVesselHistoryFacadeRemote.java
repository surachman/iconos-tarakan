/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PreserviceVesselHistory;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author x42jr
 */
@Remote
public interface PreserviceVesselHistoryFacadeRemote {

    void create(PreserviceVesselHistory preserviceVesselHistory);

    void edit(PreserviceVesselHistory preserviceVesselHistory);

    void remove(PreserviceVesselHistory preserviceVesselHistory);

    PreserviceVesselHistory find(Object id);

    List<PreserviceVesselHistory> findAll();

    List<PreserviceVesselHistory> findRange(int[] range);

    int count();

    public java.util.List<java.lang.Object[]> getHistoriesByField(java.lang.String noPpkb, java.lang.String field);

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.PreserviceVesselHistoryFacadeRemote;
import com.pelindo.ebtos.model.db.PreserviceVesselHistory;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author x42jr
 */
@Stateless
public class PreserviceVesselHistoryFacade extends AbstractFacade<PreserviceVesselHistory> implements PreserviceVesselHistoryFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PreserviceVesselHistoryFacade() {
        super(PreserviceVesselHistory.class);
    }

    public List<Object[]> getHistoriesByField(String noPpkb, String field) {
        String query = "SELECT pvh.[field], pvh.modified_by, MIN(pvh.modified_date) "
                + "FROM preservice_vessel_history pvh "
                + "WHERE pvh.booking_code = ? "
                + "GROUP BY pvh.[field], pvh.modified_by "
                + "ORDER BY MIN(pvh.modified_date) DESC";
        query = query.replace("[field]", field);
        return getEntityManager().createNativeQuery(query)
                .setParameter(1, noPpkb)
                .getResultList();
    }

}

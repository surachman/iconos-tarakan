/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.BaplieDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.CancelLoadingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceContDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import com.pelindo.ebtos.exception.DeleteNotDeletableContainerException;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class MasterContainerFacade implements MasterContainerFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @EJB
    private PlanningContLoadFacadeLocal planningContLoadFacadeLocal;
    @EJB
    private ServiceContDischargeFacadeLocal serviceContDischargeFacadeLocal;
    @EJB
    private CancelLoadingServiceFacadeLocal cancelLoadingServiceFacadeLocal;
    @EJB
    private BaplieDischargeFacadeLocal baplieDischargeFacadeLocal;
    @EJB
    private PlanningContDischargeFacadeLocal planningContDischargeFacadeLocal;
    @EJB MasterYardCoordinatFacadeRemote masterYardCoordinatFacadeRemote;

    protected EntityManager getEntityManager() {
        return em;
    }

    public Object findMovableContainer(String contNo) {
        if (contNo == null) {
            return null;
        }

        ServiceContDischarge scd = serviceContDischargeFacadeLocal.findMovableContainer(contNo);

        if (scd == null) {
            PlanningContLoad pcl = planningContLoadFacadeLocal.findMovableContainer(contNo);

            if (pcl == null) {
                CancelLoadingService cancelLoadingService = cancelLoadingServiceFacadeLocal.findMovableContainer(contNo);;

                return cancelLoadingService;
            }

            return pcl;
        }

        return scd;
    }

    public void handleDeleteDischargeContainer(String contNo, String noPpkb) throws DeleteNotDeletableContainerException {
        ServiceContDischarge serviceContDischarge = serviceContDischargeFacadeLocal.findByNoPpkbAndContNo(noPpkb, contNo);

        if (serviceContDischarge != null) {
            if (serviceContDischarge.getPosition().equals("01")) {
                baplieDischargeFacadeLocal.deleteByContNoAndNoPpkb(contNo, noPpkb);
                planningContDischargeFacadeLocal.deleteByContNoAndNoPpkb(contNo, noPpkb);
                serviceContDischargeFacadeLocal.remove(serviceContDischarge);
                masterYardCoordinatFacadeRemote.deleteByContNoAndPpkb(contNo, noPpkb);
            } else {
                throw new DeleteNotDeletableContainerException();
            }
        }
    }

    public Object[] findContainerOnYard(String contNo) {
        try {
            String query = "SELECT cont.cont_no, cont.no_ppkb, cont.vessel_name, cont.cont_size, cont.type_in_general, cont.cont_status, cont.placement_date, cont.block, cont.y_slot, cont.y_row, cont.y_tier "
                        + "FROM "
                                + "(SELECT pcl.cont_no, pcl.no_ppkb, mv.name vessel_name, pcl.cont_size, mct.type_in_general, pcl.cont_status, pcl.created_date placement_date, pcl.block, pcl.y_slot, pcl.y_row, pcl.y_tier, 0 idx "
                                + "FROM planning_cont_load pcl "
                                        + "LEFT JOIN cancel_loading_service cls ON (pcl.no_ppkb=cls.no_ppkb AND pcl.cont_no=cls.cont_no) "
                                        + "JOIN m_container_type mct ON (pcl.cont_type=mct.cont_type) "
                                        + "JOIN planning_vessel pv ON (pcl.no_ppkb=pv.no_ppkb) "
                                                + "JOIN m_vessel mv ON (pv.vessel_code=mv.vessel_code) "
                                + "WHERE (pcl.status_cancel_loading <> TRUE AND pcl.position = '03' AND cls.cont_no IS NULL) OR (pcl.status_cancel_loading = TRUE AND cls.posisi = '02' AND cls.is_delivery <> TRUE) "
                                + "UNION ALL "
                                + "SELECT scd.cont_no, scd.no_ppkb, mv.name vessel_name, scd.cont_size, mct.type_in_general, scd.cont_status, scd.start_placement_date, scd.block, scd.y_slot, scd.y_row, scd.y_tier, 1 idx "
                                + "FROM service_cont_discharge scd "
                                        + "JOIN m_container_type mct ON (scd.cont_type=mct.cont_type) "
                                        + "JOIN planning_vessel pv ON (scd.no_ppkb=pv.no_ppkb) "
                                                + "JOIN m_vessel mv ON (pv.vessel_code=mv.vessel_code) "
                                + "WHERE scd.position = '03' AND scd.is_delivery <> TRUE) cont "
                        + "WHERE cont.cont_no = ? "
                        + "ORDER BY cont.cont_no, cont.idx "
                        + "LIMIT 1;";
            return (Object[]) getEntityManager().createNativeQuery(query)
                    .setParameter(1, contNo)
                    .getSingleResult();
        } catch (NoResultException re) {
            return null;
        }
    }
}

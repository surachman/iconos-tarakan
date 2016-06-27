/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.pelindo.ebtos.ejb.remote.KapalBayanganOperationRemote;
import com.pelindo.ebtos.ejb.facade.local.EquipmentContainerMovementFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.EquipmentFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterYardCoordinatFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PerhitunganLiftServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PerhitunganPenumpukanFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ReceivingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceGateReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganLiftServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PreserviceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.master.MasterPort;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author R. Seno Anggoro A
 */
@Stateless
public class KapalBayanganOperation implements KapalBayanganOperationRemote {
    @EJB
    private PlanningContLoadFacadeLocal planningContLoadFacadeLocal;
    @EJB
    private ReceivingServiceFacadeLocal receivingServiceFacadeLocal;
    @EJB
    private ServiceGateReceivingFacadeLocal serviceGateReceivingFacadeLocal;
    @EJB
    private PlanningContReceivingFacadeLocal planningContReceivingFacadeLocal;
    @EJB
    private EquipmentContainerMovementFacadeLocal equipmentContainerMovementFacadeLocal;
    @EJB
    private EquipmentFacadeLocal equipmentFacadeLocal;
    @EJB
    private ServiceReceivingFacadeLocal serviceReceivingFacadeLocal;
    @EJB
    private MasterYardCoordinatFacadeLocal masterYardCoordinatFacadeLocal;
    @EJB PreserviceVesselFacadeRemote preserviceVesselFacadeRemote;
    @EJB PerhitunganLiftServiceFacadeLocal perhitunganLiftServiceFacadeLocal;
    @EJB PerhitunganLiftServiceFacadeRemote perhitunganLiftServiceFacadeRemote;
    @EJB PerhitunganPenumpukanFacadeLocal perhitunganPenumpukanFacadeLocal;
    
    public void handleChangePpkb(List<String> containers, List<String> jobSlips, PlanningVessel newVessel, PlanningVessel oldVessel) {
//        preserviceVesselFacadeRemote.findNextPortByBookingCode(newVessel.get);
        MasterPort port = preserviceVesselFacadeRemote.findNextPortByBookingCode(newVessel.getPreserviceVessel().getBookingCode());
        planningContReceivingFacadeLocal.updatePlanningVesselByContNo(newVessel, port, oldVessel, containers);
        planningContLoadFacadeLocal.updatePlanningVesselByContNo(newVessel, port, oldVessel, containers);
        receivingServiceFacadeLocal.updatePlanningVesselByJobSlips(newVessel, port, oldVessel, jobSlips);
        serviceReceivingFacadeLocal.updatePlanningVesselByContNo(newVessel, port, oldVessel, containers);
        serviceGateReceivingFacadeLocal.updateNoPpkbByJobSlips(newVessel.getNoPpkb(), oldVessel.getNoPpkb(), jobSlips);//
        equipmentContainerMovementFacadeLocal.updatePlanningVesselByContNo(newVessel, oldVessel, containers);
        equipmentFacadeLocal.updatePlanningVesselReceivingByContNo(newVessel.getNoPpkb(), oldVessel.getNoPpkb(), containers);
        masterYardCoordinatFacadeLocal.updatePlanningVesselByContNo(newVessel.getNoPpkb(), port, oldVessel.getNoPpkb(), containers);
//        perhitunganLiftServiceFacadeRemote.
        //        perhitunganLiftServiceFacadeLocal.findByContNoAndNoReg(oldVessel., null)
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.CancelLoadingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.EquipmentFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterYardCoordinatFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PerhitunganLiftServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PerhitunganPenumpukanFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PluggingReeferServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceContLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceGateReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceReeferFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceShiftingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ReceivingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ReeferLoadServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ReeferMonitoringFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContReceivingFacadeRemote;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PerhitunganLiftService;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukan;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import com.pelindo.ebtos.model.db.ServiceGateReceiving;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PluggingReeferService;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.ReeferLoadService;
import com.pelindo.ebtos.model.db.ReeferMonitoring;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.ServiceReefer;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author dycoder
 */
@Stateless
public class PlanningContReceivingFacade extends AbstractFacade<PlanningContReceiving> implements PlanningContReceivingFacadeRemote, PlanningContReceivingFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    @EJB
    EquipmentFacadeLocal equipmentFacade;
    @EJB
    PlanningContLoadFacadeLocal planningContLoadFacade;
    @EJB
    CancelLoadingServiceFacadeLocal cancelLoadingServiceFacade;
    @EJB
    ReceivingServiceFacadeLocal ReceivingServiceFacadeLocal;
    @EJB
    ReeferLoadServiceFacadeLocal reeferLoadServiceFacade;
    @EJB
    PluggingReeferServiceFacadeLocal pluggingReeferServiceFacade;
    @EJB
    ServiceContLoadFacadeLocal serviceContLoadFacade;
    @EJB
    ServiceGateReceivingFacadeLocal serviceGateReceivingFacade;
    @EJB
    ServiceReceivingFacadeLocal serviceReceivingFacade;
    @EJB
    ServiceReeferFacadeLocal serviceReeferFacade;
    @EJB
    ServiceShiftingFacadeLocal serviceShiftingFacade;
    @EJB
    ReeferMonitoringFacadeLocal reeferMonitoringFacade;
    @EJB
    MasterYardCoordinatFacadeLocal masterYardCoordinatFacade;
    @EJB PerhitunganLiftServiceFacadeLocal perhitunganLiftServiceFacadeLocal;
    @EJB PerhitunganPenumpukanFacadeLocal perhitunganPenumpukanFacadeLocal;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PlanningContReceivingFacade() {
        super(PlanningContReceiving.class);
    }

    @Override
    public List<Object[]> findPlanningContReceivingByNoPpkb(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContReceiving.Native.findPlanningContReceivingByNoPpkb").setParameter(1, no_ppkb).getResultList()
        );

    }

    public PlanningContReceiving findByNoPpkbAndContNo(String noPpkb, String contNo) {
        try {
            return (PlanningContReceiving) getEntityManager().createNamedQuery("PlanningContReceiving.findByNoPpkbAndContNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException re) {
            return null;
        }
    }

    public List<Object[]> findPlanningContReceivingsByNoPpkb(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContReceiving.Native.findPlanningContReceivingsByNoPpkb").setParameter(1, no_ppkb).getResultList()
        );
    }

    public int deleteByNoPpkbAndContNo(String noPpkb, String contNo) {
        return getEntityManager().createNamedQuery("PlanningContReceiving.deleteByNoPpkbAndContNo")
                .setParameter("noPpkb", noPpkb)
                .setParameter("contNo", contNo)
                .executeUpdate();
    }

    public List<Object[]> findReceivingConfirm(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContReceiving.Native.findReceivingConfirm").setParameter(1, no_ppkb).getResultList()
        );
    }
    
    //penambahan pencarian list no container by ade chelsea tanggal 24 maret 2014
    public List<Object[]> findReceivingConfirm2() {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContReceiving.Native.findReceivingConfirm2").getResultList()
        );
    }

    public List<Object[]> findPlanningContReceivings() {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContReceiving.Native.findPlanningContReceivings").getResultList()
        );
    }

    public Object[] findByContDelete(String no_ppkb, String cont_no) {
        Object[] temp = new Object[3];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PlanningContReceiving.Native.findByContDelete").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findByContNo(String no_ppkb, String cont_no, String pos) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PlanningContReceiving.Native.findByContNo").setParameter(1, no_ppkb).setParameter(2, cont_no).setParameter(3, pos).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findPlanningContReceivingGateReceiving(String noPpkb, String cont_no) {
        Object[] temp = new Object[4];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PlanningContReceiving.Native.findPlanningContReceivingGateReceiving").setParameter(1, noPpkb).setParameter(2, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findPlanningContReceivingIsGenerateTrue(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContReceiving.Native.findPlanningContReceivingIsGenerateTrue").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findPlanningContReceivingIsGenerateTrueAndBlNo(String no_ppkb, String bl_no) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("PlanningContReceiving.Native.findPlanningContReceivingIsGenerateTrueAndBlNo").setParameter(1, no_ppkb).setParameter(2, bl_no).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findPlanningContMonitoringLoad(String no_ppkb, String pos) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("PlanningContReceiving.Native.findPlanningContMonitoringLoad").setParameter(1, no_ppkb).setParameter(2, pos).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findPlanningContReceivingBaplieLoad(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContReceiving.Native.findPlanningContReceivingBaplieLoad").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Integer> findBaplieByConstraint(int cont_type, String cont_status, String gross_class, String commodity_code, String dg, String over_size, String isExport, String disch_port, String no_ppkb) {
        return decimalsToInts(
                getEntityManager().createNamedQuery("PlanningContReceiving.Native.findBaplieByConstraint")
                .setParameter(1, cont_type)
                .setParameter(2, cont_status)
                .setParameter(3, gross_class) //.setParameter(4, commodity_code)
                .setParameter(4, dg)
                .setParameter(5, over_size)
                .setParameter(6, disch_port)
                .setParameter(7, isExport)
                .setParameter(8, no_ppkb).getResultList()
        );
    }

    public List<Integer> findIdByIdConstraint(int id) {
        return decimalsToInts(
                getEntityManager().createNamedQuery("PlanningContReceiving.Native.findIdByIdConstraint").setParameter(1, id).getResultList()
        );
    }

    public List<Object[]> createGenerateByPPKB(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContReceiving.Native.createGenerateByPPKB").setParameter(1, no_ppkb).getResultList()
        );
    }

    public int deleteByIdConstraint(int id) {
        return getEntityManager().createNamedQuery("PlanningContReceiving.Native.deleteByIdConstraint").setParameter(1, id).executeUpdate();
    }

    public Integer findPlanningCont(String cont_no, String no_ppkb, String is_generate) {
        Integer idPlan = null;
        try {
            idPlan = decimalToInt(
                    (BigDecimal) getEntityManager().createNamedQuery("PlanningContReceiving.Native.findPlanningCont").setParameter(1, cont_no).setParameter(2, no_ppkb).setParameter(3, is_generate).getSingleResult()
            );
        } catch (NoResultException ex) {
            idPlan = null;
        }
        return idPlan;
    }

    public Object[] findPlanningContReceivingByCekExist(String cont_no, String no_ppkb) {
        Object[] tempp = new Object[15];
        try {
            tempp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PlanningContReceiving.Native.findPlanningContReceivingByCekExist").setParameter(1, cont_no).setParameter(2, no_ppkb).getSingleResult()
            );
        } catch (NoResultException ex) {
            tempp = null;
        }

        return tempp;
    }

    public Object[] findByContNoMobile(String cont_no, String pos) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PlanningContReceiving.Native.findByContNoMobile").setParameter(1, cont_no).setParameter(2, pos).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findPlanningContReceivingCreateGenerate(int id) {
        Object[] temp = new Object[15];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PlanningContReceiving.Native.findPlanningContReceivingCreateGenerate").setParameter(1, id).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findContainerDetails(String no_ppkb, String cont_no) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContReceiving.Native.findContainerDetails").setParameter(1, no_ppkb).setParameter(2, cont_no).getResultList()
        );
    }

    public List<Object[]> findContainerDetailsPPKBnull(String cont_no) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContReceiving.Native.findContainerDetailsPPKBnull").setParameter(1, cont_no).getResultList()
        );
    }

    public List<Object[]> findContainerDetailsCONTnull(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContReceiving.Native.findContainerDetailsCONTnull").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<String> findContainerNumbers(String no_Ppkb, String contNo) {
        String no_ppkb = "%" + no_Ppkb.toUpperCase() + "%";
        String cont_no = "%" + contNo.toUpperCase() + "%";
        return getEntityManager().createNamedQuery("PlanningContReceiving.Native.findContainerNumbers").setParameter(1, no_ppkb).setParameter(2, cont_no).getResultList();
    }

    public List<Integer> findContIds(String no_ppkb, String cont_no, String tablename) {
        return decimalsToInts(
                getEntityManager().createNamedQuery("PlanningContReceiving.Native.findContIds").setParameter(1, no_ppkb).setParameter(2, cont_no).setParameter(3, tablename).getResultList()
        );
    }

    public List<PlanningContReceiving> findByNoPpkb(String noPpkb) {
        return getEntityManager().createNamedQuery("PlanningContReceiving.findByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .getResultList();
    }

    public List<String> findPKs(String pk_field, String no_ppkb, String cont_no, String tablename) {
        List<String> temp = new ArrayList<String>();
        try {
            temp = getEntityManager().createNamedQuery("PlanningContReceiving.Native.findPKs").setParameter(1, pk_field).setParameter(2, no_ppkb).setParameter(3, cont_no).setParameter(4, tablename).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;

        //return getEntityManager().createNamedQuery("PlanningContReceiving.Native.findPKs").setParameter(1, pk_field).setParameter(2, no_ppkb).setParameter(3, cont_no).setParameter(4, tablename).getResultList();
    }

    public void saveMasterContainer(PlanningContReceiving planningContReceiving, String cont) {
        //equipment
        List<Integer> IDs = findContIds(planningContReceiving.getPlanningVessel().getNoPpkb(), cont, "equipment");
        List<String> PKs = null;
        for (int i = 0; i < IDs.size(); i++) {
            Equipment equipment = equipmentFacade.find(IDs.get(i));
            equipment.setContNo(planningContReceiving.getContNo());
            equipment.setMlo(planningContReceiving.getMlo());
            equipmentFacade.edit(equipment);
        }
        //planning_cont_load
        IDs = findContIds(planningContReceiving.getPlanningVessel().getNoPpkb(), cont, "planning_cont_load");
        for (int i = 0; i < IDs.size(); i++) {
            PlanningContLoad planningContLoad = planningContLoadFacade.find(IDs.get(i));
            planningContLoad.setContNo(planningContReceiving.getContNo());
            planningContLoad.setMlo(planningContReceiving.getMlo());
            planningContLoad.setContSize(planningContReceiving.getContSize());
            planningContLoad.setMasterContainerType(planningContReceiving.getMasterContainerType());
            planningContLoad.setContStatus(planningContReceiving.getContStatus());
            planningContLoad.setTwentyOneFeet(planningContReceiving.getTwentyOneFeet());
            planningContLoad.setMasterCommodity(planningContReceiving.getMasterCommodity());
            planningContLoad.setIsExportImport(planningContReceiving.getIsExport());
            planningContLoad.setContGross(planningContReceiving.getContGross());
            planningContLoad.setSealNo(planningContReceiving.getSealNo());
            planningContLoad.setOverSize(planningContReceiving.getOverSize());
            planningContLoad.setDg(planningContReceiving.getDg());
            planningContLoad.setDgLabel(planningContReceiving.getDgLabel());
            planningContLoadFacade.edit(planningContLoad);
        }
        //planning_cont_receiving
        edit(planningContReceiving);
        //cancel_loading_service
        PKs = findPKs("job_slip", planningContReceiving.getPlanningVessel().getNoPpkb(), cont, "cancel_loading_service");
        for (int i = 0; i < PKs.size(); i++) {
            CancelLoadingService cancelLoadingService = cancelLoadingServiceFacade.find(PKs.get(i).replace("(", "").replace(")", "").trim());
            //CancelLoadingService cancelLoadingService = cancelLoadingServiceFacade.find(PKs.get(i));
            cancelLoadingService.setContNo(planningContReceiving.getContNo());
            cancelLoadingService.setMlo(planningContReceiving.getMlo());
            cancelLoadingService.setContSize(planningContReceiving.getContSize());
            cancelLoadingService.setMasterContainerType(planningContReceiving.getMasterContainerType());
            cancelLoadingService.setContStatus(planningContReceiving.getContStatus());
            cancelLoadingService.setMasterCommodity(planningContReceiving.getMasterCommodity());
            cancelLoadingService.setContGross(planningContReceiving.getContGross());
            cancelLoadingService.setIsExport(planningContReceiving.getIsExport());
            cancelLoadingService.setSealNo(planningContReceiving.getSealNo());
            cancelLoadingService.setOverSize(planningContReceiving.getOverSize());
            cancelLoadingService.setDg(planningContReceiving.getDg());
            cancelLoadingService.setDgLabel(planningContReceiving.getDgLabel());
            cancelLoadingServiceFacade.edit(cancelLoadingService);
        }

        //receiving service
        PKs = findPKs("job_slip", planningContReceiving.getPlanningVessel().getNoPpkb(), cont, "receiving_service");
        for (int i = 0; i < PKs.size(); i++) {
            ReceivingService receivingService = ReceivingServiceFacadeLocal.find(PKs.get(i).replace("(", "").replace(")", "").trim());
            //CancelLoadingService cancelLoadingService = cancelLoadingServiceFacade.find(PKs.get(i));
            receivingService.setContNo(planningContReceiving.getContNo());
            receivingService.setMlo(planningContReceiving.getMlo());
            receivingService.setContSize(planningContReceiving.getContSize());
            receivingService.setMasterContainerType(planningContReceiving.getMasterContainerType());
            receivingService.setContStatus(planningContReceiving.getContStatus());
            receivingService.setTwentyOneFeet(planningContReceiving.getTwentyOneFeet());
            receivingService.setMasterCommodity(planningContReceiving.getMasterCommodity());
            receivingService.setContGross(planningContReceiving.getContGross());
            receivingService.setIsExport(planningContReceiving.getIsExport());
            receivingService.setSealNo(planningContReceiving.getSealNo());
            receivingService.setOverSize(planningContReceiving.getOverSize());
            receivingService.setDg(planningContReceiving.getDg());
            receivingService.setDgLabel(planningContReceiving.getDgLabel());
            ReceivingServiceFacadeLocal.edit(receivingService);
        }
        
        //reefer_load_service
        PKs = findPKs("job_slip", planningContReceiving.getPlanningVessel().getNoPpkb(), cont, "reefer_load_service");
        for (int i = 0; i < PKs.size(); i++) {
            ReeferLoadService reeferLoadService = reeferLoadServiceFacade.find(PKs.get(i).replace("(", "").replace(")", "").trim());
            reeferLoadService.setContNo(planningContReceiving.getContNo());
            reeferLoadService.setMlo(planningContReceiving.getMlo());
            reeferLoadService.setContSize(planningContReceiving.getContSize());
            reeferLoadService.setMasterContainerType(planningContReceiving.getMasterContainerType());
            reeferLoadService.setContStatus(planningContReceiving.getContStatus());
            reeferLoadService.setMasterCommodity(planningContReceiving.getMasterCommodity());
            reeferLoadService.setContGross(planningContReceiving.getContGross());
            reeferLoadService.setOverSize(planningContReceiving.getOverSize());
            reeferLoadService.setDg(planningContReceiving.getDg());
            reeferLoadService.setDgLabel(planningContReceiving.getDgLabel());
            reeferLoadServiceFacade.edit(reeferLoadService);
        }
        //plugging_reefer_service
        PKs = pluggingReeferServiceFacade.findJobSlipByPPKBnCONT(planningContReceiving.getPlanningVessel().getNoPpkb(), cont);
        for (int i = 0; i < PKs.size(); i++) {
            PluggingReeferService pluggingReeferService = pluggingReeferServiceFacade.find(PKs.get(i));
            pluggingReeferService.setContNo(planningContReceiving.getContNo());
            pluggingReeferService.setMlo(planningContReceiving.getMlo());
            pluggingReeferService.setContSize(planningContReceiving.getContSize());
            pluggingReeferService.setMasterContainerType(planningContReceiving.getMasterContainerType());
            pluggingReeferService.setContStatus(planningContReceiving.getContStatus());
            pluggingReeferService.setMasterCommodity(planningContReceiving.getMasterCommodity());
            pluggingReeferService.setContGross(planningContReceiving.getContGross());
            pluggingReeferService.setOverSize(planningContReceiving.getOverSize());
            pluggingReeferService.setDg(planningContReceiving.getDg());
            pluggingReeferService.setDgLabel(planningContReceiving.getDgLabel());
            pluggingReeferServiceFacade.edit(pluggingReeferService);
        }
        //service_cont_load
        IDs = findContIds(planningContReceiving.getPlanningVessel().getNoPpkb(), cont, "service_cont_load");
        for (int i = 0; i < IDs.size(); i++) {
            ServiceContLoad serviceContLoad = serviceContLoadFacade.find(IDs.get(i));
            serviceContLoad.setContNo(planningContReceiving.getContNo());
            serviceContLoad.setMlo(planningContReceiving.getMlo());
            serviceContLoad.setContSize(planningContReceiving.getContSize());
            serviceContLoad.setMasterContainerType(planningContReceiving.getMasterContainerType());
            serviceContLoad.setContStatus(planningContReceiving.getContStatus());
            serviceContLoad.setMasterCommodity(planningContReceiving.getMasterCommodity());
            serviceContLoad.setTwentyOneFeet(planningContReceiving.getTwentyOneFeet());
            serviceContLoad.setContGross(planningContReceiving.getContGross());
            serviceContLoad.setIsExportImport(planningContReceiving.getIsExport());
            serviceContLoad.setSealNo(planningContReceiving.getSealNo());
            serviceContLoad.setOverSize(planningContReceiving.getOverSize());
            serviceContLoad.setDangerous(planningContReceiving.getDg());
            serviceContLoad.setDgLabel(planningContReceiving.getDgLabel());
            serviceContLoadFacade.edit(serviceContLoad);
        }
        //service_gate_receiving
        IDs = findContIds(planningContReceiving.getPlanningVessel().getNoPpkb(), cont, "service_gate_receiving");
        for (int i = 0; i < IDs.size(); i++) {
            ServiceGateReceiving serviceGateReceiving = serviceGateReceivingFacade.find(IDs.get(i));
            serviceGateReceiving.setContNo(planningContReceiving.getContNo());
            serviceGateReceiving.setMlo(planningContReceiving.getMlo());
            serviceGateReceiving.setContType(planningContReceiving.getMasterContainerType().getContType());
            serviceGateReceiving.setContGross(planningContReceiving.getContGross());
            serviceGateReceiving.setSealNo(planningContReceiving.getSealNo());
            serviceGateReceivingFacade.edit(serviceGateReceiving);
        }
        //service_receiving
        IDs = findContIds(planningContReceiving.getPlanningVessel().getNoPpkb(), cont, "service_receiving");
        for (int i = 0; i < IDs.size(); i++) {
            ServiceReceiving serviceReceiving = serviceReceivingFacade.find(IDs.get(i));
            serviceReceiving.setContNo(planningContReceiving.getContNo());
            serviceReceiving.setMlo(planningContReceiving.getMlo());
            serviceReceiving.setContSize(planningContReceiving.getContSize());
            serviceReceiving.setMasterContainerType(planningContReceiving.getMasterContainerType());
            serviceReceiving.setContStatus(planningContReceiving.getContStatus());
            serviceReceiving.setMasterCommodity(planningContReceiving.getMasterCommodity());
            serviceReceiving.setTwentyOneFeet(planningContReceiving.getTwentyOneFeet());
            serviceReceiving.setContGross(planningContReceiving.getContGross());
            serviceReceiving.setGrossClass(planningContReceiving.getGrossClass());
            serviceReceiving.setSealNo(planningContReceiving.getSealNo());
            serviceReceiving.setOverSize(planningContReceiving.getOverSize());
            serviceReceiving.setDangerous(planningContReceiving.getDg());
            serviceReceiving.setDgLabel(planningContReceiving.getDgLabel());
            serviceReceivingFacade.edit(serviceReceiving);
        }
        //service_reefer
        IDs = serviceReeferFacade.findIDReeferByPPKBnCONT(planningContReceiving.getPlanningVessel().getNoPpkb(), cont);
        for (int i = 0; i < IDs.size(); i++) {
            ServiceReefer serviceReefer = serviceReeferFacade.find(IDs.get(i));
            serviceReefer.setContNo(planningContReceiving.getContNo());
            serviceReefer.setMlo(planningContReceiving.getMlo());
            serviceReefer.setContSize(planningContReceiving.getContSize());
            serviceReefer.setMasterContainerType(planningContReceiving.getMasterContainerType());
            serviceReefer.setContStatus(planningContReceiving.getContStatus());
            serviceReeferFacade.edit(serviceReefer);
            List<Integer> idRMs = reeferMonitoringFacade.findAllByIdReefer(IDs.get(i));
            for (int j = 0; j < idRMs.size(); j++) {
                ReeferMonitoring reeferMonitoring = reeferMonitoringFacade.find(idRMs.get(j));
                reeferMonitoring.setContNo(planningContReceiving.getContNo());
                reeferMonitoring.setMlo(planningContReceiving.getMlo());
                reeferMonitoringFacade.edit(reeferMonitoring);
            }
        }
        //service_shifting
        IDs = findContIds(planningContReceiving.getPlanningVessel().getNoPpkb(), cont, "service_shifting");
        for (int i = 0; i < IDs.size(); i++) {
            ServiceShifting serviceShifting = serviceShiftingFacade.find(IDs.get(i));
            serviceShifting.setContNo(planningContReceiving.getContNo());
            serviceShifting.setMlo(planningContReceiving.getMlo());
            serviceShifting.setContSize(planningContReceiving.getContSize());
            serviceShifting.setMasterContainerType(planningContReceiving.getMasterContainerType());
            serviceShifting.setContStatus(planningContReceiving.getContStatus());
            serviceShifting.setMasterCommodity(planningContReceiving.getMasterCommodity());
            serviceShifting.setContGross(planningContReceiving.getContGross());
            serviceShifting.setIsExportImport(planningContReceiving.getIsExport());
            serviceShifting.setSealNo(planningContReceiving.getSealNo());
            serviceShifting.setOverSize(planningContReceiving.getOverSize());
            serviceShifting.setTwentyOneFeet(planningContReceiving.getTwentyOneFeet());
            serviceShifting.setDg(planningContReceiving.getDg());
            serviceShifting.setDgLabel(planningContReceiving.getDgLabel());
            serviceShiftingFacade.edit(serviceShifting);
        }

        IDs = findContIds(planningContReceiving.getPlanningVessel().getNoPpkb(), cont, "m_yard_coordinat");
        for (int i = 0; i < IDs.size(); i++) {
            MasterYardCoordinat masterYardCoordinat = masterYardCoordinatFacade.find(IDs.get(i));
            masterYardCoordinat.setBlNo(planningContReceiving.getBlNo());
            masterYardCoordinat.setContSize(planningContReceiving.getContSize());
            masterYardCoordinat.setContType(planningContReceiving.getMasterContainerType().getContType().toString());
            masterYardCoordinat.setContWeight(planningContReceiving.getContGross());
            masterYardCoordinat.setGrossClass(planningContReceiving.getGrossClass());
            masterYardCoordinat.setPod(planningContReceiving.getDischPort());
        }

        //perhitungan_lift_service
        IDs = findContIds(planningContReceiving.getPlanningVessel().getNoPpkb(), cont, "perhitungan_lift_service");
         for (int i = 0; i < IDs.size(); i++) {
             PerhitunganLiftService liftService = perhitunganLiftServiceFacadeLocal.find(IDs.get(i));
             liftService.setContNo(planningContReceiving.getContNo());
             perhitunganLiftServiceFacadeLocal.edit(liftService);
         }

        //perhitungan_penumpukan
        IDs = findContIds(planningContReceiving.getPlanningVessel().getNoPpkb(), cont, "perhitungan_penumpukan");
         for (int i = 0; i < IDs.size(); i++) {
             PerhitunganPenumpukan perhitunganPenumpukan = perhitunganPenumpukanFacadeLocal.find(IDs.get(i));
             perhitunganPenumpukan.setContNo(planningContReceiving.getContNo());
             perhitunganPenumpukanFacadeLocal.edit(perhitunganPenumpukan);
         }

    }

    /**
     *
     * @param contNo
     * @return
     *      0 = planning cont rec ID,
     *      1 = p.cont_no,
     *      2 = p.no_ppkb,
     *      3 = suggested block,
     *      4 = suggested slot,
     *      5 = suggested row,
     *      6 = suggested tier,
     *      7 = ordering index,
     *      8 = p.cont_size,
     *      9 = p.cont_status,
     *      10 = p.over_size,
     *      11 = p.dg,
     *      12 = p.disch_port,
     *      13 = sgr.cont_weight,
     *      14 = condition
     *      15 = vessel name
     */
    public Object[] findReceivableContainerWithSuggestedLocation(String contNo) {
        try {
            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PlanningContReceiving.Native.findReceivableContainerWithSuggestedLocation")
                    .setParameter(1, contNo)
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            return null;
        }
    }

    /**
     *
     * @param contNo
     * @return
     *      0 = planning cont rec ID,
     *      1 = p.cont_no,
     *      2 = p.no_ppkb,
     */
    public Object[] findReceivableContainer(String contNo) {
        try {
            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PlanningContReceiving.Native.findReceivableContainer")
                    .setParameter(1, contNo)
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Integer findByContValidasiConstraint(String no_ppkb, int contSize, int contType, String contStatus, String overSize, String dg, String isExport, String gross, String port) {
        Integer idPlan = 0;
        try {
            idPlan = decimalToInt(
                    (BigDecimal) getEntityManager().createNamedQuery("PlanningContReceiving.Native.findByContValidasiConstraint")
                    .setParameter(1, no_ppkb)
                    .setParameter(2, contSize)
                    .setParameter(3, contType)
                    .setParameter(4, contStatus)
                    .setParameter(5, overSize)
                    .setParameter(6, dg)
                    .setParameter(7, gross)
                    .setParameter(8, port)
                    .setParameter(9, isExport)
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            idPlan = 0;
        }
        return idPlan;
    }

    public int updatePlanningVessel(PlanningVessel newValue, PlanningVessel oldValue) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PlanningContReceiving.updatePlanningVessel")
                .setParameter("newValue", newValue)
                .setParameter("oldValue", oldValue)
                .executeUpdate();
    }

    public int updatePlanningVesselByContNo(PlanningVessel newValue, MasterPort nextPort, PlanningVessel oldValue, List<String> containers) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PlanningContReceiving.updatePlanningVesselByContNo")
                .setParameter("newValue", newValue)
                .setParameter("dischPort", nextPort.getPortCode())
                .setParameter("portOfDelivery", nextPort)
                .setParameter("oldValue", oldValue)
                .setParameter("containers", containers)
                .executeUpdate();
    }
}

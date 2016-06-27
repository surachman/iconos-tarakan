/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.BaplieDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.BookingOnlineFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.CancelLoadingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.LogExcelFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterCommodityFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterContainerTypeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterCustomerFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterPortFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningReceivingAllocationFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningVesselFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PreserviceVesselFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselProfileFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningShiftDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningTransDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContTranshipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.BaplieDischarge;
import com.pelindo.ebtos.model.db.BookingOnline;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.LogExcel;
import com.pelindo.ebtos.model.db.PlanningContDischarge;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PlanningReceivingAllocation;
import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import com.pelindo.ebtos.model.db.PlanningTransDischarge;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.util.GrossConverter;
import com.qtasnim.persistence.criteria.CriteriaHelper;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class PlanningVesselFacade extends AbstractFacade<PlanningVessel> implements PlanningVesselFacadeRemote, PlanningVesselFacadeLocal {
    public enum ServiceType {
        LOAD, DISCHARGE
    }

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @EJB
    private PlanningShiftDischargeFacadeRemote planningShiftDischargeFacade;
    @EJB
    private PlanningTransDischargeFacadeRemote planningTransDischargeFacade;
    @EJB
    private PlanningContDischargeFacadeRemote planningContDischargeFacade;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacade;
    @EJB
    private ServiceShiftingFacadeRemote serviceShiftingFacade;
    @EJB
    private ServiceContTranshipmentFacadeRemote serviceContTranshipmentFacade;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacade;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacade;
    @EJB
    private MasterCommodityFacadeLocal masterCommodityFacade;
    @EJB
    private MasterContainerTypeFacadeLocal masterContainerTypeFacade;
    @EJB
    private MasterPortFacadeLocal masterPortFacade;
    @EJB
    private BaplieDischargeFacadeLocal baplieDischargeFacade;
    @EJB
    private PlanningContLoadFacadeLocal planningContLoadFacade;
    @EJB
    private LogExcelFacadeLocal logExcelFacade;
    @EJB
    private MasterCustomerFacadeLocal masterCustomerFacadeLocal;
    @EJB
    private ServiceReceivingFacadeLocal serviceReceivingFacadeLocal;
    @EJB
    private PlanningContLoadFacadeLocal planningContLoadFacadeLocal;
    @EJB
    private PreserviceVesselFacadeLocal preserviceVesselFacadeLocal;
    @EJB
    private BookingOnlineFacadeLocal bookingOnlineFacadeLocal;
    @EJB
    private CancelLoadingServiceFacadeLocal cancelLoadingServiceFacadeLocal;
    @EJB
    private ServiceContLoadFacadeRemote serviceContLoadFacade;
    @EJB
    private PlanningReceivingAllocationFacadeLocal planningReceivingAllocationFacade;
    @EJB
    private PlanningReceivingFacadeLocal planningReceivingFacade;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private MasterVesselProfileFacadeRemote masterVesselProfileFacade;
    
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlanningVesselFacade() {
        super(PlanningVessel.class);
    }

    public Integer findReceivingAllocationCountByNoPpkb(String noPpkb) {
        return ((BigDecimal) getEntityManager().createNamedQuery("PlanningVessel.Native.findReceivingAllocationCountByNoPpkb")
                .setParameter(1, noPpkb)
                .getSingleResult()).intValue();
    }

    public Object[] findPlanningVesselByPpkb(String no_ppkb) {
        try {
            return (Object[]) getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselByPpkb").setParameter(1, no_ppkb).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public PlanningVessel findByPpkbAndStatus(String noPpkb, String status) {
        try {
            return (PlanningVessel) getEntityManager().createNamedQuery("PlanningVessel.findByPpkbAndStatus")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("status", status)
                    .getSingleResult();
        } catch (NoResultException re) {
            return null;
        }
    }

    public List<PlanningVessel> findByKapalBayanganStatus(Boolean kapalBayangan) {
        return getEntityManager().createNamedQuery("PlanningVessel.findByKapalBayanganStatus")
                .setParameter("kapalBayangan", kapalBayangan != null && kapalBayangan)
                .getResultList();
    }

    public List<Object[]> findPlanningVesselsReceivingConfirm() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselsReceivingConfirm").getResultList();
    }

    public List<Object[]> findPlanningVessels() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVessels").getResultList();
    }

    public List<Object[]> findPlanningVesselsCy() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselsCy").getResultList();
    }

    public List<Object[]> findPlanningVesselsSg(Date close_rec) {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselsSg").setParameter(1, close_rec).getResultList();
    }

    public List<Object[]> findCancelStatusAbleVessels() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findCancelStatusAbleVessels")
                .getResultList();
    }

    public List<Object[]> findPlanningVesselsSgOther() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselsSgOther").getResultList();
    }

    public List<Object[]> findVesselsCanBeCanceled() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findVesselsCanBeCanceled").getResultList();
    }

    public List<Object[]> findPlanningVesselList() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselList").getResultList();
    }

    public List<Object[]> findPlanningVesselsServicing() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselsServicing").getResultList();
    }

    public List<Object[]> findCanceledPpkb() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findCanceledPpkb").getResultList();
    }

    public Object[] findPlanningVesselDetail(String no_ppkb) {
        return (Object[]) getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselDetail").setParameter(1, no_ppkb).getSingleResult();
    }

    public List<Object[]> findPlanningVesselsByActivity() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselsByActivity").getResultList();
    }
    
    public List<Object[]> findPlanningVesselByPluggingToLoad() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselByPluggingToLoad").getResultList();
    }

    public List<Object[]> findPlanningVesselsByActivityYardAlocation() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselsByActivityYardAlocation").getResultList();
    }
    public List<Object[]> findPlanningVesselByVessel(String vessel_code) {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselByVessel").setParameter(1, vessel_code).getResultList();
    }

    public List<Object[]> findPlanningVesselByDock(String dock_code) {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselByDock").setParameter(1, dock_code).getResultList();
    }

    public List<Object[]> findPlanningVesselByVesselCode(String vessel_code) {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselByVesselCode").setParameter(1, vessel_code).getResultList();
    }

    @Override
    public List<String> findConflictBirthPlan(String dock, Date eta, Date etd, int fr_meter, int to_meter, String noPpkb) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println("++++" + dateFormat.format(eta) + "+" + dateFormat.format(etd) + "+" + fr_meter + "+" + to_meter + "+" + dock);
        return (List<String>) getEntityManager().createNamedQuery("PlanningVessel.Native.findConflictBerthings").setParameter(1, eta).setParameter(2, etd).setParameter(3, fr_meter).setParameter(4, to_meter).setParameter(5, dock).setParameter(6, noPpkb).getResultList();
    }

    public List<Object[]> findBerthingsWithRange(Date eta, Date etd, String dockCode, String noPpkb) {
        return (List<Object[]>) getEntityManager().createNamedQuery("PlanningVessel.Native.findBerthingsWithRange").setParameter(1, eta).setParameter(2, etd).setParameter(3, dockCode).setParameter(4, noPpkb).getResultList();
    }

    public List<Object[]> findVesselScheduleByDateRangeAndDock(Date eta, Date etd, String dockCode) {
        return (List<Object[]>) getEntityManager().createNamedQuery("PlanningVessel.Native.findVesselScheduleByDateRangeAndDock")
                .setParameter(1, eta)
                .setParameter(2, etd)
                .setParameter(3, dockCode)
                .getResultList();
    }

    public List<Object[]> findBerthingsBoundWithRange(Date eta, Date etd) {
        return (List<Object[]>) getEntityManager().createNamedQuery("PlanningVessel.Native.findBerthingsBoundWithRange").setParameter(1, eta).setParameter(2, etd).getResultList();
    }

    public List<Object[]> findPlanningVesselByStatus() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselServicingOnly").getResultList();
    }

    public List<Object[]> findByStatus4HHT(String status) {
        List vessels = getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselByStatus")
                .setParameter(1, status)
                .getResultList();

        if (vessels.isEmpty()) {
            vessels.add(new Object[]{
                "No Data PPKB", "No Data Vessel"
            });
        }

        return vessels;
    }

    public List<Object[]> findPlanningVesselApproval() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselByStatusApproval").getResultList();
    }

    public List<Object[]> findPlanningVesselApprovalReady() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselByStatusApprovalReady").getResultList();
    }
    
    //penambahan untuk menampilkan semua no ppkb untuk discharge list by ade chelsea tgl 23 april 2014
    public List<Object[]> findPlanningVesselApprovalReady2() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselByStatusApprovalReady2").getResultList();
    }

    public List<Object[]> findPlanningVesselByStatusAppReadyServicing() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselByStatusAppReadyServicing").getResultList();
    }

    public List<Object[]> findPlanningVesselApprovalByDock(String dockCode) {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselByStatusApprovalAndDock").setParameter(1, dockCode).getResultList();
    }

    public List<Object[]> findPlanningVesselReadyService() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselByStatusReadyService").getResultList();
    }

    public List<Object[]> findPlanningVesselByActivityLoad() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselByActivityLoad").getResultList();
    }

    public List<Object[]> vesselMonitoringToday() {
        Date now = Calendar.getInstance().getTime();
        return getEntityManager().createNamedQuery("PlanningVessel.Native.vesselMonitoringToday").setParameter(1, now).setParameter(2, now).getResultList();
    }

    public Integer findBooking(String no_ppkb) {
        Integer temp;
        try {
            temp = ((BigDecimal) getEntityManager().createNamedQuery("PlanningVessel.Native.findBooking").setParameter(1, no_ppkb).getSingleResult()).intValue();
        } catch (NoResultException ex) {
            temp = 0;
        }
        return temp;
    }

    public String findCurrCode(String no_ppkb) {
        String temp;
        try {
            temp = (String) getEntityManager().createNamedQuery("PlanningVessel.Native.findCurrCode").setParameter(1, no_ppkb).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

     public List<Object[]> vesselMonitoringOnline(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("PlanningVessel.Native.vesselMonitoringOnline").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findPlanningVesselByBeritaAcara(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselByBeritaAcara").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public String findTipePelayaranByPPKB(String no_ppkb) {
        return (String) getEntityManager().createNamedQuery("PlanningVessel.Native.findTipePelayaranByPPKB").setParameter(1, no_ppkb).getSingleResult();
    }

    public String generateId(String bulan) {
        return (String) getEntityManager().createNamedQuery("PlanningVessel.Native.generateId").setParameter(1, bulan).getSingleResult();
    }

    public Object[] findReadyServicesMobile(String no_ppkb) {
        Object[] temp = new Object[10];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("PlanningVessel.Native.findReadyServicesMobile").setParameter(1, no_ppkb).getSingleResult();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findPlanningVesselsByFinished() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselsByFinished").getResultList();
    }

    public List<Object[]> findPlanningVesselsByFinishedNotServed() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselsByFinishedNotServed").getResultList();
    }

    public List<Object[]> findPlanningVesselsHasApprovedtoLoad() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselsHasApprovedtoLoad").getResultList();
    }

    public List<String> findPpkbNumbers(String no_Ppkb){
        String no_ppkb = "%" + no_Ppkb.toUpperCase() + "%";
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPpkbNumbers").setParameter(1, no_ppkb).getResultList();
    }

    public List<Object[]> findPlanningVesselExcConfirm() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselExcConfirm").getResultList();
    }

    /**
     *
     * @param noPpkb
     * @param contNo
     * @return
     *      0. no_ppkb
     *      1. cont_no
     *      2. iso_code
     *      3. cont_status
     *      4. bl_no
     *      5. pass_date
     *      6. cont_gross
     *      7. service_type
     */
    @Override
    public Object[] findGatePassedContainer(String noPpkb, String contNo) {
        try {
            return (Object[]) getEntityManager().createNamedQuery("PlanningVessel.Native.findGatePassedContainer")
                    .setParameter(1, noPpkb)
                    .setParameter(2, contNo)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    public void handleStartService(PlanningVessel planningVessel){
        Date tgl = Calendar.getInstance().getTime();

        //copy ke service vessel
        ServiceVessel serviceVessel = new ServiceVessel();
        serviceVessel.setNoPpkb(planningVessel.getNoPpkb());
        serviceVessel.setDockCode(planningVessel.getMasterDock().getDockCode());
        serviceVessel.setBookingCode(planningVessel.getPreserviceVessel().getBookingCode());
        serviceVessel.setTipePelayaran(planningVessel.getTipePelayaran());
        serviceVessel.setEstDischarge(planningVessel.getEstDischarge());
        serviceVessel.setEstLoad(planningVessel.getEstLoad());
        serviceVessel.setDischarge(Short.parseShort("0"));
        serviceVessel.setLoad(Short.parseShort("0"));
        serviceVessel.setArrivalTime(planningVessel.getEta());
        serviceVessel.setEta(planningVessel.getEta());
        serviceVessel.setEtd(planningVessel.getEtd());
        serviceVessel.setEbt(planningVessel.getEbt());
        serviceVessel.setEstEndWork(planningVessel.getEstEndWork());
        serviceVessel.setEstStartWork(planningVessel.getEstStartWork());
        serviceVessel.setBerhtingTime(planningVessel.getEbt());
        serviceVessel.setStartWorkTime(tgl);
        serviceVessel.setCloseRec(planningVessel.getCloseRec());
        serviceVessel.setCloseDocRec(planningVessel.getCloseDocRec());
        serviceVessel.setCloseEmpty(planningVessel.getCloseEmpty());
        serviceVessel.setCloseDocMtyref(planningVessel.getCloseDocMtyref());
        serviceVessel.setCloseReefer(planningVessel.getCloseReefer());
        serviceVessel.setPaymentType(planningVessel.getPaymentType());
        serviceVessel.setStatus("Servicing");
        serviceVessel.setFrMeter(planningVessel.getFrMeter());
        serviceVessel.setToMeter(planningVessel.getToMeter());
        serviceVessel.setLoadServiceType(planningVessel.getLoadServiceType());
        serviceVessel.setDischargeServiceType(planningVessel.getDischargeServiceType());

        serviceVesselFacade.create(serviceVessel);

        //copy planning_cont_discharge ke service_cont_discharge
        List<PlanningContDischarge> planningContDischarges = planningContDischargeFacade.findByNoPpkb(planningVessel.getNoPpkb());
        for (PlanningContDischarge planningContDischarge : planningContDischarges) {
            ServiceContDischarge serviceContDischarge = new ServiceContDischarge();

            serviceContDischarge.setContNo(planningContDischarge.getContNo());
            serviceContDischarge.setMlo(planningContDischarge.getMlo());
            serviceContDischarge.setServiceVessel(serviceVessel);
            serviceContDischarge.setMasterCommodity(planningContDischarge.getMasterCommodity());
            serviceContDischarge.setContSize(planningContDischarge.getContSize());
            serviceContDischarge.setMasterContainerType(planningContDischarge.getMasterContainerType());
            serviceContDischarge.setContStatus(planningContDischarge.getContStatus());
            serviceContDischarge.setContGross(planningContDischarge.getContGross());
            serviceContDischarge.setSealNo(planningContDischarge.getSealNo());
            serviceContDischarge.setMasterYard(planningContDischarge.getMasterYard());
            serviceContDischarge.setTwentyOneFeet(planningContDischarge.getTwentyOneFeet());
            serviceContDischarge.setDangerous(planningContDischarge.getDg());
            serviceContDischarge.setDgLabel(planningContDischarge.getDgLabel());
            serviceContDischarge.setOverSize(planningContDischarge.getOverSize());
            serviceContDischarge.setVBay(planningContDischarge.getVBay());
            serviceContDischarge.setVRow(planningContDischarge.getVRow());
            serviceContDischarge.setVTier(planningContDischarge.getVTier());
            serviceContDischarge.setYRow(planningContDischarge.getYRow());
            serviceContDischarge.setYSlot(planningContDischarge.getYSlot());
            serviceContDischarge.setYTier(planningContDischarge.getYTier());
            serviceContDischarge.setStartPlacementDate(tgl);
            serviceContDischarge.setCrane("L");
            serviceContDischarge.setPosition("01");
            serviceContDischarge.setIsDelivery("FALSE");
            serviceContDischarge.setIsBehandle("FALSE");
            serviceContDischarge.setIsCfs("FALSE");
            serviceContDischarge.setIsInspection("FALSE");
            serviceContDischarge.setGrossClass(planningContDischarge.getGrossClass());
            serviceContDischarge.setIsImport(planningContDischarge.getIsImport());
            serviceContDischarge.setBlNo(planningContDischarge.getBlNo());
            serviceContDischarge.setCancelLoading("FALSE");
            serviceContDischarge.setDischPort(planningContDischarge.getDischPort());
            serviceContDischarge.setLoadPort(planningContDischarge.getLoadPort());

            serviceContDischargeFacade.create(serviceContDischarge);
        }

        //copy planning_trans_discharge ke service_cont_transhipment
        List<PlanningTransDischarge> planningTransDischarges = planningTransDischargeFacade.findByNoPpkb(planningVessel.getNoPpkb());
        for (PlanningTransDischarge planningTransDischarge : planningTransDischarges) {
            ServiceContTranshipment serviceContTranshipment = new ServiceContTranshipment();

            serviceContTranshipment.setContNo(planningTransDischarge.getContNo());
            serviceContTranshipment.setMlo(planningTransDischarge.getMlo());
            serviceContTranshipment.setServiceVessel(serviceVessel);
            serviceContTranshipment.setMasterCommodity(planningTransDischarge.getMasterCommodity());
            serviceContTranshipment.setContSize(planningTransDischarge.getContSize());
            serviceContTranshipment.setMasterContainerType(planningTransDischarge.getMasterContainerType());
            serviceContTranshipment.setContStatus(planningTransDischarge.getContStatus());
            serviceContTranshipment.setContGross(planningTransDischarge.getContGross());
            serviceContTranshipment.setSealNo(planningTransDischarge.getSealNo());
            serviceContTranshipment.setMasterYard(planningTransDischarge.getMasterYard());
            serviceContTranshipment.setDangerous(planningTransDischarge.getDg());
            serviceContTranshipment.setDgLabel(planningTransDischarge.getDgLabel());
            serviceContTranshipment.setOverSize(planningTransDischarge.getOverSize());
            serviceContTranshipment.setTwentyOneFeet(planningTransDischarge.getTwentyOneFeet());
            serviceContTranshipment.setVBay(planningTransDischarge.getVBay());
            serviceContTranshipment.setVRow(planningTransDischarge.getVRow());
            serviceContTranshipment.setVTier(planningTransDischarge.getVTier());
            serviceContTranshipment.setYRow(planningTransDischarge.getYRow());
            serviceContTranshipment.setIsExportImport(planningTransDischarge.getIsExportImport());
            serviceContTranshipment.setYSlot(planningTransDischarge.getYSlot());
            serviceContTranshipment.setYTier(planningTransDischarge.getYTier());
            serviceContTranshipment.setStartPlacementDate(tgl);
            serviceContTranshipment.setPosition("01");
            if(planningTransDischarge.getPlanningVessel() != null)
                serviceContTranshipment.setNewPpkb(planningTransDischarge.getPlanningVessel().getNoPpkb());
            serviceContTranshipment.setCrane("L");
            serviceContTranshipment.setBlNo(planningTransDischarge.getBlNo());
            serviceContTranshipment.setDischPort(planningTransDischarge.getDischPort());
            serviceContTranshipment.setLoadPort(planningTransDischarge.getLoadPort());
            serviceContTranshipment.setPortOfDelivery(planningTransDischarge.getPortOfDelivery());

            serviceContTranshipmentFacade.create(serviceContTranshipment);
        }

        //copy planning_shift_discharge ke service shifting
        List<PlanningShiftDischarge> planningShiftDischarges = planningShiftDischargeFacade.findByNoPpkb(planningVessel.getNoPpkb());
        for (PlanningShiftDischarge planningShiftDischarge : planningShiftDischarges) {
            ServiceShifting serviceShifting = new ServiceShifting();

            serviceShifting.setContNo(planningShiftDischarge.getContNo());
            serviceShifting.setMlo(planningShiftDischarge.getMlo());
            serviceShifting.setServiceVessel(serviceVessel);
            serviceShifting.setMasterCommodity(planningShiftDischarge.getMasterCommodity());
            serviceShifting.setContSize(planningShiftDischarge.getContSize());
            serviceShifting.setMasterContainerType(planningShiftDischarge.getMasterContainerType());
            serviceShifting.setContStatus(planningShiftDischarge.getContStatus());
            serviceShifting.setContGross(planningShiftDischarge.getContGross());
            serviceShifting.setSealNo(planningShiftDischarge.getSealNo());
            serviceShifting.setTwentyOneFeet(planningShiftDischarge.getTwentyOneFeet());
            serviceShifting.setOverSize(planningShiftDischarge.getOverSize());
            serviceShifting.setDg(planningShiftDischarge.getDg());
            serviceShifting.setDgLabel(planningShiftDischarge.getDgLabel());
            serviceShifting.setIsExportImport(planningShiftDischarge.getIsExportImport());
            serviceShifting.setDischPort(planningShiftDischarge.getDischPort());
            serviceShifting.setLoadPort(planningShiftDischarge.getLoadPort());
            serviceShifting.setVBay(planningShiftDischarge.getVBay());
            serviceShifting.setVRow(planningShiftDischarge.getVRow());
            serviceShifting.setVTier(planningShiftDischarge.getVTier());
            serviceShifting.setShiftTo(planningShiftDischarge.getShiftTo());
            serviceShifting.setCrane("L");
            serviceShifting.setOperation("DISCHARGE");
            serviceShifting.setIsLanded("FALSE");
            serviceShifting.setIsPlanning("TRUE");
            serviceShifting.setIsReshipping("FALSE");
            serviceShifting.setBlNo(planningShiftDischarge.getBlNo());
            serviceShifting.setPosition("01");
            serviceShiftingFacade.create(serviceShifting);
        }

//        Tarakan Load
        List<PlanningContLoad> planningContLoads = planningContLoadFacadeLocal.findByNoPpkb(planningVessel.getNoPpkb());
        for (PlanningContLoad planningContLoad : planningContLoads) {
//            ServiceContLoad serviceContLoad = new ServiceContLoad();
//
//            serviceContLoad.setContNo(planningContLoad.getContNo());
//            serviceContLoad.setMlo(planningContLoad.getMlo());
//            serviceContLoad.setServiceVessel(serviceVessel);
//            serviceContLoad.setMasterCommodity(planningContLoad.getMasterCommodity());
//            serviceContLoad.setContSize(planningContLoad.getContSize());
//            serviceContLoad.setMasterContainerType(planningContLoad.getMasterContainerType());
//            serviceContLoad.setContStatus(planningContLoad.getContStatus());
//            serviceContLoad.setContGross(planningContLoad.getContGross());
//            serviceContLoad.setSealNo(planningContLoad.getSealNo());
//            serviceContLoad.setMasterYard(planningContLoad.getMasterYard());
//            serviceContLoad.setTwentyOneFeet(planningContLoad.getTwentyOneFeet());
//            serviceContLoad.setDangerous(planningContLoad.getDg());
//            serviceContLoad.setDgLabel(planningContLoad.getDgLabel());
//            serviceContLoad.setOverSize(planningContLoad.getOverSize());
//            serviceContLoad.setVBay(planningContLoad.getVBay());
//            serviceContLoad.setVRow(planningContLoad.getVRow());
//            serviceContLoad.setVTier(planningContLoad.getVTier());
//            serviceContLoad.setYRow(planningContLoad.getYRow());
//            serviceContLoad.setYSlot(planningContLoad.getYSlot());
//            serviceContLoad.setYTier(planningContLoad.getYTier());
//            serviceContLoad.setCrane("L");
//            serviceContLoad.setPosition("02");
//            serviceContLoad.setBlNo(planningContLoad.getBlNo());
//            serviceContLoad.setIsTranshipment("FALSE");
//            serviceContLoad.setStatusCancelLoading("FALSE");
//            serviceContLoad.setTransactionDate(new Date());
//            serviceContLoadFacade.create(serviceContLoad);
            PlanningReceivingAllocation planningReceivingAllocation = planningReceivingAllocationFacade.findByNoPpkbAndContainer(planningContLoad.getPlanningVessel().getNoPpkb(), 
                    planningContLoad.getContSize(), planningContLoad.getMasterContainerType().getContType(), planningContLoad.getContStatus());
            
            if (planningReceivingAllocation!=null) {
                List<Object[]> planningReceivingList = planningReceivingFacade.findPlanningReceivingList(planningReceivingAllocation.getId());
                if (planningReceivingList!=null) {
                    String yard = (String) planningReceivingList.get(0)[1];
                    planningContLoad.setMasterYard(masterYardFacadeRemote.find(yard));
                    
                }
            }
                
            planningContLoad.setPosition(PlanningContLoad.LOCATION_CY);
            planningContLoadFacadeLocal.edit(planningContLoad);
        }
        
        
        planningVessel.setStatus("Servicing");
        planningVessel.setEnableOpenStack("TRUE");
        planningVesselFacade.edit(planningVessel);

        //hapus dari booking online
        Integer idBO = bookingOnlineFacadeLocal.findByBookingCode(planningVessel.getPreserviceVessel().getBookingCode());
        if (idBO != null) {
            BookingOnline bookingOnline = new BookingOnline();
            bookingOnline = bookingOnlineFacadeLocal.find(idBO);
            bookingOnline.setStatus("Servicing");
            bookingOnlineFacadeLocal.edit(bookingOnline);
        }
    }


    public void saveBaplieFromExcel(PlanningVessel vessel, List<Object[]> baplies, String action, LogExcel logExcel){
        if (action.equalsIgnoreCase(ServiceType.DISCHARGE.toString())) {
            for (int count = 0; count < baplies.size(); count++) {
                String stowageLoc = (String) baplies.get(count)[0];
                String blNo = (String) baplies.get(count)[1];
                String contNo = (String) baplies.get(count)[2];
                String isoCode = (String) baplies.get(count)[3];
                String status = (String) baplies.get(count)[4];
                String gross = (String) baplies.get(count)[5];
                String sealNo = (String) baplies.get(count)[6];
                String commodityCode = (String) baplies.get(count)[7];
                String loadPortCode = (String) baplies.get(count)[8];
                String dischPortCode = (String) baplies.get(count)[9];
                String deliveryPort = (String) baplies.get(count)[10];
                String twentyOneFeet = (String) baplies.get(count)[11];
                String isExportImport = (String) baplies.get(count)[12];
                String overDimension = (String) baplies.get(count)[13];
                String dgLabel = (String) baplies.get(count)[15];
                String master = (String) baplies.get(count)[16];

                BaplieDischarge baplieDischarge = new BaplieDischarge();
                MasterCommodity masterCommodity = masterCommodityFacade.find(commodityCode);
                baplieDischarge.setMasterCommodity(masterCommodity);
                baplieDischarge.setContGross(gross.equals("") ? 0 : Integer.valueOf(gross));

                MasterContainerType masterContainerType = masterContainerTypeFacade.findByIsoCodeSimplified(isoCode);

                if (masterContainerType != null) {
                    baplieDischarge.setMasterContainerType(masterContainerType);
                    baplieDischarge.setContSize(masterContainerType.getFeetMark());
                    baplieDischarge.setGrossClass(GrossConverter.convert((short) masterContainerType.getFeetMark(), Integer.parseInt(gross)));
                } else {
                    masterContainerType = masterContainerTypeFacade.find(3);
                    baplieDischarge.setMasterContainerType(masterContainerType);
                }

                MasterPort masterPort = masterPortFacade.find(loadPortCode);
                baplieDischarge.setMasterPort(masterPort);

                masterPort = masterPortFacade.find(dischPortCode);
                baplieDischarge.setMasterPort1(masterPort);

                masterPort = masterPortFacade.find(deliveryPort);
                baplieDischarge.setPortOfDelivery(masterPort);

                baplieDischarge.setContNo(contNo.equals("") ? null : contNo);
                baplieDischarge.setSealNo(sealNo);

                if (status.equalsIgnoreCase("MTY") || status.equalsIgnoreCase("FCL") || status.equalsIgnoreCase("LCL")) {
                    baplieDischarge.setContStatus(status);
                }

                if (isExportImport.equalsIgnoreCase("y")) {
                    baplieDischarge.setIsExportImport("TRUE");
                }
                else
                {
                    baplieDischarge.setIsExportImport("FALSE");
                }
                if (overDimension.equalsIgnoreCase("y")) {
                    baplieDischarge.setOverSize("TRUE");
                }
                else {
                    baplieDischarge.setOverSize("FALSE");
                    
                }
                
                if (baplieDischarge.getMasterCommodity() != null && baplieDischarge.getMasterCommodity().getMasterDangerousClass() != null) {
                baplieDischarge.setDg("TRUE");
                } else {
                    baplieDischarge.setDg("FALSE");
                }
                
                if (dgLabel.equalsIgnoreCase("y")) {
                baplieDischarge.setDgLabel("TRUE");
                } else {
                    baplieDischarge.setDgLabel("FALSE");
                }
                if (twentyOneFeet.equalsIgnoreCase("y")) {
                baplieDischarge.setTwentyOneFeet("TRUE");
                } else {
                    baplieDischarge.setTwentyOneFeet("FALSE");
                }

                MasterCustomer mlo = masterCustomerFacadeLocal.find(master);
                baplieDischarge.setMlo(mlo);

                baplieDischarge.setBlNo(blNo);

                if (stowageLoc.equals("")) {
                    stowageLoc = "00000000";
                }

//                Tarakan
                List<Integer> bayChoices = masterVesselProfileFacade.findBaysByVessel(vessel.getVesselCode());
//                baplieDischarge.setVBay(Short.parseShort(stowageLoc.substring(0, 3)));
//                baplieDischarge.setVRow(Short.parseShort(stowageLoc.substring(3, 5)));
//                baplieDischarge.setVTier(Short.parseShort(stowageLoc.substring(5)));
                baplieDischarge.setVBay(bayChoices.get(0).shortValue());
                baplieDischarge.setVRow((short)1);
                baplieDischarge.setVTier((short)1);
                
                baplieDischarge.setPlanningVessel(vessel);
                baplieDischarge.setTradeType(vessel.getTipePelayaran());
                if (baplieDischarge.isValid()) {
                baplieDischarge.setDataComplete("TRUE");
                } else {
                    baplieDischarge.setDataComplete("FALSE");
                }

                baplieDischargeFacade.edit(baplieDischarge);
            }
        } else if (action.equalsIgnoreCase(ServiceType.LOAD.toString())) {
            for (int count = 0; count < baplies.size(); count++) {
//                PlanningContReceiving planningContReceiving = new PlanningContReceiving();
//                planningContReceiving.setPlanningVessel(vessel);
                PlanningContLoad planningContLoad = new PlanningContLoad();
                planningContLoad.setPlanningVessel(vessel);
                String blNo = (String) baplies.get(count)[1];
                String contNo = (String) baplies.get(count)[2];
                String iso = (String) baplies.get(count)[3];
                String status = (String) baplies.get(count)[4];
                String contGross = (String) baplies.get(count)[5];
                String sealNo = (String) baplies.get(count)[6];
                String commodityCode = (String) baplies.get(count)[7];
                String loadPortCode = (String) baplies.get(count)[8];
                String dischPortCode = (String) baplies.get(count)[9];
                String deliveryPortCode = (String) baplies.get(count)[10];
                String twentyOneFeet = (String) baplies.get(count)[11];
                String isExport = (String) baplies.get(count)[12];
                String overDimension = (String) baplies.get(count)[13];
                String dgLabel = (String) baplies.get(count)[15];
                String master = (String) baplies.get(count)[16];

                MasterCommodity masterCommodity = masterCommodityFacade.find(commodityCode);
                planningContLoad.setMasterCommodity(masterCommodity);
                planningContLoad.setContGross(contGross.equals("") ? 0 : Integer.valueOf(contGross));
                
                MasterContainerType masterContainerType = masterContainerTypeFacade.findByIsoCodeSimplified(iso);
                if (masterContainerType != null) {
                    planningContLoad.setMasterContainerType(masterContainerType);
                    planningContLoad.setContSize(masterContainerType.getFeetMark());
                    
                    int size = masterContainerType.getFeetMark();
//                    planningContLoad.setGrossClass(GrossConverter.convert((short) size, planningContLoad.getContGross()));
                } else {
                    masterContainerType = masterContainerTypeFacade.find(3);
                    planningContLoad.setMasterContainerType(masterContainerType);
                }

                MasterPort masterPort = masterPortFacade.find(loadPortCode);
                planningContLoad.setLoadPort(masterPort == null ? null : masterPort.getPortCode());

                masterPort = masterPortFacade.find(dischPortCode);
                planningContLoad.setDischPort(masterPort == null ? null : masterPort.getPortCode());

                masterPort = masterPortFacade.find(deliveryPortCode);
                planningContLoad.setPortOfDelivery(masterPort);
                
                planningContLoad.setContNo(contNo);
                planningContLoad.setSealNo(sealNo);

                MasterCustomer mlo = masterCustomerFacadeLocal.find(master);
                planningContLoad.setMlo(mlo);

                if (status.equalsIgnoreCase("MTY") || status.equalsIgnoreCase("FCL") || status.equalsIgnoreCase("LCL")) {
                    planningContLoad.setContStatus(status);
                }

                if (isExport.equalsIgnoreCase("y")) {
                    planningContLoad.setIsExportImport("TRUE");
                } else {
                    planningContLoad.setIsExportImport("FALSE");
                }
                
                if(overDimension.equalsIgnoreCase("y")) {
                    planningContLoad.setOverSize("TRUE");
                } else {
                    planningContLoad.setOverSize("FALSE");
                }
                if (planningContLoad.getMasterCommodity() != null && planningContLoad.getMasterCommodity().getMasterDangerousClass() != null) {
                planningContLoad.setDg("TRUE");
                } else {
                    planningContLoad.setDg("FALSE");
                }
                if (dgLabel.equalsIgnoreCase("y")) {
                planningContLoad.setDgLabel("TRUE");
                } else {
                    planningContLoad.setDgLabel("FALSE");
                }
                if (twentyOneFeet.equalsIgnoreCase("y")) {
                    planningContLoad.setTwentyOneFeet("TRUE");
                } else {
                    planningContLoad.setTwentyOneFeet("FALSE");
                }
                planningContLoad.setBlNo(blNo);
//                if (planningContLoad.isValid()) {
                    planningContLoad.setCompleted("TRUE");
//                }
//                planningContLoad.setIsGenerate("TRUE");

                planningContLoadFacade.edit(planningContLoad);
            }

            if (!baplies.isEmpty()) {
//                vessel.setCheckBaplie("FALSE");
//                TARAKAN
                vessel.setCheckBaplie("TRUE");
                planningVesselFacade.edit(vessel);
            }
        } else {
            throw new RuntimeException("Exception caught: Action value is not valid");
        }
        
        logExcelFacade.edit(logExcel);
    }

    public void handleCancelVesselSchedule(PlanningVessel planningVessel) {
        BookingOnline bookingOnline = bookingOnlineFacadeLocal.findByBooking(planningVessel.getPreserviceVessel().getBookingCode());
        PreserviceVessel preserviceVessel = planningVessel.getPreserviceVessel();
        planningVessel.setStatus("Canceled");

        int result = serviceReceivingFacadeLocal.updateStatusCancelLoadingByNoPpkb(planningVessel.getNoPpkb(), "TRUE");
        result = planningContLoadFacadeLocal.updateStatusCancelLoadingByNoPpkb(planningVessel.getNoPpkb(), "TRUE");
        List<ServiceReceiving> receivedContainers = serviceReceivingFacadeLocal.findByNoPpkb(planningVessel.getNoPpkb());

        for (ServiceReceiving serviceReceiving: receivedContainers) {
            CancelLoadingService cancelLoadingService = new CancelLoadingService();
            cancelLoadingService.setBlNo(serviceReceiving.getBlNo());
            cancelLoadingService.setMasterYard(serviceReceiving.getMasterYard());
            cancelLoadingService.setMasterCommodity(serviceReceiving.getMasterCommodity());
            cancelLoadingService.setCategory(new Integer(2).shortValue());
            cancelLoadingService.setContGross(serviceReceiving.getContGross());
            cancelLoadingService.setContNo(serviceReceiving.getContNo());
            cancelLoadingService.setContSize(serviceReceiving.getContSize());
            cancelLoadingService.setMasterContainerType(serviceReceiving.getMasterContainerType());
            cancelLoadingService.setDg(serviceReceiving.getDangerous());
            cancelLoadingService.setDgLabel(serviceReceiving.getDgLabel());
            cancelLoadingService.setMlo(serviceReceiving.getMlo());
            cancelLoadingService.setPlanningVessel(serviceReceiving.getPlanningVessel());
            cancelLoadingService.setOverSize(serviceReceiving.getOverSize());
            cancelLoadingService.setSealNo(serviceReceiving.getSealNo());
            cancelLoadingService.setJobSlip(serviceReceiving.getReceivingService().getJobSlip());
            cancelLoadingService.setPosition("CY");
            cancelLoadingService.setPullOut("FALSE");
            cancelLoadingService.setIsDischarge("FALSE");
            cancelLoadingService.setyRow(serviceReceiving.getYRow());
            cancelLoadingService.setySlot(serviceReceiving.getYSlot());
            cancelLoadingService.setyTier(serviceReceiving.getYTier());
            cancelLoadingService.setPosisi("02");
            cancelLoadingService.setStatus("Container ada di CY");
            cancelLoadingService.setTruckLosing("FALSE");
            cancelLoadingService.setChangeDestination("FALSE");
            cancelLoadingService.setContStatus(serviceReceiving.getContStatus());
            cancelLoadingServiceFacadeLocal.create(cancelLoadingService);
            serviceReceiving.setStatusCancelLoading("TRUE");
            serviceReceivingFacadeLocal.edit(serviceReceiving);
        }
        
        if (bookingOnline != null) {
            bookingOnline.setStatus("Canceled");
            bookingOnlineFacadeLocal.edit(bookingOnline);
        }

        if (preserviceVessel != null) {
            preserviceVessel.setStatus("Canceled");
            preserviceVesselFacadeLocal.edit(preserviceVessel);
        }
        
        edit(planningVessel);
    }
    

    @Override
    public List<Object[]> findPlanningVesselsServicingOnly() {
        return getEntityManager().createNamedQuery("PlanningVessel.Native.findPlanningVesselsServicingOnly").getResultList();
    }

    public int findByStatussesAndLoadOnly_Count(Map<String,String> likes, String... statusses) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery();
        Root root = query.from(PlanningVessel.class);
        List<Predicate> conditions = new ArrayList<Predicate>();

        conditions.add(
            builder.and(
                root.get("status").in(Arrays.asList(statusses))
            )
        );

        conditions.add(
            builder.and(
                builder.notEqual(
                    CriteriaHelper.translateRefference(root, "preserviceVessel.activity"), 1)
            )
        );
        
        return count(builder, query, root, likes, conditions);
    }

    public List<PlanningVessel> findByStatussesAndLoadOnly(int first, int pageSize, String sortField, boolean sortOrder, Map<String,String> likes, String... statusses) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery();
        Root root = query.from(PlanningVessel.class);
        List<Predicate> conditions = new ArrayList<Predicate>();

        conditions.add(
            builder.and(
                root.get("status").in(Arrays.asList(statusses))
            )
        );

        conditions.add(
            builder.and(
                builder.notEqual(
                    CriteriaHelper.translateRefference(root, "preserviceVessel.activity"), 1)
            )
        );

        return findAll(builder, query, root, first, pageSize, sortField, sortOrder, likes, conditions);
    }
}

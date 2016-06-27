/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.qtasnim.util.DateCalculator;
import com.pelindo.ebtos.ejb.facade.local.ChangeStatusHistoryFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.EquipmentFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterCurrencyFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterTarifContFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PerhitunganPenumpukanTranshipmentFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.RecapAirKapalFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.RecapHandlingDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.RecapHandlingLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.RecapHandlingUcFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.RecapHatchMoveFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.RecapOtherPackageFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.RecapPenumpukanFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.RecapPenumpukanUcFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.RecapReeferLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.RecapShiftingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.RecapTranshipmentFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.RecapUpahBuruhFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.RecapUpahBuruhHatchMoveFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.RecapUpahBuruhUcFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceContDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceContLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceContTranshipmentFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceHatchMovesFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceShiftingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceVesselFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.UcPerhitunganPenumpukanFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.UcReceivingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.UcShiftingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.UncontainerizedServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RecapJasaDermagaUcFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReeferFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.ChangeStatusHistory;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukanTranshipment;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.RecapAirKapal;
import com.pelindo.ebtos.model.db.RecapHandlingDischarge;
import com.pelindo.ebtos.model.db.RecapHandlingLoad;
import com.pelindo.ebtos.model.db.RecapHandlingUc;
import com.pelindo.ebtos.model.db.RecapHatchMove;
import com.pelindo.ebtos.model.db.RecapOtherPackage;
import com.pelindo.ebtos.model.db.RecapPenumpukan;
import com.pelindo.ebtos.model.db.RecapPenumpukanUc;
import com.pelindo.ebtos.model.db.RecapShifting;
import com.pelindo.ebtos.model.db.RecapTranshipment;
import com.pelindo.ebtos.model.db.RecapUpahBuruh;
import com.pelindo.ebtos.model.db.RecapUpahBuruhHatchMove;
import com.pelindo.ebtos.model.db.RecapUpahBuruhUc;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceHatchMoves;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.ServiceReefer;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.UcReceivingService;
import com.pelindo.ebtos.model.db.UcShiftingService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterDangerousClass;
import com.pelindo.ebtos.model.db.master.MasterPort;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class ServiceVesselFacade extends AbstractFacade<ServiceVessel> implements ServiceVesselFacadeRemote, ServiceVesselFacadeLocal {
    private static final String DISCHARGE = "DISCHARGE";
    private static final String LOAD = "LOAD";
    
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacade;
    @EJB
    private ChangeStatusHistoryFacadeLocal changeStatusHistoryFacade;
    @EJB
    private UcPerhitunganPenumpukanFacadeLocal ucPerhitunganPenumpukanFacade;
    @EJB
    private UcReceivingServiceFacadeLocal ucReceivingServiceFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private RecapReeferLoadFacadeLocal recapReeferLoadFacade;
    @EJB
    private RecapPenumpukanFacadeLocal recapPenumpukanFacade;
    @EJB
    private ServiceContLoadFacadeLocal serviceContLoadFacade;
    @EJB
    private ServiceContDischargeFacadeLocal serviceContDischargeFacade;
    @EJB
    private ServiceContTranshipmentFacadeLocal serviceContTranshipmentFacade;
    @EJB
    private RecapTranshipmentFacadeLocal recapTranshipmentFacade;
    @EJB
    private RecapShiftingFacadeLocal recapShiftingFacade;
    @EJB
    private RecapHandlingUcFacadeLocal recapHandlingUcFacadeLocal;
    @EJB
    private RecapUpahBuruhUcFacadeLocal recapUpahBuruhUcFacadeLocal;
    @EJB
    private RecapHandlingLoadFacadeLocal recapHandlingLoadFacade;
    @EJB
    private RecapHandlingDischargeFacadeLocal recapHandlingDischargeFacade;
    @EJB
    private ServiceShiftingFacadeLocal serviceShiftingFacade;
    @EJB
    private UcShiftingServiceFacadeLocal ucShiftingServiceFacade;
    @EJB
    private ServiceHatchMovesFacadeLocal serviceHatchMovesFacade;
    @EJB
    private MasterTarifContFacadeLocal masterTarifContFacade;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacade;
    @EJB
    private ServiceReeferFacadeRemote serviceReeferFacade;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacade;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacade;
    @EJB
    private ServiceReceivingFacadeRemote serviceReceivingFacadeRemote;
    @EJB
    private ReceivingServiceFacadeRemote receivingServiceFacadeRemote;
    @EJB
    private EquipmentFacadeLocal equipmentFacadeLocal;
    @EJB
    private RecapHatchMoveFacadeLocal recapHatchMoveFacade;
    @EJB
    private PerhitunganPenumpukanTranshipmentFacadeLocal perhitunganPenumpukanTranshipmentFacade;
    @EJB
    private MasterCurrencyFacadeLocal masterCurrencyFacadeLocal;
    @EJB
    private RecapPenumpukanUcFacadeLocal recapPenumpukanUcFacadeLocal;
    @EJB
    private UncontainerizedServiceFacadeLocal uncontainerizedServiceFacadeLocal;
    @EJB
    private RecapUpahBuruhFacadeLocal recapUpahBuruhFacadeLocal;
    @EJB
    private MasterPortFacadeRemote masterPortFacadeRemote;
    @EJB
    private RecapAirKapalFacadeLocal recapAirKapalFacadeLocal;
    @EJB 
    private RecapJasaDermagaUcFacadeRemote recapJasaDermagaUcFacadeRemote;
    @EJB
    private RecapUpahBuruhHatchMoveFacadeLocal recapUpahBuruhHatchMoveFacadeLocal;

    @EJB
    private RecapOtherPackageFacadeLocal recapOtherPackageFacade;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceVesselFacade() {
        super(ServiceVessel.class);
    }

    public List<Object[]> findServiceVesselServicing() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselByStatusServicing").getResultList();
    }
    
    public List<Object[]> findServiceVesselsServicingLoad() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselsServicingLoad").getResultList();
    }

    public List<Object[]> findServiceVesselStatusService() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findPlanningVesselByStatusService").getResultList();
    }

    public List<Object[]> findServiceVesselStatusServed() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findPlanningVesselStatusServed").getResultList();
    }

    public Object[] findServiceVesselByPpkb(String no_ppkb) {
        return (Object[]) getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselByPpkb").setParameter(1, no_ppkb).getSingleResult();
    }

    public List<Object[]> findServiceVessels() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVessels").getResultList();
    }

    public List<Object[]> findServiceVesselsServicing() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselsServicing").getResultList();
    }

    public List<Object[]> findServiceVesselsServicingByDock(String dockCode) {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselsServicingByDock").setParameter(1, dockCode).getResultList();
    }

    public List<Object[]> findAllReceivingService() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findAllReceivingService").getResultList();
    }

    public Object[] findReceivingServiceByPpkb(String no_ppkb) {
        return (Object[]) getEntityManager().createNamedQuery("ServiceVessel.Native.findReceivingServiceByPpkb").setParameter(1, no_ppkb).getSingleResult();
    }

    /**
     *
     * @return
     *      List of Vessel
     */
    public List<String> findVesselsOnPort() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findVesselsOnPort").getResultList();
    }

    public List<Object[]> findServiceVesselsByYardPlanningMonitoring() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselsByYardPlanningMonitoring").getResultList();
    }

    /**
     *
     * @return
     *      List of Object[]:
     *      0 = m_vessel.name
     *      1 = arrival_time
     *      2 = departure_time
     */
    public List<Object[]> findRecentArrivalsAndDepartures() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findRecentArrivalsAndDepartures").getResultList();
    }

    /**
     *
     * @return
     *      List of Object[]:
     *      0 = month-date
     *      1 = count of arrival
     *      2 = count of departure
     */
    public List<Object[]> findArrivalsAndDeparturesLastMonth() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findArrivalsAndDeparturesLastMonth").getResultList();
    }

    public List<Object[]> findServiceVesselsServiced() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselsServiced").getResultList();
    }
    
    public List<Object[]> findServiceVesselsServicedServicingDischarge() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselsServicedServicingDischarge").getResultList();
    }
    
    public List<Object[]> findServiceVesselsServicedLoad() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselsServicedLoad").getResultList();
    }

    public List<Object[]> findServiceVesselsServicedDischarge() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselsServicedDischarge").getResultList();
    }

    public List<Object[]> findServiceVesselsCancelLoadingConfirm() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselsCancelLoadingConfirm").getResultList();
    }

    public Object[] findServiceVesselDetail(String no_ppkb) {
        return (Object[]) getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselDetail").setParameter(1, no_ppkb).getSingleResult();
    }

    public List<Object[]> checkSpaceAvailability(Short frMeter, Short toMeter, String dockCode, String noPpkb) {
        return (List<Object[]>) getEntityManager().createNamedQuery("ServiceVessel.Native.checkSpaceAvailability").setParameter(1, frMeter).setParameter(2, toMeter).setParameter(3, dockCode).setParameter(4, noPpkb).getResultList();
    }

    public List<Object[]> findServiceVesselByOnline(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselByOnline").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public Object[] findServiceVesselsByMonitoringCountDischarge(String no_ppkb) {
        Object[] temp = new Object[10];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselsByMonitoringCountDischarge").setParameter(1, no_ppkb).getSingleResult();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public Object[] findServiceVesselsByMonitoringCountLoad(String no_ppkb) {
        Object[] temp = new Object[10];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselsByMonitoringCountLoad").setParameter(1, no_ppkb).getSingleResult();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public Object[] findServiceVesselsByMonitoringCountLoad2(String no_ppkb) {
        Object[] temp = new Object[10];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselsByMonitoringCountLoad2").setParameter(1, no_ppkb).getSingleResult();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public ServiceVessel findByPpkbAndStatus(String noPpkb, String status) {
        try {
            return (ServiceVessel) getEntityManager().createNamedQuery("ServiceVessel.findByPpkbAndStatus")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("status", status)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Object[]> findTruckDestinationByAppKey(String key) {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findTruckDestinationByAppKey").setParameter(1, key).getResultList();
    }

    public List<Object[]> findPpkbCfs() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findPpkbCfs").getResultList();
    }

    /**
     * @return
     *      0 = booking
     *      1 = confirm
     *      2 = approved
     *      3 = readyservice
     *      4 = servicing
     *      5 = served
     */
    @Override
    public Object[] getVesselActivities() {
        return (Object[]) getEntityManager().createNamedQuery("ServiceVessel.Native.getVesselActivities").getSingleResult();
    }

    /**
     * @return
     *      0 = load
     *      1 = discharge
     *      2 = behandle
     *      3 = transhipment
     *      4 = shifting
     */
    @Override
    public Object[] getContServiceActivitiesToday() {
        return (Object[]) getEntityManager().createNamedQuery("ServiceVessel.Native.getContServiceActivitiesToday").getSingleResult();
    }
    
    //      penambahan pengecekan service vessel confirm by ade chelsea tanggal 04-07-2014 jam 08:56    
    public Integer getCountServiceVesselConfirm(String no_ppkb) {        
        
            return (Integer) getEntityManager().createNamedQuery("ServiceVessel.Native.getCountServiceVesselConfirm").setParameter(1, no_ppkb).getSingleResult();
        
    }
    
    public Object[] findServiceByStatus(String no_ppkb, String status) {
        Object[] temp = new Object[10];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceByStatus").setParameter(1, no_ppkb).setParameter(2, status).getSingleResult();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<String> findPpkbNumbers(String no_Ppkb) {
        String no_ppkb = "%" + no_Ppkb.toUpperCase() + "%";
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findPpkbNumbers").setParameter(1, no_ppkb).getResultList();
    }

    public List<Object[]> findServiceVesselsFromDischarge() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselsFromDischarge").getResultList();
    }

    public List<Object[]> findServiceVesselsToLoad() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselsToLoad").getResultList();
    }

    public List<Object[]> findServiceVesselsFromChange() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselsFromChange").getResultList();
    }

    public List<Object[]> findServiceVesselsToChange() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselsToChange").getResultList();
    }

    public List<Object[]> findServiceVesselsByMonitoringContDischarge() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselsByMonitoringContDischarge").getResultList();
    }

    public List<Object[]> findServiceVesselsByMonitoringContLoad() {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselsByMonitoringContLoad").getResultList();
    }

    public List<Object[]> findServiceVesselsByMonthAndYear(Integer bulan, Integer tahun) {
        return getEntityManager().createNamedQuery("ServiceVessel.Native.findServiceVesselsByMonthAndYear").setParameter(1, bulan).setParameter(2, tahun).getResultList();
    }

    int count = 1;

    public void handleEndService(ServiceVessel serviceVessel) {
        try {
            PlanningVessel planningVessel = planningVesselFacade.find(serviceVessel.getNoPpkb());
            MasterCurrency idrMasterCurrency = masterCurrencyFacadeLocal.getIDRCurrency();
            MasterCurrency usdMasterCurrency = masterCurrencyFacadeLocal.getUSDCurrency();
            MasterCurrency masterCurrency = idrMasterCurrency;
            String implementedPortCode = masterSettingAppFacade.findImplementedPortCode();
            MasterPort implementedPort = masterPortFacadeRemote.find(implementedPortCode);
            int masaFreeRange = masterSettingAppFacade.getMasa1FreeRange();
            int masa1ContainerRange = masterSettingAppFacade.getMasa1ContainerRange();
            int masa1UcRange = masterSettingAppFacade.getMasa1UcRange();
            boolean isInternationalVessel = serviceVessel.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("i");

            if (isInternationalVessel) {
                masterCurrency = usdMasterCurrency;
            }

            calculateAirKapal(masterCurrency, serviceVessel);

            calculateHatchMoves(serviceVessel, masterCurrency, idrMasterCurrency);

            calculateContainerLoad(serviceVessel, usdMasterCurrency, idrMasterCurrency, masa1ContainerRange, masaFreeRange);

            calculateShifting(serviceVessel, usdMasterCurrency, idrMasterCurrency);

            calculateUncontaineizedShifting(serviceVessel, usdMasterCurrency, idrMasterCurrency);

            calculateContTranshipment(serviceVessel, usdMasterCurrency, idrMasterCurrency);

            calculateUncontainerizedTranshipment(serviceVessel, usdMasterCurrency, idrMasterCurrency, masa1UcRange, masaFreeRange);

            calculateContainerDischarge(serviceVessel, usdMasterCurrency, idrMasterCurrency);

            calculateUncontainerizedServiceDischargeCyToCy(serviceVessel, usdMasterCurrency, idrMasterCurrency);

            calculateReceivingUcThatHadBeenLoaded(serviceVessel, masa1UcRange, masaFreeRange, idrMasterCurrency, usdMasterCurrency);

            calculateContTranshipments(serviceVessel, idrMasterCurrency);

            serviceVessel.setStatus("Served");
            planningVessel.setStatus("Served");
            serviceVesselFacade.edit(serviceVessel);
            planningVesselFacade.edit(planningVessel);

            /*
            //Start RecapJasaDermagaUC
            List<UncontainerizedService> uncontainerizedServices = uncontainerizedServiceFacadeLocal.findByNoPpkbForRecapJasaDermagaUC(serviceVessel.getNoPpkb());
            for (UncontainerizedService uncontainerizedService : uncontainerizedServices) {
                String recapJasaDermagaUCActivityCode = masterActivityFacade.translateJasaDermagaUcActivityCode();
                MasterActivity masterActivity = masterActivityFacade.find(recapJasaDermagaUCActivityCode);
                BigDecimal penumpukanUcCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), recapJasaDermagaUCActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());

                RecapJasaDermagaUc recapJasaDermagaUc = new RecapJasaDermagaUc();
                recapJasaDermagaUc.setServiceVessel(serviceVessel);
                recapJasaDermagaUc.setBlNo(uncontainerizedService.getBlNo());
                recapJasaDermagaUc.setMasterCurrency(masterCurrency);
                recapJasaDermagaUc.setMasterActivity(masterActivity);
                recapJasaDermagaUc.setOperation(uncontainerizedService.getOperation());
                if(!uncontainerizedService.getIsShifting() && !uncontainerizedService.getIsTranshipment()) {
                    recapJasaDermagaUc.setActivity(uncontainerizedService.getOperation());
                }else if(uncontainerizedService.getIsShifting() && !uncontainerizedService.getIsTranshipment()) {
                    recapJasaDermagaUc.setActivity(RecapJasaDermagaUc.OPERATION_SHIFTING);
                }else if(!uncontainerizedService.getIsShifting() && uncontainerizedService.getIsTranshipment()) {
                    recapJasaDermagaUc.setActivity(RecapJasaDermagaUc.OPERATION_TRANSHIPMENT);
                }

                Double tonage = Double.valueOf(uncontainerizedService.getWeight() / 1000);
                Double volume = uncontainerizedService.getCubication().doubleValue();
                Double factor = tonage > volume ? tonage : volume;

                recapJasaDermagaUc.setCharge(penumpukanUcCharge);
                recapJasaDermagaUc.setTotalCharge(penumpukanUcCharge.multiply(BigDecimal.valueOf(factor)));
                if (uncontainerizedService.getIsShifting() && uncontainerizedService.getShiftTo().equalsIgnoreCase("LANDED")) {
                    recapJasaDermagaUc.setActivity(RecapJasaDermagaUc.OPERATION_SHIFTING);
                    recapJasaDermagaUcFacadeRemote.edit(recapJasaDermagaUc);
                    recapJasaDermagaUc.setActivity(RecapJasaDermagaUc.OPERATION_RESHIPPING);
                    recapJasaDermagaUcFacadeRemote.edit(recapJasaDermagaUc);
                } else {
                    recapJasaDermagaUcFacadeRemote.edit(recapJasaDermagaUc);
                }
            }
            //End RecapJasaDermagaUC
             */
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
            throw re;
        }
    }

    private void calculateReceivingUcThatHadBeenLoaded(ServiceVessel serviceVessel, int masa1UcRange, int masaFreeRange, MasterCurrency idrMasterCurrency, MasterCurrency usdMasterCurrency) {
        MasterCurrency masterCurrency;
        List<UncontainerizedService> loadedUcs = uncontainerizedServiceFacadeLocal.findReceivingUcThatHadBeenLoaded(serviceVessel.getNoPpkb());
        for (UncontainerizedService loadedUc: loadedUcs) {
            UcReceivingService ucReceivingService = loadedUc.getUcReceivingService();
            
            int min = 0;
            int masa1 = 0;
            int masa2 = 0;
            int masaFree = 1;
            
            if (loadedUc.getTruckLoosing().equals("TRUE")) {
                min = 5;
            } else {
                Equipment equipment = equipmentFacadeLocal.findByPpkbBLNoEquipmentActCodeAndOperation(serviceVessel.getNoPpkb(), loadedUc.getBlNo(), "LIFTON", "LOADUC");
                if (loadedUc.getIsShifting().equals("TRUE")) {
                    
                } else if (loadedUc.getIsTranshipment().equals("TRUE")){
                    min = DateCalculator.countRangeInDays(equipment.getStartTime(), loadedUc.getPlacementDate()) + 1;
                } else {
                    min = DateCalculator.countRangeInDays(equipment.getStartTime(), ucReceivingService.getReceivingDate()) + 1;
                }
            }
            
            if (min < (masa1UcRange + 1)) {
                if (min <= masaFreeRange) {
                    masa1 = masaFree;
                } else {
                    masa1 = min - masaFreeRange + masaFree;
                }
            } else {
                masa1 = masa1UcRange - masaFreeRange + masaFree;
                masa2 = min - masa1UcRange;
            }
            
            ucReceivingService.setMasa1(masa1);
            ucReceivingService.setMasa2(masa2);
            
            String penumpukanUcActivityCode = masterActivityFacade.translatePenumpukanUcActivityCode();
            MasterActivity masterActivity = masterActivityFacade.find(penumpukanUcActivityCode);
            BigDecimal penumpukanUcCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), penumpukanUcActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
            
            RecapPenumpukanUc recapPenumpukanUc = new RecapPenumpukanUc();
            recapPenumpukanUc.setTotalHari(min);
            recapPenumpukanUc.setBlNo(loadedUc.getBlNo());
            recapPenumpukanUc.setMasa1(ucReceivingService.getMasa1().shortValue());
            recapPenumpukanUc.setMasa2(ucReceivingService.getMasa2().shortValue());
            recapPenumpukanUc.setMasterActivity(masterActivity);
            recapPenumpukanUc.setOperation(RecapPenumpukanUc.OPERATION_LOAD);
            recapPenumpukanUc.setActivity(RecapPenumpukanUc.ACTIVITY_LOAD);
            recapPenumpukanUc.setMasterCurrency(idrMasterCurrency);
            recapPenumpukanUc.setServiceVessel(serviceVessel);
            
            if (serviceVessel.getIsLoadPortToPort()) {
                recapPenumpukanUc.setMasa1((short) (ucReceivingService.getMasa1() - 1));
            }
            
            Double tonage = Double.valueOf(loadedUc.getWeight() / 1000);
            Double volume = loadedUc.getCubication().doubleValue();
            Double factor = tonage > volume ? tonage : volume;
            
            recapPenumpukanUc.setJasaDermaga(BigDecimal.ZERO);
            recapPenumpukanUc.setChargeMasa1(penumpukanUcCharge.multiply(BigDecimal.valueOf(recapPenumpukanUc.getMasa1())).multiply(BigDecimal.valueOf(factor)));
            recapPenumpukanUc.setChargeMasa2(penumpukanUcCharge.multiply(BigDecimal.valueOf(recapPenumpukanUc.getMasa2())).multiply(BigDecimal.valueOf(factor)).multiply(BigDecimal.valueOf(2)));
            recapPenumpukanUc.setTotalCharge(recapPenumpukanUc.getChargeMasa1().add(recapPenumpukanUc.getChargeMasa2()).add(recapPenumpukanUc.getJasaDermaga()));
            
            if (recapPenumpukanUc.getTotalCharge().compareTo(BigDecimal.ZERO) > 0) {
                recapPenumpukanUcFacadeLocal.create(recapPenumpukanUc);
            }
            
            ucReceivingServiceFacade.edit(ucReceivingService);
            
            if (serviceVessel.getIsLoadCyToCy() != true) {
                continue;
            }
            
            //[1AM]
            if (loadedUc.getIsExportImport().equals("TRUE")) {
                masterCurrency = usdMasterCurrency;
            } else {
                masterCurrency = idrMasterCurrency;
            }
            
            String handlingUcActivityCode = masterActivityFacade.translateHandlingUcActivityCode(loadedUc.getWeight(), loadedUc.getCrane());
            MasterActivity handlingUcMasterActivity = masterActivityFacade.find(handlingUcActivityCode);
            BigDecimal handlingUcCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), handlingUcActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
            
            RecapHandlingUc recapHandlingLoad = recapHandlingUcFacadeLocal.findByBentuk3Constraint(
                    serviceVessel.getNoPpkb(),
                    loadedUc.getWeightClassification(),
                    loadedUc.getCrane(),
                    loadedUc.getIsExportImport(),
                    handlingUcActivityCode,
                    masterCurrency.getCurrCode(),
                    RecapHandlingUc.ACTIVITY_LOAD,
                    RecapHandlingUc.ACTIVITY_LOAD,
                    handlingUcCharge
            );
            
            if (recapHandlingLoad != null) {
                recapHandlingLoad.setAmount(recapHandlingLoad.getAmount() + 1);
            } else {
                recapHandlingLoad = new RecapHandlingUc();
                recapHandlingLoad.setServiceVessel(serviceVessel);
                recapHandlingLoad.setWeightGroup(loadedUc.getWeightClassification());
                recapHandlingLoad.setCrane(loadedUc.getCrane());
                recapHandlingLoad.setIsExportImport(loadedUc.getIsExportImport());
                recapHandlingLoad.setMasterCurrency(masterCurrency);
                recapHandlingLoad.setMasterActivity(handlingUcMasterActivity);
                recapHandlingLoad.setOperation(RecapHandlingUc.OPERATION_LOAD);
                recapHandlingLoad.setActivity(RecapHandlingUc.ACTIVITY_LOAD);
                recapHandlingLoad.setCharge(handlingUcCharge);
                recapHandlingLoad.setAmount(1);
            }
            
            recapHandlingLoad.setTotalCharge(recapHandlingLoad.getCharge().multiply(BigDecimal.valueOf(recapHandlingLoad.getAmount())));
            
            String upahBuruhUcActivityCode = masterActivityFacade.translateUbahBuruhUcActivityCode();
            MasterActivity upahBuruhUcMasterActivity = masterActivityFacade.find(upahBuruhUcActivityCode);
            BigDecimal upahBuruhUcCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), upahBuruhUcActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
            RecapUpahBuruhUc recapUpahBuruhUc = recapUpahBuruhUcFacadeLocal.findByBentuk3Constraint(
                    serviceVessel.getNoPpkb(),
                    loadedUc.getWeightClassification(),
                    loadedUc.getCrane(),
                    loadedUc.getIsExportImport(),
                    upahBuruhUcActivityCode,
                    idrMasterCurrency.getCurrCode(),
                    RecapUpahBuruh.OPERATION_LOAD,
                    RecapUpahBuruh.ACTIVITY_LOAD,
                    upahBuruhUcCharge.multiply(BigDecimal.valueOf(factor))
            );
            
            if (recapUpahBuruhUc != null) {
                recapUpahBuruhUc.setAmount(recapUpahBuruhUc.getAmount() + 1);
            } else {
                recapUpahBuruhUc = new RecapUpahBuruhUc();
                recapUpahBuruhUc.setServiceVessel(serviceVessel);
                recapUpahBuruhUc.setWeightGroup(loadedUc.getWeightClassification());
                recapUpahBuruhUc.setCrane(loadedUc.getCrane());
                recapUpahBuruhUc.setIsExportImport(loadedUc.getIsExportImport());
                recapUpahBuruhUc.setMasterCurrency(idrMasterCurrency);
                recapUpahBuruhUc.setMasterActivity(upahBuruhUcMasterActivity);
                recapUpahBuruhUc.setOperation(RecapUpahBuruh.OPERATION_LOAD);
                recapUpahBuruhUc.setActivity(RecapUpahBuruh.ACTIVITY_LOAD);
                recapUpahBuruhUc.setCharge(upahBuruhUcCharge.multiply(BigDecimal.valueOf(factor)));
                recapUpahBuruhUc.setAmount(1);
            }
            
            recapUpahBuruhUc.setTotalCharge(recapUpahBuruhUc.getCharge().multiply(BigDecimal.valueOf(recapUpahBuruhUc.getAmount())));
            
            recapUpahBuruhUcFacadeLocal.edit(recapUpahBuruhUc);
            recapHandlingUcFacadeLocal.edit(recapHandlingLoad);
            //[/1AM]
        }
    }

    private void calculateContTranshipments(ServiceVessel serviceVessel, MasterCurrency idrMasterCurrency) {
        List<ServiceContTranshipment> serviceContTranshipments = serviceContTranshipmentFacade.findByNoPpkb(serviceVessel.getNoPpkb());
        for (ServiceContTranshipment serviceContTranshipment: serviceContTranshipments) {
            Integer idReefer = serviceReeferFacade.findValidasiPenumpukan(serviceContTranshipment.getNewPpkb(), serviceContTranshipment.getContNo(), ServiceReefer.OPERATION_TRANSHIPMENT);
            
            boolean isOverSize = false;
            if ((!serviceContTranshipment.getContStatus().equals("MTY") && serviceContTranshipment.getOverSize().equals("TRUE"))
                    || serviceContTranshipment.getTwentyOneFeet().equals("TRUE")) {
                isOverSize = true;
            }
            String penumpukanTranshipmentActivityCode = masterActivityFacade.translatePenumpukanActivityCode(
                    serviceContTranshipment.getContStatus(),
                    serviceContTranshipment.getMasterContainerType().getIsoCode(),
                    isOverSize,
                    serviceContTranshipment.getContSize(), idReefer > 0);
            BigDecimal penumpukanTranshipmentCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), penumpukanTranshipmentActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
            
            //hitung perhitungan penumpukan
            PerhitunganPenumpukanTranshipment perhitunganPenumpukanTranshipment = new PerhitunganPenumpukanTranshipment();
            perhitunganPenumpukanTranshipment.setNoPpkb(serviceVessel.getNoPpkb());
            perhitunganPenumpukanTranshipment.setMasa1((short) 1);
            perhitunganPenumpukanTranshipment.setMasa2((short) 0);
            perhitunganPenumpukanTranshipment.setTotalHari(5);
            perhitunganPenumpukanTranshipment.setOperation(DISCHARGE);
            perhitunganPenumpukanTranshipment.setContNo(serviceContTranshipment.getContNo());
            perhitunganPenumpukanTranshipment.setMlo(serviceContTranshipment.getMlo());
            perhitunganPenumpukanTranshipment.setActivityCode(penumpukanTranshipmentActivityCode);
            perhitunganPenumpukanTranshipment.setJasaDermaga(BigDecimal.ZERO);
            perhitunganPenumpukanTranshipment.setCurrCode(idrMasterCurrency.getCurrCode());
            
            if (serviceContTranshipment.getDangerous().equals("TRUE") && serviceContTranshipment.getDgLabel().equals("TRUE")) {
                penumpukanTranshipmentCharge = penumpukanTranshipmentCharge.multiply(BigDecimal.valueOf(2));
            } else if (serviceContTranshipment.getDangerous().equals("TRUE") && serviceContTranshipment.getDgLabel().equals("FALSE")) {
                penumpukanTranshipmentCharge = penumpukanTranshipmentCharge.multiply(BigDecimal.valueOf(3));
            }
            
            perhitunganPenumpukanTranshipment.setCharge(penumpukanTranshipmentCharge);
            perhitunganPenumpukanTranshipment.setChargeM1(perhitunganPenumpukanTranshipment.getCharge().multiply(BigDecimal.valueOf(perhitunganPenumpukanTranshipment.getMasa1())));
            perhitunganPenumpukanTranshipment.setChargeM2(perhitunganPenumpukanTranshipment.getCharge().multiply(BigDecimal.valueOf(perhitunganPenumpukanTranshipment.getMasa2())).multiply(BigDecimal.valueOf(2)));
            perhitunganPenumpukanTranshipment.setTotalCharge(perhitunganPenumpukanTranshipment.getChargeM1().add(perhitunganPenumpukanTranshipment.getChargeM2()).add(perhitunganPenumpukanTranshipment.getJasaDermaga()));
            
            perhitunganPenumpukanTranshipmentFacade.edit(perhitunganPenumpukanTranshipment);
            
        }
    }

    private void calculateUncontainerizedServiceDischargeCyToCy(ServiceVessel serviceVessel, MasterCurrency usdMasterCurrency, MasterCurrency idrMasterCurrency) {
        MasterCurrency masterCurrency;
        if (serviceVessel.getIsDischargeCyToCy()) {
            List<UncontainerizedService> uncontainerizedServices = uncontainerizedServiceFacadeLocal.findDeliveryUCThatHaveReachedCY(serviceVessel.getNoPpkb());
            
            for (UncontainerizedService uncontainerizedService: uncontainerizedServices) {
                //[1AB]
                if (uncontainerizedService.getIsExportImport().equals("TRUE")) {
                    masterCurrency = usdMasterCurrency;
                } else {
                    masterCurrency = idrMasterCurrency;
                }
                
                String handlingUcActivityCode = masterActivityFacade.translateHandlingUcActivityCode(uncontainerizedService.getWeight(), uncontainerizedService.getCrane());
                MasterActivity handlingUcMasterActivity = masterActivityFacade.find(handlingUcActivityCode);
                BigDecimal handlingUcCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), handlingUcActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
                
                RecapHandlingUc recapHandlingDischarge = recapHandlingUcFacadeLocal.findByBentuk3Constraint(
                        serviceVessel.getNoPpkb(),
                        uncontainerizedService.getWeightClassification(),
                        uncontainerizedService.getCrane(),
                        uncontainerizedService.getIsExportImport(),
                        handlingUcActivityCode,
                        masterCurrency.getCurrCode(),
                        RecapHandlingUc.OPERATION_DISCHARGE,
                        RecapHandlingUc.ACTIVITY_DISCHARGE,
                        handlingUcCharge
                );
                
                if (recapHandlingDischarge != null) {
                    recapHandlingDischarge.setAmount(recapHandlingDischarge.getAmount() + 1);
                } else {
                    recapHandlingDischarge = new RecapHandlingUc();
                    recapHandlingDischarge.setServiceVessel(serviceVessel);
                    recapHandlingDischarge.setWeightGroup(uncontainerizedService.getWeightClassification());
                    recapHandlingDischarge.setCrane(uncontainerizedService.getCrane());
                    recapHandlingDischarge.setIsExportImport(uncontainerizedService.getIsExportImport());
                    recapHandlingDischarge.setMasterCurrency(masterCurrency);
                    recapHandlingDischarge.setMasterActivity(handlingUcMasterActivity);
                    recapHandlingDischarge.setOperation(RecapHandlingUc.OPERATION_DISCHARGE);
                    recapHandlingDischarge.setActivity(RecapHandlingUc.ACTIVITY_DISCHARGE);
                    recapHandlingDischarge.setCharge(handlingUcCharge);
                    recapHandlingDischarge.setAmount(1);
                }
                
                recapHandlingDischarge.setTotalCharge(recapHandlingDischarge.getCharge().multiply(BigDecimal.valueOf(recapHandlingDischarge.getAmount())));
                //[1AB]
                
                Double tonage = Double.valueOf(uncontainerizedService.getWeight() / 1000);
                Double volume = uncontainerizedService.getCubication().doubleValue();
                Double factor = tonage > volume ? tonage : volume;
                
                String upahBuruhUcActivityCode = masterActivityFacade.translateUbahBuruhUcActivityCode();
                MasterActivity upahBuruhUcMasterActivity = masterActivityFacade.find(upahBuruhUcActivityCode);
                BigDecimal upahBuruhUcCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), upahBuruhUcActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
                RecapUpahBuruhUc recapUpahBuruhUc = recapUpahBuruhUcFacadeLocal.findByBentuk3Constraint(
                        serviceVessel.getNoPpkb(),
                        uncontainerizedService.getWeightClassification(),
                        uncontainerizedService.getCrane(),
                        uncontainerizedService.getIsExportImport(),
                        upahBuruhUcActivityCode,
                        idrMasterCurrency.getCurrCode(),
                        RecapUpahBuruh.OPERATION_DISCHARGE,
                        RecapUpahBuruh.ACTIVITY_DISCHARGE,
                        upahBuruhUcCharge.multiply(BigDecimal.valueOf(factor))
                );
                
                if (recapUpahBuruhUc != null) {
                    recapUpahBuruhUc.setAmount(recapUpahBuruhUc.getAmount() + 1);
                } else {
                    recapUpahBuruhUc = new RecapUpahBuruhUc();
                    recapUpahBuruhUc.setServiceVessel(serviceVessel);
                    recapUpahBuruhUc.setWeightGroup(uncontainerizedService.getWeightClassification());
                    recapUpahBuruhUc.setCrane(uncontainerizedService.getCrane());
                    recapUpahBuruhUc.setIsExportImport(uncontainerizedService.getIsExportImport());
                    recapUpahBuruhUc.setMasterCurrency(idrMasterCurrency);
                    recapUpahBuruhUc.setMasterActivity(upahBuruhUcMasterActivity);
                    recapUpahBuruhUc.setOperation(RecapUpahBuruh.OPERATION_DISCHARGE);
                    recapUpahBuruhUc.setActivity(RecapUpahBuruh.ACTIVITY_DISCHARGE);
                    recapUpahBuruhUc.setCharge(upahBuruhUcCharge.multiply(BigDecimal.valueOf(factor)));
                    recapUpahBuruhUc.setAmount(1);
                }
                
                recapUpahBuruhUc.setTotalCharge(recapUpahBuruhUc.getCharge().multiply(BigDecimal.valueOf(recapUpahBuruhUc.getAmount())));
                
                //[3AB]
                String penumpukanUcActivityCode = masterActivityFacade.translatePenumpukanUcActivityCode();
                MasterActivity masterActivity = masterActivityFacade.find(penumpukanUcActivityCode);
                BigDecimal penumpukanUcCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), penumpukanUcActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
                
                RecapPenumpukanUc recapPenumpukanUc = new RecapPenumpukanUc();
                recapPenumpukanUc.setTotalHari(5);
                recapPenumpukanUc.setBlNo(uncontainerizedService.getBlNo());
                recapPenumpukanUc.setMasa1((short) 1);
                recapPenumpukanUc.setMasa2((short) 0);
                recapPenumpukanUc.setMasterActivity(masterActivity);
                recapPenumpukanUc.setOperation(RecapPenumpukanUc.OPERATION_DISCHARGE);
                recapPenumpukanUc.setActivity(RecapPenumpukanUc.ACTIVITY_DISCHARGE);
                recapPenumpukanUc.setMasterCurrency(idrMasterCurrency);
                recapPenumpukanUc.setServiceVessel(serviceVessel);
                
//                    Double tonage = Double.valueOf(uncontainerizedService.getWeight() / 1000);
//                    Double volume = uncontainerizedService.getCubication().doubleValue();
//                    Double factor = tonage > volume ? tonage : volume;
                
                recapPenumpukanUc.setJasaDermaga(BigDecimal.ZERO);
                recapPenumpukanUc.setChargeMasa1(penumpukanUcCharge.multiply(BigDecimal.valueOf(recapPenumpukanUc.getMasa1())).multiply(BigDecimal.valueOf(factor)));
                recapPenumpukanUc.setChargeMasa2(penumpukanUcCharge.multiply(BigDecimal.valueOf(recapPenumpukanUc.getMasa2())).multiply(BigDecimal.valueOf(factor)).multiply(BigDecimal.valueOf(2)));
                recapPenumpukanUc.setTotalCharge(recapPenumpukanUc.getChargeMasa1().add(recapPenumpukanUc.getChargeMasa2()).add(recapPenumpukanUc.getJasaDermaga()));
                //[/3AB]
                
                recapPenumpukanUcFacadeLocal.edit(recapPenumpukanUc);
                recapHandlingUcFacadeLocal.edit(recapHandlingDischarge);
                recapUpahBuruhUcFacadeLocal.edit(recapUpahBuruhUc);
            }
        }
    }

    private void calculateContainerDischarge(ServiceVessel serviceVessel, MasterCurrency usdMasterCurrency, MasterCurrency idrMasterCurrency) throws RuntimeException {
        MasterCurrency masterCurrency;
        // update service cont discharge
        List<ServiceContDischarge> serviceContDischargeList = serviceContDischargeFacade.findContainersThatHaveReachedCY(serviceVessel.getNoPpkb());
        boolean isSeling = false;
        for (ServiceContDischarge serviceContDischarge : serviceContDischargeList) {
            if(serviceContDischarge.getIsSeling() != null && serviceContDischarge.getIsSeling().equals("TRUE")) {
                serviceContDischarge.setIsSeling("TRUE");
            } else {
                serviceContDischarge.setIsSeling("FALSE");
            }
            
            if (serviceContDischarge.getIsImport().equals("TRUE")) {
                masterCurrency = usdMasterCurrency;
            } else {
                masterCurrency = idrMasterCurrency;
            }
            
            if (serviceContDischarge.getIsSeling().equals("TRUE")) {
                isSeling = true;
            }
            String dangerousClass = null;
            if(serviceContDischarge.getMasterCommodity() != null && serviceContDischarge.getMasterCommodity().getMasterDangerousClass() != null)
                dangerousClass = serviceContDischarge.getMasterCommodity().getMasterDangerousClass().getDangerousClass();
            
//                String stevedoringActivityCode = masterActivityFacade.translateStevedoringActivityCode(
//                        serviceContDischarge.getContStatus(),
//                        serviceContDischarge.getContSize(),
//                        serviceContDischarge.getCrane(),
//                        isSeling,
//                        serviceContDischarge.getEquipmentGroup(),
//                        serviceContDischarge.getDangerous().equals("TRUE"),
//                        serviceContDischarge.getDgLabel().equals("TRUE"),
//                        dangerousClass,
//                        isSeling,
//                        serviceContDischarge.getTwentyOneFeet().equals("TRUE"));
//
            String stevedoringActivityCode = masterActivityFacade.translateHandlingActivityCode(serviceContDischarge.getContStatus(),
                    serviceContDischarge.getContSize(), serviceContDischarge.getCrane());
            
            if(stevedoringActivityCode == null){
                throw new RuntimeException("Cannot Find Activity Code");
            }
            
            MasterActivity handlingDischargeMasterActivity = masterActivityFacade.find(stevedoringActivityCode);
            serviceContDischarge.setMasterActivity(handlingDischargeMasterActivity);
            serviceContDischargeFacade.edit(serviceContDischarge);
            
//                String htActivityCode = masterActivityFacade.translateHaulageTruckingActivityCode(
//                        serviceContDischarge.getContStatus(),
//                        serviceContDischarge.getContSize(),
//                        serviceContDischarge.getCrane(),
//                        isSeling,
//                        serviceContDischarge.getEquipmentGroup(),
//                        serviceContDischarge.getDangerous().equals("TRUE"),
//                        serviceContDischarge.getDgLabel().equals("TRUE"),
//                        dangerousClass,
//                        isSeling,
//                        serviceContDischarge.getTwentyOneFeet().equals("TRUE"));
//
//               if(htActivityCode == null){
//                   throw new RuntimeException("Cannot Find Activity Code");
//               }
//                String otherHandlingActivityCode = masterActivityFacade.translateOtherHandlingChargesActivityCode(
//                        serviceContDischarge.getContStatus(),
//                        serviceContDischarge.getContSize(),
//                        serviceContDischarge.getCrane(),
//                        isSeling,
//                        serviceContDischarge.getEquipmentGroup(),
//                        serviceContDischarge.getDangerous().equals("TRUE"),
//                        serviceContDischarge.getDgLabel().equals("TRUE"),
//                        dangerousClass,
//                        isSeling,
//                        serviceContDischarge.getTwentyOneFeet().equals("TRUE"));
//               if(otherHandlingActivityCode == null){
//                   throw new RuntimeException("Cannot Find Activity Code");
//               }
            
            
            if (serviceVessel.getIsDischargePortToPort()) {
                continue;
            }
            
//                boolean isOverSize = false;
//                isSeling = false;
//                if(serviceContDischarge.getOverSize().equals("TRUE") ) {
//                    isOverSize = true;
//                }
//                if (serviceContDischarge.getIsSeling().equals("TRUE")) {
//                    isSeling = true;
//                }
            
//                String upahBuruhActivityCode = masterActivityFacade.translateUbahBuruhHandlingActivityCode(
//                    serviceContDischarge.getContStatus(),
//                    serviceContDischarge.getContSize(),
//                    isOverSize || isSeling);
//
//                MasterActivity upahBuruhMasterActivity = masterActivityFacade.find(upahBuruhActivityCode);
//                BigDecimal upahBuruhCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), upahBuruhActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
//                RecapUpahBuruh recapUpahBuruh = recapUpahBuruhFacadeLocal.findByBentuk3Constraint(
//                        serviceContDischarge.getContSize(),
//                        serviceContDischarge.getContStatus(),
//                        serviceContDischarge.getCrane(),
//                        serviceContDischarge.getIsImport(),
//                        serviceContDischarge.getOverSize(),
//                        serviceContDischarge.getIsSeling(),
//                        serviceContDischarge.getMasterContainerType().getMasterContainerTypeInGeneral().getId(),
//                        upahBuruhActivityCode,
//                        RecapUpahBuruh.OPERATION_DISCHARGE,
//                        RecapUpahBuruh.ACTIVITY_DISCHARGE,
//                        serviceVessel.getNoPpkb(),
//                        idrMasterCurrency.getCurrCode(),
//                        serviceContDischarge.getTwentyOneFeet(),
//                        upahBuruhCharge);
//
//                if (recapUpahBuruh != null) {
//                    recapUpahBuruh.setAmount(recapUpahBuruh.getAmount() + 1);
//                } else {
//                    recapUpahBuruh = new RecapUpahBuruh();
//                    recapUpahBuruh.setIsExportImport(serviceContDischarge.getIsImport());
//                    recapUpahBuruh.setSling(serviceContDischarge.getIsSeling());
//                    recapUpahBuruh.setCrane(serviceContDischarge.getCrane());
//                    recapUpahBuruh.setContStatus(serviceContDischarge.getContStatus());
//                    recapUpahBuruh.setContSize(serviceContDischarge.getContSize());
//                    recapUpahBuruh.setTwentyOneFeet(serviceContDischarge.getTwentyOneFeet());
//                    recapUpahBuruh.setOpenDoor(serviceContDischarge.getOverSize());
//                    recapUpahBuruh.setContainerType(serviceContDischarge.getMasterContainerType().getMasterContainerTypeInGeneral());
//                    recapUpahBuruh.setOperation(RecapUpahBuruh.OPERATION_DISCHARGE);
//                    recapUpahBuruh.setActivity(RecapUpahBuruh.ACTIVITY_DISCHARGE);
//                    recapUpahBuruh.setServiceVessel(serviceVessel);
//                    recapUpahBuruh.setMasterActivity(upahBuruhMasterActivity);
//                    recapUpahBuruh.setCharge(upahBuruhCharge);
//                    recapUpahBuruh.setAmount(1);
//                    recapUpahBuruh.setMasterCurrency(idrMasterCurrency);
//                }
//
//                recapUpahBuruh.setTotalCharge(recapUpahBuruh.getCharge().multiply(BigDecimal.valueOf(recapUpahBuruh.getAmount())));
            
            //[1XB]
            BigDecimal stevedoringCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), stevedoringActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
            
            RecapHandlingDischarge recapStevedoringDischarge = new RecapHandlingDischarge();
            recapStevedoringDischarge.setContNo(serviceContDischarge.getContNo());
            recapStevedoringDischarge.setCrane(serviceContDischarge.getCrane());
            recapStevedoringDischarge.setContSize(serviceContDischarge.getContSize());
            recapStevedoringDischarge.setContStatus(serviceContDischarge.getContStatus());
            recapStevedoringDischarge.setTwentyOneFeet(serviceContDischarge.getTwentyOneFeet());
            recapStevedoringDischarge.setIsExportImport(serviceContDischarge.getIsImport());
            recapStevedoringDischarge.setSling(serviceContDischarge.getIsSeling());
            recapStevedoringDischarge.setOpenDoor(serviceContDischarge.getOverSize());
            recapStevedoringDischarge.setContainerType(serviceContDischarge.getMasterContainerType().getMasterContainerTypeInGeneral());
            recapStevedoringDischarge.setNoPpkb(serviceVessel.getNoPpkb());
            recapStevedoringDischarge.setActivityCode(stevedoringActivityCode);
            recapStevedoringDischarge.setCharge(stevedoringCharge);
            recapStevedoringDischarge.setAmount(1);
            recapStevedoringDischarge.setCurrCode(masterCurrency.getCurrCode());
            
            
            recapStevedoringDischarge.setTotalCharge(recapStevedoringDischarge.getCharge().multiply(BigDecimal.valueOf(recapStevedoringDischarge.getAmount())));
            //[/1XB]
            
//                BigDecimal htCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), htActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
            
            
//                    RecapHandlingDischarge recapHtDischarge = new RecapHandlingDischarge();
//                    recapHtDischarge.setContNo(serviceContDischarge.getContNo());
//                    recapHtDischarge.setCrane(serviceContDischarge.getCrane());
//                    recapHtDischarge.setContSize(serviceContDischarge.getContSize());
//                    recapHtDischarge.setContStatus(serviceContDischarge.getContStatus());
//                    recapHtDischarge.setTwentyOneFeet(serviceContDischarge.getTwentyOneFeet());
//                    recapHtDischarge.setIsExportImport(serviceContDischarge.getIsImport());
//                    recapHtDischarge.setSling(serviceContDischarge.getIsSeling());
//                    recapHtDischarge.setOpenDoor(serviceContDischarge.getOverSize());
//                    recapHtDischarge.setContainerType(serviceContDischarge.getMasterContainerType().getMasterContainerTypeInGeneral());
//                    recapHtDischarge.setNoPpkb(serviceVessel.getNoPpkb());
//                    recapHtDischarge.setActivityCode(htActivityCode);
//                    recapHtDischarge.setCharge(htCharge);
//                    recapHtDischarge.setAmount(1);
//                    recapHtDischarge.setCurrCode(masterCurrency.getCurrCode());
            
            
//                recapHtDischarge.setTotalCharge(recapHtDischarge.getCharge().multiply(BigDecimal.valueOf(recapHtDischarge.getAmount())));
//
//                BigDecimal otherHandlingCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(),
//                        otherHandlingActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
//
//                    RecapHandlingDischarge recapOtherHandlingDischarge = new RecapHandlingDischarge();
//                    recapOtherHandlingDischarge.setContNo(serviceContDischarge.getContNo());
//                    recapOtherHandlingDischarge.setCrane(serviceContDischarge.getCrane());
//                    recapOtherHandlingDischarge.setContSize(serviceContDischarge.getContSize());
//                    recapOtherHandlingDischarge.setContStatus(serviceContDischarge.getContStatus());
//                    recapOtherHandlingDischarge.setTwentyOneFeet(serviceContDischarge.getTwentyOneFeet());
//                    recapOtherHandlingDischarge.setIsExportImport(serviceContDischarge.getIsImport());
//                    recapOtherHandlingDischarge.setSling(serviceContDischarge.getIsSeling());
//                    recapOtherHandlingDischarge.setOpenDoor(serviceContDischarge.getOverSize());
//                    recapOtherHandlingDischarge.setContainerType(serviceContDischarge.getMasterContainerType().getMasterContainerTypeInGeneral());
//                    recapOtherHandlingDischarge.setNoPpkb(serviceVessel.getNoPpkb());
//                    recapOtherHandlingDischarge.setActivityCode(otherHandlingActivityCode);
//                    recapOtherHandlingDischarge.setCharge(otherHandlingCharge);
//                    recapOtherHandlingDischarge.setAmount(1);
//                    recapOtherHandlingDischarge.setCurrCode(masterCurrency.getCurrCode());
//
//
//                recapOtherHandlingDischarge.setTotalCharge(
//                        recapOtherHandlingDischarge.getCharge().multiply(BigDecimal.valueOf(recapOtherHandlingDischarge.getAmount())));
            
            
            //[3XB]
//                Integer idReefer = serviceReeferFacade.findValidasiPenumpukan(serviceVessel.getNoPpkb(), serviceContDischarge.getContNo(), ServiceReefer.OPERATION_DISCHARGE);
//                isOverSize = false;
//                if ((!serviceContDischarge.getContStatus().equals("MTY") && serviceContDischarge.getOverSize().equals("TRUE"))
//                        || serviceContDischarge.getTwentyOneFeet().equals("TRUE")) {
//                    isOverSize = true;
//                }
            
            if(serviceContDischarge.getContStatus().equals("fcl")){
                serviceContDischarge.setContStatus("FCL");
            }
//                String penumpukanActivityCode = masterActivityFacade.translatePenumpukanActivityCode(
//                        serviceContDischarge.getContStatus(),
//                        serviceContDischarge.getMasterContainerType().getIsoCode(),
//                        isOverSize,
//                        serviceContDischarge.getContSize(), idReefer > 0);
//                System.out.println("Test -----"+serviceContDischarge.getContStatus()+"+"+
//                        serviceContDischarge.getMasterContainerType().getIsoCode()+"+"+
//                        isOverSize+"+"+ serviceContDischarge.getContSize()+"+"+ idReefer);
//
//                System.out.println("Testku 2 :> "+penumpukanActivityCode);
//                RecapPenumpukan recapPenumpukan = new RecapPenumpukan();
//                recapPenumpukan.setContNo(serviceContDischarge.getContNo());
//                recapPenumpukan.setMlo(serviceContDischarge.getMlo());
//                recapPenumpukan.setNoPpkb(serviceVessel.getNoPpkb());
//                recapPenumpukan.setTotalHari(5);
//                recapPenumpukan.setMasa1((short) 1);
//                recapPenumpukan.setMasa2((short) 0);
//                recapPenumpukan.setActivityCode(penumpukanActivityCode);
//                recapPenumpukan.setCurrCode(idrMasterCurrency.getCurrCode());
//
//                BigDecimal penumpukanCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), penumpukanActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
//                if (serviceContDischarge.getDangerous().equals("TRUE") && serviceContDischarge.getDgLabel().equals("TRUE")) {
//                    penumpukanCharge = penumpukanCharge.multiply(BigDecimal.valueOf(2));
//                } else if (serviceContDischarge.getDangerous().equals("TRUE") && serviceContDischarge.getDgLabel().equals("FALSE")) {
//                    penumpukanCharge = penumpukanCharge.multiply(BigDecimal.valueOf(3));
//                }
            
//                recapPenumpukan.setChargeMasa1(penumpukanCharge.multiply(new BigDecimal(recapPenumpukan.getMasa1())));
//                recapPenumpukan.setChargeMasa2(BigDecimal.ZERO);
//                recapPenumpukan.setOperation(RecapPenumpukan.DISCHARGE);
//                recapPenumpukan.setJasaDermaga(BigDecimal.ZERO);
//                recapPenumpukan.setTotalCharge(recapPenumpukan.getChargeMasa1().add(recapPenumpukan.getJasaDermaga()));
            //[/3XB]
            
//                recapPenumpukanFacade.edit(recapPenumpukan);
            recapHandlingDischargeFacade.edit(recapStevedoringDischarge);
//                recapHandlingDischargeFacade.edit(recapHtDischarge);
//                recapHandlingDischargeFacade.edit(recapOtherHandlingDischarge);
//                recapUpahBuruhFacadeLocal.edit(recapUpahBuruh);
            
//                Jasa PBM CC untuk Tarakan
            String mainActivity = "DISCHARGE";
            String tarifType = "PBM";
            String jasaPBMActivityCode = masterActivityFacade.translateOtherPackageActivityCode(tarifType, serviceContDischarge.getContStatus(),
                    serviceContDischarge.getContSize(), serviceContDischarge.getCrane());
            
            if(jasaPBMActivityCode == null){
                throw new RuntimeException("Cannot Find Activity Code for Jasa PBM");
            }
            
            createOtherPackageRecap(mainActivity, tarifType, masterCurrency, serviceVessel, serviceContDischarge, jasaPBMActivityCode);
            
//                Angsur
            Equipment equipment = equipmentFacadeLocal.findByEquipmentActCodeAndOperation(serviceContDischarge.getNoPpkb(), serviceContDischarge.getContNo(), "LIFTOFF", "DISCHARGE");
            if (equipment==null)
                throw new RuntimeException("Cannot find Equipment");
            
            if ("TRUE".equals(equipment.getMasterEquipment().getOwner()))
                tarifType = "ANGSUR";
            else
                tarifType = "ANGSURPLYR";
            
            String angsurActivityCode = masterActivityFacade.translateOtherPackageActivityCode(tarifType, serviceContDischarge.getContStatus(),
                    serviceContDischarge.getContSize(), "ALLTYPE");
            
            if(angsurActivityCode == null){
                throw new RuntimeException("Cannot Find Activity Code for Angsur");
            }
            
            createOtherPackageRecap(mainActivity, tarifType, masterCurrency, serviceVessel, serviceContDischarge, angsurActivityCode);
            
//            Stripping
            tarifType = "STRIPPING";
            String strippingActivityCode = masterActivityFacade.translateOtherPackageActivityCode(tarifType, serviceContDischarge.getContStatus(),
                    serviceContDischarge.getContSize(), "ALLTYPE");
            
            if(strippingActivityCode == null){
                throw new RuntimeException("Cannot Find Activity Code for Stripping");
            }
            createOtherPackageRecap(mainActivity, tarifType, masterCurrency, serviceVessel, serviceContDischarge, strippingActivityCode);
            
        }
    }

    private void calculateUncontainerizedTranshipment(ServiceVessel serviceVessel, MasterCurrency usdMasterCurrency, MasterCurrency idrMasterCurrency, int masa1UcRange, int masaFreeRange) {
        MasterCurrency masterCurrency;
        //update rekap transhipment buat uncontainerized
        List<UncontainerizedService> ucUncontainerizedList = uncontainerizedServiceFacadeLocal.findTranshipmentUCThatHaveReachedCY(serviceVessel.getNoPpkb());
        for (UncontainerizedService uncontainerizedService : ucUncontainerizedList) {
            if (uncontainerizedService.getIsExportImport().equals("TRUE")) {
                masterCurrency = usdMasterCurrency;
            } else {
                masterCurrency = idrMasterCurrency;
            }
            
            String transhipmentUcActivityCode = masterActivityFacade.translateTranshipmentUcActivityCode(uncontainerizedService.getWeight(), uncontainerizedService.getCrane());
            BigDecimal transhipmentUcCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), transhipmentUcActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
            MasterActivity transhipmentUcMasterActivity = masterActivityFacade.find(transhipmentUcActivityCode);
            
            RecapHandlingUc transhipmentUc = recapHandlingUcFacadeLocal.findByBentuk3Constraint(
                    serviceVessel.getNoPpkb(),
                    uncontainerizedService.getWeightClassification(),
                    uncontainerizedService.getCrane(),
                    uncontainerizedService.getIsExportImport(),
                    transhipmentUcActivityCode,
                    masterCurrency.getCurrCode(),
                    RecapHandlingUc.OPERATION_DISCHARGE,
                    RecapHandlingUc.ACTIVITY_TRANSHIPMENT,
                    transhipmentUcCharge
            );
            
            if (transhipmentUc != null) {
                transhipmentUc.setAmount(transhipmentUc.getAmount() + 1);
            } else {
                transhipmentUc = new RecapHandlingUc();
                transhipmentUc.setServiceVessel(serviceVessel);
                transhipmentUc.setWeightGroup(uncontainerizedService.getWeightClassification());
                transhipmentUc.setCrane(uncontainerizedService.getCrane());
                transhipmentUc.setIsExportImport(uncontainerizedService.getIsExportImport());
                transhipmentUc.setMasterCurrency(masterCurrency);
                transhipmentUc.setMasterActivity(transhipmentUcMasterActivity);
                transhipmentUc.setOperation(RecapHandlingUc.OPERATION_DISCHARGE);
                transhipmentUc.setActivity(RecapHandlingUc.ACTIVITY_TRANSHIPMENT);
                transhipmentUc.setCharge(transhipmentUcCharge);
                transhipmentUc.setAmount(1);
            }
            
            transhipmentUc.setTotalCharge(transhipmentUc.getCharge().multiply(BigDecimal.valueOf(transhipmentUc.getAmount())));
            
            Double tonage = Double.valueOf(uncontainerizedService.getWeight() / 1000);
            Double volume = uncontainerizedService.getCubication().doubleValue();
            Double factor = tonage > volume ? tonage : volume;
            
            String upahBuruhUcActivityCode = masterActivityFacade.translateUbahBuruhUcActivityCode();
            MasterActivity upahBuruhUcMasterActivity = masterActivityFacade.find(upahBuruhUcActivityCode);
            BigDecimal upahBuruhUcCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), upahBuruhUcActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
            RecapUpahBuruhUc recapUpahBuruhUc = recapUpahBuruhUcFacadeLocal.findByBentuk3Constraint(
                    serviceVessel.getNoPpkb(),
                    uncontainerizedService.getWeightClassification(),
                    uncontainerizedService.getCrane(),
                    uncontainerizedService.getIsExportImport(),
                    upahBuruhUcActivityCode,
                    idrMasterCurrency.getCurrCode(),
                    RecapUpahBuruh.OPERATION_DISCHARGE,
                    RecapUpahBuruh.ACTIVITY_TRANSHIPMENT,
                    upahBuruhUcCharge.multiply(BigDecimal.valueOf(factor))
            );
            
            if (recapUpahBuruhUc != null) {
                recapUpahBuruhUc.setAmount(recapUpahBuruhUc.getAmount() + 1);
            } else {
                recapUpahBuruhUc = new RecapUpahBuruhUc();
                recapUpahBuruhUc.setServiceVessel(serviceVessel);
                recapUpahBuruhUc.setWeightGroup(uncontainerizedService.getWeightClassification());
                recapUpahBuruhUc.setCrane(uncontainerizedService.getCrane());
                recapUpahBuruhUc.setIsExportImport(uncontainerizedService.getIsExportImport());
                recapUpahBuruhUc.setMasterCurrency(idrMasterCurrency);
                recapUpahBuruhUc.setMasterActivity(upahBuruhUcMasterActivity);
                recapUpahBuruhUc.setOperation(RecapUpahBuruh.OPERATION_DISCHARGE);
                recapUpahBuruhUc.setActivity(RecapUpahBuruh.ACTIVITY_TRANSHIPMENT);
                recapUpahBuruhUc.setCharge(upahBuruhUcCharge.multiply(BigDecimal.valueOf(factor)));
                recapUpahBuruhUc.setAmount(1);
            }
            
            recapUpahBuruhUc.setTotalCharge(recapUpahBuruhUc.getCharge().multiply(BigDecimal.valueOf(recapUpahBuruhUc.getAmount())));
            
            String penumpukanUcActivityCode = masterActivityFacade.translatePenumpukanUcActivityCode();
            MasterActivity penumpukanUcMasterActivity = masterActivityFacade.find(penumpukanUcActivityCode);
            BigDecimal penumpukanUcCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), penumpukanUcActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
            
            RecapPenumpukanUc recapPenumpukanUc = new RecapPenumpukanUc();
            recapPenumpukanUc.setTotalHari(5);
            recapPenumpukanUc.setBlNo(uncontainerizedService.getBlNo());
            recapPenumpukanUc.setMasa1((short) 1);
            recapPenumpukanUc.setMasa2((short) 0);
            recapPenumpukanUc.setMasterActivity(penumpukanUcMasterActivity);
            recapPenumpukanUc.setOperation(RecapPenumpukanUc.OPERATION_DISCHARGE);
            recapPenumpukanUc.setActivity(RecapPenumpukanUc.ACTIVITY_TRANSHIPMENT);
            recapPenumpukanUc.setMasterCurrency(idrMasterCurrency);
            recapPenumpukanUc.setServiceVessel(serviceVessel);
            
//                Double tonage = Double.valueOf(uncontainerizedService.getWeight() / 1000);
//                Double volume = uncontainerizedService.getCubication().doubleValue();
//                Double factor = tonage > volume ? tonage : volume;
            
            recapPenumpukanUc.setJasaDermaga(BigDecimal.ZERO);
            recapPenumpukanUc.setChargeMasa1(penumpukanUcCharge.multiply(BigDecimal.valueOf(recapPenumpukanUc.getMasa1())).multiply(BigDecimal.valueOf(factor)));
            recapPenumpukanUc.setChargeMasa2(penumpukanUcCharge.multiply(BigDecimal.valueOf(recapPenumpukanUc.getMasa2())).multiply(BigDecimal.valueOf(factor)).multiply(BigDecimal.valueOf(2)));
            recapPenumpukanUc.setTotalCharge(recapPenumpukanUc.getChargeMasa1().add(recapPenumpukanUc.getChargeMasa2()).add(recapPenumpukanUc.getJasaDermaga()));
            
            recapPenumpukanUcFacadeLocal.edit(recapPenumpukanUc);
            recapUpahBuruhUcFacadeLocal.edit(recapUpahBuruhUc);
            recapHandlingUcFacadeLocal.edit(transhipmentUc);
        }
        List<UncontainerizedService> transLoadUcs = uncontainerizedServiceFacadeLocal.findTranshipmentUCThatHadBeenLoaded(serviceVessel.getNoPpkb());
        for (UncontainerizedService uncontainerizedService : transLoadUcs) {
            if (uncontainerizedService.getIsExportImport().equals("TRUE")) {
                masterCurrency = usdMasterCurrency;
            } else {
                masterCurrency = idrMasterCurrency;
            }
            
            Double tonage = Double.valueOf(uncontainerizedService.getWeight() / 1000);
            Double volume = uncontainerizedService.getCubication().doubleValue();
            Double factor = tonage > volume ? tonage : volume;
            
            String upahBuruhUcActivityCode = masterActivityFacade.translateUbahBuruhUcActivityCode();
            MasterActivity upahBuruhUcMasterActivity = masterActivityFacade.find(upahBuruhUcActivityCode);
            BigDecimal upahBuruhUcCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), upahBuruhUcActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
            RecapUpahBuruhUc recapUpahBuruhUc = recapUpahBuruhUcFacadeLocal.findByBentuk3Constraint(
                    serviceVessel.getNoPpkb(),
                    uncontainerizedService.getWeightClassification(),
                    uncontainerizedService.getCrane(),
                    uncontainerizedService.getIsExportImport(),
                    upahBuruhUcActivityCode,
                    idrMasterCurrency.getCurrCode(),
                    RecapUpahBuruh.OPERATION_DISCHARGE,
                    RecapUpahBuruh.ACTIVITY_TRANSHIPMENT,
                    upahBuruhUcCharge.multiply(BigDecimal.valueOf(factor))
            );
            
            if (recapUpahBuruhUc != null) {
                recapUpahBuruhUc.setAmount(recapUpahBuruhUc.getAmount() + 1);
            } else {
                recapUpahBuruhUc = new RecapUpahBuruhUc();
                recapUpahBuruhUc.setServiceVessel(serviceVessel);
                recapUpahBuruhUc.setWeightGroup(uncontainerizedService.getWeightClassification());
                recapUpahBuruhUc.setCrane(uncontainerizedService.getCrane());
                recapUpahBuruhUc.setIsExportImport(uncontainerizedService.getIsExportImport());
                recapUpahBuruhUc.setMasterCurrency(idrMasterCurrency);
                recapUpahBuruhUc.setMasterActivity(upahBuruhUcMasterActivity);
                recapUpahBuruhUc.setOperation(RecapUpahBuruh.OPERATION_LOAD);
                recapUpahBuruhUc.setActivity(RecapUpahBuruh.ACTIVITY_TRANSHIPMENT);
                recapUpahBuruhUc.setCharge(upahBuruhUcCharge.multiply(BigDecimal.valueOf(factor)));
                recapUpahBuruhUc.setAmount(1);
            }
            
            recapUpahBuruhUc.setTotalCharge(recapUpahBuruhUc.getCharge().multiply(BigDecimal.valueOf(recapUpahBuruhUc.getAmount())));
            recapUpahBuruhUcFacadeLocal.edit(recapUpahBuruhUc);
            
            Equipment liftOn  = equipmentFacadeLocal.findByPpkbBLNoEquipmentActCodeAndOperation(uncontainerizedService.getNoPpkb(), uncontainerizedService.getBlNo(), "LIFTON", "TRANSLOADUC");
            
            if (liftOn == null) {
                continue;
            }
            
            int min = DateCalculator.countRangeInDays(liftOn.getStartTime(), uncontainerizedService.getPlacementDate()) + 1;
            int masa2 = 0;
            int masa1 = 0;
            
            if (min > 14) {
                String handlingUcActivityCode = masterActivityFacade.translateHandlingUcActivityCode(uncontainerizedService.getWeight(), uncontainerizedService.getCrane());
                BigDecimal handlingUcCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), handlingUcActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
                MasterActivity handlingUcMasterActivity = masterActivityFacade.find(upahBuruhUcActivityCode);
                RecapHandlingUc recapHandlingLoad = recapHandlingUcFacadeLocal.findByBentuk3Constraint(
                        serviceVessel.getNoPpkb(),
                        uncontainerizedService.getWeightClassification(),
                        uncontainerizedService.getCrane(),
                        uncontainerizedService.getIsExportImport(),
                        handlingUcActivityCode,
                        masterCurrency.getCurrCode(),
                        RecapHandlingUc.OPERATION_LOAD,
                        RecapHandlingUc.ACTIVITY_LOAD,
                        handlingUcCharge
                );
                
                if (recapHandlingLoad != null) {
                    recapHandlingLoad.setAmount(recapHandlingLoad.getAmount() + 1);
                } else {
                    recapHandlingLoad = new RecapHandlingUc();
                    recapHandlingLoad.setServiceVessel(serviceVessel);
                    recapHandlingLoad.setWeightGroup(uncontainerizedService.getWeightClassification());
                    recapHandlingLoad.setCrane(uncontainerizedService.getCrane());
                    recapHandlingLoad.setIsExportImport(uncontainerizedService.getIsExportImport());
                    recapHandlingLoad.setMasterCurrency(masterCurrency);
                    recapHandlingLoad.setMasterActivity(handlingUcMasterActivity);
                    recapHandlingLoad.setOperation(RecapHandlingUc.OPERATION_LOAD);
                    recapHandlingLoad.setActivity(RecapHandlingUc.ACTIVITY_LOAD);
                    recapHandlingLoad.setCharge(handlingUcCharge);
                    recapHandlingLoad.setAmount(1);
                }
                
                recapHandlingLoad.setTotalCharge(recapHandlingLoad.getCharge().multiply(BigDecimal.valueOf(recapHandlingLoad.getAmount())));
                
                recapHandlingUcFacadeLocal.edit(recapHandlingLoad);
            } else {
                String transhipmentUcActivityCode = masterActivityFacade.translateTranshipmentUcActivityCode(uncontainerizedService.getWeight(), uncontainerizedService.getCrane());
                BigDecimal transhipmentUcCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), transhipmentUcActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
                MasterActivity transhipmentUcMasterActivity = masterActivityFacade.find(transhipmentUcActivityCode);
                
                RecapHandlingUc transhipmentUc = recapHandlingUcFacadeLocal.findByBentuk3Constraint(
                        serviceVessel.getNoPpkb(),
                        uncontainerizedService.getWeightClassification(),
                        uncontainerizedService.getCrane(),
                        uncontainerizedService.getIsExportImport(),
                        transhipmentUcActivityCode,
                        masterCurrency.getCurrCode(),
                        RecapHandlingUc.OPERATION_LOAD,
                        RecapHandlingUc.ACTIVITY_TRANSHIPMENT,
                        transhipmentUcCharge
                );
                
                if (transhipmentUc != null) {
                    transhipmentUc.setAmount(transhipmentUc.getAmount() + 1);
                } else {
                    transhipmentUc = new RecapHandlingUc();
                    transhipmentUc.setServiceVessel(serviceVessel);
                    transhipmentUc.setWeightGroup(uncontainerizedService.getWeightClassification());
                    transhipmentUc.setCrane(uncontainerizedService.getCrane());
                    transhipmentUc.setIsExportImport(uncontainerizedService.getIsExportImport());
                    transhipmentUc.setMasterCurrency(masterCurrency);
                    transhipmentUc.setMasterActivity(transhipmentUcMasterActivity);
                    transhipmentUc.setOperation(RecapHandlingUc.OPERATION_LOAD);
                    transhipmentUc.setActivity(RecapHandlingUc.ACTIVITY_TRANSHIPMENT);
                    transhipmentUc.setCharge(transhipmentUcCharge);
                    transhipmentUc.setAmount(1);
                }
                
                transhipmentUc.setTotalCharge(transhipmentUc.getCharge().multiply(BigDecimal.valueOf(transhipmentUc.getAmount())));
                recapHandlingUcFacadeLocal.edit(transhipmentUc);
                continue;
            }
            
            int masaFree = 0;
            
            if (min < (masa1UcRange + 1)) {
                if (min <= masaFreeRange) {
                    masa1 = masaFree;
                } else {
                    masa1 = min - masaFreeRange + masaFree;
                }
            } else {
                masa1 = masa1UcRange - masaFreeRange + masaFree;
                masa2 = min - masa1UcRange;
            }
            
            if (masa1 == 0) {
                continue;
            }
            
            String penumpukanUcActivityCode = masterActivityFacade.translatePenumpukanUcActivityCode();
            MasterActivity penumpukanUcMasterActivity = masterActivityFacade.find(penumpukanUcActivityCode);
            BigDecimal penumpukanUcCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), penumpukanUcActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
            
            RecapPenumpukanUc recapPenumpukanUc = new RecapPenumpukanUc();
            recapPenumpukanUc.setTotalHari(5);
            recapPenumpukanUc.setBlNo(uncontainerizedService.getBlNo());
            recapPenumpukanUc.setMasa1((short) (masa1 - 1));
            recapPenumpukanUc.setMasa2((short) masa2);
            recapPenumpukanUc.setMasterActivity(penumpukanUcMasterActivity);
            recapPenumpukanUc.setOperation(RecapPenumpukanUc.OPERATION_DISCHARGE);
            recapPenumpukanUc.setActivity(RecapPenumpukanUc.ACTIVITY_TRANSHIPMENT);
            recapPenumpukanUc.setMasterCurrency(idrMasterCurrency);
            recapPenumpukanUc.setServiceVessel(serviceVessel);
            
//                Double tonage = Double.valueOf(uncontainerizedService.getWeight() / 1000);
//                Double volume = uncontainerizedService.getCubication().doubleValue();
//                Double factor = tonage > volume ? tonage : volume;
            
            recapPenumpukanUc.setJasaDermaga(BigDecimal.ZERO);
            recapPenumpukanUc.setChargeMasa1(penumpukanUcCharge.multiply(BigDecimal.valueOf(recapPenumpukanUc.getMasa1())).multiply(BigDecimal.valueOf(factor)));
            recapPenumpukanUc.setChargeMasa2(penumpukanUcCharge.multiply(BigDecimal.valueOf(recapPenumpukanUc.getMasa2())).multiply(BigDecimal.valueOf(factor)).multiply(BigDecimal.valueOf(2)));
            recapPenumpukanUc.setTotalCharge(recapPenumpukanUc.getChargeMasa1().add(recapPenumpukanUc.getChargeMasa2()).add(recapPenumpukanUc.getJasaDermaga()));
            
            recapPenumpukanUcFacadeLocal.edit(recapPenumpukanUc);
            
            // update change status transhipment
            if (false) {
                
                List<ChangeStatusHistory> changeStatusHistoryList = changeStatusHistoryFacade.findTranshipmentsByOldPpkb(serviceVessel.getNoPpkb());
                for (ChangeStatusHistory changeStatusHistory : changeStatusHistoryList) {
                    boolean isOverSize = false;
                    if (changeStatusHistory.getOverSize().equals("TRUE")) {
                        isOverSize = true;
                    }
                    String transhipmentActivityCode = masterActivityFacade.translateTranshipmentActivityCode(
                            isOverSize, 
                            changeStatusHistory.getContStatus(), 
                            changeStatusHistory.getCrane(), 
                            changeStatusHistory.getContSize());
                    BigDecimal transhipmentCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), transhipmentActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
                    RecapTranshipment recapTranshipment = recapTranshipmentFacade.findByActivityCodeNoPpkbAndOperation(transhipmentActivityCode, serviceVessel.getNoPpkb(), DISCHARGE);

                    if (recapTranshipment != null) {
                        recapTranshipment.setAmount(recapTranshipment.getAmount() + 1);
                    } else {
                        recapTranshipment = new RecapTranshipment();
                        recapTranshipment.setNoPpkb(serviceVessel.getNoPpkb());
                        recapTranshipment.setOperation(DISCHARGE);
                        recapTranshipment.setActivityCode(transhipmentActivityCode);
                        recapTranshipment.setCharge(transhipmentCharge);
                        recapTranshipment.setAmount(1);
                        recapTranshipment.setCurrCode(masterCurrency.getCurrCode());
                    }

                    recapTranshipment.setTotalCharge(recapTranshipment.getCharge().multiply(BigDecimal.valueOf(recapTranshipment.getAmount())));
                    recapTranshipmentFacade.edit(recapTranshipment);
                }
            }
        }
    }
    
    private void calculateContTranshipment(ServiceVessel serviceVessel, MasterCurrency usdMasterCurrency, MasterCurrency idrMasterCurrency) {
        MasterCurrency masterCurrency;
        // update service cont transhipment
        //[7XB]
        List<ServiceContTranshipment> serviceContTranshipmentList = serviceContTranshipmentFacade.findByNoPpkb(serviceVessel.getNoPpkb());
        boolean isSling = false;
        boolean isOverSize = false;
        for (ServiceContTranshipment serviceContTranshipment : serviceContTranshipmentList) {
            if (serviceContTranshipment.getIsExportImport().equals("TRUE")) {
                masterCurrency = usdMasterCurrency;
            } else {
                masterCurrency = idrMasterCurrency;
            }
            
            isSling = false;
            if (serviceContTranshipment.getIsSling().equals("TRUE")) {
                isSling = true;
            }
            String transhipmentActivityCode = masterActivityFacade.translateTranshipmentActivityCode(
                    isSling,
                    serviceContTranshipment.getContStatus(), serviceContTranshipment.getCrane(), serviceContTranshipment.getContSize());
            serviceContTranshipment.setMasterActivity(masterActivityFacade.find(transhipmentActivityCode));
            serviceContTranshipmentFacade.edit(serviceContTranshipment);
            
            BigDecimal transhipmentCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), transhipmentActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
            
            if (serviceContTranshipment.getDangerous().equals("TRUE")) {
                if (serviceContTranshipment.getDgLabel().equals("FALSE")) {
                    transhipmentCharge = transhipmentCharge.multiply(BigDecimal.valueOf(3));
                } else if (serviceContTranshipment.getMasterCommodity() != null && serviceContTranshipment.getMasterCommodity().getMasterDangerousClass() != null && MasterDangerousClass.affectedToHandling.contains(serviceContTranshipment.getMasterCommodity().getMasterDangerousClass().getDangerousClass())) {
                    transhipmentCharge = transhipmentCharge.multiply(BigDecimal.valueOf(2));
                }
            }
            
            if (serviceContTranshipment.getIsSling().equals("FALSE") && serviceContTranshipment.getOverSize().equals("TRUE")) {
                transhipmentCharge = transhipmentCharge.multiply(BigDecimal.valueOf(1.5));
            } else if (serviceContTranshipment.getIsSling().equals("FALSE") && serviceContTranshipment.getOverSize().equals("FALSE") && serviceContTranshipment.getTwentyOneFeet().equals("FALSE")) {
                transhipmentCharge = transhipmentCharge.multiply(BigDecimal.valueOf(1.2));
            }
            
            RecapTranshipment recapTranshipment = recapTranshipmentFacade.findByBentuk3Constraint(serviceContTranshipment.getContSize(), serviceContTranshipment.getContStatus(), serviceContTranshipment.getCrane(), serviceContTranshipment.getIsExportImport(), serviceContTranshipment.getOverSize(), serviceContTranshipment.getIsSling(), serviceContTranshipment.getMasterContainerType().getMasterContainerTypeInGeneral().getId(), transhipmentActivityCode, DISCHARGE, serviceVessel.getNoPpkb(), masterCurrency.getCurrCode(), serviceContTranshipment.getTwentyOneFeet(), transhipmentCharge);
            
            if (recapTranshipment != null) {
                recapTranshipment.setAmount(recapTranshipment.getAmount() + 1);
            } else {
                recapTranshipment = new RecapTranshipment();
                recapTranshipment.setIsExportImport(serviceContTranshipment.getIsExportImport());
                recapTranshipment.setSling(serviceContTranshipment.getIsSling());
                recapTranshipment.setOpenDoor(serviceContTranshipment.getOverSize());
                recapTranshipment.setCrane(serviceContTranshipment.getCrane());
                recapTranshipment.setContSize(serviceContTranshipment.getContSize());
                recapTranshipment.setTwentyOneFeet(serviceContTranshipment.getTwentyOneFeet());
                recapTranshipment.setContStatus(serviceContTranshipment.getContStatus());
                recapTranshipment.setContainerType(serviceContTranshipment.getMasterContainerType().getMasterContainerTypeInGeneral());
                recapTranshipment.setNoPpkb(serviceVessel.getNoPpkb());
                recapTranshipment.setOperation(DISCHARGE);
                recapTranshipment.setActivityCode(transhipmentActivityCode);
                recapTranshipment.setCharge(transhipmentCharge);
                recapTranshipment.setAmount(1);
                recapTranshipment.setCurrCode(masterCurrency.getCurrCode());
            }
            
            recapTranshipment.setTotalCharge(recapTranshipment.getCharge().multiply(BigDecimal.valueOf(recapTranshipment.getAmount())));
            
            isOverSize = false;
            if (serviceContTranshipment.getOverSize().equals("TRUE") || serviceContTranshipment.getIsSling().equals("TRUE")) {
                isOverSize = true;
            }
            String upahBuruhActivityCode = masterActivityFacade.translateUbahBuruhHandlingActivityCode(
                    MasterActivity.Type.TRANSHIPMENT.toString(),
                    serviceContTranshipment.getContSize(),
                    isOverSize
            );
            MasterActivity upahBuruhMasterActivity = masterActivityFacade.find(upahBuruhActivityCode);
            BigDecimal upahBuruhCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), upahBuruhActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
            RecapUpahBuruh recapUpahBuruh = recapUpahBuruhFacadeLocal.findByBentuk3Constraint(
                    serviceContTranshipment.getContSize(),
                    serviceContTranshipment.getContStatus(),
                    serviceContTranshipment.getCrane(),
                    serviceContTranshipment.getIsExportImport(),
                    serviceContTranshipment.getOverSize(),
                    serviceContTranshipment.getIsSling(),
                    serviceContTranshipment.getMasterContainerType().getMasterContainerTypeInGeneral().getId(),
                    upahBuruhActivityCode,
                    RecapUpahBuruh.OPERATION_DISCHARGE,
                    RecapUpahBuruh.ACTIVITY_TRANSHIPMENT,
                    serviceVessel.getNoPpkb(),
                    idrMasterCurrency.getCurrCode(),
                    serviceContTranshipment.getTwentyOneFeet(),
                    upahBuruhCharge);
            
            if (recapUpahBuruh != null) {
                recapUpahBuruh.setAmount(recapUpahBuruh.getAmount() + 1);
            } else {
                recapUpahBuruh = new RecapUpahBuruh();
                recapUpahBuruh.setIsExportImport(serviceContTranshipment.getIsExportImport());
                recapUpahBuruh.setSling(serviceContTranshipment.getIsSling());
                recapUpahBuruh.setCrane(serviceContTranshipment.getCrane());
                recapUpahBuruh.setContStatus(serviceContTranshipment.getContStatus());
                recapUpahBuruh.setContSize(serviceContTranshipment.getContSize());
                recapUpahBuruh.setTwentyOneFeet(serviceContTranshipment.getTwentyOneFeet());
                recapUpahBuruh.setOpenDoor(serviceContTranshipment.getOverSize());
                recapUpahBuruh.setContainerType(serviceContTranshipment.getMasterContainerType().getMasterContainerTypeInGeneral());
                recapUpahBuruh.setOperation(RecapUpahBuruh.OPERATION_DISCHARGE);
                recapUpahBuruh.setActivity(RecapUpahBuruh.ACTIVITY_TRANSHIPMENT);
                recapUpahBuruh.setServiceVessel(serviceVessel);
                recapUpahBuruh.setMasterActivity(upahBuruhMasterActivity);
                recapUpahBuruh.setCharge(upahBuruhCharge);
                recapUpahBuruh.setAmount(1);
                recapUpahBuruh.setMasterCurrency(idrMasterCurrency);
            }
            
            recapUpahBuruh.setTotalCharge(recapUpahBuruh.getCharge().multiply(BigDecimal.valueOf(recapUpahBuruh.getAmount())));
            
            recapTranshipmentFacade.edit(recapTranshipment);
            recapUpahBuruhFacadeLocal.edit(recapUpahBuruh);
        }
        //[/7XB]
    }
    
    private void calculateUncontaineizedShifting(ServiceVessel serviceVessel, MasterCurrency usdMasterCurrency, MasterCurrency idrMasterCurrency) {
        MasterCurrency masterCurrency;
        // update service shifting uc
        boolean isLanded = false;
        List<UcShiftingService> ucShiftingServiceList = ucShiftingServiceFacade.findByNoPpkbAndHasPaid(serviceVessel.getNoPpkb());
        for (UcShiftingService ucShiftingService : ucShiftingServiceList) {
            UncontainerizedService uncontainerizedService = uncontainerizedServiceFacadeLocal.findByNoPpkbAndBlNo(serviceVessel.getNoPpkb(), ucShiftingService.getBlNo());
            isLanded = false;
            if (ucShiftingService.getIsLanded().equals("TRUE")) {
                isLanded = true;
            }
            String shiftingUcActivityCode =
                    masterActivityFacade.translateShiftingUcActivityCode(
                            uncontainerizedService.getWeight(),
                            ucShiftingService.getCrane(),
                            isLanded);
            MasterActivity shiftingUcMasterActivity = masterActivityFacade.find(shiftingUcActivityCode);
            
            ucShiftingService.setActivityCode(shiftingUcActivityCode);
            ucShiftingServiceFacade.edit(ucShiftingService);
            
            if (uncontainerizedService.getIsExportImport().equals("TRUE")) {
                masterCurrency = usdMasterCurrency;
            } else {
                masterCurrency = idrMasterCurrency;
            }
            
            BigDecimal shiftingUcCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), shiftingUcActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
            
            RecapHandlingUc recapShiftingUc = recapHandlingUcFacadeLocal.findByBentuk3ConstraintWithStatus(
                    serviceVessel.getNoPpkb(),
                    uncontainerizedService.getWeightClassification(),
                    ucShiftingService.getCrane(),
                    uncontainerizedService.getIsExportImport(),
                    shiftingUcActivityCode,
                    masterCurrency.getCurrCode(),
                    RecapHandlingUc.OPERATION_DISCHARGE,
                    RecapHandlingUc.ACTIVITY_SHIFTING,
                    ucShiftingService.getShiftTo(),
                    shiftingUcCharge
            );
            
            if (recapShiftingUc != null) {
                recapShiftingUc.setAmount(recapShiftingUc.getAmount() + 1);
            } else {
                recapShiftingUc = new RecapHandlingUc();
                recapShiftingUc.setServiceVessel(serviceVessel);
                recapShiftingUc.setWeightGroup(uncontainerizedService.getWeightClassification());
                recapShiftingUc.setCrane(ucShiftingService.getCrane());
                recapShiftingUc.setStatus(ucShiftingService.getShiftTo());
                recapShiftingUc.setIsExportImport(uncontainerizedService.getIsExportImport());
                recapShiftingUc.setMasterCurrency(masterCurrency);
                recapShiftingUc.setMasterActivity(shiftingUcMasterActivity);
                recapShiftingUc.setOperation(RecapHandlingUc.OPERATION_DISCHARGE);
                recapShiftingUc.setActivity(RecapHandlingUc.ACTIVITY_SHIFTING);
                recapShiftingUc.setCharge(shiftingUcCharge);
                recapShiftingUc.setAmount(1);
            }
            
            recapShiftingUc.setTotalCharge(recapShiftingUc.getCharge().multiply(BigDecimal.valueOf(recapShiftingUc.getAmount())));
            
            Double tonage = Double.valueOf(uncontainerizedService.getWeight() / 1000);
            Double volume = uncontainerizedService.getCubication().doubleValue();
            Double factor = tonage > volume ? tonage : volume;
            
            String upahBuruhUcActivityCode = masterActivityFacade.translateUbahBuruhUcActivityCode();
            MasterActivity upahBuruhUcMasterActivity = masterActivityFacade.find(upahBuruhUcActivityCode);
            BigDecimal upahBuruhUcCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), upahBuruhUcActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
            RecapUpahBuruhUc recapUpahBuruhShiftingUc = recapUpahBuruhUcFacadeLocal.findByBentuk3ConstraintWithStatus(
                    serviceVessel.getNoPpkb(),
                    uncontainerizedService.getWeightClassification(),
                    ucShiftingService.getCrane(),
                    uncontainerizedService.getIsExportImport(),
                    upahBuruhUcActivityCode,
                    idrMasterCurrency.getCurrCode(),
                    RecapUpahBuruh.OPERATION_DISCHARGE,
                    RecapUpahBuruh.ACTIVITY_SHIFTING,
                    ucShiftingService.getShiftTo(),
                    upahBuruhUcCharge.multiply(BigDecimal.valueOf(factor))
            );
            
            if (recapUpahBuruhShiftingUc != null) {
                recapUpahBuruhShiftingUc.setAmount(recapUpahBuruhShiftingUc.getAmount() + 1);
            } else {
                recapUpahBuruhShiftingUc = new RecapUpahBuruhUc();
                recapUpahBuruhShiftingUc.setServiceVessel(serviceVessel);
                recapUpahBuruhShiftingUc.setWeightGroup(uncontainerizedService.getWeightClassification());
                recapUpahBuruhShiftingUc.setCrane(ucShiftingService.getCrane());
                recapUpahBuruhShiftingUc.setStatus(ucShiftingService.getShiftTo());
                recapUpahBuruhShiftingUc.setIsExportImport(uncontainerizedService.getIsExportImport());
                recapUpahBuruhShiftingUc.setMasterCurrency(idrMasterCurrency);
                recapUpahBuruhShiftingUc.setMasterActivity(upahBuruhUcMasterActivity);
                recapUpahBuruhShiftingUc.setOperation(RecapUpahBuruh.OPERATION_DISCHARGE);
                recapUpahBuruhShiftingUc.setActivity(RecapUpahBuruh.ACTIVITY_SHIFTING);
                recapUpahBuruhShiftingUc.setCharge(upahBuruhUcCharge.multiply(BigDecimal.valueOf(factor)));
                recapUpahBuruhShiftingUc.setAmount(1);
            }
            recapUpahBuruhShiftingUc.setTotalCharge(recapUpahBuruhShiftingUc.getCharge().multiply(BigDecimal.valueOf(recapUpahBuruhShiftingUc.getAmount())));
            
            if (ucShiftingService.getShiftTo().equals("LANDED") || ucShiftingService.getShiftTo().equals("TOCY")) {
                String penumpukanUcActivityCode = masterActivityFacade.translatePenumpukanUcActivityCode();
                MasterActivity penumpukanUcMasterActivity = masterActivityFacade.find(penumpukanUcActivityCode);
                BigDecimal penumpukanUcCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), penumpukanUcActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
                
                RecapPenumpukanUc recapPenumpukanUc = new RecapPenumpukanUc();
                recapPenumpukanUc.setBlNo(uncontainerizedService.getBlNo());
                recapPenumpukanUc.setTotalHari(5);
                recapPenumpukanUc.setMasa1((short) 1);
                recapPenumpukanUc.setMasa2((short) 0);
                recapPenumpukanUc.setMasterActivity(penumpukanUcMasterActivity);
                recapPenumpukanUc.setOperation(ucShiftingService.getOperation());
                recapPenumpukanUc.setActivity(RecapPenumpukanUc.ACTIVITY_SHIFTING);
                recapPenumpukanUc.setMasterCurrency(idrMasterCurrency);
                recapPenumpukanUc.setServiceVessel(serviceVessel);
                
//                    Double tonage = Double.valueOf(uncontainerizedService.getWeight() / 1000);
//                    Double volume = uncontainerizedService.getCubication().doubleValue();
//                    Double factor = tonage > volume ? tonage : volume;
                
                recapPenumpukanUc.setJasaDermaga(BigDecimal.ZERO);
                recapPenumpukanUc.setChargeMasa1(penumpukanUcCharge.multiply(BigDecimal.valueOf(recapPenumpukanUc.getMasa1())).multiply(BigDecimal.valueOf(factor)));
                recapPenumpukanUc.setChargeMasa2(penumpukanUcCharge.multiply(BigDecimal.valueOf(recapPenumpukanUc.getMasa2())).multiply(BigDecimal.valueOf(factor)).multiply(BigDecimal.valueOf(2)));
                recapPenumpukanUc.setTotalCharge(recapPenumpukanUc.getChargeMasa1().add(recapPenumpukanUc.getChargeMasa2()).add(recapPenumpukanUc.getJasaDermaga()));
                
                RecapHandlingUc recapReshippingUc = recapHandlingUcFacadeLocal.findByBentuk3ConstraintWithStatus(
                        serviceVessel.getNoPpkb(),
                        uncontainerizedService.getWeightClassification(),
                        ucShiftingService.getCrane(),
                        uncontainerizedService.getIsExportImport(),
                        shiftingUcActivityCode,
                        masterCurrency.getCurrCode(),
                        RecapHandlingUc.OPERATION_LOAD,
                        RecapHandlingUc.ACTIVITY_RESHIPPING,
                        ucShiftingService.getShiftTo(),
                        shiftingUcCharge
                );
                
                if (recapReshippingUc != null) {
                    recapReshippingUc.setAmount(recapReshippingUc.getAmount() + 1);
                } else {
                    recapReshippingUc = new RecapHandlingUc();
                    recapReshippingUc.setServiceVessel(serviceVessel);
                    recapReshippingUc.setWeightGroup(uncontainerizedService.getWeightClassification());
                    recapReshippingUc.setCrane(ucShiftingService.getCrane());
                    recapReshippingUc.setStatus(ucShiftingService.getShiftTo());
                    recapReshippingUc.setIsExportImport(uncontainerizedService.getIsExportImport());
                    recapReshippingUc.setMasterCurrency(masterCurrency);
                    recapReshippingUc.setMasterActivity(shiftingUcMasterActivity);
                    recapReshippingUc.setOperation(RecapHandlingUc.OPERATION_LOAD);
                    recapReshippingUc.setActivity(RecapHandlingUc.ACTIVITY_RESHIPPING);
                    recapReshippingUc.setCharge(shiftingUcCharge);
                    recapReshippingUc.setAmount(1);
                }
                
                recapReshippingUc.setTotalCharge(recapReshippingUc.getCharge().multiply(BigDecimal.valueOf(recapReshippingUc.getAmount())));
                
                RecapUpahBuruhUc recapUpahBuruhReshippingUc = recapUpahBuruhUcFacadeLocal.findByBentuk3ConstraintWithStatus(
                        serviceVessel.getNoPpkb(),
                        uncontainerizedService.getWeightClassification(),
                        ucShiftingService.getCrane(),
                        uncontainerizedService.getIsExportImport(),
                        upahBuruhUcActivityCode,
                        idrMasterCurrency.getCurrCode(),
                        RecapUpahBuruhUc.OPERATION_LOAD,
                        RecapUpahBuruhUc.ACTIVITY_RESHIPPING,
                        ucShiftingService.getShiftTo(),
                        upahBuruhUcCharge.multiply(BigDecimal.valueOf(factor))
                );
                
                if (recapUpahBuruhReshippingUc != null) {
                    recapUpahBuruhReshippingUc.setAmount(recapUpahBuruhReshippingUc.getAmount() + 1);
                } else {
                    recapUpahBuruhReshippingUc = new RecapUpahBuruhUc();
                    recapUpahBuruhReshippingUc.setServiceVessel(serviceVessel);
                    recapUpahBuruhReshippingUc.setWeightGroup(uncontainerizedService.getWeightClassification());
                    recapUpahBuruhReshippingUc.setCrane(ucShiftingService.getCrane());
                    recapUpahBuruhReshippingUc.setStatus(ucShiftingService.getShiftTo());
                    recapUpahBuruhReshippingUc.setIsExportImport(uncontainerizedService.getIsExportImport());
                    recapUpahBuruhReshippingUc.setMasterCurrency(idrMasterCurrency);
                    recapUpahBuruhReshippingUc.setMasterActivity(upahBuruhUcMasterActivity);
                    recapUpahBuruhReshippingUc.setOperation(RecapUpahBuruhUc.OPERATION_LOAD);
                    recapUpahBuruhReshippingUc.setActivity(RecapUpahBuruhUc.ACTIVITY_RESHIPPING);
                    recapUpahBuruhReshippingUc.setCharge(upahBuruhUcCharge.multiply(BigDecimal.valueOf(factor)));
                    recapUpahBuruhReshippingUc.setAmount(1);
                }
                
                recapUpahBuruhReshippingUc.setTotalCharge(recapUpahBuruhReshippingUc.getCharge().multiply(BigDecimal.valueOf(recapUpahBuruhReshippingUc.getAmount())));
                
                recapPenumpukanUcFacadeLocal.edit(recapPenumpukanUc);
                recapUpahBuruhUcFacadeLocal.edit(recapUpahBuruhReshippingUc);
                recapHandlingUcFacadeLocal.edit(recapReshippingUc);
            }
            
            recapUpahBuruhUcFacadeLocal.edit(recapUpahBuruhShiftingUc);
            recapHandlingUcFacadeLocal.edit(recapShiftingUc);
        }
    }

    private void calculateShifting(ServiceVessel serviceVessel, MasterCurrency usdMasterCurrency, MasterCurrency idrMasterCurrency) {
        MasterCurrency masterCurrency;
        // update service shifting
        
        List<ServiceShifting> serviceShiftingList = serviceShiftingFacade.findByNoPpkbAndHasPaid(serviceVessel.getNoPpkb());
        //[8XB]
        for (ServiceShifting serviceShifting : serviceShiftingList) {
            boolean isOverSize = false;
            if (serviceShifting.getIsSling().equals("TRUE")) {
                isOverSize = true;
            }
            //Equipment liftOn  = equipmentFacadeLocal.findByPpkbBLNoEquipmentActCodeAndOperation(uncontainerizedService.getNoPpkb(), uncontainerizedService.getBlNo(), "LIFTON", "TRANSLOADUC");
            
//            Equipment equipment = equipmentFacadeLocal.findIdByPPKBnContNo2(serviceVessel.getNoPpkb(), serviceShifting.getContNo());
            Equipment equipment = equipmentFacadeLocal.find(serviceShifting.getIdEquipment());
            equipment.getMasterEquipment().getEquipmentGroup();
//            String shiftingActivityCode = masterActivityFacade.translateShiftingActivityCodeEquipment(
//                    isOverSize,
//                    serviceShifting.getContStatus(),
//                    serviceShifting.getShiftTo(),
//                    serviceShifting.getCrane(),
//                    serviceShifting.getContSize(),equipment.getMasterEquipment().getEquipmentGroup());
            
            String shiftingActivityCode = masterActivityFacade.translateShiftingActivityCode(
                    "SHIFTING",  serviceShifting.getShiftTo(), serviceShifting.getContStatus(), serviceShifting.getContSize(), serviceShifting.getCrane()
            );
            serviceShifting.setMasterActivity(masterActivityFacade.find(shiftingActivityCode));
            serviceShiftingFacade.edit(serviceShifting);
            
            if (serviceShifting.getIsExportImport().equals("TRUE")) {
                masterCurrency = usdMasterCurrency;
            } else {
                masterCurrency = idrMasterCurrency;
            }
            
            BigDecimal shiftingCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), shiftingActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
            
//            if ("TRUE".equals(serviceShifting.getDg())) {
//                if (serviceShifting.getDgLabel().equals("FALSE")) {
//                    shiftingCharge = shiftingCharge.multiply(BigDecimal.valueOf(3));
//                } else if (serviceShifting.getMasterCommodity() != null && serviceShifting.getMasterCommodity().getMasterDangerousClass() != null && MasterDangerousClass.affectedToHandling.contains(serviceShifting.getMasterCommodity().getMasterDangerousClass().getDangerousClass())) {
//                    shiftingCharge = shiftingCharge.multiply(BigDecimal.valueOf(2));
//                }
//            }
//            
//            if (serviceShifting.getIsSling().equals("FALSE") && serviceShifting.getOverSize().equals("TRUE")) {
//                shiftingCharge = shiftingCharge.multiply(BigDecimal.valueOf(1.5));
//            } else if (serviceShifting.getIsSling().equals("FALSE") && serviceShifting.getOverSize().equals("FALSE") && serviceShifting.getTwentyOneFeet().equals("TRUE")) {
//                shiftingCharge = shiftingCharge.multiply(BigDecimal.valueOf(1.2));
//            }
            
            
            RecapShifting recapShifting = recapShiftingFacade.findByBentuk3Constraint(
                    serviceShifting.getContSize(),
                    serviceShifting.getContStatus(),
                    serviceShifting.getCrane(),
                    serviceShifting.getIsExportImport(),
                    serviceShifting.getOverSize(),
                    serviceShifting.getIsSling(),
                    serviceShifting.getMasterContainerType().getMasterContainerTypeInGeneral().getId(),
                    shiftingActivityCode,
                    RecapShifting.OPERATION_DISCHARGE,
                    serviceShifting.getShiftTo(),
                    serviceVessel.getNoPpkb(),
                    masterCurrency.getCurrCode(),
                    serviceShifting.getTwentyOneFeet(),
                    shiftingCharge);
            
            if (recapShifting != null) {
                recapShifting.setAmount(recapShifting.getAmount() + 1);
            } else {
                recapShifting = new RecapShifting();
                recapShifting.setIsExportImport(serviceShifting.getIsExportImport());
                recapShifting.setSling(serviceShifting.getIsSling());
                recapShifting.setOpenDoor(serviceShifting.getOverSize());
                recapShifting.setCrane(serviceShifting.getCrane());
                recapShifting.setContSize(serviceShifting.getContSize());
                recapShifting.setContStatus(serviceShifting.getContStatus());
                recapShifting.setTwentyOneFeet(serviceShifting.getTwentyOneFeet());
                recapShifting.setContainerType(serviceShifting.getMasterContainerType().getMasterContainerTypeInGeneral());
                recapShifting.setNoPpkb(serviceVessel.getNoPpkb());
                recapShifting.setOperation(RecapShifting.OPERATION_DISCHARGE);
                recapShifting.setShiftTo(serviceShifting.getShiftTo());
                recapShifting.setActivityCode(shiftingActivityCode);
                recapShifting.setCharge(shiftingCharge);
                recapShifting.setAmount(1);
                recapShifting.setCurrCode(masterCurrency.getCurrCode());
            }
            
            recapShifting.setTotalCharge(recapShifting.getCharge().multiply(BigDecimal.valueOf(recapShifting.getAmount())));
            
//            isOverSize = false;
//            if (serviceShifting.getOverSize().equals("TRUE")|| serviceShifting.getIsSling().equals("TRUE")) {
//                isOverSize = true;
//            }
//            String upahBuruhActivityCode = masterActivityFacade.translateUbahBuruhShiftingActivityCode(
//                    serviceShifting.getShiftTo(),
//                    serviceShifting.getContSize(),
//                    isOverSize);
//            MasterActivity upahBuruhMasterActivity = masterActivityFacade.find(upahBuruhActivityCode);
//            BigDecimal upahBuruhCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), upahBuruhActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
//            RecapUpahBuruh recapUpahBuruh = recapUpahBuruhFacadeLocal.findByBentuk3ConstraintWithStatus(
//                    serviceShifting.getContSize(),
//                    serviceShifting.getContStatus(),
//                    serviceShifting.getCrane(),
//                    serviceShifting.getIsExportImport(),
//                    serviceShifting.getOverSize(),
//                    serviceShifting.getIsSling(),
//                    serviceShifting.getMasterContainerType().getMasterContainerTypeInGeneral().getId(),
//                    upahBuruhActivityCode,
//                    RecapUpahBuruh.OPERATION_DISCHARGE,
//                    RecapUpahBuruh.ACTIVITY_SHIFTING,
//                    serviceVessel.getNoPpkb(),
//                    idrMasterCurrency.getCurrCode(),
//                    serviceShifting.getShiftTo(),
//                    serviceShifting.getTwentyOneFeet(),
//                    upahBuruhCharge);
//            
//            if (recapUpahBuruh != null) {
//                recapUpahBuruh.setAmount(recapUpahBuruh.getAmount() + 1);
//            } else {
//                recapUpahBuruh = new RecapUpahBuruh();
//                recapUpahBuruh.setIsExportImport(serviceShifting.getIsExportImport());
//                recapUpahBuruh.setSling(serviceShifting.getIsSling());
//                recapUpahBuruh.setCrane(serviceShifting.getCrane());
//                recapUpahBuruh.setContStatus(serviceShifting.getContStatus());
//                recapUpahBuruh.setTwentyOneFeet(serviceShifting.getTwentyOneFeet());
//                recapUpahBuruh.setContSize(serviceShifting.getContSize());
//                recapUpahBuruh.setOpenDoor(serviceShifting.getOverSize());
//                recapUpahBuruh.setContainerType(serviceShifting.getMasterContainerType().getMasterContainerTypeInGeneral());
//                recapUpahBuruh.setOperation(RecapUpahBuruh.OPERATION_DISCHARGE);
//                recapUpahBuruh.setActivity(RecapUpahBuruh.ACTIVITY_SHIFTING);
//                recapUpahBuruh.setStatus(serviceShifting.getShiftTo());
//                recapUpahBuruh.setServiceVessel(serviceVessel);
//                recapUpahBuruh.setMasterActivity(upahBuruhMasterActivity);
//                recapUpahBuruh.setCharge(upahBuruhCharge);
//                recapUpahBuruh.setAmount(1);
//                recapUpahBuruh.setMasterCurrency(idrMasterCurrency);
//            }
//            
//            recapUpahBuruh.setTotalCharge(recapUpahBuruh.getCharge().multiply(new BigDecimal(recapUpahBuruh.getAmount())));
            
//            isOverSize = false;
//            if ((!serviceShifting.getContStatus().equals("MTY") && serviceShifting.getOverSize().equals("TRUE")) || serviceShifting.getTwentyOneFeet().equals("TRUE")) {
//                isOverSize = true;
//            }
//            if (serviceShifting.getShiftTo().equals("TOCY") || serviceShifting.getShiftTo().equals("LANDED")) {
//                Integer idReefer = serviceReeferFacade.findValidasiPenumpukan(serviceVessel.getNoPpkb(), serviceShifting.getContNo(), ServiceReefer.OPERATION_SHIFTING);
//                String penumpukanActivityCode = masterActivityFacade.translatePenumpukanActivityCode(
//                        serviceShifting.getContStatus(),
//                        serviceShifting.getMasterContainerType().getIsoCode(),
//                        isOverSize,
//                        serviceShifting.getContSize(),
//                        idReefer > 0);
//                RecapPenumpukan recapPenumpukan = new RecapPenumpukan();
//                recapPenumpukan.setContNo(serviceShifting.getContNo());
//                recapPenumpukan.setMlo(serviceShifting.getMlo());
//                recapPenumpukan.setNoPpkb(serviceVessel.getNoPpkb());
//                recapPenumpukan.setMasa2((short)0);
//                recapPenumpukan.setMasa1((short)1);
//                recapPenumpukan.setActivityCode(penumpukanActivityCode);
//                recapPenumpukan.setOperation(RecapPenumpukan.SHIFTING);
//                recapPenumpukan.setTotalHari(5);
//                recapPenumpukan.setCurrCode(idrMasterCurrency.getCurrCode());
//                
//                BigDecimal penumpukanCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), penumpukanActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
//                
//                if (serviceShifting.getDg().equals("TRUE") && serviceShifting.getDgLabel().equals("TRUE")) {
//                    penumpukanCharge = penumpukanCharge.multiply(BigDecimal.valueOf(2));
//                } else if (serviceShifting.getDg().equals("TRUE") && serviceShifting.getDgLabel().equals("FALSE")) {
//                    penumpukanCharge = penumpukanCharge.multiply(BigDecimal.valueOf(3));
//                }
//                
//                recapPenumpukan.setChargeMasa1(penumpukanCharge.multiply(BigDecimal.valueOf(recapPenumpukan.getMasa1())));
//                recapPenumpukan.setChargeMasa2(penumpukanCharge.multiply(BigDecimal.valueOf(recapPenumpukan.getMasa2())).multiply(BigDecimal.valueOf(2)));
//                recapPenumpukan.setJasaDermaga(BigDecimal.ZERO);
//                recapPenumpukan.setTotalCharge(recapPenumpukan.getChargeMasa1().add(recapPenumpukan.getChargeMasa2()).add(recapPenumpukan.getJasaDermaga()));
//                
//                RecapShifting recapReshipping = recapShiftingFacade.findByBentuk3Constraint(
//                        serviceShifting.getContSize(),
//                        serviceShifting.getContStatus(),
//                        serviceShifting.getCrane(),
//                        serviceShifting.getIsExportImport(),
//                        serviceShifting.getOverSize(),
//                        serviceShifting.getIsSling(),
//                        serviceShifting.getMasterContainerType().getMasterContainerTypeInGeneral().getId(),
//                        shiftingActivityCode,
//                        RecapShifting.OPERATION_LOAD,
//                        serviceShifting.getShiftTo(),
//                        serviceVessel.getNoPpkb(),
//                        masterCurrency.getCurrCode(),
//                        serviceShifting.getTwentyOneFeet(),
//                        shiftingCharge);
//                
//                if (recapReshipping != null) {
//                    recapReshipping.setAmount(recapReshipping.getAmount() + 1);
//                } else {
//                    recapReshipping = new RecapShifting();
//                    recapReshipping.setIsExportImport(serviceShifting.getIsExportImport());
//                    recapReshipping.setSling(serviceShifting.getIsSling());
//                    recapReshipping.setOpenDoor(serviceShifting.getOverSize());
//                    recapReshipping.setCrane(serviceShifting.getCrane());
//                    recapReshipping.setContSize(serviceShifting.getContSize());
//                    recapReshipping.setContStatus(serviceShifting.getContStatus());
//                    recapReshipping.setTwentyOneFeet(serviceShifting.getTwentyOneFeet());
//                    recapReshipping.setContainerType(serviceShifting.getMasterContainerType().getMasterContainerTypeInGeneral());
//                    recapReshipping.setNoPpkb(serviceVessel.getNoPpkb());
//                    recapReshipping.setOperation(RecapShifting.OPERATION_LOAD);
//                    recapReshipping.setShiftTo(serviceShifting.getShiftTo());
//                    recapReshipping.setActivityCode(shiftingActivityCode);
//                    recapReshipping.setCharge(shiftingCharge);
//                    recapReshipping.setAmount(1);
//                    recapReshipping.setCurrCode(masterCurrency.getCurrCode());
//                }
//                
//                recapReshipping.setTotalCharge(recapReshipping.getCharge().multiply(BigDecimal.valueOf(recapReshipping.getAmount())));
//                
//                RecapUpahBuruh recapUpahBuruhReshipping = recapUpahBuruhFacadeLocal.findByBentuk3ConstraintWithStatus(
//                        serviceShifting.getContSize(),
//                        serviceShifting.getContStatus(),
//                        serviceShifting.getCrane(),
//                        serviceShifting.getIsExportImport(),
//                        serviceShifting.getOverSize(),
//                        serviceShifting.getIsSling(),
//                        serviceShifting.getMasterContainerType().getMasterContainerTypeInGeneral().getId(),
//                        upahBuruhActivityCode,
//                        RecapUpahBuruh.OPERATION_LOAD,
//                        RecapUpahBuruh.ACTIVITY_RESHIPPING,
//                        serviceVessel.getNoPpkb(),
//                        idrMasterCurrency.getCurrCode(),
//                        serviceShifting.getShiftTo(),
//                        serviceShifting.getTwentyOneFeet(),
//                        upahBuruhCharge);
//                
//                if (recapUpahBuruhReshipping != null) {
//                    recapUpahBuruhReshipping.setAmount(recapUpahBuruhReshipping.getAmount() + 1);
//                } else {
//                    recapUpahBuruhReshipping = new RecapUpahBuruh();
//                    recapUpahBuruhReshipping.setIsExportImport(serviceShifting.getIsExportImport());
//                    recapUpahBuruhReshipping.setSling(serviceShifting.getIsSling());
//                    recapUpahBuruhReshipping.setCrane(serviceShifting.getCrane());
//                    recapUpahBuruhReshipping.setContStatus(serviceShifting.getContStatus());
//                    recapUpahBuruhReshipping.setTwentyOneFeet(serviceShifting.getTwentyOneFeet());
//                    recapUpahBuruhReshipping.setContSize(serviceShifting.getContSize());
//                    recapUpahBuruhReshipping.setOpenDoor(serviceShifting.getOverSize());
//                    recapUpahBuruhReshipping.setContainerType(serviceShifting.getMasterContainerType().getMasterContainerTypeInGeneral());
//                    recapUpahBuruhReshipping.setOperation(RecapUpahBuruh.OPERATION_LOAD);
//                    recapUpahBuruhReshipping.setActivity(RecapUpahBuruh.ACTIVITY_RESHIPPING);
//                    recapUpahBuruhReshipping.setStatus(serviceShifting.getShiftTo());
//                    recapUpahBuruhReshipping.setServiceVessel(serviceVessel);
//                    recapUpahBuruhReshipping.setMasterActivity(upahBuruhMasterActivity);
//                    recapUpahBuruhReshipping.setCharge(upahBuruhCharge);
//                    recapUpahBuruhReshipping.setAmount(1);
//                    recapUpahBuruhReshipping.setMasterCurrency(idrMasterCurrency);
//                }
//                
//                recapUpahBuruhReshipping.setTotalCharge(recapUpahBuruhReshipping.getCharge().multiply(new BigDecimal(recapUpahBuruhReshipping.getAmount())));
//                
//                recapShiftingFacade.edit(recapReshipping);
//                recapUpahBuruhFacadeLocal.edit(recapUpahBuruhReshipping);
//                
//                recapPenumpukanFacade.create(recapPenumpukan);
//            }
            
            recapShiftingFacade.edit(recapShifting);
//            recapUpahBuruhFacadeLocal.edit(recapUpahBuruh);
        }
        //[/8XB]
    }

    private void calculateContainerLoad(ServiceVessel serviceVessel, MasterCurrency usdMasterCurrency, MasterCurrency idrMasterCurrency, int masa1ContainerRange, int masaFreeRange) throws RuntimeException {
        MasterCurrency masterCurrency;
        //perhitungan penumpukan
        List<ServiceContLoad> serviceContLoads = serviceContLoadFacade.findContainersThatHaveBeenLoaded(serviceVessel.getNoPpkb());
        //[3XM | 4XM | 4YM]
        for (ServiceContLoad serviceContLoad: serviceContLoads) {
            if(serviceContLoad.getIsSeling() != null && serviceContLoad.getIsSeling().equals("TRUE")) {
                serviceContLoad.setIsSeling("TRUE");
            } else {
                serviceContLoad.setIsSeling("FALSE");
            }
            
            if (serviceContLoad.getIsExportImport().equals("TRUE")) {
                masterCurrency = usdMasterCurrency;
            } else {
                masterCurrency = idrMasterCurrency;
            }
            
            String stevedoringActivityCode = masterActivityFacade.translateHandlingActivityCode(serviceContLoad.getContStatus(),
                    serviceContLoad.getContSize(), serviceContLoad.getCrane());
            
            if(stevedoringActivityCode == null){
                throw new RuntimeException("Cannot Find Activity Code");
            }
            
            MasterActivity handlingLoadMasterActivity = masterActivityFacade.find(stevedoringActivityCode);
            serviceContLoad.setMasterActivity(handlingLoadMasterActivity);
            serviceContLoadFacade.edit(serviceContLoad);
            
            BigDecimal stevedoringCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), stevedoringActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
            
            RecapHandlingLoad recapStevedoringLoad = new RecapHandlingLoad();
            recapStevedoringLoad.setContNo(serviceContLoad.getContNo());
            recapStevedoringLoad.setCrane(serviceContLoad.getCrane());
            recapStevedoringLoad.setContSize(serviceContLoad.getContSize());
            recapStevedoringLoad.setContStatus(serviceContLoad.getContStatus());
            recapStevedoringLoad.setTwentyOneFeet(serviceContLoad.getTwentyOneFeet());
            recapStevedoringLoad.setIsExportImport(serviceContLoad.getIsExportImport());
            recapStevedoringLoad.setSling(serviceContLoad.getIsSeling());
            recapStevedoringLoad.setOpenDoor(serviceContLoad.getOverSize());
            recapStevedoringLoad.setContainerType(serviceContLoad.getMasterContainerType().getMasterContainerTypeInGeneral());
            recapStevedoringLoad.setNoPpkb(serviceVessel.getNoPpkb());
            recapStevedoringLoad.setActivityCode(stevedoringActivityCode);
            recapStevedoringLoad.setCharge(stevedoringCharge);
            recapStevedoringLoad.setAmount(1);
            recapStevedoringLoad.setCurrCode(masterCurrency.getCurrCode());
            
            
            recapStevedoringLoad.setTotalCharge(recapStevedoringLoad.getCharge().multiply(BigDecimal.valueOf(recapStevedoringLoad.getAmount())));
            
            if(serviceContLoad.getContStatus().equals("fcl")){
                serviceContLoad.setContStatus("FCL");
            }
            
            recapHandlingLoadFacade.edit(recapStevedoringLoad);
            
            
//                Jasa PBM CC untuk Tarakan
            String mainActivity = "LOAD";
            String tarifType = "PBM";
            String jasaPBMActivityCode = masterActivityFacade.translateOtherPackageActivityCode(tarifType, serviceContLoad.getContStatus(),
                    serviceContLoad.getContSize(), serviceContLoad.getCrane());
            
            if(jasaPBMActivityCode == null){
                throw new RuntimeException("Cannot Find Activity Code for Jasa PBM");
            }
            
            createOtherPackageRecap(mainActivity, tarifType, masterCurrency, serviceVessel, serviceContLoad, jasaPBMActivityCode);
            
            
            
//                Angsur
//            Equipment equipment = equipmentFacadeLocal.findByEquipmentActCodeAndOperation(serviceContLoad.getServiceVessel().getNoPpkb(), serviceContLoad.getContNo(), "LIFTON", "LOAD");
//            if (equipment==null)
//                throw new RuntimeException("Cannot find Equipment");
//            
//            if ("TRUE".equals(equipment.getMasterEquipment().getOwner()))
//                tarifType = "ANGSUR";
//            else
//                tarifType = "ANGSURPLYR";
//            
//            String angsurActivityCode = masterActivityFacade.translateOtherPackageActivityCode(tarifType, serviceContLoad.getContStatus(),
//                    serviceContLoad.getContSize(), "ALLTYPE");
//            
//            if(angsurActivityCode == null){
//                throw new RuntimeException("Cannot Find Activity Code for Angsur");
//            }
//            
//            createOtherPackageRecap(mainActivity, tarifType, masterCurrency, serviceVessel, serviceContLoad, angsurActivityCode);
//            
//         
//            Stuffing
            tarifType = "STUFFING";
            String strippingActivityCode = masterActivityFacade.translateOtherPackageActivityCode(tarifType, serviceContLoad.getContStatus(),
                    serviceContLoad.getContSize(), "ALLTYPE");
            
            if(strippingActivityCode == null){
                throw new RuntimeException("Cannot Find Activity Code for Stuffing");
            }
            createOtherPackageRecap(mainActivity, tarifType, masterCurrency, serviceVessel, serviceContLoad, strippingActivityCode);
            
//            if (serviceContLoad.getIsTranshipment().contentEquals("TRUE")) {
//                ServiceContTranshipment serviceContTranshipment = serviceContTranshipmentFacade.findByNewPpkbAndContNo(serviceContLoad.getServiceVessel().getNoPpkb(), serviceContLoad.getContNo());
//                if (serviceContLoad.getIsSeling() != null && serviceContLoad.getIsSeling().contentEquals("TRUE")) {
//                    serviceContLoad.setIsSeling("TRUE");
//                }
//                
//                if (serviceContTranshipment.getIsExportImport().contentEquals("TRUE") ) {
//                    masterCurrency = usdMasterCurrency;
//                } else {
//                    masterCurrency = idrMasterCurrency;
//                }
//                
//                boolean isOverSize = false;
//                if (serviceContLoad.getOverSize().contentEquals("TRUE") || serviceContLoad.getIsSeling().contentEquals("TRUE")) {
//                    isOverSize = true;
//                }
//                String upahBuruhActivityCode = masterActivityFacade.translateUbahBuruhHandlingActivityCode(
//                        MasterActivity.Type.TRANSHIPMENT.toString(),
//                        serviceContLoad.getContSize(),
//                        isOverSize
//                );
//                
//                MasterActivity upahBuruhMasterActivity = masterActivityFacade.find(upahBuruhActivityCode);
//                BigDecimal upahBuruhCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), upahBuruhActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
//                
//                RecapUpahBuruh recapUpahBuruh = recapUpahBuruhFacadeLocal.findByBentuk3Constraint(
//                        serviceContLoad.getContSize(),
//                        serviceContLoad.getContStatus(),
//                        serviceContLoad.getCrane(),
//                        serviceContLoad.getIsExportImport(),
//                        serviceContLoad.getOverSize(),
//                        serviceContLoad.getIsSeling(),
//                        serviceContLoad.getMasterContainerType().getMasterContainerTypeInGeneral().getId(),
//                        upahBuruhActivityCode,
//                        RecapUpahBuruh.OPERATION_LOAD,
//                        RecapUpahBuruh.ACTIVITY_TRANSHIPMENT,
//                        serviceVessel.getNoPpkb(),
//                        idrMasterCurrency.getCurrCode(),
//                        serviceContLoad.getTwentyOneFeet(),
//                        upahBuruhCharge);
//                
//                if (recapUpahBuruh != null) {
//                    recapUpahBuruh.setAmount(recapUpahBuruh.getAmount() + 1);
//                } else {
//                    recapUpahBuruh = new RecapUpahBuruh();
//                    recapUpahBuruh.setIsExportImport(serviceContLoad.getIsExportImport());
//                    recapUpahBuruh.setSling(serviceContLoad.getIsSeling());
//                    recapUpahBuruh.setCrane(serviceContLoad.getCrane());
//                    recapUpahBuruh.setContSize(serviceContLoad.getContSize());
//                    recapUpahBuruh.setContStatus(serviceContLoad.getContStatus());
//                    recapUpahBuruh.setOpenDoor(serviceContLoad.getOverSize());
//                    recapUpahBuruh.setTwentyOneFeet(serviceContLoad.getTwentyOneFeet());
//                    recapUpahBuruh.setContainerType(serviceContLoad.getMasterContainerType().getMasterContainerTypeInGeneral());
//                    recapUpahBuruh.setOperation(RecapUpahBuruh.OPERATION_LOAD);
//                    recapUpahBuruh.setActivity(RecapUpahBuruh.ACTIVITY_TRANSHIPMENT);
//                    recapUpahBuruh.setServiceVessel(serviceVessel);
//                    recapUpahBuruh.setMasterActivity(upahBuruhMasterActivity);
//                    recapUpahBuruh.setCharge(upahBuruhCharge);
//                    recapUpahBuruh.setAmount(1);
//                    recapUpahBuruh.setMasterCurrency(idrMasterCurrency);
//                }
//                
//                recapUpahBuruh.setTotalCharge(recapUpahBuruh.getCharge().multiply(BigDecimal.valueOf(recapUpahBuruh.getAmount())));
//                recapUpahBuruhFacadeLocal.edit(recapUpahBuruh);
//                
//                int min = DateCalculator.countRangeInDays(serviceContLoad.getTransactionDate(), serviceContTranshipment.getStartPlacementDate()) + 1;
//                int masa2 = 0;
//                int masa1 = 0;
//                
//                isOverSize = false;
//                
//                if (serviceContLoad.getIsChangeVessel().contentEquals("TRUE")  || min > 14) {
//                    if (serviceContLoad.getIsSeling().contentEquals("TRUE") ) {
//                        isOverSize = true;
//                    }
//                    String dangerousClass = null;
//                    if(serviceContLoad.getMasterCommodity() != null && serviceContLoad.getMasterCommodity().getMasterDangerousClass() != null)
//                        dangerousClass = serviceContLoad.getMasterCommodity().getMasterDangerousClass().getDangerousClass();
//                    
//                    String stevedoringActivityCode = masterActivityFacade.translateStevedoringActivityCode(
//                            serviceContLoad.getContStatus(),
//                            serviceContLoad.getContSize(),
//                            serviceContLoad.getCrane(),
//                            isOverSize,
//                            serviceContLoad.getEquipmentGroup(),
//                            serviceContLoad.getDangerous().equals("TRUE"),
//                            serviceContLoad.getDgLabel().equals("TRUE"),
//                            dangerousClass,
//                            serviceContLoad.getIsSeling().equals("TRUE"),
//                            serviceContLoad.getTwentyOneFeet().equals("TRUE"));
//                    
//                    if(stevedoringActivityCode == null){
//                        throw new RuntimeException("Cannot Find Activity Code");
//                    }
//                    String htActivityCode = masterActivityFacade.translateHaulageTruckingActivityCode(
//                            serviceContLoad.getContStatus(),
//                            serviceContLoad.getContSize(),
//                            serviceContLoad.getCrane(),
//                            isOverSize,
//                            serviceContLoad.getEquipmentGroup(),
//                            serviceContLoad.getDangerous().equals("TRUE"),
//                            serviceContLoad.getDgLabel().equals("TRUE"),
//                            dangerousClass,
//                            serviceContLoad.getIsSeling().equals("TRUE"),
//                            serviceContLoad.getTwentyOneFeet().equals("TRUE"));
//                    if(htActivityCode == null){
//                        throw new RuntimeException("Cannot Find Activity Code");
//                    }
//                    String otherHandlingActivityCode = masterActivityFacade.translateOtherHandlingChargesActivityCode(
//                            serviceContLoad.getContStatus(),
//                            serviceContLoad.getContSize(),
//                            serviceContLoad.getCrane(),
//                            isOverSize,
//                            serviceContLoad.getEquipmentGroup(),
//                            serviceContLoad.getDangerous().equals("TRUE"),
//                            serviceContLoad.getDgLabel().equals("TRUE"),
//                            dangerousClass,
//                            serviceContLoad.getIsSeling().equals("TRUE"),
//                            serviceContLoad.getTwentyOneFeet().equals("TRUE"));
//                    if(htActivityCode == null){
//                        throw new RuntimeException("Cannot Find Activity Code");
//                    }
//                    BigDecimal stevedoringCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), stevedoringActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
//                    BigDecimal htCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), htActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
//                    BigDecimal otherHandlingCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), otherHandlingActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
//                    
//                    RecapHandlingLoad recapStevedoringLoad = new RecapHandlingLoad();
//                    recapStevedoringLoad.setContNo(serviceContLoad.getContNo());
//                    recapStevedoringLoad.setNoPpkb(serviceVessel.getNoPpkb());
//                    recapStevedoringLoad.setActivityCode(stevedoringActivityCode);
//                    recapStevedoringLoad.setCrane(serviceContLoad.getCrane());
//                    recapStevedoringLoad.setContStatus(serviceContLoad.getContStatus());
//                    recapStevedoringLoad.setIsExportImport(serviceContLoad.getIsExportImport());
//                    recapStevedoringLoad.setSling(serviceContLoad.getIsSeling());
//                    recapStevedoringLoad.setTwentyOneFeet(serviceContLoad.getTwentyOneFeet());
//                    recapStevedoringLoad.setOpenDoor(serviceContLoad.getOverSize());
//                    recapStevedoringLoad.setContSize(serviceContLoad.getContSize());
//                    recapStevedoringLoad.setContainerType(serviceContLoad.getMasterContainerType().getMasterContainerTypeInGeneral());
//                    recapStevedoringLoad.setCharge(stevedoringCharge);
//                    recapStevedoringLoad.setAmount(1);
//                    recapStevedoringLoad.setCurrCode(masterCurrency.getCurrCode());
//                    recapStevedoringLoad.setTotalCharge(recapStevedoringLoad.getCharge());
//                    recapHandlingLoadFacade.edit(recapStevedoringLoad);
//                    
//                    RecapHandlingLoad recapHtLoad = new RecapHandlingLoad();
//                    recapHtLoad.setContNo(serviceContLoad.getContNo());
//                    recapHtLoad.setNoPpkb(serviceVessel.getNoPpkb());
//                    recapHtLoad.setActivityCode(htActivityCode);
//                    recapHtLoad.setCrane(serviceContLoad.getCrane());
//                    recapHtLoad.setContStatus(serviceContLoad.getContStatus());
//                    recapHtLoad.setIsExportImport(serviceContLoad.getIsExportImport());
//                    recapHtLoad.setSling(serviceContLoad.getIsSeling());
//                    recapHtLoad.setTwentyOneFeet(serviceContLoad.getTwentyOneFeet());
//                    recapHtLoad.setOpenDoor(serviceContLoad.getOverSize());
//                    recapHtLoad.setContSize(serviceContLoad.getContSize());
//                    recapHtLoad.setContainerType(serviceContLoad.getMasterContainerType().getMasterContainerTypeInGeneral());
//                    recapHtLoad.setCharge(htCharge);
//                    recapHtLoad.setAmount(1);
//                    recapHtLoad.setCurrCode(masterCurrency.getCurrCode());
//                    recapHtLoad.setTotalCharge(recapHtLoad.getCharge());
//                    recapHandlingLoadFacade.edit(recapHtLoad);
//                    
//                    RecapHandlingLoad recapOtherHandlingLoad = new RecapHandlingLoad();
//                    recapOtherHandlingLoad.setContNo(serviceContLoad.getContNo());
//                    recapOtherHandlingLoad.setNoPpkb(serviceVessel.getNoPpkb());
//                    recapOtherHandlingLoad.setActivityCode(htActivityCode);
//                    recapOtherHandlingLoad.setCrane(serviceContLoad.getCrane());
//                    recapOtherHandlingLoad.setContStatus(serviceContLoad.getContStatus());
//                    recapOtherHandlingLoad.setIsExportImport(serviceContLoad.getIsExportImport());
//                    recapOtherHandlingLoad.setSling(serviceContLoad.getIsSeling());
//                    recapOtherHandlingLoad.setTwentyOneFeet(serviceContLoad.getTwentyOneFeet());
//                    recapOtherHandlingLoad.setOpenDoor(serviceContLoad.getOverSize());
//                    recapOtherHandlingLoad.setContSize(serviceContLoad.getContSize());
//                    recapOtherHandlingLoad.setContainerType(serviceContLoad.getMasterContainerType().getMasterContainerTypeInGeneral());
//                    recapOtherHandlingLoad.setCharge(otherHandlingCharge);
//                    recapOtherHandlingLoad.setAmount(1);
//                    recapOtherHandlingLoad.setCurrCode(masterCurrency.getCurrCode());
//                    recapOtherHandlingLoad.setTotalCharge(recapOtherHandlingLoad.getCharge());
//                    recapHandlingLoadFacade.edit(recapOtherHandlingLoad);
//                } else {
////                        String transhipmentActivityCode = masterActivityFacade.translateTranshipmentActivityCode(serviceContTranshipment.getIsSling(), serviceContTranshipment.getContStatus(), serviceContTranshipment.getCrane(), serviceContTranshipment.getContSize());
//                    boolean isSling = false;
//                    if(serviceContTranshipment.getIsSling() == "TRUE" ) {
//                        isSling = true;
//                    }
//                    String transhipmentActivityCode = masterActivityFacade.translateTranshipmentActivityCode(isSling, serviceContTranshipment.getContStatus(), serviceContTranshipment.getCrane(), serviceContTranshipment.getContSize());
//                    BigDecimal transhipmentCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), transhipmentActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
//                    
//                    if (serviceContLoad.getDangerous() == "TRUE") {
//                        if (serviceContLoad.getDgLabel() != "TRUE") {
//                            transhipmentCharge = transhipmentCharge.multiply(BigDecimal.valueOf(3));
//                        } else if (serviceContLoad.getMasterCommodity() != null && serviceContLoad.getMasterCommodity().getMasterDangerousClass() != null && MasterDangerousClass.affectedToHandling.contains(serviceContLoad.getMasterCommodity().getMasterDangerousClass().getDangerousClass())) {
//                            transhipmentCharge = transhipmentCharge.multiply(BigDecimal.valueOf(2));
//                        }
//                    }
//                    
//                    if (serviceContLoad.getIsSeling() != "TRUE" && serviceContLoad.getOverSize() == "TRUE") {
//                        transhipmentCharge = transhipmentCharge.multiply(BigDecimal.valueOf(1.5));
//                    } else if (serviceContLoad.getIsSeling() != "TRUE" && serviceContLoad.getOverSize() != "TRUE" && serviceContLoad.getTwentyOneFeet() == "TRUE") {
//                        transhipmentCharge = transhipmentCharge.multiply(BigDecimal.valueOf(1.2));
//                    }
//                    
//                    RecapTranshipment recapTranshipment = recapTranshipmentFacade.findByBentuk3Constraint(serviceContLoad.getContSize(), serviceContLoad.getContStatus(), serviceContLoad.getCrane(), serviceContLoad.getIsExportImport(), serviceContLoad.getOverSize(), serviceContLoad.getIsSeling(), serviceContLoad.getMasterContainerType().getMasterContainerTypeInGeneral().getId(), transhipmentActivityCode, LOAD, serviceVessel.getNoPpkb(), masterCurrency.getCurrCode(), serviceContLoad.getTwentyOneFeet(), transhipmentCharge);
//                    
//                    if (recapTranshipment != null) {
//                        recapTranshipment.setAmount(recapTranshipment.getAmount() + 1);
//                    } else {
//                        recapTranshipment = new RecapTranshipment();
//                        recapTranshipment.setCrane(serviceContLoad.getCrane());
//                        recapTranshipment.setContStatus(serviceContLoad.getContStatus());
//                        recapTranshipment.setIsExportImport(serviceContLoad.getIsExportImport());
//                        recapTranshipment.setOpenDoor(serviceContLoad.getOverSize());
//                        recapTranshipment.setSling(serviceContLoad.getIsSeling());
//                        recapTranshipment.setContSize(serviceContLoad.getContSize());
//                        recapTranshipment.setTwentyOneFeet(serviceContLoad.getTwentyOneFeet());
//                        recapTranshipment.setContainerType(serviceContLoad.getMasterContainerType().getMasterContainerTypeInGeneral());
//                        recapTranshipment.setNoPpkb(serviceVessel.getNoPpkb());
//                        recapTranshipment.setOperation(LOAD);
//                        recapTranshipment.setActivityCode(transhipmentActivityCode);
//                        recapTranshipment.setCharge(transhipmentCharge);
//                        recapTranshipment.setAmount(1);
//                        recapTranshipment.setCurrCode(masterCurrency.getCurrCode());
//                    }
//                    
//                    recapTranshipment.setTotalCharge(recapTranshipment.getCharge().multiply(BigDecimal.valueOf(recapTranshipment.getAmount())));
//                    recapTranshipmentFacade.edit(recapTranshipment);
//                    continue;
//                }
//                
//                int masaFree = 0;
//                
//                if (min < (masa1ContainerRange + 1)) {
//                    if (min <= masaFreeRange) {
//                        masa1 = masaFree;
//                    } else {
//                        masa1 = min - masaFreeRange + masaFree;
//                    }
//                } else {
//                    masa1 = masa1ContainerRange - masaFreeRange + masaFree;
//                    masa2 = min - masa1ContainerRange;
//                }
//                
//                if (masa1 == 0) {
//                    continue;
//                }
//                
//                Integer idReefer = serviceReeferFacade.findValidasiPenumpukan(serviceVessel.getNoPpkb(), serviceContLoad.getContNo(), ServiceReefer.OPERATION_TRANSHIPMENT);
//                
//                isOverSize = false;
//                if ((!serviceContLoad.getContStatus().equals("MTY") && serviceContLoad.getOverSize().equals("TRUE")) || serviceContLoad.getTwentyOneFeet().equals("TRUE")) {
//                    isOverSize = true;
//                }
//                
//                String penumpukanTranshipmentActivityCode = masterActivityFacade.translatePenumpukanActivityCode(
//                        serviceContLoad.getContStatus(),
//                        serviceContLoad.getMasterContainerType().getIsoCode(),
//                        isOverSize, serviceContLoad.getContSize(), idReefer > 0);
//                BigDecimal penumpukanTranshipmentCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), penumpukanTranshipmentActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
//                
//                //hitung perhitungan penumpukan
//                PerhitunganPenumpukanTranshipment perhitunganPenumpukanTranshipment = new PerhitunganPenumpukanTranshipment();
//                perhitunganPenumpukanTranshipment.setNoPpkb(serviceVessel.getNoPpkb());
//                perhitunganPenumpukanTranshipment.setMasa1((short) (masa1 - 1));
//                perhitunganPenumpukanTranshipment.setMasa2((short) masa2);
//                perhitunganPenumpukanTranshipment.setTotalHari(min);
//                perhitunganPenumpukanTranshipment.setOperation(LOAD);
//                perhitunganPenumpukanTranshipment.setContNo(serviceContLoad.getContNo());
//                perhitunganPenumpukanTranshipment.setMlo(serviceContLoad.getMlo());
//                perhitunganPenumpukanTranshipment.setActivityCode(penumpukanTranshipmentActivityCode);
//                perhitunganPenumpukanTranshipment.setJasaDermaga(BigDecimal.ZERO);
//                perhitunganPenumpukanTranshipment.setCurrCode(idrMasterCurrency.getCurrCode());
//                
//                if (serviceContLoad.getDangerous().equals("TRUE") && serviceContLoad.getDgLabel().equals("TRUE")) {
//                    penumpukanTranshipmentCharge = penumpukanTranshipmentCharge.multiply(BigDecimal.valueOf(2));
//                } else if (serviceContLoad.getDangerous().equals("TRUE") && serviceContLoad.getDgLabel().equals("FALSE")) {
//                    penumpukanTranshipmentCharge = penumpukanTranshipmentCharge.multiply(BigDecimal.valueOf(3));
//                }
//                
//                perhitunganPenumpukanTranshipment.setCharge(penumpukanTranshipmentCharge);
//                perhitunganPenumpukanTranshipment.setChargeM1(perhitunganPenumpukanTranshipment.getCharge().multiply(BigDecimal.valueOf(perhitunganPenumpukanTranshipment.getMasa1())));
//                perhitunganPenumpukanTranshipment.setChargeM2(perhitunganPenumpukanTranshipment.getCharge().multiply(BigDecimal.valueOf(perhitunganPenumpukanTranshipment.getMasa2())).multiply(BigDecimal.valueOf(2)));
//                perhitunganPenumpukanTranshipment.setTotalCharge(perhitunganPenumpukanTranshipment.getChargeM1().add(perhitunganPenumpukanTranshipment.getChargeM2()).add(perhitunganPenumpukanTranshipment.getJasaDermaga()));
//                
//                perhitunganPenumpukanTranshipmentFacade.edit(perhitunganPenumpukanTranshipment);
//            } else {
//                if (serviceContLoad.getIsExportImport().equals("TRUE")) {
//                    masterCurrency = usdMasterCurrency;
//                } else {
//                    masterCurrency = idrMasterCurrency;
//                }
//                
//                ServiceReceiving serviceReceiving;
//                ReceivingService receivingService;
//                
//                if (serviceContLoad.getIsChangeVessel().equals("TRUE")) {
//                    CancelLoadingService cancelLoadingService = cancelLoadingServiceFacade.findByNewPpkbAndContNo(serviceVessel.getNoPpkb(), serviceContLoad.getContNo());
//                    serviceReceiving = serviceReceivingFacadeRemote.findByNoPpkbAndContNoCancelLoading(cancelLoadingService.getPlanningVessel().getNoPpkb(), serviceContLoad.getContNo());
//                    receivingService = serviceReceiving.getReceivingService();
//                } else {
//                    serviceReceiving = serviceReceivingFacadeRemote.findByNoPpkbAndContNoNotCancelLoading(serviceVessel.getNoPpkb(), serviceContLoad.getContNo());
//                    receivingService = serviceReceiving.getReceivingService();
//                }
//                
//                Object cekStatusCancelLoading = cancelLoadingServiceFacade.findByPPKBandCont(serviceVessel.getNoPpkb(), serviceReceiving.getContNo(), Boolean.FALSE);
//                Object cekStatusExCancelLoading = cancelLoadingServiceFacade.findByPPKBandCont(serviceReceiving.getOld_ppkb(), serviceReceiving.getContNo(), Boolean.TRUE);
//                
//                if (cekStatusCancelLoading != null) {
//                    serviceReceiving.setStatusMuat("Batal Muat");
//                } else if (cekStatusExCancelLoading != null) {
//                    serviceReceiving.setStatusMuat("EX Batal Muat");
//                } else {
//                    serviceReceiving.setStatusMuat("Muat");
//                }
//                
//                int min = DateCalculator.countRangeInDays(serviceContLoad.getTransactionDate(), serviceReceiving.getTransactionDate()) + 1;
//                short masa1 = 0;
//                short masa2 = 0;
//                int masaFree = 1;
//                
//                if (min < (masa1ContainerRange + 1)) {
//                    if (min <= masaFreeRange) {
//                        masa1 = (short) masaFree;
//                    } else {
//                        masa1 = (short) (min - masaFreeRange + masaFree);
//                    }
//                } else {
//                    masa1 = (short) (masa1ContainerRange - masaFreeRange + masaFree);
//                    masa2 = (short) (min - masa1ContainerRange);
//                }
//                
//                receivingService.setMasa1(masa1);
//                receivingService.setMasa2(masa2);
//                receivingService.setRealPenumpukan(min);
//                
//                Integer idReefer = serviceReeferFacade.findValidasiPenumpukan(serviceVessel.getNoPpkb(), receivingService.getContNo(), ServiceReefer.OPERATION_LOAD);
//                
//                boolean isOverSize = false;
//                if ((!receivingService.getContStatus().equals("MTY") && receivingService.getOverSize().equals("TRUE")) || receivingService.getTwentyOneFeet().equals("TRUE")) {
//                    isOverSize = true;
//                }
//                String penumpukanActivityCode = masterActivityFacade.translatePenumpukanActivityCode(
//                        receivingService.getContStatus(),
//                        receivingService.getMasterContainerType().getIsoCode(),
//                        isOverSize,
//                        receivingService.getContSize(), idReefer > 0);
//                System.out.println("Tesssssss : =>"+penumpukanActivityCode);
//                RecapPenumpukan recapPenumpukan = new RecapPenumpukan();
//                recapPenumpukan.setContNo(receivingService.getContNo());
//                recapPenumpukan.setMlo(receivingService.getMlo());
//                recapPenumpukan.setNoPpkb(serviceVessel.getNoPpkb());
//                recapPenumpukan.setMasa2(receivingService.getMasa2());
//                recapPenumpukan.setActivityCode(penumpukanActivityCode);
//                recapPenumpukan.setOperation(RecapPenumpukan.LOAD);
//                recapPenumpukan.setTotalHari(min);
//                recapPenumpukan.setCurrCode(idrMasterCurrency.getCurrCode());
//                
//                if (serviceVessel.getIsLoadCyToCy()) {
//                    recapPenumpukan.setMasa1(receivingService.getMasa1());
//                } else if (serviceVessel.getIsLoadPortToPort()) {
//                    recapPenumpukan.setMasa1((short) (receivingService.getMasa1() - 1));
//                }
//                
//                BigDecimal penumpukanCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), penumpukanActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
//                
//                if (serviceReceiving.getDangerous().equals("TRUE") && serviceReceiving.getDgLabel().equals("TRUE")) {
//                    penumpukanCharge = penumpukanCharge.multiply(BigDecimal.valueOf(2));
//                } else if (serviceReceiving.getDangerous().equals("TRUE") && serviceReceiving.getDgLabel().equals("TRUE")) {
//                    penumpukanCharge = penumpukanCharge.multiply(BigDecimal.valueOf(3));
//                }
//                
//                recapPenumpukan.setChargeMasa1(penumpukanCharge.multiply(BigDecimal.valueOf(recapPenumpukan.getMasa1())));
//                recapPenumpukan.setChargeMasa2(penumpukanCharge.multiply(BigDecimal.valueOf(recapPenumpukan.getMasa2())).multiply(BigDecimal.valueOf(2)));
//                recapPenumpukan.setJasaDermaga(BigDecimal.ZERO);
//                recapPenumpukan.setTotalCharge(recapPenumpukan.getChargeMasa1().add(recapPenumpukan.getChargeMasa2()).add(recapPenumpukan.getJasaDermaga()));
//                
//                //save to database
//                serviceReceivingFacadeRemote.edit(serviceReceiving);
//                receivingServiceFacadeRemote.edit(receivingService);
//                recapPenumpukanFacade.edit(recapPenumpukan);
//                
//                boolean isSeling = false;
//                if (serviceContLoad.getIsSeling().equals("TRUE") ) {
//                    isSeling = true;
//                }
//                String dangerousClass = null;
//                if(serviceContLoad.getMasterCommodity() != null && serviceContLoad.getMasterCommodity().getMasterDangerousClass() != null)
//                    dangerousClass = serviceContLoad.getMasterCommodity().getMasterDangerousClass().getDangerousClass();
//                
//                String stevedoringActivityCode = masterActivityFacade.translateStevedoringActivityCode(
//                        serviceContLoad.getContStatus(),
//                        serviceContLoad.getContSize(),
//                        serviceContLoad.getCrane(),
//                        isSeling,
//                        serviceContLoad.getEquipmentGroup(),
//                        serviceContLoad.getDangerous().equals("TRUE"),
//                        serviceContLoad.getDgLabel().equals("TRUE"),
//                        dangerousClass,
//                        isSeling,
//                        serviceContLoad.getTwentyOneFeet().equals("TRUE"));
//                
//                if(stevedoringActivityCode == null){
//                    throw new RuntimeException("Cannot Find Activity Code");
//                }
//                String htActivityCode = masterActivityFacade.translateHaulageTruckingActivityCode(
//                        serviceContLoad.getContStatus(),
//                        serviceContLoad.getContSize(),
//                        serviceContLoad.getCrane(),
//                        isSeling,
//                        serviceContLoad.getEquipmentGroup(),
//                        serviceContLoad.getDangerous().equals("TRUE"),
//                        serviceContLoad.getDgLabel().equals("TRUE"),
//                        dangerousClass,
//                        isSeling,
//                        serviceContLoad.getTwentyOneFeet().equals("TRUE"));
//                
//                if(htActivityCode == null){
//                    throw new RuntimeException("Cannot Find Activity Code");
//                }
//                String otherHandlingActivityCode = masterActivityFacade.translateOtherHandlingChargesActivityCode(
//                        serviceContLoad.getContStatus(),
//                        serviceContLoad.getContSize(),
//                        serviceContLoad.getCrane(),
//                        isSeling,
//                        serviceContLoad.getEquipmentGroup(),
//                        serviceContLoad.getDangerous().equals("TRUE"),
//                        serviceContLoad.getDgLabel().equals("TRUE"),
//                        dangerousClass,
//                        isSeling,
//                        serviceContLoad.getTwentyOneFeet().equals("TRUE"));
//                
//                if(otherHandlingActivityCode == null){
//                    throw new RuntimeException("Cannot Find Activity Code");
//                }
//                
//                MasterActivity handlingMasterActivity = masterActivityFacade.find(stevedoringActivityCode);
//                serviceContLoad.setMasterActivity(handlingMasterActivity);
//                serviceContLoadFacade.edit(serviceContLoad);
//                
//                if (serviceVessel.getIsLoadPortToPort()) {
//                    continue;
//                }
//                
//                isOverSize = false;
//                if (serviceContLoad.getOverSize().equals("TRUE")|| serviceContLoad.getIsSeling().equals("TRUE")) {
//                    isOverSize = true;
//                }
//                String upahBuruhActivityCode = masterActivityFacade.translateUbahBuruhHandlingActivityCode(
//                        serviceContLoad.getContStatus(),
//                        serviceContLoad.getContSize(),
//                        isOverSize
//                );
//                MasterActivity upahBuruhMasterActivity = masterActivityFacade.find(upahBuruhActivityCode);
//                BigDecimal upahBuruhCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), upahBuruhActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
//                RecapUpahBuruh recapUpahBuruh = recapUpahBuruhFacadeLocal.findByBentuk3Constraint(
//                        serviceContLoad.getContSize(),
//                        serviceContLoad.getContStatus(),
//                        serviceContLoad.getCrane(),
//                        serviceContLoad.getIsExportImport(),
//                        serviceContLoad.getOverSize(),
//                        serviceContLoad.getIsSeling(),
//                        serviceContLoad.getMasterContainerType().getMasterContainerTypeInGeneral().getId(),
//                        upahBuruhActivityCode,
//                        RecapUpahBuruh.OPERATION_LOAD,
//                        RecapUpahBuruh.ACTIVITY_LOAD,
//                        serviceVessel.getNoPpkb(),
//                        idrMasterCurrency.getCurrCode(),
//                        serviceContLoad.getTwentyOneFeet(),
//                        upahBuruhCharge);
//                
//                if (recapUpahBuruh != null) {
//                    recapUpahBuruh.setAmount(recapUpahBuruh.getAmount() + 1);
//                } else {
//                    recapUpahBuruh = new RecapUpahBuruh();
//                    recapUpahBuruh.setIsExportImport(serviceContLoad.getIsExportImport());
//                    recapUpahBuruh.setSling(serviceContLoad.getIsSeling());
//                    recapUpahBuruh.setCrane(serviceContLoad.getCrane());
//                    recapUpahBuruh.setContSize(serviceContLoad.getContSize());
//                    recapUpahBuruh.setContStatus(serviceContLoad.getContStatus());
//                    recapUpahBuruh.setTwentyOneFeet(serviceContLoad.getTwentyOneFeet());
//                    recapUpahBuruh.setOpenDoor(serviceContLoad.getOverSize());
//                    recapUpahBuruh.setContainerType(serviceContLoad.getMasterContainerType().getMasterContainerTypeInGeneral());
//                    recapUpahBuruh.setOperation(RecapUpahBuruh.OPERATION_LOAD);
//                    recapUpahBuruh.setActivity(RecapUpahBuruh.ACTIVITY_LOAD);
//                    recapUpahBuruh.setServiceVessel(serviceVessel);
//                    recapUpahBuruh.setMasterActivity(upahBuruhMasterActivity);
//                    recapUpahBuruh.setCharge(upahBuruhCharge);
//                    recapUpahBuruh.setAmount(1);
//                    recapUpahBuruh.setMasterCurrency(idrMasterCurrency);
//                }
//                
//                recapUpahBuruh.setTotalCharge(recapUpahBuruh.getCharge().multiply(BigDecimal.valueOf(recapUpahBuruh.getAmount())));
//                
//                //[1XM]
//                BigDecimal stevedoringCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), stevedoringActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
//                
//                RecapHandlingLoad recapStevedoringLoad = new RecapHandlingLoad();
//                recapStevedoringLoad.setContNo(serviceContLoad.getContNo());
//                recapStevedoringLoad.setNoPpkb(serviceVessel.getNoPpkb());
//                recapStevedoringLoad.setActivityCode(stevedoringActivityCode);
//                recapStevedoringLoad.setCrane(serviceContLoad.getCrane());
//                recapStevedoringLoad.setContSize(serviceContLoad.getContSize());
//                recapStevedoringLoad.setTwentyOneFeet(serviceContLoad.getTwentyOneFeet());
//                recapStevedoringLoad.setContStatus(serviceContLoad.getContStatus());
//                recapStevedoringLoad.setIsExportImport(serviceContLoad.getIsExportImport());
//                recapStevedoringLoad.setSling(serviceContLoad.getIsSeling());
//                recapStevedoringLoad.setOpenDoor(serviceContLoad.getOverSize());
//                recapStevedoringLoad.setContainerType(serviceContLoad.getMasterContainerType().getMasterContainerTypeInGeneral());
//                recapStevedoringLoad.setCharge(stevedoringCharge);
//                recapStevedoringLoad.setAmount(1);
//                recapStevedoringLoad.setCurrCode(masterCurrency.getCurrCode());
//                recapStevedoringLoad.setTotalCharge(recapStevedoringLoad.getCharge());
//                
//                recapHandlingLoadFacade.edit(recapStevedoringLoad);
//                
//                BigDecimal htCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), htActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
//                
//                
//                RecapHandlingLoad recapHtLoad = new RecapHandlingLoad();
//                recapHtLoad.setContNo(serviceContLoad.getContNo());
//                recapHtLoad.setNoPpkb(serviceVessel.getNoPpkb());
//                recapHtLoad.setActivityCode(htActivityCode);
//                recapHtLoad.setCrane(serviceContLoad.getCrane());
//                recapHtLoad.setContSize(serviceContLoad.getContSize());
//                recapHtLoad.setTwentyOneFeet(serviceContLoad.getTwentyOneFeet());
//                recapHtLoad.setContStatus(serviceContLoad.getContStatus());
//                recapHtLoad.setIsExportImport(serviceContLoad.getIsExportImport());
//                recapHtLoad.setSling(serviceContLoad.getIsSeling());
//                recapHtLoad.setOpenDoor(serviceContLoad.getOverSize());
//                recapHtLoad.setContainerType(serviceContLoad.getMasterContainerType().getMasterContainerTypeInGeneral());
//                recapHtLoad.setCharge(htCharge);
//                recapHtLoad.setAmount(1);
//                recapHtLoad.setCurrCode(masterCurrency.getCurrCode());
//                recapHtLoad.setTotalCharge(recapHtLoad.getCharge());
//                
//                recapHandlingLoadFacade.edit(recapHtLoad);
//                
//                BigDecimal otherHandlingCharge = masterTarifContFacade.findChargeAfterDiscount(
//                        masterCurrency.getCurrCode(),
//                        otherHandlingActivityCode,
//                        serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
//                
//                RecapHandlingLoad recapOtherHandlingLoad = new RecapHandlingLoad();
//                recapOtherHandlingLoad.setContNo(serviceContLoad.getContNo());
//                recapOtherHandlingLoad.setNoPpkb(serviceVessel.getNoPpkb());
//                recapOtherHandlingLoad.setActivityCode(otherHandlingActivityCode);
//                recapOtherHandlingLoad.setCrane(serviceContLoad.getCrane());
//                recapOtherHandlingLoad.setContSize(serviceContLoad.getContSize());
//                recapOtherHandlingLoad.setTwentyOneFeet(serviceContLoad.getTwentyOneFeet());
//                recapOtherHandlingLoad.setContStatus(serviceContLoad.getContStatus());
//                recapOtherHandlingLoad.setIsExportImport(serviceContLoad.getIsExportImport());
//                recapOtherHandlingLoad.setSling(serviceContLoad.getIsSeling());
//                recapOtherHandlingLoad.setOpenDoor(serviceContLoad.getOverSize());
//                recapOtherHandlingLoad.setContainerType(serviceContLoad.getMasterContainerType().getMasterContainerTypeInGeneral());
//                recapOtherHandlingLoad.setCharge(otherHandlingCharge);
//                recapOtherHandlingLoad.setAmount(1);
//                recapOtherHandlingLoad.setCurrCode(masterCurrency.getCurrCode());
//                recapOtherHandlingLoad.setTotalCharge(recapOtherHandlingLoad.getCharge());
//                
//                recapHandlingLoadFacade.edit(recapOtherHandlingLoad);
//                
//                recapUpahBuruhFacadeLocal.edit(recapUpahBuruh);
//            }
            //[/1XM]
        }
        //[/3XM | 4XM | 4YM]
    }
    
    private void calculateAirKapal(MasterCurrency masterCurrency, ServiceVessel serviceVessel) {
        String airKapalActivityCode = masterActivityFacade.translateAirKapalActivityCode();
        MasterActivity airKapalMasterActivity = masterActivityFacade.find(airKapalActivityCode);
        BigDecimal airKapalCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), airKapalActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
        
        RecapAirKapal recapAirKapal = new RecapAirKapal();
        recapAirKapal.setServiceVessel(serviceVessel);
        recapAirKapal.setMasterActivity(airKapalMasterActivity);
        recapAirKapal.setMasterCurrency(masterCurrency);
        recapAirKapal.setAmount(serviceVessel.getAirKapal());
        recapAirKapal.setCharge(airKapalCharge);
        recapAirKapal.setTotalCharge(airKapalCharge.multiply(new BigDecimal(recapAirKapal.getAmount())));
        recapAirKapalFacadeLocal.create(recapAirKapal);
    }

    private void calculateHatchMoves(ServiceVessel serviceVessel, MasterCurrency masterCurrency, MasterCurrency idrMasterCurrency) {
        //[9XB]
        List<ServiceHatchMoves> serviceHatchMoves = serviceHatchMovesFacade.findByNoPpkbAndStatusPaid(serviceVessel.getNoPpkb());
        
        for (ServiceHatchMoves serviceHatchMove : serviceHatchMoves) {
            String hatchMoveActivityCode = masterActivityFacade.translateHatchMoveActivityCode(serviceHatchMove.getCrane());
            MasterActivity hatchMoveMasterActivity = masterActivityFacade.find(hatchMoveActivityCode);
            BigDecimal hatchMoveCharge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), hatchMoveActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
            
            serviceHatchMove.setMasterActivity(hatchMoveMasterActivity);
            serviceHatchMovesFacade.edit(serviceHatchMove);
            
            RecapHatchMove recapHatchMove = recapHatchMoveFacade.findByBentuk3ConstraintTarakan(serviceVessel.getNoPpkb(), serviceHatchMove.getCrane(), serviceHatchMove.getOperation());
            
            if (recapHatchMove == null) {
                recapHatchMove = new RecapHatchMove();
                recapHatchMove.setCurrCode(masterCurrency.getCurrCode());
                recapHatchMove.setActivityCode(hatchMoveActivityCode);
                recapHatchMove.setCrane(serviceHatchMove.getCrane());
                recapHatchMove.setNoPpkb(serviceHatchMove.getNoPpkb());
                recapHatchMove.setOperation(serviceHatchMove.getOperation());
                recapHatchMove.setCharge(hatchMoveCharge);
                recapHatchMove.setAmount(1);
            } else {
                recapHatchMove.setAmount(recapHatchMove.getAmount() + 1);
            }
            
            recapHatchMove.setTotalCharge(recapHatchMove.getCharge().multiply(BigDecimal.valueOf(recapHatchMove.getAmount())));
            recapHatchMoveFacade.edit(recapHatchMove);
            
            String upahBuruhHatchMoveActivityCode = masterActivityFacade.translateUpahBuruhHatchMoveActivityCode();
            MasterActivity upahBuruhHatchMoveMasterActivity = masterActivityFacade.find(upahBuruhHatchMoveActivityCode);
            BigDecimal upahBuruhHatchMoveCharge = masterTarifContFacade.findChargeAfterDiscount(idrMasterCurrency.getCurrCode(), upahBuruhHatchMoveActivityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
            RecapUpahBuruhHatchMove recapUpahBuruhHatchMove = recapUpahBuruhHatchMoveFacadeLocal.findByBentuk3Constraint(serviceVessel.getNoPpkb(), serviceHatchMove.getCrane());
            
            if (recapUpahBuruhHatchMove == null) {
                recapUpahBuruhHatchMove = new RecapUpahBuruhHatchMove();
                recapUpahBuruhHatchMove.setMasterCurrency(idrMasterCurrency);
                recapUpahBuruhHatchMove.setMasterActivity(upahBuruhHatchMoveMasterActivity);
                recapUpahBuruhHatchMove.setCrane(serviceHatchMove.getCrane());
                recapUpahBuruhHatchMove.setServiceVessel(serviceVessel);
                recapUpahBuruhHatchMove.setCharge(upahBuruhHatchMoveCharge);
                recapUpahBuruhHatchMove.setAmount(1);
            } else {
                recapUpahBuruhHatchMove.setAmount(recapUpahBuruhHatchMove.getAmount() + 1);
            }
            
            recapUpahBuruhHatchMove.setTotalCharge(recapUpahBuruhHatchMove.getCharge().multiply(BigDecimal.valueOf(recapUpahBuruhHatchMove.getAmount())));
            recapUpahBuruhHatchMoveFacadeLocal.edit(recapUpahBuruhHatchMove);
        }
        //[/9XB]
    }

    private void createOtherPackageRecap(String mainActivity, String tarifType, MasterCurrency masterCurrency, ServiceVessel serviceVessel, ServiceContDischarge serviceContDischarge, String activityCode) {
        BigDecimal charge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), activityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
        
        RecapOtherPackage recapOtherPackage = new RecapOtherPackage();
        recapOtherPackage.setContNo(serviceContDischarge.getContNo());
        recapOtherPackage.setCrane(serviceContDischarge.getCrane());
        recapOtherPackage.setContSize(serviceContDischarge.getContSize());
        recapOtherPackage.setContStatus(serviceContDischarge.getContStatus());
        recapOtherPackage.setTwentyOneFeet(serviceContDischarge.getTwentyOneFeet());
        recapOtherPackage.setIsExportImport(serviceContDischarge.getIsImport());
        recapOtherPackage.setSling(serviceContDischarge.getIsSeling());
        recapOtherPackage.setOpenDoor(serviceContDischarge.getOverSize());
        recapOtherPackage.setContainerType(serviceContDischarge.getMasterContainerType().getMasterContainerTypeInGeneral());
        recapOtherPackage.setNoPpkb(serviceVessel.getNoPpkb());
        recapOtherPackage.setActivityCode(activityCode);
        recapOtherPackage.setMainActivity(mainActivity);
        recapOtherPackage.setTarifType(tarifType);
        recapOtherPackage.setCharge(charge);
        recapOtherPackage.setAmount(1);
        recapOtherPackage.setCurrCode(masterCurrency.getCurrCode());
        recapOtherPackage.setTotalCharge(recapOtherPackage.getCharge().multiply(BigDecimal.valueOf(recapOtherPackage.getAmount())));
        recapOtherPackageFacade.create(recapOtherPackage);
    }

    public void handleRollback(ServiceVessel serviceVessel) {
        try {
            recapAirKapalFacadeLocal.deleteByNoPpkb(serviceVessel.getNoPpkb());
            recapReeferLoadFacade.deleteByNoPpkb(serviceVessel.getNoPpkb());

            // receiving service
            List<ServiceContLoad> serviceContLoads = serviceContLoadFacade.findContainersThatHaveBeenLoaded(serviceVessel.getNoPpkb());
            //[3XM | 4XM | 4YM]
            for (ServiceContLoad serviceContLoad: serviceContLoads) {
                 if (serviceContLoad.getIsChangeVessel().equals("TRUE")) {
                    CancelLoadingService cancelLoadingService = cancelLoadingServiceFacade.findByNewPpkbAndContNo(serviceVessel.getNoPpkb(), serviceContLoad.getContNo());
                    ServiceReceiving serviceReceiving = serviceReceivingFacadeRemote.findByNoPpkbAndContNoCancelLoading(cancelLoadingService.getPlanningVessel().getNoPpkb(), serviceContLoad.getContNo());
                    ReceivingService receivingService = serviceReceiving.getReceivingService();
                    receivingService.setMasa1(null);
                    receivingService.setMasa2(null);
                    receivingService.setRealPenumpukan(null);
                    receivingServiceFacadeRemote.edit(receivingService);
                } else if (serviceContLoad.getIsTranshipment().equals("TRUE")) {

                } else {
                    ServiceReceiving serviceReceiving = serviceReceivingFacadeRemote.findByNoPpkbAndContNoNotCancelLoading(serviceVessel.getNoPpkb(), serviceContLoad.getContNo());
                    if (serviceReceiving!=null) {
                        ReceivingService receivingService = serviceReceiving.getReceivingService();
                        receivingService.setMasa1(null);
                        receivingService.setMasa2(null);
                        receivingService.setRealPenumpukan(null);
                        receivingServiceFacadeRemote.edit(receivingService);
                    }
                }
            }
            
            recapPenumpukanFacade.deleteByNoPpkb(serviceVessel.getNoPpkb());
            recapPenumpukanUcFacadeLocal.deleteByNoPpkb(serviceVessel.getNoPpkb());

            // receiving service uc
            ucReceivingServiceFacade.removeMasaByNoPpkbAndNotTruckLoosing(serviceVessel.getNoPpkb());

            // delete recap hatch move
            recapHatchMoveFacade.deleteByNoPpkb(serviceVessel.getNoPpkb());
            recapUpahBuruhHatchMoveFacadeLocal.deleteByNoPpkb(serviceVessel.getNoPpkb());

            // rollback service shifting
            serviceShiftingFacade.removeMasterActivityByNoPpkbAndHasPaid(serviceVessel.getNoPpkb());
            recapShiftingFacade.deleteByNoPpkb(serviceVessel.getNoPpkb());

            // rollback service shifting uc
            ucShiftingServiceFacade.removeActivityCodeByNoPpkbAndHasPaid(serviceVessel.getNoPpkb());
            recapHandlingUcFacadeLocal.deleteByNoPpkb(serviceVessel.getNoPpkb());
            recapUpahBuruhUcFacadeLocal.deleteByNoPpkb(serviceVessel.getNoPpkb());

            // rollback service cont transhipment
            List<Object[]> findRekap = serviceContTranshipmentFacade.findRekap(serviceVessel.getNoPpkb());
            for (Object[] ot : findRekap) {
                ServiceContTranshipment serviceContTranshipment = serviceContTranshipmentFacade.find(ot[0]);
                serviceContTranshipment.setMasterActivity(null);
                serviceContTranshipmentFacade.edit(serviceContTranshipment);
            }
            // rollback change status transhipment
            recapTranshipmentFacade.deleteByNoPpkb(serviceVessel.getNoPpkb());

            // update service cont discharge
            serviceContDischargeFacade.removeMasterActivityThatHaveReachedCY(serviceVessel.getNoPpkb());
            recapPenumpukanFacade.deleteThatHaveReachedCY(serviceVessel.getNoPpkb());
            recapHandlingDischargeFacade.deleteByNoPpkb(serviceVessel.getNoPpkb());

            // update service cont load
            serviceContLoadFacade.removeMasterActivityThatHaveBeenLoaded(serviceVessel.getNoPpkb());
            recapHandlingLoadFacade.deleteByNoPpkb(serviceVessel.getNoPpkb());
            recapUpahBuruhFacadeLocal.deleteByNoPpkb(serviceVessel.getNoPpkb());

            //service con load yang transhipment
            perhitunganPenumpukanTranshipmentFacade.deleteByNoPpkb(serviceVessel.getNoPpkb());

            recapOtherPackageFacade.deleteByNoPpkb(serviceVessel.getNoPpkb());

            PlanningVessel planningVessel = planningVesselFacade.find(serviceVessel.getNoPpkb());
            planningVessel.setStatus("Servicing");
            serviceVessel.setStatus("Servicing");
            serviceVesselFacade.edit(serviceVessel);
            planningVesselFacade.edit(planningVessel);

            //Start RecapJasaDermagaUc
//            recapJasaDermagaUcFacadeRemote.deleteByNoPpkb(serviceVessel.getNoPpkb());
            //End RecapJasaDermagaUc
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }
    }

    private void createOtherPackageRecap(String mainActivity, String tarifType, MasterCurrency masterCurrency, ServiceVessel serviceVessel, ServiceContLoad serviceContLoad, String activityCode) {
        BigDecimal charge = masterTarifContFacade.findChargeAfterDiscount(masterCurrency.getCurrCode(), activityCode, serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
        
        RecapOtherPackage recapOtherPackage = new RecapOtherPackage();
        recapOtherPackage.setContNo(serviceContLoad.getContNo());
        recapOtherPackage.setCrane(serviceContLoad.getCrane());
        recapOtherPackage.setContSize(serviceContLoad.getContSize());
        recapOtherPackage.setContStatus(serviceContLoad.getContStatus());
        recapOtherPackage.setTwentyOneFeet(serviceContLoad.getTwentyOneFeet());
        recapOtherPackage.setIsExportImport(serviceContLoad.getIsExportImport());
        recapOtherPackage.setSling(serviceContLoad.getIsSeling());
        recapOtherPackage.setOpenDoor(serviceContLoad.getOverSize());
        recapOtherPackage.setContainerType(serviceContLoad.getMasterContainerType().getMasterContainerTypeInGeneral());
        recapOtherPackage.setNoPpkb(serviceVessel.getNoPpkb());
        recapOtherPackage.setActivityCode(activityCode);
        recapOtherPackage.setMainActivity(mainActivity);
        recapOtherPackage.setTarifType(tarifType);
        recapOtherPackage.setCharge(charge);
        recapOtherPackage.setAmount(1);
        recapOtherPackage.setCurrCode(masterCurrency.getCurrCode());
        recapOtherPackage.setTotalCharge(recapOtherPackage.getCharge().multiply(BigDecimal.valueOf(recapOtherPackage.getAmount())));
        recapOtherPackageFacade.create(recapOtherPackage);
    }
}

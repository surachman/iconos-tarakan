/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.DeliveryServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.EquipmentFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterYardCoordinatFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PerhitunganHandlingDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PerhitunganLiftServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PerhitunganPenumpukanFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PluggingReeferServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ReeferDischargeServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ReeferMonitoringFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceContDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceContTranshipmentFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceDeliveryFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceGateDeliveryFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceReeferFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceShiftingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.model.db.DeliveryService;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PerhitunganHandlingDischarge;
import com.pelindo.ebtos.model.db.PerhitunganLiftService;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukan;
import com.pelindo.ebtos.model.db.PluggingReeferService;
import com.pelindo.ebtos.model.db.ReeferDischargeService;
import com.pelindo.ebtos.model.db.ReeferMonitoring;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceDelivery;
import com.pelindo.ebtos.model.db.ServiceGateDelivery;
import com.pelindo.ebtos.model.db.ServiceReefer;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author Dyware-Dev01
 */
@Stateless
public class ServiceContDischargeFacade extends AbstractFacade<ServiceContDischarge> implements ServiceContDischargeFacadeLocal, ServiceContDischargeFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;
    
    @EJB
    private DeliveryServiceFacadeLocal deliveryServiceFacade;
    @EJB
    private ReeferDischargeServiceFacadeLocal reeferDischargeServiceFacade;
    @EJB
    private PluggingReeferServiceFacadeLocal pluggingReeferServiceFacade;
    @EJB
    private ServiceDeliveryFacadeLocal serviceDeliveryFacade;
    @EJB
    private ServiceGateDeliveryFacadeLocal serviceGateDeliveryFacade;
    @EJB
    private ServiceReeferFacadeLocal serviceReeferFacade;
    @EJB
    private ServiceShiftingFacadeLocal serviceShiftingFacade;
    @EJB
    private ReeferMonitoringFacadeLocal reeferMonitoringFacade;
    @EJB
    private ServiceContTranshipmentFacadeLocal serviceContTranshipmentFacade;
    @EJB
    private EquipmentFacadeLocal equipmentFacade;
    @EJB
    private MasterYardCoordinatFacadeLocal masterYardCoordinatFacade;
    @EJB 
    private PerhitunganLiftServiceFacadeLocal perhitunganLiftServiceFacadeLocal;
    @EJB 
    private PerhitunganPenumpukanFacadeLocal perhitunganPenumpukanFacadeLocal;
    @EJB 
    private PerhitunganHandlingDischargeFacadeLocal perhitunganHandlingDischargeFacadeLocal;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceContDischargeFacade() {
        super(ServiceContDischarge.class);
    }

    public Object[] findByNoPpkb(String no_ppkb, String cont_no, String pos) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceContDischarge.Native.findByNoPpkb").setParameter(1, no_ppkb).setParameter(2, cont_no).setParameter(3, pos).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }

    public ServiceContDischarge findByNoPpkbContNoAndPosition(String noPpkb, String contNo, String position) {
        try {
            return (ServiceContDischarge) getEntityManager().createNamedQuery("ServiceContDischarge.findByNoPpkbContNoAndPosition").setParameter("noPpkb", noPpkb).setParameter("contNo", contNo).setParameter("position", position).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public ServiceContDischarge findByContNoAndPosition(String contNo, String position) {
        try {
            return (ServiceContDischarge) getEntityManager().createNamedQuery("ServiceContDischarge.findByContNoAndPosition")
                    .setParameter("contNo", contNo)
                    .setParameter("position", position)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }



    public Object[] findByContNo(String cont_no, String pos) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceContDischarge.Native.findByContNo").setParameter(1, cont_no).setParameter(2, pos).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }

    public Object[] findServiceContDischargesByContAndPpkb(String ppkb, String cont_no) {
        Object[] temp = new Object[6];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesByContAndPpkb").setParameter(1, ppkb).setParameter(2, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }

    @Override
    public List<Object[]> findServiceContDischargesByPpkb(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesByPpkb").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findServiceContDischargesStatusSatu(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesStatusSatu").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> exDischargeStocksPerCustomer(String custCode) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.exDischargeStocksPerCustomer")
                .setParameter(1, custCode)
                .getResultList()
        );
    }

    public Object[] exDischargeStocksPerCustomer_summary(String custCode) {
        try {
            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceContDischarge.Native.exDischargeStocksPerCustomer_summary")
                    .setParameter(1, custCode)
                    .getSingleResult()
            );
        } catch (NoResultException nre) {
            return new Object[] {custCode, 0, 0, 0, 0};
        }
    }

    public List<Object[]> findServiceContDischarges() {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischarges").getResultList()
        );
    }

    //ServiceContDischarge.Native.findServiceContDischargesConfirm
    public List<Object[]> findServiceContDischargesConfirm(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesConfirm").setParameter(1, no_ppkb).getResultList()
        );
    }

    //ServiceContDischarge.Native.findServiceContDischargesSelect
    public List<Object[]> findServiceContDischargesSelect(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesSelect").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findDeliverableContainersByPpkb(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findDeliverableContainersByPpkb").setParameter(1, no_ppkb).getResultList()
        );
    }
    
    //penambahan perubahan bentuk form by ade chelsea tanggal 17 maret 2014
    public List<Object[]> findDeliverableContainersByPpkb2() {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findDeliverableContainersByPpkb2").getResultList()
        );
    }

    public List<Object[]> findForRecapDischarge(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesForRekapDischarge").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findServiceContDischargesByPpkbDeliveryTrue(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesByPpkbDeliveryTrue").setParameter(1, no_ppkb).getResultList()
        );
    }
    //ServiceContDischarge.Native.updateServiceContDischargesForDeleteAll

    public int updateServiceContDischargesForDeleteAll(int id) {
        return getEntityManager().createNamedQuery("ServiceContDischarge.Native.updateServiceContDischargesForDeleteAll").setParameter(1, id).executeUpdate();
    }

    public List<Object[]> findServiceContDischargesReefer(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesReefer").setParameter(1, no_ppkb).getResultList()
        );
    }

    /**
     *
     * @param contNo
     * @return
     *      0 = scd.id,
     *      1 = scd.cont_no,
     *      2 = scd.no_ppkb,
     *      3 = scd.y_slot,
     *      4 = scd.y_row,
     *      5 = scd.y_tier,
     *      6 = scd.block,
     *      7 = scd.is_behandle,
     *      8 = scd.is_cfs,
     *      9 = scd.is_inspection,
     *      10 = asal,
     *      11 = vessel,
     *      12 = scd.cont_size,
     *      13 = scd.cont_status,
     *      14 = scd.over_size,
     *      15 = scd.dangerous,
     *      16 = mcd.name condition
     */
    public Object[] findDeliverableCont(String contNo) {
        try {
            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceContDischarge.Native.findDeliverableCont")
                    .setParameter(1, contNo)
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<Object[]> findServiceContDischargesConfirmServiced(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesConfirmServiced").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findServiceContDischargesSelectServiced(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesSelectServiced").setParameter(1, no_ppkb).getResultList()
        );
    }

    public int updateServiceContDischargesForDeleteAllPlacement(int id) {
        return getEntityManager().createNamedQuery("ServiceContDischarge.Native.updateServiceContDischargesForDeleteAllPlacement").setParameter(1, id).executeUpdate();
    }

    public Object[] findContainerReefer(String cont_no) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceContDischarge.Native.findContainerReefer").setParameter(1, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }

    public List<Object[]> findServiceContDischargesDeliveryService(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesDeliveryService").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findServiceContDischargesAngsurService(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesAngsurService").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findServiceContDischargesStrippingService(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesStrippingService").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findServiceContDischargesStuffingService(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesStuffingService").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findServiceContDischargesBehandleService(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesBehandleService").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findServiceContDischargesMovement() {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesMovement").getResultList()
            );
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findServiceContDischargesMovementBehandle() {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesMovementBehandle").getResultList()
            );
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findServiceContDischargesMovementInspection() {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesMovementInspection").getResultList()
            );
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findDischargeMonitoringByPPKB(String no_ppkb, String pos) {
        if(pos != null)
            return objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceContDischarge.Native.findDischargeMonitoringByPPKB").setParameter(1, no_ppkb).setParameter(2, pos).getResultList()
            );
        else
            return objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceContDischarge.Native.findDischargeMonitoringByPPKBAll").setParameter(1, no_ppkb).getResultList()
            );
    }

    public List<Object[]> findDischargeMonitoringByPPKB1(String no_ppkb, String pos, boolean del) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findDischargeMonitoringByPPKB1").setParameter(1, no_ppkb).setParameter(2, pos).setParameter(3, del ? "TRUE" : "FALSE").getResultList()
        );
    }

    public List<Object[]> findDischargeMonitoringByPPKB2(String no_ppkb, boolean del) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findDischargeMonitoringByPPKB2").setParameter(1, no_ppkb).setParameter(2, del ? "TRUE" : "FALSE").getResultList()
        );
    }

    public List<Object[]> findDischargeMonitoringByPPKBFront(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findDischargeMonitoringByPPKBFront").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findUcDischargeMonitoringByPPKB(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findUcDischargeMonitoringByPPKB").setParameter(1, no_ppkb).getResultList()
        );
    }

    public Object[] findByContNoMov(String cont_no, String pos, boolean is_delivery, boolean is_behandle, boolean is_cfs, boolean is_inspection) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceContDischarge.Native.findByContNoMov").setParameter(1, cont_no).setParameter(2, pos)
                            .setParameter(3, is_delivery ? "TRUE" : "FALSE")
                            .setParameter(4, is_behandle ? "TRUE" : "FALSE")
                            .setParameter(5, is_cfs ? "TRUE" : "FALSE")
                            .setParameter(6, is_inspection ? "TRUE" : "FALSE").getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findByPpkbAndContNo(String no_ppkb, String cont_no) {
        Object[] temp = new Object[7];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceContDischarge.Native.findByPpkbAndContNo").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findReeferDischargeService(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findReeferDischargeService").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findHandlingDischargeMonitoring(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findHandlingDischargeMonitoring").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findRecapPenumpukan(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceContDischarge.Native.findRecapPenumpukan").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findServiceContDischargeByPpkbAndContNoFront(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargeByPpkbAndContNoFront").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public ServiceContDischarge findMovableContainer(String contNo) {
        try {
            return (ServiceContDischarge) getEntityManager().createNamedQuery("ServiceContDischarge.findMovableContainer")
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public ServiceContDischarge findDischargableContainer(String noPpkb, String contNo) {
        try {
            Object[] result = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceContDischarge.Native.findDischargableContainer")
                    .setParameter(1, noPpkb)
                    .setParameter(2, contNo)
                    .getSingleResult()
            );

            return find((Integer) result[4]);
        } catch (NoResultException ex) {
            return null;
        }
    }

    public ServiceContDischarge findMovableOffContainer(String contNo) {
        try {
            return (ServiceContDischarge) getEntityManager().createNamedQuery("ServiceContDischarge.findMovableOffContainer")
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<Object[]> findServiceContDischargesDeliveryServiceByBL(String bl, String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesDeliveryServiceByBL").setParameter(1, bl).setParameter(2, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findServiceContDischargesStrippingServiceByBL(String bl, String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesStrippingServiceByBL").setParameter(1, bl).setParameter(2, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findServiceContDischargesStuffingServiceByBL(String bl, String no_ppkb) {
        return objectsDecimalsToObjectsInts( 
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargesStuffingServiceByBL").setParameter(1, bl).setParameter(2, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findReeferDischargeServiceByBL(String bl, String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceContDischarge.Native.findReeferDischargeServiceByBL").setParameter(1, bl).setParameter(2, no_ppkb).getResultList();
    }

    public List<Object[]> findServiceContDischargeUnplacement(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceContDischargeUnplacement").setParameter(1, no_ppkb).getResultList();
    }

    public Object[] findContMovementMobile(String cont_no, String pos, Boolean is_delivery) {
        Object[] temp = new Object[10];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("ServiceContDischarge.Native.findContMovementMobile").setParameter(1, cont_no).setParameter(2, pos)
                    .setParameter(3, is_delivery ? "TRUE" : "FALSE").getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }

    public List<Object[]> findContainerDetails(String no_ppkb, String cont_no) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findContainerDetails").setParameter(1, no_ppkb).setParameter(2, cont_no).getResultList()
        );
    }

    public List<Object[]> findContainerDetailsPPKBnull(String cont_no) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findContainerDetailsPPKBnull").setParameter(1, cont_no).getResultList()
        );
    }

    public List<Object[]> findContainerDetailsCONTnull(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findContainerDetailsCONTnull").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findServiceCondischargeToLoads(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceCondischargeToLoads").setParameter(1, no_ppkb).getResultList()
        );
    }
    //

    public List<String> findContainerNumbers(String no_Ppkb, String contNo) {
        String no_ppkb = "%" + no_Ppkb.toUpperCase() + "%";
        String cont_no = "%" + contNo.toUpperCase() + "%";
        return getEntityManager().createNamedQuery("ServiceContDischarge.Native.findContainerNumbers").setParameter(1, no_ppkb).setParameter(2, cont_no).getResultList();
    }

    public List<Integer> findContIds(String no_ppkb, String cont_no, String tablename) {
        return decimalsToInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findContIds").setParameter(1, no_ppkb).setParameter(2, cont_no).setParameter(3, tablename).getResultList()
        );
    }

    public List<String> findPKs(String pk_field, String no_ppkb, String cont_no, String tablename) {
        return getEntityManager().createNamedQuery("ServiceContDischarge.Native.findPKs").setParameter(1, pk_field).setParameter(2, no_ppkb).setParameter(3, cont_no).setParameter(4, tablename).getResultList();
    }

    public void saveMasterContainer(ServiceContDischarge serviceContDischarge, String container) {
        //delivery_service
        List<String> PKs = findPKs("job_slip", serviceContDischarge.getServiceVessel().getNoPpkb(), container, "delivery_service");
        for (int i = 0; i < PKs.size(); i++) {
            DeliveryService deliveryService = deliveryServiceFacade.find(PKs.get(i));
            deliveryService.setContNo(serviceContDischarge.getContNo());
            deliveryService.setMlo(serviceContDischarge.getMlo());
            deliveryService.setContSize(serviceContDischarge.getContSize());
            deliveryService.setMasterContainerType(serviceContDischarge.getMasterContainerType());
            deliveryService.setContStatus(serviceContDischarge.getContStatus());
            deliveryService.setMasterCommodity(serviceContDischarge.getMasterCommodity());
            deliveryService.setTwentyOneFeet(serviceContDischarge.getTwentyOneFeet());
            deliveryService.setContGross(serviceContDischarge.getContGross());
            deliveryService.setSealNo(serviceContDischarge.getSealNo());
            deliveryService.setOverSize(serviceContDischarge.getOverSize());
            deliveryService.setDg(serviceContDischarge.getDangerous());
            deliveryService.setDgLabel(serviceContDischarge.getDgLabel());
            deliveryServiceFacade.edit(deliveryService);
        }
        //reefer_discharge_service
        PKs = findPKs("job_slip", serviceContDischarge.getServiceVessel().getNoPpkb(), container, "reefer_discharge_service");
        for (int i = 0; i < PKs.size(); i++) {
            ReeferDischargeService reeferDischargeService = reeferDischargeServiceFacade.find(PKs.get(i));
            reeferDischargeService.setContNo(serviceContDischarge.getContNo());
            reeferDischargeService.setMlo(serviceContDischarge.getMlo());
            reeferDischargeService.setContSize(serviceContDischarge.getContSize());
            reeferDischargeService.setMasterContainerType(serviceContDischarge.getMasterContainerType());
            reeferDischargeService.setContStatus(serviceContDischarge.getContStatus());
            reeferDischargeService.setMasterCommodity(serviceContDischarge.getMasterCommodity());
            reeferDischargeService.setContGross(serviceContDischarge.getContGross());
            reeferDischargeService.setOverSize(serviceContDischarge.getOverSize());
            reeferDischargeService.setDg(serviceContDischarge.getDangerous());
            reeferDischargeService.setDgLabel(serviceContDischarge.getDgLabel());
            reeferDischargeServiceFacade.edit(reeferDischargeService);
        }
        //plugging_reefer_service
        PKs = pluggingReeferServiceFacade.findJobSlipByPPKBnCONT(serviceContDischarge.getServiceVessel().getNoPpkb(), container);
        for (int i = 0; i < PKs.size(); i++) {
            PluggingReeferService pluggingReeferService = pluggingReeferServiceFacade.find(PKs.get(i));
            pluggingReeferService.setContNo(serviceContDischarge.getContNo());
            pluggingReeferService.setMlo(serviceContDischarge.getMlo());
            pluggingReeferService.setContSize(serviceContDischarge.getContSize());
            pluggingReeferService.setMasterContainerType(serviceContDischarge.getMasterContainerType());
            pluggingReeferService.setContStatus(serviceContDischarge.getContStatus());
            pluggingReeferService.setMasterCommodity(serviceContDischarge.getMasterCommodity());
            pluggingReeferService.setContGross(serviceContDischarge.getContGross());
            pluggingReeferService.setOverSize(serviceContDischarge.getOverSize());
            pluggingReeferService.setDg(serviceContDischarge.getDangerous());
            pluggingReeferService.setDgLabel(serviceContDischarge.getDgLabel());
            pluggingReeferServiceFacade.edit(pluggingReeferService);
        }
        //service_cont_discharge
        edit(serviceContDischarge);
        //service_delivery
        List<Integer> IDs = findContIds(serviceContDischarge.getServiceVessel().getNoPpkb(), container, "service_delivery");
        for (int i = 0; i < IDs.size(); i++) {
            ServiceDelivery serviceDelivery = serviceDeliveryFacade.find(IDs.get(i));
            serviceDelivery.setContNo(serviceContDischarge.getContNo());
            serviceDelivery.setMlo(serviceContDischarge.getMlo());
            serviceDelivery.setContSize(serviceContDischarge.getContSize());
            serviceDelivery.setMasterContainerType(serviceContDischarge.getMasterContainerType());
            serviceDelivery.setContStatus(serviceContDischarge.getContStatus());
            serviceDelivery.setTwentyOneFeet(serviceContDischarge.getTwentyOneFeet());
            serviceDelivery.setMasterCommodity(serviceContDischarge.getMasterCommodity());
            serviceDelivery.setContGross(serviceContDischarge.getContGross());
            serviceDelivery.setSealNo(serviceContDischarge.getSealNo());
            serviceDelivery.setOverSize(serviceContDischarge.getOverSize());
            serviceDelivery.setDangerous(serviceContDischarge.getDangerous());
            serviceDelivery.setDgLabel(serviceContDischarge.getDgLabel());
            serviceDeliveryFacade.edit(serviceDelivery);
        }
        //service_gate_delivery
        IDs = findContIds(serviceContDischarge.getServiceVessel().getNoPpkb(), container, "service_gate_delivery");
        for (int i = 0; i < IDs.size(); i++) {
            ServiceGateDelivery serviceGateDelivery = serviceGateDeliveryFacade.find(IDs.get(i));
            serviceGateDelivery.setContNo(serviceContDischarge.getContNo());
            serviceGateDelivery.setMlo(serviceContDischarge.getMlo());
            serviceGateDelivery.setCont_type(serviceContDischarge.getMasterContainerType().getContType());
            serviceGateDelivery.setContGross(serviceContDischarge.getContGross());
            serviceGateDelivery.setSealNo(serviceContDischarge.getSealNo());
            serviceGateDeliveryFacade.edit(serviceGateDelivery);
        }
        //service_reefer
        IDs = serviceReeferFacade.findIDReeferByPPKBnCONT(serviceContDischarge.getServiceVessel().getNoPpkb(), container);
        for (int i = 0; i < IDs.size(); i++) {
            ServiceReefer serviceReefer = serviceReeferFacade.find(IDs.get(i));
            serviceReefer.setContNo(serviceContDischarge.getContNo());
            serviceReefer.setMlo(serviceContDischarge.getMlo());
            serviceReefer.setContSize(serviceContDischarge.getContSize());
            serviceReefer.setMasterContainerType(serviceContDischarge.getMasterContainerType());
            serviceReefer.setContStatus(serviceContDischarge.getContStatus());
            serviceReeferFacade.edit(serviceReefer);
            List<Integer> idRMs = reeferMonitoringFacade.findAllByIdReefer(IDs.get(i));
            for (int j = 0; j < idRMs.size(); j++) {
                ReeferMonitoring reeferMonitoring = reeferMonitoringFacade.find(idRMs.get(j));
                reeferMonitoring.setContNo(serviceContDischarge.getContNo());
                reeferMonitoring.setMlo(serviceContDischarge.getMlo());
                reeferMonitoringFacade.edit(reeferMonitoring);
            }
        }
        //service_shifting
        IDs = findContIds(serviceContDischarge.getServiceVessel().getNoPpkb(), container, "service_shifting");
        for (int i = 0; i < IDs.size(); i++) {
            ServiceShifting serviceShifting = serviceShiftingFacade.find(IDs.get(i));
            serviceShifting.setContNo(serviceContDischarge.getContNo());
            serviceShifting.setMlo(serviceContDischarge.getMlo());
            serviceShifting.setContSize(serviceContDischarge.getContSize());
            serviceShifting.setMasterContainerType(serviceContDischarge.getMasterContainerType());
            serviceShifting.setContStatus(serviceContDischarge.getContStatus());
            serviceShifting.setMasterCommodity(serviceContDischarge.getMasterCommodity());
            serviceShifting.setTwentyOneFeet(serviceContDischarge.getTwentyOneFeet());
            serviceShifting.setContGross(serviceContDischarge.getContGross());
            serviceShifting.setSealNo(serviceContDischarge.getSealNo());
            serviceShifting.setIsExportImport(serviceContDischarge.getIsImport());
            serviceShifting.setOverSize(serviceContDischarge.getOverSize());
            serviceShifting.setDg(serviceContDischarge.getDangerous());
            serviceShifting.setDgLabel(serviceContDischarge.getDgLabel());
            serviceShiftingFacade.edit(serviceShifting);
        }
        //service_cont_transhipment
        IDs = findContIds(serviceContDischarge.getServiceVessel().getNoPpkb(), container, "service_cont_transhipment");
        for (int i = 0; i < IDs.size(); i++) {
            ServiceContTranshipment serviceContTranshipment = serviceContTranshipmentFacade.find(IDs.get(i));
            serviceContTranshipment.setContNo(serviceContDischarge.getContNo());
            serviceContTranshipment.setMlo(serviceContDischarge.getMlo());
            serviceContTranshipment.setContSize(serviceContDischarge.getContSize());
            serviceContTranshipment.setMasterContainerType(serviceContDischarge.getMasterContainerType());
            serviceContTranshipment.setContStatus(serviceContDischarge.getContStatus());
            serviceContTranshipment.setMasterCommodity(serviceContDischarge.getMasterCommodity());
            serviceContTranshipment.setContGross(serviceContDischarge.getContGross());
            serviceContTranshipment.setTwentyOneFeet(serviceContDischarge.getTwentyOneFeet());
            serviceContTranshipment.setIsExportImport(serviceContDischarge.getIsImport());
            serviceContTranshipment.setSealNo(serviceContDischarge.getSealNo());
            serviceContTranshipment.setOverSize(serviceContDischarge.getOverSize());
            serviceContTranshipment.setDangerous(serviceContDischarge.getDangerous());
            serviceContTranshipment.setDgLabel(serviceContDischarge.getDgLabel());
            serviceContTranshipmentFacade.edit(serviceContTranshipment);
        }
        //equipment
        IDs = findContIds(serviceContDischarge.getServiceVessel().getNoPpkb(), container, "equipment");
        for (int i = 0; i < IDs.size(); i++) {
            Equipment equipment = equipmentFacade.find(IDs.get(i));
            equipment.setContNo(serviceContDischarge.getContNo());
            equipment.setMlo(serviceContDischarge.getMlo());
            equipmentFacade.edit(equipment);
        }
        //m_yard_coordinat
        Object[] myc = masterYardCoordinatFacade.findIdStatusByCoordinat(serviceContDischarge.getMasterYard().getBlock(), serviceContDischarge.getYSlot(), serviceContDischarge.getYRow(), serviceContDischarge.getYTier());
        if (myc != null) {
            MasterYardCoordinat masterYardCoordinat = masterYardCoordinatFacade.find((Integer) myc[0]);
            masterYardCoordinat.setContNo(serviceContDischarge.getContNo());
            masterYardCoordinat.setMlo(serviceContDischarge.getMlo());
            masterYardCoordinat.setContSize(serviceContDischarge.getContSize());
            masterYardCoordinat.setContType(serviceContDischarge.getMasterContainerType().getContType().toString());
            masterYardCoordinat.setContWeight(serviceContDischarge.getContGross());
            masterYardCoordinat.setPod(null);
            masterYardCoordinat.setGrossClass(serviceContDischarge.getGrossClass());
            masterYardCoordinatFacade.edit(masterYardCoordinat);
        }

        //perhitungan_lift_service
        IDs = findContIds(serviceContDischarge.getNoPpkb(), container, "perhitungan_lift_service");
         for (int i = 0; i < IDs.size(); i++) {
             PerhitunganLiftService liftService = perhitunganLiftServiceFacadeLocal.find(IDs.get(i));
             liftService.setContNo(serviceContDischarge.getContNo());
             perhitunganLiftServiceFacadeLocal.edit(liftService);
         }

        //perhitungan_penumpukan
        IDs = findContIds(serviceContDischarge.getNoPpkb(), container, "perhitungan_penumpukan");
         for (int i = 0; i < IDs.size(); i++) {
             PerhitunganPenumpukan perhitunganPenumpukan = perhitunganPenumpukanFacadeLocal.find(IDs.get(i));
             perhitunganPenumpukan.setContNo(serviceContDischarge.getContNo());
             perhitunganPenumpukanFacadeLocal.edit(perhitunganPenumpukan);
         }

        //perhitungan_handling_discharge
        IDs = findContIds(serviceContDischarge.getNoPpkb(), container, "perhitungan_handling_discharge");
         for (int i = 0; i < IDs.size(); i++) {
             PerhitunganHandlingDischarge perhitunganHandlingDischarge = perhitunganHandlingDischargeFacadeLocal.find(IDs.get(i));
             perhitunganHandlingDischarge.setContNo(serviceContDischarge.getContNo());
             perhitunganHandlingDischargeFacadeLocal.edit(perhitunganHandlingDischarge);
         }
    }

    public List<Object[]> findServiceCondischargeMonitoringContDisch(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceContDischarge.Native.findServiceCondischargeMonitoringContDisch").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<ServiceContDischarge> findContainersThatHaveNotReachedCY(String noPpkb) {
        List<String> positions = Arrays.asList("01", "02", "05");

        return getEntityManager().createNamedQuery("ServiceContDischarge.findByPpkbAndPositions").setParameter("noPpkb", noPpkb).setParameter("positions", positions).getResultList();
    }

    public List<ServiceContDischarge> findContainersThatHaveReachedCY(String noPpkb) {
        List<String> positions = Arrays.asList("03", "04");

        return getEntityManager().createNamedQuery("ServiceContDischarge.findByPpkbAndPositions")
                .setParameter("noPpkb", noPpkb)
                .setParameter("positions", positions)
                .getResultList();
    }

    public int removeMasterActivityThatHaveReachedCY(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("ServiceContDischarge.updateMasterActivityByPpkbAndPosition").setParameter("masterActivity", null).setParameter("noPpkb", noPpkb).setParameter("position", "03").executeUpdate();
    }

    public Object[] findConfirmedContainer(String noPpkb, String contNo) {
        try {
            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceContDischarge.Native.findContainerThatHasConfirmed").setParameter(1, noPpkb).setParameter(2, contNo).getSingleResult()
            );
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            return null;
        }
    }

    public List<Object[]> findSearchingContainerDischargeFront(String contNo) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceContDischarge.Native.findSearchingContainerDischargeFront").setParameter(1, contNo).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findSearchingContainerTrackingDischargeFront(String noPpkb, String contNo) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceContDischarge.Native.findSearchingContainerTrackingDischargeFront").setParameter(1, noPpkb).setParameter(2, contNo).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public ServiceContDischarge findByNoPpkbAndContNo (String noPpkb, String contNo) {
        try {
            return (ServiceContDischarge) getEntityManager().createNamedQuery("ServiceContDischarge.findByNoPpkbAndContNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
    
    @Override
    public List<Object[]> findServiceContDischargeByPPKBAndBlock(String ppkb, String block) {
        /*
            String sql = "SELECT scd.id, scd.cont_no, scd.cont_size, (SELECT mct.type_in_general FROM m_container_type mct "
                + "WHERE scd.cont_type = mct.cont_type) AS tipe,  scd.cont_status,  "
                + "(SELECT mgc.description FROM m_gross_class mgc WHERE scd.gross_class::TEXT = mgc.gross_class::TEXT) AS gross_class, "
                + "scd.dangerous, scd.over_size,scd.is_import, scd.block, scd.y_slot, scd.y_row, scd.y_tier "
                + "FROM service_cont_discharge scd "
                + "WHERE (scd.position = '01' or scd.position = '02') and scd.block ='" + block + "' and scd.no_ppkb = '" + ppkb + "' "
                + " ORDER BY scd.cont_size, scd.gross_class, scd.cont_type";
        */
        //Fix by srach
        String sql = "SELECT scd.id, scd.cont_no, scd.cont_size, (SELECT mct.type_in_general FROM m_container_type mct "
                + "WHERE scd.cont_type = mct.cont_type) AS tipe,  scd.cont_status,  "
                + "(SELECT mgc.description FROM m_gross_class mgc WHERE scd.gross_class = mgc.gross_class) AS gross_class, "
                + "scd.dangerous, scd.over_size,scd.is_import, scd.block, scd.y_slot, scd.y_row, scd.y_tier "
                + "FROM service_cont_discharge scd "
                + "WHERE (scd.position = '01' or scd.position = '02') and scd.block ='" + block + "' and scd.no_ppkb = '" + ppkb + "' "
                + " ORDER BY scd.cont_size, scd.gross_class, scd.cont_type";
        return objectsDecimalsToObjectsInts(getEntityManager().createNativeQuery(sql).getResultList());
    }
    
    
    @Override
    public List<Object[]> findServiceContDischargeChangeStatus(String ppkb) {
        String sql = "select scd.cont_no, scd.cont_size, ctype.type_in_general, scd.cont_status, scd.cont_gross, "
                + " scd.id, scd.no_ppkb from service_cont_discharge scd "
                + " inner join m_container_type ctype on scd.cont_type=ctype.cont_type "
                + " where scd.no_ppkb='" + ppkb + "' and scd.position='03' "
                + " and scd.is_delivery=FALSE and scd.cont_status='FCL' " 
                + " and scd.cont_no NOT IN (select cont_no from change_status_service)";
        System.out.println(sql);
        return objectsDecimalsToObjectsInts(getEntityManager().createNativeQuery(sql).getResultList());
    }

    @Override
    public List<Object[]> findConvertableToLoadContainers(String noPpkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContDischarge.Native.findConvertableToLoadContainers")
                .setParameter(1, noPpkb)
                .getResultList()
        );
    }

    @Override
    public ServiceContDischarge findByNoPpkbAndContNoUpdateDelivery(String noPpkb, String contNo) {
        return (ServiceContDischarge)getEntityManager().createNamedQuery("ServiceContDischarge.findByNoPpkbAndContNo")
                .setParameter("noPpkb", noPpkb).setParameter("contNo", contNo).getSingleResult();
    }

    public static String PERIODE_0_15_DAY = " between 0 and 15";
    public static String PERIODE_16_30_DAY = " between 16 and 30";
    public static String PERIODE_30_MORE_DAY = " > 30";

    @Override
    public List<Object[]> findDwellingTime(String periode) {
        String range = PERIODE_0_15_DAY;
        if (periode.equals(PERIODE_0_15_DAY)) {
            range = PERIODE_0_15_DAY;
        } else if (periode.equals(PERIODE_16_30_DAY)) {
            range = PERIODE_16_30_DAY;
        } else if (periode.equals(PERIODE_30_MORE_DAY)) {
            range = PERIODE_30_MORE_DAY;
        }
        
        /*
        Author Srach
        String sql = "SELECT s.cont_no,s.cont_size,mct.type_in_general,s.cont_status,mc.name as commodity,"
                + " s.cont_gross,change(s.over_size) as over_size, change(s.dangerous) as is_dangerous, "
                + " change(s.dg_label) as label,CASE WHEN mc.dangerous_class IS NULL THEN 'NON DG' ELSE mc.dangerous_class END,"
                + " change(s.is_import) as is_export_import,s.block,s.y_slot,s.y_row,s.y_tier,s.no_ppkb,mv.name as vessel_name, "
                + " ps.voy_in as voyage, "
                + " CASE WHEN pv.tipe_pelayaran = 'd' THEN 'Domestik'::text ELSE 'International'::text END as shipping_type, "
                + " mcust1.name as agent,mcust.name as confirm_consignee,s.start_placement_date, now() as today_date, "
                + " date_part('days', now() - s.start_placement_date) dwelling_time "
                + " FROM service_cont_discharge s "
                + " JOIN m_container_type mct ON s.cont_type=mct.cont_type  "
                + " JOIN m_commodity mc ON s.commodity_code = mc.commodity_code  "
                + " JOIN planning_vessel pv ON s.no_ppkb = pv.no_ppkb  "
                + " JOIN preservice_vessel ps ON pv.booking_code = ps.booking_code "
                + " JOIN m_vessel mv ON  ps.vessel_code = mv.vessel_code "
                + " LEFT JOIN delivery_service ds ON ds.cont_no = s.cont_no AND ds.no_ppkb=s.no_ppkb "
                + " LEFT JOIN registration r ON r.no_reg = ds.no_reg "
                + " LEFT JOIN m_customer mcust ON r.cust_code = mcust.cust_code "
                + " LEFT JOIN m_customer mcust1 ON ps.cust_code = mcust1.cust_code "
                + " WHERE s.position='03' AND s.is_delivery=FALSE and date_part('days', now() - s.start_placement_date) " + range 
                + " order by now() - s.start_placement_date";
        */
        
        String sql = "SELECT s.cont_no,s.cont_size,mct.type_in_general,s.cont_status,mc.name as commodity,"
                + " s.cont_gross,change(s.over_size) as over_size, change(s.dangerous) as is_dangerous, "
                + " change(s.dg_label) as label,CASE WHEN mc.dangerous_class IS NULL THEN 'NON DG' ELSE mc.dangerous_class END,"
                + " change(s.is_import) as is_export_import,s.block,s.y_slot,s.y_row,s.y_tier,s.no_ppkb,mv.name as vessel_name, "
                + " ps.voy_in as voyage, "
                + " CASE WHEN pv.tipe_pelayaran = 'd' THEN 'Domestik' ELSE 'International' END as shipping_type, "
                + " mcust1.name as agent,mcust.name as confirm_consignee,s.start_placement_date, now() as today_date, "
                + " extract(day from now() - s.start_placement_date) dwelling_time "
                + " FROM service_cont_discharge s "
                + " JOIN m_container_type mct ON s.cont_type=mct.cont_type  "
                + " JOIN m_commodity mc ON s.commodity_code = mc.commodity_code  "
                + " JOIN planning_vessel pv ON s.no_ppkb = pv.no_ppkb  "
                + " JOIN preservice_vessel ps ON pv.booking_code = ps.booking_code "
                + " JOIN m_vessel mv ON  ps.vessel_code = mv.vessel_code "
                + " LEFT JOIN delivery_service ds ON ds.cont_no = s.cont_no AND ds.no_ppkb=s.no_ppkb "
                + " LEFT JOIN registration r ON r.no_reg = ds.no_reg "
                + " LEFT JOIN m_customer mcust ON r.cust_code = mcust.cust_code "
                + " LEFT JOIN m_customer mcust1 ON ps.cust_code = mcust1.cust_code "
                + " WHERE s.position='03' AND s.is_delivery='FALSE' and extract(day from now() - s.start_placement_date) " + range 
                + " order by now() - s.start_placement_date";
        
        
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNativeQuery(sql).getResultList()
        );
    }
}

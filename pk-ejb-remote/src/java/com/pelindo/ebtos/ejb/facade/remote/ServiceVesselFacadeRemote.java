/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ServiceVessel;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ServiceVesselFacadeRemote {

    void create(ServiceVessel serviceVessel);

    void edit(ServiceVessel serviceVessel);

    void remove(ServiceVessel serviceVessel);

    ServiceVessel find(Object id);

    List<ServiceVessel> findAll();

    List<ServiceVessel> findRange(int[] range);

    List<Object[]> findServiceVesselServicing();

    List<Object[]> findServiceVesselStatusService();

    int count();

    Object[] findServiceVesselByPpkb(String no_ppkb);

    List<Object[]> findServiceVessels();

    List<Object[]> findServiceVesselsServicing();

    List<Object[]> findServiceVesselsServiced();

    Object[] findServiceVesselDetail(String no_ppkb);

    List<Object[]> findServiceVesselByOnline(String no_ppkb);

    public java.util.List<java.lang.Object[]> checkSpaceAvailability(java.lang.Short frMeter, java.lang.Short toMeter, java.lang.String dockCode, String noPpkb);

    public java.util.List<java.lang.Object[]> findTruckDestinationByAppKey(java.lang.String key);

    List<Object[]> findPpkbCfs();

    public java.lang.Object[] getVesselActivities();

    public java.lang.Object[] getContServiceActivitiesToday();

    public Object[] findServiceByStatus(String no_ppkb, String status);

    public java.util.List<java.lang.String> findVesselsOnPort();

    List<Object[]> findRecentArrivalsAndDepartures();

    public java.util.List<java.lang.Object[]> findArrivalsAndDeparturesLastMonth();

    List<String> findPpkbNumbers(String no_Ppkb);

    List<Object[]> findServiceVesselsFromDischarge();

    List<Object[]> findServiceVesselsToLoad();

    List<Object[]> findServiceVesselsFromChange();

    List<Object[]> findServiceVesselsToChange();

    List<Object[]> findServiceVesselsCancelLoadingConfirm();

    List<Object[]> findServiceVesselsByMonitoringContDischarge();

    List<Object[]> findServiceVesselsByMonitoringContLoad();

    List<Object[]> findServiceVesselsByMonthAndYear(Integer bulan, Integer tahun);

    public java.util.List<java.lang.Object[]> findServiceVesselsServicedDischarge();

    public java.util.List<java.lang.Object[]> findServiceVesselsServicedLoad();

    public void handleEndService(ServiceVessel serviceVessel);

    public void handleRollback(ServiceVessel serviceVessel);

    Object[] findServiceVesselsByMonitoringCountDischarge(String no_ppkb);

    Object[] findServiceVesselsByMonitoringCountLoad(String no_ppkb);

    Object[] findServiceVesselsByMonitoringCountLoad2(String no_ppkb);

    List<Object[]> findServiceVesselsByYardPlanningMonitoring();

    List<Object[]> findAllReceivingService();

    Object[] findReceivingServiceByPpkb(String no_ppkb);

    public java.util.List<java.lang.Object[]> findServiceVesselStatusServed();

    public com.pelindo.ebtos.model.db.ServiceVessel findByPpkbAndStatus(java.lang.String noPpkb, java.lang.String status);
}

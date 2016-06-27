/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ServiceVessel;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface ServiceVesselFacadeLocal {

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

    List<Object[]> findAllReceivingService();

    Object[] findReceivingServiceByPpkb(String no_ppkb);

    Object[] findServiceVesselDetail(String no_ppkb);

    java.util.List<java.lang.Object[]> findServiceVesselsServicingByDock(java.lang.String dockCode);
    
    public List<Object[]> findServiceVesselsServicingLoad();
}

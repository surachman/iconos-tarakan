/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.master.MasterPort;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author senoanggoro
 */
@Remote
public interface PreserviceVesselFacadeRemote {

    void create(PreserviceVessel preserviceVessel);

    void edit(PreserviceVessel preserviceVessel);

    void remove(PreserviceVessel preserviceVessel);

    PreserviceVessel find(Object id);

    List<PreserviceVessel> findAll();

    List<PreserviceVessel> findRange(int[] range);

    int count();
 
    List<Object[]> findPreserviceVessels();

    Object[] findPreserviceVesselByBooking(String booking_code);

    List<Object[]> findPreserviceVesselByPort (String port_code);

    List<Object[]> findPreserviceVesselByVessel (String vessel_code);
    
    List<Object[]> findPreserviceVesselOnlines(String no_ppkb);

    public java.util.List<java.lang.Object[]> findCanceledPreserviceVessels();

    public MasterPort findNextPortByBookingCode(java.lang.String bookingCode);
}

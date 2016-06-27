/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.remote;

/**
 *
 * @author Eka
 */
import java.sql.Blob;
import javax.ejb.Remote;

@Remote
public interface ShipProfileBayMonitorRemote {
    void initWithVesselAndPPKB(String vesselCode, String noPPKB, Integer bay, Integer w, Integer h, boolean isLoad);

    Blob generate();
}

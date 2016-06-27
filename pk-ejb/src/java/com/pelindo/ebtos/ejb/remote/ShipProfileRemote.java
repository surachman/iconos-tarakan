/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.remote;

/**
 *
 * @author dyware-java
 */
import java.sql.Blob;
import javax.ejb.Remote;

@Remote
public interface ShipProfileRemote {
    void initWithVessel(String vesselCode, Integer bay, Integer w, Integer h);

    void initWithPpkb(String noPpkb, Integer bay, Integer w, Integer h, boolean isLoad);

    Blob generate();
}

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
import java.util.Date;

import javax.ejb.Remote;

@Remote
public interface VesselScheduleVisualRemote {
    Blob generate();
    public void init(String dock_code, Integer w, Integer h, Date yBoundMin, Date yBoundMax);
}

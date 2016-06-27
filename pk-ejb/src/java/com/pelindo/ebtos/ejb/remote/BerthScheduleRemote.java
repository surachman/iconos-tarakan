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
public interface BerthScheduleRemote {
    void init(String dock_code, int w, int h);
    Blob generate();
}

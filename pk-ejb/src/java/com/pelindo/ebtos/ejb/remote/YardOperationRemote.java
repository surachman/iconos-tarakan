/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.remote;

import com.pelindo.ebtos.exception.ContainerNotMovableException;
import com.pelindo.ebtos.exception.LocationNotAvailableException;
import com.pelindo.ebtos.model.YardLocation;
import javax.ejb.Remote;

/**
 *
 * @author R. Seno Anggoro A
 */
@Remote
public interface YardOperationRemote {

    void liftoff(String noPpkb, String contNo, String contType, short contSize, Integer contWeight, String pod, String grossClass, YardLocation destination) throws LocationNotAvailableException;

    void lifton(String noPpkb, String contNo, short contSize, YardLocation destination) throws ContainerNotMovableException;

}

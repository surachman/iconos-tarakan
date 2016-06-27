/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.local;

import com.pelindo.ebtos.exception.ContainerNotMovableException;
import com.pelindo.ebtos.exception.LocationNotAvailableException;
import com.pelindo.ebtos.model.YardLocation;
import javax.ejb.Local;

/**
 *
 * @author R. Seno Anggoro A
 */
@Local
public interface YardOperationLocal {

    void liftoff(String noPpkb, String contNo, String contType, short contSize, Integer contWeight, String pod, String grossClass, YardLocation destination) throws LocationNotAvailableException;

    void lifton(String noPpkb, String contNo, short contSize, YardLocation destination) throws ContainerNotMovableException;

    void forceLifton(String noPpkb, String contNo, short contSize, YardLocation destination) throws ContainerNotMovableException;

    public void lifton(java.lang.String noPpkb, java.lang.String contNo, java.lang.String contType, short contSize, java.lang.String pod, java.lang.String grossClass, com.pelindo.ebtos.model.YardLocation destination) throws com.pelindo.ebtos.exception.ContainerNotMovableException;

}

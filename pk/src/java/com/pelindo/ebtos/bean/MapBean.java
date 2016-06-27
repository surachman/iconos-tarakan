/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.remote.ShipPositionRemote;
import java.io.IOException;
import java.sql.SQLException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.sql.rowset.serial.SerialException;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author dyware-java
 */
@Named(value="mapBean")
@RequestScoped
public class MapBean {
    @EJB ShipPositionRemote shipPosition;

    private DefaultStreamedContent mapArea;

    public StreamedContent getMapArea() throws IOException, SerialException, SQLException {
        mapArea = new DefaultStreamedContent(shipPosition.getPositionImage().getBinaryStream(), "image/png");
        return mapArea;
    }
    
}

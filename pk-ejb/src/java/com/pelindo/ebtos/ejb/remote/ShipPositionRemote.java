
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.remote;

//~--- JDK imports ------------------------------------------------------------

import java.sql.Blob;

import javax.ejb.Remote;

@Remote
public interface ShipPositionRemote {
    Blob getPositionImage();
}



/*
 * @(#)ShipPositionRemote.java   10/11/23
 * 
 * Copyright (c) 2010 Dycode
 */


//~ Formatted by Jindent --- http://www.jindent.com

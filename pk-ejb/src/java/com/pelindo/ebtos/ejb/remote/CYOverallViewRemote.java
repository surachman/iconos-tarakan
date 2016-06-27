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
public interface CYOverallViewRemote {
    Blob generate();
    void init(String block, Integer contWidth, Integer contHeight);
}

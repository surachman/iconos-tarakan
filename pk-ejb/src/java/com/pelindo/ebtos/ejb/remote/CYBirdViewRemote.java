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
public interface CYBirdViewRemote {
    Blob generate();
    void init(String block, Integer tier, Integer w, Integer h, boolean existOnly, String ppkb);
}

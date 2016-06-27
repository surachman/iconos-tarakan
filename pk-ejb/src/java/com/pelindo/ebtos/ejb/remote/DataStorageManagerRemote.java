/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.remote;

import javax.ejb.Remote;

/**
 *
 * @author R. Seno Anggoro A
 */
@Remote
public interface DataStorageManagerRemote {

    public java.lang.Object getData(String sessionId, String id);

    public void putData(String sessionId, String id, Object object);
    
}

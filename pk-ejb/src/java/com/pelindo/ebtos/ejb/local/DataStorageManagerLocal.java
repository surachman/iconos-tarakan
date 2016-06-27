/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.local;

import javax.ejb.Local;

/**
 *
 * @author R. Seno Anggoro A
 */
@Local
public interface DataStorageManagerLocal {

    public java.lang.Object getData(String sessionId, String id);

    public void putData(String sessionId, String id, Object object);
    
}

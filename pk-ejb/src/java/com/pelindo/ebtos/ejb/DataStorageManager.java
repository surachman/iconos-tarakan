/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.pelindo.ebtos.ejb.local.DataStorageManagerLocal;
import com.pelindo.ebtos.ejb.remote.DataStorageManagerRemote;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;

/**
 *
 * @author R. Seno Anggoro A
 */
@Singleton
public class DataStorageManager implements DataStorageManagerRemote, DataStorageManagerLocal {
    private Map<String, Object> queue;

    public DataStorageManager() {
        queue = new HashMap<String, Object>();
    }
    

    public Object getData(String sessionId, String id) {
        Object object = null;

        if (sessionId != null && id != null) {
            id = sessionId + "|" + id;
            object = queue.get(id);

            if (object != null) {
                queue.remove(id);
                Logger.getLogger(getClass().getName()).log(Level.FINE, "datasource found!");
            }
        }

        return object;
    }

    public void putData(String sessionId, String id, Object object) {
        if (sessionId != null && id != null && object != null) {
            id = sessionId + "|" + id;
            queue.put(id, object);
        } else {
            throw new RuntimeException("sessionId or id or object can not be empty");
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.RecapReceiving;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycode-java
 */
@Remote
public interface RecapReceivingFacadeRemote {

    void create(RecapReceiving recapReceiving);

    void edit(RecapReceiving recapReceiving);

    void remove(RecapReceiving recapReceiving);

    RecapReceiving find(Object id);

    List<RecapReceiving> findAll();

    List<RecapReceiving> findRange(int[] range);

    int count();

}

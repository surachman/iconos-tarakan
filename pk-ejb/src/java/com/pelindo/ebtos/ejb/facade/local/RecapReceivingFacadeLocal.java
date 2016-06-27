/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.RecapReceiving;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycode-java
 */
@Local
public interface RecapReceivingFacadeLocal {

    void create(RecapReceiving recapReceiving);

    void edit(RecapReceiving recapReceiving);

    void remove(RecapReceiving recapReceiving);

    RecapReceiving find(Object id);

    List<RecapReceiving> findAll();

    List<RecapReceiving> findRange(int[] range);

    int count();

}

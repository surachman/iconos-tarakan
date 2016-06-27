/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.RecapUpahBuruh;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author R Seno Anggoro
 */
@Remote
public interface RecapUpahBuruhFacadeRemote {

    void create(RecapUpahBuruh recapUpahBuruh);

    void edit(RecapUpahBuruh recapUpahBuruh);

    void remove(RecapUpahBuruh recapUpahBuruh);

    RecapUpahBuruh find(Object id);

    List<RecapUpahBuruh> findAll();

    List<RecapUpahBuruh> findRange(int[] range);

    int count();

}

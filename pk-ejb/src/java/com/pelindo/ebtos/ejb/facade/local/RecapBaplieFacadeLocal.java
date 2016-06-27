/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.RecapBaplie;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author R Seno Anggoro
 */
@Local
public interface RecapBaplieFacadeLocal {

    void create(RecapBaplie recapBaplie);

    void edit(RecapBaplie recapBaplie);

    void remove(RecapBaplie recapBaplie);

    RecapBaplie find(Object id);

    List<RecapBaplie> findAll();

    List<RecapBaplie> findRange(int[] range);

    int count();

    public com.pelindo.ebtos.model.db.RecapBaplie findByNoPpkb(java.lang.String noPpkb);

}

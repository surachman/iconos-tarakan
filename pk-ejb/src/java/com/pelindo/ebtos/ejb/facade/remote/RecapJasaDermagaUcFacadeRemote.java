/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.RecapJasaDermagaUc;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface RecapJasaDermagaUcFacadeRemote {

    void create(RecapJasaDermagaUc recapJasaDermagaUc);

    void edit(RecapJasaDermagaUc recapJasaDermagaUc);

    void remove(RecapJasaDermagaUc recapJasaDermagaUc);

    RecapJasaDermagaUc find(Object id);

    List<RecapJasaDermagaUc> findAll();

    List<RecapJasaDermagaUc> findRange(int[] range);

    int count();

    public int deleteByNoPpkb(java.lang.String noPpkb);

}

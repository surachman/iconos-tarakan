/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.RecapJasaDermagaUc;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface RecapJasaDermagaUcFacadeLocal {

    void create(RecapJasaDermagaUc recapJasaDermagaUc);

    void edit(RecapJasaDermagaUc recapJasaDermagaUc);

    void remove(RecapJasaDermagaUc recapJasaDermagaUc);

    RecapJasaDermagaUc find(Object id);

    List<RecapJasaDermagaUc> findAll();

    List<RecapJasaDermagaUc> findRange(int[] range);

    int count();

}

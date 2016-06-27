/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterAnggaranTrafikBm;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface MasterAnggaranTrafikBmFacadeLocal {

    void create(MasterAnggaranTrafikBm masterAnggaranTrafikBm);

    void edit(MasterAnggaranTrafikBm masterAnggaranTrafikBm);

    void remove(MasterAnggaranTrafikBm masterAnggaranTrafikBm);

    MasterAnggaranTrafikBm find(Object id);

    List<MasterAnggaranTrafikBm> findAll();

    List<MasterAnggaranTrafikBm> findRange(int[] range);

    int count();

}

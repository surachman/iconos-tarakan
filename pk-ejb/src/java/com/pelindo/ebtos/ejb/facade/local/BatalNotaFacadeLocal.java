/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.BatalNota;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface BatalNotaFacadeLocal {

    void create(BatalNota batalNota);

    void edit(BatalNota batalNota);

    void remove(BatalNota batalNota);

    BatalNota find(Object id);

    List<BatalNota> findAll();

    List<BatalNota> findRange(int[] range);

    int count();

}

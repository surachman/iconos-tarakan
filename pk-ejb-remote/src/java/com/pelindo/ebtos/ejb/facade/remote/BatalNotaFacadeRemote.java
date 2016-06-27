/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.BatalNota;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface BatalNotaFacadeRemote {

    void create(BatalNota batalNota);

    void edit(BatalNota batalNota);

    void remove(BatalNota batalNota);

    BatalNota find(Object id);

    List<BatalNota> findAll();

    List<BatalNota> findRange(int[] range);

    int count();

    List<Object[]> findByBatalNotaList();

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.UsableFakturPajak;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author R. Seno Anggoro A
 */
@Local
public interface UsableFakturPajakFacadeLocal {

    void create(UsableFakturPajak usableFakturPajak);

    void edit(UsableFakturPajak usableFakturPajak);

    void remove(UsableFakturPajak usableFakturPajak);

    UsableFakturPajak find(Object id);

    List<UsableFakturPajak> findAll();

    List<UsableFakturPajak> findRange(int[] range);

    int count();

    public java.lang.String getAvailableNoFakturPajak();

}

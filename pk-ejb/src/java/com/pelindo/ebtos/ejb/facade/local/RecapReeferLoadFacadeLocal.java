/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.RecapReeferLoad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface RecapReeferLoadFacadeLocal {

    void create(RecapReeferLoad recapReeferLoad);

    void edit(RecapReeferLoad recapReeferLoad);

    void remove(RecapReeferLoad recapReeferLoad);

    RecapReeferLoad find(Object id);

    List<RecapReeferLoad> findAll();

    List<RecapReeferLoad> findRange(int[] range);

    int count();

    Integer findByPpkb(String no_ppkb);

    public com.pelindo.ebtos.model.db.RecapReeferLoad findByNoPpkb(java.lang.String no_ppkb);

    public int deleteByNoPpkb(java.lang.String noPpkb);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.RecapReeferLoad;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface RecapReeferLoadFacadeRemote {

    void create(RecapReeferLoad recapReeferLoad);

    void edit(RecapReeferLoad recapReeferLoad);

    void remove(RecapReeferLoad recapReeferLoad);

    RecapReeferLoad find(Object id);

    List<RecapReeferLoad> findAll();

    List<RecapReeferLoad> findRange(int[] range);

    int count();

    Integer findByPpkb(String no_ppkb);
}

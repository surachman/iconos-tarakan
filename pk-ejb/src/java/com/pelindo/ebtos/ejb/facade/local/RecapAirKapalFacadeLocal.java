/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.RecapAirKapal;
import java.util.List;
import javax.ejb.Local;

@Local
public interface RecapAirKapalFacadeLocal {

    void create(RecapAirKapal RecapAirKapal);

    void edit(RecapAirKapal RecapAirKapal);

    void remove(RecapAirKapal RecapAirKapal);

    RecapAirKapal find(Object id);

    List<RecapAirKapal> findAll();

    List<RecapAirKapal> findRange(int[] range);

    int count();

    public int deleteByNoPpkb(String noPpkb);
}

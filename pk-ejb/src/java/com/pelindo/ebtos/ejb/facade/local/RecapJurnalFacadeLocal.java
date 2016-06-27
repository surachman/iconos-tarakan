/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.RecapJurnal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author R Seno Anggoro
 */
@Local
public interface RecapJurnalFacadeLocal {

    void create(RecapJurnal recapJurnal);

    void edit(RecapJurnal recapJurnal);

    void remove(RecapJurnal recapJurnal);

    RecapJurnal find(Object id);

    List<RecapJurnal> findAll();

    List<RecapJurnal> findRange(int[] range);

    int count();

    public List<RecapJurnal> createJKMRecap(java.lang.Object[] array, java.lang.String username) throws javax.ejb.EJBException;

    public java.lang.Boolean postingJurnalToSimpat(Object[] journals, java.lang.String username);

    RecapJurnal findBySumber(String sumber);

}

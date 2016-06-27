/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.AngkutPetikemas;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface AngkutPetikemasFacadeLocal {

    void create(AngkutPetikemas angkutPetikemas);

    void edit(AngkutPetikemas angkutPetikemas);

    void remove(AngkutPetikemas angkutPetikemas);

    AngkutPetikemas find(Object id);

    List<AngkutPetikemas> findAll();

    List<AngkutPetikemas> findRange(int[] range);

    int count();

}

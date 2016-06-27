/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.AngkutPetikemas;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface AngkutPetikemasFacadeRemote {

    void create(AngkutPetikemas angkutPetikemas);

    void edit(AngkutPetikemas angkutPetikemas);

    void remove(AngkutPetikemas angkutPetikemas);

    AngkutPetikemas find(Object id);

    List<AngkutPetikemas> findAll();

    List<AngkutPetikemas> findRange(int[] range);

    int count();

    List<Object[]> findAllNative();
}

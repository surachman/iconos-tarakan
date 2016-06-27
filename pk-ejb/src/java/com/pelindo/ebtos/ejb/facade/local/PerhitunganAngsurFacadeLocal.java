/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PerhitunganAngsur;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface PerhitunganAngsurFacadeLocal {

    void create(PerhitunganAngsur perhitunganAngsur);

    void edit(PerhitunganAngsur perhitunganAngsur);

    void remove(PerhitunganAngsur perhitunganAngsur);

    PerhitunganAngsur find(Object id);

    List<PerhitunganAngsur> findAll();

    List<PerhitunganAngsur> findRange(int[] range);

    int count();

    int findPerhitunganAngsurId(String no_ppkb, String no_reg, String cont_no);

}

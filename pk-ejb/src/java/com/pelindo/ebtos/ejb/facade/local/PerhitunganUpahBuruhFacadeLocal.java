/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PerhitunganUpahBuruh;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author R Seno Anggoro
 */
@Local
public interface PerhitunganUpahBuruhFacadeLocal {

    void create(PerhitunganUpahBuruh perhitunganUbahBuruh);

    void edit(PerhitunganUpahBuruh perhitunganUbahBuruh);

    void remove(PerhitunganUpahBuruh perhitunganUbahBuruh);

    PerhitunganUpahBuruh find(Object id);

    List<PerhitunganUpahBuruh> findAll();

    List<PerhitunganUpahBuruh> findRange(int[] range);

    int count();

    PerhitunganUpahBuruh findByJobslip(String jobslip);

}

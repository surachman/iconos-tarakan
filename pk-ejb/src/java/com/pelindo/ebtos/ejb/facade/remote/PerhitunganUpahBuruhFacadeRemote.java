/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PerhitunganUpahBuruh;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author R Seno Anggoro
 */
@Remote
public interface PerhitunganUpahBuruhFacadeRemote {

    void create(PerhitunganUpahBuruh perhitunganUbahBuruh);

    void edit(PerhitunganUpahBuruh perhitunganUbahBuruh);

    void remove(PerhitunganUpahBuruh perhitunganUbahBuruh);

    PerhitunganUpahBuruh find(Object id);

    List<PerhitunganUpahBuruh> findAll();

    List<PerhitunganUpahBuruh> findRange(int[] range);

    int count();

    public int deleteByJobslip(String jobslip);

    PerhitunganUpahBuruh findByJobslip(String jobslip);

    public int deleteByNoReg(String noReg);

}

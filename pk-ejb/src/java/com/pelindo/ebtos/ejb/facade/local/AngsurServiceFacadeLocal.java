/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.AngsurService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface AngsurServiceFacadeLocal {

    void create(AngsurService angsurService);

    void edit(AngsurService angsurService);

    void remove(AngsurService angsurService);

    AngsurService find(Object id);

    List<AngsurService> findAll();

    List<AngsurService> findRange(int[] range);

    int count();

    List<Object[]> findAngsurServiceByPPKBnReg(String no_ppkb, String no_reg);

    String generateId(String bulan);

    List<Object[]> findPerhitungan(String no_reg);
}

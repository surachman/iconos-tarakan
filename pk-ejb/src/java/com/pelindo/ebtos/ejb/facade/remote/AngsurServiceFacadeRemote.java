/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.AngsurService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface AngsurServiceFacadeRemote {

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

    List<Object[]> findByListBatalNotaService(String no_ppkb, String no_reg);
}

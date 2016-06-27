/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PenumpukanSusulanService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface PenumpukanSusulanServiceFacadeRemote {

    void create(PenumpukanSusulanService penumpukanSusulanService);

    void edit(PenumpukanSusulanService penumpukanSusulanService);

    void remove(PenumpukanSusulanService penumpukanSusulanService);

    PenumpukanSusulanService find(Object id);

    List<PenumpukanSusulanService> findAll();

    List<PenumpukanSusulanService> findRange(int[] range);

    int count();

    List findPenumpukanSusulanServiceByPPKBnReg(String no_ppkb, String no_reg);

    String generateId(String bulan);

    String findInvoice(String cont_no, String no_ppkb, String no_reg);

    List<Object[]> findPerhitungan(String no_reg);

    List<Object[]> findByListBatalNotaService(String no_ppkb, String no_reg);

    String findCekValidasi(String contNo, String noPpkb, String noReg);

}

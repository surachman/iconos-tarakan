/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.UcPenumpukanSusulanService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface UcPenumpukanSusulanServiceFacadeRemote {

    void create(UcPenumpukanSusulanService ucPenumpukanSusulanService);

    void edit(UcPenumpukanSusulanService ucPenumpukanSusulanService);

    void remove(UcPenumpukanSusulanService ucPenumpukanSusulanService);

    UcPenumpukanSusulanService find(Object id);

    List<UcPenumpukanSusulanService> findAll();

    List<UcPenumpukanSusulanService> findRange(int[] range);

    int count();

    List<Object[]> findPenumpukanSusulanServiceByPPKBnReg(String no_ppkb, String no_reg);

    String generateId(String bulan);

    String findInvoice(Integer cont_no, String no_ppkb, String no_reg);

    List<Object[]> findPerhitungan(String no_reg);

    List<Object> findByReg(String no_reg);
}

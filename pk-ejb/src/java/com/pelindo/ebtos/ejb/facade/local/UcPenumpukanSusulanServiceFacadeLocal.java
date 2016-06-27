/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.UcPenumpukanSusulanService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface UcPenumpukanSusulanServiceFacadeLocal {

    void create(UcPenumpukanSusulanService ucPenumpukanSusulanService);

    void edit(UcPenumpukanSusulanService ucPenumpukanSusulanService);

    void remove(UcPenumpukanSusulanService ucPenumpukanSusulanService);

    UcPenumpukanSusulanService find(Object id);

    List<UcPenumpukanSusulanService> findAll();

    List<UcPenumpukanSusulanService> findRange(int[] range);

    int count();

    public List<Object[]> findPenumpukanSusulanServiceByPPKBnReg(String noPpkb, String noReg);

}

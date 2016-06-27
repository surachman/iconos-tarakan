/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ServiceDischargeUc;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface ServiceDischargeUcFacadeLocal {

    void create(ServiceDischargeUc serviceDischargeUc);

    void edit(ServiceDischargeUc serviceDischargeUc);

    void remove(ServiceDischargeUc serviceDischargeUc);

    ServiceDischargeUc find(Object id);

    List<ServiceDischargeUc> findAll();

    List<ServiceDischargeUc> findRange(int[] range);

    List<Object[]> findByPpkb(String no_ppkb);

    List<Object[]> findByPpkbPick(String no_ppkb);

    int count();

    Object[] findByBlNo(String no_ppkb, String bl_no, String pos);

    List<Object[]> findByPpkbAja(String no_ppkb);

}

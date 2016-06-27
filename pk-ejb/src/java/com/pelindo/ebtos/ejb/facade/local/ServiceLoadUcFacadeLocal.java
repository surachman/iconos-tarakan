/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ServiceLoadUc;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface ServiceLoadUcFacadeLocal {

    void create(ServiceLoadUc serviceLoadUc);

    void edit(ServiceLoadUc serviceLoadUc);

    void remove(ServiceLoadUc serviceLoadUc);

    ServiceLoadUc find(Object id);

    List<ServiceLoadUc> findAll();

    List<ServiceLoadUc> findRange(int[] range);

    int count();

    Integer findByPpkbNBLno(String no_ppkb, String no_bl);

    List<Object[]> findByPpkb(String no_ppkb);

    List<Object[]> findByPpkbPick(String no_ppkb);

    Object[] findByBlNo(String no_ppkb, String bl_no, String pos);

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ServiceCfs;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface ServiceCfsFacadeLocal {

    void create(ServiceCfs serviceCfs);

    void edit(ServiceCfs serviceCfs);

    void remove(ServiceCfs serviceCfs);

    ServiceCfs find(Object id);

    List<ServiceCfs> findAll();

    List<ServiceCfs> findRange(int[] range);

    int count();

    List<Object[]> findStripping();
    
    List<Object[]> findStuffing();

    Object[] findByContNo(String no_ppkb, String cont_no);

    List<Object[]> findByPpkb(String no_ppkb);

    Integer findIsLast(String no_ppkb, String cont_no);

    public ServiceCfs findNotFinishedYetByNoPpkbAndContNo(String noPpkb, String contNo);

    public ServiceCfs findLastCfsByPpkbAndContNo(String noPpkb, String contNo);
}

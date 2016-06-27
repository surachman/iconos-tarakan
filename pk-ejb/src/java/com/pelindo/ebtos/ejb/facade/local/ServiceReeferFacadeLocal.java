/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ServiceReefer;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface ServiceReeferFacadeLocal {

    void create(ServiceReefer serviceReefer);

    void edit(ServiceReefer serviceReefer);

    void remove(ServiceReefer serviceReefer);

    ServiceReefer find(Object id);

    List<ServiceReefer> findAll();

    List<ServiceReefer> findRange(int[] range);

    Object[] findServiceReeferById(Integer id);

    List<Object[]> findServiceReeferByPpkbDischarge(String no_ppkb);

    List<Object[]> findServiceReeferByPpkbLoad(String no_ppkb);

    List<Object[]> findServiceReeferByRegPlugging(String no_reg);

    int count();

    Object[] findByContNo(String cont_no);

    Date findPlugOn(String cont_no, String no_ppkb);

    Object[] findPlugOnOffForReeferLoadService(String cont_no, String no_ppkb);

    List<Integer> findIDReeferByPPKBnCONT(String no_ppkb, String cont_no);

    Integer findValidasiPenumpukan(String no_ppkb, String cont_no, String operation);

    public ServiceReefer findByContNoPpkbAndOperation(String contNo, String noPpkb, String string);
}

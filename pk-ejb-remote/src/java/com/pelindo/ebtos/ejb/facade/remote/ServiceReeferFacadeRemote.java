/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ServiceReefer;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ServiceReeferFacadeRemote {

    void create(ServiceReefer serviceReefer);

    void edit(ServiceReefer serviceReefer);

    void remove(ServiceReefer serviceReefer);

    ServiceReefer find(Object id);

    List<ServiceReefer> findAll();

    List<ServiceReefer> findRange(int[] range);

    Object[] findServiceReeferById (Integer id);

    List<Object[]> findServiceReeferByPpkbDischarge (String no_ppkb);

    List<Object[]> findServiceReeferByPpkbLoad (String no_ppkb);

    List<Object[]> findServiceReeferByRegPlugging (String no_reg);

    int count();

    Object[] findByContNo(String cont_no);

    Date findPlugOn(String cont_no, String no_ppkb);

    Object[] findPlugOnOffForReeferLoadService(String cont_no, String no_ppkb);

    List<Integer> findIDReeferByPPKBnCONT(String no_ppkb, String cont_no);

    Integer findValidasiPenumpukan(String no_ppkb, String cont_no, String operation);

    Date findPlugOnForReeferPluggingService(String cont_no);

    Date findPlugOffForReeferPluggingService(String cont_no);

    Integer findValidasiUpdatePlugging(String jobslip, String cont_no, String operation);

    public com.pelindo.ebtos.model.db.ServiceReefer findByContNoPpkbAndOperation(java.lang.String contNo, java.lang.String noPpkb, java.lang.String operation);

    public com.pelindo.ebtos.model.db.ServiceReefer findByCountableLoadReefer(java.lang.String contNo, java.lang.String noPpkb);
}

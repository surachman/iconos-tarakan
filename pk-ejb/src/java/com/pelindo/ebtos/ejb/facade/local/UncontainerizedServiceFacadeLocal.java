/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.UncontainerizedService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author postgres
 */
@Local
public interface UncontainerizedServiceFacadeLocal {

    void create(UncontainerizedService uncontainerizedService);

    void edit(UncontainerizedService uncontainerizedService);

    void remove(UncontainerizedService uncontainerizedService);

    UncontainerizedService find(Object id);

    List<UncontainerizedService> findAll();

    List<UncontainerizedService> findRange(int[] range);

    int count();

    public java.util.List<com.pelindo.ebtos.model.db.UncontainerizedService> findDeliveryUCThatHaveReachedCY(java.lang.String noPpkb);

    public java.util.List<com.pelindo.ebtos.model.db.UncontainerizedService> findReceivingUcThatHadBeenLoaded(java.lang.String noPpkb);

    Integer findWeightByPpkbNBl(String no_ppkb, String bl_no);

    public java.util.List<com.pelindo.ebtos.model.db.UncontainerizedService> findTranshipmentUCThatHaveReachedCY(java.lang.String noPpkb);

    public com.pelindo.ebtos.model.db.UncontainerizedService findByNoPpkbAndBlNo(java.lang.String noPpkb, java.lang.String blNo);

    public java.util.List<com.pelindo.ebtos.model.db.UncontainerizedService> findTranshipmentUCThatHadBeenLoaded(java.lang.String noPpkb);

    public java.util.List<com.pelindo.ebtos.model.db.UncontainerizedService> findByNoPpkbForRecapJasaDermagaUC(java.lang.String noPpkb);

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterProfileDetail;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author senoanggoro
 */
@Local
public interface MasterProfileDetailFacadeLocal {

    void create(MasterProfileDetail masterProfileDetail);

    void edit(MasterProfileDetail masterProfileDetail);

    void remove(MasterProfileDetail masterProfileDetail);

    MasterProfileDetail find(Object id);

    List<MasterProfileDetail> findAll();

    List<MasterProfileDetail> findRange(int[] range);

    int count();

    List<Object[]> findBayDetailByVessel(String vesselCode, int bay);

    List<Object[]> findBayDetailByPpkb(String noPpkb, int bay);

}

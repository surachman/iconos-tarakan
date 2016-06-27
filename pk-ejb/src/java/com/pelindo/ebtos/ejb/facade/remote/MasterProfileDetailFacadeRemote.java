/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterProfileDetail;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterProfileDetailFacadeRemote {

    void create(MasterProfileDetail masterProfileDetail);

    void edit(MasterProfileDetail masterProfileDetail);

    void remove(MasterProfileDetail masterProfileDetail);

    MasterProfileDetail find(Object id);

    List<MasterProfileDetail> findAll();

    List<MasterProfileDetail> findRange(int[] range);

    int count();

    List<Object[]> findAllByProfileCode (int profile_code);

    int findLastOfID();

    int deleteByProfileCode(int profile_code);

    List<Object[]> findTierByRow(String vessel_code, String position, int bay_no, int row_name);

    List<Object[]> findBayDetailByPpkb(String noPpkb, int bay);
}

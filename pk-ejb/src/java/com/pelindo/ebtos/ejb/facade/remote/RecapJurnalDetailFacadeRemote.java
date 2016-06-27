/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.RecapJurnalDetail;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author R Seno Anggoro
 */
@Remote
public interface RecapJurnalDetailFacadeRemote {

    void create(RecapJurnalDetail recapJurnalDetail);

    void edit(RecapJurnalDetail recapJurnalDetail);

    void remove(RecapJurnalDetail recapJurnalDetail);

    RecapJurnalDetail find(Object id);

    List<RecapJurnalDetail> findAll();

    List<RecapJurnalDetail> findRange(int[] range);

    int count();

    public java.util.List<RecapJurnalDetail> findJKMDetail(String sumber);

}

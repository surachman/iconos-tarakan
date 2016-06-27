/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ReplacementContDischarge;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Dyware-Dev01
 */
@Remote
public interface ReplacementContDischargeFacadeRemote {

    void create(ReplacementContDischarge replacementContDischarge);

    void edit(ReplacementContDischarge replacementContDischarge);

    void remove(ReplacementContDischarge replacementContDischarge);

    ReplacementContDischarge find(Object id);

    List<ReplacementContDischarge> findAll();

    List<ReplacementContDischarge> findRange(int[] range);

    int count();

    List<Object[]> findReplacementContDischargeByPPKB(String no_ppkb);

}

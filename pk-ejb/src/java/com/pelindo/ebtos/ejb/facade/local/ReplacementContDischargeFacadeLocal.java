/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ReplacementContDischarge;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dyware-Dev01
 */
@Local
public interface ReplacementContDischargeFacadeLocal {

    void create(ReplacementContDischarge replacementContDischarge);

    void edit(ReplacementContDischarge replacementContDischarge);

    void remove(ReplacementContDischarge replacementContDischarge);

    ReplacementContDischarge find(Object id);

    List<ReplacementContDischarge> findAll();

    List<ReplacementContDischarge> findRange(int[] range);

    int count();

}

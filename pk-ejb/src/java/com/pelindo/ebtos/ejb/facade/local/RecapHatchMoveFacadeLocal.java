/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.RecapHatchMove;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface RecapHatchMoveFacadeLocal {

    void create(RecapHatchMove recapHatchMove);

    void edit(RecapHatchMove recapHatchMove);

    void remove(RecapHatchMove recapHatchMove);

    RecapHatchMove find(Object id);

    List<RecapHatchMove> findAll();

    RecapHatchMove findByBentuk3Constraint(String noPpkb, String crane);
    RecapHatchMove findByBentuk3ConstraintTarakan(String noPpkb, String crane, String operation);

    List<RecapHatchMove> findRange(int[] range);

    int count();

    List<Integer> findByPpkb(String no_ppkb);

    public int deleteByNoPpkb(java.lang.String noPpkb);

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.RecapUpahBuruhHatchMove;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Seno
 */
@Local
public interface RecapUpahBuruhHatchMoveFacadeLocal {

    void create(RecapUpahBuruhHatchMove recapUpahBuruhHatchMove);

    void edit(RecapUpahBuruhHatchMove recapUpahBuruhHatchMove);

    void remove(RecapUpahBuruhHatchMove recapUpahBuruhHatchMove);

    RecapUpahBuruhHatchMove find(Object id);

    List<RecapUpahBuruhHatchMove> findAll();

    List<RecapUpahBuruhHatchMove> findRange(int[] range);

    int count();

    public RecapUpahBuruhHatchMove findByBentuk3Constraint(String noPpkb, String crane);

    public int deleteByNoPpkb(String noPpkb);
    
}

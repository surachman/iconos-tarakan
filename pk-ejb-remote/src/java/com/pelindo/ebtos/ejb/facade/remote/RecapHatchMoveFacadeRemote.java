/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.RecapHatchMove;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface RecapHatchMoveFacadeRemote {

    void create(RecapHatchMove recapHatchMove);

    void edit(RecapHatchMove recapHatchMove);

    void remove(RecapHatchMove recapHatchMove);

    RecapHatchMove find(Object id);

    List<RecapHatchMove> findAll();

    List<RecapHatchMove> findRange(int[] range);

    int count();

    List<Integer> findByPpkb(String no_ppkb);

}

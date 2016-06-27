/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PerhitunganPassGate;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author x42jr
 */
@Remote
public interface PerhitunganPassGateFacadeRemote {

    void create(PerhitunganPassGate perhitunganPassGate);

    void edit(PerhitunganPassGate perhitunganPassGate);

    void remove(PerhitunganPassGate perhitunganPassGate);

    PerhitunganPassGate find(Object id);

    List<PerhitunganPassGate> findAll();

    List<PerhitunganPassGate> findRange(int[] range);

    int count();

    Integer deleteByJobSlip(String jobSlip);

    Integer deleteByNoReg(String noReg);

    PerhitunganPassGate findByJobSlip(String jobSlip);
}

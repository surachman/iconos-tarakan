/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.NoPerhitunganBentukTiga;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface NoPerhitunganBentukTigaFacadeLocal {
    void create(NoPerhitunganBentukTiga permohonanBentukTiga);

    void edit(NoPerhitunganBentukTiga permohonanBentukTiga);

    void remove(NoPerhitunganBentukTiga permohonanBentukTiga);

    NoPerhitunganBentukTiga find(Object id);

    List<NoPerhitunganBentukTiga> findAll();
}

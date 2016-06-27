/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.model.db.NoPerhitunganBentukTiga;
import com.pelindo.ebtos.ejb.facade.local.NoPerhitunganBentukTigaFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.NoPerhitunganBentukTigaFacadeRemote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author USER
 */
@Stateless
public class NoPerhitunganBentukTigaFacade extends AbstractFacade<NoPerhitunganBentukTiga> implements NoPerhitunganBentukTigaFacadeLocal,NoPerhitunganBentukTigaFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NoPerhitunganBentukTigaFacade() {
        super(NoPerhitunganBentukTiga.class);
    }
    
}

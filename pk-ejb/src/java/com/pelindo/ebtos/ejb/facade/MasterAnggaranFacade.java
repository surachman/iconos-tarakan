/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterAnggaranFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterAnggaranFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterAnggaran;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AnggiCipta
 */
@Stateless
public class MasterAnggaranFacade extends AbstractFacade<MasterAnggaran> implements MasterAnggaranFacadeLocal, MasterAnggaranFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterAnggaranFacade() {
        super(MasterAnggaran.class);
    }

    public List<Object[]> findAllNative(){
        return getEntityManager().createNamedQuery("MasterAnggaran.Native.findAll").getResultList();
    }
}

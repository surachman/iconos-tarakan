/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.MasterUtilisasiAlatFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterUtilisasiAlatFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterUtilisasiAlat;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author wulan
 */
@Stateless
public class MasterUtilisasiAlatFacade extends AbstractFacade<MasterUtilisasiAlat> implements MasterUtilisasiAlatFacadeLocal, MasterUtilisasiAlatFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterUtilisasiAlatFacade() {
        super(MasterUtilisasiAlat.class);
    }
    public List<Object[]> findAllUtilisasiAlat() {
        return getEntityManager().createNamedQuery("MasterUtilisasiAlat.Native.findAllUtilisasiAlat").getResultList();
    }
}

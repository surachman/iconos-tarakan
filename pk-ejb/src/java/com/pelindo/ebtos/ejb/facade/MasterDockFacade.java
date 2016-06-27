/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterDockFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterDockFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterDock;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterDockFacade extends AbstractFacade<MasterDock> implements MasterDockFacadeLocal, MasterDockFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterDockFacade() {
        super(MasterDock.class);
    }

    public List<String> findDocks() {
        return getEntityManager().createNamedQuery("MasterDock.Native.findDocks").getResultList();

    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterManualActivityTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterManualActivityTypeFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterManualActivityType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dyware-Dev01
 */
@Stateless
public class MasterManualActivityTypeFacade extends AbstractFacade<MasterManualActivityType> implements MasterManualActivityTypeFacadeLocal, MasterManualActivityTypeFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterManualActivityTypeFacade() {
        super(MasterManualActivityType.class);
    }

    public List<Object[]> findAllNative(){
        return getEntityManager().createNamedQuery("MasterManualActivityType.Native.findAllNative").getResultList();
    }

}

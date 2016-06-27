/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterPluggingReeferFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterPluggingReefer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterPluggingReeferFacade extends AbstractFacade<MasterPluggingReefer> implements MasterPluggingReeferFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterPluggingReeferFacade() {
        super(MasterPluggingReefer.class);
    }

    public List<String> findNotInServiceReefer(){
        return getEntityManager().createNamedQuery("MasterPluggingReefer.Native.findNotInServiceReefer").getResultList();
    }

    public List<String> findPluggingCode(){
        return getEntityManager().createNamedQuery("MasterPluggingReefer.Native.findPluggingCode").getResultList();
    }

    public List<Object[]> findPluggingCodeList(){
        return getEntityManager().createNamedQuery("MasterPluggingReefer.Native.findPluggingCodeList").getResultList();
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.RecapJurnalDetailFacadeRemote;
import com.pelindo.ebtos.model.db.RecapJurnal;
import com.pelindo.ebtos.model.db.RecapJurnalDetail;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author R Seno Anggoro
 */
@Stateless
public class RecapJurnalDetailFacade extends AbstractFacade<RecapJurnalDetail> implements RecapJurnalDetailFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapJurnalDetailFacade() {
        super(RecapJurnalDetail.class);
    }

    public List<RecapJurnalDetail> findJKMDetail(String sumber){
        return getEntityManager().createNamedQuery("RecapJurnalDetail.findJKMDetail")
                .setParameter("sumber", sumber)
                .getResultList();
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterCountryFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterCountry;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterCountryFacade extends AbstractFacade<MasterCountry> implements MasterCountryFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterCountryFacade() {
        super(MasterCountry.class);
    }

    public List<Object[]> findAllNative() {
        return getEntityManager().createNamedQuery("MasterCountry.Native.findAll").getResultList();
    }

}

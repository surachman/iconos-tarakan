/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.MasterGrossClassFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterGrossClassFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterGrossClass;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterGrossClassFacade extends AbstractFacade<MasterGrossClass> implements MasterGrossClassFacadeRemote, MasterGrossClassFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterGrossClassFacade() {
        super(MasterGrossClass.class);
    }

    public List<Object[]> findGrossClasses() {
        return (List<Object[]>) getEntityManager().createNamedQuery("MasterGrossClass.Native.findGrossClasses").getResultList();
    }


public List<MasterGrossClass> findByDescriptionOnChange(String description) {
        return (List<MasterGrossClass>) getEntityManager().createNamedQuery("MasterGrossClass.findByDescriptionOnChange")
                .setParameter("description", description)
                .getResultList();
    }
public List<MasterGrossClass> findByDescriptionOnChange2(String description) {
        return (List<MasterGrossClass>) getEntityManager().createNamedQuery("MasterGrossClass.findByDescriptionOnChange2")
                .setParameter("description", description)
                .getResultList();
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterGrossParameterFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterGrossParameterFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterGrossParameter;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author arie
 */
@Stateless
public class MasterGrossParameterFacade extends AbstractFacade<MasterGrossParameter> implements MasterGrossParameterFacadeRemote, MasterGrossParameterFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterGrossParameterFacade() {
        super(MasterGrossParameter.class);
    }

    @Override
    public MasterGrossParameter findByContTypeAndGross(Short contSize, int contGros) {
        try{
            return (MasterGrossParameter)getEntityManager().createNamedQuery("MasterGrossParameter.findByContSizeAndGross").setParameter("contSize", contSize).setParameter("contGross", contGros).getSingleResult();
        }catch(NoResultException ex){
            return null;
        }
    }
    
    
}

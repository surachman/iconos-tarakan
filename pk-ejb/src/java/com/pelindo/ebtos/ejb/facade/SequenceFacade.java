/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.SequenceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.SequenceFacadeRemote;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class SequenceFacade implements SequenceFacadeRemote, SequenceFacadeLocal{
    public static final String EDIFACT_GENERATED_ID_SEQ = "edifact_generated_id_seq";

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public Long nextValue(String sequence){
        return ((BigDecimal) getEntityManager().createNativeQuery("SELECT " + sequence + ".nextval FROM DUAL").getSingleResult()).longValue();
    }

    public Long nextEdifactID(){
//        return (Long) getEntityManager().createNativeQuery("SELECT nextval('" + EDIFACT_GENERATED_ID_SEQ + "');").getSingleResult();
        return ((BigDecimal)  getEntityManager().createNativeQuery("SELECT " + EDIFACT_GENERATED_ID_SEQ + ".nextval FROM DUAL").getSingleResult()).longValue();
    }

    public Long resetSequence(String sequence){
//        return (Long) getEntityManager().createNativeQuery("SELECT setval('" + sequence + "', 0);").getSingleResult();
        return ((BigDecimal) getEntityManager().createNativeQuery("SELECT setval('" + sequence + "', 0) FROM DUAL").getSingleResult()).longValue();
    }
}

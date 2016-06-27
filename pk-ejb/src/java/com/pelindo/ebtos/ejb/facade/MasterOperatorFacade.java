/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.model.OperatorModel;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterOperator;
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
public class MasterOperatorFacade extends AbstractFacade<MasterOperator> implements MasterOperatorFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterOperatorFacade() {
        super(MasterOperator.class);
    }

    public List<OperatorModel> findOperators(){
        List<Object[]> temp = new ArrayList<Object[]>();
        temp = getEntityManager().createNamedQuery("MasterOperator.Native.findOperators").getResultList();

        List<OperatorModel> tempList = new ArrayList<OperatorModel>();

        for(Object[] om : temp){
            OperatorModel opModel = new OperatorModel();
            opModel.setId(om[0].toString());
            opModel.setName(om[1].toString());
            tempList.add(opModel);
        }
        return tempList;
    };

    public List<Object[]> findMasterOperators4HHT(){
        List masterOperators = getEntityManager().createNamedQuery("MasterOperator.Native.findOperators").getResultList();

        if (masterOperators.isEmpty()) {
            masterOperators.add(new String[]{
                "no data", "no data"
            });
        }

        return masterOperators;
    }
}

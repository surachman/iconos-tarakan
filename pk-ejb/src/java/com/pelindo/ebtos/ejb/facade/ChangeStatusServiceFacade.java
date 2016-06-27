/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ChangeStatusServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ChangeStatusServiceFacadeRemote;
import com.pelindo.ebtos.model.db.ChangeStatusService;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lenovo
 */
@Stateless
public class ChangeStatusServiceFacade extends AbstractFacade<ChangeStatusService> implements ChangeStatusServiceFacadeLocal, ChangeStatusServiceFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChangeStatusServiceFacade() {
        super(ChangeStatusService.class);
    }
    
    @Override
    public List<Object[]> findChangeStatusService(String noPPKB){
        String sql = "select css.job_slip, css.cont_no, css.cont_size, mct.name cont_type, css.cont_status, css.over_size, "
                + " css.dg, css.dg_label, css.adm_charge, css.charge, css.total_charge from change_status_service css "
                + " inner join m_container_type mct on mct.cont_type=css.cont_type "
                + " where no_ppkb = '" + noPPKB + "'";
        return getEntityManager().createNativeQuery(sql).getResultList();
    }
    
}

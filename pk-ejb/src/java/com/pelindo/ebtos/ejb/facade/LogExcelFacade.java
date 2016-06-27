/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.LogExcelFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.LogExcelFacadeLocal;
import com.pelindo.ebtos.model.db.LogExcel;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycode-java
 */
@Stateless
public class LogExcelFacade extends AbstractFacade<LogExcel> implements LogExcelFacadeLocal, LogExcelFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public LogExcelFacade() {
        super(LogExcel.class);
    }

    //LogExcel.Native.findAllExcel
    public List<Object[]> findAllExcel(){
        return getEntityManager().createNamedQuery("LogExcel.Native.findAllExcel").getResultList();
    }

}

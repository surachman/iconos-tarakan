/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.MasterAnggaranTrafikBmFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterAnggaranTrafikBmFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterAnggaranTrafikBm;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterAnggaranTrafikBmFacade extends AbstractFacade<MasterAnggaranTrafikBm> implements MasterAnggaranTrafikBmFacadeLocal, MasterAnggaranTrafikBmFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterAnggaranTrafikBmFacade() {
        super(MasterAnggaranTrafikBm.class);
    }

    public List<Object[]> findAllNative(){
        return getEntityManager().createNamedQuery("MasterAnggaranTrafikBm.Native.findAll").getResultList();
    }

    public String findTahunMax(){
        return (String) getEntityManager().createNamedQuery("MasterAnggaranTrafikBm.Native.findTahunMax").getSingleResult();
    }

    public List<Object[]> findAnggaranByTahun(String tahun){
        return getEntityManager().createNamedQuery("MasterAnggaranTrafikBm.Native.findAnggaranByTahun").setParameter(1, tahun).getResultList();
    }

    public List<String> findTahun(){
        return getEntityManager().createNamedQuery("MasterAnggaranTrafikBm.Native.findTahun").getResultList();
    }

}

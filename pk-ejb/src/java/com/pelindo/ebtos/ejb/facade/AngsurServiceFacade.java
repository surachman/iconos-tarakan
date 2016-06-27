/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.AngsurServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.AngsurServiceFacadeRemote;
import com.pelindo.ebtos.model.db.AngsurService;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class AngsurServiceFacade extends AbstractFacade<AngsurService> implements AngsurServiceFacadeRemote, AngsurServiceFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public AngsurServiceFacade() {
        super(AngsurService.class);
    }

    public List<Object[]> findAngsurServiceByPPKBnReg(String no_ppkb, String no_reg) {
        return getEntityManager().createNamedQuery("AngsurService.Native.findByPpkbNReg").setParameter(1, no_ppkb).setParameter(2, no_reg).getResultList();
    }

    public String generateId(String bulan) {
        return (String) getEntityManager().createNamedQuery("AngsurService.Native.generateId").setParameter(1, bulan).getSingleResult();
    }

    public List<Object[]> findPerhitungan(String no_reg) {
        return getEntityManager().createNamedQuery("AngsurService.Native.perhitungan").setParameter(1, no_reg).getResultList();
    }

    public List<Object[]> findByListBatalNotaService(String no_ppkb, String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("AngsurService.Native.findByListBatalNotaService").setParameter(1, no_ppkb).setParameter(2, no_reg).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ServiceCfsFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ServiceCfsFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceCfs;
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
public class ServiceCfsFacade extends AbstractFacade<ServiceCfs> implements ServiceCfsFacadeRemote, ServiceCfsFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceCfsFacade() {
        super(ServiceCfs.class);
    }

    public List<Object[]> findStripping(){
        List<Object[]> list = new ArrayList<Object[]>();
        try{
            list = (List<Object[]>) objectsDecimalsToObjectsInts(getEntityManager().createNamedQuery("ServiceCfs.Native.findStripping").getResultList());
        } catch (NoResultException ex){
            list = null;
        }
        return list;
    }
    
        public List<Object[]> findStuffing(){
        List<Object[]> list = new ArrayList<Object[]>();
        try{
            list = (List<Object[]>) objectsDecimalsToObjectsInts(getEntityManager().createNamedQuery("ServiceCfs.Native.findStuffing").getResultList());
        } catch (NoResultException ex){
            list = null;
        }
        return list;
    }


    public Object[] findByContNo(String no_ppkb, String cont_no){
         Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts((Object[]) getEntityManager().createNamedQuery("ServiceCfs.Native.findByContNo").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByPpkb(String no_ppkb){
         List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(getEntityManager().createNamedQuery("ServiceCfs.Native.findByPpkb").setParameter(1, no_ppkb).getResultList());
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Integer findIsLast(String no_ppkb, String cont_no){
         Integer temp;
        try {
            temp = ((Number) getEntityManager().createNamedQuery("ServiceCfs.Native.findIsLast").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult()).intValue();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public ServiceCfs findLastCfsByPpkbAndContNo(String noPpkb, String contNo){
        try {
            return (ServiceCfs) getEntityManager().createNamedQuery("ServiceCfs.findLastCfsByPpkbAndContNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public ServiceCfs findNotFinishedYetByNoPpkbAndContNo(String noPpkb, String contNo){
        try {
            return (ServiceCfs) getEntityManager().createNamedQuery("ServiceCfs.findNotFinishedYetByNoPpkbAndContNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

}

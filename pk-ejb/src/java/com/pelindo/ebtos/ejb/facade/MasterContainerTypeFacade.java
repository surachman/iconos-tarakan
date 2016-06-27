/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterContainerTypeFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterContainerTypeFacade extends AbstractFacade<MasterContainerType> implements MasterContainerTypeFacadeLocal, MasterContainerTypeFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterContainerTypeFacade() {
        super(MasterContainerType.class);
    }

    public List<Object[]> findAllNative(){
        return getEntityManager().createNamedQuery("MasterContainerType.Native.findAll").getResultList();
    }

    //MasterContainerType.Native.findAllByISO
    public List<Object[]> findAllByISO(String iso_code){
        return getEntityManager().createNamedQuery("MasterContainerType.Native.findAllByISO").setParameter(1, iso_code).getResultList();
    }

    public List<Object[]> findReefer(){
        return getEntityManager().createNamedQuery("MasterContainerType.Native.findReefer").getResultList();
    }

    public MasterContainerType findByIsoCode(String isoCode){
        try {
            return (MasterContainerType) getEntityManager().createNamedQuery("MasterContainerType.findByIsoCode")
                    .setParameter("isoCode", isoCode)
                    .getSingleResult();
        } catch (NoResultException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "NoResultException caught", e);
            return null;
        } catch (NonUniqueResultException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "NonUniqueResultException caught", e);
            return null;
        }
    }

    public MasterContainerType findByIsoCodeSimplified(String isoCode){
        MasterContainerType result = findByIsoCode(isoCode);

        if (result != null) {
            if (result.getFeetMark() == 20) {
                if (result.getMasterContainerTypeInGeneral().getId().equals("BLK")) {
                    return find(20);
                } else if (result.getMasterContainerTypeInGeneral().getId().equals("DRY")) {
                    return find(1);
                } else if (result.getMasterContainerTypeInGeneral().getId().equals("FLT")) {
                    return find(14);
                } else if (result.getMasterContainerTypeInGeneral().getId().equals("HC")) {
                    return find(21);
                } else if (result.getMasterContainerTypeInGeneral().getId().equals("OPS")) {
                    return find(65);
                } else if (result.getMasterContainerTypeInGeneral().getId().equals("OPT")) {
                    return find(12);
                } else if (result.getMasterContainerTypeInGeneral().getId().equals("RFR")) {
                    return find(2);
                } else if (result.getMasterContainerTypeInGeneral().getId().equals("TNK")) {
                    return find(19);
                }
            } else if (result.getFeetMark() == 40){
                if (result.getMasterContainerTypeInGeneral().getId().equals("BLK")) {
                    return find(36);
                } else if (result.getMasterContainerTypeInGeneral().getId().equals("DRY")) {
                    return find(26);
                } else if (result.getMasterContainerTypeInGeneral().getId().equals("FLT")) {
                    return find(32);
                } else if (result.getMasterContainerTypeInGeneral().getId().equals("HC")) {
                    return find(37);
                } else if (result.getMasterContainerTypeInGeneral().getId().equals("OPS")) {
                    return find(98);
                } else if (result.getMasterContainerTypeInGeneral().getId().equals("OPT")) {
                    return find(30);
                } else if (result.getMasterContainerTypeInGeneral().getId().equals("RFR")) {
                    return find(29);
                } else if (result.getMasterContainerTypeInGeneral().getId().equals("TNK")) {
                    return find(83);
                }
            } else if (result.getFeetMark() == 45){
                if (result.getMasterContainerTypeInGeneral().getId().equals("DRY")) {
                    return find(1752);
                } else if (result.getMasterContainerTypeInGeneral().getId().equals("HC")) {
                    return find(99);
                }
            }
        }

        return null;
    }
}

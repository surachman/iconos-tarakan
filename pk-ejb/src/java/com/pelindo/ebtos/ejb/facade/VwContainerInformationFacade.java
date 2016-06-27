/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.VwContainerInformationFacadeRemote;
import com.pelindo.ebtos.model.db.VwContainerInformation;
import java.util.ArrayList;
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
public class VwContainerInformationFacade extends AbstractFacade<VwContainerInformation> implements VwContainerInformationFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public VwContainerInformationFacade() {
        super(VwContainerInformation.class);
    }

    @Override
    public Object[] findContInfo(String contNo) {
        Object[] temp = new Object[4];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("VwContainerInformation.Native.findContInfo").setParameter(1, contNo).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    @Override
    public List<Object[]> findHistoryContList(String contNo) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("VwContainerInformation.Native.findHistoryContList").setParameter(1, contNo).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    @Override
    public List<Object[]> findVesselDetail(String contNo, String ppkbNo) {
        //Object[] temp = new Object[7];
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("VwContainerInformation.Native.findVesselDetail").setParameter(1, contNo).setParameter(2, ppkbNo).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    @Override
    public List<Object[]> findContainerDetail(String contNo, String ppkbNo) {
        //Object[] temp = new Object[10];
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("VwContainerInformation.Native.findContainerDetail").setParameter(1, contNo).setParameter(2, ppkbNo).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    @Override
    public List<Object[]> findHandlingDischarge(String contNo, String ppkbNo) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("VwContainerInformation.Native.findHandlingDischarge").setParameter(1, contNo).setParameter(2, ppkbNo).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    @Override
    public List<Object[]> findHandlingLoad(String contNo, String ppkbNo) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("VwContainerInformation.Native.findHandlingLoad").setParameter(1, contNo).setParameter(2, ppkbNo).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    @Override
    public List<Object[]> findGateDischarge(String contNo, String ppkbNo) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("VwContainerInformation.Native.findGateDischarge").setParameter(1, contNo).setParameter(2, ppkbNo).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    @Override
    public List<Object[]> findGateLoad(String contNo, String ppkbNo) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("VwContainerInformation.Native.findGateLoad").setParameter(1, contNo).setParameter(2, ppkbNo).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    @Override
    public List<String> findContList(String query) {
        List<String> temp = new ArrayList<String>();
        try {
            temp = getEntityManager().createNamedQuery("VwContainerInformation.Native.findContList").setParameter(1, query).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
}

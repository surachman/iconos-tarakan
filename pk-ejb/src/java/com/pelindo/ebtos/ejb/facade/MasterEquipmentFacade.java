/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterSettingAppFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.exception.MasterSettingAppNotFoundException;
import com.pelindo.ebtos.exception.MasterSettingAppValueNotValidException;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterEquipmentType;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterEquipmentFacade extends AbstractFacade<MasterEquipment> implements MasterEquipmentFacadeRemote {
    private static final String HEAD_TRUCK_ID_PARAM = "ebtos.m_equipment.head_truck_id";
    
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;
    @EJB private MasterSettingAppFacadeLocal masterSettingAppFacade;
    @EJB private MasterEquipmentTypeFacadeRemote masterEquipmentTypeFacade;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterEquipmentFacade() {
        super(MasterEquipment.class);
    }

    @Override
    public List<Object[]> findAllNative() {
        return getEntityManager().createNamedQuery("MasterEquipment.Native.findAll").getResultList();
    }

    @Override
    public List<String> findCrane() {
        return getEntityManager().createNamedQuery("MasterEquipment.Native.findCrane").getResultList();
    }

    public List<Object> findCrane4HHT() {
        return getEntityManager().createNamedQuery("MasterEquipment.Native.findCrane").getResultList();
    }

    @Override
    public List<String> findHt() {
        return getEntityManager().createNamedQuery("MasterEquipment.Native.findHt").getResultList();
    }

    public List<Object> findHeadTruck4HHT() {
        return getEntityManager().createNamedQuery("MasterEquipment.Native.findHt").getResultList();
    }

    public List<Object> findTango4HHT() {
        return getEntityManager().createNamedQuery("MasterEquipment.Native.findTt").getResultList();
    }

    @Override
    public List<String> findTt() {
        return getEntityManager().createNamedQuery("MasterEquipment.Native.findTt").getResultList();
    }

    //MasterEquipment.Native.findCraneForView
    public List<Object[]> findCraneForView() {
        return getEntityManager().createNamedQuery("MasterEquipment.Native.findCraneForView").getResultList();
    }

    //MasterEquipment.Native.findHtForView
    public List<Object[]> findHtForView() {
        return getEntityManager().createNamedQuery("MasterEquipment.Native.findHtForView").getResultList();
    }

    //MasterEquipment.Native.findTangoForView
    public List<Object[]> findTangoForView() {
        return getEntityManager().createNamedQuery("MasterEquipment.Native.findTangoForView").getResultList();
    }

    public List<Object[]> findCraneExcKapal() {
        return getEntityManager().createNamedQuery("MasterEquipment.Native.findCraneExcKapal").getResultList();
    }

    public List<Object[]> findOwnerReport(String owner) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("MasterEquipment.Native.findOwnerReport").setParameter(1, owner).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findEquipmentSewaAlat() {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("MasterEquipment.Native.findEquipmentSewaAlat").getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    @Override
    public List<MasterEquipment> findMasterEquipmentHTOnly() throws MasterSettingAppNotFoundException, MasterSettingAppValueNotValidException{
        MasterSettingApp headTruckIdSetting = masterSettingAppFacade.find(HEAD_TRUCK_ID_PARAM);
        if (headTruckIdSetting != null) {
            MasterEquipmentType masterEquipmentType = masterEquipmentTypeFacade.find(headTruckIdSetting.getValueString());
            if (masterEquipmentType != null) {
                return findMasterEquipmentByType(masterEquipmentType.getEquipmentTypeCode());
            }
            throw new MasterSettingAppValueNotValidException();
        }
        throw new MasterSettingAppNotFoundException();
    }

    @Override
    public List<MasterEquipment> findMasterEquipmentByType(String type) {
        return getEntityManager().createNamedQuery("MasterEquipment.findByEquipCode")
                .setParameter("equipCode", type)
                .getResultList();
    }

}

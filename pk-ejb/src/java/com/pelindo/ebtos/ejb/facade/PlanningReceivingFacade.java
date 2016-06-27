/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PlanningReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeInGeneralFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningReceivingFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningReceiving;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class PlanningReceivingFacade extends AbstractFacade<PlanningReceiving> implements PlanningReceivingFacadeRemote, PlanningReceivingFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private MasterContainerTypeInGeneralFacadeRemote masterContainerTypeInGeneralFacadeRemote;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PlanningReceivingFacade() {
        super(PlanningReceiving.class);
    }

    public List<Object[]> findPlanningReceivingList(Integer idCont) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningReceiving.Native.findPlanningReceivingList").setParameter(1, idCont).getResultList()
        );
    }

    /*
     * return
     *      0 = myc.cont_no,
     *      1 = myc.block,
     *      2 = myc.slot,
     *      3 = myc.row,
     *      4 = myc.tier,
     *      5 = pya.fr_row,
     *      6 = pya.to_row
     */
    public Object[] findRecommendedLocation(String noPpkb, String portCode, Integer contType, String grossClass, Short contSize, String contStatus, String overSize, String dg, String isExport) {
        try {
            MasterContainerType masterContainerType = masterContainerTypeFacadeRemote.find(contType);
            contType = masterContainerTypeInGeneralFacadeRemote.findGenericContType(masterContainerType.getFeetMark(), masterContainerType.getMasterContainerTypeInGeneral().getId());

            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PlanningReceiving.Native.findRecommendedLocation")
                    .setParameter(1, noPpkb)
                    .setParameter(2, portCode)
                    .setParameter(3, contType)
                    .setParameter(4, grossClass)
                    .setParameter(5, contSize)
                    .setParameter(6, contStatus)
                    .setParameter(7, overSize)
                    .setParameter(8, dg)
                    .setParameter(9, isExport)
                    .getSingleResult()
            );
        } catch (RuntimeException re) {
            return null;
        }
    }

    public List<Integer> findAllIdByIdReceivingAllocation(int id){
        return decimalsToInts(
                getEntityManager().createNamedQuery("PlanningReceiving.Native.findAllIdByIdReceivingAllocation").setParameter(1, id).getResultList()
        );
    }

    public List<Object[]> findAllByIdReceivingAllocation(int id) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningReceiving.Native.findAllByIdReceivingAllocation").setParameter(1, id).getResultList()
        );
    }

    public Integer findLastOfId(){
        return decimalToInt(
                (BigDecimal) getEntityManager().createNamedQuery("PlanningReceiving.Native.findLastOfId").getSingleResult()
        );
    }
}

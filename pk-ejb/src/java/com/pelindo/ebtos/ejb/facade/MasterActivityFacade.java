package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.MasterActivityFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterContainerTypeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.LogManager;
//import org.slf4j.Logger;
import sun.java2d.opengl.OGLContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterActivityFacade extends AbstractFacade<MasterActivity> implements MasterActivityFacadeRemote, MasterActivityFacadeLocal {
   
    private Map<String, Object> TRANS_TO_ACTIVITY_CODE_DEFAULT_PARAMS = new LinkedHashMap<String, Object>()
    {{
                put("MAIN_ACTIVITY", "HANDLING");
                put("CONT_TYPE", "ALLTYPE");
                put("CONT_SIZE", 0);
                put("SHIPPING_CAT", "d");
                put("IS_OVER_DIM", "FALSE");
                put("IS_USE_SLING", "FALSE");
                put("IS_DANGER", "FALSE");
                put("IS_LANDED", "FALSE");
                put("IS_USE_SPECIAL_EQPMNT", "FALSE");
                put("IS_OPEN_DOOR", "FALSE");
                put("IS_UNCATEG", "FALSE");
                put("UNCATEG_MIN_SIZE", 0);
                put("UNCATEG_MAX_SIZE", 100);
                put("SHIFTING_CAT", "NOLANDED");
                put("RENT_FORKLIFT_TYPE", "N/A");
                put("STRIP_STUFF_FHANDLING_CAT", "N/A");
                put("MECHANICAL_CAT", "N/A");
                put("CRANE", "L"); //SHIP_OR_LAND_CRANE_CATEGORY
                put("IS_PLUGGING", "FALSE");
                put("CONT_TYPE_IN_GEN", "N/A");
                put("IS_DG_LABEL", "FALSE");
                put("DANGEROUS_CLASS", "N/A");
                put("EQUIPMENT_TYPE", "N/A");
                put("IS_TWENTY_ONE_FEET", "FALSE");
    }};
    
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @EJB
    private MasterContainerTypeFacadeLocal masterContainerTypeFacadeLocal;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterActivityFacade() {
        super(MasterActivity.class);
    }

    public List<Object[]> findAllActivity(){
        return getEntityManager().createNamedQuery("MasterActivity.Native.findAllActivity").getResultList();
    }

     public List<Object[]> findActivityBySimulasi(){
        return getEntityManager().createNamedQuery("MasterActivity.Native.findActivityBySimulasi").getResultList();
    }

    public List<Object[]> findAllActivityExc02(){
        return getEntityManager().createNamedQuery("MasterActivity.Native.findAllActivityExc02").getResultList();
    }
    
    private String translateActivityCodeForTarakan(Map<String, Object> query) {
        Query q = getEntityManager().createNamedQuery("MasterActivity.Native.translateActivityCodeForTarakan");
        int i = 1;
        StringBuilder sb = new StringBuilder();
        for(String key: query.keySet())
        {
            Object value = query.get(key);
            q = q.setParameter(i, value);
            sb.append(key+" => "+value+",");
            i++;
        }
       
        String result = null;
        try {
                   result = (String) q.getSingleResult();

        } catch(NoResultException nre) {
        }
        System.out.println("trans activity code | query: "+sb.toString()+" result: "+result);
       return result;
    }
    
    private String translateActivityCode(Map<String, Object> query){
        Query q = getEntityManager().createNamedQuery("MasterActivity.Native.translateActivityCode");
        int i = 1;
        StringBuilder sb = new StringBuilder();
        for(String key: TRANS_TO_ACTIVITY_CODE_DEFAULT_PARAMS.keySet())
        {
            Object value = query.get(key);
            if(value == null)
                value = TRANS_TO_ACTIVITY_CODE_DEFAULT_PARAMS.get(key);
            q = q.setParameter(i, value);
            sb.append(key+" => "+value+",");
            i++;
        }
       
        String result = null;
        try {
                   result = (String) q.getSingleResult();

        } catch(NoResultException nre) {
        }
        System.out.println("trans activity code | query: "+sb.toString()+" result: "+result);
       return result;
    }

    public String translateHandlingActivityCode(final String contType, final Short contSize, final String crane)
    {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "STEVEDORING");
            put("SHIFTING_CAT", "ALLTYPE");
            put("CONT_TYPE", contType);
            put("CONT_SIZE", contSize);
            put("CRANE", crane);
            
        }};
        return translateActivityCodeForTarakan(query);
    }
    
    public String translateOtherPackageActivityCode(final String mainActivity, final String contType, final Short contSize, final String crane)
    {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", mainActivity);
            put("SHIFTING_CAT", "ALLTYPE");
            put("CONT_TYPE", contType);
            put("CONT_SIZE", contSize);
            put("CRANE", crane);
            
        }};
        return translateActivityCodeForTarakan(query);

    }
    
    public String translateShiftingActivityCode(final String mainActivity, final String shiftingCategory, final String contType, final Short contSize, final String crane)
    {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", mainActivity);
            put("SHIFTING_CAT", shiftingCategory);
            put("CONT_TYPE", contType);
            put("CONT_SIZE", contSize);
            put("CRANE", crane);
            
        }};
        return translateActivityCodeForTarakan(query);
    }
    
    public String translateStevedoringActivityCode(final String contType, final Short contSize, 
            final String crane, final Boolean overSize, final String equipmentType, 
            final Boolean dangerous, final Boolean hasLabel, final String dangerousClass,
            final Boolean seling, final Boolean twentyOneFeet) {
        
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "STEVEDORING");
            put("CONT_TYPE", contType);
            put("CONT_SIZE", contSize);
            put("CRANE", crane);
            put("IS_OVERSIZE", overSize == null ? null : (overSize ? "TRUE" : "FALSE"));
            put("IS_DANGEROUS", dangerous == null ? null : (dangerous ? "TRUE" : "FALSE"));
            put("HAS_LABEL", hasLabel == null ? null : (hasLabel ? "TRUE" : "FALSE"));
            put("DANGEROUS_CLASS", dangerousClass);
            put("EQUIPMENT_TYPE", equipmentType);
            put("IS_SELING", seling == null ? null : (seling ? "TRUE" : "FALSE"));
            put("IS_TWENTY_ONE_FEET", twentyOneFeet == null ? null : (twentyOneFeet ? "TRUE" : "FALSE"));
        }};
        return translateActivityCode(query);
    }
    
    public String translateHaulageTruckingActivityCode(final String contType, final Short contSize, 
            final String crane, final Boolean overSize, final String equipmentType,
            final Boolean dangerous, final Boolean hasLabel, final String dangerousClass,
            final Boolean seling, final Boolean twentyOneFeet) {
        
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "HAULAGE_TRUCKING");
            put("CONT_TYPE", contType);
            put("CONT_SIZE", contSize);
            put("CRANE", crane);
            put("IS_OVERSIZE", overSize == null ? null : (overSize ? "TRUE" : "FALSE"));
            put("IS_DANGEROUS", dangerous == null ? null : (dangerous ? "TRUE" : "FALSE"));
            put("HAS_LABEL", hasLabel == null ? null : (hasLabel ? "TRUE" : "FALSE"));
            put("DANGEROUS_CLASS", dangerousClass);
            put("EQUIPMENT_TYPE", equipmentType);
            put("IS_SELING", seling == null ? null : (seling ? "TRUE" : "FALSE"));
            put("IS_TWENTY_ONE_FEET", twentyOneFeet == null ? null : (twentyOneFeet ? "TRUE" : "FALSE"));
        }};
        return translateActivityCode(query);
    }
    
    public String translateOtherHandlingChargesActivityCode(final String contType, final Short contSize, 
            final String crane, final Boolean overSize, final String equipmentType,
            final Boolean dangerous, final Boolean hasLabel, final String dangerousClass,
            final Boolean seling, final Boolean twentyOneFeet) {
        
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "OTHER_CHARGES_HANDLING");
            put("CONT_TYPE", contType);
            put("CONT_SIZE", contSize);
            put("CRANE", crane);
            put("IS_OVERSIZE", overSize == null ? null : (overSize ? "TRUE" : "FALSE"));
            put("IS_DANGEROUS", dangerous == null ? null : (dangerous ? "TRUE" : "FALSE"));
            put("HAS_LABEL", hasLabel == null ? null : (hasLabel ? "TRUE" : "FALSE"));
            put("DANGEROUS_CLASS", dangerousClass);
            put("EQUIPMENT_TYPE", equipmentType);
            put("IS_SELING", seling == null ? null : (seling ? "TRUE" : "FALSE"));
            put("IS_TWENTY_ONE_FEET", twentyOneFeet == null ? null : (twentyOneFeet ? "TRUE" : "FALSE"));
        }};
        return translateActivityCode(query);
    }

    public String translateLoLoActivityCode(final String contType, final Short contSize, 
            final Boolean overSize, final Boolean dangerous, 
            final Boolean hasLabel, final String dangerousClass, final String equipmentType) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "LOLO");
            put("CONT_TYPE", contType);
            put("CONT_SIZE", contSize);
            put("IS_OVERSIZE", overSize == null ? null : (overSize ? "TRUE" : "FALSE"));
            put("IS_OVERSIZE", overSize == null ? null : (overSize ? "TRUE" : "FALSE"));
            put("IS_DANGEROUS", dangerous == null ? null : (dangerous ? "TRUE" : "FALSE"));
            put("HAS_LABEL", hasLabel == null ? null : (hasLabel ? "TRUE" : "FALSE"));
            put("DANGEROUS_CLASS", dangerousClass);
            put("EQUIPMENT_TYPE", equipmentType);
        }};
        return translateActivityCode(query);
    }

    public String translateSlingActivityCode(final Short contSize) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "SLING");
            put("CONT_SIZE", contSize);
        }};
        return translateActivityCode(query);
    }

    public String translatePassGateActivityCode(final String contType, final Short contSize) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "PASS_GATE");
            put("CONT_TYPE", contType);
            put("CONT_SIZE", contSize);
        }};
        return translateActivityCode(query);
    }
    
    public String translateAngkutActivityCode(final String contType, final Short contSize) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "ANGKUT");
            put("CONT_TYPE", contType);
            put("CONT_SIZE", contSize);
        }};
        return translateActivityCode(query);
    }

    public String translatePenumpukanActivityCode(final String contType, String isoCode, 
            final Boolean overSize, final Short contSize, final Boolean isPlugging) {
        final MasterContainerType masterContainerType = masterContainerTypeFacadeLocal.findByIsoCodeSimplified(isoCode);
        
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "PENUMPUKAN");
            put("CONT_TYPE", contType);
            put("CONT_SIZE", contSize);
            put("IS_OVERSIZE", overSize == null ? null : (overSize ? "TRUE" : "FALSE"));
            put("IS_PLUGGING", isPlugging == null ? null : (isPlugging ? "TRUE" : "FALSE"));
            if(masterContainerType != null){
                put("CONT_TYPE_IN_GENERAL", masterContainerType.getMasterContainerTypeInGeneral().getId());
            }
        }};
        return translateActivityCode(query);
        
    }

    public String translateHandlingUcActivityCode(final Integer weight, final String crane) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "HANDLING");
            put("CRANE", crane);
            put("IS_UC", "TRUE");
            put("WEIGHT", weight);
        }};
        return translateActivityCode(query);
    }

    public String translateLoLoUcActivityCode(final Integer weight) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "LOLO");
            put("IS_UC", "TRUE");
            put("WEIGHT", weight);
        }};
        return translateActivityCode(query);
    }

    public String translatePenumpukanUcActivityCode() {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "PENUMPUKAN");
            put("IS_UC", "TRUE");
        }};
        return translateActivityCode(query);
    }

    public String translatePassGateUcActivityCode() {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "PASS_GATE");
            put("IS_UC", "TRUE");
        }};
        return translateActivityCode(query);
    }

    public String translateUpahBuruhStrippingStuffingActivityCode() {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "UPAH_BURUH");
            put("IS_UC", "TRUE");
        }};
        return translateActivityCode(query);
    }

    public String translateTranshipmentActivityCode(final Boolean overSize, final String contType, 
            final String crane, final Short contSize) {
        
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "TRANSHIPMENT");
            put("CONT_TYPE", contType);
            put("CONT_SIZE", contSize);
            put("CRANE", crane);
            put("IS_OVERSIZE", overSize == null ? null : (overSize ? "TRUE" : "FALSE"));
        }};
        return translateActivityCode(query);
    }

    public String translateTranshipmentUcActivityCode(final Integer weight, final String crane) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "TRANSHIPMENT");
            put("IS_UC", "TRUE");
            put("CRANE", crane);
            put("WEIGHT", weight);
        }};
        return translateActivityCode(query);
    }

    public String translateShiftingActivityCode(final Boolean overSize, final String contType, 
            final String shiftingCat, final String crane, final Short contSize) {
        
       Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "SHIFTING");
            put("CONT_TYPE", contType);
            put("CONT_SIZE", contSize);
            put("CRANE", crane);
            put("SHIFTING_CAT", shiftingCat);
            put("IS_OVERSIZE", overSize == null ? null : (overSize ? "TRUE" : "FALSE"));
        }};
        
        return translateActivityCode(query);
    }

public String translateShiftingActivityCodeEquipment(final Boolean overSize, final String contType, 
            final String shiftingCat, final String crane, final Short contSize, final String equipmentType) {
        
       Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "SHIFTING");
            put("CONT_TYPE", contType);
            put("CONT_SIZE", contSize);
            put("CRANE", crane);
            put("SHIFTING_CAT", shiftingCat);
             put("EQUIPMENT_TYPE", equipmentType);
            put("IS_OVERSIZE", overSize == null ? null : (overSize ? "TRUE" : "FALSE"));
        }};
        
        return translateActivityCode(query);
    }    

    public String translateShiftingUcActivityCode(final Integer weight, final String crane, final Boolean isLanded) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "SHIFTING");
            put("IS_UC", "TRUE");
            put("WEIGHT", weight);
            put("IS_LANDED", isLanded == null ? null : (isLanded ? "TRUE" : "FALSE"));
        }};
        
        return translateActivityCode(query);
    }

    public String translateUbahBuruhHandlingActivityCode(final String contType, final Short contSize, final Boolean overSize) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "UPAH_BURUH_HANDLING");
            put("CONT_TYPE", contType);
            put("CONT_SIZE", contSize);
            put("IS_OVERSIZE", overSize == null ? null : (overSize ? "TRUE" : "FALSE"));
        }};
        
        return translateActivityCode(query);
    }

    public String translateUbahBuruhShiftingActivityCode(final String shiftingType, 
            final Short contSize, final Boolean overSize) {
        
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "UPAH_BURUH_SHIFTING");
            put("SHIFTING_CAT", shiftingType);
            put("CONT_SIZE", contSize);
            put("IS_OVERSIZE", overSize == null ? null : (overSize ? "TRUE" : "FALSE"));
            
        }};
        return translateActivityCode(query);
    }

    public String translateCancelLoadingActivityCode(final String contType, final Short contSize) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "CANCEL_LOADING");
            put("CONT_TYPE", contType);
            put("CONT_SIZE", contSize);
        }};
        return translateActivityCode(query);
    }

    public String translateExtraMovementActivityCode(final Short contSize, final Boolean isUseSpecialEquipment) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "EXTRA_MOVEMENT");
            put("CONT_SIZE", contSize);
            put("IS_USE_SPECIAL_EQPMNT", isUseSpecialEquipment == null ? null : (isUseSpecialEquipment ? "TRUE" : "FALSE"));
        }};
        return translateActivityCode(query);
    }

    public String translateBehandleActivityCode(final Short contSize, final Boolean isUseSpecialEquipment) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "BEHANDLE");
            put("CONT_SIZE", contSize);
            put("IS_USE_SPECIAL_EQPMNT", isUseSpecialEquipment == null ? null : (isUseSpecialEquipment ? "TRUE" : "FALSE"));
        }};
        return translateActivityCode(query);
    }

    public String translateAirKapalActivityCode() {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "AIR_KAPAL");
        }};
        return translateActivityCode(query);
    }

    public String translateHatchMoveActivityCode(final String crane) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "HATCH_MOVE");
            put("CRANE", crane);
        }};
        return translateActivityCode(query);
    }
    public String translateStrippingStuffingActivityCode(final Short contSize, final boolean isOpenDoor) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "STRIPPING_STUFFING");
            put("CONT_SIZE", contSize);
            put("IS_OPEN_DOOR", (isOpenDoor ? "TRUE" : "FALSE"));
        }};
        return translateActivityCode(query);
    }

    public String translateStrippingStuffingActivityCode2(final Short contSize,final String status, final boolean isOpenDoor) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "STRIPPING_STUFFING");
            put("CONT_SIZE", contSize);
            put("CONT_TYPE", status);
            put("IS_OPEN_DOOR", (isOpenDoor ? "TRUE" : "FALSE"));
        }};
        return translateActivityCode(query);
    }

    @Override
    public String translateCancelDocumentActivityCode() {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "CANCEL_DOCUMENT");
        }};
        return translateActivityCode(query);
    }

    @Override
    public String translateUbahBuruhUcActivityCode() {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "UPAH_BURUH");
            put("IS_UC", "TRUE");
        }};
        return translateActivityCode(query);
    }

    public String translateDischargeToLoadActivityCode(final int contSize, final String contType) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "DISCHARGE_LOAD");
            put("CONT_TYPE", contType);
            put("CONT_SIZE", contSize);
        }};
        return translateActivityCode(query);
    }

    public String translateSewaForkLiftActivityCode(final String forklift, final int contSize) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "SEWA_FORKLIFT");
            put("FORKLIFT", forklift);
            put("CONT_SIZE", contSize);
        }};
        return translateActivityCode(query);
    }

    public String translateStrippingStuffingFullHandlingActivityCode(final String fullHandlingName) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "STRIPPING_STUFFING");
            put("STRIP_STUFF_FHANDLING_CAT", fullHandlingName);
        }};
        return translateActivityCode(query);
    }

    public String translateStrippingStuffingMekanikActivityCode(final String mechanicalCat) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "STRIPPING");
            put("MECHANICAL_CAT", mechanicalCat);
        }};
        return translateActivityCode(query);
    }

    @Override
    public String translateBehandleActivityCode(final Short contSize) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "BEHANDLE");
            put("CONT_SIZE", contSize);
        }};
        return translateActivityCode(query);
    }

    @Override
    public String translateSupervisiActivityCode(final Short contSize) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "SUPERVISION");
            put("CONT_SIZE", contSize);
        }};
        return translateActivityCode(query);
    }

    public String translatePluggingActivityCode(final short contSize) {
       Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "PLUGGING");
            put("CONT_SIZE", contSize);
        }};
        return translateActivityCode(query);
    }

    public String translateMonitoringActivityCode(final short contSize) {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "MONITORING");
            put("CONT_SIZE", contSize);
        }};
        return translateActivityCode(query);
    }

    public String translateJasaDermagaUcActivityCode() {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "JASA_DERMAGA");
            put("IS_UC", "TRUE");
        }};
        return translateActivityCode(query);
    }

    @Override
    public String translateUpahBuruhHatchMoveActivityCode() {
        Map<String, Object> query = new HashMap<String, Object>(){{
            put("MAIN_ACTIVITY", "UPAH_BURUH_HATCH_MOVE");
        }};
        return translateActivityCode(query);
    }
}

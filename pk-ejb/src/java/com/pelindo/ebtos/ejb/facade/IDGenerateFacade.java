/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.IDGeneratorFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterSettingAppFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.SequenceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.UsableFakturPajakFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.UsableInvoiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author R Seno Anggoro
 */
@Stateless
public class IDGenerateFacade implements IDGeneratorFacadeRemote, IDGeneratorFacadeLocal{
    public static final String MONTH = "MONTH";
    public static final String YEAR = "YEAR";

    public static final String JKM_ID_SEQ = "jkm_generated_id_seq";
    public static final String PPKB_ID_SEQ = "ppkb_generated_id_seq";
    public static final String K_PPKB_ID_SEQ = "k_ppkb_generated_id_seq";
    public static final String REGISTRATION_ID_SEQ = "registration_generated_id_seq";
    public static final String INVOICE_ID_SEQ = "invoice_generated_id_seq";
    public static final String INVOICE_NM_ID_SEQ = "invoice_nm_generated_id_seq";
    public static final String FAKTUR_PAJAK_ID_SEQ = "faktur_pajak_generated_id_seq";
    public static final String JOB_SLIP_ID_SEQ = "job_slip_generated_id_seq";
    public static final String PPKB_PLUG_ONLY_ID_SEQ = "ppkb_plug_only_id_seq";

    private static final String JKM_ID_PATTERN_PARAM = "id.jkm.pattern";
    private static final String JKM_ID_NEXT_RESET_PARAM = "id.jkm.next_reset";
    private static final String JKM_ID_RESET_TIME_PARAM = "id.jkm.reset_time";
    private static final String PPKB_ID_PATTERN_PARAM = "id.ppkb.pattern";
    private static final String PPKB_ID_NEXT_RESET_PARAM = "id.ppkb.next_reset";
    private static final String PPKB_ID_RESET_TIME_PARAM = "id.ppkb.reset_time";
    private static final String PPKB_PLUG_ONLY_ID_PATTERN_PARAM = "id.ppkb_plug_only.pattern";
    private static final String PPKB_PLUG_ONLY_ID_NEXT_RESET_PARAM = "id.ppkb_plug_only.next_reset";
    private static final String PPKB_PLUG_ONLY_ID_RESET_TIME_PARAM = "id.ppkb_plug_only.reset_time";
    private static final String K_PPKB_ID_PATTERN_PARAM = "id.k_ppkb.pattern";
    private static final String K_PPKB_ID_NEXT_RESET_PARAM = "id.k_ppkb.next_reset";
    private static final String K_PPKB_ID_RESET_TIME_PARAM = "id.k_ppkb.reset_time";
    private static final String REGISTRATION_ID_PATTERN_PARAM = "id.registration.pattern";
    private static final String REGISTRATION_ID_NEXT_RESET_PARAM = "id.registration.next_reset";
    private static final String REGISTRATION_ID_RESET_TIME_PARAM = "id.registration.reset_time";
    private static final String INVOICE_ID_PATTERN_PARAM = "id.invoice.pattern";
    private static final String INVOICE_ID_NEXT_RESET_PARAM = "id.invoice.next_reset";
    private static final String INVOICE_ID_RESET_TIME_PARAM = "id.invoice.reset_time";
    private static final String INVOICE_NM_ID_PATTERN_PARAM = "id.invoice_nm.pattern";
    private static final String INVOICE_NM_ID_NEXT_RESET_PARAM = "id.invoice_nm.next_reset";
    private static final String INVOICE_NM_ID_RESET_TIME_PARAM = "id.invoice_nm.reset_time";
    private static final String FAKTUR_PAJAK_ID_PATTERN_PARAM = "id.faktur_pajak.pattern";
    private static final String FAKTUR_PAJAK_ID_NEXT_RESET_PARAM = "id.faktur_pajak.next_reset";
    private static final String FAKTUR_PAJAK_ID_RESET_TIME_PARAM = "id.faktur_pajak.reset_time";
    private static final String JOB_SLIP_ID_PATTERN_PARAM = "id.job_slip.pattern";
    private static final String JOB_SLIP_ID_NEXT_RESET_PARAM = "id.job_slip.next_reset";
    private static final String JOB_SLIP_ID_RESET_TIME_PARAM = "id.job_slip.reset_time";
    private static final String BENTUK_TIGA_ID_PATTERN ="id.bentuk_tiga.pattern";
    private static final String BENTUK_TIGA_ID_NEXT_RESET_PARAM ="id.bentuk_tiga.next_reset";
    private static final String BENTUK_TIGA_RESET_TIME_PARAM ="id.bentuk_tiga.reset_time";
    

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @EJB
    private MasterSettingAppFacadeLocal masterSettingAppFacade;
    @EJB
    private SequenceFacadeLocal sequenceFacade;
    @EJB
    private UsableFakturPajakFacadeLocal usableFakturPajakFacadeLocal;
    @EJB
    private UsableInvoiceFacadeLocal usableInvoiceFacadeLocal;

    
    public String generatePpkbID(){
        handleExpiredSequence(PPKB_ID_SEQ, PPKB_ID_NEXT_RESET_PARAM, PPKB_ID_RESET_TIME_PARAM);
        return generateID(PPKB_ID_PATTERN_PARAM, PPKB_ID_SEQ);
    }

    public String generatePpkbPlugOnlyID(){
        handleExpiredSequence(PPKB_PLUG_ONLY_ID_SEQ, PPKB_PLUG_ONLY_ID_NEXT_RESET_PARAM, PPKB_PLUG_ONLY_ID_RESET_TIME_PARAM);
        return generateID(PPKB_PLUG_ONLY_ID_PATTERN_PARAM, PPKB_PLUG_ONLY_ID_SEQ);
    }

    public String generatePpkbKeuanganID(){
        handleExpiredSequence(K_PPKB_ID_SEQ, K_PPKB_ID_NEXT_RESET_PARAM, K_PPKB_ID_RESET_TIME_PARAM);
        return generateID(K_PPKB_ID_PATTERN_PARAM, K_PPKB_ID_SEQ);
    }
    
    public String generateRegistrationID(){
        handleExpiredSequence(REGISTRATION_ID_SEQ, REGISTRATION_ID_NEXT_RESET_PARAM, REGISTRATION_ID_RESET_TIME_PARAM);
        return generateID(REGISTRATION_ID_PATTERN_PARAM, REGISTRATION_ID_SEQ);
    }
    
    public String generateInvoiceID(){
        String id = usableInvoiceFacadeLocal.getAvailableNoInvoice();

        if (id != null) {
            return id;
        } else {
            handleExpiredSequence(INVOICE_ID_SEQ, INVOICE_ID_NEXT_RESET_PARAM, INVOICE_ID_RESET_TIME_PARAM);
            return generateID(INVOICE_ID_PATTERN_PARAM, INVOICE_ID_SEQ);
        }
    }
    
    /*
        @Add
        Author Srach
    */
    
    public String generateBentukTigaID(){
        String pattern = masterSettingAppFacade.find(BENTUK_TIGA_ID_PATTERN).getValueString();
        Integer counter = ((Number) em.createNativeQuery("SELECT get_bentuk_tiga_next_seq.nextval from dual").getSingleResult()).intValue(); //SEQ_JKM
        return generateID(pattern, counter);
    }

    public String generateInvoiceNotaManualID(){
        handleExpiredSequence(INVOICE_NM_ID_SEQ, INVOICE_NM_ID_NEXT_RESET_PARAM, INVOICE_NM_ID_RESET_TIME_PARAM);
        return generateID(INVOICE_NM_ID_PATTERN_PARAM, INVOICE_NM_ID_SEQ);
    }
    
    public String generateFakturPajakID(){
        String id = usableFakturPajakFacadeLocal.getAvailableNoFakturPajak();

        if (id != null) {
            return id;
        } else {
            String pattern = masterSettingAppFacade.find(FAKTUR_PAJAK_ID_PATTERN_PARAM).getValueString();
            Integer counter = ((Number) em.createNativeQuery("SELECT get_simpat_next_seq('FAKTUR_PAJAK') from dual").getSingleResult()).intValue();
            return generateID(pattern, counter);
        }
    }

    public String generateJkmID(){
        String pattern = masterSettingAppFacade.find(JKM_ID_PATTERN_PARAM).getValueString();
        Integer counter = ((Number) em.createNativeQuery("SELECT get_jkm_next_seq() from dual").getSingleResult()).intValue(); //SEQ_JKM
        return generateID(pattern, counter);
    }

    public String generateJobSlipID(String prefix){
        handleExpiredSequence(JOB_SLIP_ID_SEQ, JOB_SLIP_ID_NEXT_RESET_PARAM, JOB_SLIP_ID_RESET_TIME_PARAM);
        return prefix + generateID(JOB_SLIP_ID_PATTERN_PARAM, JOB_SLIP_ID_SEQ);
    }

    public String generateID(String patternString, int value){
        Object[] arguments = { new Date(System.currentTimeMillis()) };
        StringBuilder pattern = new StringBuilder(MessageFormat.format(patternString, arguments));
        StringBuilder formatter = new StringBuilder(pattern.substring(pattern.indexOf("#")).replace("#", "0"));
        int formatterLength = formatter.toString().replaceAll("[^0]", "").length();
        formatter.append(String.valueOf(value));
        return (pattern.substring(0, pattern.indexOf("#")) + formatter.substring(formatter.length() - formatterLength, formatter.length()));
    }

    private String generateID(String patternParam, String sequence){
        String pattern = masterSettingAppFacade.find(patternParam).getValueString();
        int value = sequenceFacade.nextValue(sequence).intValue();
        return generateID(pattern, value);
    }

    private void handleExpiredSequence(String sequence, String nextResetParam, String resetTimeParam){
        MasterSettingApp resetTimeSetting = masterSettingAppFacade.find(resetTimeParam);
        MasterSettingApp nextResetSetting = masterSettingAppFacade.find(nextResetParam);
        Calendar nextReset = Calendar.getInstance();
        if (nextReset.getTime().compareTo(nextResetSetting.getValueDate()) >= 0) {
            if (resetTimeSetting.getValueString().equals(MONTH))
                nextReset.add(Calendar.MONTH, 1);
            else if (resetTimeSetting.getValueString().equals(YEAR)){
                nextReset.add(Calendar.YEAR, 1);
                nextReset.set(Calendar.MONTH, 0);
            } else {
                throw new RuntimeException("Value of " + resetTimeSetting.getId() + " on MasterSettingApp is not valid!");
            }
            nextReset.set(Calendar.DAY_OF_MONTH, 1);
            nextReset.set(Calendar.HOUR_OF_DAY, 0);
            nextReset.set(Calendar.MINUTE, 0);
            nextReset.set(Calendar.SECOND, 0);
            nextReset.set(Calendar.MILLISECOND, 0);
            nextResetSetting.setValueDate(nextReset.getTime());
            masterSettingAppFacade.edit(nextResetSetting);
            sequenceFacade.resetSequence(sequence);
        }
    }
}

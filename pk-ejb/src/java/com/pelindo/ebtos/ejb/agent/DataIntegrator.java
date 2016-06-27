/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.agent;

import com.pelindo.ebtos.ejb.local.DataIntegratorLocal;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.ejb.facade.local.InvoiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterCustomerFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterSettingAppFacadeLocal;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.ScheduleExpression;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author RSenoAnggoro
 */
@Singleton
public class DataIntegrator implements DataIntegratorRemote, DataIntegratorLocal {
    private static final String CUSTOMER_SYNC_AGENT_NAME = "customerSyncAgent";
    private static final String RETRY_CREATE_SYNC_SCHEDULE_CONFIG = "retryCreateFetchSchedule";
    private static final Integer NEXT_TRY_FETCH_MINUTES = 10;

    @Resource
    private TimerService timerService;
    @EJB
    private MasterSettingAppFacadeLocal masterSettingAppFacadeLocal;
    @EJB
    private MasterCustomerFacadeLocal masterCustomerFacadeLocal;
    @EJB
    private InvoiceFacadeLocal invoiceFacadeLocal;

    private Date lastProgrammaticTimeout;
    private String info;

    public Boolean isSimpatAvailable(){
        Boolean isSimpatReady = false;

        try {
            BigDecimal idr = invoiceFacadeLocal.getKurs("USD");
            isSimpatReady = true;
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "SIMPAT server not available", re);
        }

        return isSimpatReady;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createFetchSchedule(){
        try {
            lastProgrammaticTimeout = new Date();
            Object[] parameter = masterSettingAppFacadeLocal.findCustomerSyncParameter();
            final TimerConfig config = new TimerConfig(CUSTOMER_SYNC_AGENT_NAME, true);
            timerService.createCalendarTimer(new ScheduleExpression().dayOfMonth((String) parameter[0]).hour((String) parameter[1]).minute((String) parameter[2]), config);
            Logger.getLogger(getClass().getName()).log(Level.INFO, "[EBTOS] schedule[" + CUSTOMER_SYNC_AGENT_NAME + "] has been created ");
        } catch (Exception re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "[EBTOS] exception when create schedule[" + CUSTOMER_SYNC_AGENT_NAME + "] on createFetchSchedule()", re);
            retryCreateFetchSchedule();
        }
    }

    private void retryCreateFetchSchedule(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(lastProgrammaticTimeout);
        cal.add(Calendar.MINUTE, NEXT_TRY_FETCH_MINUTES);
        Date nextTry = cal.getTime();
        Logger.getLogger(getClass().getName()).log(Level.INFO, String.format("[EBTOS] retry create schedule[%s] on %s", CUSTOMER_SYNC_AGENT_NAME, cal.getTime().toString()));
        final TimerConfig config = new TimerConfig(RETRY_CREATE_SYNC_SCHEDULE_CONFIG, false);
        timerService.createSingleActionTimer(nextTry, config);
    }

    @Timeout
    private void timeout(Timer timer) {
        if (CUSTOMER_SYNC_AGENT_NAME.equals(timer.getInfo())) {
            syncMasterCustomer();
        } else if (RETRY_CREATE_SYNC_SCHEDULE_CONFIG.equals(timer.getInfo())) {
            timer.cancel();
            createFetchSchedule();
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void syncMasterCustomer(){
        Integer affectedRows = masterCustomerFacadeLocal.syncMasterCustomer();
        lastProgrammaticTimeout = new Date();
        Logger.getLogger(getClass().getName()).log(Level.INFO, String.format("[EBTOS] successfully fetch absensi data on %s: %s rows affected", lastProgrammaticTimeout.toString(), affectedRows));
    }

    private void cancelTimer(String timerName) {
        try {
            Iterator it = timerService.getTimers().iterator();
            while (it.hasNext()) {
                Timer myTimer = (Timer) it.next();
                if ((myTimer.getInfo().equals(timerName))) {
                    myTimer.cancel();
                    break;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "[EBTOS] Exception after cancelling timer", e);
        }
        return;
    }

    @Lock(LockType.READ)
    public boolean isAgentRunning(String timerName){
        Iterator it = timerService.getTimers().iterator();
        while (it.hasNext()) {
            Timer myTimer = (Timer) it.next();
            if ((myTimer.getInfo().equals(timerName))) {
                return true;
            }
        }
        return false;
    }

    @Lock(LockType.READ)
    public String gerInfo(){
        return "Data has updated on " + lastProgrammaticTimeout.toString();
    }

    @Lock(LockType.READ)
    public String getLastProgrammaticTimeout() {
        if (lastProgrammaticTimeout != null) {
            return lastProgrammaticTimeout.toString();
        } else {
            return "never";
        }
    }
}

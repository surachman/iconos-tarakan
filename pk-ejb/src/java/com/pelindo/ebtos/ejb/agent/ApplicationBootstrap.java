/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.agent;

import com.pelindo.ebtos.ejb.local.DataIntegratorLocal;
import com.qtasnim.security.util.ShiroSessionManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author R. Seno Anggoro A
 */
@Singleton
@Startup
public class ApplicationBootstrap {
    private static final Logger logger = Logger.getLogger(ApplicationBootstrap.class.getName());

    @EJB
    private DataIntegratorLocal dataIntegratorLocal;
    
    @PostConstruct
    private void construct() {
        logger.log(Level.INFO, "[EBTOS] ApplicationBootstrap started");
        
        try {
            dataIntegratorLocal.syncMasterCustomer();
        } catch (Exception re) {
            logger.log(Level.SEVERE, "[EBTOS] fail to fetch absensi data");
        }

        dataIntegratorLocal.createFetchSchedule();
    }
}

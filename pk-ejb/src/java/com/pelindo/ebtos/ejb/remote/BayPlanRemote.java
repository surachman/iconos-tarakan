/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.remote;

/**
 *
 * @author dyware-java
 */
import java.sql.Blob;

import javax.ejb.Remote;

@Remote
public interface BayPlanRemote {
    Blob getBayPlan(int row, int tier);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.remote;

import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author R. Seno Anggoro A
 */
@Remote
public interface KapalBayanganOperationRemote {

    public void handleChangePpkb(List<String> contNo, List<String> jobSlips, com.pelindo.ebtos.model.db.PlanningVessel newVessel, com.pelindo.ebtos.model.db.PlanningVessel oldVessel);

}

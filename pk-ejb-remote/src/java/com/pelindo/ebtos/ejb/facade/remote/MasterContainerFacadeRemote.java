/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import javax.ejb.Remote;


/**
 *
 * @author senoanggoro
 */
@Remote
public interface MasterContainerFacadeRemote {

    public Object findMovableContainer(String contNo);

    public void handleDeleteDischargeContainer(java.lang.String contNo, java.lang.String noPpkb) throws com.pelindo.ebtos.exception.DeleteNotDeletableContainerException;
    
}

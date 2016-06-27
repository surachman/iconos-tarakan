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
public interface EdifactFactoryFacadeRemote {

    public String[] getBaplieByNoPpkb(java.lang.String noPpkb);

    public java.lang.String[] getCodecoByNoPpkbAndContNo(java.lang.String noPpkb, java.lang.String contNo);
    
}

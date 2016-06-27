/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface SequenceFacadeLocal {

    public Long nextValue(String sequence);
    
    public Long resetSequence(String sequence);

    public java.lang.Long nextEdifactID();
}

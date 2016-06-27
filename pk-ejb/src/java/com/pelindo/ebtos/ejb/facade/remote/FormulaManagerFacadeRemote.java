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
public interface FormulaManagerFacadeRemote {
    public com.qtasnim.formula.FormulaManager setFormula(java.lang.String key);
}

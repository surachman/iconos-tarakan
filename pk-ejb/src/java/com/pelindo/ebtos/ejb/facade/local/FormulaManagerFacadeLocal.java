/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import javax.ejb.Local;

/**
 *
 * @author senoanggoro
 */
@Local
public interface FormulaManagerFacadeLocal {

    public com.qtasnim.formula.FormulaManager setFormula(java.lang.String key);
    
}

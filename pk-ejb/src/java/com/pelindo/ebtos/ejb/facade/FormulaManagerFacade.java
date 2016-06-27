/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.qtasnim.formula.FormulaManager;
import com.pelindo.ebtos.ejb.facade.local.FormulaManagerFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterSettingAppFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.FormulaManagerFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class FormulaManagerFacade implements FormulaManagerFacadeRemote, FormulaManagerFacadeLocal {
    @EJB private MasterSettingAppFacadeLocal masterSettingAppFacade;
    @Override
    public FormulaManager setFormula(String key){
        MasterSettingApp masterSettingApp = masterSettingAppFacade.find(key);
        if (masterSettingApp == null){
            return null;
        }

        String formula = masterSettingApp.getValueString();
        if (formula == null){
            return null;
        }

        return new FormulaManager(formula);
    }
}

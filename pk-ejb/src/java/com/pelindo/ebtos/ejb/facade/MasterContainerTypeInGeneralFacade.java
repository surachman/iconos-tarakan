/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.MasterContainerTypeInGeneralFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeInGeneralFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterContainerTypeInGeneral;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class MasterContainerTypeInGeneralFacade extends AbstractFacade<MasterContainerTypeInGeneral> implements MasterContainerTypeInGeneralFacadeRemote, MasterContainerTypeInGeneralFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterContainerTypeInGeneralFacade() {
        super(MasterContainerTypeInGeneral.class);
    }

    public Integer findGenericContType(short contSize, String contType) {
        if (contSize == 20) {
            if (contType.equals("BLK")) {
                return 20;
            } else if (contType.equals("DRY")) {
                return 1;
            } else if (contType.equals("FLT")) {
                return 14;
            } else if (contType.equals("HC")) {
                return 21;
            } else if (contType.equals("OPS")) {
                return 65;
            } else if (contType.equals("OPT")) {
                return 12;
            } else if (contType.equals("RFR")) {
                return 2;
            } else if (contType.equals("TNK")) {
                return 19;
            }
        } else if (contSize == 40) {
            if (contType.equals("BLK")) {
                return 36;
            } else if (contType.equals("DRY")) {
                return 26;
            } else if (contType.equals("FLT")) {
                return 32;
            } else if (contType.equals("HC")) {
                return 37;
            } else if (contType.equals("OPS")) {
                return 98;
            } else if (contType.equals("OPT")) {
                return 30;
            } else if (contType.equals("RFR")) {
                return 29;
            } else if (contType.equals("TNK")) {
                return 83;
            }
        } else if (contSize == 45) {
            if (contType.equals("DRY")) {
                return 1752;
            } else if (contType.equals("HC")) {
                return 99;
            }
        }

        return 3;
    }
}

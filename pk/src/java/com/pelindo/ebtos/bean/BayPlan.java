/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.remote.BayPlanRemote;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.model.DefaultStreamedContent;

/**
 *
 * @author dyware-java
 */
@Named(value="bayPlan")
@RequestScoped
public class BayPlan implements Serializable{
    BayPlanRemote bayPlanEjb = lookupBayPlanRemote();
    private int printCount;
    private int row;
    private int tier;
    private DefaultStreamedContent bayPlan;
    /** Creates a new instance of BayPlan */
    public BayPlan() {
        
    }

    /**
     * @return the bayPlan
     */
    public DefaultStreamedContent getBayPlan() throws SQLException {
        if (row == 0 || tier ==0){
            row = 3;
            tier = 3;
        }
        bayPlan = new DefaultStreamedContent(bayPlanEjb.getBayPlan(this.getRow(), this.getTier()).getBinaryStream(), "image/png");
        return bayPlan;
    }

    /**
     * @return the slot
     */
    public int getRow() {
        return row;
    }

    /**
     * @param slot the slot to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the tier
     */
    public int getTier() {
        return tier;
    }

    /**
     * @param tier the tier to set
     */
    public void setTier(int tier) {
        this.tier = tier;
    }

    public void printHandler(ActionEvent event){
        printCount++;
    }
    /**
     * @return the printCount
     */
    public int getPrintCount() {
        return printCount;
    }

    /**
     * @param printCount the printCount to set
     */
    public void setPrintCount(int printCount) {
        this.printCount = printCount;
    }

    private BayPlanRemote lookupBayPlanRemote() {
        try {
            Context c = new InitialContext();
            return (BayPlanRemote) c.lookup("java:global/pkproject/pk-ejb/BayPlan!com.pelindo.ebtos.ejb.remote.BayPlanRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}

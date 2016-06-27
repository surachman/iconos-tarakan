/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author senoanggoro
 */
@ManagedBean(name="containerOverallView")
@ViewScoped
public class ContainerOverallView implements Serializable{
    MasterYardFacadeRemote masterYardFacade = lookupMasterYardFacadeRemote();
    private List<String> blocks;
    private int contWidth, contHeight;

    /** Creates a new instance of ContainerSideView */
    public ContainerOverallView() {
            contWidth = 30;
            contHeight = 15;
    }

    public String getBlockA() {
        return getBaseViewUrl("A");
    }

    public String getBlockB() {
        return getBaseViewUrl("B");
    }

    public String getBlockC() {
        return getBaseViewUrl("C");
    }

    public String getBlockD() {
        return getBaseViewUrl("D");
    }

    public String getBlockE() {
        return getBaseViewUrl("E");
    }

    public String getBlockF() {
        return getBaseViewUrl("F");
    }

    public String getBlockG() {
        return getBaseViewUrl("G");
    }

    public String getBlockH() {
        return getBaseViewUrl("H");
    }

    public String getBlockI() {
        return getBaseViewUrl("I");
    }

    public String getBlockJ() {
        return getBaseViewUrl("J");
    }

    public String getBlockK() {
        return getBaseViewUrl("K");
    }

    public String getBlockL() {
        return getBaseViewUrl("L");
    }

    public String getBlockM() {
        return getBaseViewUrl("M");
    }

    public String getBlockN() {
        return getBaseViewUrl("N");
    }

    public String getBlockO() {
        return getBaseViewUrl("O");
    }

    public String getBlockP() {
        return getBaseViewUrl("P");
    }
    
    public String getBaseViewUrl(String block){
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/../cyOverallView.png?"
                    + "&contWidth=" + contWidth
                    + "&contHeight=" + contHeight
                    + "&block="+block;
    }

    public String getBaseViewUrl(){
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/../cyOverallView.png?"
                    + "&contWidth=" + contWidth
                    + "&contHeight=" + contHeight
                    + "&block=";
    }

    public List<String> getBlocks(){
        return blocks;
    }

    private MasterYardFacadeRemote lookupMasterYardFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterYardFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterYardFacade!com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}

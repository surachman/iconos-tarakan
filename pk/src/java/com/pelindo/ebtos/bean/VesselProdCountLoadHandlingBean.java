/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.RecapHandlingLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name="vesselProdCountLoadHandlingBean")
@ViewScoped
public class VesselProdCountLoadHandlingBean implements Serializable{

    protected ServiceVesselFacadeRemote serviceVesselFacadeRemote = lookupServiceVesselFacadeRemote();
    protected RecapHandlingLoadFacadeRemote recapHandlingLoadFacadeRemote = lookupRecapHandlingLoadFacadeRemote();

    private Object[] serviceVessel;
    private List<Object[]> serviceVessels = lookupServiceVesselFacadeRemote().findServiceVesselsServiced();
    protected String noPPKB;    
    private Object[] handlings;
    protected String mode;

    /** Creates a new instance of VesselProdCountLoadHandlingBean */
    public VesselProdCountLoadHandlingBean() {
        serviceVessels = serviceVesselFacadeRemote.findServiceVesselsServiced();
    }

    private ServiceVesselFacadeRemote lookupServiceVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceVesselFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RecapHandlingLoadFacadeRemote lookupRecapHandlingLoadFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (RecapHandlingLoadFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/RecapHandlingLoadFacade!com.pelindo.ebtos.ejb.facade.remote.RecapHandlingLoadFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * @return the serviceVessel
     */
    public Object[] getServiceVessel() {
        return serviceVessel;
    }    

    /**
     * @return the serviceVessels
     */
    public List<Object[]> getServiceVessels() {
        return serviceVessels;
    }
    
    public void handleSelectNoPPKB(ActionEvent event){
        noPPKB = (String) event.getComponent().getAttributes().get("noPPKB");
        serviceVessel = serviceVesselFacadeRemote.findServiceVesselByPpkb(noPPKB);
        handlings = recapHandlingLoadFacadeRemote.countHandlingLoad(noPPKB);
    }

    public void handleDownload(ActionEvent event){
        boolean print = false;
        if (handlings != null){
            print = true;
        }
        mode = "countload";
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", print); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../realisasi.pdf?"
                + "mode=" + mode + "&"
                + "no_ppkb=" + noPPKB));
    }

    public String getUrlCountLoad(){
        if (handlings == null)
            return "#";

        mode = "countload";
	return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/realisasi.pdf?"
                + "mode=" + mode + "&"
                + "no_ppkb=" + noPPKB + "";
    }

    public String getUrlPrintCountLoad(){
        if (handlings == null)
            return "#";

        mode = "countload";
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	return  context.getRequestScheme() + "://" + context.getRequestServerName() + ":" + context.getRequestServerPort() + context.getRequestContextPath() + "/realisasi.pdf?"
                + "mode=" + mode + "&"
                + "no_ppkb=" + noPPKB + "";
    }

    /**
     * @return the handlings
     */
    public Object[] getHandlings() {
        return handlings;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
//import com.pelindo.ebtos.ejb.facade.remote.PlanningReceivingAllocationFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterCy;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name="masterYardBean")
@ViewScoped
public class MasterYardBean implements Serializable{

    @EJB private MasterYardFacadeRemote masterYardFacadeRemote;
    //@EJB private PlanningReceivingAllocationFacadeRemote planningReceivingAllocationFacadeRemote;
    protected MasterYardCoordinatFacadeRemote masterYardCoordinatFacadeRemote = lookupMasterYardCoordinatFacadeRemote();

    private MasterYard masterYard;
    private String idYard;
    private boolean delete;
    private boolean disable;
    private List<Object[]> masterYards;
    private MasterCy masterCy;
    protected List<MasterYardCoordinat> masterYardCoordinats;
    protected MasterYardCoordinat masterYardCoordinat;
    protected boolean actionAdd;
    protected List<Object[]> ListIdStatus;
    protected boolean empty;
    protected boolean breakLoop;
    protected boolean breakEdit;
    protected List<Integer> idRemove;
    //FacesContext context = FacesContext.getCurrentInstance();

    /** Creates a new instance of MasterYardBean */
    public MasterYardBean() {
        masterYard = new MasterYard();
        masterYard.setMasterCy(masterCy);
        disable = false;
        empty = false;
        breakLoop = false;
        masterYards = lookupMasterYardFacadeRemote().findMasterYards();
        masterYardCoordinat = new MasterYardCoordinat();
        masterYardCoordinats = new ArrayList<MasterYardCoordinat>();
    }

    public MasterCy getMasterCy() {
        return masterCy;
    }

    public void setMasterCy(MasterCy masterCy) {
        this.masterCy = masterCy;
    }

    /**
     * @return the masterYard
     */
    public MasterYard getMasterYard() {
        return masterYard;
    }

    /**
     * @param masterYard the masterYard to set
     */
    public void setMasterYard(MasterYard masterYard) {
        this.masterYard = masterYard;
    }

    public List<Object[]> getMasterYards(){
        return masterYards;

    }    

    public void handleAddButton(ActionEvent event){
        masterYard = new MasterYard();
        masterYard.setMasterCy(new MasterCy());
        disable = false;
        actionAdd = true;
    }

    public void handleEditButton(ActionEvent event){
        idYard = (String) event.getComponent().getAttributes().get("idYard");
        masterYard = masterYardFacadeRemote.find(idYard);
        disable = true;
        actionAdd = false;
        breakEdit = false;
    }

    public void saveEdit(ActionEvent event) {
        boolean loggedIn = false;
        FacesContext context = FacesContext.getCurrentInstance();
        if (actionAdd) {
            buildCoordinat(masterYard.getBlock(), masterYard.getSlot(), masterYard.getRow(), masterYard.getTier());
        } else {            
            editCoordinat(masterYard.getBlock(), masterYard.getSlot(), masterYard.getRow(), masterYard.getTier());
        }
        if (breakEdit) {
            context.addMessage(null, new FacesMessage("Attention", "range of slot / row / tier is not empty"));
        } else {
            if(!actionAdd){
                //remove id yang dirubah
                for(int a=0; a<idRemove.size(); a++){
                masterYardCoordinatFacadeRemote.deleteById(idRemove.get(a));
                }
                // add koordinat baru
                for(int b=0; b<masterYardCoordinats.size(); b++){
                    masterYardCoordinats.get(b).setId(null);
                    masterYardCoordinatFacadeRemote.create(masterYardCoordinats.get(b));
                }
            }
            loggedIn = true;
            masterYardFacadeRemote.edit(masterYard);
            context.addMessage(null, new FacesMessage("Successful", "save file id= " + masterYard.getBlock()));
            context.addMessage(null, new FacesMessage("Attention", "save is completed"));
            masterYards = lookupMasterYardFacadeRemote().findMasterYards();
        }
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }   

    public void handleDeleteButton(ActionEvent event){
        idYard = (String) event.getComponent().getAttributes().get("idYard");
        this.delete = true;
    }

    public void handleDeleteAll(ActionEvent event){
        this.delete = false;
    }

    public void delete(ActionEvent event)throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        if (this.delete) {
            masterYard = masterYardFacadeRemote.find(idYard);
            checkEmpty(masterYard.getBlock(), masterYard.getSlot(), masterYard.getRow(), masterYard.getTier());
            try {
                if (empty) {
                    masterYardCoordinatFacadeRemote.deleteByBlock(masterYard.getBlock());
                    masterYardFacadeRemote.remove(masterYard);
                    context.addMessage(null, new FacesMessage("Succesful","delete file with block= " + masterYard.getBlock()));
                } else {
                    context.addMessage(null, new FacesMessage("Aborted","delete file with block= " + masterYard.getBlock()));
                    context.addMessage(null, new FacesMessage("Attention","data is cannot deleted because have relationship with master yard coordinat"));
                }
            } catch (Exception ex) {
                context.addMessage(null, new FacesMessage("Aborted","delete file with block= " + masterYard.getBlock()));
                context.addMessage(null, new FacesMessage("Attention","data is cannot deleted because have relationship with primary tabel"));
            }
        } else {
            for (MasterYard contain : masterYardFacadeRemote.findAll()) {
                checkEmpty(contain.getBlock(), contain.getSlot(), contain.getRow(), contain.getTier());
                try {
                    if (empty) {
                        masterYardCoordinatFacadeRemote.deleteByBlock(contain.getBlock());
                        masterYardFacadeRemote.remove(contain);
                        context.addMessage(null, new FacesMessage("Succesful","delete file with block= " + contain.getBlock()));
                    }else{
                        context.addMessage(null, new FacesMessage("Aborted","delete file with block= " + contain.getBlock()));
                        context.addMessage(null, new FacesMessage("Attention","data is cannot deleted because have relationship with master yard coordinat"));
                    }
                } catch (Exception ex) {
                    context.addMessage(null, new FacesMessage("Aborted","delete file id= " + contain.getBlock()));
                    context.addMessage(null, new FacesMessage("Attention","data is cannot deleted because have relationship with primary tabel"));
                }
            }
        }
        masterYards = lookupMasterYardFacadeRemote().findMasterYards();
    }



    /**
     * @return the idYard
     */
    public String getIdYard() {
        return idYard;
    }

    /**
     * @param idYard the idYard to set
     */
    public void setIdYard(String idYard) {
        this.idYard = idYard;
    }

    /**
     * @return the delete
     */
    public boolean isDelete() {
        return delete;
    }

    /**
     * @param delete the delete to set
     */
    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    /**
     * @return the disable
     */
    public boolean isDisable() {
        return disable;
    }

    /**
     * @param disable the disable to set
     */
    public void setDisable(boolean disable) {
        this.disable = disable;
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

    private MasterYardCoordinatFacadeRemote lookupMasterYardCoordinatFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterYardCoordinatFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterYardCoordinatFacade!com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    protected void buildCoordinat(String block, int slot, int row, int tier){
        for(short i=1; i<=slot; i++){
            for(short j=1; j<=row; j++){
                for(short k=1; k<=tier; k++){
                    masterYardCoordinat.setBlock(block);
                    masterYardCoordinat.setSlot(i);
                    masterYardCoordinat.setRow(j);
                    masterYardCoordinat.setTier(k);
                    masterYardCoordinat.setStatus("empty");
                    masterYardCoordinatFacadeRemote.create(masterYardCoordinat);
                    masterYardCoordinat = new MasterYardCoordinat();
                }
            }
        }
    }   

    protected void checkEmpty(String block, int slot, int row, int tier) {
        empty = true;
        for (short i = 1; i <= slot; i++) {
            for (short j = 1; j <= row; j++) {
                for (short k = 1; k <= tier; k++) {
                    Object[] idStatus = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(block, i, j, k);
                    String status = (String) idStatus[1];
                    if (!status.equalsIgnoreCase("empty")) {
                        empty = false;
                        breakLoop = true;
                    }
                    if (breakLoop) {
                        break;
                    }
                }
                if (breakLoop) {
                    break;
                }
            }
            if (breakLoop) {
                break;
            }
        }
    }

    protected void editCoordinat(String block, int slot, int row, int tier) {
        boolean ada;
        breakLoop = false;
        int id = 1;
        empty = true;
        breakEdit = false;
        idRemove = new ArrayList<Integer>();
        List<Integer> idExist = new ArrayList<Integer>();
        List<Object[]> exists = new ArrayList<Object[]>();
        masterYardCoordinats = new ArrayList<MasterYardCoordinat>();
        masterYardCoordinat = new MasterYardCoordinat();
        exists = masterYardCoordinatFacadeRemote.findAllStatusByBlock(block);
        for (short i = 1; i <= slot; i++) {
            for (short j = 1; j <= row; j++) {
                for (short k = 1; k <= tier; k++) {
                    Object[] idStatus = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(block, i, j, k);
                    if (idStatus == null) {
                        masterYardCoordinat.setId(id);
                        masterYardCoordinat.setBlock(block);
                        masterYardCoordinat.setSlot(i);
                        masterYardCoordinat.setRow(j);
                        masterYardCoordinat.setTier(k);
                        masterYardCoordinat.setStatus("empty");
                        masterYardCoordinats.add(masterYardCoordinat);
                        masterYardCoordinat = new MasterYardCoordinat();
                    } else {
                        idExist.add((Integer) idStatus[0]);
                    }
                }
            }
        }

        for (int x = 0; x < exists.size(); x++) {
            ada = false;
            int idLama = (Integer) exists.get(x)[0];
            String statusLama = (String) exists.get(x)[1];
            for (int y = 0; y < idExist.size(); y++) {
                int idBaru = idExist.get(y);
                if (idLama == idBaru) {
                    ada = true;
                }
            }
            if (!ada && statusLama.equalsIgnoreCase("empty")) {
                idRemove.add(idLama);
            } else if (!ada && !statusLama.equalsIgnoreCase("empty")) {
                breakEdit = true;
                break;
            }
        }
    }
}

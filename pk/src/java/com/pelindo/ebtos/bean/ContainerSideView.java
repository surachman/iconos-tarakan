/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author senoanggoro
 */
@ManagedBean(name="containerSideView")
@ViewScoped
public class ContainerSideView  implements Serializable{
    private static final int DEFAULT_ROWS_REFERENCE = 6;
    private static final int DEFAULT_TIERS_REFERENCE = 3;
    private static final int DEFAULT_WIDTH = 1000;
    private static final int DEFAULT_HEIGHT = 400;

    private MasterYardFacadeRemote masterYardFacade = lookupMasterYardFacadeRemote();
    private String selectedBlock;
    private String existOnly;
    private Integer selectedSlot;
    private List<Object[]> blocks = masterYardFacade.findMasterYards();
    private List<SelectItem> slots;
    private String ppkb;
    
    private Integer width = DEFAULT_WIDTH;
    private Integer height = DEFAULT_HEIGHT;

    /** Creates a new instance of ContainerSideView */
    public ContainerSideView() {
        initComponents();
        initValues();
    }

    private void initComponents(){
        slots = new ArrayList();
        for (int i = 1;i <= ((Number) blocks.get(0)[2]).intValue();i++){
            slots.add(new SelectItem(new Integer(i), String.valueOf(i)));
        }
    }

    private void initValues(){
        String blockName = FacesHelper.getParam("block", FacesContext.getCurrentInstance());
        
        if (blockName == null) {
            selectedBlock = blocks.get(0)[0].toString();
            height = (int) (((Number) blocks.get(0)[4]).doubleValue() / DEFAULT_TIERS_REFERENCE * DEFAULT_HEIGHT);
            width = (int) (((Number) blocks.get(0)[3]).doubleValue() / DEFAULT_ROWS_REFERENCE * DEFAULT_WIDTH);
        }else{
            selectedBlock = blockName;
            Object[] block = masterYardFacade.findMasterYardByBlock(blockName);
            height = (int) (((Number) block[2]).doubleValue() / DEFAULT_TIERS_REFERENCE * DEFAULT_HEIGHT);
            width = (int) (((Number) block[1]).doubleValue() / DEFAULT_ROWS_REFERENCE * DEFAULT_WIDTH);
        }
        selectedSlot = new Integer(1);
        existOnly = "f";
        ppkb = null;
    }

    /**
     * @return the selectedBlock
     */
    public String getSelectedBlock() {
        return selectedBlock;
    }

    /**
     * @param selectedBlock the selectedBlock to set
     */
    public void setSelectedBlock(String selectedBlock) {
        this.selectedBlock = selectedBlock;
    }

    /**
     * @return the selectedSlot
     */
    public Integer getSelectedSlot() {
        return selectedSlot;
    }

    /**
     * @param selectedSlot the selectedSlot to set
     */
    public void setSelectedSlot(Integer selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    public String getExistOnly() {
        return existOnly;
    }

    public void setExistOnly(String existOnly) {
        this.existOnly = existOnly;
    }

    public List<Object[]> getBlocks(){
        return blocks;
    }

    public List<SelectItem> getSlots(){
        return slots;
    }
    
    public String getPpkb(){
        return ppkb;
    }

    public void setPpkb(String ppkb){
        this.ppkb = ppkb;
    }

    public String getSideViewUrl(){
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/../cySideView.png?"
                    + "block=" + selectedBlock + "&"
                    + "slot=" + selectedSlot + "&"
                    + "w="+ width +"&"
                    + "h="+ height +"&"
                    + "eo=" + existOnly
                    + (ppkb == null || ppkb.equals("") ? "" : "&ppkb=" + ppkb);
    }

    public void handleChangeBlock(ValueChangeEvent event){
        Object[] block = masterYardFacade.findMasterYardByBlock(event.getNewValue().toString());
        slots = new ArrayList();
        for (int i = 1;i <= ((Number) block[0]).intValue();i++){
            slots.add(new SelectItem(new Integer(i), String.valueOf(i)));
        }
        selectedSlot = new Integer(1);
        height = (int)(((Number) block[2]).doubleValue() / DEFAULT_TIERS_REFERENCE * DEFAULT_HEIGHT);
        width = (int)(((Number) block[1]).doubleValue() / DEFAULT_ROWS_REFERENCE * DEFAULT_WIDTH);
    }
    
    public void handleReset(ActionEvent event){
        initComponents();
        initValues();
    }

    public void handleChangeSlot(ValueChangeEvent event){
        selectedSlot = (Integer) event.getNewValue();
    }

    public void handleChangeExistOnly(ValueChangeEvent event){
        existOnly = (String) event.getNewValue();
    }

    public void handleClickNextButton(ActionEvent event){
        if (!(selectedSlot == Integer.parseInt(slots.get(slots.size() - 1).getValue().toString())))
            selectedSlot = new Integer(selectedSlot + 1);
    }

    public void handleClickPrevButton(ActionEvent event){
        if (!(selectedSlot == Integer.parseInt(slots.get(0).getValue().toString())))
            selectedSlot = new Integer(selectedSlot - 1);
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

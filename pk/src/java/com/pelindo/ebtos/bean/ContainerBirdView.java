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
@ManagedBean(name="containerBirdView")
@ViewScoped
public class ContainerBirdView implements Serializable{
    private static final int DEFAULT_SLOTS_REFERENCE = 8;
    private static final int DEFAULT_ROWS_REFERENCE = 4;
    private static final int DEFAULT_WIDTH = 1200;
    private static final int DEFAULT_HEIGHT = 400;

    private MasterYardFacadeRemote masterYardFacade = lookupMasterYardFacadeRemote();
    private String selectedBlock;
    private String existOnly;
    private Integer selectedTier;
    private List<Object[]> blocks = masterYardFacade.findMasterYards();
    private List<SelectItem> tiers;
    private String ppkb;
    private Integer width = DEFAULT_WIDTH;
    private Integer height = DEFAULT_HEIGHT;

    /** Creates a new instance of ContainerSideView */
    public ContainerBirdView() {
        initComponents();
        initValues();
    }

    private void initComponents(){
        tiers = new ArrayList();
        for (int i = 1;i <= ((Number) blocks.get(0)[4]).intValue();i++){
            tiers.add(new SelectItem(new Integer(i), String.valueOf(i)));
        }
    }

    private void initValues(){
        String blockName = FacesHelper.getParam("block", FacesContext.getCurrentInstance());
        if (blockName == null){
            selectedBlock = blocks.get(0)[0].toString();
            height = (int)(((Number) blocks.get(0)[3]).doubleValue() / DEFAULT_ROWS_REFERENCE * DEFAULT_HEIGHT);
            width = (int)(((Number) blocks.get(0)[2]).doubleValue() / DEFAULT_SLOTS_REFERENCE * DEFAULT_WIDTH);
        } else {
            selectedBlock = blockName;
            Object[] block = masterYardFacade.findMasterYardByBlock(blockName);
            height = (int)(((Number) block[1]).doubleValue() / DEFAULT_ROWS_REFERENCE * DEFAULT_HEIGHT);
            width = (int)(((Number) block[0]).doubleValue() / DEFAULT_SLOTS_REFERENCE * DEFAULT_WIDTH);
        }
        selectedTier = new Integer(1);
        existOnly = "f";
        ppkb = null;
    }
    
    public String getSelectedBlock() {
        return selectedBlock;
    }

    public void setSelectedBlock(String selectedBlock) {
        this.selectedBlock = selectedBlock;
    }

    public Integer getSelectedTier() {
        return selectedTier;
    }

    public void setSelectedTier(Integer selectedTier) {
        this.selectedTier = selectedTier;
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

    public List<SelectItem> getTiers(){
        return tiers;
    }

    public String getPpkb(){
        return ppkb;
    }

    public void setPpkb(String ppkb){
        this.ppkb = ppkb;
    }

    public void handleChangeBlock(ValueChangeEvent event){
        Object[] block = masterYardFacade.findMasterYardByBlock(event.getNewValue().toString());
        tiers = new ArrayList();
        for (int i = 1;i <= (Integer) block[2];i++){
            tiers.add(new SelectItem(new Integer(i), String.valueOf(i)));
        }
        selectedTier = new Integer(1);
        height = (int)(((Integer) block[1]).doubleValue() / DEFAULT_ROWS_REFERENCE * DEFAULT_HEIGHT);
        width = (int)(((Integer) block[0]).doubleValue() / DEFAULT_SLOTS_REFERENCE * DEFAULT_WIDTH);
    }

    public void handleReset(ActionEvent event){
        initComponents();
        initValues();
    }

    public String getBirdViewUrl(){
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/../cyBirdView.png?"
                    + "block=" + selectedBlock + "&"
                    + "tier=" + selectedTier + "&"
                    + "w="+ width +"&"
                    + "h="+ height +"&"
                    + "eo=" + existOnly
                    + (ppkb == null || ppkb.equals("") ? "" : "&ppkb=" + ppkb);
    }

    public void handleChangeTier(ValueChangeEvent event){
        selectedTier = (Integer) event.getNewValue();
    }

    public void handleClickNextButton(ActionEvent event){
        if (!(selectedTier == Integer.parseInt(tiers.get(tiers.size() - 1).getValue().toString())))
            selectedTier = new Integer(selectedTier + 1);
    }

    public void handleClickPrevButton(ActionEvent event){
        if (!(selectedTier == Integer.parseInt(tiers.get(0).getValue().toString())))
            selectedTier = new Integer(selectedTier - 1);
    }
    
    public void handleChangeExistOnly(ValueChangeEvent event){
        existOnly = (String) event.getNewValue();
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

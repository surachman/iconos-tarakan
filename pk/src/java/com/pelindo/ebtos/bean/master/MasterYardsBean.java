/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean.master;

import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterYard;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ValueChangeEvent;


/**
 *
 * @author R. Seno Anggoro A
 */
@ManagedBean(name="masterYardsBean")
@RequestScoped
public class MasterYardsBean {
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    
    private List<MasterYard> masterYards;
    private Map<Integer, Integer> slots;
    private Map<Integer, Integer> rows;
    private Map<Integer, Integer> tiers;
    private Short counterSlot = 0;
    private Short counterRow = 0;
    private Short counterTier = 0;

    /** Creates a new instance of MasterYardBean */
    public MasterYardsBean() {
    }

    @PostConstruct
    private void construct() {
        slots = new HashMap<Integer, Integer>();
        rows = new HashMap<Integer, Integer>();
        tiers = new HashMap<Integer, Integer>();
        masterYards = masterYardFacadeRemote.findAll();
        MasterYard masterYard = masterYardFacadeRemote.find("A");
        onBlockChanged(masterYard);
    }

    public List<MasterYard> getMasterYards() {
        return masterYards;
    }

    public void onBlockChanged(ValueChangeEvent event) {
        MasterYard masterYard = (MasterYard) event.getNewValue();
        onBlockChanged(masterYard);
    }

    private void onBlockChanged(MasterYard masterYard) {
        Short slot = masterYard.getSlot();
        Short row = masterYard.getRow();
        Short tier = masterYard.getTier();
        slots.clear();
        rows.clear();
        tiers.clear();
        counterSlot = slot;
        counterRow = row;
        counterTier = tier;
    }

    /**
     * @return the slots
     */
    public Map<Integer, Integer> getSlots() {
        for (int i = 1; i <= counterSlot; i++) {
            slots.put(i, i);
        }
        return slots;
    }

    /**
     * @return the rows
     */
    public Map<Integer, Integer> getRows() {
        for (int i = 1; i <= counterRow; i++) {
            rows.put(i, i);
        }
        return rows;
    }

    /**
     * @return the tiers
     */
    public Map<Integer, Integer> getTiers() {
        for (int i = 1; i <= counterTier; i++) {
            tiers.put(i, i);
        }
        return tiers;
    }
}

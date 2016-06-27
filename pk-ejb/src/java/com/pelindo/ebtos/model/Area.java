/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model;

import java.io.Serializable;
/**
 *
 * @author dyware-java
 */
public class Area implements Serializable {
    private int row;
    private int slot;
    private int tier;

    public Area(int row, int slot, int tier) {
        this.row = row;
        this.slot = slot;
        this.tier = tier;
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the slot
     */
    public int getSlot() {
        return slot;
    }

    /**
     * @param slot the slot to set
     */
    public void setSlot(int slot) {
        this.slot = slot;
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
    
}

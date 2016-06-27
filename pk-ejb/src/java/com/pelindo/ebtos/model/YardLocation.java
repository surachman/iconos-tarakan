/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model;

import java.io.Serializable;

/**
 *
 * @author R. Seno Anggoro A
 */
public class YardLocation implements Serializable{
    private String block;
    private short slot;
    private short row;
    private short tier;

    public YardLocation(String block, short slot, short row, short tier) {
        this.block = block;
        this.slot = slot;
        this.row = row;
        this.tier = tier;
    }

    public String getBlock() {
        return block;
    }

    public short getRow() {
        return row;
    }

    public short getSlot() {
        return slot;
    }

    public short getTier() {
        return tier;
    }
}

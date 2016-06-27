/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model;

import com.pelindo.ebtos.model.definition.Stripping.Equipment;
import com.pelindo.ebtos.model.definition.Stripping.FullHandling;
import com.pelindo.ebtos.model.definition.Stripping.Mekanik;
import java.io.Serializable;

/**
 *
 * @author R. Seno Anggoro A
 */
public class PaketStrippingStuffing implements Serializable{
    private boolean isUseEquipment = false;
    private Equipment equipment;
    private FullHandling fullHandling;
    private Mekanik mekanik;
    private double fullHandlingTonage;
    private double mekanikTonage;
    private String liftActivityCode;
    private String strippingActivityCode;
    private String upahBuruhActivityCode;
    private String sewaAlatActivityCode;
    private String passGateActivityCode;

    public PaketStrippingStuffing() {
        equipment = Equipment.ForkLift3Ton;
        fullHandling = FullHandling.GeneralCargo;
        mekanik = Mekanik.PalletsCargo;
        fullHandlingTonage = 0;
        mekanikTonage = 0;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public FullHandling getFullHandling() {
        return fullHandling;
    }

    public void setFullHandling(FullHandling fullHandling) {
        this.fullHandling = fullHandling;
    }

    public boolean isIsUseEquipment() {
        return isUseEquipment;
    }

    public void setIsUseEquipment(boolean isUseEquipment) {
        this.isUseEquipment = isUseEquipment;
    }

    public Mekanik getMekanik() {
        return mekanik;
    }

    public void setMekanik(Mekanik mekanik) {
        this.mekanik = mekanik;
    }

    public double getFullHandlingTonage() {
        return fullHandlingTonage;
    }

    public void setFullHandlingTonage(double fullHandlingTonage) {
        this.fullHandlingTonage = fullHandlingTonage;
    }

    public double getMekanikTonage() {
        return mekanikTonage;
    }

    public void setMekanikTonage(double mekanikTonage) {
        this.mekanikTonage = mekanikTonage;
    }

    public String getStrippingActivityCode() {
        return strippingActivityCode;
    }

    public void setStrippingActivityCode(String strippingActivityCode) {
        this.strippingActivityCode = strippingActivityCode;
    }

    public String getSewaAlatActivityCode() {
        return sewaAlatActivityCode;
    }

    public void setSewaAlatActivityCode(String sewaAlatActivityCode) {
        this.sewaAlatActivityCode = sewaAlatActivityCode;
    }

    public String getUpahBuruhActivityCode() {
        return upahBuruhActivityCode;
    }

    public void setUpahBuruhActivityCode(String upahBuruhActivityCode) {
        this.upahBuruhActivityCode = upahBuruhActivityCode;
    }

    public String getPassGateActivityCode() {
        return passGateActivityCode;
    }

    public void setPassGateActivityCode(String passGateActivityCode) {
        this.passGateActivityCode = passGateActivityCode;
    }

    public String getLiftActivityCode() {
        return liftActivityCode;
    }

    public void setLiftActivityCode(String liftActivityCode) {
        this.liftActivityCode = liftActivityCode;
    }
}

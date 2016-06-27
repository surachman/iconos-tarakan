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
public class PaketBehandle implements Serializable{
    private boolean isUseEquipment;
    private boolean includeStrippingStuffing;
    private Equipment equipment;
    private FullHandling fullHandling;
    private Mekanik mekanik;
    private double fullHandlingTonage;
    private double mekanikTonage;
    private String behandleActivityCode;
    private String supervisiActivityCode;
    private String upahBuruhActivityCode;
    private String sewaAlatActivityCode;
    private String passGateActivityCode;

    public PaketBehandle() {
        equipment = Equipment.ForkLift3Ton;
        fullHandling = FullHandling.GeneralCargo;
        mekanik = Mekanik.PalletsCargo;
        fullHandlingTonage = 0;
        mekanikTonage = 0;
        isUseEquipment = false;
        includeStrippingStuffing = false;
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

    public String getBehandleActivityCode() {
        return behandleActivityCode;
    }

    public void setBehandleActivityCode(String behandleActivityCode) {
        this.behandleActivityCode = behandleActivityCode;
    }

    public String getSupervisiActivityCode() {
        return supervisiActivityCode;
    }

    public void setSupervisiActivityCode(String supervisiActivityCode) {
        this.supervisiActivityCode = supervisiActivityCode;
    }

    public boolean isIncludeStrippingStuffing() {
        return includeStrippingStuffing;
    }

    public void setIncludeStrippingStuffing(boolean includeStrippingStuffing) {
        this.includeStrippingStuffing = includeStrippingStuffing;
    }
}

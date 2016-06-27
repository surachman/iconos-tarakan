/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.definition;

/**
 *
 * @author R. Seno Anggoro A
 */
public class Stripping {
    public enum Equipment {
        ForkLift3Ton, ForkLift7Ton
    }

    public enum FullHandling {
        GeneralCargo, CurahKering, CurahCair, PalletsCargo, DT_KSG_S_CONT_SKID_T
    }
    
    public enum Mekanik {
        PalletsCargo, NonPalletsCargo
    }
}

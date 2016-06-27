/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.util;

/**
 *
 * @author Aan Kurniawan
 */
public class ServiceUtil {
    private ServiceUtil() {}
    
    public static String getCraneCode(String equipmentCode)
    {
        String craneCode;
        if ("SHCC".equals(equipmentCode))
                craneCode = "SB";
            else if (equipmentCode.startsWith("SH"))
                craneCode = "CS";
            else
                craneCode = "CC";
        return craneCode;
    }
}

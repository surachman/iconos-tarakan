
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.pelindo.ebtos.util;

//~--- JDK imports ------------------------------------------------------------

import java.util.Hashtable;

/**
 * Class description
 *
 *
 * @version        $version$, 10/11/23
 * @author         R Seno Anggoro 
 * @company	   Dycode   
 */
public class ContainerCode {
    private static String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static Hashtable<Character, Integer> table = new Hashtable();

    private static void init() {
        int jumper = 0;

        for (int i = 0; i < alpha.length(); i++) {
            if (((i + 10 + jumper) % 11) == 0) {
                jumper++;
            }

            table.put(alpha.charAt(i), i + 10 + jumper);
        }
    }

    public static String generateFullContName(String huruf, String angka, String divider) {
        if (divider == null)
            divider = "";
        return huruf + divider + angka + divider + generateContNameSuffix(huruf, angka);
    }

    public static String generateContNameSuffix(String huruf, String angka) {
        init();
        double sum = 0;

        huruf = huruf.toUpperCase();

        for (int i = 0; i < huruf.length() + angka.length(); i++) {
            if (i < huruf.length()) {
                sum += table.get(huruf.charAt(i)) * Math.pow(2, i);
            } else {
                sum += Integer.parseInt(String.valueOf(angka.charAt(i - huruf.length()))) * Math.pow(2, i);
            }
        }
        double __sum = (sum / 11);
        double floor = Math.floor(__sum);
        int lastNumber = (int) (sum - (floor * 11));
        return String.valueOf(lastNumber);
    }
}



/*
 * @(#)ContainerCode.java   10/11/23
 * 
 * Copyright (c) 2010 Dycode
 */


//~ Formatted by Jindent --- http://www.jindent.com

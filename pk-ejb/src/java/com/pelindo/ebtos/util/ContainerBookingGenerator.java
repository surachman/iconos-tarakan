/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.util;

/**
 *
 * @author x42jr
 */
public class ContainerBookingGenerator {

    public static String generateId(String noPpkb, String lastId) {
        String i;
        if (lastId == null) {
            i = "0001";
        } else {
            i = String.valueOf(Integer.valueOf(lastId) + 1);
        }

        if (i.length() == 1) {
            i = noPpkb + "-000" + i;
        } else if (i.length() == 2) {
            i = noPpkb + "-00" + i;
        } else if (i.length() == 3) {
            i = noPpkb + "-0" + i;
        } else {
            i = noPpkb + "-" + i;
        }
        return i;
    }
}

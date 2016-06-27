/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qtasnim.util;

import java.io.Serializable;

/**
 *
 * @author dycoder
 */
public class IndonesianNumberConverter implements Serializable{

    /** Creates a new instance of IndonesianNumberConverter */
    public IndonesianNumberConverter() {
    }
    private static final long serialVersionUID = 4601639344887480057L;
    
    public String convertToWord(String theNumber) {
        int length = theNumber.length();

        if (length <= 6) {
            String[] bil = new String[]{"", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam",
                "Tujuh", "Delapan", "Sembilan", "Sepuluh", "Sebelas"};

            int x = Integer.valueOf(theNumber);

            if (x <= 0) {
                return "";
            } else if (x < 12) {
                return bil[(int) x] + " ";
            } else if (x < 20) {
                return convertToWord(String.valueOf(x - 10)) + "Belas ";
            } else if (x < 100) {
                return convertToWord(String.valueOf(x / 10)) + "Puluh "
                        + convertToWord(String.valueOf(x % 10));
            } else if (x < 200) {
                return "Seratus " + convertToWord(String.valueOf(x - 100));
            } else if (x < 1000) {
                return convertToWord(String.valueOf(x / 100)) + "Ratus "
                        + convertToWord(String.valueOf(x % 100));
            } else if (x < 2000) {
                return "Seribu " + convertToWord(String.valueOf(x - 1000));
            } else {
                return convertToWord(String.valueOf(x / 1000)) + "Ribu "
                        + convertToWord(String.valueOf(x % 1000));
            }
        } else {
            int m = length % 3;
            if (m == 0) {
                m = 3;
            }
            String s1 = theNumber.substring(0, m);
            String s2 = theNumber.substring(m, length);

            String[] bil = new String[]{"", "Juta", "Milyar", "Trilyun",
                "Kuadriliun", "Kuintiliun", "Heksiliun"};
            int index = 0;

            if (length <= 9) {
                index = 1;
            } else if (length <= 12) {
                index = 2;
            } else if (length <= 15) {
                index = 3;
            } else if (length <= 18) {
                index = 4;
            } else if (length <= 21) {
                index = 5;
            } else if (length <= 24) {
                index = 6;
            } else {
                return "itung sendiri aja, capek hitungnya ...... ";
            }

            if (Integer.valueOf(s1) <= 0) {
                return convertToWord(s2);
            } else {
                return convertToWord(s1) + bil[index] + " " + convertToWord(s2);
            }
        }
    }
}

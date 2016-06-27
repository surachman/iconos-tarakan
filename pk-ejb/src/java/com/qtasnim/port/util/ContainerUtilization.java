package com.qtasnim.port.util;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import com.qtasnim.text.*;

public class ContainerUtilization
{
    private static final StringBuilder ALPHA;
    private static final Hashtable<Character, Integer> ALPHA_TABLE;
    
    public static String generateFullContName(final String _huruf, final String _angka, String _divider) {
        if (_divider == null) {
            _divider = "";
        }
        final StringBuilder huruf = new StringBuilder(_huruf);
        final StringBuffer angka = new StringBuffer(_angka);
        final StringBuffer divider = new StringBuffer(_divider);
        return huruf.append(divider).append(angka).append(divider).append(generateContNameSuffix(_huruf, _angka)).toString();
    }
    
    public static String generateContNameSuffix(final String _huruf, final String _angka) {
        double sum = 0.0;
        final StringBuilder huruf = new StringBuilder(_huruf.toUpperCase());
        final StringBuilder angka = new StringBuilder(_angka);
        for (int i = 0; i < huruf.length() + angka.length(); ++i) {
            if (i < huruf.length()) {
                sum += ContainerUtilization.ALPHA_TABLE.get(huruf.charAt(i)) * Math.pow(2.0, i);
            }
            else {
                sum += (double)Integer.parseInt(String.valueOf(angka.charAt(i - huruf.length()))) * Math.pow(2.0, i);
            }
        }
        final double __sum = sum / 11.0;
        final double floor = Math.floor(__sum);
        return String.valueOf((int)(sum - floor * 11.0));
    }
    
    public static Boolean checkContainerNumberValidation(final String _contNo) {
        if (_contNo != null && _contNo.length() == 11) {
            final StringBuilder contNo = new StringBuilder(_contNo);
            final String fourDigitLetter = contNo.substring(0, 4);
            final String sixDigitNumber = contNo.substring(4, 10);
            final String lastDigit = contNo.substring(10, 11);
            return StringHelper.isAllLetters(fourDigitLetter) && StringHelper.isAllNumbers(sixDigitNumber) && generateContNameSuffix(fourDigitLetter, sixDigitNumber).equals(lastDigit);
        }
        return false;
    }
    
    static {
        ALPHA = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        ALPHA_TABLE = new Hashtable<Character, Integer>();
        int jumper = 0;
        for (int i = 0; i < ContainerUtilization.ALPHA.length(); ++i) {
            if ((i + 10 + jumper) % 11 == 0) {
                ++jumper;
            }
            ContainerUtilization.ALPHA_TABLE.put(ContainerUtilization.ALPHA.charAt(i), i + 10 + jumper);
        }
    }
}

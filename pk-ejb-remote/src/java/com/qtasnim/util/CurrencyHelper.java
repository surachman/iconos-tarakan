package com.qtasnim.util;

import java.util.*;
import java.text.*;
import java.math.*;

public class CurrencyHelper
{
    private static final Hashtable<String, Locale> locales;
    
    public static String moneyFormat(final BigDecimal price, final String currCode) {
        final Locale locale = CurrencyHelper.locales.get(currCode);
        if (locale != null) {
            return NumberFormat.getCurrencyInstance(locale).format(price.setScale(2, RoundingMode.HALF_EVEN));
        }
        return "0";
    }
    
    static {
        locales = new Hashtable<String, Locale>();
        CurrencyHelper.locales.put("IDR", new Locale("id", "ID"));
        CurrencyHelper.locales.put("USD", Locale.US);
    }
}

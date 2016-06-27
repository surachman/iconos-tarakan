package com.qtasnim.text;

import java.util.*;
import java.math.*;
import java.util.logging.*;
import java.text.*;

public final class TextFormatter
{
    public static Locale createLocal(final String language, final String country) {
        final Locale loc = new Locale(language, country);
        return loc;
    }
    
    public static String currencyFormat(final Locale currentLocale, final BigDecimal value, final Boolean includeCurrCode, final Integer fractionDigits) {
        if (value == null) {
            return "0";
        }
        final Double number = value.doubleValue();
        final DecimalFormat currencyFormatter = (DecimalFormat)DecimalFormat.getCurrencyInstance(currentLocale);
        if (!includeCurrCode) {
            final DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(currentLocale);
            dfs.setCurrencySymbol("");
            currencyFormatter.setDecimalFormatSymbols(dfs);
        }
        currencyFormatter.setMinimumFractionDigits(fractionDigits);
        currencyFormatter.setMinimumFractionDigits(fractionDigits);
        return currencyFormatter.format(number);
    }
    
    public static String currencySymbol(final Locale currentLocale) {
        return DecimalFormatSymbols.getInstance(currentLocale).getCurrencySymbol();
    }
    
    public static BigDecimal parseCurrency(final Locale currentLocale, final String value, final Boolean includeCurrCode, final Integer fractionDigits) {
        final DecimalFormat currencyFormatter = (DecimalFormat)DecimalFormat.getCurrencyInstance(currentLocale);
        if (!includeCurrCode) {
            final DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(currentLocale);
            dfs.setCurrencySymbol("");
            currencyFormatter.setDecimalFormatSymbols(dfs);
        }
        currencyFormatter.setMinimumFractionDigits(fractionDigits);
        currencyFormatter.setMinimumFractionDigits(fractionDigits);
        try {
            return BigDecimal.valueOf(currencyFormatter.parse(value).doubleValue());
        }
        catch (ParseException ex) {
            Logger.getLogger(TextFormatter.class.getName()).log(Level.SEVERE, (String)null, (Throwable)ex);
            return BigDecimal.ZERO;
        }
    }
}

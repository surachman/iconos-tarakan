package com.qtasnim.el;

import com.qtasnim.text.TextFormatter;
import java.math.BigDecimal;
import java.util.Locale;

public class QTasnim
{
  public static String getFormattedCurrency(String language, String country, Object value, Boolean includeCurrCode, Integer fractionDigits)
  {
    Locale local = TextFormatter.createLocal(language, country);
    return TextFormatter.currencyFormat(local, (BigDecimal)value, includeCurrCode, fractionDigits);
  }
  
  public static String getCurrencySymbol(String language, String country)
  {
    Locale local = TextFormatter.createLocal(language, country);
    return TextFormatter.currencySymbol(local);
  }
}

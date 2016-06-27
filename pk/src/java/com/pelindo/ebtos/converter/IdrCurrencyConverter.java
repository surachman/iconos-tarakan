/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.converter;

import com.qtasnim.text.TextFormatter;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author R Seno Anggoro
 */
@FacesConverter("idrCurrencyConverter")
public class IdrCurrencyConverter implements Converter {
    private static final Locale IDR_LOCALE;
    private static final Integer FRACTION_DIGITS;
    private static final Boolean INCLUDE_CURRENCY_CODE;

    static {
        IDR_LOCALE = TextFormatter.createLocal("id", "ID");
        FRACTION_DIGITS = 2;
        INCLUDE_CURRENCY_CODE = true;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            return TextFormatter.currencyFormat(IDR_LOCALE, (BigDecimal) value, INCLUDE_CURRENCY_CODE, FRACTION_DIGITS);
        } catch (RuntimeException re) {
            Logger.getLogger(IdrCurrencyConverter.class.getName()).log(Level.SEVERE, null, re);
            return null;
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            return TextFormatter.parseCurrency(IDR_LOCALE, (String) value, INCLUDE_CURRENCY_CODE, FRACTION_DIGITS);
        } catch (RuntimeException re) {
            Logger.getLogger(IdrCurrencyConverter.class.getName()).log(Level.SEVERE, null, re);
            return null;
        }
    }
}

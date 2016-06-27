package com.qtasnim.url;

import java.util.regex.*;
import java.util.*;
import javax.faces.context.*;

public class UrlHelper
{
    private static char[] specialChars;
    
    private static Pattern generateExpression(final String input) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); ++i) {
            final char letter = input.charAt(i);
            if (letter == '*') {
                sb.append(".*");
            }
            else if (contains(UrlHelper.specialChars, letter)) {
                sb.append("\\" + letter);
            }
            else {
                sb.append(letter);
            }
        }
        return Pattern.compile(sb.toString());
    }
    
    private static boolean contains(final char[] array, final char value) {
        if (array == null || array.length == 0) {
            return false;
        }
        for (int i = 0; i < array.length; ++i) {
            if (array[i] == value) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean compare(final String o1, final String o2) {
        final Pattern urlPattern = generateExpression(o1.toString());
        return urlPattern.matcher(o2.toString()).matches();
    }
    
    public static boolean compare(final List<String> list, final String o2) {
        for (final String test : list) {
            if (compare(test, o2)) {
                return true;
            }
        }
        return false;
    }
    
    public static String resolveFullAddress(final FacesContext facesContext, final String url) {
        return resolveFullAddress(facesContext.getExternalContext(), url);
    }
    
    public static String resolveFullAddress(final ExternalContext externalContext, final String url) {
        return externalContext.encodeResourceURL(externalContext.getRequestScheme() + "://" + externalContext.getRequestServerName() + ":" + externalContext.getRequestServerPort() + externalContext.getRequestContextPath() + url);
    }
    
    static {
        UrlHelper.specialChars = new char[] { '[', '\\', '^', '$', '.', '|', '?', '*', '+', '(', ')' };
    }
}

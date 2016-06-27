package com.qtasnim.text;

import java.util.regex.*;

public class StringHelper
{
    private static String ALL_LETTERS_PATTERN;
    private static String ALL_NUMBERS_PATTERN;
    
    public static Boolean checkStringWithPattern(final String text, final String pattern) {
        if (text != null && pattern != null && text.length() > 0 && pattern.length() > 0) {
            final Pattern p = Pattern.compile(pattern);
            final Matcher m = p.matcher(text);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }
    
    public static Boolean isAllLetters(final String text) {
        if (text != null && text.length() > 0) {
            return checkStringWithPattern(text, StringHelper.ALL_LETTERS_PATTERN);
        }
        return false;
    }
    
    public static Boolean isAllNumbers(final String text) {
        if (text != null && text.length() > 0) {
            return checkStringWithPattern(text, StringHelper.ALL_NUMBERS_PATTERN);
        }
        return false;
    }
    
    static {
        StringHelper.ALL_LETTERS_PATTERN = "^[a-zA-Z]+$";
        StringHelper.ALL_NUMBERS_PATTERN = "^[0-9]+$";
    }
}

package com.qtasnim.util;

import java.util.*;
import java.text.*;

public class StringGenerator
{
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    
    public static String UUIDGEn() {
        return UUID.randomUUID().toString();
    }
    
    public static String timeGen() {
        return timeGen("yyyy-MM-dd HH:mm:ss");
    }
    
    public static String timeGen(final String dateFormat) {
        return now(dateFormat);
    }
    
    private static String now(final String dateFormat) {
        final Calendar cal = Calendar.getInstance();
        return new SimpleDateFormat(dateFormat).format(cal.getTime());
    }
}

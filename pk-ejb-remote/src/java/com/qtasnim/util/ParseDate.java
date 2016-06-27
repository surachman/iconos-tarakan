package com.qtasnim.util;

import java.util.*;
import java.text.*;

public class ParseDate
{
    public static Date changeDate(final String param) {
        final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date d = null;
        try {
            d = df.parse(param);
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }
        return d;
    }
    
    public static String changeNum(final int input) {
        String output = "";
        if (input < 10) {
            return output = "0" + input;
        }
        return output = "" + input;
    }
    
    public static Date changeDate2(final String param) {
        final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date d = null;
        try {
            d = df.parse(param);
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }
        return d;
    }
}

package com.qtasnim.text;

import java.util.*;

public class SqlHelper
{
    public static String translateArrayAsSqlString(final Object[][] object, final Integer index) {
        String result = "";
        for (int i = 0; i < object.length; ++i) {
            result = result + "'" + (String)object[i][index.intValue()] + "',";
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }
    
    public static String translateListAsSqlString(final List<Object[]> objects, final Integer index) {
        String result = "";
        for (final Object[] object : objects) {
            result = result + "'" + (String)object[index.intValue()] + "',";
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }
    
    public static String removeItemFromSqlString(final String conts, final String item) {
        return conts.replace("'" + item + "',", "").replace("'" + item + "'", "");
    }
}

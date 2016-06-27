package com.qtasnim.util;

import java.util.Date;
import java.util.Calendar;

public class DateCalculator
{
    public static int countRangeInDays(final Date date2, final Date date1) {
        if (date1 != null && date2 != null) {
            final Calendar calendar1 = Calendar.getInstance();
            final Calendar calendar2 = Calendar.getInstance();
            calendar1.setTime(date1);
            calendar1.set(11, 0);
            calendar1.set(12, 0);
            calendar1.set(13, 0);
            calendar1.set(14, 0);
            calendar2.setTime(date2);
            calendar2.set(11, 0);
            calendar2.set(12, 0);
            calendar2.set(13, 0);
            calendar2.set(14, 0);
            final Long i = calendar2.getTimeInMillis() - calendar1.getTimeInMillis();
            return (int)Math.ceil(Double.parseDouble(String.valueOf(i)) / 8.64E7);
        }
        return 0;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qtasnim.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author dycoder
 */
public class EnglishNumberConverter {

    private static final long serialVersionUID = -9088183922841856380L;
    /** Holds the number 1-19, dual purpose for special cases (teens) **/
    private static final String[] UNITS = {"One", "Two", "Three", "Four",
        "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve",
        "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
        "Eighteen", "Nineteen"};
    /** Holds the tens places **/
    private static final String[] TENS = {"Ten", "Twenty", "Thirty", "Forty",
        "Fifty", "Sixty", "Seventy", "Eighty", "Ninty"};
    /** Covers max value of Long **/
    private static final String[] THOUSANDS = {"", "Thousand", "Million",
        "Billion", "Trillion", "Quadrillion", "Quintillion", "Sextillion"};
    private static final String[] ORDERDATE = {
        "", "First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh", "Eighth", "Ninth", "Tenth",
        "Eleventh", "Twelveth", "Thirteenth", "Fourteenth", "Fifteenth", "Sixteenth", "Seventeenth",
        "Eighteenth", "Nineteenth", "Twentyth", "Twenty-First", "Twenty-Second", "Twenty-Third", "Twenty-Fourth",
        "Twenty-Fifth", "Twenty-Sixth", "Twenty-Seventh", "Twenty-Eighth", "Twenty-Ninth", "Thirtieth", "Thirty-First"
    };

    protected String monthNameFactory(Date month) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("MM");
        return sdf.format(month);
    }

    /**
     * Represents a number in words (seven hundred thirty four,
     * two hundred and seven, etc...)
     *
     * The largest number this will accept is
     * <pre>999,999,999,999,999,999,999</pre> but that's okay because the largest
     * value of Long is <pre>9,223,372,036,854,775,807</pre>. The smallest number
     * is <pre>-9,223,372,036,854,775,807</pre> (Long.MIN_VALUE +1) due to a
     * limitation of Java.
     *
     * @param number between Long.MIN_VALUE and Long.MAX_VALUE
     * @return the number written in words
     */
    public String numberAsSentenceFactory(String textNumber) {
        String[] word = textNumber.replace('.', '#').split("#");
        if (word.length == 1){
            return parse(word[0]) + "Dollars";
        } else if (word.length == 2){
            return parse(word[0]) + "Dollars " + centFormatter(word[1]);
        } else {
            return "";
        }
    }

    public String parse(String textNumber){
        Long number = Long.decode(textNumber);

        StringBuilder sb = new StringBuilder();
        // Zero is an easy one, but not technically a number :P
        if (number == 0) {
            return "Zero ";
        }

        // Negative numbers are easy too
        if (number < 0) {
            sb.append("negative ");
            number = Math.abs(number);
        }

        // Log keeps track of which Thousands place we're in
        long log = 1000000000000000000L, sub = number;
        int i = 7;
        do {
            sub = number / log; // This is the 1-999 subset of the current thousand's place
            number = number % log; // Cut down number for the next loop
            log = log / 1000; // Cut down log for the next loop
            i--; // tracks the big number place
            if (sub != 0) {
                // If this thousandths place is not 0 (that's okay, just skip)
                // tack it on
                sb.append(this.hundredsInWords(sub));
                sb.append(" ");
                sb.append(THOUSANDS[i]);

                if (number != 0) {
                    sb.append(" ");
                }
            }

        } while (number != 0);

        return sb.toString();
    }

    private String centFormatter(String cent){
        String zero = "";
        while (true){
            if (zero.indexOf("0") == 0){
                System.out.println("te");
                zero += "Zero ";
                cent = cent.substring(1);
            } else {
                System.out.println(cent);
                break;
            }
        }
        cent = String.valueOf(Double.valueOf("0." + cent)).replace("0.", "");
        return cent.equals("0") ? "" : "And " + zero + parse(cent) + "Cents ";
    }

    /**
     * Convert date to number
     * Yes, it has to make its method.
     */
    public String dateToWords(int date) {
        String words = null;

        words = ORDERDATE[date];

        return words;
    }

    /**
     * Converts a number into hundreds.
     *
     * The basic unit of the American numbering system is "hundreds". Thus
     * 1,024 = (one thousand) twenty four
     * 1,048,576 = (one million) (fourty eight thousand) (five hundred seventy six)
     * so there's no need to break it down further than that.
     *
     * @param n between 1 and 999
     * @return
     */
    private String hundredsInWords(long n) {
        // Use assertion errors in private methods only!
        if (n < 1 || n > 999) {
            throw new AssertionError(n);
        }

        StringBuilder sb = new StringBuilder();

        // Handle the "hundred", with a special provision for x01-x09
        if (n > 99) {
            sb.append(UNITS[(int) ((n / 100) - 1)]);
            sb.append(" hundred");
            n = n % 100;

            if (n != 0) {
                sb.append(" ");
            }

            if (n < 10) {
                sb.append("and ");
            }
        }

        // Handle the special cases and the tens place at the same time.
        if (n > 19) {
            sb.append(TENS[(int) ((n / 10) - 1)]);
            n = n % 10;
            if (n != 0) {
                sb.append(" ");
            }
        }

        // This is the ones place
        if (n > 0) {
            sb.append(UNITS[(int) (n - 1)]);
        }

        return sb.toString();
    }
}

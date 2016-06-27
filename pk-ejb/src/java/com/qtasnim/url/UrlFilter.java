package com.qtasnim.url;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlFilter
{
  private ArrayList<Pattern> include = new ArrayList();
  private ArrayList<Pattern> exclude = new ArrayList();
  
  public UrlFilter include(String pattern)
  {
    this.include.add(generateExpression(pattern));
    return this;
  }
  
  public UrlFilter exclude(String pattern)
  {
    this.exclude.add(generateExpression(pattern));
    return this;
  }
  
  public boolean matches(String uri)
  {
    boolean match = false;
    for (Pattern pattern : this.include) {
      match = (match) || (pattern.matcher(uri).matches());
    }
    if (!match) {
      return false;
    }
    for (Pattern pattern : this.exclude) {
      match = (match) && (!pattern.matcher(uri).matches());
    }
    return match;
  }
  
  private static char[] specialChars = { '[', '\\', '^', '$', '.', '|', '?', '*', '+', '(', ')' };
  
  private static Pattern generateExpression(String input)
  {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < input.length(); i++)
    {
      char letter = input.charAt(i);
      if (letter == '*') {
        sb.append(".*");
      } else if (contains(specialChars, letter)) {
        sb.append("\\" + letter);
      } else {
        sb.append(letter);
      }
    }
    return Pattern.compile(sb.toString());
  }
  
  private static boolean contains(char[] array, char value)
  {
    if ((array == null) || (array.length == 0)) {
      return false;
    }
    for (int i = 0; i < array.length; i++)
    {
      char o = array[i];
      if (o == value) {
        return true;
      }
    }
    return false;
  }
}

package com.qtasnim.formula;

public class FormulaManager
{
  private String formula;
  private double result;
  
  public FormulaManager(String formula)
  {
    this.formula = validation(formula);
  }
  
  public double getResult()
  {
    parse(this.formula);
    
    return this.result;
  }
  
  public FormulaManager setParameter(Object key, Object value)
  {
    this.formula = this.formula.replaceAll(":" + key, String.valueOf(value));
    
    return this;
  }
  
  private int parse(String formula)
  {
    if ((!formula.contains("(")) || (!formula.contains(")")))
    {
      this.result = Double.valueOf(formula).doubleValue();
      return 1;
    }
    int start = formula.lastIndexOf("(") + 1;
    int end = formula.lastIndexOf(")");
    String temp = formula.substring(start, end);
    while (temp.contains(")"))
    {
      end = formula.substring(0, end).lastIndexOf(")");
      temp = formula.substring(start, end);
    }
    formula = formula.substring(0, start - 1) + calculate(formula.substring(start - 1, end + 1)) + formula.substring(end + 1, formula.length());
    parse(formula);
    




    return 0;
  }
  
  private double calculate(String text)
  {
    double count = 0.0D;
    String digit = new String();
    
    text = text.substring(1, text.length() - 1);
    for (int i = 0; i < text.length(); i++)
    {
      if (i == text.length() - 1) {
        break;
      }
      switch (text.charAt(i))
      {
      case '-': 
        digit = getDigit(text.substring(i + 1));
        count -= Double.parseDouble(digit);
        i += digit.length();
        
        break;
      case '+': 
        digit = getDigit(text.substring(i + 1));
        count += Double.parseDouble(digit);
        i += digit.length();
        
        break;
      case '*': 
        digit = getDigit(text.substring(i + 1));
        count *= Double.parseDouble(digit);
        i += digit.length();
        
        break;
      case '/': 
        digit = getDigit(text.substring(i + 1));
        count /= Double.parseDouble(digit);
        i += digit.length();
        
        break;
      case ',': 
      case '.': 
      default: 
        digit = getDigit(text.substring(i));
        count = Double.valueOf(digit).doubleValue();
        i += digit.length() - 1;
      }
    }
    return count;
  }
  
  private String getDigit(String text)
  {
    String digitHolder = new String();
    for (int i = 0; i < text.length(); i++)
    {
      if ((!Character.isDigit(text.charAt(i))) && (text.charAt(i) != '.')) {
        break;
      }
      digitHolder = digitHolder + String.valueOf(text.charAt(i));
    }
    return digitHolder;
  }
  
  private String validation(String text)
  {
    text = text.replaceAll(" ", "");
    
    return text;
  }
}

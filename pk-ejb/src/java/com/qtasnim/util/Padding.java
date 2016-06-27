package com.qtasnim.util;

public class Padding
{
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;
    private int paddingLeft;
    
    public Padding(final int paddingTop, final int paddingRight, final int paddingBottom, final int paddingLeft) {
        super();
        this.paddingTop = paddingTop;
        this.paddingRight = paddingRight;
        this.paddingBottom = paddingBottom;
        this.paddingLeft = paddingLeft;
    }
    
    public int getPaddingTop() {
        return this.paddingTop;
    }
    
    public int getPaddingRight() {
        return this.paddingRight;
    }
    
    public int getPaddingBottom() {
        return this.paddingBottom;
    }
    
    public int getPaddingLeft() {
        return this.paddingLeft;
    }
    
    public void setPaddingTop(final int paddingTop) {
        this.paddingTop = paddingTop;
    }
    
    public void setPaddingRight(final int paddingRight) {
        this.paddingRight = paddingRight;
    }
    
    public void setPaddingBottom(final int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }
    
    public void setPaddingLeft(final int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }
}

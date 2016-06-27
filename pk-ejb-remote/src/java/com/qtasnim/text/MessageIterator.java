package com.qtasnim.text;

public class MessageIterator
{
    private static final String DELIMETER = ".";
    private String message;
    private int position;
    
    public MessageIterator(final String message) {
        super();
        this.message = message + ".";
    }
    
    public MessageIterator(final String message, final String stopper) {
        this(message);
        this.position = stopper.length() + 1;
    }
    
    public void next() {
        this.position = this.message.indexOf(".", this.position + 1);
    }
    
    public boolean hasNext() {
        return this.message.indexOf(".", this.position + 1) != -1;
    }
    
    public String getCurrentMessage() {
        return this.message.substring(0, this.position);
    }
}

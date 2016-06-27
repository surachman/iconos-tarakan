package com.qtasnim.edi.model;

import java.io.Serializable;

public class Carrier implements Serializable
{
    private String id;
    private String maintainedCode;
    private String responsibleAgencyCode;
    
    public Carrier() {
        super();
        this.maintainedCode = "172";
        this.responsibleAgencyCode = "20";
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public String getMaintainedCode() {
        return this.maintainedCode;
    }
    
    public void setMaintainedCode(final String maintainedCode) {
        this.maintainedCode = maintainedCode;
    }
    
    public String getResponsibleAgencyCode() {
        return this.responsibleAgencyCode;
    }
    
    public void setResponsibleAgencyCode(final String responsibleAgencyCode) {
        this.responsibleAgencyCode = responsibleAgencyCode;
    }
}

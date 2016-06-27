package com.qtasnim.edi.model;

import java.io.Serializable;
import java.util.Date;

public class Vessel implements Serializable
{
    private String name;
    private String callSign;
    private String voyage;
    private String placeOfDeparture;
    private String nextPortOfCall;
    private Carrier carrier;
    private Date estimatedTimeOfArrival;
    private Date estimatedTimeOfDeparture;
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getCallSign() {
        return this.callSign;
    }
    
    public void setCallSign(final String callSign) {
        this.callSign = callSign;
    }
    
    public String getVoyage() {
        return this.voyage;
    }
    
    public void setVoyage(final String voyage) {
        this.voyage = voyage;
    }
    
    public String getPlaceOfDeparture() {
        return this.placeOfDeparture;
    }
    
    public void setPlaceOfDeparture(final String placeOfDeparture) {
        this.placeOfDeparture = placeOfDeparture;
    }
    
    public String getNextPortOfCall() {
        return this.nextPortOfCall;
    }
    
    public void setNextPortOfCall(final String nextPortOfCall) {
        this.nextPortOfCall = nextPortOfCall;
    }
    
    public Carrier getCarrier() {
        return this.carrier;
    }
    
    public void setCarrier(final Carrier carrier) {
        this.carrier = carrier;
    }
    
    public Date getEstimatedTimeOfArrival() {
        return this.estimatedTimeOfArrival;
    }
    
    public void setEstimatedTimeOfArrival(final Date estimatedTimeOfArrival) {
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
    }
    
    public Date getEstimatedTimeOfDeparture() {
        return this.estimatedTimeOfDeparture;
    }
    
    public void setEstimatedTimeOfDeparture(final Date estimatedTimeOfDeparture) {
        this.estimatedTimeOfDeparture = estimatedTimeOfDeparture;
    }
}

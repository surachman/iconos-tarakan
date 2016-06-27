package com.qtasnim.edi.codeco;

import com.qtasnim.edi.Edifact;
import com.qtasnim.edi.model.Container;
import com.qtasnim.edi.model.Vessel;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Codeco
  implements Edifact, Serializable
{
  public static final String TYPE_GATE_IN = "34";
  public static final String TYPE_GATE_OUT = "36";
  private String referenceId;
  private String sender = "UTPK";
  private String recipient;
  private String communicationAgreement;
  private String transportJourneyIdentifier;
  private String responsibleAgencyCode;
  private String documentNumber;
  private String activeLocation;
  private Date timeOfPreparation;
  private Date messageCompilationTime;
  private Vessel vessel;
  private List<Container> containers;
  
  public String getReferenceId()
  {
    return this.referenceId;
  }
  
  public void setReferenceId(String referenceId)
  {
    this.referenceId = referenceId;
  }
  
  public String getSender()
  {
    return this.sender;
  }
  
  public void setSender(String sender)
  {
    this.sender = sender;
  }
  
  public String getRecipient()
  {
    return this.recipient;
  }
  
  public void setRecipient(String recipient)
  {
    this.recipient = recipient;
  }
  
  public String getCommunicationAgreement()
  {
    return this.communicationAgreement;
  }
  
  public void setCommunicationAgreement(String communicationAgreement)
  {
    this.communicationAgreement = communicationAgreement;
  }
  
  public String getTransportJourneyIdentifier()
  {
    return this.transportJourneyIdentifier;
  }
  
  public void setTransportJourneyIdentifier(String transportJourneyIdentifier)
  {
    this.transportJourneyIdentifier = transportJourneyIdentifier;
  }
  
  public String getResponsibleAgencyCode()
  {
    return this.responsibleAgencyCode;
  }
  
  public void setResponsibleAgencyCode(String responsibleAgencyCode)
  {
    this.responsibleAgencyCode = responsibleAgencyCode;
  }
  
  public String getDocumentNumber()
  {
    return this.documentNumber;
  }
  
  public void setDocumentNumber(String documentNumber)
  {
    this.documentNumber = documentNumber;
  }
  
  public Date getTimeOfPreparation()
  {
    return this.timeOfPreparation;
  }
  
  public void setTimeOfPreparation(Date timeOfPreparation)
  {
    this.timeOfPreparation = timeOfPreparation;
  }
  
  public Date getMessageCompilationTime()
  {
    return this.messageCompilationTime;
  }
  
  public void setMessageCompilationTime(Date messageCompilationTime)
  {
    this.messageCompilationTime = messageCompilationTime;
  }
  
  public Vessel getVessel()
  {
    return this.vessel;
  }
  
  public void setVessel(Vessel vessel)
  {
    this.vessel = vessel;
  }
  
  public List<Container> getContainers()
  {
    return this.containers;
  }
  
  public void setContainers(List<Container> containers)
  {
    this.containers = containers;
  }
  
  public String getActiveLocation()
  {
    return this.activeLocation;
  }
  
  public void setActiveLocation(String activeLocation)
  {
    this.activeLocation = activeLocation;
  }
}

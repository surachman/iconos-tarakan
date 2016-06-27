package com.qtasnim.edi.baplie;

import com.qtasnim.edi.EdifactBuilder;
import com.qtasnim.edi.model.Carrier;
import com.qtasnim.edi.model.Container;
import com.qtasnim.edi.model.Vessel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pb.x12.Context;
import org.pb.x12.Segment;
import org.pb.x12.X12Simple;

public class BaplieBuilder
  implements EdifactBuilder
{
  private Baplie baplie;
  private Context c;
  private X12Simple x12;
  
  public BaplieBuilder(Baplie baplie)
  {
    this.c = new Context(Character.valueOf('\''), Character.valueOf('+'), Character.valueOf(':'));
    this.x12 = new X12Simple(this.c);
    this.baplie = baplie;
    this.baplie.setMessageCompilationTime(new Date());
  }
  
  public String buildAsString()
  {
    build();
    if (this.baplie != null) {
      return this.x12.toString();
    }
    return null;
  }
  
  public String buildAsXML()
  {
    build();
    if (this.baplie != null) {
      return this.x12.toXML();
    }
    return null;
  }
  
  private void build()
  {
    try
    {
      Date now = new Date();
      DateFormat dateFormatter = new SimpleDateFormat("yyMMdd");
      String dateOfPreparation = dateFormatter.format(this.baplie.getTimeOfPreparation());
      dateFormatter = new SimpleDateFormat("HHmm");
      String timeOfPreparation = dateFormatter.format(this.baplie.getTimeOfPreparation());
      dateFormatter = new SimpleDateFormat("yyMMddHHmm");
      this.baplie.setMessageCompilationTime(now);
      String compilationTime = dateFormatter.format(this.baplie.getMessageCompilationTime());
      String estimatedTimeOfArrival = dateFormatter.format(this.baplie.getVessel().getEstimatedTimeOfArrival());
      String estimatedTimeOfDeparture = dateFormatter.format(this.baplie.getVessel().getEstimatedTimeOfDeparture());
      
      Segment s = this.x12.addSegment();
      s.addElement("UNB");
      s.addCompositeElement(new String[] { "UNOA", "1" });
      s.addElement(this.baplie.getSender());
      s.addElement(this.baplie.getRecipient());
      s.addCompositeElement(new String[] { dateOfPreparation, timeOfPreparation });
      s.addElement(this.baplie.getReferenceId());
      s.addElement("");
      s.addElement("");
      s.addElement("");
      s.addElement("");
      s.addElement(this.baplie.getCommunicationAgreement() == null ? this.baplie.getRecipient() : this.baplie.getCommunicationAgreement());
      
      s = this.x12.addSegment();
      s.addElement("UNH");
      s.addElement(this.baplie.getReferenceId());
      s.addCompositeElement(new String[] { "BAPLIE", "1", "911", "UN", "SMDG15" });
      
      this.baplie.setDocumentNumber("BAPLIE_".concat(this.baplie.getVessel().getCallSign().concat(this.baplie.getVessel().getVoyage()).concat(compilationTime).concat(this.baplie.getReferenceId())).replace(" ", ""));
      
      s = this.x12.addSegment();
      s.addElement("BGM");
      s.addElement("");
      s.addElement(this.baplie.getDocumentNumber());
      s.addElement("22");
      
      s = this.x12.addSegment();
      s.addElement("DTM");
      s.addCompositeElement(new String[] { "137", compilationTime, "201" });
      
      s = this.x12.addSegment();
      s.addElement("TDT");
      s.addElement("20");
      s.addElement(this.baplie.getVessel().getVoyage());
      s.addElement("");
      s.addElement("");
      s.addCompositeElement(new String[] { this.baplie.getVessel().getCarrier().getId(), this.baplie.getVessel().getCarrier().getMaintainedCode(), this.baplie.getVessel().getCarrier().getResponsibleAgencyCode() });
      s.addElement("");
      s.addElement("");
      s.addCompositeElement(new String[] { this.baplie.getVessel().getCallSign(), "103", "ZZZ", this.baplie.getVessel().getName() });
      
      s = this.x12.addSegment();
      s.addElement("LOC");
      s.addElement("5");
      s.addCompositeElement(new String[] { this.baplie.getVessel().getPlaceOfDeparture(), "139", "6" });
      
      s = this.x12.addSegment();
      s.addElement("LOC");
      s.addElement("61");
      s.addCompositeElement(new String[] { this.baplie.getVessel().getNextPortOfCall(), "139", "6" });
      
      s = this.x12.addSegment();
      s.addElement("DTM");
      s.addCompositeElement(new String[] { "132", estimatedTimeOfArrival, "203" });
      
      s = this.x12.addSegment();
      s.addElement("DTM");
      s.addCompositeElement(new String[] { "133", estimatedTimeOfDeparture, "203" });
      
      s = this.x12.addSegment();
      s.addElement("RFF");
      s.addCompositeElement(new String[] { "VON", this.baplie.getVessel().getVoyage() });
      for (Container container : this.baplie.getContainers())
      {
        String bay = String.format("%03d", new Object[] { container.getBay() });
        String row = String.format("%02d", new Object[] { container.getRow() });
        String tier = String.format("%02d", new Object[] { container.getTier() });
        
        s = this.x12.addSegment();
        s.addElement("LOC");
        s.addElement("147");
        s.addCompositeElement(new String[] { bay.concat(row).concat(tier), "", "5" });
        
        s = this.x12.addSegment();
        s.addElement("FTX");
        s.addElement("AAA");
        s.addElement("");
        s.addElement("");
        s.addElement(container.getCommodityCode());
        
        s = this.x12.addSegment();
        s.addElement("MEA");
        s.addElement("WT");
        s.addElement("");
        s.addCompositeElement(new String[] { "KGM", container.getGrossWeight().toString() });
        if ((container.getTemperature() != null) && (container.getTemperatureScale() != null))
        {
          s = this.x12.addSegment();
          s.addElement("TMP");
          s.addElement("2");
          s.addCompositeElement(new String[] { container.getTemperature().toString(), container.getTemperatureScale() });
        }
        s = this.x12.addSegment();
        s.addElement("LOC");
        s.addElement("6");
        s.addElement(container.getLoadPort());
        
        s = this.x12.addSegment();
        s.addElement("LOC");
        s.addElement("12");
        s.addElement(container.getPortOfDischarge());
        if (container.getPortOfDelivery() != null)
        {
          s = this.x12.addSegment();
          s.addElement("LOC");
          s.addElement("83");
          s.addElement(container.getPortOfDelivery());
        }
        if (container.getBillOfLadingNumber() != null)
        {
          s = this.x12.addSegment();
          s.addElement("RFF");
          s.addCompositeElement(new String[] { "BM", container.getBillOfLadingNumber() });
        }
        s = this.x12.addSegment();
        s.addElement("EQD");
        s.addElement("CN");
        s.addElement(container.getContainerId());
        s.addElement(container.getContainerType());
        s.addElement("");
        s.addElement("");
        s.addElement(container.getFullEmptyIndicator());
        if (container.getCarrier() != null)
        {
          s = this.x12.addSegment();
          s.addElement("NAD");
          s.addElement("CA");
          s.addCompositeElement(new String[] { container.getCarrier().getId(), container.getCarrier().getMaintainedCode(), container.getCarrier().getResponsibleAgencyCode() });
        }
      }
      int segmentSize = this.x12.getSegments().size();
      
      s = this.x12.addSegment();
      s.addElement("UNT");
      s.addElement(String.valueOf(segmentSize));
      s.addElement(this.baplie.getReferenceId());
      
      s = this.x12.addSegment();
      s.addElement("UNZ");
      s.addElement("1");
      s.addElement(this.baplie.getReferenceId());
    }
    catch (RuntimeException re)
    {
      this.baplie = null;
      Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
    }
  }
}

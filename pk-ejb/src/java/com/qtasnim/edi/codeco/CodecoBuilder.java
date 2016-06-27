package com.qtasnim.edi.codeco;

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

public class CodecoBuilder
  implements EdifactBuilder
{
  private Codeco codeco;
  private CodecoType codecoType;
  private Context c;
  private X12Simple x12;
  
  public CodecoBuilder(Codeco codeco, CodecoType codecoType)
  {
    this.c = new Context(Character.valueOf('\''), Character.valueOf('+'), Character.valueOf(':'));
    this.x12 = new X12Simple(this.c);
    this.codeco = codeco;
    this.codecoType = codecoType;
    this.codeco.setMessageCompilationTime(new Date());
  }
  
  public String buildAsString()
  {
    build();
    if (this.codeco != null) {
      return this.x12.toString();
    }
    return null;
  }
  
  public String buildAsXML()
  {
    build();
    if (this.codeco != null) {
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
      String dateOfPreparation = dateFormatter.format(this.codeco.getTimeOfPreparation());
      dateFormatter = new SimpleDateFormat("HHmm");
      String timeOfPreparation = dateFormatter.format(this.codeco.getTimeOfPreparation());
      dateFormatter = new SimpleDateFormat("yyMMddHHmm");
      this.codeco.setMessageCompilationTime(now);
      String compilationTime = dateFormatter.format(this.codeco.getMessageCompilationTime());
      
      Segment s = this.x12.addSegment();
      s.addElement("UNB");
      s.addCompositeElement(new String[] { "UNOA", "2" });
      s.addElement(this.codeco.getSender());
      s.addElement(this.codeco.getRecipient());
      s.addCompositeElement(new String[] { dateOfPreparation, timeOfPreparation });
      s.addElement(this.codeco.getReferenceId());
      s.addElement("");
      s.addElement("");
      s.addElement("");
      s.addElement("");
      s.addElement(this.codeco.getCommunicationAgreement() == null ? this.codeco.getRecipient() : this.codeco.getCommunicationAgreement());
      
      s = this.x12.addSegment();
      s.addElement("UNH");
      s.addElement(this.codeco.getReferenceId());
      s.addCompositeElement(new String[] { "CODECO", "D", "95B", "UN", "ITG12" });
      
      this.codeco.setDocumentNumber("CODECO_".concat(this.codeco.getVessel().getCallSign().concat(this.codeco.getVessel().getVoyage()).concat(compilationTime).concat(this.codeco.getReferenceId())).replace(" ", ""));
      
      s = this.x12.addSegment();
      s.addElement("BGM");
      if (this.codecoType.equals(CodecoType.GATE_IN)) {
        s.addElement("34");
      } else if (this.codecoType.equals(CodecoType.GATE_OUT)) {
        s.addElement("36");
      }
      s.addElement(this.codeco.getDocumentNumber());
      s.addElement("22");
      
      s = this.x12.addSegment();
      s.addElement("DTM");
      s.addCompositeElement(new String[] { "137", compilationTime, "201" });
      
      s = this.x12.addSegment();
      s.addElement("TDT");
      s.addElement("20");
      s.addElement(this.codeco.getVessel().getVoyage());
      s.addElement("");
      s.addElement("");
      s.addCompositeElement(new String[] { this.codeco.getVessel().getCarrier().getId(), this.codeco.getVessel().getCarrier().getMaintainedCode(), this.codeco.getVessel().getCarrier().getResponsibleAgencyCode() });
      s.addElement("");
      s.addElement("");
      s.addCompositeElement(new String[] { this.codeco.getVessel().getCallSign(), "103", "ZZZ", this.codeco.getVessel().getName() });
      
      s = this.x12.addSegment();
      s.addElement("RFF");
      s.addCompositeElement(new String[] { "VON", this.codeco.getVessel().getVoyage() });
      for (Container container : this.codeco.getContainers())
      {
        s = this.x12.addSegment();
        s.addElement("EQD");
        s.addElement("CN");
        s.addElement(container.getContainerId());
        s.addElement(container.getContainerType());
        s.addElement("");
        s.addElement("");
        s.addElement(container.getFullEmptyIndicator());
        if (container.getBillOfLadingNumber() != null)
        {
          s = this.x12.addSegment();
          s.addElement("RFF");
          s.addCompositeElement(new String[] { "BM", container.getBillOfLadingNumber() });
        }
        dateFormatter = new SimpleDateFormat("yyMMddHHmm");
        String dateWhenPassingThroughTheGate = dateFormatter.format(container.getDateWhenPassingThroughTheGate());
        
        s = this.x12.addSegment();
        s.addElement("DTM");
        s.addCompositeElement(new String[] { "7", dateWhenPassingThroughTheGate, "203" });
        
        s = this.x12.addSegment();
        s.addElement("LOC");
        s.addElement("165");
        s.addElement(this.codeco.getActiveLocation());
        
        s = this.x12.addSegment();
        s.addElement("MEA");
        s.addElement("AAE");
        s.addElement("G");
        s.addCompositeElement(new String[] { "KGM", container.getGrossWeight().toString() });
      }
      int containerSize = this.codeco.getContainers().size();
      
      s = this.x12.addSegment();
      s.addElement("UNT");
      s.addCompositeElement(new String[] { String.valueOf(containerSize), "2" });
      
      int segmentSize = this.x12.getSegments().size();
      
      s = this.x12.addSegment();
      s.addElement("UNT");
      s.addElement(String.valueOf(segmentSize));
      s.addElement(this.codeco.getReferenceId());
      
      s = this.x12.addSegment();
      s.addElement("UNZ");
      s.addElement("1");
      s.addElement(this.codeco.getReferenceId());
    }
    catch (RuntimeException re)
    {
      this.codeco = null;
      Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
    }
  }
}

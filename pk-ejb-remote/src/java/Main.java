import java.util.*;
import com.qtasnim.edi.model.*;
import com.qtasnim.edi.codeco.*;

public class Main
{
    public static void main(final String[] args) {
        final Codeco codeco = new Codeco();
        final Date preparationDate = new Date();
        codeco.setReferenceId("0017");
        codeco.setSender("UTPM");
        codeco.setRecipient("CTP");
        codeco.setTimeOfPreparation(preparationDate);
        final Carrier carrier = new Carrier();
        carrier.setId("CTP");
        carrier.setMaintainedCode("172");
        carrier.setResponsibleAgencyCode("20");
        final Vessel vessel = new Vessel();
        vessel.setVoyage("11W/");
        vessel.setCarrier(carrier);
        vessel.setCallSign("P O E M");
        vessel.setName("CTP INNOVATION KM");
        vessel.setPlaceOfDeparture("IDMKS");
        vessel.setNextPortOfCall("IDJKT");
        vessel.setEstimatedTimeOfArrival(preparationDate);
        vessel.setEstimatedTimeOfDeparture(preparationDate);
        final Carrier mlo = new Carrier();
        mlo.setId("CTP");
        mlo.setMaintainedCode("172");
        mlo.setResponsibleAgencyCode("20");
        final Container container = new Container();
        container.setBay(null);
        container.setRow(null);
        container.setTier(null);
        container.setCommodityCode(null);
        container.setGrossWeight(null);
        container.setTemperatureScale(null);
        container.setLoadPort(null);
        container.setPortOfDischarge(null);
        container.setPortOfDelivery(null);
        container.setContainerId(null);
        container.setContainerType(null);
        container.setFullEmptyIndicator(null);
        container.setBillOfLadingNumber(null);
        container.setCarrier(mlo);
        codeco.setVessel(vessel);
        final CodecoBuilder baplieBuilder = new CodecoBuilder(codeco, CodecoType.GATE_IN);
        final String result = baplieBuilder.buildAsString();
        System.out.println(result);
    }
}

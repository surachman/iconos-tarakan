/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.MasterSettingAppFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningVesselFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.RecapBaplieFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ReceivingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.SequenceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceContLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceGateDeliveryFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceVesselFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.EdifactFactoryFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.RecapBaplie;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.qtasnim.edi.baplie.Baplie;
import com.qtasnim.edi.baplie.BaplieBuilder;
import com.qtasnim.edi.codeco.Codeco;
import com.qtasnim.edi.codeco.CodecoBuilder;
import com.qtasnim.edi.codeco.CodecoType;
import com.qtasnim.edi.model.Carrier;
import com.qtasnim.edi.model.Container;
import com.qtasnim.edi.model.Vessel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;


/**
 *
 * @author senoanggoro
 */
@Stateless
public class EdifactFactoryFacade implements EdifactFactoryFacadeRemote {
    private static final String BAPLIE_GENERATED_ID_PATTERN = "%05d";
    
    @EJB
    private ServiceVesselFacadeLocal serviceVesselFacadeLocal;
    @EJB
    private ServiceContLoadFacadeLocal serviceContLoadFacadeLocal;
    @EJB
    private MasterSettingAppFacadeLocal masterSettingAppFacadeLocal;
    @EJB
    private RecapBaplieFacadeLocal recapBaplieFacadeLocal;
    @EJB
    private SequenceFacadeLocal sequenceFacadeLocal;
    @EJB
    private PlanningVesselFacadeLocal planningVesselFacadeLocal;

    /**
     *
     * @param noPpkb
     * @return
     *      0 = id
     *      1 = message
     */
    public String[] getBaplieByNoPpkb(String noPpkb) {
        ServiceVessel serviceVessel = serviceVesselFacadeLocal.find(noPpkb);

        if (serviceVessel.getStatus().equals("Served")) {
            RecapBaplie recapBaplie = recapBaplieFacadeLocal.findByNoPpkb(noPpkb);

            if (recapBaplie == null) {
                Baplie baplie = new Baplie();
                Date preparationDate = new Date();
                Long nextId = sequenceFacadeLocal.nextEdifactID();

                String implementedPortCode = masterSettingAppFacadeLocal.findImplementedPortCode();
                String referenceId = String.format(BAPLIE_GENERATED_ID_PATTERN, nextId);

                baplie.setReferenceId(referenceId);
                baplie.setSender("UTPK");
                baplie.setRecipient(serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
                baplie.setTimeOfPreparation(preparationDate);

                Carrier carrier = new Carrier();
                carrier.setId(serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterCustomer().getCustCode());
                carrier.setMaintainedCode("172");
                carrier.setResponsibleAgencyCode("20");

                Vessel vessel = new Vessel();
                vessel.setVoyage(serviceVessel.getPlanningVessel().getPreserviceVessel().getVoyOut());
                vessel.setCarrier(carrier);
                vessel.setCallSign(serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterVessel().getCallSign());
                vessel.setName(serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterVessel().getName());
                vessel.setPlaceOfDeparture(implementedPortCode);
                vessel.setNextPortOfCall(serviceVessel.getPlanningVessel().getPreserviceVessel().getMasterPort().getPortCode());
                vessel.setEstimatedTimeOfArrival(serviceVessel.getEta());
                vessel.setEstimatedTimeOfDeparture(serviceVessel.getEtd());

                List<Container> containers = new ArrayList<Container>();
                List<Object[]> serviceContLoads = serviceContLoadFacadeLocal.findContainersThatHaveBeenLoadedAsObjectArray(noPpkb);

                for (Object[] serviceContLoad: serviceContLoads) {
                    Container container = new Container();

                    container.setBay(((Integer) serviceContLoad[17]).shortValue());
                    container.setRow(((Integer) serviceContLoad[18]).shortValue());
                    container.setTier(((Integer) serviceContLoad[19]).shortValue());
                    container.setCommodityCode((String) serviceContLoad[20]);
                    container.setGrossWeight((Integer) serviceContLoad[8]);
                    container.setLoadPort(implementedPortCode);
                    container.setPortOfDischarge((String) serviceContLoad[3]);

                    if (serviceContLoad[4] != null) {
                        container.setPortOfDelivery((String) serviceContLoad[4]);
                    }

                    container.setContainerId((String) serviceContLoad[0]);
                    container.setContainerType((String) serviceContLoad[5]);

                    String contStatus = (String) serviceContLoad[7];
                    String indicator = Container.FULL;

                    if (contStatus.equals("MTY")) {
                        indicator = Container.EMPTY;
                    }

                    container.setFullEmptyIndicator(indicator);
                    container.setBillOfLadingNumber((String) serviceContLoad[1]);

                    if (serviceContLoad[2] != null) {
                        Carrier mlo = new Carrier();
                        mlo.setId((String) serviceContLoad[2]);
                        mlo.setMaintainedCode("172");
                        mlo.setResponsibleAgencyCode("20");
                        container.setCarrier(mlo);
                    } else {
                        container.setCarrier(carrier);
                    }

                    containers.add(container);

                    //container.setTemperatureScale(null);
                    //container.setTemperature(null);
                }

                baplie.setContainers(containers);
                baplie.setVessel(vessel);

                BaplieBuilder baplieBuilder = new BaplieBuilder(baplie);
                String result = baplieBuilder.buildAsString();

                recapBaplie = new RecapBaplie();
                recapBaplie.setId(nextId.intValue());
                recapBaplie.setCompilationTime(baplie.getMessageCompilationTime());
                recapBaplie.setDocumentNumber(baplie.getDocumentNumber());
                recapBaplie.setMessage(result);
                recapBaplie.setServiceVessel(serviceVessel);

                recapBaplieFacadeLocal.create(recapBaplie);
            }

            return new String[] {
                recapBaplie.getDocumentNumber(), recapBaplie.getMessage()
            };
        }

        return new String[] {
            "error", ""
        };
    }
    /**
     *
     * @param noPpkb
     * @return
     *      0 = id
     *      1 = message
     */
    public String[] getCodecoByNoPpkbAndContNo(String noPpkb, String contNo) {
        Object[] passedContainer = planningVesselFacadeLocal.findGatePassedContainer(noPpkb, contNo);

        if (passedContainer != null) {
            PlanningVessel planningVessel = planningVesselFacadeLocal.find(noPpkb);
            List<Container> containers = new ArrayList();
            Date preparationDate = new Date();
            Long nextId = sequenceFacadeLocal.nextEdifactID();

            String implementedPortCode = masterSettingAppFacadeLocal.findImplementedPortCode();
            String referenceId = String.format(BAPLIE_GENERATED_ID_PATTERN, nextId);

            Codeco codeco = new Codeco();
            codeco.setReferenceId(referenceId);
            codeco.setSender("UTPK");
            codeco.setRecipient(planningVessel.getPreserviceVessel().getMasterCustomer().getCustCode());
            codeco.setTimeOfPreparation(preparationDate);
            codeco.setActiveLocation(implementedPortCode);

            Carrier carrier = new Carrier();
            carrier.setId(planningVessel.getPreserviceVessel().getMasterCustomer().getCustCode());
            carrier.setMaintainedCode("172");
            carrier.setResponsibleAgencyCode("20");

            Vessel vessel = new Vessel();
            vessel.setVoyage(planningVessel.getPreserviceVessel().getVoyOut());
            vessel.setCarrier(carrier);
            vessel.setCallSign(planningVessel.getPreserviceVessel().getMasterVessel().getCallSign());
            vessel.setName(planningVessel.getPreserviceVessel().getMasterVessel().getName());
            vessel.setPlaceOfDeparture(implementedPortCode);
            vessel.setNextPortOfCall(planningVessel.getPreserviceVessel().getMasterPort().getPortCode());
            vessel.setEstimatedTimeOfArrival(planningVessel.getEta());
            vessel.setEstimatedTimeOfDeparture(planningVessel.getEtd());

            Container container = new Container();
            container.setGrossWeight((Integer) passedContainer[6]);
            container.setContainerId((String) passedContainer[1]);
            container.setContainerType((String) passedContainer[2]);

            String contStatus = (String) passedContainer[3];
            String indicator = Container.FULL;
            Date passDate = (Date) passedContainer[5];

            if (contStatus.equals("MTY")) {
                indicator = Container.EMPTY;
            }

            container.setFullEmptyIndicator(indicator);
            container.setBillOfLadingNumber((String) passedContainer[4]);
            container.setDateWhenPassingThroughTheGate(passDate);

            containers.add(container);
            codeco.setVessel(vessel);
            codeco.setContainers(containers);

            CodecoType codecoType = null;

            if (passedContainer[7].equals("DELIVERY")) {
                codecoType = CodecoType.GATE_OUT;
            } else {
                codecoType = CodecoType.GATE_IN;
            }

            CodecoBuilder codecoBuilder = new CodecoBuilder(codeco, codecoType);
            String result = codecoBuilder.buildAsString();

            return new String[] {
                codeco.getDocumentNumber(), result
            };
        }

        return new String[] {
            "error", ""
        };
    }
}

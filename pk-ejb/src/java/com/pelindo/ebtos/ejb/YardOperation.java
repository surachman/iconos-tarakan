/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.pelindo.ebtos.ejb.local.YardOperationLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterYardCoordinatFacadeLocal;
import com.pelindo.ebtos.ejb.remote.YardOperationRemote;
import com.pelindo.ebtos.exception.ContainerNotMovableException;
import com.pelindo.ebtos.exception.LocationNotAvailableException;
import com.pelindo.ebtos.model.YardLocation;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author R. Seno Anggoro A
 */
@Stateless
public class YardOperation implements YardOperationRemote, YardOperationLocal {
    @EJB
    private MasterYardCoordinatFacadeLocal masterYardCoordinatFacadeLocal;

    public void liftoff(String noPpkb, String contNo, String contType, short contSize, Integer contWeight, String pod, String grossClass, YardLocation destination) throws LocationNotAvailableException {
        short teus = (short) Math.ceil((double) contSize / (double) 20);

        MasterYardCoordinat targetLocation1 = masterYardCoordinatFacadeLocal.findByCoordinatAndStatus(destination.getBlock(), destination.getSlot(), destination.getRow(), destination.getTier(), "planning", "empty");
        MasterYardCoordinat targetLocation2 = (teus > 1) ? masterYardCoordinatFacadeLocal.findByCoordinatAndStatus(destination.getBlock(), (short) (destination.getSlot() + 1), destination.getRow(), destination.getTier(), "planning", "empty") : null;
        MasterYardCoordinat targetLocation3 = (teus > 2) ? masterYardCoordinatFacadeLocal.findByCoordinatAndStatus(destination.getBlock(), (short) (destination.getSlot() + 2), destination.getRow(), destination.getTier(), "planning", "empty") : null;

        boolean isBottomAvailable = masterYardCoordinatFacadeLocal.isBottomAvailable(destination.getBlock(), destination.getSlot(), destination.getRow(), destination.getTier(), teus);

        if (isBottomAvailable && ((teus == 1 && targetLocation1 != null) || (teus == 2 && targetLocation1 != null && targetLocation2 != null) || (teus == 3 && targetLocation1 != null && targetLocation2 != null && targetLocation3 != null))) {
            targetLocation1.setContNo(contNo);
            targetLocation1.setMlo(null);
            targetLocation1.setContType(contType);
            targetLocation1.setContSize(contSize);
            targetLocation1.setStatus("exist");
            targetLocation1.setContWeight(contWeight);
            targetLocation1.setNoPpkb(noPpkb);
            targetLocation1.setPod(pod);
            targetLocation1.setGrossClass(grossClass);
            masterYardCoordinatFacadeLocal.edit(targetLocation1);

            if (teus > 1) {
                targetLocation2.setContNo(contNo);
                targetLocation2.setMlo(null);
                targetLocation2.setContType(contType);
                targetLocation2.setContSize(contSize);
                targetLocation2.setStatus("exist");
                targetLocation2.setContWeight(contWeight);
                targetLocation2.setNoPpkb(noPpkb);
                targetLocation2.setPod(pod);
                targetLocation2.setGrossClass(grossClass);
                masterYardCoordinatFacadeLocal.edit(targetLocation2);

                if (teus > 2) {
                    targetLocation3.setContNo(contNo);
                    targetLocation3.setMlo(null);
                    targetLocation3.setContType(contType);
                    targetLocation3.setContSize(contSize);
                    targetLocation3.setStatus("exist");
                    targetLocation3.setContWeight(contWeight);
                    targetLocation3.setNoPpkb(noPpkb);
                    targetLocation3.setPod(pod);
                    targetLocation3.setGrossClass(grossClass);
                    masterYardCoordinatFacadeLocal.edit(targetLocation3);
                }
            }

            return;
        }

        throw new LocationNotAvailableException();
    }

    public void plan(String noPpkb, String contNo, String contType, short contSize, String pod, String grossClass, YardLocation destination) throws LocationNotAvailableException {
        short teus = (short) Math.ceil((double) contSize / (double) 20);

        MasterYardCoordinat targetLocation1 = masterYardCoordinatFacadeLocal.findByCoordinatAndStatus(destination.getBlock(), destination.getSlot(), destination.getRow(), destination.getTier(), "empty");
        MasterYardCoordinat targetLocation2 = (teus > 1) ? masterYardCoordinatFacadeLocal.findByCoordinatAndStatus(destination.getBlock(), (short) (destination.getSlot() + 1), destination.getRow(), destination.getTier(), "empty") : null;
        MasterYardCoordinat targetLocation3 = (teus > 2) ? masterYardCoordinatFacadeLocal.findByCoordinatAndStatus(destination.getBlock(), (short) (destination.getSlot() + 2), destination.getRow(), destination.getTier(), "empty") : null;

        if ((teus == 1 && targetLocation1 != null) || (teus == 2 && targetLocation1 != null && targetLocation2 != null) || (teus == 3 && targetLocation1 != null && targetLocation2 != null && targetLocation3 != null)) {
            targetLocation1.setContNo(contNo);
            targetLocation1.setMlo(null);
            targetLocation1.setContType(contType);
            targetLocation1.setContSize(contSize);
            targetLocation1.setStatus("planning");
            targetLocation1.setContWeight(null);
            targetLocation1.setNoPpkb(noPpkb);
            targetLocation1.setPod(pod);
            targetLocation1.setGrossClass(grossClass);
            masterYardCoordinatFacadeLocal.edit(targetLocation1);

            if (teus > 1) {
                targetLocation2.setContNo(contNo);
                targetLocation2.setMlo(null);
                targetLocation2.setContType(contType);
                targetLocation2.setContSize(contSize);
                targetLocation2.setStatus("planning");
                targetLocation2.setContWeight(null);
                targetLocation2.setNoPpkb(noPpkb);
                targetLocation2.setPod(pod);
                targetLocation2.setGrossClass(grossClass);
                masterYardCoordinatFacadeLocal.edit(targetLocation2);

                if (teus > 2) {
                    targetLocation3.setContNo(contNo);
                    targetLocation3.setMlo(null);
                    targetLocation3.setContType(contType);
                    targetLocation3.setContSize(contSize);
                    targetLocation3.setStatus("planning");
                    targetLocation3.setContWeight(null);
                    targetLocation3.setNoPpkb(noPpkb);
                    targetLocation3.setPod(pod);
                    targetLocation3.setGrossClass(grossClass);
                    masterYardCoordinatFacadeLocal.edit(targetLocation3);
                }
            }

            return;
        }

        throw new LocationNotAvailableException();
    }

    public void lifton(String noPpkb, String contNo, short contSize, YardLocation destination) throws ContainerNotMovableException {
        short teus = (short) Math.ceil((double) contSize / (double) 20);

        MasterYardCoordinat targetLocation1 = masterYardCoordinatFacadeLocal.findByCoordinatAndStatus(destination.getBlock(), destination.getSlot(), destination.getRow(), destination.getTier(), "exist");
        MasterYardCoordinat targetLocation2 = (teus > 1) ? masterYardCoordinatFacadeLocal.findByCoordinatAndStatus(destination.getBlock(), (short) (destination.getSlot() + 1), destination.getRow(), destination.getTier(), "exist") : null;
        MasterYardCoordinat targetLocation3 = (teus > 2) ? masterYardCoordinatFacadeLocal.findByCoordinatAndStatus(destination.getBlock(), (short) (destination.getSlot() + 2), destination.getRow(), destination.getTier(), "exist") : null;

        boolean isTopEmpty = masterYardCoordinatFacadeLocal.isTopEmpty(destination.getBlock(), destination.getSlot(), destination.getRow(), destination.getTier(), teus);

        if (isTopEmpty) {
            if (targetLocation1 != null && targetLocation1.getContNo().equals(contNo) && targetLocation1.getNoPpkb().equals(noPpkb)) {
                targetLocation1.setContNo(null);
                targetLocation1.setMlo(null);
                targetLocation1.setContType(null);
                targetLocation1.setContSize(null);
                targetLocation1.setStatus("empty");
                targetLocation1.setContWeight(null);
                targetLocation1.setNoPpkb(null);
                targetLocation1.setPod(null);
                targetLocation1.setGrossClass(null);
                masterYardCoordinatFacadeLocal.edit(targetLocation1);
            }

            if (teus > 1) {
                if (targetLocation2 != null && targetLocation2.getContNo().equals(contNo) && targetLocation2.getNoPpkb().equals(noPpkb)) {
                    targetLocation2.setContNo(null);
                    targetLocation2.setMlo(null);
                    targetLocation2.setContType(null);
                    targetLocation2.setContSize(null);
                    targetLocation2.setStatus("empty");
                    targetLocation2.setContWeight(null);
                    targetLocation2.setNoPpkb(null);
                    targetLocation2.setPod(null);
                    targetLocation2.setGrossClass(null);
                    masterYardCoordinatFacadeLocal.edit(targetLocation2);
                }

                if (teus > 2) {
                    if (targetLocation3 != null && targetLocation3.getContNo().equals(contNo) && targetLocation3.getNoPpkb().equals(noPpkb)) {
                        targetLocation3.setContNo(null);
                        targetLocation3.setMlo(null);
                        targetLocation3.setContType(null);
                        targetLocation3.setContSize(null);
                        targetLocation3.setStatus("empty");
                        targetLocation3.setContWeight(null);
                        targetLocation3.setNoPpkb(null);
                        targetLocation3.setPod(null);
                        targetLocation3.setGrossClass(null);
                        masterYardCoordinatFacadeLocal.edit(targetLocation3);
                    }
                }
            }

            return;
        }

        throw new ContainerNotMovableException();
    }

    public void forceLifton(String noPpkb, String contNo, short contSize, YardLocation destination) throws ContainerNotMovableException {
        short teus = (short) Math.ceil((double) contSize / (double) 20);

        MasterYardCoordinat targetLocation1 = masterYardCoordinatFacadeLocal.findByCoordinatAndStatus(destination.getBlock(), destination.getSlot(), destination.getRow(), destination.getTier(), "exist");
        MasterYardCoordinat targetLocation2 = (teus > 1) ? masterYardCoordinatFacadeLocal.findByCoordinatAndStatus(destination.getBlock(), (short) (destination.getSlot() + 1), destination.getRow(), destination.getTier(), "exist") : null;
        MasterYardCoordinat targetLocation3 = (teus > 2) ? masterYardCoordinatFacadeLocal.findByCoordinatAndStatus(destination.getBlock(), (short) (destination.getSlot() + 2), destination.getRow(), destination.getTier(), "exist") : null;

        boolean isTopEmpty = true;

        if (isTopEmpty) {
            if (targetLocation1 != null && targetLocation1.getContNo().equals(contNo) && targetLocation1.getNoPpkb().equals(noPpkb)) {
                targetLocation1.setContNo(null);
                targetLocation1.setMlo(null);
                targetLocation1.setContType(null);
                targetLocation1.setContSize(null);
                targetLocation1.setStatus("empty");
                targetLocation1.setContWeight(null);
                targetLocation1.setNoPpkb(null);
                targetLocation1.setPod(null);
                targetLocation1.setGrossClass(null);
                masterYardCoordinatFacadeLocal.edit(targetLocation1);
            }

            if (teus > 1) {
                if (targetLocation2 != null && targetLocation2.getContNo().equals(contNo) && targetLocation2.getNoPpkb().equals(noPpkb)) {
                    targetLocation2.setContNo(null);
                    targetLocation2.setMlo(null);
                    targetLocation2.setContType(null);
                    targetLocation2.setContSize(null);
                    targetLocation2.setStatus("empty");
                    targetLocation2.setContWeight(null);
                    targetLocation2.setNoPpkb(null);
                    targetLocation2.setPod(null);
                    targetLocation2.setGrossClass(null);
                    masterYardCoordinatFacadeLocal.edit(targetLocation2);
                }

                if (teus > 2) {
                    if (targetLocation3 != null && targetLocation3.getContNo().equals(contNo) && targetLocation3.getNoPpkb().equals(noPpkb)) {
                        targetLocation3.setContNo(null);
                        targetLocation3.setMlo(null);
                        targetLocation3.setContType(null);
                        targetLocation3.setContSize(null);
                        targetLocation3.setStatus("empty");
                        targetLocation3.setContWeight(null);
                        targetLocation3.setNoPpkb(null);
                        targetLocation3.setPod(null);
                        targetLocation3.setGrossClass(null);
                        masterYardCoordinatFacadeLocal.edit(targetLocation3);
                    }
                }
            }

            return;
        }

        throw new ContainerNotMovableException();
    }

    public void lifton(String noPpkb, String contNo, String contType, short contSize, String pod, String grossClass, YardLocation destination) throws ContainerNotMovableException {
        short teus = (short) Math.ceil((double) contSize / (double) 20);

        MasterYardCoordinat targetLocation1 = masterYardCoordinatFacadeLocal.findByCoordinatAndStatus(destination.getBlock(), destination.getSlot(), destination.getRow(), destination.getTier(), "exist");
        MasterYardCoordinat targetLocation2 = (teus > 1) ? masterYardCoordinatFacadeLocal.findByCoordinatAndStatus(destination.getBlock(), (short) (destination.getSlot() + 1), destination.getRow(), destination.getTier(), "exist") : null;
        MasterYardCoordinat targetLocation3 = (teus > 2) ? masterYardCoordinatFacadeLocal.findByCoordinatAndStatus(destination.getBlock(), (short) (destination.getSlot() + 2), destination.getRow(), destination.getTier(), "exist") : null;

        boolean isTopEmpty = masterYardCoordinatFacadeLocal.isTopEmpty(destination.getBlock(), destination.getSlot(), destination.getRow(), destination.getTier(), teus);

        if (isTopEmpty) {
            if (targetLocation1 != null && targetLocation1.getContNo().equals(contNo) && targetLocation1.getNoPpkb().equals(noPpkb)) {
                targetLocation1.setContNo(contNo);
                targetLocation1.setMlo(null);
                targetLocation1.setContType(contType);
                targetLocation1.setContSize(contSize);
                targetLocation1.setStatus("planning");
                targetLocation1.setContWeight(null);
                targetLocation1.setNoPpkb(noPpkb);
                targetLocation1.setPod(pod);
                targetLocation1.setGrossClass(grossClass);
                masterYardCoordinatFacadeLocal.edit(targetLocation1);
            }

            if (teus > 1) {
                if (targetLocation2 != null && targetLocation2.getContNo().equals(contNo) && targetLocation2.getNoPpkb().equals(noPpkb)) {
                    targetLocation2.setContNo(contNo);
                    targetLocation2.setMlo(null);
                    targetLocation2.setContType(contType);
                    targetLocation2.setContSize(contSize);
                    targetLocation2.setStatus("planning");
                    targetLocation2.setContWeight(null);
                    targetLocation2.setNoPpkb(noPpkb);
                    targetLocation2.setPod(pod);
                    targetLocation2.setGrossClass(grossClass);
                    masterYardCoordinatFacadeLocal.edit(targetLocation2);
                }

                if (teus > 2) {
                    if (targetLocation3 != null && targetLocation3.getContNo().equals(contNo) && targetLocation3.getNoPpkb().equals(noPpkb)) {
                        targetLocation3.setContNo(contNo);
                        targetLocation3.setMlo(null);
                        targetLocation3.setContType(contType);
                        targetLocation3.setContSize(contSize);
                        targetLocation3.setStatus("planning");
                        targetLocation3.setContWeight(null);
                        targetLocation3.setNoPpkb(noPpkb);
                        targetLocation3.setPod(pod);
                        targetLocation3.setGrossClass(grossClass);
                        masterYardCoordinatFacadeLocal.edit(targetLocation3);
                    }
                }
            }

            return;
        }

        throw new ContainerNotMovableException();
    }
}

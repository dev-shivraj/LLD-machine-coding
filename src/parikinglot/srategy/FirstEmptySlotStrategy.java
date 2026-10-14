package parikinglot.srategy;

import parikinglot.model.ParkingFloor;
import parikinglot.model.ParkingLot;
import parikinglot.model.ParkingSlot;
import parikinglot.model.enums.ParkingSlotStatus;
import parikinglot.model.enums.VehicleType;

public class FirstEmptySlotStrategy implements SlotAllocationStrategy{
    @Override
    public ParkingSlot allocateSlot(ParkingLot parkingLot, VehicleType vehicleType) {
        for(ParkingFloor floor: parkingLot.getFloors()) {
            for(ParkingSlot slot: floor.getSlots()) {
                if(slot.getStatus().equals(ParkingSlotStatus.EMPTY) &&
                        slot.getAllowedType().equals(vehicleType)) {
                    return slot;
                }
            }
        }
        return null;
    }
}
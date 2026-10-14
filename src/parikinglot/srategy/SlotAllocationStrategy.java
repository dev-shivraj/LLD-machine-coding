package parikinglot.srategy;

import parikinglot.model.ParkingLot;
import parikinglot.model.ParkingSlot;
import parikinglot.model.enums.VehicleType;

public interface SlotAllocationStrategy {

    ParkingSlot allocateSlot(ParkingLot parkingLot, VehicleType vehicleType);
}

package parikinglot.model;

import parikinglot.model.enums.ParkingLotStatus;
import parikinglot.srategy.PricingStrategy;
import parikinglot.srategy.SlotAllocationStrategy;

import java.util.List;

public class ParkingLot extends BaseEntity{

    private String address;

    private List<ParkingFloor> floors;

    private List<Gate> gates;

    private ParkingLotStatus parkingLotStatus;

    private PricingStrategy pricingStrategy;

    private SlotAllocationStrategy slotAllocationStrategy;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ParkingFloor> getFloors() {
        return floors;
    }

    public void setFloors(List<ParkingFloor> floors) {
        this.floors = floors;
    }

    public List<Gate> getGates() {
        return gates;
    }

    public void setGates(List<Gate> gates) {
        this.gates = gates;
    }

    public ParkingLotStatus getParkingLotStatus() {
        return parkingLotStatus;
    }

    public void setParkingLotStatus(ParkingLotStatus parkingLotStatus) {
        this.parkingLotStatus = parkingLotStatus;
    }

    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }

    public void setPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public SlotAllocationStrategy getSlotAllocationStrategy() {
        return slotAllocationStrategy;
    }

    public void setSlotAllocationStrategy(SlotAllocationStrategy slotAllocationStrategy) {
        this.slotAllocationStrategy = slotAllocationStrategy;
    }
}

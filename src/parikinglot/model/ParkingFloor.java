package parikinglot.model;

import java.util.List;

public class ParkingFloor extends BaseEntity{

    private String floorNumber;

    private List<ParkingSlot> slots;

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public List<ParkingSlot> getSlots() {
        return slots;
    }

    public void setSlots(List<ParkingSlot> slots) {
        this.slots = slots;
    }
}
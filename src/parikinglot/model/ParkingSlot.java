package parikinglot.model;

import parikinglot.model.enums.ParkingSlotStatus;
import parikinglot.model.enums.VehicleType;

public class ParkingSlot extends BaseEntity{

    private String slotNumber;

    private VehicleType allowedType;

    private ParkingSlotStatus status;

    private Vehicle vehicle;

    // bidirectional  -> NOT NEEDED ONLY FOR CONCEPT
    private ParkingFloor floor;

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public VehicleType getAllowedType() {
        return allowedType;
    }

    public void setAllowedType(VehicleType allowedType) {
        this.allowedType = allowedType;
    }

    public ParkingSlotStatus getStatus() {
        return status;
    }

    public void setStatus(ParkingSlotStatus status) {
        this.status = status;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingFloor getFloor() {
        return floor;
    }

    public void setFloor(ParkingFloor floor) {
        this.floor = floor;
    }
}
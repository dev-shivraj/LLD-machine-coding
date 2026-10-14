package parikinglot.model;

import parikinglot.model.enums.VehicleType;

public class Vehicle extends BaseEntity{

    private String ownerName;

    private String vehicleNumber;

    private VehicleType vehicleType;

    public Vehicle(String ownerName, String vehicleNumber, VehicleType vehicleType) {
        this.ownerName = ownerName;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
}
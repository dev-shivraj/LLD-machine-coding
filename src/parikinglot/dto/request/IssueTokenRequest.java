package parikinglot.dto.request;

import parikinglot.model.enums.VehicleType;

public class IssueTokenRequest {

    private String ownerName;

    private String vehicleNumber;

    private VehicleType vehicleType;

    private int gateId;

    private int parkingLotId;

    public IssueTokenRequest(String ownerName, String vehicleNumber, VehicleType vehicleType, int gateId, int parkingLotId) {
        this.ownerName = ownerName;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.gateId = gateId;
        this.parkingLotId = parkingLotId;
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

    public int getGateId() {
        return gateId;
    }

    public void setGateId(int gateId) {
        this.gateId = gateId;
    }

    public int getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(int parkingLotId) {
        this.parkingLotId = parkingLotId;
    }
}
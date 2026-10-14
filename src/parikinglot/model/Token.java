package parikinglot.model;

import java.util.Date;

public class Token extends BaseEntity{

    private String tokenNo;

    private Date entryTime;

    private Vehicle vehicle;

    private Gate gate;

    private ParkingSlot assignedSlot;

    public String getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(String tokenNo) {
        this.tokenNo = tokenNo;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    public ParkingSlot getAssignedSlot() {
        return assignedSlot;
    }

    public void setAssignedSlot(ParkingSlot assignedSlot) {
        this.assignedSlot = assignedSlot;
    }
}
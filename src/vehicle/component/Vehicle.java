package vehicle.component;

import vehicle.enums.VehicleStatus;
import vehicle.enums.VehicleType;
import vehicle.powertrain.Chargeable;
import vehicle.powertrain.Powertrain;
import vehicle.powertrain.Refuelable;

import java.util.Objects;

public class Vehicle {

    private final String vehicleId;
    private final String registrationNumber;
    private final String manufacturer;
    private final String model;
    private final VehicleType vehicleType;
    private final Powertrain powertrain;

    private VehicleStatus status;

    public Vehicle(
            String vehicleId,
            String registrationNumber,
            String manufacturer,
            String model,
            VehicleType vehicleType,
            Powertrain powertrain
    ) {
        this.vehicleId = requireText(vehicleId, "vehicleId");
        this.registrationNumber = requireText(registrationNumber, "registrationNumber");
        this.manufacturer = requireText(manufacturer, "manufacturer");
        this.model = requireText(model, "model");
        this.vehicleType = Objects.requireNonNull(vehicleType, "vehicleType cannot be null");
        this.powertrain = Objects.requireNonNull(powertrain, "powertrain cannot be null");
        this.status = VehicleStatus.INACTIVE;
    }

    public void start() {
        ensureInactive();
        powertrain.start();
        status = VehicleStatus.ACTIVE;
    }

    public void stop() {
        ensureActive();
        status = VehicleStatus.INACTIVE;
    }

    public void drive() {
        ensureActive();
        System.out.println("Vehicle is driving");
    }

    public void accelerate() {
        ensureActive();
        System.out.println("Vehicle is accelerating");
    }

    public void refuel() {
        ensureInactive();

        if (!(powertrain instanceof Refuelable refuelable)) {
            throw new UnsupportedOperationException("Vehicle does not support refueling");
        }

        refuelable.refuel();
    }

    public void charge() {
        ensureInactive();

        if (!(powertrain instanceof Chargeable chargeable)) {
            throw new UnsupportedOperationException("Vehicle does not support charging");
        }

        chargeable.charge();
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    private void ensureActive() {

        if (status != VehicleStatus.ACTIVE) {
            throw new IllegalStateException("Vehicle must be active for this operation");
        }
    }

    private void ensureInactive() {
        if (status != VehicleStatus.INACTIVE) {
            throw new IllegalStateException("Vehicle must be inactive for this operation");
        }
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or blank");
        }

        return value;
    }
}
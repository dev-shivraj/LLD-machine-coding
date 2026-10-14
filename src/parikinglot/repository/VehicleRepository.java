package parikinglot.repository;



import parikinglot.model.Vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VehicleRepository {

    private static int vehicleId = 1;
    private Map<Integer, Vehicle> vehicleDb;

    public VehicleRepository() {
        this.vehicleDb = new HashMap<>();
    }

    public Optional<Vehicle> findByVehicleNumber(String vehicleNumber) {
        for(Vehicle vehicle: vehicleDb.values()) {
            if(vehicle.getVehicleNumber().equalsIgnoreCase(vehicleNumber)) {
                return Optional.of(vehicle);
            }
        }
        return Optional.empty();
    }


    public Vehicle save(Vehicle newVehicle) {
        newVehicle.setId(vehicleId++);
        vehicleDb.put(newVehicle.getId(),newVehicle);
        return newVehicle;
    }
}
package parikinglot.service;

import parikinglot.exception.GateNotFoundException;
import parikinglot.model.*;
import parikinglot.model.enums.ParkingSlotStatus;
import parikinglot.model.enums.VehicleType;
import parikinglot.repository.GateRepository;
import parikinglot.repository.ParkingLotRepo;
import parikinglot.repository.VehicleRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class TokenService {

    private GateRepository gateRepository;
    private ParkingLotRepo parkingLotRepo;
    private VehicleRepository vehicleRepository;

    public TokenService(GateRepository gateRepository,
                        ParkingLotRepo parkingLotRepo,
                        VehicleRepository vehicleRepository) {
        this.gateRepository = gateRepository;
        this.parkingLotRepo = parkingLotRepo;
        this.vehicleRepository = vehicleRepository;
    }

    public Token issueToken(int gateId,
                            String ownerName,
                            String vehicleNumber,
                            VehicleType vehicleType,
                            int parkingLotId) {

        // gate id should exist
        Optional<Gate> gateOptional = gateRepository.findById(gateId);
        if (gateOptional.isEmpty()) {
            throw new GateNotFoundException("Invalid gate id: " + gateId);
        }

        Optional<ParkingLot> parkingLotOptional = parkingLotRepo.findById(parkingLotId);
        if (parkingLotOptional.isEmpty()) {
            throw new RuntimeException("Invalid parking lot");
        }

        // MORE validations

        Optional<Vehicle> vehicleOptional = vehicleRepository.findByVehicleNumber(vehicleNumber);
        Vehicle vehicle = null;

        if (vehicleOptional.isEmpty()) {
            Vehicle newVehicle = new Vehicle(ownerName, vehicleNumber, vehicleType);
            vehicle = vehicleRepository.save(newVehicle);
        } else {
            vehicle = vehicleOptional.get();
        }

        Gate gate = gateOptional.get();
        ParkingLot parkingLot = parkingLotOptional.get();


        ParkingSlot assignedSlot = parkingLot.getSlotAllocationStrategy()
                .allocateSlot(parkingLot, vehicle.getVehicleType());

        // check if slot is null
        assignedSlot.setStatus(ParkingSlotStatus.FILLED);
        // db call to save the updated slot

        Token token = new Token();
        token.setTokenNo(String.valueOf(UUID.randomUUID()));
        token.setEntryTime(new Date());
        token.setGate(gate);
        token.setAssignedSlot(assignedSlot);
        token.setVehicle(vehicle);

        return token;


    }
}

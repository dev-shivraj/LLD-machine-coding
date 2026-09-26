package vehicle.factory;

import vehicle.component.Vehicle;
import vehicle.enums.PowertrainType;
import vehicle.enums.VehicleType;
import vehicle.powertrain.Powertrain;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class VehicleFactory {
    private static final Map<VehicleType, Set<PowertrainType>> ALLOWED_POWERTRAINS = Map.of(
                    VehicleType.CAR, Set.of(PowertrainType.PETROL, PowertrainType.BEV, PowertrainType.HEV, PowertrainType.PHEV),
                    VehicleType.BIKE, Set.of(PowertrainType.PETROL, PowertrainType.BEV),
                    VehicleType.TRUCK, Set.of(PowertrainType.DIESEL)
            );

    private VehicleFactory() {
    }

    public static Vehicle create(
            String vehicleId,
            String registrationNumber,
            String manufacturer,
            String model,
            VehicleType vehicleType,
            PowertrainType powertrainType
    ) {

        Objects.requireNonNull(vehicleType, "vehicleType cannot be null");
        Objects.requireNonNull(powertrainType, "powertrainType cannot be null");

        validateCombination(vehicleType, powertrainType);
        Powertrain powertrain = PowertrainFactory.create(powertrainType);

        return new Vehicle(vehicleId, registrationNumber, manufacturer, model, vehicleType, powertrain);
    }

    private static void validateCombination(VehicleType vehicleType, PowertrainType powertrainType) {
        Set<PowertrainType> allowedPowertrains = ALLOWED_POWERTRAINS.get(vehicleType);

        if (!allowedPowertrains.contains(powertrainType)) {
            throw new IllegalArgumentException("Invalid combination: " + vehicleType + " cannot use " + powertrainType);
        }
    }
}
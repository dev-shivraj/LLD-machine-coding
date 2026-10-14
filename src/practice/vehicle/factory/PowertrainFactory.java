package practice.vehicle.factory;

import practice.vehicle.enums.PowertrainType;
import practice.vehicle.powertrain.*;

public class PowertrainFactory {

    private PowertrainFactory() {
    }

    public static Powertrain create(PowertrainType type) {

        if (type == null) {
            throw new IllegalArgumentException("Powertrain type cannot be null");
        }

        return switch (type) {
            case PETROL -> new PetrolPowertrain();
            case DIESEL -> new DieselPowertrain();
            case BEV -> new BEVPowertrain();
            case HEV -> new HEVPowertrain();
            case PHEV -> new PHEVPowertrain();
        };
    }
}
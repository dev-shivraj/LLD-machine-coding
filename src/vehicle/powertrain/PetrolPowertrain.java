package vehicle.powertrain;

public class PetrolPowertrain implements Powertrain, Refuelable {

    @Override
    public void start() {
        System.out.println("Petrol engine started");
    }

    @Override
    public void refuel() {
        System.out.println("Petrol vehicle refueled");
    }
}
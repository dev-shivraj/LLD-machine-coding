package vehicle.powertrain;

public class DieselPowertrain implements Powertrain, Refuelable {

    @Override
    public void start() {
        System.out.println("Diesel engine started");
    }

    @Override
    public void refuel() {
        System.out.println("Diesel vehicle refueled");
    }
}
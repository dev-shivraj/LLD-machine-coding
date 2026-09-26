package vehicle.powertrain;

public class HEVPowertrain implements Powertrain, Refuelable {

    @Override
    public void start() {
        System.out.println("Hybrid powertrain started");
    }

    @Override
    public void refuel() {
        System.out.println("Hybrid vehicle refueled");
    }
}
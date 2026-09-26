package vehicle.powertrain;

public class PHEVPowertrain implements Powertrain, Refuelable, Chargeable {

    @Override
    public void start() {
        System.out.println("Plug-in hybrid powertrain started");
    }

    @Override
    public void refuel() {
        System.out.println("Plug-in hybrid vehicle refueled");
    }

    @Override
    public void charge() {
        System.out.println("Plug-in hybrid battery charging");
    }
}
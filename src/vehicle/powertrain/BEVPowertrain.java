package vehicle.powertrain;

public class BEVPowertrain implements Powertrain, Chargeable {

    @Override
    public void start() {
        System.out.println("Electric motor started");
    }

    @Override
    public void charge() {
        System.out.println("Battery charging");
    }
}
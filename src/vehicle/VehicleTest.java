package vehicle;

import vehicle.component.Vehicle;
import vehicle.enums.PowertrainType;
import vehicle.enums.VehicleType;
import vehicle.factory.VehicleFactory;

public class VehicleTest {
    public static void main(String[] args) {

        testPetrolCar();
//        testElectricBike();
//        testDieselTruck();
//        testPhev();
//        testInvalidCombination();
//        testInvalidState();
//        testUnsupportedCapability();
//
//        System.out.println("\nAll tests completed.");
    }

    private static void testPetrolCar() {

        System.out.println("\n--- Petrol Car ---");

        Vehicle car = VehicleFactory.create(
                "C001",
                "KA01AA1111",
                "Toyota",
                "Camry",
                VehicleType.CAR,
                PowertrainType.PETROL
        );

        System.out.println("Initial state: " + car.getStatus());

        car.refuel();
        car.start();
        System.out.println("After start: " + car.getStatus());
        car.drive();
        car.accelerate();
        car.stop();
        System.out.println("After stop: " + car.getStatus());
    }

    private static void testElectricBike() {

        System.out.println("\n--- Electric Bike ---");

        Vehicle bike = VehicleFactory.create(
                "B001",
                "KA01BB2222",
                "Ather",
                "450X",
                VehicleType.BIKE,
                PowertrainType.BEV
        );

        bike.charge();
        bike.start();
        bike.drive();
        bike.stop();
        System.out.println("Electric bike test passed");
    }

    private static void testDieselTruck() {

        System.out.println("\n--- Diesel Truck ---");

        Vehicle truck = VehicleFactory.create(
                "T001",
                "KA01CC3333",
                "Tata",
                "Prima",
                VehicleType.TRUCK,
                PowertrainType.DIESEL
        );

        truck.refuel();
        truck.start();
        truck.drive();
        truck.stop();
        System.out.println("Diesel truck test passed");
    }

    private static void testPhev() {

        System.out.println("\n--- PHEV ---");

        Vehicle phev = VehicleFactory.create(
                "C002",
                "KA01PP4444",
                "Toyota",
                "Prius",
                VehicleType.CAR,
                PowertrainType.PHEV
        );

        phev.refuel();
        phev.charge();
        phev.start();
        phev.drive();
        phev.stop();
        System.out.println("PHEV test passed");
    }

    private static void testInvalidCombination() {

        System.out.println("\n--- Invalid Combination ---");

        try {

            VehicleFactory.create(
                    "T002",
                    "KA01TT5555",
                    "Tata",
                    "Prima",
                    VehicleType.TRUCK,
                    PowertrainType.BEV
            );

            throw new AssertionError("Truck with BEV should be rejected");

        } catch (IllegalArgumentException e) {
            System.out.println("Correctly rejected: " + e.getMessage());
        }
    }

    private static void testInvalidState() {

        System.out.println("\n--- Invalid State ---");

        Vehicle car = VehicleFactory.create(
                "C003",
                "KA01AA6666",
                "Toyota",
                "Camry",
                VehicleType.CAR,
                PowertrainType.PETROL
        );

        try {
            car.drive();
            throw new AssertionError("Inactive vehicle should not drive");

        } catch (IllegalStateException e) {
            System.out.println("Correctly rejected drive(): " + e.getMessage());
        }

        car.start();

        try {

            car.start();
            throw new AssertionError("Active vehicle should not start again");
        } catch (IllegalStateException e) {
            System.out.println("Correctly rejected second start(): " + e.getMessage());
        }

        car.stop();
    }

    private static void testUnsupportedCapability() {
        System.out.println("\n--- Unsupported Capability ---");

        Vehicle bev = VehicleFactory.create(
                "C004",
                "KA01EV7777",
                "Tesla",
                "Model 3",
                VehicleType.CAR,
                PowertrainType.BEV
        );

        try {
            bev.refuel();
            throw new AssertionError("BEV should not support refueling");
        } catch (UnsupportedOperationException e) {
            System.out.println("Correctly rejected refuel(): " + e.getMessage());
        }
    }
}
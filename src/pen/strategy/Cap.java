package pen.strategy;

public class Cap implements OpeningMechanism {

    @Override
    public void open() {
        System.out.println("Cap removed");
    }

    @Override
    public void close() {
        System.out.println("Cap placed back");
    }
}
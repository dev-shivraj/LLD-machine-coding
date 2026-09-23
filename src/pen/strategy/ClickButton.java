package pen.strategy;

public class ClickButton implements OpeningMechanism {

    @Override
    public void open() {
        System.out.println("Button clicked: pen opened");
    }

    @Override
    public void close() {
        System.out.println("Button clicked: pen closed");
    }
}
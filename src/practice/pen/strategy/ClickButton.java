package practice.pen.strategy;

public class ClickButton implements OpeningMechanism {

    @Override
    public void open() {
        System.out.println("Button clicked: practice.tic_tac_toe.pen opened");
    }

    @Override
    public void close() {
        System.out.println("Button clicked: practice.tic_tac_toe.pen closed");
    }
}
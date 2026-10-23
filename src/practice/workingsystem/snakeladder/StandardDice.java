package practice.workingsystem.snakeladder;

import java.util.Random;

public class StandardDice implements Dice {
    private final Random random;

    public StandardDice() {
        this.random = new Random();
    }

    @Override
    public int roll() {
        return random.nextInt(6) + 1;
    }
}

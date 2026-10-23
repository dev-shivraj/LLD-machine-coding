package practice.workingsystem.snakeladder;

import java.util.List;

public class FixedDice implements Dice {
    private final List<Integer> rolls;
    private int currentIndex;

    public FixedDice(List<Integer> rolls) {
        if (rolls == null || rolls.isEmpty()) {
            throw new IllegalArgumentException("Dice rolls cannot be empty");
        }

        for (Integer roll : rolls) {
            if (roll == null || roll < 1 || roll > 6) {
                throw new IllegalArgumentException("Each dice roll must be between 1 and 6");
            }
        }

        this.rolls = List.copyOf(rolls);
    }

    @Override
    public int roll() {
        if (currentIndex >= rolls.size()) {
            throw new IllegalArgumentException("No predetermined dice rolls remain");
        }

        return rolls.get(currentIndex++);
    }
}

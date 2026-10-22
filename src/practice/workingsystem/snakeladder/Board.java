package practice.workingsystem.snakeladder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final int size;
    private final Map<Integer, Snake> snakes;
    private final Map<Integer, Ladder> ladders;

    public Board(int size, List<Snake> snakes, List<Ladder> ladders) {
        if(size <= 0) {
            throw new IllegalArgumentException("Board size must be positive");
        }

        this.size = size;
        this.snakes = new HashMap<>();
        this.ladders = new HashMap<>();

        configureSnakes(snakes);
        configureLadders(ladders);
        validateConfiguration();

    }

    private void configureSnakes(List<Snake> snakes) {
        for(Snake snake : snakes) {
            validatePosition(snake.getHead());
            validatePosition(snake.getTail());

            if(this.snakes.containsKey(snake.getHead())) {
                throw new IllegalArgumentException("Duplicate snake head: " + snake.getHead());
            }

            this.snakes.put(snake.getHead(), snake);
        }
    }

    private void configureLadders(List<Ladder> ladders) {
        for(Ladder ladder : ladders) {
            validatePosition(ladder.getBottom());
            validatePosition(ladder.getTop());


            if(this.ladders.containsKey(ladder.getBottom())) {
                throw new IllegalArgumentException("Duplicate ladder bottom: " + ladder.getBottom());
            }

            this.ladders.put(ladder.getBottom(), ladder);
        }
    }

    private void validatePosition(int position) {
        if(position < 1 || position > size) {
            throw new IllegalArgumentException("Position must be between 1 and " + size);
        }
    }

    private void validateConfiguration() {
        for(int head : snakes.keySet()) {
            if(ladders.containsKey(head)) {
                throw new IllegalArgumentException("A position cannot be boh snake head" + " and a ladder bottom: " + head);
            }
        }
    }

    public int resolvePosition(int position) {
        validatePosition(position);

        Snake snake = snakes.get(position);

        if(snake != null) {
            return snake.getTail();
        }

        Ladder ladder = ladders.get(position);

        if(ladder != null) {
            return  ladder.getTop();
        }

        return position;
    }

    public int getSize() {
        return size;
    }
}

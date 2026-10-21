package practice.workingsystem.tic_tac_toe.model.strategy.bot;

import practice.workingsystem.tic_tac_toe.model.board.Board;
import practice.workingsystem.tic_tac_toe.model.move.Position;

import java.util.List;

public class EasyBotStrategy implements BotStrategy {

    @Override
    public Position chooseMove(BotContext context) {

        Board board = context.board();
        List<Position> availablePositions = board.getAvailablePositions();

        if (availablePositions.isEmpty()) {
            throw new IllegalStateException("No available moves");
        }

        int center = board.getSize() / 2;
        Position centerPosition = new Position(center, center);

        if (board.isEmpty(centerPosition)) {
            return centerPosition;
        }

        return BotStrategyHelper.chooseRandom(availablePositions);
    }
}
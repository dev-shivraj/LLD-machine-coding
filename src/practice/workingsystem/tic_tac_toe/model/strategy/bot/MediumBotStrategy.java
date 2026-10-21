package practice.workingsystem.tic_tac_toe.model.strategy.bot;

import practice.workingsystem.tic_tac_toe.model.move.Position;
import practice.workingsystem.tic_tac_toe.model.player.Player;

import java.util.List;

public class MediumBotStrategy implements BotStrategy {

    @Override
    public Position chooseMove(BotContext context) {
        List<Position> availablePositions = context.board().getAvailablePositions();

        if (availablePositions.isEmpty()) {
            throw new IllegalStateException("No available moves");
        }

        // Priority 1: Win immediately if possible.
        Position winningMove = BotStrategyHelper.findImmediateWinningMove(context.board(), context.botPlayer(), context.winningStrategies());

        if (winningMove != null) {
            return winningMove;
        }

        // Priority 2: Block an opponent's immediate win.
        for (Player opponent : context.players()) {
            if (opponent == context.botPlayer()) {
                continue;
            }

            Position blockingMove = BotStrategyHelper.findImmediateWinningMove(context.board(), opponent, context.winningStrategies());

            if (blockingMove != null) {
                return blockingMove;
            }
        }

        // Priority 3: Choose randomly.
        return BotStrategyHelper.chooseRandom(availablePositions);
    }
}
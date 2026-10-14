package practice.tic_tac_toe.model.strategy.bot;


import practice.tic_tac_toe.model.move.Position;

import java.util.List;

public class RandomBotStrategy implements BotStrategy {

    @Override
    public Position chooseMove(BotContext context) {
        List<Position> availablePositions = context.board().getAvailablePositions();

        return BotStrategyHelper.chooseRandom(availablePositions);

    }

}
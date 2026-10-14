package practice.tic_tac_toe.model.strategy.bot;

import practice.tic_tac_toe.model.move.Position;

public interface BotStrategy {

    Position chooseMove(BotContext context);
}
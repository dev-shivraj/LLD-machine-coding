package tic_tac_toe.model.strategy.bot;

import tic_tac_toe.model.move.Position;

public interface BotStrategy {

    Position chooseMove(BotContext context);
}
package practice.workingsystem.tic_tac_toe.model.strategy.bot;

import practice.workingsystem.tic_tac_toe.model.move.Position;

public interface BotStrategy {

    Position chooseMove(BotContext context);
}
package tic_tac_toe.model.strategy.bot;

import tic_tac_toe.model.enums.Position;
import tic_tac_toe.model.player.BotPlayer;

public interface BotStrategy {
    Position chooseMove(Board board, BotPlayer botPlayer);
}

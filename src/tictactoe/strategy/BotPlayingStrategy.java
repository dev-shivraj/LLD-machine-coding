package tictactoe.strategy;

import tictactoe.model.Board;
import tictactoe.model.Move;

public interface BotPlayingStrategy {
    Move makeMove(Board board);
}

package tictactoe.strategy;

import tictactoe.model.Board;
import tictactoe.model.Move;
import tictactoe.model.Player;

public interface WinningStrategy {
    boolean checkWinner(Board board, Move move);
}

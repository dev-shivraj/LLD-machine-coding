package tic_tac_toe.model.strategy.winning;

import tic_tac_toe.model.board.Board;
import tic_tac_toe.model.player.Player;

import java.util.List;

public interface WinningStrategy {
    List<WinningLine> findWinningLines(Board board, Player player);
}

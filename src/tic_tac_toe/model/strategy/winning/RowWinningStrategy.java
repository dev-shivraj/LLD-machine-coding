package tic_tac_toe.model.strategy.winning;

import tic_tac_toe.model.enums.WinningConditionType;
import tic_tac_toe.model.move.Position;
import tic_tac_toe.model.board.Board;
import tic_tac_toe.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class RowWinningStrategy implements WinningStrategy{
    @Override
    public List<WinningLine> findWinningLines(Board board, Player player) {
        List<WinningLine> winningLines = new ArrayList<>();
        int size = board.getSize();

        for(int row = 0; row < size; ++row) {
            List<Position> positions = new ArrayList<>();
            boolean isWinningRow = true;

            for(int col = 0; col < size; ++col) {
                Position position   = new Position(row, col);

                if(board.getPlayer(position) != player) {
                    isWinningRow = false;
                    break;
                }

                positions.add(position);
            }

            if(isWinningRow) {
                winningLines.add(new WinningLine(WinningConditionType.ROW, positions));
            }
        }

        return winningLines;
    }
}

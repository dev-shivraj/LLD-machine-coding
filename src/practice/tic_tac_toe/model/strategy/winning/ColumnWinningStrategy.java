package practice.tic_tac_toe.model.strategy.winning;


import practice.tic_tac_toe.model.board.Board;
import practice.tic_tac_toe.model.enums.WinningConditionType;
import practice.tic_tac_toe.model.move.Position;
import practice.tic_tac_toe.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ColumnWinningStrategy implements WinningStrategy {

    @Override
    public List<WinningLine> findWinningLines(
            Board board,
            Player player) {

        List<WinningLine> winningLines = new ArrayList<>();
        int size = board.getSize();

        for (int column = 0; column < size; column++) {
            List<Position> positions = new ArrayList<>();
            boolean isWinningColumn = true;

            for (int row = 0; row < size; row++) {
                Position position = new Position(row, column);

                if (board.getPlayer(position) != player) {
                    isWinningColumn = false;
                    break;
                }

                positions.add(position);
            }

            if (isWinningColumn) {
                winningLines.add(new WinningLine(WinningConditionType.COLUMN, positions));
            }
        }

        return winningLines;
    }
}
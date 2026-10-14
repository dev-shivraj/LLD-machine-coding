package practice.tic_tac_toe.model.strategy.winning;

import practice.tic_tac_toe.model.board.Board;
import practice.tic_tac_toe.model.enums.WinningConditionType;
import practice.tic_tac_toe.model.move.Position;
import practice.tic_tac_toe.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class DiagonalWinningStrategy implements WinningStrategy{
    @Override
    public List<WinningLine> findWinningLines(Board board, Player player) {
        List<Position> positions = new ArrayList<>();
        int size = board.getSize();

        for(int i = 0; i < size; ++i) {
            Position position = new Position(i, i);

            if(board.getPlayer(position) != player) {
                return List.of();
            }

            positions.add(position);
        }

        return List.of(new WinningLine(WinningConditionType.DIAGONAL, positions));
    }
}

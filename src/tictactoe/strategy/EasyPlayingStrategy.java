package tictactoe.strategy;

import tictactoe.model.Board;
import tictactoe.model.Cell;
import tictactoe.model.Move;
import tictactoe.model.enums.CellState;

public class EasyPlayingStrategy implements BotPlayingStrategy{

    @Override
    public Move makeMove(Board board) {
        for(int row = 0; row < board.getSize(); ++row) {
            for(int col = 0; col < board.getSize(); ++col) {
                if(board.getCells().get(row).get(col).getCellState().equals(CellState.EMPTY))
                        return new Move(null, new Cell(row, col));
            }
        }

        return null;
    }
}

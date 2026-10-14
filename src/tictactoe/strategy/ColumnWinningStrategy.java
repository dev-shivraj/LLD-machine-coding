package tictactoe.strategy;

import tictactoe.model.Board;
import tictactoe.model.Move;
import tictactoe.model.Player;

import java.util.HashMap;

public class ColumnWinningStrategy implements WinningStrategy {
    private HashMap<String, Integer> colMaps[];

    public ColumnWinningStrategy() {
        this.colMaps = new HashMap[10_000];
        for(int i = 0; i < 10_000; ++i) {
            colMaps[i] = new HashMap<>();
        }
    }

    @Override
    public boolean checkWinner(Board board, Move move) {
        int currCol = move.getCell().getCol();
        HashMap<String, Integer> currColMap = colMaps[currCol];
        String currSymbol = move.getPlayer().getSymbol().getName();

        currColMap.put(currSymbol, currColMap.getOrDefault(currSymbol, 0) + 1);

        return currColMap.get(currSymbol) == board.getSize();
    }
}

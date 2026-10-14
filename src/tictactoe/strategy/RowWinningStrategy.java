package tictactoe.strategy;

import tictactoe.model.Board;
import tictactoe.model.Move;
import tictactoe.model.Player;
import tictactoe.model.Symbol;

import java.util.HashMap;

public class RowWinningStrategy implements WinningStrategy {
    private HashMap<String, Integer> rowMaps[];

    public RowWinningStrategy() {
        this.rowMaps = new HashMap[10_000]; // very large value after initially
        for(int i = 0; i < 10_000; ++i) {
            rowMaps[i] = new HashMap<>();
        }
    }

    @Override
    public boolean checkWinner(Board board, Move move) {
        // step 1 : get the current player
        Player currPlayer = move.getPlayer();

        // step 2 :
        int currRow = move.getCell().getRow();
        HashMap<String, Integer> currRowMap = rowMaps[currRow];
        String currSymbol = move.getPlayer().getSymbol().getName();

        if(!currRowMap.containsKey(currSymbol)) {
            currRowMap.put(currSymbol, 0);
        }

        currRowMap.put(currSymbol, currRowMap.get(currSymbol) + 1);

        return currRowMap.get(currSymbol) == board.getSize();
    }
}

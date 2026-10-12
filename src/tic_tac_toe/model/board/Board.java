package tic_tac_toe.model.board;

import tic_tac_toe.model.move.Position;
import tic_tac_toe.model.player.Player;

public class Board {
    private final int size;
    private final Player cells[][];

    public Board(int size) {
        if(size < 3) {
            throw new IllegalArgumentException("Board size must be at least 3");
        }

        this.size = size;
        this.cells = new Player[size][size];
    }

    public int getSize() {
        return size;
    }

    public boolean isValidPosition(Position position) {
        if(position == null) {
            return false;
        }

        int row = position.getRow();
        int col = position.getCol();

        return row >= 0 && row < size &&
                col >= 0 && col < size;
    }

    public boolean isEmpty(Position position) {
        if(!isValidPosition(position)) {
            return false;
        }

        return cells[position.getRow()][position.getCol()] == null;
    }

    public void place(Player player, Position position) {
        if(player == null) {
            throw new IllegalArgumentException("Player can not be null");
        }

        if(!isValidPosition(position)) {
            throw new IllegalArgumentException("Invalid board position");
        }

        if(!isEmpty(position)) {
            throw new IllegalArgumentException("Position is already occupied");
        }

        cells[position.getRow()][position.getCol()] = player;
    }

    public Player getPlayer(Position position) {
        if(!isValidPosition(position)) {
            throw new IllegalArgumentException("Invalid board position");
        }

        return cells[position.getRow()][position.getCol()];
    }

    public boolean isFull() {
        for(int row = 0; row < size; ++row) {
            for(int col = 0; col < size; ++col) {
                if(cells[row][col] == null) {
                    return false;
                }
            }
        }

        return true;
    }
}

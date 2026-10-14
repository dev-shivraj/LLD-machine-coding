package tictactoe.model;

import tictactoe.model.enums.CellState;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int size;
    private List<List<Cell>> cells;

    public Board(int size) {
        this.size = size;

        this.cells = new ArrayList<>();
        for(int i = 0; i < size; ++i) {

            this.cells.add(new ArrayList<>());
            for(int j = 0; j < size; ++j) {
                this.cells.get(i).add(new Cell(i, j));
            }
        }
    }

    public void display() {
        // System.out.println("board size : " + size);
        for(int i = 0; i < size; ++i) {
            for(int j = 0; j < size; ++j) {
                this.cells.get(i).get(j).display();
            }
            System.out.println();
        }
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<List<Cell>> getCells() {
        return cells;
    }

    public void setCells(List<List<Cell>> cells) {
        this.cells = cells;
    }
}

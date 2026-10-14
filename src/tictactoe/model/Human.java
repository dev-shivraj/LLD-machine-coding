package tictactoe.model;

import tictactoe.model.enums.PlayerType;

import java.util.Scanner;

public class Human extends Player{
    private int age;
    private Scanner scanner = new Scanner(System.in);

    public Human(int id, String name, Symbol symbol, int age) {
        super(id, name, symbol, PlayerType.HUMAN);
        this.age = age;
    }

    @Override
    public Move makeMove(Game game) {
        System.out.println("Please enter row and column to make a move");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        validateMove(row, col);
        return new Move(this, new Cell(row, col));
    }

    private void validateMove(int row, int col) {
        // FILLED
        // OUT OF BOUND OF GRID
        // TODO
    }
}

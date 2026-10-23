package practice.workingsystem.snakeladder.client;

import practice.workingsystem.snakeladder.*;

import java.util.List;

public class Client {

    public static void main(String[] args) {
        List<Snake> snakes = List.of(
                new Snake(16, 6),
                new Snake(47, 26),
                new Snake(64, 60),
                new Snake(93, 73)
        );

        List<Ladder> ladders = List.of(
                new Ladder(3, 22),
                new Ladder(8, 30),
                new Ladder(20, 41),
                new Ladder(63, 81)
        );

        Board board = new Board(100, snakes, ladders);

        List<Player> players = List.of(
                new Player("P1", "Alice"),
                new Player("P2", "Bob")
        );

        Dice dice = new StandardDice();

        Game game = new Game(board, players, dice);

        System.out.println("Starting Snake and Ladder!");

        game.start();

        System.out.println("\nGame Over!");

        if (game.getWinner() != null) {
            System.out.println("Winner: " + game.getWinner().getName());
        }
    }
}
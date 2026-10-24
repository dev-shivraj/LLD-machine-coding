package practice.workingsystem.snakeladder.client;

import practice.workingsystem.snakeladder.*;

import java.util.List;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        // Create board, snakes, and ladders as before.
        Board board = createBoard();
        List<Player> players = List.of(
                new Player("P1", "Alice"),
                new Player("P2", "Bob")
        );

        Dice dice = new StandardDice();
        Game game = new Game(board, players, dice);
        Scanner scanner = new Scanner(System.in);
        game.start();

        while (game.gameStatus() == GameStatus.IN_PROGRESS) {
            Player currentPlayer = game.getCurrentPlayer();
            System.out.println("\n" + currentPlayer.getName() + "'s turn. Press Enter to roll the dice.");

            scanner.nextLine();
            game.playTurn();
        }

        System.out.println("\nGame Over!");
        System.out.println("Winner: " + game.getWinner().getName());

        scanner.close();
    }

    private static Board createBoard() {
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

        return new Board(100, snakes, ladders);
    }

}
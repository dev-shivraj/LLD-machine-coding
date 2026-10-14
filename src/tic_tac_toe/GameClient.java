package tic_tac_toe;

import tic_tac_toe.model.Game;
import tic_tac_toe.model.config.GameConfig;
import tic_tac_toe.model.move.Position;
import tic_tac_toe.model.player.HumanPlayer;
import tic_tac_toe.model.player.Player;
import tic_tac_toe.model.strategy.turn.RegistrationOrderStrategy;
import tic_tac_toe.model.strategy.winning.*;

import java.util.List;
import java.util.Set;

public class GameClient {
    public static void main(String[] args) {

        Player playerX = new HumanPlayer("P1", "Player X", 'X', null);
        Player playerO = new HumanPlayer("P2", "Player O", 'O', null);

        Set<WinningStrategy> winningStrategies = Set.of(new RowWinningStrategy(), new ColumnWinningStrategy(), new DiagonalWinningStrategy(), new AntiDiagonalWinningStrategy());
        GameConfig config = new GameConfig(3, List.of(playerX, playerO), new RegistrationOrderStrategy(), winningStrategies);
        Game game = new Game(config);

        demonstrateGameStart(game);
        demonstrateTurnProgression(game, playerX, playerO);
        demonstrateUndo(game, playerX, playerO);
        demonstrateWin(game, playerX, playerO);
        demonstrateInvalidMove(game);
    }

    private static void demonstrateGameStart(Game game) {
        System.out.println("\n=== 1. Start Game ===");
        game.start();

        System.out.println("Game state: " + game.getResult().getState());
        System.out.println("Current player: " + game.getCurrentPlayer().getName());
    }

    private static void demonstrateTurnProgression(Game game, Player playerX, Player playerO) {
        System.out.println("\n=== 2. Turn Progression ===");

        game.makeMove(new Position(0, 0));

        System.out.println("Player X plays (1, 1)");
        System.out.println("Expected next player: " + playerO.getName());
        System.out.println("Actual next player: " + game.getCurrentPlayer().getName());

        game.makeMove(new Position(1, 1));

        System.out.println("Player O plays (2, 2)");
        System.out.println("Expected next player: " + playerX.getName());
        System.out.println("Actual next player: " + game.getCurrentPlayer().getName());
    }

    private static void demonstrateUndo(Game game, Player playerX, Player playerO) {
        System.out.println("\n=== 3. Undo ===");

        System.out.println("Moves before undo: " + game.getMoveHistory().size());

        boolean undone = game.undo();

        System.out.println("Undo successful: " + undone);
        System.out.println("Moves after undo: " + game.getMoveHistory().size());
        System.out.println("Current player: " + game.getCurrentPlayer().getName());
        System.out.println("Expected current player: " + playerO.getName());
    }

    private static void demonstrateWin(Game game, Player playerX, Player playerO) {
        System.out.println("\n=== 4. Winning Scenario ===");

        // The existing history contains X at (0, 0).
        // O's previous move at (1, 1) was undone.
        // O plays (1, 0), then X and O continue.

        game.makeMove(new Position(1, 0)); // O
        game.makeMove(new Position(0, 1)); // X
        game.makeMove(new Position(1, 1)); // O
        game.makeMove(new Position(0, 2)); // X wins

        System.out.println("Game state: " + game.getResult().getState());

        game.getResult().getWinner().ifPresent(winner -> System.out.println("Winner: " + winner.getName()));

        System.out.println("Winning lines: " + game.getResult().getWinningLines());
        System.out.println("Expected winner: " + playerX.getName());
    }

    private static void demonstrateInvalidMove(Game game) {
        System.out.println("\n=== 5. Invalid Move After Game Ends ===");

        try {
            game.makeMove(new Position(2, 2));
        } catch (IllegalStateException exception) {
            System.out.println("Move rejected correctly: " + exception.getMessage());
        }

        System.out.println("Final game state: " + game.getResult().getState());
    }
}

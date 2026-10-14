package practice.tic_tac_toe.view;

import practice.tic_tac_toe.model.Game;
import practice.tic_tac_toe.model.board.Board;
import practice.tic_tac_toe.model.enums.GameState;
import practice.tic_tac_toe.model.move.Position;
import practice.tic_tac_toe.model.player.Player;
import practice.tic_tac_toe.model.result.GameResult;
import practice.tic_tac_toe.model.strategy.winning.WinningLine;

import java.util.List;

public class ConsoleView {
    public void displayWelcome() {
        System.out.println();
        System.out.println("================================");
        System.out.println("       TIC TAC TOE");
        System.out.println("================================");
    }

    public void displayCommands() {
        System.out.println();
        System.out.println("Available commands:");
        System.out.println("MOVE <row> <column> - Play a move");
        System.out.println("UNDO                - Undo the previous move");
        System.out.println("STATUS              - Display game status");
        System.out.println("QUIT                - Exit the game");
        System.out.println();
        System.out.println("Rows and columns start from 1.");
    }

    public void displayBoard(Game game) {
        displayBoard(game.getBoard());
    }

    public void displayBoard(Board board) {
        int size = board.getSize();
        System.out.println();

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                Player player = board.getPlayer(new Position(row, column));
                String symbol = player == null ? " " : String.valueOf(player.getSymbol());
                System.out.print(" " + symbol + " ");

                if (column < size - 1) {
                    System.out.print("|");
                }
            }

            System.out.println();

            if (row < size - 1) {
                System.out.println("---+".repeat(size - 1) + "---");
            }
        }

        System.out.println();
    }

    public void displayCurrentPlayer(Game game) {
        if (game.getResult().getState() != GameState.IN_PROGRESS) {
            return;
        }

        Player player = game.getCurrentPlayer();
        System.out.println("Current player: " + player.getName() + " (" + player.getSymbol() + ")");
    }

    public void displayStatus(Game game) {
        GameResult result = game.getResult();
        System.out.println();
        System.out.println("Game status: " + result.getState());
        System.out.println("Moves played: " + game.getMoveHistory().size());

        if (result.getState() == GameState.IN_PROGRESS) {
            displayCurrentPlayer(game);
        }

        if (result.getState() == GameState.WON) {
            result.getWinner().ifPresent(winner -> System.out.println("Winner: " + winner.getName() + " (" + winner.getSymbol() + ")"));
            displayWinningLines(result.getWinningLines());
        } else if (result.getState() == GameState.DRAW) {
            System.out.println("The game ended in a draw.");
        }

        System.out.println();
    }

    public void displayResult(GameResult result) {
        System.out.println();

        switch (result.getState()) {
            case NOT_STARTED -> System.out.println("The game has not started.");
            case IN_PROGRESS -> System.out.println("The game is in progress.");
            case WON -> {
                result.getWinner().ifPresent(winner -> System.out.println(winner.getName() + " wins!"));
                displayWinningLines(result.getWinningLines());
            }
            case DRAW -> System.out.println("It's a draw!");
        }

        System.out.println();
    }

    private void displayWinningLines(List<WinningLine> winningLines) {
        if (winningLines.isEmpty()) {
            return;
        }

        System.out.println("Winning lines:");
        for (WinningLine line : winningLines) {
            System.out.println("  " + line.getType() + ": " + line.getPositions());
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayError(String message) {
        System.out.println("Error: " + message);
    }
}
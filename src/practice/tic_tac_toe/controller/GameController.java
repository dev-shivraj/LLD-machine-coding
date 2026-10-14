package practice.tic_tac_toe.controller;

import practice.tic_tac_toe.model.Game;
import practice.tic_tac_toe.model.enums.GameState;
import practice.tic_tac_toe.model.move.Move;
import practice.tic_tac_toe.model.move.Position;
import practice.tic_tac_toe.model.player.BotPlayer;
import practice.tic_tac_toe.model.player.HumanPlayer;
import practice.tic_tac_toe.model.player.Player;
import practice.tic_tac_toe.model.strategy.bot.BotContext;
import practice.tic_tac_toe.model.strategy.winning.WinningStrategy;
import practice.tic_tac_toe.view.ConsoleView;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class GameController {
    private final Game game;
    private final ConsoleView view;
    private final Scanner scanner;
    private boolean running;

    public GameController(Game game, ConsoleView view, Scanner scanner) {
        if (game == null || view == null || scanner == null) {
            throw new IllegalArgumentException("Game, view, and scanner cannot be null");
        }

        this.game = game;
        this.view = view;
        this.scanner = scanner;
    }

    public void run() {
        game.start();
        running = true;
        view.displayWelcome();
        view.displayCommands();

        while (running) {
            view.displayBoard(game);
            view.displayStatus(game);

            if (game.getResult().getState() == GameState.IN_PROGRESS && playBotTurnIfNecessary()) {
                continue;
            }

            if (!running) {
                break;
            }

            view.displayMessage("Enter command:");

            if (!scanner.hasNextLine()) {
                running = false;
                break;
            }

            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                view.displayError("Command cannot be empty.");
                continue;
            }

            processCommand(input);
        }

        view.displayMessage("Exiting Tic Tac Toe.");
    }

    private void processCommand(String input) {
        String[] parts = input.trim().split("\\s+");
        String command = parts[0].toUpperCase();

        try {
            switch (command) {
                case "MOVE" -> handleMove(parts);
                case "UNDO" -> handleUndo(parts);
                case "STATUS" -> handleStatus(parts);
                case "QUIT" -> handleQuit(parts);
                default -> view.displayError("Unknown command. Enter MOVE, UNDO, STATUS, or QUIT.");
            }

        } catch (IllegalArgumentException | IllegalStateException exception) {
            view.displayError(exception.getMessage());
        }
    }

    private void handleMove(String[] parts) {

        if (parts.length != 3) {
            throw new IllegalArgumentException("Usage: MOVE <row> <column>");
        }

        if (game.getResult().getState() != GameState.IN_PROGRESS) {
            throw new IllegalStateException("The game has ended. Use UNDO or QUIT.");
        }

        Player currentPlayer = game.getCurrentPlayer();

        if (!(currentPlayer instanceof HumanPlayer)) {
            throw new IllegalStateException("It is not a human player's turn.");
        }

        int row = parseCoordinate(parts[1]);
        int column = parseCoordinate(parts[2]);

        Position position = new Position(row - 1, column - 1);

        game.makeMove(position);
        view.displayMessage(currentPlayer.getName() + " played at (" + row + ", " + column + ").");
        view.displayBoard(game);
        view.displayResult(game.getResult());

        if (game.getResult().getState() == GameState.IN_PROGRESS) {
            playBotTurnIfNecessary();
        }
    }

    private int parseCoordinate(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Row and column must be integers.");
        }
    }

    private boolean playBotTurnIfNecessary() {
        if (game.getResult().getState() != GameState.IN_PROGRESS) {
            return false;
        }

        Player currentPlayer = game.getCurrentPlayer();

        if (!(currentPlayer instanceof BotPlayer botPlayer)) {
            return false;
        }

        view.displayMessage(botPlayer.getName() + " is thinking...");
        Set<WinningStrategy> winningStrategies = getWinningStrategies();
        BotContext context = new BotContext(game.getBoard(), botPlayer, game.getTurnOrder(), winningStrategies);
        Position position = botPlayer.getBotStrategy().chooseMove(context);

        if (position == null) {
            throw new IllegalStateException("Bot strategy returned a null position.");
        }

        game.makeMove(position);

        view.displayMessage(
                botPlayer.getName()
                        + " played at ("
                        + (position.getRow() + 1)
                        + ", "
                        + (position.getCol() + 1)
                        + ")."
        );

        view.displayBoard(game);
        view.displayResult(game.getResult());
        return true;
    }

    private Set<WinningStrategy> getWinningStrategies() {
        return game.getWinningStrategies();
    }

    private void handleUndo(String[] parts) {
        if (parts.length != 1) {
            throw new IllegalArgumentException("Usage: UNDO");
        }

        if (game.getResult().getState() == GameState.NOT_STARTED) {
            throw new IllegalStateException("Cannot undo before the game starts.");
        }

        List<Move> history = game.getMoveHistory();

        if (history.isEmpty()) {
            view.displayMessage("There are no moves to undo.");
            return;
        }

        boolean humanVsBot = isHumanVsBotGame();
        Move lastMove = history.get(history.size() - 1);

        if (!game.undo()) {
            view.displayMessage("There are no moves to undo.");
            return;
        }

        /*
         * In a human-vs-bot game, undoing a bot response should also
         * undo the human move that triggered that response.
         *
         * Example:
         * Human -> Bot -> Human -> Bot
         *
         * Undo:
         * Human -> Bot
         */

        if (humanVsBot && lastMove.getPlayer() instanceof BotPlayer) {
            List<Move> remainingHistory = game.getMoveHistory();

            if (!remainingHistory.isEmpty()) {
                Move previousMove = remainingHistory.get(remainingHistory.size() - 1);

                if (previousMove.getPlayer() instanceof HumanPlayer) {
                    game.undo();
                }
            }
        }

        view.displayMessage("Undo completed.");
        view.displayBoard(game);
        view.displayResult(game.getResult());
    }

    private boolean isHumanVsBotGame() {
        List<Player> players = game.getTurnOrder();

        if (players.size() != 2) {
            return false;
        }

        long humanCount = players.stream().filter(HumanPlayer.class::isInstance).count();
        long botCount = players.stream().filter(BotPlayer.class::isInstance).count();

        return humanCount == 1 && botCount == 1;
    }

    private void handleStatus(String[] parts) {
        if (parts.length != 1) {
            throw new IllegalArgumentException("Usage: STATUS");
        }

        view.displayBoard(game);
        view.displayStatus(game);
    }

    private void handleQuit(String[] parts) {
        if (parts.length != 1) {
            throw new IllegalArgumentException("Usage: QUIT");
        }

        running = false;
    }
}
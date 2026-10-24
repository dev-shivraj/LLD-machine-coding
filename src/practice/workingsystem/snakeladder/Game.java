package practice.workingsystem.snakeladder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    private final Board board;
    private final List<Player> players;
    private final Dice dice;

    private int currentPlayerIndex;
    private GameStatus gameStatus;
    private Player winner;

    public Game(Board board, List<Player> players, Dice dice) {
        if (board == null) {
            throw new IllegalArgumentException("Board cannot be null");
        }

        if (players == null || players.size() < 2) {
            throw new IllegalArgumentException("At least two players are required");
        }

        if (dice == null) {
            throw new IllegalArgumentException("Dice cannot be null");
        }

        Set<String> playerIds = new HashSet<>();
        for (Player player : players) {
            if (player == null) {
                throw new IllegalArgumentException("Player cannot be null");
            }

            if (!playerIds.add(player.getId())) {
                throw new IllegalArgumentException("Player IDs must be unique");
            }
        }

        this.board = board;
        this.players = players;
        this.dice = dice;

        this.currentPlayerIndex = 0;
        this.gameStatus = GameStatus.IN_PROGRESS;
        this.winner = null;
    }

    public void start() {
//        while (gameStatus == GameStatus.IN_PROGRESS) {
//            playTurn();
//        }

        System.out.println("Snake and Ladder game started!");
    }

    public void playTurn() {
        if (gameStatus == GameStatus.FINISHED) {
            throw new IllegalArgumentException("The game has already finished");
        }

        Player currentPlayer = getCurrentPlayer();

        int diceValue = dice.roll();
        int currentPosition = currentPlayer.getPosition();
        int targetPosition = currentPosition + diceValue;

        System.out.println(currentPlayer.getName() + " rolled " + diceValue);

        if (targetPosition > board.getSize()) {
            System.out.println(currentPlayer.getName() + " cannot move because the roll overshoots " + board.getSize());

            advanceTurn();
            return;
        }

        int resolvedPosition = board.resolvePosition(targetPosition);
        currentPlayer.moveTo(resolvedPosition);
        System.out.println(currentPlayer.getName() + " moved from " + currentPosition + " to " + resolvedPosition);

        if (resolvedPosition == board.getSize()) {
            winner = currentPlayer;
            gameStatus = GameStatus.FINISHED;
            System.out.println(currentPlayer.getName() + " wins!");
            return;
        }

        advanceTurn();
    }

    public void advanceTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public GameStatus gameStatus() {
        return gameStatus;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
}

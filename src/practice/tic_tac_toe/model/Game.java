package practice.tic_tac_toe.model;

import practice.tic_tac_toe.model.board.Board;
import practice.tic_tac_toe.model.config.GameConfig;
import practice.tic_tac_toe.model.enums.GameState;
import practice.tic_tac_toe.model.move.Move;
import practice.tic_tac_toe.model.move.Position;
import practice.tic_tac_toe.model.player.Player;
import practice.tic_tac_toe.model.result.GameResult;
import practice.tic_tac_toe.model.strategy.turn.TurnOrderStrategy;
import practice.tic_tac_toe.model.strategy.winning.WinningLine;
import practice.tic_tac_toe.model.strategy.winning.WinningStrategy;

import java.util.*;

public class Game {
    private final Board board;
    private final List<Player> players;
    private final TurnOrderStrategy turnOrderStrategy;
    private final Set<WinningStrategy> winningStrategies;
    private final List<Move> moveHistory;
    private final Deque<Move> undoHistory;
    private List<Player> turnOrder;
    private int currentPlayerIndex;
    private GameResult result;

    public Game(GameConfig config) {
        Objects.requireNonNull(config, "Game config cannot be null");

        this.board = new Board(config.getBoardSize());
        this.players = config.getPlayers();
        this.turnOrderStrategy = config.getTurnOrderStrategy();
        this.winningStrategies = config.getWinningStrategies();
        this.moveHistory = new ArrayList<>();
        this.undoHistory = new ArrayDeque<>();
        this.turnOrder = List.of();
        this.currentPlayerIndex = 0;
        this.result = new GameResult(GameState.NOT_STARTED, null, List.of());
    }

    public void start() {

        if (result.getState() != GameState.NOT_STARTED) {
            throw new IllegalStateException("Game has already been started");
        }

        turnOrder = turnOrderStrategy.determineOrder(players);

        if (turnOrder.size() != players.size() || new HashSet<>(turnOrder).size() != players.size() || !new HashSet<>(turnOrder).equals(new HashSet<>(players))) {
            throw new IllegalStateException("Turn order strategy must return every player exactly once");
        }

        currentPlayerIndex = 0;
        result = new GameResult(GameState.IN_PROGRESS, null, List.of());
    }

    public void makeMove(Position position) {

        if (result.getState() != GameState.IN_PROGRESS) {
            throw new IllegalStateException("Moves are allowed only while the game is in progress");
        }

        Objects.requireNonNull(position, "Position cannot be null");
        Player currentPlayer = getCurrentPlayer();

        // Board.place validates the position and rejects occupied cells.
        board.place(currentPlayer, position);
        Move move = new Move(currentPlayer, position);
        moveHistory.add(move);
        undoHistory.push(move);
        List<WinningLine> winningLines = findWinningLines(currentPlayer);

        if (!winningLines.isEmpty()) {
            result = new GameResult(GameState.WON, currentPlayer, winningLines);
            return;
        }

        if (board.isFull()) {
            result = new GameResult(GameState.DRAW, null, List.of());
            return;
        }

        currentPlayerIndex = (currentPlayerIndex + 1) % turnOrder.size();
    }

    private List<WinningLine> findWinningLines(Player player) {
        List<WinningLine> winningLines = new ArrayList<>();

        for (WinningStrategy strategy : winningStrategies) {
            winningLines.addAll(strategy.findWinningLines(board, player));
        }

        return List.copyOf(winningLines);
    }

    public boolean undo() {
        if (result.getState() == GameState.NOT_STARTED) {
            throw new IllegalStateException("Cannot undo before the game starts");
        }

        if (undoHistory.isEmpty()) {
            return false;
        }

        Move lastMove = undoHistory.pop();
        moveHistory.remove(moveHistory.size() - 1);
        rebuildBoard();

        result = new GameResult(GameState.IN_PROGRESS, null, List.of());
        currentPlayerIndex = turnOrder.indexOf(lastMove.getPlayer());
        return true;
    }

    private void rebuildBoard() {
        board.clear();
        for (Move move : moveHistory) {
            board.place(move.getPlayer(), move.getPosition());
        }
    }

    private int findNextPlayerIndex(Player lastPlayer) {
        int lastPlayerIndex = turnOrder.indexOf(lastPlayer);
        return (lastPlayerIndex + 1) % turnOrder.size();
    }

    public Player getCurrentPlayer() {
        if (result.getState() != GameState.IN_PROGRESS) {
            throw new IllegalStateException("There is no current player when the game is not in progress");
        }

        return turnOrder.get(currentPlayerIndex);
    }

    public Board getBoard() {
        return board;
    }

    public GameResult getResult() {
        return result;
    }

    public List<Move> getMoveHistory() {
        return List.copyOf(moveHistory);
    }

    public List<Player> getTurnOrder() {
        return turnOrder;
    }

    public Set<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }
}
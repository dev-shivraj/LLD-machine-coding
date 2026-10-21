package practice.workingsystem.tic_tac_toe.model.config;

import practice.workingsystem.tic_tac_toe.model.player.Player;
import practice.workingsystem.tic_tac_toe.model.strategy.turn.TurnOrderStrategy;
import practice.workingsystem.tic_tac_toe.model.strategy.winning.WinningStrategy;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class GameConfig {
    private final int boardSize;
    private final List<Player> players;
    private final TurnOrderStrategy turnOrderStrategy;
    private final Set<WinningStrategy> winningStrategies;

    public GameConfig(int boardSize, List<Player> players, TurnOrderStrategy turnOrderStrategy, Set<WinningStrategy> winningStrategies) {

        if (boardSize < 3) {
            throw new IllegalArgumentException("Board size must be at least 3");
        }

        Objects.requireNonNull(players, "Players cannot be null");
        Objects.requireNonNull(turnOrderStrategy, "Turn order strategy cannot be null");
        Objects.requireNonNull(winningStrategies, "Winning strategies cannot be null");

        if (players.size() < 2 || players.size() >= boardSize) {
            throw new IllegalArgumentException("Number of players must be between 2 and boardSize - 1");
        }

        if (players.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Players cannot contain null values");
        }

        if (winningStrategies.isEmpty()) {
            throw new IllegalArgumentException("At least one winning strategy is required");
        }

        if (winningStrategies.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Winning strategies cannot contain null values");
        }

        validateUniquePlayerIds(players);
        validateUniqueSymbols(players);

        this.boardSize = boardSize;
        this.players = List.copyOf(players);
        this.turnOrderStrategy = turnOrderStrategy;
        this.winningStrategies = Set.copyOf(winningStrategies);
    }

    private void validateUniquePlayerIds(List<Player> players) {
        Set<String> playerIds = new HashSet<>();

        for (Player player : players) {
            if (!playerIds.add(player.getId())) {
                throw new IllegalArgumentException("Player IDs must be unique: " + player.getId());
            }
        }
    }

    private void validateUniqueSymbols(List<Player> players) {
        Set<Character> symbols = new HashSet<>();

        for (Player player : players) {
            if (!symbols.add(player.getSymbol())) {
                throw new IllegalArgumentException("Player symbols must be unique: " + player.getSymbol());
            }
        }
    }

    public int getBoardSize() {
        return boardSize;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public TurnOrderStrategy getTurnOrderStrategy() {
        return turnOrderStrategy;
    }

    public Set<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }
}
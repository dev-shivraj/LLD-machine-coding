package practice.workingsystem.tic_tac_toe.model.result;

import practice.workingsystem.tic_tac_toe.model.enums.GameState;
import practice.workingsystem.tic_tac_toe.model.player.Player;
import practice.workingsystem.tic_tac_toe.model.strategy.winning.WinningLine;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class GameResult {
    private final GameState state;
    private final Player winner;
    private final List<WinningLine> winningLines;

    public GameResult(GameState state, Player winner, List<WinningLine> winningLines) {
        this.state = Objects.requireNonNull(state, "Game state cannot be null");
        Objects.requireNonNull(winningLines, "Winning lines cannot be null");

        if (winningLines.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Winning lines cannot contain null values");
        }

        this.winningLines = List.copyOf(winningLines);
        this.winner = winner;
        validateResult();
    }

    private void validateResult() {

        if (state == GameState.WON) {
            if (winner == null) {
                throw new IllegalArgumentException("A won game must have a winner");
            }

            if (winningLines.isEmpty()) {
                throw new IllegalArgumentException("A won game must have at least one winning line");
            }

        } else if (winner != null || !winningLines.isEmpty()) {
            throw new IllegalArgumentException("Only a won game can have a winner or winning lines");
        }
    }

    public GameState getState() {
        return state;
    }

    public Optional<Player> getWinner() {
        return Optional.ofNullable(winner);
    }

    public List<WinningLine> getWinningLines() {
        return winningLines;
    }
}
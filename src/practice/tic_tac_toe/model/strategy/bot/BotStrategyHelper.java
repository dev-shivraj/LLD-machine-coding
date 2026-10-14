package practice.tic_tac_toe.model.strategy.bot;

import practice.tic_tac_toe.model.board.Board;
import practice.tic_tac_toe.model.move.Position;
import practice.tic_tac_toe.model.player.Player;
import practice.tic_tac_toe.model.strategy.winning.WinningStrategy;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

final class BotStrategyHelper {

    private BotStrategyHelper() {
    }

    static Position chooseRandom(List<Position> positions) {
        if (positions.isEmpty()) {
            throw new IllegalStateException("No available moves");
        }

        int index = ThreadLocalRandom.current().nextInt(positions.size());
        return positions.get(index);
    }

    static boolean hasWon(Board board, Player player, Set<WinningStrategy> winningStrategies) {
        return winningStrategies.stream()
                .anyMatch(strategy -> !strategy.findWinningLines(board, player).isEmpty());
    }

    static Position findImmediateWinningMove(Board board, Player player, Set<WinningStrategy> winningStrategies) {
        for (Position position : board.getAvailablePositions()) {
            board.place(player, position);
            boolean wins;

            try {
                wins = hasWon(board, player, winningStrategies);
            } finally {
                board.remove(position);
            }

            if (wins) {
                return position;
            }
        }

        return null;
    }
}
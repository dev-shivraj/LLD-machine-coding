package practice.workingsystem.tic_tac_toe.model.strategy.bot;

import practice.workingsystem.tic_tac_toe.model.board.Board;
import practice.workingsystem.tic_tac_toe.model.player.BotPlayer;
import practice.workingsystem.tic_tac_toe.model.player.Player;
import practice.workingsystem.tic_tac_toe.model.strategy.winning.WinningStrategy;

import java.util.List;
import java.util.Set;

public record BotContext(Board board, BotPlayer botPlayer, List<Player> players, Set<WinningStrategy> winningStrategies) {

    public BotContext {
        if (board == null || botPlayer == null || players == null || winningStrategies == null) {
            throw new IllegalArgumentException("Bot context arguments cannot be null");
        }

        players = List.copyOf(players);
        winningStrategies = Set.copyOf(winningStrategies);

        if (!players.contains(botPlayer)) {
            throw new IllegalArgumentException("Bot player must belong to the participating players");
        }

        if (winningStrategies.isEmpty()) {
            throw new IllegalArgumentException("At least one winning strategy is required");
        }
    }
}
package tic_tac_toe.model.strategy.bot;

import tic_tac_toe.model.board.Board;
import tic_tac_toe.model.enums.WinningConditionType;
import tic_tac_toe.model.move.Position;
import tic_tac_toe.model.player.BotPlayer;
import tic_tac_toe.model.player.Player;
import tic_tac_toe.model.strategy.winning.*;

import java.util.List;
import java.util.Set;

public class UnbeatableBotStrategy implements BotStrategy {

    @Override
    public Position chooseMove(BotContext context) {
        validateConfiguration(context);
        Board board = context.board();
        BotPlayer bot = context.botPlayer();
        Player opponent = context.players().stream()
                .filter(player -> player != bot)
                .findFirst()
                .orElseThrow();

        List<Position> availablePositions = board.getAvailablePositions();

        if (availablePositions.isEmpty()) {
            throw new IllegalStateException("No available moves");
        }

        Position bestMove = null;

        int bestScore = Integer.MIN_VALUE;

        for (Position position : availablePositions) {
            board.place(bot, position);
            int score;

            try {
                score = minimax(board, bot, opponent, false, 0, context.winningStrategies());
            } finally {
                board.remove(position);
            }

            if (score > bestScore) {
                bestScore = score;
                bestMove = position;
            }
        }

        return bestMove;
    }

    private int minimax(Board board, Player bot, Player opponent, boolean botTurn, int depth, Set<WinningStrategy> winningStrategies) {

        if (BotStrategyHelper.hasWon(board, bot, winningStrategies)) {
            return 10 - depth;
        }

        if (BotStrategyHelper.hasWon(board, opponent, winningStrategies)) {
            return depth - 10;
        }

        if (board.isFull()) {
            return 0;
        }

        Player currentPlayer = botTurn ? bot : opponent;

        int bestScore = botTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Position position : board.getAvailablePositions()) {
            board.place(currentPlayer, position);
            int score;

            try {
                score = minimax(board, bot, opponent, !botTurn, depth + 1, winningStrategies);
            } finally {
                board.remove(position);
            }

            if (botTurn) {
                bestScore = Math.max(bestScore, score);
            } else {
                bestScore = Math.min(bestScore, score);
            }
        }

        return bestScore;
    }

    private void validateConfiguration(BotContext context) {

        if (context.board().getSize() != 3) {
            throw new IllegalArgumentException("Unbeatable strategy supports only a 3x3 board");
        }

        if (context.players().size() != 2) {
            throw new IllegalArgumentException("Unbeatable strategy supports exactly two players");
        }

        Set<WinningConditionType> requiredConditions = Set.of(WinningConditionType.ROW, WinningConditionType.COLUMN, WinningConditionType.DIAGONAL, WinningConditionType.ANTI_DIAGONAL);

        Set<WinningConditionType> configuredConditions = context.winningStrategies().stream()
                .map(this::getConditionType)
                .collect(java.util.stream.Collectors.toSet());

        if (!configuredConditions.containsAll(requiredConditions)) {
            throw new IllegalArgumentException("Unbeatable strategy requires row, column, " + "diagonal, and anti-diagonal winning strategies");
        }

    }

    private WinningConditionType getConditionType(WinningStrategy strategy) {

        if (strategy instanceof RowWinningStrategy) {
            return WinningConditionType.ROW;
        }

        if (strategy instanceof ColumnWinningStrategy) {
            return WinningConditionType.COLUMN;
        }

        if (strategy instanceof DiagonalWinningStrategy) {
            return WinningConditionType.DIAGONAL;
        }

        if (strategy instanceof AntiDiagonalWinningStrategy) {
            return WinningConditionType.ANTI_DIAGONAL;
        }

        return WinningConditionType.CUSTOM;
    }
}
package tic_tac_toe;

import tic_tac_toe.controller.GameController;
import tic_tac_toe.model.Game;
import tic_tac_toe.model.config.GameConfig;
import tic_tac_toe.model.player.BotPlayer;
import tic_tac_toe.model.player.HumanPlayer;
import tic_tac_toe.model.player.Player;
import tic_tac_toe.model.strategy.bot.BotStrategy;
import tic_tac_toe.model.strategy.bot.MediumBotStrategy;
import tic_tac_toe.model.strategy.turn.RegistrationOrderStrategy;
import tic_tac_toe.model.strategy.turn.TurnOrderStrategy;
import tic_tac_toe.model.strategy.winning.*;
import tic_tac_toe.view.ConsoleView;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        int boardSize = 3;

        Player human = new HumanPlayer("P1", "You", 'X', null);
        BotStrategy botStrategy = new MediumBotStrategy();
        Player bot = new BotPlayer("P2", "Computer", 'O', null, botStrategy);
        List<Player> players = List.of(human, bot);

        TurnOrderStrategy turnOrderStrategy = new RegistrationOrderStrategy();

        Set<WinningStrategy> winningStrategies = Set.of(
                new RowWinningStrategy(),
                new ColumnWinningStrategy(),
                new DiagonalWinningStrategy(),
                new AntiDiagonalWinningStrategy()
        );

        GameConfig config = new GameConfig(
                boardSize,
                players,
                turnOrderStrategy,
                winningStrategies
        );

        Game game = new Game(config);

        ConsoleView view = new ConsoleView();

        try (Scanner scanner = new Scanner(System.in)) {
            GameController controller = new GameController(game, view, scanner);
            controller.run();
        }
    }
}

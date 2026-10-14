package tictactoe.controller;

import tictactoe.model.Game;
import tictactoe.model.Player;
import tictactoe.model.enums.GameState;
import tictactoe.strategy.WinningStrategy;

import java.util.List;

public class GameController {
    public Game startGame(int size, List<Player> players, List<WinningStrategy> winningStrategies) {
//        return new Game(size, players, winningStrategies);
        return Game.getBuilder()
                .setSize(size)
                .setPlayers(players)
                .setWinningStrategies(winningStrategies)
                .build();
    }

    public void display(Game game) {
        game.getBoard().display();
    }

    public GameState getGameState(Game game) {
        return game.getGameState();
    }

    public void makeMove(Game game) {
        game.makeMove();
    }
}

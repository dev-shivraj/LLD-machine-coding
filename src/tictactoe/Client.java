package tictactoe;

import tictactoe.controller.GameController;
import tictactoe.model.*;
import tictactoe.model.enums.BotDifficulty;
import tictactoe.model.enums.GameState;
import tictactoe.strategy.EasyPlayingStrategy;
import tictactoe.strategy.RowWinningStrategy;
import tictactoe.strategy.WinningStrategy;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        int size = 3;
        List<Player> players = new ArrayList<>();
        Player player1 = new Human(1, "Shivraj", new Symbol("X", "X"), 27);
        Player player2 = new Human(1, "Raj", new Symbol("O", "O"), 27);
        Player player3 = new Human(1, "Shivraj Singh", new Symbol("S", "S"), 27);
        Bot bot1 = new Bot(1, "bot1", new Symbol("O", "O"), BotDifficulty.EASY);

        players.add(player1);
//        players.add(player2);
//        players.add(player3);
        players.add(bot1);


        List<WinningStrategy> winningStrategies = new ArrayList<>();
        winningStrategies.add(new RowWinningStrategy());

        GameController gameController = new GameController();
        Game game = gameController.startGame(size, players, winningStrategies);

        // play : till the time game is in progress
        while (gameController.getGameState(game).equals(GameState.IN_PROGRESS)) {
            gameController.display(game);

            // make move
            gameController.makeMove(game);
            // System.out.println("Do you want to undo ?");
            // undo functionality here
        }

        gameController.display(game);

        // checkWinner
        // if winner : update gameState to COMPLETED
        // keep on playing otherwise

        // this while loop will break under two conditions : gameState : COMPLETED or DRAW
        if (gameController.getGameState(game).equals(GameState.DRAW)) {
            System.out.println("Game Drawn");
        }

        if (gameController.getGameState(game).equals(GameState.COMPLETED)) {
            System.out.println("Player " + game.getWinner() + " has won");
        }

        // UNDO
    }
}

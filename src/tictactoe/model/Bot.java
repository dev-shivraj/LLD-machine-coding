package tictactoe.model;

import tictactoe.factory.BotPlayingStrategyFactory;
import tictactoe.model.enums.BotDifficulty;
import tictactoe.model.enums.PlayerType;
import tictactoe.strategy.BotPlayingStrategy;
import tictactoe.strategy.EasyPlayingStrategy;

public class Bot extends Player{
    private BotDifficulty botDifficulty;
    private BotPlayingStrategy playingStrategy;

    public Bot(int id, String name, Symbol symbol, BotDifficulty botDifficulty) {
        super(id, name, symbol, PlayerType.BOT);
        this.botDifficulty = botDifficulty;
        this.playingStrategy = BotPlayingStrategyFactory.getPlayingStrategy(botDifficulty);
    }

    public BotDifficulty getBotDifficulty() {
        return botDifficulty;
    }

    public void setBotDifficulty(BotDifficulty botDifficulty) {
        this.botDifficulty = botDifficulty;
    }

    public BotPlayingStrategy getPlayingStrategy() {
        return playingStrategy;
    }

    public void setPlayingStrategy(BotPlayingStrategy playingStrategy) {
        this.playingStrategy = playingStrategy;
    }

    @Override
    public Move makeMove(Game game) {
        System.out.println("It's " + this.getName() + " bot's turn");
        Move move = playingStrategy.makeMove(game.getBoard());
        move.setPlayer(this);
        return move;
    }
}

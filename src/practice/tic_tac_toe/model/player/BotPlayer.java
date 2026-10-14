package practice.tic_tac_toe.model.player;

import practice.tic_tac_toe.model.strategy.bot.BotStrategy;

public class BotPlayer extends Player{
    private final BotStrategy botStrategy;

    public BotPlayer(String id, String name, char symbol, String image, BotStrategy botStrategy) {
        super(id, name, symbol, image);

        if(botStrategy == null) {
            throw new IllegalArgumentException("Bot Strategy can not be null");
        }
        this.botStrategy = botStrategy;
    }

    public BotStrategy getBotStrategy() {
        return botStrategy;
    }
}

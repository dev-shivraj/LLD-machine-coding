package tictactoe.factory;

import tictactoe.model.enums.BotDifficulty;
import tictactoe.strategy.BotPlayingStrategy;
import tictactoe.strategy.EasyPlayingStrategy;
import tictactoe.strategy.HardPlayingStrategy;
import tictactoe.strategy.MediumPlayingStrategy;

public class BotPlayingStrategyFactory {
    public static BotPlayingStrategy getPlayingStrategy(BotDifficulty botDifficulty) {
        if(botDifficulty.equals(BotDifficulty.EASY)) {
            return new EasyPlayingStrategy();
        } else if(botDifficulty.equals(BotDifficulty.MEDIUM)) {
            return new MediumPlayingStrategy();
        } else {
            return new HardPlayingStrategy();
        }
    }
}

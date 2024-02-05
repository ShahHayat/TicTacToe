package factory;

import model.BotDifficultyLevel;
import strategy.botplayingstrategy.BotPlayingStrategy;
import strategy.botplayingstrategy.EasyBotPlayingStrategy;
import strategy.botplayingstrategy.HardBotPlayingStrategy;
import strategy.botplayingstrategy.MediumBotPlayingStrategy;

public class BotPlayingStrategyFactory {

    public static BotPlayingStrategy getPlayingStrategyForDifficultyLevel(BotDifficultyLevel botDifficultyLevel) {
        return switch(botDifficultyLevel) {
            case EASY -> new EasyBotPlayingStrategy();
            case MEDIUM -> new MediumBotPlayingStrategy();
            case HARD -> new HardBotPlayingStrategy();
            default -> new EasyBotPlayingStrategy();
        };
    }
}

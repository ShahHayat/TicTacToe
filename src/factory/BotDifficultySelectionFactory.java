package factory;

import model.BotDifficultyLevel;

public class BotDifficultySelectionFactory {

    public static BotDifficultyLevel getBotDifficultyLevel(String botPlayingDifficultyLevel) {
        return switch(botPlayingDifficultyLevel.toUpperCase()) {
            case "EASY" -> BotDifficultyLevel.EASY;
            case "MEDIUM" -> BotDifficultyLevel.MEDIUM;
            case "HARD" -> BotDifficultyLevel.HARD;
            default -> BotDifficultyLevel.EASY;
        };
    }
}

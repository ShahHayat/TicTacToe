import controller.GameController;
import factory.BotDifficultySelectionFactory;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the dimension : ");
        int dimension = sc.nextInt();

        int playerSize = dimension - 1;
        List<Player> players = new ArrayList<>();


        String botDifficulty = null;
        // sc.nextLine();

        System.out.println("Do you need Bot players (Y/N) -> ");
        String isBotRequired = sc.next();

        if(isBotRequired.equalsIgnoreCase("Y")) {
            playerSize -= 1;
            System.out.println("Select the bot difficulty level (Easy, Medium, Hard) -> ");
            botDifficulty = sc.next();

            System.out.println("Please enter bot's name : ");
            String name = sc.next();

            System.out.println("Please enter bot symbol : ");
            char symbol = sc.next().charAt(0);

            BotDifficultyLevel botDifficultyLevel = BotDifficultySelectionFactory.getBotDifficultyLevel(botDifficulty);

            players.add(new Bot(symbol, name, botDifficultyLevel));
        }

        for (int i = 0; i < playerSize; i++) {
            System.out.println("Please enter player's name : ");
            String name = sc.next();
            // sc.nextLine();

            System.out.println("Please enter player's symbol : ");
            char symbol = sc.next().charAt(0);

            players.add(new Player(symbol, name, PlayerType.HUMAN));
        }

        GameController gameController = new GameController();
        Game game = gameController.createGame(dimension, players);
        String isUndo = "N";
        boolean undoDone = false;

        while(game != null && gameController.getGameStatus(game).equals(GameStatus.IN_PROGRESS)) {

            System.out.println("This is the current board status");
            gameController.displayBoard(game);

            if(game.getMoves().size() > 0 && game.getcurrentPlayerType(game).equals(PlayerType.HUMAN) && !undoDone) {
                System.out.println("Do you want to undo your last move (Y/N) -> ");
                isUndo = sc.next();
            }
            if(isUndo.equalsIgnoreCase("Y")) {
                gameController.undo(game);
                isUndo = "N";
                undoDone = true;
            } else {
                gameController.makeNextMove(game);
                undoDone = false;
            }

            gameController.displayBoard(game);

            if(gameController.getGameStatus(game).equals(GameStatus.DRAW)) {
                System.out.println("Game is drawn");
            } else if (gameController.getGameStatus(game).equals(GameStatus.ENDED)) {
                Player winner = gameController.getGameWinner(game);
                System.out.println(winner.getName() +
                        " with symbol " + winner.getSymbol() + " is the winner!!!");
            }
        }
    }
}

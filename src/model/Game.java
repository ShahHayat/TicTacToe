package model;

import exceptions.InvalidGameCreationParametersException;
import strategy.gamewinningstrategy.EfficientGameWinningStrategy;
import strategy.gamewinningstrategy.GameWinningStrategy;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private List<Player> players;
    private List<Move> moves;
    private GameStatus gameStatus;
    private int nextPlayerIndex;
    private GameWinningStrategy gameWinningStrategy;
    private Player winner;

    private Game() {}

    public void undo() {
        int size = moves.size();
        Move move = moves.get(size - 1);
        Cell lastPlayedCell = move.getCell();

        int row = lastPlayedCell.getRow();
        int col = lastPlayedCell.getCol();

        char symbol = move.getPlayer().getSymbol();

        board.getBoard().get(row).get(col).setCellState(CellState.EMPTY);

        gameWinningStrategy.undoLastMove(move);

        this.gameStatus = GameStatus.IN_PROGRESS;
        this.nextPlayerIndex--;
        this.moves.remove(moves.size() - 1);
    }

    public void displayBoard() {
        this.board.display();
    }

    public void makeNextMove() {
        Player currentPlayer = players.get(nextPlayerIndex);
        System.out.println(currentPlayer.getName() + "'s move");
        Move move = currentPlayer.decideMove(this.board);

        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        System.out.println("Move happened : Row -> " + row + ", Col -> " + col);
        board.getBoard().get(row).get(col).setCellState(CellState.FILLED);
        board.getBoard().get(row).get(col).setPlayer(currentPlayer);

        Move playedMove = new Move(currentPlayer, board.getBoard().get(row).get(col));
        this.moves.add(playedMove);

        if(gameWinningStrategy.updateBoardAndCheckWinner(board, currentPlayer, playedMove)) {
            gameStatus = GameStatus.ENDED;
            winner = currentPlayer;
        }

        nextPlayerIndex++;
        nextPlayerIndex %= players.size();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }

    public void setNextPlayerIndex(int nextPlayerIndex) {
        this.nextPlayerIndex = nextPlayerIndex;
    }

    public GameWinningStrategy getGameWinningStrategy() {
        return gameWinningStrategy;
    }

    public void setGameWinningStrategy(GameWinningStrategy gameWinningStrategy) {
        this.gameWinningStrategy = gameWinningStrategy;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public PlayerType getcurrentPlayerType(Game game) {
        int index = ((nextPlayerIndex - 1) < 0) ? (players.size() - 1) : (nextPlayerIndex - 1);
        return game.players.get(nextPlayerIndex).getType();
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private int dimension;
        private List<Player> players;

        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Game build() throws InvalidGameCreationParametersException {
            try {
                valid();
            } catch (Exception ex) {
                throw new InvalidGameCreationParametersException("Game could not be created");
            }

            Game game = new Game();
            game.setGameStatus(GameStatus.IN_PROGRESS);
            game.setPlayers(players);
            game.setMoves(new ArrayList<>());
            game.setBoard(new Board(dimension));
            game.setNextPlayerIndex(0);
            game.setGameWinningStrategy(new EfficientGameWinningStrategy(dimension));

            return game;
        }

        private boolean valid() throws InvalidGameCreationParametersException {
            if(this.dimension < 3) {
                throw new InvalidGameCreationParametersException("Dimensions must be greater than or equal to 3");
            }
            if(this.players.size() != this.dimension - 1) {
                throw new InvalidGameCreationParametersException("Number of players should be 1 less than dimension");
            }
            if(this.players.size() < 2) {
                System.out.println(this.players.size());
                throw new InvalidGameCreationParametersException("There must be atleast 2 players");
            }

            int botCount = 0;
            for (Player player : players) {
                if(player.getType().equals(PlayerType.BOT)) {
                    botCount++;
                }
            }
            if (botCount > 1) {
                throw new InvalidGameCreationParametersException("Number of bots must not increase 1");
            }

            return true;
        }
    }
}

package strategy.gamewinningstrategy;

import model.Board;
import model.Move;
import model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EfficientGameWinningStrategy implements GameWinningStrategy {

    List<HashMap<Character, Integer>> rowSymbolCount = new ArrayList<>();
    List<HashMap<Character, Integer>> colSymbolCount = new ArrayList<>();
    HashMap<Character, Integer> topLeftDiagonalSymbolCount = new HashMap<>();
    HashMap<Character, Integer> bottomLeftDiagonalSymbolCount = new HashMap<>();
    int dimension;

    public EfficientGameWinningStrategy(int dimension) {
        for (int i = 0; i < dimension; i++) {
            rowSymbolCount.add(new HashMap<>());
            colSymbolCount.add(new HashMap<>());
        }
        this.dimension = dimension;
    }

    @Override
    public void undoLastMove(Move move) {
        char symbol = move.getPlayer().getSymbol();
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        rowSymbolCount.get(row).put(symbol, rowSymbolCount.get(row).get(symbol) - 1);
        rowSymbolCount.get(col).put(symbol, rowSymbolCount.get(col).get(symbol) - 1);

        if(isTopLeftDiagonal(row, col)) {
            topLeftDiagonalSymbolCount.put(symbol, topLeftDiagonalSymbolCount.get(symbol) - 1);
        }

        if(isBottomLeftDiagonal(row, col)) {
            bottomLeftDiagonalSymbolCount.put(symbol, bottomLeftDiagonalSymbolCount.get(symbol) - 1);
        }
    }

    @Override
    public boolean updateBoardAndCheckWinner(Board board, Player lastMovePlayer, Move lastMove) {
        char symbol = lastMovePlayer.getSymbol();
        int row = lastMove.getCell().getRow();
        int col = lastMove.getCell().getCol();

        rowSymbolCount.get(row).put(symbol, rowSymbolCount.get(row).getOrDefault(symbol, 0) + 1);
        colSymbolCount.get(col).put(symbol, colSymbolCount.get(col).getOrDefault(symbol, 0) + 1);

        if(isTopLeftDiagonal(row, col)) {
            topLeftDiagonalSymbolCount.put(symbol, topLeftDiagonalSymbolCount.getOrDefault(symbol, 0) + 1);
        }

        if(isBottomLeftDiagonal(row, col)) {
            bottomLeftDiagonalSymbolCount.put(symbol, bottomLeftDiagonalSymbolCount.getOrDefault(symbol, 0) + 1);
        }

        return CheckWinner(row, col, symbol);
    }

    private boolean isTopLeftDiagonal(int row, int col) {
        return row == col;
    }

    private boolean isBottomLeftDiagonal(int row, int col) {
        return ((row + col) == (dimension - 1));
    }

    private boolean CheckWinner(int row, int col, char symbol) {
        if(rowSymbolCount.get(row).get(symbol) == dimension) {
            return true;
        }
        if(colSymbolCount.get(col).get(symbol) == dimension) {
            return true;
        }
        if(isTopLeftDiagonal(row, col)) {
            if(topLeftDiagonalSymbolCount.get(symbol) == dimension) {
                return true;
            }
        }
        if(isBottomLeftDiagonal(row, col)) {
            if(bottomLeftDiagonalSymbolCount.get(symbol) == dimension) {
                return true;
            }
        }
        return false;
    }
}

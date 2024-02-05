package strategy.gamewinningstrategy;

import model.Board;
import model.Move;
import model.Player;

public interface GameWinningStrategy {
    boolean updateBoardAndCheckWinner(Board board, Player lastMovePlayer, Move lastMove);
    void undoLastMove(Move lastMove);
}

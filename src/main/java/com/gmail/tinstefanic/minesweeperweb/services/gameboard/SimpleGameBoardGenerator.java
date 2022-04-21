package com.gmail.tinstefanic.minesweeperweb.services.gameboard;

import com.gmail.tinstefanic.minesweeperweb.entities.GameBoard;
import com.gmail.tinstefanic.minesweeperweb.exceptions.IllegalNumberOfMinesException;
import com.gmail.tinstefanic.minesweeperweb.util.MineBoard;
import org.springframework.data.util.Pair;

/**
 * Provides method to generate a random string representing game board.
 * And a corresponding method to modify game board and ensure that the
 * first position that user opened isn't a mine.
 */
public class SimpleGameBoardGenerator implements IGameBoardGenerator {

    @Override
    public String generateGameBoardString(int width, int height, int totalMines) {
        throwExceptionIfParametersAreIllegal(width, height, totalMines);

        var mineBoard = MineBoard.createRandomMineBoard(width, height, totalMines);

        Pair<Integer, Integer> coords = mineBoard.getOptimalStartingCoords();

        return mineBoard.rightShiftRows(-coords.getSecond()).rightShiftColumns(-coords.getFirst()).toGameBoardString();
    }

    @Override
    public String ensureSafeStartLocation(GameBoard gameBoard, int x, int y) {
        return shiftArrayStartToCoords(
                gameBoard.getWidth(), gameBoard.getHeight(), gameBoard.getBoardAsString(), Pair.of(x, y)
        );
    }

    private void throwExceptionIfParametersAreIllegal(int width, int height, int totalMines) {
        if (totalMines > width * height || totalMines < 0)
            throw new IllegalNumberOfMinesException("Total number of mines (" + String.valueOf(totalMines) +
                    ") must be greater or equal to 0 and less then or equal to size of board(" +
                    String.valueOf(width * height) + ")");
    }

    private String shiftArrayStartToCoords(
            int width, int height, String gameBoardString, Pair<Integer, Integer> coords) {

        var mineBoard = MineBoard.fromString(width, height, gameBoardString);
        return mineBoard.rightShiftRows(coords.getSecond()).rightShiftColumns(coords.getFirst()).toGameBoardString();
    }
}

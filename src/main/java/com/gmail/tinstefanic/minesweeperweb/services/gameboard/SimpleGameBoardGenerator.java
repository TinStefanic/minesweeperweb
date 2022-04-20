package com.gmail.tinstefanic.minesweeperweb.services.gameboard;

import com.gmail.tinstefanic.minesweeperweb.entities.GameBoard;
import com.gmail.tinstefanic.minesweeperweb.exceptions.IllegalNumberOfMinesException;
import com.gmail.tinstefanic.minesweeperweb.exceptions.NotImplementedException;
import com.gmail.tinstefanic.minesweeperweb.util.MineBoard;
import org.springframework.data.util.Pair;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

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

//        boolean[] isMineArray = createIsMineArray(width, height, totalMines);
//
//        int[] minelessRadiusArray = createMinelessRadiusArray(width, height, isMineArray);
//
//        Pair<Integer, Integer> coords = getCoordsWithLargestMinelessRadius(width, height, minelessRadiusArray);
//
//        shiftCoordsToArrayStart(width, height, coords, isMineArray);
//
//        return isMineArrayToGameBoardString(width, height, coords, isMineArray);
    }

    @Override
    public String ensureSafeStartLocation(GameBoard gameBoard, int x, int y) {
        return shiftArrayStartToCoords(
                gameBoard.getWidth(), gameBoard.getHeight(), gameBoard.getBoardAsString(), Pair.of(x, y)
        );
    }

    private void throwExceptionIfParametersAreIllegal(int width, int height, int totalMines) {
        if (totalMines >= width * height || totalMines < 0)
            throw new IllegalNumberOfMinesException("Total number of mines (" + String.valueOf(totalMines) +
                    ") must be greater or equal to 0 and less then size of board(" +
                    String.valueOf(width * height) + ")");
    }

    private boolean[] createIsMineArray(int width, int height, int totalMines) {
        var isMineArray = new boolean[width * height]; // All values are false.

        List<Integer> mines = IntStream.range(0, width * height).boxed().toList();
        Collections.shuffle(mines);

        for (Integer i : mines.subList(0, totalMines))
            isMineArray[i] = true;

        return isMineArray;
    }

    // Represents largest n such that nxn block around the target coordinate has no mines.
    // It will only check n up to 5.
    private int[] createMinelessRadiusArray(int width, int height, boolean[] isMineArray) {
        var minelessRadiusArray = new int[width * height]; // All values are zero.

        for (int x = 0; x < width; ++x)
            for (int y = 0; y < height; ++y)
                for (int radius = 1; radius <= 5; ++radius) {
                    var hasMine = false;
                    for (int i = -radius; i <= radius; ++i) {
                        hasMine |= isMine(x + i, y - radius, width, height, isMineArray) |
                                isMine(x + i, y + radius, width, height, isMineArray) |
                                isMine(x - radius, y + i, width, height, isMineArray) |
                                isMine(x + radius, y + i, width, height, isMineArray);
                    }

                    if (hasMine) break;
                    else minelessRadiusArray[y * width + x] = radius;
                }

        return minelessRadiusArray;
    }

    private boolean isMine(int x, int y, int width, int height, boolean[] isMineArray) {
        x = (x + width) % width;
        y = (y + height) % height;
        return isMineArray[y * width + x];
    }

    private Pair<Integer, Integer> getCoordsWithLargestMinelessRadius(
            int width, int height, int[] minelessRadiusArray) {

        int max = -1, oneDimensionalCoord = -1;

        for (int i = 0; i < width * height; ++i)
            if (minelessRadiusArray[i] > max) {
                max = minelessRadiusArray[i];
                oneDimensionalCoord = i;
            }

        return Pair.of(oneDimensionalCoord % width, oneDimensionalCoord / width);
    }

    private void shiftCoordsToArrayStart(
            int width, int height, Pair<Integer, Integer> coords, boolean[] isMineArray) {

        // Shift columns.
        for (int y = 0; y < height; ++y)
            for (int x = 0; x < width; ++x) {
                int newX = (x + coords.getFirst()) % width;
                isMineArray[y * width + x] = isMineArray[y * width + newX];
            }

        // Shift rows.
        for (int x = 0; x < width; ++x)
            for (int y = 0; y < height; ++y) {
                int newY = (y + coords.getSecond()) % height;
                isMineArray[y * width + x] = isMineArray[newY * width + x];
            }
    }

    private String isMineArrayToGameBoardString(
            int width, int height, Pair<Integer, Integer> coords, boolean[] isMineArray) {

        throw new NotImplementedException();
    }

    private String shiftArrayStartToCoords(
            int width, int height, String gameBoardString, Pair<Integer, Integer> of) {

        throw new NotImplementedException();
    }
}

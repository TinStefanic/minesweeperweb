package com.gmail.tinstefanic.minesweeperweb.util;

import com.gmail.tinstefanic.minesweeperweb.services.gameboard.GameBoardLocationType;
import org.springframework.data.util.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.floorMod;

/**
 * Utility class for operating with mine board.
 * @param width Width of the mine board.
 * @param height Height of the mine board.
 * @param isMineMatrix Boolean matrix, true if a position is mine, false otherwise.
 */
public record MineBoard(int width, int height, boolean[][] isMineMatrix) {
    /**
     * Given desired width and height of the board,
     * creates MineBoard with totalMines amount of mines randomly scattered.
     * @param width Desired width of the MineBoard.
     * @param height Desired height of the MineBoard.
     * @param totalMines Desired amount of mines for MineBoard.
     * @return MineBoard with desired properties.
     */
    public static MineBoard createRandomMineBoard(int width, int height, int totalMines) {
        var isMineMatrix = new boolean[width][height]; // All values are false.

        List<Integer> mines = IntStream.range(0, width * height).boxed().collect(Collectors.toList());
        Collections.shuffle(mines);

        for (Integer i : mines.subList(0, totalMines)) {
            isMineMatrix[i % width][i / width] = true;
        }

        return new MineBoard(width, height, isMineMatrix);
    }

    /**
     * Creates new MineBoard from string, where mines are marked with
     * GameBoardLocationType.MINE.asChar() character.
     * @param width Width of the board.
     * @param height Height of the board.
     * @param gameBoardString String from which to create MineBoard.
     * @return New MineBoard created from provided string.
     */
    public static MineBoard fromString(int width, int height, String gameBoardString) {
        return MineBoard.fromString(width, height, gameBoardString, GameBoardLocationType.MINE.asChar());
    }

    /**
     * Creates new MineBoard from string, where mines are marked with mineChar char.
     * @param width Width of the board.
     * @param height Height of the board.
     * @param gameBoardString String from which to create MineBoard.
     * @param mineChar Char that represents mine in input string.
     * @return New MineBoard created from provided string.
     */
    private static MineBoard fromString(int width, int height, String gameBoardString, char mineChar) {
        var isMineMatrix = new boolean[width][height];

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y)
                // width + 1 is to count the new line character present in string as a row separator.
                isMineMatrix[x][y] = gameBoardString.charAt(x + (width + 1) * y) == mineChar;
        }

        return new MineBoard(width, height, isMineMatrix);
    }

    /**
     * Selects optimal starting position for MineBoard,
     * i.e. position with the fewest mines around.
     * @return Optimal starting coordinates as a pair of Integers.
     */
    public Pair<Integer, Integer> getOptimalStartingCoords() {
        int maxMinelessRadius = -1;
        Pair<Integer, Integer> optimalCoords = Pair.of(-1, -1);

        for (int x = 0; x < this.width; ++x) {
            for (int y = 0; y < this.height; ++y) {
                int currentMinelessRadius = calcMinelessRadius(x, y);
                if (currentMinelessRadius > maxMinelessRadius) {
                    maxMinelessRadius = currentMinelessRadius;
                    optimalCoords = Pair.of(x, y);
                }
            }
        }

        return optimalCoords;
    }

    /**
     * Returns new MineBoard with all rows shifted,
     * such that 'r'-th row becomes 'r+offset'-th row.
     * @param offset For how much to shift the rows.
     * @return New MineBoard with shifted rows.
     */
    public MineBoard rightShiftRows(int offset) {
        offset = ((offset % this.height) + this.height) % this.height;
        var newIsMineMatrix = new boolean[this.width][this.height];

        for (int x = 0; x < this.width; ++x) {
            for (int y = 0; y < this.height; ++y) {
                int newY = (y + offset) % this.height;
                newIsMineMatrix[x][newY] = this.isMineMatrix[x][y];
            }
        }

        return new MineBoard(this.width, this.height, newIsMineMatrix);
    }

    /**
     * Returns new MineBoard with all columns shifted,
     * such that 'c'-th column becomes 'c+offset'-th column.
     * @param offset For how much to shift the columns.
     * @return New MineBoard with shifted columns.
     */
    public MineBoard rightShiftColumns(int offset) {
        offset = ((offset % this.width) + this.width) % this.width;
        var newIsMineMatrix = new boolean[this.width][this.height];

        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x) {
                int newX = (x + offset) % this.width;
                newIsMineMatrix[newX][y] = this.isMineMatrix[x][y];
            }
        }

        return new MineBoard(this.width, this.height, newIsMineMatrix);
    }

    /**
     * Returns String representation of GameBoard.
     * @return String representing GameBoard.
     */
    public String toGameBoardString() {
        var gameBoardStringBuilder = new StringBuilder();

        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x) {
                gameBoardStringBuilder.append(calcGameBoardLocationTypeChar(x, y));
            }
            gameBoardStringBuilder.append("\n");
        }

        return gameBoardStringBuilder.toString();
    }

    // If given coordinate is a mine, returns -1.
    private int calcMinelessRadius(int x, int y) {
        // If mineless radius is at least limit,
        // only limit is returned even if mineless radius would be higher.
        int limit = 4;

        for (int minelessRadius = 0; minelessRadius <= limit; ++minelessRadius) {
            for (int i = -minelessRadius; i <= minelessRadius; ++i) {
                if (this.isMineMatrix[floorMod(x + i, this.width)][floorMod(y - minelessRadius, this.height)] ||
                    this.isMineMatrix[floorMod(x + i, this.width)][floorMod(y + minelessRadius, this.height)] ||
                    this.isMineMatrix[floorMod(x - minelessRadius, this.width)][floorMod(y + i, this.height)] ||
                    this.isMineMatrix[floorMod(x + minelessRadius, this.width)][floorMod(y + i, this.height)]
                ) {
                    return minelessRadius - 1;
                }
            }
        }

        return limit;
    }

    private char calcGameBoardLocationTypeChar(int x, int y) {
        if (this.isMineMatrix[x][y]) return GameBoardLocationType.MINE.asChar();

        int numSurroundingMines = 0;
        for (int xx = x - 1; xx <= x + 1; ++xx) {
            if (xx < 0 || xx >= this.width) continue;
            for (int yy = y - 1; yy <= y + 1; ++yy) {
                if (yy < 0 || yy >= this.height) continue;
                if (this.isMineMatrix[xx][yy]) ++numSurroundingMines;
            }
        }

        return GameBoardLocationType.fromNumSurroundingClosed(numSurroundingMines).asChar();
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();

        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x) {
                stringBuilder.append((this.isMineMatrix[x][y] ? "X" : "_"));
            }
            stringBuilder.append('\n');
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        MineBoard mineBoard = (MineBoard) o;
        return this.width == mineBoard.width &&
                this.height == mineBoard.height &&
                this.toString().equals(mineBoard.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.width, this.height, this.toString());
    }
}

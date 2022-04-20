package com.gmail.tinstefanic.minesweeperweb.util;

import com.gmail.tinstefanic.minesweeperweb.exceptions.NotImplementedException;
import org.springframework.data.util.Pair;

/**
 * Utility class for operating with mine board.
 * @param width Width of the mine board.
 * @param height Height of the mine board.
 * @param isMine Boolean matrix, true if a position is mine, false otherwise.
 */
public record MineBoard(int width, int height, boolean[][] isMine) {
    /**
     * Given desired width and height of the board,
     * creates MineBoard with totalMines amount of mines randomly scattered.
     * @param width Desired width of the MineBoard.
     * @param height Desired height of the MineBoard.
     * @param totalMines Desired amount of mines for MineBoard.
     * @return MineBoard with desired properties.
     */
    public static MineBoard createRandomMineBoard(int width, int height, int totalMines) {
        throw new NotImplementedException();
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
        throw new NotImplementedException();
    }

    /**
     * Selects optimal starting position for MineBoard,
     * i.e. position with the fewest mines around.
     * @return Optimal starting coordinates as a pair of Integers.
     */
    public Pair<Integer, Integer> getOptimalStartingCoords() {
        throw new NotImplementedException();
    }

    /**
     * Returns new MineBoard with all rows shifted,
     * such that 'r'-th row becomes 'r+offset'-th row.
     * @param offset For how much to shift the rows.
     * @return New MineBoard with shifted rows.
     */
    public MineBoard rightShiftRows(int offset) {
        throw new NotImplementedException();
    }

    /**
     * Returns new MineBoard with all columns shifted,
     * such that 'c'-th column becomes 'c+offset'-th column.
     * @param offset For how much to shift the columns.
     * @return New MineBoard with shifted columns.
     */
    public MineBoard rightShiftColumns(int offset) {
        throw new NotImplementedException();
    }

    /**
     * Returns String representation of GameBoard.
     * @return String representing GameBoard.
     */
    public String toGameBoardString() {
        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();

        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x)
                stringBuilder.append((this.isMine[x][y] ? "X" : "_"));
            stringBuilder.append('\n');
        }

        return stringBuilder.toString();
    }
}

package com.gmail.tinstefanic.minesweeperweb.services.gameboard;

import com.gmail.tinstefanic.minesweeperweb.exceptions.IllegalCharLocationTypeException;

import java.util.Arrays;

import static java.lang.Character.isUpperCase;

/**
 * Represents type of location on the board, it can be either mine (MINE),
 * or not yet opened space surrounded by N mines (CLOSED_N),
 * or opened space that is surrounded by M mines (OPENED_M).
 */
public enum GameBoardLocationType {
    MINE('X'),
    CLOSED_0('A'),
    CLOSED_1('B'), CLOSED_2('C'), CLOSED_3('D'), CLOSED_4('E'),
    CLOSED_5('F'), CLOSED_6('G'), CLOSED_7('H'), CLOSED_8('I'),
    OPENED_0('a'),
    OPENED_1('b'), OPENED_2('c'), OPENED_3('d'), OPENED_4('e'),
    OPENED_5('f'), OPENED_6('g'), OPENED_7('h'), OPENED_8('i');

    public static GameBoardLocationType fromNumSurroundingClosed(int numSurroundingMines) {
        return GameBoardLocationType.valueOf("CLOSED_" + numSurroundingMines);
    }

    public char asChar() {
        return location;
    }

    private final char location;

    GameBoardLocationType(char location) {
        this.location = location;
    }

    public static GameBoardLocationType fromChar(char location) {
        return Arrays.stream(GameBoardLocationType.values())
                .filter(gbl -> gbl.location == location)
                .findAny()
                .orElseThrow(IllegalCharLocationTypeException::new);
    }

    /**
     * Returns opened version of the location. Does nothing if location is already opened or mine.
     * @return Corresponding opened location, or same location.
     */
    public GameBoardLocationType toOpened() {
        if (isClosed() && this != GameBoardLocationType.MINE) {
            return GameBoardLocationType.valueOf("OPENED_" + name().charAt(name().length()-1));
        } else {
            return this;
        }
    }

    /**
     * Checks if the current location represents unopened tile.
     * @return Is the location closed.
     */
    public boolean isClosed() {
        return isUpperCase(location);
    }

    /**
     * Gets number of surrounding mines of given location that isn't a mine.
     * @return Number of surrounding mines.
     */
    public int getNumSurroundingMines() {
        if (this == GameBoardLocationType.MINE) return -1; // Operation is undefined for mine.
        if (isClosed()) return asChar() - CLOSED_0.asChar();
        else return asChar() - OPENED_0.asChar();
    }
}

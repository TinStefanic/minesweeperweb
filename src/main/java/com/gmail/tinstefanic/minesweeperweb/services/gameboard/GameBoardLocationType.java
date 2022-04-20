package com.gmail.tinstefanic.minesweeperweb.services.gameboard;

import com.gmail.tinstefanic.minesweeperweb.exceptions.IllegalCharLocationTypeArgumentException;

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
                .orElseThrow(IllegalCharLocationTypeArgumentException::new);
    }

    /**
     * Checks if the current location represents unopened tile.
     * @return Is the location closed.
     */
    public boolean isClosed() {
        return isUpperCase(location);
    }
}

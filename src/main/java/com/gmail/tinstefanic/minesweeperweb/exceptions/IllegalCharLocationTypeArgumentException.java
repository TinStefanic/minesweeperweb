package com.gmail.tinstefanic.minesweeperweb.exceptions;

/**
 * Thrown when GameBoardLocationType enum receives character it cannot map to a location type.
 */
public class IllegalCharLocationTypeArgumentException extends IllegalArgumentException {
    public IllegalCharLocationTypeArgumentException() {
        super();
    }

    public IllegalCharLocationTypeArgumentException(String message) {
        super(message);
    }
}

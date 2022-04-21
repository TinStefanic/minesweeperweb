package com.gmail.tinstefanic.minesweeperweb.exceptions;

/**
 * Thrown when GameBoardLocationType enum receives character it cannot map to a location type.
 */
public class IllegalCharLocationTypeException extends IllegalArgumentException {
    public IllegalCharLocationTypeException() {
        super();
    }

    public IllegalCharLocationTypeException(String message) {
        super(message);
    }
}

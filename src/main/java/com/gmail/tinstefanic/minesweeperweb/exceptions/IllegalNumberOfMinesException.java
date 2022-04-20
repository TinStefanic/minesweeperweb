package com.gmail.tinstefanic.minesweeperweb.exceptions;

public class IllegalNumberOfMinesException extends IllegalArgumentException {
    public IllegalNumberOfMinesException() {
        super();
    }

    public IllegalNumberOfMinesException(String message) {
        super(message);
    }
}

package com.gmail.tinstefanic.minesweeperweb.exceptions;

public class GameOverGameBoardModifiedException extends RuntimeException {
    public GameOverGameBoardModifiedException() {
        super();
    }

    public GameOverGameBoardModifiedException(String message) {
        super(message);
    }
}

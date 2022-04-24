package com.gmail.tinstefanic.minesweeperweb.models;

import lombok.Getter;

/**
 * Class containing data to be sent as a response to request to open location.
 */
@Getter
public class OpenLocationResponse {
    private final boolean isMine;
    private final int numNeighbouringMines;
    private final boolean firstAction;
    private final boolean isGameOver;

    public OpenLocationResponse(boolean isMine, int numNeighbouringMines, boolean firstAction, boolean isGameOver) {
        this.isMine = isMine;
        this.numNeighbouringMines = numNeighbouringMines;
        this.firstAction = firstAction;
        this.isGameOver = isGameOver;
    }
}

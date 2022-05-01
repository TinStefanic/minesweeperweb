package com.gmail.tinstefanic.minesweeperweb.dtos;

import lombok.Getter;

/**
 * Class containing data to be sent as a response to request to open location.
 */
@Getter
public class OpenLocationDto {
    private final boolean isMine;
    private final int numNeighbouringMines;
    private final boolean isFirstMove;
    private final boolean isGameOver;

    public OpenLocationDto(boolean isMine, int numNeighbouringMines, boolean isFirstMove, boolean isGameOver) {
        this.isMine = isMine;
        this.numNeighbouringMines = numNeighbouringMines;
        this.isFirstMove = isFirstMove;
        this.isGameOver = isGameOver;
    }
}

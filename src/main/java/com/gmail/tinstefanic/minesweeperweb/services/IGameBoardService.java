package com.gmail.tinstefanic.minesweeperweb.services;

import com.gmail.tinstefanic.minesweeperweb.entities.GameBoard;

public interface IGameBoardService {
    /**
     * Creates new game board for the selected difficulty.
     * @param difficulty String representing difficulty.
     * @return GameBoard initialized for target difficulty.
     */
    GameBoard getNewGameBoard(String difficulty);

    /**
     * Returns does the difficulty string represent valid difficulty.
     * @param difficulty String to check if it is valid difficulty.
     * @return True if given string is valid difficulty.
     */
    boolean isValidDifficulty(String difficulty);
}

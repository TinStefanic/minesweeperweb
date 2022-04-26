package com.gmail.tinstefanic.minesweeperweb.services.gameboard;

import com.gmail.tinstefanic.minesweeperweb.entities.GameBoard;

/**
 * Provides method to generate a random string representing game board.
 * And a corresponding method to modify game board and ensure that the
 * first position that user opened isn't a mine.
 */
public interface IGameBoardGenerator {
    /**
     * Generates a new game board in a string representation with provided information.
     *
     * @param width      Desired width of the generated GameBoard.
     * @param height     Desired height of the generated GameBoard.
     * @param totalMines Desired amount of mines that the generated GameBoard should have.
     * @return String representing game board generated with provided parameters.
     */
    String generateInitialGameBoardString(int width, int height, int totalMines);

    /**
     * Modifies game board to ensure that given position isn't a mine.
     * @param gameBoard Game board that should be modified.
     * @param x         Desired starting x coordinate.
     * @param y         Desired starting y coordinate.
     * @return String representing modified game board.
     */
    String ensureSafeStartLocation(GameBoard gameBoard, int x, int y);
}

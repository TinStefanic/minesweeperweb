package com.gmail.tinstefanic.minesweeperweb.services.gameboard;

import com.gmail.tinstefanic.minesweeperweb.entities.GameBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleGameBoardGeneratorTest {
    @Test
    @DisplayName("Generated board should have dimensions 16x40.")
    void generatedBoardShouldHaveDimensions16x40Test() {
        int width = 16, height = 40, totalMines = 5;

        var gameBoard = new GameBoard(width, height, totalMines, new SimpleGameBoardGenerator());

        assertThat(gameBoard.getWidth()).isEqualTo(width);
        assertThat(gameBoard.getHeight()).isEqualTo(height);
    }

    @Test
    @DisplayName("Location type on generated board should be a mine.")
    void locationTypeOnGeneratedBoardShouldBeAMineTest() {
        int width = 16, height = 40;
        int totalMines = width * height;

        var gameBoard = new GameBoard(width, height, totalMines, new SimpleGameBoardGenerator());

        assertThat(gameBoard.getLocationAt(0, 0)).isEqualTo(GameBoardLocationType.MINE);
    }

    @Test
    @DisplayName("Location type on generated board shouldn't be a mine.")
    void locationTypeOnGeneratedBoardShouldntBeAMineTest() {
        int width = 16, height = 40;
        int totalMines = width * height;

        var gameBoard = new GameBoard(width, height, totalMines, new SimpleGameBoardGenerator());

        assertThat(gameBoard.getLocationAt(0, 0)).isNotEqualTo(GameBoardLocationType.MINE);
    }

    @Test
    @DisplayName("Location type on generated board shouldn't be open.")
    void locationTypeOnGeneratedBoardShouldntBeOpenTest() {
        int width = 16, height = 40;
        int totalMines = width * height;

        var gameBoard = new GameBoard(width, height, totalMines, new SimpleGameBoardGenerator());

        assertThat(gameBoard.getLocationAt(0, 0).isClosed()).isTrue();
    }

    @Test
    @DisplayName("First location opened shouldn't be a mine.")
    void firstLocationOpenedShouldntBeAMineTest() {
        int width = 16, height = 40;
        int totalMines = width * height - 1;
        var boardGenerator = new SimpleGameBoardGenerator();

        var gameBoard = new GameBoard(width, height, totalMines, boardGenerator);

        gameBoard.setBoardAsString(
                boardGenerator.ensureSafeStartLocation(gameBoard, 13, 13)
        );
        assertThat(gameBoard.getLocationAt(13, 13)).isNotEqualTo(GameBoardLocationType.MINE);

        gameBoard.setBoardAsString(
                boardGenerator.ensureSafeStartLocation(gameBoard, 1, 17)
        );
        assertThat(gameBoard.getLocationAt(1, 17)).isNotEqualTo(GameBoardLocationType.MINE);
    }
}
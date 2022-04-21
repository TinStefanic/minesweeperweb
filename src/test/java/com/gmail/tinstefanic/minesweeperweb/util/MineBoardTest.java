package com.gmail.tinstefanic.minesweeperweb.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.data.util.Pair;

import static org.assertj.core.api.Assertions.assertThat;

class MineBoardTest {

    @Test
    @DisplayName("Optimal starting coords should be (2, 1).")
    void optimalStartingCoordsShouldBe00Test() {
        String boardString = """
                        X___
                        ____
                        ____
                        X__X
                        """;
        int width = 4, height = 4;

        var mineBoard = MineBoard.fromString(width, height, boardString);
        Pair<Integer, Integer> actualCoords = mineBoard.getOptimalStartingCoords();

        assertThat(actualCoords).isEqualTo(Pair.of(2, 1));
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 8, -2, -7})
    @DisplayName("RightShiftColumnsShouldShiftColumnsCorrectly.")
    void rightShiftColumnsShouldShiftColumnsCorrectlyTest(int offset) {
        String boardString = """
                        _X___
                        ____X
                        """;
        int width = 5, height = 2;

        var initialMineBoard = MineBoard.fromString(width, height, boardString);
        var actualMineBoard = initialMineBoard.rightShiftColumns(offset);

        String expectedBoardString = """
                        ____X
                        __X__
                        """;
        var expectedMineBoard = MineBoard.fromString(width, height, expectedBoardString);
        assertThat(actualMineBoard).isEqualTo(expectedMineBoard);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, -3, -7})
    @DisplayName("RightShiftRowsShouldShiftRowsCorrectly.")
    void rightShiftRowsShouldShiftRowsCorrectlyTest(int offset) {
        String boardString = """
                        __
                        X_
                        __
                        _X
                        """;
        int width = 2, height = 4;

        var initialMineBoard = MineBoard.fromString(width, height, boardString);
        var actualMineBoard = initialMineBoard.rightShiftRows(offset);

        String expectedBoardString = """
                        _X
                        __
                        X_
                        __
                        """;
        var expectedMineBoard = MineBoard.fromString(width, height, expectedBoardString);
        assertThat(actualMineBoard).isEqualTo(expectedMineBoard);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 7, 13, 40})
    @DisplayName("RandomMineBoardShouldHaveCorrectNumberOfMines.")
    void randomMineBoardShouldHaveCorrectNumberOfMinesTest(int numMines) {
        int width = 20, height = 20;

        var mineBoard = MineBoard.createRandomMineBoard(width, height, numMines);
        int actualNumMines = (int)mineBoard.toString().chars().filter(c -> c == 'X').count();

        assertThat(actualNumMines).isEqualTo(numMines);
    }
}
package com.gmail.tinstefanic.minesweeperweb.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class GameBoardServiceTest {

    @ParameterizedTest
    @ValueSource(strings = {"easy", "normal", "hard"})
    @DisplayName("isValidDifficulty should return true for valid difficulties.")
    void isValidDifficulty_shouldReturnTrueForValidDifficultiesTest(String s) {
        var gameService = new GameBoardBoardService();

        assertThat(gameService.isValidDifficulty(s)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"not_easy", "", "harrrd"})
    @DisplayName("isValidDifficulty should return false for invalid difficulties.")
    void isValidDifficulty_shouldReturnFalseForInvalidDifficultiesTest(String s) {
        var gameService = new GameBoardBoardService();

        assertThat(gameService.isValidDifficulty(s)).isFalse();
    }

    @Test
    @DisplayName("getNewGameBoard should return 9x9 board for easy.")
    void getNewGameBoard_shouldReturn9x9boardForEasyTest() {
        var gameService = new GameBoardBoardService();
        var difficulty = "easy";
        var gameBoard = gameService.getNewGameBoard(difficulty);

        assertThat(gameBoard.getWidth()).isEqualTo(9);
        assertThat(gameBoard.getHeight()).isEqualTo(9);
    }

    @Test
    @DisplayName("getNewGameBoard should return 12x17 board for normal.")
    void getNewGameBoard_shouldReturn12x17boardForNormalTest() {
        var gameService = new GameBoardBoardService();
        var difficulty = "normal";
        var gameBoard = gameService.getNewGameBoard(difficulty);

        assertThat(gameBoard.getWidth()).isEqualTo(12);
        assertThat(gameBoard.getHeight()).isEqualTo(17);
    }
}
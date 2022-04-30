package com.gmail.tinstefanic.minesweeperweb.services;

import com.gmail.tinstefanic.minesweeperweb.util.GameDifficulty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GameBoardGeneratorServiceTest {
    @Autowired
    GameBoardGeneratorService gameService;

    @ParameterizedTest
    @ValueSource(strings = {"easy", "normal", "hard"})
    @DisplayName("isValidDifficulty should return true for valid difficulties.")
    void isValidDifficulty_shouldReturnTrueForValidDifficultiesTest(String s) {
        assertThat(this.gameService.isValidDifficulty(s)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"not_easy", "", "harrrd"})
    @DisplayName("isValidDifficulty should return false for invalid difficulties.")
    void isValidDifficulty_shouldReturnFalseForInvalidDifficultiesTest(String s) {
        assertThat(this.gameService.isValidDifficulty(s)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"easy", "normal", "hard"})
    @DisplayName("getNewGameBoard should return board for correct difficulty.")
    void getNewGameBoard_shouldReturnBoardForCorrectDifficultyTest(String difficultyString) {
        var gameBoard = this.gameService.getNewGameBoard(difficultyString, "");

        var gameDifficulty = GameDifficulty.valueOf(difficultyString.toUpperCase());

        assertThat(gameBoard.getWidth()).isEqualTo(gameDifficulty.getWidth());
        assertThat(gameBoard.getHeight()).isEqualTo(gameDifficulty.getHeight());
    }
}
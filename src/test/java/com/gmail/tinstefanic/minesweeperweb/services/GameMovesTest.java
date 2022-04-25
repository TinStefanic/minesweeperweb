package com.gmail.tinstefanic.minesweeperweb.services;

import com.gmail.tinstefanic.minesweeperweb.entities.GameBoard;
import com.gmail.tinstefanic.minesweeperweb.repositories.GameBoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.security.Principal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameMovesTest {

    @Autowired
    GameMovesFactoryService gameMovesFactoryService;

    @MockBean
    GameBoardRepository gameBoardRepository;

    @Test
    @DisplayName("Should return false when target GameBoard doesn't exist in database.")
    void shouldReturnFalseWhenTargetGameBoardDoesntExistInDatabaseTest() {
        long id = 17;

        when(this.gameBoardRepository.findById(id)).thenReturn(Optional.empty());
        var gameMoves = this.gameMovesFactoryService.fromGameBoardId(id);

        assertThat(gameMoves.doesGameBoardExist()).isFalse();
    }

    @Test
    @DisplayName("User shouldn't be able to perform actions on someone else's GameBoard")
    void userShouldntBeAbleToPerformActionsOnSomeoneElsesGameBoardTest() {
        long id = 17;
        int width = 10, height = 10, totalMines = 10;
        var gameBoard = new GameBoard(width, height, totalMines);
        gameBoard.setUsername("user1");
        Principal principal = mock(Principal.class);

        when(principal.getName()).thenReturn("user2");
        when(this.gameBoardRepository.findById(id)).thenReturn(Optional.of(gameBoard));
        var gameMoves = this.gameMovesFactoryService.fromGameBoardId(id);

        assertThat(gameMoves.canAccessGameBoard(principal)).isFalse();
    }

    @Test
    @DisplayName("User should be able to perform actions on own GameBoard")
    void userShouldBeAbleToPerformActionsOnOwnGameBoardTest() {
        long id = 17;
        int width = 10, height = 10, totalMines = 10;
        var gameBoard = new GameBoard(width, height, totalMines);
        gameBoard.setUsername("user");
        Principal principal = mock(Principal.class);

        when(principal.getName()).thenReturn("user");
        when(this.gameBoardRepository.findById(id)).thenReturn(Optional.of(gameBoard));
        var gameMoves = this.gameMovesFactoryService.fromGameBoardId(id);

        assertThat(gameMoves.canAccessGameBoard(principal)).isTrue();
    }
}
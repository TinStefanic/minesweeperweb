package com.gmail.tinstefanic.minesweeperweb.controllers;

import com.gmail.tinstefanic.minesweeperweb.entities.GameBoard;
import com.gmail.tinstefanic.minesweeperweb.repositories.GameBoardRepository;
import com.gmail.tinstefanic.minesweeperweb.services.GameMovesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class GameApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameMovesService gameMovesService;

    @MockBean
    private GameBoardRepository gameBoardRepository;

    @Test
    @DisplayName("If GameBoard doesn't belong to user returns status 403.")
    @WithMockUser(username = "user")
    void ifGameBoardDoesntBelongToUserReturnsStatus403Test() throws Exception {
        long id = 17;
        int width = 10, height = 10, totalMines = 10;
        var gameBoard = new GameBoard(width, height, totalMines);
        gameBoard.setUsername("notuser");

        when(gameBoardRepository.findById(id)).thenReturn(Optional.of(gameBoard));

        this.mockMvc.perform(
                get("/api/open/" + String.valueOf(id))
                        .param("x", "1")
                        .param("y", "1")
        ).andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("If GameBoard belongs to user returns status 200.")
    @WithMockUser(username = "user")
    void ifGameBoardBelongsToUserReturnsStatus200Test() throws Exception {
        long id = 17;
        int width = 10, height = 10, totalMines = 10;
        var gameBoard = new GameBoard(width, height, totalMines);
        gameBoard.setUsername("user");

        when(gameBoardRepository.findById(id)).thenReturn(Optional.of(gameBoard));

        this.mockMvc.perform(
                get("/api/open/" + String.valueOf(id))
                        .param("x", "1")
                        .param("y", "1")
        ).andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource({"-1,3", "3,-4", "10,5", "7,12", "-2,13"})
    @DisplayName("If coordinates are outside of GameBoard return Bad request.")
    @WithMockUser(username = "user")
    void ifCoordinatesAreOutsideOfGameBoardReturnBadRequestTest(String x, String y) throws Exception {
        long id = 17;
        int width = 10, height = 10, totalMines = 10;
        var gameBoard = new GameBoard(width, height, totalMines);
        gameBoard.setUsername("user");

        when(gameBoardRepository.findById(id)).thenReturn(Optional.of(gameBoard));

        this.mockMvc.perform(
                get("/api/open/" + String.valueOf(id))
                        .param("x", x)
                        .param("y", y)
        ).andExpect(status().isBadRequest());
    }
}
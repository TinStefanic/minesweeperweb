package com.gmail.tinstefanic.minesweeperweb.services;

import com.gmail.tinstefanic.minesweeperweb.entities.GameBoard;
import com.gmail.tinstefanic.minesweeperweb.exceptions.LocationOutOfGameBoardBoundsException;
import com.gmail.tinstefanic.minesweeperweb.repositories.GameBoardRepository;
import com.gmail.tinstefanic.minesweeperweb.services.gameboard.IGameBoardGenerator;
import com.gmail.tinstefanic.minesweeperweb.services.gamemoves.GameMoves;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.security.Principal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
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
    @DisplayName("User shouldn't be able to perform actions on someone else's GameBoard.")
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
    @DisplayName("User should be able to perform actions on own GameBoard.")
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

    @ParameterizedTest
    @CsvSource({"-1,3", "5,-3", "13,8", "4,17", "10,1"})
    @DisplayName("Should throw exception if coordinates are outside of GameBoard.")
    void shouldThrowExceptionIfCoordinatesAreOutsideOfGameBoardTest(int x, int y) {
        long id = 17;
        int width = 10, height = 10, totalMines = 10;
        var gameBoard = new GameBoard(width, height, totalMines);

        when(this.gameBoardRepository.findById(id)).thenReturn(Optional.of(gameBoard));
        var gameMoves = this.gameMovesFactoryService.fromGameBoardId(id);

        assertThatExceptionOfType(LocationOutOfGameBoardBoundsException.class)
                .isThrownBy(() -> gameMoves.openLocation(x, y));
    }

    @ParameterizedTest
    @CsvSource({"0,0", "1,1", "2,2"})
    @DisplayName("First location opened shouldn't be a mine.")
    void firstLocationOpenedShouldntBeAMineTest(int x, int y) throws LocationOutOfGameBoardBoundsException {
        long id = 17;
        int width = 3, height = 3, totalMines = 2;
        IGameBoardGenerator gameBoardGenerator = mock(IGameBoardGenerator.class);
        when(gameBoardGenerator.generateInitialGameBoardString(width, height, totalMines))
                .thenReturn("""
                        BBB
                        BXC
                        BCX
                        """);
        var gameBoard = new GameBoard(width, height, totalMines, gameBoardGenerator);

        when(this.gameBoardRepository.findById(id)).thenReturn(Optional.of(gameBoard));
        var gameMoves = this.gameMovesFactoryService.fromGameBoardId(id);

        assertThat(gameMoves.openLocation(x, y).isMine()).isFalse();
    }

    @ParameterizedTest
    @CsvSource({"2,0,1", "1,0,2", "2,2,1", "0,2,1", "0,1,2"})
    @DisplayName("Location opened should be surrounded by n mines.")
    void locationOpenedShouldBeSurroundedByNMinesTest(int x, int y, int n)
            throws LocationOutOfGameBoardBoundsException
    {
        long id = 17;
        int width = 3, height = 3, totalMines = 2;
        String board = """
                        XCB
                        CXB
                        BBB
                        """;

        IGameBoardGenerator gameBoardGenerator = mock(IGameBoardGenerator.class);
        when(gameBoardGenerator.ensureSafeStartLocation(any(GameBoard.class), anyInt(), anyInt())).thenReturn(board);
        when(gameBoardGenerator.generateInitialGameBoardString(width, height, totalMines)).thenReturn(board);
        var gameBoard = new GameBoard(width, height, totalMines, gameBoardGenerator);

        when(this.gameBoardRepository.findById(id)).thenReturn(Optional.of(gameBoard));
        var gameMoves = new GameMoves(id, this.gameBoardRepository, gameBoardGenerator);

        assertThat(gameMoves.openLocation(x, y).getNumNeighbouringMines()).isEqualTo(n);
    }

    @Test
    @DisplayName("First move should be recognized as first move.")
    void firstMoveShouldBeRecognizedAsFirstMoveTest() throws LocationOutOfGameBoardBoundsException {
        long id = 17;
        int width = 10, height = 10, totalMines = 10;
        var gameBoard = new GameBoard(width, height, totalMines);

        when(this.gameBoardRepository.findById(id)).thenReturn(Optional.of(gameBoard));
        var gameMoves = this.gameMovesFactoryService.fromGameBoardId(id);

        assertThat(gameMoves.openLocation(0, 0).isFirstMove()).isTrue();
    }

    @Test
    @DisplayName("Second move shouldn't be recognized as first move.")
    void secondMoveShouldntBeRecognizedAsFirstMoveTest() throws LocationOutOfGameBoardBoundsException {
        long id = 17;
        int width = 10, height = 10, totalMines = 10;
        var gameBoard = new GameBoard(width, height, totalMines);

        when(this.gameBoardRepository.findById(id)).thenReturn(Optional.of(gameBoard));
        var gameMoves = this.gameMovesFactoryService.fromGameBoardId(id);
        gameMoves.openLocation(0, 0);

        assertThat(gameMoves.openLocation(1, 1).isFirstMove()).isFalse();
    }

    @Test
    @DisplayName("Game shouldn't be over after the first move.")
    void gameShouldntBeOverAfterTheFirstMoveTest() throws LocationOutOfGameBoardBoundsException {
        long id = 17;
        int width = 10, height = 10, totalMines = 10;
        var gameBoard = new GameBoard(width, height, totalMines);

        when(this.gameBoardRepository.findById(id)).thenReturn(Optional.of(gameBoard));
        var gameMoves = this.gameMovesFactoryService.fromGameBoardId(id);

        assertThat(gameMoves.openLocation(1, 1).isGameOver()).isFalse();
    }

    @Test
    @DisplayName("Game should be over after opening a mine.")
    void gameShouldBeOverAfterOpeningAMineTest() throws LocationOutOfGameBoardBoundsException {
        long id = 17;
        int width = 3, height = 3, totalMines = 2;
        String board = """
                        XCB
                        CXB
                        BBB
                        """;

        IGameBoardGenerator gameBoardGenerator = mock(IGameBoardGenerator.class);
        when(gameBoardGenerator.ensureSafeStartLocation(any(GameBoard.class), anyInt(), anyInt())).thenReturn(board);
        when(gameBoardGenerator.generateInitialGameBoardString(width, height, totalMines)).thenReturn(board);
        var gameBoard = new GameBoard(width, height, totalMines, gameBoardGenerator);

        when(this.gameBoardRepository.findById(id)).thenReturn(Optional.of(gameBoard));
        var gameMoves = new GameMoves(id, this.gameBoardRepository, gameBoardGenerator);

        assertThat(gameMoves.openLocation(1, 1).isGameOver()).isTrue();
    }

    @Test
    @DisplayName("Game should be over after opening all non mine locations, but not before.")
    void gameShouldBeOverAfterOpeningAllNonMineLocationsButNotBeforeTest()
            throws LocationOutOfGameBoardBoundsException
    {
        long id = 17;
        int width = 2, height = 2, totalMines = 2;
        String board = """
                        XC
                        CX
                        """;

        IGameBoardGenerator gameBoardGenerator = mock(IGameBoardGenerator.class);
        when(gameBoardGenerator.ensureSafeStartLocation(any(GameBoard.class), anyInt(), anyInt())).thenReturn(board);
        when(gameBoardGenerator.generateInitialGameBoardString(width, height, totalMines)).thenReturn(board);
        var gameBoard = new GameBoard(width, height, totalMines, gameBoardGenerator);

        when(this.gameBoardRepository.findById(id)).thenReturn(Optional.of(gameBoard));
        var gameMoves = new GameMoves(id, this.gameBoardRepository, gameBoardGenerator);

        assertThat(gameMoves.openLocation(1, 0).isGameOver()).isFalse();
        assertThat(gameMoves.openLocation(0, 1).isGameOver()).isTrue();
    }
}
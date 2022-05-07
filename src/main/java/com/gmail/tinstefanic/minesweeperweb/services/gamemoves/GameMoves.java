package com.gmail.tinstefanic.minesweeperweb.services.gamemoves;

import com.gmail.tinstefanic.minesweeperweb.dtos.OpenLocationDto;
import com.gmail.tinstefanic.minesweeperweb.entities.GameBoard;
import com.gmail.tinstefanic.minesweeperweb.exceptions.GameOverGameBoardModifiedException;
import com.gmail.tinstefanic.minesweeperweb.exceptions.LocationOutOfGameBoardBoundsException;
import com.gmail.tinstefanic.minesweeperweb.repositories.GameBoardRepository;
import com.gmail.tinstefanic.minesweeperweb.services.gameboard.GameBoardLocationType;
import com.gmail.tinstefanic.minesweeperweb.services.gameboard.IGameBoardGenerator;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

public class GameMoves {
    private final GameBoardRepository gameBoardRepository;
    @Getter
    private GameBoard gameBoard;
    private final boolean doesGameBoardExist;
    private final IGameBoardGenerator gameBoardGenerator;
    private boolean hasPlayerWon = false;

    /**
     * Creates new GameMoves class.
     * @param gameBoardId Id of GameBoard on which game moves should be performed.
     * @param gameBoardRepository Repository to access stored GameBoards.
     * @param gameBoardGenerator Used to modify GameBoard so that first time location is opened
     *                           that location is guaranteed not to be mine.
     */
    public GameMoves(
            long gameBoardId,
            GameBoardRepository gameBoardRepository,
            IGameBoardGenerator gameBoardGenerator)
    {
        this.gameBoardRepository = gameBoardRepository;
        this.gameBoardGenerator = gameBoardGenerator;

        Optional<GameBoard> optGameBoard = gameBoardRepository.findById(gameBoardId);

        if (optGameBoard.isPresent()) {
            this.gameBoard = optGameBoard.get();
            this.doesGameBoardExist = true;
        } else {
            this.doesGameBoardExist = false;
        }
    }

    /**
     * Checks does the GameBoard on which game moves should be performed exist in the database.
     * @return True if GameBoard exists.
     */
    public boolean doesGameBoardExist() {
        return this.doesGameBoardExist;
    }

    /**
     * Checks if the player has opened all non mine locations.
     * Returns false if provided GameBoard was already completed.
     * @return Did the player win.
     */
    public boolean hasPlayerWon() {
        return this.hasPlayerWon;
    }

    /**
     * Checks can the principal perform actions on the GameBoard.
     * @param principal Principal whose access should be tested.
     * @return True if principal can perform actions on the GameBoard, false otherwise.
     */
    public boolean canAccessGameBoard(Principal principal) {
        return this.gameBoard.getUsername().equals(principal.getName());
    }

    /**
     * Opens location (x, y) on GameBoard and returns result of the action.
     * If location is already opened throws exception.
     * @param x Column on the GameBoard in range [0, width-1].
     * @param y Row on the GameBoard in range [0, height-1].
     * @return Result of opening location.
     * @throws LocationOutOfGameBoardBoundsException Thrown if given x and/or y are outside GameBoard.
     */
    @Transactional
    public OpenLocationDto openLocation(int x, int y) throws LocationOutOfGameBoardBoundsException {
        if (this.gameBoard.isGameOver()) {
            throw new GameOverGameBoardModifiedException(
                    "Cannot perform action 'openLocation' on GameBoard with id '" +
                    this.gameBoard.getId() +
                    "' because GameBoard is in game over state."
            );
        }

        if (x < 0 || x >= this.gameBoard.getWidth() ||
            y < 0 || y >= this.gameBoard.getHeight())
        {
            throw new LocationOutOfGameBoardBoundsException(
                    "Given coordinates (" + x + ", " + y + ") are outside GameBoard dimensions (" +
                    this.gameBoard.getWidth() + "x" + this.gameBoard.getHeight() + "."
            );
        }

        boolean isFirstMove = !this.gameBoard.isGameStarted();
        if (isFirstMove) {
            ensureSafeFirstMoveAndStartGame(x, y);
        }

        GameBoardLocationType locationType = this.gameBoard.getLocationAt(x, y);

        if (locationType == GameBoardLocationType.MINE) {
            return makeGameFailedAndReturnResponse(isFirstMove);
        } else if (locationType.isClosed()) {
            return openLocationAndReturnResponse(x, y, isFirstMove);
        } else {
            return ReturnResponse(x, y);
        }
    }

    private void ensureSafeFirstMoveAndStartGame(int x, int y) {
        String safeGameBoardString = this.gameBoardGenerator.ensureSafeStartLocation(this.gameBoard, x, y);
        this.gameBoard.setBoardAsString(safeGameBoardString);

        this.gameBoard.setStartTimeMillis(System.currentTimeMillis());

        this.gameBoard.setGameStarted(true);
    }

    private OpenLocationDto makeGameFailedAndReturnResponse(boolean isFirstMove) {
        this.gameBoard.setGameOver(true);
        boolean isMine = true, isGameOver = true;
        int numNeighbouringMines = -1;

        this.gameBoardRepository.save(this.gameBoard);

        return new OpenLocationDto(isMine, numNeighbouringMines, isFirstMove, isGameOver);
    }

    private OpenLocationDto openLocationAndReturnResponse(int x, int y, boolean isFirstMove) {
        this.gameBoard.setRemainingClosedSafeFields(this.gameBoard.getRemainingClosedSafeFields() - 1);
        if (this.gameBoard.getRemainingClosedSafeFields() == 0) {
            this.gameBoard.setGameOver(true);
            this.hasPlayerWon = true;
        }

        boolean isMine = false, isGameOver = this.gameBoard.isGameOver();
        int numNeighbouringMines = this.gameBoard.getLocationAt(x, y).getNumSurroundingMines();

        this.gameBoard.openLocationAt(x, y);
        this.gameBoardRepository.save(this.gameBoard);

        return new OpenLocationDto(isMine, numNeighbouringMines, isFirstMove, isGameOver);
    }

    private OpenLocationDto ReturnResponse(int x, int y) {
        boolean isMine = false, isGameOver = this.gameBoard.isGameOver(), isFirstMove = false;
        int numNeighbouringMines = this.gameBoard.getLocationAt(x, y).getNumSurroundingMines();

        return new OpenLocationDto(isMine, numNeighbouringMines, isFirstMove, isGameOver);
    }
}

package com.gmail.tinstefanic.minesweeperweb.services.gamemoves;

import com.gmail.tinstefanic.minesweeperweb.entities.GameBoard;
import com.gmail.tinstefanic.minesweeperweb.exceptions.LocationOutOfGameBoardBoundsException;
import com.gmail.tinstefanic.minesweeperweb.exceptions.NotImplementedException;
import com.gmail.tinstefanic.minesweeperweb.models.OpenLocationResponse;
import com.gmail.tinstefanic.minesweeperweb.repositories.GameBoardRepository;
import com.gmail.tinstefanic.minesweeperweb.services.gameboard.IGameBoardGenerator;

import java.security.Principal;
import java.util.Optional;

public class GameMoves {
    private GameBoardRepository gameBoardRepository;
    private GameBoard gameBoard;
    private boolean doesGameBoardExist;
    private IGameBoardGenerator gameBoardGenerator;

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
        throw new NotImplementedException();
    }

    /**
     * Checks can the principal perform actions on the GameBoard.
     * @param principal Principal whose access should be tested.
     * @return True if principal can perform actions on the GameBoard, false otherwise.
     */
    public boolean canAccessGameBoard(Principal principal) {
        throw new NotImplementedException();
    }

    /**
     * Opens location (x, y) on GameBoard and returns result of the action.
     * @param x Column on the GameBoard in range [0, width-1].
     * @param y Row on the GameBoard in range [0, height-1].
     * @return Result of opening location.
     * @throws LocationOutOfGameBoardBoundsException Thrown if given x and/or y are outside GameBoard.
     */
    public OpenLocationResponse openLocation(int x, int y) throws LocationOutOfGameBoardBoundsException {
        throw new NotImplementedException();
    }
}

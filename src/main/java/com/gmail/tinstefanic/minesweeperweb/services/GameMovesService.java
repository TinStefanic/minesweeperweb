package com.gmail.tinstefanic.minesweeperweb.services;

import com.gmail.tinstefanic.minesweeperweb.exceptions.LocationOutOfGameBoardBoundsException;
import com.gmail.tinstefanic.minesweeperweb.exceptions.NotImplementedException;
import com.gmail.tinstefanic.minesweeperweb.models.OpenLocationResponse;
import com.gmail.tinstefanic.minesweeperweb.repositories.GameBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;

public class GameMovesService {
    @Autowired
    private GameBoardRepository gameBoardRepository;

    /**
     * Checks can the principal perform actions on the GameBoard with given gameBoardId.
     * @param principal Principal whose access should be tested.
     * @param gameBoardId Id of the GameBoard.
     * @return True if principal can perform actions on the GameBoard, false otherwise.
     */
    public boolean canAccessGameBoard(Principal principal, long gameBoardId) {
        throw new NotImplementedException();
    }

    /**
     * Opens location (x, y) on GameBoard with id gameBoardId and returns result of the action.
     * @param x Column on the GameBoard in range [0, width-1].
     * @param y Row on the GameBoard in range [0, height-1].
     * @param gameBoardId Id of the GameBoard.
     * @return Result of opening location.
     * @throws LocationOutOfGameBoardBoundsException Thrown if given x and/or y are outside GameBoard.
     */
    public OpenLocationResponse openLocation(int x, int y, long gameBoardId)
            throws LocationOutOfGameBoardBoundsException {
        throw new NotImplementedException();
    }
}

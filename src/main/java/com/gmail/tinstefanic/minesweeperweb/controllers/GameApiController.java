package com.gmail.tinstefanic.minesweeperweb.controllers;

import com.gmail.tinstefanic.minesweeperweb.exceptions.GameOverGameBoardModifiedException;
import com.gmail.tinstefanic.minesweeperweb.exceptions.LocationOutOfGameBoardBoundsException;
import com.gmail.tinstefanic.minesweeperweb.dtos.OpenLocationDto;
import com.gmail.tinstefanic.minesweeperweb.services.AddToLeaderboardService;
import com.gmail.tinstefanic.minesweeperweb.services.GameMovesFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
public class GameApiController {

    private final GameMovesFactoryService gameMovesFactoryService;
    private final AddToLeaderboardService addToLeaderBoardService;

    @Autowired
    public GameApiController(
            GameMovesFactoryService gameMovesFactoryService,
            AddToLeaderboardService addToLeaderBoardService)
    {
        this.gameMovesFactoryService = gameMovesFactoryService;
        this.addToLeaderBoardService = addToLeaderBoardService;
    }

    @GetMapping("/api/open/{id}")
    public OpenLocationDto openLocation(
            @PathVariable("id") long gameBoardId,
            @RequestParam("x") int x,
            @RequestParam("y") int y,
            Principal principal
    ) {
        var gameMoves = this.gameMovesFactoryService.fromGameBoardId(gameBoardId);

        if (!gameMoves.doesGameBoardExist()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (!gameMoves.canAccessGameBoard(principal)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        try {
            OpenLocationDto response = gameMoves.openLocation(x, y);
            if (gameMoves.hasPlayerWon()) this.addToLeaderBoardService.addToLeaderBoard(gameBoardId);
            return response;
        } catch (LocationOutOfGameBoardBoundsException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Provided coordinates are outside the game board.",
                    e
            );
        } catch (GameOverGameBoardModifiedException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Game is already over.",
                    e
            );
        }
    }
}

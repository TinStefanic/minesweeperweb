package com.gmail.tinstefanic.minesweeperweb.controllers;

import com.gmail.tinstefanic.minesweeperweb.exceptions.LocationOutOfGameBoardBoundsException;
import com.gmail.tinstefanic.minesweeperweb.models.OpenLocationResponse;
import com.gmail.tinstefanic.minesweeperweb.services.GameMovesFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
public class GameApiController {

    private final GameMovesFactoryService gameMovesFactoryService;

    public GameApiController(@Autowired GameMovesFactoryService gameMovesFactoryService) {

        this.gameMovesFactoryService = gameMovesFactoryService;
    }

    @GetMapping("/api/open/{id}")
    public OpenLocationResponse openLocation(
            @PathVariable("id") long gameBoardId,
            @RequestParam("x") int x,
            @RequestParam("y") int y,
            Principal principal
    ) {
        var gameMoves = gameMovesFactoryService.fromGameBoardId(gameBoardId);

        if (!gameMoves.doesGameBoardExist()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (!gameMoves.canAccessGameBoard(principal)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        try {
            return gameMoves.openLocation(x, y);
        } catch (LocationOutOfGameBoardBoundsException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Provided coordinates are outside the game board",
                    e
            );
        }
    }
}

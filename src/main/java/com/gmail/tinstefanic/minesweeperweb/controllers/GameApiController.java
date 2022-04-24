package com.gmail.tinstefanic.minesweeperweb.controllers;

import com.gmail.tinstefanic.minesweeperweb.exceptions.NotImplementedException;
import com.gmail.tinstefanic.minesweeperweb.models.OpenLocationResponse;
import com.gmail.tinstefanic.minesweeperweb.services.GameMovesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class GameApiController {

    private final GameMovesService gameMovesService;

    public GameApiController(@Autowired GameMovesService gameMovesService) {

        this.gameMovesService = gameMovesService;
    }

    @GetMapping("/api/open/{id}")
    public OpenLocationResponse openLocation(
            @PathVariable("id") long gameBoardId,
            @RequestParam("x") int x,
            @RequestParam("y") int y,
            Principal principal
    ) {
        throw new NotImplementedException();
    }
}

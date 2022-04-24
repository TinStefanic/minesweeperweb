package com.gmail.tinstefanic.minesweeperweb.controllers;

import com.gmail.tinstefanic.minesweeperweb.services.IGameBoardGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class GameMenuController {

    private final IGameBoardGeneratorService gameService;

    @Autowired
    public GameMenuController(IGameBoardGeneratorService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/game/{difficulty}")
    public String game(@PathVariable String difficulty, Model model, Principal principal) {
        if (!this.gameService.isValidDifficulty(difficulty))
            return "redirect:/menu";

        var gameBoard = this.gameService.getNewGameBoard(difficulty);
        gameBoard.setUsername(principal.getName());

        model.addAttribute("gameBoard", gameBoard);

        // Easy and normal difficulty share page.
        return "game_" + ("easy".equals(difficulty) ? "normal" : difficulty);
    }

    @GetMapping("/difficulty")
    public String difficulty() {
        return "difficulty";
    }
}

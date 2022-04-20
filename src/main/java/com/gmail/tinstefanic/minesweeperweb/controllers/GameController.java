package com.gmail.tinstefanic.minesweeperweb.controllers;

import com.gmail.tinstefanic.minesweeperweb.services.IGameBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GameController {

    private final IGameBoardService gameService;

    @Autowired
    public GameController(IGameBoardService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/game/{difficulty}")
    public String game(@PathVariable String difficulty, Model model) {
        if (!this.gameService.isValidDifficulty(difficulty))
            return "redirect:/menu";
        model.addAttribute("gameBoard", this.gameService.getNewGameBoard(difficulty));
        return "game/" + difficulty;
    }

    @GetMapping("/difficulty")
    public String difficulty() {
        return "difficulty";
    }
}

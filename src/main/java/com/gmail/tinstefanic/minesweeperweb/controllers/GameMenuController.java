package com.gmail.tinstefanic.minesweeperweb.controllers;

import com.gmail.tinstefanic.minesweeperweb.repositories.GameBoardRepository;
import com.gmail.tinstefanic.minesweeperweb.services.IGameBoardGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class GameMenuController {

    private final IGameBoardGeneratorService gameBoardGeneratorService;
    private final GameBoardRepository gameBoardRepository;

    @Autowired
    public GameMenuController(
            IGameBoardGeneratorService gameBoardGeneratorService,
            GameBoardRepository gameBoardRepository)
    {
        this.gameBoardGeneratorService = gameBoardGeneratorService;
        this.gameBoardRepository = gameBoardRepository;
    }

    @GetMapping("/game/{difficulty}")
    public String game(@PathVariable String difficulty, Model model, Principal principal) {
        if (!this.gameBoardGeneratorService.isValidDifficulty(difficulty))
            return "redirect:/menu";

        var gameBoard = this.gameBoardGeneratorService.getNewGameBoard(difficulty, principal.getName());
        gameBoard.setUsername(principal.getName());
        this.gameBoardRepository.save(gameBoard);

        model.addAttribute("gameBoard", gameBoard);

        return "game";
    }

    @GetMapping("/difficulty")
    public String difficulty() {
        return "difficulty";
    }
}

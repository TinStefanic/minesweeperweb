package com.gmail.tinstefanic.minesweeperweb.services;

import com.gmail.tinstefanic.minesweeperweb.entities.GameBoard;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameBoardGeneratorService implements IGameBoardGeneratorService {
    @Override
    public GameBoard getNewGameBoard(String difficulty) {
        // GameBoard(width, height, totalMines).
        if ("easy".equals(difficulty)) return new GameBoard(9, 9, 10);
        if ("normal".equals(difficulty)) return new GameBoard(12, 17, 40);
        if ("hard".equals(difficulty)) return new GameBoard(16, 40, 99);
        throw new IllegalArgumentException("Difficulty: '" + difficulty + "' is invalid difficulty");
    }

    @Override
    public boolean isValidDifficulty(String difficulty) {
        return List.of("easy", "normal", "hard").contains(difficulty);
    }
}

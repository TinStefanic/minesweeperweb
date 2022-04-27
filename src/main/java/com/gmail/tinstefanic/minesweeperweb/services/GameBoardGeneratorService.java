package com.gmail.tinstefanic.minesweeperweb.services;

import com.gmail.tinstefanic.minesweeperweb.entities.GameBoard;
import com.gmail.tinstefanic.minesweeperweb.util.GameDifficulty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameBoardGeneratorService implements IGameBoardGeneratorService {
    @Override
    public GameBoard getNewGameBoard(String difficultyString) {
        for (GameDifficulty gameDifficulty : GameDifficulty.values())  {
            if (gameDifficulty.lowercaseName().equals(difficultyString))
                return new GameBoard(
                        gameDifficulty.getWidth(),
                        gameDifficulty.getHeight(),
                        gameDifficulty.getTotalMines()
                );
        }

        throw new IllegalArgumentException("Difficulty: '" + difficultyString + "' is invalid difficulty");
    }

    @Override
    public boolean isValidDifficulty(String difficulty) {
        return List.of("easy", "normal", "hard").contains(difficulty);
    }
}

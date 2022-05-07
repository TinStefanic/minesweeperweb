package com.gmail.tinstefanic.minesweeperweb.services;

import com.gmail.tinstefanic.minesweeperweb.entities.GameBoard;
import com.gmail.tinstefanic.minesweeperweb.repositories.GameBoardRepository;
import com.gmail.tinstefanic.minesweeperweb.util.GameDifficulty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameBoardGeneratorService implements IGameBoardGeneratorService {

    private final GameBoardRepository gameBoardRepository;

    @Autowired
    public GameBoardGeneratorService(GameBoardRepository gameBoardRepository) {
        this.gameBoardRepository = gameBoardRepository;
    }

    @Override
    @Transactional
    public GameBoard getNewGameBoard(String difficultyString, String username) {
        for (GameDifficulty gameDifficulty : GameDifficulty.values())  {
            if (gameDifficulty.lowercaseName().equals(difficultyString)) {
                var gameBoard = new GameBoard(
                        gameDifficulty.getWidth(),
                        gameDifficulty.getHeight(),
                        gameDifficulty.getTotalMines()
                );

                gameBoard.setUsername(username);
                gameBoardRepository.save(gameBoard);

                return gameBoard;
            }

        }

        throw new IllegalArgumentException("Difficulty: '" + difficultyString + "' is invalid difficulty");
    }

    @Override
    public boolean isValidDifficulty(String difficulty) {
        return List.of("easy", "normal", "hard").contains(difficulty);
    }
}

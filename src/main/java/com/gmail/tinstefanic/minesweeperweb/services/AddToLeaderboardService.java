package com.gmail.tinstefanic.minesweeperweb.services;

import com.gmail.tinstefanic.minesweeperweb.entities.GameBoard;
import com.gmail.tinstefanic.minesweeperweb.entities.LeaderboardEntry;
import com.gmail.tinstefanic.minesweeperweb.repositories.GameBoardRepository;
import com.gmail.tinstefanic.minesweeperweb.repositories.LeaderboardEntryRepository;
import com.gmail.tinstefanic.minesweeperweb.util.GameDifficulty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddToLeaderboardService {

    private final LeaderboardEntryRepository leaderboardEntryRepository;
    private final GameBoardRepository gameBoardRepository;

    @Autowired
    public AddToLeaderboardService(
            LeaderboardEntryRepository leaderboardEntryRepository,
            GameBoardRepository gameBoardRepository)
    {

        this.leaderboardEntryRepository = leaderboardEntryRepository;
        this.gameBoardRepository = gameBoardRepository;
    }
    /**
     * Adds game to the leaderboard, if not already present.
     * @param gameBoardId Id of GameBoard corresponding to the game.
     */
    public void addToLeaderBoard(Long gameBoardId) {
        if (this.leaderboardEntryRepository.existsById(gameBoardId)) return;

        Optional<GameBoard> optGameBoard = this.gameBoardRepository.findById(gameBoardId);
        if (optGameBoard.isEmpty()) return;
        var gameBoard = optGameBoard.get();

        Optional<GameDifficulty> optGameDifficulty =
                GameDifficulty.fromCoords(gameBoard.getWidth(), gameBoard.getHeight());
        if (optGameDifficulty.isEmpty()) return;

        var leaderboardEntry = new LeaderboardEntry(gameBoard, optGameDifficulty.get().lowercaseName());
        this.leaderboardEntryRepository.save(leaderboardEntry);
    }
}

package com.gmail.tinstefanic.minesweeperweb.services;

import com.gmail.tinstefanic.minesweeperweb.repositories.GameBoardRepository;
import com.gmail.tinstefanic.minesweeperweb.services.gameboard.SimpleGameBoardGenerator;
import com.gmail.tinstefanic.minesweeperweb.services.gamemoves.GameMoves;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameMovesFactoryService {

    private final GameBoardRepository gameBoardRepository;

    @Autowired
    public GameMovesFactoryService(GameBoardRepository gameBoardRepository) {
        this.gameBoardRepository = gameBoardRepository;
    }

    @Transactional(readOnly = true)
    public GameMoves fromGameBoardId(long gameBoardId) {
        return new GameMoves(gameBoardId, this.gameBoardRepository, new SimpleGameBoardGenerator());
    }
}

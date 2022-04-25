package com.gmail.tinstefanic.minesweeperweb.services;

import com.gmail.tinstefanic.minesweeperweb.repositories.GameBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameMovesFactoryService {

    private GameBoardRepository gameBoardRepository;

    public GameMovesFactoryService(@Autowired GameBoardRepository gameBoardRepository) {
        this.gameBoardRepository = gameBoardRepository;
    }

    public GameMoves fromGameBoardId(long gameBoardId) {
        return new GameMoves(gameBoardId, this.gameBoardRepository);
    }
}

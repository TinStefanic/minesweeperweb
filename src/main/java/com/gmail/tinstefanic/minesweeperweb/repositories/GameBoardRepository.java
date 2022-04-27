package com.gmail.tinstefanic.minesweeperweb.repositories;

import com.gmail.tinstefanic.minesweeperweb.entities.GameBoard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameBoardRepository extends CrudRepository<GameBoard, Long> {
}
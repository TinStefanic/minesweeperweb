package com.gmail.tinstefanic.minesweeperweb.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class LeaderboardEntry {
    @Id
    private Long id; // Same id as id of GameBoard to which it is related.
    private long gameDurationMillis;

    private LocalDateTime completionDateTime;

    private String username;
    private String difficulty;

    public LeaderboardEntry() {}

    public LeaderboardEntry(GameBoard gameBoard, String difficultyString) {
        this.id = gameBoard.getId();

        this.gameDurationMillis = System.currentTimeMillis() - gameBoard.getStartTimeMillis();
        this.completionDateTime = LocalDateTime.now();

        this.username = gameBoard.getUsername();
        this.difficulty = difficultyString;
    }
}
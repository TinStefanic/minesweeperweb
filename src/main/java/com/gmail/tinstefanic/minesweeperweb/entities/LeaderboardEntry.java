package com.gmail.tinstefanic.minesweeperweb.entities;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Entity
public class LeaderboardEntry {
    @Id
    private Long id; // Same id as id of GameBoard to which it is related.
    private long gameDurationMillis;

    @Temporal(TemporalType.TIMESTAMP)
    private Date completionDateTime;

    private String username;
    private String difficulty;

    protected LeaderboardEntry() {}

    public LeaderboardEntry(GameBoard gameBoard, String difficultyString) {
        this.id = gameBoard.getId();

        this.gameDurationMillis = System.currentTimeMillis() - gameBoard.getStartTimeMillis();
        this.completionDateTime = new Date();

        this.username = gameBoard.getUsername();
        this.difficulty = difficultyString;
    }
}
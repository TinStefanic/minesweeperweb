package com.gmail.tinstefanic.minesweeperweb.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public String getCompletionDateTimeAsString() {
        return this.completionDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public long getGameDurationSeconds() {
        return Math.round(this.gameDurationMillis / 1000.0);
    }
}
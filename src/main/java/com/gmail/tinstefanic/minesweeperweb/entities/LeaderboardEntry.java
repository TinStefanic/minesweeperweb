package com.gmail.tinstefanic.minesweeperweb.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
public class LeaderboardEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long gameDurationMillis;

    @Temporal(TemporalType.TIMESTAMP)
    private Date completionDateTime;

    private String username;
    private String difficulty;
}
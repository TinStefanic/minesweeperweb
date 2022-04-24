package com.gmail.tinstefanic.minesweeperweb.entities;

import com.gmail.tinstefanic.minesweeperweb.services.gameboard.GameBoardLocationType;
import com.gmail.tinstefanic.minesweeperweb.services.gameboard.IGameBoardGenerator;
import com.gmail.tinstefanic.minesweeperweb.services.gameboard.SimpleGameBoardGenerator;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the minesweeper game board.
 */
@Getter
@Setter
@Entity
public class GameBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int width;
    private int height;
    private int totalMines;
    private long startTime; // Should be initialized after player opens first field.
    @Column(length = 1023)
    private String boardAsString;

    protected GameBoard() {}

    public GameBoard(int width, int height, int totalMines) {
        this(width, height, totalMines, new SimpleGameBoardGenerator());
    }

    public GameBoard(int width, int height, int totalMines, IGameBoardGenerator boardGenerator) {
        this.width = width;
        this.height = height;
        this.totalMines = totalMines;
        this.boardAsString = boardGenerator.generateGameBoardString(this.width, this.height, this.totalMines);
    }

    public GameBoardLocationType getLocationAt(int x, int y) {
        // width + 1 because of new lines.
        return GameBoardLocationType.fromChar(this.boardAsString.charAt(y * (this.width + 1) + x));
    }

    public List<List<GameBoardLocationType>> getRows() {
        var list = new ArrayList<List<GameBoardLocationType>>();

        for (int row = 0; row < this.height; ++row) {
            list.add(
                    this.boardAsString.substring( // -1 because new line is ignored.
                            (this.width + 1) * row, (this.width + 1) * (row + 1) - 1
                    ).chars().mapToObj(c -> GameBoardLocationType.fromChar((char) c)).toList()
            );
        }

        return list;
    }
}

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
 * Username should be set with setter method when GameBoard gets assigned to user.
 */
@Getter
@Setter
@Entity
public class GameBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int width;
    private int height;
    private int totalMines;
    private long startTimeMillis; // Should be initialized after player opens first field.
    @Column(length = 1023)
    private String boardAsString;
    private String username; // Name of user to whom this game board id assigned.
    private int remainingClosedSafeFields;
    private boolean isGameOver = false;

    protected GameBoard() {}

    public GameBoard(int width, int height, int totalMines) {
        this(width, height, totalMines, new SimpleGameBoardGenerator());
    }

    public GameBoard(int width, int height, int totalMines, IGameBoardGenerator boardGenerator) {
        this.width = width;
        this.height = height;
        this.totalMines = totalMines;
        this.remainingClosedSafeFields = this.width * this.height - this.totalMines;
        this.boardAsString = boardGenerator.generateInitialGameBoardString(this.width, this.height, this.totalMines);
    }

    public GameBoardLocationType getLocationAt(int x, int y) {
        // width + 1 because of new lines.
        return GameBoardLocationType.fromChar(this.boardAsString.charAt(y * (this.width + 1) + x));
    }

    public void setLocationAt(int x, int y, GameBoardLocationType locationType) {
        // width + 1 because of new lines.
        this.boardAsString =
                this.boardAsString.substring(0, y * (this.width + 1) + x) +
                locationType.asChar() +
                this.boardAsString.substring(y * (this.width + 1) + x + 1);
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

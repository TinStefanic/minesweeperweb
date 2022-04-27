package com.gmail.tinstefanic.minesweeperweb.util;

import java.util.Optional;

/**
 * Enum for available game difficulties and difficulty specific information.
 */
public enum GameDifficulty {
    EASY, NORMAL, HARD;

    /**
     * Returns GameDifficulty with that has given board dimensions if such exists.
     * @param width Target board width.
     * @param height Target board height.
     * @return Optional containing corresponding GameDifficulty, or empty Optional.
     */
    public static Optional<GameDifficulty> fromCoords(int width, int height) {
        for (var gameDifficulty : GameDifficulty.values()) {
            if (gameDifficulty.getWidth() == width && gameDifficulty.getHeight() == height) {
                return Optional.of(gameDifficulty);
            }
        }

        return Optional.empty();
    }

    public int getWidth() {
        return switch (this) {
            case EASY -> 9;
            case NORMAL, HARD -> 12;
        };
    }

    public int getHeight() {
        return switch (this) {
            case EASY -> 9;
            case NORMAL-> 17;
            case HARD -> 50;
        };
    }

    public int getTotalMines() {
        return switch (this) {
            case EASY -> 10;
            case NORMAL-> 40;
            case HARD -> 99;
        };
    }

    public String lowercaseName() {
        return name().toLowerCase();
    }
}

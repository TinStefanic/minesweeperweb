package com.gmail.tinstefanic.minesweeperweb.exceptions;

public class LocationAlreadyOpenedException extends RuntimeException {
    public LocationAlreadyOpenedException() {
        super();
    }

    public LocationAlreadyOpenedException(String message) {
        super(message);
    }
}

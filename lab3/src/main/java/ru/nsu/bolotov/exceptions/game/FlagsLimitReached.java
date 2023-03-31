package ru.nsu.bolotov.exceptions.game;

import ru.nsu.bolotov.exceptions.game.GameException;

public class FlagsLimitReached extends GameException {
    private final int flagsLimit;

    public FlagsLimitReached(int flagsLimit) {
        this.flagsLimit = flagsLimit;
    }

    @Override
    public String getMessage() {
        return String.format("FLAGS LIMIT REACHED: %d", flagsLimit);
    }
}

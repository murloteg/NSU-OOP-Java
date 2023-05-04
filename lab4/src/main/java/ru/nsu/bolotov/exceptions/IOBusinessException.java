package ru.nsu.bolotov.exceptions;

public class IOBusinessException extends BusinessException {
    @Override
    public String getMessage() {
        return "Cannot read from config file";
    }
}

package ru.nsu.bolotov.factory;

import java.io.*;
import ru.nsu.bolotov.commands.*;

public class Factory {
    public Command create(String command) {
        InputStream file = Command.class.getClassLoader().getResourceAsStream("~/lab2/src/main/resources/all.properties");
        switch (command) {
            case "POP": {
                // TODO: add implementation.
            }
            default:
                return null;
        }
    }
}

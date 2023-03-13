package ru.nsu.bolotov.parserofcommands;

import ru.nsu.bolotov.commands.operations.Command;
import ru.nsu.bolotov.commands.representation.CommandRepresentation;
import ru.nsu.bolotov.exceptions.FailedFileReadException;
import ru.nsu.bolotov.exceptions.IllegalFilePathException;
import ru.nsu.bolotov.factory.Factory;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Objects;

public class ParserOfCommands {
    @Nullable
    private Scanner scanner;
    @Nullable
    private BufferedReader in;

    public ParserOfCommands() {
        scanner = new Scanner(System.in);
    }

    public ParserOfCommands(@Nonnull String path) {
        try {
            in = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException exception) {
            throw new IllegalFilePathException();
        }
    }

    public String getNextString() {
        String nextString;
        if (Objects.nonNull(scanner)) {
            nextString = scanner.nextLine();
        } else {
            try {
                nextString = in.readLine();
            } catch (IOException exception) {
                throw new FailedFileReadException();
            }
        }
        return nextString;
    }

    public CommandRepresentation getNextCommand(@Nonnull String nextCommandLine) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String[] separatedCommand = nextCommandLine.split(" ");
        if (separatedCommand[0].contains("#")) {
            separatedCommand[0] = separatedCommand[0].replaceFirst(separatedCommand[0], "COMMENT");
        }
        separatedCommand[0] = separatedCommand[0].toUpperCase();

        Command nextCommand = Factory.create(separatedCommand[0]);
        return new CommandRepresentation(nextCommand, Arrays.copyOfRange(separatedCommand, 1, separatedCommand.length));
    }
}

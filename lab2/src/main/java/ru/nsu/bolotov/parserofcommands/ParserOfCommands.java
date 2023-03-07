package ru.nsu.bolotov.parserofcommands;

import ru.nsu.bolotov.commands.*;
import ru.nsu.bolotov.exceptions.FailedCreationException;
import ru.nsu.bolotov.exceptions.FailedFileReadException;
import ru.nsu.bolotov.exceptions.IllegalFilePathException;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArgsException;
import ru.nsu.bolotov.exceptions.InvalidTypeOfArgumentException;
import ru.nsu.bolotov.factory.Factory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static ru.nsu.bolotov.util.UtilConsts.*;

public class ParserOfCommands {
    private Scanner scanner = null;
    private BufferedReader in = null;

    public ParserOfCommands() {
        scanner = new Scanner(System.in);
    }

    public ParserOfCommands(String path) {
        try {
            in = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException exception) {
            throw new IllegalFilePathException();
        }
    }

    public String getNextString() {
        String nextString;
        if (scanner != null) {
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

    public CommandRepresentation getNextCommand(String nextCommandLine) {
        String[] separatedCommand = nextCommandLine.split(" ");
        Command nextCommand;
//        if (nextCommandLine.contains("#")) {
//            nextCommandLine = getNextString();
//        }
        if (nextCommandLine.contains(PUSH)) {
            checkNumberOfArgs(PUSH, separatedCommand);
            try {
                nextCommand = Factory.create(PUSH);
            } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException exception) {
                throw new FailedCreationException();
            }
            return new CommandRepresentation(nextCommand, new Object[] {separatedCommand[1]});
        } else if (nextCommandLine.contains(POP)) {
            checkNumberOfArgs(POP, separatedCommand);
            try {
                nextCommand = Factory.create(POP);
            } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException exception) {
                throw new FailedCreationException();
            }
            return new CommandRepresentation(nextCommand, new Object[] {});
        } else if (nextCommandLine.contains(PLUS)) {
            checkNumberOfArgs(PLUS, separatedCommand);
            try {
                nextCommand = Factory.create(PLUS);
            } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException exception) {
                throw new FailedCreationException();
            }
            return new CommandRepresentation(nextCommand, new Object[] {});
        } else if (nextCommandLine.contains(MINUS)) {
            checkNumberOfArgs(MINUS, separatedCommand);
            try {
                nextCommand = Factory.create(MINUS);
            } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException exception) {
                throw new FailedCreationException();
            }
            return new CommandRepresentation(nextCommand, new Object[] {});
        } else if (nextCommandLine.contains(MULTIPLY)) {
            checkNumberOfArgs(MULTIPLY, separatedCommand);
            try {
                nextCommand = Factory.create(MULTIPLY);
            } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException exception) {
                throw new FailedCreationException();
            }
            return new CommandRepresentation(nextCommand, new Object[] {});
        } else if (nextCommandLine.contains(DIVIDE)) {
            checkNumberOfArgs(DIVIDE, separatedCommand);
            try {
                nextCommand = Factory.create(DIVIDE);
            } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException exception) {
                throw new FailedCreationException();
            }
            return new CommandRepresentation(nextCommand, new Object[] {});
        } else if (nextCommandLine.contains(SQRT)) {
            checkNumberOfArgs(SQRT, separatedCommand);
            try {
                nextCommand = Factory.create(SQRT);
            } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException exception) {
                throw new FailedCreationException();
            }
            return new CommandRepresentation(nextCommand, new Object[] {});
        } else if (nextCommandLine.contains(PRINT)) {
            checkNumberOfArgs(PRINT, separatedCommand);
            try {
                nextCommand = Factory.create(PRINT);
            } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException exception) {
                throw new FailedCreationException();
            }
            return new CommandRepresentation(nextCommand, new Object[] {});
        } else if (nextCommandLine.contains(DEFINE)) {
            checkNumberOfArgs(DEFINE, separatedCommand);
            try {
                nextCommand = Factory.create(DEFINE);
            } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException exception) {
                throw new FailedCreationException();
            }
            return new CommandRepresentation(nextCommand, new Object[] {separatedCommand[1], separatedCommand[2]});
        } else {
            throw new InvalidTypeOfArgumentException();
        }
    }

    /* TODO: to handle below case (input.txt):
        command ...
        command ...
        #comment ...
     */


    private void checkNumberOfArgs(String commandType, String[] separatedCommand) {
        switch (commandType) {
            case PUSH: {
                if (separatedCommand.length != 2) {
                    throw new InvalidNumberOfArgsException();
                }
                break;
            }
            case POP:
            case PLUS:
            case MINUS:
            case MULTIPLY:
            case DIVIDE:
            case SQRT:
            case PRINT: {
                if (separatedCommand.length != 1) {
                    throw new InvalidNumberOfArgsException();
                }
                break;
            }
            case DEFINE: {
                if (separatedCommand.length != 3) {
                    throw new InvalidNumberOfArgsException();
                }
                break;
            }
            default: {
                throw new InvalidTypeOfArgumentException();
            }
        }
    }
}
